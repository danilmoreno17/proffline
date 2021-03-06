package com.promesa.internalFrame.pedidos;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.JToolBar.Separator;

import com.itextpdf.text.Font;
import com.promesa.bean.BeanDato;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanBloqueoEntrega;
import com.promesa.pedidos.bean.BeanCondicionExpedicion;
import com.promesa.pedidos.bean.BeanMensaje;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.sql.impl.SqlBloqueoEntregaImpl;
import com.promesa.pedidos.sql.impl.SqlCondicionExpedicionImpl;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class IEditarPedido extends IPedidos {
	private IListaOrdenes contenedor;
	
	public IListaOrdenes getContenedor() {
		return contenedor;
	}

	public void setContenedor(IListaOrdenes contenedor) {
		this.contenedor = contenedor;
	}

	public IEditarPedido(BeanPedido pedido, String codigoCliente, String nombreCliente, String claseRiesgo, String limiteCredito, String disponibilidad, String condicionPago, String titulo,
//			String tituloReporte, String tipoDocumentoVenta, String source) {
			String tituloReporte, String tipoDocumentoVenta, String source, IListaOrdenes contenedor) {
		// CLASE PADRE
		super(new Date().toString(),pedido, codigoCliente, nombreCliente, claseRiesgo, limiteCredito,disponibilidad, condicionPago, titulo, tituloReporte, tipoDocumentoVenta, Constante.VENTANA_EDITAR_PEDIDO);
		setTitle(titulo);
		this.tituloImpresion = tituloReporte;
		this.source = source;
		setContenedor(contenedor);
		if (tipoDocumentoVenta.compareTo("ZO01") == 0) {
			deshabilitarEdicion2();
			editable = false;
		} else {
			if (pedido.getHeader().getStrEstadoPicking().compareTo("1") == 0 
					|| Util.esPedidoZPXX(pedido.getHeader().getStrClDocVentas())) {
				deshabilitarEdicion();
			} else {
				enmascararEventoTeclaTab();
				establecerOyenteTeclaTab();
				agregarEventoClicDerecho();
			}
		}
		if(source.equals("2"))
			txtCodigoPedido.setText(pedido.getHeader().getCodigoPedido());
		else
			txtCodigoPedido.setText(pedido.getHeader().getStrDocumentoVenta());
		
		bloqueoEntrega = pedido.getHeader().getDLV_BLOCK();
		numeroPedidoCliente = pedido.getHeader().getStrClDocVentas();
		txtNPedCliente.setText(numeroPedidoCliente);
		txtBloqEntrega.setText(bloqueoEntrega);
		txtBloqEntrega.setEditable(true);
//		separador8.setVisible(true);
		btnImprimirComprobante.setVisible(true);
		condicionExpedicion = pedido.getHeader().getSHIP_COND();
		String destinatario = pedido.getPartners().getHm().get("WE");
		txtDestinatario.setText(Util.eliminarCerosInicios(destinatario));
		
		establecerCondicionExpedicion();
		establecerInformacionDePedido();
		llenarTablaBloqueosEntrega();
	}

	private void deshabilitarEdicion() {
		mnuToolBarTop.add(new Separator());
		JLabel warning = new JLabel();
		warning.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
		warning.setText("<html><font color='red'>NO SE PUEDE EDITAR ORDEN </font></html>");
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
		class MyWorker extends SwingWorker<Void, Void> {
			boolean fallo = false;
			String mensajeError = "";
			BeanDato usuario = Promesa.datose.get(0);
			BeanMensaje mensajes = null;
			List<BeanMensaje> mensajesSAP2SQLite = null; 
			protected Void doInBackground() {
				try {
					BeanPedido orden = null;
					if (source.compareTo("0") == 0 && usuario.getStrModo().equals("1")) {// source = 0 viene de SAP
						if (noHayPedidosMezclados()) {
							orden = llenarEstructuraParaGuardarActualizacion();
							// Marcelo Moyano
							if(orden == null){
								fallo = true;
							}else{
								mensajes = editarPedidoSAP(orden);
							}
						} else {
							Mensaje.mostrarWarning("No deben haber materiales mezclados en una misma orden.");							
						}
					} else if (source.compareTo("1") == 0) {
						if (noHayPedidosMezclados()){
							orden = llenarEstructuraParaGuardarSQLite();
//							orden = llenarEstructuraParaGuardarActualizacion();
							orden.getHeader().setIdBD(pedido.getHeader().getIdBD());
							mensajes = editarPedidoSQLite(orden);
						}else {
							Mensaje.mostrarWarning("No deben haber materiales mezclados en una misma orden.");
						}
						
						/*
						 * 	MARCELO MOYANO 17/04/2013 - 17:29
						 * 	REQUERIMIENTO PRO-2013-0025
						 * 	TOPIC MODIFICAR PEDIDOS OFFLINE GRABADOS EN SAP
						 */
					}else if(source.compareTo("0") == 0 && usuario.getStrModo().equals("0")){
						if (noHayPedidosMezclados()){
							//Pedido viene de sap pero no hay conecci�n
							orden = llenarEstructuraParaGuardarSQLite();
							mensajesSAP2SQLite =  guardarPedidoSap2SQLite(orden);
							mensajes = mensajesSAP2SQLite.get(0);
						}else {
							Mensaje.mostrarWarning("No deben haber materiales mezclados en una misma orden.");
						}
					}else if(source.compareTo("2") == 0 ){
						if (noHayPedidosMezclados()){
							orden = llenarEstructuraParaGuardarSQLite();
							orden.getHeader().setIdBD(pedido.getHeader().getIdBD());
							orden.setCodigoPedido(txtCodigoPedido.getText());
							mensajes = editarPedidoSQLite(orden);
						}else {
							Mensaje.mostrarWarning("No deben haber materiales mezclados en una misma orden.");
						}
					}
					//	Marcelo Moyano
				} catch (Exception e) {
					fallo = true;
					mensajeError = e.getMessage();
					Util.mostrarExcepcion(e);
				}
				return null;
		     }

		     protected void done() {
		        //progressBar.setVisible(false);
		    	 bloqueador.dispose();
		        if (fallo) {
					mensajeError = "No existe Material para Actualizar Pedido";
					Mensaje.mostrarWarning(mensajeError);
				} else {
					if (mensajes != null && !mensajes.getTipo().equals(null)) {
						if (mensajes.getTipo().compareTo("N") == 0) {
							dispose();
							Mensaje.mostrarAviso(mensajes.getDescripcion());
							if(contenedor != null){
								contenedor.actualizarPedidos();
							}
						} else {
							Mensaje.mostrarError(mensajes.getDescripcion());
						}
					}
				}
		     }
		  }
		new MyWorker().execute();
		bloqueador.setVisible(true);
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 			
	 * 			PEDIDO EDITADO DE SAP ES GUARDADO EN BASE
	 * 			LOCAL CUANDO NO TIENE CONECCION A INTERNET
	 * 
	 * @param	RECIBE COMO PARAMETRO LA ORDEN DEL PEDIDO
	 * 			QUE FUE EDITADO PARA GUARDARLO EN LA BASE LOCAL
	 * 
	 * @return	RETORNA UNA LISTA DE MENSAJES DE CONFIRMACI�N
	 * 			DE GUARDADO O MENSAJE DE ADVERTENCIA O ERROR
	 * 			SI NO SE HA GUARDADO EL PEDIDO
	 */
	protected List<BeanMensaje> guardarPedidoSap2SQLite(BeanPedido orden) {
		List<BeanMensaje> mensajes = new ArrayList<BeanMensaje>();
		List<BeanPedido> pedidos = llenarEstructuraParaCreacionEnSQLite();
		String codigoPedido = orden.getCodigoPedido();
		if (pedidos == null) {
			return null;
		}
		for (BeanPedido pedido : pedidos) {
			if (!pedido.getDetalles().isEmpty()) {
				try {
					for (int i = 0; i < pedido.getDetalles().size(); i++) {
						BeanPedidoDetalle detalle = pedido.getDetalles().get(i);
						int posicion = (i + 1) * 10;
						detalle.setPosicion(Util.completarCeros(6, "" + posicion));
					}
					pedido.setCodigoPedido(codigoPedido);
					String mensaje[] = guardarPedidoSQLite(pedido);
					if (mensaje != null) {
						BeanMensaje msg = new BeanMensaje("");
						Long codigo = 0l;
						try {
							codigo = Long.parseLong(mensaje[2]);
						} catch (NumberFormatException e) {
							Util.mostrarExcepcion(e);
							codigo = -1l;
						}
						msg.setIdentificador(codigo);
						msg.setDescripcion("El pedido de SAP se Guardo temporalmente en base Local.");
						msg.setTipo("N");
						mensajes.add(msg);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			}
		}
		return mensajes;
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
		List<BeanPedidoDetalle> listaDetallesPedido = pedido.getDetalles();
		marcadosParaActualizar = new HashMap<String, String>();
		SqlMaterialImpl sql = new SqlMaterialImpl();
		HashMap<String, String> codigos = sql.getTipoMaterial();
		int cantPadre = 1;
		for (int i = 0; i < listaDetallesPedido.size(); i++) {
			BeanPedidoDetalle b = listaDetallesPedido.get(i);
			Item item = new Item();
			item.setPosicion(b.getPosicion());
			item.setCodigo(b.getMaterial());
			marcadosParaActualizar.put(b.getPosicion(), b.getMaterial());
			item.setCantidadConfirmada(b.getCantidadConfirmada());
			item.setUnidad(b.getUM());
			item.setEditable(true);
			item.setEliminadoLogicamente(false);
			item.setEliminiable(false);
			item.setStrFech_Ing("");
			item.setPorcentajeDescuento(b.getPorcentajeDescuento());
			item.setDenominacion(b.getDenominacion());
			item.setTipoSAP(b.getTipoSAP());
			String materialCombo = codigos.get(b.getMaterial());
			if(materialCombo != null && !materialCombo.equals("")){
				item.setTipoMaterial(materialCombo);
			} else {
				item.setTipoMaterial("N");
			}
			Double precioNeto = 0d;
			try {
				precioNeto = Double.parseDouble(b.getPrecioNeto());
			} catch (Exception e) {
				precioNeto = 0d;
				Util.mostrarExcepcion(e);
			}
			item.setPrecioNeto(precioNeto);
			Double valorNeto = 0d;
			try {
				valorNeto = Double.parseDouble(b.getValorNeto());
			} catch (Exception e) {
				valorNeto = 0d;
				Util.mostrarExcepcion(e);
			}
			item.setValorNeto(valorNeto);
			item.setTipo(b.getTipo());
			item.setEditable(true);
			int cantidadAMostrar = 0;
			
			if (item.getTipo() == 2 || item.getTipo() == 3) {
				try {
					cantidadAMostrar = (int) Double.parseDouble(b.getCantidad()) / cantPadre;
				} catch (Exception e) {
					cantidadAMostrar = 0;
					Util.mostrarExcepcion(e);
				}
				item.setCantidadAMostrar(cantidadAMostrar);
				item.setEditable(false);
			} else {
				if(this.source.equals("0")){
					cantPadre = (int) Double.parseDouble(b.getCantidad());
				}
				item.setCantidad(b.getCantidad());
			}
			
			item.setSimulado(true);

			if (b != null && b.getMotivoRechazo() != null && b.getMotivoRechazo().trim().equals("19")) {
				item.setEliminadoLogicamente(true);
			}

			mdlTblItems.agregarItem(item, -2);
		}
		mdlTblItems.asociarItems();
		mdlTblItems.asociarItemsYPrecios();
		mdlTblItems.actualizarPosiciones();
		tblPedidos.updateUI();
		txtValorNeto.setText("" + mdlTblItems.getValorNeto());
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