/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ISincronizacionPedidos.java
 *
 * Created on 02/10/2012, 06:14:35 PM
 */
package com.promesa.internalFrame.sincronizacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.administracion.bean.DetalleSincronizacion;
import com.promesa.administracion.sql.SqlHistorialSincronizacion;
import com.promesa.administracion.sql.impl.SqlHistorialSincronizacionImpl;
import com.promesa.bean.BeanDato;
import com.promesa.dialogo.pedidos.DialogoConfirmacionSincronizacion;
import com.promesa.internalFrame.pedidos.custom.ModeloTablaSincrinizacion;
import com.promesa.internalFrame.pedidos.custom.RenderizadorTablaSincronizacion;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.pedidos.sql.SqlMaterial;
import com.promesa.pedidos.sql.SqlSincronizacion;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.pedidos.sql.impl.SqlSincronizacionImpl;
//import com.promesa.sap.SCobranzas;
import com.promesa.sincronizacion.bean.BeanSincronizacion;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.sincronizacion.bean.BeanTiempoTrabajo;
//import com.promesa.sincronizacion.impl.SincronizacionCobranzas;
import com.promesa.sincronizacion.impl.SincronizacionPedidos;
//import com.promesa.sincronizacion.impl.SincronizacionPlanificacion;
import com.promesa.sincronizacion.impl.TiempoTrabajo;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Renderer;
import com.promesa.util.Util;

/**
 * 
 * @author Administrador
 */
@SuppressWarnings("serial")
public class ISincronizacionPedidos extends javax.swing.JInternalFrame {

	private ModeloTablaSincrinizacion modeloTabla;
	private BeanTareaProgramada beanTareaProgramada;
	StringBuilder mensaje;

	public ISincronizacionPedidos(BeanTareaProgramada beanTareaProgramada) {
		initComponents();
		this.beanTareaProgramada = beanTareaProgramada;
		modeloTabla = new ModeloTablaSincrinizacion();
		jTable1.setModel(modeloTabla);
		llenarDatosSincronizacion();
		jTable1.getTableHeader().setReorderingAllowed(false);
		RenderizadorTablaSincronizacion renderizador = new RenderizadorTablaSincronizacion();
		jTable1.setDefaultRenderer(String.class, renderizador);
		jTable1.setDefaultRenderer(Boolean.class, renderizador);
		jTable1.setDefaultRenderer(Integer.class, renderizador);
		jTable1.setDefaultRenderer(JButton.class, renderizador);
		jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/synch.png")));
		jButton2.setVisible(false);
		jPanel1.setVisible(false);
		mensaje = new StringBuilder();
	}

