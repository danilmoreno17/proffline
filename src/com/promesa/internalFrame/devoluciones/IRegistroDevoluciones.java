package com.promesa.internalFrame.devoluciones;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar.Separator;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

//import com.promesa.administracion.sql.impl.SqlDispositivoImpl;
import com.promesa.bean.BeanConexion;
import com.promesa.bean.BeanDato;
import com.promesa.devoluciones.bean.BeanMotivoDevolucion;
import com.promesa.devoluciones.sql.impl.SqlMotivoDevolucionImpl;
import com.promesa.dialogo.devoluciones.DialogoComentarioPedidoDevolucion;
import com.promesa.impresiones.ReporteTicket;
import com.promesa.impresiones.devoluciones.DetalleTicketDevolucion;
import com.promesa.impresiones.devoluciones.TicketDevolucion;
import com.promesa.impresiones.dpp350.TicketDevolucionesDPP350;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.internalFrame.pedidos.custom.ModeloTablaItems;
import com.promesa.internarlFrame.devoluciones.custom.ModeloConsultaDevoluciones;
import com.promesa.internarlFrame.devoluciones.custom.RenderConsultaDevoluciones;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanMensaje;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.bean.BeanPedidoPartners;
import com.promesa.pedidos.bean.BeanSede;
import com.promesa.pedidos.sql.impl.SqlBloqueoEntregaImpl;
import com.promesa.pedidos.sql.impl.SqlCondicionPagoImpl;
import com.promesa.pedidos.sql.impl.SqlSedeImpl;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SDevoluciones;
import com.promesa.util.Cmd;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;
import com.promesa.util.ValidadorEntradasDeTexto;

