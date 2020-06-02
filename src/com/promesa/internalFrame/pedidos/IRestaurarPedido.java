package com.promesa.internalFrame.pedidos;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar.Separator;
import javax.swing.ScrollPaneConstants;

import com.itextpdf.text.Font;
import com.promesa.bean.BeanDato;
import com.promesa.dialogo.pedidos.DConfirmacionMultiplesPedidos;
import com.promesa.dialogo.pedidos.DConfirmacionPedidoUnico;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanBloqueoEntrega;
import com.promesa.pedidos.bean.BeanCondicionExpedicion;
import com.promesa.pedidos.bean.BeanMensaje;
import com.promesa.pedidos.bean.BeanPedido;
//import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.sql.impl.SqlBloqueoEntregaImpl;
import com.promesa.pedidos.sql.impl.SqlCondicionExpedicionImpl;
//import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class IRestaurarPedido extends IPedidos {

	private String source;

	public IRestaurarPedido(BeanPedido pedido, String codigoCliente, String nombreCliente, String claseRiesgo, String limiteCredito, String disponibilidad, String condicionPago, String titulo,
			String tituloReporte, String tipoDocumentoVenta, String source, List<Item> items, String mensajes) {
		// CLASE PADRE
		super(new Date().toString(),pedido, codigoCliente, nombreCliente, claseRiesgo, limiteCredito, disponibilidad, condicionPago, titulo, tituloReporte, tipoDocumentoVenta, Constante.VENTANA_CREAR_PEDIDO);
		setTitle(titulo);
		this.tipoDocumento = pedido.getHeader().getDOC_TYPE();
		this.tituloImpresion = tituloReporte;
		this.source = source;
		txtCodigoPedido.setText(pedido.getHeader().getIdBD());
		bloqueoEntrega = pedido.getHeader().getDLV_BLOCK();
		numeroPedidoCliente = pedido.getHeader().getPURCH_NO_C();
		txtNPedCliente.setText(numeroPedidoCliente);
		txtBloqEntrega.setText(bloqueoEntrega);
		txtBloqEntrega.setEditable(true);
//		separador8.setVisible(true);
		btnImprimirComprobante.setVisible(true);
		condicionExpedicion = pedido.getHeader().getSHIP_COND();
		establecerCondicionExpedicion();
		SqlClienteImpl sql = new SqlClienteImpl();
		String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
		for (Item item : items) {
			String tipoMaterial = item.getTipoMaterial();
			if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
				tipoMaterial = "B";
			} else if (tipoMaterial.compareTo("N") == 0 || (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
				tipoMaterial = "N";
			} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
				tipoMaterial = "P";
			} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
				tipoMaterial = "R";
			}
			item.setTipoMaterial(tipoMaterial);
			item.setStrFech_Ing("");
			mdlTblItems.agregarItem(item, -2);
		}
		establecerInformacionDePedido();
		llenarTablaBloqueosEntrega();
		btnImprimirComprobante.setVisible(false);
		generarMensaje(mensajes.split("\n"));
	}

	@SuppressWarnings("unused")
	private void deshabilitarEdicion() {
		mnuToolBarTop.add(new Separator());
		JLabel warning = new JLabel();
		warning.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
		warning.setText("<html><font color='red'>EL PICKING YA HA SIDO REALIZADO - NO SE PUEDE EDITAR LA ORDEN.</font></html>");
		mnuToolBarTop.add(warning);
		mdlTblItems.deshabilitarTodo();
		pnlDatos.setEnabled(false);
		btnDetalleDestinatario.setVisible(false);
		btnDetalleBloqEntrega.setVisible(false);
		btnDetalleCondPago.setVisible(false);
		txtBloqEntrega.setEditable(false);
		btnAniadir.setEnabled(false);
		btnEliminar.setEnabled(false);
		btnConsultaCapturaDinamica.setEnabled(false);
		btnGuiaVentas.setEnabled(false);
		btnSimularTodo.setEnabled(false);
		btnGuardarOrden.setEnabled(false);
		btnCancelar.setEnabled(false);
		cmbCondExped.setEnabled(false);
		tblPedidos.setEnabled(false);
		editable = false;
	}

	@SuppressWarnings("unused")
	private void deshabilitarEdicion2() {
		mdlTblItems.deshabilitarTodo();
		pnlDatos.setEnabled(false);
		btnDetalleDestinatario.setVisible(false);
		btnDetalleBloqEntrega.setVisible(false);
		btnDetalleCondPago.setVisible(false);
		txtBloqEntrega.setEditable(false);
		btnAniadir.setEnabled(false);
		btnEliminar.setEnabled(false);
		btnConsultaCapturaDinamica.setEnabled(false);
		btnGuiaVentas.setEnabled(false);
		btnSimularTodo.setEnabled(false);
		btnGuardarOrden.setEnabled(false);
		btnCancelar.setEnabled(false);
		cmbCondExped.setEnabled(false);
		tblPedidos.setEnabled(false);
	}

	@Override
	protected void finalizarTransaccion() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				boolean fallo = false;
				boolean sync = false;
				String mensajeError = "";
				BeanDato usuario = Promesa.datose.get(0);
				List<BeanMensaje> mensajes = new ArrayList<BeanMensaje>();
				String src = "";
				try {
					if (source.equals("0") && usuario.getStrModo().equals("1")) {
						mensajes = guardarPedidoSAP();
						src = "0";
						sync = true;
					} else {//Marcelo Moyano 22/12/2014
						BeanPedido orden = pedido;
						orden = llenarEstructuraParaGuardarActualizacion();
						orden.getHeader().setIdBD(pedido.getHeader().getIdBD());
						if(orden != null){
							BeanMensaje mensaje = editarPedidoSQLite(orden);
							mensajes.add(mensaje);
							src = "1";
						}
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					fallo = true;
					mensajeError = e.getMessage();
				} finally {
					bloqueador.dispose();
					if (fallo) {
						Mensaje.mostrarError(mensajeError);
					} else {
						if (mensajes != null && !mensajes.isEmpty()) {
							if (mensajes.size() == 1) {
								final BeanMensaje msg = mensajes.get(0);
								if (msg.getTipo().compareTo("N") == 0) {
									dispose();
									DConfirmacionPedidoUnico dlg = new DConfirmacionPedidoUnico( Promesa.getInstance(), true, msg.getDescripcion(), msg.getIdentificador(), sync, "Pedido", codigoCliente, nombreCliente, direccionDestinatario, descripcionCondicionPago, "ZP01");
									dlg.setVisible(true);
								} else {
									Mensaje.mostrarError(msg.getDescripcion());
								}
							} else {
								// Se crearon ordenes multiples
								BeanPedidoHeader header = new BeanPedidoHeader();
								header.setStrCodCliente(codigoCliente);
								header.setStrCliente(nombreCliente);
								header.setStrCondicionPago(codigoCliente);
								dispose();
								DConfirmacionMultiplesPedidos dlg = new DConfirmacionMultiplesPedidos((JFrame) Promesa.getInstance(), false, mensajes, header, src, tituloImpresion, descripcionCondicionPago, direccionDestinatario);
								dlg.setVisible(true);
							}
						}
					}
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	protected void establecerInformacionDePedido() {
		condicionPago = pedido.getHeader().getStrCondicionPago();
		organizacionVentas = pedido.getHeader().getSALES_ORG();
		canalDistribucion = pedido.getHeader().getDISTR_CHAN();
		codigoSector = pedido.getHeader().getDIVISION();
		condicionExpedicion = pedido.getHeader().getSHIP_COND();
		bloqueoEntrega = pedido.getHeader().getDLV_BLOCK();
		txtBloqEntrega.setText(bloqueoEntrega);
		txtValorNeto.setText(pedido.getHeader().getStrValorNeto());
		
		mdlTblItems.asociarItems();
		mdlTblItems.asociarItemsYPrecios();
		tblPedidos.updateUI();
		txtValorNeto.setText("" + mdlTblItems.getValorNeto());
		mdlTblItems.actualizarPosiciones();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void establecerCondicionExpedicion() {
		SqlCondicionExpedicionImpl controller = new SqlCondicionExpedicionImpl();
		controller.setListaCondicionExpedicion();
		Object[] condiciones = controller.getListaCondicionExpedicion().toArray();
		cmbCondExped.setModel(new DefaultComboBoxModel(condiciones));
		for (int i = 0; i < condiciones.length; i++) {
			BeanCondicionExpedicion bn = (BeanCondicionExpedicion) condiciones[i];
			if (bn.getStrIdCondicionExpedicion().compareTo(condicionExpedicion) == 0) {
				cmbCondExped.setSelectedItem(bn);
				break;
			}
		}
	}

	@Override
	protected void llenarTablaBloqueosEntrega() {
		try {
			tablaBloqueos = new JTable(modeloBloqueo);
			tablaBloqueos.getTableHeader().setReorderingAllowed(false);
			modeloBloqueo.setRowCount(0);
			tablaBloqueos.getColumn("C�DIGO").setMaxWidth(80);
			tablaBloqueos.getColumn("C�DIGO").setMinWidth(80);
			tablaBloqueos.getColumn("C�DIGO").setPreferredWidth(80);
			tablaBloqueos.getColumn("DESCRIPCI�N").setMaxWidth(320);
			tablaBloqueos.getColumn("DESCRIPCI�N").setMinWidth(320);
			tablaBloqueos.getColumn("DESCRIPCI�N").setPreferredWidth(320);
			scrollerBloqueo = new JScrollPane();
			scrollerBloqueo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollerBloqueo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollerBloqueo.setViewportView(tablaBloqueos);
			scrollerBloqueo.setPreferredSize(new Dimension(400, 150));
			popupBloqueo = new JPopupMenu();
			popupBloqueo.add(scrollerBloqueo);
			tablaBloqueos.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					seleccionarBloqueoEntrega(evt);
				}
			});
			SqlBloqueoEntregaImpl bloqueo = new SqlBloqueoEntregaImpl();
			bloqueo.setListaBloqueoEntrega();
			List<BeanBloqueoEntrega> lista_bloqueo = bloqueo.getListaBloqueoEntrega();

			for (BeanBloqueoEntrega bean : lista_bloqueo) {
				String[] strings = new String[2];
				strings[0] = bean.getCodigo();
				strings[1] = bean.getDescripcion();
				if (strings[0].compareTo("09") == 0) {
					modeloBloqueo.addRow(strings);
				}
			}
		} catch (Exception exec) {
			Util.mostrarExcepcion(exec);
		}
	}
}