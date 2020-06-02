package com.promesa.internalFrame.ferias;

import java.awt.CardLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultCaret;

//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.promesa.bean.BeanDato;
import com.promesa.ferias.Asistencia;
import com.promesa.ferias.BeanGremioEInstitutoEducativo;
import com.promesa.ferias.BeanProfesion;
import com.promesa.ferias.Cliente;
import com.promesa.ferias.LugarFeria;
import com.promesa.ferias.ModeloTablaFeria;
import com.promesa.ferias.Relacion;
import com.promesa.ferias.RenderConsultaFeria;
import com.promesa.ferias.sql.impl.SqlFeriasImpl;
import com.promesa.main.Promesa;
import com.promesa.sap.SFerias;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.TablaAExcel;
import com.promesa.util.Util;
import com.promesa.util.ValidadorEntradasDeTexto;

import net.coderazzi.filters.gui.TableFilterHeader;

/**
 * 
 * @author Rolando
 */
@SuppressWarnings("serial")
public class IFerias extends javax.swing.JInternalFrame {

	private CardLayout layaout;
	private TableFilterHeader filterHeader;
	private TableSorter tableSorter;
	private ModeloTablaFeria modeloTablaFeria;
	Date fechaSecuencial = null;
	private IFerias instance = null;

	private String source = "-1";
	private long id = -1;
	Asistencia asiste;

	javax.swing.Timer timerMensaje;

	@SuppressWarnings("unchecked")
	public IFerias() {

		initComponents();
		layaout = (CardLayout) pnlContenedor.getLayout();
		modeloTablaFeria = new ModeloTablaFeria();
		tableSorter = new TableSorter(modeloTablaFeria, tblAsistencias.getTableHeader());
		tblAsistencias.setModel(tableSorter);
		filterHeader = new TableFilterHeader();
		filterHeader.setTable(tblAsistencias);
		dateDesde.setDate(new Date());
		dateDesde.setMaxSelectableDate(new Date());
		dateHasta.setDate(new Date());
		dateHasta.setMaxSelectableDate(new Date());
		txtCodigoCliente.setFocusTraversalKeys(KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS, Collections.EMPTY_SET);

		agregarSecuencia();
		instance = this;
		RenderConsultaFeria renderFerias = new RenderConsultaFeria();
		tblAsistencias.setDefaultRenderer(Object.class, renderFerias);
		tblAsistencias.setRowHeight(22);
		cargarLugarFerias();
		cargarRelacion();
		cargarProfesion();
		cargarGremioInst();
		validadorTexto();
		eventos();
		inhabilitarCampos();
		HoraAsistencia();
	}

