package com.promesa.internalFrame.cobranzas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.BancoPromesa;
import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.cobranzas.sql.impl.SqlAnticipoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlBancoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlBancoPromesaImpl;
import com.promesa.cobranzas.sql.impl.SqlFormaPagoAnticipoImpl;
import com.promesa.impresiones.ReporteTicket;
import com.promesa.impresiones.cobranzas.TicketAnticipo;
import com.promesa.impresiones.dpp350.ReporteAnticipo;
import com.promesa.internalFrame.planificacion.IVentanaVisita;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.sap.SCobranzas;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.FechaHora;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;
import com.promesa.util.ValidadorEntradasDeTexto;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class IRegistroAnticipo extends javax.swing.JInternalFrame {

	private String codigoCliente;
	private String codigoVendedor;
	private String idBancoCliente;
	private String nombreCompletoCliente;
	private String formaPago;
	@SuppressWarnings("unused")
	private String numTicket;
	private String descripcionBanco;
	private boolean banderaGrabaOnLine;
	private SqlBancoClienteImpl sqlBancoClienteImpl = null;
	private String strDispositivoImpresora;
	private BeanAgenda ba;
	private static int ERROR_MENSAJE = JOptionPane.ERROR_MESSAGE;

	/** Creates new form IRegistroAnticipo */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IRegistroAnticipo(BeanAgenda ba, String codigoVendedor,
			String nombreCompletoCliente) {
		initComponents();
		this.ba =ba;
		SqlFormaPagoAnticipoImpl sqlFormaPagoAnticipoImpl = new SqlFormaPagoAnticipoImpl();
		SqlBancoPromesaImpl sqlBancoPromesaImpl = new SqlBancoPromesaImpl();
		List<FormaPago> formasPagos = sqlFormaPagoAnticipoImpl.obtenerListaFormaPagoAnticipo();
		cmbFormaPago.setModel(new DefaultComboBoxModel(formasPagos.toArray()));
		List<BancoPromesa> bcoPromesas = sqlBancoPromesaImpl.obtenerListaBancoPromesa();
		cmbBancoPromesa.setModel(new DefaultComboBoxModel(bcoPromesas.toArray()));
		habilitarDeshabilitarComponentesIniciales();
		// Marcelo Moyano 11/03/2013 - 16:22
		dateFechaDeposito.setDateFormatString("dd-MM-yyyy");
		// Marcelo Moyano 11/03/2013
		dateFechaDeposito.setDate(new Date());
		this.codigoCliente = ba.getStrCodigoCliente();
		this.codigoVendedor = codigoVendedor;
		this.nombreCompletoCliente = nombreCompletoCliente;
		final String cc = codigoCliente;

		strDispositivoImpresora = Util.verificarImpresora();

		txtNroCtaBcoCliente.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
			}

			public void keyReleased(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					sqlBancoClienteImpl = new SqlBancoClienteImpl();
					try {
						idBancoCliente = "";
						txtBancoCliente.setText("");
						List<BancoCliente> listaBancoCliente = sqlBancoClienteImpl
								.obtenerListaBancoCliente(cc);
						boolean bandera = false;
						if (listaBancoCliente.size() > 0) {
							for (BancoCliente bc : listaBancoCliente) {
								String nroCta = bc.getNroCtaBcoCliente().trim();
								if (nroCta.equals(txtNroCtaBcoCliente.getText().trim())) {
									idBancoCliente = bc.getIdBancoCliente();
									txtBancoCliente.setText(bc.getDescripcionBancoCliente());
									descripcionBanco = txtBancoCliente.getText();
									bandera = true;
									break;
								}
							}
							if (bandera) {
								txtNroCtaBcoCliente.setBackground(Color.white);
							} else {
								Color color = new Color(254, 205, 215);
								txtNroCtaBcoCliente.setBackground(color);
							}
						} else {
							String msg = "No existe ningún número de cuenta del cliente asociado a un banco.";
							JOptionPane.showMessageDialog(Promesa.getInstance(),msg,"Error", ERROR_MENSAJE);
						}
					} catch (Exception e) {
						Color color = new Color(254, 205,215);
						txtNroCtaBcoCliente.setBackground(color);
					}
				}
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		});
		DocumentListener documentListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
				txtImporte.setBackground(Color.white);
			}

			public void insertUpdate(DocumentEvent documentEvent) {
				txtImporte.setBackground(Color.white);
			}

			public void removeUpdate(DocumentEvent documentEvent) {
				txtImporte.setBackground(Color.white);
			}
		};
		txtImporte.getDocument().addDocumentListener(documentListener);
		this.setTitle("Registro de Anticipo del Cliente: " + this.codigoCliente
				+ " - " + nombreCompletoCliente);
		lblTitulo.setText("Registro de Anticipo del Cliente: "
				+ this.codigoCliente + " - " + nombreCompletoCliente);

		txtReferencia.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtReferencia)
					txtReferencia.selectAll();
			}
		});
		// Marcelo Moyano 22/04/2013 - 16:34
		ValidadorEntradasDeTexto validador = new ValidadorEntradasDeTexto(10,
				Constante.DECIMALES);
		txtImporte.setDocument(validador);
		// MArcelo Moyano
		txtImporte.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtImporte)
					txtImporte.selectAll();
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtImporte){
					String str = Util.formatearNumero(txtImporte.getText());
					txtImporte.setText(str);
				}
			}
		});
		
