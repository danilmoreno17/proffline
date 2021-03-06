package com.promesa.internalFrame.pedidos;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import com.promesa.bean.BeanDato;
import com.promesa.dialogo.pedidos.DConfirmacionMultiplesPedidos;
import com.promesa.dialogo.pedidos.DConfirmacionPedidoUnico;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanBloqueoEntrega;
import com.promesa.pedidos.bean.BeanMensaje;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.sql.impl.SqlBloqueoEntregaImpl;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class ITomarPedidos extends IPedidos {

	public ITomarPedidos(String codigoCliente, String nombreCliente,
			String claseRiesgo, String limiteCredito, String disponibilidad,
			String condicionPago, String titulo, String tituloReporte) {
		super(new Date().toString(),null, codigoCliente, nombreCliente, claseRiesgo, limiteCredito, disponibilidad, condicionPago, titulo, tituloReporte, "ZP01", Constante.VENTANA_CREAR_PEDIDO);
		setTitle("Pedido " + codigoCliente + "-" + nombreCliente);
		tituloImpresion = "Pedido";
		txtBloqEntrega.setEditable(true);
		instancia = this;
		btnImprimirComprobante.setVisible(false);
		bloqueoEntrega = "";
		txtBloqEntrega.setText(bloqueoEntrega);
		llenarTablaBloqueosEntrega();
		SqlClienteImpl sqlCliente = new SqlClienteImpl();
		String condicionPagoPorDefecto = sqlCliente.obtenerCondicionPagoPorDefecto(codigoCliente);
		txtCondPago.setText(condicionPagoPorDefecto);
	}

	@Override
	protected void finalizarTransaccion() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			@SuppressWarnings("unused")
			public void run() {
				boolean fallo = false;
				boolean sync = false;
				String mensajeError = "";
				BeanDato usuario = Promesa.datose.get(0);
				List<BeanMensaje> mensajes = null;
				String src = "";
				try {
					if (usuario.getStrModo().equals("1")) {
						mensajes = guardarPedidoSAP();
						src = "0";
						sync = true;
						if(mensajes == null)
						{
							fallo = true;
						}
					} else {
						mensajes = guardarPedidoSQLite();
						src = "1";
					}
				} catch (Exception e) {
					fallo = true;
					mensajeError = e.getMessage();
				} finally {
					bloqueador.dispose();
					if (fallo) {
						Mensaje.mostrarError("No Existen Materiales Ingresados");
					} else {
						if (mensajes != null && !mensajes.isEmpty()) {
							if (mensajes.size() == 1) {
								final BeanMensaje msg = mensajes.get(0);
								if (msg.getTipo().compareTo("N") == 0) {
									dispose();
									DConfirmacionPedidoUnico dlg = new DConfirmacionPedidoUnico(Promesa.getInstance(), true, msg.getDescripcion(), msg.getIdentificador(), sync, "Pedido", codigoCliente, nombreCliente, direccionDestinatario, descripcionCondicionPago, "ZP01");
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
								DConfirmacionMultiplesPedidos dlg = new DConfirmacionMultiplesPedidos( (JFrame) Promesa.getInstance(), false, mensajes, header, src, tituloImpresion, descripcionCondicionPago, direccionDestinatario);
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
