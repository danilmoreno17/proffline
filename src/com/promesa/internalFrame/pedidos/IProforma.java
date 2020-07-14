package com.promesa.internalFrame.pedidos;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;

import com.promesa.bean.BeanDato;
import com.promesa.dialogo.pedidos.DConfirmacionMultiplesPedidos;
import com.promesa.dialogo.pedidos.DConfirmacionPedidoUnico;
import com.promesa.internalFrame.planificacion.IVentanaVisita;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.bean.BeanBloqueoEntrega;
import com.promesa.pedidos.bean.BeanMensaje;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.pedidos.sql.impl.SqlBloqueoEntregaImpl;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class IProforma extends IPedidos {
	
	public IProforma(BeanAgenda ba,String claseRiesgo, String limiteCredito, String disponibilidad, String titulo, String tituloReporte) {
		super(ba.getBEGDA(),null, ba.getStrCodigoCliente(), ba.getStrNombreCliente(), claseRiesgo, limiteCredito, disponibilidad, ba.getStrCondicionPago(), titulo, tituloReporte, "ZP05", Constante.VENTANA_CREAR_PROFORMA);
		BeanCliente cl = new BeanCliente();
		SqlCliente getCliente = new SqlClienteImpl();
		cl = getCliente.obtenerCliente(codigoCliente);
		setTitle("Proforma " + codigoCliente + "-" + nombreCliente + "-" + cl.getStrCanal());
		tituloImpresion = "Proforma";
		txtBloqEntrega.setEditable(true);
		btnImprimirComprobante.setVisible(false);
//		bloqueoEntrega = "01";
		bloqueoEntrega = "";
		txtBloqEntrega.setText(bloqueoEntrega);
		llenarTablaBloqueosEntrega();
	}

	@Override
	protected void finalizarTransaccion() {
		String message = verificarVisitas("",codigoCliente);
		if(message.equals("")){
			IVentanaVisita tp = new IVentanaVisita(codigoCliente);
			tp.setVisible(true);
			while(tp.getisInsert()){
				System.out.println(tp.getisInsert());
			}
		}
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
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
					} else {
						mensajes = guardarPedidoSQLite();
						src = "1";
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					fallo = true;
					mensajeError = e.getMessage();
				} finally {
					if (fallo) {
						Mensaje.mostrarError(mensajeError);
					} else {
						if (mensajes != null && !mensajes.isEmpty()) {
							if (mensajes.size() == 1) {
								final BeanMensaje msg = mensajes.get(0);
								if (msg.getTipo().compareTo("N") == 0) {
									dispose();
									DConfirmacionPedidoUnico dlg = new DConfirmacionPedidoUnico(Promesa.getInstance(), true, msg.getDescripcion(), msg.getIdentificador(), sync,
											"Proforma", codigoCliente, nombreCliente, direccionDestinatario, descripcionCondicionPago, "ZP05");
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
								DConfirmacionMultiplesPedidos dlg = new DConfirmacionMultiplesPedidos((JFrame) Promesa.getInstance(), false,
										mensajes, header, src, tituloImpresion, descripcionCondicionPago, direccionDestinatario);
								dlg.setVisible(true);
							}
						} else {
							Mensaje.mostrarWarning("No se ha confirmado ningún material.");
						}
					}
					bloqueador.dispose();
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
			tablaBloqueos.getColumn("CÓDIGO").setMaxWidth(80);
			tablaBloqueos.getColumn("CÓDIGO").setMinWidth(80);
			tablaBloqueos.getColumn("CÓDIGO").setPreferredWidth(80);
			tablaBloqueos.getColumn("DESCRIPCIÓN").setMaxWidth(320);
			tablaBloqueos.getColumn("DESCRIPCIÓN").setMinWidth(320);
			tablaBloqueos.getColumn("DESCRIPCIÓN").setPreferredWidth(320);
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
				if (strings[0].compareTo("01") == 0 || strings[0].compareTo("09") == 0 || strings[0].compareTo("98") == 0) {
					modeloBloqueo.addRow(strings);
				}
			}
		} catch (Exception exec) {
			Util.mostrarExcepcion(exec);
		}
	}
	

}