package com.promesa.internalFrame.cobranzas.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.Document;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.BancoPromesa;
import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.cobranzas.sql.impl.SqlBancoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlBancoPromesaImpl;
import com.promesa.cobranzas.sql.impl.SqlFormaPagoCobranzaImpl;
import com.promesa.internalFrame.cobranzas.IRegistroPagoCliente;
import com.promesa.main.Promesa;
import com.promesa.util.Constante;
import com.promesa.util.Util;
import com.promesa.util.ValidadorEntradasDeTexto;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class PanelElementosPagosRecibidos extends javax.swing.JPanel {

	
	private boolean seleccionado;
	private String codigoCliente;
	private Color colorFondoTxtImporte;
	private Color colorFondoTxtNumeroFactura;
	private Color colorFondoTxtReferencia;
	private Color colorFondoTxtNroCtaBcoCliente;
	private Color colorFondoLblDescripcionBancoCliente;
	private Color colorFondoDtcFechaVencimiento;
	private Color colorFondoDtcFechaDocumento;
	private static final Color fondoFilaSeleccionada = new Color(252, 221, 130);
	private static final Color fondoDeshabilitado = new java.awt.Color(213, 226, 238);
	private SqlFormaPagoCobranzaImpl sqlFormaPagoCobranza;
	private SqlBancoPromesaImpl sqlBancoPromesa;
	private String idBancoCliente;
	private PagoRecibido pagoRecibido;
	private IRegistroPagoCliente registroPagoCliente;
	private List<FormaPago> listaFormaPago;
	private List<BancoPromesa> listaBancoPromesa;
	private Document doc;
	private int secuenciaFormaPago;

	/** Creates new form PanelElementosPagosRecibidos */
//	public PanelElementosPagosRecibidos(IRegistroPagoCliente registroPagoCliente, String codigoCliente) {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelElementosPagosRecibidos(IRegistroPagoCliente registroPagoCliente, String codigoCliente, int secuencia) {
		secuenciaFormaPago = secuencia;
		initComponents();
		doc = txtReferencia.getDocument();
		this.registroPagoCliente = registroPagoCliente;
		this.codigoCliente = codigoCliente;
		lblDescripcionBancoCliente.setBackground(fondoDeshabilitado);
		dtcFechaDocumento.setDateFormatString("dd.MM.yyyy");
		dtcFechaVencimiento.setDateFormatString("dd.MM.yyyy");
		lblDescripcionBancoCliente.setFont(new Font("Tahoma", 0, 8));
		colorFondoTxtImporte = txtImporte.getBackground();
		colorFondoTxtNumeroFactura = txtNumeroFactura.getBackground();
		colorFondoTxtReferencia = txtReferencia.getBackground();
		colorFondoTxtNroCtaBcoCliente = txtNroCtaBcoCliente.getBackground();
		colorFondoLblDescripcionBancoCliente = lblDescripcionBancoCliente.getBackground();
		colorFondoDtcFechaVencimiento = Color.white;
		colorFondoDtcFechaDocumento = Color.white;
		Border borde = BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(128, 128, 128));
		txtImporte.setBorder(borde);
		txtNumeroFactura.setBorder(borde);
		txtNumeroFactura.selectAll();
		txtReferencia.setBorder(borde);
		txtReferencia.selectAll();
		txtNroCtaBcoCliente.setBorder(borde);
		Border bordeLabel = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(128, 128, 128));
		lblDescripcionBancoCliente.setBorder(bordeLabel);
		sqlFormaPagoCobranza = new SqlFormaPagoCobranzaImpl();
		sqlBancoPromesa = new SqlBancoPromesaImpl();
		listaFormaPago = sqlFormaPagoCobranza.obtenerListaFormaPagoCobranza();
		listaBancoPromesa = sqlBancoPromesa.obtenerListaBancoPromesa();// original 
		cmbBancoPromesa.setEditable(false);
		cmbBancoPromesa.setEnabled(false);
		cmbBancoPromesa.setBackground(fondoDeshabilitado);
		cmbFormaPago.setModel(new DefaultComboBoxModel(listaFormaPago.toArray()));
		cmbBancoPromesa.setModel(new DefaultComboBoxModel(listaBancoPromesa.toArray()));// original 
		AutoCompleteDecorator.decorate(cmbFormaPago);
		AutoCompleteDecorator.decorate(cmbBancoPromesa);// original 
		cmbFormaPago.setRenderer(new MyComboBoxRenderer(listaFormaPago));
		cmbBancoPromesa.setRenderer(new MyComboBoxRenderer(listaBancoPromesa));// original 
		ValidadorEntradasDeTexto validador = new ValidadorEntradasDeTexto(10, Constante.DECIMALES);
		txtImporte.setDocument(validador);
		final String cc = codigoCliente;
		txtImporte.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					agregarImporteEnContenedor();
					txtImporte.setText(Util.formatearNumero(txtImporte.getText()));
				}
			}
		});
		txtNroCtaBcoCliente.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
			}

			public void keyReleased(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER  ) {
					SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
					try {
						idBancoCliente = "";
						lblDescripcionBancoCliente.setText("");
						List<BancoCliente> listaBancoCliente = sqlBancoClienteImpl.obtenerListaBancoCliente(cc);
						boolean bandera = false;
						if (listaBancoCliente.size() > 0) {
							for (BancoCliente bancoCliente : listaBancoCliente) {
								if (bancoCliente.getNroCtaBcoCliente().trim().equals(txtNroCtaBcoCliente.getText().trim())) {
									idBancoCliente = bancoCliente.getIdBancoCliente();
									lblDescripcionBancoCliente.setText(bancoCliente.getDescripcionBancoCliente());
									bandera = true;
									break;
								}
							}
							if (bandera) {
								txtNroCtaBcoCliente.setBackground(Color.white);
							} else {
								txtNroCtaBcoCliente.setBackground(new Color(254, 205, 215));
							}
						} else {
							JOptionPane.showMessageDialog(Promesa.getInstance(), "No existe ning�n n�mero de cuenta del cliente asociado a un banco.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						lblDescripcionBancoCliente.setToolTipText(lblDescripcionBancoCliente.getText());
					} catch (Exception e) {
						txtNroCtaBcoCliente.setBackground(new Color(254, 205, 215));
					}
				}
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		});
		limpiarCampos();
	}

	public void actualizarDatos(PagoRecibido pagoRecibido) {
		this.pagoRecibido = pagoRecibido;
		idBancoCliente = pagoRecibido.getBancoCliente().getIdBancoCliente();
		for (FormaPago fp : listaFormaPago) {
			if (fp.getIdFormaPago().compareTo(pagoRecibido.getFormaPago().getIdFormaPago()) == 0) {
				cmbFormaPago.setSelectedItem(fp);
			}
		}
		for (BancoPromesa bp : listaBancoPromesa) {
			if (bp.getIdBancoPromesa().compareTo(pagoRecibido.getBancoPromesa().getIdBancoPromesa()) == 0) {
				cmbBancoPromesa.setSelectedItem(bp);
			}
		}
		txtImporte.setText("" + pagoRecibido.getImporte());
		txtNumeroFactura.setText(pagoRecibido.getNumeroFactura());
		txtReferencia.setText(pagoRecibido.getReferencia());
		txtNroCtaBcoCliente.setText(pagoRecibido.getBancoCliente().getNroCtaBcoCliente());
		lblDescripcionBancoCliente.setText(pagoRecibido.getBancoCliente().getDescripcionBancoCliente());
		dtcFechaVencimiento.setDate(Util.convierteCadenaYYYYMMDDAFecha(pagoRecibido.getFechaVencimiento()));
		dtcFechaDocumento.setDate(Util.convierteCadenaYYYYMMDDAFecha(pagoRecibido.getFechaDocumento()));
	}

	public BigDecimal obtenerImporte() {
		BigDecimal valorImporteAgregado = new BigDecimal(0);
		try {
			valorImporteAgregado = new BigDecimal(txtImporte.getText());
		} catch (Exception e) {
			valorImporteAgregado = new BigDecimal(0);
		}
		txtImporte.setText("" + valorImporteAgregado);
		return valorImporteAgregado;
	}

	protected void agregarImporteEnContenedor() {
		registroPagoCliente.recalcularTodo();
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public PagoRecibido obtenerPagoRecibido() {
		PagoRecibido pr = new PagoRecibido();
		pr.setFormaPago((FormaPago) cmbFormaPago.getSelectedItem());
		Double importe = 0d;
		try {
			importe = Double.parseDouble(txtImporte.getText());
		} catch (Exception e) {
			importe = 0d;
		}
		pr.setImporte(importe);
		pr.setNumeroFactura(txtNumeroFactura.getText());
		pr.setReferencia(txtReferencia.getText());
		pr.setOrdenSecuencia(tbtnSeleccionarFila.getText());//	Marcelo Moyano Orden de Secuencia para Mostrar el Error
		BancoCliente bancoCliente = new BancoCliente();
		BancoCliente bc = null;
		bancoCliente.setIdBancoCliente(idBancoCliente);
		if(pagoRecibido != null) {
			bc = pagoRecibido.getBancoCliente();
		}
		if(bc != null) {
			bancoCliente.setIdBancoCliente(bc.getIdBancoCliente());
		}
		bancoCliente.setNroCtaBcoCliente(txtNroCtaBcoCliente.getText());
		bancoCliente.setCodigoCliente(codigoCliente);
		bancoCliente.setDescripcionBancoCliente(lblDescripcionBancoCliente.getText());
		pr.setBancoCliente(bancoCliente);
		pr.setFechaVencimiento(dtcFechaVencimiento.getDate() != null ? Util.convierteFechaAFormatoYYYYMMdd(dtcFechaVencimiento.getDate()) : "");
		String fechaActual = Util.convierteFechaAFormatoYYYYMMdd(new Date());
		String fecha = dtcFechaDocumento.getDate() != null ? Util.convierteFechaAFormatoYYYYMMdd(dtcFechaDocumento.getDate()) : fechaActual;
		pr.setFechaDocumento(fecha);
		
		BancoPromesa bancoPromesa = new BancoPromesa();
		if (cmbBancoPromesa.getSelectedItem() != null) {
			bancoPromesa = (BancoPromesa) cmbBancoPromesa.getSelectedItem();
		} else {
			bancoPromesa.setIdBancoPromesa("");
			bancoPromesa.setDescripcionBancoPromesa("");
		}
		pr.setBancoPromesa(bancoPromesa);
		return pr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		tbtnSeleccionarFila = new javax.swing.JToggleButton();
		pnlCeldas = new javax.swing.JPanel();
		cmbFormaPago = new javax.swing.JComboBox();
		txtImporte = new javax.swing.JTextField();
		txtNumeroFactura = new javax.swing.JTextField();
		txtReferencia = new javax.swing.JTextField();
		txtNroCtaBcoCliente = new javax.swing.JTextField();
		lblDescripcionBancoCliente = new javax.swing.JLabel();
		dtcFechaVencimiento = new com.toedter.calendar.JDateChooser();
		dtcFechaDocumento = new com.toedter.calendar.JDateChooser();
		cmbBancoPromesa = new javax.swing.JComboBox();

		setBackground(new java.awt.Color(179, 179, 179));
		setMaximumSize(new java.awt.Dimension(2147483647, 22));
		setMinimumSize(new java.awt.Dimension(428, 22));
		setPreferredSize(new java.awt.Dimension(400, 22));
		setLayout(new java.awt.BorderLayout());

		tbtnSeleccionarFila.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		tbtnSeleccionarFila.setContentAreaFilled(false);
		tbtnSeleccionarFila.setMaximumSize(new java.awt.Dimension(20, 25));
		tbtnSeleccionarFila.setMinimumSize(new java.awt.Dimension(20, 25));
		tbtnSeleccionarFila.setPreferredSize(new java.awt.Dimension(20, 25));
		tbtnSeleccionarFila.setText(String.valueOf(secuenciaFormaPago));
		tbtnSeleccionarFila.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tbtnSeleccionarFilaActionPerformed(evt);
			}
		});
		add(tbtnSeleccionarFila, java.awt.BorderLayout.LINE_START);

		pnlCeldas.setOpaque(false);
		pnlCeldas.setLayout(new java.awt.GridLayout(1, 8));

		cmbFormaPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		cmbFormaPago.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbFormaPagoActionPerformed(evt);
			}
		});
		cmbFormaPago.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusLost(java.awt.event.FocusEvent evt) {
				FormaPago formaPagoSeleccionado = (FormaPago) cmbFormaPago.getSelectedItem();
				if(formaPagoSeleccionado == null)
					txtImporte.selectAll();
				if(evt.getSource() == cmbFormaPago)
					txtImporte.selectAll();
            }
		});
		
		pnlCeldas.add(cmbFormaPago);

		txtImporte.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtImporte.setToolTipText("Importe");
		txtImporte.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
		    @SuppressWarnings("static-access")
			public void keyPressed(java.awt.event.KeyEvent evt) {
		    	FormaPago formaPagoSeleccionado = (FormaPago) cmbFormaPago.getSelectedItem();
		    	if (formaPagoSeleccionado == null) {
		    		return;
		    	}
		    	if (("VT").equals(formaPagoSeleccionado.getIdFormaPago())) {
		    		if( evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_TAB){
		    			agregarImporteEnContenedor();
		    			txtImporte.setText(Util.formatearNumero(txtImporte.getText()));
		    			txtNumeroFactura.requestFocus();
		    			txtNumeroFactura.selectAll();
			        }
		    	}else {
		    		if(evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_TAB){
		    			agregarImporteEnContenedor();
		    			txtImporte.setText(Util.formatearNumero(txtImporte.getText()));
		    			txtReferencia.requestFocus();
			        	txtReferencia.selectAll();
			        }
		    	}   
		    }
		});
		
		txtImporte.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusLost(java.awt.event.FocusEvent evt) {
				FormaPago formaPagoSeleccionado = (FormaPago) cmbFormaPago.getSelectedItem();
				if (formaPagoSeleccionado == null) {
		    		return;
		    	}
				if (("VT").equals(formaPagoSeleccionado.getIdFormaPago())) {
		    		if( evt.getSource() == txtImporte){
		    			agregarImporteEnContenedor();
		    			txtImporte.setText(Util.formatearNumero(txtImporte.getText()));
		    			txtNumeroFactura.selectAll();
			        }
		    	}else {
		    		if(evt.getSource() == txtImporte ){
		    			agregarImporteEnContenedor();
		    			txtImporte.setText(Util.formatearNumero(txtImporte.getText()));;
			        	txtReferencia.selectAll();
			        }
		    	} 
            }
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtImporte){
					txtImporte.selectAll();
				}
			}
		});
		
		pnlCeldas.add(txtImporte);

		txtNumeroFactura.setToolTipText("N�mero de factura");
		txtNumeroFactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);//	Marcelo Moyano
		txtNumeroFactura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		txtNumeroFactura.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusLost(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtNumeroFactura){
		        	txtReferencia.selectAll();
				}
			}
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtNumeroFactura){
					txtNumeroFactura.selectAll();
				}
				if(!txtNumeroFactura.isEditable()){
					txtReferencia.requestFocus();
					txtReferencia.selectAll();
				}
			}
		});
		pnlCeldas.add(txtNumeroFactura);

		txtReferencia.setToolTipText("Referencia");
		txtReferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);//	Marcelo Moyano
		txtReferencia.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		txtReferencia.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusLost(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtNroCtaBcoCliente && txtNroCtaBcoCliente.isEditable()){
					txtNroCtaBcoCliente.selectAll();
				}
				
			}
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtReferencia){
					txtReferencia.selectAll();
				}
			}
		});
		pnlCeldas.add(txtReferencia);

		txtNroCtaBcoCliente.setToolTipText("N�mero de cuenta de banco cliente");
		txtNroCtaBcoCliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT); // Marcelo Moyano
		txtNroCtaBcoCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		txtNroCtaBcoCliente.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtNroCtaBcoCliente){
					txtNroCtaBcoCliente.selectAll();
				}
				if(!txtNroCtaBcoCliente.isEditable() && lblDescripcionBancoCliente.isOpaque() && dtcFechaVencimiento.isEnabled()){
					dtcFechaVencimiento.getDateEditor().getUiComponent().requestFocus();
				}
				if(!txtNroCtaBcoCliente.isEditable() && lblDescripcionBancoCliente.isOpaque() && !dtcFechaVencimiento.isEnabled()){
					dtcFechaDocumento.getDateEditor().getUiComponent().requestFocus();
				}
			}
			
			public void focusLost(java.awt.event.FocusEvent evt){
				List<BancoCliente> BcosClientes = null;
				if(evt.getSource() == txtNroCtaBcoCliente && txtNroCtaBcoCliente.isEditable()){
					String nroCta = txtNroCtaBcoCliente.getText().trim();
					SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
					Promesa instance = Promesa.getInstance();
					try {
						idBancoCliente = "";
						lblDescripcionBancoCliente.setText("");
						BcosClientes = sqlBancoClienteImpl.obtenerListaBancoCliente(codigoCliente);
						boolean bandera = false;
						if (BcosClientes.size() > 0) {
							for (BancoCliente bcoCli : BcosClientes) {
								if (bcoCli.getNroCtaBcoCliente().trim().equals(nroCta)) {
									idBancoCliente = bcoCli.getIdBancoCliente();
									lblDescripcionBancoCliente.setText(bcoCli.getDescripcionBancoCliente());
									bandera = true;
									break;
								}
							}
							if (bandera) {
								txtNroCtaBcoCliente.setBackground(Color.white);
							} else {
								txtNroCtaBcoCliente.requestFocus();
								txtNroCtaBcoCliente.setBackground(new Color(254, 205, 215));
							}
						} else {
							txtNroCtaBcoCliente.requestFocus();
							String msg = "No existe ning�n n�mero de cuenta del cliente asociado a un banco.";
							JOptionPane.showMessageDialog(instance, msg, "Error", JOptionPane.ERROR_MESSAGE);
						}
						lblDescripcionBancoCliente.setToolTipText(lblDescripcionBancoCliente.getText());
					} catch (Exception ex) {
						txtNroCtaBcoCliente.setBackground(new Color(254, 205, 215));
					}
				}
			}
		});
		pnlCeldas.add(txtNroCtaBcoCliente);

		lblDescripcionBancoCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		lblDescripcionBancoCliente.setOpaque(true);
		pnlCeldas.add(lblDescripcionBancoCliente);

		dtcFechaVencimiento.setToolTipText("Fecha de vencimiento");
		pnlCeldas.add(dtcFechaVencimiento);

		dtcFechaDocumento.setToolTipText("Fecha de documento");
		dtcFechaDocumento.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				Date fecha = dtcFechaDocumento.getDate();
				System.out.println("Fecha documento: " + fecha);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		pnlCeldas.add(dtcFechaDocumento);

		cmbBancoPromesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		cmbBancoPromesa.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbBancoPromesaActionPerformed(evt);
			}
		});
		pnlCeldas.add(cmbBancoPromesa);

		add(pnlCeldas, java.awt.BorderLayout.CENTER);
	}// </editor-fold>

	public int getSecuenciaFormaPago() {
		return secuenciaFormaPago;
	}

	public void setSecuenciaFormaPago(int secuenciaFormaPago) {
		this.secuenciaFormaPago = secuenciaFormaPago;
		this.tbtnSeleccionarFila.setText(String.valueOf(this.secuenciaFormaPago));
	}

	private void establecerColorDeFondoCalendario(JDateChooser selectorFecha, Color fondo) {
		Component[] lc = selectorFecha.getComponents();
		for (int i = 0; i < lc.length; i++) {
			Object object = lc[i];
			if (object instanceof JTextField) {
				((JTextField) object).setBackground(fondo);
			}
		}
	}

	private void tbtnSeleccionarFilaActionPerformed(java.awt.event.ActionEvent evt) {
		seleccionado = tbtnSeleccionarFila.isSelected();
		if (seleccionado) {
			txtImporte.setBackground(fondoFilaSeleccionada);
			txtNumeroFactura.setBackground(fondoFilaSeleccionada);
			txtReferencia.setBackground(fondoFilaSeleccionada);
			txtNroCtaBcoCliente.setBackground(fondoFilaSeleccionada);
			lblDescripcionBancoCliente.setBackground(fondoFilaSeleccionada);
			establecerColorDeFondoCalendario(dtcFechaDocumento, fondoFilaSeleccionada);
			establecerColorDeFondoCalendario(dtcFechaVencimiento, fondoFilaSeleccionada);
		} else {
			txtImporte.setBackground(colorFondoTxtImporte);
			txtNumeroFactura.setBackground(colorFondoTxtNumeroFactura);
			txtReferencia.setBackground(colorFondoTxtReferencia);
			txtNroCtaBcoCliente.setBackground(colorFondoTxtNroCtaBcoCliente);
			lblDescripcionBancoCliente.setBackground(colorFondoLblDescripcionBancoCliente);
			establecerColorDeFondoCalendario(dtcFechaDocumento, colorFondoDtcFechaDocumento);
			establecerColorDeFondoCalendario(dtcFechaVencimiento, colorFondoDtcFechaVencimiento);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cmbFormaPagoActionPerformed(java.awt.event.ActionEvent evt) {
		limpiarCampos();
		txtReferencia.setDocument(doc);
		FormaPago formaPago = (FormaPago) cmbFormaPago.getSelectedItem();
		if (formaPago == null) {
			return;
		}
		String idFormaPago = formaPago.getIdFormaPago();
		if (("VC").equals(idFormaPago)) { // Cheque post Fechado
			txtImporte.setEditable(true);
			txtImporte.selectAll(); //	Marcelo Moyano
			txtReferencia.setEditable(true);
			txtNroCtaBcoCliente.setEditable(true);
			dtcFechaVencimiento.setEnabled(true);
			txtImporte.setBackground(Color.white);
			txtReferencia.setBackground(Color.white);
			txtNroCtaBcoCliente.setBackground(Color.white);
			establecerColorDeFondoCalendario(dtcFechaVencimiento, Color.white);
			cmbBancoPromesa.setEditable(false);		
		} else if (("VP").equals(idFormaPago)) { // Pagar�
			txtImporte.setEditable(true);
			txtImporte.selectAll(); //	Marcelo Moyano
			txtReferencia.setEditable(true);
			dtcFechaVencimiento.setEnabled(true);
			establecerColorDeFondoCalendario(dtcFechaVencimiento, Color.white);
			txtImporte.setBackground(Color.white);
			txtReferencia.setBackground(Color.white);
			cmbBancoPromesa.setEditable(false);
			establecerColorDeFondoCalendario(dtcFechaVencimiento, Color.white);
		} else if (("VT").equals(idFormaPago) || ("VI").equals(idFormaPago)) { // Retenci�n
			txtImporte.setEditable(true);
			txtImporte.selectAll(); //	Marcelo Moyano
			txtNumeroFactura.setEditable(true);
			txtReferencia.setEditable(true);
			txtImporte.setBackground(Color.white);
			txtNumeroFactura.setBackground(Color.white);
			txtReferencia.setBackground(Color.white);
			dtcFechaDocumento.setEnabled(true);// Marcelo Moyano
			establecerColorDeFondoCalendario(dtcFechaDocumento, Color.white);// Marcelo Moyano
			ValidadorEntradasDeTexto validador = new ValidadorEntradasDeTexto(18, Constante.VALIDACION_RETENCIONES);
			txtReferencia.setDocument(validador);
			cmbBancoPromesa.setEditable(false);
		} else if (("VX").equals(idFormaPago) || ("VO").equals(idFormaPago)) { // Papeleta de Dep�sito
			txtImporte.setEditable(true);
			txtImporte.selectAll(); //	Marcelo Moyano
			txtReferencia.setEditable(true);
			dtcFechaDocumento.setEnabled(true);
			establecerColorDeFondoCalendario(dtcFechaDocumento, Color.white);
			
			if(("VO").equals(idFormaPago)){
				// Obtener Los Bancos Recaudo de Tipo S
				listaBancoPromesa = sqlBancoPromesa.obtenerListaBancoPromesaRecaudo();
				cmbBancoPromesa.setModel(new DefaultComboBoxModel(listaBancoPromesa.toArray()));
				AutoCompleteDecorator.decorate(cmbBancoPromesa);
				cmbBancoPromesa.setRenderer(new MyComboBoxRenderer(listaBancoPromesa));// original 
			}else if(("VX").equals(idFormaPago)){
				listaBancoPromesa = sqlBancoPromesa.obtenerListaBancoPromesa();
				cmbBancoPromesa.setModel(new DefaultComboBoxModel(listaBancoPromesa.toArray()));
				AutoCompleteDecorator.decorate(cmbBancoPromesa);
				cmbBancoPromesa.setRenderer(new MyComboBoxRenderer(listaBancoPromesa));
			}
			//	Marcelo Moyano 02/04/2013 - 15:30
			cmbBancoPromesa.setEnabled(true);
			cmbBancoPromesa.setBackground(Color.white);
			txtImporte.setBackground(Color.white);
			txtReferencia.setBackground(Color.white);
			cmbBancoPromesa.setEditable(true);
		} else if (("VY").equals(idFormaPago)) { // Cheque a la Vista
			txtImporte.setEditable(true);
			txtImporte.selectAll(); //	Marcelo Moyano
			txtReferencia.setEditable(true);
			txtNroCtaBcoCliente.setEditable(true);
			txtImporte.setBackground(Color.white);
			txtReferencia.setBackground(Color.white);
			txtNroCtaBcoCliente.setBackground(Color.white);
			listaBancoPromesa = sqlBancoPromesa.obtenerListaBancoPromesaDepApps();
			cmbBancoPromesa.setModel(new DefaultComboBoxModel(listaBancoPromesa.toArray()));
			AutoCompleteDecorator.decorate(cmbBancoPromesa);
			cmbBancoPromesa.setRenderer(new MyComboBoxRenderer(listaBancoPromesa));// original 
			cmbBancoPromesa.setEnabled(true);
			cmbBancoPromesa.setBackground(Color.white);
			txtImporte.setBackground(Color.white);
			txtReferencia.setBackground(Color.white);
			cmbBancoPromesa.setEditable(true);
		} else if (("VZ").equals(idFormaPago)) { // Efectivo
			txtImporte.setEditable(true);
			txtImporte.selectAll(); //	Marcelo Moyano
			txtReferencia.setEditable(true);
			txtReferencia.setText("EFECTIVO");
			txtReferencia.setBackground(Color.white);
			txtImporte.setBackground(Color.white);
			cmbBancoPromesa.setEditable(false);
			cmbBancoPromesa.setEnabled(false);
			cmbBancoPromesa.setBackground(lblDescripcionBancoCliente.getBackground());
		}
	}

	private void limpiarCampos() {
		idBancoCliente = "";
		txtImporte.setText("0.0");
		txtNumeroFactura.setText("");
		txtReferencia.setText("");
		txtNroCtaBcoCliente.setText("");
		lblDescripcionBancoCliente.setText("");
		dtcFechaVencimiento.setDate(null);
		dtcFechaDocumento.setDate(null);
		cmbBancoPromesa.setSelectedIndex(0);

		txtImporte.setEditable(false);
		txtNumeroFactura.setEditable(false);
		txtReferencia.setEditable(false);
		txtNroCtaBcoCliente.setEditable(false);
		dtcFechaVencimiento.setEnabled(false);
		dtcFechaDocumento.setEnabled(false);
		cmbBancoPromesa.setEnabled(false);

		txtImporte.setBackground(fondoDeshabilitado);
		txtNumeroFactura.setBackground(fondoDeshabilitado);
		txtReferencia.setBackground(fondoDeshabilitado);
		txtNroCtaBcoCliente.setBackground(fondoDeshabilitado);
		dtcFechaVencimiento.setBackground(fondoDeshabilitado);
		dtcFechaDocumento.setBackground(fondoDeshabilitado);
		cmbBancoPromesa.setBackground(fondoDeshabilitado);
		establecerColorDeFondoCalendario(dtcFechaVencimiento, fondoDeshabilitado);
		establecerColorDeFondoCalendario(dtcFechaDocumento, fondoDeshabilitado);
	}

	private void cmbBancoPromesaActionPerformed(java.awt.event.ActionEvent evt) {
	}
	
	// Variables declaration - do not modify
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbBancoPromesa;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbFormaPago;
	private com.toedter.calendar.JDateChooser dtcFechaDocumento;
	private com.toedter.calendar.JDateChooser dtcFechaVencimiento;
	private javax.swing.JLabel lblDescripcionBancoCliente;
	private javax.swing.JPanel pnlCeldas;
	private javax.swing.JToggleButton tbtnSeleccionarFila;
	private javax.swing.JTextField txtImporte;
	private javax.swing.JTextField txtNroCtaBcoCliente;
	private javax.swing.JTextField txtNumeroFactura;
	private javax.swing.JTextField txtReferencia;
	// End of variables declaration
}

@SuppressWarnings("serial")
class MyComboBoxRenderer extends BasicComboBoxRenderer {

	private Object[] tooltips;

	@SuppressWarnings("rawtypes")
	public MyComboBoxRenderer(List tooltips) {
		super();
		this.tooltips = tooltips.toArray();
	}

	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
			if (-1 < index) {
				list.setToolTipText(tooltips[index].toString());
			}
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setFont(list.getFont());
		setText((value == null) ? "" : value.toString());
		return this;
	}
}