package com.promesa.dialogo.pedidos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
//import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.GraphicsEnvironment;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
//import javax.swing.GroupLayout;
//import javax.swing.JFrame;
//import javax.swing.GroupLayout.Alignment;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
//import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

//import com.promesa.administracion.bean.BeanUsuario;
//import com.promesa.administracion.sql.impl.SqlUsuarioImpl;
import com.promesa.bean.BeanConexion;
import com.promesa.bean.BeanDato;
//import com.promesa.dialogo.ExceptionViewer;
import com.promesa.internalFrame.pedidos.IPedidos;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.internalFrame.pedidos.custom.ModeloConsultaDinamica;
import com.promesa.internalFrame.pedidos.custom.RendererConsultaDinamica;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.bean.BeanClaseMaterial;
import com.promesa.pedidos.bean.BeanCondicionComercial1;
import com.promesa.pedidos.bean.BeanCondicionComercial2;
import com.promesa.pedidos.bean.BeanCondicionComercial3x;
import com.promesa.pedidos.bean.BeanCondicionComercial4x;
import com.promesa.pedidos.bean.BeanCondicionComercial5x;
//import com.promesa.pedidos.bean.BeanCondicionExpedicion;
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

@SuppressWarnings("serial")
public class DConsultaMateriales extends javax.swing.JDialog implements
		FocusListener, WindowListener {

	// private static final long serialVersionUID = 1L;
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
	private ListSelectionModel selectionModel;
	static MyOwnFocusTraversalPolicy newPolicy;
	private String consulta = "";
	private long cantidadRegistros = 0;
	private int paginaActual = 0;
	private int cantidadPaginas = 0;
	private int tipo;
	private String strFiltro;

	// ----- CONSULTA DINAMICA ------------- //
	// ------- MARCELO MOYANO -------------- //

	private Indexador indexDivision;
	private Indexador indexCategoria;
	private Indexador indexFamilia;
	private Indexador indexLinea;
	private Indexador indexGrupo;
	//
	private Indexador indexMaterial;
//	private Indexador indexTopCliente;
//	private Indexador indexTopTopologia;
//	private Indexador indexPromoOferta;
//	private Indexador indexDescManuales;
//	private Indexador indexDescPolitica;
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
	//
	boolean flag;
	boolean activarDivision = true;
	boolean activarCategoria = true;
	boolean activarFamilia = true;
	boolean activarLinea = true;
	boolean activarGrupo = true;
	boolean activarBusqueda = true;
	//
	private List<BeanMaterial> listaMaterialesABuscar; // ---- ESTA LISTA VA EN
														// EL COMBOX DE
														// MATERIALES ---- //

	// -------------------------------------- //

	// private ModeloConsultaDinamica modeloTablaItems;

	/** Creates new form DConsultaMateriales */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DConsultaMateriales(List<BeanDato> datose, BeanCliente cliente,
			IPedidos contenedor, java.awt.Frame parent, boolean modal,
			BeanAgenda agenda, String condPago) {
		super(Promesa.getInstance(), true);
		this.condPago = condPago;
		this.cliente = cliente;
		this.agenda = agenda;
		this.contenedor = contenedor;
		initComponents();
		cambiarVisibilidadLimitadorRegistros(false);
		selectionModel = tblMateriales.getSelectionModel();
		setAnchoColumnas();
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		this.setLocationRelativeTo(null);
		condiciones.setListaCondicionesPago();
		materialese = new ArrayList<BeanMaterial>();
		comodin = new BeanJerarquia();
		comodin.setStrVTEXT("Todos");

		// --------- CONSULTA DINAMICA -------------- //
		// //------------ INDEX JARARQUIAS ------------- //

		indexDivision = new Indexador();
		indexCategoria = new Indexador();
		indexFamilia = new Indexador();
		indexLinea = new Indexador();
		indexGrupo = new Indexador();

		// ------------------------------------------ //

		// ------------ INDEX DE MATERIALES --------- //

//		indexMaterial = new Indexador();
//		indexTopCliente = new Indexador();
//		indexTopTopologia = new Indexador();
//		indexPromoOferta = new Indexador();
//		indexDescManuales = new Indexador();
//		indexDescPolitica = new Indexador();

		// ----------------------------------------------- //

		uno = new ArrayList<BeanJerarquia>();
		dos = new ArrayList<BeanJerarquia>();
		tres = new ArrayList<BeanJerarquia>();
		cuatro = new ArrayList<BeanJerarquia>();
		cinco = new ArrayList<BeanJerarquia>();

		listaMaterialesABuscar = new ArrayList<BeanMaterial>();
		//
		strMaterial = "";
		strDivision = "";
		strCategoria = "";
		strFamilia = "";
		strLinea = "";
		strGrupo = "";
		//
		flag = false;
		tipo = 0;
		//
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

		// ------------------- CONSULTA DINAMICA ------------------ //

		// uno = jerarquia.getListaJerarquiaPorNiveles("1");
		// dos = jerarquia.getListaJerarquiaPorNiveles("2");
		// tres = jerarquia.getListaJerarquiaPorNiveles("3");
		// cuatro = jerarquia.getListaJerarquiaPorNiveles("4");
		// cinco = jerarquia.getListaJerarquiaPorNiveles("5");
		// uno.add(0, comodin);
		// dos.add(0, comodin);
		// tres.add(0, comodin);
		// cuatro.add(0, comodin);
		// cinco.add(0, comodin);
		// ------------- MARCELO MOYANO ------------------- //

		// AutoCompleteDecorator.decorate(cmbDescripcionLarga);
		// AutoCompleteDecorator.decorate(cmbDivision);
		// AutoCompleteDecorator.decorate(cmbCategoria);
		// AutoCompleteDecorator.decorate(cmbFamilia);
		// AutoCompleteDecorator.decorate(cmbGrupo);
		// AutoCompleteDecorator.decorate(cmbLinea);
		llenarTabla();
		tblMateriales.setDefaultRenderer(String.class, new RendererConsultaDinamica());
		tblMateriales.setDefaultRenderer(Double.class, new RendererConsultaDinamica());
		tblMateriales.setDefaultRenderer(Long.class, new RendererConsultaDinamica());

//		txtDescripcionLarga.addKeyListener(new java.awt.event.KeyAdapter() {
//			public void keyReleased(java.awt.event.KeyEvent evt) {
//				escucharEventoEnter(evt);
//			}
//
//			public void keyTyped(java.awt.event.KeyEvent e) {
//			}
//
//			public void keyPressed(KeyEvent e) {
//
//			}
//		});

		txtDescripcionLarga.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtDescripcionLarga)
					txtDescripcionLarga.selectAll();
			}
		});
//		txtDescripcionCorta.addKeyListener(new java.awt.event.KeyAdapter() {
//			public void keyReleased(java.awt.event.KeyEvent evt) {
//				escucharEventoEnter(evt);
//			}
//
//			public void keyTyped(KeyEvent e) {
//			}
//
//			public void keyPressed(KeyEvent e) {
//			}
//		});
		txtDescripcionCorta.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtDescripcionCorta)
					txtDescripcionCorta.selectAll();
			}
		});

//		txtMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
//			public void keyReleased(java.awt.event.KeyEvent evt) {
//				escucharEventoEnter(evt);
//			}
//
//			public void keyTyped(KeyEvent e) {
//			}
//
//			public void keyPressed(KeyEvent e) {
//			}
//		});

		txtMaterial.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtMaterial)
					txtMaterial.selectAll();
			}
		});

