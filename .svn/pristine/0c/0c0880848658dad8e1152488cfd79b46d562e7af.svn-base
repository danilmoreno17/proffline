package com.promesa.internalFrame.sincronizacion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.promesa.util.*;
import com.promesa.sap.*;
import com.promesa.administracion.sql.SqlDispositivo;
import com.promesa.administracion.sql.SqlRol;
import com.promesa.administracion.sql.SqlSincronizacion;
import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.sql.SqlUsuarioRol;
import com.promesa.administracion.sql.SqlVista;
import com.promesa.administracion.sql.SqlVistaRol;
import com.promesa.administracion.sql.impl.SqlDispositivoImpl;
import com.promesa.administracion.sql.impl.SqlRolImpll;
import com.promesa.administracion.sql.impl.SqlSincronizacionImpl;
import com.promesa.administracion.sql.impl.SqlUsuarioImpl;
import com.promesa.administracion.sql.impl.SqlUsuarioRolImpl;
import com.promesa.administracion.sql.impl.SqlVistaImpl;
import com.promesa.administracion.sql.impl.SqlVistaRolImpl;
import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.sincronizacion.bean.*;
import com.promesa.sincronizacion.impl.TiempoTrabajo;

@SuppressWarnings("serial")
public class ISincronizacionAdministracion extends JInternalFrame implements ActionListener, MouseListener, KeyListener{

	JToolBar mnuToolBar;
	JButton  btnSincronizar;
	JButton  btnGuardar;
	private JLabel lblGuardar;
	JSeparator separador;
	private JTable tblMan;
	DefaultTableModel  modeloTabla;
	JScrollPane scrMan;
	JScrollPane scrAut;
	JPanel pnlMan;
	JPanel pnlAut;	
	SAdministracion objSAP;
	static List<BeanDato> datose;
	SqlSincronizacionImpl objSI;
	List <BeanSincronizacion> since;	
	private JTextField txtHora;
	private JTextField txtMinuto;
	private JTextField txtSegundo;
	private JTextField txtMeridiano;
	private JTextField txtFrecuencia;
	JButton  btnAdelante;
	JButton  btnAtras;	
	private JLabel lblHorIni;
	@SuppressWarnings("unused")
	private JTextField txtHorIni;
	private JLabel lblFec;
	@SuppressWarnings("unused")
	private JTextField txtFec;
	int flagHora=-1;
    int flagMinuto=-1;
    int flagSegundo=-1;
    int flagMeridiano=-1;
    DetalleSincronizacion detalleSincronizacion = null;
    private String tarea;
    private int filaTabla;
	@SuppressWarnings("unused")
	private SAdministracion sapAdministracion =  null;	
	BeanTareaProgramada beanTareaProgramada = null;	
	
