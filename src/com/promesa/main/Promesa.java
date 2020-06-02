package com.promesa.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.promesa.bean.BeanDato;
import com.promesa.bean.BeanLogin;
import com.promesa.factory.Factory;
import com.promesa.internalFrame.Administrador;
import com.promesa.internalFrame.Supervisor;
import com.promesa.internalFrame.Vendedor;
import com.promesa.internalFrame.pedidos.IMarcaEspecifica;
import com.promesa.internalFrame.pedidos.IPedidos;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.pedidos.bean.Indicador;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.sql.impl.SqlSincronizacionImpl;
import com.promesa.planificacion.bean.BeanPlanificacion;
import com.promesa.sap.SPlanificacion;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.sincronizacion.impl.SincronizacionPedidos;
import com.promesa.util.AreaNotificacion;
import com.promesa.util.Cmd;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.LookAndFeel;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;
import com.promesa.util.Visita;

@SuppressWarnings("serial")
public class Promesa extends JFrame implements ActionListener, WindowListener {

	public static JDesktopPane destokp;
	private JDesktopPane Cabecera;
	private JLabel lblLogo;
	private JLabel lblMsg1;
	private JLabel lblUsuario;
	private JLabel lblCodigo;
	private JLabel lblMsg2;
	private JLabel lblAviso;
	private JLabel lblAviso2;
	ButtonGroup rdoGrupo;
	JRadioButton rdoOffline;
	JRadioButton rdoOnline;
	private JLabel lblMsg3;
	private JLabel lblConexion;
	private JLabel lblMsg4;
	private JLabel lblEstado;
	public static List<BeanDato> datose;
	int conexion;
	JButton btnEC;
	boolean ec = true;
	JSplitPane splitPane;
	@SuppressWarnings("rawtypes")
	static List menu;
	Cmd objC;
	List<BeanLogin> accesoe;
	private javax.swing.JPanel pnlBotones;
	private javax.swing.JPanel pnlMenu;
	List<String> listaMenu;
	boolean bandera = true;
	Timer[] temporizador;
	Date[] HoraProgramada = null;
	Date fechaHoraSistema = null;
	JPanel panel;
	List<BeanDato> conexionPromesa = null;
	BeanTareaProgramada beanTareaProgramada = null;
	private JTable tblMan;
	DefaultTableModel modeloTabla;
	JScrollPane scrMan;
	private JPopupMenu popupOpciones = null;
	private JPanel pnlPrincipalPopup = null;
	private JPanel pnlCerrarPopup = null;
	private JButton btnCerrar = null;
	boolean oa = false; /* OPCIÓN ADMINISTRADOR */
	boolean os = false; /* OPCIÓN SUPERVISOR */
	boolean ov = false; /* OPCIÓN VENDEDOR */
	List<BeanPlanificacion> planificacionese;
	private static Promesa INSTANCE = null;
	private int horaRe;
	private int minutoRe;
	private int segundoRe;
	private String tiempoConexion;
	public static int MAX_DELAY = 0;
	public JButton btnRegresarAIndicadores;
	
	public boolean ventanaPedidoActiva = false;

	public static List<String> listaSincro = new ArrayList<String>();
	
	StringBuilder mensaje;
	
	private boolean sincronizarAnticipoConbranza = false;

	public boolean isSincronizarAnticipoConbranza() {
		return sincronizarAnticipoConbranza;
	}

