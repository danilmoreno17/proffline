package com.promesa.internalFrame.sincronizacion;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import com.promesa.util.*;
import com.promesa.planificacion.sql.SqlSincronizacion;
import com.promesa.planificacion.sql.impl.SqlSincronizacionImpl;
import com.promesa.administracion.bean.DetalleSincronizacion;
import com.promesa.bean.*;
import com.promesa.sincronizacion.bean.*;
import com.promesa.sincronizacion.impl.TiempoTrabajo;

@SuppressWarnings("serial")
public class ISincronizacionPlanificacion extends JInternalFrame implements ActionListener, MouseListener, KeyListener{

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
	static List<BeanDato> datose;
	SqlSincronizacionImpl objSI;
	List <BeanSincronizacion> since;
	private JLabel lblHorIni;
	private JLabel lblFec;
	private JTextField txtHora;
	private JTextField txtMinuto;
	private JTextField txtSegundo;
	private JTextField txtMeridiano;
	private JTextField txtFrecuencia;
	JButton  btnAdelante;
	JButton  btnAtras;
	DetalleSincronizacion detalleSincronizacion = null;	
    int flagHora=-1;
    int flagMinuto=-1;
    int flagSegundo=-1;
    int flagMeridiano=-1;
    BeanTareaProgramada beanTareaProgramada=null;
    private String tarea;
    private int filaTabla;    
    
	@SuppressWarnings("static-access")
	public ISincronizacionPlanificacion(BeanTareaProgramada beanTareaProgramada){
		
		super("Sincronizaciones",true,true,true,true);	
		this.beanTareaProgramada=beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/isincronizaciones.png")));
		this.setPreferredSize(new java.awt.Dimension(606, 640));
		this.setBounds(0, 0, 606, 640);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));
		detalleSincronizacion = new DetalleSincronizacion();

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar,BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 790, 37);
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 790, 10);
		
		this.datose = beanTareaProgramada.getDatose();

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
		pnlMan.setBounds(0,48,790,160);
		pnlMan.setLayout(null);
		pnlMan.setBorder(BorderFactory.createTitledBorder("Sincronizaciones"));
		
		scrMan = new JScrollPane();		
		scrMan.setBounds(55, 30, 680, 105);
		scrMan.setToolTipText("Listado de Sincronizaciones");		
				
		pnlAut = new JPanel();
		getContentPane().add(pnlAut);
		pnlAut.setBounds(0,220,790,160);
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
			btnGuardar.setBounds(12, 30, 28, 20);
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
					if(column ==0){return true;}
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
			new Renderer().setSizeColumn(tblMan.getColumnModel(),4,110);
			new Renderer().setSizeColumn(tblMan.getColumnModel(),5,110);
			scrMan.setViewportView(tblMan);
			pnlMan.add(scrMan);			
			
			llenaTabla();	
		}		
			
	}
	
	/* MÉTODO QUE LLENA UNA TABLA */
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
    		modeloTabla.addRow(new Object[]{s.getStrInfSinc(), s.getStrNumCantReg(), s.getStrFecHor(), s.getStrNumTie(), s.getStrHoraIni(), s.getStrFrecuencia(), new Boolean(false)});  		
    		i++;
    	}	
	}		
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == btnSincronizar){			
			/*IMPLEMENTA FUNCIONALIDAD SINCRONIZACIÓN MANUAL*/			
			
		}		
		if(e.getSource() == btnGuardar){			
			/*IMPLEMENTA FUNCIONALIDAD SINCRONIZACIÓN AUTOMÁTICA*/
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
			SqlSincronizacion getSincronizacion = new SqlSincronizacionImpl();
			getSincronizacion.setActualizarSincronizacionDetalle(detalleSincronizacion);
			llenaTabla();			
			beanTareaProgramada.getTemporizador()[filaTabla].cancel();            
			beanTareaProgramada.getTemporizador()[filaTabla] = new Timer();
			beanTareaProgramada.getTemporizador()[filaTabla].scheduleAtFixedRate(new TiempoTrabajo(new BeanTiempoTrabajo(beanTareaProgramada.getTemporizador()[filaTabla],HoraProgramada,Integer.parseInt(detalleSincronizacion.getNumFec().trim()),tarea,Constante.OPCIONES_PLANIFICACION,beanTareaProgramada.getCodigoUsuario(),beanTareaProgramada.getPopupOpciones(),false)), HoraProgramada, 1000);		
		}catch(Exception e){
			Util.mostrarExcepcion(e);
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
    
    private void adelanteMouseClicked(java.awt.event.MouseEvent evt) {
    	int hora=1;
        int minuto=0;
        int segundo=0;
        String meridiano="PM";    	
        try{
        	hora=Integer.parseInt(txtHora.getText().trim());
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }
        try{
        	minuto=Integer.parseInt(txtMinuto.getText().trim());
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }
        try{
        	segundo=Integer.parseInt(txtSegundo.getText().trim());
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }
        try{
        	meridiano=txtMeridiano.getText().trim();
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }        
        if(flagHora==1 && (hora>=0 && hora<12)){
        	hora++;
        	if(hora<10){txtHora.setText("0"+hora);}else{txtHora.setText(""+hora);}
        }else if(flagHora==1 && hora==12){txtHora.setText("00");}        
        if(flagMinuto==1 && (minuto>=0 && minuto<59)){
        	minuto++;
        	if(minuto<10){txtMinuto.setText("0"+minuto);}else{txtMinuto.setText(""+minuto);}
        }else if(flagMinuto==1 && minuto==59){txtMinuto.setText("00");}        
        if(flagSegundo==1 && (segundo>=0 && segundo<59)){
        	segundo++;
            if(segundo<10){txtSegundo.setText("0"+segundo);}else{txtSegundo.setText(""+segundo);}
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
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }
        try{
        	minuto=Integer.parseInt(txtMinuto.getText().trim());
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }
        try{
        	segundo=Integer.parseInt(txtSegundo.getText().trim());
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }
        try{
        	meridiano=txtMeridiano.getText().trim();
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        }        
        if(flagHora==1 && (hora>0 && hora<=12)){
        	hora--;
        	if(hora<10){txtHora.setText("0"+hora);}else{txtHora.setText(""+hora);}
        }else if(flagHora==1 && hora==0){txtHora.setText("12");}        
    	if(flagMinuto==1 && (minuto>0 && minuto<=59)){
    		minuto--;
    		if(minuto<10){txtMinuto.setText("0"+minuto);}else{txtMinuto.setText(""+minuto);}
        }else if(flagMinuto==1 && minuto==0){txtMinuto.setText("59");}        
        if(flagSegundo==1 && (segundo>0 && segundo<=59)){
        	segundo--;
            if(segundo<10){txtSegundo.setText("0"+segundo);}else{txtSegundo.setText(""+segundo);}
        }else if(flagSegundo==1 && segundo==0){txtSegundo.setText("59");}        
        if(flagMeridiano==1 && meridiano.equals("AM")){
        	txtMeridiano.setText("PM");
        }else if(flagMeridiano==1){txtMeridiano.setText("AM");}
    }
	
	@Override
	public void mouseClicked(MouseEvent arg0) {		
		if(arg0.getSource() == tblMan){	
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
}