	@SuppressWarnings("static-access")
	public ISincronizacionAdministracion(BeanTareaProgramada beanTareaProgramada){
		
		super("Sincronizaciones",true,true,true,true);	
		sapAdministracion =  new SAdministracion();
		detalleSincronizacion = new DetalleSincronizacion();
		this.beanTareaProgramada = beanTareaProgramada;
		List<BeanDato> datose = beanTareaProgramada.getDatose();
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/isincronizaciones.png")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar,BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 790, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 790, 10); // 0, 39, 1318, 10
		
		this.datose = datose;

		btnSincronizar = new JButton();
		mnuToolBar.add(btnSincronizar);
		btnSincronizar.setText("Sincronizar");
		btnSincronizar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/sincronizaciones.png")));
		btnSincronizar.addActionListener(this);
		btnSincronizar.setToolTipText("Sincronizar");
		btnSincronizar.setPreferredSize(new java.awt.Dimension(93, 29));
		btnSincronizar.setBackground(new java.awt.Color(0,128,64));
		btnSincronizar.setOpaque(false);
		btnSincronizar.setBorder(null);
		
		btnSincronizar.setEnabled(false);
		
		pnlMan = new JPanel();
		getContentPane().add(pnlMan);
		pnlMan.setBounds(0,48,790,160); // 0, 48, 1119, 273
		pnlMan.setLayout(null);
		pnlMan.setBorder(BorderFactory.createTitledBorder("Sincronizaciones"));
		
		scrMan = new JScrollPane();		
		scrMan.setBounds(25, 30, 740, 105); // 150, 65, 800, 180
		scrMan.setToolTipText("Sincronización Manual");					
				
		pnlAut = new JPanel();
		getContentPane().add(pnlAut);
		pnlAut.setBounds(0,220,790,160); // 0, 48, 1119, 273
		pnlAut.setLayout(null);
		pnlAut.setBorder(BorderFactory.createTitledBorder("Configuraciones"));
		pnlAut.setFocusable(false);
		{
			btnGuardar = new JButton();			
			pnlAut.add(btnGuardar);
			btnGuardar.setText("");
			btnGuardar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.jpg")));			
			btnGuardar.addActionListener(this);
			btnGuardar.setToolTipText("Guardar");			
			btnGuardar.setBackground(new java.awt.Color(0,128,64));
			btnGuardar.setBounds(12, 30, 28, 20); // horizontal, vertical, width, high
			btnGuardar.setOpaque(false);
		}
		{
			lblGuardar = new JLabel();
			pnlAut.add(lblGuardar);
			lblGuardar.setText("GUARDAR");
			lblGuardar.setBounds(45, 32, 125, 17);
			lblGuardar.setFont(new java.awt.Font("Arial",1,10));
		}
		{
			lblHorIni = new JLabel();
			pnlAut.add(lblHorIni);
			lblHorIni.setText("Hora:");
			lblHorIni.setBounds(25, 60, 40, 21);
			lblHorIni.setFont(new java.awt.Font("Arial",1,11));			
		}
		txtHora = new JTextField();
		pnlAut.add(txtHora);
		txtHora.setBounds(82, 58, 25, 25);	
		txtHora.setEditable(false);
		txtHora.setHorizontalAlignment(SwingConstants.CENTER);
		txtHora.setFont(new java.awt.Font("Arial",0,10));
		txtHora.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtHora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	txtHoraMouseClicked(evt);
            }
        });
		
		txtMinuto = new JTextField();
		pnlAut.add(txtMinuto);
		txtMinuto.setBounds(109, 58, 25, 25);
		txtMinuto.setEditable(false);
		txtMinuto.setHorizontalAlignment(SwingConstants.CENTER);
		txtMinuto.setFont(new java.awt.Font("Arial",0,10));
		txtMinuto.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtMinuto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	txtMinutoMouseClicked(evt);
            }
        });
		
		txtSegundo = new JTextField();
		pnlAut.add(txtSegundo);
		txtSegundo.setBounds(136, 58, 25, 25);	
		txtSegundo.setEditable(false);
		txtSegundo.setHorizontalAlignment(SwingConstants.CENTER);
		txtSegundo.setFont(new java.awt.Font("Arial",0,10));
		txtSegundo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtSegundo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	txtSegundoMouseClicked(evt);
            }
        });
		txtMeridiano = new JTextField();
		pnlAut.add(txtMeridiano);
		txtMeridiano.setBounds(163, 58, 25, 25);	
		txtMeridiano.setEditable(false);
		txtMeridiano.setHorizontalAlignment(SwingConstants.CENTER);
		txtMeridiano.setFont(new java.awt.Font("Arial",0,10));
		txtMeridiano.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtMeridiano.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	txtMeridianoMouseClicked(evt);
            }
        });
		btnAtras = new JButton("<<");
		pnlAut.add(btnAtras);
		btnAtras.setBounds(190, 59, 46, 25);
		btnAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	atrasMouseClicked(evt);
            }
        });
		btnAdelante = new JButton(">>");
		pnlAut.add(btnAdelante);
		btnAdelante.setBounds(238, 59, 46, 25);
		btnAdelante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	adelanteMouseClicked(evt);
            }
        });
		{ 
			lblFec = new JLabel();
			pnlAut.add(lblFec);
			lblFec.setText("Frecuencia:");
			lblFec.setBounds(25, 100, 80, 21);
			lblFec.setFont(new java.awt.Font("Arial",1,11));			
		}
		txtFrecuencia = new JTextField();
		pnlAut.add(txtFrecuencia);
		txtFrecuencia.setBounds(102, 98, 30, 25);		
		txtFrecuencia.setHorizontalAlignment(SwingConstants.CENTER);
		txtFrecuencia.setFont(new java.awt.Font("Arial",0,10));
		txtFrecuencia.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtFrecuencia.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtFrecuenciaKeyTyped(evt);
			}
		});
		{			
			modeloTabla= new DefaultTableModel(){
				public boolean isCellEditable(int row,int column){
					if(column ==6){return true;}
					else {return false;}
				}				
		};	
			
			modeloTabla.addColumn("Información");
			modeloTabla.addColumn("Nro. Registros");
			modeloTabla.addColumn("Última Sincronización");
			modeloTabla.addColumn("Tiempo (segundos)");
			modeloTabla.addColumn("Hora Inicio");
			modeloTabla.addColumn("Frecuencia");			
	    	
			tblMan = new JTable(modeloTabla);				
			tblMan.addKeyListener(this);
			tblMan.addMouseListener(this);			
			tblMan.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
			tblMan.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getRightCell());
			tblMan.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getRightCell());
			tblMan.getColumnModel().getColumn(3).setCellRenderer(new Renderer().getRightCell());	
			tblMan.getColumnModel().getColumn(4).setCellRenderer(new Renderer().getRightCell());
			tblMan.getColumnModel().getColumn(5).setCellRenderer(new Renderer().getRightCell());
			new Renderer().setSizeColumn(tblMan.getColumnModel(),0,100);
			new Renderer().setSizeColumn(tblMan.getColumnModel(),1,90);
			new Renderer().setSizeColumn(tblMan.getColumnModel(),2,140);
			new Renderer().setSizeColumn(tblMan.getColumnModel(),3,130);				
			scrMan.setViewportView(tblMan);
			pnlMan.add(scrMan);	
			llenaTabla();	
		}		
		btnAdelante.setEnabled(false);
		btnAtras.setEnabled(false);
		btnGuardar.setEnabled(false);
	}
	
	private void txtFrecuenciaKeyTyped(java.awt.event.KeyEvent evt){
		char caracter = evt.getKeyChar();	  
	 	if(txtFrecuencia.getText().trim().length()<2 && ((int)caracter==8 || ((int)caracter>=48 && (int)caracter<=57))){		 
	 		btnGuardar.setEnabled(true); 
	 		if(txtFrecuencia.getText().trim().length()==1 && (int)caracter==8){
	 			btnGuardar.setEnabled(false);
	 		}
	 	}else if(txtFrecuencia.getText().trim().length()==2 && (int)caracter==8){
	 	}else{
		 evt.consume();
	 	}		
	}
	
    private void txtHoraMouseClicked(java.awt.event.MouseEvent evt) {
    	if(txtMeridiano.getText().trim().length()>0){
        	flagHora=1;
            flagMinuto=-1;
            flagSegundo=-1;
            flagMeridiano=-1;	
            btnGuardar.setEnabled(true);			
    		btnAdelante.setEnabled(true);
    		btnAtras.setEnabled(true);         
        	txtHora.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        	txtMinuto.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));   
        	txtSegundo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)); 
        	txtMeridiano.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    	} 
    }
    
    private void txtMinutoMouseClicked(java.awt.event.MouseEvent evt) {
    	if(txtMeridiano.getText().trim().length()>0){
    		flagHora=-1;
            flagMinuto=1;
            flagSegundo=-1;
            flagMeridiano=-1;	
            btnGuardar.setEnabled(true);
    		btnAdelante.setEnabled(true);
    		btnAtras.setEnabled(true);           
        	txtHora.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	txtMinuto.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));   
        	txtSegundo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)); 
        	txtMeridiano.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)); 
    	}  
    }
    
    private void txtSegundoMouseClicked(java.awt.event.MouseEvent evt) {
    	if(txtMeridiano.getText().trim().length()>0){
        	flagHora=-1;
            flagMinuto=-1;
            flagSegundo=1;
            flagMeridiano=-1;
            btnGuardar.setEnabled(true);	
    		btnAdelante.setEnabled(true);
    		btnAtras.setEnabled(true);           
        	txtHora.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	txtMinuto.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));   
        	txtSegundo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)); 
        	txtMeridiano.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)); 
    	}  
    }
    
    private void txtMeridianoMouseClicked(java.awt.event.MouseEvent evt) {
    	if(txtMeridiano.getText().trim().length()>0){
        	flagHora=-1;
            flagMinuto=-1;
            flagSegundo=-1;
            flagMeridiano=1;	
            btnGuardar.setEnabled(true);		
    		btnAdelante.setEnabled(true);
    		btnAtras.setEnabled(true);           
        	txtHora.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	txtMinuto.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));   
        	txtSegundo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)); 
        	txtMeridiano.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));   
    	} 
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
	
	/* MÉTODO QUE LLENA TABLA */
	public void llenaTabla(){		
		objSI = null;
		objSI = new SqlSincronizacionImpl();
		since = objSI.listaSincronizacion();	
		int i=0;		
		txtHora.setText("");
		txtHora.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));		
		txtMinuto.setText("");
		txtMinuto.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));		
		txtSegundo.setText("");
		txtSegundo.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));		
		txtMeridiano.setText("");
		txtMeridiano.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));		
		txtFrecuencia.setText("");
		txtFrecuencia.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)); 
		txtFrecuencia.setEditable(false);		
		btnAdelante.setEnabled(false);
		btnAtras.setEnabled(false);
		btnGuardar.setEnabled(false);		
        flagHora=-1;
        flagMinuto=-1;
        flagSegundo=-1;
        flagMeridiano=-1;
    	while(modeloTabla.getRowCount()>0){
    		modeloTabla.removeRow(0);
    	}		
    	for(BeanSincronizacion s : since){    		
    		modeloTabla.addRow(new Object[]{s.getStrInfSinc(), s.getStrNumCantReg(), s.getStrFecHor(), s.getStrNumTie(), s.getStrHoraIni(), s.getStrFrecuencia()});  		
    		i++;
    	}	
	}
	
	// synchronize
	@SuppressWarnings({ "unused" })
	public void sincronizacionManual(){	
		Cmd objC;		
		if (((BeanDato)datose.get(0)).getStrModo().equals("1")){
			Object obj;
			Boolean bol;
			SqlSincronizacionImpl objSI;			
			int u,r,ur,v,rv,d;
			u = r = ur = v = rv = d = 0;		
			int z;
			z = 0;			
			for (int i=0;i<since.size();i++){
				obj = modeloTabla.getValueAt(i, 6);
					if (obj instanceof Boolean) {
						bol = (Boolean) obj;
	                    	if (bol == true){
	                    		String strRoles = modeloTabla.getValueAt(i, 0).toString();
	                    		if (strRoles.equals(USUARIOS)){
	                    			u = 1;
	                    			z++;
	                    		}else if (strRoles.equals(ROLES)){
                					r = 1;
	                    			z++;
                				}else if (strRoles.equals(USUARIO_ROLES)){
                					ur = 1;	                    			
	                    			z++;
                				}else if (strRoles.equals(VISTAS)){
                					v = 1;                						                    			
	                    			z++;
                				}else if (strRoles.equals(ROLES_VISTAS)){	                    			
	                    			rv = 1;
	                    			z++;
	                    		}else if (strRoles.equals(DISPOSITIVOS)){	                    			
	                    			d = 1;
	                    			z++;
	                    		}                               			                    		
                    		}	                    			                                            					                    
					}									
			}
			if (z==0){
				Mensaje.mostrarAviso("Ninguna opción seleccionada a sincronizar.");
			}else{
				objC = new Cmd();				
				if (u == 1){
        			sincronizar_DOWN_Usuarios();        			        			
				}
				if (r == 1){
        			sincronizar_DOWN_Roles();
				}
				if (ur == 1){
        			sincronizar_DOWN_Usuarios_Roles();
				}
				if (v == 1 && rv == 0){
					Mensaje.mostrarAviso("La sincronización de las Vistas tiene que ir acompañado de Roles Vistas.");					
				}
				if (v == 0 && rv == 1){
					Mensaje.mostrarAviso("La sincronización de Roles Vistas tiene que ir acompañado de Vistas.");					
				}
				if (v == 1 && rv == 1){					
        			sincronizar_DOWN_Vistas();        			
        			sincronizar_DOWN_Roles_Vistas();
				}
				if (d == 1){
        			sincronizar_DOWN_Dispositivos();        			
				}			
				llenaTabla();				
				if (u == 1 || r == 1 || ur == 1 || (v == 1 && rv == 1) || d == 1)
					Mensaje.mostrarAviso("Sincronización exitosa.");
			}				
		}else{
			Mensaje.mostrarAviso("La sincronización solo aplica en modo ONLINE.");
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == btnSincronizar){
			sincronizacionManual();
		}		
		if(e.getSource() == btnGuardar){		
			guardarHoraFrecuenciaSincronizacion();
		}		
	}
	
	public void guardarHoraFrecuenciaSincronizacion(){	
		Date fechaHoraSistema= new Date();
		Date HoraProgramada = null;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a"); 
	    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
	    String fechaHoraTemp=dateFormat2.format(fechaHoraSistema);	    	    
		detalleSincronizacion.setNumFec(txtFrecuencia.getText().trim());
		detalleSincronizacion.setTxtHorIni(txtHora.getText().trim()+":"+txtMinuto.getText().trim()+":"+txtSegundo.getText().trim()+" "+txtMeridiano.getText().trim());			
		String fechaHoraActual=fechaHoraTemp + " " + detalleSincronizacion.getTxtHorIni();	
	    try{
	    	HoraProgramada = new Date(dateFormat.parse(fechaHoraActual).getTime());				      
		   }catch(Exception exec){Util.mostrarExcepcion(exec);}
		
		try{
			HoraProgramada = new Date(dateFormat.parse(fechaHoraActual).getTime());	
			SqlSincronizacion getSincronizacion = new SqlSincronizacionImpl();
			getSincronizacion.setActualizarSincronizacionDetalle(detalleSincronizacion);
			llenaTabla();			
			beanTareaProgramada.getTemporizador()[filaTabla].cancel();            
			beanTareaProgramada.getTemporizador()[filaTabla] = new Timer();
            beanTareaProgramada.getTemporizador()[filaTabla].scheduleAtFixedRate(new TiempoTrabajo(new BeanTiempoTrabajo(beanTareaProgramada.getTemporizador()[filaTabla],HoraProgramada,Integer.parseInt(detalleSincronizacion.getNumFec().trim()),tarea,Constante.OPCIONES_ADMINISTRACION,beanTareaProgramada.getCodigoUsuario(),beanTareaProgramada.getPopupOpciones(),false)), HoraProgramada, 1000);	
		}catch(Exception exec){
			Util.mostrarExcepcion(exec);
		}		
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		if(e.getSource() == tblMan){	
			txtFrecuencia.setEditable(true);
			try{				
				String temporal=modeloTabla.getValueAt(tblMan.getSelectedRow(),4).toString();
				String []cad = temporal.split(":");					
	 		    txtFrecuencia.setText(modeloTabla.getValueAt(tblMan.getSelectedRow(),5).toString());
				txtMeridiano.setText(cad[2].substring(2, cad[2].length()));
				txtSegundo.setText(cad[2].substring(0, 2));
				txtMinuto.setText(cad[1]);
				txtHora.setText(cad[0]);
				filaTabla = tblMan.getSelectedRow();
				tarea=modeloTabla.getValueAt(tblMan.getSelectedRow(),0).toString();
				detalleSincronizacion.setNumIdeSinc(((BeanSincronizacion)since.get(tblMan.getSelectedRow())).getStrIdeSinc());				
			}catch(Exception exec){
				Util.mostrarExcepcion(exec);
			}
		}		
	}	
	
    private void adelanteMouseClicked(java.awt.event.MouseEvent evt) {
        int hora=1;
        int minuto=0;
        int segundo=0;
        String meridiano="PM";    	
        try{
        	hora=Integer.parseInt(txtHora.getText().trim());
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);    	
        }
        try{
        	minuto=Integer.parseInt(txtMinuto.getText().trim());
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);
        }
        try{
        	segundo=Integer.parseInt(txtSegundo.getText().trim());
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);
        }
        try{
        	meridiano=txtMeridiano.getText().trim();
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);
        }            
        if(flagHora==1 && (hora>=1 && hora<12)){
           hora++;
           if(hora<10){txtHora.setText("0"+hora);}
           else {txtHora.setText(""+hora);}
        }else if(flagHora==1 && hora==12){txtHora.setText("01");}        
        if(flagMinuto==1 && (minuto>=0 && minuto<59)){
           minuto++;
           if(minuto<10){txtMinuto.setText("0"+minuto);}
           else {txtMinuto.setText(""+minuto);}
        }else if(flagMinuto==1 && minuto==59){txtMinuto.setText("00");}        
        if(flagSegundo==1 && (segundo>=0 && segundo<59)){
        	segundo++;
            if(segundo<10){txtSegundo.setText("0"+segundo);}
            else {txtSegundo.setText(""+segundo);}
         }else if(flagSegundo==1 && segundo==59){txtSegundo.setText("00");}        
        if(flagMeridiano==1 && meridiano.equals("AM")){
        	txtMeridiano.setText("PM");
         }else if(flagMeridiano==1){txtMeridiano.setText("AM");}        
    }
    
    private void atrasMouseClicked(java.awt.event.MouseEvent evt) {
        int hora=1;
        int minuto=0;
        int segundo=0;
        String meridiano="PM";    	
        try{
        	hora=Integer.parseInt(txtHora.getText().trim());
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);
        }
        try{
        	minuto=Integer.parseInt(txtMinuto.getText().trim());
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);
        }
        try{
        	segundo=Integer.parseInt(txtSegundo.getText().trim());
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);
        }
        try{
        	meridiano=txtMeridiano.getText().trim();
        }catch(Exception exe){
        	Util.mostrarExcepcion(exe);
        }        
        if(flagHora==1 && (hora>1 && hora<=12)){
           hora--;
           if(hora<10){txtHora.setText("0"+hora);}
           else {txtHora.setText(""+hora);}
        }else if(flagHora==1 && hora==1){txtHora.setText("12");}        
        if(flagMinuto==1 && (minuto>0 && minuto<=59)){
           minuto--;
           if(minuto<10){txtMinuto.setText("0"+minuto);}
           else {txtMinuto.setText(""+minuto);}
        }else if(flagMinuto==1 && minuto==0){txtMinuto.setText("59");}        
        if(flagSegundo==1 && (segundo>0 && segundo<=59)){
        	segundo--;
            if(segundo<10){txtSegundo.setText("0"+segundo);}
            else {txtSegundo.setText(""+segundo);}
         }else if(flagSegundo==1 && segundo==0){txtSegundo.setText("59");}        
        if(flagMeridiano==1 && meridiano.equals("AM")){
        	txtMeridiano.setText("PM");
         }else if(flagMeridiano==1){txtMeridiano.setText("AM");}
    }
	
	@SuppressWarnings("static-access")
	private  void sincronizar_DOWN_Usuarios(){
		Cmd	objC = new Cmd();		
		SqlUsuario sqlUsuario = new SqlUsuarioImpl();		
		BeanSincronizar bs = new BeanSincronizar(); 
		bs.setStrIdeSinc("1");		
		String fechaInicio = objC.fechaHora();
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaUsuario();  
		objSI.setEliminarSincronizacion(bs);		
		sqlUsuario.setListaUsuario();			
		bs.setStrInfSinc(USUARIOS);
		bs.setStrCantReg(sqlUsuario.getListaUsuario().size()+"");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		bs.setStrTie(objC.diferencia(fechaInicio, fechaFin)+"");		
		objSI.setInsertarSincronizar(bs);   	
			
	}	
	
	@SuppressWarnings("unused")
	private void sincronizar_UP_Usuario(){
		SqlUsuario sqlU = new SqlUsuarioImpl(); 	
		BeanUsuario usu = new BeanUsuario();
		List<BeanUsuario> listaUsu=null;
		SAdministracion sAdministracion = new SAdministracion();
		sqlU.setListaUsuario(usu);	  
		listaUsu=sqlU.getListaUsuario();		  
		for(BeanUsuario us : listaUsu){
			try{			  
				sAdministracion.ingresaModificaUsuario("", us.getStrNomUsuario(), us.getStrClaUsuario(), us.getStrClaUsuario(),  us.getStrIdentificacion());			  
			}catch(Exception ex){ 
				Util.mostrarExcepcion(ex);
			}		
		}	
	}
	
	@SuppressWarnings("static-access")
	private void sincronizar_DOWN_Roles(){
		Cmd	objC = new Cmd();		
		BeanRol rol = new BeanRol();		
		SqlRol sqlRol = new SqlRolImpll();		
		BeanSincronizar bs = new BeanSincronizar(); 
		bs.setStrIdeSinc("2");		
		String fechaInicio = objC.fechaHora();
		objSI = new SqlSincronizacionImpl(); 
		objSI.sincronizaRol();		 
		objSI.setEliminarSincronizacion(bs);		
		sqlRol.setListaRol(rol);			
		bs.setStrInfSinc(ROLES);
		bs.setStrCantReg(sqlRol.getListaRol().size()+"");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		bs.setStrTie(objC.diferencia(fechaInicio, fechaFin)+"");
		objSI.setInsertarSincronizar(bs);	     			
	}
	
	@SuppressWarnings("unused")
	private void sincronizar_UP_Roles(){
		SqlRol sqlRol = new SqlRolImpll();
		BeanRol rol = new BeanRol();	
		List<BeanRol> listaRol=null;		  
		SAdministracion sAdministracion = new SAdministracion();	
		sqlRol.setListaRol(rol);	 
		listaRol = sqlRol.getListaRol();		
		for(BeanRol rl : listaRol){
			try{				  
				sAdministracion.ingresaModifcaRol("",rl.getStrNomRol());				  
			}catch(Exception exec){
				Util.mostrarExcepcion(exec);
			}
		}		
	}	
	
	@SuppressWarnings({ "static-access", "unused" })
	private void sincronizar_DOWN_Usuarios_Roles(){
		Cmd	objC = new Cmd();		
		BeanRol rol = new BeanRol();			
		SqlUsuarioRol usuarioRol = new SqlUsuarioRolImpl();		
		BeanSincronizar bs = new BeanSincronizar(); 
		bs.setStrIdeSinc("3");		
		String fechaInicio = objC.fechaHora();
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaUsuarioRol();		
		objSI.setEliminarSincronizacion(bs);		
		usuarioRol.setListaUsuarioRol();					
		bs.setStrInfSinc(USUARIO_ROLES);
		bs.setStrCantReg(usuarioRol.getListaUsuarioRol().size()+"");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		bs.setStrTie(objC.diferencia(fechaInicio, fechaFin)+"");
		objSI.setInsertarSincronizar(bs);      			
	}
	
	@SuppressWarnings("unused")
	private void sincronizar_UP_Usuarios_Roles(){		
		SqlUsuarioRol usuarioRol = new SqlUsuarioRolImpl();
		SqlUsuario sqlU = new SqlUsuarioImpl();	
		BeanUsuario usu = new BeanUsuario();
		BeanUsuarioRol bur = new BeanUsuarioRol();
		BeanRol rol = new BeanRol();	 
		List<BeanUsuarioRol> listaUsuarioRol=null;
		List<BeanUsuario> listaUsu=null;
		List <String> datos = new ArrayList<String>();
		String idUsuario="";			  
		SAdministracion sAdministracion = new SAdministracion();
		bur.setUsuario(usu);	 
		usuarioRol.setListaUsuarioRol(bur);
		listaUsuarioRol = usuarioRol.getListaUsuarioRol();	 
		datos.clear();				
		for(BeanUsuarioRol usr : listaUsuarioRol){		  
			usu =  usr.getUsuario();	
			rol =  usr.getRol();			  
			idUsuario= usu.getStrIdUsuario();
			datos.add(rol.getStrIdRol());
		}	
		try{			 
			sAdministracion.asignaRolUsuario(datos, idUsuario);
		}catch(Exception ex){ 
			Util.mostrarExcepcion(ex);
		}		
	}
	
	@SuppressWarnings("static-access")
	private  void sincronizar_DOWN_Vistas(){
		Cmd	objC = new Cmd();		
		BeanVista vista = new BeanVista();				
		SqlVista sqlVista = new SqlVistaImpl();		
		BeanSincronizar bs = new BeanSincronizar(); 
		bs.setStrIdeSinc("4");				
		String fechaInicio = objC.fechaHora();
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaVista();		
		objSI.setEliminarSincronizacion(bs);	
		sqlVista.setListaVista(vista);					
		bs.setStrInfSinc(VISTAS);
		bs.setStrCantReg(sqlVista.getListaVista().size()+"");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		bs.setStrTie(objC.diferencia(fechaInicio, fechaFin)+"");
		objSI.setInsertarSincronizar(bs);			
	}
	
	@SuppressWarnings("unused")
	private  void sincronizar_UP_Vistas(){
	}	
	
	@SuppressWarnings("static-access")
	private  void sincronizar_DOWN_Roles_Vistas(){
		Cmd	objC = new Cmd();						
		SqlVistaRol vistaRol = new SqlVistaRolImpl();				
		BeanSincronizar bs = new BeanSincronizar(); 
		bs.setStrIdeSinc("5");		
		String fechaInicio = objC.fechaHora();
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaRolVista();		 
		objSI.setEliminarSincronizacion(bs);		
		vistaRol.setListaVistaRol();					
		bs.setStrInfSinc(ROLES_VISTAS);
		bs.setStrCantReg(vistaRol.getListaVistaRol().size()+"");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		bs.setStrTie(objC.diferencia(fechaInicio, fechaFin)+"");
		objSI.setInsertarSincronizar(bs);		
	}	   
	
	@SuppressWarnings("unused")
	private  void sincronizar_UP_Roles_Vistas(){
		SqlVistaRol sqlVistR = new SqlVistaRolImpl();		 
		BeanRolVista vistaRol = new BeanRolVista();
		BeanRol rol = new BeanRol();	
		BeanVista vista = new BeanVista();	  
		List<BeanRolVista> listaBeanRolVista=null;		 
		SAdministracion sAdministracion = new SAdministracion();
		vistaRol.setRol(rol);		 
		sqlVistR.setListaVistaRol(vistaRol);		 
		listaBeanRolVista = sqlVistR.getListaVistaRol();			  
		for(BeanRolVista brv : listaBeanRolVista){
			rol = brv.getRol();	
			vista = brv.getVista();
			try{			  
			}catch(Exception ex){ Util.mostrarExcepcion(ex);}
		}	
	}
	
	@SuppressWarnings("static-access")
	private  void sincronizar_DOWN_Dispositivos(){
		Cmd	objC = new Cmd();		
		BeanDispositivo dispositivo  = new BeanDispositivo();		
		SqlDispositivo sqlDispositivo = new SqlDispositivoImpl();				
		BeanSincronizar bs = new BeanSincronizar(); 
		bs.setStrIdeSinc("6");		
		String fechaInicio = objC.fechaHora();
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaDispositivo();		 
		objSI.setEliminarSincronizacion(bs);		
		sqlDispositivo.setListaDispositivo(dispositivo);					
		bs.setStrInfSinc(DISPOSITIVOS);
		bs.setStrCantReg(sqlDispositivo.getListaDispositivo().size()+"");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		bs.setStrTie(objC.diferencia(fechaInicio, fechaFin)+"");
		objSI.setInsertarSincronizar(bs);
	}
	
	@SuppressWarnings("unused")
	private  void sincronizar_UP_Dispositivos(){
		SqlDispositivo sqlD = new SqlDispositivoImpl(); 	
		BeanDispositivo disp = new BeanDispositivo();
		List<BeanDispositivo> listaDisp=null;	  
		SAdministracion sAdministracion = new SAdministracion(); 
		sqlD.setListaDispositivo(disp);
		listaDisp=sqlD.getListaDispositivo();	  
		for(BeanDispositivo bd : listaDisp){
			try{
				sAdministracion.ingresaModificaDispositivo("", bd.getStrTipoDispositivo(), bd.getStrNumeroSerieDispositivo(), bd.getStrCodigoActivo(),  bd.getStrSimm(), bd.getStrImei(), bd.getStrNumeroTelefono(), bd.getStrNumeroSeguro(), bd.getStrIdUsuario(), bd.getStrNomUsuario(), bd.getStrDispositivoRelacionado(),  bd.getStrObservacion(), ((BeanDato)datose.get(0)).getStrNU());			
			}catch(Exception e){
				Util.mostrarExcepcion(e);
			}	  
		}		
	}	

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}	
	
	private static String USUARIOS="Usuarios";
	private static String ROLES="Roles";
	private static String USUARIO_ROLES="Usuarios Roles";
	private static String VISTAS="Vistas";
	private static String ROLES_VISTAS="Roles Vistas";	                    			
	private static String DISPOSITIVOS="Dispositivos";
}