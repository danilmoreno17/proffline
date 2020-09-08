package com.promesa.dialogo.pedidos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;

import com.promesa.bean.BeanConexion;
import com.promesa.bean.BeanDato;
import com.promesa.internalFrame.pedidos.IPedidos;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.internalFrame.pedidos.custom.ModeloConsultaDinamica;
import com.promesa.internalFrame.pedidos.custom.ModeloConsultaDinamica2;
import com.promesa.internalFrame.pedidos.custom.RendererConsultaDinamica2;
import com.promesa.main.Promesa;
import com.promesa.pedidos.agruparCabecera.ColumnGroup;
import com.promesa.pedidos.agruparCabecera.GroupableTableHeader;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.bean.BeanClaseMaterial;
import com.promesa.pedidos.bean.BeanCondicionComercial1;
import com.promesa.pedidos.bean.BeanCondicionComercial2;
import com.promesa.pedidos.bean.BeanCondicionComercial3x;
import com.promesa.pedidos.bean.BeanCondicionComercial4x;
import com.promesa.pedidos.bean.BeanCondicionComercial5x;
import com.promesa.pedidos.bean.BeanCondicionPago;
import com.promesa.pedidos.bean.BeanJerarquia;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.bean.BeanPedidoPartners;
import com.promesa.pedidos.busqueda.Buscador;
import com.promesa.pedidos.busqueda.Indexador;
import com.promesa.pedidos.sql.impl.SqlCondicionPagoImpl;
import com.promesa.pedidos.sql.impl.SqlDivisionImpl;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SPedidos;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("serial")
public class DConsultaMateriales2 extends javax.swing.JDialog implements FocusListener, WindowListener {

	@SuppressWarnings("unused")
	private SqlDivisionImpl jerarquia = new SqlDivisionImpl();
	private SqlCondicionPagoImpl condiciones = new SqlCondicionPagoImpl();
	private BeanJerarquia comodin;
	private List<BeanJerarquia> uno;
	private List<BeanJerarquia> dos;
	private List<BeanJerarquia> tres;
	private List<BeanJerarquia> cuatro;
	private List<BeanJerarquia> cinco;
	private IPedidos contenedor;
	private List<BeanMaterial> materialese;
	private BeanCliente cliente;
	private BeanAgenda agenda;
	private String condPago;
	private JTextField textFieldCantidad;
	private JTextField txtDescuentosManuales;
	private ListSelectionModel selectionModel;
	static MyOwnFocusTraversalPolicy newPolicy;
	private String consulta = "";
	private long cantidadRegistros = 0;
	private int paginaActual = 0;
	private int cantidadPaginas = 0;
	private int tipo;
	private String strFiltro;
	@SuppressWarnings("unused")
	private boolean esTextoSelecionado = false;
//	ModeloConsultaDinamica2 modeloTablaItems;
	
	private String strPath ;

	String tipoMaterial;
	//
	private Indexador indexTopCliente;
	private Indexador indexTopTipologia;
	//
	private String strMaterial;
	private String strDivision;
	private String strCategoria;
	private String strFamilia;
	private String strLinea;
	private String strGrupo;
	//
	private JTextField txtCategoriaEditor;
	private JTextField txtDivisionEditor;
	private JTextField txtFamiliaEditor;
	private JTextField txtLineaEditor;
	private JTextField txtGrupoEditor;
	//
	private BeanMaterial comodinMaterila;
	private BeanJerarquia comodinDivision;
	private BeanJerarquia comodinCategoria;
	private BeanJerarquia comodinFamilia;
	private BeanJerarquia comodinLinea;
	private BeanJerarquia comodinGrupo;
	
	boolean flag;
	boolean activarDivision = true;
	boolean activarCategoria = true;
	boolean activarFamilia = true;
	boolean activarLinea = true;
	boolean activarGrupo = true;
	boolean activarBusqueda = true;
	//private boolean esmaterialnuevo = false;
	private int idTipoConsulta;
	//
	private List<BeanMaterial> listaMaterialesABuscar; // ---- ESTA LISTA VA EN EL COMBOX DE MATERIALES ---- //

	/** Creates new form DConsultaMateriales */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DConsultaMateriales2(List<BeanDato> datose, BeanCliente cliente,
			IPedidos contenedor, java.awt.Frame parent, boolean modal, BeanAgenda agenda, String condPago,int _pIdTipoConsulta) {
		super(Promesa.getInstance(), true);
		this.condPago = condPago;
		this.cliente = cliente;
		this.agenda = agenda;
		this.contenedor = contenedor;
		//this.esmaterialnuevo = esmaterialnuevo;
		this.idTipoConsulta = _pIdTipoConsulta;
//		modeloTablaItems = new ModeloConsultaDinamica2();
		initComponents();
		cambiarVisibilidadLimitadorRegistros(false);
		selectionModel = tblMateriales.getSelectionModel();
		setAnchoColumnas2();
//		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		this.setLocationRelativeTo(null);
		condiciones.setListaCondicionesPago();
		materialese = new ArrayList<BeanMaterial>();
		comodin = new BeanJerarquia();
		comodin.setStrVTEXT("Todos");
		
		strPath = Constante.PATH + Constante.MATERIAL;

		uno = new ArrayList<BeanJerarquia>();
		dos = new ArrayList<BeanJerarquia>();
		tres = new ArrayList<BeanJerarquia>();
		cuatro = new ArrayList<BeanJerarquia>();
		cinco = new ArrayList<BeanJerarquia>();

		listaMaterialesABuscar = new ArrayList<BeanMaterial>();
		
		strMaterial = "";
		strDivision = "";
		strCategoria = "";
		strFamilia = "";
		strLinea = "";
		strGrupo = "";
		tipoMaterial = "";
		flag = false;
		tipo = 0;
		cmbDescripcionLarga.setEditable(true);
		cmbDivision.setEditable(true);
		cmbCategoria.setEditable(true);
		cmbFamilia.setEditable(true);
		cmbLinea.setEditable(true);
		cmbGrupo.setEditable(true);

		comodinMaterila = new BeanMaterial();
		comodinDivision = new BeanJerarquia();
		comodinCategoria = new BeanJerarquia();
		comodinFamilia = new BeanJerarquia();
		comodinLinea = new BeanJerarquia();
		comodinGrupo = new BeanJerarquia();

		try{
			llenarTabla();
		}catch (NullPointerException nulle){
			System.out.println("null llenar tabla");
		}
		
		//System.out.println("Inicilizando Tabla");
		
		tblMateriales.setDefaultRenderer(String.class, new RendererConsultaDinamica2());
		tblMateriales.setDefaultRenderer(Double.class, new RendererConsultaDinamica2());
		tblMateriales.setDefaultRenderer(Long.class, new RendererConsultaDinamica2());

		tblMateriales.getTableHeader().setReorderingAllowed(false);
		Vector<Component> order = new Vector<Component>(14);

		order.add(cmbTipoConsulta);
		order.add(cmbDescripcionLarga.getEditor().getEditorComponent());
		order.add(cmbDivision.getEditor().getEditorComponent());
		order.add(cmbCategoria.getEditor().getEditorComponent());
		order.add(cmbFamilia.getEditor().getEditorComponent());
		order.add(cmbLinea.getEditor().getEditorComponent());
		order.add(cmbGrupo.getEditor().getEditorComponent());
		order.add(txtDescripcionCorta);
		order.add(txtDescripcionLarga);
		order.add(txtMaterial);
		order.add(txtMarca);
		order.add(cmbTipo);
		order.add(cmbCondPago);
		order.add(btnBuscar);
		order.add(btnBuscarVoz);
		order.add(btnPasarPedido);
		newPolicy = new MyOwnFocusTraversalPolicy(order);
		setFocusTraversalPolicy(newPolicy);
		String[] listaTiposMaterial = { "Todos", "Normal", "Pesado", "Rojo", "Bodega" };
		cmbTipo.setModel(new DefaultComboBoxModel(listaTiposMaterial));
		llenarCombos();

		/*
		 * CONSULTA DINAMICA
		 * MARCELO MOYANO
		 */
		try {
			cargarJerarquiaMateriales();
		} catch (IOException e1) {
			e1.printStackTrace();
			Util.mostrarExcepcion(e1);
		}
		/*if(esmaterialnuevo){
			cmbTipoConsulta.setSelectedIndex(3);	
			cmbTipoConsultaActionPerformed(null);
		}*/
		if(idTipoConsulta!=0){
			cmbTipoConsulta.setSelectedIndex(idTipoConsulta);	
			cmbTipoConsultaActionPerformed(null);
		}
	}

	private void agruparColumnas() {
		TableColumnModel cm = tblMateriales.getColumnModel();
	    ColumnGroup g_es1 = new ColumnGroup(" ");
	    g_es1.add(cm.getColumn(0));
	    g_es1.add(cm.getColumn(1));
	    g_es1.add(cm.getColumn(2));
	    g_es1.add(cm.getColumn(3));
	    g_es1.add(cm.getColumn(4));
	    g_es1.add(cm.getColumn(5));
	    ColumnGroup g_precio = new ColumnGroup("PRECIO");
	    g_precio.add(cm.getColumn(6));
	    g_precio.add(cm.getColumn(7));
	    g_precio.add(cm.getColumn(8));
	    g_precio.add(cm.getColumn(9));
//	    ColumnGroup g_es2 = new ColumnGroup(" ");
//	    g_es2.add(cm.getColumn(9));
	    ColumnGroup g_cantidad = new ColumnGroup("CANT.");
	    g_cantidad.add(cm.getColumn(10));
	    ColumnGroup g_descuentoManuales = new ColumnGroup("%");
	    g_descuentoManuales.add(cm.getColumn(11));
	    ColumnGroup g_cantidadConfirmada = new ColumnGroup("CANT.");
	    g_cantidadConfirmada.add(cm.getColumn(12));
	    ColumnGroup g_es3 = new ColumnGroup(" ");
	    g_es3.add(cm.getColumn(14));
	    g_es3.add(cm.getColumn(17));
	    
	    ColumnGroup g_top = new ColumnGroup(cmbTipoConsulta.getSelectedIndex()==4?"Prom. 6 Meses":"TOP(Prom)");
	    g_top.add(cm.getColumn(15));
	    g_top.add(cm.getColumn(16));
	    
	    ColumnGroup g_vta_crz = new ColumnGroup("VENTA REAL");
	    g_vta_crz.add(cm.getColumn(18));
	    g_vta_crz.add(cm.getColumn(19));
	    
	    ColumnGroup g_cant = new ColumnGroup("CANT.");
	    g_cant.add(cm.getColumn(20));
	    g_cant.add(cm.getColumn(21));
	    g_cant.add(cm.getColumn(22));
	    
	    GroupableTableHeader header = (GroupableTableHeader)tblMateriales.getTableHeader();
	    
	    header.addColumnGroup(g_es1);
	    header.addColumnGroup(g_precio);
//	    header.addColumnGroup(g_es2);
	    header.addColumnGroup(g_cantidad);
	    header.addColumnGroup(g_descuentoManuales);
	    header.addColumnGroup(g_cantidadConfirmada);
	    header.addColumnGroup(g_es3);
	    header.addColumnGroup(g_top);
	    header.addColumnGroup(g_vta_crz);
	    header.addColumnGroup(g_cant);
	}

	public String getCondPago() {
		return condPago;
	}

	public void setCondPago(String condPago) {
		this.condPago = condPago;
	}

	public IPedidos getContenedor() {
		return contenedor;
	}

	public void setContenedor(IPedidos contenedor) {
		this.contenedor = contenedor;
	}

	public BeanCliente getCliente() {
		return cliente;
	}

	public void setCliente(BeanCliente cliente) {
		this.cliente = cliente;
	}

	public BeanAgenda getAgenda() {
		return agenda;
	}

	public void setAgenda(BeanAgenda agenda) {
		this.agenda = agenda;
	}
	
