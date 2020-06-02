package com.promesa.frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import com.promesa.administracion.sql.impl.*;
import com.promesa.bean.*;
import com.promesa.util.*;
import com.promesa.sap.*;
import com.promesa.main.*;


public class CambioClave extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBase;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JLabel lblAntiguaClave;
	private JTextField txtAntiguaClave;
	private JLabel lblNuevaClave;
	private JTextField txtNuevaClave;
	private JLabel lblConfirmacionNuevaClave;
	private JTextField txtConfirmacionNuevaClave;
	private JButton btnCancelar;
	private JLabel lblLogo;
	private JButton btnAceptar;
	SqlUsuarioImpl objUsu;
	Cmd objC;
	SAdministracion objSAP;
	static String acceso;
	List<BeanDato> datose;
	List<BeanLogin> accesoe;
	BeanDato objD;
	Promesa objP;
	List<BeanDato> ldato;

	public CambioClave(String nomUsu, String claUsu, List<BeanDato> datose) {

		this.setTitle("Proffline");
		this.setSize(400, 407);
		this.setResizable(false);
		this.addWindowListener(this);
		this.setIconImage(new ImageIcon("imagenes/icono.jpg").getImage());
		getContentPane().setBackground(new java.awt.Color(255, 255, 255));
		pnlBase = new JPanel();
		pnlBase.setLayout(null);
		getContentPane().add(pnlBase, BorderLayout.CENTER);
		pnlBase.setBackground(new java.awt.Color(255, 255, 255));
		pnlBase.setPreferredSize(new java.awt.Dimension(399, 322));
		this.datose = datose;
		{
			btnAceptar = new JButton();
			pnlBase.add(btnAceptar);
			btnAceptar.setBounds(72, 290, 91, 21);
			btnAceptar.setBorderPainted(false);
			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/btnAceptar.gif")));
			btnAceptar.addActionListener(this);
			btnAceptar.setCursor(new Cursor(TipoCursor.Hand()));
			btnAceptar.setToolTipText("Aceptar");
			btnAceptar.setContentAreaFilled(false);
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
			lblUsuario.setBounds(12, 140, 112, 21);
			lblUsuario.setText("USUARIO:");
			lblUsuario.setFont(new java.awt.Font("Arial", 1, 10));
			lblUsuario.setForeground(new java.awt.Color(0, 0, 128));
			lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			txtUsuario = new JTextField();
			pnlBase.add(txtUsuario);
			txtUsuario.setBounds(180, 140, 196, 21);
			txtUsuario.setForeground(new java.awt.Color(0, 0, 0));
			txtUsuario.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			txtUsuario.setFont(new java.awt.Font("Arial", 0, 10));
			txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
			txtUsuario.setBackground(new java.awt.Color(168, 226, 255));
			txtUsuario.setEditable(false);
			txtUsuario.setText(nomUsu);
		}
		{
			lblAntiguaClave = new JLabel();
			pnlBase.add(lblAntiguaClave);
			lblAntiguaClave.setBounds(12, 170, 112, 21);
			lblAntiguaClave.setText("ANTIGUA CLAVE:");
			lblAntiguaClave.setFont(new java.awt.Font("Arial", 1, 10));
			lblAntiguaClave.setForeground(new java.awt.Color(0, 0, 128));
			lblAntiguaClave.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			txtAntiguaClave = new JPasswordField();
			pnlBase.add(txtAntiguaClave);
			txtAntiguaClave.setBounds(180, 170, 196, 21);
			txtAntiguaClave.setForeground(new java.awt.Color(0, 0, 0));
			txtAntiguaClave.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			txtAntiguaClave.setFont(new java.awt.Font("Arial", 0, 10));
			txtAntiguaClave.setHorizontalAlignment(SwingConstants.CENTER);
			txtAntiguaClave.setBackground(new java.awt.Color(168, 226, 255));
			txtAntiguaClave.setEditable(false);
			txtAntiguaClave.setText(claUsu);
		}
		{
			lblNuevaClave = new JLabel();
			pnlBase.add(lblNuevaClave);
			lblNuevaClave.setBounds(12, 200, 112, 21);
			lblNuevaClave.setText("NUEVA CLAVE:");
			lblNuevaClave.setFont(new java.awt.Font("Arial", 1, 10));
			lblNuevaClave.setForeground(new java.awt.Color(0, 0, 128));
			lblNuevaClave.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			txtNuevaClave = new JPasswordField();
			pnlBase.add(txtNuevaClave);
			txtNuevaClave.setBounds(180, 200, 196, 21);
			txtNuevaClave.setForeground(new java.awt.Color(0, 0, 0));
			txtNuevaClave.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			txtNuevaClave.setHorizontalAlignment(SwingConstants.CENTER);
			txtNuevaClave.setFont(new java.awt.Font("Arial", 0, 10));
			txtNuevaClave.setBackground(new java.awt.Color(255, 255, 255));
			txtNuevaClave.requestFocus();
		}
		{
			lblConfirmacionNuevaClave = new JLabel();
			pnlBase.add(lblConfirmacionNuevaClave);
			lblConfirmacionNuevaClave.setBounds(12, 230, 200, 21);
			lblConfirmacionNuevaClave.setText("CONFIRMACIÓN NUEVA CLAVE:");
			lblConfirmacionNuevaClave.setFont(new java.awt.Font("Arial", 1, 10));
			lblConfirmacionNuevaClave.setForeground(new java.awt.Color(0, 0, 128));
			lblConfirmacionNuevaClave.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			txtConfirmacionNuevaClave = new JPasswordField();
			pnlBase.add(txtConfirmacionNuevaClave);
			txtConfirmacionNuevaClave.setBounds(180, 230, 196, 21);
			txtConfirmacionNuevaClave.setForeground(new java.awt.Color(0, 0, 0));
			txtConfirmacionNuevaClave.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			txtConfirmacionNuevaClave.setHorizontalAlignment(SwingConstants.CENTER);
			txtConfirmacionNuevaClave.setFont(new java.awt.Font("Arial", 0, 10));
			txtConfirmacionNuevaClave.setBackground(new java.awt.Color(255, 255, 255));
			txtConfirmacionNuevaClave.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						actualizar();
					}
				}
			});
		}
		{
			lblLogo = new JLabel();
			pnlBase.add(lblLogo);
			lblLogo.setBounds(28, 21, 150, 95);
			lblLogo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/logo_promesa.jpg")));
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

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Mensaje.mostrarAviso(Constante.MENSAJE_CIERRE);
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

	@SuppressWarnings({ "static-access", "rawtypes" })
	public void actualizar() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				String x, y;
				x = txtNuevaClave.getText().trim();
				y = txtConfirmacionNuevaClave.getText().trim();
				if (x.equals("") && y.equals("")) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_CLAVES);
					txtNuevaClave.requestFocus();
				} else if (!(x.equals("")) && y.equals("")) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_CONFIRMACION_CLAVE);
					txtConfirmacionNuevaClave.requestFocus();
				} else if (x.equals("") && !(y.equals(""))) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_CLAVE);
					txtNuevaClave.requestFocus();
				} else if (!(x.equals("")) && !(y.equals(""))) {
					if (x.equals(y)) {
						if (!x.equals(txtAntiguaClave.getText().trim())) {
							ldato = new ArrayList<BeanDato>();
							String valor;
							objD = new BeanDato();
							valor = objC.disponibilidadSAP();
							objD.setStrConexion(valor);
							try {
								objD.setStrModo(Constante.MODO_ONLINE);
								objSAP = new SAdministracion();
								accesoe = objSAP.accesoUsuario(txtUsuario.getText().trim(), txtAntiguaClave.getText().trim(), 
										txtAntiguaClave.getText().trim(), txtNuevaClave.getText().trim(), txtConfirmacionNuevaClave.getText().trim(), objC.numeroSerie());
								if (((BeanLogin) accesoe.get(0)).getStrEstado().equals(Constante.VACIO)) {
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_SERIE_VACIA);
								} else if (((BeanLogin) accesoe.get(0)).getStrEstado().equals(Constante.VALOR_ESTADO_INACTIVO_DISPOSITIVO)) {
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_SERIE_INACTIVA);
								} else {
									if (((BeanLogin) accesoe.get(0)).getStrAcceso().equals("X")) {
										objD.setStrUsuario(((BeanLogin) accesoe.get(0)).getStrUsuario());
										objD.setStrCodigo(((BeanLogin) accesoe.get(0)).getStrCodigo());
										objD.setStrNU(txtUsuario.getText().trim());
										objD.setStrPU(txtNuevaClave.getText().trim());
										objD.setMenu(((BeanLogin) accesoe.get(0)).getMenu());
										objD.setStrRol(((BeanLogin) accesoe.get(0)).getStrRol());
										ldato.add(objD);
										Mensaje.mostrarAviso(Constante.ACCESO_ONLINE);
										dispose();
										bloqueador.dispose();
										objP = new Promesa(ldato);
									} else {
										List msg = ((BeanLogin) accesoe.get(0)).getMsg();
										if (msg.size() > 1) {
											Mensaje.mostrarError(msg.get(0).toString() + "\n" + msg.get(1).toString());
										}
										if (msg.get(0).toString().equals(Constante.MENSAJE_USUARIO_SIN_ROL)) {
											Mensaje.mostrarError(msg.get(0).toString());
										}
									}
								}
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
								Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_ACTUALIZA_CLAVE);
							}
						} else {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_17);
						}
					} else {
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_16);
					}
				}
				if (bloqueador.isVisible()) {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAceptar) {
			actualizar();
		}
		if (e.getSource() == btnCancelar) {
			if (Mensaje.preguntar(Constante.PREGUNTA_SALIR))
				System.exit(0);
		}
	}
}