//		ValidadorEntradasDeTexto validadorIva = new ValidadorEntradasDeTexto(10,
//				Constante.DECIMALES);
//		txtImporteIva.setDocument(validadorIva);
//		
//		txtImporteIva.addFocusListener(new java.awt.event.FocusAdapter() {
//			public void focusGained(java.awt.event.FocusEvent evt) {
//				if (evt.getSource() == txtImporteIva)
//					txtImporteIva.selectAll();
//			}
//
//			public void focusLost(java.awt.event.FocusEvent evt) {
//				if (evt.getSource() == txtImporteIva){
//					String str = Util.formatearNumero(txtImporteIva.getText());
//					txtImporteIva.setText(str);
//				}
//			}
//		});
		
		
		txtNroCtaBcoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtNroCtaBcoCliente)
					txtNroCtaBcoCliente.selectAll();
			}
		});
		txtBancoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtBancoCliente)
					txtBancoCliente.selectAll();
			}
		});
		txtObservaciones.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtObservaciones)
					txtObservaciones.selectAll();
			}
		});
	}

	private void habilitarDeshabilitarComponentesIniciales() {
		btnImprimir.setEnabled(false);
		txtReferencia.setEditable(false);
		lblImporte.setText("Importe:");
		txtImporte.setEditable(false);
		//Marcelo Moyano Retencion Iva
		// 05/02/2015 
//		lblImporteIva.setVisible(false);
//		txtImporteIva.setVisible(false);
		txtObservaciones.setEnabled(false);
		lblNroCtaBcoCliente.setText("Nro. Cta. Bco. Cliente:");
		txtNroCtaBcoCliente.setEditable(false);
		lblBcoPromesa.setText("Bco. PROMESA:");
		cmbBancoPromesa.setEnabled(false);
		dateFechaDeposito.setEnabled(false);
	}

	private void deshabilitarComponentes() {
		cmbFormaPago.setEnabled(false);
		txtReferencia.setBackground(new Color(212, 208, 200));
		txtReferencia.setEditable(false);
		txtImporte.setBackground(new Color(212, 208, 200));
		txtImporte.setEditable(false);
		
//		txtImporteIva.setBackground(new Color(212, 208, 200));
//		txtImporteIva.setEditable(false);
		txtNroCtaBcoCliente.setBackground(new Color(212, 208, 200));
		txtNroCtaBcoCliente.setEditable(false);
		txtBancoCliente.setBackground(new Color(212, 208, 200));
		txtBancoCliente.setEditable(false);
		cmbBancoPromesa.setEnabled(false);
		txtObservaciones.setBackground(new Color(212, 208, 200));
		txtObservaciones.setEnabled(false);
		dateFechaDeposito.setEnabled(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
		pnlMain = new JPanel();
		lblTitulo = new JLabel();
		lblCentral = new JPanel();
		toolBarAnticipo = new JToolBar();
		btnGrabar = new JButton();
		jSeparator2 = new JToolBar.Separator();
		btnCancelar = new JButton();
		btnAgenda = new JButton();
		jSeparator1 = new JToolBar.Separator();
		btnImprimir = new JButton();
		pnlDatos = new JPanel();
		lblInfoGeneral = new JLabel();
		pnlDatosInput = new JPanel();
		lblFormaPago = new JLabel();
		cmbFormaPago = new JComboBox();
		lblReferencia = new JLabel();
		txtReferencia = new JTextField();
		lblImporte = new JLabel();
//		lblImporteIva = new JLabel();
		lblNroCtaBcoCliente = new JLabel();
		txtNroCtaBcoCliente = new JTextField();
		txtAnticipoSecuencia = new JTextField();
		lblBancoCliente = new JLabel();
		txtBancoCliente = new JTextField();
		lblBcoPromesa = new JLabel();
		cmbBancoPromesa = new JComboBox();
		lblObservaciones = new JLabel();
		scrObservaciones = new JScrollPane();
		txtObservaciones = new JTextPane();
		txtImporte = new JTextField();
//		txtImporteIva = new JTextField();
		dateFechaDeposito = new JDateChooser();
		lblFechaDeposito = new JLabel();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Registro de Anticipo de Cliente:");

		pnlMain.setLayout(new BorderLayout());

		lblTitulo.setBackground(new Color(175, 200, 222));
		lblTitulo.setFont(new Font("Tahoma", 0, 18));
		lblTitulo.setText("Registro de Anticipo de Cliente:");
		lblTitulo.setOpaque(true);
		pnlMain.add(lblTitulo, BorderLayout.PAGE_START);

		lblCentral.setLayout(new BorderLayout());

		toolBarAnticipo.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		toolBarAnticipo.setFloatable(false);
		toolBarAnticipo.setRollover(true);

		String guardar32Png = "/imagenes/guardar_32.png";
		URL urlGuardar32 = getClass().getResource(guardar32Png);
		btnGrabar.setIcon(new ImageIcon(urlGuardar32)); // NOI18N
		btnGrabar.setText("Grabar");
		btnGrabar.setFocusable(false);
		btnGrabar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGrabarActionPerformed(evt);
			}
		});
		toolBarAnticipo.add(btnGrabar);
		toolBarAnticipo.add(jSeparator2);

		String cancelar24Gif = "/imagenes/eliminar_24.gif";
		URL urlCancelar24 = getClass().getResource(cancelar24Gif);
		btnCancelar.setIcon(new ImageIcon(urlCancelar24)); // NOI18N
		btnCancelar.setText("Cancelar");
		btnCancelar.setFocusable(false);
		btnCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCancelarActionPerformed(evt);
			}
		});
		toolBarAnticipo.add(btnCancelar);
		toolBarAnticipo.add(jSeparator1);

		String imprimirPng = "/imagenes/icon-imprimir.png";
		URL urlImprimir = getClass().getResource(imprimirPng);
		btnImprimir.setIcon(new ImageIcon(urlImprimir)); // NOI18N
		btnImprimir.setText("Imprimir comprobante");
		btnImprimir.setFocusable(false);
		btnImprimir.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnImprimir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnImprimirActionPerformed(evt);
			}
		});
		toolBarAnticipo.add(btnImprimir);
		JToolBar.Separator separatorAgenda = new JToolBar.Separator();
		toolBarAnticipo.add(separatorAgenda);

		//TODO
		String AgendaPng = "/imagenes/iplanificaciones.gif";
		URL urlAgenda = this.getClass().getResource(AgendaPng);
		ImageIcon agenda = new ImageIcon(urlAgenda);
		btnAgenda.setIcon(agenda);
		btnAgenda.setText("Proximas Visitas");
		btnAgenda.setFocusable(false);
		btnAgenda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnAgenda.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	btmMostrarProximasVisitasActionPerformed(e);
		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Apéndice de método generado automáticamente
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Apéndice de método generado automáticamente
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Apéndice de método generado automáticamente
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Apéndice de método generado automáticamente
				
			}
		});
		toolBarAnticipo.add(btnAgenda);
		
		
		lblCentral.add(toolBarAnticipo, BorderLayout.PAGE_START);

		pnlDatos.setLayout(new BorderLayout());

		lblInfoGeneral.setBackground(new Color(175, 200, 222));
		lblInfoGeneral.setFont(new Font("Tahoma", 1, 11));
		lblInfoGeneral.setText("Información general");
		lblInfoGeneral.setBorder(BorderFactory.createEmptyBorder(3,3, 3, 1));
		lblInfoGeneral.setOpaque(true);
		pnlDatos.add(lblInfoGeneral, BorderLayout.PAGE_START);

		pnlDatosInput.setBackground(new Color(255, 255, 255));

		lblFormaPago.setText("<html>Forma de Pago <font color='red'>(*)</font></html>");
		// Marcelo Moyano
		lblFechaDeposito.setText("<html>Fecha Dep./Dcto.:</html>");

		cmbFormaPago.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		cmbFormaPago.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbFormaPagoActionPerformed(evt);
			}
		});

		lblReferencia.setText("Referencia:");

		lblImporte.setText("Importe:");
		