	private void escucharEventoTeclado() {
		TableColumn columnaCantidad = tblMateriales.getColumnModel().getColumn(10);
		textFieldCantidad = new JTextField();
		textFieldCantidad.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
		textFieldCantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		textFieldCantidad.addFocusListener(this);
		textFieldCantidad.setBorder(BorderFactory.createEmptyBorder());
		DefaultCellEditor singleclickCantidad = new DefaultCellEditor(textFieldCantidad);
		singleclickCantidad.setClickCountToStart(1);
		columnaCantidad.setCellEditor(singleclickCantidad);

		KeyListener keyListenerCantidad = new KeyListener() {
			@SuppressWarnings("unused")
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
					int fila = tblMateriales.getSelectedRow();
					int cantidad = 0;
					try {
						String valor = textFieldCantidad.getText();
						if(valor.equals("")){
							valor = "0";
						}
						cantidad = Integer.parseInt(valor);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						cantidad = 0;
					}
					textFieldCantidad.setText("" + cantidad);
					int numeroFilas = tblMateriales.getRowCount();
					selectionModel.setSelectionInterval(fila, fila);
					tblMateriales.editCellAt(fila, 11);
		
					tblMateriales.setValueAt(cantidad, fila, 10);
					
					
					
					try {
						txtDescuentosManuales.setBackground(new Color(255, 251, 170));
						txtDescuentosManuales.requestFocusInWindow();
						txtDescuentosManuales.setEditable(true);
						txtDescuentosManuales.selectAll();	
					} catch(NullPointerException e) {
			            System.out.println("NullPointerException caught");
			        }
					
					
									
				} else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					int fila = tblMateriales.getSelectedRow();
					try {
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
					}
					tblMateriales.getCellEditor().stopCellEditing();
					invocarSimulacion();
					tblMateriales.updateUI();
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
			
		};
		textFieldCantidad.addKeyListener(keyListenerCantidad);
		((DefaultTableCellRenderer) tblMateriales.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblMateriales.setRowHeight(Constante.ALTO_COLUMNAS);

		tblMateriales.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		((InputMap) UIManager.get("Table.ancestorInputMap")).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");

		tblMateriales.getActionMap().put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int fila = tblMateriales.getSelectedRow();
				int numeroFilas = tblMateriales.getModel().getRowCount();
				if (fila < numeroFilas - 1) {
					fila++;
					tblMateriales.getSelectionModel().setSelectionInterval(fila, fila);
					tblMateriales.editCellAt(fila, 1);
					textFieldCantidad.requestFocusInWindow();
					textFieldCantidad.setEditable(true);
					textFieldCantidad.selectAll();
				}
			}
		});
	}

	private void cambiarVisibilidadLimitadorRegistros(boolean visibilidad) {
		lblCliente.setVisible(visibilidad);
	}
	
	private void escucharEventoTecledoDEscuentosManuales() {
		TableColumn columnaCantidad = tblMateriales.getColumnModel().getColumn(11);
		txtDescuentosManuales = new JTextField();
		txtDescuentosManuales.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
		//txtDescuentosManuales.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		txtDescuentosManuales.addFocusListener(this);
		txtDescuentosManuales.setBorder(BorderFactory.createEmptyBorder());
		DefaultCellEditor singleclickCantidad = new DefaultCellEditor(txtDescuentosManuales);
		singleclickCantidad.setClickCountToStart(1);
		columnaCantidad.setCellEditor(singleclickCantidad);

		KeyListener keyListenerDescuentoManual = new KeyListener() {
			@SuppressWarnings("unused")
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
					int fila = tblMateriales.getSelectedRow();
					double descuentoManual = 0;
					try {
						String descuento = txtDescuentosManuales.getText();
						if(descuento.equals("")){
							descuento = "0.00";
							txtDescuentosManuales.setText(descuento);
						}
						descuentoManual = Double.parseDouble(txtDescuentosManuales.getText());
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						descuentoManual = 0d;
					}
					txtDescuentosManuales.setText("" + descuentoManual);
					tblMateriales.setValueAt(descuentoManual, fila, 11);
					int numeroFilas = tblMateriales.getRowCount();
					if (fila < numeroFilas - 1) {
						fila++;
						selectionModel.setSelectionInterval(fila, fila);
						tblMateriales.editCellAt(fila, 10);
						
						try {
							
							textFieldCantidad.setBackground(new Color(255, 251, 170));
							textFieldCantidad.requestFocusInWindow();
							textFieldCantidad.setEditable(true);
							textFieldCantidad.selectAll();
							
						} catch (NullPointerException e){
							System.out.println("NullPointerException caught");
						}//End try Catch
	
					}
				} else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					int fila = tblMateriales.getSelectedRow();
					try {
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
					}
					tblMateriales.getCellEditor().stopCellEditing();
					invocarSimulacion();
					tblMateriales.updateUI();
				}
			}
			public void keyReleased(KeyEvent keyEvent) {
			}
			public void keyTyped(KeyEvent keyEvent) {
			}
		};
		txtDescuentosManuales.addKeyListener(keyListenerDescuentoManual);
		((DefaultTableCellRenderer) tblMateriales.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblMateriales.setRowHeight(Constante.ALTO_COLUMNAS);

		tblMateriales.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		((InputMap) UIManager.get("Table.ancestorInputMap")).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");

		tblMateriales.getActionMap().put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int fila = tblMateriales.getSelectedRow();
				int numeroFilas = tblMateriales.getModel().getRowCount();
				if (fila < numeroFilas - 1) {
					fila++;
					tblMateriales.getSelectionModel().setSelectionInterval(fila, fila);
					tblMateriales.editCellAt(fila, 1);
					txtDescuentosManuales.requestFocusInWindow();
					txtDescuentosManuales.setEditable(true);
					txtDescuentosManuales.selectAll();
				}
			}
		});
	}//End Escuchar Evento del teclado
	
	

	
	@Override
	public void focusGained(FocusEvent e) {

		if (e.getSource() == textFieldCantidad) {
			
			try {
				
				textFieldCantidad.setBackground(new Color(255, 251, 170));			
				textFieldCantidad.requestFocusInWindow();
				textFieldCantidad.setEditable(true);
				textFieldCantidad.selectAll();
				consultarStock();
				
			} catch (NullPointerException nulle){
				
				System.out.println("Null caugth");
			}//End try - catch
			
			
		}
		if (e.getSource() == txtDescuentosManuales) {
			
			try {

				txtDescuentosManuales.setBackground(new Color(255, 251, 170));
				txtDescuentosManuales.requestFocusInWindow();
				txtDescuentosManuales.setEditable(true);
				txtDescuentosManuales.selectAll();
				
			} catch (NullPointerException nulle){
				
				System.out.println("Null caugth");
			}//End try - catch
			
		}				
	}
	
	private void consultarStock() {
		class worker extends SwingWorker<Void,Void>{

				@Override
				protected Void doInBackground(){
					try {
						int fila = tblMateriales.getSelectedRow();
						if(fila>-1) {
							SqlMaterialImpl sqlMaterialImpl = new SqlMaterialImpl();
							String codigoMaterial = tblMateriales.getValueAt(fila, 0).toString();
							String stock = "00.00";
							//System.out.println("Consultando Stock. ");
							BeanDato usuario = Promesa.datose.get(0);
							if (usuario.getStrModo().equals("1")) {// DESDE SAP
								SPedidos objSAP = new SPedidos();
								List<BeanMaterial> materiales =objSAP.listaMaterialesStock(codigoMaterial);
								if(materiales.size()>0)
									stock = materiales.get(0).getStock();
								else
									stock = sqlMaterialImpl.obtenerStockMaterial(codigoMaterial);
							}else {
								stock = sqlMaterialImpl.obtenerStockMaterial(codigoMaterial);
							}
							
							//System.out.println("Stock: " + stock);
							
							if(stock.isEmpty())
								stock = "0.00";
							
							tblMateriales.setValueAt(stock, fila, 13);
							
							try{
								tblMateriales.repaint();
							}catch (NullPointerException nulle){
								System.out.println("update UI");
							}
							
							
						}
					} catch (NullPointerException nulle){
						System.out.println("Worker Exp Internal");
					} catch(Exception e) {
						System.out.println("Exepcion:  " + e.getStackTrace());
					}finally {
					}
					return null;
				}
		}
		
		try{
			new worker().execute();
		}catch (NullPointerException nulle){
			System.out.println("Worker Exp");
		}
		
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == textFieldCantidad) {
			try{
				textFieldCantidad.setBackground(Color.white);
				textFieldCantidad.setEditable(false);
			} catch (NullPointerException nulle){
				System.out.println("Catchh ");
			}
			
		}
		
		if (e.getSource() == txtDescuentosManuales) {
			try{
				txtDescuentosManuales.setBackground(Color.white);
				txtDescuentosManuales.setEditable(false);
			} catch (NullPointerException nulle){
				System.out.println("Catchh ");
			}
			
			
		}
	}

	protected void escucharEventoEnter(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			buscar();
		}
	}
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE SE ENCARGAR DE CARGAR DESDE LA BASE LOCAL
	 * 			LAS JERARQUIA DE LOS MATERIALES Y LOS MATERIALES.
	 */
	private void cargarJerarquiaMateriales() throws IOException {
		indexTopCliente = new Indexador(Constante.PATH + Constante.TOPCLIENTE);
		indexTopCliente.reconstruirTopCliente(cliente.getStrIdCliente());
		indexTopCliente.closeIndexador();
		
		indexTopTipologia = new Indexador(Constante.PATH + Constante.TOPTIPOLOGIA);
		indexTopTipologia.reconstruirTopTipologia(cliente.getStrCodigoTipologia());
		indexTopTipologia.closeIndexador();
	}

	/* Verificar el Hilo*/
	private void invocarSimulacion() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					String condicion_pago = ((BeanCondicionPago) cmbCondPago.getSelectedItem()).getTxtZTERM();
					if(condicion_pago == null){
						return;
					}
					BeanDato usuario = Promesa.datose.get(0);
					
					if (usuario.getStrModo().equals("1")) {
						// DESDE SAP
						BeanPedido pedido = llenarEstructura(usuario, condicion_pago);
						BeanPedidoHeader h = pedido.getHeader();
						SPedidos objSAP = new SPedidos();
						try {
							List<String[]> items = objSAP.invocarSimulacion(h.getDOC_TYPE(), h.getSALES_ORG(),
									h.getDISTR_CHAN(), h.getDIVISION(), h.getSALES_GRP(), h.getSALES_OFF(),
									h.getREQ_DATE_H(), h.getPURCH_DATE(), h.getPMNTTRMS(), h.getDLV_BLOCK(),
									h.getPRICE_DATE(), h.getPURCH_NO_C(), h.getSD_DOC_CAT(), h.getDOC_DATE(),
									h.getBILL_DATE(), h.getSERV_DATE(), h.getCURRENCY(), h.getCREATED_BY(),
									h.getSHIP_TYPE(), pedido.getPartners().getHm(), pedido.getDetalles());
							if (items != null && !items.isEmpty()) {
								actualizarPreciosMateriales(items);
							}
						} catch (Exception e) {
							Mensaje.mostrarError(e.getMessage());
							bloqueador.dispose();
						}
					} else {
						java.awt.EventQueue.invokeLater(new Runnable() {
							public void run() {
								simulaPreciosDeMaterialesSQLite2();
								try{
									tblMateriales.updateUI();
								}catch (NullPointerException nulle){
									System.out.println("update UI");
								}								
								
							}
						});
					}
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

	protected void actualizarPreciosMateriales(final List<String[]> items) {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				for (String[] strings : items) {
					for (int i = 0; i < tblMateriales.getRowCount(); i++) {
						String codigoTabla = Util.completarCeros(8, tblMateriales.getValueAt(i, 0).toString());
						String strPorcentajeDescuento = tblMateriales.getValueAt(i, 11).toString();/*Marcelo Moyano*/
						String codigoSap = Util.completarCeros(8, strings[1]);
						if (codigoTabla.compareTo(codigoSap) == 0) {
							Double precio = 0d;
							Double precioiva = 0d;
							Double precioBase = 0d;
							Double porcentajeDescuento = 0d;/*Marcelo Moyano*/
							try {
								precio = Double.parseDouble(strings[8]);
							} catch (Exception e) {
								precio = 0d;
							}
							try {
								precioiva = Double.parseDouble(strings[10]);
							} catch (Exception e) {
								precioiva = 0d;
							}
							try {
								precioBase = Double.parseDouble(strings[7]);
							} catch (Exception e) {
								precioBase = 0d;
							}
							try {/*Marcelo Moyano*/
								porcentajeDescuento = Double.parseDouble(strPorcentajeDescuento);
							} catch (Exception e) {
								precioBase = 0d;
							}
							
							/*Marcelo Moyano CALCULO DE DESCUENTO MANUAL*/
//							precio = (precio * (100 - porcentajeDescuento))/100.0d;
							precio = calcularDescuentoManual(precio, porcentajeDescuento);
							
							final int value2 = i;
							final double pi = precioiva;
							final double p = precio;
							final double pb = precioBase;
							final double pd = porcentajeDescuento;
							final String cantidadConfirmada = strings[3];
							double cantConfirmada = 0d;
 							
							try {
								cantConfirmada = Double.parseDouble(cantidadConfirmada);
							} catch (Exception e) {
								cantConfirmada = 0d;
							}
							final double cc = cantConfirmada;
							java.awt.EventQueue.invokeLater(new Runnable() {
								public void run() {
									double tempPi = pi;
									if (cc != 0d) {
										tempPi /= cc;
									}
									double tempPb = pb;
									tempPi = Double.parseDouble(Util.formatearNumero(tempPi));
									tempPb = Double.parseDouble(Util.formatearNumero(tempPb));
//									tblMateriales.setValueAt(pb, value2, 7);
//									tblMateriales.setValueAt((tempPi + tempPb), value2, 8);
									tblMateriales.setValueAt(calcularDescuentoManual(pb, pd), value2, 7);
									tblMateriales.setValueAt(calcularDescuentoManual((tempPi + tempPb), pd), value2, 8);
									tblMateriales.setValueAt(cantidadConfirmada, value2, 12);
									tblMateriales.setValueAt(p, value2, 14);
								}
							});
						}
					}
				}
			}
		});
	}

	protected Double calcularDescuentoManual(Double precio,
			Double porcentajeDescuento) {
		// TODO Auto-generated method stub
		Double descuento = 0d;
		descuento = (precio * (100 - porcentajeDescuento))/100.0d;
		return descuento;
	}

	private BeanPedido llenarEstructura(BeanDato usuario, String condicion_pago) {
		BeanPedidoHeader header;
		BeanPedidoPartners partners;
		List<BeanPedidoDetalle> detalles;
		BeanPedido pedido = new BeanPedido();
		pedido = new BeanPedido();
		header = new BeanPedidoHeader();
		partners = new BeanPedidoPartners();
		java.util.Date fechaActual = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		String fecha = sdf.format(fechaActual);
		String bloqueoEntrega = "";
		String numeroPedidoCliente = "";
		String destinatario = "";
		BeanConexion cn = new BeanConexion();

		bloqueoEntrega = contenedor.getBloqueoEntrega();
		numeroPedidoCliente = contenedor.getNumeroPedidoCliente();
		destinatario = contenedor.getDestinatario();

		String DOC_TYPE = contenedor.getTipoDocumento();
		String SALES_ORG = contenedor.getOrganizacionVentas();
		String DISTR_CHAN = contenedor.getCanalDistribucion();
		String DIVISION = contenedor.getCodigoSector();
		String SALES_GRP = "";
		String SALES_OFF = "";

		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(agenda.getStrCodigoCliente());

		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}
		if ((SALES_ORG == null || SALES_ORG.isEmpty()) || (DISTR_CHAN == null
				|| DISTR_CHAN.isEmpty()) || (DIVISION == null || DIVISION.isEmpty())) {
			SALES_ORG = cliente.getStrCodOrgVentas();
			DISTR_CHAN = cliente.getStrCodCanalDist();
			DIVISION = cliente.getStrCodSector();
		}

		String date = fecha;
		// String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicion_pago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = numeroPedidoCliente;
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		String CREATED_BY = cn.getStrUsuarioSAP();
		String SHIP_TYPE = "01";
		header.setDOC_TYPE(DOC_TYPE);
		header.setSALES_ORG(SALES_ORG);
		header.setDISTR_CHAN(DISTR_CHAN);
		header.setDIVISION(DIVISION);
		header.setSALES_GRP(SALES_GRP);
		header.setSALES_OFF(SALES_OFF);
		header.setREQ_DATE_H(date);
		header.setPURCH_DATE(PURCH_DATE);
		header.setPMNTTRMS(PMNTTRMS);
		header.setDLV_BLOCK(DLV_BLOCK);
		header.setPRICE_DATE(PRICE_DATE);
		header.setPURCH_NO_C(PURCH_NO_C);
		// header.setPURCH_NO_S(PURCH_NO_S);
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setStrCodCliente(agenda.getStrCodigoCliente());
		header.setStrCliente(agenda.getStrNombreCliente());
		header.setStrCodVendedor(usuario.getStrCodigo());
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, agenda.getStrCodigoCliente()));
		hm.put("WE", Util.completarCeros(10, destinatario));
		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		detalles = new ArrayList<BeanPedidoDetalle>();
		TableModel modelo = tblMateriales.getModel();
		for (int i = 0; i < modelo.getRowCount(); i++) {
//			String material = tblMateriales.getValueAt(i, 0).toString();
			String material = modelo.getValueAt(i, 0).toString();
			String cantidad = "";
			try {
				cantidad = ""+ Integer.parseInt(tblMateriales.getValueAt(i, 10).toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				cantidad = "0";
			}
			if (cantidad.compareTo("0") != 0) {
				BeanPedidoDetalle detalle = new BeanPedidoDetalle();
				String posicion = Util.completarCeros(6, "" + ((i + 1) * 10));
				detalle.setPosicion(posicion);
				detalle.setMaterial(material);
				detalle.setCantidad(cantidad);
				detalle.setPorcentajeDescuento("" + modelo.getValueAt(i, 11));
				detalles.add(detalle);
			}
		}
		pedido.setHeader(header);
		pedido.setPartners(partners);
		pedido.setDetalles(detalles);
		return pedido;
	}

	private void cmbTipoConsultaActionPerformed(java.awt.event.ActionEvent evt) {
		tipo = cmbTipoConsulta.getSelectedIndex();
		String mensaje = "";
		/*
		 * CONSULTA DINAMICA
		 * LIMPIA LOS COMBOBOX Y LAS LISTAS
		 */
		cmbDescripcionLarga.removeAllItems();
		cmbDivision.removeAllItems();
		txtDivisionEditor.setText("");
		strDivision = "";
		uno.clear();
		cmbCategoria.removeAllItems();
		cmbFamilia.removeAllItems();
		cmbLinea.removeAllItems();
		cmbGrupo.removeAllItems();
		tipoMaterial = "";
		txtDescripcionCorta.setText("");
		strMaterial = "";
		strCategoria = "";
		listaMaterialesABuscar.clear();
		materialese.clear();
		tblMateriales.removeAll();
		
		try{
			llenarTabla();
		}catch (NullPointerException nulle){
			System.out.println("null llenar tabla");
		}
		
		switch (tipo) {
		case 0: // Por material
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
			strPath = Constante.PATH + Constante.MATERIAL;
			break;
		case 1: // Por cliente
			mensaje = cliente.getStrIdCliente() + "-"+ cliente.getStrNombreCliente().toUpperCase();
			cambiarVisibilidadLimitadorRegistros(true);
			strPath = Constante.PATH + Constante.TOPCLIENTE;
			buscar();
			break;
		case 2: // Por tipologia
			mensaje = cliente.getStrCodigoTipologia() + "-" + cliente.getStrDescripcionTipologia();
			cambiarVisibilidadLimitadorRegistros(true);
			strPath = Constante.PATH + Constante.TOPTIPOLOGIA;
			buscar();
			break;
		case 3:// Material Nuevo
			mensaje = cliente.getStrIdCliente() + "-"+ cliente.getStrNombreCliente().toUpperCase();
			cambiarVisibilidadLimitadorRegistros(false);
			strPath = Constante.PATH + Constante.MATERIAL_NUEVO;
			buscar();
			break;
		case 4://Venta Cruzada
			mensaje = cliente.getStrIdCliente() + "-" + cliente.getStrNombreCliente().toUpperCase();
			cambiarVisibilidadLimitadorRegistros(true);
			strPath = Constante.PATH + Constante.VENTACRUZADA;
			buscar();
			break;
		case 5:// descuento politica
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
			strPath = Constante.PATH + Constante.DESCUENTOPOLITICA;
			break;
		case 6:// descuentos manuales
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
				strPath = Constante.PATH + Constante.DESCUENTOMANUAL;
			break;
		default:
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
			break;
		}
		lblCliente.setText(mensaje);
	}
	
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

    	jPanelCabecera = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTipoConsulta = new javax.swing.JLabel();
        cmbTipoConsulta = new javax.swing.JComboBox();
        lblCliente = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox();
        lblCondPago = new javax.swing.JLabel();
        cmbCondPago = new javax.swing.JComboBox();
        btnCerrar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblDescripcionLarga = new javax.swing.JLabel();
        cmbDescripcionLarga = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        btnBuscarVoz = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblDivision = new javax.swing.JLabel();
        cmbDivision = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        lblCategoria = new javax.swing.JLabel();
        lblFamilia = new javax.swing.JLabel();
        lblLinea = new javax.swing.JLabel();
        lblGrupo = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox();
        cmbFamilia = new javax.swing.JComboBox();
        cmbLinea = new javax.swing.JComboBox();
        cmbGrupo = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        lblConfirmacion = new javax.swing.JLabel();
        scrollMateriales = new javax.swing.JScrollPane();
