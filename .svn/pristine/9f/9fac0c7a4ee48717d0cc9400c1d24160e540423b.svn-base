package com.promesa.internalFrame.pedidos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
//import javax.swing.JDesktopPane;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

//import com.promesa.bean.BeanDato;
//import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanJerarquia;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.sql.impl.SqlDivisionImpl;
import com.promesa.sap.SPedidos;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.ItemArbol;
import com.promesa.util.Mensaje;
import com.promesa.util.ModeloArbol;
import com.promesa.util.NodoArbol;
import com.promesa.util.Renderer;
import com.promesa.util.Util;

public class IListarPrecios extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	SPedidos objSAP;
	SqlDivisionImpl objSQL;
	List<BeanMaterial> listaMateriales;
	List<BeanMaterial> materiale;
	BeanMaterial material = null;
	private boolean collapse = false;
	private int dividerLocation = 0;
	public String modo = "0";

	/** Creates new form IListarPrecios */
	public IListarPrecios(BeanTareaProgramada beanTareaProgramada) {
		initComponents();
		btnIzq.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/izq.png")));
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/ipanelcontrol.png")));
		buscarMateriales();
		tablaVacia();
		tablaPrecios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		((DefaultTableCellRenderer) tablaPrecios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tablaPrecios.setRowHeight(Constante.ALTO_COLUMNAS);
		Util.setAnchoColumnas(tablaPrecios);
	}

	private void btnIzqActionPerformed(java.awt.event.ActionEvent evt) {
		collapse = !collapse;
		if (collapse) {
			dividerLocation = splitPane.getDividerLocation();
			splitPane.setDividerLocation(0);
			btnIzq.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/der.png")));
			btnIzq.setToolTipText("Expander");
		} else {
			splitPane.setDividerLocation(dividerLocation);
			btnIzq.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/izq.png")));
			btnIzq.setToolTipText("Contraer");
		}
	}

	private void initComponents() {
		splitPane = new javax.swing.JSplitPane();
		scrollLeft = new javax.swing.JScrollPane();
		treeMateriales = new javax.swing.JTree();
		pnlTabla = new javax.swing.JPanel();
		lblTitulo = new javax.swing.JLabel();
		scrollRight = new javax.swing.JScrollPane();
		tablaPrecios = new javax.swing.JTable();
		pnlBoton = new javax.swing.JPanel();
		btnIzq = new javax.swing.JButton();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Lista de Precios");

		splitPane.setDividerLocation(200);
		splitPane.setDividerSize(2);

		treeMateriales.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				treeMaterialesMouseReleased(evt);
			}
		});
		scrollLeft.setViewportView(treeMateriales);

		splitPane.setLeftComponent(scrollLeft);

		pnlTabla.setLayout(new java.awt.BorderLayout());

		lblTitulo.setBackground(new java.awt.Color(175, 200, 222));
		lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		lblTitulo.setText("MATERIAL");
		lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
		lblTitulo.setOpaque(true);
		pnlTabla.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

		tablaPrecios.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		scrollRight.setViewportView(tablaPrecios);

		pnlTabla.add(scrollRight, java.awt.BorderLayout.CENTER);

		pnlBoton.setLayout(new javax.swing.BoxLayout(pnlBoton, javax.swing.BoxLayout.LINE_AXIS));

		btnIzq.setMaximumSize(new java.awt.Dimension(20, 20));
		btnIzq.setMinimumSize(new java.awt.Dimension(20, 20));
		btnIzq.setPreferredSize(new java.awt.Dimension(20, 20));
		btnIzq.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnIzqActionPerformed(evt);
			}
		});
		pnlBoton.add(btnIzq);

		pnlTabla.add(pnlBoton, java.awt.BorderLayout.LINE_START);

		splitPane.setRightComponent(pnlTabla);

		getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

		pack();
	}

	private void treeMaterialesMouseReleased(java.awt.event.MouseEvent evt) {
		TreePath selPath = treeMateriales.getPathForLocation(evt.getX(), evt.getY());
		final DLocker bloqueador = new DLocker();
		if (selPath != null) {
			ItemArbol agrupacion = (ItemArbol) selPath.getLastPathComponent();
			final String id = agrupacion.getId();
			if (id != null) {
				Thread hilo = new Thread() {
					public void run() {
						try {
							objSQL = new SqlDivisionImpl();
							listaMateriales = objSQL.getListaPrecios(id);
							materiale = new ArrayList<BeanMaterial>();
							for (BeanMaterial m : listaMateriales) {
								material = new BeanMaterial();
								material.setIdMaterial(m.getIdMaterial());
								material.setUn(m.getUn());
								material.setDescripcion(m.getText_line());
								material.setPrice_1(m.getPrice_1());
								material.setTipoMaterial(m.getTipoMaterial());
								material.setPrdha(m.getPrdha());
								try {
									materiale.add(material);
								} catch (Exception e) {
									Util.mostrarExcepcion(e);
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
								}
							}
							if (materiale == null || materiale.size() == 0) {
								tablaVacia();
							} else {
								tablaLlena();
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
	}

	private void tablaVacia() {
		String Columnas[] = { "MATERIAL", "UM", "DENOMINACIÓN", "LISTA", "PROCEDENCIA" };
		DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaPrecios.setModel(tblTablaModel);
		this.updateUI();
	}

	private void tablaLlena() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "MATERIAL", "UM", "DENOMINACIÓN", "LISTA", "PROCEDENCIA" };
				DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}

					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return String.class;
					}
				};
				tblTablaModel.setNumRows(materiale.size());
				for (int i = 0; i < materiale.size(); i++) {
					tblTablaModel.setValueAt(materiale.get(i).getIdMaterial(), i, 0);
					tblTablaModel.setValueAt(materiale.get(i).getUn(), i, 1);
					tblTablaModel.setValueAt(materiale.get(i).getDescripcion(), i, 2);
					tblTablaModel.setValueAt(Util.formatearNumero(materiale.get(i).getPrice_1()), i, 3);
					tblTablaModel.setValueAt(materiale.get(i).getTipoMaterial(), i, 4);
				}
				tablaPrecios.setDefaultRenderer(String.class, new Renderer());
				tablaPrecios.setModel(tblTablaModel);
				tablaPrecios.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
				tablaPrecios.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getCenterCell());
				tablaPrecios.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getLeftCell());
				tablaPrecios.getColumnModel().getColumn(3).setCellRenderer(new Renderer().getRightCell());
				tablaPrecios.getColumnModel().getColumn(4).setCellRenderer(new Renderer().getCenterCell());
				tablaPrecios.setModel(tblTablaModel);
				tablaPrecios.updateUI();
				Util.setAnchoColumnas(tablaPrecios);
			}
		});
	}

	private void buscarMateriales() {
		List<BeanJerarquia> jerarquia[];
		try {
			objSQL = new SqlDivisionImpl();
			jerarquia = objSQL.jerarquiaMateriales();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			jerarquia = null;
		}
		// Llenar arbol de jerarquia
		llenarArbolDeJerarquia(jerarquia);
	}

	@SuppressWarnings("unchecked")
	private void llenarArbolDeJerarquia(List<BeanJerarquia>[] jerarquia) {
		TreeModel model;
		treeMateriales.setModel(null);
		ItemArbol rootNode;
		rootNode = new ItemArbol("Jerarquía", null);
		ItemArbol level_0 = null;
		HashMap<BeanJerarquia, ItemArbol> hm_source = new HashMap<BeanJerarquia, ItemArbol>();
		HashMap<BeanJerarquia, ItemArbol> hm_target = new HashMap<BeanJerarquia, ItemArbol>();
		for (BeanJerarquia bean : jerarquia[0]) {
			level_0 = new ItemArbol(bean.getStrVTEXT(), bean.getStrPRODH());
			rootNode.addChild(level_0);
			hm_source.put(bean, level_0);
		}
		for (int i = 1; i < jerarquia.length; i++) {
			List<BeanJerarquia> list = jerarquia[i];
			hm_target.clear();
			for (BeanJerarquia bean : list) {
				level_0 = new ItemArbol(bean.getStrVTEXT(), bean.getStrPRODH());
				ItemArbol padre = obtenerNivelJerarquicoSuperior(hm_source, bean);
				if (padre != null) {
					padre.addChild(level_0);
					hm_target.put(bean, level_0);
				}
			}
			hm_source = (HashMap<BeanJerarquia, ItemArbol>) hm_target.clone();
		}
		model = new ModeloArbol(rootNode);
		treeMateriales.setModel(model);
		treeMateriales.setCellRenderer(new NodoArbol());
	}

	private ItemArbol obtenerNivelJerarquicoSuperior(HashMap<BeanJerarquia, ItemArbol> hm_source, BeanJerarquia bean) {
		@SuppressWarnings("rawtypes")
		Iterator it = hm_source.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry) it.next();
			String codigoHijo = bean.getStrPRODH();
			BeanJerarquia beanDivisionPadre = (BeanJerarquia) e.getKey();
			String codigoPadre = beanDivisionPadre.getStrPRODH();
			if (codigoHijo.startsWith(codigoPadre)) {
				return (ItemArbol) e.getValue();
			}
		}
		return null;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}
	private javax.swing.JPanel pnlTabla;
	private javax.swing.JButton btnIzq;
	private javax.swing.JLabel lblTitulo;
	private javax.swing.JPanel pnlBoton;
	private javax.swing.JScrollPane scrollLeft;
	private javax.swing.JScrollPane scrollRight;
	private javax.swing.JSplitPane splitPane;
	private javax.swing.JTable tablaPrecios;
	private javax.swing.JTree treeMateriales;
}