//		txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
//			public void keyReleased(java.awt.event.KeyEvent evt) {
//				escucharEventoEnter(evt);
//			}
//
//			//
//			public void keyTyped(KeyEvent e) {
//			}
//
//			//
//			public void keyPressed(KeyEvent e) {
//			}
//		});
		//
		txtMarca.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (evt.getSource() == txtMarca)
					txtMarca.selectAll();
			}
		});
		lblCliente.setText("");
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

		// ------- CONSULTA DINAMICA ------------------ //
		try {
			cargarJerarquiaMateriales();
		} catch (IOException e1) {
			e1.printStackTrace();
			Util.mostrarExcepcion(e1);
		}
		// ------------- MARCELO MOYANO -------------- //
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

	// @SuppressWarnings("serial")
	private void escucharEventoTeclado() {
		TableColumn columnaCantidad = tblMateriales.getColumnModel().getColumn(4);
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
				if (keyEvent.getKeyCode() == KeyEvent.VK_TAB ) {
					int fila = tblMateriales.getSelectedRow();
					int cantidad = 0;
					try {
						cantidad = Integer.parseInt(textFieldCantidad.getText());
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						cantidad = 0;
					}
					textFieldCantidad.setText("" + cantidad);
					int numeroFilas = tblMateriales.getRowCount();
					if (fila < numeroFilas - 1) {
						fila++;
						selectionModel.setSelectionInterval(fila, fila);
						tblMateriales.editCellAt(fila, 4);
						tblMateriales.setValueAt(cantidad, fila, 4);
						textFieldCantidad.setBackground(new Color(255, 251, 170));
						textFieldCantidad.requestFocusInWindow();
						textFieldCantidad.setEditable(true);
						textFieldCantidad.selectAll();
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
		jSpinner1.setVisible(visibilidad);
		jLabel1.setVisible(visibilidad);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == textFieldCantidad) {
			textFieldCantidad.setBackground(new Color(255, 251, 170));
			textFieldCantidad.requestFocusInWindow();
			textFieldCantidad.setEditable(true);
			textFieldCantidad.selectAll();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == textFieldCantidad) {
			textFieldCantidad.setBackground(Color.white);
			textFieldCantidad.setEditable(false);
		}
	}

	protected void escucharEventoEnter(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			buscar();
		}
	}

	// ----------------------- CONSULTA DINAMICA -------------------------- //
	// ------------------------ MARCELO MOYANO ---------------------------- //
	// ------------- CARGA LAS JERARQUIA DE LOS MATERIALES ---------------- //

	private void cargarJerarquiaMateriales() throws IOException {
		indexDivision.reconstruirIndexJerarquia("1");
		indexCategoria.reconstruirIndexJerarquia("2");
		indexFamilia.reconstruirIndexJerarquia("3");
		indexLinea.reconstruirIndexJerarquia("4");
		indexGrupo.reconstruirIndexJerarquia("5");

		indexMaterial = new Indexador();
		indexMaterial.reconstruirIndexMaterial();
//		indexTopCliente.reconstruirTopCliente(cliente.getStrIdCliente());
//		indexTopTopologia.reconstruirTopTipologia(cliente.getStrCodigoTipologia());
//		indexPromoOferta.reconstruirPromoOferta();
//		indexDescPolitica.reconstruirDescuentoPolitica();
//		indexDescManuales.reconstruirDescuentoManuales();
	}

	// --------------------------------------------------------------- //

	private void invocarSimulacion() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					String condicion_pago = ((BeanCondicionPago) cmbCondPago.getSelectedItem()).getTxtZTERM();
					BeanDato usuario = Promesa.datose.get(0);
					BeanPedido pedido = llenarEstructura(usuario, condicion_pago);
					BeanPedidoHeader h = pedido.getHeader();
					if (usuario.getStrModo().equals("1")) {
						// DESDE SAP
						SPedidos objSAP = new SPedidos();
						try {
							List<String[]> items = objSAP.invocarSimulacion(h
									.getDOC_TYPE(), h.getSALES_ORG(), h
									.getDISTR_CHAN(), h.getDIVISION(), h
									.getSALES_GRP(), h.getSALES_OFF(), h
									.getREQ_DATE_H(), h.getPURCH_DATE(), h
									.getPMNTTRMS(), h.getDLV_BLOCK(), h
									.getPRICE_DATE(), h.getPURCH_NO_C(), h
									.getSD_DOC_CAT(), h.getDOC_DATE(), h
									.getBILL_DATE(), h.getSERV_DATE(), h
									.getCURRENCY(), h.getCREATED_BY(), h
									.getSHIP_TYPE(), pedido.getPartners()
									.getHm(), pedido.getDetalles());

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
								tblMateriales.updateUI();
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
						String codigoSap = Util.completarCeros(8, strings[1]);
						if (codigoTabla.compareTo(codigoSap) == 0) {
							Double precio = 0d;
							Double precioiva = 0d;
							Double precioBase = 0d;
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
							final int value2 = i;
							final double pi = precioiva;
							final double p = precio;
							final double pb = precioBase;
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
									tblMateriales.setValueAt(pb, value2, 5);
									double tempPi = pi;
									if (cc != 0d) {
										tempPi /= cc;
									}
									double tempPb = pb;
									tempPi = Double.parseDouble(Util.formatearNumero(tempPi));
									tempPb = Double.parseDouble(Util.formatearNumero(tempPb));
									tblMateriales.setValueAt((tempPi + tempPb), value2, 6);
									tblMateriales.setValueAt(p, value2, 7);
									tblMateriales.setValueAt(cantidadConfirmada, value2, 11);
								}
							});
						}

					}
				}
			}
		});

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
		if ((SALES_ORG == null || SALES_ORG.isEmpty())
				|| (DISTR_CHAN == null || DISTR_CHAN.isEmpty()) || (DIVISION == null || DIVISION.isEmpty())) {
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
			String material = tblMateriales.getValueAt(i, 0).toString();
			String cantidad = "";
			try {
				cantidad = ""
						+ Integer.parseInt(tblMateriales.getValueAt(i, 4)
								.toString());
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
				detalles.add(detalle);
			}
		}
		pedido.setHeader(header);
		pedido.setPartners(partners);
		pedido.setDetalles(detalles);
		return pedido;
	}
	
	
	private void cmbTipoConsultaActionPerformed(java.awt.event.ActionEvent evt) {
		// int opcion = cmbTipoConsulta.getSelectedIndex();
		tipo = cmbTipoConsulta.getSelectedIndex();
		String mensaje = "";

		// Limpiar opciones de busqueda
		// cmbDivision.setSelectedIndex(0);
		// cmbCategoria.setSelectedIndex(0);
		// cmbFamilia.setSelectedIndex(0);
		// cmbLinea.setSelectedIndex(0);
		// cmbGrupo.setSelectedIndex(0);
		// txtDescripcionLarga.setText("");
		// txtDescripcionCorta.setText("");
		// txtMaterial.setText("");
		// txtMarca.setText("");
		// cmbTipo.setSelectedIndex(0);

		// ---------- CONSULTA DINAMICA ---------- //
		// Limpiar opciones de busqueda
		cmbDescripcionLarga.removeAllItems();
		cmbDivision.removeAllItems();
		cmbCategoria.removeAllItems();
		cmbFamilia.removeAllItems();
		cmbLinea.removeAll();
		cmbGrupo.removeAll();
		// Limpiar opciones de busqueda
		// ----------- CONSULTA DINAMICA ----------- //
		indexMaterial = new Indexador();
		switch (tipo) {
		case 0:
			// Por material
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
			try {
				indexMaterial.reconstruirIndexMaterial();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			// Por cliente
			mensaje = cliente.getStrIdCliente() + "-" + cliente.getStrNombreCliente().toUpperCase();
			cambiarVisibilidadLimitadorRegistros(true);
			try {
				indexMaterial.reconstruirTopCliente(cliente.getStrIdCliente());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			// Por tipologia
			mensaje = cliente.getStrCodigoTipologia() + "-" + cliente.getStrDescripcionTipologia();
			cambiarVisibilidadLimitadorRegistros(true);
			try {
				indexMaterial.reconstruirTopTipologia(cliente.getStrCodigoTipologia());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:// Promo Oferta
			try {
				indexMaterial.reconstruirPromoOferta();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:// descuento politica
			try {
				indexMaterial.reconstruirDescuentoPolitica();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:// descuentos manuales
			try {
				indexMaterial.reconstruirDescuentoManuales();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
			break;
		}
		lblCliente.setText(mensaje);
	}

	@SuppressWarnings("unused")
	private void cmbTipoConsultaActionPerformed2(java.awt.event.ActionEvent evt) {
		// int opcion = cmbTipoConsulta.getSelectedIndex();
		tipo = cmbTipoConsulta.getSelectedIndex();
		String mensaje = "";

		// Limpiar opciones de busqueda
		// cmbDivision.setSelectedIndex(0);
		// cmbCategoria.setSelectedIndex(0);
		// cmbFamilia.setSelectedIndex(0);
		// cmbLinea.setSelectedIndex(0);
		// cmbGrupo.setSelectedIndex(0);
		// txtDescripcionLarga.setText("");
		// txtDescripcionCorta.setText("");
		// txtMaterial.setText("");
		// txtMarca.setText("");
		// cmbTipo.setSelectedIndex(0);

		// ---------- CONSULTA DINAMICA ---------- //
		// Limpiar opciones de busqueda
		cmbDescripcionLarga.removeAllItems();
		cmbDivision.removeAllItems();
		cmbCategoria.removeAllItems();
		cmbFamilia.removeAllItems();
		cmbLinea.removeAll();
		cmbGrupo.removeAll();
		// Limpiar opciones de busqueda
		// ----------- CONSULTA DINAMICA ----------- //
		switch (tipo) {
		case 0:
			// Por material
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
			break;
		case 1:
			// Por cliente
			mensaje = cliente.getStrIdCliente() + "-" + cliente.getStrNombreCliente().toUpperCase();
			cambiarVisibilidadLimitadorRegistros(true);
			break;
		case 2:
			// Por tipologia
			mensaje = cliente.getStrCodigoTipologia() + "-"
					+ cliente.getStrDescripcionTipologia();
			cambiarVisibilidadLimitadorRegistros(true);
			break;
		default:
			mensaje = "";
			cambiarVisibilidadLimitadorRegistros(false);
			break;
		}
		lblCliente.setText(mensaje);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		jPanel5 = new javax.swing.JPanel();
		pnlTipo = new javax.swing.JPanel();
		lblTipoConsulta = new javax.swing.JLabel();
		cmbTipoConsulta = new javax.swing.JComboBox();
		lblCliente = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jSpinner1 = new javax.swing.JSpinner();
		jPanel2 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		pnlLeft = new javax.swing.JPanel();
		lblDescripcionLarga = new javax.swing.JLabel();
		lblMaterial = new javax.swing.JLabel();
		pnlLeft1 = new javax.swing.JPanel();
		txtDescripcionCorta = new javax.swing.JTextField();
		pnlBottom = new javax.swing.JPanel();
		txtMaterial = new javax.swing.JTextField();
		lblMarca = new javax.swing.JLabel();
		txtMarca = new javax.swing.JTextField();
		jPanel4 = new javax.swing.JPanel();
		pnlLeft2 = new javax.swing.JPanel();
		lblDescripcionCorta = new javax.swing.JLabel();
		cmbDescripcionLarga = new javax.swing.JComboBox();
		lblTipo = new javax.swing.JLabel();
		pnlLeft3 = new javax.swing.JPanel();
		txtDescripcionLarga = new javax.swing.JTextField();
		jPanel1 = new javax.swing.JPanel();
		cmbTipo = new javax.swing.JComboBox();
		lblCondPago = new javax.swing.JLabel();
		cmbCondPago = new javax.swing.JComboBox();
		pnlBotones = new javax.swing.JPanel();
		btnBuscar = new javax.swing.JButton();
		btnBuscarVoz = new javax.swing.JButton();
		btnPasarPedido = new javax.swing.JButton();
		pnlMateriales = new javax.swing.JPanel();
		pnlPaginacion = new javax.swing.JPanel();
		btnInicioPagina = new javax.swing.JButton();
		btnPaginaAnterior = new javax.swing.JButton();
		lblPaginas = new javax.swing.JLabel();
		btnPaguinaSiguiente = new javax.swing.JButton();
		btnPaginaFin = new javax.swing.JButton();
		lblConfirmacion = new javax.swing.JLabel();
		scrollMateriales = new javax.swing.JScrollPane();
		tblMateriales = new javax.swing.JTable();
		btnCerrar = new javax.swing.JButton();
		jPanel6 = new javax.swing.JPanel();
		jPanel7 = new javax.swing.JPanel();
		lblDivision = new javax.swing.JLabel();
		cmbDivision = new javax.swing.JComboBox();
		jPanel8 = new javax.swing.JPanel();
		lblCategoria = new javax.swing.JLabel();
		cmbCategoria = new javax.swing.JComboBox();
		jPanel9 = new javax.swing.JPanel();
		lblFamilia = new javax.swing.JLabel();
		cmbFamilia = new javax.swing.JComboBox();
		jPanel10 = new javax.swing.JPanel();
		lblLinea = new javax.swing.JLabel();
		cmbLinea = new javax.swing.JComboBox();
		jPanel11 = new javax.swing.JPanel();
		lblGrupo = new javax.swing.JLabel();
		cmbGrupo = new javax.swing.JComboBox();
		lblCantidadRegistros = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Consulta de Materiales");

		pnlTipo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		lblTipoConsulta.setText("Tipo de Consulta:");
		pnlTipo.add(lblTipoConsulta);

		cmbTipoConsulta.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "Por Material", "Top x Cliente",
						"Top x Tipologia", "PromeOferta", "Dscto Politica",
						"Mat Dscto Manual" }));
		cmbTipoConsulta.setPreferredSize(new java.awt.Dimension(150, 20));
		cmbTipoConsulta.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbTipoConsultaActionPerformed(evt);
			}
		});
		pnlTipo.add(cmbTipoConsulta);

		lblCliente.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblCliente.setText("<Cliente>");
		pnlTipo.add(lblCliente);

		jLabel2.setText("      ");
		pnlTipo.add(jLabel2);

		jLabel1.setText("Cantidad de Registros:");
		pnlTipo.add(jLabel1);

		jSpinner1.setModel(new javax.swing.SpinnerNumberModel(10, 10, 1000, 1));
		jSpinner1.setMinimumSize(new java.awt.Dimension(60, 20));
		jSpinner1.setPreferredSize(new java.awt.Dimension(60, 20));
		pnlTipo.add(jSpinner1);

		jPanel2.setLayout(new java.awt.GridLayout(1, 0, 4, 4));

		jPanel3.setLayout(new java.awt.BorderLayout(4, 0));

		pnlLeft.setLayout(new java.awt.GridLayout(2, 1, 4, 4));

		lblDescripcionLarga.setText("Busqueda:");
		pnlLeft.add(lblDescripcionLarga);

		lblMaterial.setText("Material:");
		lblMaterial.setVisible(false);
		// pnlLeft.add(lblMaterial);
		pnlLeft.add(lblTipo);

		jPanel3.add(pnlLeft, java.awt.BorderLayout.LINE_START);

		pnlLeft1.setLayout(new java.awt.GridLayout(2, 1, 4, 8));

		// --------------------------------- CONSULTA DINAMICA ----------------------------------------- //
		// ----------------------------------- MARCELO MOYANO  ------------------------------------------ //
		// ------------------------------------- MATERIALES -------------------------------------------- //
		cmbDescripcionLarga.setMaximumSize(new java.awt.Dimension(300, 20));
		cmbDescripcionLarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
		txtDescripcionCorta = (JTextField) cmbDescripcionLarga.getEditor().getEditorComponent();
		txtDescripcionCorta.setMaximumSize(new java.awt.Dimension(300, 20));
		txtDescripcionCorta.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtDescripcionCortaKeyReleased(evt);
				// escucharEventoEnter(evt);
			}
		});
		txtDescripcionCorta.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (activarBusqueda) {
					strMaterial = "";
					txtDescripcionCorta.setText("");
					listaMaterialesABuscar.clear();
					cmbDescripcionLarga.removeAllItems();
					cmbDescripcionLarga.setModel(new DefaultComboBoxModel(new String[] {}));
					cmbDescripcionLarga.setRenderer(new MyComboBoxRenderer2(listaMaterialesABuscar));
				}
				activarBusqueda = true;
			}
		});
		cmbDescripcionLarga.setBounds(145, 60, 480, 24);
		pnlLeft1.add(cmbDescripcionLarga);// --- consulta dinamica de materiales --- //
		// pnlLeft1.add(txtDescripcionCorta);
		// --------------------------------- CONSULTA DINAMICA ------------------------------------------- //

		pnlBottom.setLayout(new java.awt.GridLayout(1, 3, 4, 4));
		txtMaterial.setVisible(false);
		// pnlBottom.add(txtMaterial);
		pnlBottom.add(cmbTipo);

		lblMarca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblMarca.setText("Marca:");
		// pnlBottom.add(lblMarca);
		// pnlBottom.add(txtMarca);
		pnlBottom.add(lblCondPago);
		pnlBottom.add(cmbCondPago);

		pnlLeft1.add(pnlBottom);

		jPanel3.add(pnlLeft1, java.awt.BorderLayout.CENTER);

		jPanel2.add(jPanel3);

		jPanel4.setLayout(new java.awt.BorderLayout(4, 0));

		pnlLeft2.setLayout(new java.awt.GridLayout(2, 1, 4, 4));

		lblDescripcionCorta.setText("Descripción Corta:");
		lblDescripcionCorta.setVisible(false);
		pnlLeft2.add(lblDescripcionCorta);
		// pnlLeft2.add(btnBuscar);

		lblTipo.setText("Tipo:");
		// pnlLeft2.add(lblTipo);
		pnlLeft2.add(btnPasarPedido);

		jPanel4.add(pnlLeft2, java.awt.BorderLayout.LINE_START);

		pnlLeft3.setLayout(new java.awt.GridLayout(2, 4, 4, 8));

		txtDescripcionLarga.setVisible(false);
		pnlLeft3.add(txtDescripcionLarga);

		pnlLeft3.add(btnBuscar);

		btnBuscarVoz.setText("Voz");
		btnBuscarVoz.setEnabled(false);
		pnlLeft3.add(btnBuscarVoz);
		btnBuscarVoz.setText("Voz");
		btnBuscarVoz.setEnabled(false);
		pnlLeft3.add(btnBuscarVoz);
		pnlLeft3.add(new JLabel());

		cmbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"Item 1", "Item 2", "Item 3", "Item 4" }));

		lblCondPago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCondPago.setText("<html>Cond. Pago:<font color='red'> *</font></html>");

		cmbCondPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"Item 1", "Item 2", "Item 3", "Item 4" }));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		
