package com.promesa.internalFrame.pedidos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.promesa.administracion.bean.BeanUsuario;
import com.promesa.administracion.sql.impl.SqlUsuarioImpl;
import com.promesa.bean.BeanConexion;
import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.CabeceraHojaMaestraCredito;
import com.promesa.cobranzas.bean.Presupuesto;
import com.promesa.cobranzas.sql.impl.SqlCabeceraHojaMaestraCreditoImpl;
import com.promesa.dialogo.pedidos.DConsultaMateriales2;
import com.promesa.dialogo.pedidos.DHistoricoCliente;
import com.promesa.factory.Factory;
import com.promesa.impresiones.ReporteTicket;
import com.promesa.impresiones.dpp350.ReportePedido;
import com.promesa.impresiones.pedidos.DetalleTicketOrden;
import com.promesa.impresiones.pedidos.TicketOrden;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.internalFrame.pedidos.custom.ModeloTablaItems;
import com.promesa.internalFrame.pedidos.custom.RenderizadorTablaItems;
import com.promesa.internalFrame.planificacion.IVentanaVisita;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.bean.BeanClaseMaterial;
import com.promesa.pedidos.bean.BeanCondicionComercial1;
import com.promesa.pedidos.bean.BeanCondicionComercial2;
import com.promesa.pedidos.bean.BeanCondicionComercial3x;
import com.promesa.pedidos.bean.BeanCondicionComercial4x;
import com.promesa.pedidos.bean.BeanCondicionComercial5x;
import com.promesa.pedidos.bean.BeanCondicionExpedicion;
import com.promesa.pedidos.bean.BeanCondicionPago;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.bean.BeanMensaje;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.bean.BeanPedidoPartners;
import com.promesa.pedidos.bean.BeanSede;
import com.promesa.pedidos.bean.BeanTipoGestion;
import com.promesa.pedidos.bean.BeanVentaCruzada;
import com.promesa.pedidos.bean.Indicador;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.bean.MarcaVendedor;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.SqlMaterial;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.pedidos.sql.impl.SqlComboImpl;
import com.promesa.pedidos.sql.impl.SqlCondicionExpedicionImpl;
import com.promesa.pedidos.sql.impl.SqlCondicionPagoImpl;
import com.promesa.pedidos.sql.impl.SqlJerarquiaImpl;
import com.promesa.pedidos.sql.impl.SqlMarcaEstrategicaImpl;
import com.promesa.pedidos.sql.impl.SqlMarcaVendedorImpl;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.pedidos.sql.impl.SqlPedidoImpl;
import com.promesa.pedidos.sql.impl.SqlSedeImpl;
import com.promesa.pedidos.sql.impl.SqlLlenarComboTipoGestion;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SPedidos;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;
import com.promesa.util.ValidadorEntradasDeTexto;

@SuppressWarnings("serial")
public abstract class IPedidos extends javax.swing.JInternalFrame implements FocusListener, InternalFrameListener {

	private boolean flag = false;
	private int tempfila, tempfila1;
	private String dPresupuesto = "";
	private String dAcumaloReal = "";
	private double resultadoAnual = 0;
	private String strbadate="";
	private List<String> arrMarca;
	private static IPedidos instance = null;

	public IPedidos(String strbadate,BeanPedido pedido, String codigoCliente, String nombreCliente, String claseRiesgo,
			String limiteCredito, String disponibilidad, String strCondicionPago, String titulo, String tituloReporte,
			String tipoDocumento, int tipoVentana) {
		this.strbadate =strbadate;
		this.codigoCliente = codigoCliente;
		this.instance = this;
		this.pedido = pedido;
		this.arrMarca=new ArrayList();
		initComponents();
		SqlClienteImpl sqlCliente = new SqlClienteImpl();
		BeanCliente cliente = sqlCliente.buscarCliente(codigoCliente);
		instancia = this;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addInternalFrameListener(this);

		// this.codigoCliente = codigoCliente;
		this.nombreCliente = nombreCliente;
		this.claseRiesgo = claseRiesgo;
		this.limiteCredito = limiteCredito;
		this.disponibilidad = disponibilidad;
		this.condicionPago = strCondicionPago;
		this.tipoDocumento = tipoDocumento;
		this.tipoVentana = tipoVentana;
		this.lblTitulo.setText(titulo);
		lblTitulo.setVisible(false);
		this.organizacionVentas = cliente.getStrCodOrgVentas();
		this.canalDistribucion = cliente.getStrCodCanalDist();
		this.codigoSector = cliente.getStrCodSector();
		this.txtCondPago.setText(strCondicionPago);
		this.txtDestinatario.setText(codigoCliente);
		this.txtNombre.setText(nombreCliente);
		this.txtClasRiesgo.setText(claseRiesgo);
		this.txtDisponible.setText(disponibilidad);

		txtLimCredito.setEditable(false);
		this.txtLimCredito.setText(limiteCredito);
		pnlTextsTres.add(txtLimCredito);

		txtDisponible.setEditable(false);
		pnlTextsTres.add(txtDisponible);
		llenarTablaCondicionesPago();
		llenarTablaDestinatarios();
		llenarCondicionesExpedicion();
		llenarComboTipoGestion();
		enmascararEventoTeclaTab();
		establecerOyenteTeclaTab();
		agregarEventoClicDerecho();
		strDispositivoImpresora = Util.verificarImpresora();

		Promesa.getInstance().setVentanaActiva(true);

		Promesa.getInstance().configSplitPane(this.configurarMarcaEstrategica());
		this.cargarMarcaEstrategica();
		//this.cargarIndicadores();
	}

	protected void generarMensaje(String[] mensaje) {
		if (mensaje != null && mensaje.length > 0) {
			String html = "<html><table>";
			URL url = this.getClass().getResource("/imagenes/error.png");
			for (int i = 0; i < mensaje.length; i++) {
				String string = mensaje[i];
				html += "<tr><td><img src=\"" + url + "\"></td><td>" + string + "</td></tr>";
			}
			html += "</table></html>";
			lblWarnigs.setText(html);
		}
	}

	protected void agregarEventoClicDerecho() {
		tblPedidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = tblPedidos.rowAtPoint(e.getPoint());
				if (r >= 0 && r < tblPedidos.getRowCount()) {
					tblPedidos.setRowSelectionInterval(r, r);
				} else {
					tblPedidos.clearSelection();
				}
				final int rowindex = tblPedidos.getSelectedRow();
				Item it = mdlTblItems.obtenerItem(rowindex);
				if (it == null)
					return;
				final boolean estaSimulado = it.isSimulado();
				final String codigo = it.getCodigo();
				if (rowindex < 0 || codigo.isEmpty())
					return;
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = new JPopupMenu();
					String texto = "";
					JMenuItem labels = new JCheckBoxMenuItem();
					labels.setOpaque(true);
					// Para limpiar la selección
					JMenuItem seleccion = new JCheckBoxMenuItem();
					seleccion.setOpaque(true);
					seleccion.setText("Limpiar selección");
					if (estaSimulado) {
						texto = "Marcar como no confirmado";
						labels.setBackground(new Color(251, 209, 202));
						seleccion.setBackground(new Color(251, 209, 202));
					} else {
						texto = "Marcar como confirmado";
						labels.setBackground(Color.white);
						seleccion.setBackground(Color.white);
					}
					labels.setText(texto);
					labels.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							mdlTblItems.actualizarEstadoSimulado(rowindex, !estaSimulado);
							tblPedidos.updateUI();
							tblPedidos.clearSelection();
						}
					});
					seleccion.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							tblPedidos.clearSelection();
						}
					});
					popup.add(labels);
					popup.add(seleccion);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initComponents() {
		pnlBackground = new JPanel();
		lblTitulo = new JLabel();
		pnlContenedor = new JPanel();
		pnlDatos = new JPanel();
		pnlInformacion = new JPanel();
		pnlInformacionGeneral = new JPanel();
		pnlUno = new JPanel();
		pnlLabelsUno = new JPanel();
		lblVtaMercaderia = new JLabel();
		lblDestinatario = new JLabel();
		lblBloqEntrega = new JLabel();
		pnlTextsUno = new JPanel();
		txtCodigoPedido = new JTextField();
		pnlVtaMercaderia = new JPanel();
		btnDetalleDestinatario = new JButton();
		txtDestinatario = new JTextField();
		pnlBloqEntrega = new JPanel();
		btnDetalleBloqEntrega = new JButton();
		txtBloqEntrega = new JTextField();
		pnlTres = new JPanel();
		pnlLabels3 = new JPanel();
		
		lblClasRiesgo = new JLabel();
		lblCondPago = new JLabel();
		pnlTextsTres = new JPanel();
		txtClasRiesgo = new JTextField();
		jPanel9 = new JPanel();
		panelClase = new JPanel();
		panelClaseRiesgo = new JPanel();
		btnDetalleCondPago = new JButton();
		txtLimCredito = new JTextField();
		txtLimCredito.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCondPago = new JTextField();
		pnlCuatro = new JPanel();
		pnlLabels4 = new JPanel();
		lblValorNeto = new JLabel();
		pnlTexts4 = new JPanel();
		txtValorNeto = new JTextField();
		txtValorNeto.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlDos = new JPanel();
		pnlLabelsDos = new JPanel();
		lblNPedCliente = new JLabel();
		lblNombre = new JLabel();
		pnlTextsDos = new JPanel();
		txtNPedCliente = new JTextField();
		txtNombre = new JTextField();
		pnlTabla = new JPanel();
		mnuToolBarMiddle = new JToolBar();
		btnAniadir = new JButton();
		separador2 = new JToolBar.Separator();
		btnEliminar = new JButton();
		separador3 = new JToolBar.Separator();
		btnConsultaCapturaDinamica = new JButton();
		separador4 = new JToolBar.Separator();
		btnGuiaVentas = new JButton();
		btnSimularTodo = new JButton();
		btnMaterialNuevo =new JButton();
		btnVentaCruzada = new JButton();
		btnVentaCruzadaCliente = new JButton();
		btnResultadoMarca = new JButton(); 
		separador5 = new JToolBar.Separator();
		separador6 = new JToolBar.Separator();
		separador7 = new JToolBar.Separator();
		separador8 = new JToolBar.Separator();
		separador9 = new JToolBar.Separator();
		lblMensajes = new JLabel();
		scroller = new JScrollPane();
		tblPedidos = new JTable(){
			private SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
        	private int cant=0;
        	ImageIcon redicon = new  ImageIcon(this.getClass().getResource("/imagenes/redcircle.png"));
        	ImageIcon yellowicon = new  ImageIcon(this.getClass().getResource("/imagenes/yellowcircle.png"));
        	ImageIcon blueicon = new  ImageIcon(this.getClass().getResource("/imagenes/bluecircle.png"));

			@Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				JLabel c = (JLabel)super.prepareRenderer(renderer, row, col);
				String IdMaterial = (String)getValueAt(row, 1);
                String value = (String)getValueAt(row, 2);
                if(col==0)
                	cant = sqlMateriales.getCountRowNuevo(IdMaterial);
                if (col!=1&&col!=2&&col!=6&&cant>0&&value.equals("0")) {
                    c.setBackground(Color.ORANGE);
                }
                //---------------------------------------------------
                Double valorNeto =Double.parseDouble((String)getValueAt(row,9));
                if(valorNeto>0&&col==0) {
                	BeanMaterial mat = sqlMateriales.getMaterial(IdMaterial);
                	if(mat!=null) {
	                	Double margen_obj = Double.parseDouble(mat.getStrMargen_Obj());
	                	Double costo= Double.parseDouble(mat.getStrCosto());
	                	Double margen = ((valorNeto-costo)/valorNeto)*100;
	                	if(margen<margen_obj) {
	                		c.setIcon(redicon);
	                	}else {
	                		c.setIcon(blueicon);
	                	}
                	}
                }
                //---------------------------------------------------
                return c;
            }
		};
		pnlWarnings = new JPanel();
		lblWarnigs = new JLabel();
		cmbCondExped = new JComboBox();
		cmbTipoGestion = new JComboBox();
		txtDisponible = new JTextField();
		txtDisponible.setHorizontalAlignment(SwingConstants.RIGHT);
		btnImprimirComprobante = new JButton("Imprimir");
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);

		String tomaPedido = "/imagenes/toma_pedido.png";
		URL url = this.getClass().getResource(tomaPedido);
		ImageIcon imageIcon = new ImageIcon(url);

		this.setFrameIcon(imageIcon);

		pnlBackground.setLayout(new BorderLayout());

		lblTitulo.setFont(new Font("Tahoma", 1, 18));
		lblTitulo.setText("Pedido <id_cliente> <nombre_cliente>");
		pnlBackground.add(lblTitulo, BorderLayout.PAGE_START);

		pnlContenedor.setLayout(new BorderLayout());

		lblWarnigs.setForeground(new java.awt.Color(255, 0, 0));
		lblWarnigs.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 4));
		pnlWarnings.setLayout(new BorderLayout());
		pnlWarnings.add(lblWarnigs, BorderLayout.CENTER);

		panel = new JPanel();
		pnlWarnings.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		mnuToolBarTop = new JToolBar();
		panel.add(mnuToolBarTop);
		mnuToolBarTop.setRollover(true);
		btnGuardarOrden = new JButton();

		btnGuardarOrden.setText("Guardar");
		btnGuardarOrden.setFocusable(false);
		btnGuardarOrden.setHorizontalTextPosition(SwingConstants.CENTER);
		btnGuardarOrden.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnGuardarOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGuardarOrdenActionPerformed(evt);
			}
		});
		mnuToolBarTop.add(btnGuardarOrden);

		separator = new JSeparator();
		separator.setAlignmentX(Component.LEFT_ALIGNMENT);
		separator.setOrientation(SwingConstants.VERTICAL);
		mnuToolBarTop.add(separator);
		btnCancelar = new JButton();

		btnCancelar.setText("Cancelar");
		btnCancelar.setFocusable(false);
		btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
		mnuToolBarTop.add(btnCancelar);

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cerrar();
			}
		});

		String eliminarGif = "/imagenes/eliminar_24.gif";
		URL urlEliminar = this.getClass().getResource(eliminarGif);
		ImageIcon eliminar = new ImageIcon(urlEliminar);
		btnCancelar.setIcon(eliminar);
		btnCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCancelar.setVerticalTextPosition(SwingConstants.CENTER);

		separator_1 = new JSeparator();
		separator_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		mnuToolBarTop.add(separator_1);

		// mnuToolBarTop.add(btnImprimirComprobante);

		btnImprimir = new JButton("Imprimir");
		mnuToolBarTop.add(btnImprimir);

		String printPng = "/imagenes/print.png";
		URL urlPrint = this.getClass().getResource(printPng);
		ImageIcon print = new ImageIcon(urlPrint);
		btnImprimir.setIcon(print);

		btnImprimir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btmImprimirComprobanteActionPerformed(evt);
			}
		});

		String guardar32Png = "/imagenes/guardar_32.png";
		URL urlGuardar = this.getClass().getResource(guardar32Png);
		ImageIcon guardar = new ImageIcon(urlGuardar);
		btnGuardarOrden.setIcon(guardar);

		btnGuardarOrden.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnGuardarOrden.setVerticalTextPosition(SwingConstants.CENTER);
		mnuToolBarTop.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		separator_2 = new JSeparator();
		separator_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		separator_2.setOrientation(SwingConstants.VERTICAL);
		mnuToolBarTop.add(separator_2);
		
		btnAgenda = new JButton("Proximas Visitas");
		mnuToolBarTop.add(btnAgenda);
		
		String AgendaPng = "/imagenes/iplanificaciones.gif";
		URL urlAgenda = this.getClass().getResource(AgendaPng);
		ImageIcon agenda = new ImageIcon(urlAgenda);
		btnAgenda.setIcon(agenda);
		btnAgenda.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	btmMostrarProximasVisitasActionPerformed(e);
		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		});
		
		JSeparator separatorAgenda = new JSeparator();

		separatorAgenda.setAlignmentX(Component.LEFT_ALIGNMENT);
		separatorAgenda.setOrientation(SwingConstants.VERTICAL);
		mnuToolBarTop.add(separatorAgenda);
		// CUSTOM

		panel_7 = new JPanel();
		mnuToolBarTop.add(panel_7);
		panel_7.setLayout(new GridLayout(0, 1, 0, 0));

		panel_2 = new JPanel();
		panel_7.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		pnlLbPresupuesto = new JPanel();
		panel_2.add(pnlLbPresupuesto);
		pnlLbPresupuesto.setLayout(new GridLayout(4, 1, 0, 0));

		lblPresupuestoAnual = new JLabel("Presupuesto Anual:");
		lblPresupuestoAnual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPresupuestoAnual.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlLbPresupuesto.add(lblPresupuestoAnual);

		lblPresupuestoMensual = new JLabel("Presupuesto Acum.");
		lblPresupuestoMensual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPresupuestoMensual.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlLbPresupuesto.add(lblPresupuestoMensual);

		lblVentaAnual = new JLabel("Venta Anual:");
		lblVentaAnual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVentaAnual.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlLbPresupuesto.add(lblVentaAnual);

		txtEstadoAnual = new JLabel("");
		pnlLbPresupuesto.add(txtEstadoAnual);
		txtEstadoAnual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtEstadoAnual.setForeground(Color.WHITE);
		txtEstadoAnual.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEstadoAnual.setOpaque(true);

		pnlValores = new JPanel();
		panel_2.add(pnlValores);
		pnlValores.setLayout(new GridLayout(4, 1, 0, 0));

		panel_10 = new JPanel();
		pnlValores.add(panel_10);
		panel_10.setLayout(new GridLayout(0, 2, 0, 0));

		lblValorAnual = new JLabel("");
		panel_10.add(lblValorAnual);
		lblValorAnual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblValorAnual.setHorizontalAlignment(SwingConstants.RIGHT);

		label_2 = new JLabel("");
		panel_10.add(label_2);

		panel_11 = new JPanel();
		pnlValores.add(panel_11);
		panel_11.setLayout(new GridLayout(0, 2, 0, 0));

		lblValorAfecha = new JLabel("");
		panel_11.add(lblValorAfecha);
		lblValorAfecha.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblValorAfecha.setHorizontalAlignment(SwingConstants.RIGHT);

		lblNewLabel_2 = new JLabel("");
		panel_11.add(lblNewLabel_2);

		panel_9 = new JPanel();
		pnlValores.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 2, 0, 0));

		lblValorVtaAnual = new JLabel("");
		panel_9.add(lblValorVtaAnual);
		lblValorVtaAnual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblValorVtaAnual.setHorizontalAlignment(SwingConstants.RIGHT);

		lblPorcentajeVtaAnual = new JLabel("");
		lblPorcentajeVtaAnual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPorcentajeVtaAnual.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblPorcentajeVtaAnual.setOpaque(true);
		panel_9.add(lblPorcentajeVtaAnual);

		panel_5 = new JPanel();
		pnlValores.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));

		txtResultadoAnual = new JLabel("");
		txtResultadoAnual.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_5.add(txtResultadoAnual);
		txtResultadoAnual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtResultadoAnual.setForeground(Color.WHITE);
		txtResultadoAnual.setOpaque(true);
		txtResultadoAnual.setHorizontalAlignment(SwingConstants.RIGHT);

		label_3 = new JLabel("");
		panel_5.add(label_3);

		panel_8 = new JPanel();
		mnuToolBarTop.add(panel_8);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));

		panel_6 = new JPanel();
		panel_8.add(panel_6);
		panel_6.setLayout(new GridLayout(4, 1, 0, 0));

		pbAnual = new JProgressBar();
		panel_6.add(pbAnual);
		pbAnual.setFont(new Font("Tahoma", Font.PLAIN, 8));

		pbActual = new JProgressBar();
		panel_6.add(pbActual);
		pbActual.setFont(new Font("Tahoma", Font.PLAIN, 8));

		pbReal = new JProgressBar();
		pbReal.setForeground(Color.WHITE);
		pbReal.setBackground(Color.WHITE);
		pbReal.setOpaque(true);
		panel_6.add(pbReal);

		panel_3 = new JPanel();
		panel_6.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));

		pnlCreimientoAnual = new JPanel();
		panel_3.add(pnlCreimientoAnual);
		pnlCreimientoAnual.setLayout(new GridLayout(0, 2, 0, 0));

		lblNewLabel = new JLabel("Creci. Anual:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pnlCreimientoAnual.add(lblNewLabel);

		lblCrecimientoAnual = new JLabel("");
		lblCrecimientoAnual.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCrecimientoAnual.setForeground(Color.WHITE);
		lblCrecimientoAnual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCrecimientoAnual.setBackground(Color.WHITE);
		lblCrecimientoAnual.setOpaque(true);
		pnlCreimientoAnual.add(lblCrecimientoAnual);

		panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));

		lblPromePlusValor = new JLabel("");
		lblPromePlusValor.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPromePlusValor.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblPromePlusValor);

		lblPromePlus = new JLabel("");
		lblPromePlus.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPromePlus.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPromePlus.setForeground(Color.WHITE);
		lblPromePlus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPromePlus.setOpaque(true);
		panel_4.add(lblPromePlus);

		panel_1 = new JPanel();
		mnuToolBarTop.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		pnlPresupuesto = new JPanel();

		pnlbpPresupuestos = new JPanel();
		pnlbpPresupuestos.setLayout(new GridLayout(1, 2, 0, 0));
		pnlPresupuesto.setLayout(new GridLayout(0, 1, 0, 0));
		pnlPresupuesto.add(pnlbpPresupuestos);

		pnlActivar = new JPanel();
		pnlbpPresupuestos.add(pnlActivar);
		pnlActivar.setLayout(new GridLayout(0, 1, 0, 0));

		lblNewLabel_1 = new JLabel("Cartera:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setEnabled(true);
		pnlActivar.add(lblNewLabel_1);

		lblActivar = new JLabel("");
		lblActivar.setHorizontalAlignment(SwingConstants.CENTER);
		pnlActivar.add(lblActivar);
		panel_1.add(pnlPresupuesto);

		pnlContenedor.add(pnlWarnings, BorderLayout.PAGE_START);

		pnlDatos.setLayout(new BorderLayout());

		//pnlInformacionGeneral.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		//pnlInformacionGeneral.setPreferredSize(new Dimension(800,800));
		pnlUno.setLayout(new BorderLayout(5, 5));

		pnlLabelsUno.setLayout(new GridLayout(4, 1));

		lblVtaMercaderia.setText("Vta. Mercadería:");
		pnlLabelsUno.add(lblVtaMercaderia);

		String destino = "<html>Destinatario:<font color='red'> *</font></html>";
		lblDestinatario.setText(destino);
		pnlLabelsUno.add(lblDestinatario);

		lblBloqEntrega.setText("Bloq. Entrega:");
		pnlLabelsUno.add(lblBloqEntrega);
		
		JLabel lblTipologia = new JLabel("Tipologia:");
		pnlLabelsUno.add(lblTipologia);
		//--------------------------------------------------------	
		pnlUno.add(pnlLabelsUno, BorderLayout.LINE_START);

		pnlTextsUno.setLayout(new GridLayout(4, 1, 5, 5));
		pnlTextsUno.add(txtCodigoPedido);

		pnlVtaMercaderia.setLayout(new BorderLayout());

		btnDetalleDestinatario.setPreferredSize(new Dimension(20, 9));
		btnDetalleDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDetalleDestinatarioActionPerformed(evt);
			}
		});

		pnlVtaMercaderia.add(btnDetalleDestinatario, BorderLayout.LINE_END);
		pnlVtaMercaderia.add(txtDestinatario, BorderLayout.CENTER);

		pnlTextsUno.add(pnlVtaMercaderia);

		pnlBloqEntrega.setLayout(new BorderLayout());

		btnDetalleBloqEntrega.setPreferredSize(new Dimension(20, 9));
		btnDetalleBloqEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDetalleBloqEntregaActionPerformed(evt);
			}
		});
		txtBloqEntrega.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				if (evt.getSource() == txtBloqEntrega)
					txtBloqEntrega.selectAll();
			}
		});
		pnlBloqEntrega.add(btnDetalleBloqEntrega, BorderLayout.LINE_END);
		pnlBloqEntrega.add(txtBloqEntrega, BorderLayout.CENTER);

		pnlTextsUno.add(pnlBloqEntrega);
		
		BeanCliente cli = new SqlClienteImpl().buscarCliente(codigoCliente);
		JTextField txtTipologia = new JTextField();
		txtTipologia.setEditable(false);
		txtTipologia.setText(cli.getStrDescripcionTipologia());
		pnlTextsUno.add(txtTipologia);
