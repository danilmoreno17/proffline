package com.promesa.internalFrame.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import com.promesa.bean.*;
import com.promesa.main.Promesa;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;
import com.promesa.sap.*;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.internalFrame.*;

public class INuevoRol extends JInternalFrame implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	JToolBar mnuToolBar;
	JButton btnRol;
	JButton btnVista;
	JButton btnUsuario;
	JButton btnDispositivo;
	JButton btnGuardar;
	private JLabel lblGuardar;
	private JLabel lblIdRol;
	private JTextField txtIdRol;
	private JLabel lblNomRol;
	private JTextField txtNomRol;
	JSeparator separador;
	JPanel pnlRol;
	SAdministracion objSAP;
	static List<BeanDato> datose;
	private BeanTareaProgramada beanTareaProgramada;

	@SuppressWarnings("static-access")
	public INuevoRol(String idRol, String nomRol, BeanTareaProgramada beanTareaProgramada) {

		super("Roles", true, true, true, true);
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iroles.png")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar, BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 1366, 10); // 0, 39, 1318, 10

		this.datose = this.beanTareaProgramada.getDatose();

		btnRol = new JButton();
		mnuToolBar.add(btnRol);
		btnRol.setText("Roles");
		btnRol.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/roles.png")));
		btnRol.addActionListener(this);
		btnRol.setToolTipText("Roles");
		btnRol.setPreferredSize(new java.awt.Dimension(93, 29));
		btnRol.setBackground(new java.awt.Color(0, 128, 64));
		btnRol.setOpaque(false);
		btnRol.setBorder(null);
		btnRol.setFocusable(false);

		btnVista = new JButton();
		mnuToolBar.add(btnVista);
		btnVista.setText("Vistas");
		btnVista.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/vistas.gif")));
		btnVista.addActionListener(this);
		btnVista.setToolTipText("Vistas");
		btnVista.setPreferredSize(new java.awt.Dimension(100, 29));
		btnVista.setBackground(new java.awt.Color(0, 128, 64));
		btnVista.setOpaque(false);
		btnVista.setBorder(null);
		btnVista.setFocusable(false);

		btnUsuario = new JButton();
		mnuToolBar.add(btnUsuario);
		btnUsuario.setText("Usuarios");
		btnUsuario.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/usuarios.png")));
		btnUsuario.addActionListener(this);
		btnUsuario.setToolTipText("Usuarios");
		btnUsuario.setPreferredSize(new java.awt.Dimension(100, 29));
		btnUsuario.setBackground(new java.awt.Color(0, 128, 64));
		btnUsuario.setOpaque(false);
		btnUsuario.setBorder(null);
		btnUsuario.setFocusable(false);

		btnDispositivo = new JButton();
		mnuToolBar.add(btnDispositivo);
		btnDispositivo.setText("Dispositivos");
		btnDispositivo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/dispositivos.png")));
		btnDispositivo.addActionListener(this);
		btnDispositivo.setToolTipText("Dispositivos");
		btnDispositivo.setPreferredSize(new java.awt.Dimension(100, 29));
		btnDispositivo.setBackground(new java.awt.Color(0, 128, 64));
		btnDispositivo.setOpaque(false);
		btnDispositivo.setBorder(null);
		btnDispositivo.setFocusable(false);

		pnlRol = new JPanel();
		getContentPane().add(pnlRol);
		pnlRol.setBounds(0, 48, 790, 273); // 0, 48, 1119, 273
		pnlRol.setLayout(null);
		pnlRol.setBorder(BorderFactory.createTitledBorder("Detalle del Rol"));
		{
			btnGuardar = new JButton();
			pnlRol.add(btnGuardar);
			btnGuardar.setText("");
			btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.jpg")));
			btnGuardar.addActionListener(this);
			btnGuardar.setToolTipText("Guardar");
			btnGuardar.setBackground(new java.awt.Color(0, 128, 64));
			btnGuardar.setBounds(12, 30, 28, 20); // horizontal, vertical,  width, high
			btnGuardar.setOpaque(false);
			btnGuardar.setFocusable(false);
		}
		{
			lblGuardar = new JLabel();
			pnlRol.add(lblGuardar);
			lblGuardar.setText("GUARDAR");
			lblGuardar.setBounds(45, 32, 125, 17);
			lblGuardar.setFont(new java.awt.Font("Arial", 1, 10));
		}
		{
			lblIdRol = new JLabel();
			pnlRol.add(lblIdRol);
			lblIdRol.setText("ID Rol:");
			lblIdRol.setBounds(16, 80, 119, 17);
			lblIdRol.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtIdRol = new JTextField();
			pnlRol.add(txtIdRol);
			txtIdRol.setBounds(100, 78, 147, 24);
			txtIdRol.setEditable(false);
			txtIdRol.setBackground(new java.awt.Color(168, 226, 255));
			txtIdRol.setRequestFocusEnabled(false);
			txtIdRol.setToolTipText("ID Rol");
			txtIdRol.setText(idRol);
		}
		{
			lblNomRol = new JLabel();
			pnlRol.add(lblNomRol);
			lblNomRol.setText("Nombre Rol:");
			lblNomRol.setBounds(16, 120, 119, 17);
			lblNomRol.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtNomRol = new JTextField();
			pnlRol.add(txtNomRol);
			txtNomRol.setBounds(100, 118, 147, 24);
			txtNomRol.setBackground(new java.awt.Color(255, 255, 255));
			txtNomRol.setToolTipText("Nombre Rol");
			txtNomRol.setText(nomRol);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtNomRol.setRequestFocusEnabled(false);
			}
			txtNomRol.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					if (txtNomRol.getText().length() > 15) {
						txtNomRol.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
						txtNomRol.setToolTipText(Constante.MENSAJE_VALIDA_MAGNITUD_NOMBRE_ROL);
					}
				}
				public void keyPressed(KeyEvent arg0) {
				}

				public void keyReleased(KeyEvent arg0) {
				}
			});
		}
	}

	@SuppressWarnings("static-access")
	public INuevoRol(BeanTareaProgramada beanTareaProgramada) {

		super("Roles", true, true, true, true);
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iroles.png")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
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

		btnRol = new JButton();
		mnuToolBar.add(btnRol);
		btnRol.setText("Roles");
		btnRol.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/roles.png")));
		btnRol.addActionListener(this);
		btnRol.setToolTipText("Roles");
		btnRol.setPreferredSize(new java.awt.Dimension(93, 29));
		btnRol.setBackground(new java.awt.Color(0, 128, 64));
		btnRol.setOpaque(false);
		btnRol.setBorder(null);
		btnRol.setFocusable(false);

		btnVista = new JButton();
		mnuToolBar.add(btnVista);
		btnVista.setText("Vistas");
		btnVista.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/vistas.gif")));
		btnVista.addActionListener(this);
		btnVista.setToolTipText("Vistas");
		btnVista.setPreferredSize(new java.awt.Dimension(100, 29));
		btnVista.setBackground(new java.awt.Color(0, 128, 64));
		btnVista.setOpaque(false);
		btnVista.setBorder(null);
		btnVista.setFocusable(false);

		btnUsuario = new JButton();
		mnuToolBar.add(btnUsuario);
		btnUsuario.setText("Usuarios");
		btnUsuario.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/usuarios.png")));
		btnUsuario.addActionListener(this);
		btnUsuario.setToolTipText("Usuarios");
		btnUsuario.setPreferredSize(new java.awt.Dimension(100, 29));
		btnUsuario.setBackground(new java.awt.Color(0, 128, 64));
		btnUsuario.setOpaque(false);
		btnUsuario.setBorder(null);
		btnUsuario.setFocusable(false);

		btnDispositivo = new JButton();
		mnuToolBar.add(btnDispositivo);
		btnDispositivo.setText("Dispositivos");
		btnDispositivo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/dispositivos.png")));
		btnDispositivo.addActionListener(this);
		btnDispositivo.setToolTipText("Dispositivos");
		btnDispositivo.setPreferredSize(new java.awt.Dimension(100, 29));
		btnDispositivo.setBackground(new java.awt.Color(0, 128, 64));
		btnDispositivo.setOpaque(false);
		btnDispositivo.setBorder(null);
		btnDispositivo.setFocusable(false);

		pnlRol = new JPanel();
		getContentPane().add(pnlRol);
		pnlRol.setBounds(0, 48, 790, 273); // 0, 48, 1119, 273
		pnlRol.setLayout(null);
		pnlRol.setBorder(BorderFactory.createTitledBorder("Detalle del Rol"));
		{
			btnGuardar = new JButton();
			pnlRol.add(btnGuardar);
			btnGuardar.setText("");
			btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.jpg")));
			btnGuardar.addActionListener(this);
			btnGuardar.setToolTipText("Guardar");
			btnGuardar.setBackground(new java.awt.Color(0, 128, 64));
			btnGuardar.setBounds(12, 30, 28, 20); // horizontal, vertical, width, high
			btnGuardar.setOpaque(false);
			btnGuardar.setFocusable(false);
		}
		{
			lblGuardar = new JLabel();
			pnlRol.add(lblGuardar);
			lblGuardar.setText("GUARDAR");
			lblGuardar.setBounds(45, 32, 125, 17);
			lblGuardar.setFont(new java.awt.Font("Arial", 1, 10));
		}
		{
			lblIdRol = new JLabel();
			pnlRol.add(lblIdRol);
			lblIdRol.setText("ID Rol:");
			lblIdRol.setBounds(16, 80, 119, 17);
			lblIdRol.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtIdRol = new JTextField();
			pnlRol.add(txtIdRol);
			txtIdRol.setBounds(100, 78, 147, 24);
			txtIdRol.setEditable(false);
			txtIdRol.setBackground(new java.awt.Color(168, 226, 255));
			txtIdRol.setRequestFocusEnabled(false);
			txtIdRol.setToolTipText("ID Rol");
		}
		{
			lblNomRol = new JLabel();
			pnlRol.add(lblNomRol);
			lblNomRol.setText("Nombre Rol:");
			lblNomRol.setBounds(16, 120, 119, 17);
			lblNomRol.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtNomRol = new JTextField();
			pnlRol.add(txtNomRol);
			txtNomRol.setBounds(100, 118, 147, 24);
			txtNomRol.setBackground(new java.awt.Color(255, 255, 255));
			txtNomRol.setToolTipText("Nombre Rol");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtNomRol.setRequestFocusEnabled(false);
			}
			txtNomRol.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					if (txtNomRol.getText().length() > 15) {
						txtNomRol.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
						txtNomRol.setToolTipText(Constante.MENSAJE_VALIDA_MAGNITUD_NOMBRE_ROL);
					}
				}
				public void keyPressed(KeyEvent arg0) {
				}

				public void keyReleased(KeyEvent arg0) {
				}
			});
		}
	}

	/* MÉTODO QUE INGRESA O MODIFICA UN ROL */
	public void ingresaModificaRol() {
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			if (!(txtNomRol.getText().trim().equals(Constante.VACIO))) {
				if (txtNomRol.getText().trim().length() > 20) {
					Mensaje.mostrarError(Constante.MENSAJE_VALIDA_MAGNITUD_NOMBRE_ROL);
					txtNomRol.setText(txtNomRol.getText().trim());
					txtNomRol.setToolTipText("Nombre Rol");
					txtNomRol.requestFocus();
				} else {
					int cont1, cont2;
					cont1 = cont2 = 0;
					for (int i = 0; i < txtNomRol.getText().trim().length(); i++) {
						if ((txtNomRol.getText().trim().charAt(i) != 'A'
								&& txtNomRol.getText().trim().charAt(i) != 'a'
								&& txtNomRol.getText().trim().charAt(i) != 'B'
								&& txtNomRol.getText().trim().charAt(i) != 'b'
								&& txtNomRol.getText().trim().charAt(i) != 'C'
								&& txtNomRol.getText().trim().charAt(i) != 'c'
								&& txtNomRol.getText().trim().charAt(i) != 'D'
								&& txtNomRol.getText().trim().charAt(i) != 'd'
								&& txtNomRol.getText().trim().charAt(i) != 'E'
								&& txtNomRol.getText().trim().charAt(i) != 'e'
								&& txtNomRol.getText().trim().charAt(i) != 'F'
								&& txtNomRol.getText().trim().charAt(i) != 'f'
								&& txtNomRol.getText().trim().charAt(i) != 'G'
								&& txtNomRol.getText().trim().charAt(i) != 'g'
								&& txtNomRol.getText().trim().charAt(i) != 'H'
								&& txtNomRol.getText().trim().charAt(i) != 'h'
								&& txtNomRol.getText().trim().charAt(i) != 'I'
								&& txtNomRol.getText().trim().charAt(i) != 'i'
								&& txtNomRol.getText().trim().charAt(i) != 'J'
								&& txtNomRol.getText().trim().charAt(i) != 'j'
								&& txtNomRol.getText().trim().charAt(i) != 'K'
								&& txtNomRol.getText().trim().charAt(i) != 'k'
								&& txtNomRol.getText().trim().charAt(i) != 'L'
								&& txtNomRol.getText().trim().charAt(i) != 'l'
								&& txtNomRol.getText().trim().charAt(i) != 'M'
								&& txtNomRol.getText().trim().charAt(i) != 'm'
								&& txtNomRol.getText().trim().charAt(i) != 'N'
								&& txtNomRol.getText().trim().charAt(i) != 'n'
								&& txtNomRol.getText().trim().charAt(i) != 'O'
								&& txtNomRol.getText().trim().charAt(i) != 'o'
								&& txtNomRol.getText().trim().charAt(i) != 'P'
								&& txtNomRol.getText().trim().charAt(i) != 'p'
								&& txtNomRol.getText().trim().charAt(i) != 'Q'
								&& txtNomRol.getText().trim().charAt(i) != 'q'
								&& txtNomRol.getText().trim().charAt(i) != 'R'
								&& txtNomRol.getText().trim().charAt(i) != 'r'
								&& txtNomRol.getText().trim().charAt(i) != 'S'
								&& txtNomRol.getText().trim().charAt(i) != 's'
								&& txtNomRol.getText().trim().charAt(i) != 'T'
								&& txtNomRol.getText().trim().charAt(i) != 't'
								&& txtNomRol.getText().trim().charAt(i) != 'U'
								&& txtNomRol.getText().trim().charAt(i) != 'u'
								&& txtNomRol.getText().trim().charAt(i) != 'V'
								&& txtNomRol.getText().trim().charAt(i) != 'v'
								&& txtNomRol.getText().trim().charAt(i) != 'W'
								&& txtNomRol.getText().trim().charAt(i) != 'w'
								&& txtNomRol.getText().trim().charAt(i) != 'X'
								&& txtNomRol.getText().trim().charAt(i) != 'x'
								&& txtNomRol.getText().trim().charAt(i) != 'Y'
								&& txtNomRol.getText().trim().charAt(i) != 'y'
								&& txtNomRol.getText().trim().charAt(i) != 'Z' && txtNomRol.getText().trim().charAt(i) != 'z')
								&& txtNomRol.getText().trim().charAt(i) == ' ')
							cont1++;
						if ((txtNomRol.getText().trim().charAt(i) != 'A'
								&& txtNomRol.getText().trim().charAt(i) != 'a'
								&& txtNomRol.getText().trim().charAt(i) != 'B'
								&& txtNomRol.getText().trim().charAt(i) != 'b'
								&& txtNomRol.getText().trim().charAt(i) != 'C'
								&& txtNomRol.getText().trim().charAt(i) != 'c'
								&& txtNomRol.getText().trim().charAt(i) != 'D'
								&& txtNomRol.getText().trim().charAt(i) != 'd'
								&& txtNomRol.getText().trim().charAt(i) != 'E'
								&& txtNomRol.getText().trim().charAt(i) != 'e'
								&& txtNomRol.getText().trim().charAt(i) != 'F'
								&& txtNomRol.getText().trim().charAt(i) != 'f'
								&& txtNomRol.getText().trim().charAt(i) != 'G'
								&& txtNomRol.getText().trim().charAt(i) != 'g'
								&& txtNomRol.getText().trim().charAt(i) != 'H'
								&& txtNomRol.getText().trim().charAt(i) != 'h'
								&& txtNomRol.getText().trim().charAt(i) != 'I'
								&& txtNomRol.getText().trim().charAt(i) != 'i'
								&& txtNomRol.getText().trim().charAt(i) != 'J'
								&& txtNomRol.getText().trim().charAt(i) != 'j'
								&& txtNomRol.getText().trim().charAt(i) != 'K'
								&& txtNomRol.getText().trim().charAt(i) != 'k'
								&& txtNomRol.getText().trim().charAt(i) != 'L'
								&& txtNomRol.getText().trim().charAt(i) != 'l'
								&& txtNomRol.getText().trim().charAt(i) != 'M'
								&& txtNomRol.getText().trim().charAt(i) != 'm'
								&& txtNomRol.getText().trim().charAt(i) != 'N'
								&& txtNomRol.getText().trim().charAt(i) != 'n'
								&& txtNomRol.getText().trim().charAt(i) != 'O'
								&& txtNomRol.getText().trim().charAt(i) != 'o'
								&& txtNomRol.getText().trim().charAt(i) != 'P'
								&& txtNomRol.getText().trim().charAt(i) != 'p'
								&& txtNomRol.getText().trim().charAt(i) != 'Q'
								&& txtNomRol.getText().trim().charAt(i) != 'q'
								&& txtNomRol.getText().trim().charAt(i) != 'R'
								&& txtNomRol.getText().trim().charAt(i) != 'r'
								&& txtNomRol.getText().trim().charAt(i) != 'S'
								&& txtNomRol.getText().trim().charAt(i) != 's'
								&& txtNomRol.getText().trim().charAt(i) != 'T'
								&& txtNomRol.getText().trim().charAt(i) != 't'
								&& txtNomRol.getText().trim().charAt(i) != 'U'
								&& txtNomRol.getText().trim().charAt(i) != 'u'
								&& txtNomRol.getText().trim().charAt(i) != 'V'
								&& txtNomRol.getText().trim().charAt(i) != 'v'
								&& txtNomRol.getText().trim().charAt(i) != 'W'
								&& txtNomRol.getText().trim().charAt(i) != 'w'
								&& txtNomRol.getText().trim().charAt(i) != 'X'
								&& txtNomRol.getText().trim().charAt(i) != 'x'
								&& txtNomRol.getText().trim().charAt(i) != 'Y'
								&& txtNomRol.getText().trim().charAt(i) != 'y'
								&& txtNomRol.getText().trim().charAt(i) != 'Z' && txtNomRol.getText().trim().charAt(i) != 'z')
								&& txtNomRol.getText().trim().charAt(i) != ' ')
							cont2++;
					}
					if (cont1 > 0 && cont2 == 0) {
						Mensaje.mostrarError(Constante.MENSAJE_VALIDA_ESPACIO_NOMBRE_ROL);
						txtNomRol.setText(txtNomRol.getText().trim());
						txtNomRol.requestFocus();
					} else if (cont1 == 0 && cont2 > 0) {
						Mensaje.mostrarError(Constante.MENSAJE_VALIDA_CARACTER_NOMBRE_ROL);
						txtNomRol.setText(txtNomRol.getText().trim());
						txtNomRol.requestFocus();
					} else if (cont1 > 0 && cont2 > 0) {
						Mensaje.mostrarError(Constante.MENSAJE_VALIDA_CARACTER_ESPACIO_NOMBRE_ROL);
						txtNomRol.setText(txtNomRol.getText().trim());
						txtNomRol.requestFocus();
					} else {
						if (txtIdRol.getText().trim().equals(Constante.VACIO)) {
							final DLocker bloqueador = new DLocker();
							Thread hilo = new Thread() {
								public void run() {
									Administrador a;
									List<String> retorno = new ArrayList<String>();
									objSAP = new SAdministracion();
									a = new Administrador();
									try {
										retorno = objSAP.ingresaModifcaRol(Constante.VACIO, txtNomRol.getText().trim());
										if (retorno.get(1).equals(Constante.MENSAJE_ERROR_NOMBRE_ROL)) {
											Mensaje.mostrarError(Constante.MENSAJE_ERROR_NOMBRE_ROL);
											txtNomRol.requestFocus();
										} else {
											if (!retorno.get(0).equals(Constante.VACIO)) {
												txtIdRol.setText(retorno.get(0));
												Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_NUEVO_ROL);
												txtNomRol.setText(" "+ txtNomRol.getText().trim());
												btnGuardar.setEnabled(false);
												txtNomRol.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
												txtNomRol.setToolTipText("Nombre Rol");
												a.muestraVentanaAdministrador(0, Promesa.destokp, beanTareaProgramada);
											} else {
												Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_NUEVO_ROL);
											}
										}
									} catch (Exception e) {
										Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_NUEVO_ROL);
										Util.mostrarExcepcion(e);
									}
									bloqueador.dispose();
								}
							};
							hilo.start();
							bloqueador.setVisible(true);
						} else {
							final DLocker bloqueador = new DLocker();
							Thread hilo = new Thread() {
								public void run() {
									Administrador a;
									List<String> retorno = new ArrayList<String>();
									objSAP = new SAdministracion();
									a = new Administrador();
									try {
										retorno = objSAP.ingresaModifcaRol(txtIdRol.getText().trim(), txtNomRol.getText().trim());
										if (retorno.get(1).equals(Constante.MENSAJE_ERROR_NOMBRE_ROL)) {
											Mensaje.mostrarError(Constante.MENSAJE_ERROR_NOMBRE_ROL);
											txtNomRol.requestFocus();
										} else {
											if (!retorno.get(0).equals(Constante.VACIO)) {
												txtIdRol.setText(retorno.get(0));
												Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_MODIFICA_ROL);
												txtNomRol.setText(" "+ txtNomRol.getText().trim());
												btnGuardar.setEnabled(false);
												txtNomRol.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
												txtNomRol.setToolTipText("Nombre Rol");
												a.muestraVentanaAdministrador(0, Promesa.destokp, beanTareaProgramada);
											} else {
												Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_MODIFICA_ROL);
											}
										}
									} catch (Exception e) {
										Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_MODIFICA_ROL);
										Util.mostrarExcepcion(e);
									}
									bloqueador.dispose();
								}
							};
							hilo.start();
							bloqueador.setVisible(true);
						}
					}
				}
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_VALIDA_CAMPO_VACIO_NOMBRE_ROL);
				txtNomRol.requestFocus();
			}
		} else {
			if (txtIdRol.getText().trim().equals(Constante.VACIO)) {
				Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_NUEVO_ROL);
			} else {
				Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_MODIFICA_ROL);
			}
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnRol) {
			/* LLAMA A PANTALLA ROL */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(0, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnVista) {
			/* LLAMA A PANTALLA VISTA */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(1, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnUsuario) {
			/* LLAMA A PANTALLA USUARIO */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(2, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnDispositivo) {
			/* LLAMA A PANTALLA DISPOSITIVO */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(6, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnGuardar) {
			/* FUNCIONALIDAD GUARDAR */
			ingresaModificaRol();
		}
	}
}