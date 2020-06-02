package com.promesa.internalFrame.administracion;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import com.promesa.administracion.sql.SqlRol;
import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.sql.impl.SqlRolImpll;
import com.promesa.administracion.sql.impl.SqlUsuarioImpl;
import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.main.Promesa;
import com.promesa.util.*;
import com.promesa.sap.*;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.internalFrame.*;

public class INuevoUsuario extends JInternalFrame implements ActionListener, MouseListener, KeyListener{

	private static final long serialVersionUID = 1L;
	
	JToolBar mnuToolBar;
	JButton  btnRol;
	JButton  btnVista;
	JButton  btnUsuario;
	JButton  btnDispositivo;
	JButton  btnGuardar;
	JButton  btnDesbloquear;
	private JLabel lblGuardar;
	private JLabel lblDesbloquear; 
	private JLabel lblIdUsu;
	private JTextField txtIdUsu;
	private JLabel lblUsu;
	private JTextField txtUsu;	
	private JLabel lblClave;
	private JPasswordField txtClave;
	private JLabel lblConClave;
	private JPasswordField txtConClave;
	private JLabel lblFecCre;
	private JTextField txtFecCre;
	private JLabel lblFecUltAccSis;
	private JTextField txtFecUltAccSis;
	private JLabel lblHorUltAccSis;
	private JTextField txtHorUltAccSis;
	private JLabel lblIdentificacion;
	private JTextField txtIdentificacion;
	private JLabel Identificacion;
	private JLabel lblCR;
	JSeparator separador;
	private JTable tblRol;
	DefaultTableModel  modeloTabla;
	JScrollPane scrRol;
	JPanel pnlUsuario;
	JPanel pnlRol;
	SAdministracion objSAP;
	List<BeanRol> rolese;
	List<BeanRolUsuario> rolesusuarioe;
	List<BeanIdentificacion> identificacionese;
	static List<BeanDato> datose;
	@SuppressWarnings("unused")
	private SqlUsuario getUsuario = null;
	private JPopupMenu popupOpciones = null;
    private JScrollPane scrollerOpciones;    
    private JTable tablaOpciones = new JTable();
    ModeloTablaOpcion modelOpciones;
    @SuppressWarnings("unused")
	private TableRowSorter<DefaultTableModel> sorterOpciones;  
    private BeanTareaProgramada beanTareaProgramada;
    
    int iFilaRol = -1;
    String strRol = "";
    boolean strActivo = false; 
	
