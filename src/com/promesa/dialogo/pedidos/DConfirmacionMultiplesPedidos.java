package com.promesa.dialogo.pedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.promesa.bean.BeanDato;
import com.promesa.impresiones.ReporteTicket;
import com.promesa.impresiones.pedidos.DetalleTicketOrden;
import com.promesa.impresiones.pedidos.TicketOrden;
import com.promesa.internalFrame.pedidos.IEditarPedido;
import com.promesa.internalFrame.pedidos.IRestaurarPedido;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanMensaje;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.sql.SqlPedido;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.pedidos.sql.impl.SqlPedidoImpl;
import com.promesa.sap.SPedidos;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class DConfirmacionMultiplesPedidos extends javax.swing.JDialog {

	private javax.swing.JLabel lblMensaje;
	private javax.swing.JPanel pnlContenedor;
	private List<BeanMensaje> mensajes;
	private BeanPedidoHeader header;
	private String source;
	private String titulo;
	private String descripcionCondicionPago;
	private String direccionDestinatario;
	private String idPedido;

	/** Creates new form DConfirmacionMultiplesPedidos */
	public DConfirmacionMultiplesPedidos(java.awt.Frame parent, boolean modal, List<BeanMensaje> mensajes, BeanPedidoHeader header, String source,
			String titulo, String descripcionCondicionPago, String direccionDestinatario) {
		super(parent, modal);
		initComponents();
		this.mensajes = mensajes;
		this.header = header;
		this.source = source;
		this.titulo = titulo;
		this.descripcionCondicionPago = descripcionCondicionPago;
		this.direccionDestinatario = direccionDestinatario;
		generarOpcionesOrdenes();
		setResizable(false);
		pack();
		this.setLocationRelativeTo(null);
	}

	private void initComponents() {

		lblMensaje = new javax.swing.JLabel();
		pnlContenedor = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(".:: Confirmación ::.");

		lblMensaje.setText("Se han creado las siguientes órdenes:");
		lblMensaje.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
		getContentPane().add(lblMensaje, java.awt.BorderLayout.PAGE_START);

		pnlContenedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlContenedor.setLayout(new java.awt.GridLayout(0, 3, 5, 5));
		getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

		pack();
	}

	private void imprimir(String titutlo, String codigoCliente, String nombreCliente, String direccionDestinatario,
			String descripcionCondicionPago, String codigoPedido, List<Item> items) {
		BeanDato usuario = Promesa.datose.get(0);
		TicketOrden ticket = new TicketOrden(titutlo);
		ticket.setDireccion(Util.obtenerDireccionPromesa());
		ticket.setTelefono(Util.obtenerTelefonoPromesa());
		int cod = 0;
		try {
			cod = Integer.parseInt(codigoCliente);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			cod = 0;
		}
		ticket.setCliente(cod + "-" + nombreCliente);
		ticket.setDireccionCliente(direccionDestinatario);
		try {
			cod = Integer.parseInt(usuario.getStrCodigo());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			cod = 0;
		}
		ticket.setVendedor(cod + "-" + usuario.getStrUsuario());
		Date fecha = new Date();
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MMM-yyyy");
		ticket.setFecha(formatoDeFecha.format(fecha));
		ticket.setCondicionesPago(descripcionCondicionPago);
		ticket.setNumeroPedido(codigoPedido);
		List<DetalleTicketOrden> detalles = new ArrayList<DetalleTicketOrden>();
		for (Item item : items) {
			String material = item.getCodigo();
			int cantidad = 0;
			double precio = 0d;
			double valor = 0d;
			try {
				cantidad = (int) Double.parseDouble(item.getCantidad());
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				cantidad = 0;
			}
			try {
				precio = item.getPrecioNeto();
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				precio = 0d;
			}
			try {
				valor = item.getValorNeto();
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			if (!material.isEmpty()) {
				DetalleTicketOrden detalle = new DetalleTicketOrden(material,
						item.getDenominacion(), item.getTipo(), cantidad, precio, valor);
				detalles.add(detalle);
			}
		}
		// ------------
		ticket.setDetalles(detalles);
		boolean estaSincronizado = false;
		if (source.compareTo("0") == 0) {
			estaSincronizado = true;
		}
		ReporteTicket reporte = new ReporteTicket(ticket, estaSincronizado);
		reporte.generarReportePedido();
	}

	private void generarOpcionesOrdenes() {
		if (mensajes != null) {
			for (BeanMensaje codigo : mensajes) {
				JLabel lblCodigo = null;
				long estatus = codigo.getIdentificador();
				String mensaje = "";
				if (estatus < 0) {
					// ERROR AL CREAR LA ORDEN
					lblMensaje.setText("Algunos Pedidos no se grabaron correctamente.");
					SqlPedido sqlPedido = new SqlPedidoImpl();
					try {
						codigo.getPedido().getHeader().setSource(1);
						idPedido = sqlPedido.insertarPedidos(codigo.getPedido());
						codigo.getPedido().setCodigoPedido(idPedido);
						mensaje = "Pedido se Guardo localmente con codigo: " + idPedido;
					} catch (Exception e1) {
						mensaje = "Error al Al guardar Pedido a Local, Corregir Pedido o se perdera Información del Pedido";
					}
					lblCodigo = new JLabel("<html><font color='red'> "+mensaje+"</font></html>");
					lblCodigo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/icon_check_no.png")));
				} else {
					lblCodigo = new JLabel("Orden Nº " + codigo.getIdentificador());
					lblCodigo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/icon_check_yes.png")));
				}

				JButton btnDetalle = new JButton("Detalle");
				btnDetalle.setVisible(false);// volver a quitar cuando regrese Nelson
				JButton btnImprimir = new JButton("Imprimir");
				JButton btnCorregir = new JButton("Solucionar");
				final String finalCodigo = "" + codigo.getIdentificador();
				final String src = source;
				final BeanMensaje temporal = codigo;
				btnDetalle.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						header.setStrDocumentoVenta(finalCodigo);
						final DLocker bloqueador = new DLocker();
						bloqueador.setAlwaysOnTop(true);
						Thread hilo = new Thread() {
							public void run() {
								try {
									SqlAgendaImpl agenda = new SqlAgendaImpl();
									String[] valoresCrediticios = agenda.valoresAgenda(header.getStrCodCliente());
									String limiteCredito = "";
									String claseRiesgo = "";
									String disponible = "";
									if (valoresCrediticios != null) {
										limiteCredito = valoresCrediticios[0];
										claseRiesgo = valoresCrediticios[1];
										disponible = valoresCrediticios[2];
									}
									BeanPedido pedido = null;
									BeanDato usuario = Promesa.datose.get(0);
									// DESDE SAP
									if (usuario.getStrModo().equals("1")) {
										SPedidos objSap = new SPedidos();
										pedido = objSap.obtenerDetallePedido(header.getStrDocumentoVenta());
									} else {
										SqlPedidoImpl sql = new SqlPedidoImpl();
										pedido = sql.obtenerPedido(header.getStrDocumentoVenta());
									}
									pedido.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
									pedido.getHeader().setStrValorNeto(header.getStrValorNeto());
									IEditarPedido tp = new IEditarPedido(pedido, header.getStrCodCliente(), header.getStrCliente(), claseRiesgo, limiteCredito,
											disponible, pedido.getHeader().getStrCondicionPago(), "Editar Pedido " + header.getStrCodCliente() + "-" + header.getStrCliente(), "Editar Pedido", "ZP01", src, null);

									Promesa.destokp.add(tp);
									tp.setVisible(true);
									tp.setMaximum(true);
									tp.moveToFront();
									tp.setSelected(true);
								} catch (Exception ex) {
									Mensaje.mostrarError(ex.getMessage());
								} finally {
									bloqueador.dispose();
									bloqueador.setAlwaysOnTop(false);
								}
							}
						};
						hilo.start();
						bloqueador.setVisible(true);
					}
				});
				btnImprimir.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						final DLocker bloqueador = new DLocker();
						bloqueador.setAlwaysOnTop(true);
						Thread hilo = new Thread() {
							public void run() {
								try {
									// DESDE SAP
									header.setStrDocumentoVenta(finalCodigo);
									BeanDato usuario = Promesa.datose.get(0);
									BeanPedido pedido = null;
									if (usuario.getStrModo().equals("1")) {
										SPedidos objSap = new SPedidos();
										pedido = objSap.obtenerDetallePedido(header.getStrDocumentoVenta());
									} else {
										SqlPedidoImpl sql = new SqlPedidoImpl();
										pedido = sql.obtenerPedido(header.getStrDocumentoVenta());
									}
									if (pedido != null) {
										List<Item> items = new ArrayList<Item>();
										List<BeanPedidoDetalle> detalles = pedido.getDetalles();
										for (BeanPedidoDetalle bp : detalles) {
											Item it = new Item();
											it.setCodigo(bp.getMaterial());
											it.setDenominacion(bp.getDenominacion());
											it.setTipo(bp.getTipo());
											it.setCantidad(bp.getCantidadConfirmada());
											double pn = 0d;
											try {
												pn = Double.parseDouble(bp.getPrecioNeto());
											} catch (Exception e2) {
												Util.mostrarExcepcion(e2);
												pn = 0d;
											}
											double vn = 0d;
											try {
												vn = Double.parseDouble(bp.getValorNeto());
											} catch (Exception e2) {
												vn = 0d;
											}
											it.setPrecioNeto(pn);
											it.setValorNeto(vn);
											items.add(it);
										}

										imprimir(titulo, header.getStrCodCliente(), header.getStrCliente(), direccionDestinatario, descripcionCondicionPago, finalCodigo, items);
									} else {
										Mensaje.mostrarWarning("El pedido no ha sido encontrado.");
									}
								} catch (Exception e2) {
									Mensaje.mostrarError(e2.getMessage());
								} finally {
									bloqueador.dispose();
									bloqueador.setAlwaysOnTop(false);
								}
							}
						};
						hilo.start();
						bloqueador.setVisible(true);
					}
				});
				btnCorregir.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						final DLocker bloqueador = new DLocker();
						bloqueador.setAlwaysOnTop(true);
						Thread hilo = new Thread() {
							public void run() {
								try {
									// DESDE SAP
//									BeanPedido pedido = temporal.getPedido();
									String id = temporal.getPedido().getCodigoPedido();
									SqlPedidoImpl sqlPedido = new SqlPedidoImpl();
									BeanPedido pedido = sqlPedido.obtenerPedido(id);
//									BeanDato usuario = Promesa.datose.get(0);
//									if (usuario.getStrModo().equals("1")) {
										if (pedido != null) {
											SqlAgendaImpl agenda = new SqlAgendaImpl();
											String[] valoresCrediticios = agenda.valoresAgenda(header.getStrCodCliente());
											String limiteCredito = "";
											String claseRiesgo = "";
											String disponible = "";
											if (valoresCrediticios != null) {
												limiteCredito = valoresCrediticios[0];
												claseRiesgo = valoresCrediticios[1];
												disponible = valoresCrediticios[2];
											}
											BeanPedidoHeader h = pedido.getHeader();
											IRestaurarPedido tp = new IRestaurarPedido( pedido, h.getStrCodCliente(), h.getStrCliente(),
													claseRiesgo,limiteCredito,disponible, h.getPMNTTRMS(),"Pedido " + h.getStrCodCliente()+ "-"
													+ h.getStrCliente(), "", "", "" + h.getSource(), temporal.getIts(), temporal.getDescripcion());
											Promesa.destokp.add(tp);
											tp.setVisible(true);
											tp.setMaximum(true);
											tp.moveToFront();
											tp.setSelected(true);
										}
//									}
								} catch (Exception e2) {
									Util.mostrarExcepcion(e2);
								} finally {
									bloqueador.dispose();
									bloqueador.setAlwaysOnTop(false);
								}
							}
						};
						hilo.start();
						bloqueador.setVisible(true);
					}
				});
				pnlContenedor.add(lblCodigo);

				if (estatus < 0) {
					pnlContenedor.add(btnCorregir);
					pnlContenedor.add(new JLabel());
				} else {
					pnlContenedor.add(btnDetalle);
					pnlContenedor.add(btnImprimir);
				}
			}
		}
	}

}
