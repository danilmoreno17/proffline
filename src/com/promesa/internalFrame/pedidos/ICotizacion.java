package com.promesa.internalFrame.pedidos;

import java.util.Date;
import java.util.List;

//import javax.swing.JFrame;
//import javax.swing.JOptionPane;

import com.promesa.bean.BeanDato;
//import com.promesa.dialogo.pedidos.DConfirmacionMultiplesPedidos;
import com.promesa.dialogo.pedidos.DConfirmacionPedidoUnico;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanMensaje;
//import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class ICotizacion extends IPedidos {

	public ICotizacion(String codigoCliente, String nombreCliente, String claseRiesgo, String limiteCredito, String disponibilidad,
			String condicionPago, String titulo, String tituloReporte) {
		super(new Date().toString(),null, codigoCliente, nombreCliente, claseRiesgo, limiteCredito, disponibilidad, condicionPago, titulo, tituloReporte, "ZO01", Constante.VENTANA_CREAR_COTIZACION);
		setTitle("Cotización " + codigoCliente + "-" + nombreCliente);
		tituloImpresion = "Cotización";
		txtBloqEntrega.setEditable(true);
		btnImprimirComprobante.setVisible(false);
		llenarTablaBloqueosEntrega();
		txtBloqEntrega.setEditable(false);
		btnDetalleBloqEntrega.setVisible(false);
		llenarTablaBloqueosEntrega();
	}

	@Override
	protected void finalizarTransaccion() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				boolean sync = false;
				boolean fallo = false;
				String mensajeError = "";
				BeanDato usuario = Promesa.datose.get(0);
				List<BeanMensaje> mensajes = null;
				try {
					if (usuario.getStrModo().equals("1")) {
						sync = true;
						mensajes = cotizarPedidoSAP();
					} else  {
						System.out.println("Nada");
					}
				} catch (Exception e) {
					fallo = true;
					mensajeError = e.getMessage();
					Util.mostrarExcepcion(e);
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
									DConfirmacionPedidoUnico dlg = new DConfirmacionPedidoUnico(Promesa.getInstance(), true, msg.getDescripcion(),
											msg.getIdentificador(), sync, "Cotización", codigoCliente, nombreCliente, direccionDestinatario, descripcionCondicionPago, "ZO01");
									dlg.setVisible(true);
								} else {
									Mensaje.mostrarError(msg.getDescripcion());
								}
							}
						}
					}
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	protected void llenarTablaBloqueosEntrega() {
	}
}