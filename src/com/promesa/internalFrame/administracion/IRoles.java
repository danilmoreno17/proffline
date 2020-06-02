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
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.promesa.administracion.sql.SqlRol;
import com.promesa.administracion.sql.impl.SqlRolImpll;
import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.main.Promesa;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Renderer;
import com.promesa.util.Util;
import com.promesa.sap.*;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.internalFrame.*;

@SuppressWarnings("serial")
public class IRoles extends JInternalFrame implements ActionListener, MouseListener, KeyListener{

	JToolBar mnuToolBar;
	JButton  btnRol;
	JButton  btnVista;
	JButton  btnUsuario;	
	JButton  btnDispositivo;
	JButton  btnNuevo;
	JButton  btnBorrar;
	JSeparator separador;
	private JTable tblRol;
	DefaultTableModel  modeloTabla;
	JScrollPane scrRol;
	JPanel pnlRol;	
	SAdministracion objSAP;
	static List<BeanDato> datose;
	private List<BeanRol> lista;	  
	private SqlRol getRol;
	private BeanRol rol;
	List<BeanRol> rolese;
	private BeanTareaProgramada beanTareaProgramada;
	
	@SuppressWarnings("static-access")
	public IRoles(BeanTareaProgramada beanTareaProgramada){
		
		super("Roles",true,true,true,true);
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iroles.png")));
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
		
		this.datose = this.beanTareaProgramada.getDatose();
		getRol = new SqlRolImpll();

		btnRol= new JButton();
		mnuToolBar.add(btnRol);
		btnRol.setText("Roles");
		btnRol.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/roles.png")));
		btnRol.addActionListener(this);
		btnRol.setToolTipText("Roles");
		btnRol.setPreferredSize(new java.awt.Dimension(93, 29));
		btnRol.setBackground(new java.awt.Color(0,128,64));
		btnRol.setEnabled(false);
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
				
		pnlRol = new JPanel();
		getContentPane().add(pnlRol);
		pnlRol.setBounds(0, 48, 790, 273); // 0, 48, 1119, 273
		pnlRol.setLayout(null);
		pnlRol.setBorder(BorderFactory.createTitledBorder("Lista de Roles"));
		
		{
			btnNuevo = new JButton();			
			pnlRol.add(btnNuevo);
			btnNuevo.setText("Nuevo");
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/nuevo.png")));			
			btnNuevo.addActionListener(this);
			btnNuevo.setToolTipText("Nuevo");			
			btnNuevo.setBackground(new java.awt.Color(0,128,64));
			btnNuevo.setBounds(12, 30, 100, 20); // horizontal, vertical, width, high
			btnNuevo.setOpaque(false);
			btnNuevo.setFocusable(false);
		}
		
		{
			btnBorrar = new JButton();			
			pnlRol.add(btnBorrar);
			btnBorrar.setText("Borrar");
			btnBorrar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/borrar.png")));			
			btnBorrar.addActionListener(this);
			btnBorrar.setToolTipText("Borrar");			
			btnBorrar.setBackground(new java.awt.Color(0,128,64));
			btnBorrar.setBounds(112, 30, 100, 20); // horizontal, vertical, width, high
			btnBorrar.setOpaque(false);
			btnBorrar.setFocusable(false);
		}
	
		scrRol = new JScrollPane();
		pnlRol.add(scrRol);
		scrRol.setBounds(50, 65, 680, 180); // 150, 65, 800, 180
		scrRol.setToolTipText("Relación de Roles");

		buscaRol();	
			
	}
	
