package com.promesa.internalFrame.planificacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import com.promesa.bean.*;
import com.promesa.planificacion.bean.*;
import com.promesa.planificacion.sql.*;
import com.promesa.planificacion.sql.impl.*;
import com.promesa.sap.SPlanificacion;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.*;
import com.toedter.calendar.JCalendar;

public class IPlanificacion extends JInternalFrame implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	JToolBar mnuToolBar;
	JButton btnGuardar;
	JButton btnEliminar;
	private JLabel lblEjVen;
	@SuppressWarnings("rawtypes")
	JComboBox cmbEjVen;
	private JLabel lblClie;
	@SuppressWarnings("rawtypes")
	JComboBox cmbClie;
	private JLabel lblDiaOcu;
	private JLabel lblDesDO;
	private JLabel lblHorSug;
	@SuppressWarnings("rawtypes")
	JComboBox cmbHorSug;
	private JLabel lblFre;
	@SuppressWarnings("rawtypes")
	JComboBox cmbFre;
	private JLabel lblIdClie = new JLabel();
	private JLabel lblIdHorSug = new JLabel();
	private JLabel lblIdFre = new JLabel();
	private JLabel lblBotonImportExcel = null;
	private JLabel lblBotonLeerExcel = null;
	private JLabel lblBotonSalir = null;
	private JLabel lblBotonExaminarExcel = null;
	private JPopupMenu popupImportaExcel = null;
	private JPanel pnlImportaExcel = null;
	private JPanel pnlPrincipalPopup = null;
	private JPanel pnlInterno1Popup = null;
	private JPanel pnlInterno2Popup = null;
	private JPanel pnlInterno3Popup = null;
	private JPanel pnlInterno4Popup = null;
	private JTextField txtDireccionExcel;
	private JProgressBar pgbInsertado = null;
	JSeparator separador;
	JPanel pnlParametros;
	JPanel pnlListado;
	JScrollPane scrListado;
	private JTable tblPlanificacion;
	DefaultTableModel modeloTabla;
	private JCalendar dchCalendario;
	static List<BeanDato> datose;
	private SqlEmpleado getEmpleado = null;
	private SqlCliente getCliente = null;
	private SqlHora getHora = null;
	private SqlFrecuencia getFrecuencia = null;
	private SqlPlanificacion getPlanificacion = null;
	@SuppressWarnings("unused")
	private SqlVisita getVisita = null;
	BeanEmpleado em = null;
	BeanCliente clie = null;
	BeanHora hor = null;
	BeanFrecuencia fre = null;
	BeanPlanificacion pla = null;
	BeanVisita vis = null;
	List<BeanEmpleado> listaEmpleado = null;
	List<BeanCliente> listaCliente = null;
	List<BeanHora> listaHora = null;
	List<BeanFrecuencia> listaFrecuencia = null;
	List<BeanPlanificacion> listaPlanificacion = null;
	SPlanificacion objSAP;
	List<BeanPlanificacion> planificacionese;
	private String fecAct = "";
	private String fsp = ""; /* FECHA DE SELECCIÓN PARA PLANIFICACIÓN */
	private String fsv = ""; /* FECHA DE SELECCIÓN PARA VISITA */
	String[] fecha;
	private String DireccionFormato = " ";
	private String estado = "0"; // 0 = NUEVA PLANIFICACIÓN y 1 = ACTUALIZA
	private String iD = ""; /* ID DE LA PLANIFICACIÓN */

	@SuppressWarnings({ "static-access", "rawtypes" })
	public IPlanificacion(BeanTareaProgramada beanTareaProgramada) {

		super("Parámetros de Planificación", true, true, true, true);
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iplanificaciones.gif")));
		this.setPreferredSize(new java.awt.Dimension(606, 640));
		this.setBounds(0, 0, 606, 640);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar, BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 1366, 10); // 0, 39, 1318, 10

		this.datose = beanTareaProgramada.getDatose();
		getEmpleado = new SqlEmpleadoImpl();
		getCliente = new SqlClienteImpl();
		getHora = new SqlHoraImpl();
		getFrecuencia = new SqlFrecuenciaImpl();
		getPlanificacion = new SqlPlanificacionImpl();
		getVisita = new SqlVisitaImpl();

		btnGuardar = new JButton();
		mnuToolBar.add(btnGuardar);
		btnGuardar.setText("Guardar");
		btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.png")));
		btnGuardar.addActionListener(this);
		btnGuardar.setToolTipText("Guardar");
		btnGuardar.setPreferredSize(new java.awt.Dimension(93, 29));
		btnGuardar.setBackground(new java.awt.Color(0, 128, 64));
		btnGuardar.setOpaque(false);
		btnGuardar.setBorder(null);
		btnGuardar.setFocusable(false);

		btnEliminar = new JButton();
		mnuToolBar.add(btnEliminar);
		btnEliminar.setText("Eliminar");
		btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/eliminar.gif")));
		btnEliminar.addActionListener(this);
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setPreferredSize(new java.awt.Dimension(100, 29));
		btnEliminar.setBackground(new java.awt.Color(0, 128, 64));
		btnEliminar.setOpaque(false);
		btnEliminar.setBorder(null);
		btnEliminar.setFocusable(false);

		pnlParametros = new JPanel();
		getContentPane().add(pnlParametros);
		pnlParametros.setBounds(0, 48, 790, 210);
		pnlParametros.setLayout(null);
		pnlParametros.setBorder(BorderFactory.createTitledBorder("Parámetros"));
		pnlParametros.setFocusable(false);
		{
			lblEjVen = new JLabel();
			pnlParametros.add(lblEjVen);
			lblEjVen.setText("Ejecutivo de Ventas:");
			lblEjVen.setBounds(16, 25, 119, 17);
			lblEjVen.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbEjVen = new JComboBox();
			pnlParametros.add(cmbEjVen);
			cmbEjVen.setToolTipText("Ejecutivo de Ventas");
			cmbEjVen.setBounds(145, 23, 220, 24);
			cmbEjVen.setFocusable(false);
			cmbEjVen.setFont(new java.awt.Font("Candara", 0, 11));
			cargaInicialVendedor();
			cmbEjVen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							cargaCliente();
							bloqueador.dispose();
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}
			});
			cmbEjVen.setBackground(new java.awt.Color(255, 255, 255));
		}
		{
			lblClie = new JLabel();
			pnlParametros.add(lblClie);
			lblClie.setText("Cliente:");
			lblClie.setBounds(389, 25, 125, 17);
			lblClie.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbClie = new JComboBox();
			pnlParametros.add(cmbClie);
			cmbClie.setToolTipText("Cliente");
			cmbClie.setBounds(449, 23, 220, 24);
			cmbClie.setFocusable(false);
			cmbClie.setFont(new java.awt.Font("Candara", 0, 11));
			cargaInicialCliente();
			cmbClie.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							buscaPlanificacion();
							bloqueador.dispose();
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}
			});
			cmbClie.setBackground(new java.awt.Color(255, 255, 255));
		}
		{
			lblDiaOcu = new JLabel();
			pnlParametros.add(lblDiaOcu);
			lblDiaOcu.setText("Día y Ocurrencia:");
			lblDiaOcu.setBounds(16, 60, 125, 17);
			lblDiaOcu.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			dchCalendario = new JCalendar();
			pnlParametros.add(dchCalendario);
			dchCalendario.setBounds(335, 50, 200, 155);
			dchCalendario.setRequestFocusEnabled(false);
			dchCalendario.getDayChooser().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent evt) {
					validaFecha();
				}
			});
			dchCalendario.setFocusable(false);
		}
		{
			lblDesDO = new JLabel();
			pnlParametros.add(lblDesDO);
			lblDesDO.setText(capturaOrganizaFecha());
			lblDesDO.setBounds(145, 60, 147, 17);
			lblDesDO.setFont(new java.awt.Font("Arial", 2, 12));
			lblDesDO.setForeground(new java.awt.Color(0, 0, 255));
		}
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			{
				lblBotonImportExcel = new JLabel("Importar (desde Excel)");
				lblBotonImportExcel.setBackground(new Color(238, 238, 238));
				lblBotonImportExcel.setFont(new Font("Arial", 1, 11));
				lblBotonImportExcel.setIcon(new ImageIcon(getClass().getResource("/imagenes/exportar.gif")));
				lblBotonImportExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonImportExcel.setBounds(620, 180, 160, 20);
				lblBotonImportExcel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonImportExcelMouseClicked(evt);
					}

					public void mousePressed(MouseEvent evt) {
						lblBotonImportExcelMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonImportExcelMouseReleased(evt);
					}
				});
				pnlParametros.add(lblBotonImportExcel);
			}
			{
				lblBotonLeerExcel = new JLabel(" Leer ");
				lblBotonLeerExcel.setBackground(new Color(238, 238, 238));
				lblBotonLeerExcel.setFont(new Font("Arial", 1, 12));
				lblBotonLeerExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonLeerExcel.setBounds(650, 50, 90, 20);
				lblBotonLeerExcel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonLeerExcelMouseClicked(evt);
					}

					public void mousePressed(MouseEvent evt) {
						lblBotonLeerExcelMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonLeerExcelMouseReleased(evt);
					}
				});
			}
			{
				lblBotonSalir = new JLabel();
				lblBotonSalir.setBackground(new Color(238, 238, 238));
				lblBotonSalir.setFont(new Font("Arial", 1, 12));
				lblBotonSalir.setIcon(new ImageIcon(getClass().getResource("/imagenes/borrar.png")));
				lblBotonSalir.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonSalir.setBounds(650, 50, 150, 20);
				lblBotonSalir.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonSalirMouseClicked(evt);
					}

					public void mousePressed(MouseEvent evt) {
						lblBotonSalirMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonSalirMouseReleased(evt);
					}
				});
			}
			{
				lblBotonExaminarExcel = new JLabel(" Examinar ");
				lblBotonExaminarExcel.setBackground(new Color(238, 238, 238));
				lblBotonExaminarExcel.setFont(new Font("Arial", 1, 12));
				lblBotonExaminarExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonExaminarExcel.setBounds(650, 50, 90, 20);
				lblBotonExaminarExcel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonExaminarExcelMouseClicked(evt);
					}

					public void mousePressed(MouseEvent evt) {
						lblBotonExaminarExcelMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonExaminarExcelMouseReleased(evt);
					}
				});
			}
			{
				pgbInsertado = new JProgressBar(0, 100);
				pgbInsertado.setValue(0);
				pgbInsertado.setStringPainted(true);
			}
			{
				txtDireccionExcel = new JTextField();
				txtDireccionExcel.setEditable(false);
				txtDireccionExcel.setBackground(new java.awt.Color(255, 255,255));
			}
			{
				pnlInterno1Popup = new JPanel(new BorderLayout());
				pnlInterno1Popup.add(txtDireccionExcel, BorderLayout.NORTH);
			}
			{
				pnlInterno3Popup = new JPanel();
				pnlInterno3Popup.add(lblBotonLeerExcel);
				lblBotonLeerExcel.setBounds(1, 100, 60, 30);
			}
			{
				pnlInterno4Popup = new JPanel(new BorderLayout());
				pnlInterno4Popup.setLayout(new BorderLayout());
				pnlInterno4Popup.add(lblBotonSalir, BorderLayout.EAST);
			}
			{
				pnlInterno2Popup = new JPanel();
				pnlInterno2Popup.setLayout(new BorderLayout());
				pnlInterno2Popup.add(pnlInterno3Popup, BorderLayout.CENTER);
				pnlInterno2Popup.add(lblBotonExaminarExcel, BorderLayout.NORTH);
			}
			{
				pnlImportaExcel = new JPanel();
				pnlImportaExcel.setLayout(new BorderLayout());
				pnlImportaExcel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Importar Excel", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 1, 12)));
				pnlImportaExcel.add(pnlInterno1Popup, BorderLayout.CENTER);
				pnlImportaExcel.add(pnlInterno2Popup, BorderLayout.EAST);
			}
			{
				pnlPrincipalPopup = new JPanel();
				pnlPrincipalPopup.setLayout(new BorderLayout());
				pnlPrincipalPopup.add(pnlInterno4Popup, BorderLayout.NORTH);
				pnlPrincipalPopup.add(pnlImportaExcel, BorderLayout.CENTER);
				pnlPrincipalPopup.add(pgbInsertado, BorderLayout.SOUTH);
				pnlPrincipalPopup.setPreferredSize(new Dimension(300, 115));
			}
			{
				popupImportaExcel = new JPopupMenu();
				popupImportaExcel.add(pnlPrincipalPopup);
			}
		}
		{
			lblHorSug = new JLabel();
			pnlParametros.add(lblHorSug);
			lblHorSug.setText("Hora Sugerida:");
			lblHorSug.setBounds(16, 95, 125, 17);
			lblHorSug.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbHorSug = new JComboBox();
			pnlParametros.add(cmbHorSug);
			cmbHorSug.setToolTipText("Hora Sugerida");
			cmbHorSug.setBounds(145, 93, 147, 24);
			cmbHorSug.setFocusable(false);
			cmbHorSug.setFont(new java.awt.Font("Candara", 0, 11));
			cargaHora();
			cmbHorSug.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eventoHora();
				}
			});
			cmbHorSug.setBackground(new java.awt.Color(255, 255, 255));
		}
		{
			lblFre = new JLabel();
			pnlParametros.add(lblFre);
			lblFre.setText("Frecuencia:");
			lblFre.setBounds(16, 130, 125, 17);
			lblFre.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbFre = new JComboBox();
			pnlParametros.add(cmbFre);
			cmbFre.setToolTipText("Frecuencia");
			cmbFre.setBounds(145, 128, 147, 24);
			cmbFre.setFocusable(false);
			cmbFre.setFont(new java.awt.Font("Candara", 0, 11));
			cargaFrecuencia();
			cmbFre.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eventoFrecuencia();
				}
			});
			cmbFre.setBackground(new java.awt.Color(255, 255, 255));
		}
		pnlListado = new JPanel();
		getContentPane().add(pnlListado);
		pnlListado.setBounds(0, 260, 790, 145);
		pnlListado.setLayout(null);
		pnlListado.setBorder(BorderFactory.createTitledBorder("Planificaciones"));
		pnlListado.setFocusable(false);

		scrListado = new JScrollPane();
		pnlListado.add(scrListado);
		scrListado.setBounds(50, 29, 680, 95);
		scrListado.setToolTipText("Relación de Planificaciones");
		buscaPlanificacion();
	}

	/* MÉTODO QUE LISTA PLANIFICACIONES */
	public void buscaPlanificacion() {
		BeanEmpleado be = (BeanEmpleado) cmbEjVen.getSelectedItem();
		BeanCliente bc = (BeanCliente) cmbClie.getSelectedItem();
		planificacionese = new ArrayList<BeanPlanificacion>();
		try {
			objSAP = new SPlanificacion();
			if (be == null && bc == null) {
				listaPlanificacion = null;
			} else if (be.getStrIdEmpleado() == null && bc.getStrIdCliente() == null) {
				Mensaje.mostrarAviso(Constante.MENSAJE_NO_VENDEDOR_NO_CLIENTE);
			} else if (be.getStrIdEmpleado() != null && bc == null) {
				if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
					listaPlanificacion = objSAP.listaPlanificacion(be.getStrIdEmpleado(), Constante.VACIO);
				} else {
					BeanPlanificacion planificacion = new BeanPlanificacion();
					planificacion.setStrIdVendedor(be.getStrIdEmpleado());
					planificacion.setStrIdCliente(Constante.VACIO);
					getPlanificacion.setListaPlanificacion(planificacion);
					listaPlanificacion = getPlanificacion.getListaPlanificacion();
				}
			} else if (be.getStrIdEmpleado() != null && bc.getStrIdCliente() != null) {
				if (bc.getStrIdCliente().equals(Constante.VACIO)) {
					if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
						listaPlanificacion = objSAP.listaPlanificacion(be.getStrIdEmpleado(), Constante.VACIO);
					} else {
						BeanPlanificacion planificacion = new BeanPlanificacion();
						planificacion.setStrIdVendedor(be.getStrIdEmpleado());
						planificacion.setStrIdCliente(Constante.VACIO);
						getPlanificacion.setListaPlanificacion(planificacion);
						listaPlanificacion = getPlanificacion.getListaPlanificacion();
					}
				} else {
					if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
						listaPlanificacion = objSAP.listaPlanificacion(Constante.VACIO, bc.getStrIdCliente());
					} else {
						BeanPlanificacion planificacion = new BeanPlanificacion();
						planificacion.setStrIdVendedor(Constante.VACIO);
						planificacion.setStrIdCliente(bc.getStrIdCliente());
						getPlanificacion.setListaPlanificacion(planificacion);
						listaPlanificacion = getPlanificacion.getListaPlanificacion();
					}
				}
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		if (listaPlanificacion != null) {
			for (BeanPlanificacion p : listaPlanificacion) {
				pla = new BeanPlanificacion();
				pla.setStrIdPlan(p.getStrIdPlan());
				pla.setStrIdVendedor(p.getStrIdVendedor());
				pla.setStrIdJefe(p.getStrIdJefe());
				pla.setStrFechaInicio(p.getStrFechaInicio());
				pla.setStrFechaFin(p.getStrFechaFin());
				pla.setStrHora(p.getStrHora());
				pla.setStrIdCliente(p.getStrIdCliente());
				pla.setStrIdFrecuencia(p.getStrIdFrecuencia());
				pla.setStrFrecuencia(p.getStrFrecuencia());
				pla.setStrDia(p.getStrDia());
				pla.setStrNombreCliente(p.getStrNombreCliente());
				try {
					planificacionese.add(pla);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
				}
			}
		}
		if (planificacionese == null || planificacionese.size() == 0) {
			tablaVacia();
			btnEliminar.setEnabled(false);
		} else {
			tablaLLena();
			btnEliminar.setEnabled(true);
		}
	}

	/* MÉTODO QUE CARGA VENDEDORES SEGÚN SUPERVISOR */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargaInicialVendedor() {
		BeanEmpleado empleado = new BeanEmpleado();
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			try {
				objSAP = new SPlanificacion();
				listaEmpleado = objSAP.listaVendedor(((BeanDato) datose.get(0)).getStrCodigo());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(e.getMessage());
			}
		} else {
			empleado.setStrIdSupervisor(((BeanDato) datose.get(0)).getStrCodigo());
			getEmpleado.setListaEmpleado(empleado);
			listaEmpleado = new ArrayList<BeanEmpleado>();
			listaEmpleado = getEmpleado.getListaEmpleado();
		}
		if (listaEmpleado != null && listaEmpleado.size() > 0) {
			cmbEjVen.setModel(new DefaultComboBoxModel(listaEmpleado.toArray()));
		} else {
			cmbEjVen.setModel(new DefaultComboBoxModel());
		}
	}

	/* MÉTODO QUE CARGA CLIENTES SEGÚN VENDEDOR */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargaInicialCliente() {
		if (listaEmpleado != null && listaEmpleado.size() > 0) {
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
				try {
					objSAP = new SPlanificacion();
					listaCliente = objSAP.listaClientePlanificacion(listaEmpleado.get(0).getStrIdEmpleado());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					Mensaje.mostrarError(e.getMessage());
				}
			} else {
				BeanCliente cliente = new BeanCliente();
				BeanClienteEmpleado clienteEmpleado = new BeanClienteEmpleado();
				clienteEmpleado.setStrIdEmpleado(listaEmpleado.get(0).getStrIdEmpleado());
				cliente.setClienteEmpleado(clienteEmpleado);
				getCliente.setListaCliente(cliente);
				listaCliente = new ArrayList<BeanCliente>();
				listaCliente = getCliente.getListaCliente();
			}
		}
		if (listaCliente != null && listaCliente.size() > 0) {
			cmbClie.setModel(new DefaultComboBoxModel(listaCliente.toArray()));

		} else {
			cmbClie.setModel(new DefaultComboBoxModel());
		}
	}

	/* MÉTODO QUE CARGA CLIENTES SEGÚN CAMBIO DE VENDEDOR */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargaCliente() {
		BeanEmpleado be = (BeanEmpleado) cmbEjVen.getSelectedItem();
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			try {
				objSAP = new SPlanificacion();
				listaCliente = objSAP.listaClientePlanificacion(be.getStrIdEmpleado());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(e.getMessage());
			}
		} else {
			BeanCliente cliente = new BeanCliente();
			BeanClienteEmpleado clienteEmpleado = new BeanClienteEmpleado();
			clienteEmpleado.setStrIdEmpleado(be.getStrIdEmpleado());
			cliente.setClienteEmpleado(clienteEmpleado);
			getCliente.setListaCliente(cliente);
			listaCliente = new ArrayList<BeanCliente>();
			listaCliente = getCliente.getListaCliente();
		}
		if (listaCliente != null && listaCliente.size() > 0) {
			cmbClie.setModel(new DefaultComboBoxModel(listaCliente.toArray()));
		} else {
			cmbClie.setModel(new DefaultComboBoxModel());
		}
		buscaPlanificacion();
	}

	/* MÉTODO QUE PINTA UNA TABLA SIN DATOS */
	public void tablaVacia() {
		String Columnas[] = { "Plan", "Fecha Inicio", "Día y Ocurrencia", "Hora", "Frecuencia", "ID Cliente", "Cliente" };
		modeloTabla = new DefaultTableModel(null, Columnas);
		TableModel tblTablaModel = new DefaultTableModel(
				new Object[][] { { "", "", "", "", "", "", "" },
						{ "", "", "", "", "", "", "" },
						{ "", "", "", "", "", "", "" },
						{ "", "", "", "", "", "", "" } }, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblPlanificacion = new JTable();
		tblPlanificacion.setModel(tblTablaModel);
		scrListado.setViewportView(tblPlanificacion);
		pnlListado.add(scrListado);
	}

	/* MÉTODO QUE PINTA UNA TABLA CON DATOS */
	public void tablaLLena() {
		String Columnas[] = { "Plan", "Fecha Inicio", "Día y Ocurrencia", "Hora", "Frecuencia", "ID Cliente", "Cliente" };
		modeloTabla = new DefaultTableModel(null, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		if (planificacionese.size() > 4) {
			modeloTabla.setNumRows(planificacionese.size());
		} else {
			modeloTabla.setNumRows(4);
		}
		for (int i = 0; i < planificacionese.size(); i++) {
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrIdPlan(), i, 0);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
				modeloTabla.setValueAt(convierteFecha(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio()), i, 1);
			} else {
				modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio(), i, 1);
			}
			modeloTabla.setValueAt(convierteDO(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio()), i, 2);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrHora(),i, 3);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrFrecuencia(), i, 4);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrIdCliente(), i, 5);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrNombreCliente(), i, 6);
		}
		tblPlanificacion = new JTable();
		tblPlanificacion.setModel(modeloTabla);
		tblPlanificacion.addKeyListener(this);
		tblPlanificacion.addMouseListener(this);
		tblPlanificacion.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(3).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(4).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(5).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(6).setCellRenderer(new Renderer().getCenterCell());
		scrListado.setViewportView(tblPlanificacion);
		pnlListado.add(scrListado);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 0, 70);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 1, 80);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 2, 130);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 3, 40);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 4, 70);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 5, 89);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 6, 201);
	}

	/* MÉTODO QUE OBTIENE DÍA Y OCURRENCIA */
	public String convierteDO(String fsf) {
		int dia, mes, anio;
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			dia = Integer.parseInt(convierteFecha(fsf).substring(0, 2));
		} else {
			dia = Integer.parseInt(fsf.substring(0, 2));
		}
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			mes = Integer.parseInt(convierteFecha(fsf).substring(3, 5)) - 1;
		} else {
			mes = Integer.parseInt(fsf.substring(3, 5)) - 1;
		}
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			anio = Integer.parseInt(convierteFecha(fsf).substring(6, 10));
		} else {
			anio = Integer.parseInt(fsf.substring(6, 10));
		}
		Calendar c = new GregorianCalendar(Locale.ROOT);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(anio, mes, dia);
		int posicionMes = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			return obtieneDO(String.valueOf(posicionMes), convierteFecha(fsf));
		} else {
			return obtieneDO(String.valueOf(posicionMes), fsf);
		}
	}

	/* CONVERSIÓN A DD.MM.YYYY */
	public String convierteFecha(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return e.getMessage();
		}
	}

	/* MÉTODO QUE CAPTURA ID FRECUENCIA */
	public void eventoFrecuencia() {
		lblIdFre.setText(listaFrecuencia.get(cmbFre.getSelectedIndex()).getStrIdFrecuencia());
	}

	/* MÉTODO QUE CAPTURA ID HORA */
	public void eventoHora() {
		lblIdHorSug.setText(listaHora.get(cmbHorSug.getSelectedIndex()).getStrIdHora());
	}

	/* MÉTODO QUE CAPTURA ID CLIENTE */
	public void eventoCliente() {
		lblIdClie.setText(listaCliente.get(cmbClie.getSelectedIndex()).getStrIdCliente());
	}

	/* MÉTODO QUE CARGA LAS FRECUENCIAS */
	@SuppressWarnings("unchecked")
	public void cargaFrecuencia() {
		BeanFrecuencia frecuencia = new BeanFrecuencia();
		getFrecuencia.setListaFrecuencia(frecuencia);
		listaFrecuencia = new ArrayList<BeanFrecuencia>();
		listaFrecuencia = getFrecuencia.getListaFrecuencia();
		if (listaFrecuencia != null && listaFrecuencia.size() > 0) {
			lblIdFre.setText(listaFrecuencia.get(0).getStrIdFrecuencia());
			for (int i = 0; i < listaFrecuencia.size(); i++) {
				fre = new BeanFrecuencia();
				fre = listaFrecuencia.get(i);
				cmbFre.addItem(fre.getStrFrecuencia());
			}
		} else {
			cmbFre.addItem("");
		}
	}

	/* MÉTODO QUE CARGA LAS HORAS */
	@SuppressWarnings("unchecked")
	public void cargaHora() {
		BeanHora hora = new BeanHora();
		getHora.setListaHora(hora);
		listaHora = new ArrayList<BeanHora>();
		listaHora = getHora.getListaHora();
		if (listaHora != null && listaHora.size() > 0) {
			lblIdHorSug.setText(listaHora.get(0).getStrIdHora());
			for (int i = 0; i < listaHora.size(); i++) {
				hor = new BeanHora();
				hor = listaHora.get(i);
				cmbHorSug.addItem(hor.getStrHora());
			}
		} else {
			cmbHorSug.addItem("");
		}
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
		if (arg0.getClickCount() == 2) {
			if (arg0.getSource() == tblPlanificacion) {
				int fila = tblPlanificacion.rowAtPoint(arg0.getPoint());
				if ((fila > -1)) {
					estado = "1"; /* ESPECIFICA UNA ACTUALIZACIÓN */
					iD = modeloTabla.getValueAt(fila, 0).toString();
					cmbEjVen.setEnabled(false);
					cmbClie.setEnabled(false);
					btnEliminar.setEnabled(false);
				} else {
					Mensaje.mostrarError(Constante.MENSAJE_BUSQUEDA_PLANIFICACION);
				}
			}
		}
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

	/* MÉTODO QUE PROCESA LA FECHA */
	public String[] fecha() {
		String[] fecha = new String[3];
		fecha[0] = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR));
		fecha[1] = Integer.toString(dchCalendario.getCalendar().get(
				java.util.Calendar.MONTH) + 1);
		if (fecha[1].equals("1") || fecha[1].equals("2") || fecha[1].equals("3") || fecha[1].equals("4")
				|| fecha[1].equals("5") || fecha[1].equals("6") || fecha[1].equals("7") || fecha[1].equals("8") || fecha[1].equals("9")) {
			fecha[1] = "0" + fecha[1];
		}
		fecha[2] = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.DATE));
		if (fecha[2].equals("1") || fecha[2].equals("2") || fecha[2].equals("3") || fecha[2].equals("4")
				|| fecha[2].equals("5") || fecha[2].equals("6") || fecha[2].equals("7") || fecha[2].equals("8")
				|| fecha[2].equals("9")) {
			fecha[2] = "0" + fecha[2];
		}
		return fecha;
	}

	/* MÉTODO QUE RECIBE Y ENVÍA LA FECHA */
	public String capturaOrganizaFecha() {
		fecha = fecha();
		fecAct = fecha[2] + "." + fecha[1] + "." + fecha[0];
		return fecAct;
	}

	/* MÉTODO QUE OBTIENE LA OCURRENCIA */
	public String obtieneDO(String v1, String v2) {
		String cadena = "";
		if (v1.equals("1")) {
			cadena = Constante.PRIMERO;
		}
		if (v1.equals("2")) {
			cadena = Constante.SEGUNDO;
		}
		if (v1.equals("3")) {
			cadena = Constante.TERCERO;
		}
		if (v1.equals("4")) {
			cadena = Constante.CUARTO;
		}
		if (v1.equals("5")) {
			cadena = Constante.QUINTO;
		}
		return cadena + " " + FechaHora.getDia(v2) + " del mes";
	}

	/* MÉTODO QUE ANUNCIA LOS MESES (DE REALIZACIÓN) DE LA PLANIFICACIÓN */
	public String anunciaMesesPlanificacion(String mes) {
		String msg = "";
		if (mes.equals("01")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Enero y Febrero.";
		}
		if (mes.equals("02")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Febrero y Marzo.";
		}
		if (mes.equals("03")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Marzo y Abril.";
		}
		if (mes.equals("04")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Abril y Mayo.";
		}
		if (mes.equals("05")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Mayo y Junio.";
		}
		if (mes.equals("06")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Junio y Julio.";
		}
		if (mes.equals("07")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Julio y Agosto.";
		}
		if (mes.equals("08")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Agosto y Setiembre.";
		}
		if (mes.equals("09")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Setiembre y Octubre.";
		}
		if (mes.equals("10")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Octubre y Noviembre.";
		}
		if (mes.equals("11")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Noviembre y Diciembre.";
		}
		if (mes.equals("12")) {
			msg = "Tener en cuenta que la planificación (generada) es para los meses de Diciembre y Enero.";
		}
		return msg;
	}

	/* MÉTODO QUE VALIDA LA FECHA */
	public void validaFecha() {
		String año; /* AÑO */
		año = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR)); /* CAPTURA AÑO */
		if ((FechaHora.getFecha().charAt(6) + Constante.VACIO + FechaHora.getFecha().charAt(7) + Constante.VACIO + FechaHora.getFecha().charAt(8) + Constante.VACIO + FechaHora.getFecha().charAt(9)).equals(año)) {
			String vD;
			vD = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.DAY_OF_WEEK));
			if (!(vD.equals("1"))) {
				String mes, dia;
				mes = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.MONTH) + 1);
				if (mes.equals("1") || mes.equals("2") || mes.equals("3") || mes.equals("4") || mes.equals("5")
									|| mes.equals("6") || mes.equals("7") || mes.equals("8") || mes.equals("9")) {
					mes = "0" + mes;
				}
				dia = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.DATE));
				if (dia.equals("1") || dia.equals("2") || dia.equals("3") || dia.equals("4") || dia.equals("5")
									|| dia.equals("6") || dia.equals("7") || dia.equals("8") || dia.equals("9")) {
					dia = "0" + dia;
				}
				fsp = año + mes + dia;
				fsv = dia + Constante.SEPARADOR + mes + Constante.SEPARADOR + año;
				lblDesDO.setText(obtieneDO(String.valueOf(dchCalendario.getCalendar().get(java.util.Calendar.DAY_OF_WEEK_IN_MONTH)), fsv));
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_DOMINGO);
			}
		} else {
			Mensaje.mostrarError(Constante.MENSAJE_ERROR_AÑO_ACTUAL);
		}
	}

	/* MÉTODO QUE CALCULA EL MES */
	public String calculaMes(String cadena) {
		String mes = "";
		if ((cadena.charAt(4) + "" + cadena.charAt(5)).equals("12")) {
			mes = "01";
		} else {
			int temp;
			temp = Integer.parseInt(cadena.charAt(4) + "" + cadena.charAt(5)) + 1;
			if (temp < 10) {
				mes = "0" + temp;
			} else {
				mes = String.valueOf(temp);
			}
		}
		return mes;
	}

	/* MÉTODO QUE CALCULA EL DÍA */
	public String calculaDia(String cadena) {
		String dia = "";
		if (calculaMes(cadena).equals("01")) {
			dia = "31";
		}
		if (calculaMes(cadena).equals("02")) {
			GregorianCalendar calendar = new GregorianCalendar();
			if (calendar.isLeapYear(Integer.parseInt(cadena.charAt(0) + "" + cadena.charAt(1) + "" + cadena.charAt(2) + "" + cadena.charAt(3))))
				dia = "29";
			else
				dia = "28";
		}
		if (calculaMes(cadena).equals("03")) {
			dia = "31";
		}
		if (calculaMes(cadena).equals("04")) {
			dia = "30";
		}
		if (calculaMes(cadena).equals("05")) {
			dia = "31";
		}
		if (calculaMes(cadena).equals("06")) {
			dia = "30";
		}
		if (calculaMes(cadena).equals("07")) {
			dia = "31";
		}
		if (calculaMes(cadena).equals("08")) {
			dia = "31";
		}
		if (calculaMes(cadena).equals("09")) {
			dia = "30";
		}
		if (calculaMes(cadena).equals("10")) {
			dia = "31";
		}
		if (calculaMes(cadena).equals("11")) {
			dia = "30";
		}
		if (calculaMes(cadena).equals("12")) {
			dia = "31";
		}
		return dia;
	}

	/* MÉTODO QUE INGRESA UNA PLANIFICACIÓN */
	@SuppressWarnings("static-access")
	public void ingresaModificaPlanificacion() {
		BeanEmpleado be = (BeanEmpleado) cmbEjVen.getSelectedItem();
		BeanCliente bc = (BeanCliente) cmbClie.getSelectedItem();
		if (be == null && bc == null) {
			Mensaje.mostrarError(Constante.MENSAJE_ERROR_CAMPOS_VACIOS_VENDEDOR_CLIENTE);
		} else if (cmbEjVen.getSelectedItem().toString().equals("") && cmbClie.getSelectedItem().toString().equals("")) {
			Mensaje.mostrarError(Constante.MENSAJE_ERROR_CAMPOS_VACIOS_VENDEDOR_CLIENTE);
		} else if (cmbEjVen.getSelectedItem().toString().equals("") && !cmbClie.getSelectedItem().toString().equals("")) {
			Mensaje.mostrarError(Constante.MENSAJE_ERROR_CAMPO_VACIO_VENDEDOR);
		} else if (!cmbEjVen.getSelectedItem().toString().equals("") && cmbClie.getSelectedItem().toString().equals("")) {
			Mensaje.mostrarError(Constante.MENSAJE_ERROR_CAMPO_VACIO_CLIENTE);
		} else if (!cmbEjVen.getSelectedItem().toString().equals("") && !cmbClie.getSelectedItem().toString().equals("")) {
			if (estado.equals("0")) {
				if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							BeanEmpleado be = (BeanEmpleado) cmbEjVen.getSelectedItem();
							BeanCliente bc = (BeanCliente) cmbClie.getSelectedItem();
							String idPla;
							try {
								objSAP = new SPlanificacion();
								idPla = objSAP.ingresaPlanificacion(be.getStrIdEmpleado(), ((BeanDato) datose.get(0)).getStrCodigo(),
										fsp, Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR))
												+ calculaMes(fsp) + calculaDia(fsp), cmbHorSug .getSelectedItem().toString(), bc.getStrIdCliente(), "0"
												+ lblIdFre.getText().trim());
								if (idPla != null) {
									String msg;
									for (int j = 0; j < idPla.length(); j++) {
										if (idPla.charAt(j) != '0') {
											idPla = idPla.substring(j, idPla.length());
											break;
										}
									}
									Visita objFI = new Visita();
									List<String> lstFechas = objFI.siguientesVisitas(fsv, "0" + lblIdFre.getText().trim());
									msg = objSAP.ingresaVisitaFueraDeRuta(be.getStrIdEmpleado(), bc.getStrIdCliente(), lstFechas, cmbHorSug.getSelectedItem().toString(), Constante.TIPO_VISITA1, idPla);
									if (msg != null) {
										Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_NUEVA_PLANIFICACION);
										Mensaje.mostrarAviso(anunciaMesesPlanificacion(fsp.substring(4, 6)));
										buscaPlanificacion();
									} else {
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_PLANIFICACION);
									}
								} else {
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_PLANIFICACION);
								}
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_PLANIFICACION);
							}
							bloqueador.dispose();
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				} else {
					Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_NUEVA_PLANIFICACION);
				}
			} else {
				if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							BeanEmpleado be = (BeanEmpleado) cmbEjVen.getSelectedItem();
							BeanCliente bc = (BeanCliente) cmbClie.getSelectedItem();
							String msg1;
							try {
								objSAP = new SPlanificacion();
								msg1 = objSAP.actualizaPlanificacion(iD, bc.getStrIdCliente(), fsp, cmbHorSug.getSelectedItem().toString(), "0" + lblIdFre.getText().trim());
								if (msg1 != null) {
									String msg2;
									Visita objFI = new Visita();
									List<String> lstFechas = objFI.siguientesVisitas(fsv, "0"+ lblIdFre.getText().trim());
									msg2 = objSAP.ingresaVisitaFueraDeRuta(be.getStrIdEmpleado(), bc.getStrIdCliente(), lstFechas, cmbHorSug.getSelectedItem().toString(), Constante.TIPO_VISITA1, iD);
									if (msg2 != null) {
										Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_MODIFICA_PLANIFICACION);
										Mensaje.mostrarAviso(anunciaMesesPlanificacion(fsp.substring(4, 6)));
										buscaPlanificacion();
									} else {
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_MODIFICA_PLANIFICACION);
									}
									cmbEjVen.setEnabled(true);
									cmbClie.setEnabled(true);
									btnEliminar.setEnabled(true);
									estado = "0";
								} else {
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_MODIFICA_PLANIFICACION);
								}
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_MODIFICA_PLANIFICACION);
							}
							bloqueador.dispose();
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				} else {
					Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_MODIFICA_PLANIFICACION);
				}
			}
		}
	}

	public void eliminaPlanificacion() {
		if (!cmbEjVen.getSelectedItem().toString().equals("")&& !cmbClie.getSelectedItem().toString().equals("")) {
			if (tblPlanificacion.getSelectedRow() > -1) {
				if (Mensaje.preguntar(Constante.MENSAJE_PREGUNTA_ELIMINA_PLANIFICACION)) {
					if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
						final DLocker bloqueador = new DLocker();
						Thread hilo = new Thread() {
							public void run() {
								BeanEmpleado be = (BeanEmpleado) cmbEjVen.getSelectedItem();
								BeanCliente bc = (BeanCliente) cmbClie.getSelectedItem();
								try {
									objSAP = new SPlanificacion();
									DefaultTableModel dtm = (DefaultTableModel) tblPlanificacion.getModel();
									int fila = tblPlanificacion.getSelectedRow();
									objSAP.eliminaPlanificacion(modeloTabla.getValueAt(fila, 0).toString(), be.getStrIdEmpleado(), bc.getStrIdCliente());
									Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_ELIMINA_PLANIFICACION);
									dtm.removeRow(fila);
									if (modeloTabla.getRowCount() == 3) {
										dtm.insertRow(3, new Object[] { "", "", "", "", "" });
									} else {
										dtm.insertRow(planificacionese.size() - 1, new Object[] { "", "", "", "", "" });
									}
								} catch (Exception e) {
									Util.mostrarExcepcion(e);
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_ELIMINA_PLANIFICACION);
								}
								bloqueador.dispose();
							}
						};
						hilo.start();
						bloqueador.setVisible(true);
					} else {
						Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_ELIMINA_PLANIFICACION);
					}
				}
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_SELECCION_ELIMINA_PLANIFICACION);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnGuardar) {
			/* IMPLEMEMTA FUNCIONALIDAD GUARDAR */
			ingresaModificaPlanificacion();
		}
		if (arg0.getSource() == btnEliminar) {
			/* IMPLEMEMTA FUNCIONALIDAD ELIMINAR */
			eliminaPlanificacion();
		}
	}

	private void lblBotonImportExcelMouseClicked(MouseEvent evt) {
		lblBotonImportExcel.add(popupImportaExcel);
		popupImportaExcel.show(lblBotonImportExcel, -150, 0);
		popupImportaExcel.setVisible(true);
	}

	private void lblBotonImportExcelMousePressed(MouseEvent evt) {
		if (lblBotonImportExcel.isEnabled() == true) {
			lblBotonImportExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonImportExcel.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonImportExcelMouseReleased(MouseEvent evt) {
		lblBotonImportExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonImportExcel.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void lblBotonLeerExcelMouseClicked(MouseEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				pgbInsertado.setValue(0);
				insertaPlanificado();
				bloqueador.dispose();
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	/* MÉTODO QUE RETORNA EL ID FRECUENCIA SEGÚN DESCRIPCIÓN */
	public String idFrecuencia(String frecuencia) {
		String idFre = "";
		if (frecuencia.equalsIgnoreCase("7")) {
			idFre = "01";
		}
		if (frecuencia.equalsIgnoreCase("14")) {
			idFre = "02";
		}
		if (frecuencia.equalsIgnoreCase("28")) {
			idFre = "03";
		}
		return idFre;
	}

	/* MÉTODO QUE COMPLETA CEROS */
	public String completaCeros(String codigo) {
		int i = codigo.length();
		while (i < 10) {
			codigo = "0" + codigo;
			i++;
		}
		return codigo;
	}

	/* MÉTODO QUE VALIDA UN VENDEDOR */
	public boolean validaVendedor(String codigo) {
		boolean resultado = false;
		String temp = completaCeros(codigo);
		for (int i = 0; i < listaEmpleado.size(); i++) {
			if (listaEmpleado.get(i).getStrIdEmpleado().equals(temp)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}

	/* MÉTODO QUE VALIDA HORA */
	public boolean validaHora(String hora) {
		boolean resultado = false;
		if (hora.length() == 5) {
			if (hora.equals("08:00") || hora.equals("08:30") || hora.equals("09:00") || hora.equals("09:30") || hora.equals("10:00")
					|| hora.equals("10:30") || hora.equals("11:00") || hora.equals("11:30") || hora.equals("12:00")
					|| hora.equals("12:30") || hora.equals("13:00") || hora.equals("13:30") || hora.equals("14:00") 
					|| hora.equals("14:30") || hora.equals("15:00") || hora.equals("15:30") || hora.equals("16:00")
					|| hora.equals("16:30") || hora.equals("17:00") || hora.equals("17:30") || hora.equals("18:00")
					|| hora.equals("18:30"))
				resultado = true;
		}
		return resultado;
	}

	/* MÉTODO QUE VALIDA UN CLIENTE */
	public boolean validaCliente(String codigo) {
		boolean resultado = false;
		String temp = completaCeros(codigo);
		for (int i = 0; i < listaCliente.size(); i++) {
			if (listaCliente.get(i).getStrIdCliente().equals(temp)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}

	/* MÉTODO QUE VALIDA UNA FECHA */
	public boolean validaFecha(String fecha) {
		boolean resultado = false;
		String anio = "" + fecha.charAt(0) + fecha.charAt(1) + fecha.charAt(2) + fecha.charAt(3);
		String mes = "" + fecha.charAt(4) + fecha.charAt(5);
		String dia = "" + fecha.charAt(6) + fecha.charAt(7);
		if (fecha.length() == 8) {
			if (anio.equals(Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR)))) {
				if (mes.charAt(0) == '0') {
					if (mes.charAt(1) == '1' || mes.charAt(1) == '2' || mes.charAt(1) == '3' || mes.charAt(1) == '4' || mes.charAt(1) == '5' || mes.charAt(1) == '6' || mes.charAt(1) == '7' || mes.charAt(1) == '8' || mes.charAt(1) == '9') {
						if (dia.charAt(0) == '0') {
							if (dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '1') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '2') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '3') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1') {
								resultado = true;
							}
						}
					}
				}
				if (mes.charAt(0) == '1') {
					if (mes.charAt(1) == '0' || mes.charAt(1) == '1' || mes.charAt(1) == '2') {
						if (dia.charAt(0) == '0') {
							if (dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '1') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '2') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '3') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1') {
								resultado = true;
							}
						}
					}
				}
			}
		}
		return resultado;
	}

	/* MÉTODO QUE VALIDA FRECUENCIA */
	public boolean validaFrecuencia(String frecuencia) {
		boolean resultado = false;
		if (frecuencia.equals("7") || frecuencia.equals("14") || frecuencia.equals("28"))
			resultado = true;
		return resultado;
	}

	@SuppressWarnings({ "unused", "static-access" })
	public void insertaPlanificado() {
		boolean estado = false; /* VALOR INICIAL */
		LeerHojaExcel leerHojaExcel = new LeerHojaExcel();
		List<HashMap<String, String>> listaPlanificacion = null;
		HashMap<String, String> dataSet = null;
		SqlPlanificacion getPlanificacion = new SqlPlanificacionImpl();
		ThreadSocketProgressBar thrProgreso = null;
		String idPla;
		String msg;
		String diaVisita;
		int filas = 0;
		int contarFilas = 0;
		SqlVisita sqlVisita = new SqlVisitaImpl();
		BeanVisita visita = null;
		BeanPlanificacion planificacion = null;
		pgbInsertado.setValue(0);
		HashMap<Integer, String> lblColumna_Planificacion = new HashMap<Integer, String>();
		lblColumna_Planificacion.put(0, Id_Vendedor_Planificacion);
		lblColumna_Planificacion.put(1, Id_Cliente_Planificacion);
		lblColumna_Planificacion.put(2, FechaInicio_Planificacion);
		lblColumna_Planificacion.put(3, Hora_Planificacion);
		lblColumna_Planificacion.put(4, Frecuencia_Planificacion);
		leerHojaExcel.LeerHojaExcel1(txtDireccionExcel.getText(), 1, 6, lblColumna_Planificacion);
		listaPlanificacion = leerHojaExcel.getListaDataSet();
		filas = listaPlanificacion.size();
		for (HashMap<String, String> map : listaPlanificacion) {
			if (map.get(Id_Vendedor_Planificacion) != null && map.get(FechaInicio_Planificacion) != null && map.get(Hora_Planificacion) != null && map.get(Id_Cliente_Planificacion) != null && map.get(Frecuencia_Planificacion) != null) {
				if (validaVendedor(map.get(Id_Vendedor_Planificacion)) == true && validaFecha(map.get(FechaInicio_Planificacion)) == true && validaHora(map.get(Hora_Planificacion)) == true && validaCliente(map.get(Id_Cliente_Planificacion)) == true && validaFrecuencia(map.get(Frecuencia_Planificacion)) == true) {
					try {
						objSAP = new SPlanificacion();
						idPla = objSAP.ingresaPlanificacion(completaCeros(map.get(Id_Vendedor_Planificacion)), ((BeanDato) datose.get(0)).getStrCodigo(), map.get(FechaInicio_Planificacion), Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR)) + calculaMes(map.get(FechaInicio_Planificacion)) + calculaDia(map.get(FechaInicio_Planificacion)), map.get(Hora_Planificacion), completaCeros(map.get(Id_Cliente_Planificacion)), idFrecuencia(map.get(Frecuencia_Planificacion)));
						if (idPla != null) {
							for (int j = 0; j < idPla.length(); j++) {
								if (idPla.charAt(j) != '0') {
									idPla = idPla.substring(j, idPla.length());
									break;
								}
							}
							Visita objFI = new Visita();
							List<String> lstFechas = objFI .siguientesVisitas("" + map.get(FechaInicio_Planificacion).charAt(6)
													+ map.get(FechaInicio_Planificacion).charAt(7) + Constante.SEPARADOR
													+ map.get(FechaInicio_Planificacion).charAt(4)
													+ map.get(FechaInicio_Planificacion).charAt(5) + Constante.SEPARADOR
													+ map.get(FechaInicio_Planificacion).charAt(0) 
													+ map.get(FechaInicio_Planificacion).charAt(1)
													+ map.get(FechaInicio_Planificacion).charAt(2)
													+ map.get(FechaInicio_Planificacion).charAt(3), idFrecuencia(map.get(Frecuencia_Planificacion)));
							msg = objSAP.ingresaVisitaFueraDeRuta(completaCeros(map.get(Id_Vendedor_Planificacion)), completaCeros(map.get(Id_Cliente_Planificacion)), lstFechas, map.get(Hora_Planificacion), Constante.TIPO_VISITA1, idPla);
							if (msg != null) {
								contarFilas++;
								pgbInsertado.setValue((int) ((100 * contarFilas) / filas));
								pgbInsertado.repaint();
								estado = true;
							} else {
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_IMPORTA_PLANIFICACION);
								break;
							}
						} else {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_IMPORTA_PLANIFICACION);
							break;
						}
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_PLANIFICACION);
						break;
					}
				}
			}
		}
		if (estado) {
			Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_IMPORTA_PLANIFICACION);
			buscaPlanificacion();
		}
	}

	private void lblBotonLeerExcelMousePressed(MouseEvent evt) {
		if (lblBotonLeerExcel.isEnabled() == true) {
			lblBotonLeerExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonLeerExcel.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonLeerExcelMouseReleased(MouseEvent evt) {
		lblBotonLeerExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonLeerExcel.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void lblBotonSalirMouseClicked(MouseEvent evt) {
		popupImportaExcel.setVisible(false);
		pgbInsertado.setValue(0);
	}

	private void lblBotonSalirMousePressed(MouseEvent evt) {
		if (lblBotonSalir.isEnabled() == true) {
			lblBotonSalir.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonSalir.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonSalirMouseReleased(MouseEvent evt) {
		lblBotonSalir.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonSalir.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void lblBotonExaminarExcelMouseClicked(MouseEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(IPlanificacion.this);
		if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			DireccionFormato = String.valueOf(file.getAbsoluteFile());
			DireccionFormato = DireccionFormato.trim();
			txtDireccionExcel.setText(DireccionFormato);
			popupImportaExcel.setVisible(true);
		}
		pgbInsertado.setValue(0);
	}

	private void lblBotonExaminarExcelMousePressed(MouseEvent evt) {
		if (lblBotonExaminarExcel.isEnabled() == true) {
			lblBotonExaminarExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonExaminarExcel.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonExaminarExcelMouseReleased(MouseEvent evt) {
		lblBotonExaminarExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonExaminarExcel.setVerticalAlignment(SwingConstants.CENTER);
	}

	private class ThreadSocketProgressBar extends Thread {
		int progreso;

		@SuppressWarnings("unused")
		public ThreadSocketProgressBar(int progreso) {
			this.progreso = progreso;
		}

		public void run() {
			pgbInsertado.setValue(progreso);
			Rectangle progressRect = pgbInsertado.getBounds();
			progressRect.x = 0;
			progressRect.y = 0;
			pgbInsertado.paintImmediately(progressRect);
		}
	}

	private String Id_Vendedor_Planificacion = "IdVendedor";
	private String Id_Cliente_Planificacion = "IdCliente";
	private String FechaInicio_Planificacion = "FechaInicio";
	private String Hora_Planificacion = "Hora";
	private String Frecuencia_Planificacion = "Frecuencia";
}