//        tblMateriales = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnPasarPedido = new javax.swing.JButton();
        btnInicioPagina = new javax.swing.JButton();
        btnPaginaAnterior = new javax.swing.JButton();
        lblPaginas = new javax.swing.JLabel();
        btnPaguinaSiguiente = new javax.swing.JButton();
        btnPaginaFin = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblCantidadRegistros = new javax.swing.JLabel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//		setResizable(false);

        /*
         * @author		Marcelo Moyano
         * @code 		PRO-2013-0088
         * @title		Ajustes a Consulta Dynamica Opcion B
         * 
         */
        ModeloConsultaDinamica2 modeloTablaItems = new ModeloConsultaDinamica2();
        DefaultTableModel dm = new DefaultTableModel();
        dm.setColumnIdentifiers(modeloTablaItems.identificadorClumnas());
        tblMateriales = new javax.swing.JTable(dm) {
        	private SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
        	private List<BeanMaterial> materiaTopCliente = sqlMateriales.obtenerMaterialesTopCliente(cliente.getStrIdCliente());
        	private int cant=0;
        	protected JTableHeader createDefaultTableHeader() {
        		return new GroupableTableHeader(columnModel);
        	}
        	@Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                String IdMaterial = (String)getValueAt(row, 0);
                if(col==0)
                	cant = sqlMateriales.getCountRowNuevo(IdMaterial);
                String Status = (String)getValueAt(row,17);
                if ("NOT".equals(Status)&&col!=10&&col!=11&&cant>0) {
                    try{
                    	c.setBackground(Color.ORANGE);
                    }catch (NullPointerException nulle){
                    	System.out.println("nulle");
                    }              	                	
                }
                boolean flag = false;
                for(BeanMaterial mate : materiaTopCliente){
                	if(mate.getIdMaterial().equals(IdMaterial)){
                		flag = true;
                	}
                }
                if ("NOT".equals(Status)&&col!=10&&col!=11&&cmbTipoConsulta.getSelectedIndex()==2&&!flag) {
                    try{
                    	c.setBackground(new Color(205,97,85));
                    }catch (NullPointerException nulle){
                    	System.out.println("nulle");
                    }              	                	
                }	
                return c;
            }
        };
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTipoConsulta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoConsulta.setText("Tipo de Consulta:");
        
        cmbTipoConsulta.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"Por Material", "Top x Cliente", "Top x Tipologia", "Material Nuevo","Venta Cruzada"}));
        
        cmbTipoConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoConsultaActionPerformed(evt);
            }
        });
        
        
        
        lblTipo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipo.setText("Tipo :");
        lblTipo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        lblCondPago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCondPago.setText("Cond. Pago *:");

        cmbCondPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                cmbCondPagoActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTipoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbTipoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181)
                .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCondPago, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbCondPago, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(220, 220, 220)
                    .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(681, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCondPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCondPago, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(lblCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        lblDescripcionLarga.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescripcionLarga.setText("Busqueda: ");
        lblDescripcionLarga.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        cmbDescripcionLarga.setEditable(true);
        cmbDescripcionLarga.setMaximumSize(new java.awt.Dimension(300, 20/*32767*/));
        cmbDescripcionLarga.setMinimumSize(new java.awt.Dimension(100,20));
//        cmbDescripcionLarga.setPreferredSize(new java.awt.Dimension(200, 20));
        
        /*
         * @author		MARCELO MOYANO
         */
        txtDescripcionCorta = (JTextField) cmbDescripcionLarga.getEditor().getEditorComponent();
		txtDescripcionCorta.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtDescripcionCortaKeyReleased(evt);
			}
		});
		txtDescripcionCorta.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (activarBusqueda) {
					txtDescripcionCorta.selectAll();
					esTextoSelecionado = true;
					cmbDescripcionLarga.repaint();
//					buscadorMaterial(txtDescripcionCorta.getText(), indexMaterial);
					if(tipo!=3)
						buscadorMaterial(txtDescripcionCorta.getText());
				}
				activarBusqueda = true;
			}

			public void focusLost(FocusEvent evt) {
				selectionModel.setSelectionInterval(0, 0);
				if(materialese.size() > 0){
					tblMateriales.editCellAt(0, 10);
					tblMateriales.transferFocus();
					textFieldCantidad.requestFocus();
				}
			}
		});
		
		cmbDescripcionLarga.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbDescripcionLargaMouseClicked(evt);
			}
		});
		
        /**********************/

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnBuscarVoz.setText("Voz");
        btnBuscarVoz.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDescripcionLarga, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbDescripcionLarga, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarVoz, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDescripcionLarga, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cmbDescripcionLarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBuscar)
                .addComponent(btnBuscarVoz))
        );

        jPanel1.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 51));

        jPanel6.setLayout(new java.awt.GridLayout(2, 0));

        lblDivision.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDivision.setText("División");
        lblDivision.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel6.add(lblDivision);

        cmbDivision.setEditable(true);
        cmbDivision.setToolTipText("");
//        cmbDivision.setMaximumSize(new java.awt.Dimension(100, 20));
//        cmbDivision.setMinimumSize(new java.awt.Dimension(50, 20));
//        cmbDivision.setPreferredSize(new java.awt.Dimension(100, 20));
        /*
         * @author		MARCELO MOYANO
         */
        txtDivisionEditor = (JTextField) cmbDivision.getEditor().getEditorComponent();
		txtDivisionEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtDivisionEditorKeyReleased(evt);
			}
		});
        
        jPanel6.add(cmbDivision);

        jPanel4.setMinimumSize(new java.awt.Dimension(100, 400));
        jPanel4.setLayout(new java.awt.GridLayout(2, 3, 7, 0));

        lblCategoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCategoria.setText("Categoria");
        jPanel4.add(lblCategoria);

        lblFamilia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFamilia.setText("Familia");
        jPanel4.add(lblFamilia);

        lblLinea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLinea.setText("Linea");
        jPanel4.add(lblLinea);

        lblGrupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGrupo.setText("Grupo");
//        jPanel4.add(lblGrupo);

        cmbCategoria.setEditable(true);
        cmbCategoria.setToolTipText("");
//        cmbCategoria.setMaximumSize(new java.awt.Dimension(50, 20));
//        cmbCategoria.setMinimumSize(new java.awt.Dimension(50, 20));
//        cmbCategoria.setPreferredSize(new java.awt.Dimension(200, 20));
        /*
         * Marcelo Moyano
         */
        txtCategoriaEditor = (JTextField) cmbCategoria.getEditor().getEditorComponent();
		txtCategoriaEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtCategoriaEditorKeyReleased(evt);
			}
		});
        jPanel4.add(cmbCategoria);

        cmbFamilia.setEditable(true);
        cmbFamilia.setToolTipText("");
//        cmbFamilia.setMaximumSize(new java.awt.Dimension(50, 20));
//        cmbFamilia.setMinimumSize(new java.awt.Dimension(50, 20));
//        cmbFamilia.setPreferredSize(new java.awt.Dimension(200, 20));
        /* Marcelo Moyano */
        txtFamiliaEditor = (JTextField) cmbFamilia.getEditor().getEditorComponent();
        cmbFamilia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbFamiliaActionPerformed(evt);
			}
		});
		
		txtFamiliaEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFamiliaEditorKeyReleased(evt);
			}
		});
        
		txtFamiliaEditor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbFamiliaFocusGained(evt);
            }
        });
        jPanel4.add(cmbFamilia);

        cmbLinea.setEditable(true);
        cmbLinea.setToolTipText("");
//        cmbLinea.setMaximumSize(new java.awt.Dimension(50, 20));
//        cmbLinea.setMinimumSize(new java.awt.Dimension(50, 20));
//        cmbLinea.setPreferredSize(new java.awt.Dimension(200, 20));
        /* Marcelo Moyano*/
        txtLineaEditor = (JTextField) cmbLinea.getEditor().getEditorComponent();
        txtLineaEditor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbLineaFocusGained(evt);
            }
        });
        cmbLinea.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbLineaActionPerformed(evt);
			}
		});
		
		txtLineaEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtLineaEditorKeyReleased(evt);
			}
		});
        jPanel4.add(cmbLinea);
        
        
        jPanel9.setMaximumSize(new java.awt.Dimension(100, 32767));
        jPanel9.setMinimumSize(new java.awt.Dimension(36, 40));
        jPanel9.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel9.setLayout(new java.awt.GridLayout(2, 0));

//        lblGrupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        lblGrupo.setText("Grupo");
//        lblGrupo.setMaximumSize(new java.awt.Dimension(25, 14));
//        lblGrupo.setMinimumSize(new java.awt.Dimension(25, 14));
//        lblGrupo.setPreferredSize(new java.awt.Dimension(25, 14));
        jPanel9.add(lblGrupo);

        cmbGrupo.setEditable(true);
        cmbGrupo.setToolTipText("");
//        cmbGrupo.setMaximumSize(new java.awt.Dimension(50, 20));
//        cmbGrupo.setMinimumSize(new java.awt.Dimension(50, 20));
//        cmbGrupo.setPreferredSize(new java.awt.Dimension(50, 20));
        /* Marcelo Moyano */
        cmbGrupo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbGrupoActionPerformed(evt);
			}
		});
		txtGrupoEditor = (JTextField) cmbGrupo.getEditor().getEditorComponent();
		txtGrupoEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtGrupoEditorKeyReleased(evt);
			}
		});
		
		txtGrupoEditor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtGrupoFocusGained(evt);
			}
		});