	private void botonSincronizacionManualActionPerformed(java.awt.event.ActionEvent evt) {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				boolean fallo = false;
				public void run() {
					try {
						sincronizacionManual();
					} catch (Exception e2) {
						Util.mostrarExcepcion(e2);
						fallo = true;
					} finally {
						bloqueador.dispose();
						if (fallo) {
						}
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarWarning("Solo se puede sincronizar en modo ONLINE.");
		}
	}

	@SuppressWarnings("deprecation")
	public void sincronizacionCompleta() throws Exception {
		Date fechaInicio = new Date();
		fechaInicio.setMonth(fechaInicio.getMonth() - fechaInicio.getMonth());
		fechaInicio.setDate(1);
		for (BeanSincronizacion bn : modeloTabla.getElementos()) {
			activaSincronizacion(bn);
		}
		llenarDatosSincronizacion();
	}
	
	

	@SuppressWarnings("deprecation")
	public void sincronizacionManual() throws Exception {
		Date fechaInicio = new Date();
		fechaInicio.setMonth(fechaInicio.getMonth() - fechaInicio.getMonth());
		fechaInicio.setDate(1);
		boolean flag = false;
		List<String[]> msg = new ArrayList<String[]>();
		for (BeanSincronizacion bn : modeloTabla.getElementos()) {
			if (bn.isSeleccionado()) {
				msg.add(activaSincronizacion(bn));
				flag = true;
			}
		}
		if (!flag) {
			Mensaje.mostrarAviso("Ninguna opción seleccionada a sincronizar.");
		}
		llenarDatosSincronizacion();
		if (!msg.isEmpty()) {
			final List<String[]> mensajes = new ArrayList<String[]>(msg);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					DialogoConfirmacionSincronizacion dlg = new DialogoConfirmacionSincronizacion(Promesa.getInstance(), true, mensajes, "Confirmación");
					dlg.setVisible(true);
				}
			});
		}
	}

	
	//TODO
	@SuppressWarnings("deprecation")
	public String[] activaSincronizacion(BeanSincronizacion bs) throws Exception {
		String[] mensaje = new String[2];
		int opcion = 0;
		try {
			opcion = Integer.parseInt(bs.getStrIdeSinc());
		} catch (Exception e) {
			opcion = 0;
		}
		int cantidadRegistros = 0;
		com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlSincronizacionImpl = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
		Calendar inicioCalendario = Calendar.getInstance();
		long inicio = inicioCalendario.getTimeInMillis();
		switch (opcion) {
		case 1:
			mensaje[1] = "Sincronización de usuarios";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaUsuario();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO");
			break;
		case 2:
			mensaje[1] = "Sincronización de roles y usuarios";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaRol();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA");
			break;
		case 3:
			mensaje[1] = "Sincronización de agendas";
			Date fechaInicio = new Date();
			fechaInicio.setMonth(fechaInicio.getMonth() - fechaInicio.getMonth());
			fechaInicio.setDate(1);
			BeanBuscarPedido param = new BeanBuscarPedido();
			param.setStrVendorId(beanTareaProgramada.getCodigoUsuario());
			param.setStrDocType("ZP01");
			param.setStrFechaInicio(fechaInicio);
			param.setStrFechaFin(new Date());
			mensaje[0] = SincronizacionPedidos.sincronizarTablaAgenda(param);
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_AGENDA");
			break;
		case 4:
			mensaje[1] = "Sincronización Bloqueos de entrega";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaBloqueoEntrega();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BLOQUEO_ENTREGA");
			break;
		case 5:
			mensaje[1] = "Sincronización de clase de materiales";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaClaseMaterial();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLASE_MATERIAL");
			break;
		case 6:
			String codigoVendedor = Promesa.datose.get(0).getStrCodigo();
			mensaje[1] = "Sincronización de clientes";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaEmpleado(codigoVendedor);
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLIENTE");
			break;
		case 7:
			mensaje[1] = "Sincronización de combos";
			mensaje[0] = SincronizacionPedidos.sincronizaTablaCombo();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_COMBO");
			break;
		case 8:
			mensaje[1] = "Sincronización de condiciones comerciales";
			mensaje[0] = SincronizacionPedidos.sincronizarCondicionesComerciales();
			
			int cant1=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_1X");
			int cant2=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_2X");
			int cant3=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_3X");
			int cant4=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_4X");
			int cant5=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_5X");
			int cant6=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_ESCALAS");
			int cant7=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_ZPR1");
			cantidadRegistros = cant1+cant2+cant3+cant4+cant5+cant6+cant7;
			break;
		case 9:
			mensaje[1] = "Sincronización de condiciones de pago";
			mensaje[0] = SincronizacionPedidos.sincronizarCondicionesPago();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICIONES_PAGO");
			break;
		case 10:
			mensaje[1] = "Sincronización de destinatarios";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaDestinatario();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SEDE");
			break;
		case 11:
			mensaje[1] = "Sincronización de dispositivos";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaDispositivo();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DISPOSITIVO");
			break;
		case 12:
			break;
		case 13:
			mensaje[1] = "Sincronización de feriados";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaFeriado();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FERIADO");
			break;
		case 14:
			mensaje[1] = "Sincronización de jerarquías";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaJerarquia();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_JERARQUIA");
			break;
		case 15:
			mensaje[1] = "Sincronización de materiales";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaMaterial();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL");
			break;
		case 16:
			mensaje[1] = "Sincronización de stock materiales";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaMaterialStock();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_STOCK");
			break;
		case 17:
			mensaje[1] = "Sincronización de tipologías";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaTipologia();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_TIPOLOGIA");
			break;
		case 18:
			mensaje[1] = "Sincronización de consulta dinámica";
			mensaje[0] = SincronizacionPedidos.sincronizaMaterialConsultaDinamica();
			SqlMaterial sqlMaterial = new SqlMaterialImpl();
			Util.CargarConsultaDinamica();
			cantidadRegistros = (sqlMaterial.getCountRowPromoOferta() 
								+ sqlMaterial.getCountRowDsctoPolitica() 
								+ sqlMaterial.getCountRowDsctoManual() 
								+ sqlMaterial.getCountRowTopCliente() 
								+ sqlMaterial.getCountRowTopTipologia());
			break;
		case 19:
			mensaje[1] = "Sincronización de banco cliente";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaBancoCliente();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BANCO_CLIENTE");
			break;
		case 20:
			mensaje[1] = "Sincronización de hoja maestra crédito";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaHojaMaestraCredito();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CABECERA_HOJA_MAESTRA_CREDITO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DIA_DEMORA_TRAS_VENCIMIENTO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_HISTORIAL_PAGO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VALOR_POR_VENCER");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_NOTA_CREDITO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PROTESTO");
			break;
		case 21:
			mensaje[1] = "Sincronización de partida individual";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaPartidaIndividual();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL");
			break;
		case 22:
			mensaje[1] = "Sincronización de pedido pendiente devolución";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaPedidoPendienteDevolucion();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PEDIDO_PENDIENTE_DEVOLUCION");
			break;
		case 23:
			mensaje[1] = "Sincronización de formas de pago de cobranzas";
			mensaje[0] = SincronizacionPedidos.sincronizarFormaPagoCobranza();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FORMA_PAGO_COBRANZA");
			break;
		case 24:
			mensaje[1] = "Sincronización de formas de pago de anticipos";
			mensaje[0] = SincronizacionPedidos.sincronizarFormaPagoAnticipo();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FORMA_PAGO_ANTICIPO");
			break;
		case 25:
			mensaje[1] = "Sincronización de Bancos Promesa";
			mensaje[0] = SincronizacionPedidos.sincronizarBancoPromesa();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BANCO_PROMESA");
			break;
		case 26:
			mensaje[1] = "Cobranzas fuera de linea";
			mensaje[0] = SincronizacionPedidos.sincronizaPartidasIndividualesAbiertas();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA");
			break;
		case 27://	mARCELO mOYANO Secuencia sincronización 03/04/2013 - 14:38
			codigoVendedor = Promesa.datose.get(0).getStrCodigo();
			mensaje[1] = "Secuencial Vendedor";
			mensaje[0] = SincronizacionPedidos.sincronizaSecuenciaPorVendedor();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SECUENCIA");
			break;
		case 28://	mARCELO mOYANO Secuencia sincronización 03/04/2013 - 14:38
			mensaje[1] = "Parametros Constante";
			mensaje[0] = SincronizacionPedidos.sincronizaParametrosConstante();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ZTCONSTANTE");
			break;
		case 29:
			mensaje[1] = "Cobranzas OFFLINE Eliminada: " + sqlSincronizacionImpl.cobranzaEliminados();
			mensaje[0] = SincronizacionPedidos.sincronizacionCobranzasOffLineEliminado();
			cantidadRegistros = sqlSincronizacionImpl.cobranzaEliminados();//Poner la cantidad de Cobranzas se ha enviado a SAP(Eliminado en DB)
			break;
		case 30:
			mensaje[1] = "Anticipos OFFLINE Eliminada: " + sqlSincronizacionImpl.anticiposEliminados();
			mensaje[0] = SincronizacionPedidos.sincronizacionAnticiposOffLineEliminado();
			cantidadRegistros = sqlSincronizacionImpl.anticiposEliminados();//Poner la cantidad de Anticipo se ha enviado a SAP(Eliminado en DB)
			break;
		case 31:
			mensaje[1] = "Presupuesto Cliente";
			mensaje[0] = SincronizacionPedidos.sincronizacionPresupuesto();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PRESUPUESTO");
			break;
		case 32:
			mensaje[1] = "Marca Estrategicas e Indicadores";
			mensaje[0] = SincronizacionPedidos.sincronizacionMarcaEstrategica();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MARCA_ESTRATEGICA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_INDICADORES") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_NOMBRE_MARCA_ESTRATEGICA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MARCA_VENDEDOR");
			break;
		case 33:
			mensaje[1] = "Materiales Nuevos";
			mensaje[0] = SincronizacionPedidos.sincronizarTablaMaterialNuevo();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_NUEVO");
			break;
		case 34:
			codigoVendedor = Promesa.datose.get(0).getStrCodigo();
			mensaje[1] = "Venta Cruzada";
			mensaje[0] = SincronizacionPedidos.sincronizarVentaCruzada(codigoVendedor);
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_VENTA_CRUZADA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VENTA_CRUZADA");
			break;
		case 35:
			codigoVendedor = Promesa.datose.get(0).getStrCodigo();
			mensaje[1] = "Mercadeo";
			mensaje[0] = SincronizacionPedidos.sincronizarMercadeo(codigoVendedor);
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_MERCADEO") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_PROMOCION");
			break;
		default:
			mensaje[1] = "No se ha seleccionado ninguna tabla";
			mensaje[0] = "E";
			cantidadRegistros = 0;
			break;
		}
		Calendar finCalendario = Calendar.getInstance();
		long fin = finCalendario.getTimeInMillis();
		String strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
		com.promesa.administracion.bean.BeanSincronizar beanSincronizar = new com.promesa.administracion.bean.BeanSincronizar();
		beanSincronizar.setStrIdeSinc(bs.getStrIdeSinc());
		beanSincronizar.setStrTie(strTie);
		beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
		beanSincronizar.setStrCantReg(cantidadRegistros + "");
		SqlSincronizacionImpl sqlSincronizacion = new SqlSincronizacionImpl();
		sqlSincronizacion.actualizarSincronizar(beanSincronizar);
		return mensaje;
	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		BeanDato usuario = Promesa.datose.get(0);
		
		if (usuario.getStrModo().equals("1")) {
			
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						
						String strCodigoVendedor = Promesa.datose.get(0).getStrCodigo();
						Promesa.getInstance().mostrarAvisoSincronizacion("SINCRONIZACIÓN TOTAL.");
						Util.validoDescargaCorrecta(strCodigoVendedor);
						if(Util.isFlag()){
							Promesa.getInstance().mostrarAvisoSincronizacion("SINCRONIZANDO BASES MAESTRA.");
						} else {
							Promesa.getInstance().mostrarAvisoSincronizacion("SINCRONIZANDO BASES VENDEDOR.");
						}
						Util.sincronizarTablasPendientes();
						mensaje = Util.SincronizarCreditoAnticiposASAP(strCodigoVendedor);
					} catch (Exception e2) {
						Util.mostrarExcepcion(e2);
					} finally {
						bloqueador.dispose();
						Promesa.getInstance().mostrarAvisoSincronizacion("");
						if(mensaje.length() > 0){
							Mensaje.mostrarAviso(mensaje.toString());
						}
						
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarWarning("Solo se puede sincronizar en modo ONLINE.");
		}
	}

	private void llenarDatosSincronizacion() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				modeloTabla.limpiarItems();
				SqlSincronizacionImpl objSI = new SqlSincronizacionImpl();
				sincronizaciones = objSI.listaSincronizacion();
				Promesa.getInstance().mostrarAvisoSincronizacion("ACTUALIZANDO TABLA SINCRONIZACIÓN.");
				for (BeanSincronizacion beanSincronizacion : sincronizaciones) {
					ActualizarNumeroRegistros(beanSincronizacion);
					modeloTabla.agregarElemento(beanSincronizacion);
				}
				Util.setAnchoColumnas(jTable1);
				jTable1.updateUI();
				((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
				jTable1.setRowHeight(Constante.ALTO_COLUMNAS);
			}
		});
	}
	
	
	private void ActualizarNumeroRegistros(BeanSincronizacion bs){
		int cantidadRegistros = 0;
		int opcion = 0;
		com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlSincronizacionImpl = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
    	opcion = Integer.parseInt(bs.getStrIdeSinc());
    	Calendar inicioCalendario = Calendar.getInstance();
    	long inicio = inicioCalendario.getTimeInMillis();
    	switch (opcion) {
		case 1:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO");
			break;
		case 2:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA");
			break;
		case 3:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_AGENDA");
			break;
		case 4:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BLOQUEO_ENTREGA");
			break;
		case 5:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLASE_MATERIAL");
			break;
		case 6:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLIENTE");
			break;
		case 7:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_COMBO");
			break;
		case 8:
			int cant1=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_1X");
			int cant2=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_2X");
			int cant3=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_3X");
			int cant4=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_4X");
			int cant5=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_5X");
			int cant6=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_ESCALAS");
			int cant7=sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_ZPR1");
			
			cantidadRegistros = cant1+cant2+cant3+cant4+cant5+cant6+cant7;
			break;
		case 9:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICIONES_PAGO");
			break;
		case 10:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SEDE");
			break;
		case 11:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DISPOSITIVO");
			break;
		case 12:
			break;
		case 13:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FERIADO");
			break;
		case 14:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_JERARQUIA");
			break;
		case 15:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL");
			break;
		case 16:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_STOCK");
			break;
		case 17:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_TIPOLOGIA");
			break;
		case 18:
			SqlMaterial sqlMaterial = new SqlMaterialImpl();
			cantidadRegistros = (sqlMaterial.getCountRowPromoOferta() 
					+ sqlMaterial.getCountRowDsctoPolitica() 
					+ sqlMaterial.getCountRowDsctoManual()
					+ sqlMaterial.getCountRowTopCliente()
					+ sqlMaterial.getCountRowTopTipologia());
			break;
		case 19:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BANCO_CLIENTE");
			break;
		case 20:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CABECERA_HOJA_MAESTRA_CREDITO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DIA_DEMORA_TRAS_VENCIMIENTO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_HISTORIAL_PAGO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VALOR_POR_VENCER");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_NOTA_CREDITO");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PROTESTO");
			break;
		case 21:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL");
			break;
		case 22:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PEDIDO_PENDIENTE_DEVOLUCION");
			break;
		case 23:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FORMA_PAGO_COBRANZA");
			break;
		case 24:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FORMA_PAGO_ANTICIPO");
			break;
		case 25:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BANCO_PROMESA");
			break;
		case 26:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA");
			break;
		case 27:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SECUENCIA");
			break;
		case 28:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ZTCONSTANTE");
			break;
		case 29:
			cantidadRegistros = sqlSincronizacionImpl.cobranzaEliminados();
			break;
		case 30:
			cantidadRegistros = sqlSincronizacionImpl.anticiposEliminados();
			break;
		case 31:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PRESUPUESTO");
			break;
		case 32:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MARCA_ESTRATEGICA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_INDICADORES") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_NOMBRE_MARCA_ESTRATEGICA");
			break;
		case 33:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_NUEVO");
			break;
		case 34:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_VENTA_CRUZADA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VENTA_CRUZADA");
			break;
		case 35:
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_MERCADEO") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_PROMOCION");
			break;
		default:
			cantidadRegistros = 0;
			break;
		}
    	Calendar finCalendario = Calendar.getInstance();
		long fin = finCalendario.getTimeInMillis();
		String strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
		com.promesa.administracion.bean.BeanSincronizar beanSincronizar = new com.promesa.administracion.bean.BeanSincronizar();
		beanSincronizar.setStrIdeSinc(bs.getStrIdeSinc());
		beanSincronizar.setStrTie(strTie);
		beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
		beanSincronizar.setStrCantReg(cantidadRegistros + "");
    	bs.setStrNumCantReg(String.valueOf(cantidadRegistros));
    	bs.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
    	bs.setStrNumTie(strTie);
    	sqlSincronizacionImpl.setNumeroRegistro(bs);
    	SqlSincronizacionImpl sqlSincronizacion = new SqlSincronizacionImpl();
		sqlSincronizacion.actualizarSincronizar(beanSincronizar);
		
	};

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void creaTablaDescripciones(List<BeanSincronizar> lista) {
		modelOpciones = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return true;
				} else {
					return false;
				}
			}
		};
		modelOpciones.addColumn("ÚLTIMA SINCRONIZACIÓN");
		modelOpciones.addColumn("TIEMPO (SEGUNDOS)");
		tablaOpciones = new JTable(modelOpciones);
		tablaOpciones.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getLeftCell());
		tablaOpciones.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getLeftCell());
		scrollerOpciones = new JScrollPane();
		scrollerOpciones.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerOpciones.setViewportView(tablaOpciones);
		scrollerOpciones.setPreferredSize(new Dimension(400, 250));
		modelOpciones.setRowCount(0);
		for (BeanSincronizar b : lista) {
			modelOpciones.addRow(new Object[] { b.getStrFecHor(), b.getStrTie() });
		}
		TableRowSorter sorterOpciones = new TableRowSorter<DefaultTableModel>(modelOpciones);
		tablaOpciones.setRowSorter(sorterOpciones);
		((DefaultTableCellRenderer) tablaOpciones.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tablaOpciones.setRowHeight(22);
		popupOpciones = new JPopupMenu();
		popupOpciones.setLayout(new BorderLayout());
		popupOpciones.add(scrollerOpciones, BorderLayout.CENTER);
		popupOpciones.add(scrollerOpciones);
		Promesa.getInstance().add(popupOpciones);
		int posX = this.getWidth() - 2 * popupOpciones.getWidth();
		int posY = this.getHeight() - 2 * popupOpciones.getHeight();
		popupOpciones.show(Promesa.getInstance(), posX / 2, posY / 2);
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		int filaSeleccionada = jTable1.getSelectedRow();
		if (filaSeleccionada > -1) {
			DetalleSincronizacion detalleSincronizacion = new DetalleSincronizacion();
			Date fechaHoraSistema = new Date();
			Date HoraProgramada = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
			String fechaHoraTemp = dateFormat2.format(fechaHoraSistema);
			detalleSincronizacion.setNumFec(jTextField1.getText().trim());
			detalleSincronizacion.setTxtHorIni(jSpinner1.getValue().toString().trim() + ":" + jSpinner2.getValue().toString().trim() + ":"
					+ jSpinner3.getValue().toString().trim() + " " + jSpinner4.getValue().toString().trim());
			String fechaHoraActual = fechaHoraTemp + " " + detalleSincronizacion.getTxtHorIni();
			try {
				HoraProgramada = new Date(dateFormat.parse(fechaHoraActual).getTime());
				SqlSincronizacion getSincronizacion = new SqlSincronizacionImpl();
				detalleSincronizacion.setNumIdeSinc(modeloTabla.obtenerElemento(filaSeleccionada).getStrIdeSinc());
				getSincronizacion.setActualizarSincronizacionDetalle(detalleSincronizacion);
				llenarDatosSincronizacion();
				beanTareaProgramada.getTemporizador()[filaSeleccionada].cancel();
				beanTareaProgramada.getTemporizador()[filaSeleccionada] = new Timer();
				beanTareaProgramada.getTemporizador()[filaSeleccionada].scheduleAtFixedRate(new TiempoTrabajo(
										new BeanTiempoTrabajo(beanTareaProgramada.getTemporizador()[filaSeleccionada],
												HoraProgramada, Integer.parseInt(detalleSincronizacion.getNumFec().trim()),
												tarea, Constante.OPCIONES_PEDIDOS, beanTareaProgramada .getCodigoUsuario(),
												beanTareaProgramada .getPopupOpciones(), false)), HoraProgramada, 1000);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		}
	}
	
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		botonSincronizacionManual = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JToolBar.Separator();
		jButton3 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jPanel1 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jSpinner1 = new javax.swing.JSpinner();
		jLabel3 = new javax.swing.JLabel();
		jSpinner2 = new javax.swing.JSpinner();
		jLabel4 = new javax.swing.JLabel();
		jSpinner3 = new javax.swing.JSpinner();
		jSpinner4 = new javax.swing.JSpinner();
		jButton2 = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		jTextField1 = new javax.swing.JTextField();
		jPanel6 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Sincronización de pedidos");

		jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);

		botonSincronizacionManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sincronizar.png"))); // NOI18N
		jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar_24.png")));
		jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/all.png"))); // NOI18N
		botonSincronizacionManual.setText("Sincronizar marcados");
		botonSincronizacionManual.setFocusable(false);

		botonSincronizacionManual.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonSincronizacionManualActionPerformed(evt);
			}
		});

		botonSincronizacionManual.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		jToolBar1.add(botonSincronizacionManual);
		jToolBar1.add(jSeparator1);

		jButton3.setText("Sincronizar todo");
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton3);

		getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		jScrollPane1.setViewportView(jTable1);

		getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jPanel1.setLayout(new java.awt.BorderLayout(5, 5));

		jPanel2.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

		jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jSpinner1.setModel(new javax.swing.SpinnerNumberModel(12, 0, 12, 1));
		jSpinner1.setMinimumSize(new java.awt.Dimension(60, 20));
		jSpinner1.setPreferredSize(new java.awt.Dimension(60, 20));
		jPanel3.add(jSpinner1);

		jLabel3.setText(":");
		jPanel3.add(jLabel3);

		jSpinner2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
		jSpinner2.setPreferredSize(new java.awt.Dimension(60, 20));
		jPanel3.add(jSpinner2);

		jLabel4.setText(":");
		jPanel3.add(jLabel4);

		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jSpinner3.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
		jSpinner3.setPreferredSize(new java.awt.Dimension(60, 20));
		jPanel3.add(jSpinner3);

		jSpinner4.setModel(new javax.swing.SpinnerListModel(new String[] { "AM", "PM" }));
		jPanel3.add(jSpinner4);
		jPanel3.add(jButton2);

		jPanel2.add(jPanel3);

		jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jTextField1.setMinimumSize(new java.awt.Dimension(100, 20));
		jTextField1.setPreferredSize(new java.awt.Dimension(100, 20));
		jPanel4.add(jTextField1);
		jPanel2.add(jPanel4);
		jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);
		jPanel6.setLayout(new java.awt.GridLayout(2, 1, 5, 5));
		jLabel1.setText("HORA:");
		jPanel6.add(jLabel1);
		jLabel2.setText("FRECUENCIA:");
		jPanel6.add(jLabel2);
		jPanel1.add(jPanel6, java.awt.BorderLayout.LINE_START);
		getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
		pack();
	}// </editor-fold>
		// Variables declaration - do not modify

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;

		private int posicion;

		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setContentAreaFilled(false);
			button.setOpaque(true);
			button.setIcon(new javax.swing.ImageIcon(getClass().getResource( "/imagenes/detail.png")));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(new Color(252, 221, 130));
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(UIManager.getColor("Button.background"));
			}
			posicion = row;
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				SqlHistorialSincronizacion sqlHistorialSincronizacion = new SqlHistorialSincronizacionImpl();
				int id = Integer.parseInt(((BeanSincronizacion) sincronizaciones.get(posicion)).getStrIdeSinc().trim());
				sqlHistorialSincronizacion.listaHistorialSincronizacion(id);
				List<BeanSincronizar> lista = sqlHistorialSincronizacion.getlistaHistorialSincronizacion();
				try {
					creaTablaDescripciones(lista);
				} catch (Exception exec) {
					Util.mostrarExcepcion(exec);
				}
			}
			isPushed = false;
			return new String("");
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	private javax.swing.JButton botonSincronizacionManual;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JSpinner jSpinner1;
	private javax.swing.JSpinner jSpinner2;
	private javax.swing.JSpinner jSpinner3;
	private javax.swing.JSpinner jSpinner4;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JToolBar jToolBar1;

	private DefaultTableModel modelOpciones;
	private List<BeanSincronizacion> sincronizaciones;
	private javax.swing.JTable tablaOpciones;
	private JScrollPane scrollerOpciones;
	@SuppressWarnings("unused")
	private JDialog dlgOpciones;
	private JPopupMenu popupOpciones = null;
	private String tarea = "";

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
			setContentAreaFilled(false);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
					if (isSelected) {
						setForeground(table.getSelectionForeground());
						setBackground(new Color(252, 221, 130));
					} else {
						setForeground(table.getForeground());
						setBackground(UIManager.getColor("Button.background"));
					}
					setText((value == null) ? "" : value.toString());
					setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/detail.png")));
			return this;
		}
	}
}