//---------------------------------------------------------------
		pnlUno.add(pnlTextsUno, BorderLayout.CENTER);

		pnlTres.setLayout(new BorderLayout(5, 5));

		pnlLabels3.setMinimumSize(new Dimension(15, 0));
		pnlLabels3.setPreferredSize(new Dimension(85, 70));
		pnlLabels3.setLayout(new GridLayout(4, 1, 5, 5));
		
		JLabel lblClase 	= new JLabel("Clase:");		
		JTextField txtClase = new JTextField();
		txtClase.setPreferredSize(new Dimension(75, 9));
		txtClase.setEditable(false);
		txtClase.setText(cli.getStrClase());	
		
		panelClase.setLayout(new BorderLayout());
		panelClase.add(lblClase, BorderLayout.CENTER);
		//panelClase.add(txtClase,  BorderLayout.LINE_END);
		
		pnlLabels3.add(panelClase);	

		lblCondPago.setText("<html>Cond. Pago:<font color='red'> *</font></html>");
		pnlLabels3.add(lblCondPago);

		pnlTres.add(pnlLabels3, BorderLayout.LINE_START);

		label = new JLabel();
		label.setText("Lim. Crédito:");
		pnlLabels3.add(label);

		label_1 = new JLabel();
		label_1.setText("Disponible:");
		pnlLabels3.add(label_1);

		pnlTextsTres.setLayout(new GridLayout(4, 1, 5, 5));
		
		lblClasRiesgo.setText("Clas. Riesgo:");
				
		//Border used as padding
		//Border paddingBorder = BorderFactory.createEmptyBorder(10,0,0,0);
		//JLabel will be involved for this border
		//Border border = BorderFactory.createLineBorder(Color.white);

		//txtClase.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
		
		lblClasRiesgo.setBorder(new EmptyBorder(0,10,0,0));
		
		txtClasRiesgo.setPreferredSize(new Dimension(75, 9));
		
		panelClaseRiesgo.setLayout(new BorderLayout());
		panelClaseRiesgo.add(txtClase, BorderLayout.LINE_START);
		panelClaseRiesgo.add(lblClasRiesgo);
		panelClaseRiesgo.add(txtClasRiesgo,  BorderLayout.LINE_END);
		
		pnlTextsTres.add(panelClaseRiesgo);
		
		jPanel9.setLayout(new BorderLayout());

		btnDetalleCondPago.setPreferredSize(new Dimension(30, 9));
		btnDetalleCondPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDetalleCondPagoActionPerformed(evt);
			}
		});
		jPanel9.add(btnDetalleCondPago, BorderLayout.LINE_END);
		txtCondPago.setPreferredSize(new Dimension(30, 9));
		jPanel9.add(txtCondPago, BorderLayout.CENTER);

		pnlTextsTres.add(jPanel9);
		
		

		pnlTres.add(pnlTextsTres, BorderLayout.CENTER);

		pnlCuatro.setLayout(new BorderLayout(5, 5));

		pnlLabels4.setLayout(new GridLayout(4, 1, 5, 5));

		lblPresupuesto = new JLabel("Presup. Mes:");
		pnlLabels4.add(lblPresupuesto);

		lblAcumReal = new JLabel("Acum. Mes:");
		pnlLabels4.add(lblAcumReal);

		lblValorNeto.setText("Venta hoy:");
		pnlLabels4.add(lblValorNeto);

		pnlCuatro.add(pnlLabels4, BorderLayout.LINE_START);

		lblSaldoPend = new JLabel("Sld Pend. Mes:");
		pnlLabels4.add(lblSaldoPend);

		pnlTexts4.setLayout(new GridLayout(4, 1, 5, 5));

		txtPresupuesto = new JTextField();
		txtPresupuesto.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPresupuesto.setEditable(false);
		pnlTexts4.add(txtPresupuesto);
		txtPresupuesto.setColumns(10);

		txtAcumReal = new JTextField();
		txtAcumReal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAcumReal.setEditable(false);
		pnlTexts4.add(txtAcumReal);
		txtAcumReal.setColumns(10);
		pnlTexts4.add(txtValorNeto);

		pnlCuatro.add(pnlTexts4, BorderLayout.CENTER);

		pnlDos.setLayout(new BorderLayout(5, 5));

		pnlLabelsDos.setMaximumSize(new Dimension(100, 32767));
		pnlLabelsDos.setMinimumSize(new Dimension(100, 42));
		pnlLabelsDos.setPreferredSize(new Dimension(90, 100));
		pnlLabelsDos.setLayout(new GridLayout(4, 1));

		lblNPedCliente.setText("N° ped. cliente:");
		lblNPedCliente.setPreferredSize(new Dimension(60, 14));
		pnlLabelsDos.add(lblNPedCliente);

		lblNombre.setText("Nombre:");
		lblNombre.setPreferredSize(new Dimension(60, 14));
		pnlLabelsDos.add(lblNombre);

		JLabel lblCondExped = new JLabel("Cond. Exped.");
		pnlLabelsDos.add(lblCondExped);
		
		JLabel lblTipoGestion = new JLabel("Tipo Gestion:");
		pnlLabelsDos.add(lblTipoGestion);
		
		
		
