package com.promesa.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.itextpdf.text.Font;
import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.sql.impl.*;
import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.util.*;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.pedidos.sql.impl.SqlSincronizacionImpl;
import com.promesa.sap.*;
import com.promesa.sincronizacion.impl.SincronizacionPedidos;
import com.promesa.frame.*;

public class Index extends JFrame implements ActionListener, /* WindowListener, */
KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBase;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JLabel lblClave;
	private JPasswordField txtClave;
	private JButton btnCancelar;
	private JLabel lblLogo;
	private JButton btnIniciar;
	SqlUsuarioImpl objUsu;
	Cmd objC;
	SAdministracion objSAP;
	static String acceso;
	List<BeanDato> datose;
	List<BeanLogin> accesoe;
	BeanDato objD;
	Promesa objP = null;
	private static Index INSTANCE = null;
	private static int MAX_DELAY = 0;
	StringBuilder mensaje;
	private boolean sincronizarAnticipoCobranza;

	public Index() {
		try {
			Properties props = new Properties();
			InputStreamReader in = null;
			in = new InputStreamReader(new FileInputStream("conexion.properties"), "UTF-8");
			props.load(in);
			MAX_DELAY = Integer.parseInt((props.getProperty("sap.maxdelay").trim()));
			in.close();
			mensaje = new StringBuilder();
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			System.exit(1);
		}
		this.setTitle("ProMovil");
		this.setSize(400, 407);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("imagenes/icono.jpg").getImage());
		getContentPane().setBackground(new java.awt.Color(255, 255, 255));
		pnlBase = new JPanel();
		pnlBase.setLayout(null);
		getContentPane().add(pnlBase, BorderLayout.CENTER);
		pnlBase.setBackground(new java.awt.Color(255, 255, 255));
		pnlBase.setPreferredSize(new java.awt.Dimension(399, 322));
		UIManager.put("DesktopIcon.width", 300);
		sincronizarAnticipoCobranza = false;
		{
			btnIniciar = new JButton();
			pnlBase.add(btnIniciar);
			btnIniciar.setBounds(72, 290, 91, 21);
			btnIniciar.setBorderPainted(false);
			btnIniciar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/btnAceptar.gif")));
			btnIniciar.addActionListener(this);
			btnIniciar.setCursor(new Cursor(TipoCursor.Hand()));
			btnIniciar.setToolTipText("Ingresar");
			btnIniciar.setContentAreaFilled(false);
			btnIniciar.addKeyListener(new MakeEnterDoAction());
		}
		{
			btnCancelar = new JButton();
			pnlBase.add(btnCancelar);
			btnCancelar.setBounds(222, 290, 91, 21);
			btnCancelar.setBorderPainted(false);
			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/btnCancelar.gif")));
			btnCancelar.addActionListener(this);
			btnCancelar.setCursor(new Cursor(TipoCursor.Hand()));
			btnCancelar.setToolTipText("Salir");
			btnCancelar.setContentAreaFilled(false);
		}
		{
			lblUsuario = new JLabel();
			pnlBase.add(lblUsuario);
			lblUsuario.setBounds(47, 166, 112, 21);
			lblUsuario.setText("USUARIO:");
			lblUsuario.setFont(new java.awt.Font("Arial", 1, 10));
			lblUsuario.setForeground(new java.awt.Color(0, 0, 128));
			lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			txtUsuario = new JTextField();
			pnlBase.add(txtUsuario);
			txtUsuario.setBounds(139, 166, 196, 21);
			txtUsuario.setForeground(new java.awt.Color(0, 0, 0));
			txtUsuario.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			txtUsuario.setFont(new java.awt.Font("Arial", 0, 10));
			txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
			txtUsuario.requestFocus();
		}
		{
			lblClave = new JLabel();
			pnlBase.add(lblClave);
			lblClave.setBounds(47, 227, 112, 21);
			lblClave.setText("CLAVE:");
			lblClave.setFont(new java.awt.Font("Arial", 1, 10));
			lblClave.setForeground(new java.awt.Color(0, 0, 128));
			lblClave.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			txtClave = new JPasswordField();
			pnlBase.add(txtClave);
			txtClave.setBounds(139, 227, 196, 21);
			txtClave.addActionListener(this);
			txtClave.setForeground(new java.awt.Color(0, 0, 0));
			txtClave.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			txtClave.setHorizontalAlignment(SwingConstants.CENTER);
			txtClave.setFont(new java.awt.Font("Arial", 0, 10));
			txtClave.setBackground(new java.awt.Color(255, 255, 255));
			txtClave.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						acceder();
					}
				}
			});
		}
		{
			lblLogo = new JLabel();
			pnlBase.add(lblLogo);
			lblLogo.setBounds(28, 21, 290, 95);// 150
			String logoPromovil = "/imagenes/logo_promovil.jpg";
			URL ulrLogo = this.getClass().getResource(logoPromovil);
			lblLogo.setIcon(new ImageIcon(ulrLogo));
			lblLogo.setForeground(Color.blue.darker().darker());
			lblLogo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16)); // NOI18N
			lblLogo.setText("RC 3.0");
			lblLogo.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			lblLogo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		}
		INSTANCE = this;
		setIconImage(new ImageIcon(this.getClass().getResource("/imagenes/main_icon.png")).getImage());
	}

	public static Index getInstance() {
		createInstance();
		return INSTANCE;
	}

	// IMPEDIR QUE LA CLASE SE PUEDA CLONAR
	// El método "clone" es sobreescrito por el siguiente que arroja una
	// excepción:
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private static void createInstance() {
		if (INSTANCE == null) {
			synchronized (Promesa.class) {
				if (INSTANCE == null) {
					INSTANCE = new Index();
				}
			}
		}
	}

	public class MakeEnterDoAction extends KeyAdapter {
		public void keyPressed(KeyEvent ke) {
			if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
				Object src = ke.getSource();
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new ActionEvent(src, ActionEvent.ACTION_PERFORMED, "Enter"));
			}
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Index i = new Index();
		i.setLocationRelativeTo(null);
		i.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		i.setVisible(true);
	}

	@SuppressWarnings({ "deprecation", "static-access", "rawtypes" })
	public void acceder() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			@SuppressWarnings("unused")
			public void run() {
				String strUsuario, strClave;
				strUsuario = txtUsuario.getText().toUpperCase().trim();
				strClave = txtClave.getText().trim();
				if (strUsuario.equals(Constante.VACIO) && strClave.equals(Constante.VACIO)) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_CLAVE);
					txtUsuario.requestFocus();
				} else if (!(strUsuario.equals(Constante.VACIO)) && strClave.equals(Constante.VACIO)) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_CLAVE);
					txtClave.requestFocus();
				} else if (strUsuario.equals(Constante.VACIO) && !(strClave.equals(Constante.VACIO))) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO);
					txtUsuario.requestFocus();
				} else if (!(strUsuario.equals(Constante.VACIO)) && !(strClave.equals(Constante.VACIO))) {
					String valor;
					int conexion;
					objD = new BeanDato();//clase donde se guarda todo lo relacionado con la conexion y el usuario
					datose = new ArrayList<BeanDato>();
					valor = objC.disponibilidadSAP();//devuelve un long convertido en string acerca del tiempo de conexion; si es menor a 0 no hay conexion
					objD.setStrConexion(valor);//setea el tiempo de conexion
					conexion = Integer.parseInt(valor);
					Util util = new Util();
					/*********metodo para obtener la fecha actual en tipo Date***********/
					Date fechaHoraSistema = new Date();
					String tempFechaHoraSistema = null;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
					String fecha = null;
					try {
						tempFechaHoraSistema = dateFormat.format(fechaHoraSistema);
						fechaHoraSistema = new Date(dateFormat.parse(tempFechaHoraSistema).getTime());
					} catch (Exception e) {
						Mensaje.mostrarError("e");
					}
					/*******************************************************************/
					try {
						objUsu = new SqlUsuarioImpl();//clase donde se define las llamadas y seteos a la bd entorno al usuario
						if (conexion < 0) { // MODO OFFLINE
							List<BeanUsuarioRol> usuariorol = objUsu.accesoUsuario(strUsuario, strClave);//carga desde la base de datos local el usuario
							objD.setStrModo(Constante.MODO_OFFLINE);
							if (usuariorol != null && usuariorol.size() > 0) {//consulta si devolvio un resultado
								if (((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrUsuarioBloqueado().equals("1")) {//consulta si esta bloqueado
									Mensaje.mostrarError(Constante.MENSAJE_USUARIO_BLOQUEADO);
									txtUsuario.setText("");
									txtClave.setText("");
									txtUsuario.requestFocus();
								} else {//sino esta bloqueado
									String rol = ((BeanUsuarioRol) usuariorol.get(0)).getRol().getStrIdRol();
									if (rol.equals(Constante.ID_ROL_ADMINISTRADOR)) {//Administradores no tienen acceso
										Mensaje.mostrarError(Constante.MENSAJE_ACCESO_ROL_DENEGADO_ADMINISTRADOR);
									} else if (rol.equals(Constante.ID_ROL_SUPERVISOR)) {//Supervisores no tienen acceso
										Mensaje.mostrarError(Constante.MENSAJE_ACCESO_ROL_DENEGADO_SUPERVISOR);
									} else {
										objD.setStrNU(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrNomUsuario());
										objD.setStrPU(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrClaUsuario());
										objD.setStrCodigo(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrIdentificacion());
										objD.setStrUsuario(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrNomApe());
										List menu = objUsu.getVistasPorUsuarioRol(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrNomUsuario(), ((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrClaUsuario());
										if (menu != null) {
											objD.setMenu(menu);
										}
										objD.setStrRol(((BeanUsuarioRol) usuariorol.get(0)).getRol().getStrIdRol());
										datose.add(objD);
										Mensaje.mostrarAviso(Constante.ACCESO_OFFLINE);
										dispose();
										objP = new Promesa(datose);
									}
								}
							} else { // EL USUARIO NO AUTENTICA
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_ACCESO);
								txtUsuario.setText("");
								txtClave.setText("");
								txtUsuario.requestFocus();
							}
						} else if (conexion >= 0 && conexion < MAX_DELAY) { //ONLINE
							boolean debeSincronizarTablasRestantes = false;
							try {
								objD.setStrModo(Constante.MODO_ONLINE);
								objSAP = new SAdministracion();//clase que se relaciona con la conexion a sap
								accesoe = objSAP.accesoUsuario(strUsuario, strClave, "", "", "", Constante.VACIO);
								if (((BeanLogin) accesoe.get(0)).getStrEstado().equals("0")) {//pregunta si el dispositivo esta bloqueado
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_SERIE_INACTIVA);
									txtUsuario.setText("");
									txtClave.setText("");
									txtUsuario.requestFocus();
								} else {
									if (((BeanLogin) accesoe.get(0)).getStrAcceso().equals("X")) {
										objD.setStrUsuario(((BeanLogin) accesoe.get(0)).getStrUsuario());
										objD.setStrCodigo(((BeanLogin) accesoe.get(0)).getStrCodigo());
										objD.setStrNU(strUsuario);
										objD.setStrPU(strClave);
										objD.setMenu(((BeanLogin) accesoe.get(0)).getMenu());
										objD.setStrRol(((BeanLogin) accesoe.get(0)).getStrRol());
										datose.add(objD);
										Mensaje.mostrarAviso(Constante.ACCESO_ONLINE);
										SqlUsuario sqlUsuario = new SqlUsuarioImpl();
										List<BeanUsuario> listaUsuario = null;
										BeanDato usuario = ((BeanDato) datose.get(0));
										if (usuario.getStrRol().compareTo(Constante.ID_ROL_VENDEDOR) == 0 || usuario.getStrRol().compareTo(Constante.ID_ROL_VENDEDOR_CAC) == 0) {
											// SINOCRNIZAR
											Calendar incioCalendario = Calendar.getInstance();
											long inicio = incioCalendario.getTimeInMillis();
											Util.limpiarBasesDeDatosMalDescargadas();
											sincronizaAgenda();
											if (!Util.comparaIgualdadFechaHoyYFechaArchivo(Util.convierteFechaAFormatoDDMMYYYY(new Date()), Util.leerFechaArchivo())) {
												bloqueador.mostrarNuevoMensaje("Sincronizando base. Esto tomará varios minutos.");
												String strCodigoVendedor ;
												strCodigoVendedor = objD.getStrCodigo();
												
												if (Util.validoDescargaCorrecta(strCodigoVendedor)) { // Sincronizamos
													Util.escribirFechaAArchivo(Util.convierteFechaAFormatoDDMMYYYY(new Date()));
													// -----------------------------------
													sincronizarAnticipoCobranza = true;
													objD.setSincronizarAnticopoCobranza(sincronizarAnticipoCobranza);
													if(Util.isFlag()) {
														bloqueador.mostrarNuevoMensaje("Sincronizanco Base Mestra.");
													} else {
														bloqueador.mostrarNuevoMensaje("Sincronizando Base Vendedor.");
													}
													Calendar finCalendario = Calendar.getInstance();
													long fin = finCalendario.getTimeInMillis();
													fin = fin - inicio;
													
													// ----- Importante no comentar ----------
													for (int i = 1; i <= 34; i++) {
														if (i != 3 && i != 12 && i!=29 && i!=30 && i!=31 && i!=32) {
														//if (i != 12) {
															activaSincronizacion(i, fin);
														}
													}
													// ---------------------------------------
													Util.CargarConsultaDinamica();
												} else {
													Mensaje.mostrarWarning("El servidor de sincronización parece no responder correctamente.");
												}
											}//End if del dia de sincronizacion
										}
										dispose();
										objP = new Promesa(datose);
										if (usuario.getStrModo().equals(Constante.MODO_ONLINE)) {
											BeanUsuario usr = new BeanUsuario();
											usr.setStrNomUsuario(txtUsuario.getText().trim().toUpperCase());
											sqlUsuario.setListaUsuario(usr);
											listaUsuario = sqlUsuario.getListaUsuario();
											if (listaUsuario != null) {
												for (BeanUsuario s : listaUsuario) {
													try {
														fecha = s.getStrFecUltAccSis();
														util.isActualizaData(fechaHoraSistema, fecha);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											}
											// LISTAR ROLES
										}
									} else {
										if (((BeanLogin) accesoe.get(0)).getStrCambioClave().equals("X")) {
											objD.setStrUsuario(strUsuario);
											datose.add(objD);
											CambioClave objCC = new CambioClave(strUsuario, strClave, datose);
											objCC.setLocationRelativeTo(null);
											objCC.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
											objCC.setVisible(true);
											dispose();
										} else {
											List msg = ((BeanLogin) accesoe.get(0)).getMsg();
											if (msg.size() > 1) {
												Mensaje.mostrarError(msg.get(0).toString() + "\n" + msg.get(1).toString());
												txtUsuario.setText("");
												txtClave.setText("");
												txtUsuario.requestFocus();
											}
											if (msg.get(0).toString().equals(Constante.MENSAJE_USUARIO_BLOQUEADO)) {
												Mensaje.mostrarError(msg.get(0).toString());
												txtUsuario.setText("");
												txtClave.setText("");
												txtUsuario.requestFocus();
											}
											if (msg.get(0).toString().equals(Constante.MENSAJE_CLAVE_INCORRECTA)) {
												Mensaje.mostrarError(msg.get(0).toString());
												txtClave.setText("");
												txtClave.requestFocus();
											}
											if (msg.get(0).toString().equals(Constante.MENSAJE_USUARIO_INEXISTENTE)) {
												Mensaje.mostrarError(msg.get(0).toString());
												txtUsuario.setText("");
												txtClave.setText("");
												txtUsuario.requestFocus();
											}
											if (msg.get(0).toString().equals(Constante.MENSAJE_USUARIO_SIN_ROL)) {
												Mensaje.mostrarError(msg.get(0).toString());
												txtUsuario.setText("");
												txtClave.setText("");
												txtUsuario.requestFocus();
											}
										}
									}
								}
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
							}
						}else if (conexion >= MAX_DELAY) {
							List<BeanUsuarioRol> usuariorol = objUsu.accesoUsuario(strUsuario, strClave);
							objD.setStrModo(Constante.MODO_OFFLINE);
							if (usuariorol != null && usuariorol.size() > 0) {
								if (((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrUsuarioBloqueado().equals("1")) {
									Mensaje.mostrarError(Constante.MENSAJE_USUARIO_BLOQUEADO);
									txtUsuario.setText("");
									txtClave.setText("");
									txtUsuario.requestFocus();
								} else {
									if (((BeanUsuarioRol) usuariorol.get(0)).getRol().getStrIdRol().equals(Constante.ID_ROL_ADMINISTRADOR)) {
										Mensaje.mostrarError(Constante.MENSAJE_ACCESO_ROL_DENEGADO_ADMINISTRADOR);
									} else if (((BeanUsuarioRol) usuariorol.get(0)).getRol().getStrIdRol().equals(Constante.ID_ROL_SUPERVISOR)) {
										Mensaje.mostrarError(Constante.MENSAJE_ACCESO_ROL_DENEGADO_SUPERVISOR);
									} else {
										objD.setStrNU(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrNomUsuario());
										objD.setStrPU(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrClaUsuario());
										objD.setStrCodigo(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrIdentificacion());
										objD.setStrUsuario(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrNomApe());
										List menu = objUsu.getVistasPorUsuarioRol(((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrNomUsuario(),
																					((BeanUsuarioRol) usuariorol.get(0)).getUsuario().getStrClaUsuario());
										if (menu != null) {
											objD.setMenu(menu);
										}
										objD.setStrRol(((BeanUsuarioRol) usuariorol.get(0)).getRol().getStrIdRol());
										datose.add(objD);
										Mensaje.mostrarAviso(Constante.ACCESO_OFFLINE);
										dispose();
										objP = new Promesa(datose);
//										objP.setSincronizarAnticipoConbranza(sincronizarAnticipoCobranza);
									}
								}
							} else {
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_ACCESO);
								txtUsuario.setText("");
								txtClave.setText("");
								txtUsuario.requestFocus();
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						Mensaje.mostrarAviso(Constante.MENSAJE_ERROR);
					}
				}
				bloqueador.dispose();
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}
	private void sincronizaAgenda(){
		String[] mensaje = new String[2];
		int cantidadRegistros = 0;
		com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlSincronizacionImpl = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
		mensaje[1] = "Sincronización de agendas";
		Calendar inicioCalendario = Calendar.getInstance();
		long inicio = inicioCalendario.getTimeInMillis();
		Date fechaInicio = new Date();
		fechaInicio.setMonth(fechaInicio.getMonth() - fechaInicio.getMonth());
		fechaInicio.setDate(1);
		BeanBuscarPedido param = new BeanBuscarPedido();
		param.setStrVendorId(objD.getStrCodigo());
		param.setStrDocType("ZP01");
		param.setStrFechaInicio(fechaInicio);
		param.setStrFechaFin(new Date());
		mensaje[0] = SincronizacionPedidos.sincronizarTablaAgenda(param);
		cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_AGENDA");
		Calendar finCalendario = Calendar.getInstance();
		long fin = finCalendario.getTimeInMillis();
		String strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
		com.promesa.administracion.bean.BeanSincronizar beanSincronizar = new com.promesa.administracion.bean.BeanSincronizar();
		beanSincronizar.setStrIdeSinc("3");
		beanSincronizar.setStrTie(strTie);
		beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
		beanSincronizar.setStrCantReg(cantidadRegistros + "");
		SqlSincronizacionImpl sqlSincronizacion = new SqlSincronizacionImpl();
		sqlSincronizacion.actualizarSincronizar(beanSincronizar);
	}
	
	private void activaSincronizacion(int opcion, long tiempoTotal) throws Exception {
		int cantidadRegistros = 0;
		com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlSincronizacionImpl = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
		switch (opcion) {
		case 1:// usuarios
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO");
			break;
		case 2:// Roles y usuarios
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA_ROL");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA");
			break;
		case 3:// Agendas
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_AGENDA");
			break;
		case 4:// Bloqueos de entrega
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BLOQUEO_ENTREGA");
			break;
		case 5:// Clase de materiales
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLASE_MATERIAL");
			break;
		case 6:// Clientes
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLIENTE");
			break;
		case 7:// Combos
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_COMBO");
			break;
		case 8:// Condiciones comerciales
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_1");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_2");
			break;
		case 9:// Condiciones de pago
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICIONES_PAGO");
			break;
		case 10:// Destinatarios
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SEDE");
			break;
		case 11:// Dispositivos
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DISPOSITIVO");
			break;
		case 12:// Clientes
			break;
		case 13:// Feriados
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FERIADO");
			break;
		case 14:// Jerarquías
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_JERARQUIA");
			break;
		case 15:// Materiales
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL");
			break;
		case 16:// Stock materiales
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_STOCK");
			break;
		case 17:// Tipologías
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_TIPOLOGIA");
			break;
		case 29:// 
			cantidadRegistros = sqlSincronizacionImpl.cobranzaEliminados();
			break;
		case 30:// Tipologías
			cantidadRegistros = sqlSincronizacionImpl.anticiposEliminados();
			break;
		case 31:// Tipologías
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PRESUPUESTO");;
			break;
		case 33:// Materiales Nuevos
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_NUEVO");;
			break;
		case 34:// Materiales Nuevos
			cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_VENTA_CRUZADA");
			cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VENTA_CRUZADA");
			break;
		default:// No se ha seleccionado ninguna tabla
			cantidadRegistros = 0;
			break;
		}
		String strTie = Util.convierteMilisegundosAFormatoMMSS(tiempoTotal);
		com.promesa.administracion.bean.BeanSincronizar beanSincronizar = new com.promesa.administracion.bean.BeanSincronizar();
		beanSincronizar.setStrIdeSinc(opcion + "");
		beanSincronizar.setStrTie(strTie);
		beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
		beanSincronizar.setStrCantReg(cantidadRegistros + "");
		SqlSincronizacionImpl sqlSincronizacion = new SqlSincronizacionImpl();
		sqlSincronizacion.actualizarSincronizar(beanSincronizar);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnIniciar) {
			acceder();
		}
		if (e.getSource() == btnCancelar) {
			if (Mensaje.preguntar(Constante.PREGUNTA_SALIR))
				System.exit(0);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		new MakeEnterDoAction();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}