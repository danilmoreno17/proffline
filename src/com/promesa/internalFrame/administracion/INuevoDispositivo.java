package com.promesa.internalFrame.administracion;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import com.promesa.administracion.sql.SqlDispositivo;
import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.sql.SqlUsuarioRol;
import com.promesa.administracion.sql.impl.SqlDispositivoImpl;
import com.promesa.administracion.sql.impl.SqlUsuarioImpl;
import com.promesa.administracion.sql.impl.SqlUsuarioRolImpl;
import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.main.Promesa;
import com.promesa.util.*;
import com.promesa.sap.*;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.internalFrame.*;
@SuppressWarnings("rawtypes")
public class INuevoDispositivo extends JInternalFrame implements ActionListener, MouseListener, KeyListener {

	
	private static final long serialVersionUID = 1L;

	JToolBar mnuToolBar;
	JButton btnRol;
	JButton btnVista;
	JButton btnUsuario;
	JButton btnDispositivo;
	JButton btnGuardar;
	JButton btnBloquear;
	private JLabel lblGuardar;
	private JLabel lblBloquear;
	private JLabel lblIdDis;
	private JTextField txtIdDis;
	private JLabel lblNomRel;
	JComboBox cmbNomRel;
	private JLabel lblSerie;
	private JTextField txtSerie;
	private JLabel lblCodAct;
	private JTextField txtCodAct;
	private JLabel lblNumSeg;
	private JTextField txtNumSeg;
	private JLabel lblSimm;
	private JTextField txtSimm;
	private JLabel lblImei;
	private JTextField txtImei;
	private JLabel lblNumTel;
	private JTextField txtNumTel;
	private JLabel lblNomUsu;
	JComboBox cmbNomUsu;
	private JLabel lblObs;
	private JTextField txtObs;
	private JLabel lblCR;
	JSeparator separador;
	JPanel pnlDispositivo;
	SAdministracion objSAP;
	List<BeanUsuarioRol> usuariosrolese;
	List<BeanUsuario> usuariose;
	List<BeanDispositivo> dispositivose;
	static List<BeanDato> datose;
	private List<BeanUsuarioRol> listaUsuarioRol = null;
	private SqlUsuarioRol getUsuarioRol = null;
	private List<BeanUsuario> listaUsuario = null;
	private SqlUsuario getUsuario = null;
	@SuppressWarnings("unused")
	private SqlDispositivo getDispositivo = null;
	private BeanTareaProgramada beanTareaProgramada;
	String idUsu;