//		 jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//				 .addGroup(jPanel1Layout.createSequentialGroup().addComponent(cmbTipo,0, 83, Short.MAX_VALUE)
//				 .addGroup(jPanel1Layout.createSequentialGroup().addComponent(btnPasarPedido,0, 83, Short.MAX_VALUE)
//				 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//				 .addComponent(lblCondPago, javax.swing.GroupLayout.DEFAULT_SIZE, 83,Short.MAX_VALUE)
//				 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//				 .addComponent(cmbCondPago, 0, 84, Short.MAX_VALUE)));
		
		
//		 jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//		 .addGroup(jPanel1Layout.createSequentialGroup()
//		 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//		 .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE,
//		 javax.swing.GroupLayout.DEFAULT_SIZE,
//		 javax.swing.GroupLayout.PREFERRED_SIZE)
//		 .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
//		 .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//		 .addComponent(lblCondPago, javax.swing.GroupLayout.Alignment.LEADING,
//		 javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
//		 .addComponent(cmbCondPago, javax.swing.GroupLayout.DEFAULT_SIZE, 20,
//		 Short.MAX_VALUE))) .addContainerGap()));

		pnlLeft3.add(jPanel1);

		jPanel4.add(pnlLeft3, java.awt.BorderLayout.CENTER);

		jPanel2.add(jPanel4);

		pnlBotones.setLayout(new java.awt.FlowLayout(
				java.awt.FlowLayout.CENTER, 5, 0));

		btnBuscar.setText("Buscar");
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});
		// pnlBotones.add(btnBuscar);

		btnPasarPedido.setText("Pasar Pedido");
		btnPasarPedido.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPasarPedidoActionPerformed(evt);
			}
		});
		// pnlBotones.add(btnPasarPedido);

		pnlMateriales.setLayout(new java.awt.BorderLayout());

		btnInicioPagina.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnInicioPagina.setText("|<");
		btnInicioPagina.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInicioPaginaActionPerformed(evt);
			}
		});
		pnlPaginacion.add(btnInicioPagina);

		btnPaginaAnterior.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnPaginaAnterior.setText("<");
		btnPaginaAnterior
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnPaginaAnteriorActionPerformed(evt);
					}
				});
		pnlPaginacion.add(btnPaginaAnterior);

		lblPaginas.setBackground(new java.awt.Color(255, 255, 255));
		lblPaginas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblPaginas.setText("0/0");
		lblPaginas.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));
		lblPaginas.setOpaque(true);
		pnlPaginacion.add(lblPaginas);

		btnPaguinaSiguiente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnPaguinaSiguiente.setText(">");
		btnPaguinaSiguiente
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnPaguinaSiguienteActionPerformed(evt);
					}
				});
		pnlPaginacion.add(btnPaguinaSiguiente);

		btnPaginaFin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnPaginaFin.setText(">|");
		btnPaginaFin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPaginaFinActionPerformed(evt);
			}
		});
		pnlPaginacion.add(btnPaginaFin);

		pnlMateriales.add(pnlPaginacion, java.awt.BorderLayout.PAGE_END);
		pnlMateriales.add(lblConfirmacion, java.awt.BorderLayout.PAGE_START);
		lblConfirmacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		tblMateriales.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		scrollMateriales.setViewportView(tblMateriales);

		pnlMateriales.add(scrollMateriales, java.awt.BorderLayout.CENTER);

		btnCerrar.setText("Cerrar");
		btnCerrar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCerrarActionPerformed(evt);
			}
		});

		jPanel7.setLayout(new java.awt.GridLayout(2, 1));
		jPanel7.setMaximumSize(new java.awt.Dimension(40, 35));

		lblDivision.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblDivision.setText("División");
		lblDivision.setMaximumSize(new java.awt.Dimension(25, 10));
		lblDivision.setMinimumSize(new java.awt.Dimension(25, 10));
		lblDivision.setPreferredSize(new java.awt.Dimension(25, 10));
		jPanel7.add(lblDivision);

		cmbDivision.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
		cmbDivision.setMaximumSize(new java.awt.Dimension(36, 20));
		cmbDivision.setMinimumSize(new java.awt.Dimension(36, 20));
		// ------------------------ CONSULTA DINAMICA  -------------------------------- //
		// -------------------------- MARCELO MOYANO --------------------------------- //
		txtDivisionEditor = (JTextField) cmbDivision.getEditor().getEditorComponent();
		txtDivisionEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtDivisionEditorKeyReleased(evt);
			}
		});
		txtDivisionEditor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (activarDivision) {
					strDivision = "";
					txtDivisionEditor.setText("");
					cmbDivision.removeAllItems();
					cmbDivision.setModel(new DefaultComboBoxModel(new String[] {}));
					cmbDivision.setRenderer(new MyComboBoxRenderer(uno));
				}
				activarDivision = true;
			}
		});

		// cmbDivision.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// cmbDivisionActionPerformed(evt);
		// }
		// });
		// ---------------------------- CONSULTA DINAMICA  --------------------------------- //
		jPanel7.add(cmbDivision);

		jPanel8.setLayout(new java.awt.GridLayout(2, 1));

		lblCategoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblCategoria.setText("Categoría");
		lblCategoria.setMaximumSize(new java.awt.Dimension(25, 10));
		lblCategoria.setMinimumSize(new java.awt.Dimension(25, 10));
		lblCategoria.setPreferredSize(new java.awt.Dimension(25, 10));
		jPanel8.add(lblCategoria);

		cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
		cmbCategoria.setMaximumSize(new java.awt.Dimension(56, 20));
		cmbCategoria.setMinimumSize(new java.awt.Dimension(56, 20));
		// --------------------- CONSULTA DINAMICA CATEGORIA ------------------------- //
		// -------------------------- MARCELO MOYANO -------------------------------- //
		txtCategoriaEditor = (JTextField) cmbCategoria.getEditor().getEditorComponent();
		txtCategoriaEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtCategoriaEditorKeyReleased(evt);
				// }
			}
		});
		txtCategoriaEditor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (activarCategoria) {
					strCategoria = "";
					txtCategoriaEditor.setText("");
					dos.clear();
					cmbCategoria.removeAllItems();
					cmbCategoria.setModel(new DefaultComboBoxModel(new String[] {}));
					cmbCategoria.setRenderer(new MyComboBoxRenderer(dos));
				}
				activarCategoria = true;
			}
		});
		// cmbCategoria.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// cmbCategoriaActionPerformed(evt);
		// }
		// });
		// --------------------- CONSULTA DINAMICA ------------------------ //
		jPanel8.add(cmbCategoria);

		jPanel9.setLayout(new java.awt.GridLayout(2, 1));

		lblFamilia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblFamilia.setText("Familia");
		lblFamilia.setMaximumSize(new java.awt.Dimension(25, 10));
		lblFamilia.setMinimumSize(new java.awt.Dimension(25, 10));
		lblFamilia.setPreferredSize(new java.awt.Dimension(25, 10));
		jPanel9.add(lblFamilia);

		cmbFamilia.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] {}));
		cmbFamilia.setMaximumSize(new java.awt.Dimension(56, 20));
		cmbFamilia.setMinimumSize(new java.awt.Dimension(56, 20));

		txtFamiliaEditor = (JTextField) cmbFamilia.getEditor().getEditorComponent();
		txtFamiliaEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFamiliaEditorKeyReleased(evt);
			}
		});
		txtFamiliaEditor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (activarFamilia) {
					strFamilia = "";
					txtFamiliaEditor.setText("");
					tres.clear();
					cmbFamilia.removeAllItems();
					cmbFamilia.setModel(new DefaultComboBoxModel(
							new String[] {}));
					cmbFamilia.setRenderer(new MyComboBoxRenderer(tres));
				}
				activarFamilia = true;
			}
		});
		// cmbFamilia.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// cmbFamiliaActionPerformed(evt);
		// }
		// });
		// ---------------------- CONSULTA DINAMICA ------------------------ //

		jPanel9.add(cmbFamilia);

		jPanel10.setLayout(new java.awt.GridLayout(2, 1));

		lblLinea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblLinea.setText("Línea");
		lblLinea.setMaximumSize(new java.awt.Dimension(25, 10));
		lblLinea.setMinimumSize(new java.awt.Dimension(25, 10));
		lblLinea.setPreferredSize(new java.awt.Dimension(25, 10));
		jPanel10.add(lblLinea);

		cmbLinea.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
		cmbLinea.setMaximumSize(new java.awt.Dimension(56, 20));
		cmbLinea.setMinimumSize(new java.awt.Dimension(56, 20));
		// ----------------------- CONSULTA DINAMICA ---------------------------- //
		txtLineaEditor = (JTextField) cmbLinea.getEditor().getEditorComponent();
		txtLineaEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtLineaEditorKeyReleased(evt);
			}
		});
		txtLineaEditor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (activarLinea) {
					strLinea = "";
					txtLineaEditor.setText("");
					cuatro.clear();
					cmbLinea.removeAllItems();
					cmbLinea.setModel(new DefaultComboBoxModel(new String[] {}));
					cmbLinea.setRenderer(new MyComboBoxRenderer(cuatro));
				}
				activarLinea = true;
			}
		});

		// cmbLinea.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// cmbLineaActionPerformed(evt);
		// }
		// });
		// ----------------------- CONSULTA DINAMICA
		// ---------------------------- //

		jPanel10.add(cmbLinea);

		jPanel11.setLayout(new java.awt.GridLayout(2, 1));

		lblGrupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblGrupo.setText("Grupo");
		lblGrupo.setMaximumSize(new java.awt.Dimension(25, 10));
		lblGrupo.setMinimumSize(new java.awt.Dimension(25, 10));
		lblGrupo.setPreferredSize(new java.awt.Dimension(25, 10));

		jPanel11.add(lblGrupo);

		cmbGrupo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
		cmbGrupo.setMaximumSize(new java.awt.Dimension(56, 20));
		cmbGrupo.setMinimumSize(new java.awt.Dimension(56, 20));
		// ------------------------------- CONSULTA DINAMICA
		// ------------------------ //
		txtGrupoEditor = (JTextField) cmbGrupo.getEditor().getEditorComponent();
		txtGrupoEditor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtGrupoEditorKeyReleased(evt);
			}
		});
		txtGrupoEditor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (activarGrupo) {
					strGrupo = "";
					txtGrupoEditor.setText("");
					cinco.clear();
					cmbGrupo.removeAllItems();
					cmbGrupo.setModel(new DefaultComboBoxModel(new String[] {}));
					cmbGrupo.setRenderer(new MyComboBoxRenderer(cinco));
				}
				activarGrupo = true;
			}
		});

		// ----------------------- CONSULTA DINAMICA ---------------------------- //
		jPanel11.add(cmbGrupo);

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout .createSequentialGroup()
						.addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(4, 4, 4)