//		lblImporteIva.setText("Importe IVA:");

		lblNroCtaBcoCliente.setText("Nro. Cta. Bco. Cliente:");

		lblBancoCliente.setText("Banco cliente:");

		txtBancoCliente.setEditable(false);

		lblBcoPromesa.setText("Bco. PROMESA:");

		cmbBancoPromesa.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		cmbBancoPromesa.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbBancoPromesaActionPerformed(evt);
			}
		});

		lblObservaciones.setText("Observaciones:");

		/*
		 * Requerimiento Cobranza new
		 */
		// txtObservaciones.addKeyListener(new java.awt.event.KeyListener(){
		//
		// @Override
		// public void keyPressed(KeyEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void keyReleased(KeyEvent e) {
		// // TODO Auto-generated method stub
		// }
		//
		// @Override
		// public void keyTyped(KeyEvent e) {
		// // TODO Auto-generated method stub
		// String strLongitud = txtObservaciones.getText();
		// if(strLongitud.length() > 50){
		// e.consume();
		// }
		// }
		//
		// });
		scrObservaciones.setViewportView(txtObservaciones);

		GroupLayout pnlDatosInputLayout = new GroupLayout(pnlDatosInput);
		pnlDatosInput.setLayout(pnlDatosInputLayout);
		pnlDatosInputLayout.setHorizontalGroup(pnlDatosInputLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(pnlDatosInputLayout.createSequentialGroup().addContainerGap()
										.addGroup(
												pnlDatosInputLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																pnlDatosInputLayout
																		.createSequentialGroup()
																		.addGroup(
																				pnlDatosInputLayout
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								lblNroCtaBcoCliente,
																								GroupLayout.PREFERRED_SIZE,
																								152,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblReferencia)
																						.addComponent(
																								lblFormaPago,
																								GroupLayout.PREFERRED_SIZE,
																								144,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblBcoPromesa,
																								GroupLayout.PREFERRED_SIZE,
																								143,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDatosInputLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								pnlDatosInputLayout
																										.createSequentialGroup()
																										.addGroup(
																												pnlDatosInputLayout
																														.createParallelGroup(
																																Alignment.TRAILING)
																														.addComponent(
																																cmbFormaPago,
																																Alignment.LEADING,
																																GroupLayout.PREFERRED_SIZE,
																																170,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																txtReferencia,
																																Alignment.LEADING,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																txtNroCtaBcoCliente,
																																Alignment.LEADING,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18,
																												18,
																												18)
																										.addGroup(
																												pnlDatosInputLayout
																														.createParallelGroup(
																																Alignment.TRAILING,
																																false)
//																														.addComponent(
//																																lblImporteIva,
//																																GroupLayout.PREFERRED_SIZE,
//																																109,
//																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblBancoCliente,
																																GroupLayout.PREFERRED_SIZE,
																																109,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblImporte,
																																GroupLayout.PREFERRED_SIZE,
																																109,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblFechaDeposito,
																																GroupLayout.PREFERRED_SIZE,
																																109,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												pnlDatosInputLayout
																														.createParallelGroup(
																																Alignment.LEADING,
																																false)
//																														.addComponent(txtImporteIva)
																														.addComponent(txtImporte)
																														.addComponent(
																																txtBancoCliente,
																																GroupLayout.DEFAULT_SIZE,
																																160,
																																Short.MAX_VALUE)
																														.addComponent(
																																dateFechaDeposito,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)))
																						.addComponent(
																								cmbBancoPromesa,
																								GroupLayout.PREFERRED_SIZE,
																								200,//256
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																pnlDatosInputLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblObservaciones)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				scrObservaciones,
																				GroupLayout.PREFERRED_SIZE,
																				436,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(53, Short.MAX_VALUE)));

		pnlDatosInputLayout.linkSize(SwingConstants.HORIZONTAL,
				new Component[] { lblBancoCliente, lblFormaPago,
						lblImporte, lblNroCtaBcoCliente, lblObservaciones,
						lblReferencia, lblBcoPromesa, lblFechaDeposito });

		pnlDatosInputLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { cmbFormaPago, txtNroCtaBcoCliente,
						txtReferencia, dateFechaDeposito });

		pnlDatosInputLayout
				.setVerticalGroup(pnlDatosInputLayout
						.createParallelGroup(
								Alignment.LEADING)
						.addGroup(
								pnlDatosInputLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlDatosInputLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblFormaPago,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblFechaDeposito,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cmbFormaPago,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																dateFechaDeposito,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDatosInputLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblReferencia)
														.addComponent(lblImporte)
														.addComponent(txtReferencia,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(txtImporte,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												pnlDatosInputLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNroCtaBcoCliente)
														.addComponent(
																txtBancoCliente,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblBancoCliente)
														.addComponent(
																txtNroCtaBcoCliente,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												pnlDatosInputLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblBcoPromesa)
														.addComponent(
																cmbBancoPromesa,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
//														.addComponent(lblImporteIva)
//														.addComponent(txtImporteIva))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												pnlDatosInputLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblObservaciones)
														.addComponent(
																scrObservaciones,
																GroupLayout.PREFERRED_SIZE,
																134,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(80, Short.MAX_VALUE)));

		pnlDatos.add(pnlDatosInput,BorderLayout.CENTER);

		lblCentral.add(pnlDatos, BorderLayout.CENTER);

		pnlMain.add(lblCentral, BorderLayout.CENTER);

		getContentPane().add(pnlMain, BorderLayout.CENTER);

		txtAnticipoSecuencia.setVisible(false);
		pack();
	}

	private void btnGrabarActionPerformed(ActionEvent evt) {
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
				try {
					try {
						Double.parseDouble(txtImporte.getText());
					} catch (Exception e) {
						txtImporte.setText("0.00");
						txtImporte.setBackground(new Color(254, 205, 215));
						return;
					}
					BeanDato usuario = Promesa.datose.get(0);
					if (usuario.getStrModo().equals("1")) { // ONLINE}
						grabarAnticipoOnLine();
						banderaGrabaOnLine = true;
					} else { //OFFLINE}
						grabarAnticipoOffLine();
						banderaGrabaOnLine = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}
	public String verificarVisitas(String strMensaje, String codigoCliente){
		BeanDato usuario = Promesa.datose.get(0);
		BeanAgenda ba = new BeanAgenda();
		Util util = new Util();
		ba.setVENDOR_ID(usuario.getStrCodigo());
		ba.setCUST_ID(codigoCliente);
		SqlAgenda getAgenda = new SqlAgendaImpl();
		getAgenda.setListaAgenda(ba);
		List<BeanAgenda> listaAgenda =getAgenda.getListaAgenda();
		if(listaAgenda.size()>1){
			strMensaje="<html>Las proximas visitas son:";
			List<Date> fecha = new ArrayList<Date>();
			for(int i=0;i<listaAgenda.size();i++){
				Date date = util.convierteStringaDate(listaAgenda.get(i).getBEGDA());
				if(date.after(util.convierteStringaDate(this.ba.getBEGDA()))){
				String[] hora = listaAgenda.get(i).getTIME().trim().split(":");
				Calendar cal = Calendar.getInstance(); // creates calendar
			    cal.setTime(date); // sets calendar time/date
			    cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hora[0])); // adds n hour
			    cal.add(Calendar.MINUTE, Integer.parseInt(hora[1])); // adds n minutes
				date = cal.getTime(); // returns new date object, one hour in the future
				fecha.add(date);
				}
			}
			Collections.sort(fecha);  
			for(int i=0;i<fecha.size();i++){
				if(fecha.get(i).after(util.convierteStringaDate(this.ba.getBEGDA()))){
					strMensaje += "<br>"+Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(fecha.get(i));
				}
			}
			strMensaje += "</html>";
			if(fecha.isEmpty()){
				strMensaje="";
			}
		}
		return strMensaje;
	}


	protected void btmMostrarProximasVisitasActionPerformed(MouseEvent evt) {
		try {
			String visitas = verificarVisitas("",codigoCliente);
			if(visitas.equals("")){
				visitas = "El cliente no tiene proximas visitas";
			}
			JOptionPane optionpane = new JOptionPane(visitas,JOptionPane.PLAIN_MESSAGE,
					JOptionPane.DEFAULT_OPTION,null,null,null);//.showMessageDialog(this,visitas);
			
			JDialog dialog = optionpane.createDialog("Proximas Visitas");
			Point point = new Point(evt.getComponent().getLocationOnScreen().x+evt.getX(),evt.getComponent().getLocationOnScreen().y+evt.getY());
			dialog.setLocation(point);
			dialog.setVisible(true);
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			Util.mostrarExcepcion(e);
		}
	}
	private void grabarAnticipoOnLine() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FormaPago formPg = (FormaPago) cmbFormaPago.getSelectedItem();
				Promesa promesa = Promesa.getInstance();
				String codVend = Promesa.datose.get(0).getStrCodigo();
				String secuencia = Util.obtenerSecuenciaPorVendedor(codVend);
				Long antSecuencia;
				if (secuencia.equals("0")) {
					secuencia = "1";
					antSecuencia = Long.parseLong(secuencia);
					try {
						Util.insertarSecuenciaPorVendedor(codVend, secuencia);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					antSecuencia = Long.parseLong(secuencia);
					antSecuencia++;
					secuencia = String.valueOf(antSecuencia);
					Util.actualizarSecuencia(codVend, secuencia);
				}
				if (("").equals(formPg.getIdFormaPago())) { // Vacío
					String msg = "Por favor, seleccione una forma de pago.";
					JOptionPane.showMessageDialog(promesa, msg, "Error",ERROR_MENSAJE);
				} else if (("VT").equals(formPg.getIdFormaPago()) ) {
					// Retención VT
					if (!Pattern.matches(Constante.EXP_REG_REFERENCIA,txtReferencia.getText().trim())) {
						JOptionPane.showMessageDialog(promesa,
								Constante.MENSAJE_ANTICIPO_RETENCION, "Error",
								ERROR_MENSAJE);
					} else {
						String importe = txtImporte.getText().trim();
//						if(importe.equals("")){
//							txtImporte.setText("0.00");
//							importe = "0.00";
//						}
						String referencia = txtReferencia.getText();
						if (!importe.equals("0.00")  && !referencia.equals("") ) {
//							if(txtImporteIva.getText().trim().equals("")){
//								txtImporteIva.setText("0.00");
//							}
							AnticipoCliente ac = new AnticipoCliente();
							ac.setCodigoCliente(codigoCliente);
							ac.setNombreCompletoCliente(nombreCompletoCliente);
							ac.setIdFormaPago(formPg.getIdFormaPago());
							ac.setReferencia(txtReferencia.getText());
							ac.setImporte(txtImporte.getText());
							ac.setNroCtaBcoCliente("");
							ac.setIdBancoCliente("");
							ac.setIdBancoPromesa("");
							
							Date fDeposito = dateFechaDeposito.getDate();
							String fCreacion = Util.convierteFechaAFormatoYYYYMMdd(fDeposito);
							ac.setFechaCreacion(fCreacion);
							
							ac.setCodigoVendedor(codigoVendedor);
							ac.setObservaciones(txtObservaciones.getText());
//							ac.setImporteIva(txtImporteIva.getText());
							String secAnti = String.valueOf(antSecuencia);
							ac.setAnticipoSecuencia(secAnti);
							descripcionBanco = "";
							SCobranzas sCobranzas = new SCobranzas();
							try {
								String[] arreglo = sCobranzas.registroAnticipo(ac);
								if (arreglo != null) {
									if (("S").equals(arreglo[1])) {
										numTicket = arreglo[0];
										deshabilitarComponentes();
										btnGrabar.setEnabled(false);
										btnImprimir.setEnabled(true);
										String antSecu = String.valueOf(antSecuencia);
										txtAnticipoSecuencia.setText(antSecu+ "-" + arreglo[0]);
										JOptionPane.showMessageDialog(promesa,arreglo[2],"Exito",
														JOptionPane.INFORMATION_MESSAGE);
									} else { // E
										JOptionPane.showMessageDialog(promesa,
												arreglo[2], "Error",ERROR_MENSAJE);
									}
								} else {
									String msg = "No se realizó correctamente la conexión a SAP.";
									JOptionPane.showMessageDialog(promesa, msg,
											"Error", ERROR_MENSAJE);
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(promesa,
										e.getMessage(), "Error", ERROR_MENSAJE);
							}
						} else {
							String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
							JOptionPane.showMessageDialog(promesa, msg,
									"Error", ERROR_MENSAJE);
						}
					}
				} else if (("VX").equals(formPg.getIdFormaPago())) {
					BancoPromesa bnkPrm = (BancoPromesa) cmbBancoPromesa
							.getSelectedItem();
					String importe = txtImporte.getText().trim();
					String idBnkPromesa = bnkPrm.getIdBancoPromesa();
					String referencia = txtReferencia.getText();
					if (!importe.equals("") && !idBnkPromesa.equals("") && !referencia.equals("")) {
						AnticipoCliente ac = new AnticipoCliente();
						ac.setCodigoCliente(codigoCliente);
						ac.setNombreCompletoCliente(nombreCompletoCliente);
						ac.setIdFormaPago(formPg.getIdFormaPago());
						ac.setReferencia(txtReferencia.getText());
						ac.setImporte(txtImporte.getText());
						ac.setNroCtaBcoCliente("");
						ac.setIdBancoCliente("");

						Date fDeposito = dateFechaDeposito.getDate();
						String fCreacion = Util.convierteFechaAFormatoYYYYMMdd(fDeposito);
						ac.setFechaCreacion(fCreacion);

						descripcionBanco = bnkPrm.getDescripcionBancoPromesa();
						ac.setDescripcionBancoPromesa(descripcionBanco);

						ac.setCodigoVendedor(codigoVendedor);
						ac.setObservaciones(txtObservaciones.getText());
						ac.setAnticipoSecuencia(String.valueOf(antSecuencia));
						ac.setImporteIva("0.00");
						SCobranzas sCobranzas = new SCobranzas();
						try {
							String[] arreglo = sCobranzas.registroAnticipo(ac);
							if (arreglo != null) {
								if (("S").equals(arreglo[1])) {
									numTicket = arreglo[0];
									deshabilitarComponentes();
									btnGrabar.setEnabled(false);
									btnImprimir.setEnabled(true);
									String antSecu = String.valueOf(antSecuencia);
									txtAnticipoSecuencia.setText(antSecu+"-"+ arreglo[0]);
									JOptionPane.showMessageDialog(promesa,arreglo[2],
											"Exito",JOptionPane.INFORMATION_MESSAGE);
								} else { // E
									JOptionPane.showMessageDialog(promesa,arreglo[2],"Error", ERROR_MENSAJE);
								}
							} else {
								String msg = "No se realizó correctamente la conexión a SAP.";
								JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
							}
						} catch (Exception e) {
							String msg = e.getMessage();
							JOptionPane.showMessageDialog(promesa, msg,"Error", ERROR_MENSAJE);
						}
					} else {
						String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
						JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
					}
				} else if (("VY").equals(formPg.getIdFormaPago())) {
					// cheque a la vista
					String importe = txtImporte.getText().trim();
					String NroCtaBcoCli = txtNroCtaBcoCliente.getText().trim();
					String referencia = txtReferencia.getText();
					boolean flagNroCuentCli = validarNumeroDeCuentaCliente(codigoCliente, NroCtaBcoCli);
					if (!importe.equals("")&& !NroCtaBcoCli.equals("") && flagNroCuentCli
							&& !("").equals(idBancoCliente) && !referencia.equals("")) {
						AnticipoCliente ac = new AnticipoCliente();
						ac.setCodigoCliente(codigoCliente);
						ac.setNombreCompletoCliente(nombreCompletoCliente);
						ac.setIdFormaPago(formPg.getIdFormaPago());
						ac.setReferencia(txtReferencia.getText());
						ac.setImporte(txtImporte.getText());
						ac.setNroCtaBcoCliente(txtNroCtaBcoCliente.getText());
						ac.setDescripcionBancoCliente(txtBancoCliente.getText());
						ac.setIdBancoPromesa("");
						
//						String fDeposito = dateFechaDeposito.getDate().toString();
////					String fCreacion = Util.convierteFechaAFormatoYYYYMMdd(fDeposito);
						ac.setFechaCreacion(FechaHora.getFecha());
						
						ac.setCodigoVendedor(codigoVendedor);
						ac.setObservaciones(txtObservaciones.getText());
						ac.setAnticipoSecuencia(String.valueOf(antSecuencia));
						ac.setImporteIva("0.00");
						SCobranzas sCobranzas = new SCobranzas();
						try {
							String[] arreglo = sCobranzas.registroAnticipo(ac);
							if (arreglo != null) {
								if (("S").equals(arreglo[1])) {
									numTicket = arreglo[0];
									deshabilitarComponentes();
									btnGrabar.setEnabled(false);
									btnImprimir.setEnabled(true);
									String antSec = String.valueOf(antSecuencia);
									txtAnticipoSecuencia.setText(antSec+ "-"+ arreglo[0]);
									JOptionPane.showMessageDialog(promesa,arreglo[2],
											"Exito",JOptionPane.INFORMATION_MESSAGE);
								} else { // E
									JOptionPane.showMessageDialog(promesa,arreglo[2],"Error", ERROR_MENSAJE);
								}
							} else {
								String msg = "No se realizó correctamente la conexión a SAP.";
								JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(promesa, e.getMessage(),"Error", ERROR_MENSAJE);
						}
					} else {
						String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
						JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
					}
				} else if (("VZ").equals(formPg.getIdFormaPago())) { // Efectivo
					if (!txtImporte.getText().trim().equals("")) {
						AnticipoCliente ac = new AnticipoCliente();
						ac.setCodigoCliente(codigoCliente);
						ac.setNombreCompletoCliente(nombreCompletoCliente);
						ac.setIdFormaPago(formPg.getIdFormaPago());
						ac.setReferencia(txtReferencia.getText());
						ac.setImporte(txtImporte.getText());
						ac.setNroCtaBcoCliente("");
						ac.setIdBancoCliente("");
						ac.setIdBancoPromesa("");
						ac.setCodigoVendedor(codigoVendedor);
						ac.setObservaciones(txtObservaciones.getText());
						String fCreacion = Util.convierteFechaAFormatoYYYYMMdd(new Date());
						ac.setFechaCreacion(fCreacion);
						ac.setAnticipoSecuencia(String.valueOf(antSecuencia));
						ac.setImporteIva("0.00");
						descripcionBanco = "";
						SCobranzas sCobranzas = new SCobranzas();
						try {
							String[] arreglo = sCobranzas.registroAnticipo(ac);
							if (arreglo != null) {
								if (("S").equals(arreglo[1])) {
									numTicket = arreglo[0];
									deshabilitarComponentes();
									btnGrabar.setEnabled(false);
									btnImprimir.setEnabled(true);
									String antSecu = String.valueOf(antSecuencia);
									txtAnticipoSecuencia.setText(antSecu+ "-"+ arreglo[0]);
									JOptionPane.showMessageDialog(promesa,arreglo[2],
											"Exito",JOptionPane.INFORMATION_MESSAGE);
								} else { // E
									JOptionPane.showMessageDialog(promesa,arreglo[2],
											"Error", ERROR_MENSAJE);
								}
							} else {
								String msg = "No se realizó correctamente la conexión a SAP.";
								JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(promesa, e.getMessage(),"Error", ERROR_MENSAJE);
						}
					} else {
						String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
						JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
					}
				}
			}
		});
	}

	private void grabarAnticipoOffLine() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				FormaPago formPg = (FormaPago) cmbFormaPago.getSelectedItem();
				Promesa promesa = Promesa.getInstance();
				Long secuencia = grabarSecuencialTicketRA();
				txtAnticipoSecuencia.setText(String.valueOf(secuencia));
				if (("").equals(formPg.getIdFormaPago())) { // Vacío
					String msg = "Por favor, seleccione una forma de pago.";
					JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
				} else if (("VT").equals(formPg.getIdFormaPago())) { // Retención
					if (!Pattern.matches(Constante.EXP_REG_REFERENCIA,
							txtReferencia.getText().trim())) {
						JOptionPane.showMessageDialog(promesa,
								Constante.MENSAJE_ANTICIPO_RETENCION, "Error",ERROR_MENSAJE);
					} else {
						String importe = txtImporte.getText().trim();
						String referencia = txtReferencia.getText();
						if (!importe.equals("") && !referencia.equals("")) {
//							String importeIva = txtImporteIva.getText();
//							if(importeIva.trim().equals("")){
//								txtImporteIva.setText("0.00");
//							}
							AnticipoCliente ac = new AnticipoCliente();
							ac.setCodigoCliente(codigoCliente);
							ac.setNombreCompletoCliente(nombreCompletoCliente);
							ac.setIdFormaPago(formPg.getIdFormaPago());
							ac.setReferencia(referencia);
							ac.setImporte(importe);
							ac.setNroCtaBcoCliente("");
							ac.setIdBancoCliente("");
							ac.setIdBancoPromesa("");
							ac.setCodigoVendedor(codigoVendedor);
							ac.setObservaciones(txtObservaciones.getText());
							ac.setSincronizado("0");
							
							String strFechaDeposito;
							SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
							Date fDeposito = dateFechaDeposito.getDate();
							strFechaDeposito = formato.format(fDeposito);
							ac.setFechaCreacion(strFechaDeposito);
							ac.setAnticipoSecuencia(String.valueOf(secuencia));
//							ac.setImporteIva(txtImporteIva.getText());
							descripcionBanco = "";
							SqlAnticipoClienteImpl sqlAnticipoClienteImpl = new SqlAnticipoClienteImpl();
							try {
								sqlAnticipoClienteImpl.insertarAnticipoCliente(ac);
								String msg = "Se grabó correctamente el anticipo con N° Ticket ";
								JOptionPane.showMessageDialog(promesa,msg + secuencia, "Exito",
										JOptionPane.INFORMATION_MESSAGE);
								deshabilitarComponentes();
								btnGrabar.setEnabled(false);
								btnImprimir.setEnabled(true);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(promesa, e.getMessage(),"Error", ERROR_MENSAJE);
							}
						} else {
							String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
							JOptionPane.showMessageDialog(promesa,msg,"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (("VX").equals(formPg.getIdFormaPago())) {
					BancoPromesa bcoProSelec = (BancoPromesa) cmbBancoPromesa.getSelectedItem();
					String importe = txtImporte.getText().trim();
					String idBcoPro = bcoProSelec.getIdBancoPromesa();
					String referencia = txtReferencia.getText();
					if (!importe.equals("")&& !idBcoPro.equals("")&& !referencia.equals("")) {
						AnticipoCliente ac = new AnticipoCliente();
						ac.setCodigoCliente(codigoCliente);
						ac.setNombreCompletoCliente(nombreCompletoCliente);
						ac.setIdFormaPago(formPg.getIdFormaPago());
						ac.setReferencia(txtReferencia.getText());
						ac.setImporte(txtImporte.getText());
						ac.setNroCtaBcoCliente("");
						ac.setIdBancoCliente("");
						ac.setIdBancoPromesa(bcoProSelec.getIdBancoPromesa());
						
						String strFechaDeposito;
						SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
						Date fDeposito = dateFechaDeposito.getDate();
						strFechaDeposito = formato.format(fDeposito);
						ac.setFechaCreacion(strFechaDeposito);
						
						String DescripBcoPro = bcoProSelec.getDescripcionBancoPromesa();
						ac.setDescripcionBancoPromesa(DescripBcoPro);
						ac.setDescripcionBancoCliente(DescripBcoPro);
						ac.setCodigoVendedor(codigoVendedor);
						ac.setObservaciones(txtObservaciones.getText());
						ac.setSincronizado("0");
						ac.setAnticipoSecuencia(String.valueOf(secuencia));
						ac.setImporteIva("0.00");
						descripcionBanco = bcoProSelec.getDescripcionBancoPromesa();
						SqlAnticipoClienteImpl sqlAnticipoClienteImpl = new SqlAnticipoClienteImpl();
						try {
							sqlAnticipoClienteImpl.insertarAnticipoCliente(ac);
							String msg = "Se grabó correctamente el anticipo con N° Ticket ";
							JOptionPane.showMessageDialog(promesa,msg+ secuencia, "Exito",
									JOptionPane.INFORMATION_MESSAGE);
							deshabilitarComponentes();
							btnGrabar.setEnabled(false);
							btnImprimir.setEnabled(true);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(promesa, e.getMessage(),"Error", ERROR_MENSAJE);
						}
					} else {
						String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
						JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
					}
				} else if (("VY").equals(formPg.getIdFormaPago())) {
					String importe = txtImporte.getText().trim();
					String nroCtaBcoCli = txtNroCtaBcoCliente.getText().trim();
					String referencia = txtReferencia.getText();
					if (!importe.equals("") && !nroCtaBcoCli.equals("")
							&& validarNumeroDeCuentaCliente(codigoCliente,nroCtaBcoCli)
							&& !("").equals(idBancoCliente) && !referencia.equals("")) {
						AnticipoCliente ac = new AnticipoCliente();
						ac.setCodigoCliente(codigoCliente);
						ac.setNombreCompletoCliente(nombreCompletoCliente);
						ac.setIdFormaPago(formPg.getIdFormaPago());
						ac.setReferencia(txtReferencia.getText());
						ac.setImporte(txtImporte.getText());
						ac.setNroCtaBcoCliente(txtNroCtaBcoCliente.getText());
						ac.setIdBancoCliente(idBancoCliente);
						ac.setIdBancoPromesa("");
						ac.setDescripcionBancoCliente(txtBancoCliente.getText());
						ac.setCodigoVendedor(codigoVendedor);
						ac.setObservaciones(txtObservaciones.getText());
						ac.setSincronizado("0");
						ac.setFechaCreacion(FechaHora.getFecha());
						ac.setAnticipoSecuencia(String.valueOf(secuencia));
						ac.setImporteIva("0.00");
						SqlAnticipoClienteImpl sqlAnticipoClienteImpl = new SqlAnticipoClienteImpl();
						try {
							sqlAnticipoClienteImpl.insertarAnticipoCliente(ac);
							String msg = "Se grabó correctamente el anticipo con N° Ticket ";
							JOptionPane.showMessageDialog(promesa,msg+ secuencia, "Exito",
									JOptionPane.INFORMATION_MESSAGE);
							deshabilitarComponentes();
							btnGrabar.setEnabled(false);
							btnImprimir.setEnabled(true);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(promesa, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
						JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
					}
				} else if (("VZ").equals(formPg.getIdFormaPago())) { // Efectivo
					String importe = txtImporte.getText().trim();
					if (!importe.equals("") && !importe.equals("0.00")) {
						AnticipoCliente ac = new AnticipoCliente();
						ac.setCodigoCliente(codigoCliente);
						ac.setNombreCompletoCliente(nombreCompletoCliente);
						ac.setIdFormaPago(formPg.getIdFormaPago());
						ac.setReferencia(txtReferencia.getText());
						ac.setImporte(txtImporte.getText());
						ac.setNroCtaBcoCliente("");
						ac.setIdBancoCliente("");
						ac.setIdBancoPromesa("");
						ac.setCodigoVendedor(codigoVendedor);
						ac.setObservaciones(txtObservaciones.getText());
						ac.setSincronizado("0");
						ac.setFechaCreacion(FechaHora.getFecha());
						ac.setAnticipoSecuencia(String.valueOf(secuencia));
						ac.setImporteIva("0.00");
						descripcionBanco = "";
						SqlAnticipoClienteImpl sqlAnticipoClienteImpl = new SqlAnticipoClienteImpl();
						try {
							sqlAnticipoClienteImpl.insertarAnticipoCliente(ac);
							String msg = "Se grabó correctamente el anticipo con N° Ticket ";
							JOptionPane.showMessageDialog(promesa,msg+ secuencia, "Exito",
									JOptionPane.INFORMATION_MESSAGE);
							deshabilitarComponentes();
							btnGrabar.setEnabled(false);
							btnImprimir.setEnabled(true);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(promesa, e.getMessage(),"Error", ERROR_MENSAJE);
						}
					} else {
						String msg = "Por favor, ingrese el(los) campo(s) obligatorio(s).";
						JOptionPane.showMessageDialog(promesa,msg,"Error", ERROR_MENSAJE);
					}
				}
			}
		});
	}

	/*
	 * Metodo de Generacion de ticket Secuencal
	 */
	public Long grabarSecuencialTicketRA() {
		Long idCabecera;
		String codVendByCli = Util.obtenerCodigoVendedorPorCliente(codigoCliente);
		String codVend = String.valueOf(codVendByCli);
		if (!codVend.equals("")) {
			String secuVend = Util.obtenerSecuenciaPorVendedor(codVend);
			idCabecera = Long.parseLong(secuVend);
		} else {
			idCabecera = Long.parseLong("0");
		}
		idCabecera++;
		try {
			if (Util.verificarVendedorEnTablaSecuencia(codVend))
				Util.actualizarSecuencia(codVend,String.valueOf(idCabecera));
			else
				Util.insertarSecuenciaPorVendedor(codVend, "1");
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return idCabecera;
	}

	private boolean validarNumeroDeCuentaCliente(String codigoCliente, String nroCtaBcoCliente) {
		boolean bandera = false;
		sqlBancoClienteImpl = new SqlBancoClienteImpl();
		List<BancoCliente> bcoClientes = sqlBancoClienteImpl.obtenerListaBancoCliente(codigoCliente);
		for (BancoCliente bcoCli : bcoClientes) {
			if (bcoCli.getNroCtaBcoCliente().equals(nroCtaBcoCliente)) {
				bandera = true;
				break;
			}
		}
		return bandera;
	}

	private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		String msg = "¿Desea cerrar la ventana?";
		int tipo = JOptionPane.showConfirmDialog(this,msg, "Confirmación",JOptionPane.YES_NO_OPTION);
		if (tipo == JOptionPane.OK_OPTION) {
			this.dispose();
			if(verificarVisitas("",codigoCliente).equals("")){
			
				IVentanaVisita tp = new IVentanaVisita(codigoCliente);
				tp.setVisible(true);
			}
		}
	}

	private void imprimirDPP350(String strTitulo) {
		ReporteAnticipo reporte = new ReporteAnticipo();
		String codVend = Promesa.datose.get(0).getStrCodigo();
		String usuario = Promesa.datose.get(0).getStrUsuario();
		reporte.setTitulo(strTitulo);
		reporte.setVendedor(codVend + " "+ usuario);
		reporte.setDireccion(Util.obtenerDireccionPromesa());
		
		Date date = dateFechaDeposito.getDate();
		String fecha = Util.convierteFechaAFormatoDDMMMYYYY(date);
		reporte.setFecha(fecha);
		reporte.setCliente(codigoCliente + " " + nombreCompletoCliente);
		reporte.setTelefono(Util.obtenerTelefonoPromesa());
		reporte.setReferencia(txtReferencia.getText());
		reporte.setNumeroCuentaBanco(descripcionBanco);
		reporte.setObservacion(txtObservaciones.getText());
		reporte.setFormaPago(formaPago);
		
//		Marcelo Moyano Retención Renta y Retención Iva
		String strImporte = txtImporte.getText();
//		String strIva = txtImporteIva.getText();
//		if(strIva.equals("")){
//			strIva = "0.00";
//		}
		@SuppressWarnings("unused")
		double totalCobranza;
		double importe = Double.parseDouble(strImporte);
//		double iva = Double.parseDouble(strIva);
//		totalCobranza = importe + iva;
		
		reporte.setImporte(strImporte);
//		reporte.setIva(strIva);
		FormaPago formPg = (FormaPago) cmbFormaPago.getSelectedItem();
		reporte.setIdFormaPago(formPg.getIdFormaPago());
		
		reporte.setTotalCobranza(Util.formatearNumero(importe));
		String[] numTicket = txtAnticipoSecuencia.getText().split("-");
		if (numTicket.length > 1) {
			String numeroTicket = Util.completarCeros(10, numTicket[0]);
			reporte.setNumeroTicket(numeroTicket);
			reporte.setNumeroTicketSAP(Util.completarCeros(10, numTicket[1]));
		} else {
			String numeroTicket = Util.completarCeros(10, numTicket[0]);
			reporte.setNumeroTicket(numeroTicket);
			reporte.setNumeroTicketSAP("");
		}
		// nohayImpresoraDpp350 = reporte.isNohayImpresoraDpp350();
		reporte.imprimir();
	}

	@SuppressWarnings("unused")
	private void imprimirOld() {
		String codVend = Promesa.datose.get(0).getStrCodigo();
		String usuario = Promesa.datose.get(0).getStrUsuario();
		TicketAnticipo ta = new TicketAnticipo();
		ta.setCliente(codigoCliente + " " + nombreCompletoCliente);
		ta.setDireccion(Util.obtenerDireccionPromesa());
		ta.setTelefono(Util.obtenerTelefonoPromesa());
		ta.setVendedor(codVend + " "+ usuario);
		
		Date date = dateFechaDeposito.getDate();
		String fecha = Util.convierteFechaAFormatoDDMMMYYYY(date);
		ta.setFecha(fecha);
		ta.setReferencia(txtReferencia.getText());
		ta.setNumeroCuentaBanco(descripcionBanco);
		ta.setFormaPago(formaPago);
		ta.setObservacion(txtObservaciones.getText());
		//Marcelo Moyano Retenciones
		String strImporte = txtImporte.getText();
//		String strIva = txtImporteIva.getText();
//		if(strIva.equals("")){
//			strIva = "0.00";
//		}
		double totalCobranza;
		double importe = Double.parseDouble(strImporte);
//		double iva = Double.parseDouble(strIva);
//		totalCobranza = importe + iva;
		
		ta.setImporte(strImporte);
//		ta.setIva(strIva);
		FormaPago formPg = (FormaPago) cmbFormaPago.getSelectedItem();
		ta.setIdFormaPago(formPg.getIdFormaPago());
		Util.formatearNumero(importe);
		ta.setTotalCobranza(Util.formatearNumero(importe));
		if (banderaGrabaOnLine) { // ONLINE} else {//OFFLINE}
			String numeroTicket = txtAnticipoSecuencia.getText();
			ta.setNumeroTicket(numeroTicket);// Marcelo Moyano
			ReporteTicket reporteTicketAnticipo = new ReporteTicket(ta);
			reporteTicketAnticipo.generarReporteAnticipoOnline();
		} else {
			String[] numTicket = txtAnticipoSecuencia.getText().split("-");
			String numeroticket = Util.completarCeros(10, numTicket[0]);
			ta.setNumeroTicket(numeroticket);// Marcelo Moyano
			ReporteTicket reporteTicketAnticipo = new ReporteTicket(ta);
			reporteTicketAnticipo.generarReporteAnticipo();
		}
	}

	private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					// imprimirDPP350("-  ORIGINAL  -");
					// imprimirOld();
					if (Constante.IMPRESORA_NEW.equals(strDispositivoImpresora)) {
						try {
							imprimirDPP350("-  ORIGINAL  -");
							imprimirDPP350("-  COPIA  -");

						} catch (Exception e) {
							imprimirOld();
						}
					} else {
						imprimirOld();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void cmbFormaPagoActionPerformed(java.awt.event.ActionEvent evt) {
		limpiarCampos();
		FormaPago formPg = (FormaPago) cmbFormaPago.getSelectedItem();
		String idFormaPg = formPg.getIdFormaPago();
		if (("VT").equals(idFormaPg) || ("VI").equals(idFormaPg)) { // Retención
			String referencia = "<html>Referencia: <font color='red'>(*)</font></html>";
			lblReferencia.setText(referencia);
			txtReferencia.setEditable(true);
			String importe = "<html>Importe Renta: <font color='red'>(*)</font></html>";
			lblImporte.setText(importe);
			txtImporte.setEditable(true);
			
			//Marcelo Moyano
			// Retención Iva 05/02/2015
//			String importeIva = "<html>Importe Iva: <font color='red'>(*)</font></html>";
//			lblImporteIva.setText(importeIva);
//			lblImporteIva.setVisible(true);
//			txtImporteIva.setVisible(true);
			
			txtObservaciones.setEnabled(true);
			lblNroCtaBcoCliente.setText("Nro. Cta. Bco. Cliente:");
			txtNroCtaBcoCliente.setEditable(false);
			txtNroCtaBcoCliente.setBackground(new Color(212, 208, 200));
			lblBcoPromesa.setText("Bco. PROMESA:");
			cmbBancoPromesa.setEnabled(false);
			dateFechaDeposito.setEnabled(true);
			formaPago = "Retención";
		} else if (("VX").equals(idFormaPg)) { //PapeletaDepósito
			String referencia = "<html>Referencia: <font color='red'>(*)</font></html>";
			lblReferencia.setText(referencia);
			txtReferencia.setEditable(true);
			String importe = "<html>Importe: <font color='red'>(*)</font></html>";
			lblImporte.setText(importe);
			txtImporte.setEditable(true);
			
//			lblImporteIva.setVisible(false);
//			txtImporteIva.setVisible(false);
			txtObservaciones.setEnabled(true);
			lblNroCtaBcoCliente.setText("Nro. Cta. Bco. Cliente:");
			txtNroCtaBcoCliente.setBackground(new Color(212, 208, 200));
			txtNroCtaBcoCliente.setEditable(false);
			String bcoPromesa = "<html>Bco. PROMESA: <font color='red'>(*)</font></html>";
			lblBcoPromesa.setText(bcoPromesa);
			cmbBancoPromesa.setEnabled(true);
			dateFechaDeposito.setEnabled(true);
			formaPago = "Papeleta de Depósito";
		} else if (("VY").equals(idFormaPg)) { // Cheque Vista
			String referencia = "<html>Referencia: <font color='red'>(*)</font></html>";
			lblReferencia.setText(referencia);
			txtReferencia.setEditable(true);
			String importe = "<html>Importe: <font color='red'>(*)</font></html>";
			lblImporte.setText(importe);
			txtImporte.setEditable(true);
			
//			lblImporteIva.setVisible(false);
//			txtImporteIva.setVisible(false);
			txtObservaciones.setEnabled(true);
			String nroCtaBcoCli = "<html>Nro. Cta. Bco. Cliente: <font color='red'>(*)</font></html>";
			lblNroCtaBcoCliente.setText(nroCtaBcoCli);
			txtNroCtaBcoCliente.setBackground(Color.white);
			txtNroCtaBcoCliente.setEditable(true);
			lblBcoPromesa.setText("Bco. PROMESA:");
			cmbBancoPromesa.setEnabled(false);
			dateFechaDeposito.setEnabled(false);
			formaPago = "Cheque a la Vista";
		} else if (("VZ").equals(idFormaPg)) { // Efectivo
			lblReferencia.setText("Referencia:");
			txtReferencia.setEditable(true);
			String importe = "<html>Importe: <font color='red'>(*)</font></html>";
			lblImporte.setText(importe);
			txtImporte.setEditable(true);
			
//			lblImporteIva.setVisible(false);
//			txtImporteIva.setVisible(false);
			txtObservaciones.setEnabled(true);
			lblNroCtaBcoCliente.setText("Nro. Cta. Bco. Cliente:");
			txtNroCtaBcoCliente.setBackground(new Color(212, 208, 200));
			txtNroCtaBcoCliente.setEditable(false);
			lblBcoPromesa.setText("Bco. PROMESA:");
			cmbBancoPromesa.setEnabled(false);
			dateFechaDeposito.setEnabled(false);
			formaPago = "Efectivo";
		} else if (("").equals(idFormaPg)) { // Vacío
			txtReferencia.setEditable(false);
			lblImporte.setText("Importe:");
			txtImporte.setEditable(false);
			txtImporte.setBackground(new Color(212, 208, 200));
			
//			lblImporteIva.setVisible(false);
//			txtImporteIva.setVisible(false);
			txtObservaciones.setEnabled(false);
			lblNroCtaBcoCliente.setText("Nro. Cta. Bco. Cliente:");
			txtNroCtaBcoCliente.setBackground(new Color(212, 208, 200));
			txtNroCtaBcoCliente.setEditable(false);
			lblBcoPromesa.setText("Bco. PROMESA:");
			cmbBancoPromesa.setEnabled(false);
			dateFechaDeposito.setEnabled(false);
			formaPago = "";
		}
	}

	private void limpiarCampos() {
		idBancoCliente = "";
		formaPago = "";
		numTicket = "";
		descripcionBanco = "";
		txtBancoCliente.setText("");
		txtImporte.setBackground(Color.white);
		txtImporte.setText("");
		txtNroCtaBcoCliente.setText("");
		txtObservaciones.setText("");
		txtReferencia.setText("");
		cmbBancoPromesa.setSelectedIndex(0);
	}

	private void cmbBancoPromesaActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private JButton btnCancelar;
	private JButton btnGrabar;
	private JButton btnImprimir;
	//TODO
	private JButton btnAgenda;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbFormaPago;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbBancoPromesa;
	private JToolBar.Separator jSeparator1;
	private JToolBar.Separator jSeparator2;
	private JLabel lblBancoCliente;
	private JPanel lblCentral;
	private JLabel lblFormaPago;
	private JLabel lblImporte;
//	private JLabel lblImporteIva;
	private JLabel lblInfoGeneral;
	private JLabel lblNroCtaBcoCliente;
	private JLabel lblObservaciones;
	private JLabel lblReferencia;
	private JLabel lblTitulo;
	private JLabel lblBcoPromesa;
	private JPanel pnlDatos;
	private JPanel pnlDatosInput;
	private JPanel pnlMain;
	private JScrollPane scrObservaciones;
	private JToolBar toolBarAnticipo;
	private JTextField txtBancoCliente;
	private JTextField txtImporte;
//	private JTextField txtImporteIva;
	private JTextField txtNroCtaBcoCliente;
	private JTextField txtAnticipoSecuencia;
	private JTextPane txtObservaciones;
	private JTextField txtReferencia;
	private JDateChooser dateFechaDeposito;
	private JLabel lblFechaDeposito;
	// End of variables declaration
}