	@SuppressWarnings("static-access")
	public INuevoUsuario(String idUsu, String nomUsu, String claUsu, String fecCre, String fecUltAccSis, String horUltAccSis, String bloqueado, String identificacion, List<BeanRolUsuario> rolesusuariose, BeanTareaProgramada beanTareaProgramada){
		
		super("Usuarios",true,true,true,true);
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iusuarios.png")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar,BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 1366, 10); // 0, 39, 1318, 10

		this.datose = beanTareaProgramada.getDatose();
		getUsuario = new SqlUsuarioImpl();
		
		btnRol= new JButton();
		mnuToolBar.add(btnRol);
		btnRol.setText("Roles");
		btnRol.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/roles.png")));
		btnRol.addActionListener(this);
		btnRol.setToolTipText("Roles");
		btnRol.setPreferredSize(new java.awt.Dimension(93, 29));
		btnRol.setBackground(new java.awt.Color(0,128,64));
		btnRol.setOpaque(false);
		btnRol.setBorder(null);

		btnVista= new JButton();
		mnuToolBar.add(btnVista);
		btnVista.setText("Vistas");
		btnVista.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/vistas.gif")));
		btnVista.addActionListener(this);
		btnVista.setToolTipText("Vistas");
		btnVista.setPreferredSize(new java.awt.Dimension(100, 29));
		btnVista.setBackground(new java.awt.Color(0,128,64));
		btnVista.setOpaque(false);
		btnVista.setBorder(null);
		
		btnUsuario= new JButton();
		mnuToolBar.add(btnUsuario);
		btnUsuario.setText("Usuarios");
		btnUsuario.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/usuarios.png")));
		btnUsuario.addActionListener(this);
		btnUsuario.setToolTipText("Usuarios");
		btnUsuario.setPreferredSize(new java.awt.Dimension(100, 29));
		btnUsuario.setBackground(new java.awt.Color(0,128,64));
		btnUsuario.setOpaque(false);
		btnUsuario.setBorder(null);
		
		btnDispositivo = new JButton();
		mnuToolBar.add(btnDispositivo);
		btnDispositivo.setText("Dispositivos");
		btnDispositivo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/dispositivos.png")));
		btnDispositivo.addActionListener(this);
		btnDispositivo.setToolTipText("Dispositivos");
		btnDispositivo.setPreferredSize(new java.awt.Dimension(100, 29));
		btnDispositivo.setBackground(new java.awt.Color(0,128,64));
		btnDispositivo.setOpaque(false);
		btnDispositivo.setBorder(null);

		pnlUsuario = new JPanel();
		getContentPane().add(pnlUsuario);
		pnlUsuario.setBounds(0, 48, 790, 210); // 0, 48, 1119, 273
		pnlUsuario.setLayout(null);
		pnlUsuario.setBorder(BorderFactory.createTitledBorder("Detalle del Usuario"));
		pnlUsuario.setFocusable(false);
		{
			btnGuardar = new JButton();			
			pnlUsuario.add(btnGuardar);
			btnGuardar.setText("");
			btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.jpg")));
			btnGuardar.addActionListener(this);
			btnGuardar.setToolTipText("Guardar");			
			btnGuardar.setBackground(new java.awt.Color(0,128,64));
			btnGuardar.setBounds(12, 28, 28, 20); // horizontal, vertical, width, high
			btnGuardar.setOpaque(false);
		}
		{
			lblGuardar = new JLabel();
			pnlUsuario.add(lblGuardar);
			lblGuardar.setText("GUARDAR");
			lblGuardar.setBounds(45, 30, 125, 17);
			lblGuardar.setFont(new java.awt.Font("Arial",1,10));
		}
		if (bloqueado.equals("") || bloqueado.equals("0")){
			{
				lblCR = new JLabel();
				pnlUsuario.add(lblCR);
				lblCR.setText("(*) Campos Obligatorios");
				lblCR.setBounds(115, 30, 125, 17);
				lblCR.setFont(new java.awt.Font("Arial",1,10));
			}			
		}else{
			{
				btnDesbloquear = new JButton();			
				pnlUsuario.add(btnDesbloquear);
				btnDesbloquear.setText("");
				btnDesbloquear.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/candado_abierto.png")));
				btnDesbloquear.addActionListener(this);
				btnDesbloquear.setToolTipText("Desbloquear");			
				btnDesbloquear.setBackground(new java.awt.Color(0,128,64));
				btnDesbloquear.setBounds(110, 30, 28, 20); // horizontal, vertical, width, high
				btnDesbloquear.setEnabled(true);
				btnDesbloquear.setOpaque(false);
			}
			{
				lblDesbloquear = new JLabel();
				pnlUsuario.add(lblDesbloquear);
				lblDesbloquear.setText("DESBLOQUEAR USUARIO");
				lblDesbloquear.setBounds(143, 30, 200, 17);
				lblDesbloquear.setFont(new java.awt.Font("Arial",1,10));
			}
			{
				lblCR = new JLabel();
				pnlUsuario.add(lblCR);
				lblCR.setText("(*) Campos Obligatorios");
				lblCR.setBounds(290, 30, 125, 17);
				lblCR.setFont(new java.awt.Font("Arial",1,10));
			}
		}
		{
			lblIdUsu = new JLabel();
			pnlUsuario.add(lblIdUsu);
			lblIdUsu.setText("ID Usuario:");
			lblIdUsu.setBounds(16, 70, 119, 17);
			lblIdUsu.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtIdUsu = new JTextField();
			pnlUsuario.add(txtIdUsu);
			txtIdUsu.setBounds(130, 68, 147, 24);
			txtIdUsu.setEditable(false);
			txtIdUsu.setBackground(new java.awt.Color(168,226,255));						
			txtIdUsu.setToolTipText("ID Usuario");
			txtIdUsu.setFocusable(false);
			txtIdUsu.setText(idUsu);
		}
		{
			lblUsu = new JLabel();
			pnlUsuario.add(lblUsu);
			lblUsu.setText("Usuario:  *");
			lblUsu.setBounds(316, 70, 119, 17);
			lblUsu.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtUsu = new JTextField();
			pnlUsuario.add(txtUsu);
			txtUsu.setBounds(430, 68, 147, 24);			
			txtUsu.setBackground(new java.awt.Color(255,255,255));			
			txtUsu.setToolTipText("Usuario");
			txtUsu.setText(nomUsu);
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtUsu.setRequestFocusEnabled(false);}
		}
		{
			lblClave = new JLabel();
			pnlUsuario.add(lblClave);
			lblClave.setBounds(16, 102, 112, 21);
			lblClave.setText("Clave:  *");			
			lblClave.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtClave = new JPasswordField();
			pnlUsuario.add(txtClave);
			txtClave.setBounds(130, 100, 147, 24);
			txtClave.setBackground(new java.awt.Color(255,255,255));			
			txtClave.setToolTipText("Clave");
			txtClave.setText(claUsu);
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtClave.setRequestFocusEnabled(false);}
		}
		{
			lblConClave = new JLabel();
			pnlUsuario.add(lblConClave);
			lblConClave.setBounds(316, 102, 112, 21);
			lblConClave.setText("Confirmación:  *");			
			lblConClave.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtConClave = new JPasswordField();
			pnlUsuario.add(txtConClave);
			txtConClave.setBounds(430, 100, 147, 24);
			txtConClave.setBackground(new java.awt.Color(255,255,255));
			txtConClave.setToolTipText("Confirmación Clave");
			txtConClave.setText(claUsu);
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtConClave.setRequestFocusEnabled(false);}
		}
		{
			lblFecCre = new JLabel();
			pnlUsuario.add(lblFecCre);
			lblFecCre.setText("Fecha Creación:");
			lblFecCre.setBounds(16, 134, 119, 17);
			lblFecCre.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			
			txtFecCre = new JTextField();
			pnlUsuario.add(txtFecCre);
			txtFecCre.setBounds(130, 132, 147, 24);
			txtFecCre.setEditable(false);
			txtFecCre.setBackground(new java.awt.Color(168,226,255));						
			txtFecCre.setToolTipText("Fecha Creación");
			txtFecCre.setFocusable(false);
			txtFecCre.setText(fecCre);
		}
		{
			lblFecUltAccSis = new JLabel();
			pnlUsuario.add(lblFecUltAccSis);
			lblFecUltAccSis.setText("Fec. Ult. Acc. Sis.:");
			lblFecUltAccSis.setBounds(316, 134, 119, 17);
			lblFecUltAccSis.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			txtFecUltAccSis = new JTextField();
			pnlUsuario.add(txtFecUltAccSis);
			txtFecUltAccSis.setBounds(430, 132, 147, 24);
			txtFecUltAccSis.setEditable(false);
			txtFecUltAccSis.setBackground(new java.awt.Color(168,226,255));						
			txtFecUltAccSis.setToolTipText("Fecha del Último Acceso al Sistema");
			txtFecUltAccSis.setFocusable(false);
			txtFecUltAccSis.setText(fecUltAccSis);
		}
		{
			lblHorUltAccSis = new JLabel();
			pnlUsuario.add(lblHorUltAccSis);
			lblHorUltAccSis.setText("Hor. Ult. Acc. Sis.:");
			lblHorUltAccSis.setBounds(16, 166, 119, 17);
			lblHorUltAccSis.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			txtHorUltAccSis = new JTextField();
			pnlUsuario.add(txtHorUltAccSis);
			txtHorUltAccSis.setBounds(130, 164, 147, 24);
			txtHorUltAccSis.setEditable(false);
			txtHorUltAccSis.setBackground(new java.awt.Color(168,226,255));						
			txtHorUltAccSis.setToolTipText("Hora del Último Acceso al Sistema");
			txtHorUltAccSis .setFocusable(false);
			txtHorUltAccSis.setText(horUltAccSis);
		}
		{
			lblIdentificacion = new JLabel();
			pnlUsuario.add(lblIdentificacion);
			lblIdentificacion.setText("Identificación: *");
			lblIdentificacion.setBounds(316, 166, 119, 17);
			lblIdentificacion.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			txtIdentificacion = new JTextField();
			pnlUsuario.add(txtIdentificacion);
			txtIdentificacion.setBounds(430, 164, 147, 24);		
			if(((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){txtIdentificacion.setEditable(false);}			
			txtIdentificacion.setBackground(new java.awt.Color(255,255,255));						
			txtIdentificacion.setToolTipText("Identificación");
			txtIdentificacion.setText(identificacion);
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtIdentificacion.setRequestFocusEnabled(false);}
			if(((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){
				txtIdentificacion.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						txtIdentificacionMouseClicked(evt);
		            }
		        });
			}			
		}
		{
			Identificacion = new JLabel();
			pnlUsuario.add(Identificacion);
			Identificacion.setBounds(433, 186, 300, 21);						
			Identificacion.setFont(new java.awt.Font("Arial",1,11));
			if(((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){Identificacion.setText(traeDato(identificacion));}
		}
		pnlRol = new JPanel();
		getContentPane().add(pnlRol);
		pnlRol.setBounds(0,260, 790, 145); // 0, 48, 1119, 273
		pnlRol.setLayout(null);
		pnlRol.setBorder(BorderFactory.createTitledBorder("Roles"));
		pnlRol.setFocusable(false);
		
		scrRol = new JScrollPane();
		pnlRol.add(scrRol);
		scrRol.setBounds(50, 25, 680, 95);
		scrRol.setToolTipText("Asignación de Roles");
		
        this.rolesusuarioe = rolesusuariose;  
		String Columnas[] = {"Rol","Asignado"};
        modeloTabla = new DefaultTableModel(null,Columnas);			
		modeloTabla.setNumRows(rolesusuariose.size());	
		for (int i = 0; i < rolesusuariose.size(); i++){				
			for(int j = 0; j < 1; j++){
				modeloTabla.setValueAt(((BeanRolUsuario)rolesusuariose.get(i)).getStrNomRol(), i, j);				
					if (((BeanRolUsuario)rolesusuariose.get(i)).getStrC() != null)					
						modeloTabla.setValueAt(new Boolean(true), i, j+1);
					else					
						modeloTabla.setValueAt(new Boolean(false), i, j+1);													
			}
		}		
		tblRol = new JTable();		
		tblRol.setModel(modeloTabla);			
		tblRol.addKeyListener(this);
		tblRol.addMouseListener(this);	
		tblRol.getColumn("Asignado").setCellRenderer(new CELL_RENDERER());
		tblRol.getColumn("Asignado").setMaxWidth(400);
		tblRol.getColumn("Asignado").setCellEditor(new CELL_EDITOR(new JCheckBox()));
		scrRol.setViewportView(tblRol);
		pnlRol.add(scrRol);		
		new Renderer().setSizeColumn(tblRol.getColumnModel(),1,400);		
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(txtUsu);
		policy.addIndexedComponent(txtClave);
		policy.addIndexedComponent(txtConClave);		
		setFocusTraversalPolicy(policy);
		if(((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){
			creaTablaOpciones();
			llenarModeloOpciones();
		}
	}

	@SuppressWarnings("static-access")
	public INuevoUsuario(BeanTareaProgramada beanTareaProgramada){
		
		super("Usuarios",true,true,true,true);
		this.beanTareaProgramada=beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iusuarios.png")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar,BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 1366, 10); // 0, 39, 1318, 10
		
		this.datose = beanTareaProgramada.getDatose();
		new Util();

		btnRol= new JButton();
		mnuToolBar.add(btnRol);
		btnRol.setText("Roles");
		btnRol.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/roles.png")));
		btnRol.addActionListener(this);
		btnRol.setToolTipText("Roles");
		btnRol.setPreferredSize(new java.awt.Dimension(93, 29));
		btnRol.setBackground(new java.awt.Color(0,128,64));
		btnRol.setOpaque(false);
		btnRol.setBorder(null);

		btnVista= new JButton();
		mnuToolBar.add(btnVista);
		btnVista.setText("Vistas");
		btnVista.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/vistas.gif")));
		btnVista.addActionListener(this);
		btnVista.setToolTipText("Vistas");
		btnVista.setPreferredSize(new java.awt.Dimension(100, 29));
		btnVista.setBackground(new java.awt.Color(0,128,64));
		btnVista.setOpaque(false);
		btnVista.setBorder(null);
		
		btnUsuario= new JButton();
		mnuToolBar.add(btnUsuario);
		btnUsuario.setText("Usuarios");
		btnUsuario.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/usuarios.png")));
		btnUsuario.addActionListener(this);
		btnUsuario.setToolTipText("Usuarios");
		btnUsuario.setPreferredSize(new java.awt.Dimension(100, 29));
		btnUsuario.setBackground(new java.awt.Color(0,128,64));
		btnUsuario.setOpaque(false);
		btnUsuario.setBorder(null);
		
		btnDispositivo = new JButton();
		mnuToolBar.add(btnDispositivo);
		btnDispositivo.setText("Dispositivos");
		btnDispositivo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/dispositivos.png")));
		btnDispositivo.addActionListener(this);
		btnDispositivo.setToolTipText("Dispositivos");
		btnDispositivo.setPreferredSize(new java.awt.Dimension(100, 29));
		btnDispositivo.setBackground(new java.awt.Color(0,128,64));
		btnDispositivo.setOpaque(false);
		btnDispositivo.setBorder(null);

		pnlUsuario = new JPanel();
		getContentPane().add(pnlUsuario);
		pnlUsuario.setBounds(0, 48, 790, 210); // 0, 48, 1119, 273
		pnlUsuario.setLayout(null);
		pnlUsuario.setBorder(BorderFactory.createTitledBorder("Detalle del Usuario"));
		pnlUsuario.setFocusable(false);
		{
			btnGuardar = new JButton();			
			pnlUsuario.add(btnGuardar);
			btnGuardar.setText("");
			btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.jpg")));
			btnGuardar.addActionListener(this);
			btnGuardar.setToolTipText("Guardar");			
			btnGuardar.setBackground(new java.awt.Color(0,128,64));
			btnGuardar.setBounds(12, 28, 28, 20); // horizontal, vertical, width, high
			btnGuardar.setOpaque(false);
		}
		{
			lblGuardar = new JLabel();
			pnlUsuario.add(lblGuardar);
			lblGuardar.setText("GUARDAR");
			lblGuardar.setBounds(45, 30, 125, 17);
			lblGuardar.setFont(new java.awt.Font("Arial",1,10));
		}
		{
			lblCR = new JLabel();
			pnlUsuario.add(lblCR);
			lblCR.setText("(*) Campos Obligatorios");
			lblCR.setBounds(115, 30, 125, 17);
			lblCR.setFont(new java.awt.Font("Arial",1,10));
		}
		{
			lblIdUsu = new JLabel();
			pnlUsuario.add(lblIdUsu);
			lblIdUsu.setText("ID Usuario:");
			lblIdUsu.setBounds(16, 70, 119, 17);
			lblIdUsu.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtIdUsu = new JTextField();
			pnlUsuario.add(txtIdUsu);
			txtIdUsu.setBounds(130, 68, 147, 24);
			txtIdUsu.setEditable(false);
			txtIdUsu.setBackground(new java.awt.Color(168,226,255));						
			txtIdUsu.setToolTipText("ID Usuario");
			txtIdUsu.setFocusable(false);
		}
		{
			lblUsu = new JLabel();
			pnlUsuario.add(lblUsu);
			lblUsu.setText("Usuario:  *");
			lblUsu.setBounds(316, 70, 119, 17);
			lblUsu.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtUsu = new JTextField();
			pnlUsuario.add(txtUsu);
			txtUsu.setBounds(430, 68, 147, 24);			
			txtUsu.setBackground(new java.awt.Color(255,255,255));			
			txtUsu.setToolTipText("Usuario");
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtUsu.setRequestFocusEnabled(false);}
		}
		{
			lblClave = new JLabel();
			pnlUsuario.add(lblClave);
			lblClave.setBounds(16, 102, 112, 21);
			lblClave.setText("Clave:  *");			
			lblClave.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtClave = new JPasswordField();
			pnlUsuario.add(txtClave);
			txtClave.setBounds(130, 100, 147, 24);
			txtClave.setBackground(new java.awt.Color(255,255,255));			
			txtClave.setToolTipText("Clave");
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtClave.setRequestFocusEnabled(false);}
		}
		{
			lblConClave = new JLabel();
			pnlUsuario.add(lblConClave);
			lblConClave.setBounds(316, 102, 112, 21);
			lblConClave.setText("Confirmación:  *");			
			lblConClave.setFont(new java.awt.Font("Arial",1,11));			
		}
		{
			txtConClave = new JPasswordField();
			pnlUsuario.add(txtConClave);
			txtConClave.setBounds(430, 100, 147, 24);
			txtConClave.setBackground(new java.awt.Color(255,255,255));
			txtConClave.setToolTipText("Confirmación Clave");
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtConClave.setRequestFocusEnabled(false);}
		}
		{
			lblFecCre = new JLabel();
			pnlUsuario.add(lblFecCre);
			lblFecCre.setText("Fecha Creación:");
			lblFecCre.setBounds(16, 134, 119, 17);
			lblFecCre.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			txtFecCre = new JTextField();
			pnlUsuario.add(txtFecCre);
			txtFecCre.setBounds(130, 132, 147, 24);
			txtFecCre.setEditable(false);
			txtFecCre.setBackground(new java.awt.Color(168,226,255));						
			txtFecCre.setToolTipText("Fecha Creación");
			txtFecCre.setFocusable(false);			
		}
		{
			lblFecUltAccSis = new JLabel();
			pnlUsuario.add(lblFecUltAccSis);
			lblFecUltAccSis.setText("Fec. Ult. Acc. Sis.:");
			lblFecUltAccSis.setBounds(316, 134, 119, 17);
			lblFecUltAccSis.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			txtFecUltAccSis = new JTextField();
			pnlUsuario.add(txtFecUltAccSis);
			txtFecUltAccSis.setBounds(430, 132, 147, 24);
			txtFecUltAccSis.setEditable(false);
			txtFecUltAccSis.setBackground(new java.awt.Color(168,226,255));						
			txtFecUltAccSis.setToolTipText("Fecha del Último Acceso al Sistema");
			txtFecUltAccSis.setFocusable(false);			
		}
		{
			lblHorUltAccSis = new JLabel();
			pnlUsuario.add(lblHorUltAccSis);
			lblHorUltAccSis.setText("Hor. Ult. Acc. Sis.:");
			lblHorUltAccSis.setBounds(16, 166, 119, 17);
			lblHorUltAccSis.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			txtHorUltAccSis = new JTextField();
			pnlUsuario.add(txtHorUltAccSis);
			txtHorUltAccSis.setBounds(130, 164, 147, 24);
			txtHorUltAccSis.setEditable(false);
			txtHorUltAccSis.setBackground(new java.awt.Color(168,226,255));						
			txtHorUltAccSis.setToolTipText("Hora del Último Acceso al Sistema");
			txtHorUltAccSis .setFocusable(false);
		}
		{
			lblIdentificacion = new JLabel();
			pnlUsuario.add(lblIdentificacion);
			lblIdentificacion.setText("Identificación: *");
			lblIdentificacion.setBounds(316, 166, 119, 17);
			lblIdentificacion.setFont(new java.awt.Font("Arial",1,11));
		}
		{
			txtIdentificacion = new JTextField();
			pnlUsuario.add(txtIdentificacion);
			txtIdentificacion.setBounds(430, 164, 147, 24);
			if(((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){txtIdentificacion.setEditable(false);}
			txtIdentificacion.setBackground(new java.awt.Color(255,255,255));						
			txtIdentificacion.setToolTipText("Identificación");
			if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_OFFLINE)){txtIdentificacion.setRequestFocusEnabled(false);}
			if(((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){
				txtIdentificacion.addMouseListener(new MouseAdapter() {
		            public void mouseClicked(MouseEvent evt) {
		            	txtIdentificacionMouseClicked(evt);
		            }
		        });
			}
		}
		{
			Identificacion = new JLabel();
			pnlUsuario.add(Identificacion);
			Identificacion.setBounds(433, 186, 300, 21);						
			Identificacion.setFont(new java.awt.Font("Arial",1,11));			
		}
		pnlRol = new JPanel();
		getContentPane().add(pnlRol);
		pnlRol.setBounds(0,260, 790, 145); // 0, 48, 1119, 273
		pnlRol.setLayout(null);
		pnlRol.setBorder(BorderFactory.createTitledBorder("Roles"));
		pnlRol.setFocusable(false);
		
		scrRol = new JScrollPane();
		pnlRol.add(scrRol);
		scrRol.setBounds(50, 25, 680, 95);
		scrRol.setToolTipText("Asignación de Roles");
		
        cargaRol();
        tablaRol();
		
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(txtUsu);
		policy.addIndexedComponent(txtClave);
		policy.addIndexedComponent(txtConClave);		
		setFocusTraversalPolicy(policy);		

		if(((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){
			creaTablaOpciones();
			llenarModeloOpciones();
		}
	}
	
	/* MÉTODO QUE CARGA ROLES */
	public void cargaRol(){
		if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){			
			try {
				objSAP = new SAdministracion();
				rolese = objSAP.listaRoles();		
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		}else{
			BeanRol beanRol = null;		
			BeanRol rol = new BeanRol();
			List<BeanRol> listaRol = null;
			SqlRol getRol = new SqlRolImpll();
			getRol.setListaRol(rol);  
			listaRol = getRol.getListaRol();
			rolese = new ArrayList<BeanRol>();
			for(BeanRol r : listaRol){
				beanRol = new BeanRol();
				beanRol.setStrIdRol(r.getStrIdRol()); 
				beanRol.setStrMandante(r.getStrMandante());
				beanRol.setStrNomRol(r.getStrNomRol());			
				rolese.add(beanRol);				  
			}
		}
	}
	
	/* MÉTODO QUE LLENA TABLA DE ROLES */
	public void tablaRol(){
		String Columnas[] = {"Rol","Asignado"};
		modeloTabla= new DefaultTableModel(null,Columnas);		
		modeloTabla.setNumRows(rolese.size());			
		for (int i=0; i<rolese.size(); i++){				
			for(int j=0; j<1; j++){
				modeloTabla.setValueAt(((BeanRol)rolese.get(i)).getStrNomRol(), i, j);
				modeloTabla.setValueAt(Boolean.FALSE, i, j+1);
			}
		}		
		tblRol = new JTable();		
		tblRol.setModel(modeloTabla);			
		tblRol.addKeyListener(this);
		tblRol.addMouseListener(this);		
		tblRol.getColumn("Asignado").setCellRenderer(new CELL_RENDERER());
		tblRol.getColumn("Asignado").setMaxWidth(400);
		tblRol.getColumn("Asignado").setCellEditor(new CELL_EDITOR(new JCheckBox()));        
		scrRol.setViewportView(tblRol);
		pnlRol.add(scrRol);		
		new Renderer().setSizeColumn(tblRol.getColumnModel(),1,400);
	}
	
	public class CELL_RENDERER extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
	  
	    public CELL_RENDERER(){
	    	setHorizontalAlignment(JLabel.CENTER);
	    }
	    
	    @Override
	    public Component getTableCellRendererComponent(JTable arg0,
	    Object value, boolean arg2, boolean arg3, int arg4, int arg5) {
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
	
	/* MÉTODO QUE DESBLOQUEA USUARIO */
	public void desbloqueaUsuario(){
		if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					Administrador a = new Administrador();		
					try {
						objSAP = new SAdministracion();
						objSAP.desbloqueaUsuario(txtIdUsu.getText().trim());				
						btnDesbloquear.setEnabled(false);
						Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_DESBLOQUEA_USUARIO);
						a.muestraVentanaAdministrador(2, Promesa.destokp, beanTareaProgramada);
					} catch (Exception e) {				
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_DESBLOQUEA_USUARIO);
						Util.mostrarExcepcion(e);
					}
					bloqueador.dispose();							
				}
			};	
			hilo.start();
			bloqueador.setVisible(true);		
		}else{
			Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_DESBLOQUEA_USUARIO);
		}				
	}
	
	private boolean ZonaValidaVendedor(String identificacion) {
		for(BeanIdentificacion bi : identificacionese) {
			if (bi.getStrId().equals(identificacion)) {
				String strZonaValida = bi.getStrC1().substring(0, 1);
				if(bi.getStrC1().equals("0") || strZonaValida.equals("Z")){
					return false;	
				}
			}
		}
		return true;
	}
	
	/* MÉTODO QUE INGRESA MODIFICA USUARIO */
	@SuppressWarnings("deprecation")
	public void ingresaModificaUsuario() throws Exception{
		if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){
			String x,y,z,iD;
			x = txtUsu.getText().toUpperCase().trim();
			y = txtClave.getText().trim();
			z = txtConClave.getText().trim();
			iD = txtIdentificacion.getText().trim();
			if(!iD.equals("")) {
				if (!ZonaValidaVendedor(iD)) {
					Mensaje.mostrarAviso("Identificacion no tiene zina valida para vendedor.");
					txtIdentificacion.requestFocus();
				}
			}
			if (x.equals("") && y.equals("") && z.equals("") && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_01);					
				txtUsu.requestFocus();
			}else if (!(x.equals("")) && y.equals("") && z.equals("") && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_02);					
				txtClave.requestFocus();
			}else if (!(x.equals("")) && !(y.equals("")) && z.equals("") && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_03);					
				txtConClave.requestFocus();
			}else if (!(x.equals("")) && !(y.equals("")) && !(z.equals("")) && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_04);					
				txtIdentificacion.requestFocus();
			}else if (x.equals("") && !(y.equals("")) && z.equals("") && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_05);					
				txtUsu.requestFocus();		
			}else if (x.equals("") && !(y.equals("")) && !(z.equals("")) && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_06);					
				txtUsu.requestFocus();		
			}else if (x.equals("") && !(y.equals("")) && !(z.equals("")) && !(iD.equals(""))){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_07);					
				txtUsu.requestFocus();
			}else if (x.equals("") && y.equals("") && !(z.equals("")) && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_08);					
				txtUsu.requestFocus();
			}else if (x.equals("") && y.equals("") && !(z.equals("")) && !(iD.equals(""))){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_09);					
				txtUsu.requestFocus();
			}else if (x.equals("") && y.equals("") && z.equals("") && !(iD.equals(""))){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_10);					
				txtUsu.requestFocus();	
			}else if (!(x.equals("")) && y.equals("") && !(z.equals("")) && iD.equals("")){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_11);					
				txtUsu.requestFocus();
			}else if (!(x.equals("")) && y.equals("") && z.equals("") && !(iD.equals(""))){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_12);					
				txtClave.requestFocus();
			}else if (x.equals("") && !(y.equals("")) && z.equals("") && !(iD.equals(""))){
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_13);					
				txtUsu.requestFocus();		
			}else if (!(x.equals(""))  && !(y.equals("")) && !(z.equals("")) && !(iD.equals(""))){
				if((x.length()>20) && (!(y.equals(z)))){
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_14);
				}else if((x.length()>20) && y.equals(z)){
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_15);				
				}else if(x.length()<=20 && (!(y.equals(z)))){
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_USUARIO_16);				
				}else if(x.length()<=20 && y.equals(z)){					
					if(txtIdUsu.getText().trim().equals(Constante.VACIO)){						
						final DLocker bloqueador = new DLocker();
						Thread hilo = new Thread() {
							public void run() {
								objSAP = new SAdministracion();								
								List<String> retorno = new ArrayList<String>();
								Object obj;
								Boolean bol;
								int cont, ven, flag;
								ven = flag = cont = 0;					
								List <String> datos = new ArrayList<String>();
								Administrador a = new Administrador();
								String x,y,z,iD;
								x = txtUsu.getText().toUpperCase().trim();
								y = txtClave.getText().trim();
								z = txtConClave.getText().trim();
								iD = txtIdentificacion.getText().trim();
								for (int i=0;i<rolese.size();i++){								
									obj = modeloTabla.getValueAt(i, 1);
									if (obj instanceof Boolean) {
										bol = (Boolean) obj;
										if (bol == true){
											cont ++;
											datos.add(((BeanRol)rolese.get(i)).getStrIdRol());
											if(((BeanRol)rolese.get(i)).getStrIdRol().equals(Constante.ROL_VENDEDOR)){
												ven++;	
											}					                    			
										}                        					                    					                   
									}												
								}
								if (cont == 0){
									Mensaje.mostrarAviso(Constante.MENSAJE_ASIGNA_ROL_USUARIO);
								}else{								
									for(int i=0; i<identificacionese.size(); i++){    			
										if (((BeanIdentificacion)identificacionese.get(i)).getStrId().equals(txtIdentificacion.getText().trim())){
											if (!(((BeanIdentificacion)identificacionese.get(i)).getStrC2().equals("X")))
												flag++;
											break;
										}									
									}
									if (ven > 0 && flag > 0){
										Mensaje.mostrarAviso(Constante.MENSAJE_IDENTIFICACION_NO_VALIDA);
									}else{
										try {
											retorno = objSAP.ingresaModificaUsuario("", x, y, z, iD);
											if(retorno.get(1).equals(Constante.MENSAJE_ERROR_NOMBRE_USUARIO)){
												Mensaje.mostrarError(Constante.MENSAJE_ERROR_NOMBRE_USUARIO);
												txtUsu.requestFocus();
											}else if(retorno.get(1).equals(Constante.MENSAJE_ERROR_CODIGO_USUARIO)){
												Mensaje.mostrarError(Constante.MENSAJE_ERROR_CODIGO_USUARIO);
											}else if(retorno.get(1).equals(Constante.MENSAJE_ERROR_NOMBRE_CODIGO_USUARIO)){
												Mensaje.mostrarError(Constante.MENSAJE_ERROR_NOMBRE_CODIGO_USUARIO);
												txtUsu.requestFocus();
											}else{
												if(!retorno.get(0).equals(Constante.VACIO)){
													objSAP.asignaRolUsuario(datos, retorno.get(0));
													txtIdUsu.setText(retorno.get(0));
													btnGuardar.setEnabled(false);
													Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_NUEVO_USUARIO);
												}else{
													Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_NUEVO_USUARIO);
												}
												a.muestraVentanaAdministrador(2, Promesa.destokp, beanTareaProgramada);
											}											
										} catch (Exception e) {
											Util.mostrarExcepcion(e);
											Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_NUEVO_USUARIO);
										}																	
									}																
								}
								bloqueador.dispose();							
							}
						};	
						hilo.start();
						bloqueador.setVisible(true);																																																																
					}else{
						final DLocker bloqueador = new DLocker();
						Thread hilo = new Thread() {
							public void run() {
								objSAP = new SAdministracion();
								List<String> retorno = new ArrayList<String>();												
								Object obj;
								Boolean bol;
								int cont, ven, flag;
								ven = flag = cont = 0;					
								List <String> datos = new ArrayList<String>();
								Administrador a = new Administrador();
								String x,y,z,iD;
								x = txtUsu.getText().toUpperCase().trim();
								y = txtClave.getText().trim();
								z = txtConClave.getText().trim();
								iD = txtIdentificacion.getText().trim();
								for (int i=0;i<rolesusuarioe.size();i++){
									obj = modeloTabla.getValueAt(i, 1);
									if (obj instanceof Boolean) {
										bol = (Boolean) obj;
										if (bol == true){
											cont++;
											datos.add(((BeanRolUsuario)rolesusuarioe.get(i)).getStrIdRol());
											if(((BeanRolUsuario)rolesusuarioe.get(i)).getStrIdRol().equals(Constante.ROL_VENDEDOR)){
												ven++;	
											}
										}                   					                    					                   
									}												
								}
								if (cont == 0){
									Mensaje.mostrarAviso(Constante.MENSAJE_ASIGNA_ROL_USUARIO);
								}else{								
									for(int i=0;i<identificacionese.size();i++){    			
										if (((BeanIdentificacion)identificacionese.get(i)).getStrId().equals(txtIdentificacion.getText().trim())){
											if (!(((BeanIdentificacion)identificacionese.get(i)).getStrC2().equals("X")))
												flag++;
											break;
										}									
									}
									if (ven > 0 && flag > 0){
										Mensaje.mostrarAviso(Constante.MENSAJE_IDENTIFICACION_NO_VALIDA);
									}else{
										try {
											retorno = objSAP.ingresaModificaUsuario(txtIdUsu.getText().trim(), x, y, z, iD);											
											if(retorno.get(1).equals(Constante.MENSAJE_ERROR_NOMBRE_USUARIO)){
												Mensaje.mostrarError(Constante.MENSAJE_ERROR_NOMBRE_USUARIO);
												txtUsu.requestFocus();
											}else if(retorno.get(1).equals(Constante.MENSAJE_ERROR_CODIGO_USUARIO)){
												Mensaje.mostrarError(Constante.MENSAJE_ERROR_CODIGO_USUARIO);
											}else if(retorno.get(1).equals(Constante.MENSAJE_ERROR_NOMBRE_CODIGO_USUARIO)){
												Mensaje.mostrarError(Constante.MENSAJE_ERROR_NOMBRE_CODIGO_USUARIO);
												txtUsu.requestFocus();
											}else{												
												if(!retorno.get(0).equals(Constante.VACIO)){
													objSAP.asignaRolUsuario(datos, retorno.get(0));
													btnGuardar.setEnabled(false);
													Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_MODIFICA_USUARIO);
												}else{
													Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_MODIFICA_USUARIO);
												}									
												a.muestraVentanaAdministrador(2, Promesa.destokp, beanTareaProgramada);
											}											
										} catch (Exception e) {
											Util.mostrarExcepcion(e);
											Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_MODIFICA_USUARIO);
										}										
									}																
								}
								bloqueador.dispose();							
							}
						};	
						hilo.start();
						bloqueador.setVisible(true);												
					}	
				}
			}
		}else{
			if (txtIdUsu.getText().trim().equals(Constante.VACIO)){
				Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_NUEVO_USUARIO);				
			}else{
				Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_MODIFICA_USUARIO);
			}
		}			
	}
	
	// Begin
	private class IndexedFocusTraversalPolicy extends  FocusTraversalPolicy {

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
		tblRolMouseClicked(arg0);
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
		if(arg0.getSource() == btnRol){			
			/*LLAMA A PANTALLA ROL*/
			Administrador a = new Administrador();			
			try {
				a.muestraVentanaAdministrador(0, Promesa.destokp, beanTareaProgramada);				
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}		
		if(arg0.getSource() == btnVista){			
			/*LLAMA A PANTALLA VISTA*/
			Administrador a = new Administrador();			
			try {
				a.muestraVentanaAdministrador(1, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}		
		if(arg0.getSource() == btnUsuario){
			/*LLAMA A PANTALLA USUARIO*/
			Administrador a = new Administrador();			
			try {
				a.muestraVentanaAdministrador(2, Promesa.destokp, beanTareaProgramada);				
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}			
		}		
		if(arg0.getSource() == btnDispositivo){
			/*LLAMA A PANTALLA DISPOSITIVO*/
			Administrador a = new Administrador();			
			try {
				a.muestraVentanaAdministrador(6, Promesa.destokp, beanTareaProgramada);								
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}			
		}		
		if(arg0.getSource() == btnDesbloquear){
				desbloqueaUsuario();		
		}		
		if(arg0.getSource() == btnGuardar){
			try {
				ingresaModificaUsuario();
			} catch (Exception e) {
				Mensaje.mostrarError(e.getMessage());
				Util.mostrarExcepcion(e);
			}
		}		
	}	
	
	public void creaTablaOpciones(){
		modelOpciones = new ModeloTablaOpcion();
	    tablaOpciones = new JTable(modelOpciones);	    
	    tablaOpciones.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
	    tablaOpciones.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getLeftCell());
	    tablaOpciones.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getLeftCell());    
	    scrollerOpciones = new JScrollPane();
	    scrollerOpciones.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollerOpciones.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollerOpciones.setViewportView(tablaOpciones);	    
	    scrollerOpciones.setPreferredSize(new Dimension(420, 200));        
	    popupOpciones = new JPopupMenu();
	    popupOpciones.add(scrollerOpciones);	     
	    tablaOpciones.addMouseListener(new MouseAdapter() {
	      public void mousePressed(MouseEvent evt) { tablaTransportePesajeMousePressed(evt);  }
	      public void mouseClicked(MouseEvent evt) { tablaTransportePesajeMouseClic(evt);  }	
	    });	    
	}
	
    private void tablaTransportePesajeMousePressed(MouseEvent evt) {  }
    
    private void tablaTransportePesajeMouseClic(MouseEvent evt) {     
    	int fila = 0;     
    	fila = tablaOpciones.getSelectedRow();    	     
    	popupOpciones.setVisible(false);         	
    	txtIdentificacion.setText(String.valueOf(tablaOpciones.getValueAt(fila, 0)));
    	Identificacion.setText(String.valueOf(tablaOpciones.getValueAt(fila, 1)).toLowerCase());        
    }
    
    private void txtIdentificacionMouseClicked(java.awt.event.MouseEvent evt) {
    	llenarTablaOpciones();
    	SetPopupOpciones(evt,txtIdentificacion,0,21);
    }
    
    private void SetPopupOpciones(java.awt.event.MouseEvent evt,JTextField txtf,int x,int y){	  
    	txtf.add(popupOpciones);
    	popupOpciones.show(txtf,x,y);
    	txtf.requestFocus();
    	popupOpciones.setVisible(true);
    }
    
    private void llenarModeloOpciones() {
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				objSAP = new SAdministracion();
        			identificacionese = objSAP.identificacion();
        			quickSort(0, identificacionese.size() - 1);
    			} catch (Exception ex) {
    				ex.printStackTrace();
    				Mensaje.mostrarError(ex.getMessage());
	    			Util.mostrarExcepcion(ex);
    			}
    		}
    	});
    }
    
    
    private void llenarTablaOpciones(){
    	boolean AsignadoVendedor = (Boolean) modeloTabla.getValueAt(2, 1);
    	if((strRol != "" && strRol.equals("Vendedor") && strActivo) || AsignadoVendedor){
    		modelOpciones.limpiar();
    		SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				try{
    	    			quickSort(0, identificacionese.size() - 1);
    	    			for(int i=0;i<identificacionese.size();i++){
    	    				BeanIdentificacion bi = identificacionese.get(i);
    	    				String strZonaValida = bi.getStrC1().substring(0, 1);
    	    				if(!bi.getStrC1().equals("0") && !strZonaValida.equals("Z")){
    	    					modelOpciones.agregarOpciones(bi);
    	    				}
    	    			}
    	    			tablaOpciones.updateUI();
    	    			strRol = "";
    	    		}catch(Exception e){
    	    			Mensaje.mostrarError(e.getMessage());
    	    			Util.mostrarExcepcion(e);
    	        	}
    			}
    		});
    		
    	} else {
    		modelOpciones.limpiar();
    		SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				try{
    	    			quickSort(0, identificacionese.size() - 1);
    	    			for(int i=0;i<identificacionese.size();i++){
    	    				BeanIdentificacion bi = identificacionese.get(i);
    	    				modelOpciones.agregarOpciones(bi);
    	    			}
    	    			tablaOpciones.setModel(modelOpciones);
    	    			tablaOpciones.updateUI();
    	    		}catch(Exception e){
    	    			Mensaje.mostrarError(e.getMessage());
    	    			Util.mostrarExcepcion(e);
    	        	}
    			}
    		});
    	}	 
	}
    
    private void quickSort(int left, int right){
    	int index = 0;
    	index = partition(left, right);
		if (left < index - 1)
			quickSort(left, index - 1);
		if (index < right)
			quickSort(index, right);
    }
    
    private int partition(int left, int right){
	    int i = left, j = right;
	    int pivot = (left + right) / 2;
	    BeanIdentificacion biPivot = identificacionese.get(pivot);
	    String strNombrePivot = biPivot.getStrNombre();
	    BeanIdentificacion bitmp = null;
	    while (i <= j) {
	    	while ((((identificacionese.get(i)).getStrNombre()).compareTo(strNombrePivot) < 0 ))
			   	i++;
			while ((((identificacionese.get(j)).getStrNombre()).compareTo(strNombrePivot) > 0 ))
			   	j--;
	    	if (i <= j) {
	    		bitmp = identificacionese.get(i);
	    		identificacionese.set(i, identificacionese.get(j));
	    		identificacionese.set(j, bitmp);
	    		i++;
	    		j--;
	    	}
	    }; 
	    return i;
	}
    
    
    protected void tblRolMouseClicked(MouseEvent evt) {
		iFilaRol = tblRol.rowAtPoint(evt.getPoint());
		if(iFilaRol > -1){
			strRol = modeloTabla.getValueAt(iFilaRol, 0).toString();
			strActivo = (Boolean) modeloTabla.getValueAt(iFilaRol, 1);
		}
	}
    
    public String traeDato(String ID){    	
    	try{
			objSAP = new SAdministracion();
			identificacionese = objSAP.identificacion();    		
			for(int i=0;i<identificacionese.size();i++){
				if (((BeanIdentificacion)identificacionese.get(i)).getStrId().equals(ID)){
					return ((BeanIdentificacion)identificacionese.get(i)).getStrNombre().toLowerCase();					
				}				 	              
			}   	    
		}catch(Exception e){
			Mensaje.mostrarError(e.getMessage());
			Util.mostrarExcepcion(e);
    	}   	
		return "";		
    }
}