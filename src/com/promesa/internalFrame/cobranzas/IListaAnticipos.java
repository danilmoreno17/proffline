package com.promesa.internalFrame.cobranzas;

import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.cobranzas.sql.impl.SqlAnticipoClienteImpl;
import com.promesa.impresiones.cobranzas.TicketAnticipo;
import com.promesa.internalFrame.cobranzas.custom.ModeloTablaListaAnticipos;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorListaAnticipos;
import com.promesa.main.Promesa;
import com.promesa.sincronizacion.impl.SincronizacionCobranzas;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class IListaAnticipos extends javax.swing.JInternalFrame {

	private ModeloTablaListaAnticipos mdlAnticipos;

	public IListaAnticipos() {
		initComponents();
		mdlAnticipos = new ModeloTablaListaAnticipos();
		tblAnticipos.setModel(mdlAnticipos);
		establecerAnchoColumnas();
		((DefaultTableCellRenderer) tblAnticipos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblAnticipos.setRowHeight(Constante.ALTO_COLUMNAS);
		tblAnticipos.getTableHeader().setReorderingAllowed(false);
		dtcDesde.setDateFormatString("dd-MM-yyyy");
		dtcHasta.setDateFormatString("dd-MM-yyyy");
		dtcDesde.setDate(new Date());
		dtcHasta.setDate(new Date());
		btnBuscar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/buscar.gif")));
		btnSincronizar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/synch.png")));
		btnEliminar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/borrar.png")));
		RenderizadorListaAnticipos render = new RenderizadorListaAnticipos();
		tblAnticipos.setDefaultRenderer(Object.class, render);
		tblAnticipos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}

	private void initComponents() {

		pnlBuscar = new javax.swing.JPanel();
		lblFechaDesde = new javax.swing.JLabel();
		dtcDesde = new com.toedter.calendar.JDateChooser();
		lblHasta = new javax.swing.JLabel();
		dtcHasta = new com.toedter.calendar.JDateChooser();
		btnBuscar = new javax.swing.JButton();
		btnSincronizar = new javax.swing.JButton();
		btnEliminar = new javax.swing.JButton();
		scrTablaAnticipos = new javax.swing.JScrollPane();
		tblAnticipos = new javax.swing.JTable();
		//	Marcelo Moyano 19/03/2013 - 09:14
		dtcDesde.setVisible(false);
		dtcHasta.setVisible(false);
		// Marcelo Moyano
		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Lista de anticipos");

		pnlBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

		lblFechaDesde.setText("Fecha:");
		lblFechaDesde.setVisible(false);//	Marcelo Moyano 19/03/2013 - 09:14

		lblHasta.setText("Fecha:");
		lblHasta.setVisible(false);//	Marcelo Moyano 19/03/2013 - 09:14

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
		pnlBuscarLayout.setHorizontalGroup(pnlBuscarLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlBuscarLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlBuscarLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																pnlBuscarLayout
																		.createSequentialGroup()
																		.addComponent(
																				btnBuscar,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				99,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btnSincronizar,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				113,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlBuscarLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblFechaDesde)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				dtcDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblHasta)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				dtcHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btnEliminar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												92,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(133, Short.MAX_VALUE)));

		pnlBuscarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { btnBuscar, btnEliminar, btnSincronizar });

		pnlBuscarLayout.setVerticalGroup(pnlBuscarLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlBuscarLayout
										.createSequentialGroup()
										.addGroup(
												pnlBuscarLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																dtcHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																pnlBuscarLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																		.addComponent(
																				lblHasta,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				lblFechaDesde,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				dtcDesde,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlBuscarLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnSincronizar)
														.addComponent(btnBuscar)
														.addComponent(
																btnEliminar))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		tblAnticipos.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		scrTablaAnticipos.setViewportView(tblAnticipos);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														scrTablaAnticipos,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														526, Short.MAX_VALUE)
												.addComponent(
														pnlBuscar,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(pnlBuscar,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(scrTablaAnticipos,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										331, Short.MAX_VALUE).addContainerGap()));

		pack();
	}

	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
