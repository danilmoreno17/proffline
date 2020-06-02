package com.promesa.dialogo.pedidos;

import java.awt.FontMetrics;
import java.beans.PropertyVetoException;

import com.promesa.bean.BeanDato;
import com.promesa.internalFrame.pedidos.IEditarPedido;
import com.promesa.internalFrame.pedidos.IPedidos;
import com.promesa.internalFrame.pedidos.IProforma;
import com.promesa.internalFrame.planificacion.IVentanaVisita;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.pedidos.sql.impl.SqlPedidoImpl;
import com.promesa.sap.SPedidos;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class DConfirmacionPedidoUnico extends javax.swing.JDialog {

	@SuppressWarnings("unused")
	private String titulo;
	private String codigoCliente;
	private String nombreCliente;
	@SuppressWarnings("unused")
	private String direccionDestinatario;
	@SuppressWarnings("unused")
	private String descripcionCondicionPago;
	private String tipoDocumentoVenta;
	private long codigoPedido;
	@SuppressWarnings("unused")
	private boolean sync;

	/** Creates new form DConfirmacionPedidoUnico */
	public DConfirmacionPedidoUnico(java.awt.Frame parent, boolean modal, String mensaje, long codigoPedido, boolean sync, String titulo,
			String codigoCliente, String nombreCliente, String direccionDestinatario, String descripcionCondicionPago, String tipoDocumentoVenta) {
		super(parent, modal);
		initComponents();
		this.sync = sync;
		this.tipoDocumentoVenta = tipoDocumentoVenta;
		this.titulo = titulo;
		this.codigoCliente = codigoCliente;
		this.nombreCliente = nombreCliente;
		this.direccionDestinatario = direccionDestinatario;
		this.descripcionCondicionPago = descripcionCondicionPago;
		this.codigoPedido = codigoPedido;

		FontMetrics fontMetrics = lblMensaje.getFontMetrics(lblMensaje.getFont());
		int width = fontMetrics.stringWidth(mensaje) + 72;
		if (width < 250) {
			width = 250;
		}
		this.setSize(width, 110);
		lblMensaje.setText(mensaje);
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.lblMensaje.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/informationIcon.png")));
	}
	
	private void initComponents() {

		pnlBotones = new javax.swing.JPanel();
		btnVerDetalle = new javax.swing.JButton();
		btnFinalizar = new javax.swing.JButton();
		pnlMensaje = new javax.swing.JPanel();
		lblMensaje = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(".:: Confirmación ::.");

		pnlBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

		btnVerDetalle.setText("Detalle");
		btnVerDetalle.setVisible(false);// volver a quitar cuando regrese Nelson
		btnVerDetalle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnVerDetalleActionPerformed(evt);
			}
		});
		pnlBotones.add(btnVerDetalle);

		btnFinalizar.setText("Finalizar");
		btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFinalizarActionPerformed(evt);
			}
		});
		pnlBotones.add(btnFinalizar);

		getContentPane().add(pnlBotones, java.awt.BorderLayout.PAGE_END);

		pnlMensaje.setLayout(new java.awt.BorderLayout(5, 5));

		lblMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblMensaje.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 1, 1));
		pnlMensaje.add(lblMensaje, java.awt.BorderLayout.CENTER);

		getContentPane().add(pnlMensaje, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		dispose();
		Thread hilo = new Thread() {
			public void run() {
				try {
					IPedidos tp = null;
					BeanDato usuario = Promesa.datose.get(0);
					SqlAgendaImpl agenda = new SqlAgendaImpl();
					String[] valoresCrediticios = agenda.valoresAgenda(codigoCliente);
					String limiteCredito = "";
					String claseRiesgo = "";
					String disponible = "";
					if (valoresCrediticios != null) {
						limiteCredito = valoresCrediticios[0];
						claseRiesgo = valoresCrediticios[1];
						disponible = valoresCrediticios[2];
					}
					if (tipoDocumentoVenta.compareTo("ZP01") == 0) {
						// PEDIDO
						if (usuario.getStrModo().equals("1")) {
							SPedidos objSap = new SPedidos();
							BeanPedido order = objSap.obtenerDetallePedido("" + codigoPedido);
							order.setCodigoPedido("" + codigoPedido);
							order.getHeader().setStrDocumentoVenta("" + codigoPedido);
							tp = new IEditarPedido(order, codigoCliente, nombreCliente, claseRiesgo, limiteCredito,
									disponible, order.getHeader().getStrCondicionPago(), "Edición de pedido " + codigoCliente + "-" + nombreCliente, "Pedido", "ZP01", "0", null);
						} else {
							SqlPedidoImpl sql = new SqlPedidoImpl();
							BeanPedido order = sql.obtenerPedido("" + codigoPedido);
							order.setCodigoPedido("" + codigoPedido);
							order.getHeader().setStrDocumentoVenta("" + codigoPedido);
							order.getHeader().setStrEstadoPicking("0");
							tp = new IEditarPedido(order, codigoCliente, nombreCliente, claseRiesgo, limiteCredito,
									disponible, order.getHeader().getStrCondicionPago(), "Edición de pedido " + codigoCliente + "-" + nombreCliente, "Pedido", "ZP01", "1", null);
						}
					} else if (tipoDocumentoVenta.compareTo("ZP05") == 0) {
						// PROFORMA
						if (usuario.getStrModo().equals("1")) {
							SPedidos objSap = new SPedidos();
							BeanPedido order = objSap.obtenerDetallePedido("" + codigoPedido);
							order.setCodigoPedido("" + codigoPedido);
							order.getHeader().setStrDocumentoVenta("" + codigoPedido);
							tp = new IEditarPedido(order, codigoCliente, nombreCliente, claseRiesgo, limiteCredito,
									disponible, order.getHeader().getStrCondicionPago(), "Edición de proforma " + codigoCliente + "-" + nombreCliente, "Proforma", "ZP05", "0", null);
						} else {
							SqlPedidoImpl sql = new SqlPedidoImpl();
							BeanPedido order = sql.obtenerPedido("" + codigoPedido);
							order.setCodigoPedido("" + codigoPedido);
							order.getHeader().setStrDocumentoVenta("" + codigoPedido);
							order.getHeader().setStrEstadoPicking("0");
							tp = new IEditarPedido(order, codigoCliente, nombreCliente, claseRiesgo, limiteCredito,
									disponible, order.getHeader().getStrCondicionPago(), "Edición de proforma " + codigoCliente + "-" + nombreCliente, "Proforma", "ZP05", "1", null);
						}
					} else if (tipoDocumentoVenta.compareTo("ZO01") == 0) {
						// COTIZACIÓN
						if (usuario.getStrModo().equals("1")) {
							SPedidos objSap = new SPedidos();
							BeanPedido order = objSap.obtenerDetallePedido("" + codigoPedido);
							order.setCodigoPedido("" + codigoPedido);
							order.getHeader().setStrDocumentoVenta("" + codigoPedido);
							order.getHeader().setStrEstadoPicking("0");
							tp = new IEditarPedido(order, codigoCliente, nombreCliente, claseRiesgo, limiteCredito,
									disponible, order.getHeader().getStrCondicionPago(), "Edición de cotización " + codigoCliente + "-" + nombreCliente, "Cotización", "ZO01", "0", null);
						} else {
							SqlPedidoImpl sql = new SqlPedidoImpl();
							BeanPedido order = sql.obtenerPedido("" + codigoPedido);
							order.setCodigoPedido("" + codigoPedido);
							order.getHeader().setStrDocumentoVenta("" + codigoPedido);
							order.getHeader().setStrEstadoPicking("0");
							tp = new IEditarPedido(order, codigoCliente, nombreCliente, claseRiesgo, limiteCredito,
									disponible, order.getHeader() .getStrCondicionPago(), "Edición de cotización " + codigoCliente + "-" + nombreCliente, "Cotización", "ZO01", "1", null);
						}
					}
					if (tp != null) {
						try {
							Promesa.destokp.add(tp);
							tp.setVisible(true);
							tp.setMaximum(true);
							tp.moveToFront();
							tp.setSelected(true);
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}

					}
				} catch (Exception e) {
					Mensaje.mostrarError(e.getMessage());
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {
		//TODO
		dispose();
		/*if(this.titulo.equals("Proforma")){
			try{
				this.setSize(300, 200);
				IVentanaVisita tp = new IVentanaVisita(codigoCliente,nombreCliente);
				tp.setVisible(true);
			}catch(Exception e){
				Util.mostrarExcepcion(e);
			}
		}*/
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnFinalizar;
	private javax.swing.JButton btnVerDetalle;
	private javax.swing.JLabel lblMensaje;
	private javax.swing.JPanel pnlBotones;
	private javax.swing.JPanel pnlMensaje;
	// End of variables declaration

}
