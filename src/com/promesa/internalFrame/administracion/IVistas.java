package com.promesa.internalFrame.administracion;

import java.awt.BorderLayout;
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
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.promesa.administracion.sql.SqlVista;
import com.promesa.administracion.sql.impl.SqlVistaImpl;
import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.internalFrame.*;
import com.promesa.main.Promesa;
import com.promesa.util.Constante;
import com.promesa.util.Renderer;
import com.promesa.util.Util;
import com.promesa.sap.*;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;

@SuppressWarnings("serial")
public class IVistas extends JInternalFrame implements ActionListener, MouseListener, KeyListener{

	JToolBar mnuToolBar;
	JButton  btnRol;
	JButton  btnVista;
	JButton  btnUsuario;
	JButton  btnDispositivo;
	JButton  btnAsignar;
	JSeparator separador;
	private JTable tblVista;
	DefaultTableModel  modeloTabla;
	TableModel tblTablaModel;
	JScrollPane scrVista;
	JPanel pnlVista;
	SAdministracion objSAP;	
	static List<BeanDato> datose;
	List<BeanVista> vistase;
	private BeanVista vista = null;
	private SqlVista getVista = null;
	private List<BeanVista> listaVista =null;
	private BeanTareaProgramada beanTareaProgramada;
	
	@SuppressWarnings("static-access")
	public IVistas(BeanTareaProgramada beanTareaProgramada){
		
		super("Vistas",true,true,true,true);
		this.beanTareaProgramada =  beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/ivistas.gif")));
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
		vista = new BeanVista();
		getVista = new SqlVistaImpl();
		
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
		btnRol.setFocusable(false);

		btnVista= new JButton();
		mnuToolBar.add(btnVista);
		btnVista.setText("Vistas");
		btnVista.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/vistas.gif")));	
		btnVista.addActionListener(this);
		btnVista.setToolTipText("Vistas");
		btnVista.setPreferredSize(new java.awt.Dimension(100, 29));
		btnVista.setBackground(new java.awt.Color(0,128,64));
		btnVista.setEnabled(false);
		btnVista.setOpaque(false);
		btnVista.setBorder(null);
		btnVista.setFocusable(false);
		
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
		btnUsuario.setFocusable(false);
		
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
		btnDispositivo.setFocusable(false);

		pnlVista = new JPanel();
		getContentPane().add(pnlVista);
		pnlVista.setBounds(0, 48, 790, 273); // 0, 48, 1119, 273
		pnlVista.setLayout(null);
		pnlVista.setBorder(BorderFactory.createTitledBorder("Lista de Vistas"));
		
		{
			btnAsignar = new JButton();			
			pnlVista.add(btnAsignar);
			btnAsignar.setText("Asignar vista a rol");
			btnAsignar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/asignar.gif")));			
			btnAsignar.addActionListener(this);
			btnAsignar.setToolTipText("Asignar vista a rol");			
			btnAsignar.setBackground(new java.awt.Color(0,128,64));
			btnAsignar.setBounds(12, 30, 160, 20); // horizontal, vertical, width, high
			btnAsignar.setOpaque(false);
			btnAsignar.setFocusable(false);
		}
		
		scrVista = new JScrollPane();
		pnlVista.add(scrVista);
		scrVista.setBounds(50, 65, 680, 180); // 150, 65, 800, 180
		scrVista.setToolTipText("Relación de Vistas");
		
		buscaVista();
			
	}
	
	public void buscaVista(){
		if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){				
			try {
				objSAP = new SAdministracion();
				vistase = objSAP.listaVistas("");						
			} catch (Exception e) {
				Util.mostrarExcepcion(e);		        
			}			
		}else{			
			getVista.setListaVista(vista);
			listaVista=getVista.getListaVista();			
			BeanVista beanVista = null;			
			vistase = new ArrayList<BeanVista>();
			for(BeanVista vista : listaVista){
				beanVista = new BeanVista();				
				beanVista.setStrNomVis(vista.getStrNomVis());
				beanVista.setStrDesVis(vista.getStrDesVis());
				vistase.add(beanVista);				
			}			
		}
		if (vistase!=null  && vistase.size()>0){
			tablaLlena();			
		}else{
			tablaVacia();
		}
	}
	
	public void tablaVacia(){
		String Columnas[] = {"Nombre","Descripción"};
		modeloTabla= new DefaultTableModel(null,Columnas);
		TableModel tblTablaModel = new DefaultTableModel (new Object[][] { { "", "" }, { "", "" }, { "", "" }, {"", "" }, {"", "" }, {"", "" }, {"", "" }, {"", "" }, {"", "" } }, Columnas){
			private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
			}};
		tblVista = new JTable();		
		tblVista.setModel(tblTablaModel);		
		tblVista.addMouseListener(this);
		tblVista.addKeyListener(this);
		tblVista.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getLeftCell());
		tblVista.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getLeftCell());		
		scrVista.setViewportView(tblVista);
		pnlVista.add(scrVista);			
		new Renderer().setSizeColumn(tblVista.getColumnModel(),0,340);
		new Renderer().setSizeColumn(tblVista.getColumnModel(),1,340);
	}
	
	public void tablaLlena(){
		String Columnas[] = {"Nombre","Descripción"};
		modeloTabla= new DefaultTableModel(null,Columnas){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}};
		modeloTabla.setNumRows(vistase.size());			
		for (int i=0; i<vistase.size(); i++){				
			for(int j=0; j<1; j++){
				modeloTabla.setValueAt(((BeanVista)vistase.get(i)).getStrNomVis(), i, j);
				modeloTabla.setValueAt(((BeanVista)vistase.get(i)).getStrDesVis(), i, j+1);			
			}
		}
		tblVista = new JTable();		
		tblVista.setModel(modeloTabla);		
		tblVista.addMouseListener(this);
		tblVista.addKeyListener(this);
		tblVista.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getLeftCell());
		tblVista.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getLeftCell());		
		scrVista.setViewportView(tblVista);
		pnlVista.add(scrVista);			
		new Renderer().setSizeColumn(tblVista.getColumnModel(),0,340);
		new Renderer().setSizeColumn(tblVista.getColumnModel(),1,340);
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
		if(arg0.getSource() == btnRol){			
			/*LLAMA A PANTALLA ROL*/
			Administrador a = new Administrador();			
			try {
				a.muestraVentanaAdministrador(0, Promesa.destokp, beanTareaProgramada);				
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
		if(arg0.getSource() == btnAsignar){
			/*LLAMA A PANTALLA ASIGNAR ROL A VISTA*/
			Administrador a = new Administrador();			
			try {
				a.muestraVentanaAdministrador(3, Promesa.destokp, beanTareaProgramada);								
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}			
		}		
	}

}