	public void buscaRol(){
		if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){					
			try {
				objSAP = new SAdministracion();
				rolese = objSAP.listaRoles();					
			} catch (Exception e) {
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_LISTA_ROL);
				Util.mostrarExcepcion(e);
			}							
		}else{
			rol = new BeanRol();
			getRol.setListaRol(rol);  
			lista = getRol.getListaRol();
			BeanRol beanRol = null;
			rolese = new ArrayList<BeanRol>();
			for(BeanRol r : lista){
				beanRol = new BeanRol();
				beanRol.setStrMandante(r.getStrMandante());
				beanRol.setStrIdRol(r.getStrIdRol());				
				beanRol.setStrNomRol(r.getStrNomRol());
				rolese.add(beanRol);				
			}
		}
		if (rolese!=null  && rolese.size()>0){
			tablaLlena();
		}else{
			tablaVacia();
		}
	}
	
	public void tablaVacia(){
		String Columnas[] = {"ID","Nombre"};
		modeloTabla= new DefaultTableModel(null,Columnas);
		TableModel tblTablaModel = new DefaultTableModel (new Object[][] { { "", "" }, { "", "" }, { "", "" }, {"", "" }, {"", "" }, {"", "" }, {"", "" }, {"", "" }, {"", "" } }, Columnas){
        	private static final long serialVersionUID = 1L;
        		@Override
        		public boolean isCellEditable(int row, int column) {
        			return false;
			}};
		tblRol = new JTable();
		tblRol.setDefaultRenderer(Object.class, new Renderer());
		tblRol.setModel(tblTablaModel);
		tblRol.addKeyListener(this);				
		new Renderer().setSizeColumn(tblRol.getColumnModel(),0,400);
		new Renderer().setSizeColumn(tblRol.getColumnModel(),1,400);					
		scrRol.setViewportView(tblRol);			
		tblRol.addMouseListener(this);
	}
	
	public void tablaLlena(){
		String Columnas[] = {"ID","Nombre"};
		modeloTabla= new DefaultTableModel(null,Columnas){
			private static final long serialVersionUID = 1L;
			@Override
				public boolean isCellEditable(int row, int column) {
					return false;
			}};
		if(rolese.size()<= 9){modeloTabla.setNumRows(9);}else{modeloTabla.setNumRows(rolese.size());}				
		for (int i=0; i<rolese.size(); i++){				
			for(int j=1; j<3; j++){
				if (j == 1)
					modeloTabla.setValueAt(((BeanRol)rolese.get(i)).getStrIdRol(), i, j-1);
				if (j == 2)
					modeloTabla.setValueAt(((BeanRol)rolese.get(i)).getStrNomRol(), i, j-1);					
			}
		}
		tblRol = new JTable();
		tblRol.setDefaultRenderer(Object.class, new Renderer());
		tblRol.setModel(modeloTabla);
		tblRol.addKeyListener(this);
		tblRol.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getLeftCell());
		tblRol.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getLeftCell());		
		new Renderer().setSizeColumn(tblRol.getColumnModel(),0,400);
		new Renderer().setSizeColumn(tblRol.getColumnModel(),1,400);					
		scrRol.setViewportView(tblRol);			
		tblRol.addMouseListener(this);
	}		
	
	/* MÉTODO QUE BORRA UN ROL */
	public void borraRol(){
		if (modeloTabla.getRowCount() > 0) {
			try {						
				if (tblRol.getSelectedRow()>-1){
					if(Mensaje.preguntar(Constante.MENSAJE_PREGUNTA_ELIMINA_ROL)){					
						if (((BeanDato)datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)){
							final DLocker bloqueador = new DLocker();
							Thread hilo = new Thread() {
								public void run() {						
									objSAP = new SAdministracion();								
									int fila = tblRol.getSelectedRow();
									try {
										if(objSAP.eliminaRol(tblRol.getValueAt(fila,0).toString())){
											DefaultTableModel dtm = (DefaultTableModel) tblRol.getModel();
											dtm.removeRow(fila);
											if(rolese.size() <= 9){dtm.insertRow(8,new Object[] {"",""});}else{dtm.insertRow(rolese.size()-1,new Object[] {"",""});}									
										}else{
											Mensaje.mostrarError(Constante.MENSAJE_ERROR_ELIMINA_ROL);
										}
									} catch (Exception e) {										
										Util.mostrarExcepcion(e);
									}
									bloqueador.dispose();							
								}
							};	
							hilo.start();
							bloqueador.setVisible(true);														
						}else{
							Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_ELIMINA_ROL);											
						}										
					}
				}else{
					Mensaje.mostrarError(Constante.MENSAJE_SELECCION_ELIMINA_ROL);
				}
			} catch (Exception e) {
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_ELIMINA_ROL);
				Util.mostrarExcepcion(e);
			}
		}else{
			Mensaje.mostrarAviso(Constante.MENSAJE_NO_ROL_ELIMINA);
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

	/* MÉTODO QUE TRANSFIERE DATOS A LA PANTALLA NUEVO ROL */
	@Override
	public void mouseClicked(MouseEvent arg0) {		
		Administrador a = new Administrador();		
		if (arg0.getClickCount() == 2) {
			int fila = tblRol.rowAtPoint(arg0.getPoint());	        
				if ((fila > -1)){						        			        		
					try {
						a.ventanaAdministradorPasaDatosRoles(0, Promesa.destokp,tblRol.getValueAt(fila, 0).toString(),tblRol.getValueAt(fila, 1).toString(), beanTareaProgramada);	        					
        			} catch (PropertyVetoException e1) {
        				Mensaje.mostrarError(Constante.MENSAJE_ERROR_TRANSFERENCIA_ROL);
        				Util.mostrarExcepcion(e1);
    				}        		 
	        	}else{
	        		Mensaje.mostrarAviso(Constante.MENSAJE_NO_ROLES);
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

	@Override
	public void actionPerformed(ActionEvent arg0) {		
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
		if(arg0.getSource() == btnNuevo){
			/*LLAMA A PANTALLA NUEVO ROL*/
			Administrador a = new Administrador();			
			try {
				a.muestraVentanaAdministrador(4, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}			
		}		
		if(arg0.getSource() == btnBorrar){
			/*FUNCIONALIDAD BORRAR*/
			borraRol();			
		}		
	}
}