package com.promesa.internalFrame.sisinfo;

import java.awt.GridLayout;
//import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.itextpdf.text.Element;
import com.promesa.bean.BeanDato;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.promesa.main.Promesa;
import com.promesa.impresiones.ReportePDFCambioDePrecio;
import com.promesa.internalFrame.sisinfo.custom.RenderizadorTablaIndicadoresVendedor;
import com.promesa.sap.SSistemasInformacion;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.TablaAExcel;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class ICambioPrecio extends javax.swing.JInternalFrame {

	// private DefaultTableModel modelo;
	private List<String[]> reporteCambiosDePrecioVendedor = null;
	//	Marcelo Moyano
	//	Atributos Modelo y render
	//	para el jTable 01/03/2013 - 11:40
	private DefaultTableModel modelo;
	private RenderizadorTablaIndicadoresVendedor render;
	//	Marcelo Moyano

	public ICambioPrecio() {
		initComponents();
		txtCanalDistribucion.setText(Util.obtenerCanalPromesa());
		txtOrgVentas.setText(Util.obtenerSociedadPromesa());
		dateInicio.setDate(new Date());
		dateFin.setDate(new Date());
		datefecha.setDate(new Date());
		tblReporte.setContentType("text/html");
		tblReporte.setEditable(false);
		
		//Marcelo Moyano
		// 01/03/2013 - 11:48
		render = new RenderizadorTablaIndicadoresVendedor();
		modelo = new DefaultTableModel(){	
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		jtblReporte.setDefaultRenderer(Object.class, render);
		jtblReporte.setModel(modelo);
		jtblReporte.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);
		jtblReporte.setRowHeight(Constante.ALTO_COLUMNAS);
		
		//	Marcelo Moyano
		//	Requerimiento: PRO-2013-0015
		//	Titulo: Agregar fecha de Ing Local (Correccion)
		//	Solicitante: Renato Flores 
		cargarReporteIndicadores(txtOrgVentas.getText(), txtCanalDistribucion.getText(), Util.convierteFechaAFormatoYYYYMMdd(dateInicio.getDate()),
				 Util.convierteFechaAFormatoYYYYMMdd(dateFin.getDate()), Util.convierteFechaAFormatoYYYYMMdd(datefecha.getDate()));
	}

	private String generarReporteEnFormatoHTML(List<String[]> reporteSinFormato) {
		String html = "<html><table border='0' witdh='100%'><tr><td align='center' colspan='7'>"
				+ "<b>CAMBIOS&#47;INGRESO DE MERCADERÍA</b></td></tr>";
		html += "<tr><td align='center' colspan='7'><b>"
				+ Util.getFechaActualDDMMYYYY(dateInicio.getDate()) + "-"
				+ Util.getFechaActualDDMMYYYY(dateFin.getDate()) + "</b></td></tr>";
		html += "<tr><th align='left'>Código</th><th align='left'>Descripción</th>" +
				"<th>Und</th><th>Prec. Actual</th><th>Observación</th><th>Validez Inicio</th>" + "<th>Marca</th></tr>";
		
		String division = "";
		String categoria = "";
		String familia = "";
		String linea = "";
		String grupo = "";
		String divisionTemporal = "¬";
		String categoriaTemporal = "¬";
		String familiaTemporal = "¬";
		String lineaTemporal = "¬";
		String grupoTemporal = "¬";
		if(reporteSinFormato != null){//Nelson
		for (String[] strings : reporteSinFormato) {
			divisionTemporal = strings[8];
			categoriaTemporal = strings[9];
			familiaTemporal = strings[10];
			lineaTemporal = strings[11];
			grupoTemporal = strings[12];
			if (divisionTemporal.compareTo(division) != 0) {
				division = divisionTemporal;
				html += "<tr><td align='left' colspan='7'><b>" + division + "</b></td></tr>";
			}
			if (categoriaTemporal.compareTo(categoria) != 0) {
				categoria = categoriaTemporal;
				html += "<tr><td align='left' colspan='7'><b>" + categoria + "</b></td></tr>";
			}
			if (familiaTemporal.compareTo(familia) != 0) {
				familia = familiaTemporal;
				html += "<tr><td align='left'><b>" + strings[18] 
				        + "</b></td><td align='left' colspan='6'><b><u>" + familia 
				        + "</u></b></td></tr>";
			}
			if (lineaTemporal.compareTo(linea) != 0) {
				linea = lineaTemporal;
				html += "<tr><td align='left'><b>" + strings[19] 
				        + "</b></td><td align='left' colspan='6'><b><u>" + linea 
				        + "</u></b></td></tr>";
			}
			if (grupoTemporal.compareTo(grupo) != 0) {
				grupo = grupoTemporal;
				html += "<tr><td align='left'><b>" + strings[20] 
				        + "</b></td><td align='left' colspan='6'><b><u>" + grupo 
				        + "</u></b></td></tr>";
			}
			html += "<tr align='center'><td align='left'>" + strings[21] 
			        + "</td><td align='left'>"
					+ strings[22] + "</td><td>" + strings[36]
					+ "</td><td align='right'>" + strings[33] + "</td><td>"
					+ strings[1] + "</td><td>" + strings[30] + "</td><td>"
					+ strings[39] + "</td></tr>";
		}
		}//Nelson
		html += "</table></html>";
		return html;
	}
	//	Marcelo Moyano
	//	Cambio hecho al metodo cargarReporteIndicadores
	//	para Cargar los datos
	//	01/03/2013 - 14:19
	//	Requerimiento: PRO-2013-0015
	//	Titulo: Agregar fecha de Ing Local (Correccion)
	//	Solicitante: Renato Flores 
	private void cargarReporteIndicadores(final String orgVentas, final String canalDistribucion, final String fechaInicio, final String fechaFinal, final String strdatefecha){
//			final String fechaFinal){
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1"))
		{	
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread()
			{
				public void run()
				{
					SSistemasInformacion si = new SSistemasInformacion();
					reporteCambiosDePrecioVendedor = si.obtenerIndicadoresCambioPrecio(orgVentas, canalDistribucion, fechaInicio, fechaFinal, strdatefecha);
					tblReporte.setText(generarReporteEnFormatoHTML(reporteCambiosDePrecioVendedor));
					
					//	Marcelo Moyano
					//	01/03/2013 - 14:55
					String[] nombresColumnas = { "CÓDIGO", "DESCRIPCIÓN","UND", "PREC. ACTUAL", "OBSERVACIÓN", "VALIDEZ I", "MARCA" };
					 modelo.setRowCount(0);
					 modelo.setColumnIdentifiers(nombresColumnas);
					 for (String[] strings : reporteCambiosDePrecioVendedor){
						 String[] celdas = new String[40];
						 for (int i = 1, j = 0; i < strings.length; i++, j++) {
							 celdas[j] = strings[i];
						 }
						 modelo.addRow(celdas);
					 }
					//Marcelo Moyano
					bloqueador.dispose();
			}//Fin de metodo run
		};//Fin de Thread hilo
		hilo.start();
		bloqueador.setVisible(true);// Fin de if 
		}else{
			Mensaje.mostrarAviso("Consulta Cambio de Precio Requiere Conexión Online");
		}	
	}//	Fin del metodo cargarReporteIndicadores 

	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		mnuOpciones = new javax.swing.JToolBar();
		btnPDF = new javax.swing.JButton();
		separador1 = new javax.swing.JToolBar.Separator();
		btnExcel = new javax.swing.JButton();
		separador2 = new javax.swing.JToolBar.Separator();
		btnCancelar = new javax.swing.JButton();
		contenedorPrincipal = new javax.swing.JPanel();
//		contenedorPrincipal = new javax.swing.JPanel(new GridLayout());
		pnlParametrosBusqueda = new javax.swing.JPanel();
//		pnlParametrosBusqueda = new javax.swing.JPanel(new GridLayout(3,6));
		lblAnio = new javax.swing.JLabel();
		dateInicio = new com.toedter.calendar.JDateChooser();
		lblMes = new javax.swing.JLabel();
		dateFin = new com.toedter.calendar.JDateChooser();
		lblOrgVentas = new javax.swing.JLabel();
		txtOrgVentas = new javax.swing.JTextField();
		lblCanalDistribucion = new javax.swing.JLabel();
		txtCanalDistribucion = new javax.swing.JTextField();
		btnBuscar = new javax.swing.JButton();
		scrReporte = new javax.swing.JScrollPane();
		tblReporte = new javax.swing.JEditorPane();
		//	Marcelo Moyano
		//	01/03/2013 - 12:01
		//	Requerimiento: PRO-2013-0015
		//	Titulo: Agregar fecha de Ing Local (Correccion)
		//	Solicitante: Renato Flores  
		jtblReporte = new javax.swing.JTable();
		lblfecha = new javax.swing.JLabel();
		datefecha = new com.toedter.calendar.JDateChooser();
		// Marcelo Moyano

		setClosable(true);
		setForeground(java.awt.Color.white);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Consulta Cambio de Precios");

		mnuOpciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		mnuOpciones.setFloatable(false);
		mnuOpciones.setRollover(true);

		btnPDF.setText("Ver en pdf");
		btnPDF.setFocusable(false);
		btnPDF.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnPDF.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnPDF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPDFActionPerformed(evt);
			}
		});
		mnuOpciones.add(btnPDF);
		mnuOpciones.add(separador1);

		//	Marcelo Moyano
		//	Boton Ver en Excel
		//	01/03/2013 - 11:36
		btnExcel.setText("Ver en excel");
		btnExcel.setFocusable(false);
		btnExcel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnExcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnExcel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExcelActionPerformed(evt);
			}
		});
		mnuOpciones.add(btnExcel);
		mnuOpciones.add(separador2);
		//	Marcelo Moyano
		
		btnCancelar.setText("Cancelar");
		btnCancelar.setFocusable(false);
		btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelarActionPerformed(evt);
			}
		});
		mnuOpciones.add(btnCancelar);

		getContentPane().add(mnuOpciones, java.awt.BorderLayout.PAGE_START);

		contenedorPrincipal.setLayout(new java.awt.BorderLayout());

		pnlParametrosBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder("Parámetros de búsqueda"));
		pnlParametrosBusqueda.setLayout(new GridLayout(0,6,10,5));

		lblAnio.setText("Inicio:");
		lblAnio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlParametrosBusqueda.add(lblAnio);
		pnlParametrosBusqueda.add(dateInicio);

		lblMes.setText("Fin:");
		lblMes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlParametrosBusqueda.add(lblMes);
		pnlParametrosBusqueda.add(dateFin);

		lblOrgVentas.setText("Org. de ventas:");
		lblOrgVentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlParametrosBusqueda.add(lblOrgVentas);

		txtOrgVentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtOrgVentas.setMinimumSize(new java.awt.Dimension(30, 20));
		txtOrgVentas.setPreferredSize(new java.awt.Dimension(70, 20));
		txtOrgVentas.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtOrgVentas){
					txtOrgVentas.selectAll();
				}
			}
		});
		pnlParametrosBusqueda.add(txtOrgVentas);

		lblCanalDistribucion.setText("Canal de distribución:");
		lblCanalDistribucion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlParametrosBusqueda.add(lblCanalDistribucion);

		txtCanalDistribucion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCanalDistribucion.setMinimumSize(new java.awt.Dimension(30, 20));
		txtCanalDistribucion.setPreferredSize(new java.awt.Dimension(70, 20));
		txtCanalDistribucion.addFocusListener(new java.awt.event.FocusAdapter(){
			public void focusGained(java.awt.event.FocusEvent evt){
				if(evt.getSource() == txtCanalDistribucion){
					txtCanalDistribucion.selectAll();
				}
			}
		});
		pnlParametrosBusqueda.add(txtCanalDistribucion);
		
		lblfecha.setText("Fecha Movimiento: ");// Marcelo Moyano 18/04/2013 - 14:49
		lblfecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlParametrosBusqueda.add(lblfecha);// Agregar fecha de Ing Local (Correccion)
		pnlParametrosBusqueda.add(datefecha);//	codigo: PRO-2013-0015
		
		btnBuscar.setText("Buscar");
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});
		pnlParametrosBusqueda.add(new javax.swing.JLabel(""));
		pnlParametrosBusqueda.add(btnBuscar);
		
		contenedorPrincipal.add(pnlParametrosBusqueda, java.awt.BorderLayout.PAGE_START);

		//	Marcelo Moyano
		//	01/03/2013 - 12:09
		 jtblReporte.setModel(new javax.swing.table.DefaultTableModel(new Object[][] 
		          { { null, null, null, null },{ null, null, null, null },
				    { null, null, null, null },{ null, null, null, null },
					{ null, null, null, null },{ null, null, null, null },
					{ null, null, null, null } }, new String[] { "Title 1","Title 2", 
				    "Title 3", "Title 4", "Title5", "Title 6", "Title 7" }));
		// Marcelo Moyano
		scrReporte.setViewportView(tblReporte);//original
		contenedorPrincipal.add(scrReporte, java.awt.BorderLayout.CENTER);
		getContentPane().add(contenedorPrincipal, java.awt.BorderLayout.CENTER);
		pack();
	}// </editor-fold>

	private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {
		 final DLocker bloqueador = new DLocker();
		 	Thread hilo = new Thread() {
		 		public void run() {
		 			try {
		 				ReportePDFCambioDePrecio rcp = new
		 				ReportePDFCambioDePrecio(reporteCambiosDePrecioVendedor);
		 				rcp.generarReporteCambioDePrecioporFecha(Util.getFechaActualDDMMYYYY(dateInicio.getDate()), Util.getFechaActualDDMMYYYY(dateFin.getDate()));
		 			} catch (Exception e) {
		 				e.printStackTrace();
		 			} finally {
		 				bloqueador.dispose();
		 			}
		 		}
		 	};
		 	hilo.start();
		 	bloqueador.setVisible(true);
	}

	//	Metodo para generar reporte Excel de cambio de precio
	private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {	
//		 final DLocker bloqueador = new DLocker();
//		 Thread hilo = new Thread() {
//		 public void run() {
//		 try {
//		 if(reporteCambiosDePrecioVendedor.size() > 0){
//		 TablaAExcel tablaAExcel = new TablaAExcel();
//		 tablaAExcel.generarReporteExcelCambioDePrecioVendedor("cambio_precio_vendedor_"
//		 + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date())
//		 + ".xls", tblReporte.getModel());
//		 }
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 } finally {
//		 bloqueador.dispose();
//		 }
//		 }
//		 };
//		 hilo.start();
//		 bloqueador.setVisible(true);
		
		//	Marcelo Moyano
		//	Generación de Reporte de Cambio de Precio Excel
		//	01/03/2013
		 final DLocker bloqueador = new DLocker();
		 Thread hilo = new Thread(){
			 public void run(){
				 try{
					 if(reporteCambiosDePrecioVendedor.size() > 0){
						 TablaAExcel tablaAExcel = new TablaAExcel();
						 tablaAExcel.generarReporteExcelCambioDePrecioVendedor
						 			("cambio_precio_vendedor_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS
						 					(new Date()) + ".xls", Util.getFechaActualDDMMYYYY(dateInicio.getDate()), 
						 					Util.getFechaActualDDMMYYYY(dateFin.getDate()), reporteCambiosDePrecioVendedor);
		             }
					 //Marcelo Moyano
					 else{
						 Mensaje.mostrarAviso("No Existen Datos de Cambio de Precio");
					 }
				 }catch (Exception e){
					 e.printStackTrace();
				 }
				 finally{
					 bloqueador.dispose();
				 }
			 }//Fin del metodo run
		 };//Fin del hilo Thread
		 hilo.start();
		 bloqueador.setVisible(true);
//		//	Marcelo Moyano
	}//Fin del metodo btnExcelActionPerformed

	private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		int tipo = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la ventana?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (tipo == JOptionPane.OK_OPTION) {
			this.dispose();
		}
	}

	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					int orgVentas = Integer.parseInt(txtOrgVentas.getText());
				} catch (NumberFormatException e) {
					Mensaje.mostrarError("La Org. de ventas debe de ser un número entero.");
					return;
				}
				try {
					@SuppressWarnings("unused")
					int canalDistribucion = Integer.parseInt(txtCanalDistribucion.getText());
				} catch (NumberFormatException e) {
					Mensaje.mostrarError("El canal de distribución debe de ser un número entero.");
					return;
				}
				if (dateInicio.getDate() == null) {
					Mensaje.mostrarError("Debe seleccionar correctamente la fecha de inicio.");
					return;
				}
				if (dateFin.getDate() == null) {
					Mensaje.mostrarError("Debe seleccionar correctamente la fecha de fin.");
					return;
				}
				//	Marcelo Moyano
				//	Requerimiento: PRO-2013-0015
				//	Titulo: Agregar fecha de Ing Local (Correccion)
				//	Solicitante: Renato Flores 
				cargarReporteIndicadores(txtOrgVentas.getText(),txtCanalDistribucion.getText(), Util.convierteFechaAFormatoYYYYMMdd(dateInicio.getDate()),
								Util.convierteFechaAFormatoYYYYMMdd(dateFin.getDate()), Util.convierteFechaAFormatoYYYYMMdd(datefecha.getDate()));
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnCancelar;
	private javax.swing.JButton btnExcel;
	private javax.swing.JButton btnPDF;
	private javax.swing.JPanel contenedorPrincipal;
	private com.toedter.calendar.JDateChooser dateFin;
	private com.toedter.calendar.JDateChooser dateInicio;
	private javax.swing.JLabel lblAnio;
	private javax.swing.JLabel lblCanalDistribucion;
	private javax.swing.JLabel lblMes;
	private javax.swing.JLabel lblOrgVentas;
	private javax.swing.JToolBar mnuOpciones;
	private javax.swing.JPanel pnlParametrosBusqueda;
	private javax.swing.JScrollPane scrReporte;
	private javax.swing.JToolBar.Separator separador1;
	private javax.swing.JToolBar.Separator separador2;
	private javax.swing.JEditorPane tblReporte;
	private javax.swing.JTextField txtCanalDistribucion;
	private javax.swing.JTextField txtOrgVentas;
	private javax.swing.JTable jtblReporte;
	private javax.swing.JLabel lblfecha;//MArcelo Moyano Agregar fecha de Ing Local (Correccion)
	private com.toedter.calendar.JDateChooser datefecha;//	codigo: PRO-2013-0015
}