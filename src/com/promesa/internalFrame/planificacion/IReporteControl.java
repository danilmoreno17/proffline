package com.promesa.internalFrame.planificacion;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.promesa.bean.BeanDato;
import com.promesa.planificacion.bean.*;
import com.promesa.sap.SPlanificacion;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.*;
import com.toedter.calendar.JDateChooser;

public class IReporteControl extends JInternalFrame implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	JToolBar mnuToolBar;
	JButton btnBuscar;
	JButton btnExportar;
	private JLabel lblVen;
	@SuppressWarnings("rawtypes")
	JComboBox cmbVen;
	JCheckBox cboVen;
	private JLabel lblCli;
	@SuppressWarnings("rawtypes")
	JComboBox cmbCli;
	JSeparator separador;
	JPanel pnlParametros;
	JPanel pnlListado;
	JScrollPane scrListado;
	private JTable tblPlanificacion;
	private JTable tblVisita;
	DefaultTableModel modeloTabla;
	private JLabel lblDesde;
	private JDateChooser dchDesde;
	private JLabel lblHasta;
	private JDateChooser dchHasta;
	private JLabel lblTipo;
	JRadioButton rdbPlanificacion;
	JRadioButton rdbVisita;
	static List<BeanDato> datose;
	SPlanificacion objSAP;
	List<BeanEmpleado> listaEmpleado = null;
	List<BeanCliente> listaCliente = null;
	List<BeanPlanificacion> listaPlanificacion = null;
	List<BeanPlanificacion> planificacionese;
	List<BeanVisita> listaVisita = null;
	List<BeanVisita> visitase;
	BeanPlanificacion pla = null;
	BeanVisita vis = null;

	@SuppressWarnings({ "static-access", "rawtypes" })
	public IReporteControl(BeanTareaProgramada beanTareaProgramada) {

		super("Reportes de Control", true, true, true, true);
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/ireporte.jpg")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar, BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));

		this.datose = beanTareaProgramada.getDatose();

		btnBuscar = new JButton();
		mnuToolBar.add(btnBuscar);
		btnBuscar.setText("Buscar");
		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/search.png")));
		btnBuscar.addActionListener(this);
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setPreferredSize(new java.awt.Dimension(93, 29));
		btnBuscar.setBackground(new java.awt.Color(0, 128, 64));
		btnBuscar.setOpaque(false);
		btnBuscar.setBorder(null);
		btnBuscar.setFocusable(false);

		btnExportar = new JButton();
		mnuToolBar.add(btnExportar);
		btnExportar.setText("Exportar");
		btnExportar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/excel-24.png")));
		btnExportar.addActionListener(this);
		btnExportar.setToolTipText("Exportar");
		btnExportar.setPreferredSize(new java.awt.Dimension(100, 29));
		btnExportar.setBackground(new java.awt.Color(0, 128, 64));
		btnExportar.setOpaque(false);
		btnExportar.setBorder(null);
		btnExportar.setFocusable(false);

		pnlParametros = new JPanel();
		getContentPane().add(pnlParametros);
		pnlParametros.setBounds(0, 48, 790, 160); // 0, 48, 1119, 243
		pnlParametros.setLayout(null);
		pnlParametros.setBorder(BorderFactory.createTitledBorder("Criterios (de b�squeda)"));
		pnlParametros.setFocusable(false);
		{
			lblVen = new JLabel();
			pnlParametros.add(lblVen);
			lblVen.setText("Ejecutivo de Ventas:");
			lblVen.setBounds(16, 20, 119, 17);
			lblVen.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbVen = new JComboBox();
			pnlParametros.add(cmbVen);
			cmbVen.setToolTipText("Ejecutivo de Ventas");
			cmbVen.setBounds(145, 18, 220, 24);
			cmbVen.setFocusable(false);
			cmbVen.setFont(new java.awt.Font("Candara", 0, 11));
			cargaInicialVendedor();
			cmbVen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							cargaCliente();
							bloqueador.dispose();
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}
			});
			cmbVen.setBackground(new java.awt.Color(255, 255, 255));
		}
		{
			cboVen = new JCheckBox();
			pnlParametros.add(cboVen);
			cboVen.setToolTipText("Reporte de todos los vendedores");
			cboVen.setBounds(370, 18, 60, 24);
			cboVen.setText("Todos");
			cboVen.setFont(new java.awt.Font("Arial", 1, 11));
			cboVen.addActionListener(this);
		}
		{
			lblCli = new JLabel();
			pnlParametros.add(lblCli);
			lblCli.setText("Cliente:");
			lblCli.setBounds(440, 20, 50, 17);
			lblCli.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			cmbCli = new JComboBox();
			pnlParametros.add(cmbCli);
			cmbCli.setToolTipText("Cliente");
			cmbCli.setBounds(509, 18, 220, 24);
			cmbCli.setFocusable(false);
			cmbCli.setFont(new java.awt.Font("Candara", 0, 11));
			cargaInicialCliente();
			cmbCli.setBackground(new java.awt.Color(255, 255, 255));
		}
		{
			lblDesde = new JLabel();
			pnlParametros.add(lblDesde);
			lblDesde.setText("Fecha (desde):");
			lblDesde.setBounds(16, 55, 119, 17);
			lblDesde.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			dchDesde = new JDateChooser();
			pnlParametros.add(dchDesde);
			dchDesde.setBounds(145, 53, 147, 24);
			dchDesde.setDateFormatString("dd.MM.yyyy");
			dchDesde.setDate(new Date());
			dchDesde.setToolTipText("Desde");
			dchDesde.getDateEditor().getUiComponent().setEnabled(false);
			dchDesde.getDateEditor().getUiComponent().setBackground(new java.awt.Color(255, 255, 255));
			dchDesde.getDateEditor().getUiComponent().setForeground(new java.awt.Color(0, 0, 255));
			dchDesde.getDateEditor().getUiComponent().setFont(new java.awt.Font("Arial", 1, 12));
		}
		{
			lblHasta = new JLabel();
			pnlParametros.add(lblHasta);
			lblHasta.setText("Fecha (hasta):");
			lblHasta.setBounds(16, 85, 119, 17);
			lblHasta.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			dchHasta = new JDateChooser();
			pnlParametros.add(dchHasta);
			dchHasta.setBounds(145, 83, 147, 24);
			dchHasta.setDateFormatString("dd.MM.yyyy");
			dchHasta.setDate(new Date());
			dchHasta.setToolTipText("Desde");
			dchHasta.getDateEditor().getUiComponent().setEnabled(false);
			dchHasta.getDateEditor().getUiComponent().setBackground(new java.awt.Color(255, 255, 255));
			dchHasta.getDateEditor().getUiComponent().setForeground(new java.awt.Color(0, 0, 255));
			dchHasta.getDateEditor().getUiComponent().setFont(new java.awt.Font("Arial", 1, 12));
		}
		{
			lblTipo = new JLabel();
			pnlParametros.add(lblTipo);
			lblTipo.setText("Tipo:");
			lblTipo.setBounds(16, 120, 119, 17);
			lblTipo.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			rdbPlanificacion = new JRadioButton();
			pnlParametros.add(rdbPlanificacion);
			rdbPlanificacion.addActionListener(this);
			rdbPlanificacion.setBounds(145, 118, 100, 24);
			rdbPlanificacion.setText("Planificaci�n");
			rdbPlanificacion.setSelected(true);
		}
		{
			rdbVisita = new JRadioButton();
			pnlParametros.add(rdbVisita);
			rdbVisita.addActionListener(this);
			rdbVisita.setBounds(250, 118, 100, 24);
			rdbVisita.setText("Visita");
		}
		pnlListado = new JPanel();
		getContentPane().add(pnlListado);
		pnlListado.setBounds(0, 210, 790, 195);
		pnlListado.setLayout(null);
		pnlListado.setBorder(BorderFactory.createTitledBorder("Resultados (de b�squeda)"));
		pnlListado.setFocusable(false);

		scrListado = new JScrollPane();
		pnlListado.add(scrListado);
		scrListado.setBounds(50, 29, 680, 142);
		scrListado.setToolTipText("Relaci�n de Resultados");
		mnuToolBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		if (rdbPlanificacion.isSelected()) {
			tablaVaciaPlanificacion();
		} else {
			tablaVaciaVisita();
		}

	}

	/* M�TODO QUE PINTA UNA TABLA SIN DATOS DE VISITAS */
	public void tablaVaciaVisita() {
		String Columnas[] = { "Fecha", "Hora", "ID Cliente", "Cliente", "Tel�fono", "Tipo" };
		modeloTabla = new DefaultTableModel(null, Columnas);
		TableModel tblTablaModel = new DefaultTableModel(new Object[][] {
				{ "", "", "", "", "", "" }, { "", "", "", "", "", "" },
				{ "", "", "", "", "", "" }, { "", "", "", "", "", "" },
				{ "", "", "", "", "", "" }, { "", "", "", "", "", "" },
				{ "", "", "", "", "", "" } }, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblVisita = new JTable();
		tblVisita.setModel(tblTablaModel);
		scrListado.setViewportView(tblVisita);
		pnlListado.add(scrListado);
	}

	/* M�TODO QUE PINTA UNA TABLA CON DATOS DE VISITAS */
	public void tablaLLenaVisita() {
		String Columnas[] = { "Fecha", "Hora", "ID Cliente", "Cliente", "Tel�fono", "Tipo" };
		modeloTabla = new DefaultTableModel(null, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		if (visitase.size() > 7) {
			modeloTabla.setNumRows(visitase.size());
		} else {
			modeloTabla.setNumRows(7);
		}
		for (int i = 0; i < visitase.size(); i++) {
			modeloTabla.setValueAt(((BeanVisita) visitase.get(i)).getStrFechaVisita(), i, 0);
			modeloTabla.setValueAt(((BeanVisita) visitase.get(i)).getStrHora(), i, 1);
			modeloTabla.setValueAt(((BeanVisita) visitase.get(i)).getStrIdCliente(), i, 2);
			modeloTabla.setValueAt(((BeanVisita) visitase.get(i)).getStrNomCliente(), i, 3);
			modeloTabla.setValueAt(((BeanVisita) visitase.get(i)).getStrTelCliente(), i, 4);
			modeloTabla.setValueAt(((BeanVisita) visitase.get(i)).getStrTipo(), i, 5);
		}
		tblVisita = new JTable();
		tblVisita.setModel(modeloTabla);
		tblVisita.addKeyListener(this);
		tblVisita.addMouseListener(this);
		tblVisita.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
		tblVisita.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getCenterCell());
		tblVisita.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getCenterCell());
		tblVisita.getColumnModel().getColumn(3).setCellRenderer(new Renderer().getCenterCell());
		tblVisita.getColumnModel().getColumn(4).setCellRenderer(new Renderer().getCenterCell());
		tblVisita.getColumnModel().getColumn(5).setCellRenderer(new Renderer().getCenterCell());
		scrListado.setViewportView(tblVisita);
		pnlListado.add(scrListado);
		new Renderer().setSizeColumn(tblVisita.getColumnModel(), 0, 80);
		new Renderer().setSizeColumn(tblVisita.getColumnModel(), 1, 40);
		new Renderer().setSizeColumn(tblVisita.getColumnModel(), 2, 60);
		new Renderer().setSizeColumn(tblVisita.getColumnModel(), 3, 300);
		new Renderer().setSizeColumn(tblVisita.getColumnModel(), 4, 80);
		new Renderer().setSizeColumn(tblVisita.getColumnModel(), 5, 120);
	}

	/* M�TODO QUE PINTA UNA TABLA SIN DATOS DE PLANIFICACIONES */
	public void tablaVaciaPlanificacion() {
		String Columnas[] = { "Plan", "Fecha Inicio", "D�a y Ocurrencia", "Hora", "Frecuencia", "ID Cliente", "Cliente" };
		modeloTabla = new DefaultTableModel(null, Columnas);
		TableModel tblTablaModel = new DefaultTableModel(new Object[][] {
				{ "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "" } }, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblPlanificacion = new JTable();
		tblPlanificacion.setModel(tblTablaModel);
		scrListado.setViewportView(tblPlanificacion);
		pnlListado.add(scrListado);
	}

	/* M�TODO QUE PINTA UNA TABLA CON DATOS DE PLANIFICACIONES */
	public void tablaLLenaPlanificacion() {
		String Columnas[] = { "Plan", "Fecha Inicio", "D�a y Ocurrencia", "Hora", "Frecuencia", "ID Cliente", "Cliente" };
		modeloTabla = new DefaultTableModel(null, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		if (planificacionese.size() > 7) {
			modeloTabla.setNumRows(planificacionese.size());
		} else {
			modeloTabla.setNumRows(7);
		}
		for (int i = 0; i < planificacionese.size(); i++) {
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrIdPlan(), i, 0);
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
				modeloTabla.setValueAt(convierteFecha(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio()), i, 1);
			} else {
				modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio(), i, 1);
			}
			modeloTabla.setValueAt(convierteDO(((BeanPlanificacion) planificacionese.get(i)).getStrFechaInicio()), i, 2);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrHora(), i, 3);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrFrecuencia(), i, 4);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrIdCliente(), i, 5);
			modeloTabla.setValueAt(((BeanPlanificacion) planificacionese.get(i)).getStrNombreCliente(), i, 6);
		}
		tblPlanificacion = new JTable();
		tblPlanificacion.setModel(modeloTabla);
		tblPlanificacion.addKeyListener(this);
		tblPlanificacion.addMouseListener(this);
		tblPlanificacion.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(3).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(4).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(5).setCellRenderer(new Renderer().getCenterCell());
		tblPlanificacion.getColumnModel().getColumn(6).setCellRenderer(new Renderer().getCenterCell());
		scrListado.setViewportView(tblPlanificacion);
		pnlListado.add(scrListado);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 0, 70);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 1, 80);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 2, 130);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 3, 40);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 4, 70);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 5, 89);
		new Renderer().setSizeColumn(tblPlanificacion.getColumnModel(), 6, 201);
	}

	/* M�TODO QUE OBTIENE LA OCURRENCIA */
	public String obtieneDO(String v1, String v2) {
		String cadena = "";
		if (v1.equals("1")) {
			cadena = Constante.PRIMERO;
		}
		if (v1.equals("2")) {
			cadena = Constante.SEGUNDO;
		}
		if (v1.equals("3")) {
			cadena = Constante.TERCERO;
		}
		if (v1.equals("4")) {
			cadena = Constante.CUARTO;
		}
		if (v1.equals("5")) {
			cadena = Constante.QUINTO;
		}
		return cadena + " " + FechaHora.getDia(v2) + " del mes";
	}

	/* M�TODO QUE CALCULA D�A Y OCURRENCIA */
	public String convierteDO(String fsf) {
		int dia, mes, anio;
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			dia = Integer.parseInt(convierteFecha(fsf).substring(0, 2));
		} else {
			dia = Integer.parseInt(fsf.substring(0, 2));
		}
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			mes = Integer.parseInt(convierteFecha(fsf).substring(3, 5)) - 1;
		} else {
			mes = Integer.parseInt(fsf.substring(3, 5)) - 1;
		}
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			anio = Integer.parseInt(convierteFecha(fsf).substring(6, 10));
		} else {
			anio = Integer.parseInt(fsf.substring(6, 10));
		}
		Calendar c = new GregorianCalendar(Locale.ROOT);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(anio, mes, dia);
		int posicionMes = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			return obtieneDO(String.valueOf(posicionMes), convierteFecha(fsf));
		} else {
			return obtieneDO(String.valueOf(posicionMes), fsf);
		}
	}

	/* CONVERSI�N A DD.MM.YYYY */
	public String convierteFecha(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return e.getMessage();
		}
	}

	/* M�TODO QUE LISTA PLANIFICACIONES O VISITAS */
	public void busca() {
		BeanEmpleado be = (BeanEmpleado) cmbVen.getSelectedItem();
		BeanCliente bc = (BeanCliente) cmbCli.getSelectedItem();
		planificacionese = new ArrayList<BeanPlanificacion>();
		visitase = new ArrayList<BeanVisita>();
		String aD = Integer.toString(dchDesde.getCalendar().get(java.util.Calendar.YEAR));
		String mD = Integer.toString(dchDesde.getCalendar().get(java.util.Calendar.MONTH) + 1);
		if (mD.length() == 1) {
			mD = "0" + mD;
		}
		String dD = Integer.toString(dchDesde.getCalendar().get(java.util.Calendar.DATE));
		if (dD.length() == 1) {
			dD = "0" + dD;
		}
		String aH = Integer.toString(dchHasta.getCalendar().get(java.util.Calendar.YEAR));
		String mH = Integer.toString(dchHasta.getCalendar().get(java.util.Calendar.MONTH) + 1);
		if (mH.length() == 1) {
			mH = "0" + mH;
		}
		String dH = Integer.toString(dchHasta.getCalendar().get(java.util.Calendar.DATE));
		if (dH.length() == 1) {
			dH = "0" + dH;
		}
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			try {
				objSAP = new SPlanificacion();
				if (cboVen.isSelected()) {
					List<String> lstVen = new ArrayList<String>();
					for (int i = 0; i < listaEmpleado.size(); i++) {
						lstVen.add(((BeanEmpleado) listaEmpleado.get(i)).getStrIdEmpleado());
					}
					List<String> lstFecD = new ArrayList<String>();
					for (int j = 0; j < lstVen.size(); j++) {
						lstFecD.add(aD + mD + dD);
					}
					List<String> lstFecH = new ArrayList<String>();
					for (int k = 0; k < lstVen.size(); k++) {
						lstFecH.add(aH + mH + dH);
					}
					if (rdbPlanificacion.isSelected()) {
						listaPlanificacion = objSAP.reportePlanificacion02(lstVen, lstFecD, lstFecH);
					} else {
						listaVisita = objSAP.reporteVisita02(lstVen, lstFecD, lstFecH);
					}
				} else {
					if (be.getStrIdEmpleado() == null && bc.getStrIdCliente() == null) {
						Mensaje.mostrarAviso(Constante.MENSAJE_NO_VENDEDOR_NO_CLIENTE);
					} else if (be.getStrIdEmpleado() != null && bc == null) {
						if (rdbPlanificacion.isSelected()) {
							listaPlanificacion = objSAP.reportePlanificacion(be.getStrIdEmpleado(), aD + mD + dD, aH + mH + dH);
						} else {
							listaVisita = objSAP.reporteVisita( be.getStrIdEmpleado(), aD + mD + dD, aH + mH + dH);
						}
					} else if (be.getStrIdEmpleado() != null
							&& bc.getStrIdCliente() != null) {
						if (rdbPlanificacion.isSelected()) {
							listaPlanificacion = objSAP.reportePlanificacion( be.getStrIdEmpleado(), aD + mD + dD, aH + mH + dH);
						} else {
							listaVisita = objSAP.reporteVisita(be.getStrIdEmpleado(), aD + mD + dD, aH + mH + dH);
						}
					}
				}
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		} else {
			/*
			 * BeanPlanificacion planificacion = new BeanPlanificacion();
			 * planificacion.setStrIdVendedor(be.getStrIdEmpleado());
			 * planificacion.setStrIdCliente(bc.getStrIdCliente());
			 * getPlanificacion.setListaPlanificacion(planificacion);
			 * listaPlanificacion = getPlanificacion.getListaPlanificacion();
			 */
		}
		if (rdbPlanificacion.isSelected()) {
			if (listaPlanificacion != null) {
				if (bc == null) {
					for (BeanPlanificacion p : listaPlanificacion) {
						pla = new BeanPlanificacion();
						pla.setStrIdPlan(p.getStrIdPlan());
						pla.setStrIdVendedor(p.getStrIdVendedor());
						pla.setStrIdJefe(p.getStrIdJefe());
						pla.setStrFechaInicio(p.getStrFechaInicio());
						pla.setStrFechaFin(p.getStrFechaFin());
						pla.setStrHora(p.getStrHora());
						pla.setStrIdCliente(p.getStrIdCliente());
						pla.setStrIdFrecuencia(p.getStrIdFrecuencia());
						pla.setStrFrecuencia(p.getStrFrecuencia());
						pla.setStrDia(p.getStrDia());
						pla.setStrNombreCliente(p.getStrNombreCliente());
						try {
							planificacionese.add(pla);
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
						}
					}
				} else {
					if (bc.getStrIdCliente().length() == 0) {
						for (BeanPlanificacion p : listaPlanificacion) {
							pla = new BeanPlanificacion();
							pla.setStrIdPlan(p.getStrIdPlan());
							pla.setStrIdVendedor(p.getStrIdVendedor());
							pla.setStrIdJefe(p.getStrIdJefe());
							pla.setStrFechaInicio(p.getStrFechaInicio());
							pla.setStrFechaFin(p.getStrFechaFin());
							pla.setStrHora(p.getStrHora());
							pla.setStrIdCliente(p.getStrIdCliente());
							pla.setStrIdFrecuencia(p.getStrIdFrecuencia());
							pla.setStrFrecuencia(p.getStrFrecuencia());
							pla.setStrDia(p.getStrDia());
							pla.setStrNombreCliente(p.getStrNombreCliente());
							try {
								planificacionese.add(pla);
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
							}
						}
					} else {
						for (BeanPlanificacion p : listaPlanificacion) {
							try {
								if (p.getStrIdCliente().equals(bc.getStrIdCliente())) {
									pla = new BeanPlanificacion();
									pla.setStrIdPlan(p.getStrIdPlan());
									pla.setStrIdVendedor(p.getStrIdVendedor());
									pla.setStrIdJefe(p.getStrIdJefe());
									pla.setStrFechaInicio(p.getStrFechaInicio());
									pla.setStrFechaFin(p.getStrFechaFin());
									pla.setStrHora(p.getStrHora());
									pla.setStrIdCliente(p.getStrIdCliente());
									pla.setStrIdFrecuencia(p.getStrIdFrecuencia());
									pla.setStrFrecuencia(p.getStrFrecuencia());
									pla.setStrDia(p.getStrDia());
									pla.setStrNombreCliente(p.getStrNombreCliente());
									planificacionese.add(pla);
								}
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
							}
						}
					}
				}
			}
			if (planificacionese == null || planificacionese.size() == 0) {
				tablaVaciaPlanificacion();
				btnExportar.setEnabled(false);
			} else {
				tablaLLenaPlanificacion();
				btnExportar.setEnabled(true);
			}
		} else {
			if (listaVisita != null) {
				if (bc == null) {
					for (BeanVisita v : listaVisita) {
						vis = new BeanVisita();
						vis.setStrFechaVisita(v.getStrFechaVisita());
						vis.setStrHora(v.getStrHora());
						vis.setStrIdCliente(v.getStrIdCliente());
						vis.setStrNomCliente(v.getStrNomCliente());
						vis.setStrDirCliente(v.getStrDirCliente());
						vis.setStrTelCliente(v.getStrTelCliente());
						vis.setStrTipo(v.getStrTipo());
						try {
							visitase.add(vis);
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_VISITA);
						}
					}
				} else {
					if (bc.getStrIdCliente().length() == 0) {
						for (BeanVisita v : listaVisita) {
							vis = new BeanVisita();
							vis.setStrFechaVisita(v.getStrFechaVisita());
							vis.setStrHora(v.getStrHora());
							vis.setStrIdCliente(v.getStrIdCliente());
							vis.setStrNomCliente(v.getStrNomCliente());
							vis.setStrDirCliente(v.getStrDirCliente());
							vis.setStrTelCliente(v.getStrTelCliente());
							vis.setStrTipo(v.getStrTipo());
							try {
								visitase.add(vis);
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_VISITA);
							}
						}
					} else {
						for (BeanVisita v : listaVisita) {
							try {
								if (completaCeros(v.getStrIdCliente()).equals(bc.getStrIdCliente())) {
									vis = new BeanVisita();
									vis.setStrFechaVisita(v.getStrFechaVisita());
									vis.setStrHora(v.getStrHora());
									vis.setStrIdCliente(v.getStrIdCliente());
									vis.setStrNomCliente(v.getStrNomCliente());
									vis.setStrDirCliente(v.getStrDirCliente());
									vis.setStrTelCliente(v.getStrTelCliente());
									vis.setStrTipo(v.getStrTipo());
									visitase.add(vis);
								}
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
								Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
							}
						}
					}
				}
			}
			if (visitase == null || visitase.size() == 0) {
				tablaVaciaVisita();
				btnExportar.setEnabled(false);
			} else {
				tablaLLenaVisita();
				btnExportar.setEnabled(true);
			}
		}
	}

	/* M�TODO QUE COMPLETA CEROS */
	public String completaCeros(String codigo) {
		int i = codigo.length();
		while (i < 10) {
			codigo = "0" + codigo;
			i++;
		}
		return codigo;
	}

	/* M�TODO QUE CARGA CLIENTES SEG�N VENDEDOR */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargaInicialCliente() {
		if (listaEmpleado != null && listaEmpleado.size() > 0) {
			if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
				try {
					objSAP = new SPlanificacion();
					listaCliente = objSAP.listaClientePlanificacion(listaEmpleado.get(0).getStrIdEmpleado());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					Mensaje.mostrarError(e.getMessage());
				}
			} else {
				/*
				 * BeanCliente cliente = new BeanCliente(); BeanClienteEmpleado
				 * clienteEmpleado = new BeanClienteEmpleado();
				 * clienteEmpleado.setStrIdEmpleado
				 * (listaEmpleado.get(0).getStrIdEmpleado());
				 * cliente.setClienteEmpleado(clienteEmpleado);
				 * getCliente.setListaCliente(cliente); listaCliente = new
				 * ArrayList<BeanCliente>(); listaCliente =
				 * getCliente.getListaCliente();
				 */
			}
		}
		if (listaCliente != null && listaCliente.size() > 0) {
			cmbCli.setModel(new DefaultComboBoxModel(listaCliente.toArray()));
		} else {
			cmbCli.setModel(new DefaultComboBoxModel());
		}
	}

	/* M�TODO QUE CARGA VENDEDORES SEG�N SUPERVISOR */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargaInicialVendedor() {
		@SuppressWarnings("unused")
		BeanEmpleado empleado = new BeanEmpleado();
		if (((BeanDato) datose.get(0)).getStrModo().equals(
				Constante.MODO_ONLINE)) {
			try {
				objSAP = new SPlanificacion();
				listaEmpleado = objSAP.listaVendedor(((BeanDato) datose.get(0)).getStrCodigo());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(e.getMessage());
			}
		} else {
			/*
			 * empleado.setStrIdSupervisor(((BeanDato)datose.get(0)).getStrCodigo
			 * ()); getEmpleado.setListaEmpleado(empleado); listaEmpleado = new
			 * ArrayList<BeanEmpleado>(); listaEmpleado =
			 * getEmpleado.getListaEmpleado();
			 */
		}
		if (listaEmpleado != null && listaEmpleado.size() > 0) {
			cmbVen.setModel(new DefaultComboBoxModel(listaEmpleado.toArray()));
		} else {
			cmbVen.setModel(new DefaultComboBoxModel());
		}
	}

	/* M�TODO QUE CARGA CLIENTES SEG�N CAMBIO DE VENDEDOR */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargaCliente() {
		BeanEmpleado be = (BeanEmpleado) cmbVen.getSelectedItem();
		if (((BeanDato) datose.get(0)).getStrModo().equals(
				Constante.MODO_ONLINE)) {
			try {
				objSAP = new SPlanificacion();
				listaCliente = objSAP.listaClientePlanificacion(be.getStrIdEmpleado());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(e.getMessage());
			}
		} else {
			/*
			 * BeanCliente cliente = new BeanCliente(); BeanClienteEmpleado
			 * clienteEmpleado = new BeanClienteEmpleado();
			 * clienteEmpleado.setStrIdEmpleado(be.getStrIdEmpleado());
			 * cliente.setClienteEmpleado(clienteEmpleado);
			 * getCliente.setListaCliente(cliente); listaCliente = new
			 * ArrayList<BeanCliente>(); listaCliente =
			 * getCliente.getListaCliente();
			 */
		}
		if (listaCliente != null && listaCliente.size() > 0) {
			cmbCli.setModel(new DefaultComboBoxModel(listaCliente.toArray()));
		} else {
			cmbCli.setModel(new DefaultComboBoxModel());
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

	/* M�TODO QUE EXPORTA A EXCEL */
	@SuppressWarnings("static-access")
	public void exportar() {
		if (rdbPlanificacion.isSelected()) {
			if (modeloTabla.getRowCount() > 0) {
				javax.swing.JFileChooser jF1 = new javax.swing.JFileChooser();
				String ruta = "";
				try {
					if (jF1.showSaveDialog(null) == jF1.APPROVE_OPTION) {
						ruta = jF1.getSelectedFile().getAbsolutePath();
						if (fillData(tblPlanificacion, new File(ruta + Constante.EXTENSION_ARCHIVO)))
							Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_EXPORTA + ruta + Constante.EXTENSION_ARCHIVO);
						else
							Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_EXPORTA_PLANIFICACION);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_NO_PLANIFICACION_EXPORTA);
			}
		} else {
			if (modeloTabla.getRowCount() > 0) {
				javax.swing.JFileChooser jF1 = new javax.swing.JFileChooser();
				String ruta = "";
				try {
					if (jF1.showSaveDialog(null) == jF1.APPROVE_OPTION) {
						ruta = jF1.getSelectedFile().getAbsolutePath();

						TablaAExcel objTE = new TablaAExcel();
						try {
							objTE.generarReporte("test02", modeloTabla);
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}

						if (fillData(tblVisita, new File(ruta + Constante.EXTENSION_ARCHIVO)))
							Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_EXPORTA + ruta + Constante.EXTENSION_ARCHIVO);
						else
							Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_EXPORTA_VISITA);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_NO_VISITA_EXPORTA);
			}
		}
	}

	boolean fillData(JTable table, File file) {
		boolean resultado;
		if (rdbPlanificacion.isSelected()) {
			try {
				WritableWorkbook workbook1 = Workbook.createWorkbook(file);
				WritableSheet sheet1 = workbook1.createSheet("Planificaciones", 0);
				TableModel model = table.getModel();
				for (int i = 0; i < model.getColumnCount(); i++) {
					Label column = new Label(i, 0, model.getColumnName(i));
					sheet1.addCell(column);
				}
				int j = 0;
				for (int i = 0; i < model.getRowCount(); i++) {
					for (j = 0; j < model.getColumnCount(); j++) {
						if (model.getValueAt(i, j) != null) {
							Label row = new Label(j, i + 1, model.getValueAt(i, j).toString());
							sheet1.addCell(row);
						}
					}
				}
				workbook1.write();
				workbook1.close();
				resultado = true;
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(e.getMessage());
				resultado = false;
			}
		} else {
			try {
				WritableWorkbook workbook1 = Workbook.createWorkbook(file);
				WritableSheet sheet1 = workbook1.createSheet("Visitas", 0);
				TableModel model = table.getModel();
				for (int i = 0; i < model.getColumnCount(); i++) {
					Label column = new Label(i, 0, model.getColumnName(i));
					sheet1.addCell(column);
				}
				int j = 0;
				for (int i = 0; i < model.getRowCount(); i++) {
					for (j = 0; j < model.getColumnCount(); j++) {
						if (model.getValueAt(i, j) != null) {
							Label row = new Label(j, i + 1, model.getValueAt(i, j).toString());
							sheet1.addCell(row);
						}
					}
				}
				workbook1.write();
				workbook1.close();
				resultado = true;
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(e.getMessage());
				resultado = false;
			}
		}
		return resultado;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == cboVen) {
			if (cboVen.isSelected()) {
				cmbVen.setEnabled(false);
				cmbCli.setEnabled(false);
			} else {
				cmbVen.setEnabled(true);
				cmbCli.setEnabled(true);
			}
		}
		if (arg0.getSource() == rdbPlanificacion) {
			if (rdbPlanificacion.isSelected())
				rdbVisita.setSelected(false);
			tablaVaciaPlanificacion();
		}
		if (arg0.getSource() == rdbVisita) {
			if (rdbVisita.isSelected())
				rdbPlanificacion.setSelected(false);
			tablaVaciaVisita();
		}
		if (arg0.getSource() == btnBuscar) {
			/* IMPLEMEMTA FUNCIONALIDAD BUSCAR */
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					busca();
					bloqueador.dispose();
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
		if (arg0.getSource() == btnExportar) {
			/* IMPLEMEMTA FUNCIONALIDAD EXPORTAR */
			exportar();
		}
	}
}