//					buscaAnticipos();
					buscaAnticiposSinFiltrosFechas();//	Marcelo Moyano 19/03/2013 - 09:49
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
		final int filas[] = tblAnticipos.getSelectedRows();
		if (filas != null && filas.length > 0) {
			int tipo = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el anticipo seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);
			if (tipo == JOptionPane.OK_OPTION) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						try {
							SqlAnticipoClienteImpl sqlAnticipoClienteImpl = new SqlAnticipoClienteImpl();
							for (int m : filas) {
								AnticipoCliente anticipoCliente = mdlAnticipos.obtenerAnticipoCliente(m);
								sqlAnticipoClienteImpl.cambiarEstadoEliminadoAniticpoCliente(anticipoCliente);
//								sqlAnticipoClienteImpl.eliminarAnticipoCliente(anticipoCliente);
							}
//							buscaAnticipos();
							buscaAnticiposSinFiltrosFechas();//	Marcelo Moyano 19/03/2013 - 09:49
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
		}else{
			JOptionPane.showMessageDialog(Promesa.getInstance(),"Debe seleccionar un anticipo para poder eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void btnSincronizarActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) { //ONLINE} else {//OFFLINE}
			Thread hilo = new Thread() {
				public void run() {
					try {
						SincronizacionCobranzas sco = new SincronizacionCobranzas();
						int filas[] = tblAnticipos.getSelectedRows();
						if (filas == null || filas.length == 0) {
							bloqueador.dispose();
							return;
						}
						for (int m : filas) {
							AnticipoCliente anticipoCliente = mdlAnticipos.obtenerAnticipoCliente(m);
							String[] arregloRegistroAnticipo = sco.sincronizaAnticipoCliente(anticipoCliente);
							if(arregloRegistroAnticipo != null){
								TicketAnticipo ticketAnticipo = new TicketAnticipo();
								ticketAnticipo.setCliente(anticipoCliente.getCodigoCliente() + " " + anticipoCliente.getNombreCompletoCliente());
								ticketAnticipo.setDireccion(Util.obtenerDireccionPromesa());
								ticketAnticipo.setTelefono(Util.obtenerTelefonoPromesa());
								ticketAnticipo.setVendedor(Promesa.datose.get(0).getStrUsuario());
								ticketAnticipo.setFecha(anticipoCliente.getFechaCreacion());
								ticketAnticipo.setReferencia(anticipoCliente.getReferencia());
								if(("VT").equals(anticipoCliente.getIdFormaPago())){ // Retención
									ticketAnticipo.setNumeroCuentaBanco("");
								}else if(("VX").equals(anticipoCliente.getIdFormaPago())){ // Papeleta de Depósito
									ticketAnticipo.setNumeroCuentaBanco(anticipoCliente.getDescripcionBancoPromesa());
								}else if(("VY").equals(anticipoCliente.getIdFormaPago())){ // Cheque a la Vista
									ticketAnticipo.setNumeroCuentaBanco(anticipoCliente.getDescripcionBancoCliente());
								}else if(("VZ").equals(anticipoCliente.getIdFormaPago())){ // Efectivo
									ticketAnticipo.setNumeroCuentaBanco("");
								}
								ticketAnticipo.setFormaPago(anticipoCliente.getDescripcionFormaPago());
								ticketAnticipo.setObservacion(anticipoCliente.getObservaciones());
								ticketAnticipo.setTotalCobranza(anticipoCliente.getImporte());
//								ticketAnticipo.setNumeroTicket(arregloRegistroAnticipo[0]);
								// Marcelo Moyano Ticket Anticipo
								String numeroRegistro = ""+anticipoCliente.getCodigoVendedor() + anticipoCliente.getAnticipoSecuencia();
								// Marcelo Moyano 27/03/2013
								ticketAnticipo.setNumeroTicket(numeroRegistro);
								Mensaje.mostrarAviso(arregloRegistroAnticipo[2]);
							} else {
								Mensaje.mostrarAviso("El anticipo con Papeleta de Deposito " + anticipoCliente.getDescripcionBancoPromesa() + "y secuencial " + anticipoCliente.getAnticipoSecuencia()+ " ya existe, por favor eliminar y volver a ingresar.");
							}
						}
//						buscaAnticipos();
						buscaAnticiposSinFiltrosFechas();//	Marcelo Moyano 19/03/2013 - 09:49
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(Promesa.getInstance(), "Ud. sólo puede sincronizar en modo ONLINE.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//	Marcelo Moyano 19/03/2013 - 09:09
	@SuppressWarnings("static-access")
	private void buscaAnticiposSinFiltrosFechas() {
		SqlAnticipoClienteImpl sql = new SqlAnticipoClienteImpl();
		String cod = Promesa.getInstance().datose.get(0).getStrCodigo();
		final List<AnticipoCliente> AnticiposClientes = sql.obtenerListaAnticipoCliente(cod);
		if (AnticiposClientes.size() >= 0) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					mdlAnticipos.limpiar();
					for (AnticipoCliente anticipoCliente : AnticiposClientes) {
							mdlAnticipos.agregarAnticipoCliente(anticipoCliente);
					}
					establecerAnchoColumnas();
				}
			});
		}
	}

	private void establecerAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblAnticipos.getColumnModel();
		TableColumn columnaTabla;
		columnaTabla = modeloColumna.getColumn(0);
		anchoColumna = 0;
		columnaTabla.setMinWidth(anchoColumna);
		columnaTabla.setMaxWidth(anchoColumna);
		columnaTabla.setPreferredWidth(anchoColumna);
		Util.setAnchoColumnas(tblAnticipos);
	}

	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnEliminar;
	private javax.swing.JButton btnSincronizar;
	private com.toedter.calendar.JDateChooser dtcDesde;
	private com.toedter.calendar.JDateChooser dtcHasta;
	private javax.swing.JLabel lblFechaDesde;
	private javax.swing.JLabel lblHasta;
	private javax.swing.JPanel pnlBuscar;
	private javax.swing.JScrollPane scrTablaAnticipos;
	private javax.swing.JTable tblAnticipos;
}