	@SuppressWarnings("rawtypes")
	private void initComponents() {	
		toolBarOpciones = new javax.swing.JToolBar();
        btnRegistrarAsistencia = new javax.swing.JButton();
        sptUno = new javax.swing.JToolBar.Separator();
        btnDescargarListaClientes = new javax.swing.JButton();
        sptDos = new javax.swing.JToolBar.Separator();
        btnSincronizarRegistros = new javax.swing.JButton();
        sptTres = new javax.swing.JToolBar.Separator();
        btnSalir = new javax.swing.JButton();
        pnlContenedor = new javax.swing.JPanel();
        pnlRegistrarAsistencia = new javax.swing.JPanel();
        pnlContenedorRegistro = new javax.swing.JPanel();
        pnlDatosBasicos = new javax.swing.JPanel();
        lblDatosBasicos = new javax.swing.JLabel();
        pnlDatosBasicosControles = new javax.swing.JPanel();
        lblAnio = new javax.swing.JLabel();
        txtAnio = new javax.swing.JLabel();
        lblSecuencia = new javax.swing.JLabel();
        txtSecuencia = new javax.swing.JLabel();
        lblLugar = new javax.swing.JLabel();
        cmbLugar = new javax.swing.JComboBox();
        pnlDatosRegistro = new javax.swing.JPanel();
        lblFechaRegistro = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        lblusuario = new javax.swing.JLabel();
        txtFechaRegistro = new javax.swing.JLabel();
        txtHora = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JLabel();
        pnlDatosIngresoContenedor = new javax.swing.JPanel();
        lblDatosIngreso = new javax.swing.JLabel();
        pnlEtiquetas = new javax.swing.JPanel();
        lblRelacion = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblGremioEInstitutoEducativo = new javax.swing.JLabel();
        lblInvitado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblFacebook = new javax.swing.JLabel();
        lblTwitter = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblIntereses = new javax.swing.JLabel();
        lblObservaciones = new javax.swing.JLabel();
        pnlCampos = new javax.swing.JPanel();
        pnlContenedorCampos = new javax.swing.JPanel();
        pnlRelacion = new javax.swing.JPanel();
        cmbRelacion = new javax.swing.JComboBox();
        pnlCliente = new javax.swing.JPanel();
        txtCodigoCliente = new javax.swing.JTextField();
        pnlRazonSocial = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        txtRazonSocial = new javax.swing.JTextField();
        pnlDireccion = new javax.swing.JPanel();
        txtDireccion = new javax.swing.JTextField();
        pnlGremioEInstitutoEducativo = new javax.swing.JPanel();
        txtCodigoGremioInst = new javax.swing.JTextField();
        cmbGremioEInstitutoEducativo = new javax.swing.JComboBox();
        pnlInvitado = new javax.swing.JPanel();
        txtInvitado = new javax.swing.JTextField();
        pnlProfesion = new javax.swing.JPanel();
        cmbProfesion = new javax.swing.JComboBox();
        pnlCorreo = new javax.swing.JPanel();
        txtCorreo = new javax.swing.JTextField();
        pnlFacebook = new javax.swing.JPanel();
        txtFacebook = new javax.swing.JTextField();
        pnlTwitter = new javax.swing.JPanel();
        txtTwitter = new javax.swing.JTextField();
        pnlTelefonos = new javax.swing.JPanel();
        txtTelefono = new javax.swing.JTextField();
        lblCelular = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        lblOtro = new javax.swing.JLabel();
        txtOtro = new javax.swing.JTextField();
        pnlIntereses = new javax.swing.JPanel();
        txtIntereses = new javax.swing.JTextField();
        pnlObservaciones = new javax.swing.JPanel();
        txtObservaciones = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        pnlDescargarClientes = new javax.swing.JPanel();
        lblTituloActualizacion = new javax.swing.JLabel();
        pnlActualizacionBaseDatosClientes = new javax.swing.JPanel();
        toolBarActualizacion = new javax.swing.JToolBar();
        pnlToolBar = new javax.swing.JPanel();
        btnActualizarCliente = new javax.swing.JButton();
        scrClientes = new javax.swing.JScrollPane();
        consolaVisualizacion = new javax.swing.JEditorPane();
        pnlSincronizarRegistros = new javax.swing.JPanel();
        lblTituloSincronizacion = new javax.swing.JLabel();
        pnlSincronizacion = new javax.swing.JPanel();
        toolBarOpcionesSincronozacion = new javax.swing.JToolBar();
        lblEmpty1 = new javax.swing.JLabel();
        btnMarcarTodo = new javax.swing.JButton();
        lblEmpty2 = new javax.swing.JToolBar.Separator();
        btnSincronizar = new javax.swing.JButton();
        sptCuatro = new javax.swing.JToolBar.Separator();
        btnExportar = new javax.swing.JButton();
        sptCinco = new javax.swing.JToolBar.Separator();
        lblFiltrarDesde = new javax.swing.JLabel();
        dateDesde = new com.toedter.calendar.JDateChooser();
        lblHasta = new javax.swing.JLabel();
        dateHasta = new com.toedter.calendar.JDateChooser();
        lblEmpty3 = new javax.swing.JLabel();
        btnBuscarAsistencia = new javax.swing.JButton();
        sptSeis = new javax.swing.JToolBar.Separator();
        btnEliminar = new javax.swing.JButton();
        scrTablaAsistencia = new javax.swing.JScrollPane();
        tblAsistencias = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle(".:: Registro de Ferias ::.");

        toolBarOpciones.setBackground(new java.awt.Color(163, 193, 228));
        toolBarOpciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolBarOpciones.setFloatable(false);
        toolBarOpciones.setRollover(true);
        toolBarOpciones.setPreferredSize(new java.awt.Dimension(100, 30));

        btnRegistrarAsistencia.setBackground(new java.awt.Color(242, 225, 175));
        btnRegistrarAsistencia.setText("Registrar Asistencia");
        btnRegistrarAsistencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnRegistrarAsistencia.setContentAreaFilled(false);
        btnRegistrarAsistencia.setFocusable(false);
        btnRegistrarAsistencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistrarAsistencia.setMaximumSize(new java.awt.Dimension(103, 23));
        btnRegistrarAsistencia.setOpaque(true);
        btnRegistrarAsistencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        toolBarOpciones.add(btnRegistrarAsistencia);
        toolBarOpciones.add(sptUno);

        btnDescargarListaClientes.setBackground(new java.awt.Color(242, 225, 175));
        btnDescargarListaClientes.setText("Descargar Lista de Clientes");
        btnDescargarListaClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDescargarListaClientes.setContentAreaFilled(false);
        btnDescargarListaClientes.setFocusable(false);
        btnDescargarListaClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDescargarListaClientes.setMaximumSize(new java.awt.Dimension(137, 23));
        btnDescargarListaClientes.setOpaque(true);
        btnDescargarListaClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        toolBarOpciones.add(btnDescargarListaClientes);
        toolBarOpciones.add(sptDos);

        btnSincronizarRegistros.setBackground(new java.awt.Color(242, 225, 175));
        btnSincronizarRegistros.setText("Sincronizar Registros");
        btnSincronizarRegistros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSincronizarRegistros.setContentAreaFilled(false);
        btnSincronizarRegistros.setFocusable(false);
        btnSincronizarRegistros.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSincronizarRegistros.setMaximumSize(new java.awt.Dimension(110, 23));
        btnSincronizarRegistros.setMinimumSize(new java.awt.Dimension(110, 23));
        btnSincronizarRegistros.setOpaque(true);
        btnSincronizarRegistros.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        toolBarOpciones.add(btnSincronizarRegistros);
        toolBarOpciones.add(sptTres);

        btnSalir.setBackground(new java.awt.Color(242, 225, 175));
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.setContentAreaFilled(false);
        btnSalir.setFocusable(false);
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setMaximumSize(new java.awt.Dimension(28, 23));
        btnSalir.setMinimumSize(new java.awt.Dimension(23, 23));
        btnSalir.setOpaque(true);
        btnSalir.setPreferredSize(new java.awt.Dimension(23, 23));
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        toolBarOpciones.add(btnSalir);

        getContentPane().add(toolBarOpciones, java.awt.BorderLayout.PAGE_START);

        pnlContenedor.setBackground(new java.awt.Color(203, 219, 234));
        pnlContenedor.setLayout(new java.awt.CardLayout());

        pnlRegistrarAsistencia.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlRegistrarAsistencia.setOpaque(false);
        pnlRegistrarAsistencia.setLayout(new java.awt.BorderLayout());

        pnlContenedorRegistro.setOpaque(false);

        pnlDatosBasicos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        pnlDatosBasicos.setOpaque(false);
        pnlDatosBasicos.setLayout(new java.awt.BorderLayout());

        lblDatosBasicos.setBackground(new java.awt.Color(174, 206, 219));
        lblDatosBasicos.setText("Datos Básicos");
        lblDatosBasicos.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        lblDatosBasicos.setOpaque(true);
        pnlDatosBasicos.add(lblDatosBasicos, java.awt.BorderLayout.PAGE_START);

        pnlDatosBasicosControles.setOpaque(false);
        pnlDatosBasicosControles.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 4));

        lblAnio.setText("Año:");
        lblAnio.setPreferredSize(new java.awt.Dimension(80, 14));
        pnlDatosBasicosControles.add(lblAnio);

        txtAnio.setPreferredSize(new java.awt.Dimension(100, 20));
        txtAnio.setRequestFocusEnabled(false);
        txtAnio.setText(Util.convierteFechaAFormatoYYYY(new Date()));
        pnlDatosBasicosControles.add(txtAnio);

        lblSecuencia.setText("Secuencia:");
        lblSecuencia.setPreferredSize(new java.awt.Dimension(80, 14));
        pnlDatosBasicosControles.add(lblSecuencia);

        txtSecuencia.setPreferredSize(new java.awt.Dimension(100, 20));
        pnlDatosBasicosControles.add(txtSecuencia);

        lblLugar.setText("Lugar:");
        lblLugar.setPreferredSize(new java.awt.Dimension(80, 14));
        pnlDatosBasicosControles.add(lblLugar);

        cmbLugar.setPreferredSize(new java.awt.Dimension(100, 20));
        pnlDatosBasicosControles.add(cmbLugar);

        pnlDatosBasicos.add(pnlDatosBasicosControles, java.awt.BorderLayout.CENTER);

        pnlDatosRegistro.setOpaque(false);

        lblFechaRegistro.setForeground(new java.awt.Color(0, 0, 255));
        lblFechaRegistro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaRegistro.setText("Fecha de Registro:");

        lblHora.setForeground(new java.awt.Color(0, 0, 255));
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHora.setText("Hora:");

        lblusuario.setForeground(new java.awt.Color(0, 0, 255));
        lblusuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblusuario.setText("Usuario:");

        txtFechaRegistro.setForeground(new java.awt.Color(0, 0, 255));
        txtFechaRegistro.setText(Util.convierteFechaAFormatoDDMMYYYY(new Date()));
        txtFechaRegistro.setBorder(null);
        txtFechaRegistro.setOpaque(false);

        txtHora.setForeground(new java.awt.Color(0, 0, 255));
        txtHora.setText(getHoraActual());
        txtHora.setBorder(null);
        txtHora.setOpaque(false);

        txtUsuario.setForeground(new java.awt.Color(0, 0, 255));
        txtUsuario.setText(((BeanDato) Promesa.datose.get(0)).getStrUsuario());
        txtUsuario.setBorder(null);
        txtUsuario.setOpaque(false);

        javax.swing.GroupLayout pnlDatosRegistroLayout = new javax.swing.GroupLayout(pnlDatosRegistro);
        pnlDatosRegistro.setLayout(pnlDatosRegistroLayout);
        pnlDatosRegistroLayout.setHorizontalGroup( pnlDatosRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosRegistroLayout.createSequentialGroup().addContainerGap()
            .addGroup(pnlDatosRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFechaRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(lblHora, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(lblusuario, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(pnlDatosRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
            .addComponent(txtUsuario).addComponent(txtHora)
            .addComponent(txtFechaRegistro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
            .addContainerGap()) );
        pnlDatosRegistroLayout.setVerticalGroup(pnlDatosRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosRegistroLayout.createSequentialGroup().addContainerGap()
            .addGroup(pnlDatosRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblFechaRegistro)
            .addComponent(txtFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(pnlDatosRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblHora)
            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(pnlDatosRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblusuario)
            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(25, Short.MAX_VALUE)));

        pnlDatosIngresoContenedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        pnlDatosIngresoContenedor.setOpaque(false);
        pnlDatosIngresoContenedor.setPreferredSize(new java.awt.Dimension(490, 400));
        pnlDatosIngresoContenedor.setLayout(new java.awt.BorderLayout());

        lblDatosIngreso.setBackground(new java.awt.Color(174, 206, 219));
        lblDatosIngreso.setText("Datos de Ingreso");
        lblDatosIngreso.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        lblDatosIngreso.setOpaque(true);
        pnlDatosIngresoContenedor.add(lblDatosIngreso, java.awt.BorderLayout.PAGE_START);

        pnlEtiquetas.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        pnlEtiquetas.setOpaque(false);
        pnlEtiquetas.setLayout(new java.awt.GridLayout(12, 1));

        lblRelacion.setText("Relación");
        pnlEtiquetas.add(lblRelacion);

        lblCliente.setText("Cliente:");
        pnlEtiquetas.add(lblCliente);

        lblDireccion.setText("Ciudad:");
        pnlEtiquetas.add(lblDireccion);

        lblGremioEInstitutoEducativo.setText("Gremio/Inst.:");
        pnlEtiquetas.add(lblGremioEInstitutoEducativo);

        lblInvitado.setText("<html><font color='red'>*</font> Invitado:</html>");
        pnlEtiquetas.add(lblInvitado);

        jLabel1.setText("Profesión:");
        pnlEtiquetas.add(jLabel1);

        lblCorreo.setText("Correo:");
        pnlEtiquetas.add(lblCorreo);

        lblFacebook.setText("Facebook:");
        pnlEtiquetas.add(lblFacebook);

        lblTwitter.setText("Twitter:");
        pnlEtiquetas.add(lblTwitter);

        lblTelefono.setText("<html><font color='red'>*</font> Teléfono:</html>");
        pnlEtiquetas.add(lblTelefono);

        lblIntereses.setText("Intereses:");
        pnlEtiquetas.add(lblIntereses);

        lblObservaciones.setText("Observaciones:");
        pnlEtiquetas.add(lblObservaciones);

        pnlDatosIngresoContenedor.add(pnlEtiquetas, java.awt.BorderLayout.LINE_START);

        pnlCampos.setOpaque(false);
        pnlCampos.setLayout(new java.awt.BorderLayout());

        pnlContenedorCampos.setOpaque(false);
        pnlContenedorCampos.setPreferredSize(new java.awt.Dimension(400, 240));
        pnlContenedorCampos.setLayout(new java.awt.GridLayout(12, 1));

        pnlRelacion.setOpaque(false);
        pnlRelacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        cmbRelacion.setPreferredSize(new java.awt.Dimension(120, 20));
        pnlRelacion.add(cmbRelacion);

        pnlContenedorCampos.add(pnlRelacion);

        pnlCliente.setOpaque(false);
        pnlCliente.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtCodigoCliente.setPreferredSize(new java.awt.Dimension(120, 20));
        pnlCliente.add(txtCodigoCliente);

        pnlRazonSocial.setOpaque(false);
        pnlRazonSocial.setPreferredSize(new java.awt.Dimension(200, 20));
        pnlRazonSocial.setLayout(new java.awt.BorderLayout());

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.setContentAreaFilled(false);

        pnlRazonSocial.add(btnBuscar, java.awt.BorderLayout.LINE_END);

        txtRazonSocial.setPreferredSize(new java.awt.Dimension(120, 20));
        pnlRazonSocial.add(txtRazonSocial, java.awt.BorderLayout.CENTER);

        pnlCliente.add(pnlRazonSocial);

        pnlContenedorCampos.add(pnlCliente);

        pnlDireccion.setOpaque(false);
        pnlDireccion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtDireccion.setPreferredSize(new java.awt.Dimension(322, 20));
        pnlDireccion.add(txtDireccion);

        pnlContenedorCampos.add(pnlDireccion);

        pnlGremioEInstitutoEducativo.setOpaque(false);
        pnlGremioEInstitutoEducativo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtCodigoGremioInst.setPreferredSize(new java.awt.Dimension(100, 20));
        txtCodigoGremioInst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoGremioInstKeyReleased(evt);
            }});
        pnlGremioEInstitutoEducativo.add(txtCodigoGremioInst);

        cmbGremioEInstitutoEducativo.setPreferredSize(new java.awt.Dimension(120, 20));
        pnlGremioEInstitutoEducativo.add(cmbGremioEInstitutoEducativo);

        pnlContenedorCampos.add(pnlGremioEInstitutoEducativo);

        pnlInvitado.setOpaque(false);
        pnlInvitado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtInvitado.setPreferredSize(new java.awt.Dimension(322, 20));
        pnlInvitado.add(txtInvitado);

        pnlContenedorCampos.add(pnlInvitado);

        pnlProfesion.setOpaque(false);
        pnlProfesion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        cmbProfesion.setPreferredSize(new java.awt.Dimension(120, 20));
        pnlProfesion.add(cmbProfesion);

        pnlContenedorCampos.add(pnlProfesion);

        pnlCorreo.setOpaque(false);
        pnlCorreo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtCorreo.setPreferredSize(new java.awt.Dimension(220, 20));
        pnlCorreo.add(txtCorreo);

        pnlContenedorCampos.add(pnlCorreo);

        pnlFacebook.setOpaque(false);
        pnlFacebook.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtFacebook.setPreferredSize(new java.awt.Dimension(220, 20));
        pnlFacebook.add(txtFacebook);

        pnlContenedorCampos.add(pnlFacebook);

        pnlTwitter.setOpaque(false);
        pnlTwitter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtTwitter.setPreferredSize(new java.awt.Dimension(220, 20));
        pnlTwitter.add(txtTwitter);

        pnlContenedorCampos.add(pnlTwitter);

        pnlTelefonos.setOpaque(false);
        pnlTelefonos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtTelefono.setPreferredSize(new java.awt.Dimension(103, 20));
        pnlTelefonos.add(txtTelefono);

        lblCelular.setText("Celular:");
        pnlTelefonos.add(lblCelular);

        txtCelular.setPreferredSize(new java.awt.Dimension(103, 20));
        pnlTelefonos.add(txtCelular);

        lblOtro.setText("Otro:");
        pnlTelefonos.add(lblOtro);

        txtOtro.setPreferredSize(new java.awt.Dimension(103, 20));
        pnlTelefonos.add(txtOtro);

        pnlContenedorCampos.add(pnlTelefonos);

        pnlIntereses.setOpaque(false);
        pnlIntereses.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtIntereses.setPreferredSize(new java.awt.Dimension(380, 20));
        pnlIntereses.add(txtIntereses);

        pnlContenedorCampos.add(pnlIntereses);

        pnlObservaciones.setOpaque(false);
        pnlObservaciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        txtObservaciones.setPreferredSize(new java.awt.Dimension(380, 20));
        pnlObservaciones.add(txtObservaciones);

        pnlContenedorCampos.add(pnlObservaciones);

        pnlCampos.add(pnlContenedorCampos, java.awt.BorderLayout.LINE_START);

        pnlDatosIngresoContenedor.add(pnlCampos, java.awt.BorderLayout.CENTER);

        btnCancelar.setBackground(new java.awt.Color(242, 225, 175));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/16_cancel.png"))); // NOI18N
        btnCancelar.setText("Limpiar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setOpaque(true);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		btnCancelarActionPerformed(evt);
        	}
        });

        btnGuardar.setBackground(new java.awt.Color(242, 225, 175));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/16_save.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setOpaque(true);

        javax.swing.GroupLayout pnlContenedorRegistroLayout = new javax.swing.GroupLayout(pnlContenedorRegistro);
        pnlContenedorRegistro.setLayout(pnlContenedorRegistroLayout);
        pnlContenedorRegistroLayout.setHorizontalGroup( pnlContenedorRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContenedorRegistroLayout.createSequentialGroup().addContainerGap()
            .addGroup(pnlContenedorRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDatosIngresoContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
            .addGroup(pnlContenedorRegistroLayout.createSequentialGroup()
            .addComponent(pnlDatosBasicos, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(36, 36, 36)
            .addComponent(pnlDatosRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorRegistroLayout.createSequentialGroup()
            .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnGuardar)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnCancelar))).addContainerGap())
        );
        pnlContenedorRegistroLayout.setVerticalGroup(pnlContenedorRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContenedorRegistroLayout.createSequentialGroup().addContainerGap()
            .addGroup(pnlContenedorRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDatosRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlDatosBasicos, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(pnlDatosIngresoContenedor, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(28, 28, 28)
            .addGroup(pnlContenedorRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(29, Short.MAX_VALUE))
        );

        pnlRegistrarAsistencia.add(pnlContenedorRegistro, java.awt.BorderLayout.CENTER);

        pnlContenedor.add(pnlRegistrarAsistencia, "card2");

        pnlDescargarClientes.setLayout(new java.awt.BorderLayout());

        lblTituloActualizacion.setBackground(new java.awt.Color(255, 255, 255));
        lblTituloActualizacion.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblTituloActualizacion.setForeground(new java.awt.Color(48, 104, 151));
        lblTituloActualizacion.setText("Actualización de Base de Datos de Clientes");
        lblTituloActualizacion.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 0));
        lblTituloActualizacion.setOpaque(true);
        pnlDescargarClientes.add(lblTituloActualizacion, java.awt.BorderLayout.PAGE_START);

        pnlActualizacionBaseDatosClientes.setLayout(new java.awt.BorderLayout());

        toolBarActualizacion.setBackground(new java.awt.Color(163, 193, 229));
        toolBarActualizacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolBarActualizacion.setFloatable(false);
        toolBarActualizacion.setRollover(true);

        pnlToolBar.setOpaque(false);
        pnlToolBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 2));

        btnActualizarCliente.setBackground(new java.awt.Color(239, 221, 171));
        btnActualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descarga.png"))); // NOI18N
        btnActualizarCliente.setText("Actualizar Clientes");
        btnActualizarCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnActualizarCliente.setContentAreaFilled(false);
        btnActualizarCliente.setFocusable(false);
        btnActualizarCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnActualizarCliente.setOpaque(true);
        pnlToolBar.add(btnActualizarCliente);

        toolBarActualizacion.add(pnlToolBar);

        pnlActualizacionBaseDatosClientes.add(toolBarActualizacion, java.awt.BorderLayout.PAGE_START);

        scrClientes.setBorder(null);

        consolaVisualizacion.setEditable(false);
        consolaVisualizacion.setAutoscrolls(false);
        scrClientes.setViewportView(consolaVisualizacion);

        pnlActualizacionBaseDatosClientes.add(scrClientes, java.awt.BorderLayout.CENTER);

        pnlDescargarClientes.add(pnlActualizacionBaseDatosClientes, java.awt.BorderLayout.CENTER);

        pnlContenedor.add(pnlDescargarClientes, "card3");

        pnlSincronizarRegistros.setLayout(new java.awt.BorderLayout());

        lblTituloSincronizacion.setBackground(new java.awt.Color(255, 255, 255));
        lblTituloSincronizacion.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblTituloSincronizacion.setForeground(new java.awt.Color(48, 104, 151));
        lblTituloSincronizacion.setText("Sincronización de Registro de Visitas");
        lblTituloSincronizacion.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 0));
        lblTituloSincronizacion.setOpaque(true);
        pnlSincronizarRegistros.add(lblTituloSincronizacion, java.awt.BorderLayout.PAGE_START);

        pnlSincronizacion.setLayout(new java.awt.BorderLayout());

        toolBarOpcionesSincronozacion.setBackground(new java.awt.Color(163, 193, 229));
        toolBarOpcionesSincronozacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolBarOpcionesSincronozacion.setFloatable(false);
        toolBarOpcionesSincronozacion.setRollover(true);
        toolBarOpcionesSincronozacion.setPreferredSize(new java.awt.Dimension(100, 29));

        lblEmpty1.setText(" ");
        toolBarOpcionesSincronozacion.add(lblEmpty1);

        btnMarcarTodo.setBackground(new java.awt.Color(242, 225, 175));
        btnMarcarTodo.setText("Marcar todo");
        btnMarcarTodo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnMarcarTodo.setContentAreaFilled(false);
        btnMarcarTodo.setFocusable(false);
        btnMarcarTodo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMarcarTodo.setMaximumSize(new java.awt.Dimension(65, 17));
        btnMarcarTodo.setMinimumSize(new java.awt.Dimension(65, 17));
        btnMarcarTodo.setOpaque(true);
        btnMarcarTodo.setPreferredSize(new java.awt.Dimension(65, 17));
        btnMarcarTodo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarOpcionesSincronozacion.add(btnMarcarTodo);
        toolBarOpcionesSincronozacion.add(lblEmpty2);

        btnSincronizar.setBackground(new java.awt.Color(242, 225, 175));
        btnSincronizar.setText("Sincronizar");
        btnSincronizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSincronizar.setContentAreaFilled(false);
        btnSincronizar.setFocusable(false);
        btnSincronizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSincronizar.setMaximumSize(new java.awt.Dimension(60, 17));
        btnSincronizar.setMinimumSize(new java.awt.Dimension(60, 17));
        btnSincronizar.setOpaque(true);
        btnSincronizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarOpcionesSincronozacion.add(btnSincronizar);
        toolBarOpcionesSincronozacion.add(sptCuatro);

        btnExportar.setBackground(new java.awt.Color(242, 225, 175));
        btnExportar.setText("Exportar");
        btnExportar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExportar.setContentAreaFilled(false);
        btnExportar.setFocusable(false);
        btnExportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportar.setMaximumSize(new java.awt.Dimension(50, 17));
        btnExportar.setOpaque(true);
        btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarOpcionesSincronozacion.add(btnExportar);
        toolBarOpcionesSincronozacion.add(sptCinco);

        lblFiltrarDesde.setText("Filtrar desde: ");
        toolBarOpcionesSincronozacion.add(lblFiltrarDesde);

        dateDesde.setMaximumSize(new java.awt.Dimension(100, 20));
        dateDesde.setOpaque(false);
        dateDesde.setPreferredSize(new java.awt.Dimension(87, 17));
        toolBarOpcionesSincronozacion.add(dateDesde);

        lblHasta.setText(" hasta: ");
        toolBarOpcionesSincronozacion.add(lblHasta);

        dateHasta.setMaximumSize(new java.awt.Dimension(100, 20));
        dateHasta.setOpaque(false);
        toolBarOpcionesSincronozacion.add(dateHasta);

        lblEmpty3.setText(" ");
        toolBarOpcionesSincronozacion.add(lblEmpty3);

        btnBuscarAsistencia.setBackground(new java.awt.Color(242, 225, 175));
        btnBuscarAsistencia.setText("Buscar");
        btnBuscarAsistencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscarAsistencia.setContentAreaFilled(false);
        btnBuscarAsistencia.setFocusable(false);
        btnBuscarAsistencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscarAsistencia.setMaximumSize(new java.awt.Dimension(40, 17));
        btnBuscarAsistencia.setOpaque(true);
        btnBuscarAsistencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarOpcionesSincronozacion.add(btnBuscarAsistencia);
        toolBarOpcionesSincronozacion.add(sptSeis);

        btnEliminar.setBackground(new java.awt.Color(242, 225, 175));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setMaximumSize(new java.awt.Dimension(40, 17));
        btnEliminar.setMinimumSize(new java.awt.Dimension(35, 17));
        btnEliminar.setOpaque(true);
        btnEliminar.setPreferredSize(new java.awt.Dimension(35, 17));
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        toolBarOpcionesSincronozacion.add(btnEliminar);

        pnlSincronizacion.add(toolBarOpcionesSincronozacion, java.awt.BorderLayout.PAGE_START);

        scrTablaAsistencia.setViewportView(tblAsistencias);

        pnlSincronizacion.add(scrTablaAsistencia, java.awt.BorderLayout.CENTER);

        pnlSincronizarRegistros.add(pnlSincronizacion, java.awt.BorderLayout.CENTER);

        pnlContenedor.add(pnlSincronizarRegistros, "card4");

        getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

        pack();
		
    }// </editor-fold>

	/*
	 * 	INHABILITA TODOS LOS CAMPO DE TEXTO
	 */
	private void inhabilitarCampos() {
		txtCodigoCliente.setEditable(false);
		txtRazonSocial.setEditable(false);
		txtDireccion.setEditable(false);
		txtInvitado.setEditable(false);
		txtCorreo.setEditable(false);
		txtFacebook.setEditable(false);
		txtTwitter.setEditable(false);
		txtTelefono.setEditable(false);
		txtCelular.setEditable(false);
		txtOtro.setEditable(false);
		txtIntereses.setEditable(false);
		txtObservaciones.setEditable(false);
		txtCodigoGremioInst.setEditable(false);
	}
	
	/*
	 *	METODO DONDE SE ENCUENTRA TODOS LOS EVENTOS
	 */
	private void eventos() {
		btnSincronizarRegistros.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSincronizarRegistrosActionPerformed(evt);
			}
		});

		btnRegistrarAsistencia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnRegistrarAsistenciaActionPerformed(evt);
			}
		});

		btnDescargarListaClientes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDescargarListaClientesActionPerformed(evt);
			}
		});

		btnSalir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSalirActionPerformed(evt);
			}
		});

		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});

		btnGuardar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnGuardarActionPerformed(evt);
			}
		});

		btnActualizarCliente.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnActualizarClienteActionPerformed(evt);
			}
		});

		btnMarcarTodo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnMarcarTodoActionPerformed(evt);
			}
		});

		btnMarcarTodo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnMarcarTodoActionPerformed(evt);
			}
		});

		btnSincronizar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSincronizarActionPerformed(evt);
			}
		});

		btnExportar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExportarActionPerformed(evt);
			}
		});

		btnBuscarAsistencia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						llenarTablaAsistencia();
						bloqueador.dispose();
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
		});

		tblAsistencias.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblAsistenciasMouseClicked(evt);
			}
		});

		txtCodigoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtCodigoClienteFocusGained(evt);
			}
		});

		txtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtCodigoClienteKeyReleased(evt);
			}
		});
		
		cmbGremioEInstitutoEducativo.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusLost(java.awt.event.FocusEvent evt){
				cmbGremioEInstitutoEducativoFocusGained(evt);
			}
		});
	}

	protected void cmbGremioEInstitutoEducativoFocusGained(FocusEvent evt) {
		if(evt.getSource() == cmbGremioEInstitutoEducativo){
			if(txtCodigoGremioInst.isEditable()){
				String strNombreGremioInst = cmbGremioEInstitutoEducativo.getSelectedItem().toString();
				if(strNombreGremioInst != " - "){
					SqlFeriasImpl sqlFeria = new SqlFeriasImpl(); 
					txtCodigoGremioInst.setText(sqlFeria.obtenerCodigoGremioInstitucionEducativa(strNombreGremioInst));
				}
			}
		}
	}

	protected void txtCodigoClienteFocusGained(java.awt.event.FocusEvent evt) {
		if (!cmbRelacion.getSelectedItem().toString().equals("Gremios") && !cmbRelacion.getSelectedItem().toString().equals("Institución Educativa")) {
			if (evt.getSource() == txtCodigoCliente && !txtCodigoCliente.isEditable()) {
				txtCodigoCliente.setEditable(true);
				btnBuscar.setEnabled(true);;
				txtCodigoCliente.requestFocus();
				if (timerMensaje != null)
					timerMensaje.stop();
			}
		} else {
			habilitarCampos();
			txtCodigoCliente.setEditable(false);
			txtRazonSocial.setEditable(false);
			btnBuscar.setEnabled(false);
			txtCodigoGremioInst.requestFocusInWindow();
		}
	}

	/*
	 * 	VALIDA LOS INGRESE DE CAMPUS NUMERICOS
	 */
	private void validadorTexto() {
		ValidadorEntradasDeTexto validadorCodigoCliente = new ValidadorEntradasDeTexto(15, Constante.CADENAS);
		txtCodigoCliente.setDocument(validadorCodigoCliente);

		ValidadorEntradasDeTexto validadorTelefono = new ValidadorEntradasDeTexto(10, Constante.DECIMALES);
		txtTelefono.setDocument(validadorTelefono);

		ValidadorEntradasDeTexto validadorCelular = new ValidadorEntradasDeTexto(11, Constante.DECIMALES);
		txtCelular.setDocument(validadorCelular);

		ValidadorEntradasDeTexto validadorOtroTelefono = new ValidadorEntradasDeTexto(11, Constante.DECIMALES);
		txtOtro.setDocument(validadorOtroTelefono);
		
		ValidadorEntradasDeTexto validadorCodigoGremio = new ValidadorEntradasDeTexto(5, Constante.DECIMALES);
		txtCodigoGremioInst.setDocument(validadorCodigoGremio);
	}

	/*
	 * 	Secuencia
	 */
	private void agregarSecuencia() {
		fechaSecuencial = new Date();
		SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
		String secuencia = sqlFeria.obtenerUltimoRegistroAsistencia();
		String strFechaSecuencial = Util.convierteFechaAFormatoDDMMYYYYHHMMSS(fechaSecuencial);
		if (!secuencia.equals("")) {
			txtSecuencia.setText(strFechaSecuencial + "-" + Util.completarCeros(4, secuencia));
		} else {
			txtSecuencia.setText(strFechaSecuencial + "-0000");
		}
	}

	/*
	 * 	METODOS QUE CARGAS LOS COMBOS BOX
	 */
	@SuppressWarnings("unchecked")
	private void cargarLugarFerias() {// -- COMBO BOX -- LUGAR --
		SqlFeriasImpl sqlFerias = new SqlFeriasImpl();
		List<LugarFeria> lugaresferias = sqlFerias.obtenerLugares();
		cmbLugar.addItem(" - ");
		if (lugaresferias != null && lugaresferias.size() > 0) {
			for (LugarFeria lf : lugaresferias) {
				cmbLugar.addItem(lf.getNombreCiudad());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void cargarRelacion() {// -- COMBO BOX -- RELACIÓN --
		SqlFeriasImpl sqlFerias = new SqlFeriasImpl();
		List<Relacion> relaciones = sqlFerias.obtenerRelacion();
		cmbRelacion.addItem(" - ");
		if (relaciones != null && relaciones.size() > 0) {
			for (Relacion r : relaciones) {
				cmbRelacion.addItem(r.getTipoRelacion());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void cargarProfesion() {// -- COMBO BOX -- PROFESIÓN --
		SqlFeriasImpl sqlFerias = new SqlFeriasImpl();
		List<BeanProfesion> Profesiones = sqlFerias.obtenerProfesion();
		cmbProfesion.addItem(" - ");
		if (Profesiones != null && Profesiones.size() > 0) {
			for (BeanProfesion r : Profesiones) {
				cmbProfesion.addItem(r.getNombreProfesion());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void cargarGremioInst() {// -- COMBO BOX -- GREMIO INSTITUCIÓN EDUCATIVO --
		SqlFeriasImpl sqlFerias = new SqlFeriasImpl();
		List<BeanGremioEInstitutoEducativo> gremios = sqlFerias.obtenerGremioEInstitucionEducativa();
		cmbGremioEInstitutoEducativo.addItem(" - ");
		if (gremios != null && gremios.size() > 0) {
			for (BeanGremioEInstitutoEducativo r : gremios) {
				cmbGremioEInstitutoEducativo.addItem(r.getNombreGremioInst());
			}
		}
	}
	/*
	 * FIN
	 */

	protected void btnCancelarActionPerformed(ActionEvent evt) {
		vaciarCampos();
		inhabilitarCampos();
		cmbRelacion.requestFocusInWindow();
	}

	private void txtCodigoClienteKeyReleased(java.awt.event.KeyEvent evt) {
		if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
			SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
			String strCodigoCliente = txtCodigoCliente.getText();
			Cliente c = null;
			if (strCodigoCliente != null && !strCodigoCliente.equals("")) {
				if (source.equals("-1")) {
					c = sqlFeria.buscarCliente(strCodigoCliente);
					if (c != null) {
						habilitarCampos();
						llenarCampos(c);
					} else {
						lblMensaje.setText("<html><font color='red'>No existe Cliente con ese codigo.</font></html>");
						limpiaMensajes();
					}
				}
			} else {
				lblMensaje.setText("<html><font color='red'>Ingrese un número de codigo de cliente.</font></html>");
				limpiaMensajes();
			}
		}
	}

	public void limpiaMensajes() {
		timerMensaje = new javax.swing.Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblMensaje.setText("");
				txtHora.setText(getHoraActual());
			}
		});
		timerMensaje.start();
	}

	public void HoraAsistencia() {
		javax.swing.Timer t = new javax.swing.Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtHora.setText(getHoraActual());
			}
		});
		t.start();
	}

	/*
	 * 	HABILITA LOS CAMPOS
	 */
	private void habilitarCampos() {
		txtRazonSocial.setEditable(true);
		txtDireccion.setEditable(true);
		txtCorreo.setEditable(true);
		txtTelefono.setEditable(true);
		txtCelular.setEditable(true);
		txtOtro.setEditable(true);
		txtFacebook.setEditable(true);
		txtTwitter.setEditable(true);
		txtIntereses.setEditable(true);
		txtObservaciones.setEditable(true);
		if (cmbRelacion.getSelectedItem().toString().equals("Cliente")) {
			txtInvitado.setEditable(false);
			txtCorreo.requestFocusInWindow();
			btnBuscar.setEnabled(true);
		} else {
			txtInvitado.setEditable(true);
			txtInvitado.requestFocusInWindow();
		}
		if(cmbRelacion.getSelectedItem().toString().equals("Gremios") || cmbRelacion.getSelectedItem().toString().equals("Institución Educativa")) {
			txtCodigoCliente.setEditable(false);
			txtRazonSocial.setEditable(false);
			txtCodigoGremioInst.setEditable(true);
			txtCodigoGremioInst.requestFocusInWindow();
		} else {
			txtCodigoCliente.setEditable(true);
			txtRazonSocial.setEditable(true);
		}
	}

	/*
	 * 	LLENA LOS CAMPOS CON LOS DATOS DEL CLIENTE
	 */
	private void llenarCampos(Cliente c) {
		txtObservaciones.setEditable(true);
		if (cmbRelacion.getSelectedItem().toString().equals("Cliente")) {
			txtInvitado.setEditable(false);
			txtRazonSocial.setText("" + c.getRazonSocial());
			txtDireccion.setText("" + c.getCiudad());
			txtCorreo.setText("" + c.getCorreo());
			txtTelefono.setText("" + c.getTelefono());
			txtCelular.setText("" + c.getCelular());
			txtOtro.setText("" + c.getOtroTelefono());
			txtObservaciones.setEditable(true);
			txtCorreo.requestFocusInWindow();
		} else {
			txtRazonSocial.setText("" + c.getRazonSocial());
			txtDireccion.setText("" + c.getCiudad());
			txtInvitado.setEditable(true);
			txtInvitado.requestFocusInWindow();
		}
	}

	protected void btnExportarActionPerformed(ActionEvent evt) {
		TablaAExcel exportarFerias = new TablaAExcel();
		int filas[] = tblAsistencias.getSelectedRows();
		if (filas == null || filas.length == 0) {
			Mensaje.mostrarAviso("No existe información para exportar");
			return;
		}
		try {
			exportarFerias.generarReporteAsistencia("reporte-ferias" + Util.convierteFechaAFormatoDDMMMYYYYHHMMSS(new Date()) + ".xls", modeloTablaFeria);
		} catch (Exception e) {
			e.printStackTrace();
			Util.mostrarExcepcion(e);
		}
	}

	/*
	 * 	SELECCIONA TODAS LOS ELEMENTOS DE LA TABLA ASISTENCIA
	 */
	protected void btnMarcarTodoActionPerformed(ActionEvent evt) {
		tblAsistencias.getSelectionModel().setSelectionInterval(0, tblAsistencias.getRowCount() - 1);
	}

	/*
	 * 	 EVENTO DE DOBLE CLIP A LA TABLA ASISTENCIA
	 */
	protected void tblAsistenciasMouseClicked(MouseEvent evt) {
		final int fila = tblAsistencias.rowAtPoint(evt.getPoint());
		if (evt.getClickCount() == 2) {
			if (fila > -1) {
				llenarCamposAsistenciaEditar(fila);
				habilitarCampos();
			} else {
				Mensaje.mostrarAviso("Debe seleccionas un registro.");
			}
		}
	}

	private void llenarCamposAsistenciaEditar(int fila) {
		SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
		String codigoCiudadFeria = tblAsistencias.getValueAt(fila, 2).toString();
		txtAnio.setText(tblAsistencias.getValueAt(fila, 20).toString());
		txtSecuencia.setText(tblAsistencias.getValueAt(fila, 1).toString());
		cmbLugar.setSelectedItem(sqlFeria.obtenerNombreCiudad(codigoCiudadFeria));

		String strCodigoRelacion = tblAsistencias.getValueAt(fila, 3).toString();
		String strtipoRelacion = sqlFeria.obtenerTipoRelacion(strCodigoRelacion);
		cmbRelacion.setSelectedItem(strtipoRelacion);

		String strGremioInst = tblAsistencias.getValueAt(fila, 22).toString();
		if(!strGremioInst.equals("")){
			String strNombreGremioInst = sqlFeria.buscarGremioInstitucionEducativa(strGremioInst);
			cmbGremioEInstitutoEducativo.setSelectedItem(strNombreGremioInst);
			txtCodigoGremioInst.setText(strGremioInst);
		} else {
			cmbGremioEInstitutoEducativo.setSelectedItem(" - ");
		}
		
		String strProfesion = tblAsistencias.getValueAt(fila, 23).toString();
		if(!strProfesion.equals("")){
			String strNombreProfesion = sqlFeria.obtenerNombreProfesion(strProfesion);
			cmbProfesion.setSelectedItem(strNombreProfesion);
		} else {
			cmbProfesion.setSelectedItem(" - ");
		}
		String strCodigocliente = tblAsistencias.getValueAt(fila, 4).toString();
		if(!strCodigocliente.equals("")){
			Cliente c = sqlFeria.buscarCliente(strCodigocliente);
			if(c != null && !c.getRazonSocial().equals("")){
				txtCodigoCliente.setText(strCodigocliente);
				txtRazonSocial.setText(c.getRazonSocial());
			} else {
				txtCodigoCliente.setText("");
				txtRazonSocial.setText("");
			}
		} else {
			txtCodigoCliente.setText("");
			txtRazonSocial.setText("");
		}
		txtDireccion.setText(tblAsistencias.getValueAt(fila, 6).toString());
		txtInvitado.setText(tblAsistencias.getValueAt(fila, 7).toString());
		txtCorreo.setText(tblAsistencias.getValueAt(fila, 8).toString());
		txtFacebook.setText(tblAsistencias.getValueAt(fila, 9).toString());
		txtTwitter.setText(tblAsistencias.getValueAt(fila, 10).toString());
		txtTelefono.setText(tblAsistencias.getValueAt(fila, 11).toString());
		txtCelular.setText(tblAsistencias.getValueAt(fila, 12).toString());
		txtOtro.setText(tblAsistencias.getValueAt(fila, 13).toString());
		txtIntereses.setText(tblAsistencias.getValueAt(fila, 14).toString());
		txtObservaciones.setText(tblAsistencias.getValueAt(fila, 15).toString());
		txtFechaRegistro.setText(tblAsistencias.getValueAt(fila, 16).toString());
		txtHora.setText(tblAsistencias.getValueAt(fila, 17).toString());
		txtUsuario.setText(Promesa.datose.get(0).getStrUsuario());
		source = tblAsistencias.getValueAt(fila, 0).toString();
		if (source.equals("1")) {
			id = Long.parseLong(tblAsistencias.getValueAt(fila, 19).toString());
		} else {
			id = 0;
		}
		layaout.show(pnlContenedor, "card2");
	}

	protected void btnSincronizarActionPerformed(ActionEvent evt) {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				@SuppressWarnings("unused")
				public void run() {
					int filas[] = tblAsistencias.getSelectedRows();
					if (filas == null || filas.length == 0) {
						bloqueador.dispose();
						Mensaje.mostrarAviso("Debe Seleccionar Resistro para Seleccionar.");
						return;
					}
					List<Asistencia> listaAsistenciaNueva = new ArrayList<Asistencia>();
					List<Asistencia> listaAsistenciaEditada = new ArrayList<Asistencia>();
					SFerias sf = new SFerias();
					String[] resultado = null;
					List<Asistencia> listaAsistencia = modeloTablaFeria.getAsistencia();
					for (Asistencia asiste : listaAsistencia) {
						String strFecha = asiste.getFechaAsistencia();
						asiste.setFechaAsistencia(Util.convierteFechaDDMMYYYYAFormatoYYYYMMDD(strFecha));
						String strHora = asiste.getHoraAsistencia();
						asiste.setHoraAsistencia(Util.convierteHoraDosPuntosAHoraHHMMSS(strHora));
						asiste.setIdUsuario(Promesa.datose.get(0).getStrCodigo());
						if (asiste.getSource().equals("1")) {
							String[] strSecuencia = asiste.getSecuencia().split("-");
							String strSecuenciaNormal;
							if(strSecuencia.length > 1){
								strSecuenciaNormal = strSecuencia[0] + strSecuencia[1];
							} else {
								strSecuenciaNormal = strSecuencia[0];
							}
							asiste.setSecuencia(strSecuenciaNormal);
							listaAsistenciaNueva.add(asiste);
						} else {
							asiste.setIdUsuario(Promesa.datose.get(0).getStrCodigo());
							listaAsistenciaEditada.add(asiste);
						}
					}
					try {
						if (listaAsistenciaNueva.size() > 0) {
							resultado = sf.registrarAsistencia(listaAsistenciaNueva);
							SqlFeriasImpl sqlFerias = new SqlFeriasImpl();
							sqlFerias.asistenciasSincronizadas(listaAsistenciaNueva);
						}
						llenarTablaAsistencia();
					} catch (Exception e) {
						e.printStackTrace();
						Util.mostrarExcepcion(e);
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarWarning(Constante.ERROR_SINCRONIZACION);
		}
	}
	
	/*
	 * 	LLENA LA TABLA ASISTENCIA
	 */
	protected void llenarTablaAsistencia() {
		if (Util.comparaFecha1MenorOIgualQueFecha2(dateDesde.getDate(), dateHasta.getDate())) {
			SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
			String strFechaDesde = formato.format(dateDesde.getDate());
			String strFechaHasta = formato.format(dateHasta.getDate());
			List<Asistencia> listaAsistenciasSQL = new ArrayList<Asistencia>();
			SqlFeriasImpl sqlFerias = new SqlFeriasImpl();
			listaAsistenciasSQL = sqlFerias.obtenerAsistencias(Util.convierteFechaYYYYMMDDAFormatoDDMMYYYY(strFechaDesde), Util.convierteFechaYYYYMMDDAFormatoDDMMYYYY(strFechaHasta));
			if (Promesa.datose.get(0).getStrModo().equals("1")) {
				SFerias sf = new SFerias();
				List<Asistencia> listaAsistenciasSAP = sf.consultaFeriasAsistencia(Promesa.datose.get(0).getStrCodigo(), strFechaDesde, strFechaHasta);
				if (listaAsistenciasSAP != null) {
					for (Asistencia asiste : listaAsistenciasSAP) {
						String strCodigoCliente = asiste.getCodigoCliente();
						if(!strCodigoCliente.equals("")){
							Cliente c = sqlFerias.buscarCliente(strCodigoCliente);
							if(c != null && c.getRazonSocial() != null){
								asiste.setRazonSocial(c.getRazonSocial());
							} else {
								asiste.setRazonSocial("");
							}
						}
					}
					listaAsistenciasSQL.addAll(listaAsistenciasSAP);
				}
			}
			if (listaAsistenciasSQL != null) {
				final List<Asistencia> listaFinal = listaAsistenciasSQL;
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						modeloTablaFeria.limpiar();
						for (Asistencia asistencia : listaFinal) {
							modeloTablaFeria.agregarAsistencia(asistencia);
						}
						Util.setAnchoColumnas(tblAsistencias);
						tblAsistencias.updateUI();
						setAnchoColumnas();
					}
				});
			}
		} else {
			Mensaje.mostrarError("Fechas incorrectas.");
		}
	}
	// --------------------------------------------------------------------------------------------

	protected void setAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblAsistencias.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblAsistencias.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 19:
			case 20:
			case 21:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}

	public static String getHoraActual() {
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
		return formateador.format(ahora);
	}

	protected void btnActualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					SFerias sf = new SFerias();
					SqlFeriasImpl sqlFerias = new SqlFeriasImpl();

					// -- CLIENTES -- SAP -- A -- BASE LOCAL ----------------------
					List<Cliente> listaClientes = sf.cargarClienteFeriasSap(instance);
					sqlFerias.eliminarTablaClienteFerias();
					sqlFerias.guardarClientes2(instance, listaClientes);
					// ------------------------------------------------------------

					// -- LUGAR DE FERIAS -- SAP -- A -- BASE LOCAL ---------------
					List<LugarFeria> listaLugares = sf.consultaLugarFerias(instance, "S", "EC");
					sqlFerias.eliminarTablaLugarFerias();
					cmbLugar.removeAllItems();
					sqlFerias.guardarLugarFeria2(instance, listaLugares);
					// ------------------------------------------------------------

					// -- RELACIONES -- SAP -- A BASE LOCAL -----------------------
					List<Relacion> listaR = sf.consultaRelacion(instance);
					sqlFerias.eliminarTablaRelacionFerias();
					cmbRelacion.removeAllItems();
					sqlFerias.guardarRelacion(instance, listaR);
					// -------------------------------------------------------------

					// -- GREMIO INSTITUCIÓN EDUCATIVA -- SAP -- A -- BASE LOCAL --
					List<BeanGremioEInstitutoEducativo> listaGremioInst = sf.cargarGremioEInstitucionEducativa(instance);
					sqlFerias.eliminarTablaGremioEInstitucionEducativa();
					cmbGremioEInstitutoEducativo.removeAllItems();
					sqlFerias.guardarGremioEInstitucionEducativa(instance, listaGremioInst);
					// ------------------------------------------------------------

					// -- PROFESION -- SAP -- A -- BASE LOCAL ---------------------
					List<BeanProfesion> listaProfesion = sf.cargarProfesion(instance);
					sqlFerias.eliminarTablaProfesion();
					cmbProfesion.removeAllItems();
					sqlFerias.guardarProfesion(instance, listaProfesion);
					// ------------------------------------------------------------

					// --- CARGAR COMBO BOX ---
					cargarLugarFerias();// -- LUGAR FERIAS --
					cargarRelacion();// -- RELACIÓN --
					cargarGremioInst(); // -- GREMIO INSTITICIÓN EDUCATIVA --
					cargarProfesion();// -- PROFESIÓN --
				} catch (Exception e) {
					e.printStackTrace();
					Util.mostrarExcepcion(e);
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
		salir();
	}

	private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
		boolean resultado = false;
		if (source.equals("-1")) {
			resultado = guardarRegistroAsistencia();
		} else if (source.equals("1")) {
			resultado = ActualizarRegistroAsistencia();
		} else if (source.equals("0")) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						ActualizarRegistroSAP();
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
			resultado = true;
		}
		if (resultado == true) {
			InhabilitarCamposDespuesdeGuardar();
		}
	}

	/*
	 * 	INHABILITA LOS CAMPOS DESPUES DE GUARDAD 
	 */
	private void InhabilitarCamposDespuesdeGuardar() {
		vaciarCampos();
		inhabilitarCampos();
		cmbRelacion.requestFocusInWindow();
	}

	/*
	 * 	ACTUALIZA ASISTENCIA DE SAP
	 */
	private void ActualizarRegistroSAP() {
		String strFecha = txtFechaRegistro.getText();
		String strHora = txtHora.getText();
		String strIdUsuario = Promesa.datose.get(0).getStrCodigo();
		String strAnio = txtAnio.getText();
		String strSecuencia = txtSecuencia.getText();
		String strLugarFerias = cmbLugar.getSelectedItem().toString();
		String strIntere = txtIntereses.getText();
		String strObservacion = txtObservaciones.getText();
		String strTelefono = txtTelefono.getText();
		String strCelular = txtCelular.getText();
		String strOtroTelefono = txtOtro.getText();
		String strRazonSocial = txtRazonSocial.getText();
		String strDirreccion = txtDireccion.getText();
		String strGremioInst = cmbGremioEInstitutoEducativo.getSelectedItem().toString();
		String strProfesion = cmbProfesion.getSelectedItem().toString();
		String strCodigoGremioInst = txtCodigoGremioInst.getText();
		if (strLugarFerias.equals(" - ")) {
			Mensaje.mostrarAviso("Debe tener un Lugar de Ferias.");
			cmbLugar.requestFocusInWindow();
			return;
		}
		String strRelacion = cmbRelacion.getSelectedItem().toString();
		if (strRelacion.equals(" - ")) {
			cmbRelacion.requestFocusInWindow();
			Mensaje.mostrarAviso("Debe tener una Relación.");
			return;
		}
		String strCodigoCliente = txtCodigoCliente.getText();
		if (!strRelacion.equals("Gremios") && !strRelacion.equals("Institución Educativa")) {
			if (strCodigoCliente.equals("")) {
				Mensaje.mostrarAviso("Debe tener un codigo de cliente.");
				txtCodigoCliente.requestFocus();
				return;
			}
		} else if ( strRelacion.equals("Gremios") || strRelacion.equals("Institución Educativa")){
			if(strGremioInst.equals(" - ")){
				Mensaje.mostrarAviso("Debe seleccionar un gremio.");
				return;
			}
		}
		
		if (strRelacion.equals("Gremios") || strRelacion.equals("Usuario Final")) {
			if(strProfesion.equals(" - ")) {
				Mensaje.mostrarAviso("Gremios o Usuario final debe tener una Profesión.");
				cmbProfesion.requestFocusInWindow();
				return;
			}
		}
		
		if(strGremioInst.equals(" - ")){
			strGremioInst = "";
		}

		String strInvitado = txtInvitado.getText();
		if (strInvitado.equals("") && !strRelacion.equals("Cliente")) {
			Mensaje.mostrarAviso("Debe tener un invitado.");
			txtInvitado.requestFocus();
			return;
		}
		String strFacebook = txtFacebook.getText();
		if (strFacebook.length() >= 51) {
			Mensaje.mostrarAviso("su facebook debe tener una longuitud menos de 50 caracteres.");
			txtFacebook.requestFocus();
			return;
		}
		String strTwitter = txtTwitter.getText();
		if (strTwitter.length() >= 51) {
			Mensaje.mostrarAviso("su twitter debe tener una longuitud menos de 50 caracteres.");
			txtTwitter.requestFocus();
			return;
		}
		String strCorreo = txtCorreo.getText();
		if (strCorreo.length() >= 51) {
			Mensaje.mostrarAviso("su correo debe tener una longuitud menos de 50 caracteres.");
			txtCorreo.requestFocus();
			return;
		}
		if (strTelefono.equals("") && strCelular.equals("") && strOtroTelefono.equals("")) {
			Mensaje.mostrarAviso("Debe ingresar un número telefonico.");
			txtTelefono.requestFocus();
			return;
		}
		
		if(strProfesion.equals(" - ")){
			strProfesion = "";
		}
		
		SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
		final Asistencia asiste = new Asistencia();

		asiste.setEjercicio(strAnio);
		asiste.setSecuencia(strSecuencia);
		String strCodigoCiudadFeria = sqlFeria.obtenerCodigoCiudad(strLugarFerias);
		asiste.setCiudadFeria(strCodigoCiudadFeria);

		String strCodigoRelacion = sqlFeria.obtenerCodigoRelacion(strRelacion);

		asiste.setRelacion(strCodigoRelacion);
		asiste.setCodigoCliente(strCodigoCliente);
		asiste.setRazonSocial(strRazonSocial);
		asiste.setCiudadCliente(strDirreccion);
		asiste.setInvitado(strInvitado);
		asiste.setCorreo(strCorreo);
		asiste.setFacebook(strFacebook);
		asiste.setTwitter(strTwitter);
		asiste.setTelefono(strTelefono);
		asiste.setCelular(strCelular);
		asiste.setOtroTelefono(strOtroTelefono);
		asiste.setInteresEspeciales(strIntere);
		asiste.setObservacion(strObservacion);
		asiste.setFechaAsistencia(Util.convierteFechaDDMMYYYYAFormatoYYYYMMDD(strFecha));
		asiste.setHoraAsistencia(Util.convierteHoraDosPuntosAHoraHHMMSS(strHora));
		asiste.setIdUsuario(strIdUsuario);
		asiste.setGremio(strGremioInst);
		asiste.setProfesion(strProfesion);
		asiste.setSource(source);
		asiste.setId(String.valueOf(id));
		asiste.setGremio(strCodigoGremioInst);
		
		if(strProfesion != ""){
			String strCodigoProfecionString = sqlFeria.obtenerCodigoProfesion(strProfesion);
			asiste.setProfesion(strCodigoProfecionString);
		} else {
			asiste.setProfesion("");
		}
		
		SFerias sf = new SFerias();
		String[] resultadoSap = null;
		try {
			List<Asistencia> la = new ArrayList<Asistencia>();
			la.add(asiste);
			resultadoSap = sf.editarAsistencia(la);
			agregarSecuencia();
		} catch (Exception ex) {
			Util.mostrarExcepcion(ex);
		} finally {
			lblMensaje.setText("<html><font color='green'>" + resultadoSap[3] + "</font></html>");
		}
	}
	
	/*
	 * 	ACTUALIZA ASISTENCIA EN BASE LOCAL
	 */
	@SuppressWarnings("finally")
	private boolean ActualizarRegistroAsistencia() {
		if (validarEntradas()) {
			boolean flag = false;
			SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
			try {
				asiste.setId("" + id);
				sqlFeria.actualizarAsistencia(asiste);
				agregarSecuencia();
				flag = true;
			} catch (Exception ex) {
				flag = false;
				Util.mostrarExcepcion(ex);
			} finally {
				if (flag == true) {
					lblMensaje.setText("<html><font color='green'>Se guardo correctamente la asistencia.</font></html>");
					limpiaMensajes();
				} else {
					lblMensaje.setText("<html><font color='red'>No se guardo correctamente la asistencia.</font></html>");
					limpiaMensajes();
				}
				return flag;
			}
		} else
			return false;
	}
	
	/*
	 * 	GUARDA UNA NUEVA ASISTENCIA EN BASE LOCAL
	 */
	@SuppressWarnings("finally")
	private boolean guardarRegistroAsistencia() {
		if (validarEntradas()) {
			boolean flag = false;
			SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
			try {
				sqlFeria.registrarAsistencia(asiste);
				agregarSecuencia();
				flag = true;
			} catch (Exception ex) {
				flag = false;
				Util.mostrarExcepcion(ex);
			} finally {
				if (flag == true) {
					lblMensaje.setText("<html><font color='green'>Se guardo correctamente la asistencia.</font></html>");
				} else {
					lblMensaje.setText("<html><font color='red'>No se guardo correctamente la asistencia.</font></html>");
				}
				limpiaMensajes();
				return flag;
			}
		} else
			return false;
	}
	
	/*
	 *	VALIDA TODAS LAS DATOS INGRESADO 
	 */
	private boolean validarEntradas() {
		String strFecha = txtFechaRegistro.getText();
		String strHora = txtHora.getText();
		String strIdUsuario = Promesa.datose.get(0).getStrCodigo();
		String strAnio = txtAnio.getText();
		String strSecuencia = txtSecuencia.getText();
		String strLugarFerias = cmbLugar.getSelectedItem().toString();
		String strIntere = txtIntereses.getText();
		String strObservacion = txtObservaciones.getText();
		String strTelefono = txtTelefono.getText();
		String strCelular = txtCelular.getText();
		String strOtroTelefono = txtOtro.getText();
		String strRazonSocial = txtRazonSocial.getText().replace("'", " ");
		String strDirreccion = txtDireccion.getText();
		String strProfesion = cmbProfesion.getSelectedItem().toString();
		String strGremioInst = cmbGremioEInstitutoEducativo.getSelectedItem().toString();
		String strCodigoGremioInst = txtCodigoGremioInst.getText();
		if (strLugarFerias.equals(" - ")) {
			Mensaje.mostrarAviso("Debe tener un Lugar de Ferias.");
			cmbLugar.requestFocusInWindow();
			return false;
		}
		String strRelacion = cmbRelacion.getSelectedItem().toString();
		if (strRelacion.equals(" - ")) {
			cmbRelacion.requestFocusInWindow();
			Mensaje.mostrarAviso("Debe tener una Relación.");
			return false;
		}
		String strCodigoCliente = txtCodigoCliente.getText();
		if (!strRelacion.equals("Gremios") && !strRelacion.equals("Institución Educativa")) {
			if (strCodigoCliente.equals("")) {
				Mensaje.mostrarAviso("Debe tener un codigo de cliente.");
				txtCodigoCliente.requestFocus();
				return false;
			}
		}else if ( strRelacion.equals("Gremios") || strRelacion.equals("Institución Educativa")){
			if(strGremioInst.equals(" - ")){
				Mensaje.mostrarAviso("Debe seleccionar un gremio.");
				return false;
			}
		}
		if (strRelacion.equals("Gremios") || strRelacion.equals("Usuario Final")) {
			if(strProfesion.equals(" - ")) {
				Mensaje.mostrarAviso("Gremios o Usuario final debe tener una Profesión.");
				cmbProfesion.requestFocusInWindow();
				return false;
			}
		}
		if(strGremioInst.equals(" - ")){
			strCodigoGremioInst = "";
		}
		String strInvitado = txtInvitado.getText();
		if (strInvitado.equals("") && !strRelacion.equals("Cliente")) {
			Mensaje.mostrarAviso("Debe tener un invitado.");
			txtInvitado.requestFocus();
			return false;
		}
		String strFacebook = txtFacebook.getText();
		if (strFacebook.length() >= 51) {
			Mensaje.mostrarAviso("su facebook debe tener una longuitud menos de 50 caracteres.");
			txtFacebook.requestFocus();
			return false;
		}
		String strTwitter = txtTwitter.getText();
		if (strTwitter.length() >= 51) {
			Mensaje.mostrarAviso("su twitter debe tener una longuitud menos de 50 caracteres.");
			txtTwitter.requestFocus();
			return false;
		}
		String strCorreo = txtCorreo.getText();
		if (strCorreo.length() >= 51) {
			Mensaje.mostrarAviso("su correo debe tener una longuitud menos de 50 caracteres.");
			txtCorreo.requestFocus();
			return false;
		}
		if (strTelefono.equals("") && strCelular.equals("") && strOtroTelefono.equals("")) {
			Mensaje.mostrarAviso("Debe ingresar un número telefonico.");
			txtTelefono.requestFocus();
			return false;
		}
		if(strProfesion.equals(" - ")){
			strProfesion = "";
		}
		
		SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
		asiste = new Asistencia();

		asiste.setEjercicio(strAnio);
		asiste.setSecuencia(strSecuencia);
		String strCodigoCiudadFeria = sqlFeria.obtenerCodigoCiudad(strLugarFerias);
		asiste.setCiudadFeria(strCodigoCiudadFeria);

		String strCodigoRelacion = sqlFeria.obtenerCodigoRelacion(strRelacion);

		asiste.setRelacion(strCodigoRelacion);
		asiste.setCodigoCliente(strCodigoCliente);
		asiste.setRazonSocial(strRazonSocial);
		asiste.setCiudadCliente(strDirreccion);
		asiste.setInvitado(strInvitado);
		asiste.setCorreo(strCorreo);
		asiste.setFacebook(strFacebook);
		asiste.setTwitter(strTwitter);
		asiste.setTelefono(strTelefono);
		asiste.setCelular(strCelular);
		asiste.setOtroTelefono(strOtroTelefono);
		asiste.setInteresEspeciales(strIntere);
		asiste.setObservacion(strObservacion);
		asiste.setFechaAsistencia(strFecha);
		asiste.setHoraAsistencia(strHora);
		asiste.setIdUsuario(strIdUsuario);
		asiste.setSource(source);
		asiste.setGremio(strCodigoGremioInst);
		
		if(strProfesion != ""){
			String strCodigoProfecionString = sqlFeria.obtenerCodigoProfesion(strProfesion);
			asiste.setProfesion(strCodigoProfecionString);
		} else {
			asiste.setProfesion("");
		}
		return true;
	}

	/*
	 * 	VACIA TODOS LOS CAMPOS
	 */
	private void vaciarCampos() {
		cmbRelacion.setSelectedIndex(0);
		cmbProfesion.setSelectedIndex(0);
		cmbGremioEInstitutoEducativo.setSelectedIndex(0);
		txtCodigoCliente.setText("");
		txtRazonSocial.setText("");
		txtDireccion.setText("");
		txtInvitado.setText("");
		txtCorreo.setText("");
		txtFacebook.setText("");
		txtTwitter.setText("");
		txtTelefono.setText("");
		txtCelular.setText("");
		txtOtro.setText("");
		txtIntereses.setText("");
		txtObservaciones.setText("");
		txtCodigoGremioInst.setText("");
		source = "-1";
		id = -1;
	}

	public void establecerClienteSelecciondo(String[] clienteSeleccionado) {
		if (clienteSeleccionado != null && clienteSeleccionado.length == 8) {
			if(cmbRelacion.getSelectedItem().equals("Cliente")){
				txtCodigoCliente.setText(clienteSeleccionado[0]);
				txtRazonSocial.setText(clienteSeleccionado[2]);
				txtTelefono.setText(clienteSeleccionado[4]);
				txtCelular.setText(clienteSeleccionado[5]);
				txtDireccion.setText(clienteSeleccionado[6]);
				txtCorreo.setText(clienteSeleccionado[7]);
			} else {
				txtCodigoCliente.setText(clienteSeleccionado[0]);
				txtRazonSocial.setText(clienteSeleccionado[2]);
				txtDireccion.setText(clienteSeleccionado[6]);
			}
			habilitarCampos();
		}
	}

	private void btnRegistrarAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {
		vaciarCampos();
		inhabilitarCampos();
		source = "-1";
		layaout.show(pnlContenedor, "card2");
		cmbRelacion.requestFocusInWindow();
	}

	private void btnDescargarListaClientesActionPerformed(java.awt.event.ActionEvent evt) {
		consolaVisualizacion.setText("");
		layaout.show(pnlContenedor, "card3");
	}

	private void btnSincronizarRegistrosActionPerformed(java.awt.event.ActionEvent evt) {
		modeloTablaFeria.limpiar();
		layaout.show(pnlContenedor, "card4");
	}

	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		DListadoClientes dlg = new DListadoClientes(Promesa.getInstance(), true, this);
		dlg.setVisible(true);
	}

	/*
	 * 	METODO QUE IMPRIME MENSAJE EN CONSOLA DE VISUALIZACION
	 */
	public void registrarMensaje(String mensaje) {
		consolaVisualizacion.setText("\n" + mensaje);
		DefaultCaret caret = (DefaultCaret) consolaVisualizacion.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
		final int filas[] = tblAsistencias.getSelectedRows();
		if (filas == null || filas.length == 0) {
			return;
		}
		final List<Asistencia> asistentes = new ArrayList<Asistencia>();
		final SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
		if (filas.length > 0) {
			final int respuesta = JOptionPane.showConfirmDialog(this, "Desea Eliminar los Resistros locales", "Eliminar Asistencia", JOptionPane.YES_NO_CANCEL_OPTION);
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					if (respuesta == 0) {
						try {
							for (int i : filas) {
								asistentes.add(modeloTablaFeria.obtenerFila(i));
							}
							sqlFeria.asistenciasSincronizadas(asistentes);
							llenarTablaAsistencia();
						} catch (Exception ex) {
							Util.mostrarExcepcion(ex);
							ex.printStackTrace();
						} finally {
							bloqueador.dispose();
						}
					}
					tblAsistencias.updateUI();
					setAnchoColumnas();
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
	}
	
	private void txtCodigoGremioInstKeyReleased(java.awt.event.KeyEvent evt) {
		if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
			SqlFeriasImpl sqlFeria = new SqlFeriasImpl();
			String strCodigoGremioInst = txtCodigoGremioInst.getText();
			String strnombreGremioInst = "";
			if (strCodigoGremioInst != null && !strCodigoGremioInst.equals("")) {
				strnombreGremioInst = sqlFeria.buscarGremioInstitucionEducativa(strCodigoGremioInst);
					if (strnombreGremioInst != null && !strnombreGremioInst.equals("")) {
						cmbGremioEInstitutoEducativo.setSelectedItem(strnombreGremioInst);
						txtInvitado.requestFocusInWindow();
					} else {
						lblMensaje.setText("<html><font color='red'>No existe Gremio o Institución con ese codigo.</font></html>");
						limpiaMensajes();
					}
				}else {
					lblMensaje.setText("<html><font color='red'>Ingrese un número de codigo de cliente.</font></html>");
					limpiaMensajes();
				} 
			}
		}

	private void salir() {
		this.setVisible(false);
		this.dispose();
	}
	
	private javax.swing.JButton btnActualizarCliente;
	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnBuscarAsistencia;
	private javax.swing.JButton btnCancelar;
	private javax.swing.JButton btnDescargarListaClientes;
	private javax.swing.JButton btnEliminar;
	private javax.swing.JButton btnExportar;
	private javax.swing.JButton btnGuardar;
	private javax.swing.JButton btnMarcarTodo;
	private javax.swing.JButton btnRegistrarAsistencia;
	private javax.swing.JButton btnSalir;
	private javax.swing.JButton btnSincronizar;
	private javax.swing.JButton btnSincronizarRegistros;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbLugar;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbProfesion;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbRelacion;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbGremioEInstitutoEducativo;
	private javax.swing.JEditorPane consolaVisualizacion;
	private com.toedter.calendar.JDateChooser dateDesde;
	private com.toedter.calendar.JDateChooser dateHasta;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel lblAnio;
	private javax.swing.JLabel lblCelular;
	private javax.swing.JLabel lblCliente;
	private javax.swing.JLabel lblCorreo;
	private javax.swing.JLabel lblDatosBasicos;
	private javax.swing.JLabel lblDatosIngreso;
	private javax.swing.JLabel lblDireccion;
	private javax.swing.JLabel lblEmpty1;
	private javax.swing.JToolBar.Separator lblEmpty2;
	private javax.swing.JLabel lblEmpty3;
	private javax.swing.JLabel lblFacebook;
	private javax.swing.JLabel lblFechaRegistro;
	private javax.swing.JLabel lblFiltrarDesde;
	private javax.swing.JLabel lblGremioEInstitutoEducativo;
	private javax.swing.JLabel lblHasta;
	private javax.swing.JLabel lblHora;
	private javax.swing.JLabel lblIntereses;
	private javax.swing.JLabel lblInvitado;
	private javax.swing.JLabel lblLugar;
	private javax.swing.JLabel lblMensaje;
	private javax.swing.JLabel lblObservaciones;
	private javax.swing.JLabel lblOtro;
	private javax.swing.JLabel lblRelacion;
	private javax.swing.JLabel lblSecuencia;
	private javax.swing.JLabel lblTelefono;
	private javax.swing.JLabel lblTituloActualizacion;
	private javax.swing.JLabel lblTituloSincronizacion;
	private javax.swing.JLabel lblTwitter;
	private javax.swing.JLabel lblusuario;
	private javax.swing.JPanel pnlActualizacionBaseDatosClientes;
	private javax.swing.JPanel pnlCampos;
	private javax.swing.JPanel pnlCliente;
	private javax.swing.JPanel pnlContenedor;
	private javax.swing.JPanel pnlContenedorCampos;
	private javax.swing.JPanel pnlContenedorRegistro;
	private javax.swing.JPanel pnlCorreo;
	private javax.swing.JPanel pnlDatosBasicos;
	private javax.swing.JPanel pnlDatosBasicosControles;
	private javax.swing.JPanel pnlDatosIngresoContenedor;
	private javax.swing.JPanel pnlDatosRegistro;
	private javax.swing.JPanel pnlDescargarClientes;
	private javax.swing.JPanel pnlDireccion;
	private javax.swing.JPanel pnlEtiquetas;
	private javax.swing.JPanel pnlFacebook;
	private javax.swing.JPanel pnlGremioEInstitutoEducativo;
	private javax.swing.JPanel pnlIntereses;
	private javax.swing.JPanel pnlInvitado;
	private javax.swing.JPanel pnlObservaciones;
	private javax.swing.JPanel pnlProfesion;
	private javax.swing.JPanel pnlRazonSocial;
	private javax.swing.JPanel pnlRegistrarAsistencia;
	private javax.swing.JPanel pnlRelacion;
	private javax.swing.JPanel pnlSincronizacion;
	private javax.swing.JPanel pnlSincronizarRegistros;
	private javax.swing.JPanel pnlTelefonos;
	private javax.swing.JPanel pnlToolBar;
	private javax.swing.JPanel pnlTwitter;
	private javax.swing.JScrollPane scrClientes;
	private javax.swing.JScrollPane scrTablaAsistencia;
	private javax.swing.JToolBar.Separator sptCinco;
	private javax.swing.JToolBar.Separator sptCuatro;
	private javax.swing.JToolBar.Separator sptDos;
	private javax.swing.JToolBar.Separator sptSeis;
	private javax.swing.JToolBar.Separator sptTres;
	private javax.swing.JToolBar.Separator sptUno;
	private javax.swing.JTable tblAsistencias;
	private javax.swing.JToolBar toolBarActualizacion;
	private javax.swing.JToolBar toolBarOpciones;
	private javax.swing.JToolBar toolBarOpcionesSincronozacion;
	private javax.swing.JLabel txtAnio;
	private javax.swing.JTextField txtCelular;
	private javax.swing.JTextField txtCodigoCliente;
	private javax.swing.JTextField txtCorreo;
	private javax.swing.JTextField txtDireccion;
	private javax.swing.JTextField txtFacebook;
	private javax.swing.JLabel txtFechaRegistro;
	private javax.swing.JTextField txtCodigoGremioInst;
	private javax.swing.JLabel txtHora;
	private javax.swing.JTextField txtIntereses;
	private javax.swing.JTextField txtInvitado;
	private javax.swing.JTextField txtObservaciones;
	private javax.swing.JTextField txtOtro;
	private javax.swing.JTextField txtRazonSocial;
	private javax.swing.JLabel txtSecuencia;
	private javax.swing.JTextField txtTelefono;
	private javax.swing.JTextField txtTwitter;
	private javax.swing.JLabel txtUsuario;
}