//						.addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE).addGap(4, 4, 4)
						.addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE).addGap(4, 4, 4)
						.addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE).addGap(4, 4, 4)
						.addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE).addGap(4, 4, 4)
						.addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)));

		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,jPanel5Layout.createSequentialGroup().addContainerGap()
				.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(jPanel2,javax.swing.GroupLayout.Alignment.LEADING,javax.swing.GroupLayout.DEFAULT_SIZE,705,Short.MAX_VALUE)
				.addComponent(pnlBotones,javax.swing.GroupLayout.Alignment.LEADING,javax.swing.GroupLayout.DEFAULT_SIZE,705,Short.MAX_VALUE)
				.addComponent(pnlMateriales,javax.swing.GroupLayout.Alignment.LEADING,javax.swing.GroupLayout.DEFAULT_SIZE,705,Short.MAX_VALUE)
				.addGroup(jPanel5Layout.createSequentialGroup().addComponent(pnlTipo,javax.swing.GroupLayout.DEFAULT_SIZE,620,Short.MAX_VALUE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnCerrar,javax.swing.GroupLayout.PREFERRED_SIZE,79,javax.swing.GroupLayout.PREFERRED_SIZE))
				.addComponent(jPanel6,javax.swing.GroupLayout.Alignment.LEADING,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE))
										.addContainerGap()));

		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				.addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
				.addComponent(pnlTipo,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel6,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel2,javax.swing.GroupLayout.PREFERRED_SIZE,54,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												pnlBotones,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												23,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												pnlMateriales,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												173, Short.MAX_VALUE)
										.addContainerGap()));

		getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);
		getContentPane().add(lblCantidadRegistros,
				java.awt.BorderLayout.PAGE_END);

		pack();
	}// </editor-fold>

	protected void txtDescripcionCortaKeyReleased(KeyEvent evt) {
		char c = evt.getKeyChar();
		if (caracterEspecial(c)) {
			if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
				strMaterial = strMaterial + c;
				txtDescripcionCorta.setText(strMaterial);
				buscarMaterial(strMaterial);
			}
			if (c == '\b' && !strMaterial.equals("")) {
				strMaterial = strMaterial.substring(0, strMaterial.length() - 1);
				txtDescripcionCorta.setText(strMaterial);
				buscarMaterial(strMaterial);
			}
			if (c == '\n' && !strMaterial.equals("")) {
				String strMaterialSeleccionado =  cmbDescripcionLarga.getSelectedItem().toString();
				txtDescripcionCorta.setText(strMaterialSeleccionado);
				buscar2();
				activarBusqueda = false;
			}
			txtDescripcionCorta.select(strMaterial.length(), strMaterial.length());
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
		// TODO Auto-generated method stub
		buscadorMaterialXTipo(strTextoAbuscar, indexMaterial);
//		switch (tipo) {
//		case 0:// --- TODOS LOS MATERIALES --- //
//			buscadorMaterialXTipo(strTextoAbuscar, indexMaterial);
//			break;
//		case 1:// --- TODOS LOS MATERIALES TOP CLIENTE --- //
//			buscadorMaterialXTipo(strTextoAbuscar, indexTopCliente);
//			break;
//		case 2:// --- TODOS LOS MATERIALES TIPOLOGIA --- //
//			buscadorMaterialXTipo(strTextoAbuscar, indexTopTopologia);
//			break;
//		case 3:// --- TODOS LOS MATERIALES PROMO OFERTA --- //
//			buscadorMaterialXTipo(strTextoAbuscar, indexPromoOferta);
//			break;
//		case 4:// --- TODOS LOS MATERIALES DESCUENTO POLITICA --- //
//			buscadorMaterialXTipo(strTextoAbuscar, indexDescPolitica);
//			break;
//		case 5:// --- TODOS LOS MATERIALES DESCUENTO MANUALES --- //
//			buscadorMaterialXTipo(strTextoAbuscar, indexDescManuales);
//			break;
//		default:
//			break;
//		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buscadorMaterialXTipo(String strTextoAbuscar, Indexador index) {
		cmbDescripcionLarga.removeAllItems();
		listaMaterialesABuscar.clear();
		cmbDescripcionLarga.hidePopup();
		Buscador buscarMaterial;
		BeanMaterial material;
		if (!strTextoAbuscar.equals("") && strTextoAbuscar.length() > 0) {
			buscarMaterial = new Buscador(index);

			String strCodigo = null;
			BeanJerarquia bean = (BeanJerarquia) cmbGrupo.getSelectedItem();
			if (bean == null) {
				bean = (BeanJerarquia) cmbLinea.getSelectedItem();
				if (bean == null) {
					bean = (BeanJerarquia) cmbFamilia.getSelectedItem();
					if (bean == null) {
						bean = (BeanJerarquia) cmbCategoria.getSelectedItem();
						if (bean == null) {
							bean = (BeanJerarquia) cmbDivision.getSelectedItem();
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

			String[] cadena = strTextoAbuscar.split(" ");
			String strtmp = "";
			for (int i = 0; i < cadena.length; i++) {
				if(!cadena[i].equals(""))
					strtmp += cadena[i] + "* ";
			}
			try {
				ScoreDoc[] scoreResultado = buscarMaterial.funcionBuscador(strtmp.trim());
				if (scoreResultado != null && scoreResultado.length > 0) {
					for (int i = 0; i < scoreResultado.length; i++) {
						material = new BeanMaterial();
						int iDoc = scoreResultado[i].doc;
						Document d = buscarMaterial.getIbuscador().doc(iDoc);
						if (d.get("PRODH").startsWith(strCodigo) && validarPalabra(d.get("Busqueda"), cadena)) {
							material.setIdMaterial(d.get("Codigo"));
							material.setText_line(d.get("Descrip Larga"));
							material.setDescripcion(d.get("Descrip Corta"));
							material.setNormt(d.get("NORMT"));
							material.setPrice_1(d.get("Precio"));
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
							
							material.setTipoMaterial(d.get("tipoMaterial"));
							material.setStrBusqueda(d.get("Busqueda"));
							listaMaterialesABuscar.add(material);
						}

					}
					comodinMaterila.setText_line(strTextoAbuscar);
					comodinMaterila.setDescripcion(strTextoAbuscar);
					comodinMaterila.setStrBusqueda(strTextoAbuscar);
					comodinMaterila.setIdMaterial(strTextoAbuscar);
					comodinMaterila.setNormt(strTextoAbuscar);
					listaMaterialesABuscar.add(0, comodinMaterila);
					index.closeIndexador();
				} else {
					comodinMaterila.setText_line(strTextoAbuscar);
					comodinMaterila.setDescripcion(strTextoAbuscar);
					comodinMaterila.setIdMaterial(strTextoAbuscar);
					comodinMaterila.setStrBusqueda(strTextoAbuscar);
					comodinMaterila.setNormt(strTextoAbuscar);
					listaMaterialesABuscar.add(0, comodinMaterila);
				}
				cmbDescripcionLarga.setModel(new DefaultComboBoxModel(listaMaterialesABuscar.toArray()));
				cmbDescripcionLarga.setRenderer(new MyComboBoxRenderer2(listaMaterialesABuscar));
				// cmbDescripcionLarga.setSelectedIndex(0);
				cmbDescripcionLarga.showPopup();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static boolean validarPalabra(String string, String[] cadena) {
		// TODO Auto-generated method stub
		if(cadena.length > 0){
			for (int i = 0; i < cadena.length; i++) {
				if(string.indexOf (cadena[i].toUpperCase().trim()) == -1){
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
	
	protected void enmascararEventoTeclaTab() {
		tblMateriales.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblMateriales.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		((InputMap) UIManager.get("Table.ancestorInputMap")).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");

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
		try {
			SqlMaterialImpl sqlMaterial = new SqlMaterialImpl();
			int fila = tblMateriales.getSelectedRow();
			for (int idx = 0; idx < tblMateriales.getRowCount(); idx++) {
				String cantidad = tblMateriales.getValueAt(idx, 4).toString();
				if (idx == fila) {
					cantidad = this.textFieldCantidad.getText();
					this.textFieldCantidad.setText("0");
				}
				if (cantidad.compareTo("0") != 0 || tblMateriales.isRowSelected(idx)) {
					String material = tblMateriales.getValueAt(idx, 0).toString();
					String un = tblMateriales.getValueAt(idx, 2).toString();
					String precioNeto = tblMateriales.getValueAt(idx, 5).toString();
					String valorNeto = tblMateriales.getValueAt(idx, 7).toString();
					String tipoMaterial = tblMateriales.getValueAt(idx, 9).toString();
					String denominacion = sqlMaterial.obtenerNombreMaterial(material);
					tblMateriales.setValueAt("0", idx, 4);
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
					item.setUnidad(un);
					item.setPorcentajeDescuento("0.0");
					item.setDenominacion(denominacion);
					item.setPrecioNeto(price);
					item.setValorNeto(price2);
					item.setTipo(0); // Item normal
					item.setTipoMaterial(tipoMaterial);
					item.setEditable(true);
					item.setSimulado(false);
					int respuesta = contenedor.agregarMaterial(item, -2);
					if (respuesta > 0) {
						lblConfirmacion.setText("El pedido se ha agregado correctamente");
						lblConfirmacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check.png"))); // NOI18N
					} else {
						lblConfirmacion.setText("<html><font color='red'>No se pudo agregar el material " + item.getCodigo() + "</font></html>");
						lblConfirmacion.setIcon(null); // NOI18N
					}
				}
			}
			contenedor.actualizarPosiciones();
		} catch (IndexOutOfBoundsException e) {
			Util.mostrarExcepcion(e);
		}
	}

	private void llenarTabla() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ModeloConsultaDinamica modeloTablaItems = new ModeloConsultaDinamica();
				tblMateriales.setModel(modeloTablaItems);
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
					it.setTipoMaterial("" + beanMaterial.getTipoMaterial());
					it.setDenominacionCorta("" + beanMaterial.getDescripcion());
					it.setStrValorAcumulado("" + beanMaterial.getDblAcumulado());
					it.setStrValorPromedio("" + beanMaterial.getDblPromedio());
					it.setCantidadConfirmada("0.0");
					it.setPorcentajeDescuento("0.00");
					modeloTablaItems.agregarItem(it);
				}
				lblCantidadRegistros.setText("Número de registros: " + tblMateriales.getModel().getRowCount());
				int tipo = cmbTipoConsulta.getSelectedIndex();
				if (tipo == 1 || tipo == 2) {
					setAnchoColumnasMostrarAdicionales();
				} else {
					setAnchoColumnas();
				}
				tblMateriales.updateUI();
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaItems);
				tblMateriales.setRowSorter(sorter);
				escucharEventoTeclado();
			}
		});
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private void cmbDivisionActionPerformed(java.awt.event.ActionEvent evt) {
		List<BeanJerarquia> dostemp = new ArrayList<BeanJerarquia>();
		List<BeanJerarquia> trestemp = new ArrayList<BeanJerarquia>();
		List<BeanJerarquia> cuatrotemp = new ArrayList<BeanJerarquia>();
		List<BeanJerarquia> cincotemp = new ArrayList<BeanJerarquia>();
		BeanJerarquia divison_seleccionado = (BeanJerarquia) cmbDivision.getSelectedItem();
		String prodh = divison_seleccionado.getStrPRODH() == null ? "" : divison_seleccionado.getStrPRODH();
		if (!prodh.isEmpty()) {
			for (BeanJerarquia beanJerarquia : dos) {
				prodh = beanJerarquia.getStrPRODH() == null ? "" : beanJerarquia.getStrPRODH();
				if (prodh.startsWith(divison_seleccionado.getStrPRODH())) {
					dostemp.add(beanJerarquia);
				}
			}
			for (BeanJerarquia beanJerarquia : tres) {
				prodh = beanJerarquia.getStrPRODH() == null ? "" : beanJerarquia.getStrPRODH();
				if (prodh.startsWith(divison_seleccionado.getStrPRODH())) {
					trestemp.add(beanJerarquia);
				}
			}
			for (BeanJerarquia beanJerarquia : cuatro) {
				prodh = beanJerarquia.getStrPRODH() == null ? "" : beanJerarquia.getStrPRODH();
				if (prodh.startsWith(divison_seleccionado.getStrPRODH())) {
					cuatrotemp.add(beanJerarquia);
				}
			}
			for (BeanJerarquia beanJerarquia : cinco) {
				prodh = beanJerarquia.getStrPRODH() == null ? "" : beanJerarquia.getStrPRODH();
				if (prodh.startsWith(divison_seleccionado.getStrPRODH())) {
					cincotemp.add(beanJerarquia);
				}
			}
			dostemp.add(0, comodin);
			trestemp.add(0, comodin);
			cuatrotemp.add(0, comodin);
			cincotemp.add(0, comodin);
		} else {
			dostemp = dos;
			trestemp = tres;
			cuatrotemp = cuatro;
			cincotemp = cinco;

		}

		cmbCategoria.setModel(new DefaultComboBoxModel(dostemp.toArray()));
		cmbFamilia.setModel(new DefaultComboBoxModel(trestemp.toArray()));
		cmbLinea.setModel(new DefaultComboBoxModel(cuatrotemp.toArray()));
		cmbGrupo.setModel(new DefaultComboBoxModel(cincotemp.toArray()));

		cmbCategoria.setRenderer(new MyComboBoxRenderer(dostemp));
		cmbFamilia.setRenderer(new MyComboBoxRenderer(trestemp));
		cmbLinea.setRenderer(new MyComboBoxRenderer(cuatrotemp));
		cmbGrupo.setRenderer(new MyComboBoxRenderer(cincotemp));
	}

	@SuppressWarnings("unused")
	private void cmbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {

		// List<BeanJerarquia> trestemp = new ArrayList<BeanJerarquia>();
		// List<BeanJerarquia> cuatrotemp = new ArrayList<BeanJerarquia>();
		// List<BeanJerarquia> cincotemp = new ArrayList<BeanJerarquia>();
		// BeanJerarquia categoria_seleccionado = (BeanJerarquia)
		// cmbCategoria.getSelectedItem();
		// String prodh = categoria_seleccionado.getStrPRODH() == null ? "" :
		// categoria_seleccionado.getStrPRODH();
		// if (!prodh.isEmpty()) {
		// for (BeanJerarquia beanJerarquia : tres) {
		// prodh = beanJerarquia.getStrPRODH() == null ? "" :
		// beanJerarquia.getStrPRODH();
		// if (prodh.startsWith(categoria_seleccionado.getStrPRODH())) {
		// trestemp.add(beanJerarquia);
		// }
		// }
		// for (BeanJerarquia beanJerarquia : cuatro) {
		// prodh = beanJerarquia.getStrPRODH() == null ? "" :
		// beanJerarquia.getStrPRODH();
		// if (prodh.startsWith(categoria_seleccionado.getStrPRODH())) {
		// cuatrotemp.add(beanJerarquia);
		// }
		// }
		// for (BeanJerarquia beanJerarquia : cinco) {
		// prodh = beanJerarquia.getStrPRODH() == null ? "" :
		// beanJerarquia.getStrPRODH();
		// if (prodh.startsWith(categoria_seleccionado.getStrPRODH())) {
		// cincotemp.add(beanJerarquia);
		// }
		// }
		// trestemp.add(0, comodin);
		// cuatrotemp.add(0, comodin);
		// cincotemp.add(0, comodin);
		// } else {
		// trestemp = tres;
		// cuatrotemp = cuatro;
		// cincotemp = cinco;
		// }
		//
		// cmbFamilia.setModel(new DefaultComboBoxModel(trestemp.toArray()));
		// cmbLinea.setModel(new DefaultComboBoxModel(cuatrotemp.toArray()));
		// cmbGrupo.setModel(new DefaultComboBoxModel(cincotemp.toArray()));
		//
		// cmbFamilia.setRenderer(new MyComboBoxRenderer(trestemp));
		// cmbLinea.setRenderer(new MyComboBoxRenderer(cuatrotemp));
		// cmbGrupo.setRenderer(new MyComboBoxRenderer(cincotemp));
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private void cmbFamiliaActionPerformed(java.awt.event.ActionEvent evt) {
		List<BeanJerarquia> cuatrotemp = new ArrayList<BeanJerarquia>();
		List<BeanJerarquia> cincotemp = new ArrayList<BeanJerarquia>();
		BeanJerarquia familia_seleccionada = (BeanJerarquia) cmbFamilia
				.getSelectedItem();
		String prodh = familia_seleccionada.getStrPRODH() == null ? ""
				: familia_seleccionada.getStrPRODH();
		if (!prodh.isEmpty()) {
			for (BeanJerarquia beanJerarquia : cuatro) {
				prodh = beanJerarquia.getStrPRODH() == null ? ""
						: beanJerarquia.getStrPRODH();
				if (prodh.startsWith(familia_seleccionada.getStrPRODH())) {
					cuatrotemp.add(beanJerarquia);
				}
			}
			for (BeanJerarquia beanJerarquia : cinco) {
				prodh = beanJerarquia.getStrPRODH() == null ? ""
						: beanJerarquia.getStrPRODH();
				if (prodh.startsWith(familia_seleccionada.getStrPRODH())) {
					cincotemp.add(beanJerarquia);
				}
			}
			cuatrotemp.add(0, comodin);
			cincotemp.add(0, comodin);
		} else {
			cuatrotemp = cuatro;
			cincotemp = cinco;

		}
		cmbLinea.setModel(new DefaultComboBoxModel(cuatrotemp.toArray()));
		cmbGrupo.setModel(new DefaultComboBoxModel(cincotemp.toArray()));

		cmbLinea.setRenderer(new MyComboBoxRenderer(cuatrotemp));
		cmbGrupo.setRenderer(new MyComboBoxRenderer(cincotemp));
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private void cmbLineaActionPerformed(java.awt.event.ActionEvent evt) {
		List<BeanJerarquia> cincotemp = new ArrayList<BeanJerarquia>();
		BeanJerarquia linea_seleccionada = (BeanJerarquia) cmbLinea
				.getSelectedItem();
		String prodh = linea_seleccionada.getStrPRODH() == null ? ""
				: linea_seleccionada.getStrPRODH();
		if (!prodh.isEmpty()) {
			for (BeanJerarquia beanJerarquia : cinco) {
				prodh = beanJerarquia.getStrPRODH() == null ? ""
						: beanJerarquia.getStrPRODH();
				if (prodh.startsWith(linea_seleccionada.getStrPRODH())) {
					cincotemp.add(beanJerarquia);
				}
			}
			cincotemp.add(0, comodin);
		} else {
			cincotemp = cinco;

		}
		cmbGrupo.setModel(new DefaultComboBoxModel(cincotemp.toArray()));
		cmbGrupo.setRenderer(new MyComboBoxRenderer(cincotemp));
	}

	@SuppressWarnings("rawtypes")
	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		ComboBoxModel materialesModelo = cmbDescripcionLarga.getModel();
		if(materialesModelo.getSize() > 0){
			buscar2();
		}else {
			buscar();
		}
		
	}

	private void setAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblMateriales.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblMateriales.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 60;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 350;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 2:
				anchoColumna = 50;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 80;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 60;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 90;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 90;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 90;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 90;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 60;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 10:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 80;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 12:
				anchoColumna = 0;
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
			}
		}
	}

	private void setAnchoColumnasMostrarAdicionales() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblMateriales.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblMateriales.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 60;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 350;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 2:
				anchoColumna = 40;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 80;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 50;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 70;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 70;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 70;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 70;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 60;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 10:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 70;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 12:
				anchoColumna = 70;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 13:
				anchoColumna = 70;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}

	private void buscar() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					Runtime.getRuntime().gc();
					lblConfirmacion.setText("");
					lblConfirmacion.setIcon(null); // NOI18N
					String strCodigo;
					BeanJerarquia bean = (BeanJerarquia) cmbGrupo.getSelectedItem();
					if (bean == null) {
						bean = (BeanJerarquia) cmbLinea.getSelectedItem();
						if (bean == null) {
							bean = (BeanJerarquia) cmbFamilia.getSelectedItem();
							if (bean == null) {
								bean = (BeanJerarquia) cmbCategoria.getSelectedItem();
								if (bean == null) {
									bean = (BeanJerarquia) cmbDivision.getSelectedItem();
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
					// ------------------------ CONSULTA DINAMICA
					// ----------------------- //

					// ---------------------- MARCELO MOYANO NUEVO
					// ---------------------- //
					// ------------------------ CONSULTA DINAMICA
					// ----------------------- //
					// ----------------------- VALIDACION ORIGINAL
					// ---------------------- //
					// BeanJerarquia bean = (BeanJerarquia)
					// cmbGrupo.getSelectedItem();
					// String codigo = bean.getStrPRODH();
					// if (codigo == null) {
					// bean = (BeanJerarquia) cmbLinea.getSelectedItem();
					// codigo = bean.getStrPRODH();
					// if (codigo == null) {
					// bean = (BeanJerarquia) cmbFamilia.getSelectedItem();
					// codigo = bean.getStrPRODH();
					// if (codigo == null) {
					// bean = (BeanJerarquia) cmbCategoria.getSelectedItem();
					// codigo = bean.getStrPRODH();
					// if (codigo == null) {
					// bean = (BeanJerarquia) cmbDivision.getSelectedItem();
					// codigo = bean.getStrPRODH();
					// if (codigo == null) {
					// codigo = "";
					// }
					// }
					// }
					// }
					// }
					// ---------------- ORIGINAL
					// -------------------------------------------- //
					List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
					SqlDivisionImpl dao = new SqlDivisionImpl();
					int tipo = cmbTipoConsulta.getSelectedIndex();
					int type = cmbTipo.getSelectedIndex();

					BeanMaterial beanMaterial;

					// String validadorSeleccionCombo =
					// cmbDescripcionLarga.getSelectedItem().toString();
					// if(!validadorSeleccionCombo.equals("*")){
					// beanMaterial = (BeanMaterial)
					// listaMaterialesABuscar.get(index);
					// } else {
					// // beanMaterial = new BeanMaterial();
					// beanMaterial = (BeanMaterial)
					// listaMaterialesABuscar.get(0);
					// }

					int index = cmbDescripcionLarga.getSelectedIndex();
					if (index > -1) {
						beanMaterial = listaMaterialesABuscar.get(index);
					} else if (listaMaterialesABuscar.size() > 0) {
						beanMaterial = listaMaterialesABuscar.get(0);
					} else {
						beanMaterial = new BeanMaterial();
					}

					int c = 0;
					switch (tipo) {
					case 0:
						// -------------- CONSULTA DINAMICA
						// ---------------------
						// Object[] objetos = dao.buscarCantidadMateriales(
						// cmbTipoConsulta.getSelectedIndex(), strCodigo,
						// txtDescripcionLarga.getText(),
						// txtDescripcionCorta.getText(),
						// txtMaterial.getText(),
						// cliente.getStrCodigoTipologia(), type,
						// txtMarca.getText());

						Object[] objetos = dao.buscarCantidadMateriales( cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(), beanMaterial.getText_line(), beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type, beanMaterial.getNormt());

						consulta = objetos[0].toString();
						cantidadRegistros = (Long) objetos[1];
						calcularPaginacion();
						materiales = dao.buscarMateriales(consulta, 1);
						break;
					case 1:
						// ----- CONSULTA DINAMICA ---------------------- //
						// materiales = dao.buscarMaterialesTopCliente(
						// cmbTipoConsulta.getSelectedIndex(), strCodigo,
						// txtDescripcionLarga.getText(),
						// txtDescripcionCorta.getText(),
						// txtMaterial.getText(),
						// cliente.getStrCodigoTipologia(), type,
						// txtMarca.getText(), cliente.getStrIdCliente());
						// --------- CONSULTSA DINAMICA
						// --------------------------- //
						//
						materiales = dao.buscarMaterialesTopCliente(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt(),
								cliente.getStrIdCliente());

						c = Integer.parseInt(String.valueOf(jSpinner1.getValue()));
						if (materiales.size() >= c) {
							materiales = materiales.subList(0, c);
						}
						break;
					case 2:
						// ------------------ CONSULTA DINAMICA
						// --------------------- //
						// materiales = dao.buscarMaterialesTopTipologia(
						// cmbTipoConsulta.getSelectedIndex(), strCodigo,
						// txtDescripcionLarga.getText(),
						// txtDescripcionCorta.getText(),
						// txtMaterial.getText(),
						// cliente.getStrCodigoTipologia(), type,
						// txtMarca.getText(), cliente.getStrIdCliente());
						// -------------------- CONSULTA DINAMICA
						// ---------------------- //
						materiales = dao.buscarMaterialesTopTipologia(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt(),
								cliente.getStrIdCliente());

						c = Integer.parseInt(String.valueOf(jSpinner1.getValue()));
						if (materiales.size() >= c) {
							materiales = materiales.subList(0, c);
						}
						break;
					case 3:
						// -------------- CONSULTA DINAMICA
						// ------------------------- //
						// materiales = dao.buscarMaterialesPromoOferta(
						// cmbTipoConsulta.getSelectedIndex(), strCodigo,
						// txtDescripcionLarga.getText(),
						// txtDescripcionCorta.getText(),
						// txtMaterial.getText(),
						// cliente.getStrCodigoTipologia(), type,
						// txtMarca.getText());
						// ----------------- CONSULTA DINAMICA
						// --------------------- //

						materiales = dao.buscarMaterialesPromoOferta(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt());

						break;
					case 4:
						// ------------------ CONSULTA DINAMICA
						// -------------------- //
						// materiales = dao.buscarMaterialesDescuentoPolitica(
						// cmbTipoConsulta.getSelectedIndex(), strCodigo,
						// txtDescripcionLarga.getText(),
						// txtDescripcionCorta.getText(),
						// txtMaterial.getText(),
						// cliente.getStrCodigoTipologia(), type,
						// txtMarca.getText());
						// ------------------- CONSULTA DINAMICA
						// ------------------- //

						materiales = dao.buscarMaterialesDescuentoPolitica(
								cmbTipoConsulta.getSelectedIndex(), strCodigo,
								beanMaterial.getDescripcion(),
								beanMaterial.getText_line(),
								beanMaterial.getIdMaterial(),
								cliente.getStrCodigoTipologia(), type,
								beanMaterial.getNormt());
						break;
					case 5:
						// -------------- CONSULTA DINAMICA
						// ------------------------ //
						// materiales = dao.buscarMaterialesDescuentoManual(
						// cmbTipoConsulta.getSelectedIndex(), strCodigo,
						// txtDescripcionLarga.getText(),
						// txtDescripcionCorta.getText(),
						// txtMaterial.getText(),
						// cliente.getStrCodigoTipologia(), type,
						// txtMarca.getText());
						// ---------------------- CONSULTA DINAMICA
						// ------------------- //
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
					llenarTabla();
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}
	
	private void buscar2() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					
					List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
					@SuppressWarnings("rawtypes")
					ComboBoxModel materialesModelo ;

					BeanMaterial beanMaterial;

					materialesModelo = cmbDescripcionLarga.getModel();
					
					int index = cmbDescripcionLarga.getSelectedIndex();
					if(index > 0){
						beanMaterial = (BeanMaterial) cmbDescripcionLarga.getSelectedItem();
						materiales.add(beanMaterial);
					}else if(materialesModelo.getSize() > 0 ){
						for (int i = 1; i < materialesModelo.getSize(); i++) {
							beanMaterial = (BeanMaterial) materialesModelo.getElementAt(i);
							materiales.add(beanMaterial);
						}
					}
					
					materialese = materiales;
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					llenarTabla();
					bloqueador.dispose();
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

	private void calcularPaginacion(int pagina) {
		if (pagina > 0 && pagina <= cantidadPaginas) {
			cantidadPaginas = cantidadPaginas <= 1 ? 1 : cantidadPaginas;
			paginaActual = pagina;
			lblPaginas.setText(paginaActual + "/" + cantidadPaginas);
			SqlDivisionImpl dao = new SqlDivisionImpl();
			materialese = dao.buscarMateriales(consulta, pagina);
			llenarTabla();
		}
	}

	class MyComboBoxRendererCondiciones extends BasicComboBoxRenderer {
		private Object[] tooltips;

		public MyComboBoxRendererCondiciones(List<BeanCondicionPago> tooltips) {
			super();
			this.tooltips = tooltips.toArray();
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
				if (-1 < index) {
					list.setToolTipText(((BeanCondicionPago) tooltips[index])
							.getTxtVTEXT());
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

	class MyComboBoxRenderer extends BasicComboBoxRenderer {
		private Object[] tooltips;

		public MyComboBoxRenderer(List<BeanJerarquia> tooltips) {
			super();
			this.tooltips = tooltips.toArray();
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
				if (-1 < index) {
					list.setToolTipText(((BeanJerarquia) tooltips[index])
							.getStrVTEXT());
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

	class MyComboBoxRenderer2 extends BasicComboBoxRenderer {
		private Object[] tooltips;

		public MyComboBoxRenderer2(List<BeanMaterial> tooltips) {
			super();
			this.tooltips = tooltips.toArray();
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
				if (-1 < index) {
					list.setToolTipText(((BeanMaterial) tooltips[index])
							.getBusqueda());
				}
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setFont(list.getFont());
			BeanMaterial bm = (BeanMaterial) value;
			setText((bm == null) ? "" : bm.toString());
			return this;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void llenarCombos() {
		// cmbDivision.setModel(new DefaultComboBoxModel(uno.toArray()));
		// cmbCategoria.setModel(new DefaultComboBoxModel(dos.toArray()));
		// cmbFamilia.setModel(new DefaultComboBoxModel(tres.toArray()));
		// cmbLinea.setModel(new DefaultComboBoxModel(cuatro.toArray()));
		// cmbGrupo.setModel(new DefaultComboBoxModel(cinco.toArray()));
		// cmbCategoria.setRenderer(new MyComboBoxRenderer(dos));
		// cmbFamilia.setRenderer(new MyComboBoxRenderer(tres));
		// cmbLinea.setRenderer(new MyComboBoxRenderer(cuatro));
		// cmbGrupo.setRenderer(new MyComboBoxRenderer(cinco));
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

	// --------------- CONSULTA DINAMICA POR DIVISIÓN ---------------------------- //
	// -------------------- MARCELO MOYANO --------------------------------------- //
	public void txtDivisionEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			strDivision = strDivision + c;
			txtDivisionEditor.setText(strDivision);
			BuscarJerarquiaDivision(strDivision);
		}
		if (c == '\b' && !strDivision.equals("")) {
			strDivision = strDivision.substring(0, strDivision.length() - 1);
			txtDivisionEditor.setText(strDivision);
			BuscarJerarquiaDivision(strDivision);
		}
		if (c == '\n' && !strDivision.equals("")) {
			cmbDivision.getSelectedItem();
			txtDivisionEditor.setText(cmbDivision.getSelectedItem().toString());
			buscar();
			activarDivision = false;
		}
		txtDivisionEditor.select(strDivision.length(), strDivision.length());
	}

	// -------------------- CONSULTA DINAMICA POR DIVISIÓN  ---------------------- //
	// ------------------------- MARCELO MOYANO --------------------------------- //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void BuscarJerarquiaDivision(String strPalabraABuscar) {
		cmbDivision.removeAllItems();
		uno.clear();
		cmbDivision.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaDivision = new Buscador(indexDivision);

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
						// if ( d.get("Busqueda").indexOf
						// (strPalabraABuscar.toUpperCase().trim()) != -1){
						bjDos.setStrVTEXT(d.get("VTEXT"));
						bjDos.setStrPRODH(d.get("PRODH"));
						uno.add(bjDos);
						// }

					}
					comodinDivision.setStrVTEXT(strPalabraABuscar);
					uno.add(0, comodinDivision);
					indexDivision.closeIndexador();
				} else {
					comodinDivision.setStrVTEXT(strPalabraABuscar);
					uno.add(0, comodinDivision);
				}
				cmbDivision.setModel(new DefaultComboBoxModel(uno.toArray()));
				cmbDivision.setRenderer(new MyComboBoxRenderer(uno));
				// cmbDivision.setSelectedIndex(0);
				cmbDivision.showPopup();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// --------------------------------------------------------------------------
	// //

	// -------------------- CONSULTA DINAMICA POR CATEGORIA ---------------------- //
	// ------------------------- MARCELO MOYANO --------------------------------- //
	public void txtCategoriaEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			strCategoria = strCategoria + c;
			txtCategoriaEditor.setText(strCategoria);
			BuscarJerarquiaCategoria(strCategoria);
		}
		if (c == '\b' && !strCategoria.equals("")) {
			strCategoria = strCategoria.substring(0, strCategoria.length() - 1);
			txtCategoriaEditor.setText(strCategoria);
			BuscarJerarquiaCategoria(strCategoria);
		}
		if (c == '\n' && !strCategoria.equals("")) {
			txtCategoriaEditor.setText(cmbCategoria.getSelectedItem().toString());
			buscar();
			activarCategoria = false;
		}
//		txtCategoriaEditor.select(strCategoria.length(), strCategoria.length());
		txtCategoriaEditor.select(txtCategoriaEditor.getText().length(), txtCategoriaEditor.getText().length());
	}

	// -------------------- CONSULTA DINAMICA POR CATEGORIA ---------------------- //
	// ------------------------- MARCELO MOYANO --------------------------------- //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void BuscarJerarquiaCategoria(String strPalabraABuscar) {
		cmbCategoria.removeAllItems();
		dos.clear();
		cmbCategoria.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaCategoria = new Buscador(indexCategoria);
			// String strFiltro;
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
						if (d.get("PRODH").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRODH"));
							dos.add(bjDos);
						}

					}
					comodinCategoria.setStrVTEXT(strPalabraABuscar);
					dos.add(0, comodinCategoria);
					indexCategoria.closeIndexador();
				} else {
					comodinCategoria.setStrVTEXT(strPalabraABuscar);
					dos.add(0, comodinCategoria);
				}
				cmbCategoria.setModel(new DefaultComboBoxModel(dos.toArray()));
				cmbCategoria.setRenderer(new MyComboBoxRenderer(dos));
				// cmbCategoria.setSelectedIndex(0);
				cmbCategoria.showPopup();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ----------------------------------------------------------------- //

	// ----------------------- CONSULTA DINAMICA POR FAMILIA ----------------- //
	// ---------------------------- MARCELO MOYANO --------------------------- //
	private void txtFamiliaEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			strFamilia = strFamilia + c;
			txtFamiliaEditor.setText(strFamilia);
			BuscarJerarquiaFamilia(strFamilia);
		}
		if (c == '\b' && !strFamilia.equals("")) {
			strFamilia = strFamilia.substring(0, strFamilia.length() - 1);
			txtFamiliaEditor.setText(strFamilia);
			BuscarJerarquiaFamilia(strFamilia);
		}
		if (c == '\n' && !strFamilia.equals("")) {
			txtFamiliaEditor.setText(cmbFamilia.getSelectedItem().toString());
			buscar();
			activarFamilia = false;
		}
		txtFamiliaEditor.select(txtFamiliaEditor.getText().length(), txtFamiliaEditor.getText().length());
	}

	// -------------------- CONSULTA DINAMICA POR FAMILIA ----------------------- //
	// ------------------------- MARCELO MOYANO --------------------------------- //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void BuscarJerarquiaFamilia(String strPalabraABuscar) {
		cmbFamilia.removeAllItems();
		tres.clear();
		cmbFamilia.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaFamilia = new Buscador(indexFamilia);
			
			
			// String strFiltro;
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
						if (d.get("PRODH").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRODH"));
							tres.add(bjDos);
						}
					}
					comodinFamilia.setStrVTEXT(strPalabraABuscar);
					tres.add(0, comodinFamilia);
					indexFamilia.closeIndexador();
				} else {
					comodinFamilia.setStrVTEXT(strPalabraABuscar);
					tres.add(0, comodinFamilia);
				}
				cmbFamilia.setModel(new DefaultComboBoxModel(tres.toArray()));
				cmbFamilia.setRenderer(new MyComboBoxRenderer(tres));
				// cmbFamilia.setSelectedIndex(0);
				cmbFamilia.showPopup();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ----------------------------------------------------------------- //

	// ------------------ CONSULTA DINAMICA POR LINEA ------------------------- //
	// ---------------------- MARCELO MOYANO ----------------------------------- //
	private void txtLineaEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			strLinea = strLinea + c;
			txtLineaEditor.setText(strLinea);
			BuscarJerarquiaLinea(strLinea);
		}
		if (c == '\b' && !strLinea.equals("")) {
			strLinea = strLinea.substring(0, strLinea.length() - 1);
			txtLineaEditor.setText(strLinea);
			BuscarJerarquiaLinea(strLinea);
		}
		if (c == '\n' && !strLinea.equals("")) {
			cmbLinea.getSelectedItem();
			txtLineaEditor.setText(cmbLinea.getSelectedItem().toString());
			buscar();
			activarLinea = false;
		}
		txtLineaEditor.select(txtLineaEditor.getText().length(), txtLineaEditor.getText().length());
	}

	// ---------------------- CONSULTA DINAMICA -------------------------------- //

	// ------------------ CONSULTA DINAMICA POR LINEA ------------------------- //
	// ---------------------- MARCELO MOYANO ----------------------------------- //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void BuscarJerarquiaLinea(String strPalabraABuscar) {
		cmbLinea.removeAllItems();
		cuatro.clear();
		cmbLinea.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscaLinea = new Buscador(indexLinea);
			// String strFiltro ;
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
						if (d.get("PRODH").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRODH"));
							cuatro.add(bjDos);
						}
					}
					comodinLinea.setStrVTEXT(strPalabraABuscar);
					cuatro.add(0, comodinLinea);
					indexLinea.closeIndexador();
				} else {
					comodin.setStrVTEXT(strPalabraABuscar);
					cuatro.add(0, comodin);
				}
				cmbLinea.setModel(new DefaultComboBoxModel(cuatro.toArray()));
				cmbLinea.setRenderer(new MyComboBoxRenderer(cuatro));
				// cmbLinea.setSelectedIndex(0);
				cmbLinea.showPopup();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ------------------------ CONSULTA DINAMICA ------------------------------------ //

	// ------------------ CONSULTA DINAMICA POR GRUPO ------------------------- //
	// ---------------------- MARCELO MOYANO ----------------------------------- //
	private void txtGrupoEditorKeyReleased(java.awt.event.KeyEvent evt) {
		char c = evt.getKeyChar();
		if (Character.isDigit(c) || Character.isLetter(c) || c == ' ') {
			strGrupo = strGrupo + c;
			txtGrupoEditor.setText(strGrupo);
			BuscarJerarquiaGrupo(strGrupo);
		}
		if (c == '\b' && !strGrupo.equals("")) {
			strGrupo = strGrupo.substring(0, strGrupo.length() - 1);
			txtGrupoEditor.setText(strGrupo);
			BuscarJerarquiaGrupo(strGrupo);
		}
		if (c == '\n' && !strGrupo.equals("")) {
			cmbGrupo.getSelectedItem();
			txtGrupoEditor.setText(cmbGrupo.getSelectedItem().toString());
			buscar();
			activarGrupo = false;
		}
		txtGrupoEditor.select(txtGrupoEditor.getText().length(), txtGrupoEditor.getText().length());
	}

	// ---------------------- CONSULTA DINAMICA --------------------------------
	// //

	// ------------------ CONSULTA DINAMICA POR GRUPO -------------------------
	// //
	// ---------------------- MARCELO MOYANO -----------------------------------
	// //

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void BuscarJerarquiaGrupo(String strPalabraABuscar) {
		cmbGrupo.removeAllItems();
		cinco.clear();
		cmbGrupo.hidePopup();
		if (!strPalabraABuscar.equals("") && strPalabraABuscar.length() > 0) {
			BeanJerarquia bjDos;
			Buscador buscagGrupo = new Buscador(indexGrupo);
			// String strFiltro;
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
						if (d.get("PRODH").startsWith(strFiltro)) {
							bjDos.setStrVTEXT(d.get("VTEXT"));
							bjDos.setStrPRODH(d.get("PRODH"));
							cinco.add(bjDos);
						}

					}
					comodinGrupo.setStrVTEXT(strPalabraABuscar);
					cinco.add(0, comodinGrupo);
					indexGrupo.closeIndexador();
				} else {
					comodinGrupo.setStrVTEXT(strPalabraABuscar);
					cinco.add(0, comodinGrupo);
				}
				cmbGrupo.setModel(new DefaultComboBoxModel(cinco.toArray()));
				cmbGrupo.setRenderer(new MyComboBoxRenderer(cinco));
				// cmbGrupo.setSelectedIndex(0);
				cmbGrupo.showPopup();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ---------------------------- CONSULTA DINAMICA
	// --------------------------- //

	private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
		this.setVisible(false);
		this.dispose();
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

	// ---------Marcelo Moyano Calcula los precios en consulta de
	// Materiales-------------
	protected void simulaPreciosDeMaterialesSQLite2() {
		try {
			List<BeanPedidoDetalle> detalles = new ArrayList<BeanPedidoDetalle>();
			List<Item> listaItems = new ArrayList<Item>();
			for (int i = 0; i < tblMateriales.getRowCount(); i++) {
				String material = tblMateriales.getValueAt(i, 0).toString();
				String cantidad = "";
				try {
					cantidad = "" + Integer.parseInt(tblMateriales.getValueAt(i, 4).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					cantidad = "0";
				}
				if (cantidad.compareTo("0") != 0) {
					BeanPedidoDetalle detalle = new BeanPedidoDetalle();
					Item it = new Item();
					String posicion = Util.completarCeros(6, "" + ((i + 1) * 10));
					detalle.setPosicion(posicion);
					it.setPosicion(posicion);
					detalle.setMaterial(material);
					detalle.setPorcentajeDescuento("0.00");
					it.setCodigo(material);
					detalle.setCantidad(cantidad);
					it.setCantidad(cantidad);
					detalles.add(detalle);
					it.setIntUbicacionGrilla(i);
					listaItems.add(it);
				} else {
					tblMateriales.setValueAt("0", i, 5);
					tblMateriales.setValueAt("0", i, 6);
					tblMateriales.setValueAt("0", i, 7);
					tblMateriales.setValueAt("0", i, 10);
					tblMateriales.updateUI();
				}
			}
			List<Item> lista = new ArrayList<Item>();
			lista.addAll(listaItems);
			String codCliente = cliente.getStrIdCliente();
			;
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
				if (i.getCodigo() != null && !i.getCodigo().trim().equals("")&& i.getTipo() == 0) {
					BeanCondicionComercial2 b2 = new BeanCondicionComercial2();
					b2.setStrCliente(codCliente);
					b2.setStrGrupoCliente("" + Integer.parseInt(beanCliente.getStrCodigoTipologia()));
					b2.setStrCanal(beanCliente.getStrCodCanalDist());
					List<BeanCondicionComercial2> listaCondiciones2 = sqlMateriales.listarCondicion2(b2);
					Double dblDscto = new Double("0");
					BeanCondicionComercial2 beanCondicion2 = null;
					if (listaCondiciones2 != null && listaCondiciones2.size() == 1) {
						beanCondicion2 = listaCondiciones2.get(0);
						dblDscto = beanCondicion2.getDblImporte() / 10;
						dblPrecioFinal = dblPrecioFinal * (100 + dblDscto) / 100;
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
					BeanCondicionComercial3x beanCondicion3 = null;
					Double dblDscto = new Double("0");
					List<BeanCondicionComercial3x> listaCondiciones3x = sqlMateriales.listarCondicion3(b3);
					// se calcula el importe del pedido, considerando los % de
					// descuento que se aplicaron
					// en 1x y 2x de todos los items
					dblImportePedido = calculaImportePedidoClaseCondicion1x2x2(lista, i, listaMaterialesGrilla);
					if (listaCondiciones3x != null && listaCondiciones3x.size() == 1) {
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
					System.out.println("-->" + dblCantidadPedido);
					if (listaCondiciones4x != null && listaCondiciones4x.size() == 1) {
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
					System.out.println("-->" + dblCantidadPedido);
					if (listaCondiciones4x != null && listaCondiciones4x.size() == 1) {
						beanCondicion4 = listaCondiciones4x.get(0);
						if (beanCondicion4.getStrEscala().equals("X")) {
							dblDscto = beanCondicion4.getDblImporteEscala(dblCantidadPedido) / 10;
						} else {
							dblDscto = beanCondicion4.getDblImporte() / 10;
						}
						dblPrecioFinal = dblPrecioFinal * (100 + dblDscto)
								/ 100;
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
					BeanMaterial objMat = obtenerMaterialDeGrilla(listaMaterialesGrilla, i.getCodigo());
					b5.setStrDivisionCom(objMat.getPrdha().substring(0, 2));
					b5.setStrCatProd(objMat.getPrdha().substring(2, 5));
					b5.setStrFamilia(objMat.getPrdha().substring(5, 9));
					List<BeanCondicionComercial5x> listaCondiciones5x = sqlMateriales.listarCondicion5(b5);
					dblCantidadPedido = calculaImportePedido4x2(lista, i,listaMaterialesGrilla);
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
			// Double precioXClienteMatrial = 0d;
			// SqlSincronizacionImpl sqlSincronizacionPedido = new
			// SqlSincronizacionImpl();

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
						Double dblDsctoManual = Double.parseDouble(i.getPorcentajeDescuento());
						dblPrecioTemp = dblPrecioTemp * (100 - dblDsctoManual) / 100;
					} else {
						Double dblDsctoManual = Double.parseDouble("0.00");
						dblPrecioTemp = dblPrecioTemp * (100 - dblDsctoManual) / 100;
					}
					dblPrecioFinal = dblPrecioTemp;
					i.setValorNeto(dblPrecioFinal * Integer.parseInt(i.getCantidadConfirmada()));
					i.setPrecioNeto(dblPrecioFinal);
					i.setPrecioIVA(dblPrecioFinal + (dblPrecioFinal * 0.12));

					tblMateriales.setValueAt("" + i.getPrecioNeto(), i.getIntUbicacionGrilla(), 5);
					tblMateriales.setValueAt("" + i.getPrecioIVA(), i.getIntUbicacionGrilla(), 6);
					tblMateriales.setValueAt("" + i.getValorNeto(), i.getIntUbicacionGrilla(), 7);
					tblMateriales.setValueAt("" + i.getCantidadConfirmada(), i.getIntUbicacionGrilla(), 10);

					i.setSimulado(true);

					// Marcelo Moyano
					// Aparece Pila de Errores
					System.out.println("Mat:" + i.getCodigo() + "\n");
					System.out.println("Dscto 1:" + i.getStrCondCalc1() + " " + i.getDblDesc1());
					System.out.println("Dscto 2:" + i.getStrCondCalc2() + " " + i.getDblDesc2());
					System.out.println("Dscto 3:" + i.getStrCondCalc3() + " " + i.getDblDesc3());
					System.out.println("Dscto 4:" + i.getStrCondCalc4() + " " + i.getDblDesc4());
					System.out.println("Dscto 5:" + i.getStrCondCalc5() + " " + i.getDblDesc5());
					// }// ------------------- precio por cliente material
					// -----------------
				}
			}
			tblMateriales.updateUI();

		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		} finally {
		}
	}

	private BeanMaterial obtenerMaterialDeGrilla(List<BeanMaterial> lista,
			String strCodMaterial) {
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
				if (item.getCantidad() != null
						&& !item.getCantidad().equals("")) {
					dblCantidad = new Double(item.getCantidad());
				}
				if (item.getCodigo() != null && !item.getCodigo().equals("")
						&& item.getCantidad() != null && !item.getCantidad().equals("")
						&& item.getStrCondCalc3() != null && i.getStrCondCalc3() != null
						&& item.getStrCondCalc3().equals(i.getStrCondCalc3())
						&& item.getStrNroCond3() != null && i.getStrNroCond3() != null
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

	private Double calculaImportePedido4x2(List<Item> listaItem, Item i,
			List<BeanMaterial> listaMaterialesGrilla) {
		double dblRetorno = 0;
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = null;
				beanMaterial = obtenerMaterialDeGrilla(listaMaterialesGrilla,
						item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
					Double dblPrecioTemp = new Double(beanMaterial.getPrice_1());
					if (item.getDblDesc1() != null) {
						dblPrecioTemp = dblPrecioTemp
								* (100 + item.getDblDesc1()) / 100;
					}
					if (item.getDblDesc2() != null) {
						dblPrecioTemp = dblPrecioTemp
								* (100 + item.getDblDesc2()) / 100;
					}
					if (item.getDblDesc3() != null) {
						dblPrecioTemp = dblPrecioTemp
								* (100 + item.getDblDesc3()) / 100;
					}
					if (item.getDblDesc4() != null) {
						dblPrecioTemp = dblPrecioTemp
								* (100 + item.getDblDesc4()) / 100;
					}
					dblPrecio = dblPrecioTemp;
				}
				if (item.getCantidad() != null
						&& !item.getCantidad().equals("")) {
					dblCantidad = new Double(item.getCantidad());
				}
				try {
					dblRetorno += dblCantidad.doubleValue()
							* dblPrecio.doubleValue();
				} catch (Exception e) {
				}
			}
		}
		return new Double(dblRetorno);
	}

	private Double calculaCantidadPedidoClaseCondicion2(List<Item> listaItem,
			Item i) {
		double dblRetorno = 0;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				System.out.println("..." + item.getStrCondCalc4() + " - "
						+ i.getStrCondCalc4());
			}
			if (item.getCodigo() != null
					&& !item.getCodigo().equals("")
					&& item.getCantidad() != null
					&& !item.getCantidad().equals("")
					&& item.getStrCondCalc4() != null
					&& i.getStrCondCalc4() != null
					&& item.getStrCondCalc4().equals(i.getStrCondCalc4())
					&& item.getStrNroCond4() != null
					&& i.getStrNroCond4() != null
					&& item.getStrNroCond4().trim()
							.equals(i.getStrNroCond4().trim())) {
				try {
					dblRetorno += Double.parseDouble(item.getCantidad());
				} catch (Exception e) {
				}
			}
		}
		return new Double(dblRetorno);
	}

	// -------------- Marcelo Moyano --------------------------------

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
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JSpinner jSpinner1;
	private javax.swing.JLabel lblCantidadRegistros;
	private javax.swing.JLabel lblCategoria;
	private javax.swing.JLabel lblCliente;
	private javax.swing.JLabel lblCondPago;
	private javax.swing.JLabel lblConfirmacion;
	private javax.swing.JLabel lblDescripcionCorta;
	private javax.swing.JLabel lblDescripcionLarga;
	private javax.swing.JLabel lblDivision;
	private javax.swing.JLabel lblFamilia;
	private javax.swing.JLabel lblGrupo;
	private javax.swing.JLabel lblLinea;
	private javax.swing.JLabel lblMarca;
	private javax.swing.JLabel lblMaterial;
	private javax.swing.JLabel lblPaginas;
	private javax.swing.JLabel lblTipo;
	private javax.swing.JLabel lblTipoConsulta;
	private javax.swing.JPanel pnlBotones;
	private javax.swing.JPanel pnlBottom;
	private javax.swing.JPanel pnlLeft;
	private javax.swing.JPanel pnlLeft1;
	private javax.swing.JPanel pnlLeft2;
	private javax.swing.JPanel pnlLeft3;
	private javax.swing.JPanel pnlMateriales;
	private javax.swing.JPanel pnlPaginacion;
	private javax.swing.JPanel pnlTipo;
	private javax.swing.JScrollPane scrollMateriales;
	private javax.swing.JTable tblMateriales;
	private javax.swing.JTextField txtDescripcionCorta;// editor de
														// cmbDescripción larga
	private javax.swing.JTextField txtDescripcionLarga;
	private javax.swing.JTextField txtMarca;
	private javax.swing.JTextField txtMaterial;
	// End of variables declaration
}