//        jPanel4.add(cmbGrupo);
        jPanel9.add(cmbGrupo);
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(154, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(724, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            );

        javax.swing.GroupLayout jPanelCabeceraLayout = new javax.swing.GroupLayout(jPanelCabecera);
        jPanelCabecera.setLayout(jPanelCabeceraLayout);
        jPanelCabeceraLayout.setHorizontalGroup(
                jPanelCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelCabeceraLayout.createSequentialGroup()
                    .addGroup(jPanelCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(103, 103, 103))
            );
            jPanelCabeceraLayout.setVerticalGroup(
                jPanelCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelCabeceraLayout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

        jPanel5.setLayout(new java.awt.BorderLayout());

        lblConfirmacion.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel7.add(lblConfirmacion, java.awt.BorderLayout.NORTH);

        tblMateriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollMateriales.setViewportView(tblMateriales);

        jPanel5.add(scrollMateriales, java.awt.BorderLayout.CENTER);

        btnPasarPedido.setText("Pasar Pedido");
        btnPasarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnPasarPedidoActionPerformed(evt);
            }
        });
        jPanel7.add(btnPasarPedido);

        btnInicioPagina.setText("|<");
        btnInicioPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioPaginaActionPerformed(evt);
            }
        });
        jPanel7.add(btnInicioPagina);

        btnPaginaAnterior.setText("<");
        btnPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaAnteriorActionPerformed(evt);
            }
        });
        jPanel7.add(btnPaginaAnterior);

        lblPaginas.setText("0/0");
        lblPaginas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.add(lblPaginas);

        btnPaguinaSiguiente.setText(">");
        btnPaguinaSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaguinaSiguienteActionPerformed(evt);
            }
        });
        jPanel7.add(btnPaguinaSiguiente);

        btnPaginaFin.setText(">|");
        btnPaginaFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaFinActionPerformed(evt);
            }
        });
        jPanel7.add(btnPaginaFin);

        jPanel5.add(jPanel7, java.awt.BorderLayout.NORTH);

        lblCantidadRegistros.setText("Número de registros: 0");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1063, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblCantidadRegistros, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblCantidadRegistros, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCabecera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCabecera, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
        );

        pack();
    	
    }// </editor-fold>

    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void txtGrupoFocusGained(FocusEvent evt) {
		if (activarGrupo) {
			strGrupo = "";
			txtGrupoEditor.setText("");
			cinco.clear();
			cmbGrupo.removeAllItems();
			cmbGrupo.setModel(new DefaultComboBoxModel(new String[] {}));
			cmbGrupo.setRenderer(new MyComboBoxRenderer(cinco));
			cmbGrupo.repaint();
		}
		activarGrupo = true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void cmbLineaFocusGained(FocusEvent evt) {
		if (activarLinea) {
			strLinea = "";
			txtLineaEditor.setText("");
			cuatro.clear();
			cmbLinea.removeAllItems();
			cmbLinea.setModel(new DefaultComboBoxModel(new String[] {}));
			cmbLinea.setRenderer(new MyComboBoxRenderer(cuatro));
			cmbLinea.repaint();
		}
		activarLinea = true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void cmbFamiliaFocusGained(FocusEvent evt) {
		if (activarFamilia) {
			strFamilia = "";
			txtFamiliaEditor.setText("");
			txtFamiliaEditor.requestFocus();
			tres.clear();
			cmbFamilia.removeAllItems();
			cmbFamilia.setModel(new DefaultComboBoxModel(new String[] {}));
			cmbFamilia.setRenderer(new MyComboBoxRenderer(tres)); 
			cmbFamilia.repaint();
		}
		activarFamilia = true;
	}

	protected void cmbDescripcionLargaFocusLost(FocusEvent evt) {
		selectionModel.setSelectionInterval(0, 0);
		if(materialese.size() > 0){
			tblMateriales.editCellAt(0, 10);
			tblMateriales.transferFocus();
			textFieldCantidad.requestFocus();
		}
	}

	protected void cmbDescripcionLargaFocusGained(FocusEvent evt) {
		if (activarBusqueda) {
			txtDescripcionCorta.selectAll();
			esTextoSelecionado = true;
			cmbDescripcionLarga.repaint();
			buscadorMaterial(txtDescripcionCorta.getText());
		}
		activarBusqueda = true;
	}

	protected void tablac(ActionListener evt){
		
		tblMateriales.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (tblMateriales.getSelectedRow() > -1) {
                    // print first column value from selected row
                    //System.out.println(tblMateriales.getValueAt(tblMateriales.getSelectedRow(), 0).toString());
                }
            }
        });
		
	}
	
	protected void cmbTipoActionPerformed(ActionEvent evt) {
		int tipo = cmbTipo.getSelectedIndex();
		tipoMaterial = obtenerType(tipo);
	}

	//** Evento**//
	protected void cmbDescripcionLargaMouseClicked(java.awt.event.ActionEvent evt) {
		final int fila = cmbDescripcionLarga.getSelectedIndex();
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		if (fila > 0) {
			BeanMaterial beanMaterial = (BeanMaterial) cmbDescripcionLarga.getSelectedItem();
			materiales.add(beanMaterial);
		}
		materialese = materiales;
		if(tipo!=3)
		try{
			llenarTabla();
		}catch (NullPointerException nulle){
			System.out.println("null llenar tabla");
		}	
		
	}
	
	//** Evento **//
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void txtDescripcionCortaKeyReleased(KeyEvent evt) {
		char c = evt.getKeyChar();
		String strTmp2 = "";
		String strTmp1 = "";
		if (caracterEspecial(c)) {
			if (Character.isDigit(c) || Character.isLetter(c) || c == ' ' || c == '/') {
				try {
					if(txtDescripcionCorta.getCaretPosition() == 1){
						if(strMaterial.length() > 1){
							strMaterial = "";
							txtDescripcionCorta.setText("");
							listaMaterialesABuscar.clear();
							cmbDescripcionLarga.removeAllItems();
							cmbDescripcionLarga.setModel(new DefaultComboBoxModel(new String[] {}));
							cmbDescripcionLarga.setRenderer(new MyComboBoxRenderer2(listaMaterialesABuscar));
						}
					}
					if(strMaterial.length() < txtDescripcionCorta.getCaretPosition()){
						strMaterial = strMaterial + c;
					}else if (strMaterial.length() == txtDescripcionCorta.getCaretPosition()){
						strTmp1 = strMaterial.substring(0, txtDescripcionCorta.getCaretPosition() );
						strTmp2 = strMaterial.substring(txtDescripcionCorta.getCaretPosition() , strMaterial.length());
						strMaterial = strTmp1 + c + strTmp2;
					}else {
						strTmp1 = strMaterial.substring(0, txtDescripcionCorta.getCaretPosition() - 1);
						strTmp2 = strMaterial.substring(txtDescripcionCorta.getCaretPosition() -1, strMaterial.length());
						strMaterial = strTmp1 + c + strTmp2;
					}
				} catch (Exception e) {
					// TODO: handle exception
					strMaterial = strMaterial + c;
				}
				if(tipo != 3){
					buscarMaterial(strMaterial);
					//buscar();
				}else{
					buscarnuevo();
				}
			}
			if (c == '\b' && !strMaterial.equals("")) {
				if(txtDescripcionCorta.getCaretPosition() == 0){
					strMaterial = "";
					txtDescripcionCorta.setText("");
					listaMaterialesABuscar.clear();
					cmbDescripcionLarga.removeAllItems();
					cmbDescripcionLarga.setModel(new DefaultComboBoxModel(new String[] {}));
					cmbDescripcionLarga.setRenderer(new MyComboBoxRenderer2(listaMaterialesABuscar));
				}else {
					strMaterial = txtDescripcionCorta.getText();
					strTmp2 = strMaterial.substring(txtDescripcionCorta.getCaretPosition(), strMaterial.length());
					strMaterial = strMaterial.substring(0, txtDescripcionCorta.getCaretPosition()) + strTmp2;
				}
				txtDescripcionCorta.setText(strMaterial);
				txtDescripcionCorta.select(strMaterial.length(), strMaterial.length());
				buscarMaterial(strMaterial);
			}
			if (c == '\n' && !strMaterial.equals("")) {
				String strTextobuscar = cmbDescripcionLarga.getSelectedItem().toString();
				txtDescripcionCorta.setText(strTextobuscar);
				buscar2();
				activarBusqueda = false;
			}else if(c == '\n'){
				buscar();
			}
			if(c == '' && !strMaterial.equals("")){
				strMaterial = txtDescripcionCorta.getText();
			}
			txtDescripcionCorta.select(txtDescripcionCorta.getCaretPosition() - strTmp2.length(), txtDescripcionCorta.getCaretPosition() - strTmp2.length());
		}
	}
	
	private boolean caracterEspecial(char caracter) {
		boolean resultado = false;
		if (caracter != '+' && caracter != '*') {
			resultado = true;
		}
		return resultado;
	}

	private void buscarMaterial(String strTextoAbuscar) {
		if(tipo!=3)
		buscadorMaterialXTipo(strTextoAbuscar);
	}

	private void buscadorMaterialXTipo(String strTextoAbuscar) {
		cmbDescripcionLarga.hidePopup();
		buscadorMaterial(strTextoAbuscar);
		cmbDescripcionLarga.showPopup();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buscadorMaterial(String strTextoAbuscar) {
		cmbDescripcionLarga.removeAllItems();
		listaMaterialesABuscar.clear();
		Buscador buscarMaterial;
		BeanMaterial material;
		if (!strTextoAbuscar.equals("") && strTextoAbuscar.length() > 0) {
			buscarMaterial = new Buscador(strPath);
			String strCodigo = escogerJerarquia();
			String[] cadena = strTextoAbuscar.split(" ");
			String strtmp = "";
			for (int i = 0; i < cadena.length; i++) {
				if (!cadena[i].equals(""))
					strtmp += cadena[i] + "* ";
			}
			try {
				ScoreDoc[] scoreResultado = buscarMaterial.funcionBuscador(strtmp.trim());
				if (scoreResultado != null && scoreResultado.length > 0) {
					for (int i = 0; i < scoreResultado.length; i++) {
						material = new BeanMaterial();
						int iDoc = scoreResultado[i].doc;
						Document d = buscarMaterial.getIbuscador().doc(iDoc);
						if (d.get("PRDHA").startsWith(strCodigo) && d.get("tipoMaterial").startsWith(tipoMaterial) && validarPalabra(d.get("Busqueda"), cadena)) {
							material.setIdMaterial(d.get("Codigo"));
							material.setText_line(d.get("Descrip Larga"));
							material.setDescripcion(d.get("Descrip Corta"));
							material.setNormt(d.get("NORMT"));
							material.setPrice_1(d.get("Precio"));
							material.setPrdha(d.get("PRDHA"));
							material.setBooleanToString(true);
							try {
								material.setDblAcumulado(Double.parseDouble(d.get("Acumulada")));
							} catch (Exception e) {
								material.setDblAcumulado(0d);
							}
							material.setUn(d.get("Un"));

							try {
								material.setDblPromedio(Double.parseDouble(d.get("Promedio")));
							} catch (Exception e) {
								material.setDblPromedio(0d);
							}
							material.setTypeMat(d.get("tipoMaterial"));
							material.setStrBusqueda(d.get("Busqueda"));
							listaMaterialesABuscar.add(material);
						}

					}
					comodinMaterila.setText_line(strTextoAbuscar);
					comodinMaterila.setDescripcion(strTextoAbuscar);
					comodinMaterila.setStrBusqueda(strTextoAbuscar);
					comodinMaterila.setIdMaterial(strTextoAbuscar);
					comodinMaterila.setNormt(strTextoAbuscar);
					comodinMaterila.setBooleanToString(true);
					listaMaterialesABuscar.add(0, comodinMaterila);
				} else {
					comodinMaterila.setText_line(strTextoAbuscar);
					comodinMaterila.setDescripcion(strTextoAbuscar);
					comodinMaterila.setIdMaterial(strTextoAbuscar);
					comodinMaterila.setStrBusqueda(strTextoAbuscar);
					comodinMaterila.setNormt(strTextoAbuscar);
					comodinMaterila.setBooleanToString(true);
					listaMaterialesABuscar.add(0, comodinMaterila);
				}
				cmbDescripcionLarga.setModel(new DefaultComboBoxModel(listaMaterialesABuscar.toArray()));
				cmbDescripcionLarga.setRenderer(new MyComboBoxRenderer2(listaMaterialesABuscar));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean validarPalabra(String string, String[] cadena) {
		if (cadena.length > 0) {
			for (int i = 0; i < cadena.length; i++) {
				if (string.indexOf(cadena[i].toUpperCase().trim()) == -1) {
					return false;
				}
			}
		}
		return true;
	}

	private void btnInicioPaginaActionPerformed(java.awt.event.ActionEvent evt) {
		calcularPaginacion(1);
	}

	private void btnPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {
		calcularPaginacion(paginaActual - 1);
	}

	private void btnPaguinaSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
		calcularPaginacion(paginaActual + 1);
	}

	private void btnPaginaFinActionPerformed(java.awt.event.ActionEvent evt) {
		calcularPaginacion(cantidadPaginas);
	}

	//** Evento **//
	protected void enmascararEventoTeclaTab() {
		tblMateriales.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		((InputMap) UIManager.get("Table.ancestorInputMap")).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");

		tblMateriales.getActionMap().put("tab", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int fila = tblMateriales.getSelectedRow();
				int numeroFilas = tblMateriales.getModel().getRowCount();
				if (fila < numeroFilas - 1) {
					fila++;
					selectionModel.setSelectionInterval(fila, fila);
					tblMateriales.editCellAt(fila, 4);
					textFieldCantidad.requestFocusInWindow();
					textFieldCantidad.setEditable(true);
					textFieldCantidad.selectAll();
				}
			}
		});
	}

	protected void btnPasarPedidoActionPerformed(ActionEvent evt) {
		eventoPasarPedido();
	}
	
	public void eventoPasarPedido() {
		try {
			pasarPedidos();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}
	
	public void pasarPedidos(){
		try {
			int fila = tblMateriales.getSelectedRow();
			for (int idx = 0; idx < tblMateriales.getRowCount(); idx++) {
				String cantidad = tblMateriales.getValueAt(idx, 10).toString();
				String strDescuencoManual = tblMateriales.getValueAt(idx, 11).toString();
				if (idx == fila) {
					cantidad = this.textFieldCantidad.getText();
					this.textFieldCantidad.setText("0");
					strDescuencoManual = this.txtDescuentosManuales.getText();
					this.txtDescuentosManuales.setText("0.00");
				}
				if (cantidad.compareTo("0")!= 0){
					String material = tblMateriales.getValueAt(idx, 0).toString();
					String denominacion = tblMateriales.getValueAt(idx, 1).toString();
					String un = tblMateriales.getValueAt(idx, 2).toString();
					String precioNeto = tblMateriales.getValueAt(idx, 7).toString();
					String valorNeto = tblMateriales.getValueAt(idx, 14).toString();
					String tipoMaterial = tblMateriales.getValueAt(idx, 4).toString();
					tblMateriales.setValueAt("0", idx, 10);
					tblMateriales.setValueAt("0.00", idx, 11);
					Double price = 0d;
					try {
						price = Double.parseDouble(precioNeto);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						price = 0d;
					}
					Double price2 = 0d;
					try {
						price2 = Double.parseDouble(valorNeto);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						price2 = 0d;
					}
					Item item = new Item();
					item.setCodigo(material);
					item.setCantidad(cantidad);
					item.setCantidadConfirmada(cantidad);
					item.setStock(tblMateriales.getValueAt(idx, 13).toString());
					item.setUnidad(un);
					item.setPorcentajeDescuento("0.0");
					item.setPorcentajeDescuento(strDescuencoManual);
					item.setDenominacion(denominacion);
					item.setPrecioNeto(price);
					item.setValorNeto(price2);
					item.setTipo(0); // Item normal
					item.setTipoMaterial(tipoMaterial);
					item.setEditable(true);
					item.setSimulado(false);
					item.setStrFech_Ing("");
					int respuesta = contenedor.agregarMaterial(item, -2);
	
					if (respuesta > 0) {
						lblConfirmacion.setText("El pedido se ha agregado correctamente");
						lblConfirmacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check.png"))); // NOI18N
					} else {
						lblConfirmacion.setText("<html><font color='red'>No se pudo agregar el material " + item.getCodigo() + "</font></html>");
						lblConfirmacion.setIcon(null); // NOI18N
					}
				}
				tblMateriales.setValueAt("0", idx, 10);
				tblMateriales.setValueAt("0.00", idx, 11);
				tblMateriales.setValueAt("0.00", idx, 7);
				tblMateriales.setValueAt("0.00", idx, 8);
				tblMateriales.setValueAt("0.0", idx, 11);
				tblMateriales.setValueAt("0.00", idx, 12);
				tblMateriales.setValueAt("0.00", idx, 14);
				tblMateriales.updateUI();
			}
			contenedor.actualizarPosiciones();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
			
	}

	//** Evento **//
	private void llenarTabla() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ModeloConsultaDinamica2 modeloTablaItems = new ModeloConsultaDinamica2();
				tblMateriales.setModel(modeloTablaItems);
//				agruparColumnas();
				for (int i = 0; i < materialese.size(); i++) {
					Item it = new Item();
					BeanMaterial beanMaterial = materialese.get(i);
					String price = "" + beanMaterial.getPrice_1();
					Double precio = 0d;
					if (!price.isEmpty()) {
						precio = Double.parseDouble(price);
					}
					it.setCodigo("" + beanMaterial.getIdMaterial());
					if (beanMaterial.getText_line() != null && beanMaterial.getText_line().trim().equals("")) {
						it.setDenominacion("" + beanMaterial.getDescripcion());
					} else {
						it.setDenominacion("" + beanMaterial.getText_line());
					}
					it.setUnidad("" + beanMaterial.getUn());
					it.setMarca("" + beanMaterial.getNormt());
					it.setCantidad("0");
					it.setPrecioNeto(0d);
					it.setPrecioIVA(0d);
					it.setPrecioCompra(0d);
					it.setPrecioLista(precio);
					it.setTipoMaterial("" + beanMaterial.getTypeMat());
					it.setDenominacionCorta("" + beanMaterial.getDescripcion());
					it.setStrValorAcumulado("" + beanMaterial.getDblAcumulado());
					it.setStrValorPromedio("" + beanMaterial.getDblPromedio());
					it.setCantidadConfirmada("0.0");
					it.setPorcentajeDescuento("0.00");
					it.setDescuentosManuales("0.00");
					it.setFecha(beanMaterial.getZzordco()!=null?beanMaterial.getZzordco():"NOT");
					if(beanMaterial.getStrVentaReal()!=null){
						it.setDblValorReal(Double.valueOf(beanMaterial.getStrVentaReal()));
						Double dblCumpl =(it.getDblValorReal()/beanMaterial.getDblAcumulado())*100;
						it.setDblCumplimiento(dblCumpl);
					}
					if(beanMaterial.getCantSug()!=null){
						it.setStrCantFacturado("" + beanMaterial.getDblPromedio());
						it.setStrCantSugerido("" + beanMaterial.getCantSug());
						Double cant = (beanMaterial.getCantSug() - beanMaterial.getDblPromedio());
						it.setStrCantRestante("" + (cant<0d?0d:cant));
					}
					modeloTablaItems.agregarItem(it);
				}
				lblCantidadRegistros.setText("Número de registros: " + tblMateriales.getModel().getRowCount());
				int tipo = cmbTipoConsulta.getSelectedIndex();
				if (tipo == 1) {
					setAnchoColumnasMostrarAdicionales5();
				} else {
					setAnchoColumnas2();
				}
				if(tipo == 2){
					setAnchoColumnasMostrarAdicionales2();
				}
				if(tipo == 3){
					setAnchoColumnasMostrarAdicionales3();
				}
				if(tipo == 4){
					setAnchoColumnasMostrarAdicionales4();
				}
				agruparColumnas();
				tblMateriales.updateUI();
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaItems);
				tblMateriales.setRowSorter(sorter);
				
				try{
					escucharEventoTeclado();
					escucharEventoTecledoDEscuentosManuales();
				}catch (NullPointerException nulle){
					System.out.println("evento");
				}
				
				//escucharEventoClick();
				//** agrego evento de la tabla **//
			}
		});
	}
	
	private void escucharEventoClick() {
		
	}
	
	private void cmbFamiliaActionPerformed(java.awt.event.ActionEvent evt) {
		int index = cmbFamilia.getSelectedIndex();
		volverABuscar(index);
	}

	private void cmbLineaActionPerformed(java.awt.event.ActionEvent evt) {
		int index = cmbLinea.getSelectedIndex();
		volverABuscar(index);
	}
	
	private void cmbGrupoActionPerformed(java.awt.event.ActionEvent evt) {
		int index = cmbGrupo.getSelectedIndex();
		volverABuscar(index);
	}
	
	public void volverABuscar(int index){
		if(index > -1){
			String strTextoBuscar = txtDescripcionCorta.getText();
			if(strTextoBuscar.length() > 0){
				buscadorMaterial(strTextoBuscar);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		if(strMaterial.equals("")){
			listaMaterialesABuscar.clear();
			cmbDescripcionLarga.removeAllItems();
		}
		String strCodigo = escogerJerarquia();
		if(strCodigo.equals("")){
			buscadorMaterial(strMaterial);
		}
		ComboBoxModel materialesModelo = cmbDescripcionLarga.getModel();
		if (materialesModelo.getSize() > 0) {
			buscar2();
		} else {
			buscar();
		}
	}
	
	private void setAnchoColumnas2() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblMateriales.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblMateriales.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 300;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 2:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 10:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 12:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 13:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 14:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 15:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 16:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 17:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 18:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 19:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 20:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 21:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 22:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}
	private void setAnchoColumnasMostrarAdicionales3() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblMateriales.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblMateriales.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 270;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 2:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 10:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 12:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 13:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 14:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 15:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 16:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 17:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 18:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 19:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 20:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 21:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 22:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}
	private void setAnchoColumnasMostrarAdicionales2() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblMateriales.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblMateriales.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 200;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 2:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 20;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 90;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 90;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 90;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 10:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 12:
				anchoColumna = 45;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 13:
				//TODO
				anchoColumna = 65;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 14:
				anchoColumna = 55;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 15:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 16:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 17:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 18:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 19:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 20:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 21:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 22:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}
	private void setAnchoColumnasMostrarAdicionales5() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblMateriales.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblMateriales.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 200;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 2:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 20;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 90;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 90;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 90;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 10:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 12:
				anchoColumna = 45;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 13:
				//TODO
				anchoColumna = 65;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 14:
				anchoColumna = 55;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 15:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 16:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 17:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 18:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 19:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 20:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 21:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 22:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}
	private void setAnchoColumnasMostrarAdicionales4() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblMateriales.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblMateriales.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 200;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 2:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 100;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 70;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 70;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 70;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 5;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 10:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 12:
				anchoColumna = 45;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 13:
				//TODO
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 14:
				anchoColumna = 50;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 15:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 16:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 17:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 18:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 19:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 20:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 21:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 22:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}
	private void buscarnuevo(){
		
		Thread hilo = new Thread(){
			public void run(){
				try{
					lblConfirmacion.setText("");
					lblConfirmacion.setIcon(null);
					String mate = txtDescripcionCorta.getText();
					List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
					SqlDivisionImpl dao = new SqlDivisionImpl();
					materiales = dao.getMaterialNuevo(mate);
					materialese = materiales;
				}catch(Exception e){
					Util.mostrarExcepcion(e);
				}finally{
					
					try{
						llenarTabla();
					}catch (NullPointerException nulle){
						System.out.println("null llenar tabla");
					}
					
				}
			}
		};
		hilo.start();
		
	}
	private void buscar() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					Runtime.getRuntime().gc();
					lblConfirmacion.setText("");
					lblConfirmacion.setIcon(null); // NOI18N
					String strCodigo = escogerJerarquia();
					/*
					 * ORIGINAL
					 */
					
					List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
					SqlDivisionImpl dao = new SqlDivisionImpl();
					int tipo = cmbTipoConsulta.getSelectedIndex();
					int type = cmbTipo.getSelectedIndex();
					BeanMaterial beanMaterial;
					
					int index = cmbDescripcionLarga.getSelectedIndex();
					if (index > -1) {
						beanMaterial = listaMaterialesABuscar.get(index);
					} else if (listaMaterialesABuscar.size() > 0) {
						if(strMaterial.equals("")){
							listaMaterialesABuscar.clear();
							cmbDescripcionLarga.removeAllItems();
							beanMaterial = new BeanMaterial();
						}else {
							beanMaterial = listaMaterialesABuscar.get(0);
						}
					} else {
						beanMaterial = new BeanMaterial();
					}

					switch (tipo) {
					case 0:
						Object[] objetos = dao.buscarCantidadMateriales(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt());

						consulta = objetos[0].toString();
						cantidadRegistros = (Long) objetos[1];
						calcularPaginacion();
						materiales = dao.buscarMateriales(consulta, 1);
						break;
					case 1:
						materiales = dao.buscarMaterialesTopCliente(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt(),
								cliente.getStrIdCliente());
						break;
					case 2:
						materiales = dao.buscarMaterialesTopTipologia(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt(),
								cliente.getStrIdCliente());
						break;
					case 3:
						String mate = txtDescripcionCorta.getText();
						materiales = dao.getMaterialNuevo(mate);
						break;
					case 4:
						materiales = dao.buscarMaterialesVentaCruzada(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt(),
								cliente.getStrIdCliente());
						break;
					case 5:
						materiales = dao.buscarMaterialesDescuentoPolitica(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt());
						break;
					case 6:
						materiales = dao.buscarMaterialesDescuentoManual(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt());
						break;
					}
					materialese = materiales;
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					
					try{
						llenarTabla();
						bloqueador.dispose();
					}catch (NullPointerException nulle){
						System.out.println("null llenar tabla");
					}
					
				}
			}
		};
		
		try{
			hilo.start();
			bloqueador.setVisible(true);
		}catch (NullPointerException nulle){
			System.out.println("hilo");
		}
		
	}

	/*
	 * @autor	MARCELO MOYANO
	 * 
	 * 			VALIDA LAS JERARQUIA SELECCIONADA.
	 * 
	 * @return	strCodigo RETORNA EL CODIGO PRODH DE LAS
	 * 			JERARQUIA SELECCIONADA.
	 */
	protected String escogerJerarquia() {
		String strCodigo = "";
		BeanJerarquia bean = null;
		try {
			bean = (BeanJerarquia) cmbGrupo.getSelectedItem();
		} catch (Exception e) {
			bean = null;
		}
		if (bean == null) {
			try {
				bean = (BeanJerarquia) cmbLinea.getSelectedItem();
			} catch (Exception e) {
				bean = null;
			}
			if (bean == null) {
				try {
					bean = (BeanJerarquia) cmbFamilia.getSelectedItem();
				} catch (Exception e) {
					bean = null;
				}
				if (bean == null) {
					try {
						bean = (BeanJerarquia) cmbCategoria.getSelectedItem();
					} catch (Exception e) {
						bean = null;
					}
					if (bean == null) {
						try {
							bean = (BeanJerarquia) cmbDivision.getSelectedItem();
						} catch (Exception e) {
							bean = null;
						}
					}
				}
			}
		}
		if (bean != null) {
			strCodigo = bean.getStrPRODH();
		} else {
			strCodigo = "";
		}
		if (strCodigo == null) {
			strCodigo = "";
		}
		return strCodigo;
	}

	/*
	 * @autor	MARCELO MOYANO
	 * 
	 * 			OBTIENE EL TIPO DE MATERIAL
	 * 
	 * @return	type RETORNA EL STRING DEL TIPO DE MATERIAL.
	 */
	private String obtenerType(int tipo) {
		String type;
		switch (tipo) {
		case 0:
			type = "";
			break;
		case 1:
			type = "N";
			break;
		case 2:
			type = "P";
			break;
		case 3:
			type = "R";
			break;
		case 4:
			type = "B";
			break;
		default:
			type = "";
			break;
		}
		return type;
	}

	/*
	 * @autor	MARCELO MOYANO
	 * 
	 * 			SELECCIONA EL CONTENIDO DEL
	 * 			COMBOBOX DONDE SE ENCUENTRA
	 * 			LOS MATERIALES BUSCADOS.
	 */
	private void buscar2() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			@SuppressWarnings("rawtypes")
			public void run() {
				try {
					List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
					ComboBoxModel materialesModelo;
					BeanMaterial beanMaterial;
					materialesModelo = cmbDescripcionLarga.getModel();
					int index = cmbDescripcionLarga.getSelectedIndex();
					if (index > 0) {
						beanMaterial = (BeanMaterial) cmbDescripcionLarga.getSelectedItem();
						materiales.add(beanMaterial);
					} else if (materialesModelo.getSize() > 0) {
						cantidadRegistros = materialesModelo.getSize();
						calcularPaginacion();
						for (int i = 1; i < materialesModelo.getSize(); i++) {
							beanMaterial = (BeanMaterial) materialesModelo.getElementAt(i);
							if(beanMaterial.getTypeMat().startsWith(tipoMaterial)){
								materiales.add(beanMaterial);
							}
							
						}
					}
					materialese = materiales;
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					

					try{
						llenarTabla();
						calcularPaginacion();
						bloqueador.dispose();
					}catch (NullPointerException nulle){
						System.out.println("null llenar tabla");
					}

					
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void calcularPaginacion() {
		cantidadPaginas = (int) (cantidadRegistros / 400) + 1;
		cantidadPaginas = cantidadPaginas <= 1 ? 1 : cantidadPaginas;
		paginaActual = 1;
		lblPaginas.setText(paginaActual + "/" + cantidadPaginas);
	}

	@SuppressWarnings("rawtypes")
	private void calcularPaginacion(int pagina) {
		if (pagina > 0 && pagina <= cantidadPaginas) {
			Point p = new Point(0, 0);
			scrollMateriales.getViewport().setViewPosition(p);
			tblMateriales.removeAll();
			materialese.clear();
			cantidadPaginas = cantidadPaginas <= 1 ? 1 : cantidadPaginas;
			paginaActual = pagina;
			lblPaginas.setText(paginaActual + "/" + cantidadPaginas);
			SqlDivisionImpl dao = new SqlDivisionImpl();
			if (consulta.length() > 0) {
				materialese = dao.buscarMateriales(consulta, pagina);
			}
			List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
			ComboBoxModel modeloMaterial = cmbDescripcionLarga.getModel();
			if (modeloMaterial.getSize() > 0) {
				int itemFinal = modeloMaterial.getSize() < pagina
						* Constante.CANTIDAD_REGISTROS_POR_PAGINA ? modeloMaterial.getSize() - 1 : pagina
						* Constante.CANTIDAD_REGISTROS_POR_PAGINA;
				for (int i = (pagina - 1)
						* Constante.CANTIDAD_REGISTROS_POR_PAGINA; i < itemFinal; i++) {
					if (i != 0) {
						BeanMaterial beanMaterial = (BeanMaterial) modeloMaterial.getElementAt(i);
						materiales.add(beanMaterial);
					}
				}
				materialese = materiales;
			}
			
			try{
				llenarTabla();
				tblMateriales.updateUI();
			}catch (NullPointerException nulle){
				System.out.println("null llenar tabla");
			}
			
			
		}
	}

	class MyComboBoxRendererCondiciones extends BasicComboBoxRenderer {
		private Object[] tooltips;
		public MyComboBoxRendererCondiciones(List<BeanCondicionPago> tooltips) {
			super();
			this.tooltips = tooltips.toArray();
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {

				try{
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				}catch (NullPointerException nulle){
					System.out.println("nulle");
				}
           
				if (-1 < index) {
					list.setToolTipText(((BeanCondicionPago) tooltips[index]).getTxtVTEXT());
				}
			} else {

				try{
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}catch (NullPointerException nulle){
					System.out.println("nulle");
				}
           	
			}
			setFont(list.getFont());
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	class MyComboBoxRenderer extends BasicComboBoxRenderer {
		private Object[] tooltips;
		public MyComboBoxRenderer(List<BeanJerarquia> tooltips) {
			super();
			this.tooltips = tooltips.toArray();
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				try{
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				}catch (NullPointerException nulle){
					System.out.println("nulle");
				}
				
				if (-1 < index) {
					list.setToolTipText(((BeanJerarquia) tooltips[index]).getStrVTEXT());
				}
			} else {
				try{
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}catch (NullPointerException nulle){
					System.out.println("nulle");
				}
				
			}
			setFont(list.getFont());
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	class MyComboBoxRenderer2 extends BasicComboBoxRenderer {
		private Object[] tooltips;

		public MyComboBoxRenderer2(List<BeanMaterial> tooltips) {
			super();
			this.tooltips = tooltips.toArray();
		}
		
		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				try{
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				}catch (NullPointerException nulle){
					System.out.println("nulle");
				}
				
				if (-1 < index) {
					list.setToolTipText(((BeanMaterial) tooltips[index]).getBusqueda());
				}
			} else {
				try{
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}catch (NullPointerException nulle){
					System.out.println("nulle");
				}
				
			}
			setFont(list.getFont());
			BeanMaterial bm = (BeanMaterial) value;
			setText((bm == null) ? "" : bm.toString());
			return this;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void llenarCombos() {
		List<BeanCondicionPago> listaCondiciones = condiciones.listarCondicionesPagoPorCliente(cliente.getStrCondicionPagoDefecto());
		cmbCondPago.setModel(new DefaultComboBoxModel(listaCondiciones.toArray()));
		cmbCondPago.setRenderer(new MyComboBoxRendererCondiciones(condiciones.getListaCondiciones()));
		for (BeanCondicionPago beanCondicionPago : listaCondiciones) {
			if (beanCondicionPago.getTxtZTERM().compareTo(condPago) == 0) {
				cmbCondPago.setSelectedItem(beanCondicionPago);
				break;
			}
		}
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			EVENTO DISPARADO CUANDO SE DIGITA EN EL
	 * 			CAMBOBOX (CAJA DE TEXTO) CMBDIVISION.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txtDivisionEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		String strTmp2 = "";
		String strTmp1 = "";
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ' || c == '/') {
			try {
				if(txtDivisionEditor.getCaretPosition() == 1){
					if(strDivision.length() > 1){
						strDivision = "";
						txtDivisionEditor.setText("");
						cmbDivision.removeAllItems();
						cmbDivision.setModel(new DefaultComboBoxModel(new String[] {}));
						cmbDivision.setRenderer(new MyComboBoxRenderer(uno));
					}
				}
				if(strDivision.length() < txtDivisionEditor.getCaretPosition()){
					strDivision = strDivision + c;
				}else if(strDivision.length() == txtDivisionEditor.getCaretPosition()) {
					strTmp1 = strDivision.substring(0, txtDivisionEditor.getCaretPosition());
					strTmp2 = strDivision.substring(txtDivisionEditor.getCaretPosition(), strDivision.length());
					strDivision = strTmp1 + c + strTmp2;
				}
			} catch (Exception e) {
				// TODO: handle exception
				strDivision = strDivision + c;
			}
			BuscarJerarquiaDivision(strDivision);
		}
		if (c == '\b' && !strDivision.equals("")) {
			if(txtDivisionEditor.getCaretPosition() == 0){
				strDivision = "";
				txtDivisionEditor.setText("");
				uno.clear();
				cmbDivision.removeAllItems();
				cmbDivision.setModel(new DefaultComboBoxModel(new String[] {}));
				cmbDivision.setRenderer(new MyComboBoxRenderer(uno));
			} else {
				strDivision = txtDivisionEditor.getText();
				strTmp2 = strDivision.substring(txtDivisionEditor.getCaretPosition(), strDivision.length());
				strDivision = strDivision.substring(0, txtDivisionEditor.getCaretPosition()) + strTmp2;
			}
			txtDivisionEditor.setText(strDivision);
			txtDivisionEditor.select(strDivision.length(), strDivision.length());
			BuscarJerarquiaDivision(strDivision);
		}
		if (c == '\n' && !strDivision.equals("")) {
			String strTextoBuscar = cmbDivision.getSelectedItem().toString();
			txtDivisionEditor.setText(strTextoBuscar);
			buscar();
			if(tblMateriales.getModel().getRowCount() == 0){
				buscar2();
			}
			activarDivision = false;
		}else if(c == '\n'){
			buscadorMaterial(txtDescripcionCorta.getText());
			buscar2();
		}
		if(c == '' && !strDivision.equals("")) {
			strDivision = txtDivisionEditor.getText();
		}
		txtDivisionEditor.select(txtDivisionEditor.getCaretPosition() - strTmp2.length(), txtDivisionEditor.getCaretPosition() - strTmp2.length());
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE BUSCA LA JERARQUIA DIVISIÓN
	 * 			DE MATERIALES EN MEMORIA.
	 * 
	 * @param	strPalabraABuscar ES LA PALABRA QUE SE DESEA BUSCAR.		
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void BuscarJerarquiaDivision(String strPalabraABuscar) {
		cmbDivision.removeAllItems();
		uno.clear();
		cmbDivision.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaDivision = new Buscador(Constante.PATH + "Division");
			String[] cadena = strPalabraABuscar.split(" ");
			String strtmp = "";
			for (int i = 0; i < cadena.length; i++) {
				strtmp += cadena[i] + "* ";
			}
			try {
				ScoreDoc[] h5 = buscaDivision.funcionBuscador(strtmp.trim());
				if (h5.length > 0) {
					for (int i = 0; i < h5.length; i++) {
						bjDos = new BeanJerarquia();
						int iDoc = h5[i].doc;
						Document d = buscaDivision.getIbuscador().doc(iDoc);
						bjDos.setStrVTEXT(d.get("VTEXT"));
						bjDos.setStrPRODH(d.get("PRDHA"));
						uno.add(bjDos);
					}
					comodinDivision.setStrVTEXT(strPalabraABuscar);
					uno.add(0, comodinDivision);
				} else {
					comodinDivision.setStrVTEXT(strPalabraABuscar);
					uno.add(0, comodinDivision);
				}
				cmbDivision.setModel(new DefaultComboBoxModel(uno.toArray()));
				cmbDivision.setRenderer(new MyComboBoxRenderer(uno));
				cmbDivision.showPopup();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			EVENTO DISPARADO CUANDO SE DIGITA EN EL
	 * 			CAMBOBOX (CAJA DE TEXTO) CMBCATEGORIA.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txtCategoriaEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		String strTmp2 = "";
		String strTmp1 = "";
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			if(Character.isDigit(c) || Character.isLetter(c) || c == ' ' || c == '/'){
				try {
					if(txtCategoriaEditor.getCaretPosition() == 1){
						if(strCategoria.length() > 1){
							strCategoria = "";
							txtCategoriaEditor.setText("");
							dos.clear();
							cmbCategoria.removeAllItems();
							cmbCategoria.setModel(new DefaultComboBoxModel(new String[] {}));
							cmbCategoria.setRenderer(new MyComboBoxRenderer(dos));
						}
					}
					if(strCategoria.length() < txtCategoriaEditor.getCaretPosition()){
						strCategoria = strCategoria + c;
					}else if (strCategoria.length() == txtCategoriaEditor.getCaretPosition()){
						strTmp1 = strCategoria.substring(0, txtCategoriaEditor.getCaretPosition());
						strTmp2 = strCategoria.substring(txtCategoriaEditor.getCaretPosition() , strCategoria.length());
						strCategoria = strTmp1 + c + strTmp2;
					}else {
						strTmp1 = strCategoria.substring(0, txtCategoriaEditor.getCaretPosition() - 1);
						strTmp2 = strCategoria.substring(txtCategoriaEditor.getCaretPosition() -1, strCategoria.length());
						strCategoria = strTmp1 + c + strTmp2;
					}
				} catch (Exception e) {
					// TODO: handle exception
					strCategoria = strCategoria + c;
				}
			}
			BuscarJerarquiaCategoria(strCategoria);
		}
		if (c == '\b' && !strCategoria.equals("")) {
			if(txtCategoriaEditor.getCaretPosition() == 0){
				strCategoria = "";
				txtCategoriaEditor.setText("");
				dos.clear();
				cmbCategoria.removeAllItems();
				cmbCategoria.setModel(new DefaultComboBoxModel(new String[] {}));
				cmbCategoria.setRenderer(new MyComboBoxRenderer(dos));
			}else {
				strCategoria = txtCategoriaEditor.getText();
				strTmp2 = strCategoria.substring(txtCategoriaEditor.getCaretPosition(), strCategoria.length());
				strCategoria = strCategoria.substring(0, txtCategoriaEditor.getCaretPosition()) + strTmp2;
			}
			txtCategoriaEditor.setText(strCategoria);
			txtCategoriaEditor.select(strCategoria.length(), strCategoria.length());
			BuscarJerarquiaCategoria(strCategoria);
		}
		if (c == '\n' && !strCategoria.equals("")) {
			String strTextoBuscar = cmbCategoria.getSelectedItem().toString();
			txtCategoriaEditor.setText(strTextoBuscar);
			buscar();
			if(tblMateriales.getModel().getRowCount() == 0){
				buscar2();
			}
			activarCategoria = false;
		}else if(c == '\n'){
			buscadorMaterial(txtDescripcionCorta.getText());
			buscar2();
		}
		if(c == '' && !strCategoria.equals("")){
			strCategoria = txtCategoriaEditor.getText();
		}
		txtCategoriaEditor.select(txtCategoriaEditor.getCaretPosition() - strTmp2.length(), txtCategoriaEditor.getCaretPosition() - strTmp2.length());
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE BUSCA LA JERARQUIA CATEGORIA
	 * 			DE MATERIALES EN MEMORIA.
	 * 
	 * @param	strPalabraABuscar ES LA PALABRA QUE SE DESEA BUSCAR.		
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void BuscarJerarquiaCategoria(String strPalabraABuscar) {
		cmbCategoria.removeAllItems();
		dos.clear();
		cmbCategoria.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaCategoria = new Buscador(Constante.PATH + "Categoria");
			BeanJerarquia filtro = (BeanJerarquia) cmbDivision.getSelectedItem();
			if (filtro != null) {
				strFiltro = filtro.getStrPRODH();
			} else {
				strFiltro = "";
			}
			if (strFiltro == null) {
				strFiltro = "";
			}

			String[] cadena = strPalabraABuscar.split(" ");
			String strtmp = "";
			for (int i = 0; i < cadena.length; i++) {
				strtmp += cadena[i] + "* ";
			}
			try {
				ScoreDoc[] h5 = buscaCategoria.funcionBuscador(strtmp.trim());
				if (h5.length > 0) {
					for (int i = 0; i < h5.length; i++) {
						bjDos = new BeanJerarquia();
						int iDoc = h5[i].doc;
						Document d = buscaCategoria.getIbuscador().doc(iDoc);
						if (d.get("PRDHA").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRDHA"));
							dos.add(bjDos);
						}

					}
					comodinCategoria.setStrVTEXT(strPalabraABuscar);
					dos.add(0, comodinCategoria);
				} else {
					comodinCategoria.setStrVTEXT(strPalabraABuscar);
					dos.add(0, comodinCategoria);
				}
				cmbCategoria.setModel(new DefaultComboBoxModel(dos.toArray()));
				cmbCategoria.setRenderer(new MyComboBoxRenderer(dos));
				cmbCategoria.showPopup();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			EVENTO DISPARADO CUANDO SE DIGITA EN EL
	 * 			CAMBOBOX (CAJA DE TEXTO) CMBFAMILIA.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void txtFamiliaEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		String strTmp2 = "";
		String strTmp1 = "";
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			try {
				if(txtFamiliaEditor.getCaretPosition() == 1){
					if(strFamilia.length() > 1){
						strFamilia = "";
						txtFamiliaEditor.setText("");
						tres.clear();
						cmbFamilia.removeAllItems();
						cmbFamilia.setModel(new DefaultComboBoxModel(new String[] {}));
						cmbFamilia.setRenderer(new MyComboBoxRenderer(tres));
					}
				}
				if(strFamilia.length() < txtFamiliaEditor.getCaretPosition()){
					strFamilia = strFamilia + c;
				}else if (strFamilia.length() == txtFamiliaEditor.getCaretPosition()){
					strTmp1 = strFamilia.substring(0, txtFamiliaEditor.getCaretPosition() );
					strTmp2 = strFamilia.substring(txtFamiliaEditor.getCaretPosition() , strFamilia.length());
					strFamilia = strTmp1 + c + strTmp2;
				}else {
					strTmp1 = strFamilia.substring(0, txtFamiliaEditor.getCaretPosition() - 1);
					strTmp2 = strFamilia.substring(txtFamiliaEditor.getCaretPosition() -1, strFamilia.length());
					strFamilia = strTmp1 + c + strTmp2;
				}
			} catch (Exception e) {
				// TODO: handle exception
				strFamilia = strFamilia + c;
			}
			BuscarJerarquiaFamilia(strFamilia);
		}
		if (c == '\b' && !strFamilia.equals("")) {
			if(txtFamiliaEditor.getCaretPosition() == 0){
				strFamilia = "";
				txtFamiliaEditor.setText("");
				tres.clear();
				cmbFamilia.removeAllItems();
				cmbFamilia.setModel(new DefaultComboBoxModel(new String[] {}));
				cmbFamilia.setRenderer(new MyComboBoxRenderer(tres));
			}else {
				strFamilia = txtFamiliaEditor.getText();
				strTmp2 = strFamilia.substring(txtFamiliaEditor.getCaretPosition(), strFamilia.length());
				strFamilia = strFamilia.substring(0, txtFamiliaEditor.getCaretPosition()) + strTmp2;
			}
			txtFamiliaEditor.setText(strFamilia);
			txtFamiliaEditor.select(strFamilia.length(), strFamilia.length());
			BuscarJerarquiaFamilia(strFamilia);
		}
		if (c == '\n' && !strFamilia.equals("")) {
			String strTextBuscar = cmbFamilia.getSelectedItem().toString();
			txtFamiliaEditor.setText(strTextBuscar);
			buscar();
			if(tblMateriales.getModel().getRowCount() == 0){
				buscar2();
			}
			activarFamilia = false;
		}else if(c == '\n'){
			buscadorMaterial(txtDescripcionCorta.getText());
			buscar2();
		}
		if(c == '' && !strFamilia.equals("")){
			strFamilia = txtFamiliaEditor.getText();
		}
		txtFamiliaEditor.select(txtFamiliaEditor.getCaretPosition() - strTmp2.length(), txtFamiliaEditor.getCaretPosition() - strTmp2.length());
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE BUSCA LA JERARQUIA FAMILIA
	 * 			DE MATERIALES EN MEMORIA.
	 * 
	 * @param	strPalabraABuscar ES LA PALABRA QUE SE DESEA BUSCAR.		
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void BuscarJerarquiaFamilia(String strPalabraABuscar) {
		cmbFamilia.removeAllItems();
		tres.clear();
		cmbFamilia.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaFamilia = new Buscador(Constante.PATH + "Familia");
			try {
				BeanJerarquia filtro = (BeanJerarquia) cmbCategoria.getSelectedItem();
				if (filtro != null) {
					strFiltro = filtro.getStrPRODH();
				} else {
					strFiltro = "";
				}
				if (strFiltro == null) {
					strFiltro = "";
				}
			} catch (Exception e) {
				strFiltro = "";
			}
			String[] cadena = strPalabraABuscar.split(" ");
			String strtmp = "";
			for (int i = 0; i < cadena.length; i++) {
				strtmp += cadena[i] + "* ";
			}
			try {
				ScoreDoc[] h5 = buscaFamilia.funcionBuscador(strtmp.trim());
				if (h5.length > 0) {
					for (int i = 0; i < h5.length; i++) {
						bjDos = new BeanJerarquia();
						int iDoc = h5[i].doc;
						Document d = buscaFamilia.getIbuscador().doc(iDoc);
						if (d.get("PRDHA").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRDHA"));
							tres.add(bjDos);
						}
					}
					comodinFamilia.setStrVTEXT(strPalabraABuscar);
					tres.add(0, comodinFamilia);
				} else {
					comodinFamilia.setStrVTEXT(strPalabraABuscar);
					tres.add(0, comodinFamilia);
				}
				cmbFamilia.setModel(new DefaultComboBoxModel(tres.toArray()));
				cmbFamilia.setRenderer(new MyComboBoxRenderer(tres));
				cmbFamilia.showPopup();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			EVENTO DISPARADO CUANDO SE DIGITA EN EL
	 * 			CAMBOBOX (CAJA DE TEXTO) CMBLINEA.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void txtLineaEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		String strTmp2 = "";
		String strTmp1 = "";
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			try {
				if(txtLineaEditor.getCaretPosition() == 1){
					if(strLinea.length() > 1){
						strLinea = "";
						txtLineaEditor.setText("");
						cuatro.clear();
						cmbLinea.removeAllItems();
						cmbLinea.setModel(new DefaultComboBoxModel(new String[] {}));
						cmbLinea.setRenderer(new MyComboBoxRenderer(cuatro));
					}
				}
				if(strLinea.length() < txtLineaEditor.getCaretPosition()){
					strLinea = strLinea + c;
				}else if (strLinea.length() == txtLineaEditor.getCaretPosition()){
					strTmp1 = strLinea.substring(0, txtLineaEditor.getCaretPosition() );
					strTmp2 = strLinea.substring(txtLineaEditor.getCaretPosition() , strLinea.length());
					strLinea = strTmp1 + c + strTmp2;
				}else {
					strTmp1 = strLinea.substring(0, txtLineaEditor.getCaretPosition() - 1);
					strTmp2 = strLinea.substring(txtLineaEditor.getCaretPosition() -1, strLinea.length());
					strLinea = strTmp1 + c + strTmp2;
				}
			} catch (Exception e) {
				// TODO: handle exception
				strLinea = strLinea + c;
			}
			BuscarJerarquiaLinea(strLinea);
		}
		if (c == '\b' && !strLinea.equals("")) {
			if(txtLineaEditor.getCaretPosition() == 0){
				strLinea = "";
				txtLineaEditor.setText("");
				cuatro.clear();
				cmbLinea.removeAllItems();
				cmbLinea.setModel(new DefaultComboBoxModel(new String[] {}));
				cmbLinea.setRenderer(new MyComboBoxRenderer(cuatro));
			}else {
				strLinea = txtLineaEditor.getText();
				strTmp2 = strLinea.substring(txtLineaEditor.getCaretPosition(), strLinea.length());
				strLinea = strLinea.substring(0, txtLineaEditor.getCaretPosition()) + strTmp2;
			}
			txtLineaEditor.setText(strLinea);
			txtLineaEditor.select(strLinea.length(), strLinea.length());
			BuscarJerarquiaLinea(strLinea);
		}
		if (c == '\n' && !strLinea.equals("")) {
			String strTextoBuscar = cmbLinea.getSelectedItem().toString();
			txtLineaEditor.setText(strTextoBuscar);
			buscar();
			if(tblMateriales.getModel().getRowCount() == 0){
				buscar2();
			}
			activarLinea = false;
		}else if(c == '\n'){
			buscadorMaterial(txtDescripcionCorta.getText());
			buscar2();
		}
		if(c == '' && !strLinea.equals("")){
			strLinea = txtLineaEditor.getText();
		}
		txtLineaEditor.select(txtLineaEditor.getCaretPosition() - strTmp2.length(), txtLineaEditor.getCaretPosition() - strTmp2.length());
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE BUSCA LA JERARQUIA LINEA
	 * 			DE MATERIALES EN MEMORIA.
	 * 
	 * @param	strPalabraABuscar ES LA PALABRA QUE SE DESEA BUSCAR.		
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void BuscarJerarquiaLinea(String strPalabraABuscar) {
		cmbLinea.removeAllItems();
		cuatro.clear();
		cmbLinea.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaLinea = new Buscador(Constante.PATH + "Linea");
			BeanJerarquia filtro = (BeanJerarquia) cmbFamilia.getSelectedItem();
			if (filtro != null) {
				strFiltro = filtro.getStrPRODH();
			} else {
				strFiltro = "";
			}
			if (strFiltro == null) {
				strFiltro = "";
			}

			String[] cadena = strPalabraABuscar.split(" ");
			String strtmp = "";
			for (int i = 0; i < cadena.length; i++) {
				strtmp += cadena[i] + "* ";
			}
			try {
				ScoreDoc[] h5 = buscaLinea.funcionBuscador(strtmp.trim());
				if (h5.length > 0) {
					for (int i = 0; i < h5.length; i++) {
						bjDos = new BeanJerarquia();
						int iDoc = h5[i].doc;
						Document d = buscaLinea.getIbuscador().doc(iDoc);
						if (d.get("PRDHA").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRDHA"));
							cuatro.add(bjDos);
						}
					}
					comodinLinea.setStrVTEXT(strPalabraABuscar);
					cuatro.add(0, comodinLinea);
				} else {
					comodin.setStrVTEXT(strPalabraABuscar);
					cuatro.add(0, comodin);
				}
				cmbLinea.setModel(new DefaultComboBoxModel(cuatro.toArray()));
				cmbLinea.setRenderer(new MyComboBoxRenderer(cuatro));
				cmbLinea.showPopup();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			EVENTO QUE SE DISPARA CUANDO SE DIGITA EN EL
	 * 			CAMBOBOX (CAJA DE TEXTO) CMBGRUPO.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void txtGrupoEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		String strTmp2 = "";
		String strTmp1 = "";
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			try {
				if(txtGrupoEditor.getCaretPosition() == 1){
					if(strGrupo.length() > 1){
						strGrupo = "";
						txtGrupoEditor.setText("");
						cinco.clear();
						cmbGrupo.removeAllItems();
						cmbGrupo.setModel(new DefaultComboBoxModel(new String[] {}));
						cmbGrupo.setRenderer(new MyComboBoxRenderer(cinco));
					}
				}
				if(strGrupo.length() < txtGrupoEditor.getCaretPosition()){
					strGrupo = strGrupo + c;
				}else if (strGrupo.length() == txtGrupoEditor.getCaretPosition()){
					strTmp1 = strGrupo.substring(0, txtGrupoEditor.getCaretPosition() );
					strTmp2 = strGrupo.substring(txtGrupoEditor.getCaretPosition() , strGrupo.length());
					strGrupo = strTmp1 + c + strTmp2;
				}else {
					strTmp1 = strGrupo.substring(0, txtGrupoEditor.getCaretPosition() - 1);
					strTmp2 = strGrupo.substring(txtGrupoEditor.getCaretPosition() -1, strGrupo.length());
					strGrupo = strTmp1 + c + strTmp2;
				}
			} catch (Exception e) {
				// TODO: handle exception
				strGrupo = strGrupo + c;
			}
			BuscarJerarquiaGrupo(strGrupo);
		}
		if (c == '\b' && !strGrupo.equals("")) {
			if(txtGrupoEditor.getCaretPosition() == 0){
				strGrupo = "";
				txtGrupoEditor.setText("");
				cinco.clear();
				cmbGrupo.removeAllItems();
				cmbGrupo.setModel(new DefaultComboBoxModel(new String[] {}));
				cmbGrupo.setRenderer(new MyComboBoxRenderer(cinco));
			}else {
				strGrupo = txtGrupoEditor.getText();
				strTmp2 = strGrupo.substring(txtGrupoEditor.getCaretPosition(), strGrupo.length());
				strGrupo = strGrupo.substring(0, txtGrupoEditor.getCaretPosition()) + strTmp2;
			}
			txtGrupoEditor.setText(strGrupo);
			txtGrupoEditor.select(strGrupo.length(), strGrupo.length());
			BuscarJerarquiaGrupo(strGrupo);
		}
		if (c == '\n' && !strGrupo.equals("")) {
			String strTextoBuscar = cmbGrupo.getSelectedItem().toString(); 
			txtGrupoEditor.setText(strTextoBuscar);
			buscar();
			if(tblMateriales.getModel().getRowCount() == 0){
				buscar2();
			}
			activarGrupo = false;
		}else if(c == '\n'){
			buscadorMaterial(txtDescripcionCorta.getText());
			buscar2();
		}
		if(c == '' && !strGrupo.equals("")){
			strGrupo = txtGrupoEditor.getText();
		}
		txtGrupoEditor.select(txtGrupoEditor.getCaretPosition() - strTmp2.length(), txtGrupoEditor.getCaretPosition() - strTmp2.length());
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE BUSCA LA JERARQUIA GRUPO
	 * 			DE MATERIALES EN MEMORIA.
	 * 
	 * @param	strPalabraABuscar ES LA PALABRA QUE SE DESEA BUSCAR.		
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void BuscarJerarquiaGrupo(String strPalabraABuscar) {
		cmbGrupo.removeAllItems();
		cinco.clear();
		cmbGrupo.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscagGrupo = new Buscador(Constante.PATH + "Grupo");
			BeanJerarquia filtro = (BeanJerarquia) cmbLinea.getSelectedItem();
			if (filtro != null) {
				strFiltro = filtro.getStrPRODH();
			} else {
				strFiltro = "";
			}
			if (strFiltro == null) {
				strFiltro = "";
			}

			String[] cadena = strPalabraABuscar.split(" ");
			String strtmp = "";
			for (int i = 0; i < cadena.length; i++) {
				strtmp += cadena[i] + "* ";
			}
			try {
				ScoreDoc[] h5 = buscagGrupo.funcionBuscador(strtmp.trim());
				if (h5.length > 0) {
					for (int i = 0; i < h5.length; i++) {
						bjDos = new BeanJerarquia();
						int iDoc = h5[i].doc;
						Document d = buscagGrupo.getIbuscador().doc(iDoc);
						if (d.get("PRDHA").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRDHA"));
							cinco.add(bjDos);
						}

					}
					comodinGrupo.setStrVTEXT(strPalabraABuscar);
					cinco.add(0, comodinGrupo);
				} else {
					comodinGrupo.setStrVTEXT(strPalabraABuscar);
					cinco.add(0, comodinGrupo);
				}
				cmbGrupo.setModel(new DefaultComboBoxModel(cinco.toArray()));
				cmbGrupo.setRenderer(new MyComboBoxRenderer(cinco));
				cmbGrupo.showPopup();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			deletedAll();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setVisible(false);
		this.dispose();
	}

	private void deletedAll() throws CorruptIndexException, IOException {
		listaMaterialesABuscar.clear();
		
		uno.clear();
		dos.clear();
		tres.clear();
		cuatro.clear();
		cinco.clear();
		
//		indexDivision.closeIndexador();
//		indexCategoria.closeIndexador();
//		indexFamilia.closeIndexador();
//		indexLinea.closeIndexador();
//		indexGrupo.closeIndexador();
//		indexMaterial.closeIndexador();
		
		System.gc();
//		Runtime garbage = Runtime.getRuntime();
//	    garbage.gc();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		ModeloConsultaDinamica modeloTablaItems = new ModeloConsultaDinamica();
		tblMateriales.setModel(modeloTablaItems);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	public static class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy {
		Vector<Component> order;

		public MyOwnFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}

		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
	}

	/*
	 * 		CALCULO DEL PRECIO DE MATERIALES EN
	 * 		CONSULTA DINAMICA. 
	 */
	protected void simulaPreciosDeMaterialesSQLite2() {
		try {
			List<BeanPedidoDetalle> detalles = new ArrayList<BeanPedidoDetalle>();
			List<Item> listaItems = new ArrayList<Item>();
//			ModeloConsultaDinamica2 mcd = (ModeloConsultaDinamica2) tblMateriales.getModel();
//			listaItems = mcd.obtenerTodosItemsNoSimulados();
			int pos = tblMateriales.getSelectedRow();
			
			if(pos > -1){
				String material = tblMateriales.getValueAt(pos, 0).toString();
				String cantidad = "";
				String precioLista = tblMateriales.getValueAt(pos, 6).toString();
				String strDescuentoManuales = tblMateriales.getValueAt(pos, 11).toString();
				try {
					cantidad = ""+ Integer.parseInt(tblMateriales.getValueAt(pos, 10).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					cantidad = "0";
				}
				if (!precioLista.equals("0.0") && cantidad.compareTo("0") != 0 ) {
					BeanPedidoDetalle detalle = new BeanPedidoDetalle();
					Item it = new Item();
					String posicion = Util.completarCeros(6, "" + ((pos + 1) * 10));
					detalle.setPosicion(posicion);
					it.setPosicion(posicion);
					detalle.setMaterial(material);
					detalle.setPorcentajeDescuento("0.00");
					detalle.setPorcentajeDescuento(strDescuentoManuales);
					it.setPorcentajeDescuento(strDescuentoManuales);
					it.setCodigo(material);
					detalle.setCantidad(cantidad);
					it.setCantidad(cantidad);
					detalles.add(detalle);
					it.setIntUbicacionGrilla(pos);
					listaItems.add(it);
				} else if (cantidad.compareTo("0") == 0){
					tblMateriales.setValueAt("0", pos, 7);
					tblMateriales.setValueAt("0", pos, 8);
					tblMateriales.setValueAt("0", pos, 12);
					tblMateriales.setValueAt("0", pos, 10);
					tblMateriales.updateUI();
				}
			} else {
				for (int i = 0; i < tblMateriales.getRowCount(); i++) {
					String material = tblMateriales.getValueAt(i, 0).toString();
					String cantidad = "";
					String total = tblMateriales.getValueAt(i, 14).toString();
					String strDescuentoManuales = tblMateriales.getValueAt(i, 11).toString();
					try {
						cantidad = ""+ Integer.parseInt(tblMateriales.getValueAt(i, 10).toString());
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						cantidad = "0";
					}
					if (cantidad.compareTo("0") != 0 && total.equals("0.0")) {
						BeanPedidoDetalle detalle = new BeanPedidoDetalle();
						Item it = new Item();
						String posicion = Util.completarCeros(6, "" + ((i + 1) * 10));
						detalle.setPosicion(posicion);
						it.setPosicion(posicion);
						detalle.setMaterial(material);
						detalle.setPorcentajeDescuento("0.00");
						detalle.setPorcentajeDescuento(strDescuentoManuales);
						it.setPorcentajeDescuento(strDescuentoManuales);
						it.setCodigo(material);
						detalle.setCantidad(cantidad);
						it.setCantidad(cantidad);
						detalles.add(detalle);
						it.setIntUbicacionGrilla(i);
						listaItems.add(it);
					} else if (cantidad.compareTo("0") == 0){
						tblMateriales.setValueAt("0", i, 7);
						tblMateriales.setValueAt("0", i, 8);
						tblMateriales.setValueAt("0", i, 12);
						tblMateriales.setValueAt("0", i, 10);
						tblMateriales.updateUI();
					}
				}
			}
			List<Item> lista = new ArrayList<Item>();
			lista.addAll(listaItems);
			String codCliente = cliente.getStrIdCliente();

			String codCondPago = condPago;
			SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
			Double dblPrecioFinal = new Double("0");
			Double dblCantidadPedido = new Double(0);
			Double dblImportePedido = new Double(0);

			List<BeanMaterial> listaMaterialesGrilla = new ArrayList<BeanMaterial>();
			// se limpian los % y clases de condicion entre cada simulaciòn
			for (Item item : lista) {
				item.setDblDesc1(new Double(0));
				item.setDblDesc2(new Double(0));
				item.setDblDesc3(new Double(0));
				item.setDblDesc4(new Double(0));
				item.setDblDesc5(new Double(0));
				item.setStrCondCalc1("");
				item.setStrCondCalc2("");
				item.setStrCondCalc3("");
				item.setStrCondCalc4("");
				item.setStrCondCalc5("");
				item.setPrecioNeto(new Double(0));
				item.setPrecioIVA(new Double(0));
				item.setValorNeto(new Double(0));
				listaMaterialesGrilla.add(sqlMateriales.getMaterial(item.getCodigo()));
			}
			SqlClienteImpl sqlCliente = new SqlClienteImpl();
			BeanCliente beanCliente = sqlCliente.buscarCliente(codCliente);

			// ///CONDICIONES COMERCIALES 1X
			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("") && i.getTipo() == 0) {
					BeanMaterial beanMaterial = null;
					beanMaterial = obtenerMaterialDeGrilla(listaMaterialesGrilla, i.getCodigo());
					i.setPrecioBase(Double.parseDouble(beanMaterial.getPrice_1()));
					/*
					 * 	MARCELO MOYANO
					 * 	PRECIO CONSULTA MATERIAL
					 * 	COMBO MATERIAL
					 */
					if(i.getTipo() == 2){
						double importeCombo = sqlMateriales.getPrecioXCliente("", i.getCodigo());// Material Combo //
						if(importeCombo != 0d){
							i.setPrecioBase(importeCombo);
						} else {
							double importePrecioClienteMaterial = sqlMateriales.getPrecioXCliente(codCliente, i.getCodigo());
							if(importePrecioClienteMaterial != 0d){
								i.setPrecioBase(importePrecioClienteMaterial);
							}
						}
					}else {
						double importePrecioClienteMaterial = sqlMateriales.getPrecioXCliente(codCliente, i.getCodigo());
						if(importePrecioClienteMaterial != 0d){
							i.setPrecioBase(importePrecioClienteMaterial);
						}
					}
					/***/
					List<BeanClaseMaterial> listaClasesMaterial = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
					BeanCondicionComercial1 b1 = new BeanCondicionComercial1();
					b1.setStrCondicionPago(codCondPago);
					b1.setStrCliente(codCliente);
					b1.setListaStrClaseMaterial(listaClasesMaterial);
					Double dblDscto = new Double("0");
					List<BeanCondicionComercial1> listaCondiciones1 = sqlMateriales.listarCondicion1(b1);
					BeanCondicionComercial1 beanCondicion1 = null;
					if (listaCondiciones1 != null && listaCondiciones1.size() == 1) {
						beanCondicion1 = listaCondiciones1.get(0);
						dblDscto = beanCondicion1.getDblImporte() / 10;
						dblPrecioFinal = Double.parseDouble(beanMaterial.getPrice_1()) * (100 + dblDscto) / 100;
						i.setDblDesc1(dblDscto);
						i.setStrCondCalc1(beanCondicion1.getStrClaseCond());
						i.setStrNroCond1(beanCondicion1.getStrNroRegCond());
					} else {
						dblPrecioFinal = Double.parseDouble(beanMaterial.getPrice_1());
						i.setDblDesc1(new Double(0));
						i.setStrCondCalc1("");
						i.setStrNroCond1("");
					}
				}
			}
			// ///CONDICIONES COMERCIALES 1X

			// ///CONDICIONES COMERCIALES 2X
			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("") && i.getTipo() == 0) {
					BeanCondicionComercial2 b2 = new BeanCondicionComercial2();
					b2.setStrCliente(codCliente);
					b2.setStrGrupoCliente(""+ Integer.parseInt(beanCliente.getStrCodigoTipologia()));
					b2.setStrCanal(beanCliente.getStrCodCanalDist());
					b2.setStrMATNR(i.getCodigo());
					List<BeanCondicionComercial2> listaCondiciones2 = sqlMateriales.listarCondicion2(b2);
					Double dblDscto = new Double("0");
					BeanCondicionComercial2 beanCondicion2 = null;
					if (listaCondiciones2 != null&& listaCondiciones2.size() == 1) {
						beanCondicion2 = listaCondiciones2.get(0);
						dblDscto = beanCondicion2.getDblImporte() / 10;
						dblPrecioFinal = dblPrecioFinal * (100 + dblDscto)/ 100;
						i.setDblDesc2(dblDscto);
						i.setStrCondCalc2(beanCondicion2.getStrClaseCond());
						i.setStrNroCond2(beanCondicion2.getStrNroRegCond());
					} else {
						dblPrecioFinal = dblPrecioFinal + 0;
						i.setDblDesc2(new Double(0));
						i.setStrCondCalc2("");
						i.setStrNroCond2("");
					}
					int intSolicitado = (int) Double.parseDouble(i.getCantidad());
					// Nelson Villegas-Verificar stock
					// int intStock = (int)
					// Double.parseDouble(beanMaterial.getSt_1());
					String cantidadMaterialStock = sqlMateriales.obtenerStockMaterial(i.getCodigo());
					if (cantidadMaterialStock.length() == 0) {
						cantidadMaterialStock = "0";
					}
					int intStock = (int) Double.parseDouble(cantidadMaterialStock);
					// Nelson Villegas-Verificar stock
					// int intNuevoStock = 0;
					int intConfirmado = 0;
					if (intSolicitado <= intStock) {
						// intNuevoStock = intStock - intSolicitado;
						intConfirmado = intSolicitado;
					} else {
						// intNuevoStock = 0;
						intConfirmado = intStock;
					}
					i.setCantidadConfirmada(String.valueOf(intConfirmado));
				}
			}
			// ///CONDICIONES COMERCIALES 2X

			// ///CONDICIONES COMERCIALES 3X
			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("")&& i.getTipo() == 0) {
					BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
					// cliente
					b3.setStrCliente(codCliente);
					BeanMaterial objMat = obtenerMaterialDeGrilla(listaMaterialesGrilla, i.getCodigo());
					List<BeanClaseMaterial> listClaMat = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
					if (listClaMat != null && listClaMat.size() == 1) {
						// clase material
						b3.setStrClase(listClaMat.get(0).getStrDescripcionClaseMaterial());
					} else {
						String strClaseMaterialTemp = "";
						for (BeanClaseMaterial beanClaseMaterial : listClaMat) {
							// strClaseMaterialTemp+="-"+beanClaseMaterial.getStrDescripcionClaseMaterial();
							strClaseMaterialTemp += beanClaseMaterial.getStrDescripcionClaseMaterial() + "','";
						}
					}
					// jerarquia
					b3.setStrDivisionCom(objMat.getPrdha().substring(0, 2));
					b3.setStrCatProd(objMat.getPrdha().substring(2, 5));
					b3.setStrFamilia(objMat.getPrdha().substring(5, 9));
					b3.setStrLinea(objMat.getPrdha().substring(9, 13));
					b3.setStrGrupo(objMat.getPrdha().substring(13, 18));
					// material
					b3.setStrMaterial(i.getCodigo());
					// división
					BeanCliente objCliente = sqlCliente.buscarCliente(codCliente);
					b3.setStrDivisionC(objCliente.getStrGrupoVentas());
					BeanCondicionComercial3x beanCondicion3 = null;
					Double dblDscto = new Double("0");
					List<BeanCondicionComercial3x> listaCondiciones3x = sqlMateriales.listarCondicion3(b3);
					// se calcula el importe del pedido, considerando los % de
					// descuento que se aplicaron
					// en 1x y 2x de todos los items
					if (listaCondiciones3x != null && listaCondiciones3x.size() == 1) {
						beanCondicion3 = listaCondiciones3x.get(0);
						i.setStrCondCalc3(beanCondicion3.getStrClaseCond());
						i.setDblDesc3(dblDscto);
						i.setStrNroCond3(beanCondicion3.getStrNroRegCond());
					} else {
						i.setDblDesc3(new Double(0));
						i.setStrCondCalc3("");
						i.setStrNroCond3("");
					}
				}
			}
			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("") && i.getTipo() == 0) {
					BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
					// cliente
					b3.setStrCliente(codCliente);
					BeanMaterial objMat = obtenerMaterialDeGrilla( listaMaterialesGrilla, i.getCodigo());
					List<BeanClaseMaterial> listClaMat = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
					if (listClaMat != null && listClaMat.size() == 1) {
						// clase material
						b3.setStrClase(listClaMat.get(0).getStrDescripcionClaseMaterial());
					} else {
						String strClaseMaterialTemp = "";
						for (BeanClaseMaterial beanClaseMaterial : listClaMat) {
							// strClaseMaterialTemp+="-"+beanClaseMaterial.getStrDescripcionClaseMaterial();
							strClaseMaterialTemp += beanClaseMaterial.getStrDescripcionClaseMaterial() + "','";
						}
						b3.setStrClase(strClaseMaterialTemp);
					}
					// jerarquia
					b3.setStrDivisionCom(objMat.getPrdha().substring(0, 2));
					b3.setStrCatProd(objMat.getPrdha().substring(2, 5));
					b3.setStrFamilia(objMat.getPrdha().substring(5, 9));
					b3.setStrLinea(objMat.getPrdha().substring(9, 13));
					b3.setStrGrupo(objMat.getPrdha().substring(13, 18));
					// material
					b3.setStrMaterial(i.getCodigo());
					// división
					BeanCliente objCliente = sqlCliente.buscarCliente(codCliente);
					b3.setStrDivisionC(objCliente.getStrGrupoVentas());
					BeanCondicionComercial3x beanCondicion3 = null;
					Double dblDscto = new Double("0");
					List<BeanCondicionComercial3x> listaCondiciones3x = sqlMateriales.listarCondicion3(b3);
					// se calcula el importe del pedido, considerando los % de
					// descuento que se aplicaron
					// en 1x y 2x de todos los items
					dblImportePedido = calculaImportePedidoClaseCondicion1x2x2(lista, i, listaMaterialesGrilla);
					if (listaCondiciones3x != null&& listaCondiciones3x.size() == 1) {
						beanCondicion3 = listaCondiciones3x.get(0);
						if (beanCondicion3.getStrEscala().equals("X")) {
							dblDscto = beanCondicion3.getDblImporteEscala(dblImportePedido) / 10;
						} else {
							dblDscto = beanCondicion3.getDblImporte() / 10;
						}
						dblPrecioFinal = dblPrecioFinal * (100 + dblDscto) / 100;
						i.setStrCondCalc3(beanCondicion3.getStrClaseCond());
						i.setDblDesc3(dblDscto);
						i.setStrNroCond3(beanCondicion3.getStrNroRegCond());
					} else {
						dblPrecioFinal = dblPrecioFinal + 0;
						i.setDblDesc3(new Double(0));
						i.setStrCondCalc3("");
						i.setStrNroCond3("");
					}
				}
			}
			// ///CONDICIONES COMERCIALES 3X

			// ///CONDICIONES COMERCIALES 4X
			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("") && i.getTipo() == 0) {
					BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
					// cliente
					b3.setStrCliente(codCliente);
					BeanMaterial objMat = obtenerMaterialDeGrilla(listaMaterialesGrilla, i.getCodigo());
					List<BeanClaseMaterial> listClaMat = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
					if (listClaMat != null && listClaMat.size() == 1) {
						// clase material
						b3.setStrClase(listClaMat.get(0).getStrDescripcionClaseMaterial());
					} else {
						String strClaseMaterialTemp = "";
						for (BeanClaseMaterial beanClaseMaterial : listClaMat) {
							// strClaseMaterialTemp+="-"+beanClaseMaterial.getStrDescripcionClaseMaterial();
							strClaseMaterialTemp += beanClaseMaterial.getStrDescripcionClaseMaterial() + "','";
						}
						b3.setStrClase(strClaseMaterialTemp);
					}
					// jerarquia
					b3.setStrDivisionCom(objMat.getPrdha().substring(0, 2));
					b3.setStrCatProd(objMat.getPrdha().substring(2, 5));
					b3.setStrFamilia(objMat.getPrdha().substring(5, 9));
					b3.setStrLinea(objMat.getPrdha().substring(9, 13));
					b3.setStrGrupo(objMat.getPrdha().substring(13, 18));
					// material
					b3.setStrMaterial(i.getCodigo());
					// división
					BeanCliente objCliente = sqlCliente.buscarCliente(codCliente);
					b3.setStrDivisionC(objCliente.getStrGrupoVentas());
					BeanCondicionComercial4x beanCondicion4 = null;
					List<BeanCondicionComercial4x> listaCondiciones4x = sqlMateriales.listarCondicion4(b3);
					dblCantidadPedido = calculaCantidadPedidoClaseCondicion2(lista, i);
					//System.out.println("-->" + dblCantidadPedido);
					if (listaCondiciones4x != null&& listaCondiciones4x.size() == 1) {
						beanCondicion4 = listaCondiciones4x.get(0);
						i.setStrCondCalc4(beanCondicion4.getStrClaseCond());
						i.setStrNroCond4(beanCondicion4.getStrNroRegCond());
					} else {
						i.setStrCondCalc4("");
						i.setStrNroCond4("");
					}
				}
			}
			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("") && i.getTipo() == 0) {
					BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
					// cliente
					b3.setStrCliente(codCliente);
					BeanMaterial objMat = obtenerMaterialDeGrilla(listaMaterialesGrilla, i.getCodigo());
					List<BeanClaseMaterial> listClaMat = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
					if (listClaMat != null && listClaMat.size() == 1) {
						// clase material
						b3.setStrClase(listClaMat.get(0).getStrDescripcionClaseMaterial());
					} else {
						String strClaseMaterialTemp = "";
						for (BeanClaseMaterial beanClaseMaterial : listClaMat) {
							strClaseMaterialTemp += beanClaseMaterial.getStrDescripcionClaseMaterial() + "','";
						}
						b3.setStrClase(strClaseMaterialTemp);
					}
					// jerarquia
					b3.setStrDivisionCom(objMat.getPrdha().substring(0, 2));
					b3.setStrCatProd(objMat.getPrdha().substring(2, 5));
					b3.setStrFamilia(objMat.getPrdha().substring(5, 9));
					b3.setStrLinea(objMat.getPrdha().substring(9, 13));
					b3.setStrGrupo(objMat.getPrdha().substring(13, 18));
					// material
					b3.setStrMaterial(i.getCodigo());
					// división
					BeanCliente objCliente = sqlCliente.buscarCliente(codCliente);
					b3.setStrDivisionC(objCliente.getStrGrupoVentas());
					Double dblDscto = new Double("0");
					BeanCondicionComercial4x beanCondicion4 = null;
					List<BeanCondicionComercial4x> listaCondiciones4x = sqlMateriales.listarCondicion4(b3);
					dblCantidadPedido = calculaCantidadPedidoClaseCondicion2(lista, i);
					//System.out.println("-->" + dblCantidadPedido);
					if (listaCondiciones4x != null && listaCondiciones4x.size() == 1) {
						beanCondicion4 = listaCondiciones4x.get(0);
						if (beanCondicion4.getStrEscala().equals("X")) {
							dblDscto = beanCondicion4.getDblImporteEscala(dblCantidadPedido) / 10;
						} else {
							dblDscto = beanCondicion4.getDblImporte() / 10;
						}
						dblPrecioFinal = dblPrecioFinal * (100 + dblDscto) / 100;
						i.setDblDesc4(dblDscto);
						i.setStrCondCalc4(beanCondicion4.getStrClaseCond());
					} else {
						dblPrecioFinal = dblPrecioFinal + 0;
						i.setDblDesc4(new Double(0));
						i.setStrCondCalc4("");
					}
				}
			}
			// ///CONDICIONES COMERCIALES 4X

			// ///CONDICIONES COMERCIALES 5X
			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("") && i.getTipo() == 0) {
					BeanCondicionComercial5x beanCondicion5 = null;
					Double dblDscto = new Double("0");
					BeanCondicionComercial5x b5 = new BeanCondicionComercial5x();
					b5.setStrCliente(codCliente);
					BeanMaterial objMat = obtenerMaterialDeGrilla( listaMaterialesGrilla, i.getCodigo());
					b5.setStrDivisionCom(objMat.getPrdha().substring(0, 2));
					b5.setStrCatProd(objMat.getPrdha().substring(2, 5));
					b5.setStrFamilia(objMat.getPrdha().substring(5, 9));
					List<BeanCondicionComercial5x> listaCondiciones5x = sqlMateriales.listarCondicion5(b5);
					dblCantidadPedido = calculaImportePedido4x2(lista, i, listaMaterialesGrilla);
					if (listaCondiciones5x != null && listaCondiciones5x.size() == 1) {
						beanCondicion5 = listaCondiciones5x.get(0);
						if (beanCondicion5.getStrEscala().equals("X")) {
							dblDscto = beanCondicion5.getDblImporteEscala(dblCantidadPedido) / 10;
						} else {
							dblDscto = beanCondicion5.getDblImporte() / 10;
						}
						dblPrecioFinal = dblPrecioFinal * (100 + dblDscto) / 100;
						i.setDblDesc5(dblDscto);
						i.setStrCondCalc5(beanCondicion5.getStrClaseCond());
						i.setStrNroCond5(beanCondicion5.getStrNroRegCond());
					} else {
						dblPrecioFinal = dblPrecioFinal + 0;
						i.setDblDesc5(new Double(0));
						i.setStrCondCalc5("");
						i.setStrNroCond5("");
					}
				}
			}
			// ///CONDICIONES COMERCIALES 5X

			String strDescCalc = "";

			for (Item i : lista) {
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("") && i.getTipo() == 0) {

					strDescCalc += "precio bruto del material " + i.getCodigo() + ": " + i.getPrecioBase();
					strDescCalc += "\n";
					Double dblPrecioTemp = i.getPrecioBase();
					if (i.getDblDesc1() != null && i.getStrCondCalc1() != null && !i.getStrCondCalc1().equals("")) {
						dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc1()) / 100;
						strDescCalc += " primer descuento :" + i.getStrCondCalc1() + " / " + i.getDblDesc1();
						strDescCalc += " precio final :" + Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
						strDescCalc += "\n";
					}
					if (i.getDblDesc2() != null && i.getStrCondCalc2() != null && !i.getStrCondCalc2().equals("")) {
						dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc2()) / 100;
						strDescCalc += " segundo descuento :" + i.getStrCondCalc2() + " / " + i.getDblDesc2();
						strDescCalc += " precio final :" + Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
						strDescCalc += "\n";
					}
					if (i.getDblDesc3() != null && i.getStrCondCalc3() != null && !i.getStrCondCalc3().equals("")) {
						dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc3()) / 100;
						strDescCalc += " tercer descuento :" + i.getStrCondCalc3() + " / " + i.getDblDesc3();
						strDescCalc += " precio final :" + Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
						strDescCalc += "\n";
					}
					if (i.getDblDesc4() != null && i.getStrCondCalc4() != null && !i.getStrCondCalc4().equals("")) {
						dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc4()) / 100;
						strDescCalc += " cuarto descuento :" + i.getStrCondCalc4() + " / " + i.getDblDesc4();
						strDescCalc += " precio final :" + Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
						strDescCalc += "\n";
					}
					if (i.getDblDesc5() != null && i.getStrCondCalc5() != null && !i.getStrCondCalc5().equals("")) {
						dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc5()) / 100;
						strDescCalc += " quinto descuento :" + i.getStrCondCalc5() + " / " + i.getDblDesc5();
						strDescCalc += " precio final :" + Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
						strDescCalc += "\n";
					}
					if (!i.getPorcentajeDescuento().equals("")) {
						Double dblDsctoManual = Double.parseDouble(i .getPorcentajeDescuento());
						dblPrecioTemp = dblPrecioTemp * (100 - dblDsctoManual) / 100;
					} else {
						Double dblDsctoManual = Double.parseDouble("0.00");
						dblPrecioTemp = dblPrecioTemp * (100 - dblDsctoManual) / 100;
					}
					
					dblPrecioFinal = dblPrecioTemp;
					
					BigDecimal valorNeto = new BigDecimal(dblPrecioFinal* Integer.parseInt(i.getCantidadConfirmada()));
					valorNeto = valorNeto.setScale(2, RoundingMode.HALF_UP);
					
					i.setValorNeto(valorNeto.doubleValue());
					//i.setValorNeto(dblPrecioFinal* Integer.parseInt(i.getCantidadConfirmada()));
					i.setPrecioNeto(dblPrecioFinal);