	@SuppressWarnings({ "static-access", "unchecked" })
	public INuevoDispositivo(String idDis, String nomRel, String serie, String codAct, String numSeg, String simm, String imei,
			String numTel, String idUsu, String nomUsu, String estado, String obs, BeanTareaProgramada beanTareaProgramada) {

		super("Dispositivos", true, true, true, true);
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/idispositivos.png")));
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
		new BeanUsuario();
		getUsuarioRol = new SqlUsuarioRolImpl();
		getUsuario = new SqlUsuarioImpl();
		getDispositivo = new SqlDispositivoImpl();
		this.idUsu = idUsu;

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

		pnlDispositivo = new JPanel();
		getContentPane().add(pnlDispositivo);
		pnlDispositivo.setBounds(0, 48, 790, 273);
		pnlDispositivo.setLayout(null);
		pnlDispositivo.setBorder(BorderFactory.createTitledBorder("Detalle del Dispositivo"));
		pnlDispositivo.setFocusable(false);
		{
			btnGuardar = new JButton();
			pnlDispositivo.add(btnGuardar);
			btnGuardar.setText("");
			btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.jpg")));
			btnGuardar.addActionListener(this);
			btnGuardar.setToolTipText("Guardar");
			btnGuardar.setBackground(new java.awt.Color(0, 128, 64));
			btnGuardar.setBounds(12, 28, 28, 20);
			btnGuardar.setOpaque(false);
		}
		{
			lblGuardar = new JLabel();
			pnlDispositivo.add(lblGuardar);
			lblGuardar.setText("GUARDAR");
			lblGuardar.setBounds(45, 30, 125, 17);
			lblGuardar.setFont(new java.awt.Font("Arial", 1, 10));
		}
		if (estado.equals("0")) {
			{
				lblCR = new JLabel();
				pnlDispositivo.add(lblCR);
				lblCR.setText("(*) Campos Obligatorios");
				lblCR.setBounds(115, 30, 125, 17);
				lblCR.setFont(new java.awt.Font("Arial", 1, 10));
			}
		} else {
			{
				btnBloquear = new JButton();
				pnlDispositivo.add(btnBloquear);
				btnBloquear.setText("");
				btnBloquear.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/candado_cerrado.png")));
				btnBloquear.addActionListener(this);
				btnBloquear.setToolTipText("Bloquear");
				btnBloquear.setBackground(new java.awt.Color(0, 128, 64));
				btnBloquear.setBounds(110, 28, 28, 20);
				btnBloquear.setEnabled(true);
				btnBloquear.setOpaque(false);
			}
			{
				lblBloquear = new JLabel();
				pnlDispositivo.add(lblBloquear);
				lblBloquear.setText("BLOQUEAR DISPOSITIVO");
				lblBloquear.setBounds(143, 30, 200, 17);
				lblBloquear.setFont(new java.awt.Font("Arial", 1, 10));
			}
			{
				lblCR = new JLabel();
				pnlDispositivo.add(lblCR);
				lblCR.setText("(*) Campos Obligatorios");
				lblCR.setBounds(290, 30, 125, 17);
				lblCR.setFont(new java.awt.Font("Arial", 1, 10));
			}
		}
		{
			lblIdDis = new JLabel();
			pnlDispositivo.add(lblIdDis);
			lblIdDis.setText("ID Dispositivo:");
			lblIdDis.setBounds(16, 70, 119, 17);
			lblIdDis.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtIdDis = new JTextField();
			pnlDispositivo.add(txtIdDis);
			txtIdDis.setBounds(130, 68, 147, 24);
			txtIdDis.setEditable(false);
			txtIdDis.setBackground(new java.awt.Color(168, 226, 255));
			txtIdDis.setToolTipText("ID Dispositivo");
			txtIdDis.setFocusable(false);
			txtIdDis.setText(idDis);
		}
		{
			lblNomRel = new JLabel();
			pnlDispositivo.add(lblNomRel);
			lblNomRel.setText("Tipo Dispositivo:");
			lblNomRel.setBounds(316, 70, 119, 17);
			lblNomRel.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			ComboBoxModel cmbcmbNomRelModel = new DefaultComboBoxModel(new String[] { "IMPRESORA", "NETBOOK", "OTRO" });
			cmbNomRel = new JComboBox();
			pnlDispositivo.add(cmbNomRel);
			cmbNomRel.setModel(cmbcmbNomRelModel);
			cmbNomRel.setToolTipText("Dispositivos");
			cmbNomRel.setFont(new java.awt.Font("Candara", 0, 11));
			cmbNomRel.setBackground(new java.awt.Color(255, 255, 255));
			cmbNomRel.setBounds(430, 68, 147, 24);
			cmbNomRel.setSelectedItem(nomRel);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				cmbNomRel.setEnabled(false);
			}
		}
		{
			lblSerie = new JLabel();
			pnlDispositivo.add(lblSerie);
			lblSerie.setText("N�mero Serie:  *");
			lblSerie.setBounds(16, 102, 112, 21);
			lblSerie.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtSerie = new JTextField();
			pnlDispositivo.add(txtSerie);
			txtSerie.setBounds(130, 100, 147, 24);
			txtSerie.setBackground(new java.awt.Color(255, 255, 255));
			txtSerie.setToolTipText("N�mero Serie");
			txtSerie.setText(serie);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtSerie.setRequestFocusEnabled(false);
			}
		}
		{
			lblCodAct = new JLabel();
			pnlDispositivo.add(lblCodAct);
			lblCodAct.setText("Cod. Act.:  *");
			lblCodAct.setBounds(316, 102, 119, 17);
			lblCodAct.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtCodAct = new JTextField();
			pnlDispositivo.add(txtCodAct);
			txtCodAct.setBounds(430, 100, 147, 24);
			txtCodAct.setBackground(new java.awt.Color(255, 255, 255));
			txtCodAct.setToolTipText("C�digo Activaci�n");
			txtCodAct.setText(codAct);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtCodAct.setRequestFocusEnabled(false);
			}
		}
		{
			lblNumSeg = new JLabel();
			pnlDispositivo.add(lblNumSeg);
			lblNumSeg.setText("Num. Seg.:  *");
			lblNumSeg.setBounds(16, 134, 119, 17);
			lblNumSeg.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtNumSeg = new JTextField();
			pnlDispositivo.add(txtNumSeg);
			txtNumSeg.setBounds(130, 132, 147, 24);
			txtNumSeg.setBackground(new java.awt.Color(255, 255, 255));
			txtNumSeg.setToolTipText("N�mero Seguro");
			txtNumSeg.setText(numSeg);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtNumSeg.setRequestFocusEnabled(false);
			}
		}
		{
			lblSimm = new JLabel();
			pnlDispositivo.add(lblSimm);
			lblSimm.setText("Simm:  *");
			lblSimm.setBounds(316, 134, 119, 17);
			lblSimm.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtSimm = new JTextField();
			pnlDispositivo.add(txtSimm);
			txtSimm.setBounds(430, 132, 147, 24);
			txtSimm.setBackground(new java.awt.Color(255, 255, 255));
			txtSimm.setToolTipText("Simm");
			txtSimm.setText(simm);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtSimm.setRequestFocusEnabled(false);
			}
		}
		{
			lblImei = new JLabel();
			pnlDispositivo.add(lblImei);
			lblImei.setText("Imei:  *");
			lblImei.setBounds(16, 166, 119, 17);
			lblImei.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtImei = new JTextField();
			pnlDispositivo.add(txtImei);
			txtImei.setBounds(130, 164, 147, 24);
			txtImei.setBackground(new java.awt.Color(255, 255, 255));
			txtImei.setToolTipText("Imei");
			txtImei.setText(imei);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtImei.setRequestFocusEnabled(false);
			}
		}
		{
			lblNumTel = new JLabel();
			pnlDispositivo.add(lblNumTel);
			lblNumTel.setText("Num. Tel.:  *");
			lblNumTel.setBounds(316, 166, 119, 17);
			lblNumTel.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtNumTel = new JTextField();
			pnlDispositivo.add(txtNumTel);
			txtNumTel.setBounds(430, 164, 147, 24);
			txtNumTel.setBackground(new java.awt.Color(255, 255, 255));
			txtNumTel.setToolTipText("N�mero Tel�fono");
			txtNumTel.setText(numTel);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtNumTel.setRequestFocusEnabled(false);
			}
		}
		{
			lblNomUsu = new JLabel();
			pnlDispositivo.add(lblNomUsu);
			lblNomUsu.setText("Usuario:");
			lblNomUsu.setBounds(16, 198, 119, 17);
			lblNomUsu.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbNomUsu = new JComboBox();
			pnlDispositivo.add(cmbNomUsu);
			cmbNomUsu.setBounds(130, 196, 147, 24);
			cmbNomUsu.setToolTipText("Usuarios");
			cmbNomUsu.addItem(nomUsu);
			cmbNomUsu.setFont(new java.awt.Font("Candara", 0, 11));
			cmbNomUsu.setBackground(new java.awt.Color(255, 255, 255));
			cmbNomUsu.setEnabled(false);
		}
		{
			lblObs = new JLabel();
			pnlDispositivo.add(lblObs);
			lblObs.setText("Observaci�n:");
			lblObs.setBounds(316, 198, 119, 17);
			lblObs.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtObs = new JTextField();
			pnlDispositivo.add(txtObs);
			txtObs.setBounds(430, 196, 147, 24);
			txtObs.setBackground(new java.awt.Color(255, 255, 255));
			txtObs.setToolTipText("Observaci�n");
			txtObs.setText(obs);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtObs.setRequestFocusEnabled(false);
			}
		}
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(txtSerie);
		policy.addIndexedComponent(txtCodAct);
		policy.addIndexedComponent(txtNumSeg);
		policy.addIndexedComponent(txtSimm);
		policy.addIndexedComponent(txtImei);
		policy.addIndexedComponent(txtNumTel);
		policy.addIndexedComponent(txtObs);
		setFocusTraversalPolicy(policy);
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public INuevoDispositivo(BeanTareaProgramada beanTareaProgramada) {

		super("Dispositivos", true, true, true, true);
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/idispositivos.png")));
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
		new BeanUsuario();
		getUsuarioRol = new SqlUsuarioRolImpl();
		getUsuario = new SqlUsuarioImpl();
		getDispositivo = new SqlDispositivoImpl();

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

		pnlDispositivo = new JPanel();
		getContentPane().add(pnlDispositivo);
		pnlDispositivo.setBounds(0, 48, 790, 273); // 0, 48, 1119, 273
		pnlDispositivo.setLayout(null);
		pnlDispositivo.setBorder(BorderFactory.createTitledBorder("Detalle del Dispositivo"));
		pnlDispositivo.setFocusable(false);
		{
			btnGuardar = new JButton();
			pnlDispositivo.add(btnGuardar);
			btnGuardar.setText("");
			btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.jpg")));
			btnGuardar.addActionListener(this);
			btnGuardar.setToolTipText("Guardar");
			btnGuardar.setBackground(new java.awt.Color(0, 128, 64));
			btnGuardar.setBounds(12, 28, 28, 20); // horizontal, vertical, width, high
			btnGuardar.setOpaque(false);
		}
		{
			lblGuardar = new JLabel();
			pnlDispositivo.add(lblGuardar);
			lblGuardar.setText("GUARDAR");
			lblGuardar.setBounds(45, 30, 125, 17);
			lblGuardar.setFont(new java.awt.Font("Arial", 1, 10));
		}
		{
			lblCR = new JLabel();
			pnlDispositivo.add(lblCR);
			lblCR.setText("(*) Campos Obligatorios");
			lblCR.setBounds(115, 30, 125, 17);
			lblCR.setFont(new java.awt.Font("Arial", 1, 10));
		}
		{
			lblIdDis = new JLabel();
			pnlDispositivo.add(lblIdDis);
			lblIdDis.setText("ID Dispositivo:");
			lblIdDis.setBounds(16, 70, 119, 17);
			lblIdDis.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtIdDis = new JTextField();
			pnlDispositivo.add(txtIdDis);
			txtIdDis.setBounds(130, 68, 147, 24);
			txtIdDis.setEditable(false);
			txtIdDis.setBackground(new java.awt.Color(168, 226, 255));
			txtIdDis.setToolTipText("ID Dispositivo");
		}
		{
			lblNomRel = new JLabel();
			pnlDispositivo.add(lblNomRel);
			lblNomRel.setText("Tipo Dispositivo:");
			lblNomRel.setBounds(316, 70, 119, 17);
			lblNomRel.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			ComboBoxModel cmbcmbNomRelModel = new DefaultComboBoxModel(new String[] { "IMPRESORA", "NETBOOK", "OTRO" });
			cmbNomRel = new JComboBox();
			pnlDispositivo.add(cmbNomRel);
			cmbNomRel.setModel(cmbcmbNomRelModel);
			cmbNomRel.setToolTipText("Dispositivos");
			cmbNomRel.setFont(new java.awt.Font("Candara", 0, 11));
			cmbNomRel.setBackground(new java.awt.Color(255, 255, 255));
			cmbNomRel.setBounds(430, 68, 147, 24);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				cmbNomRel.setEnabled(false);
			}
		}
		{
			lblSerie = new JLabel();
			pnlDispositivo.add(lblSerie);
			lblSerie.setText("N�mero Serie:  *");
			lblSerie.setBounds(16, 102, 112, 21);
			lblSerie.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtSerie = new JTextField();
			pnlDispositivo.add(txtSerie);
			txtSerie.setBounds(130, 100, 147, 24);
			txtSerie.setBackground(new java.awt.Color(255, 255, 255));
			txtSerie.setToolTipText("N�mero Serie");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtSerie.setRequestFocusEnabled(false);
			}
		}
		{
			lblCodAct = new JLabel();
			pnlDispositivo.add(lblCodAct);
			lblCodAct.setText("Cod. Act.:  *");
			lblCodAct.setBounds(316, 102, 119, 17);
			lblCodAct.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtCodAct = new JTextField();
			pnlDispositivo.add(txtCodAct);
			txtCodAct.setBounds(430, 100, 147, 24);
			txtCodAct.setBackground(new java.awt.Color(255, 255, 255));
			txtCodAct.setToolTipText("C�digo Activaci�n");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtCodAct.setRequestFocusEnabled(false);
			}
		}
		{
			lblNumSeg = new JLabel();
			pnlDispositivo.add(lblNumSeg);
			lblNumSeg.setText("Num. Seg.:  *");
			lblNumSeg.setBounds(16, 134, 119, 17);
			lblNumSeg.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtNumSeg = new JTextField();
			pnlDispositivo.add(txtNumSeg);
			txtNumSeg.setBounds(130, 132, 147, 24);
			txtNumSeg.setBackground(new java.awt.Color(255, 255, 255));
			txtNumSeg.setToolTipText("N�mero Seguro");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtNumSeg.setRequestFocusEnabled(false);
			}
		}
		{
			lblSimm = new JLabel();
			pnlDispositivo.add(lblSimm);
			lblSimm.setText("Simm:  *");
			lblSimm.setBounds(316, 134, 119, 17);
			lblSimm.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtSimm = new JTextField();
			pnlDispositivo.add(txtSimm);
			txtSimm.setBounds(430, 132, 147, 24);
			txtSimm.setBackground(new java.awt.Color(255, 255, 255));
			txtSimm.setToolTipText("Simm");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtSimm.setRequestFocusEnabled(false);
			}
		}
		{
			lblImei = new JLabel();
			pnlDispositivo.add(lblImei);
			lblImei.setText("Imei:  *");
			lblImei.setBounds(16, 166, 119, 17);
			lblImei.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtImei = new JTextField();
			pnlDispositivo.add(txtImei);
			txtImei.setBounds(130, 164, 147, 24);
			txtImei.setBackground(new java.awt.Color(255, 255, 255));
			txtImei.setToolTipText("Imei");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtImei.setRequestFocusEnabled(false);
			}
		}
		{
			lblNumTel = new JLabel();
			pnlDispositivo.add(lblNumTel);
			lblNumTel.setText("Num. Tel.:  *");
			lblNumTel.setBounds(316, 166, 119, 17);
			lblNumTel.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtNumTel = new JTextField();
			pnlDispositivo.add(txtNumTel);
			txtNumTel.setBounds(430, 164, 147, 24);
			txtNumTel.setBackground(new java.awt.Color(255, 255, 255));
			txtNumTel.setToolTipText("N�mero Tel�fono");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtNumTel.setRequestFocusEnabled(false);
			}
		}
		{
			lblNomUsu = new JLabel();
			pnlDispositivo.add(lblNomUsu);
			lblNomUsu.setText("Vendedor:");
			lblNomUsu.setBounds(16, 198, 119, 17);
			lblNomUsu.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbNomUsu = new JComboBox();
			pnlDispositivo.add(cmbNomUsu);
			cmbNomUsu.setBounds(130, 196, 147, 24);
			cmbNomUsu.setToolTipText("Usuarios");
			cmbNomUsu.setFont(new java.awt.Font("Candara", 0, 11));
			cmbNomUsu.setBackground(new java.awt.Color(255, 255, 255));
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				cmbNomUsu.setEnabled(false);
			}
		}
		cargaUsuario();
		{
			lblObs = new JLabel();
			pnlDispositivo.add(lblObs);
			lblObs.setText("Observaci�n:");
			lblObs.setBounds(316, 198, 119, 17);
			lblObs.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtObs = new JTextField();
			pnlDispositivo.add(txtObs);
			txtObs.setBounds(430, 196, 147, 24);
			txtObs.setBackground(new java.awt.Color(255, 255, 255));
			txtObs.setToolTipText("Observaci�n");
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)) {
				txtObs.setRequestFocusEnabled(false);
			}
		}
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(txtSerie);
		policy.addIndexedComponent(txtCodAct);
		policy.addIndexedComponent(txtNumSeg);
		policy.addIndexedComponent(txtSimm);
		policy.addIndexedComponent(txtImei);
		policy.addIndexedComponent(txtNumTel);
		policy.addIndexedComponent(txtObs);
		setFocusTraversalPolicy(policy);
	}

	/* M�TODO QUE REALIZA CARGA DE USUARIOS */
	@SuppressWarnings("unchecked")
	public void cargaUsuario() {
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			objSAP = new SAdministracion();
			try {
				usuariose = objSAP.buscaUsuario(Constante.VACIO);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		} else {
			usuariosrolese = new ArrayList<BeanUsuarioRol>();
			getUsuarioRol.setListaUsuarioRol();
			listaUsuarioRol = getUsuarioRol.getListaUsuarioRol();
			BeanUsuarioRol beanUsuarioRol = null;
			for (BeanUsuarioRol ur : listaUsuarioRol) {
				beanUsuarioRol = new BeanUsuarioRol();
				beanUsuarioRol.setStrIdRol(ur.getRol().getStrIdRol());
				beanUsuarioRol.setStrIdUsu(ur.getUsuario().getStrIdUsuario());
				beanUsuarioRol.setStrMandante(ur.getStrMandante());
				usuariosrolese.add(beanUsuarioRol);
			}
			usuariose = new ArrayList<BeanUsuario>();
			getUsuario.setListaUsuario();
			listaUsuario = getUsuario.getListaUsuario();
			BeanUsuario beanUsuario = null;
			for (BeanUsuario u : listaUsuario) {
				beanUsuario = new BeanUsuario();
				beanUsuario.setStrBloqueado(u.getStrUsuarioBloqueado() + "");
				beanUsuario.setStrClaUsuario(u.getStrClaUsuario());
				beanUsuario.setStrFecCre(u.getStrFecCre());
				beanUsuario.setStrHorUltAccSis(u.getStrHorUltAccSis());
				beanUsuario.setStrIdUsuario(u.getStrIdUsuario());
				beanUsuario.setStrMandante(u.getStrMandante());
				beanUsuario.setStrNomUsuario(u.getStrNomUsuario());
				beanUsuario.setStrFecUltAccSis(u.getStrFecUltAccSis());
				usuariose.add(beanUsuario);
			}
		}
		if (usuariose != null && usuariose.size() > 0) {
			for (int j = 0; j < usuariose.size(); j++) {
				cmbNomUsu.addItem(((BeanUsuario) usuariose.get(j)).getStrNomUsuario());
			}
		} else {
			cmbNomUsu.addItem("");
		}
	}

	public class CELL_RENDERER extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CELL_RENDERER() {
			setHorizontalAlignment(JLabel.CENTER);
		}
		@Override
		public Component getTableCellRendererComponent(JTable arg0, Object value, boolean arg2, boolean arg3, int arg4, int arg5) {
			setSelected((value != null && ((Boolean) value).booleanValue()));
			setBackground(arg0.getBackground());
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	}

	public class CELL_EDITOR extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;

		public CELL_EDITOR(JCheckBox checkBox) {
			super(checkBox);
			checkBox.setHorizontalAlignment(JLabel.CENTER);
		}
	}

	/* M�TODO QUE BLOQUEA DISPOSITIVO */
	public void bloqueaDispositivo() {
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					Administrador a = new Administrador();
					try {
						objSAP = new SAdministracion();
						if (objSAP.bloqueaDispositivo(txtIdDis.getText().trim())) {
							btnBloquear.setEnabled(false);
							Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_BLOQUEA_DISPOSITIVO);
							a.muestraVentanaAdministrador(6, Promesa.destokp, beanTareaProgramada);
						} else {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_BLOQUEA_DISPOSITIVO);
						}
					} catch (Exception e) {
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_BLOQUEA_DISPOSITIVO);
						Util.mostrarExcepcion(e);
					}
					bloqueador.dispose();
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_BLOQUEA_DISPOSITIVO);
		}
	}

	/* M�TODO QUE INGRESA MODIFICA DISPOSITIVO */
	public void ingresaModificaDispositivo() {
		String strIdDis = txtIdDis.getText();
		String strSerie = txtSerie.getText().toUpperCase().trim();
		String strCodAct = txtCodAct.getText().toUpperCase().trim();
		String strNumSeg = txtNumSeg.getText().toUpperCase().trim();
		String strSimm = txtSimm.getText().toUpperCase().trim();
		String strImei = txtImei.getText().toUpperCase().trim();
		String strNumTel = txtNumTel.getText().toUpperCase().trim();
		String strNomUsu = "";
		if (cmbNomUsu.getSelectedItem() != null) {
			strNomUsu = cmbNomUsu.getSelectedItem().toString();
		}
		String mensajeError = "";
		if (strSerie.equalsIgnoreCase("")) {
			mensajeError = Constante.MENSAJE_INGRESA_DISPOSITIVO;
			txtSerie.requestFocus();
		} else if (strCodAct.equalsIgnoreCase("")) {
			mensajeError = Constante.MENSAJE_INGRESA_DISPOSITIVO;
			txtCodAct.requestFocus();
		} else if (strNumSeg.equalsIgnoreCase("")) {
			mensajeError = Constante.MENSAJE_INGRESA_DISPOSITIVO;
			txtNumSeg.requestFocus();
		} else if (strSimm.equalsIgnoreCase("")) {
			mensajeError = Constante.MENSAJE_INGRESA_DISPOSITIVO;
			txtSimm.requestFocus();
		} else if (strImei.equalsIgnoreCase("")) {
			mensajeError = Constante.MENSAJE_INGRESA_DISPOSITIVO;
			txtImei.requestFocus();
		} else if (strNumTel.equalsIgnoreCase("")) {
			mensajeError = Constante.MENSAJE_INGRESA_DISPOSITIVO;
			txtNumTel.requestFocus();
		} else if (strNomUsu.equalsIgnoreCase("")) {
			mensajeError = Constante.MENSAJE_INGRESA_DISPOSITIVO;
			cmbNomUsu.requestFocus();
		}
		if (strIdDis.equalsIgnoreCase("")) {
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
				if (!mensajeError.equalsIgnoreCase("")) {
					Mensaje.mostrarError(mensajeError);
				} else {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							String strNomRel = cmbNomRel.getSelectedItem().toString();
							String strSerie = txtSerie.getText().toUpperCase().trim();
							String strCodAct = txtCodAct.getText().toUpperCase().trim();
							String strNumSeg = txtNumSeg.getText().toUpperCase().trim();
							String strSimm = txtSimm.getText().toUpperCase().trim();
							String strImei = txtImei.getText().toUpperCase().trim();
							String strNumTel = txtNumTel.getText().toUpperCase().trim();
							String strObs = txtObs.getText().toUpperCase().trim();
							String strNomUsu = cmbNomUsu.getSelectedItem().toString();
							Administrador a = new Administrador();
							List<String> retorno = new ArrayList<String>();
							String idUsu, std;
							idUsu = "";
							std = "0";
							try {
								dispositivose = objSAP.buscaDispositivo("");
								if (dispositivose != null) {
									for (int i = 0; i < dispositivose.size(); i++) {
										if (((BeanDispositivo) dispositivose.get(i)).getStrNomUsuario().equals(cmbNomUsu.getSelectedItem().toString().trim())
												&& ((BeanDispositivo) dispositivose.get(i)).getStrDispositivoRelacionado().equals(cmbNomRel.getSelectedItem().toString().trim())
												&& ((BeanDispositivo) dispositivose.get(i)).getStrEstado().equals("1")) {
											std = "1";
											break;
										}
									}
								}
								if (std.equalsIgnoreCase("1")) {
									Mensaje.mostrarError(Constante.DISPOSITIVO_ASIGNADO_VENDEDOR);
								} else {
									for (int i = 0; i < usuariose.size(); i++) {
										if (((BeanUsuario) usuariose.get(i)).getStrNomUsuario().equals(cmbNomUsu.getSelectedItem().toString().trim())) {
											idUsu = ((BeanUsuario) usuariose.get(i)).getStrIdUsuario();
											break;
										}
									}
									if (strNomRel.equalsIgnoreCase("NETBOOK")) {
										retorno = objSAP.ingresaModificaDispositivo(Constante.VACIO, Constante.NETBOOK, strSerie, strCodAct,
														strNumSeg, strSimm, strImei, strNumTel, idUsu, strNomUsu,
														strNomRel, strObs, ((BeanDato) datose.get(0)).getStrNU());
									} else if (strNomRel.equalsIgnoreCase("IMPRESORA")) {
										retorno = objSAP.ingresaModificaDispositivo(Constante.VACIO, Constante.IMPRESORA,
														strSerie, strCodAct, strNumSeg, strSimm, strImei, strNumTel, idUsu, strNomUsu,
														strNomRel, strObs, ((BeanDato) datose.get(0)).getStrNU());
									} else if (strNomRel.equalsIgnoreCase("OTRO")) {
										retorno = objSAP.ingresaModificaDispositivo(Constante.VACIO, Constante.OTRO,
														strSerie, strCodAct, strNumSeg, strSimm, strImei, strNumTel,
														idUsu, strNomUsu, strNomRel, strObs, ((BeanDato) datose .get(0)) .getStrNU());
									}
									if (retorno.get(1).equals(Constante.MENSAJE_ERROR_NUMERO_SERIE)) {
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUMERO_SERIE);
										txtSerie.requestFocus();
									} else if (retorno.get(1).equals(Constante.MENSAJE_ERROR_CODIGO_ACTIVACION)) {
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_CODIGO_ACTIVACION);
										txtCodAct.requestFocus();
									} else if (retorno.get(1).equals(Constante.MENSAJE_ERROR_NUMERO_SERIE_CODIGO_ACTIVACION)) {
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUMERO_SERIE_CODIGO_ACTIVACION);
										txtSerie.requestFocus();
									} else {
										if (!retorno.get(0).equals(Constante.VACIO)) {
											txtIdDis.setText(retorno.get(0));
											btnGuardar.setEnabled(false);
											Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_NUEVO_DISPOSTIVO);
										} else {
											Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVO_DISPOSTIVO);
										}
										a.muestraVentanaAdministrador(6, Promesa.destokp, beanTareaProgramada);
									}
								}
							} catch (Exception e) {
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVO_DISPOSTIVO);
								Util.mostrarExcepcion(e);
							}
							bloqueador.dispose();
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}
			} else {
				Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_NUEVO_DISPOSTIVO);
			}
		} else {
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
				if (!mensajeError.equalsIgnoreCase("")) {
					Mensaje.mostrarError(mensajeError);
				} else {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							String strIdDis = txtIdDis.getText();
							String strNomRel = cmbNomRel.getSelectedItem().toString();
							String strSerie = txtSerie.getText().toUpperCase().trim();
							String strCodAct = txtCodAct.getText().toUpperCase().trim();
							String strNumSeg = txtNumSeg.getText().toUpperCase().trim();
							String strSimm = txtSimm.getText().toUpperCase().trim();
							String strImei = txtImei.getText().toUpperCase().trim();
							String strNumTel = txtNumTel.getText().toUpperCase().trim();
							String strObs = txtObs.getText().toUpperCase().trim();
							String strNomUsu = cmbNomUsu.getSelectedItem().toString();
							Administrador a = new Administrador();
							List<String> retorno = new ArrayList<String>();
							objSAP = new SAdministracion();
							try {
								if (strNomRel.equalsIgnoreCase("NETBOOK")) {
									retorno = objSAP.ingresaModificaDispositivo(strIdDis, Constante.NETBOOK, strSerie, strCodAct,
													strNumSeg, strSimm, strImei, strNumTel, idUsu, strNomUsu, strNomRel, strObs, ((BeanDato) datose.get(0)).getStrNU());
								} else if (strNomRel.equalsIgnoreCase("IMPRESORA")) {
									retorno = objSAP.ingresaModificaDispositivo( strIdDis, Constante.IMPRESORA, strSerie, strCodAct, strNumSeg, strSimm, strImei, strNumTel, idUsu,
													strNomUsu, strNomRel, strObs, ((BeanDato) datose.get(0)).getStrNU());
								} else if (strNomRel.equalsIgnoreCase("OTRO")) {
									retorno = objSAP.ingresaModificaDispositivo(strIdDis, Constante.OTRO, strSerie, strCodAct,
													strNumSeg, strSimm, strImei, strNumTel, idUsu, strNomUsu, strNomRel, strObs, ((BeanDato) datose .get(0)).getStrNU());
								}
								if (retorno.get(1).equals(Constante.MENSAJE_ERROR_NUMERO_SERIE)) {
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUMERO_SERIE);
									txtSerie.requestFocus();
								} else if (retorno.get(1).equals(Constante.MENSAJE_ERROR_CODIGO_ACTIVACION)) {
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_CODIGO_ACTIVACION);
									txtCodAct.requestFocus();
								} else if (retorno.get(1).equals(Constante.MENSAJE_ERROR_NUMERO_SERIE_CODIGO_ACTIVACION)) {
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUMERO_SERIE_CODIGO_ACTIVACION);
									txtSerie.requestFocus();
								} else {
									if (!retorno.get(0).equals(Constante.VACIO)) {
										btnGuardar.setEnabled(false);
										Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_MODIFICA_DISPOSITIVO);
									} else {
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVO_DISPOSTIVO);
									}
									a.muestraVentanaAdministrador(6, Promesa.destokp, beanTareaProgramada);
								}
							} catch (Exception e) {
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_MODIFICA_DISPOSTIVO);
								Util.mostrarExcepcion(e);
							}
							bloqueador.dispose();
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}
			} else {
				Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_MODIFICA_DISPOSTIVO);
			}
		}
	}

	// Begin
	private class IndexedFocusTraversalPolicy extends FocusTraversalPolicy {
		private ArrayList<Component> components = new ArrayList<Component>();

		public void addIndexedComponent(Component component) {
			components.add(component);
		}

		@Override
		public Component getComponentAfter(Container aContainer, Component aComponent) {
			int atIndex = components.indexOf(aComponent);
			int nextIndex = (atIndex + 1) % components.size();
			return components.get(nextIndex);
		}

		@Override
		public Component getComponentBefore(Container aContainer, Component aComponent) {
			int atIndex = components.indexOf(aComponent);
			int nextIndex = (atIndex + components.size() - 1) % components.size();
			return components.get(nextIndex);
		}

		@Override
		public Component getFirstComponent(Container aContainer) {
			return components.get(0);
		}

		@Override
		public Component getDefaultComponent(Container aContainer) {
			return null;
		}

		@Override
		public Component getLastComponent(Container aContainer) {
			return null;
		}
	}
	// End

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
		if (arg0.getSource() == btnBloquear) {
			/* IMPLEMENTA FUNCIONALIDAD BLOQUEAR DISPOSITIVO */
			bloqueaDispositivo();
		}
		if (arg0.getSource() == btnGuardar) {
			/* IMPLEMEMTA FUJNCIONALIDAD GUARDAR */
			ingresaModificaDispositivo();
		}
	}
}