public class IRegistroDevoluciones extends javax.swing.JInternalFrame implements
		FocusListener, InternalFrameListener {

	//rrodriguez@csticorp.biz
	private static final long serialVersionUID = 1L;
	List<String[]> p;
	private Cmd objC;
	SDevoluciones objSAP;
	List<BeanMotivoDevolucion> listaDevoluciones = null;
	private String strPURCH_NO_C = "";
	private boolean pierdeFoco = false;
	private String facturaSRI;
	@SuppressWarnings("unused")
	private String[] strCantidad = null;
	private int row = -1;
	private String strCiudadCliente;
	private String facturaSap ;
	private String strFechaDevolucionSistema;
	private String fechaFacturaPedido;
	private String strDispositivoImpresora;
	private ModeloConsultaDevoluciones tblTablaModel;
	private List<String[]> listaPedidos ;// MARCELO MOYANO
	
	
//	public IRegistroDevoluciones(String codigoCliente, String nombreCliente, String claseRiesgo, String limiteCredito, String disponibilidad, String condicionPago, String codigoPedidoDevolucion, List<String[]> p) {
	public IRegistroDevoluciones(String codigoCliente, String nombreCliente, String claseRiesgo, String limiteCredito, String disponibilidad, String condicionPago, String codigoPedidoDevolucion, String nombreResponsable, String fecFactura,List<String[]> p) {
	//	Marcelo Moyano
	if (p.size() > 5) {
			String[] str = p.get(5);
			strPURCH_NO_C = str[40];
		}
		this.p = p;
		this.codigoCliente = codigoCliente;
		this.nombreCliente = nombreCliente;
		
		listaPedidos = new ArrayList<String[]>();
		
		initComponents();
		listaDevoluciones = new ArrayList<BeanMotivoDevolucion>();
		condiciones = new SqlCondicionPagoImpl();
		bloqueoEntregaImpl = new SqlBloqueoEntregaImpl();
		setTitle("Pedido de Devolución: [" + codigoCliente + "] - [" + nombreCliente + "]");

		SqlClienteImpl sqlCliente = new SqlClienteImpl();
		BeanCliente cliente = sqlCliente.buscarCliente(codigoCliente);

		instancia = this;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addInternalFrameListener(this);

		this.claseRiesgo = claseRiesgo;
		this.limiteCredito = limiteCredito;
		this.disponibilidad = disponibilidad;
		this.condicionPago = condicionPago;
		this.organizacionVentas = cliente.getStrCodOrgVentas();
		this.canalDistribucion = cliente.getStrCodCanalDist();
		this.codigoSector = cliente.getStrCodSector();
		this.txtCondPago.setText(condicionPago);
		this.txtDestinatario.setText(codigoCliente);
		this.txtNombre.setText(nombreCliente);
		this.txtClasRiesgo.setText(claseRiesgo);
		this.txtCondPago.setText(condicionPago);
		this.txtPersonaReportaDevolucion.setText(nombreResponsable);
		this.codigoPedidoDevolucion = codigoPedidoDevolucion;
		txtNombre.addFocusListener(this);
		txtClasRiesgo.addFocusListener(this);
		txtNombreContactoRetiro.addFocusListener(this);
		txtCodigoPedido.addFocusListener(this);
		txtBloqEntrega.addFocusListener(this);
		String[] cadena = p.get(1);
		facturaSRI = cadena[64];
		facturaSap = cadena[53];
		fechaFacturaPedido = Util.convierteFecha(cadena[77]);
		strFechaDevolucionSistema = Util.convierteFecha(cadena[45]);
 		strCantidad = p.get(6);
 		String[] strParners = p.get(2);
 		strCiudadCliente = strParners[14];
		ValidadorEntradasDeTexto validadorBultos = new ValidadorEntradasDeTexto(4, Constante.DECIMALES);
		ValidadorEntradasDeTexto validadorFud = new ValidadorEntradasDeTexto(10, Constante.DECIMALES);
		txtNroBultos.setDocument(validadorBultos);
		txtNroFud.setDocument(validadorFud);
		llenarTablaDestinatarios();
		llenarTablaDevoluciones();
		llenarTabla(); // Tabla Mayor
		llenarTablaMD(); // Tabla Menor
		establecerOyenteTeclaTab();
		strDispositivoImpresora = Util.verificarImpresora();
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	protected void initComponents() {
		pnlBackground = new javax.swing.JPanel();
		lblTitulo = new javax.swing.JLabel();
		pnlContenedor = new javax.swing.JPanel();
		mnuToolBarTop = new javax.swing.JToolBar();
		btnGuardarOrden = new javax.swing.JButton();
		btnCancelar = new javax.swing.JButton();
		btnImprimirComprobante = new javax.swing.JButton();
		btnAniadirComentario = new javax.swing.JButton();
		pnlDatos = new javax.swing.JPanel();
		separador = new javax.swing.JSeparator();
		pnlInformacion = new javax.swing.JPanel();
		pnlInformacionGeneral = new javax.swing.JPanel();
		pnlUno = new javax.swing.JPanel();
		pnlLabelsUno = new javax.swing.JPanel();
		lblVtaMercaderia = new javax.swing.JLabel();
		lblDestinatario = new javax.swing.JLabel();
		lblBloqEntrega = new javax.swing.JLabel();
		
		/*
		 *  Marcelo Moyano 10/05/2013 - 09:11
		 *  Codigo: PRO-2013-0024
		 *  Titulo: Inlcuir campos Adicionales en Pantalla de Devolucione
		 */
		lblNoGuiaTransporte = new javax.swing.JLabel();
		lblNroBultos = new javax.swing.JLabel();
		lblNombreContactoRetiro = new javax.swing.JLabel();
		lblNroFud = new javax.swing.JLabel();
		lblTpoOns = new javax.swing.JLabel();// (Dir, Ven)
		lblPersonaReportaDevolucion = new javax.swing.JLabel();
		lblPesoTotal = new JLabel();
		
		/*
		 *  Marcelo Moyano 10/05/2013 - 09:11
		 *  Codigo: PRO-2013-0024
		 *  Titulo: Inlcuir campos Adicionales en Pantalla de Devolucione
		 */
		txtNoGuiaTransporte = new javax.swing.JTextField();
		txtNroBultos = new javax.swing.JTextField();
		txtNombreContactoRetiro = new javax.swing.JTextField();
		cmbDirVen = new javax.swing.JComboBox();
		txtNroFud = new javax.swing.JTextField();
		txtPersonaReportaDevolucion = new javax.swing.JTextField();
		txtPesoTotal = new JTextField();
		
		pnlTextsUno = new javax.swing.JPanel();
		txtCodigoPedido = new javax.swing.JTextField();
		pnlVtaMercaderia = new javax.swing.JPanel();
		btnDetalleDestinatario = new javax.swing.JButton();
		txtDestinatario = new javax.swing.JTextField();
		pnlBloqEntrega = new javax.swing.JPanel();
		btnDetalleBloqEntrega = new javax.swing.JButton();
		txtBloqEntrega = new javax.swing.JTextField();
		pnlTres = new javax.swing.JPanel();
		pnlLabels3 = new javax.swing.JPanel();
		lblClasRiesgo = new javax.swing.JLabel();
		lblCondPago = new javax.swing.JLabel();
		pnlTextsTres = new javax.swing.JPanel();
		txtClasRiesgo = new javax.swing.JTextField();
		jPanel9 = new javax.swing.JPanel();
		btnDetalleCondPago = new javax.swing.JButton();
		txtCondPago = new javax.swing.JTextField();
		pnlCuatro = new javax.swing.JPanel();
		pnlLabels4 = new javax.swing.JPanel();
		lblValorNeto = new javax.swing.JLabel();
		pnlTexts4 = new javax.swing.JPanel();
		txtValorNeto = new javax.swing.JTextField();
		pnlDos = new javax.swing.JPanel();
		pnlLabelsDos = new javax.swing.JPanel();
		lblNPedCliente = new javax.swing.JLabel();
		lblNombre = new javax.swing.JLabel();
		pnlTextsDos = new javax.swing.JPanel();
		txtNPedCliente = new javax.swing.JTextField();
		txtNombre = new javax.swing.JTextField();
		pnlTabla = new javax.swing.JPanel();
		mnuToolBarMiddle = new javax.swing.JToolBar();
		btnAniadir = new javax.swing.JButton();
		separador2 = new javax.swing.JToolBar.Separator();
		btnEliminar = new javax.swing.JButton();
		separador3 = new javax.swing.JToolBar.Separator();
		separador4 = new javax.swing.JToolBar.Separator();
		separador5 = new javax.swing.JToolBar.Separator();
		lblMensajes = new javax.swing.JLabel();
		scroller = new javax.swing.JScrollPane();
		tblPedidos = new javax.swing.JTable();
		pnlWarnings = new javax.swing.JPanel();
		lblWarnigs = new javax.swing.JLabel();
		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);

		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/idevolucion.gif")));

		pnlBackground.setLayout(new java.awt.BorderLayout());

		lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18));
		pnlBackground.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

		pnlContenedor.setLayout(new java.awt.BorderLayout());

		mnuToolBarTop.setFloatable(false);
		mnuToolBarTop.setRollover(true);

		btnGuardarOrden.setText("Guardar");
		btnGuardarOrden.setFocusable(false);
		btnGuardarOrden.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnGuardarOrden.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnGuardarOrden.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnGuardarOrdenActionPerformed(evt);
			}
		});
		mnuToolBarTop.add(btnGuardarOrden);

		Separator separador7 = new javax.swing.JToolBar.Separator();
		mnuToolBarTop.add(separador7);

		btnCancelar.setText("Cancelar");
		btnCancelar.setFocusable(false);
		btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBarTop.add(btnCancelar);

		btnImprimirComprobante.setText("Imprimir");
		btnImprimirComprobante.setFocusable(false);
		btnImprimirComprobante.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnImprimirComprobante.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		separador8 = new javax.swing.JToolBar.Separator();
		mnuToolBarTop.add(separador8);
		mnuToolBarTop.add(btnImprimirComprobante);

		btnAniadirComentario.setText("Añadir comentario");
		btnAniadirComentario.setFocusable(false);
		btnAniadirComentario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnAniadirComentario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

		lblWarnigs.setForeground(new java.awt.Color(255, 0, 0));
		lblWarnigs.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 4));
		pnlWarnings.setLayout(new java.awt.BorderLayout());
		pnlWarnings.add(lblWarnigs, java.awt.BorderLayout.CENTER);
		pnlWarnings.add(mnuToolBarTop, java.awt.BorderLayout.PAGE_START);

		pnlContenedor.add(pnlWarnings, java.awt.BorderLayout.PAGE_START);

		pnlDatos.setLayout(new java.awt.BorderLayout());
		pnlDatos.add(separador, java.awt.BorderLayout.PAGE_START);

		pnlInformacionGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		pnlUno.setLayout(new java.awt.BorderLayout(5, 5));

		pnlLabelsUno.setLayout(new java.awt.GridLayout(5, 1));

		lblVtaMercaderia.setText("Vta. Mercadería:");
		pnlLabelsUno.add(lblVtaMercaderia);

		lblDestinatario.setText("Destinatario:");
		pnlLabelsUno.add(lblDestinatario);

		lblBloqEntrega.setText("Bloq. Entrega:");
		pnlLabelsUno.add(lblBloqEntrega);

		txtBloqEntrega.setText("10");
		
		lblNoGuiaTransporte.setText("<html>N°. Guia Transp.: <font color=\"red\">*</font></html>");// Marcelo Moyano
		pnlLabelsUno.add(lblNoGuiaTransporte);// 10/05/2013 - 09:48
		
		lblTpoOns.setText("Tipo:");
		pnlLabelsUno.add(lblTpoOns);

		pnlUno.add(pnlLabelsUno, java.awt.BorderLayout.LINE_START);

		pnlTextsUno.setLayout(new java.awt.GridLayout(5, 1, 5, 5));
		pnlTextsUno.add(txtCodigoPedido);

		pnlVtaMercaderia.setLayout(new java.awt.BorderLayout());

		btnDetalleDestinatario.setPreferredSize(new java.awt.Dimension(20, 9));
		btnDetalleDestinatario.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDetalleDestinatarioActionPerformed(evt);
			}
		});

		btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cerrar();
			}
		});

		pnlVtaMercaderia.add(btnDetalleDestinatario, java.awt.BorderLayout.LINE_END);
		pnlVtaMercaderia.add(txtDestinatario, java.awt.BorderLayout.CENTER);
		pnlTextsUno.add(pnlVtaMercaderia);

		pnlBloqEntrega.setLayout(new java.awt.BorderLayout());

		btnDetalleBloqEntrega.setPreferredSize(new java.awt.Dimension(20, 9));
		btnDetalleBloqEntrega.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDetalleBloqEntregaActionPerformed(evt);
			}
		});
		pnlBloqEntrega.add(btnDetalleBloqEntrega, java.awt.BorderLayout.LINE_END);
		pnlBloqEntrega.add(txtBloqEntrega, java.awt.BorderLayout.CENTER);

		pnlTextsUno.add(pnlBloqEntrega);
		pnlTextsUno.add(txtNoGuiaTransporte);//Marcelo Moyano 10/05/2013 - 10:47

		cmbDirVen.addItem("");
		cmbDirVen.addItem("DIR");
		cmbDirVen.addItem("VEN");
		pnlTextsUno.add(cmbDirVen);//Marcelo Moyano 10/05/2013 - 10:47

		pnlUno.add(pnlTextsUno, java.awt.BorderLayout.CENTER);

		pnlTres.setLayout(new java.awt.BorderLayout(5, 5));

		pnlLabels3.setMinimumSize(new java.awt.Dimension(10, 0));
		pnlLabels3.setPreferredSize(new java.awt.Dimension(80, 70));
		pnlLabels3.setLayout(new java.awt.GridLayout(5, 1, 5, 5));

		lblClasRiesgo.setText("Clas. Riesgo:");
		pnlLabels3.add(lblClasRiesgo);

		lblCondPago.setText("Cond. Pago:");
		pnlLabels3.add(lblCondPago);
		{
			jLabel1 = new JLabel();
			pnlLabels3.add(jLabel1);
			jLabel1.setText("<html>Mot. Dev.: <font color=\"red\">*</font></html>");
		}
		
		lblNombreContactoRetiro.setText("<html>Contacto: <font color=\"red\">*</font></html>");//Marcelo Moyano 10/05/2013 - 10:47
		pnlLabels3.add(lblNombreContactoRetiro);//Marcelo Moyano 10/05/2013 - 10:47
		lblPersonaReportaDevolucion.setText("Responsable:");//Marcelo Moyano 10/05/2013 - 12:22
		pnlLabels3.add(lblPersonaReportaDevolucion);//Marcelo Moyano 10/05/2013 - 12:22

		pnlTres.add(pnlLabels3, java.awt.BorderLayout.LINE_START);

		pnlTextsTres.setLayout(new java.awt.GridLayout(5, 1, 5, 5));
		pnlTextsTres.add(txtClasRiesgo);

		jPanel9.setLayout(new java.awt.BorderLayout());

		btnDetalleCondPago.setPreferredSize(new java.awt.Dimension(20, 9));
		btnDetalleCondPago.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDetalleCondPagoActionPerformed(evt);
			}
		});
		jPanel9.add(btnDetalleCondPago, java.awt.BorderLayout.LINE_END);
		jPanel9.add(txtCondPago, java.awt.BorderLayout.CENTER);

		pnlTextsTres.add(jPanel9);
		{
			jPanel1 = new JPanel();
			pnlTextsTres.add(jPanel1);
			BorderLayout jPanel1Layout = new BorderLayout();
			jPanel1.setLayout(jPanel1Layout);
			{
				jButton1 = new JButton();
				jButton1.setToolTipText("Pulsar");
				jPanel1.add(jButton1, BorderLayout.EAST);
				jButton1.setPreferredSize(new java.awt.Dimension(20, 9));
				jButton1.setEnabled(false);
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				txtMotivoDevolucion = new JTextField();
				txtMotivoDevolucion.setEditable(false);
				txtMotivoDevolucion.setToolTipText("Motivo de Devolución");
				jPanel1.add(txtMotivoDevolucion, BorderLayout.CENTER);
				String[] temporal = p.get(0);
				if (temporal[0].trim().equals("CASO")) {
					String[] t1 = p.get(1);
					txtMotivoDevolucion.setText(t1[27].trim());
					String[] t2 = p.get(p.size() - 1);
					for (int i = 1 + Integer.parseInt(t2[0]) + Integer.parseInt(t2[3]); i < 1 + Integer.parseInt(t2[0]) + Integer.parseInt(t2[3]) + Integer.parseInt(t2[2]); i++) {
						String[] t3 = p.get(i);
						txtCodigoPedido.setText(eCeros(t3[1]));
					}
				}
				txtMotivoDevolucion.setEditable(false);
				txtMotivoDevolucion.setPreferredSize(new java.awt.Dimension(31, 21));
				{
					jPopupMenu1 = new JPopupMenu();
					setComponentPopupMenu(txtMotivoDevolucion, jPopupMenu1);
					{
						jScrollPane1 = new JScrollPane();
						jPopupMenu1.add(jScrollPane1);
						{
							@SuppressWarnings("unused")
							TableModel jTable1Model = new DefaultTableModel(
									new String[][] { { "One", "Two" }, { "Three", "Four" } },
									new String[] { "Column 1", "Column 2" });
							jTable1 = new JTable();
							jScrollPane1.setViewportView(jTable1);
							jTable1.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jTable1MouseClicked(evt);
								}
							});
						}
					}
				}
			}
		}
		//Marcelo Moyano 10/05/2013 - 10:47
		pnlTextsTres.add(txtNombreContactoRetiro);
		txtPersonaReportaDevolucion.setEditable(false);
		pnlTextsTres.add(txtPersonaReportaDevolucion);
		
		pnlTres.add(pnlTextsTres, java.awt.BorderLayout.CENTER);

		pnlCuatro.setLayout(new java.awt.BorderLayout(5, 5));

		pnlLabels4.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

		lblValorNeto.setText("Valor Neto:");
		pnlLabels4.add(lblValorNeto);
		
		pnlLabels4.add(btnAniadirComentario);
		
		lblPesoTotal.setText("Peso Total (KG.):");
		pnlLabels4.add(lblPesoTotal);

		pnlCuatro.add(pnlLabels4, java.awt.BorderLayout.LINE_START);

		pnlTexts4.setLayout(new java.awt.GridLayout(3, 1, 5, 5));
		pnlTexts4.add(txtValorNeto);
		pnlTexts4.add(new JLabel());
		txtPesoTotal.setEditable(false);
		pnlTexts4.add(txtPesoTotal);

		pnlCuatro.add(pnlTexts4, java.awt.BorderLayout.CENTER);

		pnlDos.setLayout(new java.awt.BorderLayout(5, 5));

		pnlLabelsDos.setMaximumSize(new java.awt.Dimension(90, 32767));
		pnlLabelsDos.setMinimumSize(new java.awt.Dimension(90, 42));
		pnlLabelsDos.setPreferredSize(new java.awt.Dimension(90, 100));
		pnlLabelsDos.setLayout(new java.awt.GridLayout(5, 1));

		lblNPedCliente.setText("Nº ped. cliente:");
		lblNPedCliente.setVisible(false);
		lblNPedCliente.setPreferredSize(new java.awt.Dimension(60, 14));
		pnlLabelsDos.add(lblNPedCliente);

		lblNombre.setText("Nombre:");
		lblNombre.setPreferredSize(new java.awt.Dimension(60, 14));
		pnlLabelsDos.add(lblNombre);

		JLabel lblCondExped = new JLabel("<html>Sello: <font color=\"red\">*</font></html>");
		pnlLabelsDos.add(lblCondExped);
		
		lblNroBultos.setText("<html> Nro. Bultos.: <font color=\"red\">*</font></html>");
		pnlLabelsDos.add(lblNroBultos);
		
		lblNroFud.setText("<html> Nro. FUD.: <font color=\"red\">*</font></html>");
		pnlLabelsDos.add(lblNroFud);
		
		pnlDos.add(pnlLabelsDos, java.awt.BorderLayout.LINE_START);

		pnlTextsDos.setLayout(new java.awt.GridLayout(5, 1, 5, 5));
		txtNPedCliente.setEditable(false);
		txtNPedCliente.setVisible(false);
		pnlTextsDos.add(txtNPedCliente);
		pnlTextsDos.add(txtNombre);
		pnlDos.add(pnlTextsDos, java.awt.BorderLayout.CENTER);

		javax.swing.GroupLayout pnlInformacionGeneralLayout = new javax.swing.GroupLayout(pnlInformacionGeneral);
		pnlInformacionGeneral.setLayout(pnlInformacionGeneralLayout);
		pnlInformacionGeneralLayout.setHorizontalGroup(pnlInformacionGeneralLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlInformacionGeneralLayout.createSequentialGroup().addContainerGap()
				.addComponent(pnlUno, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlDos, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlTres, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlCuatro, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE).addContainerGap()));
		pnlInformacionGeneralLayout.setVerticalGroup(pnlInformacionGeneralLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlInformacionGeneralLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlInformacionGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(pnlCuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(pnlInformacionGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
				.addComponent(pnlTres, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
									   javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(pnlUno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
									  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(pnlDos, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pnlTabla.setLayout(new java.awt.BorderLayout(5, 5));

		mnuToolBarMiddle.setFloatable(false);
		mnuToolBarMiddle.setRollover(true);
		
		//Marcelo Moyano	07/03/2013 - 11:56 Comentar el boton añadir
//		btnAniadir.setText("Añadir");
//		btnAniadir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
//		btnAniadir.setFocusable(false);
//		btnAniadir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//		btnAniadir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//		mnuToolBarMiddle.add(btnAniadir);
//		mnuToolBarMiddle.add(separador2);

		btnAniadir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAniadirPedidoActionPerformed(evt);
			}
		});

		btnEliminar.setText("Eliminar");
		btnEliminar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnEliminar.setFocusable(false);
		btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBarMiddle.add(btnEliminar);

		lblMensajes.setText("");
		mnuToolBarMiddle.add(lblMensajes);

		pnlTabla.add(mnuToolBarMiddle, java.awt.BorderLayout.PAGE_START);

		tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		tblPedidos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scroller.setViewportView(tblPedidos);

		pnlTabla.add(scroller, java.awt.BorderLayout.CENTER);

		javax.swing.GroupLayout pnlInformacionLayout = new javax.swing.GroupLayout(pnlInformacion);
		pnlInformacion.setLayout(pnlInformacionLayout);
		pnlInformacionLayout.setHorizontalGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,pnlInformacionLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(pnlTabla, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
										668,Short.MAX_VALUE)
				.addComponent(pnlInformacionGeneral, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
													 javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		pnlInformacionLayout.setVerticalGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlInformacionLayout.createSequentialGroup().addContainerGap()
				.addComponent(pnlInformacionGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												     javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE).addContainerGap()));

		pnlDatos.add(pnlInformacion, java.awt.BorderLayout.CENTER);

		pnlContenedor.add(pnlDatos, java.awt.BorderLayout.CENTER);

		pnlBackground.add(pnlContenedor, java.awt.BorderLayout.CENTER);

		getContentPane().add(pnlBackground, java.awt.BorderLayout.CENTER);

		// CUSTOM
		btnImprimirComprobante.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/print.png")));
		btnGuardarOrden.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/guardar_32.png")));
		btnCancelar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/eliminar_24.gif")));
		btnImprimirComprobante.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnImprimirComprobante.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnGuardarOrden.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnGuardarOrden.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		mnuToolBarTop.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		lblTitulo.setOpaque(true);
		lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));

		lblTitulo.setBackground(new java.awt.Color(175, 200, 222));
		tblTablaModel = new ModeloConsultaDevoluciones();
		tblPedidos.setModel(tblTablaModel);
		tblPedidos.setDefaultRenderer(Object.class, render);
		tblPedidos.setRowHeight(Constante.ALTO_COLUMNAS);
		pack();
		/*for (int i = 0; i < 12; i++) {
			agregarItemVacio(-1);
		}*/
		tblPedidos.updateUI();
		selectionModel = tblPedidos.getSelectionModel();
		this.setAnchoColumnas();
		((DefaultTableCellRenderer) tblPedidos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		btnImprimirComprobante.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				btnImprimirComprobanteActionPerformed(evt);
			}
		});

		btnAniadirComentario.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BeanDato usuario = Promesa.datose.get(0);
				if (usuario.getStrModo().equals("1")) { //ONLINE} else {//OFFLINE}
					try {
						final DLocker bloqueador = new DLocker();
						Thread hilo = new Thread() {
							public void run() {
								BeanDato usuario = Promesa.datose.get(0);
								if (usuario.getStrModo().equals("1")) { // ONLINE
									DialogoComentarioPedidoDevolucion dlg = new DialogoComentarioPedidoDevolucion(Promesa.getInstance(), true, txtCodigoPedido.getText());
									bloqueador.dispose();
									dlg.setVisible(true);
								}
							}
						};
						hilo.start();
						bloqueador.setVisible(true);
					} catch (Exception e) {
						Mensaje.mostrarError(e.getMessage());
					}
				}else{
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_GUARDAR_COMENTARIO_MODO_ONLINE);
				}
			}
		});
		btnEliminar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEliminarPedidoActionPerformed(evt);
			}
		});
		tblPedidos.getTableHeader().setReorderingAllowed(false);
		txtValorNeto.setEditable(false);
		txtCodigoPedido.setEditable(false);
		txtClasRiesgo.setEditable(false);
		txtDestinatario.setEditable(false);
		txtNombre.setEditable(false);
		{
			txtSello = new JTextField();
			txtSello.setToolTipText("Añadir Sello");
			pnlTextsDos.add(txtSello);
		}
		pnlTextsDos.add(txtNroBultos);
		pnlTextsDos.add(txtNroFud);
		
		txtCondPago.setEditable(false);
		txtBloqEntrega.setEditable(false);

		btnImprimirComprobante.setEnabled(false);
		btnDetalleBloqEntrega.setEnabled(false);
		btnDetalleCondPago.setEnabled(false);
	}

	protected void btnSimularTodoActionPerformed(ActionEvent evt) {
		//simularPreciosTodosMateriales();
	}
	
	private void deshabilitarCamposInput(){
		txtNroBultos.setEditable(false);
		txtNoGuiaTransporte.setEditable(false);
		txtNroFud.setEditable(false);
		txtSello.setEditable(false);
		cmbDirVen.setEnabled(false);
		cmbDirVen.setEditable(false);
		txtNombreContactoRetiro.setEditable(false);
		textFieldCantidad.setEditable(false);
		btnEliminar.setEnabled(false);
	}

	protected void llenarTablaDevoluciones() {
		try {
			tablaDevolucion = new JTable(modeloDevolución);
			tablaDevolucion.getTableHeader().setReorderingAllowed(false);
			modeloDevolución.setRowCount(0);
			tablaDevolucion.getColumn("CÓDIGO").setMaxWidth(80);
			tablaDevolucion.getColumn("CÓDIGO").setMinWidth(80);
			tablaDevolucion.getColumn("CÓDIGO").setPreferredWidth(80);
			tablaDevolucion.getColumn("DESCRIPCIÓN").setMaxWidth(320);
			tablaDevolucion.getColumn("DESCRIPCIÓN").setMinWidth(320);
			tablaDevolucion.getColumn("DESCRIPCIÓN").setPreferredWidth(320);
			scrollDevolucion = new JScrollPane();
			scrollDevolucion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollDevolucion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollDevolucion.setViewportView(tablaDevolucion);
			scrollDevolucion.setPreferredSize(new Dimension(400, 100));
			popupDevolucion = new JPopupMenu();
			popupDevolucion.add(scrollDevolucion);
			tablaDevolucion.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					seleccionarDevolucion(evt);
				}
			});
			SqlMotivoDevolucionImpl devoluciones = new SqlMotivoDevolucionImpl();
			devoluciones.sMotivoDevolucion();
			listaDevoluciones = devoluciones.gMotivoDevolucion();
			for (BeanMotivoDevolucion beanDevolucion : listaDevoluciones) {
				String[] values = new String[] {beanDevolucion.getStrCodMotDev(), beanDevolucion.getStrMotDev() };
				modeloDevolución.addRow(values);
			}
		} catch (Exception exec) {
			exec.printStackTrace();
		}
	}

	// MÉTODO QUE LLENA TABLA (POPUP) DE MOTIVO DE DEVOLUCIONES
	protected void llenarTablaMD() {
		try {
			tablaMD = new JTable(modeloMD);
			tablaMD.getTableHeader().setReorderingAllowed(false);
			modeloMD.setRowCount(0);
			tablaMD.getColumn("POSICIÓN").setMaxWidth(80);
			tablaMD.getColumn("POSICIÓN").setMinWidth(80);
			tablaMD.getColumn("POSICIÓN").setPreferredWidth(80);
			tablaMD.getColumn("MATERIAL").setMaxWidth(100);
			tablaMD.getColumn("MATERIAL").setMinWidth(100);
			tablaMD.getColumn("MATERIAL").setPreferredWidth(100);
			tablaMD.getColumn("CANTIDAD").setMaxWidth(80);
			tablaMD.getColumn("CANTIDAD").setMinWidth(80);
			tablaMD.getColumn("CANTIDAD").setPreferredWidth(80);
			tablaMD.getColumn("UM").setMaxWidth(60);
			tablaMD.getColumn("UM").setMinWidth(60);
			tablaMD.getColumn("UM").setPreferredWidth(60);
			tablaMD.getColumn("DENOMINACIÓN").setMaxWidth(230);
			tablaMD.getColumn("DENOMINACIÓN").setMinWidth(230);
			tablaMD.getColumn("DENOMINACIÓN").setPreferredWidth(230);
			tablaMD.getColumn("PRC. NETO").setMaxWidth(125);
			tablaMD.getColumn("PRC. NETO").setMinWidth(125);
			tablaMD.getColumn("PRC. NETO").setPreferredWidth(125);
			tablaMD.getColumn("VALOR NETO").setMaxWidth(125);
			tablaMD.getColumn("VALOR NETO").setMinWidth(125);
			tablaMD.getColumn("VALOR NETO").setPreferredWidth(125);
			scrollMD = new JScrollPane();
			scrollMD.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollMD.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollMD.setViewportView(tablaMD);
			scrollMD.setPreferredSize(new Dimension(800, 100));
			popupMD = new JPopupMenu();
			popupMD.add(scrollMD);
			tablaMD.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					seleccionarMD(evt);
				}
			});
			String[] temporal = p.get(p.size() - 1);
			String[] t = p.get(0);
			if (t[0].trim().equals("CASO")) {
				for (int i = 1 + Integer.parseInt(temporal[0]) + Integer.parseInt(temporal[3]) + Integer.parseInt(temporal[2]); i < p.size() - 1; i++) {
					String[] t2 = p.get(i);
					String[] values = new String[] {
							t2[2], eCeros(t2[3]), t2[51], t2[19], t2[8], String.valueOf(redondeo(Double.parseDouble(t2[98]))), String.valueOf(redondeo(Double.parseDouble(t2[45])))};
					modeloMD.addRow(values);
				}
			} else {
				for (int i = 1 + Integer.parseInt(temporal[0]); i < p.size() - 1; i++) {
					String[] t2 = p.get(i);
					String[] values = new String[] {
							t2[2], eCeros(t2[3]), t2[51], t2[19], t2[8], String.valueOf(redondeo(Double.parseDouble(t2[98]))), String.valueOf(redondeo(Double.parseDouble(t2[45]))) };
					modeloMD.addRow(values);
				}
			}
		} catch (Exception exec) {
			exec.printStackTrace();
		}
	}
	/*
	 * MARCELO MOYANO
	 * 15/03/2014
	 */
	protected void calcularValorNeto2(){
		java.awt.EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int fila = row;
				if (fila > -1){
					for(int i = 0; i < tblTablaModel.getRowCount(); i++){
						if(i == fila){
							Item itemsModelo = tblTablaModel.getItems(i);
							String[] itemsReal = listaPedidos.get(i);
							double cantidadOriginal = Double.parseDouble(itemsReal[51]);
							double cantidad = Double.parseDouble(itemsModelo.getCantidad());
							if(cantidadOriginal > cantidad){
								double precioNeto = itemsModelo.getPrecioNeto() * cantidad;
								itemsModelo.setValorNeto(Double.parseDouble(Util.formatearNumero(precioNeto)));
							} else if(cantidadOriginal == cantidad){
								itemsModelo.setValorNeto(Double.parseDouble(itemsReal[45]));
							} else {
								itemsModelo.setCantidad("" + (int)cantidadOriginal);
							}
						}
					}
					calcularValoresTotales();
					tblPedidos.updateUI();
				}
			}
			
		});
	}
	
	//	Marcelo Moyano 27/05/2013
	protected void calcularValorNeto() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				int fila = row;
				if(fila > -1){
					int pos = fila + 6;
					String [] cadena = p.get(pos);
					String cantidadReal = cadena[51];
					double dCantidadReal = Double.parseDouble(cantidadReal);
					//String strCantidadDevuelta = textFieldCantidad.getText();
					String strCantidadDevuelta = (String) tblPedidos.getValueAt(fila, 2);
					double dCantidadDevuelta = Double.parseDouble(strCantidadDevuelta);
					if(dCantidadReal >= dCantidadDevuelta){
						String strPrecioNeto = tblPedidos.getValueAt(fila, 5).toString().trim();
						Double dValorNeto = 0d;
						try {
							dValorNeto = Double.parseDouble(strCantidadDevuelta) * Double.parseDouble(strPrecioNeto);
						}catch (Exception ex){
							dValorNeto = Double.parseDouble(cantidadReal) * Double.parseDouble(strPrecioNeto);
						}
						tblPedidos.setValueAt(String.valueOf(Util.formatearNumero(dValorNeto)), fila, 6);
						tblPedidos.updateUI();
						Double dValorNetoTotal = 0d;
						Double dPesoTotal = 0d;
						for(int i = 0; i < tblPedidos.getRowCount(); i++){
							String cantidad = tblPedidos.getValueAt(i, 2).toString().trim();
							String strValorNeto = tblPedidos.getValueAt(i, 6).toString().trim();
							
							dValorNetoTotal +=  Double.parseDouble(strValorNeto);
							String PesoUnitario = tblPedidos.getValueAt(i, 7).toString().trim();
							dPesoTotal +=  Double.parseDouble(cantidad) * Double.parseDouble(PesoUnitario);
						}
						txtPesoTotal.setText(String.valueOf(Util.formatearNumero(dPesoTotal)));
						txtValorNeto.setText(String.valueOf(Util.formatearNumero(dValorNetoTotal)));
					} /*else if(dCantidadReal == dCantidadDevuelta) {
						Item items = tblTablaModel.getItems(fila);
						int posicion = Util.sumarDigitos(items.getPosicion());
						System.out.println("Posicion: " + posicion);
						String[] t2 = p.get(5 + posicion);
						Double dValorNeto = Double.parseDouble(t2[45]);
						tblPedidos.setValueAt(String.valueOf(Util.formatearNumero(dValorNeto)), fila, 6);
						tblPedidos.updateUI();
						
						Double dValorNetoTotal = 0d;
						Double dPesoTotal = 0d;
						for(int i = 0; i < tblPedidos.getRowCount(); i++){
							String cantidad = tblPedidos.getValueAt(i, 2).toString().trim();
							String strValorNeto = tblPedidos.getValueAt(i, 6).toString().trim();
							
							dValorNetoTotal +=  Double.parseDouble(strValorNeto);
							String PesoUnitario = tblPedidos.getValueAt(i, 7).toString().trim();
							dPesoTotal +=  Double.parseDouble(cantidad) * Double.parseDouble(PesoUnitario);
						}
						txtPesoTotal.setText(String.valueOf(Util.formatearNumero(dPesoTotal)));
						txtValorNeto.setText(String.valueOf(Util.formatearNumero(dValorNetoTotal)));
						
					} */else {
						int icantidadReal = (int) dCantidadReal;
						textFieldCantidad.setText("" + icantidadReal);
						tblPedidos.setValueAt(String.valueOf(icantidadReal), fila, 2);
						tblPedidos.updateUI();
					}
				}else {
					if(pierdeFoco)
						Mensaje.mostrarWarning("Debe seleccionar una devolución.");
					pierdeFoco = true;
				}
			}
		});
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
			exec.printStackTrace();
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
			scrollerCondiciones.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollerCondiciones.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollerCondiciones.setViewportView(tablaCondiciones);
			scrollerCondiciones.setPreferredSize(new Dimension(400, 200));
			popupCondiciones = new JPopupMenu();
			popupCondiciones.add(scrollerCondiciones);
			tablaCondiciones.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					seleccionarCondicionPago(evt);
				}
			});
			
		} catch (Exception exec) {
			exec.printStackTrace();
		}
	}

	protected void seleccionarDestinatario(MouseEvent evt) {
		int fila = 0;
		fila = tablaSucursales.getSelectedRow();
		popupSucursales.setVisible(false);
		txtDestinatario.setText(String.valueOf(tablaSucursales.getValueAt(fila, 0)));
		direccionDestinatario = String.valueOf(tablaSucursales.getValueAt(fila,	1));
	}

	protected void seleccionarMD(MouseEvent evt) {
		int fila = 0;
		fila = tablaMD.getSelectedRow();
		popupMD.setVisible(false);
		if (tblPedidos.getRowCount() > 0) {
			boolean std = false;
			for (int i = 0; i < tblPedidos.getRowCount(); i++) {
				if (tblPedidos.getValueAt(i, 1).toString().trim().equals(tablaMD.getValueAt(fila, 1).toString().trim())) {
					JOptionPane.showMessageDialog(null, "La fila seleccionada ya se encuentra añadida.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					std = true;
					break;
				}
			}
			if (!std) {
				DefaultTableModel dtm = (DefaultTableModel) tblPedidos.getModel();
				dtm.insertRow(tblPedidos.getRowCount(),
						new Object[] { tablaMD.getValueAt(fila, 0), tablaMD.getValueAt(fila, 1), tablaMD.getValueAt(fila, 2), tablaMD.getValueAt(fila, 3),
								tablaMD.getValueAt(fila, 4), tablaMD.getValueAt(fila, 5), tablaMD.getValueAt(fila, 6) });
			}
		} else {
			DefaultTableModel dtm = (DefaultTableModel) tblPedidos.getModel();
			dtm.insertRow(0, new Object[] { tablaMD.getValueAt(fila, 0), tablaMD.getValueAt(fila, 1), tablaMD.getValueAt(fila, 2),
					tablaMD.getValueAt(fila, 3), tablaMD.getValueAt(fila, 4), tablaMD.getValueAt(fila, 5), tablaMD.getValueAt(fila, 6) });
		}
	}

	protected void seleccionarDevolucion(MouseEvent evt) {
		int fila = 0;
		fila = tablaDevolucion.getSelectedRow();
		popupDevolucion.setVisible(false);
		txtMotivoDevolucion.setText(String.valueOf(tablaDevolucion.getValueAt(fila, 0)));
		motivoDevolucion = String.valueOf(tablaDevolucion.getValueAt(fila, 1));
	}

	protected void seleccionarCondicionPago(MouseEvent evt) {
		int fila = 0;
		fila = tablaCondiciones.getSelectedRow();
		popupCondiciones.setVisible(false);
		txtCondPago.setText(String.valueOf(tablaCondiciones.getValueAt(fila, 0)));
		descripcionCondicionPago = String.valueOf(tablaCondiciones.getValueAt(fila, 1));
		condicionPago = String.valueOf(tablaCondiciones.getValueAt(fila, 0));
		// Llamar a la simulación
		simularPreciosTodosMateriales();
	}

	protected void seleccionarBloqueoEntrega(MouseEvent evt) {
		int fila = 0;
		fila = tablaBloqueos.getSelectedRow();
		popupBloqueo.setVisible(false);
		txtBloqEntrega.setText(String.valueOf(tablaBloqueos.getValueAt(fila, 0)));
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

	protected void setPopupDevoluciones(ActionEvent evt, JTextField txtMotDev, int x, int y) {
		txtMotDev.add(popupDevolucion);
		popupDevolucion.show(txtMotDev, x - 400 + txtMotDev.getWidth(), y);
		txtMotDev.requestFocus();
		popupDevolucion.setVisible(true);
	}

	protected void setPopupMD(ActionEvent evt, JButton btnAniadir, int x, int y) {
		popupMD.show(btnAniadir, x - 800 + btnAniadir.getWidth(), y);
		btnAniadir.requestFocus();
		popupMD.setVisible(true);
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
				anchoColumna = 65;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
			case 2:
			case 5:
				anchoColumna = 65;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 3:
				anchoColumna = 100;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 75;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}

	protected void cotizar() {

	}

	protected void guardar() {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) { //ONLINE} else {//OFFLINE}
			if (!txtMotivoDevolucion.getText().trim().equals(Constante.VACIO)) {
				if (!txtSello.getText().trim().equals(Constante.VACIO)) {
					if(!txtNoGuiaTransporte.getText().trim().equals(Constante.VACIO)){
						if(!txtNroBultos.getText().trim().equals(Constante.VACIO) ){
							if(!txtNombreContactoRetiro.getText().trim().equals(Constante.VACIO)){
//								if(!cmbDirVen.getSelectedItem().toString().trim().equals(Constante.VACIO)){
									if(!txtNroFud.getText().trim().equals(Constante.VACIO) ){
										if (tblPedidos.getRowCount() > 0) {
											final DLocker bloqueador = new DLocker();
											Thread hilo = new Thread() {
												public void run() {
													try {
														guardaPedidoDevoluciones();
														deshabilitarCamposInput();
														
													} finally {
														bloqueador.dispose();
													}
												}
											};
											hilo.start();
											bloqueador.setVisible(true);
										} else {
											Mensaje.mostrarError(Constante.MENSAJE_NO_EXISTE_MATERIAL);
										}
									}else{
										Mensaje.mostrarError(Constante.MENSAJE_CAMPO_VACIO_NUMERO_FUD);
									}
//								}else{
//									Mensaje.mostrarError(Constante.MENSAJE_CAMPO_VACIO_DIR_VEN);
//								}
							}else{
								Mensaje.mostrarError(Constante.MENSAJE_CAMPO_VACIO_NOMBRE_CONTACTO);
							}
						}else{
							Mensaje.mostrarError(Constante.MENSAJE_CAMPO_VACIO_NUMERO_BULTOS);
						}
					}else{
						Mensaje.mostrarError(Constante.MENSAJE_CAMPO_VACIO_GUIA_TRANSPORTE);
					}
				} else {
					Mensaje.mostrarError(Constante.MENSAJE_CAMPO_VACIO_SELLO);
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_CAMPO_VACIO_MOTIVO_DEVOLUCION);
			}
		}else{
			Mensaje.mostrarError(Constante.MENSAJE_ERROR_GUARDAR_DEVOLUCION_MODO_ONLINE);
		}
	}

	protected List<BeanPedido> llenarEstructuras() {
		List<BeanPedido> pedidos = new ArrayList<BeanPedido>();

		BeanDato usuario = Promesa.datose.get(0);
		BeanPedidoHeader header = new BeanPedidoHeader();
		BeanPedidoPartners partners = new BeanPedidoPartners();

		java.util.Date fechaActual = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		String fecha = sdf.format(fechaActual);
		String date = fecha;
		String bloqueoEntrega = txtBloqEntrega.getText();
		String numeroBulto = txtNroBultos.getText();
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
		@SuppressWarnings("unused")
		String REQ_DATE_H = date;
		String PURCH_DATE = date;
		String PMNTTRMS = condicionPago;
		String DLV_BLOCK = bloqueoEntrega;
		String PRICE_DATE = date;
		BeanConexion cn = new BeanConexion();
		String CREATED_BY = cn.getStrUsuarioSAP();
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
		header.setPURCH_NO_C(strPURCH_NO_C);
		header.setCREATED_BY(CREATED_BY);
		header.setORD_REASON(txtMotivoDevolucion.getText());
		
		/*
		 *  Marcelo Moyano 10/05/2013 - 11:15
		 *  Codigo: PRO-2013-0024
		 *  Titulo: Incluir campos Adicionales en Pantalla de Devoluciones
		 */
		String strGuiaTransporte = txtNoGuiaTransporte.getText();
		header.setREF_1(strGuiaTransporte);
		header.setDUN_COUNT(numeroBulto);
		String nombreContacto = txtNombreContactoRetiro.getText();
		header.setPURCH_NO_S(nombreContacto);
		String strDirVen = cmbDirVen.getSelectedItem().toString();
		header.setPO_SUPPLEM(strDirVen);
		String numeroFud = txtNroFud.getText();
		header.setCOLLECT_NO(numeroFud);
		header.setNAME(txtPersonaReportaDevolucion.getText());

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("AG", Util.completarCeros(10, codigoCliente));
		hm.put("WE", Util.completarCeros(10, txtDestinatario.getText()));
		hm.put("ZS", Util.obtenerCodigoSupervisor());
		hm.put("ZV", Util.completarCeros(10, usuario.getStrCodigo()));
		partners.setHm(hm);

		List<BeanPedidoDetalle> materialesNormal = new ArrayList<BeanPedidoDetalle>();
		for (int i = 0; i < tblPedidos.getRowCount(); i++) {
			String posicion = tblPedidos.getValueAt(i, 0).toString().trim();
			String material = tblPedidos.getValueAt(i, 1).toString().trim();
			String cantidad = tblPedidos.getValueAt(i, 2).toString().trim();
			
			BeanPedidoDetalle detalle = new BeanPedidoDetalle();
			detalle.setPosicion(posicion);
			detalle.setMaterial(material);
			detalle.setCantidad(cantidad);
			materialesNormal.add(detalle);
		}

		BeanPedido pedidoNormal = new BeanPedido();
		pedidoNormal.setTipo("N");
		pedidoNormal.setHeader(header);
		pedidoNormal.setPartners(partners);
		pedidoNormal.setDetalles(materialesNormal);
		pedidos.add(pedidoNormal);
		return pedidos;
	}

	@SuppressWarnings("static-access")
	private void guardaPedidoDevoluciones() {
		String[] temporal = p.get(p.size() - 1);
		boolean std = false;
		boolean bandera = true;
		for (int i = 1 + Integer.parseInt(temporal[0]) + Integer.parseInt(temporal[3]) + Integer.parseInt(temporal[2]); i < p.size() - 1; i++) {
			String[] t2 = p.get(i);
			for (int j = 0; j < tblPedidos.getRowCount(); j++) {
				if (tblPedidos.getValueAt(j, 1).toString().trim().equals(eCeros(t2[3]))) {
					if (!tblPedidos.getValueAt(j, 2).toString().trim().equals(t2[51].trim())) {
						try {
							double cantidadPedido = Double.parseDouble(tblPedidos.getValueAt(j, 2).toString().trim());
							if (cantidadPedido > Double.parseDouble(t2[51].trim())) {
								JOptionPane.showMessageDialog(null, "El material " + eCeros(t2[3]) + " tiene una cantidad mayor a la asignada.", "Mensaje", JOptionPane.WARNING_MESSAGE);
								std = true;
								bandera = false;
								break;
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "La cantidad a ingresar debe de ser un número válido.", "Mensaje", JOptionPane.WARNING_MESSAGE);
							std = true;
							bandera = false;
							break;
						}
					}
				}
			}
			if (!bandera) {
				break;
			}
		}
		if (!std) {
			objC = new Cmd();
			int tiempo = Integer.parseInt(objC.disponibilidadSAP().trim());
			if (tiempo > 0 && tiempo <= Promesa.MAX_DELAY) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						try {
							List<BeanMensaje> mensajes = new ArrayList<BeanMensaje>();
							List<BeanPedido> pedidos = llenarEstructuras();
							for (BeanPedido pedido : pedidos) {
								objSAP = new SDevoluciones();
								if (!pedido.getDetalles().isEmpty()) {
									try {
										pedido.setCodigoPedido(codigoPedidoDevolucion);
										pedido.getHeader().setStrSello(txtSello.getText().trim());
										String mensaje[] = objSAP.guardarDevolucion(pedido);
										if (mensaje != null) {
											BeanMensaje msg = new BeanMensaje("");
											Long codigo = 0l;
											try {
												codigo = Long.parseLong(mensaje[1]);
											} catch (NumberFormatException e) {
												codigo = -1l;
												msg.setPedido(pedido);
											}
											msg.setIdentificador(codigo);
											msg.setDescripcion(mensaje[2]);
											msg.setTipo(mensaje[0]);
											msg.setTipoMaterial(pedido.getTipo());
											mensajes.add(msg);
										}
									} catch (Exception e) {
										Util.mostrarExcepcion(e);
									}
								}
							}
							boolean habilitaBotonImpresion = true;
							if (mensajes.size() > 0) {
								for (BeanMensaje beanMensaje : mensajes) {
									System.out.println("--------------------------------------------------");
									System.out.println("Tipo : " + beanMensaje.getTipo());
									System.out.println("Descripción : " + beanMensaje.getDescripcion());
									System.out.println("Identificador : " + beanMensaje.getIdentificador());
									System.out.println("Tipo de material : " + beanMensaje.getTipoMaterial());
									System.out.println("--------------------------------------------------");
									if (beanMensaje.getTipo().equals("N")) {
										JOptionPane.showMessageDialog(Promesa.getInstance(), beanMensaje.getDescripcion(), "Exito", JOptionPane.INFORMATION_MESSAGE);
									} else if (beanMensaje.getTipo().equals("E")) {
										habilitaBotonImpresion = false;
										JOptionPane.showMessageDialog(Promesa.getInstance(), beanMensaje.getDescripcion(), "Error", JOptionPane.ERROR_MESSAGE);
									}
								}
							}
							if (mensajes.size() > 0 && habilitaBotonImpresion) {
								btnImprimirComprobante.setEnabled(true);
								btnGuardarOrden.setEnabled(false);
								btnAniadirComentario.setEnabled(false);
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
		}
	}

	protected void btnGuardarOrdenActionPerformed(java.awt.event.ActionEvent evt) {
		guardar();
		
	}

	protected void btnConsultaCapturaDinamicaActionPerformed(
			java.awt.event.ActionEvent evt) {
	}

	protected void btnGuiaVentasActionPerformed(java.awt.event.ActionEvent evt) {

	}

	protected void btnAniadirPedidoActionPerformed(ActionEvent evt) {
		setPopupMD(evt, btnAniadir, 0, 21);
	}

	/*public void agregarItemVacio(int pos) {
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
		item.setTipo(0); // Indica que no hay ningún item
		item.setSimulado(true);
		mdlTblItems.agregarItem(item, pos);
	}*/

	protected void establecerOyenteTeclaTab() {
		textFieldCantidad = new JTextField();
		textFieldCantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		ValidadorEntradasDeTexto validadorCantidades = new ValidadorEntradasDeTexto(10, Constante.ENTEROS);
		TableColumn columnaCantidad = tblPedidos.getColumnModel().getColumn(2);
		textFieldCantidad.setDocument(validadorCantidades);
		textFieldCantidad.addFocusListener(this);
		textFieldCantidad.setBorder(BorderFactory.createEmptyBorder());
		DefaultCellEditor singleclickCantidad = new DefaultCellEditor(textFieldCantidad);
		singleclickCantidad.setClickCountToStart(1);
		columnaCantidad.setCellEditor(singleclickCantidad);
	}

	protected void simularPreciosMateriales() {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {// DESDE SAP
		} else {
		}
	}

	protected void simularPreciosTodosMateriales() {
		if (tblPedidos.getCellEditor() != null) {
			tblPedidos.getCellEditor().stopCellEditing();
		}
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {// DESDE SAP
		} else {
		}
		txtValorNeto.setText("" + mdlTblItems.getValorNeto());
	}
	
	/*protected void llenarTabla(final List<String[]> items) {
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
					item.setPosicion(strings[0]);
					item.setCodigo(strings[1]);
					item.setCantidad(Long.parseLong(strings[2]) + "");
					item.setCantidadConfirmada(strings[3]);
					item.setUnidad(strings[4]);
					// item.setPorcentajeDescuento(strings[5]);
					item.setDenominacion(strings[6]);
					iva = strings[10]; // GUARDA VALOR IVA
					Double precioNeto = 0d;
					try {
						precioNeto = Double.parseDouble(strings[7]);
					} catch (Exception e) {
						precioNeto = 0d;
					}
					Double valorNeto = 0d;
					try {
						valorNeto = Double.parseDouble(strings[8]);
					} catch (Exception e) {
						valorNeto = 0d;
					}
					item.setEditable(true);
					item.setPrecioNeto(precioNeto);
					item.setValorNeto(valorNeto);
					String categoria = strings[9];
					if (categoria.compareTo("ZP01") == 0) {
						item.setTipo(0);
					} else if (categoria.compareTo("ZPP1") == 0) {
						item.setTipo(1);
					} else if (categoria.compareTo("ZPH1") == 0) {
						int k = 0;
						try {
							k = Integer.parseInt(strings[2]);
						} catch (Exception e) {
						}
						item.setCantidadAMostrar(k);
						item.setTipo(2);
						item.setEditable(false);
					}
					item.setTipoMaterial(sql.getTipoMaterial(item.getCodigo()));
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
						}
						try {
							cantidadPadre = Integer.parseInt(i.getPadre().getCantidad());
						} catch (Exception e) {
						}
						if (cantidadPadre != 0) {
							cantidad /= cantidadPadre;
							i.setCantidad("" + cantidad);
						}
					}
				}
			}
		});
	}*/
/*
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
						pos = 0;
					}
					item.setPosicion(strings[0]);
					item.setCodigo(strings[1]);
					item.setCantidad(Long.parseLong(strings[2]) + "");
					item.setCantidadConfirmada(strings[3]);
					item.setUnidad(strings[4]);
					item.setDenominacion(strings[6]);
					iva = strings[10]; // GUARDA VALOR IVA
					Double precioNeto = 0d;
					try {
						precioNeto = Double.parseDouble(strings[7]);
					} catch (Exception e) {
						precioNeto = 0d;
					}
					Double valorNeto = 0d;
					try {
						valorNeto = Double.parseDouble(strings[8]);
					} catch (Exception e) {
						valorNeto = 0d;
					}
					item.setEditable(true);
					item.setPrecioNeto(precioNeto);
					item.setValorNeto(valorNeto);
					String categoria = strings[9];
					if (categoria.compareTo("ZP01") == 0) {
						item.setTipo(0);
					} else if (categoria.compareTo("ZPP1") == 0) {
						item.setTipo(1);
					} else if (categoria.compareTo("ZPH1") == 0) {
						int k = 0;
						try {
							k = Integer.parseInt(strings[2]);
						} catch (Exception e) {
						}
						item.setCantidadAMostrar(k);
						item.setTipo(2);
						item.setEditable(false);
					}
					item.setTipoMaterial(sql.getTipoMaterial(item.getCodigo()));
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
						}
						try {
							cantidadPadre = Integer.parseInt(i.getPadre().getCantidad());
						} catch (Exception e) {
						}
						if (cantidadPadre != 0) {
							cantidad /= cantidadPadre;
							i.setCantidad("" + cantidad);
						}
					}
				}
			}
		});
	}
*/
	@Override
	public void focusGained(FocusEvent arg0) {
		if (arg0.getSource() == textFieldCantidad) {
			int fila = tblPedidos.getSelectedRow();
			//row = fila;
			int pos = 0;
			String posicion = tblPedidos.getValueAt(fila, 0).toString();
			for(int i = 6; i < p.size(); i++){
				String[] cadena = p.get(i);
				String verifica = cadena[2];
				if(posicion.equals(verifica)){
					pos = i;
					break;
				}
			}
			String[] datoMaterial = p.get(pos);
			String hijo = datoMaterial[13];
			if(hijo.equals("000000")){
				textFieldCantidad.setEditable(true);
				textFieldCantidad.selectAll();
			}
			else 
				textFieldCantidad.setEditable(false);
		}
		if(arg0.getSource() == txtNombre){
			txtSello.requestFocusInWindow();
		}
		if(arg0.getSource() == txtClasRiesgo){
			txtNombreContactoRetiro.requestFocusInWindow();
		}
		if(arg0.getSource() == txtPersonaReportaDevolucion){
			btnAniadirComentario.setFocusable(true);
			btnAniadirComentario.requestFocus(true);
			btnAniadirComentario.requestFocus();
		}
		if(arg0.getSource() == txtCodigoPedido){
			btnDetalleDestinatario.requestFocusInWindow();
		}
		if(arg0.getSource() == txtBloqEntrega){
			txtNoGuiaTransporte.requestFocusInWindow();
		}
		if(arg0.getSource() == txtValorNeto){
			btnEliminar.requestFocusInWindow();
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (arg0.getSource() == textFieldCantidad) {
			row = tblPedidos.getSelectedRow();
			//calcularValorNeto();
			calcularValorNeto2();
		}
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
	
	private void btnImprimirComprobanteActionPerformed(java.awt.event.ActionEvent evt){
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) { //ONLINE} else {//OFFLINE}
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						if(Constante.IMPRESORA_NEW.equals(strDispositivoImpresora)){
							try {
								imprimirDPP350();
							} catch (Exception e) {
								imprimir();
							}
						} else {
							imprimir();
						}
					} catch (Exception e) {
						e.printStackTrace();
						Mensaje.mostrarError(e.getMessage());
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}else{
			Mensaje.mostrarError(Constante.MENSAJE_ERROR_IMPRIMIR_DEVOLUCION_MODO_ONLINE);
		}
	}

	protected void imprimir() {
		TicketDevolucion ticketDevolucion = new TicketDevolucion();
		ticketDevolucion.setDireccion(strCiudadCliente);
		ticketDevolucion.setTelefono(Util.obtenerTelefonoPromesa());
		ticketDevolucion.setCodigoCliente(codigoCliente);
		ticketDevolucion.setNombreCliente(nombreCliente);
		ticketDevolucion.setNumeroPedido(txtCodigoPedido.getText());
		ticketDevolucion.setGuiatransp(txtNoGuiaTransporte.getText().trim());
		ticketDevolucion.setNumeroBulto(txtNroBultos.getText().trim());
		ticketDevolucion.setTipoDirVen(cmbDirVen.getSelectedItem().toString().trim());
		ticketDevolucion.setContacto(txtNombreContactoRetiro.getText().trim());
		SqlMotivoDevolucionImpl devoluciones = new SqlMotivoDevolucionImpl();
		String strCodigoMotivoDevoluciones = txtMotivoDevolucion.getText().trim(); 
		ticketDevolucion.setMotivo(strCodigoMotivoDevoluciones + ": " + devoluciones.obtenerDescripcionMotivoDevolucion(strCodigoMotivoDevoluciones));
		ticketDevolucion.setDireccionDestinatario(direccionDestinatario);
		ticketDevolucion.setCodigoPedidoDevoluciones(codigoPedidoDevolucion);// numero de seguimiento
		ticketDevolucion.setFacturaSRI(facturaSRI);
		ticketDevolucion.setFacturaSAP(facturaSap);
		if(!txtBloqEntrega.getText().trim().equals("")){
			ticketDevolucion.setDescripcionBloqueEntrega(bloqueoEntregaImpl.obtenerDescripcionBloqueoEntrega(txtBloqEntrega.getText().trim()));
		}else{
			ticketDevolucion.setDescripcionBloqueEntrega("");
		}
		ticketDevolucion.setNumeroPedidoCliente(txtNPedCliente.getText().trim());
		ticketDevolucion.setSello(txtSello.getText().trim());
		ticketDevolucion.setStrFud(txtNroFud.getText().trim());
		ticketDevolucion.setClaseRiesgo(txtClasRiesgo.getText());
		if(!txtCondPago.getText().trim().equals("")){
			ticketDevolucion.setDescripcionCondicionPago(condiciones.obtenerDescripcionCondicionesPago(txtCondPago.getText().trim()));
		}else{
			ticketDevolucion.setDescripcionCondicionPago("");
		}
		ticketDevolucion.setDescripcionMotivoDevolucion("");
		for (BeanMotivoDevolucion beanDevolucion : listaDevoluciones) {
			if(beanDevolucion.getStrCodMotDev().equals(txtMotivoDevolucion.getText())){
				ticketDevolucion.setDescripcionMotivoDevolucion(beanDevolucion.getStrMotDev());
				break;
			}
		}
		ticketDevolucion.setValorNeto(Util.formatearNumero(txtValorNeto.getText()));
		ticketDevolucion.setFechaFactura(Util.convierteFechaPuntoDDMMYYYAFormatoBarraDDMMMYYYY(fechaFacturaPedido));
		SDevoluciones sDevoluciones = new SDevoluciones();
		ticketDevolucion.setCabeceraFormulario(sDevoluciones.leerComentarioPedidoDevolucion(txtCodigoPedido.getText(), "0001"));
		ticketDevolucion.setNotaCabecera(sDevoluciones.leerComentarioPedidoDevolucion(txtCodigoPedido.getText(), "0002"));
		
		List<DetalleTicketDevolucion> listaDetalleTicketDevolucion = new ArrayList<DetalleTicketDevolucion>();
		
		for (int j = 0; j < tblPedidos.getRowCount(); j++) {
			DetalleTicketDevolucion detalleTicketDevolucion = new DetalleTicketDevolucion();
			detalleTicketDevolucion.setPosicion(tblPedidos.getValueAt(j, 0).toString().trim());
			detalleTicketDevolucion.setMaterial(tblPedidos.getValueAt(j, 1).toString().trim());
			detalleTicketDevolucion.setCantidad(tblPedidos.getValueAt(j, 2).toString().trim());
			detalleTicketDevolucion.setUm(tblPedidos.getValueAt(j, 3).toString().trim());
			detalleTicketDevolucion.setDenominacion(tblPedidos.getValueAt(j, 4).toString().trim());
			detalleTicketDevolucion.setPrecioNeto(Util.formatearNumero(tblPedidos.getValueAt(j, 5).toString().trim()));
			detalleTicketDevolucion.setValorNeto(Util.formatearNumero(tblPedidos.getValueAt(j, 6).toString().trim()));
			listaDetalleTicketDevolucion.add(detalleTicketDevolucion);
		}
		
		ticketDevolucion.setListaDetalleTicketDevolucion(listaDetalleTicketDevolucion);
		ReporteTicket reporteTicket = new ReporteTicket(ticketDevolucion);
		reporteTicket.generarReporteDevolucion();
	}
	
	protected void imprimirDPP350() {
		TicketDevolucionesDPP350 ticketDevolucion = new TicketDevolucionesDPP350();
		ticketDevolucion.setDireccion(strCiudadCliente);
		ticketDevolucion.setTelefono(Util.obtenerTelefonoPromesa());
		ticketDevolucion.setCodigoCliente(codigoCliente);
		ticketDevolucion.setNombreCliente(nombreCliente);
		ticketDevolucion.setNumeroPedido(txtCodigoPedido.getText());
		ticketDevolucion.setGuiaTransp(txtNoGuiaTransporte.getText().trim());
		ticketDevolucion.setNumeroBulto(txtNroBultos.getText().trim());
		ticketDevolucion.setTipoDirVen(cmbDirVen.getSelectedItem().toString().trim());
		ticketDevolucion.setContacto(txtNombreContactoRetiro.getText().trim());
		SqlMotivoDevolucionImpl devoluciones = new SqlMotivoDevolucionImpl();
		String strCodigoMotivoDevoluciones = txtMotivoDevolucion.getText().trim(); 
		ticketDevolucion.setMotivo(strCodigoMotivoDevoluciones + ": " + devoluciones.obtenerDescripcionMotivoDevolucion(strCodigoMotivoDevoluciones));
		ticketDevolucion.setDireccionDestinatario(direccionDestinatario);
		ticketDevolucion.setCodigoPedidoDevoluciones(codigoPedidoDevolucion);// numero de seguimiento
		ticketDevolucion.setFacturaSRI(facturaSRI);
		ticketDevolucion.setFacturaSAP(facturaSap);
		if(!txtBloqEntrega.getText().trim().equals("")){
			ticketDevolucion.setDescripcionBloqueEntrega(bloqueoEntregaImpl.obtenerDescripcionBloqueoEntrega(txtBloqEntrega.getText().trim()));
		}else{
			ticketDevolucion.setDescripcionBloqueEntrega("");
		}
		ticketDevolucion.setNumeroPedidoCliente(txtNPedCliente.getText().trim());
		ticketDevolucion.setSello(txtSello.getText().trim());
		ticketDevolucion.setStrFud(txtNroFud.getText().trim());
		ticketDevolucion.setClaseRiesgo(txtClasRiesgo.getText());
		if(!txtCondPago.getText().trim().equals("")){
			ticketDevolucion.setDescripcionCondicionPago(condiciones.obtenerDescripcionCondicionesPago(txtCondPago.getText().trim()));
		}else{
			ticketDevolucion.setDescripcionCondicionPago("");
		}
		ticketDevolucion.setDescripcionMotivoDevolucion("");
		for (BeanMotivoDevolucion beanDevolucion : listaDevoluciones) {
			if(beanDevolucion.getStrCodMotDev().equals(txtMotivoDevolucion.getText())){
				ticketDevolucion.setDescripcionMotivoDevolucion(beanDevolucion.getStrMotDev());
				break;
			}
		}
		ticketDevolucion.setValorNeto(Util.formatearNumero(txtValorNeto.getText()));
		ticketDevolucion.setFechaFactura(Util.convierteFechaPuntoDDMMYYYAFormatoBarraDDMMMYYYY(fechaFacturaPedido));
		ticketDevolucion.setStrFechaDevolucionSistema(Util.convierteFechaPuntoDDMMYYYAFormatoBarraDDMMMYYYY(strFechaDevolucionSistema));
		SDevoluciones sDevoluciones = new SDevoluciones();
		ticketDevolucion.setCabeceraFormulario(sDevoluciones.leerComentarioPedidoDevolucion(txtCodigoPedido.getText(), "0001"));
		ticketDevolucion.setNotaCabecera(sDevoluciones.leerComentarioPedidoDevolucion(txtCodigoPedido.getText(), "0002"));
		
		List<DetalleTicketDevolucion> listaDetalleTicketDevolucion = new ArrayList<DetalleTicketDevolucion>();
		
		for (int j = 0; j < tblPedidos.getRowCount(); j++) {
			DetalleTicketDevolucion detalleTicketDevolucion = new DetalleTicketDevolucion();
			detalleTicketDevolucion.setPosicion(tblPedidos.getValueAt(j, 0).toString().trim());
			detalleTicketDevolucion.setMaterial(tblPedidos.getValueAt(j, 1).toString().trim());
			detalleTicketDevolucion.setCantidad(tblPedidos.getValueAt(j, 2).toString().trim());
			detalleTicketDevolucion.setUm(tblPedidos.getValueAt(j, 3).toString().trim());
			detalleTicketDevolucion.setDenominacion(tblPedidos.getValueAt(j, 4).toString().trim());
			detalleTicketDevolucion.setPrecioNeto(Util.formatearNumero(tblPedidos.getValueAt(j, 5).toString().trim()));
			detalleTicketDevolucion.setValorNeto(Util.formatearNumero(tblPedidos.getValueAt(j, 6).toString().trim()));
			listaDetalleTicketDevolucion.add(detalleTicketDevolucion);
		}
		
		ticketDevolucion.setListaDetalleTicketDevolucion(listaDetalleTicketDevolucion);
		ticketDevolucion.imprimir();
	}

	protected void btnEliminarPedidoActionPerformed(ActionEvent evt) {
		if (tblPedidos.getCellEditor() != null) {
			tblPedidos.getCellEditor().stopCellEditing();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int fila = tblPedidos.getSelectedRow();
				pierdeFoco = false;
				if (fila > -1) {
					String hijo = "";
					
					String strPesoNeto = tblPedidos.getValueAt(fila, 7).toString();
					double dPesoTotal = Double.parseDouble(txtPesoTotal.getText()) - Double.parseDouble(strPesoNeto);
					txtPesoTotal.setText(String.valueOf(redondeo(dPesoTotal)));
					//DefaultTableModel dtm = (DefaultTableModel) tblPedidos.getModel();
					//ModeloConsultaDevoluciones dtm =  (ModeloConsultaDevoluciones) tblPedidos.getModel();
				
					String posicion = tblPedidos.getValueAt(fila, 0).toString();
					//dtm.removeRow(fila);
					tblTablaModel.eliminarFila(fila);
					listaPedidos.remove(fila);
					for(int i = 6; i < p.size(); i++){
						String[] cadena = p.get(i);
						if(cadena.length > 13){
							hijo = cadena[13];
						}else
							break;
						if(posicion.equals(hijo)){
							//dtm.removeRow(fila);
							tblTablaModel.eliminarFila(fila);
							listaPedidos.remove(fila);
						}
					}
					calcularValoresTotales();
					tblPedidos.updateUI();
					/*if (tblPedidos.getRowCount() > 0) {
						double count = 0d;
						double dpeso = 0d;
						for (int i = 0; i < tblPedidos.getRowCount(); i++) {
							count += Double.parseDouble(tblPedidos.getValueAt(i, 6).toString());
							double dpesoUnitario = Double.parseDouble(tblPedidos.getValueAt(i, 7).toString());
							double dcantidad = Double.parseDouble(tblPedidos.getValueAt(i, 2).toString());
							dpeso += (dpesoUnitario * dcantidad); 
						}
						txtValorNeto.setText(String.valueOf(Util.formatearNumero(count)));
						txtPesoTotal.setText(String.valueOf(Util.formatearNumero(dpeso)));
					} else {
						txtValorNeto.setText(String.valueOf(0d));
						txtPesoTotal.setText(String.valueOf(0d));
					}*/
				} else {
					JOptionPane.showMessageDialog(null, "Por favor seleccione un item de la tabla.", "Mensaje", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	protected void calcularValoresTotales(){
		double precioNeto = 0d;
		double pesoTotal = 0d;
		for(int i = 0; i < tblTablaModel.getRowCount(); i++){
			Item it = tblTablaModel.getItems(i);
			pesoTotal += Double.parseDouble(it.getCantidad()) * it.getPesoUnitario() ;
			precioNeto += it.getValorNeto();
		}
		txtValorNeto.setText(Util.formatearNumero(precioNeto));
		txtPesoTotal.setText(Util.formatearNumero(pesoTotal));
	}

	protected BeanCliente obtenerCliente(String codigo) {
		BeanCliente cliente = null;
		SqlClienteImpl sql = new SqlClienteImpl();
		cliente = sql.buscarCliente(codigo);
		return cliente;
	}

	protected void cerrar() {
		if (editable) {
			int tipo = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la ventana?", "Confirmación", JOptionPane.YES_NO_OPTION);
			if (tipo == JOptionPane.OK_OPTION) {
				this.dispose();
			}
		} else {
			this.dispose();
		}
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		setPopupDevoluciones(evt, txtMotivoDevolucion, 0, 21);
	}

	/* MÉTODO QUE ELIMINA CEROS */
	private String eCeros(String codigo) {
		String nCodigo = "";
		int pos = 0;
		for (int i = 0; i < codigo.length(); i++) {
			if (codigo.charAt(i) != '0') {
				pos = i;
				break;
			}
		}
		for (int j = pos; j < codigo.length(); j++) {
			nCodigo += "" + codigo.charAt(j);
		}
		return nCodigo;
	}

	private void llenarTabla() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				/*
				String Columnas[] = { "POS.", "MATERIAL", "CANTIDAD", "UM", "DENOMINACIÓN", "PRC. NETO", "VALOR NETO", "PESO UNIT." };
				DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						if (column == 2) {
							return true;
						}
						return false;
					}
				};*/
				String[] temporal = p.get(p.size() - 1);
				//tblTablaModel.setNumRows(Integer.parseInt(temporal[1]));
				int z = 0; // Contador de filas
				double count = 0d; // Contador de valores netos
				double dPesoTotal = 0d;//Contadoe de peso neto
				Item items ;
				String[] t = p.get(0);
				if (t[0].trim().equals("CASO")) {
					for (int i = 1 + Integer.parseInt(temporal[0]) + Integer.parseInt(temporal[3]) + Integer.parseInt(temporal[2]); i < p.size() - 1; i++) {
						String[] t2 = p.get(i);
						listaPedidos.add(t2);
						items = new Item();
						// POSICIÓN
						items.setPosicion(t2[2]);
						//tblTablaModel.setValueAt(t2[2], z, 0);
						// CÓDIGO DE MATERIAL
						items.setCodigo(eCeros(t2[3]));
						//tblTablaModel.setValueAt(eCeros(t2[3]), z, 1);
						// CANTIDAD
						long cantidad = 0L;
						try {
							cantidad = (long) (Double.parseDouble(t2[51]));
						} catch (Exception e) {
							cantidad = 0L;
						}
						items.setCantidad("" + cantidad);
						//tblTablaModel.setValueAt(cantidad, z, 2);
						// UM
						items.setUnidad(t2[19]);
						//tblTablaModel.setValueAt(t2[19], z, 3);
						// DENOMINACIÓN
						items.setDenominacion(t2[8]);
						//tblTablaModel.setValueAt(t2[8], z, 4);
						// PRECIO NETO
						items.setPrecioNeto(Double.parseDouble(t2[98]));
						//tblTablaModel.setValueAt(Util.formatearNumero(Double.parseDouble(t2[98])), z, 5);
						// VALOR NETO
						items.setValorNeto(Double.parseDouble(t2[45]));
						//tblTablaModel.setValueAt(Util.formatearNumero(Double.parseDouble(t2[45])), z, 6);
						count += Double.parseDouble(t2[45]);
						//	PESO NETO
						double pesoUnitario = Double.parseDouble(t2[60]) / Double.parseDouble(t2[51]);
						items.setPesoUnitario(Double.parseDouble(Util.formatearNumero(pesoUnitario)));
						//tblTablaModel.setValueAt(Double.parseDouble(Util.formatearNumero(pesoUnitario) ), z, 7);
						//dPesoTotal += Double.parseDouble(t2[60]) * cantidad;
						dPesoTotal += Double.parseDouble(t2[60]) ;
						tblTablaModel.agregarItem(items);
						//listaPedidos.add(items);
						z++;
					}
				} else {
					for (int i = 1 + Integer.parseInt(temporal[0]); i < p.size() - 1; i++) {
						String[] t2 = p.get(i);
						items = new Item();
						// POSICIÓN
						items.setPosicion(t2[2]);
						//tblTablaModel.setValueAt(t2[2], z, 0);
						// CÓDIGO DE MATERIAL
						items.setCodigo(eCeros(t2[3]));
						//tblTablaModel.setValueAt(eCeros(t2[3]), z, 1);
						// CANTIDAD
						long cantidad = 0L;
						try {
							cantidad = (long) (Double.parseDouble(t2[51]));
						} catch (Exception e) {
							cantidad = 0L;
						}
						items.setCantidad("" + cantidad);
						//tblTablaModel.setValueAt(cantidad, z, 2);
						// UM
						items.setUnidad(t2[19]);
						//tblTablaModel.setValueAt(t2[19], z, 3);
						// DENOMINACIÓN
						items.setDenominacion(t2[8]);
						//tblTablaModel.setValueAt(t2[8], z, 4);
						// PRECIO NETO
						items.setPrecioNeto(Double.parseDouble(t2[98]));
						//tblTablaModel.setValueAt(Util.formatearNumero(Double.parseDouble(t2[98])), z, 5);
						// VALOR NETO
						items.setValorNeto(Double.parseDouble(t2[45]));
						//tblTablaModel.setValueAt(Util.formatearNumero(Double.parseDouble(t2[45])), z, 6);
						count += Double.parseDouble(t2[45]);
						//	PESO NETO
						Double pesoUnitario = Double.parseDouble(t2[60]) / Double.parseDouble(t2[51]);
						items.setPesoUnitario(Double.parseDouble(Util.formatearNumero(pesoUnitario)));
						//tblTablaModel.setValueAt(Util.formatearNumero(pesoUnitario), z, 7);
						//dPesoTotal += Double.parseDouble(t2[60]) * cantidad;
						dPesoTotal += Double.parseDouble(t2[60]);
						z++;
						tblTablaModel.agregarItem(items);
						//listaPedidos.add(items);
						
					}
				}
				txtValorNeto.setText(String.valueOf(Util.formatearNumero(count)));
				txtPesoTotal.setText(String.valueOf(Util.formatearNumero(dPesoTotal)));
				tblPedidos.setModel(tblTablaModel);
				
				//RenderConsultaDevoluciones render = new RenderConsultaDevoluciones();
				//tblPedidos.setDefaultRenderer(Object.class, render);
				tblPedidos.updateUI();
				Util.setAnchoColumnas(tblPedidos);
				setAnchoColumnas();
			}
		});
	}

	/* MÉTODO QUE APLICA REDONDEO A 3 DECIMALES */
	private double redondeo(double numero) {
		return Math.rint(numero * 1000) / 1000;
	}

	private void setComponentPopupMenu(final java.awt.Component parent, final javax.swing.JPopupMenu menu) {
		parent.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}

			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
		});
	}

	private void jTable1MouseClicked(MouseEvent evt) {
	}

	// Variables declaration - do not modify
	protected javax.swing.JButton btnAniadir;
	protected javax.swing.JButton btnCancelar;
	protected javax.swing.JButton btnDetalleBloqEntrega;
	protected javax.swing.JButton btnDetalleCondPago;
	protected javax.swing.JButton btnDetalleDestinatario;
	protected javax.swing.JButton btnEliminar;
	protected javax.swing.JButton btnGuardarOrden;
	protected javax.swing.JButton btnImprimirComprobante;
	protected javax.swing.JButton btnAniadirComentario;
	protected javax.swing.JPanel jPanel9;
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
	
	/*
	 * 	Marcelo Moyano 10/05/2013 - 09:11
	 * 	Codigo: PRO-2013-0024
	 * 	Titulo: Inlcuir campos Adicionales en Pantalla de Devoluciones
	 */
	protected JLabel lblNoGuiaTransporte;
	protected JLabel lblNroBultos;
	protected JLabel lblNombreContactoRetiro;
	protected JLabel lblNroFud;
	protected JLabel lblTpoOns;// (Dir, Ven)
	protected JLabel lblPersonaReportaDevolucion;
	protected JLabel lblPesoTotal;
	
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
	protected javax.swing.JSeparator separador;
	protected javax.swing.JToolBar.Separator separador2;
	protected javax.swing.JToolBar.Separator separador3;
	protected javax.swing.JToolBar.Separator separador4;
	protected javax.swing.JToolBar.Separator separador5;
	protected javax.swing.JTable tblPedidos;
	protected javax.swing.JTextField txtBloqEntrega;
	protected javax.swing.JTextField txtClasRiesgo;
	protected javax.swing.JTextField txtCodigoPedido;
	protected javax.swing.JTextField txtCondPago;
	protected javax.swing.JTextField txtDestinatario;
	protected javax.swing.JTextField txtNPedCliente;
	protected javax.swing.JTextField txtNombre;
	protected javax.swing.JTextField txtValorNeto;
	
	/*
	 * 	Marcelo Moyano 10/05/2013 - 09:11
	 * 	Codigo: PRO-2013-0024
	 * 	Titulo: Inlcuir campos Adicionales en Pantalla de Devoluciones
	 */
	protected JTextField txtNoGuiaTransporte;
	protected JTextField txtNroBultos;
	protected JTextField txtNombreContactoRetiro;
	protected JTextField txtNroFud;
	protected JTextField txtPersonaReportaDevolucion;
	protected JTextField txtPesoTotal;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbDirVen;
	
	protected Separator separador8;
	protected ModeloTablaItems mdlTblItems = new ModeloTablaItems();
	protected RenderConsultaDevoluciones render = new RenderConsultaDevoluciones();
	// Sucursales
	protected JPopupMenu popupSucursales = null;
	protected JScrollPane scrollSucursales;
	protected JTable tablaSucursales = new JTable();
	protected String Columnas[] = { "CÓDIGO", "DIRECCIÓN" };
	@SuppressWarnings("serial")
	protected DefaultTableModel modeloSucursales = new DefaultTableModel(new Object[][] {}, Columnas) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	// Devoluciones
	protected JPopupMenu popupDevolucion = null;
	protected JScrollPane scrollDevolucion;
	protected JTable tablaDevolucion = new JTable();
	protected String ColDev[] = { "CÓDIGO", "DESCRIPCIÓN" };
	@SuppressWarnings("serial")
	protected DefaultTableModel modeloDevolución = new DefaultTableModel(new Object[][] {}, ColDev) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	// Bloqueos de entrega
	protected JPopupMenu popupBloqueo = null;
	protected JScrollPane scrollerBloqueo;
	protected JTable tablaBloqueos = new JTable();
	protected String Columnas3[] = { "CÓDIGO", "DESCRIPCIÓN" };
	@SuppressWarnings("serial")
	protected DefaultTableModel modeloBloqueo = new DefaultTableModel(new Object[][] {}, Columnas3) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	// Motivos de Devoluciones
	protected JPopupMenu popupMD = null;
	protected JScrollPane scrollMD;
	protected JTable tablaMD = new JTable();
	protected String ColMD[] = { "POSICIÓN", "MATERIAL", "CANTIDAD", "UM", "DENOMINACIÓN", "PRC. NETO", "VALOR NETO" };
	@SuppressWarnings("serial")
	protected DefaultTableModel modeloMD = new DefaultTableModel(new Object[][] {}, ColMD) {
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
	protected SqlBloqueoEntregaImpl bloqueoEntregaImpl;
	@SuppressWarnings("serial")
	protected DefaultTableModel modelCondiciones = new DefaultTableModel(new Object[][] {}, Columnas2) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	protected ListSelectionModel selectionModel;
	protected String codigoCliente = "";
	protected String nombreCliente = "";
	protected String direccionDestinatario = "";
	protected String motivoDevolucion = "";
	protected JTextField txtSello;
	private JTable jTable1;
	protected JScrollPane jScrollPane1;
	protected JPopupMenu jPopupMenu1;
	protected JTextField txtMotivoDevolucion;
	protected JButton jButton1;
	protected JPanel jPanel1;
	protected JLabel jLabel1;
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
	private String codigoPedidoDevolucion = "";
	protected String iva = null;
	protected int tipoVentana;
	protected BeanPedido pedido;
	protected JTextField textFieldCantidad;
	// End of variables declaration
	protected IRegistroDevoluciones instancia;
	protected boolean editable = true;
	public HashMap<String, List<Item>> mapaItems;
	DefaultTableModel modeloTabla;
}