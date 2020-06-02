package com.promesa.internalFrame.cobranzas;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.CabeceraRegistroPagoCliente;
import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.cobranzas.bean.RegistroPagoCliente;
import com.promesa.cobranzas.sql.impl.SqlBancoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlCabeceraRegistroPagoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlPagoParcialImpl;
import com.promesa.cobranzas.sql.impl.SqlPagoRecibidoImpl;
import com.promesa.cobranzas.sql.impl.SqlPartidaIndividualAbiertaImpl;
import com.promesa.cobranzas.sql.impl.SqlRegistroPagoClienteImpl;
//import com.promesa.impresiones.ReporteTicket;
import com.promesa.impresiones.cobranzas.DetalleTicketPago;
import com.promesa.impresiones.cobranzas.TicketPago;
import com.promesa.internalFrame.cobranzas.custom.ModeloTablaListaRegistroPagos;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorListaPagos;
import com.promesa.main.Promesa;
import com.promesa.planificacion.bean.BeanCliente;
//import com.promesa.planificacion.sql.impl.SqlClienteEmpleadoImpl;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SCobranzas;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class IListaRegistroPagos extends javax.swing.JInternalFrame {

	private ModeloTablaListaRegistroPagos modeloRegistroPagos;
	List<TicketPago> listaTicketPago = new ArrayList<TicketPago>();

	/** Creates new form IListaRegistroPagos */
	public IListaRegistroPagos() {
		initComponents();
		dtcDesde.setDateFormatString("dd-MM-yyyy");
		dtcHasta.setDateFormatString("dd-MM-yyyy");
		dtcDesde.setDate(new Date());
		dtcHasta.setDate(new Date());
		btnBuscar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/buscar.gif")));
		btnSincronizar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/synch.png")));
		btnEliminar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/borrar.png")));
		modeloRegistroPagos = new ModeloTablaListaRegistroPagos();
		tblRegistrosPagos.setModel(modeloRegistroPagos);
		((DefaultTableCellRenderer) tblRegistrosPagos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblRegistrosPagos.setRowHeight(Constante.ALTO_COLUMNAS);
		tblRegistrosPagos.getTableHeader().setReorderingAllowed(false);
		RenderizadorListaPagos render = new RenderizadorListaPagos();
		tblRegistrosPagos.setDefaultRenderer(Object.class, render);
		tblRegistrosPagos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblRegistrosPagos.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblOrdenesMouseClicked(evt);
			}
		});
		establecerAnchoColumnas();
	}

	private void tblOrdenesMouseClicked(java.awt.event.MouseEvent evt) {
		final int fila = tblRegistrosPagos.rowAtPoint(evt.getPoint());
		final String codigoCliente = "" + tblRegistrosPagos.getValueAt(fila, 0);
		if (evt.getClickCount() == 2) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						long idRegistroPagoCliente = Long.parseLong(codigoCliente);
						RegistroPagoCliente registroPagoCliente = SqlRegistroPagoClienteImpl.obtenerRegistroPagoCliente(idRegistroPagoCliente);
						if (registroPagoCliente != null) {
							IRegistroPagoCliente iR = new IRegistroPagoCliente(registroPagoCliente);
							Promesa.destokp.add(iR);
							iR.setVisible(true);
							iR.setMaximum(true);
						}
					} catch (Exception e) {
						e.printStackTrace();
						if (!(e instanceof NullPointerException)) {
							Mensaje.mostrarAviso(e.getMessage());
						}
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
	}

	private void initComponents() {

		pnlBuscar = new javax.swing.JPanel();
		dtcDesde = new com.toedter.calendar.JDateChooser();
		lblDesde = new javax.swing.JLabel();
		lblHasta = new javax.swing.JLabel();
		dtcHasta = new com.toedter.calendar.JDateChooser();
		btnBuscar = new javax.swing.JButton();
		btnSincronizar = new javax.swing.JButton();
		btnEliminar = new javax.swing.JButton();
		scrRegistrosPagos = new javax.swing.JScrollPane();
		tblRegistrosPagos = new javax.swing.JTable();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Lista de Registro de Pagos");

		pnlBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

		lblDesde.setText("Desde:");

		lblHasta.setText("Hasta:");

		btnBuscar.setText("Buscar");
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});

		btnSincronizar.setText("Sincronizar");
		btnSincronizar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSincronizarActionPerformed(evt);
			}
		});

		btnEliminar.setText("Eliminar");
		btnEliminar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEliminarActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pnlBuscarLayout = new javax.swing.GroupLayout(pnlBuscar);
		pnlBuscar.setLayout(pnlBuscarLayout);
		pnlBuscarLayout.setHorizontalGroup(pnlBuscarLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlBuscarLayout.createSequentialGroup().addContainerGap()
			.addGroup(pnlBuscarLayout.createParallelGroup(
					javax.swing.GroupLayout.Alignment.TRAILING)
			.addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addGroup(pnlBuscarLayout.createSequentialGroup()
			.addComponent(lblDesde).addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(dtcDesde, javax.swing.GroupLayout.PREFERRED_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.PREFERRED_SIZE)))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(pnlBuscarLayout.createParallelGroup(
									javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlBuscarLayout.createSequentialGroup().addComponent(lblHasta)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(dtcHasta, javax.swing.GroupLayout.PREFERRED_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.PREFERRED_SIZE))
			.addGroup(pnlBuscarLayout.createSequentialGroup()
			.addComponent(btnSincronizar).addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(btnEliminar))).addContainerGap(303, Short.MAX_VALUE)));

		pnlBuscarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { btnBuscar, btnEliminar, btnSincronizar });

		pnlBuscarLayout.setVerticalGroup(pnlBuscarLayout.createParallelGroup(
									javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlBuscarLayout.createSequentialGroup()
			.addGroup(pnlBuscarLayout.createParallelGroup(
					javax.swing.GroupLayout.Alignment.LEADING)
			.addComponent(dtcHasta, javax.swing.GroupLayout.PREFERRED_SIZE,
								    javax.swing.GroupLayout.DEFAULT_SIZE,
								    javax.swing.GroupLayout.PREFERRED_SIZE)
			.addGroup(pnlBuscarLayout.createParallelGroup(
					javax.swing.GroupLayout.Alignment.LEADING, false)
			.addComponent(lblHasta, javax.swing.GroupLayout.Alignment.TRAILING,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(lblDesde, javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
			.addComponent(dtcDesde, javax.swing.GroupLayout.PREFERRED_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.PREFERRED_SIZE)))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(pnlBuscarLayout.createParallelGroup(
									javax.swing.GroupLayout.Alignment.BASELINE)
			.addComponent(btnBuscar).addComponent(btnSincronizar)
			.addComponent(btnEliminar)).addContainerGap(
									javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		tblRegistrosPagos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] 
		              { { null, null, null, null },{ null, null, null, null },
						{ null, null, null, null },{ null, null, null, null } }, 
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		scrRegistrosPagos.setViewportView(tblRegistrosPagos);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
									javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
			.addContainerGap().addGroup(layout.createParallelGroup(
											javax.swing.GroupLayout.Alignment.TRAILING)
			.addComponent(scrRegistrosPagos, javax.swing.GroupLayout.Alignment.LEADING,
										     javax.swing.GroupLayout.DEFAULT_SIZE,
										     674, Short.MAX_VALUE)
			.addComponent(pnlBuscar, javax.swing.GroupLayout.Alignment.LEADING,
								 	javax.swing.GroupLayout.DEFAULT_SIZE,
								 	javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
			.addContainerGap().addComponent(pnlBuscar, javax.swing.GroupLayout.PREFERRED_SIZE,
					javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(scrRegistrosPagos, javax.swing.GroupLayout.DEFAULT_SIZE,
					347, Short.MAX_VALUE).addContainerGap()));
		pack();
	}// </editor-fold>

	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					buscaCabecerasRegistroPagoCliente();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
		final int filas[] = tblRegistrosPagos.getSelectedRows();
		if (filas != null && filas.length > 0) {
			int tipo = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el(los) registro(s) de pagos(s) seleccionado(s)?", "Confirmación", JOptionPane.YES_NO_OPTION);
			if (tipo == JOptionPane.OK_OPTION) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						try {
							PagoParcial pagoParcial = new PagoParcial();
							SqlPartidaIndividualAbiertaImpl sql = new SqlPartidaIndividualAbiertaImpl();
							SqlCabeceraRegistroPagoClienteImpl sqlcRpCi = new SqlCabeceraRegistroPagoClienteImpl();
							SqlPagoRecibidoImpl sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
							SqlPagoParcialImpl sqlPagoParcialImpl = new SqlPagoParcialImpl();
							for (int m : filas) {
								CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = modeloRegistroPagos.obtenerCabeceraRegistroPagoCliente(m);
								pagoParcial = sqlPagoParcialImpl.obtenerListaPagoParcialActivo(cabeceraRegistroPagoCliente.getIdCabecera());
								Double importePago = sql.ObtenerImportePagoPartidasIndividualAbierta(pagoParcial);
								importePago = importePago + pagoParcial.getImportePago();
								Double importePagoParcial = pagoParcial.getImportePagoParcial()- pagoParcial.getImportePago();
								sql.ActualizarImportePagoPartidasIndividuales(pagoParcial, importePago, importePagoParcial);
//								sqlPagoRecibidoImpl.eliminarListaPagoRecibido(cabeceraRegistroPagoCliente.getIdCabecera());
//								sqlPagoParcialImpl.eliminarListaPagoParcial(cabeceraRegistroPagoCliente.getIdCabecera());
//								sqlcRpCi.eliminarCabeceraRegistroPagoCliente(cabeceraRegistroPagoCliente);
								sqlcRpCi.cambiarEstadoCabeceraRegistroPagoCliente(cabeceraRegistroPagoCliente);
							}
							buscaCabecerasRegistroPagoCliente();
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						} finally {
							bloqueador.dispose();
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
		} else {
			JOptionPane.showMessageDialog(Promesa.getInstance(), "Debe seleccionar por lo menos un registro de pago para poder eliminar.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void btnSincronizarActionPerformed(java.awt.event.ActionEvent evt) {
		int[] filas = tblRegistrosPagos.getSelectedRows();
		for (int i : filas) {
			final String id = "" + tblRegistrosPagos.getValueAt(i, 0);
			try {
				final DLocker bloqueador = new DLocker();
				BeanDato usuario = Promesa.datose.get(0);
				if (usuario.getStrModo().equals("1")) { //ONLINE} else {//OFFLINE}
					Thread hilo = new Thread() {
						public void run() {
							try {
								long idRegistroPagoCliente = Long.parseLong(id);
								RegistroPagoCliente registroPagoCliente = SqlRegistroPagoClienteImpl.obtenerRegistroPagoCliente(idRegistroPagoCliente);
								if(registroPagoCliente.getCabeceraRegistroPagoCliente().getImporteSinAsignar() >= 1d){
									int tipo = JOptionPane.showConfirmDialog
											(Promesa.getInstance(), "¿Desea sincronizar el pago, " + "se realizará un anticipo automático por " 
											+ registroPagoCliente.getCabeceraRegistroPagoCliente().getImporteSinAsignar() + " ... confirmar?", 
											"Confirmación", JOptionPane.YES_NO_OPTION);
									if (tipo == JOptionPane.OK_OPTION) {
										if(sincronizarRegistroPagoCliente(registroPagoCliente)){
											registroPagoCliente.getCabeceraRegistroPagoCliente().setCabeceraSecuencia("1");
											SqlRegistroPagoClienteImpl.actualizarCabeceraSincronizado(registroPagoCliente);
											SqlRegistroPagoClienteImpl.eliminarRegistroPagoCliente(registroPagoCliente);
											buscaCabecerasRegistroPagoCliente();
										}
									}
								}else{
									if(sincronizarRegistroPagoCliente(registroPagoCliente)){
										registroPagoCliente.getCabeceraRegistroPagoCliente().setCabeceraSecuencia("1");
										SqlRegistroPagoClienteImpl.actualizarCabeceraSincronizado(registroPagoCliente);
										SqlRegistroPagoClienteImpl.eliminarRegistroPagoCliente(registroPagoCliente);
										buscaCabecerasRegistroPagoCliente();
									}
								}
							} catch(Exception ex) {
								bloqueador.dispose();
								ex.printStackTrace();
							}finally {
								bloqueador.dispose();
							}
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(Promesa.getInstance(), "Ud. sólo puede sincronizar en modo ONLINE.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (!(e instanceof NullPointerException)) {
					Mensaje.mostrarAviso(e.getMessage());
				}
			}
		}
	}

	private boolean sincronizarRegistroPagoCliente(RegistroPagoCliente registroPagoCliente) {
		boolean bandera = false;
		SCobranzas sCobranzas = new SCobranzas();
		List<PagoRecibido> listaPagosRecibidos = registroPagoCliente.getListaPagoRecibido();
		List<PagoParcial> listaPagosParciales = registroPagoCliente.getListaPagoParcial();
		List<PagoParcial> listaPagosParcialesActivos = new ArrayList<PagoParcial>();
		for (PagoParcial pagoParcial : listaPagosParciales) {
			if (!pagoParcial.isHijo() && pagoParcial.getImportePago() > 0d && pagoParcial.isActivo()) {
				listaPagosParcialesActivos.add(pagoParcial);
			}
		}
		Object arregloRegistroPagoCliente[] = sCobranzas.grabarRegistroPagoCliente(listaPagosRecibidos, listaPagosParcialesActivos);
		String mensajeHTML = "<html><table>";
		if (arregloRegistroPagoCliente != null) {
			@SuppressWarnings("unchecked")
			List<String> lstMensaje = (List<String>) arregloRegistroPagoCliente[1];
			URL check = this.getClass().getResource("/imagenes/check.png");
			URL error = this.getClass().getResource("/imagenes/error.png");
			for (String mensaje : lstMensaje) {
				String[] valores = String.valueOf(mensaje).split("[¬]");
				if (valores[1].trim().equals("S")) {
					mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valores[3].trim() + "</td></tr>";
				} else {
					mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valores[3].trim() + "</td></tr>";
					
				}
			}
			mensajeHTML += "</table></html>";
			JOptionPane.showMessageDialog(Promesa.getInstance(), mensajeHTML, "Resultados", JOptionPane.INFORMATION_MESSAGE);
			@SuppressWarnings("unchecked")
			List<String> lstDetallesDePago = (List<String>) arregloRegistroPagoCliente[0];
			if ( lstDetallesDePago.size() > 0) {
				// Aquí debo de llenar la lista listaTicketPago
				HashMap<String, TicketPago> hmTicketPago = new HashMap<String, TicketPago>();
				for (String detalleDePago : lstDetallesDePago) {
					String[] valores = String.valueOf(detalleDePago).split("[¬]");
					String numeroTicket = valores[7].trim();
					TicketPago ticketPago = hmTicketPago.get(numeroTicket);
					DetalleTicketPago detalleTicketPago = new DetalleTicketPago();
					detalleTicketPago.setNroFactura(valores[5].trim());
					detalleTicketPago.setFechaVencimiento(Util.convierteFecha(valores[12].trim()));
					detalleTicketPago.setValor(Double.parseDouble(valores[9].trim()));
					detalleTicketPago.setAbono(Double.parseDouble(valores[10].trim()));
					if (ticketPago == null) {
						TicketPago ticketPagoTemporal = new TicketPago();
						ticketPagoTemporal.setNumeroTicket(valores[7].trim());
						String codigoCliente = tblRegistrosPagos.getValueAt(tblRegistrosPagos.getSelectedRow(), 2).toString();
						SqlClienteImpl SqlClienteImpl = new com.promesa.planificacion.sql.impl.SqlClienteImpl();
						BeanCliente beanCliente = SqlClienteImpl.obtenerCliente(codigoCliente);
						ticketPagoTemporal.setCliente(codigoCliente + " - " + beanCliente.getStrNombreCliente());
						ticketPagoTemporal.setVendedor(Promesa.datose.get(0).getStrUsuario());
						ticketPagoTemporal.setReferencia(!valores[13].trim().equals("|") ? valores[13].trim() : "");
						if(!("|").equals(valores[14].trim())){
							ticketPagoTemporal.setCuentaBanco("");
							SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
							List<BancoCliente> listaBancoCliente = sqlBancoClienteImpl.obtenerListaBancoCliente(codigoCliente);
							for(BancoCliente bancoCliente : listaBancoCliente){
								if(bancoCliente.getNroCtaBcoCliente().equals(valores[14].trim())){
									ticketPagoTemporal.setCuentaBanco
										(bancoCliente.getDescripcionBancoCliente());
									break;
								}
							}
						}else{
							ticketPagoTemporal.setCuentaBanco("");
						}
						ticketPagoTemporal.setFechaDocumento(!valores[16].trim().equals("Mon Jan 01 00:00:00 COT 1900") ? Util.convierteFechaADDMMMYYYY(valores[16].trim()) : "");
						ticketPagoTemporal.setFechaVencimiento(!valores[15].trim().equals("Mon Jan 01 00:00:00 COT 1900") ? Util.convierteFechaADDMMMYYYY(valores[15].trim()) : "");
						if (("VC").equals(valores[2].trim())) {
							ticketPagoTemporal.setFormaPago("Cheque Postfechado");
						} else if (("VP").equals(valores[2].trim())) {
							ticketPagoTemporal.setFormaPago("Pagaré");
						} else if (("VT").equals(valores[2].trim())) {
							ticketPagoTemporal.setFormaPago("Retención");
						} else if (("VX").equals(valores[2].trim())) {
							ticketPagoTemporal.setFormaPago("Papeleta de Depósito");
						} else if (("VY").equals(valores[2].trim())) {
							ticketPagoTemporal.setFormaPago("Cheque a la vista");
						} else if (("VZ").equals(valores[2].trim())) {
							ticketPagoTemporal.setFormaPago("Efectivo");
						}else if(("VO").equals(valores[2].trim())){
							ticketPagoTemporal.setFormaPago("Recaudo");
						}
						hmTicketPago.put(numeroTicket, ticketPagoTemporal);
						ticketPagoTemporal.agregarDetalleALaLista(detalleTicketPago);
					} else {
						ticketPago.agregarDetalleALaLista(detalleTicketPago);
					}
				}
				for (Entry<String, TicketPago> elemento : hmTicketPago.entrySet()) {
					listaTicketPago.add(elemento.getValue());
				}
				if (listaTicketPago != null) {
//					Marcelo Moyano Anular Reporte en Consulta Cobranza offline
//					ReporteTicket rt = new ReporteTicket(listaTicketPago);
//					rt.generarReporteRegistroPagoOnline();
				}
				bandera = true;
			} //else {
//				JOptionPane.showMessageDialog(Promesa.getInstance(), "No se realizó correctamente la conexión a SAP.", "Error", JOptionPane.ERROR_MESSAGE);
//			}
		} else {
			JOptionPane.showMessageDialog(Promesa.getInstance(), "No se realizó correctamente la conexión a SAP.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return bandera;
	}

	private void buscaCabecerasRegistroPagoCliente() {
		if (dtcDesde.getDate() != null && dtcHasta.getDate() != null) {
			establecerColorDeFondoCalendario(dtcDesde, Color.white);
			establecerColorDeFondoCalendario(dtcHasta, Color.white);
			if (Util.comparaFecha1MenorOIgualQueFecha2(dtcDesde.getDate(), dtcHasta.getDate())) {
				final String fechaDesde = Util.convierteFechaAFormatoYYYYMMdd(dtcDesde.getDate());
				final String fechaHasta = Util.convierteFechaAFormatoYYYYMMdd(dtcHasta.getDate());
				final SqlCabeceraRegistroPagoClienteImpl sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
				@SuppressWarnings("static-access")
				final List<CabeceraRegistroPagoCliente> listaCabeceraRegistroPagoCliente =  sqlCabeceraRegistroPagoClienteImpl.obtenerListaCabeceraRegistroPagoCliente(Promesa.getInstance().datose.get(0).getStrCodigo());
				if (listaCabeceraRegistroPagoCliente.size() >= 0) {
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							modeloRegistroPagos.limpiar();
							for (CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente : listaCabeceraRegistroPagoCliente) {
								if (Util.comparaFecha1MenorOIgualQueFecha2(Util.convierteCadenaYYYYMMDDAFecha(fechaDesde), Util.convierteCadenaDDMMYYYYAFecha(cabeceraRegistroPagoCliente.getFechaCreacion()))
										&& Util.comparaFecha1MenorOIgualQueFecha2(Util.convierteCadenaDDMMYYYYAFecha(cabeceraRegistroPagoCliente.getFechaCreacion()), Util.convierteCadenaYYYYMMDDAFecha(fechaHasta))) {
									cabeceraRegistroPagoCliente.setCantidadPagoRecibido(sqlCabeceraRegistroPagoClienteImpl.obtenerCantidadPagoRecibido(cabeceraRegistroPagoCliente.getIdCabecera()));
									modeloRegistroPagos.agregarCabeceraRegistroPago(cabeceraRegistroPagoCliente);
								}
							}
							establecerAnchoColumnas();
						}
					});
				}
			} else {
				JOptionPane.showMessageDialog(Promesa.getInstance(), "La fecha Desde debe ser menor o igual que la fecha Hasta.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			if (dtcDesde.getDate() == null && dtcHasta.getDate() == null) {
				establecerColorDeFondoCalendario(dtcDesde, new Color(254, 205, 215));
				establecerColorDeFondoCalendario(dtcHasta, new Color(254, 205, 215));
				JOptionPane.showMessageDialog(Promesa.getInstance(), "Por favor, ingrese las fechas Desde y Hasta.", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (dtcDesde.getDate() == null) {
				establecerColorDeFondoCalendario(dtcDesde, new Color(254, 205, 215));
				establecerColorDeFondoCalendario(dtcHasta, Color.white);
				JOptionPane.showMessageDialog(Promesa.getInstance(), "Por favor, ingrese la fecha Desde.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				establecerColorDeFondoCalendario(dtcDesde, Color.white);
				establecerColorDeFondoCalendario(dtcHasta, new Color(254, 205, 215));
				JOptionPane.showMessageDialog(Promesa.getInstance(), "Por favor, ingrese la fecha Hasta.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void establecerColorDeFondoCalendario(JDateChooser dateChooser, Color background) {
		Component[] lc = dateChooser.getComponents();
		for (int i = 0; i < lc.length; i++) {
			Object object = lc[i];
			if (object instanceof JTextField) {
				((JTextField) object).setBackground(background);
			}
		}
	}

	private void establecerAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblRegistrosPagos.getColumnModel();
		TableColumn columnaTabla;
		columnaTabla = modeloColumna.getColumn(0);
		anchoColumna = 0;
		columnaTabla.setMinWidth(anchoColumna);
		columnaTabla.setMaxWidth(anchoColumna);
		columnaTabla.setPreferredWidth(anchoColumna);
		Util.setAnchoColumnas(tblRegistrosPagos);
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnEliminar;
	private javax.swing.JButton btnSincronizar;
	private com.toedter.calendar.JDateChooser dtcDesde;
	private com.toedter.calendar.JDateChooser dtcHasta;
	private javax.swing.JLabel lblDesde;
	private javax.swing.JLabel lblHasta;
	private javax.swing.JPanel pnlBuscar;
	private javax.swing.JScrollPane scrRegistrosPagos;
	private javax.swing.JTable tblRegistrosPagos;
	// End of variables declaration
}