package com.promesa.dialogo.pedidos;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

//import com.promesa.bean.BeanDato;
import com.promesa.internalFrame.pedidos.IPedidos;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.internalFrame.pedidos.custom.ModeloTablaFacturaDetalle;
import com.promesa.internalFrame.pedidos.custom.ModeloTablaFacturas;
import com.promesa.internalFrame.pedidos.custom.RenderizadorTablaFactura;
import com.promesa.internalFrame.pedidos.custom.RenderizadorTablaFacturaDetalle;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanFactura;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.sap.SPedidos;
import com.promesa.util.Constante;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class DHistoricoCliente extends javax.swing.JDialog {

	private String codCli = null; // CÓDIGO DE CLIENTE
	private String nomCli = null; // NOMBRE DEL CLIENTE
	private String codMat = null; // CÓDIGO DEL MATERIAL
	private JLabel mensaje;
	SPedidos objSAP;
	List<BeanFactura> facturas;
	List<BeanMaterial> materiales;
	private static final int maximaCantidadRegistros = 100;
	List<BeanFactura> detalle;
	private IPedidos contenedor;
	private ModeloTablaFacturas mdlTblFacturas;
	private ModeloTablaFacturaDetalle mdlTblFacturaDetalle;

	private static final long serialVersionUID = 1L;

	public DHistoricoCliente(IPedidos contenedor, String codCli, String nomCli, String codMat) {
		super(Promesa.getInstance(), false);
		this.contenedor = contenedor;
		this.codCli = codCli;
		this.nomCli = nomCli;
		this.codMat = codMat;
		initComponents();
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		this.setLocationRelativeTo(null);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jLabel4.setText(codCli);
		jLabel7.setText(nomCli);
		mdlTblFacturas = new ModeloTablaFacturas();
		mdlTblFacturaDetalle = new ModeloTablaFacturaDetalle();
		jTable1.setModel(mdlTblFacturas);
		jTable2.setModel(mdlTblFacturaDetalle);
		Util.addEscapeKey(this);
		setAnchoColumnas();
		JTableHeader tableHeader = jTable1.getTableHeader();
		((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		jTable1.setRowHeight(Constante.ALTO_COLUMNAS);
		
		JTableHeader tableHeader2 = jTable2.getTableHeader();
		((DefaultTableCellRenderer) tableHeader2.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		jTable2.setRowHeight(Constante.ALTO_COLUMNAS);
		jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		RenderizadorTablaFactura render = new RenderizadorTablaFactura();
		RenderizadorTablaFacturaDetalle render2 = new RenderizadorTablaFacturaDetalle();
		jTable1.setDefaultRenderer(String.class, render);
		jTable2.setDefaultRenderer(String.class, render2);
		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblFacturasMouseClicked(evt);
			}
		});
		jLabel22.setText(codCli);
		cargaFacturas();
		JTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				actualizarCantidadRegistros(evt);
			}
		});
		JTextField1.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == JTextField1)
					JTextField1.selectAll();
			}
		});
	}

	private void actualizarCantidadRegistros(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			mdlTblFacturas.limpiar();
			llenarTablaFactura();
		}
	}

	private void cargaFacturas() {
		try {
			objSAP = new SPedidos();
			//	Marcelo Moyano
			facturas = objSAP.facturas(Util.completarCeros(10, codCli));
			llenarTablaFactura();
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.mostrarError(e.getMessage());
		}
	}
	
	private void llenarTablaFactura() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void run() {
				if (facturas != null && facturas.size() > 0) {
					materiales = new ArrayList<BeanMaterial>();
					int cantidadRegistros = maximaCantidadRegistros;
					try {
						cantidadRegistros = Integer.parseInt(JTextField1.getText());
					} catch (Exception e) {
						cantidadRegistros = maximaCantidadRegistros;
					}
					int i = 0;
					for (BeanFactura f : facturas) {
						if (i < cantidadRegistros) {
							mdlTblFacturas.agregarFactura(f);
							if (!estaAgregadoMaterial(f.getMaterial().getIdMaterial())) {
								BeanMaterial bm = new BeanMaterial();
								bm.setIdMaterial(f.getMaterial().getIdMaterial());
								bm.setDescripcion(f.getMaterial().getDescripcion());
								materiales.add(bm);
							}
						}
						i++;
					}
					materiales.add(0, new BeanMaterial());
					if (materiales != null) {
						Collections.sort(materiales);
					}
					cmbDesMat.setModel(new DefaultComboBoxModel(materiales.toArray()));
					if (codMat != null && !codMat.isEmpty()) {
						for (int j = 0; j < materiales.size(); j++) {
							BeanMaterial material = (BeanMaterial) cmbDesMat.getItemAt(j);
							if (material.getIdMaterial().compareTo(codMat) == 0) {
								cmbDesMat.setSelectedIndex(j);
								break;
							}
						}
					}
					Util.setAnchoColumnas(jTable1);
				} else {
					mdlTblFacturas.limpiar();
				}
				JTextField1.setText("" + mdlTblFacturas.obtenerCantidadFacturas());
			}
		});
	}

	private boolean estaAgregadoMaterial(String material) {
		for (BeanMaterial mt : materiales) {
			if (mt.getIdMaterial().compareTo(material) == 0) {
				return true;
			}
		}
		return false;
	}

	protected void jButton1ActionPerformed(ActionEvent evt) {
		int numeroMaterialesSeleccionados[] = jTable2.getSelectedRows();
		SqlMaterialImpl sql = new SqlMaterialImpl();
		boolean fallo = false;
		if (numeroMaterialesSeleccionados.length == 0) {
			int numeroMateriales = jTable2.getRowCount();
			for (int i = 0; i < numeroMateriales; i++) {
				String codigo = jTable2.getValueAt(i, 0).toString();
				String cantidad = "";
				try {
					cantidad = "" + (int) Double.parseDouble(jTable2.getValueAt(i, 4).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					cantidad = "0";
					fallo = true;
				}
				String cantidadConfirmada = "0";
				String porcentaDescuento = "0";
				String denominacion = jTable2.getValueAt(i, 1).toString();
				String precioNeto = "0.0";
				String valorNeto = "0.0";
				String tipo = sql.getTipoMaterial(codigo);
				Item it = new Item();
				it.setCodigo(codigo);
				it.setCantidad(cantidad);
				it.setCantidadConfirmada(cantidadConfirmada);
				it.setUnidad(sql.getUnidadMaterial(codigo));
				it.setPorcentajeDescuento(porcentaDescuento);
				it.setDenominacion(denominacion);
				Double valor = 0d;
				try {
					valor = Double.parseDouble(precioNeto);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					valor = 0d;
					fallo = true;
				}
				it.setPrecioNeto(valor);
				try {
					valor = Double.parseDouble(valorNeto);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					valor = 0d;
					fallo = true;
				}
				it.setValorNeto(valor);
				it.setTipoMaterial(tipo);
				it.setEditable(true);
				contenedor.agregarMaterial(it, -2);
				if (!fallo) {
					mensaje.setText("<html><font color='green'>El pedido se ha pasado correctamente</font></html>");
				} else {
					mensaje.setText("");
				}
			}
		} else {
			for (int i : numeroMaterialesSeleccionados) {
				String codigo = jTable2.getValueAt(i, 0).toString();
				String cantidad = "";
				try {
					cantidad = "" + (int) Double.parseDouble(jTable2.getValueAt(i, 4).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					cantidad = "0";
					fallo = true;
				}
				String cantidadConfirmada = "0";
				String porcentaDescuento = "0";
				String denominacion = jTable2.getValueAt(i, 1).toString();
				String precioNeto = "0.0";
				String valorNeto = "0.0";
				String tipo = sql.getTipoMaterial(codigo);
				Item it = new Item();
				it.setCodigo(codigo);
				it.setCantidad(cantidad);
				it.setCantidadConfirmada(cantidadConfirmada);
				it.setUnidad(sql.getUnidadMaterial(codigo));
				it.setPorcentajeDescuento(porcentaDescuento);
				it.setDenominacion(denominacion);
				Double valor = 0d;
				try {
					valor = Double.parseDouble(precioNeto);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					valor = 0d;
					fallo = true;
				}
				it.setPrecioNeto(valor);
				try {
					valor = Double.parseDouble(valorNeto);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					valor = 0d;
					fallo = true;
				}
				it.setValorNeto(valor);
				it.setTipoMaterial(tipo);
				it.setEditable(true);
				contenedor.agregarMaterial(it, -2);
				if (!fallo) {
					mensaje.setText("<html><font color='green'>El pedido se ha pasado correctamente</font></html>");
				} else {
					mensaje.setText("");
				}
			}
		}
		contenedor.actualizarPosiciones();
	}

	private void tblFacturasMouseClicked(java.awt.event.MouseEvent evt) {
		final int fila = jTable1.rowAtPoint(evt.getPoint());
		if (evt.getClickCount() == 2) {
			BeanFactura f = mdlTblFacturas.obtenerFactura(fila);
			if (f != null) {
				jLabel23.setText(f.getStrNFactura());
				jLabel29.setText(f.getStrFFactura());
				detalle = new ArrayList<BeanFactura>();
				Double suma = 0d;
				for (BeanFactura fact : facturas) {
					if (fact.getStrNFactura().compareTo(f.getStrNFactura()) == 0) {
						suma += Double.parseDouble(fact.getStrVNeto());
						detalle.add(fact);
					}
				}
				llenarDetallesFactura();
				jLabel30.setText(Util.formatearNumero((suma)));
			}
		}
	}

	private void llenarDetallesFactura() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				mdlTblFacturaDetalle.limpiar();
				if (detalle != null && detalle.size() > 0) {
					for (BeanFactura f : detalle) {
						mdlTblFacturaDetalle.agregarFactura(f);
					}
					Util.setAnchoColumnas(jTable2);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void initComponents() {
		jPanel1 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		lblCodigoMaterial = new javax.swing.JLabel();
		lblPrecioMaterial = new javax.swing.JLabel();
		jPanel7 = new javax.swing.JPanel();
		jLabel11 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jPanel8 = new javax.swing.JPanel();
		jLabel15 = new javax.swing.JLabel();
		lblUnidadMaterial = new javax.swing.JLabel();
		jPanel9 = new javax.swing.JPanel();
		jLabel17 = new javax.swing.JLabel();
		lblMarcaMaterial = new javax.swing.JLabel();
		jPanel5 = new javax.swing.JPanel();
		jLabel7 = new javax.swing.JLabel();
		jPanel6 = new javax.swing.JPanel();
		jLabel9 = new javax.swing.JLabel();
		lblPrecioConIVA = new javax.swing.JLabel();
		jPanel10 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		JTextField1 = new javax.swing.JTextField();
		jPanel11 = new javax.swing.JPanel();
		jPanel12 = new javax.swing.JPanel();
		jPanel13 = new javax.swing.JPanel();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		jPanel14 = new javax.swing.JPanel();
		jLabel22 = new javax.swing.JLabel();
		jLabel23 = new javax.swing.JLabel();
		jPanel18 = new javax.swing.JPanel();
		jPanel19 = new javax.swing.JPanel();
		jLabel30 = new javax.swing.JLabel();
		jLabel34 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jPanel15 = new javax.swing.JPanel();
		jLabel25 = new javax.swing.JLabel();
		jPanel17 = new javax.swing.JPanel();
		jLabel26 = new javax.swing.JLabel();
		jLabel29 = new javax.swing.JLabel();
		jPanel16 = new javax.swing.JPanel();
		jPanel20 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable2 = new javax.swing.JTable();
		cmbDesMat = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Histórico Cliente");

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales"));
		jPanel1.setLayout(new java.awt.BorderLayout(5, 5));

		jPanel3.setPreferredSize(new java.awt.Dimension(200, 100));
		jPanel3.setLayout(new java.awt.BorderLayout(5, 5));

		jPanel2.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

		jLabel1.setText("Cliente:");
		jPanel2.add(jLabel1);

		jLabel2.setText("Material:");
		jPanel2.add(jLabel2);

		jLabel3.setText("Precio Actual:");
		jPanel2.add(jLabel3);

		jPanel3.add(jPanel2, java.awt.BorderLayout.LINE_START);

		jPanel4.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

		jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel4.add(jLabel4);

		lblCodigoMaterial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel4.add(lblCodigoMaterial);

		lblPrecioMaterial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel4.add(lblPrecioMaterial);

		jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

		jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_START);

		jPanel7.setPreferredSize(new java.awt.Dimension(240, 62));
		jPanel7.setLayout(new java.awt.GridLayout(3, 1, 5, 5));
		jLabel11.setText("Registros:");
		jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jPanel7.add(jLabel11);
		
		jPanel7.add(JTextField1);
		jPanel7.add(jLabel13);
		jPanel7.add(jLabel14);

		jPanel8.setLayout(new java.awt.BorderLayout(5, 5));

		jLabel15.setText("Unidad:");
		jPanel8.add(jLabel15, java.awt.BorderLayout.LINE_START);

		lblUnidadMaterial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jPanel8.add(lblUnidadMaterial, java.awt.BorderLayout.CENTER);

		jPanel7.add(jPanel8);

		jPanel9.setLayout(new java.awt.BorderLayout(5, 5));

		jLabel17.setText("Marca:");
		jPanel9.add(jLabel17, java.awt.BorderLayout.LINE_START);

		lblMarcaMaterial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel9.add(lblMarcaMaterial, java.awt.BorderLayout.CENTER);

		jPanel7.add(jPanel9);

		jPanel1.add(jPanel7, java.awt.BorderLayout.LINE_END);

		jPanel5.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

		jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel5.add(jLabel7);
		jPanel5.add(cmbDesMat);

		jPanel6.setLayout(new java.awt.BorderLayout(5, 5));

		jLabel9.setText("<html>Precio<font color='red'> (Incluído IVA)</font>:</html>");
		jPanel6.add(jLabel9, java.awt.BorderLayout.LINE_START);

		lblPrecioConIVA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel6.add(lblPrecioConIVA, java.awt.BorderLayout.CENTER);

		jPanel5.add(jPanel6);

		jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

		jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Factura"));
		jPanel10.setLayout(new java.awt.BorderLayout());

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		jScrollPane1.setViewportView(jTable1);
		jPanel10.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de Factura"));
		jPanel11.setLayout(new java.awt.BorderLayout(5, 5));

		jPanel12.setPreferredSize(new java.awt.Dimension(200, 100));
		jPanel12.setLayout(new java.awt.BorderLayout(5, 5));

		jPanel13.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

		jLabel19.setText("Cliente:");
		jPanel13.add(jLabel19);

		jLabel20.setText("Factura:");
		jPanel13.add(jLabel20);

		jPanel12.add(jPanel13, java.awt.BorderLayout.LINE_START);

		jPanel14.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

		jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jPanel14.add(jLabel22);

		jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel14.add(jLabel23);

		jPanel12.add(jPanel14, java.awt.BorderLayout.CENTER);

		jPanel11.add(jPanel12, java.awt.BorderLayout.LINE_START);

		jPanel18.setPreferredSize(new java.awt.Dimension(160, 62));
		jPanel18.setLayout(new java.awt.BorderLayout());

		jPanel19.setLayout(new java.awt.BorderLayout(5, 5));
		jLabel30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel19.add(jLabel30, java.awt.BorderLayout.CENTER);

		jLabel34.setText("Total:");
		jPanel19.add(jLabel34, java.awt.BorderLayout.LINE_START);

		jPanel18.add(jPanel19, java.awt.BorderLayout.PAGE_START);

		jButton1.setText("Pasa a Pedido");
		jPanel18.add(jButton1, java.awt.BorderLayout.PAGE_END);

		jPanel11.add(jPanel18, java.awt.BorderLayout.LINE_END);

		jPanel15.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

		jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jLabel25.setText(" " + nomCli);
		jPanel15.add(jLabel25);

		jPanel17.setLayout(new java.awt.BorderLayout(5, 5));

		jLabel26.setText("Fecha:");
		jPanel17.add(jLabel26, java.awt.BorderLayout.LINE_START);

		jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel17.add(jLabel29, java.awt.BorderLayout.CENTER);

		jPanel15.add(jPanel17);

		mensaje = new JLabel();
		mensaje.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jPanel16.setLayout(new java.awt.BorderLayout(5, 5));
		jPanel16.add(mensaje, BorderLayout.CENTER);

		jPanel15.add(jPanel16);

		jPanel11.add(jPanel15, java.awt.BorderLayout.CENTER);

		jPanel20.setLayout(new java.awt.BorderLayout());

		jTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		jScrollPane2.setViewportView(jTable2);

		jPanel20.add(jScrollPane2, java.awt.BorderLayout.CENTER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
				.addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
				.addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
				.addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
				.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(3, 3, 3)
				.addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE).addContainerGap()));

		pack();

		cmbDesMat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BeanMaterial mt = (BeanMaterial) cmbDesMat.getSelectedItem();
				actualizarVista(mt);
			}
		});
	}

	protected void actualizarVista(BeanMaterial mt) {
		final String codigoMaterial = mt.getIdMaterial();
		mdlTblFacturaDetalle.limpiar();
		mdlTblFacturas.limpiar();
		/*
		 * 	LLENAMOS LOS DATOS DEL MATERIAL
		 */
		SqlMaterialImpl sqlMaterial = new SqlMaterialImpl();
		final BeanMaterial material = sqlMaterial.getMaterial(codigoMaterial);
		if (material != null) {
			String marcadoParaAplicarIVA = material.getCell_design();
			double precioConIVA = 0d;
			try {
				precioConIVA = Double.parseDouble(material.getPrice_1());
			} catch (Exception e2) {
				precioConIVA = 0d;
			}
			if (marcadoParaAplicarIVA.compareTo("1") == 0) {
				precioConIVA = (Util.obtenerIva() + 1) * precioConIVA;
			}
			lblCodigoMaterial.setText(material.getIdMaterial());
			lblPrecioMaterial.setText(material.getPrice_1());
			lblUnidadMaterial.setText(material.getUn());
			lblMarcaMaterial.setText(material.getNormt());// MARCA
			lblPrecioConIVA.setText(Util.formatearNumero(precioConIVA));
		} else {
			lblCodigoMaterial.setText("");
			lblPrecioMaterial.setText("");
			lblUnidadMaterial.setText("");
			lblMarcaMaterial.setText("");// MARCA
			lblPrecioConIVA.setText("");
		}
		Util.setAnchoColumnas(jTable2);
		if (codigoMaterial == null || codigoMaterial.isEmpty()) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					// Establecemos la máxima cantida de registros
					int cantidadRegistros = maximaCantidadRegistros;
					try {
						cantidadRegistros = Integer.parseInt(JTextField1.getText());
					} catch (Exception ex) {
						cantidadRegistros = maximaCantidadRegistros;
					}
					int i = 0;
					for (BeanFactura f : facturas) {
						if (i < cantidadRegistros) {
							mdlTblFacturas.agregarFactura(f);
							i++;
						}
					}
					Util.setAnchoColumnas(jTable1);
				}
			});
		} else {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					JTextField1.setText("" + facturas.size());
					int cantidadRegistros = maximaCantidadRegistros;
					try {
						cantidadRegistros = Integer.parseInt(JTextField1.getText());
					} catch (Exception ex) {
						cantidadRegistros = maximaCantidadRegistros;
					}
					int i = 0;
					for (BeanFactura f : facturas) {
						if (codigoMaterial.compareTo(f.getMaterial().getIdMaterial()) == 0 && i < cantidadRegistros) {
							mdlTblFacturas.agregarFactura(f);
							i++;
						}
					}
					Util.setAnchoColumnas(jTable1);
				}
			});
		}
	}
	
	protected void setAnchoColumnas() {
		jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Util.setAnchoColumnas(jTable1);
		Util.setAnchoColumnas(jTable2);
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel lblPrecioConIVA;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel lblUnidadMaterial;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel lblMarcaMaterial;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel23;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel29;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel30;
	private javax.swing.JLabel jLabel34;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel lblCodigoMaterial;
	private javax.swing.JLabel lblPrecioMaterial;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel14;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JPanel jPanel17;
	private javax.swing.JPanel jPanel18;
	private javax.swing.JPanel jPanel19;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel20;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable jTable1;
	DefaultTableModel modeloTabla1;
	private javax.swing.JTable jTable2;
	DefaultTableModel modeloTabla2;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbDesMat;
	private javax.swing.JTextField JTextField1;
}