//					i.setPrecioIVA(dblPrecioFinal + (dblPrecioFinal * 0.12));
					BeanMaterial material = sqlMateriales.getMaterial(i.getCodigo());
					if(material.getCell_design().equals("1"))
						i.setPrecioIVA(dblPrecioFinal + (dblPrecioFinal * Util.obtenerIva()));
					else
						i.setPrecioIVA(dblPrecioFinal);

					tblMateriales.setValueAt("" + i.getPrecioNeto(), i.getIntUbicacionGrilla(), 7);
					tblMateriales.setValueAt("" + i.getPrecioIVA(), i.getIntUbicacionGrilla(), 8);
					tblMateriales.setValueAt("" + i.getValorNeto(), i.getIntUbicacionGrilla(), 14);
					tblMateriales.setValueAt("" + i.getCantidadConfirmada(), i.getIntUbicacionGrilla(), 12);

					i.setSimulado(true);

					// Marcelo Moyano
					// Aparece Pila de Errores
					//System.out.println("\nMat:" + i.getCodigo());
					//System.out.println("Dscto 1:" + i.getStrCondCalc1() + " "+ i.getDblDesc1());
					//System.out.println("Dscto 2:" + i.getStrCondCalc2() + " " + i.getDblDesc2());
					//System.out.println("Dscto 3:" + i.getStrCondCalc3() + " " + i.getDblDesc3());
					//System.out.println("Dscto 4:" + i.getStrCondCalc4() + " " + i.getDblDesc4());
					//System.out.println("Dscto 5:" + i.getStrCondCalc5() + " " + i.getDblDesc5());
				}
			}
			tblMateriales.updateUI();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		} finally {
		}
	}

	private BeanMaterial obtenerMaterialDeGrilla(List<BeanMaterial> lista, String strCodMaterial) {
		for (BeanMaterial beanMaterial : lista) {
			if (beanMaterial.getIdMaterial().equals(strCodMaterial))
				return beanMaterial;
		}
		return null;
	}

	private Double calculaImportePedidoClaseCondicion1x2x2(List<Item> listaItem, Item i, List<BeanMaterial> listaMaterialesGrilla) {
		double dblRetorno = 0;
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = obtenerMaterialDeGrilla(listaMaterialesGrilla, item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
//					Double dblPrecioTemp = new Double(beanMaterial.getPrice_1());
					Double dblPrecioTemp = new Double(i.getPrecioBase());
					if (item.getDblDesc1() != null) {
						dblPrecioTemp = dblPrecioTemp * (100 + item.getDblDesc1()) / 100;
					}
					if (item.getDblDesc2() != null) {
						dblPrecioTemp = dblPrecioTemp * (100 + item.getDblDesc2()) / 100;
					}
					dblPrecio = dblPrecioTemp;
				}
				if (item.getCantidad() != null && !item.getCantidad().equals("")) {
					dblCantidad = new Double(item.getCantidad());
				}
				if (item.getCodigo() != null && !item.getCodigo().equals("") && item.getCantidad() != null 
						&& !item.getCantidad().equals("") && item.getStrCondCalc3() != null && i.getStrCondCalc3() != null
						&& item.getStrCondCalc3().equals(i.getStrCondCalc3()) && item.getStrNroCond3() != null && i.getStrNroCond3() != null
						&& item.getStrNroCond3().trim().equals(i.getStrNroCond3().trim())) {
					try {
						dblRetorno += (dblCantidad.doubleValue() * dblPrecio.doubleValue());
					} catch (Exception e) {
					}
				}
			}
		}
		return new Double(dblRetorno);
	}

	private Double calculaImportePedido4x2(List<Item> listaItem, Item i, List<BeanMaterial> listaMaterialesGrilla) {
		double dblRetorno = 0;
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = null;
				beanMaterial = obtenerMaterialDeGrilla(listaMaterialesGrilla, item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
					Double dblPrecioTemp = new Double(beanMaterial.getPrice_1());
					if (item.getDblDesc1() != null) {
						dblPrecioTemp = dblPrecioTemp * (100 + item.getDblDesc1()) / 100;
					}
					if (item.getDblDesc2() != null) {
						dblPrecioTemp = dblPrecioTemp * (100 + item.getDblDesc2()) / 100;
					}
					if (item.getDblDesc3() != null) {
						dblPrecioTemp = dblPrecioTemp * (100 + item.getDblDesc3()) / 100;
					}
					if (item.getDblDesc4() != null) {
						dblPrecioTemp = dblPrecioTemp * (100 + item.getDblDesc4()) / 100;
					}
					dblPrecio = dblPrecioTemp;
				}
				if (item.getCantidad() != null && !item.getCantidad().equals("")) {
					dblCantidad = new Double(item.getCantidad());
				}
				try {
					dblRetorno += dblCantidad.doubleValue() * dblPrecio.doubleValue();
				} catch (Exception e) {
				}
			}
		}
		return new Double(dblRetorno);
	}

	private Double calculaCantidadPedidoClaseCondicion2(List<Item> listaItem, Item i) {
		double dblRetorno = 0;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				System.out.println("..." + item.getStrCondCalc4() + " - " + i.getStrCondCalc4());
			}
			if (item.getCodigo() != null && !item.getCodigo().equals("") && item.getCantidad() != null
					&& !item.getCantidad().equals("") && item.getStrCondCalc4() != null && i.getStrCondCalc4() != null
					&& item.getStrCondCalc4().equals(i.getStrCondCalc4()) && item.getStrNroCond4() != null && i.getStrNroCond4() != null
					&& item.getStrNroCond4().trim().equals(i.getStrNroCond4().trim())) {
				try {
					dblRetorno += Double.parseDouble(item.getCantidad());
				} catch (Exception e) {
				}
			}
		}
		return new Double(dblRetorno);
	}
	/*
	 * MARCELO MOYANO
	 */
	// Variables declaration - do not modify
	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnBuscarVoz;
	private javax.swing.JButton btnCerrar;
	private javax.swing.JButton btnInicioPagina;
	private javax.swing.JButton btnPaginaAnterior;
	private javax.swing.JButton btnPasarPedido;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbDescripcionLarga;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbCategoria;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbCondPago;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbDivision;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbFamilia;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbGrupo;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbLinea;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbTipo;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbTipoConsulta;
	private javax.swing.JButton btnPaguinaSiguiente;
	private javax.swing.JButton btnPaginaFin;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelCabecera;
	private javax.swing.JLabel lblCantidadRegistros;
	private javax.swing.JLabel lblCategoria;
	private javax.swing.JLabel lblCliente;
	private javax.swing.JLabel lblCondPago;
	private javax.swing.JLabel lblConfirmacion;
	private javax.swing.JLabel lblDescripcionLarga;
	private javax.swing.JLabel lblDivision;
	private javax.swing.JLabel lblFamilia;
	private javax.swing.JLabel lblGrupo;
	private javax.swing.JLabel lblLinea;
	private javax.swing.JLabel lblPaginas;
	private javax.swing.JLabel lblTipo;
	private javax.swing.JLabel lblTipoConsulta;
	private javax.swing.JScrollPane scrollMateriales;
	private javax.swing.JTable tblMateriales;
	private javax.swing.JTextField txtDescripcionCorta;// editor de cmbDescripción larga
	private javax.swing.JTextField txtDescripcionLarga;
	private javax.swing.JTextField txtMarca;
	private javax.swing.JTextField txtMaterial;
}