//---------------------------------------------------------------------
		pnlDos.add(pnlLabelsDos, BorderLayout.LINE_START);

		pnlTextsDos.setLayout(new GridLayout(4, 1, 5, 5));

		txtNPedCliente.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				if (evt.getSource() == txtNPedCliente)
					txtNPedCliente.selectAll();
			}
		});
		pnlTextsDos.add(txtNPedCliente);
		pnlTextsDos.add(txtNombre);
		cmbCondExped.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		pnlTextsDos.add(cmbCondExped);
		
		cmbTipoGestion.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		pnlTextsDos.add(cmbTipoGestion);
		
		
		
		//**Modificado **//
		//JTextField txtClase = new JTextField();
		//txtClase.setEditable(false);
		//Double defaultHeight = txtClase.getSize().getHeight();
		// 3. set custom width and default height
		//txtClase.setSize(new Dimension(5, 5));
		//txtClase.setText(cli.getStrClase());
		//pnlTextsDos.add(txtClase);
		
		//------------------------------------------------
		pnlDos.add(pnlTextsDos, BorderLayout.CENTER);

		GroupLayout pnlInformacionGeneralLayout = new GroupLayout(pnlInformacionGeneral);
		pnlInformacionGeneralLayout
				.setHorizontalGroup(
						pnlInformacionGeneralLayout
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(pnlInformacionGeneralLayout.createSequentialGroup().addContainerGap()
										.addComponent(pnlUno, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(pnlDos, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(pnlTres, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(pnlCuatro, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
										.addContainerGap()));
		pnlInformacionGeneralLayout.setVerticalGroup(pnlInformacionGeneralLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pnlInformacionGeneralLayout.createSequentialGroup().addContainerGap()
						.addGroup(pnlInformacionGeneralLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(pnlCuatro, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(pnlTres, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(pnlUno, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(pnlDos, 0, 0, Short.MAX_VALUE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlInformacionGeneral.setLayout(pnlInformacionGeneralLayout);

		pnlTabla.setLayout(new BorderLayout(5, 5));

		mnuToolBarMiddle.setFloatable(false);
		mnuToolBarMiddle.setRollover(true);

		btnAniadir.setText("Añadir");
		btnAniadir.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnAniadir.setFocusable(false);
		btnAniadir.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAniadir.setVerticalTextPosition(SwingConstants.BOTTOM);
		mnuToolBarMiddle.add(btnAniadir);
		mnuToolBarMiddle.add(separador2);

		btnAniadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAniadirPedidoActionPerformed(evt);
			}
		});

		btnEliminar.setText("Eliminar");
		btnEliminar.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnEliminar.setFocusable(false);
		btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
		mnuToolBarMiddle.add(btnEliminar);
		mnuToolBarMiddle.add(separador3);

		btnConsultaCapturaDinamica.setText("Consulta y Captura Dinámica");
		btnConsultaCapturaDinamica.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnConsultaCapturaDinamica.setFocusable(false);
		btnConsultaCapturaDinamica.setHorizontalTextPosition(SwingConstants.CENTER);
		btnConsultaCapturaDinamica.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnConsultaCapturaDinamica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnConsultaCapturaDinamicaActionPerformed(evt);
			}
		});
		mnuToolBarMiddle.add(btnConsultaCapturaDinamica);
		mnuToolBarMiddle.add(separador4);

		btnGuiaVentas.setText("Histórico de Facturas");
		btnGuiaVentas.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnGuiaVentas.setFocusable(false);
		btnGuiaVentas.setHorizontalTextPosition(SwingConstants.CENTER);
		btnGuiaVentas.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnGuiaVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGuiaVentasActionPerformed(evt);
			}
		});

		btnSimularTodo.setText("Calcular precios");
		btnSimularTodo.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnSimularTodo.setFocusable(false);
		btnSimularTodo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSimularTodo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSimularTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSimularTodoActionPerformed(evt);
			}
		});
		mnuToolBarMiddle.add(btnGuiaVentas);
		mnuToolBarMiddle.add(separador5);
		
		
		mnuToolBarMiddle.add(btnSimularTodo);
		mnuToolBarMiddle.add(separador6);
		btnMaterialNuevo.setText("Material Nuevo");
		btnMaterialNuevo.setBackground(Color.YELLOW);
		btnMaterialNuevo.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnMaterialNuevo.setFocusable(false);
		btnMaterialNuevo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnMaterialNuevo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnMaterialNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				consultaDinamica(3);
				
			}
		});
		mnuToolBarMiddle.add(btnMaterialNuevo);
		
		mnuToolBarMiddle.add(separador7);
		btnVentaCruzada.setText("Venta Cruzada");
		btnVentaCruzada.setBackground(Color.ORANGE);
		btnVentaCruzada.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnVentaCruzada.setFocusable(false);
		btnVentaCruzada.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVentaCruzada.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVentaCruzada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				consultaDinamica(4);
				
			}
		});
		mnuToolBarMiddle.add(btnVentaCruzada);
		
		mnuToolBarMiddle.add(separador8);
		btnVentaCruzadaCliente.setText("Venta Cruzada por Categoria");
		btnVentaCruzadaCliente.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnVentaCruzadaCliente.setFocusable(false);
		btnVentaCruzadaCliente.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVentaCruzadaCliente.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVentaCruzadaCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btmMostrarVentasCruzadasActionPerformed();
			}
		});
		mnuToolBarMiddle.add(btnVentaCruzadaCliente);
		
		mnuToolBarMiddle.add(separador9);
		btnResultadoMarca.setText("Resultado Marca");
		btnResultadoMarca.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnResultadoMarca.setFocusable(false);
		btnResultadoMarca.setHorizontalTextPosition(SwingConstants.CENTER);
		btnResultadoMarca.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnResultadoMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MostrarMarcas(codigoCliente);
				
			}
		});
		mnuToolBarMiddle.add(btnResultadoMarca);
		
		
		//lblMensajes.setText("EL MATERIAL SELECCIONADO NO EXISTE");
		lblMensajes.setText("");
		lblMensajes.setPreferredSize(new Dimension(400, 14));
		lblMensajes.setMinimumSize(new Dimension(400, 14));
		lblMensajes.setMaximumSize(new Dimension(400, 14));
		lblMensajes.setForeground(Color.RED);
		
		mnuToolBarMiddle.add(lblMensajes);
		
		
		
		pnlTabla.add(mnuToolBarMiddle, BorderLayout.PAGE_START);

		tblPedidos
				.setModel(new DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroller.setViewportView(tblPedidos);

		pnlTabla.add(scroller, BorderLayout.CENTER);

		GroupLayout pnlInformacionLayout = new GroupLayout(pnlInformacion);
		pnlInformacionLayout.setHorizontalGroup(pnlInformacionLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(pnlInformacionLayout.createSequentialGroup()
						.addGroup(pnlInformacionLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(pnlInformacionLayout.createSequentialGroup().addGap(10)
										.addComponent(pnlTabla, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE))
								.addComponent(pnlInformacionGeneral, GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE))
						.addContainerGap()));
		pnlInformacionLayout.setVerticalGroup(pnlInformacionLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pnlInformacionLayout.createSequentialGroup()
						.addComponent(pnlInformacionGeneral, GroupLayout.PREFERRED_SIZE, 111,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(pnlTabla, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE).addContainerGap()));
		pnlInformacion.setLayout(pnlInformacionLayout);

		pnlDatos.add(pnlInformacion, BorderLayout.CENTER);

		pnlContenedor.add(pnlDatos, BorderLayout.CENTER);

		pnlBackground.add(pnlContenedor, BorderLayout.CENTER);

		getContentPane().add(pnlBackground, BorderLayout.CENTER);

		// CUSTOM
		// btnImprimirComprobante.setIcon(print);

		lblTitulo.setOpaque(true);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));

		SqlCabeceraHojaMaestraCreditoImpl sqlCabeceraHojaMaestraCreditoImpl = new SqlCabeceraHojaMaestraCreditoImpl();
		String codigoVendedor = Promesa.datose.get(0).getStrCodigo();
		CabeceraHojaMaestraCredito cabeceraHojaMaestraCredito = sqlCabeceraHojaMaestraCreditoImpl
				.obtenerCabeceraHojaMaestraCredito(codigoCliente, codigoVendedor);
		if (("X").equalsIgnoreCase(cabeceraHojaMaestraCredito.getBloqueoCredito())) {
			lblActivar.setIcon(new ImageIcon(IPedidos.class.getResource("/imagenes/circulo_rojo.png")));
		} else {
			lblActivar.setIcon(new ImageIcon(IPedidos.class.getResource("/imagenes/circulo_verda.png")));
		}

		lblTitulo.setBackground(new Color(175, 200, 222));
		tblPedidos.setModel(mdlTblItems);
		tblPedidos.setDefaultRenderer(String.class, render);
		tblPedidos.setRowHeight(Constante.ALTO_COLUMNAS);
		pack();
		for (int i = 0; i < 12; i++) {
			agregarItemVacio(-1);
		}
		tblPedidos.updateUI();
		selectionModel = tblPedidos.getSelectionModel();
		this.setAnchoColumnas();
		((DefaultTableCellRenderer) tblPedidos.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		btnEliminar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEliminarPedidoActionPerformed(evt);
			}
		});
		tblPedidos.getTableHeader().setReorderingAllowed(false);
		txtValorNeto.setEditable(false);

		panel_12 = new JPanel();
		pnlTexts4.add(panel_12);
		panel_12.setLayout(new GridLayout(0, 3, 0, 0));

		txtSaldoPendiente = new JTextField();
		panel_12.add(txtSaldoPendiente);
		txtSaldoPendiente.setForeground(Color.WHITE);
		txtSaldoPendiente.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSaldoPendiente.setEditable(false);
		txtSaldoPendiente.setColumns(10);

		lblSldoAcum = new JLabel("Sld Pend. Acum:");
		lblSldoAcum.setHorizontalAlignment(SwingConstants.CENTER);
		panel_12.add(lblSldoAcum);

		txtSldoAcum = new JTextField();
		txtSldoAcum.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtSldoAcum.setForeground(Color.WHITE);
		txtSldoAcum.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSldoAcum.setEditable(false);
		panel_12.add(txtSldoAcum);
		txtSldoAcum.setColumns(10);
		txtCodigoPedido.setEditable(false);
		txtClasRiesgo.setEditable(false);
		txtDestinatario.setEditable(false);
		txtNombre.setEditable(false);
		txtCondPago.setEditable(false);
		txtBloqEntrega.setEditable(false);

		initPresupuesto();
	}// </editor-fold>

	
	public void MostrarMarcas(String codigoCliente) {
		Object[] cols = {"Marca","Ppto Mes","Vta Mes","Cumpl Mes","Ppto Acum","Vta Acum","Cumpl Acum"};
		SqlMarcaVendedorImpl smvi = new SqlMarcaVendedorImpl();
		SqlJerarquiaImpl sji = new SqlJerarquiaImpl();
		List<MarcaVendedor> colMV = smvi.getMarcaVendedorByCliente(codigoCliente);
		int irows = colMV.size();
		int icols = cols.length;
		Object[][] rows = new Object[irows][icols]; 
		for(int i=0; i<irows; i++) {
			rows[i][0]=colMV.get(i).getMarca()+" - "+sji.getMarcaVendedor(colMV.get(i).getMarca());
			rows[i][1]=colMV.get(i).getPresupuestoMes();
			rows[i][2]=colMV.get(i).getVentaMes();
			if(colMV.get(i).getPresupuestoMes().equals("0"))
				rows[i][3]="<html><font color='red'>0%</font></html>";
			else {
				int cm = Integer.parseInt(String.valueOf((long)((Double.parseDouble(colMV.get(i).getVentaMes())/Double.parseDouble(colMV.get(i).getPresupuestoMes()))*100)));
				String strM = "";
				if(cm<100) {
					strM = "<html><font color='red'>" + cm + "%</font></html>";
				}else {
					strM = cm+"%";
				} 
				rows[i][3]=strM;
			}
			rows[i][4]=colMV.get(i).getPresupuestoAcumulado();
			rows[i][5]=colMV.get(i).getVentaAcumulado();
			if(colMV.get(i).getPresupuestoAcumulado().equals("0"))
				rows[i][6]="<html><font color='red'>0%</font></html>";
			else {
				int cm = Integer.parseInt(String.valueOf((long)((Double.parseDouble(colMV.get(i).getVentaAcumulado())/Double.parseDouble(colMV.get(i).getPresupuestoAcumulado()))*100)));
				String strM = "";
				if(cm<100) {
					strM = "<html><font color='red'>" + cm + "%</font></html>";
				}else {
					strM = cm+"%";
				} 
				rows[i][6]=strM;
			}
		}
		JTable table = new JTable(rows, cols);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setCellRenderer(tcr);
		table.getColumnModel().getColumn(2).setCellRenderer(tcr);
		table.getColumnModel().getColumn(3).setCellRenderer(tcr);
		table.getColumnModel().getColumn(4).setCellRenderer(tcr);
		table.getColumnModel().getColumn(5).setCellRenderer(tcr);
		table.getColumnModel().getColumn(6).setCellRenderer(tcr);
		table.setSize(900, 600);
		table.setPreferredSize(new Dimension(900,600));
	
		JScrollPane jsp = new JScrollPane(table);
		JPanel jp = new JPanel();
		jp.add(jsp);
		jsp.getViewport().add(table);
		JOptionPane.showMessageDialog(null, jp,"Cliente - Marca",JOptionPane.PLAIN_MESSAGE);
		
	}

	private void initPresupuesto() {

		SqlCliente sqlCliente = new SqlClienteImpl();
		Presupuesto pre = sqlCliente.getPresupestoByCliente(Util.completarCeros(10, codigoCliente));

		if (pre != null) {

			pbReal.setStringPainted(true);
			pbReal.setString("");

			double dAnual = Double.parseDouble(pre.getPresupuestoAnual());
			double dVtaAnual = Double.parseDouble(pre.getVentaAnual());

			double dMaxAnual = dAnual;
			if (dAnual < dVtaAnual) {
				dMaxAnual = dVtaAnual;
			}
			int maxAnual = (int) dMaxAnual;

			pbAnual.setMaximum(maxAnual);
			pbActual.setMaximum(maxAnual);
			pbReal.setMaximum(maxAnual);

			int anual = (int) dAnual;
			pbAnual.setValue(anual);

			double dPreFecha = Double.parseDouble(pre.getPresupuestoFecha());
			int presupuestoFecha = (int) dPreFecha;
			Double porcentajeVenta = 0.0;
			if (dPreFecha > 0) {
				porcentajeVenta = (dVtaAnual * 100) / dPreFecha;
			}

			pbReal.setFont(new java.awt.Font("Tahoma", 1, 11));
			pbReal.setString(Util.formatearNumero(porcentajeVenta) + "%");

			pbActual.setValue(presupuestoFecha);

			int venteAnual = (int) dVtaAnual;
			pbReal.setValue(venteAnual);

			dPresupuesto = pre.getPresupuestoMensual();
			dAcumaloReal = pre.getVentaMensual();

			txtPresupuesto.setText(dPresupuesto);
			txtAcumReal.setText(dAcumaloReal);

			lblValorAnual.setText(Util.formatoDigitoDecimal(pre.getPresupuestoAnual()));
			lblValorAfecha.setText(Util.formatoDigitoDecimal(pre.getPresupuestoFecha()));
			lblValorVtaAnual.setText(Util.formatoDigitoDecimal(pre.getVentaAnual()));

			resultadoAnual = dVtaAnual - presupuestoFecha;
			if (resultadoAnual > 0) {
				txtEstadoAnual.setText("Superavit");
				txtEstadoAnual.setBackground(Color.BLUE);
				pbReal.setForeground(Color.BLUE);
				txtResultadoAnual.setBackground(Color.BLUE);
			} else if (resultadoAnual < 0) {
				txtEstadoAnual.setText("Deficit");
				pbReal.setForeground(Color.RED);
				txtEstadoAnual.setBackground(Color.red);
				txtResultadoAnual.setBackground(Color.RED);
			} else {
				txtEstadoAnual.setText("En Cump.");
				pbReal.setForeground(Color.GREEN);
				txtEstadoAnual.setBackground(Color.GREEN);
				txtResultadoAnual.setBackground(Color.GREEN);
			}
			txtResultadoAnual.setText(Util.formatoDigitoDecimal(Math.abs(resultadoAnual)));

			double VtaAnioAnt = Double.parseDouble(pre.getVentaAcumAnioAnt());
			double creciAnual = 0.0;
			if (VtaAnioAnt > 0) {
				creciAnual = (dVtaAnual - VtaAnioAnt) / VtaAnioAnt;
			} else {
				creciAnual = 0.0;
			}

			if (creciAnual < 0) {
				lblCrecimientoAnual.setBackground(Color.red);
			} else if (creciAnual > 0) {
				lblCrecimientoAnual.setBackground(Color.blue);
			} else {
				lblCrecimientoAnual.setBackground(Color.green);
			}
			lblCrecimientoAnual.setText(Util.formatearNumero(creciAnual * 100) + "%");

			if (!pre.getPromoPlus().equals("0")) {
				String valorNeto = "0.0";
				try {
					valorNeto = this.pedido.getHeader().getStrValorNeto();
				} catch (Exception e) {
					valorNeto = txtValorNeto.getText();
					if (valorNeto.equalsIgnoreCase("")) {
						valorNeto = "0.0";
					}
				} finally {
					if (valorNeto == null) {
						valorNeto = "0.0";
					}
				}
				double dValorNeto = Double.parseDouble(valorNeto);
				double promePlus = dValorNeto + Double.parseDouble(pre.getVta_gracia())/ Double.parseDouble(pre.getPromoPlus());
				if (promePlus > 1) {
					lblPromePlus.setBackground(Color.blue);
				} else if (promePlus < 1) {
					lblPromePlus.setBackground(Color.red);
				} else {
					lblPromePlus.setBackground(Color.green);
				}
				lblPromePlusValor.setText("Clientes Gracia: " + pre.getPromoPlus()+".00");
				lblPromePlus.setText(Util.formatearNumero(promePlus * 100) + "%");
			} else {
				lblPromePlusValor.setText("Clientes Gracia: " + pre.getPromoPlus()+".00");
				lblPromePlus.setText(pre.getPromoPlus() + "%");
				lblPromePlus.setForeground(Color.BLACK);
			}
			calcularSaldoPendiente();
		}
	}

	private void calcularSaldoPendiente() {
		//
		SqlCliente sqlCliente = new SqlClienteImpl();
		Presupuesto pre = sqlCliente.getPresupestoByCliente(Util.completarCeros(10, codigoCliente));
		String valorNeto = "0.0";
		try {
			valorNeto = this.pedido.getHeader().getStrValorNeto();
		} catch (Exception e) {
			valorNeto = txtValorNeto.getText();
			if (valorNeto.equalsIgnoreCase("")) {
				valorNeto = "0.0";
			}
		} finally {
			if (valorNeto == null) {
				valorNeto = "0.0";
			}
		}
		double presMensual = 0.0;
		try {
			presMensual = Double.parseDouble(dPresupuesto);
		} catch (Exception e) {
			presMensual = 0.0;
		}
		
		double dAcumreal = 0.0;
		try {
			dAcumreal = Double.parseDouble(dAcumaloReal);
		} catch (Exception e) {
			dAcumreal = 0.0;
		}
		
		double dValorNeto = Double.parseDouble(valorNeto);
		if (!pre.getPromoPlus().equals("0")&&pre!=null) {
			double promePlus = (dValorNeto + Double.parseDouble(pre.getVta_gracia()))/ Double.parseDouble(pre.getPromoPlus());
			if (promePlus > 1) {
				lblPromePlus.setBackground(Color.blue);
			} else if (promePlus < 1) {
				lblPromePlus.setBackground(Color.red);
			} else {
				lblPromePlus.setBackground(Color.green);
			}
			lblPromePlus.setText(Util.formatearNumero(promePlus * 100) + "%");
		} else {
			lblPromePlus.setText(pre.getPromoPlus() + "%");
			lblPromePlus.setForeground(Color.BLACK);
		}
		
		double dSaldoPend = presMensual - dAcumreal - dValorNeto;
		if (dSaldoPend < 0) {
			txtSaldoPendiente.setBackground(Color.BLUE);
		} else {
			txtSaldoPendiente.setBackground(Color.RED);
		}
		txtSaldoPendiente.setText(Util.formatearNumero(Math.abs(dSaldoPend)));

		String estadoAnual = txtEstadoAnual.getText();

		double sldoAcum = dSaldoPend;
		int mesesFaltante = Util.getMesesFaltante();
		if (mesesFaltante > 0) {
			if (estadoAnual.equalsIgnoreCase("Superavit")) {
				sldoAcum = dSaldoPend - (Math.abs(resultadoAnual) / Util.getMesesFaltante());
			} else {
				sldoAcum = dSaldoPend + (Math.abs(resultadoAnual) / Util.getMesesFaltante());
			}
		}

		if (sldoAcum < 0) {
			txtSldoAcum.setBackground(Color.BLUE);
		} else {
			txtSldoAcum.setBackground(Color.RED);
		}
		txtSldoAcum.setText(Util.formatearNumero(Math.abs(sldoAcum)));
	}

	protected void btmImprimirComprobanteActionPerformed(ActionEvent evt) {
		try {
			if (Constante.IMPRESORA_NEW.equals(strDispositivoImpresora)) {
				try {
					imprimirDPP350(tituloImpresion);
					imprimirDPP350("-  COPIA  -");
				} catch (Exception e) {
					imprimir(tituloImpresion);
				}
			} else {
				imprimir(tituloImpresion);
			}
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			Util.mostrarExcepcion(e);
		}
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
	//TODO cambiar
	protected void btmMostrarVentasCruzadasActionPerformed() {
		try {
			Object[][] rows = getVentaCruzadaCategoria(codigoCliente);
			Object[] cols = {
			    "Categoria","Venta/Real","Oportunidad","Cump"
			};
			JTable table = new JTable(rows, cols);
			JOptionPane optionpane = new JOptionPane(new JScrollPane(table),JOptionPane.PLAIN_MESSAGE,
					JOptionPane.DEFAULT_OPTION,null,null,null);
			JDialog dialog = optionpane.createDialog("Venta Cruzada Por Categoria");
			dialog.setVisible(true);
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			Util.mostrarExcepcion(e);
		}
	}

	protected void btnSimularTodoActionPerformed(ActionEvent evt) {
		simularPreciosTodosMateriales();
		establecerCursorPrimeraPosicionLibre();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void llenarCondicionesExpedicion() {
		SqlCondicionExpedicionImpl controller = new SqlCondicionExpedicionImpl();
		controller.setListaCondicionExpedicion();
		Object[] condiciones = controller.getListaCondicionExpedicion().toArray();
		cmbCondExped.setModel(new DefaultComboBoxModel(condiciones));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void llenarComboTipoGestion() {
		SqlLlenarComboTipoGestion controller = new SqlLlenarComboTipoGestion();
		controller.setListaTipoGestion();
		Object[] tpoGestion = controller.getListaTipoGestion().toArray();
		cmbTipoGestion.setModel(new DefaultComboBoxModel(tpoGestion));
		cmbTipoGestion.setSelectedIndex(9);
	}

	protected void llenarTablaDestinatarios() {
		try {
			tablaSucursales = new JTable(modeloSucursales);
			tablaSucursales.getTableHeader().setReorderingAllowed(false);
			modeloSucursales.setRowCount(0);
			tablaSucursales.getColumn("CÓDIGO").setMaxWidth(80);
			tablaSucursales.getColumn("CÓDIGO").setMinWidth(80);
			tablaSucursales.getColumn("CÓDIGO").setPreferredWidth(80);
			tablaSucursales.getColumn("DIRECCIÓN").setMaxWidth(320);
			tablaSucursales.getColumn("DIRECCIÓN").setMinWidth(320);
			tablaSucursales.getColumn("DIRECCIÓN").setPreferredWidth(320);
			scrollSucursales = new JScrollPane();
			scrollSucursales.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollSucursales.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollSucursales.setViewportView(tablaSucursales);
			scrollSucursales.setPreferredSize(new Dimension(400, 100));
			popupSucursales = new JPopupMenu();
			popupSucursales.add(scrollSucursales);
			tablaSucursales.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					seleccionarDestinatario(evt);
				}
			});
			SqlSedeImpl sedes = new SqlSedeImpl();
			sedes.setListarSede(codigoCliente);
			List<BeanSede> listaSedes = sedes.getListaSede();
			for (BeanSede beanSede : listaSedes) {
				String[] values = new String[] { beanSede.getCodigo(), beanSede.getDireccion() };
				if (beanSede.getCodigo().compareTo(codigoCliente) == 0) {
					direccionDestinatario = beanSede.getDireccion();
				}
				modeloSucursales.addRow(values);
			}
		} catch (Exception exec) {
			Util.mostrarExcepcion(exec);
		}
	}

	protected void llenarTablaCondicionesPago() {
		try {
			tablaCondiciones = new JTable(modelCondiciones);
			tablaCondiciones.getTableHeader().setReorderingAllowed(false);
			modelCondiciones.setRowCount(0);
			tablaCondiciones.getColumn("CONDICIÓN DE PAGO").setMaxWidth(150);
			tablaCondiciones.getColumn("CONDICIÓN DE PAGO").setMinWidth(150);
			tablaCondiciones.getColumn("CONDICIÓN DE PAGO").setPreferredWidth(150);
			tablaCondiciones.getColumn("ACLARACIÓN PROPIA").setMaxWidth(250);
			tablaCondiciones.getColumn("ACLARACIÓN PROPIA").setMinWidth(250);
			tablaCondiciones.getColumn("ACLARACIÓN PROPIA").setPreferredWidth(250);
			scrollerCondiciones = new JScrollPane();
			int hScrollAlways = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
			scrollerCondiciones.setHorizontalScrollBarPolicy(hScrollAlways);
			int vScrollAlways = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
			scrollerCondiciones.setVerticalScrollBarPolicy(vScrollAlways);
			scrollerCondiciones.setViewportView(tablaCondiciones);
			scrollerCondiciones.setPreferredSize(new Dimension(400, 200));
			popupCondiciones = new JPopupMenu();
			popupCondiciones.add(scrollerCondiciones);
			tablaCondiciones.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					seleccionarCondicionPago(evt);
				}
			});

			condiciones = new SqlCondicionPagoImpl();
			String condicionPagoClienteXDefecto;
			SqlClienteImpl sqlCliente = new SqlClienteImpl();

			condicionPagoClienteXDefecto = sqlCliente.obtenerCondicionPagoPorDefecto(codigoCliente);

			List<BeanCondicionPago> lista = condiciones.listarCondicionesPagoPorCliente(condicionPagoClienteXDefecto);
			for (BeanCondicionPago beanCondicionPago : lista) {
				String[] strings = new String[2];
				strings[0] = beanCondicionPago.getTxtZTERM();
				strings[1] = beanCondicionPago.getTxtVTEXT();
				if (strings[0].compareTo(condicionPago) == 0) {
					descripcionCondicionPago = strings[1];
				}
				modelCondiciones.addRow(strings);
			}
		} catch (Exception exec) {
			Util.mostrarExcepcion(exec);
		}
	}

	protected abstract void llenarTablaBloqueosEntrega();

	protected void seleccionarDestinatario(MouseEvent evt) {
		int fila = 0;
		fila = tablaSucursales.getSelectedRow();
		popupSucursales.setVisible(false);
		String destinatario = tablaSucursales.getValueAt(fila, 0).toString();
		txtDestinatario.setText(destinatario);
		direccionDestinatario = tablaSucursales.getValueAt(fila, 1).toString();
	}

	protected void seleccionarCondicionPago(MouseEvent evt) {
		int fila = 0;
		fila = tablaCondiciones.getSelectedRow();
		popupCondiciones.setVisible(false);
		String condPago = tablaCondiciones.getValueAt(fila, 0).toString();
		txtCondPago.setText(condPago);
		descripcionCondicionPago = tablaCondiciones.getValueAt(fila, 1).toString();
		condicionPago = tablaCondiciones.getValueAt(fila, 0).toString();
		// Llamar a la simulación
		simularPreciosTodosMateriales();
	}

	protected void seleccionarBloqueoEntrega(MouseEvent evt) {
		int fila = 0;
		fila = tablaBloqueos.getSelectedRow();
		popupBloqueo.setVisible(false);
		String bloq = tablaBloqueos.getValueAt(fila, 0).toString();
		txtBloqEntrega.setText(bloq);
	}

	protected void btnDetalleDestinatarioActionPerformed(java.awt.event.ActionEvent evt) {
		setPopupDestinatarios(evt, txtDestinatario, 0, 21);
	}

	protected void btnDetalleCondPagoActionPerformed(java.awt.event.ActionEvent evt) {
		if (tblPedidos.getCellEditor() != null) {
			tblPedidos.getCellEditor().stopCellEditing();
		}
		setPopupCondiciones(evt, txtCondPago, 0, 21);
	}

	protected void btnDetalleBloqEntregaActionPerformed(ActionEvent evt) {
		setPopupBloqueo(evt, txtBloqEntrega, 0, 21);
	}

	protected void setPopupDestinatarios(ActionEvent evt, JTextField txtDestinatario2, int x, int y) {
		txtDestinatario2.add(popupSucursales);
		popupSucursales.show(txtDestinatario2, x, y);
		txtDestinatario2.requestFocus();
		popupSucursales.setVisible(true);
	}

	protected void setPopupCondiciones(ActionEvent evt, JTextField txtCondPago2, int x, int y) {
		txtCondPago2.add(popupCondiciones);
		popupCondiciones.show(txtCondPago2, x - 400 + txtCondPago2.getWidth(), y);
		txtCondPago2.requestFocus();
		popupCondiciones.setVisible(true);
	}

	protected void setPopupBloqueo(java.awt.event.ActionEvent evt, JTextField txtf, int x, int y) {
		txtf.add(popupBloqueo);
		popupBloqueo.show(txtf, x, y);
		txtf.requestFocus();
		popupBloqueo.setVisible(true);
	}

	protected void setAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblPedidos.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblPedidos.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 20;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
			case 2:
			case 3:
			case 6:
				anchoColumna = 65;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 45;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 35;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 75;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
			case 8:
				anchoColumna = 200;
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
			case 10:
				anchoColumna = 75;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 11:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}

	protected boolean noHayPedidosMezclados() {
		List<Item> items = mdlTblItems.obtenerTodosItems();
		if (items.isEmpty()) {
			return false;
		} else if (items.size() == 1) {
			return true;
		} else {
			for (int i = 1; i < items.size(); i++) {
				Item it1 = items.get(i);
				Item it2 = items.get(i - 1);
				if ((!it1.getCodigo().isEmpty() && !it2.getCodigo().isEmpty())
						&& it1.getTipoMaterial().compareTo(it2.getTipoMaterial()) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	protected void cotizar() {
	}

	protected void guardar() {
		guardarPedidoSQLite();
	}

	protected void btnGuardarOrdenActionPerformed(java.awt.event.ActionEvent evt) {
		if (tblPedidos.getCellEditor() != null) {
			tblPedidos.getCellEditor().stopCellEditing();
		}

		finalizarTransaccion();
		// guardarPresupuesto();
//		calcularMarcasEstrategicas();
	}

	protected void btnConsultaCapturaDinamicaActionPerformed(java.awt.event.ActionEvent evt) {
		consultaDinamica(0);
	}

	protected void btnGuiaVentasActionPerformed(java.awt.event.ActionEvent evt) {
		guiaVentas();
	}

	protected void btnAniadirPedidoActionPerformed(ActionEvent evt) {
		for (int i = 0; i < 12; i++) {
			agregarItemVacio(-1);
		}
		mdlTblItems.actualizarPosiciones();
	}

	public void agregarItemVacio(int pos) {
		Item item = new Item();
		item.setTipoMaterial("");
		item.setCodigo("");
		item.setCantidad("0");
		item.setCantidadConfirmada("0");
		item.setUnidad("");
		item.setPorcentajeDescuento("0.0");
		item.setDenominacion("");
		item.setPrecioNeto(new Double(0d));
		item.setValorNeto(new Double(0d));
		item.setEditable(true);
		item.setTipo(0); // Indica que no hay ning�n item
		item.setSimulado(true);
		item.setStrFech_Ing("");
		mdlTblItems.agregarItem(item, pos);
	}

	protected void enmascararEventoTeclaTab() {
		tblPedidos.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblPedidos.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblPedidos.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
		tblPedidos.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0),
				"tab");
		((InputMap) UIManager.get("Table.ancestorInputMap")).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");

		tblPedidos.getActionMap().put("tab", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int fila = tblPedidos.getSelectedRow();
				if (mdlTblItems.esHijo(fila)) {
					int numeroFilas = mdlTblItems.getRowCount();
					if (fila < numeroFilas - 1) {
						fila++;
						selectionModel.setSelectionInterval(fila, fila);
						tblPedidos.editCellAt(fila, 1);
						textFieldCodigo.requestFocusInWindow();
						textFieldCodigo.setEditable(true);
						textFieldCodigo.selectAll();
					}
				}
			}
		});
	}

	protected void establecerOyenteTeclaTab() {
		textFieldCodigo = new JTextField();
		textFieldCantidad = new JTextField();
		textFieldPorcentajeDescuento = new JTextField();

		textFieldCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		textFieldCantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		textFieldPorcentajeDescuento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

		ValidadorEntradasDeTexto validadorCodigos = new ValidadorEntradasDeTexto(10, Constante.ENTEROS);
		TableColumn columnaCodigo = tblPedidos.getColumnModel().getColumn(1);
		textFieldCodigo.setDocument(validadorCodigos);
		textFieldCodigo.addFocusListener(this);
		textFieldCodigo.setBorder(BorderFactory.createEmptyBorder());
		DefaultCellEditor singleclickCodigo = new DefaultCellEditor(textFieldCodigo);
		singleclickCodigo.setClickCountToStart(1);
		columnaCodigo.setCellEditor(singleclickCodigo);

		ValidadorEntradasDeTexto validadorCantidades = new ValidadorEntradasDeTexto(10, Constante.ENTEROS);
		TableColumn columnaCantidad = tblPedidos.getColumnModel().getColumn(2);
		textFieldCantidad.setDocument(validadorCantidades);
		textFieldCantidad.addFocusListener(this);
		textFieldCantidad.setBorder(BorderFactory.createEmptyBorder());
		DefaultCellEditor singleclickCantidad = new DefaultCellEditor(textFieldCantidad);
		singleclickCantidad.setClickCountToStart(1);
		columnaCantidad.setCellEditor(singleclickCantidad);
		
		ValidadorEntradasDeTexto validadorPorcentajes = new ValidadorEntradasDeTexto(10, Constante.DECIMALES);
		TableColumn columnaPorcentaje = tblPedidos.getColumnModel().getColumn(6);
		textFieldPorcentajeDescuento.setDocument(validadorPorcentajes);
		textFieldPorcentajeDescuento.addFocusListener(this);
		textFieldPorcentajeDescuento.setBorder(BorderFactory.createEmptyBorder());
		DefaultCellEditor singleclickPorcentaje = new DefaultCellEditor(textFieldPorcentajeDescuento);
		singleclickPorcentaje.setClickCountToStart(1);
		columnaPorcentaje.setCellEditor(singleclickPorcentaje);

		KeyListener keyListenerMaterial = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_TAB || keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					// AGREGAMOS UN MATERIAL
					agregarMaterialPorEventoTab();
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		};

		KeyListener keyListenerCantidad = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				// Agregar Metodo Tab
				int fila = tblPedidos.getSelectedRow();
				List<BeanMaterial> datos = new ArrayList();
				List<Integer> posi = new ArrayList();
				if (keyEvent.getKeyCode() == KeyEvent.VK_TAB || keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					datos = cargardatosdetabla();
					String content = (String) tblPedidos.getValueAt(fila, 1);
					SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
					BeanMaterial mat = sqlMateriales.getMaterial(content);
					int pos = posicion(datos,content);
					if(pos>-1){
						datos.remove(pos);
					}
					
					agregarMaterialPorEventoTab01();
					
				}

				if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
					tblPedidos.editCellAt(fila, 6);					
					textFieldPorcentajeDescuento.requestFocusInWindow();
					textFieldPorcentajeDescuento.setEditable(true);
					textFieldPorcentajeDescuento.selectAll();
					mdlTblItems.asociarItemsYPrecios();
					mdlTblItems.actualizarEstadoSimulado(fila, false);
					selectionModel.setSelectionInterval(fila, fila);
					for(BeanMaterial m:datos){
						int o = posiciondisponible();
						tblPedidos.setValueAt(m.getIdMaterial(), o, 1);
					}
					tblPedidos.updateUI();
				} else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					
					mdlTblItems.actualizarEstadoSimulado(fila, false);
					tblPedidos.getCellEditor().stopCellEditing();
					mdlTblItems.asociarItemsYPrecios();
					tblPedidos.updateUI();
					simularPreciosMateriales();
					
					for(BeanMaterial m:datos){
						int o = posiciondisponible();
						tblPedidos.setValueAt(m.getIdMaterial(), o, 1);					
					}
					tblPedidos.updateUI();
					txtValorNeto.setText("" + Util.formatearNumero(mdlTblItems.getValorNeto()));
					calcularSaldoPendiente();
					establecerCursorPrimeraPosicionLibre();					
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		};
		
		KeyListener keyListenerPorcentaje = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				int fila = tblPedidos.getSelectedRow();
				int posicionFinal = mdlTblItems.getRowCount() - 1;
				if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
					mdlTblItems.actualizarEstadoSimulado(fila, false);
					if (fila < posicionFinal) {
						fila++;
					} else if (fila == posicionFinal) {
						fila = 0;
					}
					selectionModel.setSelectionInterval(fila, fila);
					tblPedidos.editCellAt(fila, 1);
					textFieldCodigo.requestFocusInWindow();
					textFieldCodigo.selectAll();
					textFieldCodigo.setEditable(true);
				} else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					mdlTblItems.actualizarEstadoSimulado(fila, false);
					tblPedidos.getCellEditor().stopCellEditing();
					mdlTblItems.asociarItemsYPrecios();
					simularPreciosMateriales();
					tblPedidos.updateUI();
					txtValorNeto.setText("" + Util.formatearNumero(mdlTblItems.getValorNeto()));
					calcularSaldoPendiente();
					establecerCursorPrimeraPosicionLibre();
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		};
		

		//textFieldCodigo.addFocusListener(focusListenerCodigo);
		textFieldCodigo.addKeyListener(keyListenerMaterial);
		textFieldCantidad.addKeyListener(keyListenerCantidad);
		//textFieldCantidad.addFocusListener(focusListenerCantidad);
		textFieldPorcentajeDescuento.addKeyListener(keyListenerPorcentaje);
	}
	public int posiciondisponible(){
		int j = 0;
		for(int i=0;i<tblPedidos.getRowCount();i++){
			if(((String)tblPedidos.getValueAt(i, 1)).equals(""))
			return i;
		}
		return j;
	}
	public int posicion(List<BeanMaterial> datos,String codigo){
		int p=-1;
		for(int i=0;i<datos.size();i++){
			if(datos.get(i).getIdMaterial().equals(codigo)){
				return i;
			}
				
		}
		return p;
	}
	public List<BeanMaterial> cargardatosdetabla(){
		List<BeanMaterial> datos = new ArrayList();
		for(int i=0;i<tblPedidos.getRowCount();i++){

			String content = (String)tblPedidos.getValueAt(i, 1);
			if(!content.equals("")){
				SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
				BeanMaterial mat = sqlMateriales.getMaterial(content);
				if(mat!=null&&!yaestaenlista(datos,content)&&((String)tblPedidos.getValueAt(i, 8)).equals("")){
					//mat.setStock((String)tblPedidos.getValueAt(i, 2));
					datos.add(mat);
					if(tblPedidos.getSelectedRow()!=i)
					tblPedidos.setValueAt("", i, 1);
				}
			}
		}
		return datos;
	}
	public boolean yaestaenlista(List<BeanMaterial> datos, String codigo){
		boolean t= false;
		for(BeanMaterial mat:datos){
			if(mat.getIdMaterial().equals(codigo)){
				return true;
			}
		}
		return t;
	}
	public void agregarmaterialretenido(){
		List<BeanMaterial> datos = cargardatosdetabla();
		for(int i = 0;i<datos.size();i++){
			if (!mdlTblItems.yaEstaAgregado4(datos.get(i).getIdMaterial(),1)) {
				BeanMaterial mat = datos.get(i);
				SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
				SqlComboImpl sqlCombos = new SqlComboImpl();
				boolean vacio = false;
				if (mat != null && (mat.getMtart().equals("HAWA") || mat.getMtart().equals("ZCOM"))) {
					Item material = new Item();
					material.setDenominacion(sqlMateriales.obtenerNombreMaterial(mat.getIdMaterial()));
					material.setPosicion(mdlTblItems.getValueAt(mdlTblItems.posicionDisponible(), 0).toString());
					material.setCodigo(mat.getIdMaterial());
					material.setUnidad(mat.getUn());
					material.setCantidad(mat.getStock());
					material.setCantidadConfirmada("0");
					material.setPorcentajeDescuento("0.00");
					material.setPrecioNeto(mdlTblItems.obtenerItem(mdlTblItems.posicionDisponible()).getPrecioNeto());
					material.setValorNeto(mdlTblItems.obtenerItem(mdlTblItems.posicionDisponible()).getValorNeto());
					material.setTipo(0); // Item normal
					material.setEditable(true);

					String tipoMaterial = mat.getTypeMat();
					SqlClienteImpl sql = new SqlClienteImpl();
					String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
					if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
						tipoMaterial = "B";
					} else if (tipoMaterial.compareTo("N") == 0
							|| (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
						tipoMaterial = "N";
					} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
						tipoMaterial = "P";
					} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
						tipoMaterial = "R";
					}
					material.setTipoMaterial(tipoMaterial);
					material.setSimulado(false);
					mdlTblItems.agregarItem(material, mdlTblItems.posicionDisponible());
					if(!arrMarca.contains(mat.getNormt())){
						arrMarca.add(mat.getNormt());
					agregarMaterialNuevo(mat.getNormt(),tempfila1,mat.getIdMaterial());
					}
					tblPedidos.updateUI();
				} else {
					Item combo = sqlCombos.getCombo(mat.getIdMaterial());
					if (combo != null) {
						if (!mdlTblItems.existeCombo(mat.getIdMaterial())) {
							combo.setCantidad("1");
							combo.setCantidadConfirmada(mdlTblItems.getValueAt(mdlTblItems.posicionDisponible(), 3).toString());
							combo.setPorcentajeDescuento(mdlTblItems.getValueAt(mdlTblItems.posicionDisponible(), 6).toString());
							combo.setEditable(true);
							combo.setSimulado(true);
							mdlTblItems.agregarItem(combo, mdlTblItems.posicionDisponible());
							tblPedidos.updateUI();
						}
					} else {
						if(mat!=null){
							String strMensaje = "<html><font color='red'>El material "+mat.getIdMaterial()+", tipo "+mat.getMtart()+" no permitido</font></html>";
							MostarMensaje(strMensaje, "Materiales no permitidos");
						}else{
							vacio = true;
						}
					}
				}
			}
		}
	}
	protected void guiaVentas() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				boolean fallo = false;
				try {
					DHistoricoCliente objHC = null;
					int fila = tblPedidos.getSelectedRow();
					String codigoMaterial = "";
					Item itemSeleccionado = mdlTblItems.obtenerItem(fila);
					if (fila > -1) {
						codigoMaterial = itemSeleccionado.getCodigo().trim();
					}
					objHC = new DHistoricoCliente(instancia, codigoCliente, nombreCliente, codigoMaterial);
					objHC.setVisible(true);
				} catch (Exception e) {
					fallo = true;
					Util.mostrarExcepcion(e);
				} finally {
					bloqueador.dispose();
					if (!fallo) {
						if (tblPedidos.getCellEditor() != null) {
							tblPedidos.getCellEditor().stopCellEditing();
						}
					}
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	protected void simularPreciosMateriales() {
		List<Item> lista = mdlTblItems.obtenerTodosItems();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getCantidad().equals("0")&&!lista.get(i).getCodigo().equals("")&&!lista.get(i).isEsmaterialnuevo()){
				lista.remove(i);
				i--;
			}
		}
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {// DESDE SAP
			simularPreciosDeMaterialesSAP(false);
		} else {
			flag = true;
			simulaPreciosDeMaterialesSQLite2();
		}
