package com.promesa.internalFrame.sisinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.promesa.bean.BeanDato;
import com.promesa.impresiones.ReportePDFSistemaInformacion;
import com.promesa.internalFrame.sisinfo.custom.RenderizadorTablaIndicadoresDetalleSupervisor;
import com.promesa.internalFrame.sisinfo.custom.RenderizadorTablaIndicadoresSupervisor;
import com.promesa.main.Promesa;
import com.promesa.sap.SSistemasInformacion;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.TablaAExcel;
import com.promesa.util.Util;
import com.promesa.util.Mensaje;

@SuppressWarnings("serial")
public class IIndicadoresSupervisor extends javax.swing.JInternalFrame {

	private RenderizadorTablaIndicadoresSupervisor render;
	private DefaultTableModel modelo;
	private List<String[]> reporteIndicadoresSupervisor = null;
	private List<String[]> reporteIndicadoresDetalleSupervisor = null;
	private RenderizadorTablaIndicadoresDetalleSupervisor renderDetalle;
	private DefaultTableModel modeloDetalle;

	/** Creates new form IndicadoresSupervisor */
	public IIndicadoresSupervisor() {
		initComponents();
		reporteIndicadoresSupervisor = new ArrayList<String[]>();
		reporteIndicadoresDetalleSupervisor = new ArrayList<String[]>();
		// ******************************************************
		render = new RenderizadorTablaIndicadoresSupervisor();
		modelo = new DefaultTableModel() {
			
			public boolean isCellEditable(int row, int column) {
				// only columns 0 and 1 are editable
				return false;
			}
		};
		tblIndicadores.setDefaultRenderer(Object.class, render);
		tblIndicadores.setModel(modelo);
		tblIndicadores.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);
		tblIndicadores.setRowHeight(Constante.ALTO_COLUMNAS);
		cargarReporteIndicadores();
		// ******************************************************
		renderDetalle = new RenderizadorTablaIndicadoresDetalleSupervisor();
		modeloDetalle = new DefaultTableModel() {
			
			public boolean isCellEditable(int row, int column) {
				// only columns 0 and 1 are editable
				return false;
			}
		};
		tblDetalle.setDefaultRenderer(Object.class, renderDetalle);
		tblDetalle.setModel(modeloDetalle);
		tblDetalle.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);
		tblDetalle.setRowHeight(Constante.ALTO_COLUMNAS);
		// ******************************************************
	}
	
    private void initComponents() {

        mnuOpciones = new javax.swing.JToolBar();
        btnPDF = new javax.swing.JButton();
        separador1 = new javax.swing.JToolBar.Separator();
        btnExcel = new javax.swing.JButton();
        separador2 = new javax.swing.JToolBar.Separator();
        btnCancelar = new javax.swing.JButton();
        contenedorPrincipal = new javax.swing.JPanel();
        pnlParametrosBusqueda = new javax.swing.JPanel();
        lblAnio = new javax.swing.JLabel();
        selectorAnio = new com.toedter.calendar.JYearChooser();
        lblMes = new javax.swing.JLabel();
        selectorMes = new com.toedter.calendar.JMonthChooser();
        btnBuscar = new javax.swing.JButton();
        tbpContenedor = new javax.swing.JTabbedPane();
        srcIndicadores = new javax.swing.JScrollPane();
        tblIndicadores = new javax.swing.JTable();
        scrDetalle = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();

        setClosable(true);
        setForeground(java.awt.Color.white);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Indicadores por Vendedor");

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
        pnlParametrosBusqueda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblAnio.setText("Año:");
        pnlParametrosBusqueda.add(lblAnio);
        pnlParametrosBusqueda.add(selectorAnio);

        lblMes.setText("Mes:");
        pnlParametrosBusqueda.add(lblMes);
        pnlParametrosBusqueda.add(selectorMes);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pnlParametrosBusqueda.add(btnBuscar);

        contenedorPrincipal.add(pnlParametrosBusqueda, java.awt.BorderLayout.PAGE_START);

        srcIndicadores.setBorder(null);

        tblIndicadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        srcIndicadores.setViewportView(tblIndicadores);

        tbpContenedor.addTab("Indicadores", srcIndicadores);

        scrDetalle.setBorder(null);

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrDetalle.setViewportView(tblDetalle);

        tbpContenedor.addTab("Detalles", scrDetalle);

        contenedorPrincipal.add(tbpContenedor, java.awt.BorderLayout.CENTER);

        getContentPane().add(contenedorPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>

	
	private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					int panelSeleccionado = tbpContenedor.getSelectedIndex();
					switch(panelSeleccionado) {
						case 0:
							ReportePDFSistemaInformacion ris = new ReportePDFSistemaInformacion(reporteIndicadoresSupervisor, 3);
							ris.generarReporteIndicadoresSupervisor();
							break;
						case 1:
							ReportePDFSistemaInformacion rids = new ReportePDFSistemaInformacion(reporteIndicadoresDetalleSupervisor, 4);
							rids.generarReporteIndicadoresDetalleSupervisor();
							break;
					}
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

	private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					int panelSeleccionado = tbpContenedor.getSelectedIndex();
					switch(panelSeleccionado) {
						case 0:
							if(reporteIndicadoresSupervisor.size() > 0){
								TablaAExcel tablaAExcel = new TablaAExcel();
								tablaAExcel.generarReporteExcelSistemaInformacion("indicadores_supervisor_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".xls", tblIndicadores.getModel());
							}
							break;
						case 1:
							if(reporteIndicadoresDetalleSupervisor.size() > 0){
								TablaAExcel tablaAExcel = new TablaAExcel();
								tablaAExcel.generarReporteExcelSistemaInformacion("indicadores_detalle_supervisor_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".xls", tblDetalle.getModel());
							}
							break;
					}
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

	private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		int tipo = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la ventana?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (tipo == JOptionPane.OK_OPTION) {
			this.dispose();
		}
	}

	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		int panelSeleccionado = tbpContenedor.getSelectedIndex();
		switch(panelSeleccionado) {
			case 0:
				cargarReporteIndicadores();
				break;
			case 1:
				cargarReporteDetalle();
				break;
		}
	}

	private void cargarReporteIndicadores() {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				int mes = selectorMes.getMonth() + 1;
				int anio = selectorAnio.getYear();
				String codigoUsuario = Promesa.datose.get(0).getStrCodigo();
				SSistemasInformacion ss = new SSistemasInformacion();
				reporteIndicadoresSupervisor = ss.obtenerIndicadoresSupervisor(mes, anio, codigoUsuario);
				String[] nombresColumnas = { "CÓDIGO KPI", "DESCRIPCIÓN", "REAL", "OBJETIVO", "VALOR" };
				modelo.setRowCount(0);
				modelo.setColumnIdentifiers(nombresColumnas);
				if(reporteIndicadoresSupervisor != null){
				for (String[] strings : reporteIndicadoresSupervisor) {
					String[] celdas = new String[5];
					for (int i = 1, j = 0; i < strings.length; i++, j++) {
						celdas[j] = strings[i];
					}
					modelo.addRow(celdas);
				}
				}
				bloqueador.dispose();
				Util.setAnchoColumnas(tblIndicadores);
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
		} 
		else {
		Mensaje.mostrarAviso("Consulta de Indicadores Requiere Conexión Online");
		}			
	}
	
	private void cargarReporteDetalle() {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {
			
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				int mes = selectorMes.getMonth() + 1;
				int anio = selectorAnio.getYear();
				String codigoUsuario = Promesa.datose.get(0).getStrCodigo();
				SSistemasInformacion ss = new SSistemasInformacion();
				reporteIndicadoresDetalleSupervisor = ss.obtenerIndicadoresDetalleSupervisor(mes, anio, codigoUsuario);
				String[] nombresColumnas = { "CÓD. VEND.", "VEND.",
						"VENT. NETA (IMP. REAL)", "VENT. NETA (IMP. OBJ.)", "VENT. NETA",
						"FLIA. BÁS. (IMP. REAL)", "FLIA. BÁS. (IMP. OBJ.)", "FLIA. BÁS.",
						"FLIA. OBJ. (IMP. REAL)", "FLIA. OBJ. (IMP. OBJ.)", "FLIA. OBJ.",
						"CBT. CLI. (IMP. REAL)", "CBT. CLI. (IMP. OBJ.)", "CBT. CLI.",
						"COB. (IMP. REAL)", "COB. (IMP. OBJ.)", "COB.",
						"DEV. PENAL. (IMP. REAL)", "DEV. PENAL. (IMP. OBJ.)", "DEV. PENAL.",
						"VENT. CÓD. R. (IMP. REAL)", "VENT. CÓD. R. (IMP. OBJ.)", "VENT. CÓD. R.",
						"RIT. DE VENT. (IMP. REAL)", "RIT. DE VENT. (IMP. OBJ.)", "RIT. DE VENT."};
				modeloDetalle.setRowCount(0);
				modeloDetalle.setColumnIdentifiers(nombresColumnas);
				String[] totalCeldas = new String[26];
				double[] dTotalCelda = new double[26];
				
				if(reporteIndicadoresDetalleSupervisor != null && reporteIndicadoresDetalleSupervisor.size() > 0){
					String[] strReporte = new String[27];
					for (String[] strings : reporteIndicadoresDetalleSupervisor) {
						String[] celdas = new String[26];
						
						for (int i = 1, j = 0; i < strings.length; i++, j++) {
							double dTotal = 0d;
							celdas[j] = strings[i];
							if(j >=2){
								dTotal += Double.parseDouble(celdas[j]);
								dTotalCelda[j] += dTotal;
								totalCeldas[j] = "" + dTotalCelda[j];
								strReporte[i] = "" + dTotalCelda[j];
							}
						}
						modeloDetalle.addRow(celdas);
					}
					totalCeldas[0] = "Total:";
					totalCeldas[1] = "";
					strReporte[0] = "";
					strReporte[1] = "Total:";
					strReporte[2] = "";
					modeloDetalle.addRow(totalCeldas);
					reporteIndicadoresDetalleSupervisor.add(strReporte);
				}
				Util.setAnchoColumnas(tblDetalle);
				bloqueador.dispose();
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
		} 
		else {
		Mensaje.mostrarAviso("Consulta Detalle Requiere Conexión Online");
		}		
	}

	private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnPDF;
    private javax.swing.JPanel contenedorPrincipal;
    private javax.swing.JLabel lblAnio;
    private javax.swing.JLabel lblMes;
    private javax.swing.JToolBar mnuOpciones;
    private javax.swing.JPanel pnlParametrosBusqueda;
    private javax.swing.JScrollPane scrDetalle;
    private com.toedter.calendar.JYearChooser selectorAnio;
    private com.toedter.calendar.JMonthChooser selectorMes;
    private javax.swing.JToolBar.Separator separador1;
    private javax.swing.JToolBar.Separator separador2;
    private javax.swing.JScrollPane srcIndicadores;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblIndicadores;
    private javax.swing.JTabbedPane tbpContenedor;
}
