package com.promesa.internalFrame.planificacion;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.promesa.bean.*;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.planificacion.bean.*;
import com.promesa.planificacion.sql.*;
import com.promesa.planificacion.sql.impl.*;
import com.promesa.sap.SPlanificacion;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.*;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class IVisita extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JToolBar mnuToolBar;
	JButton btnProgramarVisita;
	JButton btnFecha;
	List<BeanAgenda> agendas;
	private JLabel lblNomCli;
	private javax.swing.JScrollPane scrResultado;
	private JLabel lblVisitasDia;
	@SuppressWarnings("rawtypes")
	JComboBox cmbNomCli;
	private JLabel lblIdClie = new JLabel();
	private JLabel lblCalendario = new JLabel();
	private JLabel lblFecVis;
	private JLabel lblHorSug;
	@SuppressWarnings("rawtypes")
	JComboBox cmbHorSug;
	private JLabel lblIdHorSug = new JLabel();
	JSeparator separador;
	JPanel pnlVisita;
	private JDateChooser dchCalendario;
	private SqlCliente getCliente = null;
	private SqlHora getHora = null;
	BeanCliente clie = null;
	BeanHora hor = null;
	List<BeanCliente> listaCliente = null;
	List<BeanHora> listaHora = null;
	@SuppressWarnings("unused")
	private SqlVisita getVisita = null;
	SPlanificacion objSAP;
	private String fecAct = "";
	String[] fecha;
	private JLabel lblBotonImportExcel = null;
	private JLabel lblBotonLeerExcel = null;
	private JLabel lblBotonSalir = null;
	private JLabel lblBotonExaminarExcel = null;
	private JPopupMenu popupImportaExcel = null;
	private JPanel pnlImportaExcel = null;
	private JPanel pnlPrincipalPopup = null;
	private JPanel pnlInterno1Popup = null;
	private JPanel pnlInterno2Popup = null;
	private JPanel pnlInterno3Popup = null;
	private JPanel pnlInterno4Popup = null;
	private JTextField txtDireccionExcel;
	
	private JProgressBar pgbInsertado = null;
	private String DireccionFormato = " ";
	public String modo = "0";
	private javax.swing.JTable tblResultado;

	@SuppressWarnings("rawtypes")
	public IVisita() {

		super(Constante.OPCION_VISITA_FUERA_RUTA, true, true, true, true);
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iplanificaciones.gif")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
	
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));
		
		mnuToolBar = new JToolBar();
		mnuToolBar.setFloatable(false);
		getContentPane().add(mnuToolBar, BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 1366, 10); // 0, 39, 1318, 10

		getCliente = new SqlClienteImpl();
		getHora = new SqlHoraImpl();
		getVisita = new SqlVisitaImpl();
		javax.swing.LookAndFeel previousLF = UIManager.getLookAndFeel();
		btnProgramarVisita = new JButton();
		mnuToolBar.add(btnProgramarVisita);
		btnProgramarVisita.setText("Programar Visita");
		btnProgramarVisita.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/guardar.png")));
		btnProgramarVisita.addActionListener(this);
		btnProgramarVisita.setToolTipText("Programar Visita");
		btnProgramarVisita.setPreferredSize(new java.awt.Dimension(93, 29));
		btnProgramarVisita.setBackground(new java.awt.Color(0, 128, 64));
		btnProgramarVisita.setOpaque(false);
		btnProgramarVisita.setBorder(null);
		btnProgramarVisita.setFocusable(false);
		
		pnlVisita = new JPanel();
		getContentPane().add(pnlVisita);
		pnlVisita.setBounds(0, 48, 1090, 310); // 0, 48, 1119, 243
		pnlVisita.setLayout(null);
		pnlVisita.setBorder(BorderFactory.createTitledBorder("Detalle"));
		pnlVisita.setFocusable(false);

		{
			lblNomCli = new JLabel();
			pnlVisita.add(lblNomCli);
			lblNomCli.setText("Cliente:");
			lblNomCli.setBounds(16, 53, 119, 17);
			lblNomCli.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbNomCli = new JComboBox();
			pnlVisita.add(cmbNomCli);
			cmbNomCli.setToolTipText("Cliente");
			cmbNomCli.setBounds(145, 60, 280, 24);
			cmbNomCli.setFont(new java.awt.Font("Century Gothic", 0, 10));
			cargaComboCliente();
			cmbNomCli.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eventoCliente();
				}
			});
			cmbNomCli.setBackground(new java.awt.Color(255, 255, 255));
		}
		{
			lblFecVis = new JLabel();
			pnlVisita.add(lblFecVis);
			lblFecVis.setText("Día Visita:");
			lblFecVis.setBounds(16, 93, 125, 17);
			lblFecVis.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{		
			
			try{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				UIManager.put("Button.disabledText", new Color(176,196,222));
				dchCalendario = new JDateChooser();
				pnlVisita.add(dchCalendario);
				
				dchCalendario.setBounds(145, 91, 147, 24);
				dchCalendario.setDateFormatString("dd.MM.yyyy");
				dchCalendario.setDate(new Date());
				dchCalendario.setMinSelectableDate(new Date());
				dchCalendario.setToolTipText("Día de Visita");
				dchCalendario.setUnselectedDays(cargarFechasNoDisponibles());
				dchCalendario.getDateEditor().getUiComponent().setEnabled(true);
				dchCalendario.getDateEditor().getUiComponent().setBackground(new java.awt.Color(255, 255, 255));
				dchCalendario.getDateEditor().getUiComponent().setForeground(new java.awt.Color(0, 0, 255));
				dchCalendario.getDateEditor().getUiComponent().setFont(new java.awt.Font("Arial", 1, 12));
				dchCalendario.getDateEditor().addPropertyChangeListener(new PropertyChangeListener(){
	
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						
						if ("date".equals(evt.getPropertyName())) {
			                System.out.println(evt.getPropertyName()
			                    + ": " + (Date) evt.getNewValue());
			                try {
								buscarAgenda();
								cargaHoraEvento((Date) evt.getNewValue());
							} catch (Exception e) {
								// TODO Bloque catch generado automáticamente
								e.printStackTrace();
							}
			            }
						
					}
					
				});
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}catch(Exception e){
				
			}
			pnlVisita.add(dchCalendario);
			
			lblCalendario.setText(capturaOrganizaFecha());
		}
		{
			lblHorSug = new JLabel();
			pnlVisita.add(lblHorSug);
			lblHorSug.setText("Hora Sugerida:");
			lblHorSug.setBounds(16, 133, 125, 17);
			lblHorSug.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbHorSug = new JComboBox();
			pnlVisita.add(cmbHorSug);
			cmbHorSug.setToolTipText("Hora Sugerida");
			cmbHorSug.setBounds(145, 131, 147, 24);
			cmbHorSug.setFont(new java.awt.Font("Century Gothic", 0, 11));
			cargaHora();
			//cmbHorSug.addActionListener(new ActionListener() {
			//	@Override
			//	public void actionPerformed(ActionEvent e) {
			//		eventoHora();
			//	}
			//});
			cmbHorSug.setBackground(new java.awt.Color(255, 255, 255));
		}
		{
			lblVisitasDia = new JLabel();
			pnlVisita.add(lblVisitasDia);
			lblVisitasDia.setText("Visitas de dia ");
			lblVisitasDia.setBounds(545, 20, 225, 27);
			lblVisitasDia.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			scrResultado = new javax.swing.JScrollPane();
			scrResultado.setBounds(445,60,400,220);
			tblResultado = new javax.swing.JTable();
			tblResultado.setModel(new javax.swing.table.DefaultTableModel(
					new Object[][] { { null, null, null, null },
							{ null, null, null, null }, { null, null, null, null },
							{ null, null, null, null } }, new String[] { "Title 1",
							"Title 2", "Title 3", "Title 4" }));
			scrResultado.setViewportView(tblResultado);
			pnlVisita.add(scrResultado);
			JTableHeader tableHeader = tblResultado.getTableHeader();
			tblResultado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			/*((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
				
			tblResultado.setRowHeight(Constante.ALTO_COLUMNAS);
			final DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tblResultado.getTableHeader().getDefaultRenderer();
			tableHeader.setDefaultRenderer(new TableCellRenderer(){
				private JLabel lbl;
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, 
	                    int row, int column){
					lbl = (JLabel) hr.getTableCellRendererComponent(table, value, 
                            false, false, row, column);
                    lbl.setBorder(BorderFactory.createCompoundBorder(
                            lbl.getBorder(), 
                            BorderFactory.createEmptyBorder(1, 1, 1, 1)));
                    lbl.setHorizontalAlignment(SwingConstants.CENTER);
                    lbl.setBackground(new Color(176,196,222));
                 
					return lbl;
				}
			});*/
			try {
				buscarAgenda();
			} catch (Exception e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			
		}
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {
			{
				lblBotonImportExcel = new JLabel("Importar (desde Excel)");
				lblBotonImportExcel.setBackground(new Color(238, 238, 238));
				lblBotonImportExcel.setFont(new Font("Arial", 1, 11));
				lblBotonImportExcel.setIcon(new ImageIcon(getClass().getResource("/imagenes/exportar.gif")));
				lblBotonImportExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonImportExcel.setBounds(620, 180, 160, 20);
				lblBotonImportExcel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonImportExcelMouseClicked(evt);
					}

					public void mousePressed(MouseEvent evt) {
						lblBotonImportExcelMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonImportExcelMouseReleased(evt);
					}
				});
				pnlVisita.add(lblBotonImportExcel);
			}
			{
				lblBotonLeerExcel = new JLabel(" Leer ");
				lblBotonLeerExcel.setBackground(new Color(238, 238, 238));
				lblBotonLeerExcel.setFont(new Font("Arial", 1, 12));
				lblBotonLeerExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonLeerExcel.setBounds(650, 50, 90, 20);
				lblBotonLeerExcel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonLeerExcelMouseClicked(evt);
					}

					public void mousePressed(MouseEvent evt) {
						lblBotonLeerExcelMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonLeerExcelMouseReleased(evt);
					}
				});
			}
			{
				lblBotonSalir = new JLabel();
				lblBotonSalir.setBackground(new Color(238, 238, 238));
				lblBotonSalir.setFont(new Font("Arial", 1, 12));
				lblBotonSalir.setIcon(new ImageIcon(getClass().getResource("/imagenes/borrar.png")));
				lblBotonSalir.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonSalir.setBounds(650, 50, 150, 20);
				lblBotonSalir.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonSalirMouseClicked(evt);
					}

					public void mousePressed(MouseEvent evt) {
						lblBotonSalirMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonSalirMouseReleased(evt);
					}
				});
			}
			{
				lblBotonExaminarExcel = new JLabel(" Examinar ");
				lblBotonExaminarExcel.setBackground(new Color(238, 238, 238));
				lblBotonExaminarExcel.setFont(new Font("Arial", 1, 12));
				lblBotonExaminarExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				lblBotonExaminarExcel.setBounds(650, 50, 90, 20);
				lblBotonExaminarExcel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						lblBotonExaminarExcelMouseClicked(evt);
					}
					public void mousePressed(MouseEvent evt) {
						lblBotonExaminarExcelMousePressed(evt);
					}

					public void mouseReleased(MouseEvent evt) {
						lblBotonExaminarExcelMouseReleased(evt);
					}
				});
			}
			{
				pgbInsertado = new JProgressBar(0, 100);
				pgbInsertado.setValue(0);
				pgbInsertado.setStringPainted(true);
			}
			{
				txtDireccionExcel = new JTextField();
				txtDireccionExcel.setEditable(false);
				txtDireccionExcel.setBackground(new java.awt.Color(255, 255, 255));
			}
			{
				pnlInterno1Popup = new JPanel(new BorderLayout());
				pnlInterno1Popup.add(txtDireccionExcel, BorderLayout.NORTH);
			}
			{
				pnlInterno3Popup = new JPanel();
				pnlInterno3Popup.add(lblBotonLeerExcel);
				lblBotonLeerExcel.setBounds(1, 100, 60, 30);
			}
			{
				pnlInterno4Popup = new JPanel(new BorderLayout());
				pnlInterno4Popup.setLayout(new BorderLayout());
				pnlInterno4Popup.add(lblBotonSalir, BorderLayout.EAST);
			}
			{
				pnlInterno2Popup = new JPanel();
				pnlInterno2Popup.setLayout(new BorderLayout());
				pnlInterno2Popup.add(pnlInterno3Popup, BorderLayout.CENTER);
				pnlInterno2Popup.add(lblBotonExaminarExcel, BorderLayout.NORTH);
			}
			{
				pnlImportaExcel = new JPanel();
				pnlImportaExcel.setLayout(new BorderLayout());
				pnlImportaExcel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Importar Excel", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 1, 12)));
				pnlImportaExcel.add(pnlInterno1Popup, BorderLayout.CENTER);
				pnlImportaExcel.add(pnlInterno2Popup, BorderLayout.EAST);
			}
			{
				pnlPrincipalPopup = new JPanel();
				pnlPrincipalPopup.setLayout(new BorderLayout());
				pnlPrincipalPopup.add(pnlInterno4Popup, BorderLayout.NORTH);
				pnlPrincipalPopup.add(pnlImportaExcel, BorderLayout.CENTER);
				pnlPrincipalPopup.add(pgbInsertado, BorderLayout.SOUTH);
				pnlPrincipalPopup.setPreferredSize(new Dimension(300, 115));
			}
			{
				popupImportaExcel = new JPopupMenu();
				popupImportaExcel.add(pnlPrincipalPopup);
			}
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	public void tablaVacia() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "CÓDIGO", "CLIENTE", "CIUDAD", "HORA"};
				DefaultTableModel tblTablaModel = new DefaultTableModel( new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblResultado.setModel(tblTablaModel);
				tblResultado.updateUI();
				Util.setAnchoColumnas(tblResultado);
			}
		});
	}
	public void tablaLLena() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "CÓDIGO", "CLIENTE", "CIUDAD", "HORA"};
				DefaultTableModel tblTablaModel = new DefaultTableModel( new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblTablaModel.setNumRows(0);
				for(BeanAgenda ba: agendas){
					BeanCliente cl = new BeanCliente();
					SqlCliente getCliente = new SqlClienteImpl();
					String[] values = {ba.getCUST_ID(),ba.getCUST_NAME(),getCliente.obtenerCliente(ba.getCUST_ID()).getStrCiudadCliente(),ba.getStrHora()};
					tblTablaModel.addRow(values);
				}
				tblResultado.setDefaultRenderer(Object.class, new Renderer());
				tblResultado.setModel(tblTablaModel);
				for (int i = 0; i < 4; i++) {
					tblResultado.getColumnModel().getColumn(i).setCellRenderer(new Renderer().getCenterCell());
				}
				tblResultado.updateUI();
				Util.setAnchoColumnas(tblResultado);
			}
		});
	}
	/*	METODO QUE CARGA LAS LISTAS DE FECHAS EN LAS CUALES CUMPLEN EL MAXIMO DIAS DE VISITAS*/
	public List<Date> cargarFechasNoDisponibles(){
		List<Date> fechas = new ArrayList<Date>();
		BeanDato usuario = Promesa.datose.get(0);
		BeanAgenda ba = new BeanAgenda();
		ba.setVENDOR_ID(usuario.getStrCodigo());
		SqlAgenda getAgenda = new SqlAgendaImpl();
		getAgenda.setListaAgenda(ba);
		int max = getAgenda.obtenerMaxVisitas();
		List<BeanAgenda> listaAgenda =getAgenda.getListaAgenda();
		for(int i=0;i<listaAgenda.size();i++){
			Util util = new Util();
			SqlAgenda tmpAgenda = new SqlAgendaImpl();
			listaAgenda.get(i).setCUST_ID("");
			tmpAgenda.setListaAgenda(listaAgenda.get(i));			
			List<BeanAgenda> tmplistaAgenda =tmpAgenda.getListaAgenda();
			Date date = util.convierteStringaDate(listaAgenda.get(i).getBEGDA());
			if(tmplistaAgenda.size()==max){
				if(!fechas.contains(date)){
					fechas.add(date);
				}
			}
		}
		return fechas;
	} 
	
	/* MÉTODO QUE CARGA LAS HORAS DE LAS VISITAS */
	@SuppressWarnings("unchecked")
	public void cargaHora() {
		BeanHora hora = new BeanHora();
		getHora.setListaHora(hora);
		listaHora = new ArrayList<BeanHora>();
		listaHora = getHora.getListaHora();
		if (listaHora != null && listaHora.size() > 0) {
			lblIdHorSug.setText(listaHora.get(0).getStrIdHora());
			for (int i = 0; i < listaHora.size(); i++) {
				hor = new BeanHora();
				hor = listaHora.get(i);
				cmbHorSug.addItem(hor.getStrHora());
			}
		} else {
			cmbHorSug.addItem("");
		}
	}
	
	/* Cargar Hora en el evento fecha */
	@SuppressWarnings("unchecked")
	public void cargaHoraEvento(Date fecha) {
		BeanHora hora = new BeanHora();
		getHora.setListaHoraEvento(hora, fecha);
		listaHora = new ArrayList<BeanHora>();
		listaHora = getHora.getListaHoraEvento();
		this.cmbHorSug.removeAllItems();
		if (listaHora != null && listaHora.size() > 0) {
			lblIdHorSug.setText(listaHora.get(0).getStrIdHora());
			for (int i = 0; i < listaHora.size(); i++) {
				hor = new BeanHora();
				hor = listaHora.get(i);
				cmbHorSug.addItem(hor.getStrHora());
			}
		} else {
			cmbHorSug.addItem("");
		}
		
		//this.cmbHorSug.updateUI();
		this.cmbHorSug.repaint();
	}

	/* MÉTODO QUE CAPTURA EL ID HORA */
	public void eventoHora() {
		lblIdHorSug.setText(listaHora.get(cmbHorSug.getSelectedIndex()).getStrIdHora());
	}

	/* MÉTODO QUE CARGA LOS CLIENTES POR VENDEDOR */
	@SuppressWarnings("unchecked")
	public void cargaComboCliente() {
		BeanDato usuario = Promesa.datose.get(0);
			listaCliente = new ArrayList<BeanCliente>();
			getCliente.clientesXvendedor(usuario.getStrCodigo());
			listaCliente = getCliente.getListaCliente();
		
		if (listaCliente != null && listaCliente.size() > 0) {
			lblIdClie.setText(listaCliente.get(0).getStrIdCliente());
			for (int i = 0; i < listaCliente.size(); i++) {
					cmbNomCli.addItem(((BeanCliente) listaCliente.get(i)).getStrNombreCliente() +" - "+ ((BeanCliente) listaCliente.get(i)).getStrIdCliente());
			}
		} else {
			cmbNomCli.addItem(Constante.VACIO);
		}
	}
	
	private void buscarAgenda() throws Exception{
		agendas = new ArrayList<BeanAgenda>();
		BeanDato usuario = ((BeanDato) Promesa.datose.get(0));
		BeanAgenda ba = new BeanAgenda();
		//System.out.println("mira"+dchCalendario.getCalendar().getTime());
		ba.setBEGDA(dchCalendario.getCalendar().getTime().toString());
		ba.setVENDOR_ID(usuario.getStrCodigo());
		SqlAgenda getAgenda = new SqlAgendaImpl();
		String query = "SELECT txtVendor_Id, "
				+ "txtVendor_Name, "
				+ "txtBegda, "
				+ "txtEndda, "
				+ "txtHora, "
				+ "txtCust_Id, "
				+ "txtCust_Name, "
				+ "txtCust_Addres, "
				+ "txtCust_Telf, "
				+ "txtCust_Telfx, "
				+ "txtCust_1, "
				+ "txtCu_1, "
				+ "txtCu_2, "
				+ "txtCust_Klimk, "
				+ "txtCus, "
				+ "txtCust_Available, "
				+ "txtDescription, "
				+ "txtSt, "
				+ "txtStat, "
				+ "txtTy, "
				+ "txtCust_2, "
				+ "txtType_Description, "
				+ "txtStdRegAge "
				+ "FROM PROFFLINE_TB_AGENDA WHERE "
				+ "txtVendor_Id = '"+ba.getVENDOR_ID()+"' and "
				+ "txtBegda = '"+ba.getBEGDA()+"' "
				+ "order by txtHora asc";
		getAgenda.getAgendaByQuery(query);
		agendas = getAgenda.getListaAgenda();
		if(agendas.size()==0){
			tablaVacia();
		}else{
			tablaLLena();
		}
		lblVisitasDia.setText("Visitas de dia "+Util.convierteFecha(dchCalendario.getCalendar().getTime().toString()).toString());
	}
	
	/* MÉTODO QUE CAPTURA EL ID CLIENTE */
	public void eventoCliente() {
		lblIdClie.setText(listaCliente.get(cmbNomCli.getSelectedIndex()).getStrIdCliente());
	}

	/* MÉTODO QUE REGISTRA UNA VISITA */
	@SuppressWarnings("static-access")
	public void programaVisita() {
		BeanDato usuario = Promesa.datose.get(0);
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				if (cmbNomCli.getSelectedItem().toString().equals(" - ")) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_CAMPO_VACIO_CLIENTE);
				} else {
					String año = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR));
					//if ((FechaHora.getFecha().charAt(6) + Constante.VACIO + FechaHora.getFecha().charAt(7) + Constante.VACIO + FechaHora.getFecha().charAt(8) + Constante.VACIO + FechaHora.getFecha() .charAt(9)).equals(año)) {
						String vD = Integer.toString(dchCalendario .getCalendar().get( java.util.Calendar.DAY_OF_WEEK));
						if (!(vD.equals("1"))) {
							String mes = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.MONTH) + 1);
							if (mes.length() == 1) {
								mes = "0" + mes;
							}
							String dia = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.DATE));
							if (dia.length() == 1) {
								dia = "0" + dia;
							}
							lblCalendario.setText(dia + Constante.SEPARADOR + mes + Constante.SEPARADOR + año);
							String strIdCliente = lblIdClie.getText() == null ? "" : lblIdClie.getText();
							String strFechaVisita = lblCalendario.getText() == null ? "" : lblCalendario.getText();
							Visita objV = new Visita();
							if (objV.esFeriado(strFechaVisita)) {
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_FERIADO);
							} else {
								try {
									BeanDato usuario = Promesa.datose.get(0);
									List<BeanAgenda> listaAgenda = new ArrayList<BeanAgenda>();
									BeanAgenda ba = new BeanAgenda();
									Util util = new Util();
									ba.setVENDOR_ID(usuario.getStrCodigo());
									ba.setBEGDA(util.convierteFecha2(año + mes + dia));
									ba.setTIME(cmbHorSug.getSelectedItem().toString());
									ba.setCUST_ID(strIdCliente);
									ba.setCUST_NAME(((BeanCliente) listaCliente.get(cmbNomCli.getSelectedIndex())).getStrNombreCliente());
									ba.setCUST_ADDRES(((BeanCliente) listaCliente.get(cmbNomCli.getSelectedIndex())).getStrDireccionCliente());
									ba.setCUST_TELF1(((BeanCliente) listaCliente.get(cmbNomCli.getSelectedIndex())).getStrTelefonoTrabajoCliente());
									Date fechaV = util.convierteStringaDate(ba.getBEGDA());
									Calendar cal = Calendar.getInstance();
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);
									Date now = cal.getTime();
									if(fechaV.after(now)){
										ba.setTY(Constante.TIPO_VISITA1);
									}else{
										ba.setTY(Constante.TIPO_VISITA2);
									}
									ba.setESTADO("111"); 
									SqlClienteImpl sqlCliente = new SqlClienteImpl();
									ba.setCUST_2(sqlCliente.obtenerCondicionPagoPorDefecto(strIdCliente));
									listaAgenda.add(ba);
									SqlAgenda getAgenda = new SqlAgendaImpl();
									String validaciones = validavisita("",ba);
									if(validaciones.equals("")){
									getAgenda.setInsertaAgenda(listaAgenda);
									if (getAgenda.isInsertarAgenda()){
										Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_NUEVA_VISITA);
										buscarAgenda();
										dchCalendario.setUnselectedDays(cargarFechasNoDisponibles());
										Promesa.getInstance().mostrarAvisoSincronizacion(null);
									}else
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_VISITA);
									}else{
										Mensaje.mostrarError(validaciones);
									}

								} catch (Exception e) {
									Util.mostrarExcepcion(e);
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_VISITA);
								}
							}
						} else {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_DOMINGO);
						}
					/*} else {
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_AÑO_ACTUAL);
					}*/
				}
				bloqueador.dispose();
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}
	/* METODO QUE VALIDA LAS VISITAS*/
	public String validavisita(String strMensaje, BeanAgenda ba){
		Util util = new Util();
		//////////////////////////////
		Calendar calnow = Calendar.getInstance();
		calnow.set(Calendar.HOUR_OF_DAY, 0);
		calnow.set(Calendar.MINUTE, 0);
		calnow.set(Calendar.SECOND, 0);
		calnow.set(Calendar.MILLISECOND, 0);
		Date now = calnow.getTime();
		////////////////////////////////
		BeanAgenda ag = new BeanAgenda();
		ag.setBEGDA(ba.getBEGDA());
		ag.setVENDOR_ID(ba.getVENDOR_ID());
		SqlAgenda getAgenda = new SqlAgendaImpl();
		String cliente = ba.getCUST_ID();
		getAgenda.setListaAgenda(ag);
		List<BeanAgenda> listaAgenda =getAgenda.getListaAgenda();
		int max = getAgenda.obtenerMaxVisitas();
		String time[] = ba.getTIME().trim().split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTime(util.convierteStringaDate(ag.getBEGDA()));
		Date fechaV = cal.getTime();
		try{
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		}catch(Exception e){
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);	
		}
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		for(int iag=0;iag<listaAgenda.size();iag++){
			if(listaAgenda.get(iag).getTIME().equals(ba.getTIME())){
				strMensaje += Constante.MENSAJE_ERROR_VISITA_EXISTENTE+"<br>";
			}
			if(listaAgenda.get(iag).getCUST_ID().equals(cliente)){
				strMensaje += Constante.MENSAJE_ERROR_VISITA_CLIENTE_EXISTENTE+"<br>";
			}
		}
		if(listaAgenda.size()==max && fechaV.compareTo(now)!=0){
			strMensaje += Constante.MENSAJE_ERROR_MAX_VISITA+"<br>";
		}
		if(cal.getTime().before(new Date())){
			strMensaje += Constante.MENSAJE_ERROR_VISITA_FECHA+"<br>";
		}
		if(!strMensaje.equals("")){
			strMensaje = "<html>"+strMensaje+"</html>";
		}
		return strMensaje;
	}
	/* MÉTODO QUE PROCESA LA FECHA */
	public String[] fecha() {
		String[] fecha = new String[3];
		fecha[0] = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR));
		fecha[1] = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.MONTH) + 1);
		fecha[2] = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.DATE));
		return fecha;
	}

	/* MÉTODO QUE RECIBE Y ENVÍA FECHA */
	public String capturaOrganizaFecha() {
		fecha = fecha();
		fecAct = fecha[2] + "." + fecha[1] + "." + fecha[0];
		return fecAct;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnProgramarVisita) {
			programaVisita();
		}
	}

	private void lblBotonImportExcelMouseClicked(MouseEvent evt) {
		lblBotonImportExcel.add(popupImportaExcel);
		popupImportaExcel.show(lblBotonImportExcel, -150, 0);
		popupImportaExcel.setVisible(true);
	}

	private void lblBotonImportExcelMousePressed(MouseEvent evt) {
		if (lblBotonImportExcel.isEnabled() == true) {
			lblBotonImportExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonImportExcel.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonImportExcelMouseReleased(MouseEvent evt) {
		lblBotonImportExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonImportExcel.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void lblBotonLeerExcelMouseClicked(MouseEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				pgbInsertado.setValue(0);
				insertaFueraDeRuta();
				bloqueador.dispose();
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	/* MÉTODO QUE COMPLETA CEROS */
	public String completaCeros(String codigo) {
		int i = codigo.length();
		while (i < 10) {
			codigo = "0" + codigo;
			i++;
		}
		return codigo;
	}

	/* MÉTODO QUE VALIDA HORA */
	public boolean validaHora(String hora) {
		boolean resultado = false;
		if (hora.length() == 5) {
			if (hora.equals("08:00") || hora.equals("08:30") || hora.equals("09:00") || hora.equals("09:30")
					|| hora.equals("10:00") || hora.equals("10:30") || hora.equals("11:00") || hora.equals("11:30")
					|| hora.equals("12:00") || hora.equals("12:30") || hora.equals("13:00") || hora.equals("13:30")
					|| hora.equals("14:00") || hora.equals("14:30") || hora.equals("15:00") || hora.equals("15:30")
					|| hora.equals("16:00") || hora.equals("16:30") || hora.equals("17:00") || hora.equals("17:30")
					|| hora.equals("18:00") || hora.equals("18:30")){
				resultado = true;
			}
		}else if (hora.length() == 4){
			if(hora.equals("8:00") || hora.equals("8:30") || hora.equals("9:00") || hora.equals("9:30")){
				resultado = true;
			}
		}
		return resultado;
	}

	/* MÉTODO QUE VALIDA UN CLIENTE */
	public boolean validaCliente(String codigo) {
		boolean resultado = false;
//		String temp = completaCeros(codigo);
		for (int i = 0; i < listaCliente.size(); i++) {
			if (listaCliente.get(i).getStrIdCliente().equals(codigo)) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}

	/* MÉTODO QUE VALIDA UNA FECHA */
	public boolean validaFecha(String fecha) {
		boolean resultado = false;
		/*
		 * formato de fecha es : AñoMesDia - 20131107
		 */
		String anio = "" + fecha.charAt(0) + fecha.charAt(1) + fecha.charAt(2) + fecha.charAt(3);
		String mes = "" + fecha.charAt(4) + fecha.charAt(5);
		String dia = "" + fecha.charAt(6) + fecha.charAt(7);
		if (fecha.length() == 8) {
			if (anio.equals(Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR)))) {
				if (mes.charAt(0) == '0') {
					if (mes.charAt(1) == '1' || mes.charAt(1) == '2' || mes.charAt(1) == '3' || mes.charAt(1) == '4' || mes.charAt(1) == '5' || mes.charAt(1) == '6' || mes.charAt(1) == '7' || mes.charAt(1) == '8' || mes.charAt(1) == '9') {
						if (dia.charAt(0) == '0') {
							if (dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '1') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '2') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '3') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1') {
								resultado = true;
							}
						}
					}
				}
				if (mes.charAt(0) == '1') {
					if (mes.charAt(1) == '0' || mes.charAt(1) == '1' || mes.charAt(1) == '2') {
						if (dia.charAt(0) == '0') {
							if (dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '1') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '2') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1' || dia.charAt(1) == '2' || dia.charAt(1) == '3' || dia.charAt(1) == '4' || dia.charAt(1) == '5' || dia.charAt(1) == '6' || dia.charAt(1) == '7' || dia.charAt(1) == '8' || dia.charAt(1) == '9') {
								resultado = true;
							}
						}
						if (dia.charAt(0) == '3') {
							if (dia.charAt(1) == '0' || dia.charAt(1) == '1') {
								resultado = true;
							}
						}
					}
				}
			}
		}
		return resultado;
	}

	@SuppressWarnings("unused")
	public void insertaFueraDeRuta() {
		boolean estado = false; /* VALOR INICIAL */
		LeerHojaExcel leerHojaExcel = new LeerHojaExcel();
		List<HashMap<String, String>> listaVisita = null;
		HashMap<String, String> dataSet = null;
		SqlPlanificacion getPlanificacion = new SqlPlanificacionImpl();
		ThreadSocketProgressBar thrProgreso = null;
		SqlVisita sqlVisita = new SqlVisitaImpl();
		BeanVisita visita = null;
		String idPla;
		String diaVisita;
		int filas = 0;
		int contarFilas = 0;
		HashMap<Integer, String> lblColumna_Visita = new HashMap<Integer, String>();
		lblColumna_Visita.put(0, Id_Cliente_Visita);
		lblColumna_Visita.put(1, Fecha_Visita);
		lblColumna_Visita.put(2, Hora_Planeada_Visita);
//		leerHojaExcel.LeerHojaExcel1(txtDireccionExcel.getText(), 1, 4, lblColumna_Visita);
		leerHojaExcel.LeerHojaExcel1(txtDireccionExcel.getText(), 0, 3, lblColumna_Visita);
		listaVisita = leerHojaExcel.getListaDataSet();
		filas = listaVisita.size();
		for (HashMap<String, String> map : listaVisita) {
			if (map.get(Id_Cliente_Visita) != null && map.get(Fecha_Visita) != null && map.get(Hora_Planeada_Visita) != null) {
				String strHora = Util.convierteHoraAFormatoHHMM(map.get(Hora_Planeada_Visita));
				if (validaCliente(map.get(Id_Cliente_Visita)) == true && validaFecha(map.get(Fecha_Visita)) == true && validaHora(strHora) == true) {
					try {
						List<String> lstFechas = new ArrayList<String>();
						lstFechas.add(map.get(Fecha_Visita));
						objSAP = new SPlanificacion();
						BeanDato usuario = Promesa.datose.get(0);
						String msg = objSAP.ingresaVisitaFueraDeRuta( usuario.getStrCodigo(), completaCeros(map.get(Id_Cliente_Visita)), lstFechas, strHora, Constante.TIPO_VISITA2, Constante.VACIO);
						if (msg != null) {
							contarFilas++;
							pgbInsertado.setValue((int) ((100 * contarFilas) / filas));
							pgbInsertado.repaint();
							estado = true;
						} else {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_VISITA);
							break;
						}
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_NUEVA_VISITA);
						break;
					}
				}
			}
		}
		if (estado) {
			Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_IMPORTA_VISITA);
		}
	}

	private void lblBotonLeerExcelMousePressed(MouseEvent evt) {
		if (lblBotonLeerExcel.isEnabled() == true) {
			lblBotonLeerExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonLeerExcel.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonLeerExcelMouseReleased(MouseEvent evt) {
		lblBotonLeerExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonLeerExcel.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void lblBotonSalirMouseClicked(MouseEvent evt) {
		popupImportaExcel.setVisible(false);
		pgbInsertado.setValue(0);
	}

	private void lblBotonSalirMousePressed(MouseEvent evt) {
		if (lblBotonSalir.isEnabled() == true) {
			lblBotonSalir.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonSalir.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonSalirMouseReleased(MouseEvent evt) {
		lblBotonSalir.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonSalir.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void lblBotonExaminarExcelMouseClicked(MouseEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(IVisita.this);
		if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			DireccionFormato = String.valueOf(file.getAbsoluteFile());
			DireccionFormato = DireccionFormato.trim();
			txtDireccionExcel.setText(DireccionFormato);
			popupImportaExcel.setVisible(true);
		}
		pgbInsertado.setValue(0);
	}

	private void lblBotonExaminarExcelMousePressed(MouseEvent evt) {
		if (lblBotonExaminarExcel.isEnabled() == true) {
			lblBotonExaminarExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lblBotonExaminarExcel.setVerticalAlignment(SwingConstants.BOTTOM);
		}
	}

	private void lblBotonExaminarExcelMouseReleased(MouseEvent evt) {
		lblBotonExaminarExcel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblBotonExaminarExcel.setVerticalAlignment(SwingConstants.CENTER);
	}

	private class ThreadSocketProgressBar extends Thread {
		int progreso;
		@SuppressWarnings("unused")
		public ThreadSocketProgressBar(int progreso) {
			this.progreso = progreso;
		}
		public void run() {
			pgbInsertado.setValue(progreso);
			Rectangle progressRect = pgbInsertado.getBounds();
			progressRect.x = 0;
			progressRect.y = 0;
			pgbInsertado.paintImmediately(progressRect);
		}
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	private String Id_Cliente_Visita = "IdCliente";
	private String Fecha_Visita = "FechaVisita";
	private String Hora_Planeada_Visita = "HoraPlaneada";
}