//		calcularMarcasEstrategicas();
		hiloCalulador();
	}

	protected void simularPreciosTodosMateriales() {
		if (tblPedidos.getCellEditor() != null) {
			tblPedidos.getCellEditor().stopCellEditing();
		}
		List<Item> lista = mdlTblItems.obtenerTodosItems();
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getCantidad().equals("0")&&!lista.get(i).getCodigo().equals("")){
				lista.remove(i);
				i--;
			}
		}
		BeanDato usuario = Promesa.datose.get(0);
		lblWarnigs.setText("");
		if (usuario.getStrModo().equals("1")) {// DESDE SAP
			simularPreciosDeTodosMaterialesSAP(true);
		} else {
			flag = true;
			simulaPreciosDeMaterialesSQLite2();
		}
		txtValorNeto.setText("" + Util.formatearNumero(mdlTblItems.getValorNeto()));
		hiloCalulador();
	}

	private void calcularMarcasEstrategicas() {
		BeanPedido pedido = llenarEstructuraParaSimulacionTotal();
		List<BeanPedidoDetalle> detalles = pedido.getDetalles();
		IMarcaEspecifica.getIinstance().inicializarValoresVentaHoy();
		for (BeanPedidoDetalle detalle : detalles) {
			String prdha = Factory.createSqlMaterial().getMaterialMarcaEstrategica(Util.eliminarCerosInicios(detalle.getMaterial()));
			if (Factory.createSqlMarcaEstrategica().perteneceAMarcaEstrategica(Util.completarCeros(10, codigoCliente),prdha)) {
				IMarcaEspecifica.getIinstance().actualizarValores(prdha, Double.parseDouble(detalle.getValorNeto()));
			}
		}
		//IMarcaEspecifica.getIinstance().actualizarCampoRealIndicador();
	}

	private void guardarMarcasEstrategicas() {
		List<MarcaEstrategica> marcas = IMarcaEspecifica.getIinstance().getDatosMarcaEstrategica();
		Factory.createSqlMarcaEstrategica().actualizarMarcaEstrategica(marcas);
		IMarcaEspecifica.getIinstance().clearCamposMarcaEstrategica();
		//IMarcaEspecifica.getIinstance().clearCamposIndicadores();
		Promesa.getInstance().setVentanaActiva(false);
		this.instance = null;
		Promesa.getInstance().regresarAMenu();
	}
	
	public void hiloCalulador(){
		final DLocker bloqueador = new DLocker();
		class worker extends SwingWorker<Void,Void>{
			@Override
			protected Void doInBackground() throws Exception {
				try {					
					calcularSaldoPendiente();
					calcularMarcasEstrategicas();
				}catch(Exception e) {
					
				}finally {
					bloqueador.dispose();
				}
				return null;
			}
		}
		new worker().execute();
		bloqueador.setVisible(true);
	}

	protected void simularPreciosDeTodosMaterialesSAP(boolean t) {
		final DLocker bloqueador = new DLocker();
		class worker extends SwingWorker<Void,Void>{

			@Override
			protected Void doInBackground() throws Exception {
				try {
					List<Item> lista = mdlTblItems.obtenerTodosItems();
					for(int i=0;i<lista.size();i++){
						if(lista.get(i).getCantidad().equals("0")&&!lista.get(i).getCodigo().equals("")){
							lista.remove(i);
							i--;
						}
					}
					BeanDato usuario = Promesa.datose.get(0);
					BeanPedido pedido = llenarEstructuraParaSimulacionTotal();
					if (usuario.getStrModo().equals("1")) {// DESDE SAP
						SPedidos objSAP = new SPedidos();						
						try {
							Promesa.getInstance().mostrarAvisoSincronizacion("Accediendo a SAP");
							List<String[]> items = objSAP.obtenerItemsSimulados(pedido);
							if (items != null && !items.isEmpty()) {
								llenarTabla(items);
							}
							Promesa.getInstance().mostrarAvisoSincronizacion("");
						} catch (Exception e) {
							Mensaje.mostrarError(e.getMessage());
							bloqueador.dispose();
						}
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					bloqueador.dispose();
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							mdlTblItems.asociarItemsYPrecios();
							Double valorNeto = mdlTblItems.getValorNeto();
							String valor = Util.formatearNumero(valorNeto);
							txtValorNeto.setText(valor);
							bloqueador.dispose();
							tblPedidos.updateUI();
							tblPedidos.clearSelection();
						}
					});
				}
				return null;
			}
			
		}
		new worker().execute();
		bloqueador.setVisible(true);
	}

	protected BeanMensaje editarPedidoSAP(BeanPedido pedido) {
		BeanMensaje msg = new BeanMensaje("");
		SPedidos objSAP = new SPedidos();
		try {
			String mensaje[] = objSAP.editarOrden(pedido);
			if (mensaje != null) {
				Long codigo = 0l;
				try {
					codigo = Long.parseLong(mensaje[2]);
				} catch (NumberFormatException e) {
					Util.mostrarExcepcion(e);
					codigo = -1l;
				}
				guardarMarcasEstrategicas();
				msg.setIdentificador(codigo);
				msg.setDescripcion(mensaje[1]);
				msg.setTipo(mensaje[0]);
			}
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
		}
		return msg;
	}

	protected BeanMensaje editarPedidoSQLite(BeanPedido pedido) {
		BeanMensaje msg = new BeanMensaje("");
		for (int i = 0; i < pedido.getDetalles().size(); i++) {
			BeanPedidoDetalle detalle = pedido.getDetalles().get(i);
			int posicion = (i + 1) * 10;
			detalle.setPosicion(Util.completarCeros(6, "" + posicion));
		}
		SqlPedidoImpl sqlPedido = new SqlPedidoImpl();
		try {
			String mensaje = sqlPedido.actualizaPedido(pedido);
			if (mensaje != null) {
				Long codigo = 0l;
				try {
					codigo = Long.parseLong(mensaje);
				} catch (NumberFormatException e) {
					Util.mostrarExcepcion(e);
					codigo = -1l;
				}
				msg.setIdentificador(codigo);
				msg.setDescripcion("El pedido se actualizó correctamente.");
				msg.setTipo("N");
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return msg;
	}

	protected void establecerCursorPrimeraPosicionLibre() {
		class worker extends SwingWorker<Void,Void>{
			@Override
			protected Void doInBackground() throws Exception {
				try {					
					int numeroFilas = tblPedidos.getRowCount();
					for (int i = 0; i < numeroFilas; i++) {
						String codigo = tblPedidos.getValueAt(i, 1).toString();
						if (codigo.isEmpty()) {
							selectionModel.setSelectionInterval(i, i);
							tblPedidos.editCellAt(i, 1);
							textFieldCodigo.requestFocusInWindow();
							textFieldCodigo.selectAll();
							textFieldCodigo.setEditable(true);
							break;
						}
					}
				}catch(Exception e) {
					
				}finally {
					
				}
				return null;
			}
		}
		new worker().execute();
	}

	protected List<BeanPedido> llenarEstructuraParaCreacionEnSAP() {
		List<Item> materiales = mdlTblItems.obtenerTodosItemsConfirmados();
		if (materiales == null || materiales.isEmpty()) {
			return null;
		}
		BeanDato usuario = Promesa.datose.get(0);
		BeanCondicionExpedicion ce = (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
		BeanTipoGestion tg = (BeanTipoGestion) cmbTipoGestion.getSelectedItem();
		// Segmentamos las �rdenes de acuerdo al tipo de materiales
		List<BeanPedido> pedidos = new ArrayList<BeanPedido>();
		BeanPedidoHeader header = new BeanPedidoHeader();
		BeanPedidoPartners partners = new BeanPedidoPartners();
		String SHIP_COND = ce.getStrIdCondicionExpedicion();
		String PO_METHOD = tg.getBsar();
		java.util.Date fechaActual = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		String fecha = sdf.format(fechaActual);
		String bloqueoEntrega = txtBloqEntrega.getText();
		String DOC_TYPE = tipoDocumento;
		String SALES_ORG = organizacionVentas;
		String DISTR_CHAN = canalDistribucion;
		String DIVISION = codigoSector;

		String SALES_GRP = "";
		String SALES_OFF = "";

		SqlClienteImpl sqlC = new SqlClienteImpl();
		BeanCliente cliente = sqlC.buscarCliente(codigoCliente);

		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}
		if ((SALES_ORG == null || SALES_ORG.isEmpty()) || (DISTR_CHAN == null || DISTR_CHAN.isEmpty())
				|| (DIVISION == null || DIVISION.isEmpty())) {
			SALES_ORG = cliente.getStrCodOrgVentas();
			DISTR_CHAN = cliente.getStrCodCanalDist();
			DIVISION = cliente.getStrCodSector();
		}

		String date = fecha;
		@SuppressWarnings("unused")
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = txtNPedCliente.getText();
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		BeanConexion cn = new BeanConexion();
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
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setSHIP_COND(SHIP_COND);
		header.setPO_METHOD(PO_METHOD);
		header.setStrValorNeto(txtValorNeto.getText());
		header.setStrCodCliente(codigoCliente);
		header.setStrCliente(nombreCliente);
		header.setStrCodVendedor(usuario.getStrCodigo());
		header.setTxtClaseRiesgo(txtClasRiesgo.getText());
		header.setTxtDisponible(txtDisponible.getText());
		header.setTxtLimiteCredito(txtLimCredito.getText().replaceAll(",", ""));
		header.setSource(0);
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));
		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		List<BeanPedidoDetalle> materialesBodega = new ArrayList<BeanPedidoDetalle>();
		List<BeanPedidoDetalle> materialesNormal = new ArrayList<BeanPedidoDetalle>();
		List<BeanPedidoDetalle> materialesPesado = new ArrayList<BeanPedidoDetalle>();
		List<BeanPedidoDetalle> materialesRojos = new ArrayList<BeanPedidoDetalle>();
		int fila = 1;
		for (Item item : materiales) {
			String posicion = item.getPosicion();
			String material = item.getCodigo();
			String cantidad = item.getCantidad();
			SqlMaterial sqlM = new SqlMaterialImpl();
			BeanMaterial bm = sqlM.getMaterial(material);
			if (!mdlTblItems.esHijo(fila) && bm.getPrice_1().equals("0.0")) {
				Mensaje.mostrarWarning("El material " + material + " de la posicion " + item.getPosicion()
						+ " tiene Precio Lista = 0.");
				return null;
			}
			String cantidadConfirmada = item.getCantidadConfirmada();
			String um = item.getUnidad();
			String porcentaje = item.getPorcentajeDescuento();
			String denominacion = item.getDenominacion();
			String precioNeto = "" + item.getPrecioNeto();
			String valorNeto = "" + item.getValorNeto();
			String tipoMaterial = item.getTipoMaterial();
			int tipo = item.getTipo();
			int cantidadPedidaItem = 0;
			try {
				cantidadPedidaItem = Integer.parseInt(item.getCantidad());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				cantidadPedidaItem = 0;
			}
			if (cantidadPedidaItem > 0 && (tipo == 0 || tipo == 1)) {
				if (!item.getCodigo().trim().isEmpty()) {
					BeanPedidoDetalle detalle = new BeanPedidoDetalle();
					detalle.setPosicion(posicion);
					detalle.setMaterial(material);
					detalle.setCantidad(cantidad);
					detalle.setCantidadConfirmada(cantidadConfirmada);
					detalle.setUM(um);
					detalle.setPorcentajeDescuento(porcentaje);
					detalle.setDenominacion(denominacion);
					detalle.setPrecioNeto(precioNeto);
					detalle.setValorNeto(valorNeto);
					SqlClienteImpl sql = new SqlClienteImpl();
					String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
					if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
						materialesBodega.add(detalle);
					} else if (tipoMaterial.compareTo("N") == 0
							|| (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
						materialesNormal.add(detalle);
					} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
						materialesPesado.add(detalle);
					} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
						materialesRojos.add(detalle);
					}
				}
			} else if (cantidadPedidaItem <= 0) {
				Mensaje.mostrarWarning(
						"El material " + material + " de la posicion " + posicion + " tiene cantidad = 0.");
				return null;
			}
		}
		BeanPedido pedidoBodega = new BeanPedido();
		BeanPedido pedidoNormal = new BeanPedido();
		BeanPedido pedidoPesado = new BeanPedido();
		BeanPedido pedidoRojo = new BeanPedido();

		int secuencia = Factory.createSqlSecuenciaPedido().getSecuencialPedido(usuario.getStrCodigo()) + 1;
		header.setSecuencialPedido(secuencia);
		Factory.createSqlSecuenciaPedido().actualizarSecuencialPedido(usuario.getStrCodigo(), secuencia);

		pedidoBodega.setTipo("B");
		pedidoNormal.setTipo("N");
		pedidoPesado.setTipo("P");
		pedidoRojo.setTipo("R");

		pedidoBodega.setHeader(header);
		pedidoBodega.setPartners(partners);
		pedidoBodega.setDetalles(materialesBodega);

		pedidoNormal.setHeader(header);
		pedidoNormal.setPartners(partners);
		pedidoNormal.setDetalles(materialesNormal);

		pedidoPesado.setHeader(header);
		pedidoPesado.setPartners(partners);
		pedidoPesado.setDetalles(materialesPesado);

		pedidoRojo.setHeader(header);
		pedidoRojo.setPartners(partners);
		pedidoRojo.setDetalles(materialesRojos);

		pedidos.add(pedidoBodega);
		pedidos.add(pedidoNormal);
		pedidos.add(pedidoPesado);
		pedidos.add(pedidoRojo);
		return pedidos;
	}

	protected List<BeanPedido> llenarEstructuraParaCreacionEnSQLite() {
		List<Item> materiales = mdlTblItems.obtenerTodosItemsConfirmadosSQLite();
		if (materiales == null || materiales.isEmpty()) {
			return null;
		}
		BeanDato usuario = Promesa.datose.get(0);
		BeanCondicionExpedicion ce = (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
		BeanTipoGestion tg = (BeanTipoGestion) cmbTipoGestion.getSelectedItem();
		// Segmentamos las �rdenes de acuerdo al tipo de materiales
		List<BeanPedido> pedidos = new ArrayList<BeanPedido>();
		BeanPedidoHeader header = new BeanPedidoHeader();
		BeanPedidoPartners partners = new BeanPedidoPartners();
		String SHIP_COND = ce.getStrIdCondicionExpedicion();
		String PO_METHOD = tg.getBsar();
		java.util.Date fechaActual = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fecha = sdf.format(fechaActual);
		String bloqueoEntrega = txtBloqEntrega.getText();
		String DOC_TYPE = tipoDocumento;
		String SALES_ORG = organizacionVentas;
		String DISTR_CHAN = canalDistribucion;
		String DIVISION = codigoSector;

		String SALES_GRP = "";
		String SALES_OFF = "";
		
		SqlClienteImpl sqlC = new SqlClienteImpl();
		BeanCliente cliente = sqlC.buscarCliente(codigoCliente);
		
		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}

		String date = fecha;
		@SuppressWarnings("unused")
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = txtNPedCliente.getText();
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		BeanConexion cn = new BeanConexion();
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
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setSHIP_COND(SHIP_COND);
		header.setPO_METHOD(PO_METHOD);
		header.setStrValorNeto(txtValorNeto.getText());
		header.setStrCodCliente(codigoCliente);
		header.setStrCliente(nombreCliente);
		header.setStrCodVendedor(usuario.getStrCodigo());
		header.setTxtClaseRiesgo(txtClasRiesgo.getText());
		header.setTxtDisponible(txtDisponible.getText());
		header.setTxtLimiteCredito(txtLimCredito.getText().replaceAll(",", ""));
		header.setStrDocumentoReferencia("");
		header.setStrFechaReferencia("");
		if (source != null) {
			if ((source.compareTo("0") == 0) && usuario.getStrModo().equals("0")) {
				header.setSource(Constante.FROM_SAP_OFFLINE);
			} else {
				header.setSource(Constante.FROM_DB);
			}
		} else {
			header.setSource(Constante.FROM_DB);
		}
		/**
		 * PARTNERS
		 */
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		List<BeanPedidoDetalle> materialesBodega = new ArrayList<BeanPedidoDetalle>();
		List<BeanPedidoDetalle> materialesNormal = new ArrayList<BeanPedidoDetalle>();
		List<BeanPedidoDetalle> materialesPesado = new ArrayList<BeanPedidoDetalle>();
		List<BeanPedidoDetalle> materialesRojos = new ArrayList<BeanPedidoDetalle>();

		Double valorNetoBodega = 0d;
		Double valorNetoNormal = 0d;
		Double valorNetoPesado = 0d;
		Double valorNetoRojos = 0d;
		int fila = 1;
		for (Item item : materiales) {
			String posicion = item.getPosicion();
			String material = item.getCodigo();
			SqlMaterial sqlM = new SqlMaterialImpl();
			BeanMaterial bm = sqlM.getMaterial(material);
			if (!mdlTblItems.esHijo(fila) && bm.getPrice_1().equals("0.0")) {
				Mensaje.mostrarWarning("El material " + material + " de la posicion " + item.getPosicion()
						+ " tiene Precio Lista = 0.");
				return null;
			}
			String cantidad = item.getCantidad();
			Double dCantidad = Double.parseDouble(cantidad);
			cantidad = String.valueOf(dCantidad);
			String cantidadConfirmada = item.getCantidadConfirmada();
			String um = item.getUnidad();
			String porcentaje = item.getPorcentajeDescuento();
			String denominacion = item.getDenominacion();
			String precioNeto = "" + item.getPrecioNeto();
			String valorNeto = "" + item.getValorNeto();
			String tipoMaterial = item.getTipoMaterial();
			int tipo = item.getTipo();
			int cantidadPedidaItem = 0;
			double vl = 0d;
			try {
				cantidadPedidaItem = Integer.parseInt(item.getCantidad());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				cantidadPedidaItem = 0;
			}
			try {
				vl = Double.parseDouble(valorNeto);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				vl = 0d;
			}
			// if (cantidadPedidaItem > 0 && (tipo == 0 || tipo == 1)) {
			// if (cantidadPedidaItem > 0 && tipo != 2 ) {
			if (cantidadPedidaItem > 0) {
				if (!item.getCodigo().trim().isEmpty()) {
					BeanPedidoDetalle detalle = new BeanPedidoDetalle();
					detalle.setTipo(tipo);
					detalle.setPosicion(posicion);
					detalle.setMaterial(material);
					detalle.setCantidad(cantidad);
					detalle.setCantidadConfirmada(cantidadConfirmada);
					detalle.setUM(um);
					detalle.setPorcentajeDescuento(porcentaje);
					detalle.setDenominacion(denominacion);
					detalle.setPrecioNeto(precioNeto);
					detalle.setValorNeto(valorNeto);
					SqlClienteImpl sql = new SqlClienteImpl();
					String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
					if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
						materialesBodega.add(detalle);
						if (tipo == 0 || tipo == 1) {
							valorNetoBodega += vl;
						}
					} else if (tipoMaterial.compareTo("N") == 0
							|| (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
						materialesNormal.add(detalle);
						if (tipo == 0 || tipo == 1) {
							valorNetoNormal += vl;
						}
					} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
						materialesPesado.add(detalle);
						if (tipo == 0 || tipo == 1) {
							valorNetoPesado += vl;
						}
					} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
						materialesRojos.add(detalle);
						if (tipo == 0 || tipo == 1) {
							valorNetoRojos += vl;
						}
					}
				}
			} else if (cantidadPedidaItem <= 0 && (tipo == 0 || tipo == 1)) {
				Mensaje.mostrarWarning(
						"El material " + material + " de la posicion " + posicion + " tiene cantidad = 0.");
				return null;
			}
			fila++;
		}
		BeanPedido pedidoBodega = new BeanPedido();
		BeanPedido pedidoNormal = new BeanPedido();
		BeanPedido pedidoPesado = new BeanPedido();
		BeanPedido pedidoRojo = new BeanPedido();

		int secuencialPedido = Factory.createSqlSecuenciaPedido().getSecuencialPedido(usuario.getStrCodigo()) + 1;
		header.setSecuencialPedido(secuencialPedido);
		Factory.createSqlSecuenciaPedido().actualizarSecuencialPedido(usuario.getStrCodigo(), secuencialPedido);

		BeanPedidoHeader hdBodega = (BeanPedidoHeader) header.clone();
		BeanPedidoHeader hdNormal = (BeanPedidoHeader) header.clone();
		BeanPedidoHeader hdPesado = (BeanPedidoHeader) header.clone();
		BeanPedidoHeader hdRojo = (BeanPedidoHeader) header.clone();

		hdBodega.setStrValorNeto("" + valorNetoBodega);
		pedidoBodega.setHeader(hdBodega);
		pedidoBodega.setPartners(partners);
		pedidoBodega.setDetalles(materialesBodega);

		hdNormal.setStrValorNeto("" + valorNetoNormal);
		pedidoNormal.setHeader(hdNormal);
		pedidoNormal.setPartners(partners);
		pedidoNormal.setDetalles(materialesNormal);

		hdPesado.setStrValorNeto("" + valorNetoPesado);
		pedidoPesado.setHeader(hdPesado);
		pedidoPesado.setPartners(partners);
		pedidoPesado.setDetalles(materialesPesado);

		hdRojo.setStrValorNeto("" + valorNetoRojos);
		pedidoRojo.setHeader(hdRojo);
		pedidoRojo.setPartners(partners);
		pedidoRojo.setDetalles(materialesRojos);

		pedidos.add(pedidoBodega);
		pedidos.add(pedidoNormal);
		pedidos.add(pedidoPesado);
		pedidos.add(pedidoRojo);

		return pedidos;
	}

	protected BeanPedido llenarEstructuraParaSimulacionTotal() {
		BeanDato usuario = Promesa.datose.get(0);
		BeanCondicionExpedicion ce = (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
		String SHIP_COND = ce.getStrIdCondicionExpedicion();
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
		String bloqueoEntrega = txtBloqEntrega.getText();
		String DOC_TYPE;
		if (tipoDocumento.compareTo("ZO01") == 0) {
			DOC_TYPE = "ZP01";
		} else {
			DOC_TYPE = tipoDocumento;
		}
		String SALES_ORG = organizacionVentas;
		String DISTR_CHAN = canalDistribucion;
		String DIVISION = codigoSector;

		String SALES_GRP = "";
		String SALES_OFF = "";

		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(codigoCliente);

		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}

		String date = fecha;
		@SuppressWarnings("unused")
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = txtNPedCliente.getText();
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		BeanConexion cn = new BeanConexion();
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
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setSHIP_COND(SHIP_COND);
		header.setStrValorNeto(txtValorNeto.getText());
		header.setStrCodCliente(codigoCliente);
		header.setStrCliente(nombreCliente);
		header.setStrCodVendedor(usuario.getStrCodigo());
		header.setTxtClaseRiesgo(txtClasRiesgo.getText());
		header.setTxtDisponible(txtDisponible.getText());
		header.setTxtLimiteCredito(txtLimCredito.getText().replaceAll(",", ""));
		/**
		 * PARTNERS
		 */
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));
		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		// mdlTblItems.actualizarDescuentoCombo();
		List<Item> materiales = mdlTblItems.obtenerTodosItems();
		detalles = new ArrayList<BeanPedidoDetalle>();
		for (Item item : materiales) {
			String posicion = item.getPosicion();
			String material = item.getCodigo();
			String cantidad = item.getCantidad();
			String cantidadConfirmada = item.getCantidadConfirmada();
			String um = item.getUnidad();
			String porcentaje = item.getPorcentajeDescuento();
			String denominacion = item.getDenominacion();
			String precioNeto = "" + item.getPrecioNeto();
			String valorNeto = "" + item.getValorNeto();
			int tipo = item.getTipo();
			if (tipo == 0 || tipo == 1) {
				if (!item.getCodigo().trim().isEmpty()) {
					BeanPedidoDetalle detalle = new BeanPedidoDetalle();
					detalle.setPosicion(posicion);
					detalle.setMaterial(Util.completarCeros(18, material));
					detalle.setCantidad(cantidad);
					detalle.setCantidadConfirmada(cantidadConfirmada);
					detalle.setUM(um);
					detalle.setPorcentajeDescuento(porcentaje);
					detalle.setDenominacion(denominacion);
					detalle.setPrecioNeto(precioNeto);
					detalle.setValorNeto(valorNeto);
					detalles.add(detalle);
				}
			}
		}
		pedido.setHeader(header);
		pedido.setPartners(partners);
		pedido.setDetalles(detalles);
		return pedido;
	}

	protected BeanPedido llenarEstructuraParaSimulacion() {
		BeanDato usuario = Promesa.datose.get(0);
		BeanCondicionExpedicion ce = (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
		String SHIP_COND = ce.getStrIdCondicionExpedicion();
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
		String bloqueoEntrega = txtBloqEntrega.getText();
		String DOC_TYPE;
		if (tipoDocumento.compareTo("ZO01") == 0) {
			DOC_TYPE = "ZP01";
		} else {
			DOC_TYPE = tipoDocumento;
		}
		String SALES_ORG = organizacionVentas;
		String DISTR_CHAN = canalDistribucion;
		String DIVISION = codigoSector;

		String SALES_GRP = "";
		String SALES_OFF = "";

		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(codigoCliente);

		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}

		String date = fecha;
		@SuppressWarnings("unused")
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = txtNPedCliente.getText();
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		BeanConexion cn = new BeanConexion();
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
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setSHIP_COND(SHIP_COND);
		header.setStrValorNeto(txtValorNeto.getText());
		header.setStrCodCliente(codigoCliente);
		header.setStrCliente(nombreCliente);
		header.setStrCodVendedor(usuario.getStrCodigo());
		header.setTxtClaseRiesgo(txtClasRiesgo.getText());
		header.setTxtDisponible(txtDisponible.getText());
		header.setTxtLimiteCredito(txtLimCredito.getText().replaceAll(",", ""));
		/**
		 * PARTNERS
		 */
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));
		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		List<Item> materiales = mdlTblItems.obtenerTodosItemsNoSimulados();
		detalles = new ArrayList<BeanPedidoDetalle>();
		for (Item item : materiales) {
			if(!item.getCantidad().equals("0")||!item.getCantidad().equals("")){
				String posicion = item.getPosicion();
				String material = item.getCodigo();
				String cantidad = item.getCantidad();
				String cantidadConfirmada = item.getCantidadConfirmada();
				String um = item.getUnidad();
				String porcentaje = item.getPorcentajeDescuento();
				String denominacion = item.getDenominacion();
				String precioNeto = "" + item.getPrecioNeto();
				String valorNeto = "" + item.getValorNeto();
				int tipo = item.getTipo();
				if (tipo == 0 || tipo == 1) {
					if (!item.getCodigo().trim().isEmpty()) {
						BeanPedidoDetalle detalle = new BeanPedidoDetalle();
						detalle.setPosicion(posicion);
						detalle.setMaterial(Util.completarCeros(18, material));
						detalle.setCantidad(cantidad);
						detalle.setCantidadConfirmada(cantidadConfirmada);
						detalle.setUM(um);
						detalle.setPorcentajeDescuento(porcentaje);
						detalle.setDenominacion(denominacion);
						detalle.setPrecioNeto(precioNeto);
						detalle.setValorNeto(valorNeto);
						detalles.add(detalle);
					}
				}
			}
		}
		pedido.setHeader(header);
		pedido.setPartners(partners);
		pedido.setDetalles(detalles);
		return pedido;
	}

	@SuppressWarnings("unused")
	protected BeanPedido llenarEstructuraParaGuardarSQLite() {
		BeanDato usuario = Promesa.datose.get(0);
		BeanCondicionExpedicion ce = (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
		String SHIP_COND = ce.getStrIdCondicionExpedicion();
		BeanPedidoHeader header;
		BeanPedidoPartners partners;
		List<BeanPedidoDetalle> detalles;
		BeanPedido pedido = new BeanPedido();
		pedido = new BeanPedido();
		header = new BeanPedidoHeader();
		partners = new BeanPedidoPartners();
		pedido.setCodigoPedido(txtCodigoPedido.getText());
		Date fechaActual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fecha = sdf.format(fechaActual);
		String bloqueoEntrega = txtBloqEntrega.getText();
		String DOC_TYPE = tipoDocumento;
		String SALES_ORG = organizacionVentas;
		String DISTR_CHAN = canalDistribucion;
		String DIVISION = codigoSector;

		String SALES_GRP = "";
		String SALES_OFF = "";

		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(codigoCliente);

		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}
		if ((SALES_ORG == null || SALES_ORG.isEmpty()) || (DISTR_CHAN == null || DISTR_CHAN.isEmpty())
				|| (DIVISION == null || DIVISION.isEmpty())) {
			SALES_ORG = cliente.getStrCodOrgVentas();
			DISTR_CHAN = cliente.getStrCodCanalDist();
			DIVISION = cliente.getStrCodSector();
		}
		String date = fecha;
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = txtNPedCliente.getText();
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		BeanConexion cn = new BeanConexion();
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
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setSHIP_COND(SHIP_COND);
		header.setStrValorNeto(txtValorNeto.getText());
		header.setStrCodCliente(codigoCliente);
		header.setStrCliente(nombreCliente);
		header.setStrCodVendedor(usuario.getStrCodigo());
		header.setTxtClaseRiesgo(txtClasRiesgo.getText());
		header.setTxtDisponible(txtDisponible.getText());
		header.setTxtLimiteCredito(txtLimCredito.getText().replaceAll(",", ""));
		if ((source.compareTo("0") == 0 && usuario.getStrModo().equals("0")) || source.compareTo("2") == 0) {
			header.setSource(Constante.FROM_SAP_OFFLINE);
		} else {
			header.setSource(Constante.FROM_DB);
		}
		/**
		 * PARTNERS
		 */
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));

		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		List<Item> materiales = mdlTblItems.obtenerTodosItems();
		detalles = new ArrayList<BeanPedidoDetalle>();
		for (Item item : materiales) {
			String posicion = item.getPosicion();
			String material = item.getCodigo();
			String cantidad = item.getCantidad();
			String cantidadAMostrar = "" + item.getCantidadAMostrar();
			String cantidadConfirmada = item.getCantidadConfirmada();
			String um = item.getUnidad();
			String porcentaje = item.getPorcentajeDescuento();
			String denominacion = item.getDenominacion();
			String precioNeto = "" + item.getPrecioNeto();
			String valorNeto = "" + item.getValorNeto();
			int tipo = item.getTipo();
			if (!item.getCodigo().trim().isEmpty()) {
				BeanPedidoDetalle detalle = new BeanPedidoDetalle();
				detalle.setPosicion(posicion);
				detalle.setMaterial(Util.completarCeros(18, material));
				detalle.setCantidad(cantidad);
				// if (tipo == 2) {
				// detalle.setCantidad(cantidadAMostrar);
				// }
				detalle.setCantidadConfirmada(cantidadConfirmada);
				detalle.setUM(um);
				detalle.setPorcentajeDescuento(porcentaje);
				detalle.setDenominacion(denominacion);
				detalle.setPrecioNeto(precioNeto);
				detalle.setValorNeto(valorNeto);
				detalle.setTipo(tipo);
				detalles.add(detalle);
			}
		}
		pedido.setHeader(header);
		pedido.setPartners(partners);
		pedido.setDetalles(detalles);
		return pedido;
	}

	protected BeanPedido llenarEstructuraParaGuardarCotizacion() {
		BeanDato usuario = Promesa.datose.get(0);
		BeanCondicionExpedicion ce = (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
		String SHIP_COND = ce.getStrIdCondicionExpedicion();
		BeanPedidoHeader header;
		BeanPedidoPartners partners;
		List<BeanPedidoDetalle> detalles;
		BeanPedido pedido = new BeanPedido();
		pedido = new BeanPedido();
		header = new BeanPedidoHeader();
		partners = new BeanPedidoPartners();
		pedido.setCodigoPedido(txtCodigoPedido.getText());
		java.util.Date fechaActual = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		String fecha = sdf.format(fechaActual);
		String bloqueoEntrega = txtBloqEntrega.getText();
		String DOC_TYPE = tipoDocumento;
		String SALES_ORG = organizacionVentas;
		String DISTR_CHAN = canalDistribucion;
		String DIVISION = codigoSector;

		String SALES_GRP = "";
		String SALES_OFF = "";

		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(codigoCliente);

		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}
		if ((SALES_ORG == null || SALES_ORG.isEmpty()) || (DISTR_CHAN == null || DISTR_CHAN.isEmpty())
				|| (DIVISION == null || DIVISION.isEmpty())) {
			SALES_ORG = cliente.getStrCodOrgVentas();
			DISTR_CHAN = cliente.getStrCodCanalDist();
			DIVISION = cliente.getStrCodSector();
		}
		String date = fecha;
		@SuppressWarnings("unused")
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = txtNPedCliente.getText();
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		BeanConexion cn = new BeanConexion();
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
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setSHIP_COND(SHIP_COND);
		header.setStrValorNeto(txtValorNeto.getText());
		header.setStrCodCliente(codigoCliente);
		header.setStrCliente(nombreCliente);
		header.setStrCodVendedor(usuario.getStrCodigo());
		header.setTxtClaseRiesgo(txtClasRiesgo.getText());
		header.setTxtDisponible(txtDisponible.getText());
		header.setTxtLimiteCredito(txtLimCredito.getText().replaceAll(",", ""));
		/**
		 * PARTNERS
		 */
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));
		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		List<Item> materiales = mdlTblItems.obtenerTodosItems();
		detalles = new ArrayList<BeanPedidoDetalle>();
		for (Item item : materiales) {
			String posicion = item.getPosicion();
			String material = item.getCodigo();
			String cantidad = item.getCantidad();
			String cantidadConfirmada = item.getCantidadConfirmada();
			String um = item.getUnidad();
			String porcentaje = item.getPorcentajeDescuento();
			String denominacion = item.getDenominacion();
			String precioNeto = "" + item.getPrecioNeto();
			String valorNeto = "" + item.getValorNeto();
			int tipo = item.getTipo();
			if (tipo == 0 || tipo == 1) {
				if (!item.getCodigo().trim().isEmpty()) {
					BeanPedidoDetalle d = new BeanPedidoDetalle();
					d.setPosicion(posicion);
					String codMat = Util.completarCeros(18, material);
					d.setMaterial(codMat);
					d.setCantidad(cantidad);
					d.setCantidadConfirmada(cantidadConfirmada);
					d.setUM(um);
					d.setPorcentajeDescuento(porcentaje);
					d.setDenominacion(denominacion);
					d.setPrecioNeto(precioNeto);
					d.setValorNeto(valorNeto);
					d.setTipo(tipo);
					detalles.add(d);
				}
			}
		}
		pedido.setHeader(header);
		pedido.setPartners(partners);
		pedido.setDetalles(detalles);
		return pedido;
	}

	protected BeanPedido llenarEstructuraParaGuardarActualizacion() {
		BeanDato usuario = Promesa.datose.get(0);
		BeanCondicionExpedicion ce = (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
		String SHIP_COND = ce.getStrIdCondicionExpedicion();
		BeanPedidoHeader header;
		BeanPedidoPartners partners;
		List<BeanPedidoDetalle> detalles;
		BeanPedido pedido = new BeanPedido();
		pedido = new BeanPedido();
		header = new BeanPedidoHeader();
		partners = new BeanPedidoPartners();
		pedido.setCodigoPedido(txtCodigoPedido.getText());
		java.util.Date fechaActual = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		String fecha = sdf.format(fechaActual);
		String bloqueoEntrega = txtBloqEntrega.getText();
		String DOC_TYPE = tipoDocumento;

		String SALES_ORG = organizacionVentas;
		String DISTR_CHAN = canalDistribucion;
		String DIVISION = codigoSector;

		String SALES_GRP = "";
		String SALES_OFF = "";

		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(codigoCliente);

		if (cliente != null) {
			SALES_GRP = cliente.getStrGrupoVentas();
			SALES_OFF = cliente.getStrOficinaVentas();
		}
		if ((SALES_ORG == null || SALES_ORG.isEmpty()) || (DISTR_CHAN == null || DISTR_CHAN.isEmpty())
				|| (DIVISION == null || DIVISION.isEmpty())) {
			SALES_ORG = cliente.getStrCodOrgVentas();
			DISTR_CHAN = cliente.getStrCodCanalDist();
			DIVISION = cliente.getStrCodSector();
		}
		String date = fecha;
		@SuppressWarnings("unused")
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		String PURCH_NO_C = txtNPedCliente.getText();
		String SD_DOC_CAT = "C";
		String DOC_DATE = date;
		String BILL_DATE = date;
		String SERV_DATE = date;
		String CURRENCY = "USD";
		BeanConexion cn = new BeanConexion();
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
		header.setSD_DOC_CAT(SD_DOC_CAT);
		header.setDOC_DATE(DOC_DATE);
		header.setBILL_DATE(BILL_DATE);
		header.setSERV_DATE(SERV_DATE);
		header.setCURRENCY(CURRENCY);
		header.setCREATED_BY(CREATED_BY);
		header.setSHIP_TYPE(SHIP_TYPE);
		header.setSHIP_COND(SHIP_COND);
		header.setStrValorNeto(txtValorNeto.getText());
		header.setStrCodCliente(codigoCliente);
		header.setStrCliente(nombreCliente);
		header.setStrCodVendedor(usuario.getStrCodigo());
		header.setTxtClaseRiesgo(txtClasRiesgo.getText());
		header.setTxtDisponible(txtDisponible.getText());
		header.setTxtLimiteCredito(txtLimCredito.getText().replaceAll(",", ""));
		/**
		 * PARTNERS
		 */
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));
		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);
		/*
		 * MATERIALES
		 */
		List<Item> materiales = mdlTblItems.obtenerTodosItems();
		detalles = new ArrayList<BeanPedidoDetalle>();
		int i = 0;
		for (Item item : materiales) {
			String posicion = item.getPosicion();
			String material = item.getCodigo();
			String cantidad = item.getCantidad();
			String cantidadConfirmada = item.getCantidadConfirmada();
			String um = item.getUnidad();
			String porcentaje = item.getPorcentajeDescuento();
			String denominacion = item.getDenominacion();
			String precioNeto = "" + item.getPrecioNeto();
			String valorNeto = "" + item.getValorNeto();
			int tipo = item.getTipo();
			// Marcelo Moyano
			if (i == 0 && material.equals("")) {
				return null;
			}
			i++;
			if (tipo == 0 || tipo == 1) {
				if (!item.getCodigo().trim().isEmpty()) {
					BeanPedidoDetalle detalle = new BeanPedidoDetalle();
					detalle.setPosicion(posicion);
					detalle.setMaterial(Util.completarCeros(18, material));
					detalle.setCantidad(cantidad);
					if (item.isEliminadoLogicamente()) {
						detalle.setMotivoRechazo("19");
					} else {
						detalle.setMotivoRechazo("");
					}
					detalle.setCantidadConfirmada(cantidadConfirmada);
					detalle.setUM(um);
					detalle.setPorcentajeDescuento(porcentaje);
					detalle.setDenominacion(denominacion);
					detalle.setPrecioNeto(precioNeto);
					detalle.setValorNeto(valorNeto);
					detalle.setTipo(tipo);
					detalles.add(detalle);
				}
			}
		}
		pedido.setCodigoPedido(txtCodigoPedido.getText());
		pedido.setHeader(header);
		pedido.setPartners(partners);
		pedido.setDetalles(detalles);
		return pedido;
	}

	protected void simulaPreciosDeMaterialesSQLite2() {
		final DLocker bloqueador = new DLocker();
		class worker extends SwingWorker<Void,Void>{

			@Override
			protected Void doInBackground() throws Exception {
				try {
					
					List<Item> lista;
					// mdlTblItems.actualizarDescuentoCombo();

					if (flag) {
						flag = false;
						lista = mdlTblItems.obtenerTodosItems();
					} else {
						lista = mdlTblItems.obtenerTodosItemsNoSimulados();
					}
				
					String codCliente = codigoCliente;
					String codCondPago = txtCondPago.getText();
					SqlMaterialImpl sqlMat = new SqlMaterialImpl();

					Double dblCantidadPedido = new Double(0);
					Double dblImportePedido = new Double(0);

					List<BeanMaterial> materialesGrillas = new ArrayList<BeanMaterial>();
					// se limpian los % y clases de condicion entre cada
					// simulaciòn
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
						BeanMaterial cod = sqlMat.getMaterial(item.getCodigo());
						materialesGrillas.add(cod);
					}
					SqlClienteImpl sqlCli = new SqlClienteImpl();
					BeanCliente beanCli = sqlCli.buscarCliente(codCliente);
					
					// ///CONDICIONES COMERCIALES 1X
					for (Item i : lista) {
						
						i.setSimulado(true);
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 2 || i.getTipo() == 3)) {
							
							BeanMaterial material = null;
							material = getMaterialDeGrilla(materialesGrillas, i.getCodigo());
							i.setPrecioBase(Double.parseDouble(material.getPrice_1()));
							/*
							 * PRECIO CLIENTE MATERIAL
							 */
							if (i.getTipo() == 2 || i.getTipo() == 3) {
								double importeCombo = Double.parseDouble(material.getPrice_4());
								if (importeCombo != 0d) {
									i.setPrecioBase(importeCombo);
								} else {
									i.setTipo(3);
								}
							} else if (i.getTipo() == 0) {
								double importe = sqlMat.getPrecioXCliente(codCliente, i.getCodigo());
								if (importe != 0d) {
									i.setPrecioBase(importe);
								}
							}
							/***/
							List<BeanClaseMaterial> clasesMaterial = sqlMat.obtenerClaseMaterial(i.getCodigo());
							BeanCondicionComercial1 b1 = new BeanCondicionComercial1();
							b1.setStrCondicionPago(codCondPago);
							b1.setStrCliente(codCliente);
							b1.setListaStrClaseMaterial(clasesMaterial);
							Double dblDscto = new Double("0");
							List<BeanCondicionComercial1> condiciones1 = sqlMat.listarCondicion1(b1);
							BeanCondicionComercial1 condicion1 = null;
							if (condiciones1 != null && condiciones1.size() == 1) {
								condicion1 = condiciones1.get(0);
								dblDscto = condicion1.getDblImporte() / 10;
								i.setDblDesc1(dblDscto);
								i.setStrCondCalc1(condicion1.getStrClaseCond());
								i.setStrNroCond1(condicion1.getStrNroRegCond());
							} else {
								i.setDblDesc1(new Double(0));
								i.setStrCondCalc1("");
								i.setStrNroCond1("");
							}
						}					}
					// ///CONDICIONES COMERCIALES 1X

					// ///CONDICIONES COMERCIALES 2X
					for (Item i : lista) {
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 2 || i.getTipo() == 3)) {
							BeanCondicionComercial2 b2 = new BeanCondicionComercial2();
							b2.setStrCliente(codCliente);
							b2.setStrGrupoCliente("" + Integer.parseInt(beanCli.getStrCodigoTipologia()));
							b2.setStrCanal(beanCli.getStrCodCanalDist());
							b2.setStrMATNR(i.getCodigo());
							List<BeanCondicionComercial2> condiciones2 = sqlMat.listarCondicion2(b2);
							Double dblDscto = new Double("0");
							BeanCondicionComercial2 condicion2 = null;
							if (condiciones2 != null && condiciones2.size() == 1) {
								condicion2 = condiciones2.get(0);
								dblDscto = condicion2.getDblImporte() / 10;
								i.setDblDesc2(dblDscto);
								i.setStrCondCalc2(condicion2.getStrClaseCond());
								i.setStrNroCond2(condicion2.getStrNroRegCond());
							} else {
								i.setDblDesc2(new Double(0));
								i.setStrCondCalc2("");
								i.setStrNroCond2("");
							}
							int intSolicitado = (int) Double.parseDouble(i.getCantidad());
							if (i.getTipo() != 0) {
								int cantidadPadre = (int) Double.parseDouble(i.getPadre().getCantidad());
								intSolicitado = (int) Double.parseDouble(i.getCantidad()) * cantidadPadre;
							}
							// Nelson Villegas-Verificar stock
							// int intStock = (int)
							// Double.parseDouble(beanMaterial.getSt_1());
							String cantidadStock = sqlMat.obtenerStockMaterial(i.getCodigo());
							if (cantidadStock.length() == 0) {
								cantidadStock = "0";
							}
							int intStock = (int) Double.parseDouble(cantidadStock);
							// Nelson Villegas-Verificar stock
							// int intNuevoStock = 0;
							int intConfirmado = 0;
							if (intSolicitado <= intStock) {
								intConfirmado = intSolicitado;
							} else {
								intConfirmado = intStock;
							}
							i.setCantidadConfirmada(String.valueOf(intConfirmado));
							/*if(intStock==0) {
								BeanMaterial mat = sqlMat.getMaterial(i.getCodigo());
								if(!mat.getStrFec_Ing().equals("null"))
									i.setStrFech_Ing(Util.convierteFecha(mat.getStrFec_Ing()));
							} */
						}
					}
					// ///CONDICIONES COMERCIALES 2X

					// ///CONDICIONES COMERCIALES 3X
					for (Item i : lista) {
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 3)) {
							BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
							// cliente
							b3.setStrCliente(codCliente);
							BeanMaterial objMat = getMaterialDeGrilla(materialesGrillas, i.getCodigo());
							List<BeanClaseMaterial> claMat = sqlMat.obtenerClaseMaterial(i.getCodigo());
							if (claMat != null && claMat.size() == 1) {
								// clase material
								b3.setStrClase(claMat.get(0).getStrDescripcionClaseMaterial());
							} else {
								String clasTMP = "";
								for (BeanClaseMaterial clase : claMat) {
									clasTMP += clase.getStrDescripcionClaseMaterial() + "','";
								}
								b3.setStrClase(clasTMP);
							}
							// jerarquia
							String div = objMat.getPrdha();
							b3.setStrDivisionCom(div.substring(0, 2) != null ? div.substring(2, 5) : "");
							b3.setStrCatProd(div.substring(2, 5) != null ? div.substring(2, 5) : "");
							b3.setStrFamilia(div.substring(5, 9) != null ? div.substring(2, 5) : "");
							b3.setStrLinea(div.substring(9, 13) != null ? div.substring(2, 5) : "");
							b3.setStrGrupo(div.substring(13, 18) != null ? div.substring(2, 5) : "");
							// material
							b3.setStrMaterial(i.getCodigo());
							// división
							BeanCliente objCliente = sqlCli.buscarCliente(codCliente);
							b3.setStrDivisionC(objCliente.getStrGrupoVentas());
							BeanCondicionComercial3x condicion3 = null;
							Double dblDscto = new Double("0");
							List<BeanCondicionComercial3x> condiciones3x = sqlMat.listarCondicion3(b3);
							// se calcula el importe del pedido, considerando
							// los % de descuento que se aplicaron
							// en 1x y 2x de todos los items
							if (condiciones3x != null && condiciones3x.size() == 1) {
								condicion3 = condiciones3x.get(0);
								i.setStrCondCalc3(condicion3.getStrClaseCond());
								i.setDblDesc3(dblDscto);
								i.setStrNroCond3(condicion3.getStrNroRegCond());
							} else {
								i.setDblDesc3(new Double(0));
								i.setStrCondCalc3("");
								i.setStrNroCond3("");
							}
						}
					}
					for (Item i : lista) {
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 3)) {
							BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
							// cliente
							b3.setStrCliente(codCliente);
							BeanMaterial objMat = getMaterialDeGrilla(materialesGrillas, i.getCodigo());
							List<BeanClaseMaterial> claMat = sqlMat.obtenerClaseMaterial(i.getCodigo());
							if (claMat != null && claMat.size() == 1) {
								// clase material
								b3.setStrClase(claMat.get(0).getStrDescripcionClaseMaterial());
							} else {
								String claTMP = "";
								for (BeanClaseMaterial claseMat : claMat) {
									claTMP += claseMat.getStrDescripcionClaseMaterial() + "','";
								}
								b3.setStrClase(claTMP);
							}
							// jerarquia
							String prdha = objMat.getPrdha();
							b3.setStrDivisionCom(prdha.substring(0, 2));
							b3.setStrCatProd(prdha.substring(2, 5));
							b3.setStrFamilia(prdha.substring(5, 9));
							b3.setStrLinea(prdha.substring(9, 13));
							b3.setStrGrupo(prdha.substring(13, 18));
							// material
							b3.setStrMaterial(i.getCodigo());
							// división
							BeanCliente objCliente = sqlCli.buscarCliente(codCliente);
							b3.setStrDivisionC(objCliente.getStrGrupoVentas());
							BeanCondicionComercial3x condicion3 = null;
							Double dblDscto = new Double("0");
							List<BeanCondicionComercial3x> condiciones3x = sqlMat.listarCondicion3(b3);
							// se calcula el importe del pedido, considerando
							// los % de descuento que se aplicaron
							// en 1x y 2x de todos los items
							dblImportePedido = calculaImportePedidoClaseCondicion1x2x2(lista, i, materialesGrillas);

							if (condiciones3x != null && condiciones3x.size() == 1) {
								condicion3 = condiciones3x.get(0);
								if (condicion3.getStrEscala().equals("X")) {
									dblDscto = condicion3.getDblImporteEscala(dblImportePedido) / 10;
								} else {
									dblDscto = condicion3.getDblImporte() / 10;
								}
								i.setStrCondCalc3(condicion3.getStrClaseCond());
								i.setDblDesc3(dblDscto);
								i.setStrNroCond3(condicion3.getStrNroRegCond());
							} else {
								i.setDblDesc3(new Double(0));
								i.setStrCondCalc3("");
								i.setStrNroCond3("");
							}
						}					}
					// ///CONDICIONES COMERCIALES 3X

					// ///CONDICIONES COMERCIALES 4X
					for (Item i : lista) {
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 3)) {
							BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
							// cliente
							b3.setStrCliente(codCliente);
							BeanMaterial objMat = getMaterialDeGrilla(materialesGrillas, i.getCodigo());
							List<BeanClaseMaterial> claMat = sqlMat.obtenerClaseMaterial(i.getCodigo());
							if (claMat != null && claMat.size() == 1) {
								// clase material
								b3.setStrClase(claMat.get(0).getStrDescripcionClaseMaterial());
							} else {
								String claseTMP = "";
								for (BeanClaseMaterial claseMat : claMat) {
									claseTMP += claseMat.getStrDescripcionClaseMaterial() + "','";
								}
								b3.setStrClase(claseTMP);
							}
							// jerarquia
							String prdha = objMat.getPrdha();
							b3.setStrDivisionCom(prdha.substring(0, 2));
							b3.setStrCatProd(prdha.substring(2, 5));
							b3.setStrFamilia(prdha.substring(5, 9));
							b3.setStrLinea(prdha.substring(9, 13));
							b3.setStrGrupo(prdha.substring(13, 18));
							// material
							b3.setStrMaterial(i.getCodigo());
							// división
							BeanCliente objCliente = sqlCli.buscarCliente(codCliente);
							b3.setStrDivisionC(objCliente.getStrGrupoVentas());
							BeanCondicionComercial4x condicion4 = null;
							List<BeanCondicionComercial4x> condiciones4x = sqlMat.listarCondicion4(b3);
							dblCantidadPedido = calculaCantidadPedidoClaseCondicion2(lista, i);
							System.out.println("-->" + dblCantidadPedido);
							if (condiciones4x != null && condiciones4x.size() == 1) {
								condicion4 = condiciones4x.get(0);
								i.setStrCondCalc4(condicion4.getStrClaseCond());
								i.setStrNroCond4(condicion4.getStrNroRegCond());
							} else {
								i.setStrCondCalc4("");
								i.setStrNroCond4("");
							}
						}
					}
					for (Item i : lista) {
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 3)) {
							BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
							// cliente
							b3.setStrCliente(codCliente);
							BeanMaterial objMat = getMaterialDeGrilla(materialesGrillas, i.getCodigo());
							List<BeanClaseMaterial> claMat = sqlMat.obtenerClaseMaterial(i.getCodigo());
							if (claMat != null && claMat.size() == 1) {
								// clase material
								b3.setStrClase(claMat.get(0).getStrDescripcionClaseMaterial());
							} else {
								String claseTMP = "";
								for (BeanClaseMaterial claseMat : claMat) {
									claseTMP += claseMat.getStrDescripcionClaseMaterial() + "','";
								}
								b3.setStrClase(claseTMP);
							}
							// jerarquia
							String prdha = objMat.getPrdha();
							b3.setStrDivisionCom(prdha.substring(0, 2));
							b3.setStrCatProd(prdha.substring(2, 5));
							b3.setStrFamilia(prdha.substring(5, 9));
							b3.setStrLinea(prdha.substring(9, 13));
							b3.setStrGrupo(prdha.substring(13, 18));
							// material
							b3.setStrMaterial(i.getCodigo());
							// división
							BeanCliente objCliente = sqlCli.buscarCliente(codCliente);
							b3.setStrDivisionC(objCliente.getStrGrupoVentas());
							Double dblDscto = new Double("0");
							BeanCondicionComercial4x condicion4 = null;
							List<BeanCondicionComercial4x> condiciones4x = sqlMat.listarCondicion4(b3);

							dblCantidadPedido = calculaCantidadPedidoClaseCondicion2(lista, i);
							System.out.println("-->" + dblCantidadPedido);
							if (condiciones4x != null && condiciones4x.size() == 1) {
								condicion4 = condiciones4x.get(0);
								if (condicion4.getStrEscala().equals("X")) {
									dblDscto = condicion4.getDblImporteEscala(dblCantidadPedido) / 10;
								} else {
									dblDscto = condicion4.getDblImporte() / 10;
								}
								i.setDblDesc4(dblDscto);
								i.setStrCondCalc4(condicion4.getStrClaseCond());
							} else {
								i.setDblDesc4(new Double(0));
								i.setStrCondCalc4("");
							}
						}					}
					// ///CONDICIONES COMERCIALES 4X

					// ///CONDICIONES COMERCIALES 5X
					for (Item i : lista) {
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 3)) {
							BeanCondicionComercial5x condicion5 = null;
							Double dblDscto = new Double("0");
							BeanCondicionComercial5x b5 = new BeanCondicionComercial5x();
							b5.setStrCliente(codCliente);
							BeanMaterial objMat = getMaterialDeGrilla(materialesGrillas, i.getCodigo());
							String prdha = objMat.getPrdha();
							b5.setStrDivisionCom(prdha.substring(0, 2));
							b5.setStrCatProd(prdha.substring(2, 5));
							b5.setStrFamilia(prdha.substring(5, 9));
							List<BeanCondicionComercial5x> condiciones5x = sqlMat.listarCondicion5(b5);
							dblCantidadPedido = calculaImportePedido4x2(lista, i, materialesGrillas);

							if (condiciones5x != null && condiciones5x.size() == 1) {
								condicion5 = condiciones5x.get(0);
								if (condicion5.getStrEscala().equals("X")) {
									dblDscto = condicion5.getDblImporteEscala(dblCantidadPedido) / 10;
								} else {
									dblDscto = condicion5.getDblImporte() / 10;
								}
								i.setDblDesc5(dblDscto);
								i.setStrCondCalc5(condicion5.getStrClaseCond());
								i.setStrNroCond5(condicion5.getStrNroRegCond());
							} else {
								i.setDblDesc5(new Double(0));
								i.setStrCondCalc5("");
								i.setStrNroCond5("");
							}
						}					}
					// ///CONDICIONES COMERCIALES 5X
					String strDescCalc = "";
					for (Item i : lista) {
						if (i.getCodigo() != null && !i.getCodigo().trim().equals("")
								&& (i.getTipo() == 0 || i.getTipo() == 2 || i.getTipo() == 3)) {
							Double dblPrecioFinal = new Double(0);
							strDescCalc += "precio bruto del material " + i.getCodigo() + ": " + i.getPrecioBase();
							strDescCalc += "\n";
							Double dblPrecioTemp = i.getPrecioBase();
							if (i.getDblDesc1() != null && i.getStrCondCalc1() != null
									&& !i.getStrCondCalc1().equals("")) {
								dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc1()) / 100;
								strDescCalc += " primer descuento :" + i.getStrCondCalc1() + " / " + i.getDblDesc1();
								strDescCalc += " precio final :"
										+ Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
								strDescCalc += "\n";
							}
							if (i.getDblDesc2() != null && i.getStrCondCalc2() != null
									&& !i.getStrCondCalc2().equals("")) {
								dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc2()) / 100;
								strDescCalc += " segundo descuento :" + i.getStrCondCalc2() + " / " + i.getDblDesc2();
								strDescCalc += " precio final :"
										+ Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
								strDescCalc += "\n";
							}
							if (i.getDblDesc3() != null && i.getStrCondCalc3() != null
									&& !i.getStrCondCalc3().equals("")) {
								dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc3()) / 100;
								strDescCalc += " tercer descuento :" + i.getStrCondCalc3() + " / " + i.getDblDesc3();
								strDescCalc += " precio final :"
										+ Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
								strDescCalc += "\n";
							}
							if (i.getDblDesc4() != null && i.getStrCondCalc4() != null
									&& !i.getStrCondCalc4().equals("")) {
								dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc4()) / 100;
								strDescCalc += " cuarto descuento :" + i.getStrCondCalc4() + " / " + i.getDblDesc4();
								strDescCalc += " precio final :"
										+ Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
								strDescCalc += "\n";
							}
							if (i.getDblDesc5() != null && i.getStrCondCalc5() != null
									&& !i.getStrCondCalc5().equals("")) {
								dblPrecioTemp = dblPrecioTemp * (100 + i.getDblDesc5()) / 100;
								strDescCalc += " quinto descuento :" + i.getStrCondCalc5() + " / " + i.getDblDesc5();
								strDescCalc += " precio final :"
										+ Integer.parseInt(i.getCantidadConfirmada()) * dblPrecioTemp;
								strDescCalc += "\n";
							}
							String valor = i.getPorcentajeDescuento();
							if (valor.equals("")) {
								valor = "0.00";
							}
							Double dblDsctoManual = Double.parseDouble(valor);
							dblPrecioTemp = dblPrecioTemp * (100 - Math.abs(dblDsctoManual)) / 100;

							dblPrecioFinal = dblPrecioTemp;
							double d = dblPrecioFinal * Integer.parseInt(i.getCantidadConfirmada());
							i.setValorNeto((d * 1000) / 1000.0d);
							i.setPrecioNeto(dblPrecioFinal);

							i.setSimulado(true);

							// Marcelo Moyano
							// ExameptionViewer.getInstance().limpiar();
							// ExceptionViewer.getInstance().setStackTrace(strDescCalc);
							// ExceptionViewer.getInstance().hacerVisible();
							// Marcelo Moyano
							// Aparece Pila de Errores
							String codigo = i.getCodigo();
							
							String condCalc1 = i.getStrCondCalc1();
							double dblDesc1 = i.getDblDesc1();
							
							String condCalc2 = i.getStrCondCalc2();
							double dblDesc2 = i.getDblDesc2();
							
							String condCalc3 = i.getStrCondCalc3();
							double dblDesc3 = i.getDblDesc3();


							String condCalc4 = i.getStrCondCalc4();
							double dblDesc4 = i.getDblDesc4();

							String condCalc5 = i.getStrCondCalc5();
							double dblDesc5 = i.getDblDesc5();
						}					}

					mdlTblItems.asociarItemsYPrecios();

					String valorNeto = mdlTblItems.getValorNeto().toString();
					String treDigitos = Util.formatearNumeroTresDigitos(valorNeto);
					txtValorNeto.setText(treDigitos);
					tblPedidos.updateUI();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					Promesa.getInstance().mostrarAvisoSincronizacion("");
					bloqueador.dispose();
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							mdlTblItems.asociarItemsYPrecios();
							String valorNeto = mdlTblItems.getValorNeto().toString();
							txtValorNeto.setText(Util.formatearNumero(valorNeto));
							Promesa.getInstance().mostrarAvisoSincronizacion("");	
							bloqueador.dispose();
							tblPedidos.updateUI();
							tblPedidos.clearSelection();
						}
					});
				}

				return null;
			}
			
		}
		new worker().execute();
		bloqueador.setVisible(true);
	}

	protected void simularPreciosDeMaterialesSAP(boolean t) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					Promesa.getInstance().mostrarAvisoSincronizacion("Calculando Precios....");
					BeanDato usuario = Promesa.datose.get(0);
					// mdlTblItems.actualizarDescuentoCombo();										
					List<Item> simulados = mdlTblItems.obtenerTodosItemsSimulados();
					BeanPedido pedido = llenarEstructuraParaSimulacion();
					if (usuario.getStrModo().equals("1")) {// DESDE SAP
						SPedidos objSAP = new SPedidos();
						try {
							List<String[]> items = objSAP.obtenerItemsSimulados(pedido);
							if (items != null && !items.isEmpty()) {
								llenarTabla(items, simulados);
							}
						} catch (Exception e) {
							
							Mensaje.mostrarError(e.getMessage());
							bloqueador.dispose();
							return;
						}
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					Promesa.getInstance().mostrarAvisoSincronizacion("");
					bloqueador.dispose();
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							mdlTblItems.asociarItemsYPrecios();
							String valorNeto = mdlTblItems.getValorNeto().toString();
							txtValorNeto.setText("" + Util.formatearNumero(valorNeto));
							Promesa.getInstance().mostrarAvisoSincronizacion("");
							bloqueador.dispose();
							tblPedidos.updateUI();
							tblPedidos.clearSelection();
						}
					});
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	protected List<BeanMensaje> guardarPedidoSQLite() {
		List<Item> lista;
		// mdlTblItems.actualizarDescuentoCombo();

		if (flag) {
			flag = false;
			lista = mdlTblItems.obtenerTodosItems();
		} else {
			lista = mdlTblItems.obtenerTodosItemsNoSimulados();
		}
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getCantidad().equals("0")&&!lista.get(i).getCodigo().equals("")){
				lista.remove(i);
				i--;
			}
			
		}
		List<BeanMensaje> mensajes = new ArrayList<BeanMensaje>();
		// mdlTblItems.actualizarDescuentoPadre();
		List<BeanPedido> pedidos = llenarEstructuraParaCreacionEnSQLite();
		if (pedidos == null) {
			return null;
		}
		for (BeanPedido pedido : pedidos) {
			if (!pedido.getDetalles().isEmpty()) {
				try {
					for (int i = 0; i < pedido.getDetalles().size(); i++) {
						BeanPedidoDetalle detalle = pedido.getDetalles().get(i);
						int posicion = (i + 1) * 10;
						detalle.setPosicion(Util.completarCeros(6, "" + posicion));
					}
					pedido.setCodigoPedido("");
					String mensaje[] = guardarPedidoSQLite(pedido);
					if (mensaje != null) {
						BeanMensaje msg = new BeanMensaje("");
						Long codigo = 0l;
						try {
							codigo = Long.parseLong(mensaje[2]);
						} catch (NumberFormatException e) {
							Util.mostrarExcepcion(e);
							codigo = -1l;
						}
						msg.setIdentificador(codigo);
						msg.setDescripcion(mensaje[1]);
						msg.setTipo(mensaje[0]);
						mensajes.add(msg);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			}
		}
		guardarMarcasEstrategicas();
		return mensajes;
	}

	protected List<BeanMensaje> guardarPedidoSAP() {
		List<Item> lista;
		if (flag) {
			flag = false;
			lista = mdlTblItems.obtenerTodosItems();
		} else {
			lista = mdlTblItems.obtenerTodosItemsNoSimulados();
		}
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getCantidad().equals("0")&&!lista.get(i).getCodigo().equals("")){
				lista.remove(i);
				i--;
			}
			
		}
		List<BeanMensaje> mensajes = new ArrayList<BeanMensaje>();
		// mdlTblItems.actualizarDescuentoPadre();
		List<BeanPedido> pedidos = llenarEstructuraParaCreacionEnSAP();
		mapaItems = new HashMap<String, List<Item>>();
		List<Item> its = mdlTblItems.obtenerTodosItems();
		List<Item> itsNormal = new ArrayList<Item>();
		List<Item> itsRojo = new ArrayList<Item>();
		List<Item> itsPesado = new ArrayList<Item>();
		List<Item> itsBodega = new ArrayList<Item>();

		for (Item item : its) {
			if (item.getTipo() == 0 || item.getTipo() == 1) {
				if (item.getTipoMaterial().toUpperCase().compareTo("N") == 0) {
					itsNormal.add(item);
				} else if (item.getTipoMaterial().toUpperCase().compareTo("R") == 0) {
					itsRojo.add(item);
				} else if (item.getTipoMaterial().toUpperCase().compareTo("B") == 0) {
					itsBodega.add(item);
				} else if (item.getTipoMaterial().toUpperCase().compareTo("P") == 0) {
					itsPesado.add(item);
				}
			}
		}
		mapaItems.put("N", itsNormal);
		mapaItems.put("R", itsRojo);
		mapaItems.put("B", itsBodega);
		mapaItems.put("P", itsPesado);
		if (pedidos == null) {
			return null;
		}
		for (BeanPedido pedido : pedidos) {
			SPedidos objSAP = new SPedidos();
			// BeanDato user = Promesa.datose.get(0);
			// if (user.getStrModo().equals("0")) {
			// return null;
			// }
			if (!pedido.getDetalles().isEmpty()) {
				try {
					for (int i = 0; i < pedido.getDetalles().size(); i++) {
						BeanPedidoDetalle detalle = pedido.getDetalles().get(i);
						int posicion = (i + 1) * 10;
						detalle.setPosicion(Util.completarCeros(6, "" + posicion));
					}
					String mensaje[] = objSAP.guardarPedido(pedido);
					if (mensaje != null) {
						BeanMensaje msg = new BeanMensaje("");
						Long codigo = 0l;
						try {
							codigo = Long.parseLong(mensaje[2]);
							if (codigo < 0) {
								msg.setPedido(pedido);
								msg.setIts(mapaItems.get(pedido.getTipo()));
							}
						} catch (NumberFormatException e) {
							codigo = -1l;
							msg.setPedido(pedido);
							msg.setIts(mapaItems.get(pedido.getTipo()));
						}
						msg.setIdentificador(codigo);
						msg.setDescripcion(mensaje[1]);
						msg.setTipo(mensaje[0]);
						msg.setTipoMaterial(pedido.getTipo());
						mensajes.add(msg);
					}
				} catch (Exception e) {
					Mensaje.mostrarError(e.getMessage());
				}
			}
		}
		guardarMarcasEstrategicas();
		return mensajes;
	}

	protected List<BeanMensaje> cotizarPedidoSAP() {
		List<BeanMensaje> mensajes = new ArrayList<BeanMensaje>();
		BeanPedido pedido = llenarEstructuraParaGuardarCotizacion();
		if (pedido == null) {
			System.out.println("Pedido nulo.");
			return null;
		}
		SPedidos objSAP = new SPedidos();
		if (!pedido.getDetalles().isEmpty()) {
			try {
				String mensaje[] = objSAP.crearCotizacion(pedido);
				if (mensaje != null) {
					BeanMensaje msg = new BeanMensaje("");
					Long codigo = 0l;
					try {
						codigo = Long.parseLong(mensaje[2]);
					} catch (NumberFormatException e) {
						Util.mostrarExcepcion(e);
						codigo = -1l;
					}
					msg.setIdentificador(codigo);
					msg.setDescripcion(mensaje[1]);
					msg.setTipo(mensaje[0]);
					mensajes.add(msg);
				}
			} catch (Exception e) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
		return mensajes;
	}

	protected String[] guardarPedidoSQLite(BeanPedido pedido) {
		BeanMensaje mensaje = new BeanMensaje("");
		boolean fallo = false;
		String idBD = "0";
		String msj = "";
		long codigo = 0;
		try {
			SqlPedidoImpl pedidosSQL = new SqlPedidoImpl();
			idBD = pedidosSQL.insertarPedidos(pedido);
			try {
				codigo = Long.parseLong(idBD);
			} catch (Exception e) {
				codigo = 0l;
				Util.mostrarExcepcion(e);
			}
			switch (tipoVentana) {
			case Constante.VENTANA_CREAR_PEDIDO:
				msj = "El pedido N° " + codigo + " se ha guardado correctamente en base de datos local.";
				break;

			case Constante.VENTANA_CREAR_PROFORMA:
				msj = "La proforma N° " + codigo + " se ha guardado correctamente en base de datos local.";
				break;
			case Constante.VENTANA_CREAR_COTIZACION:
				msj = "La cotización N° " + codigo + " se ha guardado correctamente en base de datos local.";
				break;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			fallo = true;
			mensaje.setDescripcion("No se ha podido guardar correctamente el pedido porque: \n" + e.getMessage());
			mensaje.setIdentificador(0l);
			mensaje.setTipo("E");

		} finally {
			if (!fallo) {
				mensaje.setDescripcion(msj);
				mensaje.setIdentificador(codigo);
				mensaje.setTipo("N");
			}
		}
		String[] msg = new String[3];
		msg[0] = mensaje.getTipo();
		msg[1] = mensaje.getDescripcion();
		msg[2] = "" + mensaje.getIdentificador();
		return msg;
	}

	protected void llenarTabla(final List<String[]> items) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SqlMaterialImpl sql = new SqlMaterialImpl();
				for (String[] strings : items) {
					Item item = new Item();
					@SuppressWarnings("unused")
					int pos = 0;
					try {
						pos = Integer.parseInt(strings[0]);
					} catch (Exception e) {
						pos = 0;
					}
					try {
					item.setPosicion(strings[0]);
					item.setCodigo(strings[1]);
					item.setCantidad(strings[2]);
					item.setCantidadConfirmada(strings[3]);
					item.setStock("-1");
					item.setUnidad(strings[4]);
					item.setDenominacion(strings[6]);
					item.setStrFech_Ing("");
					iva = strings[10]; // GUARDA VALOR IVA
					
					}catch(Exception e) {
						Util.mostrarExcepcion(e);
					}
					Double precioNeto = 0d;
					try {
						precioNeto = Double.parseDouble(strings[7]);
						// precioNeto =
						// Double.parseDouble(Util.formatearNumeroTresDigitos(strings[7]));
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						precioNeto = 0d;
					}

					Double valorNeto = 0d;
					try {
						valorNeto = Double.parseDouble(strings[8]);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						valorNeto = 0d;
					}
					item.setEditable(true);
					item.setPrecioNeto(precioNeto);
					item.setValorNeto(valorNeto);
					String categoria = strings[9];
					item.setTipoSAP(categoria);
					if (categoria.compareTo("ZP01") == 0) {
						item.setTipo(0);
					} else if (categoria.compareTo("ZPP1") == 0 || categoria.compareTo("ZPP3") == 0) {
						item.setTipo(1);
					} else if (categoria.compareTo("ZPH1") == 0) {
						int k = 0;
						try {
							k = Integer.parseInt(strings[2]);
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}
						item.setCantidadAMostrar(k);
						item.setTipo(2);
						item.setEditable(false);
					}
					String tipoMaterial = sql.getTipoMaterial(item.getCodigo());
					if (tipoMaterial != null && !tipoMaterial.equals("")) {
						item.setTipoMaterial(tipoMaterial);
					} else {
						SqlComboImpl sqlCombos = new SqlComboImpl();				
						//tipoMaterial = sqlCombos.getCombo(item.getCodigo()).getTipoMaterial();
					}
					item.setSimulado(true);
					final Item it = item;
					mdlTblItems.actualizarItem(it);
				}
				mdlTblItems.asociarItemsYPrecios();
				List<Item> items = mdlTblItems.obtenerTodosItems();
				for (Item i : items) {
					if (i.getTipo() == 2) {
						int cantidad = 0;
						int cantidadPadre = 0;
						try {
							cantidad = Integer.parseInt(i.getCantidad());
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}
						try {
							cantidadPadre = Integer.parseInt(i.getPadre().getCantidad());
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}
						if (cantidadPadre != 0) {
							cantidad *= cantidadPadre;
							// i.setCantidad("" + cantidad);
							i.setCantidadConfirmada("" + cantidad);
						}
					}
				}
			}
		});
	}

	protected void llenarTabla(final List<String[]> items, final List<Item> itemsSimulados) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SqlMaterialImpl sql = new SqlMaterialImpl();
				for (String[] strings : items) {
					Item item = new Item();
					
					@SuppressWarnings("unused")
					int pos = 0;
					try {
						pos = Integer.parseInt(strings[0]);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						pos = 0;
					}
					item.setPosicion(strings[0]);
					item.setCodigo(strings[1]);
					item.setCantidad(strings[2]);
					item.setCantidadConfirmada(strings[3]);					
					item.setStock("-1");
					item.setStrFech_Ing("");
					item.setUnidad(strings[4]);
					item.setDenominacion(strings[6]);
					iva = strings[10]; // GUARDA VALOR IVA
					Double precioNeto = 0d;
					try {
						precioNeto = Double.parseDouble(strings[7]);
						// precioNeto =
						// Double.parseDoubl1e(Util.formatearNumeroTresDigitos(strings[7]));
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						precioNeto = 0d;
					}
					Double valorNeto = 0d;
					try {
						valorNeto = Double.parseDouble(strings[8]);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						valorNeto = 0d;
					}
					item.setEditable(true);
					item.setPrecioNeto(precioNeto);
					item.setValorNeto(valorNeto);
					String categoria = strings[9];
					item.setTipoSAP(categoria);
					if (categoria.compareTo("ZP01") == 0) {
						item.setTipo(0);
					} else if (categoria.compareTo("ZPP1") == 0 || categoria.compareTo("ZPP3") == 0) {
						item.setTipo(1);
					} else if (categoria.compareTo("ZPH1") == 0) {
						int k = 0;
						try {
							k = Integer.parseInt(strings[2]);
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}
						item.setCantidadAMostrar(k);
						item.setTipo(2);
						item.setEditable(false);
					}
					
					String tipoMaterial = sql.getTipoMaterial(item.getCodigo());
					if (tipoMaterial != null && !tipoMaterial.equals("")) {
						item.setTipoMaterial(tipoMaterial);
					}
					item.setSimulado(true);
					final Item it = item;
					mdlTblItems.actualizarItem(it);
					pos++;
				}
				mdlTblItems.asociarItemsYPrecios();
				List<Item> items = mdlTblItems.obtenerTodosItems();
				for (Item i : items) {
					if (i.getTipo() == 2) {
						int cantidad = 0;
						int cantidadPadre = 0;
						try {
							cantidad = Integer.parseInt(i.getCantidad());
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}
						try {
							cantidadPadre = (int) Double.parseDouble(i.getPadre().getCantidad());
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}
						if (cantidadPadre != 0) {
							cantidad *= cantidadPadre;
							// i.setCantidad("" + cantidad);
							i.setCantidadConfirmada("" + cantidad);
						}
					}
				}
			}
		});
	}
	protected void agregarMaterialNuevo(String codigo, int fila, String IdMaterial) {
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		List<BeanMaterial> listmat = sqlMateriales.getMaterialNuevo(codigo,IdMaterial);
		int fila1 =0;
		for(int i = 0;i<listmat.size();i++){
			//if (!mdlTblItems.yaEstaAgregado2(listmat.get(i).getIdMaterial(), fila1)){
				//fila1++;
				agregarItemVacio(-1);
			//}
		}
		mdlTblItems.actualizarPosiciones();
		for(BeanMaterial mat: listmat){
			if (!mdlTblItems.yaEstaAgregado3(mat.getIdMaterial())) {			
				fila=mdlTblItems.posicionDisponible();
				if (mat != null) {
					Item material = new Item();
					material.setDenominacion(sqlMateriales.obtenerNombreMaterial(mat.getIdMaterial()));
					material.setPosicion(mdlTblItems.getValueAt(fila, 0).toString());
					material.setCodigo(mat.getIdMaterial());
					material.setUnidad(mat.getUn());
					material.setCantidad("0");
					material.setCantidadConfirmada(mdlTblItems.getValueAt(fila, 3).toString());
					material.setPorcentajeDescuento(mdlTblItems.getValueAt(fila, 6).toString());
					material.setStock("");
					material.setStrFech_Ing(mdlTblItems.getValueAt(fila, 7).toString());
					material.setPrecioNeto(mdlTblItems.obtenerItem(fila).getPrecioNeto());
					material.setValorNeto(mdlTblItems.obtenerItem(fila).getValorNeto());
					material.setTipo(0); // Item normal
					material.setEsmaterialnuevo(true);
					material.setEditable(true);
	
					String tipoMaterial = mat.getTypeMat();
					SqlClienteImpl sql = new SqlClienteImpl();
					String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
					if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
						tipoMaterial = "B";
					} else if (tipoMaterial.compareTo("N") == 0
							|| (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
						tipoMaterial = "N";
					} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
						tipoMaterial = "P";
					} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
						tipoMaterial = "R";
					}
					material.setTipoMaterial(tipoMaterial);
					material.setSimulado(false);
					mdlTblItems.agregarItem(material, fila);
				}
			
			} 
		}
	}

	protected void agregarMaterialPorEventoTab() {
		int fila = tblPedidos.getSelectedRow();
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		SqlComboImpl sqlCombos = new SqlComboImpl();
		String codigo = textFieldCodigo.getText();
		if (!mdlTblItems.yaEstaAgregado4(codigo,fila)) {
			BeanMaterial mat = sqlMateriales.getMaterial(codigo);
			boolean vacio = false;
			if (mat != null && (mat.getMtart().equals("HAWA") || mat.getMtart().equals("ZCOM"))) {
				Item material = new Item();
				material.setDenominacion(sqlMateriales.obtenerNombreMaterial(codigo));
				material.setPosicion(mdlTblItems.getValueAt(fila, 0).toString());
				material.setCodigo(textFieldCodigo.getText());
				material.setUnidad(mat.getUn());
				material.setCantidad(mdlTblItems.getValueAt(fila, 2).toString());
				material.setCantidadConfirmada(mdlTblItems.getValueAt(fila, 3).toString());
				material.setStock("");
				material.setPorcentajeDescuento(mdlTblItems.getValueAt(fila, 6).toString());
				material.setStrFech_Ing(mdlTblItems.obtenerItem(fila).getStrFech_Ing());
				material.setPrecioNeto(mdlTblItems.obtenerItem(fila).getPrecioNeto());
				material.setValorNeto(mdlTblItems.obtenerItem(fila).getValorNeto());
				material.setTipo(0); // Item normal
				material.setEditable(true);
			

				String tipoMaterial = mat.getTypeMat();
				SqlClienteImpl sql = new SqlClienteImpl();
				String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
				if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
					tipoMaterial = "B";
				} else if (tipoMaterial.compareTo("N") == 0
						|| (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
					tipoMaterial = "N";
				} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
					tipoMaterial = "P";
				} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
					tipoMaterial = "R";
				}
				material.setTipoMaterial(tipoMaterial);
				material.setSimulado(false);
				mdlTblItems.agregarItem(material, fila);
				tblPedidos.updateUI();
			} else {
				Item combo = sqlCombos.getCombo(codigo);
				if (combo != null) {
					if (!mdlTblItems.existeCombo(codigo)) {
						combo.setCantidad("1");
						combo.setCantidadConfirmada(mdlTblItems.getValueAt(fila, 3).toString());
						combo.setPorcentajeDescuento(mdlTblItems.getValueAt(fila, 6).toString());
						combo.setEditable(true);
						combo.setSimulado(true);
						mdlTblItems.agregarItem(combo, fila);
						tblPedidos.updateUI();
					}
				} else {
					if(mat!=null){
						String strMensaje = "<html><font color='red'>El material "+mat.getIdMaterial()+", tipo "+mat.getMtart()+" no permitido</font></html>";
						MostarMensaje(strMensaje, "Materiales no permitidos");
					}else{
						vacio = true;
					}
				}
			}
			if (vacio) {
				lblMensajes.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
				lblMensajes.setText("EL MATERIAL SELECCIONADO NO EXISTE");
				textFieldCodigo.selectAll();
			} else {
				lblMensajes.setText("");
				actualizarPosiciones();
				tblPedidos.editCellAt(fila, 2);
				textFieldCantidad.requestFocusInWindow();
				textFieldCantidad.setEditable(true);
				selectionModel.setSelectionInterval(fila, fila);
			}
		} else {
			tblPedidos.editCellAt(fila, 1);
			textFieldCodigo.requestFocusInWindow();
			textFieldCodigo.selectAll();
			textFieldCodigo.setEditable(true);
		}
	}

	public void actualizarPosiciones() {
		mdlTblItems.actualizarPosiciones();
	}

	public int agregarMaterial(Item item, int posicion) {
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		BeanMaterial mat = sqlMateriales.getMaterial(item.getCodigo());
		if (mat != null  && (mat.getMtart().equals("HAWA") || mat.getMtart().equals("ZCOM"))) {
			String tipoMaterial = mat.getTypeMat();
			SqlClienteImpl sql = new SqlClienteImpl();
			String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
			if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
				tipoMaterial = "B";
			} else if (tipoMaterial.compareTo("N") == 0
					|| (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
				tipoMaterial = "N";
			} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
				tipoMaterial = "P";
			} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
				tipoMaterial = "R";
			}
			item.setTipoMaterial(tipoMaterial);
			/*
			 * marcelo moyano
			 */
			Integer k = 0;
			try {
				k = Integer.parseInt(item.getCantidad());
			} catch (Exception ex) {
				k = 0;
			} // ---- Marcelo Moyano ---- //
				// Integer k = Integer.parseInt(item.getCantidad());
			int opcion = mdlTblItems.yaEstaAgregadoDeConsultaDinamica(item.getCodigo(), k);
			switch (opcion) {
			case -2:
				return -1;
			case -1:
				if(item.getPosicion().equals(""))
					item.setPosicion(String.valueOf(mdlTblItems.posicionDisponible()));
				agregarItemVacio(-1);
				int pos = mdlTblItems.agregarItem(item, posicion);
				agregarMaterialNuevo(mat.getNormt(),pos,mat.getIdMaterial());
				tblPedidos.updateUI();
				return 1;
			default:
				if(item.getPosicion().equals(""))
					item.setPosicion(String.valueOf(mdlTblItems.posicionDisponible()));
				int posi = mdlTblItems.agregarItem(item, opcion);
				//agregarMaterialNuevo(mat.getNormt(),posi,mat.getIdMaterial());
				tblPedidos.updateUI();
				return 1;
			}
		}else if(mat!=null){
			String strMensaje = "<html><font color='red'>EL MATERIAL "+mat.getIdMaterial()+" CON TIPO DE MATERIAL "+mat.getMtart()+"</font></html>";
			MostarMensaje(strMensaje, "Materiales no permitidos");
		}
		return -1;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if (arg0.getSource() == textFieldCodigo) {
			textFieldCodigo.selectAll();
		} else if (arg0.getSource() == textFieldCantidad) {
			textFieldCantidad.selectAll();
		} else if (arg0.getSource() == textFieldPorcentajeDescuento) {
			textFieldPorcentajeDescuento.selectAll();
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
	}

	public void setTitulo(String titulo) {
		lblTitulo.setText(titulo);
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getBloqueoEntrega() {
		return bloqueoEntrega;
	}

	public void setBloqueoEntrega(String bloqueoEntrega) {
		this.bloqueoEntrega = bloqueoEntrega;
	}

	public String getNumeroPedidoCliente() {
		return numeroPedidoCliente;
	}

	public void setNumeroPedidoCliente(String numeroPedidoCliente) {
		this.numeroPedidoCliente = numeroPedidoCliente;
		txtNPedCliente.setText(numeroPedidoCliente);
	}

	public BeanCondicionExpedicion getCondicionExpedicion() {
		return (BeanCondicionExpedicion) cmbCondExped.getSelectedItem();
	}

	public String getDestinatario() {
		return txtDestinatario.getText();
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getClaseRiesgo() {
		return claseRiesgo;
	}

	public void setClaseRiesgo(String claseRiesgo) {
		this.claseRiesgo = claseRiesgo;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(String limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public String getCondicionPago() {
		return condicionPago;
	}

	public void setCondicionPago(String condicionPago) {
		this.condicionPago = condicionPago;
	}

	public String getOrganizacionVentas() {
		return organizacionVentas;
	}

	public void setOrganizacionVentas(String organizacionVentas) {
		this.organizacionVentas = organizacionVentas;
	}

	public String getCanalDistribucion() {
		return canalDistribucion;
	}

	public void setCanalDistribucion(String canalDistribucion) {
		this.canalDistribucion = canalDistribucion;
	}

	public String getCodigoSector() {
		return codigoSector;
	}

	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}

	public void setCondicionExpedicion(String condicionExpedicion) {
		this.condicionExpedicion = condicionExpedicion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void internalFrameClosing(InternalFrameEvent e) {
		cerrar();
	}

	public void internalFrameClosed(InternalFrameEvent e) {
	}

	public void internalFrameOpened(InternalFrameEvent e) {
	}

	public void internalFrameIconified(InternalFrameEvent e) {
	}

	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	public void internalFrameActivated(InternalFrameEvent e) {
	}

	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	protected void imprimir(String titutlo) {
		boolean estaSincronizado = false;
		if (source.compareTo("0") == 0) {
			estaSincronizado = true;
		}
		BeanDato usuario = Promesa.datose.get(0);
		TicketOrden ticket = new TicketOrden(titutlo);
		ticket.setDireccion(Util.obtenerDireccionPromesa());
		ticket.setTelefono(Util.obtenerTelefonoPromesa());
		int cod = 0;
		try {
			cod = Integer.parseInt(codigoCliente);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			cod = 0;
		}
		ticket.setCodigoCliente("" + cod);
		ticket.setCliente(cod + "-" + nombreCliente);
		ticket.setDireccionCliente(direccionDestinatario);
		try {
			cod = Integer.parseInt(usuario.getStrCodigo());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			cod = 0;
		}
		ticket.setVendedor(cod + "-" + usuario.getStrUsuario());
		ticket.setFecha(Util.convierteFechaAFormatoDDMMMYYYY(new Date()));
		ticket.setCondicionesPago(descripcionCondicionPago);
		ticket.setNumeroPedido(txtCodigoPedido.getText());
		List<DetalleTicketOrden> detalles = new ArrayList<DetalleTicketOrden>();
		List<Item> items = mdlTblItems.obtenerTodosItems();
		// llenar items
		for (Item item : items) {
			String material = item.getCodigo();
			int cantidad = 0;
			double precio = 0d;
			double valor = 0d;
			try {
				cantidad = (int) Double.parseDouble(item.getCantidad());
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				cantidad = 0;
			}
			try {
				precio = item.getPrecioNeto();
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				precio = 0d;
			}
			try {
				valor = item.getValorNeto();
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			if (!material.isEmpty()) {
				DetalleTicketOrden detalle = new DetalleTicketOrden(material, item.getDenominacion(), item.getTipo(),
						cantidad, precio, valor);
				detalles.add(detalle);
			}
		}
		// ------------
		ticket.setDetalles(detalles);
		ReporteTicket reporte = new ReporteTicket(ticket, estaSincronizado);
		reporte.generarReportePedido();
	}

	protected void imprimirDPP350(String titulo) {
		@SuppressWarnings("unused")
		boolean estaSincronizado = false;
		if (source.compareTo("0") == 0) {
			estaSincronizado = true;
		}
		BeanDato usuario = Promesa.datose.get(0);
		ReportePedido ticket = new ReportePedido();
		ticket.setTitulo(titulo);
		ticket.setDireccion(Util.obtenerDireccionPromesa());
		ticket.setTelefono(Util.obtenerTelefonoPromesa());
		int cod = 0;
		try {
			cod = Integer.parseInt(codigoCliente);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			cod = 0;
		}
		ticket.setCodigoCliente("" + cod);
		ticket.setCliente(cod + "-" + nombreCliente);
		ticket.setDireccionCliente(direccionDestinatario);
		try {
			cod = Integer.parseInt(usuario.getStrCodigo());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			cod = 0;
		}
		ticket.setVendedor(cod + "-" + usuario.getStrUsuario());
		ticket.setFecha(Util.convierteFechaAFormatoDDMMMYYYY(new Date()));
		ticket.setCondicionesPago(descripcionCondicionPago);
		ticket.setNumeroPedido(txtCodigoPedido.getText());
		List<DetalleTicketOrden> detalles = new ArrayList<DetalleTicketOrden>();
		List<Item> items = mdlTblItems.obtenerTodosItems();
		// llenar items
		for (Item item : items) {
			String material = item.getCodigo();
			int cantidad = 0;
			double precio = 0d;
			double valor = 0d;
			try {
				cantidad = (int) Double.parseDouble(item.getCantidad());
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				cantidad = 0;
			}
			try {
				precio = Double.parseDouble(Util.formatearNumero(item.getPrecioNeto()));
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				precio = 0d;
			}
			try {
				valor = Double.parseDouble(Util.formatearNumero(item.getValorNeto()));
			} catch (NumberFormatException e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			if (!material.isEmpty()) {
				DetalleTicketOrden detalle = new DetalleTicketOrden(material, item.getDenominacion(), item.getTipo(),
						cantidad, precio, valor);
				detalles.add(detalle);
			}
		}
		ticket.setTotal(Double.parseDouble(txtValorNeto.getText()));
		ticket.setDetalles(detalles);
		ticket.imprimir();
	}

	protected void btnEliminarPedidoActionPerformed(ActionEvent evt) {
		if (tblPedidos.getCellEditor() != null) {
			tblPedidos.getCellEditor().stopCellEditing();
		}
		int fila = tblPedidos.getSelectedRow();
		if (fila > -1) {
			mdlTblItems.borrarItem(fila);
			tblPedidos.updateUI();
			txtValorNeto.setText("" + Util.formatearNumero(mdlTblItems.getValorNeto()));
			calcularSaldoPendiente();
			calcularMarcasEstrategicas();
		} else {
			JOptionPane.showMessageDialog(null, "Por favor seleccione un item de la tabla.", "Mensaje",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	protected BeanCliente obtenerCliente(String codigo) {
		BeanCliente cliente = null;
		SqlClienteImpl sql = new SqlClienteImpl();
		cliente = sql.buscarCliente(codigo);
		return cliente;
	}

	protected void consultaDinamica(final int mn) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				DConsultaMateriales2 dlgConsulta = null;
				boolean fallo = false;
				try {
					BeanCliente cliente = obtenerCliente(codigoCliente);
					BeanAgenda agenda = new BeanAgenda();
					agenda.setStrCodigoCliente(codigoCliente);
					agenda.setStrNombreCliente(nombreCliente);
					cliente.setStrCondicionPagoDefecto(condicionPago);
					if (cliente != null) {
						
						try{
							dlgConsulta = new DConsultaMateriales2(Promesa.datose, cliente, instancia,
									Promesa.getInstance(), true, agenda, txtCondPago.getText(),mn);
						}catch (NullPointerException nulle){
							System.out.println("DConsultaMateriales2");
						}
						
						
					}
				} catch (Exception ex) {
					fallo = true;
					Util.mostrarExcepcion(ex);
				} finally {
					bloqueador.dispose();
					if (!fallo) {
						if (tblPedidos.getCellEditor() != null) {
							tblPedidos.getCellEditor().stopCellEditing();
						}
						dlgConsulta.setVisible(true);
					}
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	protected abstract void finalizarTransaccion();

	protected void cerrar() {	
		if (editable) {
			int tipo = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la ventana?", "Confirmación",JOptionPane.YES_NO_OPTION);
			if (tipo == JOptionPane.OK_OPTION) {
				
				this.dispose();
				IMarcaEspecifica.getIinstance().clearCamposMarcaEstrategica();
				//IMarcaEspecifica.getIinstance().clearCamposIndicadores();
				Promesa.getInstance().setVentanaActiva(false);
				this.instance = null;
				Promesa.getInstance().regresarAMenu();
				String message = verificarVisitas("",codigoCliente);
				if(message.equals("")){
					
					IVentanaVisita tp = new IVentanaVisita(codigoCliente);
					tp.setVisible(true);
				}
			}
		} else {
			this.dispose();
			IMarcaEspecifica.getIinstance().clearCamposMarcaEstrategica();
			//IMarcaEspecifica.getIinstance().clearCamposIndicadores();
			Promesa.getInstance().setVentanaActiva(false);
			this.instance = null;
			Promesa.getInstance().regresarAMenu();
		}
		
	}
	
	public Object[][] getVentaCruzadaCategoria(String strCodigoCliente){
		Object[][] rows = null;
		List<BeanVentaCruzada> colVentCrz = new ArrayList();
		SqlMaterialImpl impl = new SqlMaterialImpl();
		colVentCrz = impl.getListVentaCruzadaCliente(strCodigoCliente);
		rows = new Object[colVentCrz.size()+1][4];
		int i=0;
		Double dblTotalVentaReal = 0d;
		int intTotalOportunidad = 0;
		int intTotalCumpl = 0;
		for(BeanVentaCruzada vtacrz: colVentCrz){
			rows[i][0] = vtacrz.getStrCategoria();
			rows[i][1] = vtacrz.getDblVentaReal();
			dblTotalVentaReal += vtacrz.getDblVentaReal();
			rows[i][2] = vtacrz.getDblOportunidad();
			intTotalOportunidad += vtacrz.getDblOportunidad()>0?1:0;
			Double dblCumpl = (vtacrz.getDblVentaReal()/vtacrz.getDblObjetivo())*100;
			rows[i][3] = String.format("%.2f",dblCumpl)+"%";
			intTotalCumpl += dblCumpl>0?1:0;
			i++;
		}
		
		rows[i][0] = "TOTAL";
		rows[i][1] = dblTotalVentaReal;
		rows[i][2] = intTotalOportunidad;
		Double dblPromCumpl = (double) ((intTotalCumpl/i)*100);
		rows[i][3] = String.format("%.2f",dblPromCumpl)+"%";;
		return rows;
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
		if(listaAgenda.size()!=0)
		if(listaAgenda.size()>1||util.convierteStringaDate(listaAgenda.get(0).getBEGDA()).after(new Date())){
			strMensaje="<html>Las proximas visitas son:";
			List<Date> fecha = new ArrayList<Date>();
			for(int i=0;i<listaAgenda.size();i++){
				Date date = util.convierteStringaDate(listaAgenda.get(i).getBEGDA());
				if(date.after(util.convierteStringaDate(strbadate))){
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
				if(fecha.get(i).after(util.convierteStringaDate(strbadate))){
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

	private Double calculaCantidadPedidoClaseCondicion(List<Item> listaItem, Item i) {
		double dblRetorno = 0;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("") && item.getCantidad() != null
					&& !item.getCantidad().equals("") && item.getStrCondCalc4() != null && i.getStrCondCalc4() != null
					&& item.getStrCondCalc4().equals(i.getStrCondCalc4())) {
				try {
					dblRetorno += Double.parseDouble(item.getCantidad());
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
					&& item.getStrCondCalc4().equals(i.getStrCondCalc4()) && item.getStrNroCond4() != null
					&& i.getStrNroCond4() != null && item.getStrNroCond4().trim().equals(i.getStrNroCond4().trim())) {
				try {
					dblRetorno += Double.parseDouble(item.getCantidad());
				} catch (Exception e) {
				}
			}
		}
		return new Double(dblRetorno);
	}

	@SuppressWarnings("unused")
	private Double calculaImportePedidoClaseCondicion1x2x(List<Item> listaItem, Item i) {
		double dblRetorno = 0;
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = null;
				beanMaterial = sqlMateriales.getMaterial(item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
					/*
					 * agregado para calcular el importe considerando los
					 * descuentos anteriores
					 */
					Double dblPrecioTemp = new Double(beanMaterial.getPrice_1());
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
				try {
					dblRetorno += (dblCantidad.doubleValue() * dblPrecio.doubleValue());
				} catch (Exception e) {
				}
			}
		}
		return new Double(dblRetorno);
	}

	private Double calculaImportePedidoClaseCondicion1x2x2(List<Item> items, Item i,
			List<BeanMaterial> materialesGrilla) {
		double dblRetorno = 0;
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : items) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = getMaterialDeGrilla(materialesGrilla, item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
					/*
					 * agregado para calcular el importe considerando los
					 * descuentos anteriores
					 */
					// Double dblPrecioTemp = new
					// Double(beanMaterial.getPrice_1());
					Double dblPrecioTemp = new Double(item.getPrecioBase());
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
						&& !item.getCantidad().equals("") && item.getStrCondCalc3() != null
						&& i.getStrCondCalc3() != null && item.getStrCondCalc3().equals(i.getStrCondCalc3())
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

	private Double calculaImportePedidoClaseCondicion3x(List<Item> listaItem, Item i) {
		double dblRetorno = 0;
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = null;
				beanMaterial = sqlMateriales.getMaterial(item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
					/*
					 * agregado para calcular el importe considerando los
					 * descuentos anteriores
					 */
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
					dblPrecio = dblPrecioTemp;
				}
				if (item.getCantidad() != null && !item.getCantidad().equals("")) {
					dblCantidad = new Double(item.getCantidad());
				}

				try {
					dblRetorno += (dblCantidad.doubleValue() * dblPrecio.doubleValue());
				} catch (Exception e) {
				}
			}
		}
		return new Double(dblRetorno);
	}

	@SuppressWarnings("unused")
	private Double calculaImportePedido4x(List<Item> listaItem, Item i) {
		double dblRetorno = 0;
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : listaItem) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = null;
				beanMaterial = sqlMateriales.getMaterial(item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
					/*
					 * agregado para calcular el importe considerando los
					 * descuentos anteriores
					 */
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

	private Double calculaImportePedido4x2(List<Item> items, Item i, List<BeanMaterial> materialesGrilla) {
		double dblRetorno = 0;
		Double dblCantidad = null;
		Double dblPrecio = null;
		for (Item item : items) {
			if (item.getCodigo() != null && !item.getCodigo().equals("")) {
				dblCantidad = new Double(0);
				BeanMaterial beanMaterial = null;
				beanMaterial = getMaterialDeGrilla(materialesGrilla, item.getCodigo());
				if (beanMaterial != null && beanMaterial.getPrice_1() != null) {
					/*
					 * agregado para calcular el importe considerando los
					 * descuentos anteriores
					 */
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
					// ///dblPrecio = new Double(beanMaterial.getPrice_1());
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

	@SuppressWarnings("unused")
	private void setearCondicionDescuentosItems1x2x(List<Item> lista) {
		String codCliente = codigoCliente;
		String codCondPago = txtCondPago.getText();
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		for (Item i : lista) {
			if (i.getCodigo() != null && !i.getCodigo().equals("")) {
				// ///CONDICIONES COMERCIALES 1X
				List<BeanClaseMaterial> listaClasesMaterial = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
				BeanCondicionComercial1 b1 = new BeanCondicionComercial1();
				b1.setStrCondicionPago(codCondPago);
				b1.setStrCliente(codCliente);
				b1.setListaStrClaseMaterial(listaClasesMaterial);
				List<BeanCondicionComercial1> listaCondiciones1 = sqlMateriales.listarCondicion1(b1);
				if (listaCondiciones1 != null && listaCondiciones1.size() == 1) {
					i.setStrCondCalc1(listaCondiciones1.get(0).getStrClaseCond());
					i.setDblDesc1(listaCondiciones1.get(0).getDblImporte() / 10);
				} else {
					i.setStrCondCalc1("");
					i.setDblDesc1(new Double(0));
				}
				// ///CONDICIONES COMERCIALES 1X
				// ///CONDICIONES COMERCIALES 2X
				BeanCondicionComercial2 b2 = new BeanCondicionComercial2();
				SqlClienteImpl sqlCliente = new SqlClienteImpl();
				BeanCliente beanCliente = sqlCliente.buscarCliente(codCliente);
				b2.setStrGrupoCliente("" + Integer.parseInt(beanCliente.getStrCodigoTipologia()));
				b2.setStrCanal(beanCliente.getStrCodCanalDist());
				List<BeanCondicionComercial2> listaCondiciones2 = sqlMateriales.listarCondicion2(b2);
				if (listaCondiciones2 != null && listaCondiciones2.size() == 1) {
					i.setStrCondCalc2(listaCondiciones2.get(0).getStrClaseCond());
					i.setDblDesc2(listaCondiciones2.get(0).getDblImporte() / 10);
				} else {
					i.setStrCondCalc2("");
					i.setDblDesc2(new Double(0));
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void setearCondicionDescuentosItems3x(List<Item> lista) {
		String codCliente = codigoCliente;
		// String codCondPago = txtCondPago.getText();
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		SqlUsuarioImpl sqlUsr = new SqlUsuarioImpl();
		String strCodigoUsuario = Promesa.datose.get(0).getStrCodigo();
		BeanUsuario objUsuario = sqlUsr.obtenerDatosUsuario(strCodigoUsuario);
		for (Item i : lista) {
			if (i.getCodigo() != null && !i.getCodigo().equals("")) {
				// ///CONDICIONES COMERCIALES 3X
				BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
				// cliente
				b3.setStrCliente(codCliente);
				BeanMaterial objMat = sqlMateriales.getMaterial(i.getCodigo());
				List<BeanClaseMaterial> listClaMat = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
				if (listClaMat != null && listClaMat.size() == 1) {
					// clase material
					b3.setStrClase(listClaMat.get(0).getStrDescripcionClaseMaterial());
				} else {
					String strClaseMaterialTemp = "";
					for (BeanClaseMaterial beanClaseMaterial : listClaMat) {
						strClaseMaterialTemp += "-" + beanClaseMaterial.getStrDescripcionClaseMaterial();
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
				if (objUsuario.getStrDivision() != null && !objUsuario.getStrDivision().equals("")) {
					b3.setStrDivisionC("" + Integer.parseInt(objUsuario.getStrDivision()));
				} else {
					b3.setStrDivisionC("");
				}
				Double dblImportePedido = calculaImportePedidoClaseCondicion3x(lista, i);
				BeanCondicionComercial3x beanCondicion3 = null;
				Double dblDscto = new Double(0);

				List<BeanCondicionComercial3x> listaCondiciones3x = sqlMateriales.listarCondicion3(b3);
				if (listaCondiciones3x != null && listaCondiciones3x.size() == 1) {
					i.setStrCondCalc3(listaCondiciones3x.get(0).getStrClaseCond());
					beanCondicion3 = listaCondiciones3x.get(0);
					if (beanCondicion3.getStrEscala().equals("X")) {
						dblDscto = beanCondicion3.getDblImporteEscala(dblImportePedido) / 10;
					} else {
						dblDscto = beanCondicion3.getDblImporte() / 10;
					}
					i.setDblDesc3(dblDscto);
				} else {
					i.setStrCondCalc3("");
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void setearCondicionDescuentosItems4x(List<Item> lista) {
		String codCliente = codigoCliente;
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		SqlUsuarioImpl sqlUsr = new SqlUsuarioImpl();
		String strCodigoUsuario = Promesa.datose.get(0).getStrCodigo();
		BeanUsuario objUsuario = sqlUsr.obtenerDatosUsuario(strCodigoUsuario);
		for (Item i : lista) {
			if (i.getCodigo() != null && !i.getCodigo().equals("")) {
				// ///CONDICIONES COMERCIALES 3X
				BeanCondicionComercial3x b3 = new BeanCondicionComercial3x();
				// cliente
				b3.setStrCliente(codCliente);
				BeanMaterial objMat = sqlMateriales.getMaterial(i.getCodigo());
				List<BeanClaseMaterial> listClaMat = sqlMateriales.obtenerClaseMaterial(i.getCodigo());
				if (listClaMat != null && listClaMat.size() == 1) {
					// clase material
					b3.setStrClase(listClaMat.get(0).getStrDescripcionClaseMaterial());
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
				// b3.setStrDivisionC(objUsuario.getStrDivision());
				if (objUsuario.getStrDivision() != null && !objUsuario.getStrDivision().equals("")) {
					b3.setStrDivisionC("" + Integer.parseInt(objUsuario.getStrDivision()));
				} else {
					b3.setStrDivisionC("");
				}
				Double dblDscto = new Double(0);
				List<BeanCondicionComercial4x> listaCondiciones4x = sqlMateriales.listarCondicion4(b3);
				if (listaCondiciones4x != null && listaCondiciones4x.size() == 1) {
					i.setStrCondCalc4(listaCondiciones4x.get(0).getStrClaseCond());
				} else {
					i.setStrCondCalc4("");
				}

				Double dblCantidadPedido = calculaCantidadPedidoClaseCondicion(lista, i);
				BeanCondicionComercial4x beanCondicion4 = null;
				if (listaCondiciones4x != null && listaCondiciones4x.size() == 1) {
					beanCondicion4 = listaCondiciones4x.get(0);
					if (beanCondicion4.getStrEscala().equals("X")) {
						dblDscto = beanCondicion4.getDblImporteEscala(dblCantidadPedido) / 10;
					} else {
						dblDscto = beanCondicion4.getDblImporte() / 10;
					}
					i.setDblDesc4(dblDscto);

				} else {
					beanCondicion4 = new BeanCondicionComercial4x();
					beanCondicion4.setStrClaseCond("No aplica");
					i.setDblDesc4(new Double(0));
				}
			}
		}
	}

	// Variables declaration - do not modify
	protected javax.swing.JButton btnAniadir;
	protected javax.swing.JButton btnCancelar;
	protected javax.swing.JButton btnConsultaCapturaDinamica;
	protected javax.swing.JButton btnDetalleBloqEntrega;
	protected javax.swing.JButton btnDetalleCondPago;
	protected javax.swing.JButton btnDetalleDestinatario;
	protected javax.swing.JButton btnEliminar;
	protected javax.swing.JButton btnGuardarOrden;
	protected javax.swing.JButton btnGuiaVentas;
	protected javax.swing.JButton btnSimularTodo;
	protected javax.swing.JButton btnImprimirComprobante;
	protected javax.swing.JButton btnMaterialNuevo;
	protected javax.swing.JButton btnVentaCruzada;
	protected javax.swing.JButton btnVentaCruzadaCliente;
	protected javax.swing.JButton btnResultadoMarca;
	protected javax.swing.JPanel jPanel9;
	protected javax.swing.JPanel panelClaseRiesgo;
	protected javax.swing.JPanel panelClase;
	protected javax.swing.JLabel lblBloqEntrega;
	protected javax.swing.JLabel lblClasRiesgo;
	protected javax.swing.JLabel lblCondPago;
	protected javax.swing.JLabel lblDestinatario;
	protected javax.swing.JLabel lblMensajes;
	protected javax.swing.JLabel lblNPedCliente;
	protected javax.swing.JLabel lblNombre;
	protected javax.swing.JLabel lblTitulo;
	protected javax.swing.JLabel lblValorNeto;
	protected javax.swing.JLabel lblVtaMercaderia;
	protected javax.swing.JToolBar mnuToolBarMiddle;
	protected javax.swing.JToolBar mnuToolBarTop;
	protected javax.swing.JPanel pnlBackground;
	protected javax.swing.JPanel pnlBloqEntrega;
	protected javax.swing.JPanel pnlContenedor;
	protected javax.swing.JPanel pnlCuatro;
	protected javax.swing.JPanel pnlDatos;
	protected javax.swing.JPanel pnlDos;
	protected javax.swing.JPanel pnlInformacion;
	protected javax.swing.JPanel pnlInformacionGeneral;
	protected javax.swing.JPanel pnlLabels3;
	protected javax.swing.JPanel pnlLabels4;
	protected javax.swing.JPanel pnlLabelsDos;
	protected javax.swing.JPanel pnlLabelsUno;
	protected javax.swing.JPanel pnlTabla;
	protected javax.swing.JPanel pnlTexts4;
	protected javax.swing.JPanel pnlTextsDos;
	protected javax.swing.JPanel pnlTextsTres;
	protected javax.swing.JPanel pnlTextsUno;
	protected javax.swing.JPanel pnlTres;
	protected javax.swing.JPanel pnlUno;
	protected javax.swing.JPanel pnlVtaMercaderia;
	protected javax.swing.JPanel pnlWarnings;
	protected javax.swing.JLabel lblWarnigs;
	protected javax.swing.JScrollPane scroller;
	protected javax.swing.JToolBar.Separator separador2;
	protected javax.swing.JToolBar.Separator separador3;
	protected javax.swing.JToolBar.Separator separador4;
	protected javax.swing.JToolBar.Separator separador5;
	protected javax.swing.JToolBar.Separator separador6;
	protected javax.swing.JToolBar.Separator separador7;
	protected javax.swing.JToolBar.Separator separador8;
	protected javax.swing.JToolBar.Separator separador9;
	protected javax.swing.JTable tblPedidos;
	protected javax.swing.JTextField txtBloqEntrega;
	protected javax.swing.JTextField txtClasRiesgo;
	protected javax.swing.JTextField txtCodigoPedido;
	protected javax.swing.JTextField txtCondPago;
	protected javax.swing.JTextField txtDestinatario;
	protected javax.swing.JTextField txtNPedCliente;
	protected javax.swing.JTextField txtNombre;
	protected javax.swing.JTextField txtValorNeto;
	protected javax.swing.JTextField txtDisponible;
	protected javax.swing.JTextField txtLimCredito;
	@SuppressWarnings("rawtypes")
	protected javax.swing.JComboBox cmbCondExped;
	protected javax.swing.JComboBox cmbTipoGestion;
	protected ModeloTablaItems mdlTblItems = new ModeloTablaItems();
	protected RenderizadorTablaItems render = new RenderizadorTablaItems();
	// Sucursales
	protected JPopupMenu popupSucursales = null;
	protected JScrollPane scrollSucursales;
	protected JTable tablaSucursales = new JTable();
	protected String Columnas[] = { "CÓDIGO", "DIRECCIÓN" };

	protected DefaultTableModel modeloSucursales = new DefaultTableModel(new Object[][] {}, Columnas) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	// Bloqueos de entrega
	protected JPopupMenu popupBloqueo = null;
	protected JScrollPane scrollerBloqueo;
	protected JTable tablaBloqueos = new JTable();
	protected String Columnas3[] = { "CÓDIGO", "DESCRIPCIÓN" };
	protected DefaultTableModel modeloBloqueo = new DefaultTableModel(new Object[][] {}, Columnas3) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	// Condiciones de pago
	protected JPopupMenu popupCondiciones = null;
	protected JScrollPane scrollerCondiciones;
	protected JTable tablaCondiciones = new JTable();
	protected String Columnas2[] = { "CONDICIÓN DE PAGO", "ACLARACIÓN PROPIA" };
	protected SqlCondicionPagoImpl condiciones;
	protected DefaultTableModel modelCondiciones = new DefaultTableModel(new Object[][] {}, Columnas2) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	protected ListSelectionModel selectionModel;
	protected String codigoCliente = "";
	protected String nombreCliente = "";
	protected String direccionDestinatario = "";
	protected String descripcionCondicionPago = "";
	protected String bloqueoEntrega = "";
	protected String condicionExpedicion = "";
	protected String numeroPedidoCliente = "";
	protected String claseRiesgo = "";
	protected String limiteCredito = "";
	protected String disponibilidad = "";
	protected String condicionPago = "";
	protected String organizacionVentas = "";
	protected String canalDistribucion = "";
	protected String codigoSector = "";
	protected String tipoDocumento = "";
	protected String iva = null;
	protected int tipoVentana;
	protected BeanPedido pedido;
	protected JTextField textFieldCodigo;
	protected JTextField textFieldCantidad;
	protected JTextField textFieldPorcentajeDescuento;
	// End of variables declaration
	protected IPedidos instancia;
	protected String tituloImpresion;
	protected boolean editable = true;
	public HashMap<String, List<Item>> mapaItems;
	protected String source;
	private String strDispositivoImpresora;
	protected HashMap<String, String> marcadosParaActualizar;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblPresupuesto;
	private JLabel lblAcumReal;
	private JLabel lblSaldoPend;
	private JTextField txtPresupuesto;
	private JTextField txtAcumReal;
	private JTextField txtSaldoPendiente;
	private JButton btnImprimir;
	
	private JButton btnAgenda;
	private JPanel pnlPresupuesto;
	private JPanel pnlLbPresupuesto;
	private JLabel lblPresupuestoAnual;
	private JLabel lblPresupuestoMensual;
	private JLabel lblVentaAnual;
	private JPanel pnlbpPresupuestos;
	private JProgressBar pbAnual;
	private JProgressBar pbActual;
	private JProgressBar pbReal;
	private JPanel pnlActivar;
	private JLabel lblActivar;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel pnlValores;
	private JLabel lblValorAnual;
	private JLabel lblValorAfecha;
	private JLabel lblValorVtaAnual;
	private JLabel txtResultadoAnual;
	private JLabel txtEstadoAnual;
	private JPanel panel_3;
	private JLabel lblCrecimientoAnual;
	private JLabel lblPromePlus;
	private JPanel pnlCreimientoAnual;
	private JLabel lblNewLabel;
	private JPanel panel_4;
	private JLabel lblPromePlusValor;
	private JLabel lblNewLabel_1;
	private JPanel panel;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JPanel panel_9;
	private JLabel lblPorcentajeVtaAnual;
	private JPanel panel_10;
	private JLabel label_2;
	private JPanel panel_11;
	private JLabel lblNewLabel_2;
	private JLabel label_3;
	private JPanel panel_12;
	private JLabel lblSldoAcum;
	private JTextField txtSldoAcum;
	
	private void MostarMensaje(String strMensaje,String strTitle){
		try {
			JOptionPane optionpane = new JOptionPane(strMensaje,JOptionPane.PLAIN_MESSAGE,
					JOptionPane.DEFAULT_OPTION,null,null,null);//.showMessageDialog(this,visitas);
			
			JDialog dialog = optionpane.createDialog(strTitle);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			Util.mostrarExcepcion(e);
		}
	}
	
	private BeanMaterial getMaterialDeGrilla(List<BeanMaterial> lista, String strCodMaterial) {
		for (BeanMaterial beanMaterial : lista) {
			if (beanMaterial != null) {
				if (beanMaterial.getIdMaterial().equals(strCodMaterial))
					return beanMaterial;
			}
		}
		return null;
	}

	protected void agregarMaterialPorEventoTab01() {
		int fila = tblPedidos.getSelectedRow();
		SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
		SqlComboImpl sqlCombos = new SqlComboImpl();
		String codigo = (String) tblPedidos.getValueAt(fila, 1);
		if (codigo.equals("")) {
			return;
		} else if (mdlTblItems.yaEstaAgregado5(codigo)) {
			BeanMaterial mat = sqlMateriales.getMaterial(codigo);
			if(mat != null && !arrMarca.contains(mat.getNormt())){
				//strMarca=mat.getNormt();
				arrMarca.add(mat.getNormt());
				agregarMaterialNuevo(mat.getNormt(),fila,mat.getIdMaterial());
			}else{
				mdlTblItems.yaEstaAgregado4(codigo,fila);
			}
			
		}else{
			BeanMaterial mat = sqlMateriales.getMaterial(codigo);
			boolean vacio = false;
			if (mat != null && (mat.getMtart().equals("HAWA") || mat.getMtart().equals("ZCOM"))) {
				Item material = new Item();
				material.setDenominacion(sqlMateriales.obtenerNombreMaterial(codigo));
				material.setPosicion(mdlTblItems.getValueAt(fila, 0).toString());
				material.setCodigo(codigo);
				material.setUnidad(mat.getUn());
				material.setCantidad(mdlTblItems.getValueAt(fila, 2).toString());
				material.setCantidadConfirmada(mdlTblItems.getValueAt(fila, 3).toString());
				material.setStock("");
				material.setStrFech_Ing(mat.getStrFec_Ing());
				material.setPorcentajeDescuento(mdlTblItems.getValueAt(fila, 6).toString());
				material.setPrecioNeto(mdlTblItems.obtenerItem(fila).getPrecioNeto());
				material.setValorNeto(mdlTblItems.obtenerItem(fila).getValorNeto());
				material.setTipo(0); // Item normal
				material.setEditable(true);

				String tipoMaterial = mat.getTypeMat();
				SqlClienteImpl sql = new SqlClienteImpl();
				String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
				if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
					tipoMaterial = "B";
				} else if (tipoMaterial.compareTo("N") == 0
						|| (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
					tipoMaterial = "N";
				} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
					tipoMaterial = "P";
				} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
					tipoMaterial = "R";
				}
				material.setTipoMaterial(tipoMaterial);
				material.setSimulado(false);
				mdlTblItems.agregarItem(material, fila);
				if(!arrMarca.contains(mat.getNormt())){
					//strMarca=mat.getNormt();
					arrMarca.add(mat.getNormt());
					agregarMaterialNuevo(mat.getNormt(),fila,mat.getIdMaterial());
				}
				tblPedidos.updateUI();
			} else {
				Item combo = sqlCombos.getCombo(codigo);
				if (combo != null) {
					if (!mdlTblItems.existeCombo(codigo)) {
						combo.setCantidad("1");
						combo.setCantidadConfirmada(mdlTblItems.getValueAt(fila, 3).toString());
						combo.setPorcentajeDescuento(mdlTblItems.getValueAt(fila, 6).toString());
						mdlTblItems.agregarItem(combo, fila);
						tblPedidos.updateUI();
					}
				} else {
					if(mat!=null){
						String strMensaje = "<html><font color='red'>EL MATERIAL "+mat.getIdMaterial()+" CON TIPO DE MATERIAL "+mat.getMtart()+"</font></html>";
						MostarMensaje(strMensaje, "Materiales no permitidos");
							
					}else{
						vacio = true;
					}
				}
			}
			if (vacio) {
				lblMensajes.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
				lblMensajes.setText("EL MATERIAL SELECCIONADO NO EXISTE");
				textFieldCodigo.selectAll();
			}else{
				lblMensajes.setText("");
				actualizarPosiciones();
				tblPedidos.editCellAt(fila, 2);
				this.textFieldPorcentajeDescuento.requestFocusInWindow();
				this.textFieldPorcentajeDescuento.setEditable(true);
				selectionModel.setSelectionInterval(fila, fila);
			}
			}
			/*} else {
			tblPedidos.editCellAt(fila, 1);
			this.textFieldPorcentajeDescuento.requestFocusInWindow();
			this.textFieldPorcentajeDescuento.selectAll();
			this.textFieldPorcentajeDescuento.setEditable(true);
		}*/
	}

	private IMarcaEspecifica configurarMarcaEstrategica() {
		return IMarcaEspecifica.getIinstance();
	}

	/*private void cargarIndicadores() {
		Indicador i = Factory.createSqlIndicador().getIndicacorByCliente(Util.completarCeros(10, codigoCliente));
		IMarcaEspecifica.getIinstance().clearCamposIndicadores();
		if (i != null) {
			IMarcaEspecifica.getIinstance().setIndicador(i);
			IMarcaEspecifica.getIinstance().setDataIndicadores();
			IMarcaEspecifica.getIinstance().actualizarCampoRealIndicador();
		} 
	}*/

	private void cargarMarcaEstrategica() {
		List<MarcaEstrategica> marcas = Factory.createSqlMarcaEstrategica()
				.getMarcaEstrategicaByCliente(Util.completarCeros(10, codigoCliente));
		Util.quickSort(marcas, 0, marcas.size() - 1);
		IMarcaEspecifica.getIinstance().clearCamposMarcaEstrategica();
		if (marcas != null && marcas.size() > 0) {
			IMarcaEspecifica.getIinstance().addMarcaEstrategica(marcas);
		}
	}

	public static IPedidos getInstance() {
		return instance;
	}

	public String getCliente() {
		return this.codigoCliente;
	}
}
class FocusGrabber implements Runnable{
	private JComponent component;
	public FocusGrabber (JComponent component){
		this.component = component;
	}
	@Override
	public void run() {
		component.grabFocus();
		// TODO Apéndice de método generado automáticamente
		
	}
	
}