package com.promesa.internalFrame.pedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.promesa.bean.BeanDato;
import com.promesa.main.Promesa;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.bean.BeanClienteEmpleado;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SPedidos;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Renderer;
import com.promesa.util.TablaAExcel;
import com.promesa.util.Util;

public class IBusquedaClientes extends javax.swing.JInternalFrame implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	List<BeanCliente> listaCliente;
	List<BeanCliente> clientee;
	BeanCliente cliente = null;
	SPedidos objSAP;
	private String strIdVendedor;
	static List<BeanDato> datose;
	public String modo = "0";

	@SuppressWarnings("static-access")
	public IBusquedaClientes(BeanTareaProgramada beanTareaProgramada) {
		initComponents();
		btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnBuscar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/search.png")));
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});

		btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLimpiarActionPerformed(evt);
			}
		});

		btnExportar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnExportar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/excel-24.png")));
		btnExportar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exportarTablaAExcel(evt);
			}
		});

		btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnLimpiar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/clear.png")));

		mnuToolBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/ipanelcontrol.png")));
		this.datose = beanTareaProgramada.getDatose();
		BeanDato usuario = ((BeanDato) datose.get(0));
		strIdVendedor = usuario.getStrCodigo();
		tablaVacia();
		txtCodCliente.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtCodClienteKeyReleased(evt);
			}
		});
		txtCodCliente.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtCodCliente){
					txtCodCliente.selectAll();
				}
			}
		});
		txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtCodClienteKeyReleased(evt);
			}
		});
		txtNombreCliente.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtNombreCliente){
					txtNombreCliente.selectAll();
				}
			}
		});
		JTableHeader tableHeader = tblResultado.getTableHeader();
		tblResultado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblResultado.setRowHeight(Constante.ALTO_COLUMNAS);
		lblResultado.setVisible(false);
	}

	protected void exportarTablaAExcel(ActionEvent evt) {
		try {
			TablaAExcel reporte = new TablaAExcel();
			reporte.generarReporte("clientes.xls", tblResultado.getModel());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			Mensaje.mostrarError(e.getMessage());
		}
	}

	private void initComponents() {
		mnuToolBar = new javax.swing.JToolBar();
		btnBuscar = new javax.swing.JButton();
		btnLimpiar = new javax.swing.JButton();
		btnExportar = new javax.swing.JButton();
		pnlContenedor = new javax.swing.JPanel();
		lblCodCliente = new javax.swing.JLabel();
		txtCodCliente = new javax.swing.JTextField();
		pnlResultado = new javax.swing.JPanel();
		lblResultado = new javax.swing.JLabel();
		scrResultado = new javax.swing.JScrollPane();
		tblResultado = new javax.swing.JTable();
		lblNombreCliente = new javax.swing.JLabel();
		txtNombreCliente = new javax.swing.JTextField();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setTitle("Búsqueda Clientes");

		mnuToolBar.setFloatable(false);
		mnuToolBar.setRollover(true);

		btnBuscar.setText("Buscar");
		btnBuscar.setFocusable(false);
		btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBar.add(btnBuscar);

		mnuToolBar.add(new javax.swing.JToolBar.Separator());

		btnLimpiar.setText("Limpiar");
		btnLimpiar.setFocusable(false);
		btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBar.add(btnLimpiar);

		mnuToolBar.add(new javax.swing.JToolBar.Separator());

		btnExportar.setText("Exportar");
		btnExportar.setFocusable(false);
		btnExportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBar.add(btnExportar);

		getContentPane().add(mnuToolBar, java.awt.BorderLayout.PAGE_START);

		lblCodCliente.setText("Cód. Cliente:");

		pnlResultado.setLayout(new java.awt.BorderLayout());

		lblResultado.setBackground(new java.awt.Color(175, 200, 222));
		lblResultado.setFont(new java.awt.Font("Tahoma", 1, 18));
		lblResultado.setText("RESULTADO DE BÚSQUEDA");
		lblResultado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
		lblResultado.setOpaque(true);
		lblResultado.setPreferredSize(new java.awt.Dimension(115, 24));
		pnlResultado.add(lblResultado, java.awt.BorderLayout.PAGE_START);

		tblResultado.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		scrResultado.setViewportView(tblResultado);

		pnlResultado.add(scrResultado, java.awt.BorderLayout.CENTER);

		lblNombreCliente.setText("Nombre de Cliente:");

		javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
		pnlContenedor.setLayout(pnlContenedorLayout);
		pnlContenedorLayout.setHorizontalGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlContenedorLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlContenedorLayout.createSequentialGroup().addComponent(lblCodCliente)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addComponent(pnlResultado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 568,
											Short.MAX_VALUE)).addContainerGap()));

		pnlContenedorLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { txtCodCliente, txtNombreCliente });

		pnlContenedorLayout.setVerticalGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlContenedorLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblCodCliente)
				.addComponent(txtCodCliente,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,
											javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(lblNombreCliente)
				.addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlResultado, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE).addContainerGap()));

		getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void txtCodClienteKeyReleased(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (clientee == null) {
				buscar();
			} else {
				tablaLLena(txtCodCliente.getText(), txtNombreCliente.getText());
			}
		}
	}

	public void tablaVacia() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "CÓDIGO", "CLIENTE", "CALLE", "TELÉFONO", "TELEFAX" };
				DefaultTableModel tblTablaModel = new DefaultTableModel( new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblResultado.setModel(tblTablaModel);
				tblResultado.updateUI();
				Util.setAnchoColumnas(tblResultado);
			}
		});
	}

	public void tablaLLena(final String codigoCliente, final String nombreCliente) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "CÓDIGO", "CLIENTE", "CALLE", "TELÉFONO", "TELEFAX" };
				DefaultTableModel tblTablaModel = new DefaultTableModel( new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblTablaModel.setNumRows(0);
				for (int i = 0; i < clientee.size(); i++) {
					String codigo = clientee.get(i).getStrIdCliente();
					String nombre = clientee.get(i).getStrNombreCliente();
					String[] values = { 
							codigo, nombre, clientee.get(i).getStrDireccionCliente(), clientee.get(i).getStrTelefonoTrabajoCliente(),
							clientee.get(i).getStrNumeroFaxCliente() };
					if (codigoCliente.isEmpty() && nombreCliente.isEmpty()) {
						tblTablaModel.addRow(values);
					} else {
						if (!codigoCliente.isEmpty()) {
							if (!nombreCliente.isEmpty()) {
								if (codigo.compareTo(codigoCliente) == 0 && nombre.contains(nombreCliente)) {
									tblTablaModel.addRow(values);
								}
							} else {
								if (codigo.compareTo(codigoCliente) == 0) {
									tblTablaModel.addRow(values);
								}
							}
						} else {
							if (!nombreCliente.isEmpty()) {
								if (nombre.contains(nombreCliente)) {
									tblTablaModel.addRow(values);
								}
							}
						}
					}

				}
				tblResultado.setDefaultRenderer(Object.class, new Renderer());
				tblResultado.setModel(tblTablaModel);
				for (int i = 0; i < 5; i++) {
					tblResultado.getColumnModel().getColumn(i).setCellRenderer(new Renderer().getCenterCell());
				}
				tblResultado.updateUI();
				Util.setAnchoColumnas(tblResultado);
			}
		});
	}

	private void btnBuscarActionPerformed(ActionEvent evt) {
		buscar();
	}

	private void btnLimpiarActionPerformed(ActionEvent evt) {
		txtCodCliente.setText("");
		txtNombreCliente.setText("");
		txtCodCliente.requestFocusInWindow();
	}

	private void buscar() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					buscaClientes();
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

	private void buscaClientes() throws Exception {
		clientee = new ArrayList<BeanCliente>();
		BeanDato usuario = ((BeanDato) Promesa.datose.get(0));
		BeanCliente cliente = new BeanCliente();
		BeanClienteEmpleado beanClienteEmpleado = new BeanClienteEmpleado();

		cliente.setStrIdCliente(txtCodCliente.getText().trim());
		cliente.setStrNombreCliente(txtNombreCliente.getText().trim());
		beanClienteEmpleado.setStrIdEmpleado(strIdVendedor);
		cliente.setClienteEmpleado(beanClienteEmpleado);

		if (usuario.getStrModo().equals("1")) {
			// DESDE SAP
			objSAP = new SPedidos();
			listaCliente = objSAP.vendedorClientes(strIdVendedor, txtCodCliente.getText(), txtNombreCliente.getText());
		} else {
			// DESDE SQLITE
			SqlCliente sqlCliente = new SqlClienteImpl();
			sqlCliente.setListaCliente(cliente);
			listaCliente = sqlCliente.getListaCliente();

		}
		for (BeanCliente c : listaCliente) {
			cliente = new BeanCliente();
			cliente.setStrIdCliente(c.getStrIdCliente());
			cliente.setStrNombreCliente(c.getStrNombreCliente());
			cliente.setStrDireccionCliente(c.getStrDireccionCliente());
			cliente.setStrTelefonoTrabajoCliente(c.getStrTelefonoTrabajoCliente());
			cliente.setStrNumeroFaxCliente(c.getStrNumeroFaxCliente());
			try {
				clientee.add(cliente);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
			}
		}
		if (clientee == null || clientee.size() == 0) {
			tablaVacia();
		} else {
			tablaLLena(txtCodCliente.getText(), txtNombreCliente.getText());
		}
	}

	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnLimpiar;
	private javax.swing.JButton btnExportar;
	private javax.swing.JLabel lblNombreCliente;
	private javax.swing.JLabel lblCodCliente;
	private javax.swing.JLabel lblResultado;
	private javax.swing.JToolBar mnuToolBar;
	private javax.swing.JPanel pnlContenedor;
	private javax.swing.JPanel pnlResultado;
	private javax.swing.JScrollPane scrResultado;
	private javax.swing.JTable tblResultado;
	private javax.swing.JTextField txtCodCliente;
	private javax.swing.JTextField txtNombreCliente;

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}