	public void setSincronizarAnticipoConbranza(boolean sincronizarAnticipoConbranza) {
		this.sincronizarAnticipoConbranza = sincronizarAnticipoConbranza;
	}

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public Promesa(final List<BeanDato> datose) {
		try {
			Properties props = new Properties();
			InputStreamReader in = null;
			in = new InputStreamReader(new FileInputStream("conexion.properties"), "UTF-8");
			props.load(in);
			MAX_DELAY = Integer.parseInt((props.getProperty("sap.maxdelay").trim()));
			in.close();
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			System.exit(1);
		}
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		BorderLayout thisLayout = new BorderLayout();
		panel.setLayout(thisLayout);
		panel.setPreferredSize(new java.awt.Dimension(732, 50));
		panel.setBorder(BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 255), new java.awt.Color(175, 200, 222)));
		Cabecera = new JDesktopPane();
		Cabecera.setBounds(0, 0, 50, 50);
		Cabecera.setBackground(new java.awt.Color(255, 255, 255));
		this.add(Cabecera);
		this.addWindowListener(this);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		Cabecera.setPreferredSize(new java.awt.Dimension(501, 50));
		lblLogo = new JLabel();
		Cabecera.add(lblLogo, JLayeredPane.DEFAULT_LAYER);
		lblLogo.setBounds(25, 0, 385, 50);
		lblLogo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/logo_promesa3.jpg")));
		listaMenu = new ArrayList();
		this.datose = datose;
		conexionPromesa = datose;
		beanTareaProgramada = new BeanTareaProgramada();
		beanTareaProgramada.setDatose(datose);
		beanTareaProgramada.setCodigoUsuario(((BeanDato) datose.get(0)).getStrCodigo());
		menu = ((BeanDato) datose.get(0)).getMenu();
		mensaje = new StringBuilder();
		tiempoConexion = "";
		sincronizarAnticipoConbranza = datose.get(0).isSincronizarAnticopoCobranza();
		
		{
			btnEC = new JButton();
			Cabecera.add(btnEC, JLayeredPane.DEFAULT_LAYER);
			btnEC.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/izq.png")));
			btnEC.addActionListener(this);
			btnEC.setToolTipText("Expander");
			btnEC.setBackground(new java.awt.Color(255, 255, 255));
			btnEC.setBounds(150, 23, 30, 20);
		}
		{
			lblMsg1 = new JLabel();
			Cabecera.add(lblMsg1, JLayeredPane.DEFAULT_LAYER);
			lblMsg1.setText("Usuario:");
			lblMsg1.setFont(new java.awt.Font("Arial", 1, 12));
			lblMsg1.setForeground(new java.awt.Color(0, 0, 128));
			lblMsg1.setBounds(215, 16, 120, 17);
		}
		{
			lblCodigo = new JLabel();
			Cabecera.add(lblCodigo, JLayeredPane.DEFAULT_LAYER);
			lblCodigo.setText("(" + ((BeanDato) datose.get(0)).getStrCodigo() + ")");
			lblCodigo.setFont(new java.awt.Font("Arial", 1, 11));
			lblCodigo.setForeground(new java.awt.Color(0, 0, 0));
			lblCodigo.setBounds(270, 11, 200, 17);
		}
		{
			lblUsuario = new JLabel();
			Cabecera.add(lblUsuario, JLayeredPane.DEFAULT_LAYER);
			lblUsuario.setText(((BeanDato) datose.get(0)).getStrUsuario());
			lblUsuario.setFont(new java.awt.Font("Arial", 1, 11));
			lblUsuario.setForeground(new java.awt.Color(0, 0, 0));
			lblUsuario.setBounds(270, 27, 260, 17);
		}
		{
			lblMsg2 = new JLabel();
			Cabecera.add(lblMsg2, JLayeredPane.DEFAULT_LAYER);
			lblMsg2.setText("Conexión (M):");
			lblMsg2.setBounds(460, 7, 156, 22);
			lblMsg2.setFont(new java.awt.Font("Arial", 1, 12));
			lblMsg2.setForeground(new java.awt.Color(0, 0, 128));
		}
		add(Cabecera, BorderLayout.NORTH);
		construirMenu();
		destokp = new JDesktopPane();
		destokp.setBackground(Color.white);
		JScrollPane srcScroll = new JScrollPane(destokp);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(pnlMenu);
		splitPane.setRightComponent(destokp);
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(false);
		splitPane.setBackground(new java.awt.Color(235, 239, 242));
		Dimension minimumSize = new Dimension(100, 50);
		srcScroll.setMinimumSize(minimumSize);
		srcScroll.setPreferredSize(new java.awt.Dimension(115, 217));
		splitPane.setDividerLocation(180);
		splitPane.setPreferredSize(new Dimension(500, 300));
		add(splitPane, BorderLayout.CENTER);

		rdoGrupo = new ButtonGroup();

		rdoOnline = new JRadioButton();
		rdoOnline.setBackground(Color.white);
		rdoOnline.setOpaque(true);
		rdoOnline.setText("Online");
		rdoOnline.addActionListener(this);
		rdoOnline.setBounds(543, 7, 62, 22);
		rdoGrupo.add(rdoOnline);
		rdoOnline.setFont(new java.awt.Font("Arial", 0, 12));
		rdoOnline.setForeground(new java.awt.Color(0, 0, 0));
		rdoOnline.setToolTipText("Online");

		rdoOffline = new JRadioButton();
		rdoOffline.setBackground(Color.white);
		rdoOffline.setOpaque(true);
		rdoOffline.setText("Offline");
		rdoOffline.addActionListener(this);
		rdoOffline.setBounds(606, 7, 60, 22);
		rdoGrupo.add(rdoOffline);
		rdoOffline.setFont(new java.awt.Font("Arial", 0, 12));
		rdoOffline.setForeground(new java.awt.Color(0, 0, 0));
		rdoOffline.setToolTipText("Offline");

		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			rdoOnline.setSelected(true);
			rdoOnline.setBackground(Color.green);
			rdoOffline.setBackground(Color.white);
		} else {
			rdoOffline.setSelected(true);
			rdoOnline.setBackground(Color.white);
			rdoOffline.setBackground(Color.red);
		}
		rdoGrupo.add(rdoOffline);
		rdoGrupo.add(rdoOnline);
		Cabecera.add(rdoOffline, JLayeredPane.DEFAULT_LAYER);
		Cabecera.add(rdoOnline, JLayeredPane.DEFAULT_LAYER);
		{
			lblMsg3 = new JLabel();
			Cabecera.add(lblMsg3, JLayeredPane.DEFAULT_LAYER);
			lblMsg3.setText("Conexión (T):");
			lblMsg3.setBounds(684, 7, 156, 22);
			lblMsg3.setFont(new java.awt.Font("Arial", 1, 12));
			lblMsg3.setForeground(new java.awt.Color(0, 0, 128));
		}
		{
			lblConexion = new JLabel();
			Cabecera.add(lblConexion, JLayeredPane.DEFAULT_LAYER);
			lblConexion.setFont(new java.awt.Font("Arial", 1, 12));
			lblConexion.setBounds(764, -17, 277, 71);
		}
		{
			lblAviso = new JLabel();
			lblAviso2 = new JLabel();
			Cabecera.add(lblAviso, JLayeredPane.DEFAULT_LAYER);
			Cabecera.add(lblAviso2, JLayeredPane.DEFAULT_LAYER);
			lblAviso.setBounds(450, 24, 320, 22);// 470
			lblAviso2.setBounds(700, 24, 280, 22);
			lblAviso2.setFont(new java.awt.Font("Calibri", 2, 11));
			lblAviso2.setForeground(Color.green.darker());
			lblAviso.setFont(new java.awt.Font("Calibri", 2, 11));
			lblAviso.setForeground(new java.awt.Color(255, 0, 0));
		}
		{
			lblMsg4 = new JLabel();
			Cabecera.add(lblMsg4, JLayeredPane.DEFAULT_LAYER);
			lblMsg4.setText("Conexión (E):");
			lblMsg4.setBounds(855, 7, 156, 22);
			lblMsg4.setFont(new java.awt.Font("Arial", 1, 12));
			lblMsg4.setForeground(new java.awt.Color(0, 0, 128));
		}
		{
			lblEstado = new JLabel();
			Cabecera.add(lblEstado, JLayeredPane.DEFAULT_LAYER);
			lblEstado.setFont(new java.awt.Font("Arial", 1, 12));
			lblEstado.setBounds(935, -17, 277, 71);
		}
		btnCerrar = new JButton();
		btnCerrar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/borrar.png")));
		btnCerrar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCerrarActionPerformed(evt);
			}
		});
		modeloTabla = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		scrMan = new JScrollPane();
		scrMan.setToolTipText("Listado de Sincronizaciones");
		modeloTabla.addColumn("Sincronizando");
		tblMan = new JTable(modeloTabla);
		scrMan.setViewportView(tblMan);

		pnlCerrarPopup = new JPanel();
		pnlCerrarPopup.setLayout(new BorderLayout());
		pnlCerrarPopup.add(btnCerrar, BorderLayout.EAST);

		pnlPrincipalPopup = new JPanel();
		pnlPrincipalPopup.setLayout(new BorderLayout());
		pnlPrincipalPopup.add(pnlCerrarPopup, BorderLayout.NORTH);
		pnlPrincipalPopup.add(scrMan, BorderLayout.CENTER);
		pnlPrincipalPopup.setPreferredSize(new Dimension(300, 115));

		popupOpciones = new JPopupMenu();
		popupOpciones.add(pnlPrincipalPopup);
		popupOpciones.setLocation(200, 180);

		this.setTitle("ProMovil");
		LookAndFeel.IconoVentana(this);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);

		activarTiempoConexion();

		INSTANCE = this; // No se debe cambiar esta ubicación
		final DLocker bloqueador = new DLocker();
		setIconImage(new ImageIcon(this.getClass().getResource("/imagenes/main_icon.png")).getImage());
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			Thread hilo = new Thread() {
				public void run() {
					try {
						ListaTareas();
						ventanaXdefecto();
						if (ov) {
								Util.ActualizarNumeroRegistros();
								System.out.println("actualizado los numeros de registro");
								Util.sincronizarTablasPendientes();
								
								if(isSincronizarAnticipoConbranza()){
									mensaje = Util.SincronizarCreditoAnticiposASAP(beanTareaProgramada.getCodigoUsuario());
								}
//								mensaje = Util.SincronizarCreditoAnticiposASAP(beanTareaProgramada.getCodigoUsuario());
								
						}
					} catch (Exception e) {
						bloqueador.dispose();
						e.printStackTrace();
					} finally {
						bloqueador.dispose();
						if(mensaje.length() > 0){
							Mensaje.mostrarAviso(mensaje.toString());
						}
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
	}

	public void mostrarAvisoSincronizacion(String aviso) {
		try {
			lblAviso2.setFont(new java.awt.Font("Arial", Font.BOLD, 9));
			if (aviso != null && !aviso.trim().isEmpty()) {
				lblAviso2.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/loading.gif")));
				lblAviso2.setText(aviso.toUpperCase());
			} else {
				lblAviso2.setIcon(null);
				lblAviso2.setText("");
			}
		} catch (NullPointerException e) {
		}
	}

	public String obtenerMensajeSincro() {
		return lblAviso2.getText();
	}

	public static Promesa getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("static-access")
	public void activarTiempoConexion() {
		TimerTask timerTask = new TimerTask() {
			public void run() {
				tiempoConexion = objC.disponibilidadSAP();
				setInformacionConexion(Integer.parseInt(tiempoConexion));
				int numeroIntentos = 0;
				if (Integer.parseInt(tiempoConexion) < 0 && numeroIntentos < Constante.MAX_NUMERO_INTENTOS) {
					if (((BeanDato) datose.get(0)).getStrModo().equals(
							Constante.MODO_ONLINE)) {
						if (((BeanDato) datose.get(0)).getStrRol().equals(Constante.ID_ROL_ADMINISTRADOR) || ((BeanDato) datose.get(0)).getStrRol().equals(Constante.ID_ROL_SUPERVISOR)) {
							Mensaje.mostrarAviso(Constante.MENSAJE_CONEXION_NULA);
							System.exit(0);
						} else {
							Mensaje.mostrarAviso(Constante.MENSAJE_CONEXION_NULA_VENDEDOR);
							cambioOffline();
						}
					}
					numeroIntentos++;
				}
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 20000);
	}

	/* MÉTODO DE PASO A MODO OFFLINE */
	public void cambioOffline() {
		List<BeanDato> temporal = new ArrayList<BeanDato>();
		BeanDato t = new BeanDato();
		t.setStrModo(Constante.MODO_OFFLINE);
		t.setStrConexion("0");
		t.setStrUsuario(((BeanDato) datose.get(0)).getStrUsuario());
		t.setStrCodigo(((BeanDato) datose.get(0)).getStrCodigo());
		t.setStrNU(((BeanDato) datose.get(0)).getStrNU());
		t.setStrPU(((BeanDato) datose.get(0)).getStrPU());
		t.setMenu(((BeanDato) datose.get(0)).getMenu());
		t.setStrRol(((BeanDato) datose.get(0)).getStrRol());
		temporal.add(t);
		datose = temporal;
		beanTareaProgramada.setDatose(datose);
		rdoOffline.setSelected(true);
		rdoOnline.setBackground(Color.white);
		rdoOffline.setBackground(Color.red);
	}

	/* MÉTODO QUE PERMITE LA CARGA DE UNA VENTANA (POR DEFECTO) */
	public void ventanaXdefecto() {
		Administrador a = new Administrador();
		Supervisor s = new Supervisor();
		Vendedor v = new Vendedor();
		beanTareaProgramada.setPopupOpciones(popupOpciones);
		if (oa) {
			try {
				a.muestraVentanaAdministrador(1, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}
		if (os) {
			try {
				s.muestraVentanaSupervisor(1, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}
		if (ov) {
			try {
				if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							actualizaVisitas();
							bloqueador.setVisible(false);
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}
				v.muestraVentanaVendedor(2, destokp, beanTareaProgramada);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
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
			return e.getMessage();
		}
	}

	/* MÉTODO QUE OBTIENE AÑO */
	public String calculaAnio() {
		Calendar c = new GregorianCalendar();
		return Integer.toString(c.get(Calendar.YEAR));
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

	/* MÉTODO QUE ACTUALIZA VISITAS (PLANIFICADAS) */
	@SuppressWarnings("static-access")
	public void actualizaVisitas() {
		SPlanificacion objSAP;
		try {
			objSAP = new SPlanificacion();
			planificacionese = objSAP.actualizaVisitas(((BeanDato) datose.get(0)).getStrCodigo());
			if (planificacionese != null) {
				for (int i = 0; i < planificacionese.size(); i++) {
					if (((BeanPlanificacion) planificacionese.get(i)).getStrEstado().equals("0")&& ((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio() != null
							&& !((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio().equals("null")) {
						Visita objFI = new Visita();
						List<String> lstFechas = objFI.siguientesVisitas(convierteFecha(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio()),
															((BeanPlanificacion) planificacionese.get(i)).getStrIdFrecuencia());
						for (int j = 0; j < lstFechas.size(); j++) {
							if (Integer.parseInt(lstFechas.get(j).charAt(3) + "" + lstFechas.get(j).charAt(4)) > Integer
									.parseInt(convierteFecha(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio()).charAt(3)
											+ "" + convierteFecha(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio()).charAt(4))) {
								List<String> temporal1 = objFI.siguientesVisitas(lstFechas.get(j), ((BeanPlanificacion) planificacionese.get(i)).getStrIdFrecuencia());
								for (int k = 0; k < temporal1.size(); k++) {
									if (Integer.parseInt(temporal1.get(k).charAt(3)+ ""+ temporal1.get(k).charAt(4)) > Integer
											.parseInt(lstFechas.get(j).charAt(3)+ "" + lstFechas.get(j) .charAt(4))) {
										List<String> temporal2 = objFI.siguientesVisitas(temporal1.get(k), ((BeanPlanificacion) planificacionese
																.get(i)).getStrIdFrecuencia());
										objSAP.ingresaVisitaFueraDeRuta(((BeanDato) datose.get(0)).getStrCodigo(),
												((BeanPlanificacion) planificacionese.get(i)).getStrIdCliente(), temporal2,
												((BeanPlanificacion) planificacionese.get(i)).getStrHora(), Constante.TIPO_VISITA1,
												((BeanPlanificacion) planificacionese.get(i)).getStrIdPlan());
										break;
									}
								}
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	public void setInformacionConexion(int tiempo) {
		if (tiempo < 0) {
			lblConexion.setText("Sin conexión");
			lblConexion.setForeground(new java.awt.Color(255, 0, 0));
			lblEstado.setText("Sin conexión");
			lblEstado.setForeground(new java.awt.Color(255, 0, 0));
		} else if (tiempo >= 0 && tiempo < Promesa.MAX_DELAY) {
			lblConexion.setText(tiempo + " ms");
			lblConexion.setForeground(new java.awt.Color(0, 0, 0));
			lblEstado.setText("BUENO");
			lblEstado.setForeground(new java.awt.Color(0, 0, 0));
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				int i = 0;
				while (i < 120000000) {
					lblAviso.setText(Constante.MENSAJE_CONEXION_BUENA);
					i++;
				}
			}
		} else if (tiempo == Promesa.MAX_DELAY) {
			lblConexion.setText(tiempo + " ms");
			lblConexion.setForeground(new java.awt.Color(0, 0, 0));
			lblEstado.setText(" REGULAR");
			lblEstado.setForeground(new java.awt.Color(0, 0, 0));
		} else if (tiempo > Promesa.MAX_DELAY) {
			lblConexion.setText(tiempo + " ms");
			lblConexion.setForeground(new java.awt.Color(255, 0, 0));
			lblEstado.setText(" BAJO");
			lblEstado.setForeground(new java.awt.Color(255, 0, 0));
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
				int i = 0;
				while (i < 120000000) {
					lblAviso.setText(Constante.MENSAJE_CONEXION_BAJA);
					i++;
				}
			}
		}
		lblAviso.setText("");
		tiempoConexion = String.valueOf(tiempo);
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public void sincronizaTablasRestantesPorVendedor(boolean debeSincronizar) throws Exception {
		if (debeSincronizar) {
			com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlSincronizacionImpl = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
			com.promesa.administracion.bean.BeanSincronizar beanSincronizar = new com.promesa.administracion.bean.BeanSincronizar();
			int cantidadRegistros = 0;
			String estado = "";
			Date fechaInicio = new Date();
			Calendar inicioCalendario = Calendar.getInstance();
			long inicio = inicioCalendario.getTimeInMillis();
			fechaInicio.setMonth(fechaInicio.getMonth() - fechaInicio.getMonth());
			fechaInicio.setDate(1);
			/*BeanBuscarPedido param = new BeanBuscarPedido();
			param.setStrVendorId(beanTareaProgramada.getCodigoUsuario());
			param.setStrDocType("ZP01");
			param.setStrFechaInicio(fechaInicio);
			param.setStrFechaFin(new Date());
			estado = SincronizacionPedidos.sincronizarTablaAgenda(param);
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_AGENDA");*/

			Calendar finCalendario = Calendar.getInstance();
			long fin = finCalendario.getTimeInMillis();
			String strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
			beanSincronizar.setStrIdeSinc("3");
			beanSincronizar.setStrTie(strTie);
			beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
			beanSincronizar.setStrCantReg(cantidadRegistros + "");
			SqlSincronizacionImpl sqlSincronizacion = new SqlSincronizacionImpl();
			sqlSincronizacion.actualizarSincronizar(beanSincronizar);

			inicioCalendario = Calendar.getInstance();
			inicio = inicioCalendario.getTimeInMillis();
			estado = SincronizacionPedidos.sincronizarTablaEmpleado(Promesa.datose.get(0).getStrCodigo());
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLIENTE");
			finCalendario = Calendar.getInstance();
			fin = finCalendario.getTimeInMillis();
			strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
			beanSincronizar.setStrIdeSinc("6");
			beanSincronizar.setStrTie(strTie);
			beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
			beanSincronizar.setStrCantReg(cantidadRegistros + "");
			sqlSincronizacion.actualizarSincronizar(beanSincronizar);
			inicioCalendario = Calendar.getInstance();
			inicio = inicioCalendario.getTimeInMillis();
			estado = SincronizacionPedidos.sincronizarTablaDestinatario();
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SEDE");
			finCalendario = Calendar.getInstance();
			fin = finCalendario.getTimeInMillis();
			strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
			beanSincronizar.setStrIdeSinc("10");
			beanSincronizar.setStrTie(strTie);
			beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
			beanSincronizar.setStrCantReg(cantidadRegistros + "");
			sqlSincronizacion.actualizarSincronizar(beanSincronizar);
		}
	}

	public void ListaTareas() throws Exception {
		BeanDato usuario = ((BeanDato) datose.get(0));
		String rol = usuario.getStrRol();
		if (rol.compareTo(Constante.ID_ROL_VENDEDOR) == 0 || rol.compareTo(Constante.ID_ROL_VENDEDOR_CAC) == 0) {
			ov = true;
		} else if(rol.compareTo(Constante.ID_ROL_ADMINISTRADOR) == 0) {
			oa = true;
		} else if(rol.compareTo(Constante.ID_ROL_SUPERVISOR) == 0) {
			os = true;
		}
	}

	public void activarTareas(String opciones) throws Exception {
	}

	@SuppressWarnings("deprecation")
	public Date getHorasMasFrecuencia(Date date, int f) {
		date.setHours(date.getHours() + f);
		return date;
	}

	@SuppressWarnings("deprecation")
	public int getSegundos(Date date) {
		return ((date.getHours() * 3600) + (date.getMinutes() * 60) + date.getSeconds());
	}

	@SuppressWarnings("deprecation")
	public Date getHorasMenosFrecuencia(Date date, int f) {
		date.setHours(date.getHours() - f);
		return date;
	}

	@SuppressWarnings("deprecation")
	public void getCapturarHorasMinutos(Date dateBD, Date dateSi) {
		int horaBD = 0, horaSi = 0;
		int minutoBD = 0, minutoSi = 0;
		horaBD = dateBD.getHours();
		horaSi = dateSi.getHours();
		horaRe = horaSi - horaBD;
		minutoBD = dateBD.getMinutes();
		minutoSi = dateSi.getMinutes();
		segundoRe = dateSi.getSeconds();
		minutoRe = minutoSi - minutoBD;
	}

	@SuppressWarnings("deprecation")
	public Date getActualizaHorasMinutos(Date dateIn) {
		dateIn.setHours(dateIn.getHours() + horaRe);
		dateIn.setMinutes(dateIn.getMinutes() + minutoRe);
		dateIn.setSeconds(dateIn.getSeconds() + segundoRe);
		return dateIn;
	}

	@SuppressWarnings("deprecation")
	public int getDia(Date date) {
		return date.getDay();
	}
	
	/*
	 * 	BOTONES DE MENUS
	 */
	private void construirMenu() {
		pnlMenu = new javax.swing.JPanel();
		pnlMenu.setLayout(new BorderLayout(0, 0));
		pnlBotones = new javax.swing.JPanel();
		pnlMenu.setMaximumSize(new java.awt.Dimension(180, 32767));
		pnlMenu.setMinimumSize(new java.awt.Dimension(180, 69));
		pnlMenu.setPreferredSize(new java.awt.Dimension(180, 278));
		pnlBotones.setLayout(new java.awt.GridLayout(0, 1));
		btnRegresarAIndicadores = new JButton("Ir a Indicadores");
		pnlMenu.add(pnlBotones, BorderLayout.NORTH);
		
		if(!ventanaPedidoActiva){
			btnRegresarAIndicadores.setEnabled(false);
		}
		
		pnlMenu.add(btnRegresarAIndicadores, BorderLayout.SOUTH);
		pnlMenu.setBackground(new java.awt.Color(235, 239, 242));
		if (menu != null) {
			for (int i = 0; i < menu.size(); i++) {
				String cadena = String.valueOf(menu.get(i));
				String[] m = cadena.split("¬");
				final JButton opcion = new JButton(m[2]);
				/*
				 * 	Marcelo Moyano 05/04/2013
				 * 	Alineación de Texto de los botones
				 */
				opcion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
				if(m.length == 5){
					if(m[4].equals("X"))
						listaMenu.add(m[2]);// Botones
				}
				opcion.setFocusable(false);
				opcion.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						escucharEvento(opcion.getText());
					}
				});
				pnlBotones.add(opcion);
			}
		}
		btnRegresarAIndicadores.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Promesa.getInstance().configSplitPane(IMarcaEspecifica.getIinstance());
				cargarMarcaEstrategica();
				//cargarIndicadores();
			}
		});
		pnlBotones.setPreferredSize(new java.awt.Dimension(178, 24 * (menu.size())));
	}

	private void escucharEvento(String comando) {
		Administrador a = new Administrador();
		Supervisor s = new Supervisor();
		Vendedor v = new Vendedor();
		beanTareaProgramada.setPopupOpciones(popupOpciones);
		if (comando.trim().contains(Constante.OPCION_PANEL_CONTROL)) {
			try {
				v.muestraVentanaVendedor(2, destokp, beanTareaProgramada);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_CONSULTA_PEDIDOS)) {
			try {
				v.muestraVentanaVendedor(3, destokp, beanTareaProgramada);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_CLIENTES)) {
			try {
				v.muestraVentanaVendedor(4, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_LISTA_PRECIOS)) {
			try {
				v.muestraVentanaVendedor(5, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_DEVOLUCIONES)) {
			try {
				v.muestraVentanaVendedor(8, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_VISITA_FUERA_RUTA)) {
			try {
				v.muestraVentanaVendedor(1, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_SINC_PED)) {
			try {
				v.muestraVentanaVendedor(7, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_ADMINISTRACION)) {
			try {
				a.muestraVentanaAdministrador(1, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_SINC_ADM)) {
			try {
				a.muestraVentanaAdministrador(8, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_REPORTE_CONTROL)) {
			try {
				s.muestraVentanaSupervisor(3, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_PARAMETROS_PLANIFICACION)) {
			try {
				s.muestraVentanaSupervisor(1, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_SINC_PLA)) {
			try {
				s.muestraVentanaSupervisor(2, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().toUpperCase().contains("COBRANZA OFFLINE")) {
			try {
				v.muestraVentanaVendedor(9, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().toUpperCase().contains("ANTICIPO OFFLINE")) {
			try {
				v.muestraVentanaVendedor(10, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().toUpperCase().contains("CONSULTA INDICADORES VEND")) {
			try {
				v.muestraVentanaVendedor(11, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().toUpperCase().contains("CONSULTA INDICADORES SUPERV")) {
			try {
				s.muestraVentanaSupervisor(4, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().toUpperCase().contains("CONSULTA CAMBIO PRECIOS")) {
			try {
				v.muestraVentanaVendedor(13, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		} else if (comando.trim().contains(Constante.OPCION_SALIR)) {
			if (Mensaje.preguntar(Constante.PREGUNTA_SALIR))
				System.exit(0);
		}else if (comando.trim().contains(Constante.OPCION_FACTURAS)){
			try {
				v.muestraVentanaVendedor(14, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}else if (comando.trim().contains(Constante.OPCION_FERIAS)){
			try {
				v.muestraVentanaVendedor(15, destokp, beanTareaProgramada);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}
	}

	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Proffline");
		LookAndFeel.IconoVentana(frame);
		AreaNotificacion.Notificar();
		frame.add(new Promesa(datose));
		frame.pack();
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public void actionPerformed(ActionEvent ae) {
		Administrador a = new Administrador();
		if (ae.getSource() == btnEC) {
			if (ec) {
				splitPane.setDividerLocation(0);
				ec = false;
				btnEC.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/der.png")));
				btnEC.setToolTipText("Contraer");
			} else {
				splitPane.setDividerLocation(180);
				ec = true;
				btnEC.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/izq.png")));
				btnEC.setToolTipText("Expander");
			}
		}
		if (ae.getSource() == rdoOffline) {
			if (((BeanDato) datose.get(0)).getStrRol().equals(Constante.ID_ROL_ADMINISTRADOR)) {
				Mensaje.mostrarAviso(Constante.MENSAJE_ACCESO_ROL_DENEGADO_ADMINISTRADOR2);
				rdoOnline.setSelected(true);
				rdoOffline.setFocusable(false);
			} else if (((BeanDato) datose.get(0)).getStrRol().equals(Constante.ID_ROL_SUPERVISOR)) {
				Mensaje.mostrarError(Constante.MENSAJE_ACCESO_ROL_DENEGADO_SUPERVISOR2);
				rdoOnline.setSelected(true);
				rdoOffline.setFocusable(false);
			} else {
				cambioOffline();
			}
		}
		if (ae.getSource() == rdoOnline) {
			int valorConexion = Integer.parseInt(objC.disponibilidadSAP());
			if (valorConexion > 0) {
				try {
					datose = conexionPromesa;
					if (!datose.isEmpty()) {
						datose.get(0).setStrModo(Constante.MODO_ONLINE);
					}
					beanTareaProgramada.setDatose(datose);
					rdoOnline.setBackground(Color.green);
					rdoOffline.setBackground(Color.white);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			} else {
				Mensaje.mostrarAviso("No puede pasar a modo ONLINE ya que no existe conexión con SAP.");
				rdoOffline.setSelected(true);
			}
		}
	}

	private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
		popupOpciones.setVisible(false);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int tipo = JOptionPane.showConfirmDialog(this, "¿Desea salir de PROMOVIL?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (tipo == JOptionPane.OK_OPTION) {
			this.dispose();
			System.exit(0);
		}
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
	
	
	public void configSplitPane(JPanel comp){
		splitPane.setLeftComponent(comp);
	}
	
	public void regresarAMenu(){
		if(ventanaPedidoActiva){
			btnRegresarAIndicadores.setEnabled(true);
		} else {
			btnRegresarAIndicadores.setEnabled(false);
		}
		splitPane.setLeftComponent(pnlMenu);
	}
	
	public boolean esVentanaActiva(){
		return ventanaPedidoActiva;
	}
	
	public void setVentanaActiva(boolean flag){
		this.ventanaPedidoActiva = flag;
	}
	
	private void cargarMarcaEstrategica(){
		List<MarcaEstrategica> marcas = Factory.createSqlMarcaEstrategica().getMarcaEstrategicaByCliente(Util.completarCeros(10, IPedidos.getInstance().getCliente()));
		IMarcaEspecifica.getIinstance().clearCamposMarcaEstrategica();
		if(marcas != null && marcas.size() > 0){
			IMarcaEspecifica.getIinstance().addMarcaEstrategica(marcas);
		}
	}
	
	/*private void cargarIndicadores(){
		Indicador i = Factory.createSqlIndicador().getIndicacorByCliente(Util.completarCeros(10, IPedidos.getInstance().getCliente()));
		IMarcaEspecifica.getIinstance().clearCamposIndicadores();
		if(i != null) {	
			IMarcaEspecifica.getIinstance().setIndicador(i);
			IMarcaEspecifica.getIinstance().setDataIndicadores();
			IMarcaEspecifica.getIinstance().actualizarCampoRealIndicador();
		}
	}*/
}
