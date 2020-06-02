package com.promesa.internalFrame.cobranzas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.promesa.cobranzas.bean.BeanFacturaDetalle;
import com.promesa.cobranzas.bean.BeanFacturaHeader;
import com.promesa.internalFrame.cobranzas.custom.RenderDetalleFactura;
import com.promesa.sap.SFacturas;
import com.promesa.util.Constante;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class IDetalleFactura extends javax.swing.JInternalFrame {
	
	private BeanFacturaHeader cabeceraFactura = null;
	List<BeanFacturaDetalle> listaDetalle = null;

    public IDetalleFactura(BeanFacturaHeader facturaHeader) {
        initComponents();
        inhabilitarCampos();
        cabeceraFactura = facturaHeader;
        ((DefaultTableCellRenderer) tblDetalleFactura.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        tblDetalleFactura.setRowHeight(Constante.ALTO_COLUMNAS);
        txtCliente.setText(cabeceraFactura.getCodigocliente() + " - " + cabeceraFactura.getNombreCliente());
        txtFacturaSap.setText(cabeceraFactura.getNumeroFacturaSap());
        txtFacturaSRI.setText(cabeceraFactura.getNumeroFacturaSRI());
        txtFechaFactura.setText(cabeceraFactura.getFechaFactura());
        txtTotalFactura.setText(cabeceraFactura.getValorTotal());
        lblInformacionGeneral.setVisible(false);
        buscarDetalleFactura();
    }

    private void buscarDetalleFactura() {
    	listaDetalle = new ArrayList<BeanFacturaDetalle>();
		SFacturas sf = new SFacturas();
		listaDetalle = sf.obtenerDetalleFactura(Util.completarCeros(10, cabeceraFactura.getNumeroFacturaSap()));
		if(listaDetalle == null || listaDetalle.size() == 0)
	        tablaVacia();
		else
			llenarTabla();
	}

	private void llenarTabla() {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "", "POS.", "MATERIAL", "DESCRIPCION", "CANT. PEDIDO", "CANT. FAC.", "PREC. UNIT.",
						"DIS. CANAL", "DESC. 3X", "DESC. 4X", "DESC. VOLUMEN", "DESC. N°. COD.", "DESC. MANUAL", "VALOR NETO", "PREC. UNIT. FAC."};
				DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				
				tblTablaModel.setNumRows(0);
				for (int i = 0; i < listaDetalle.size(); i++) {
					BeanFacturaDetalle fd = listaDetalle.get(i);
					String cantidadPedido = "0";
					if(!fd.getCantidadPedido().equals("0")){
						String strCantidadPedido = fd.getCantidadPedido().trim();
						cantidadPedido = strCantidadPedido.substring(0, strCantidadPedido.indexOf("."));
					}
					String cantidadFactura = "0";
					if(!fd.getCantidadFactura().equals("0")){
						String strCantidadFactura = fd.getCantidadFactura().trim();
						cantidadFactura = strCantidadFactura.substring(0, strCantidadFactura.indexOf("."));
					}
					
					String pos = Util.eliminarCerosInicios(fd.getPosicion());
					String material = Util.eliminarCerosInicios(fd.getCodigoMaterial().trim());
					
					String[] values = { "",/* 00 */ 
							"" + pos, //fd.getPosicion(),//	01
							"" + material, //fd.getCodigoMaterial(),// 02
							"" + fd.getDescripcion(),// 03
							"" + cantidadPedido, // fd.getCantidadPedido(),// 04
							"" + cantidadFactura, // fd.getCantidadFactura(),// 05
							"" + Util.formatearNumero(fd.getPrecioUnitario()),// 06
							"" + fd.getDescuentoCanal(),//07
							"" + fd.getDescuentos3X(),//08
							"" + fd.getDescuentos4X(),//09
							"" + fd.getDescuentoVolumen(),// 10
							"" + fd.getDescuentoCodigo(),// 11
							"" + fd.getDescuentoManual(),// 12
							"" + fd.getValorNeto(),// 13
							"" + Util.formatearNumero(fd.getPrecioNeto()) };// 14
					tblTablaModel.addRow(values);
				}
				RenderDetalleFactura render = new RenderDetalleFactura();
				tblDetalleFactura.setDefaultRenderer(Object.class, render);
				tblDetalleFactura.setModel(tblTablaModel);
				tblDetalleFactura.updateUI();
				Util.setAnchoColumnas(tblDetalleFactura);
				setAnchoColumnas();
			}
		});
	}
	
	private void setAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblDetalleFactura.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblDetalleFactura.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}

	private void initComponents() {
        pnlBackground = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlContenedor = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        separador = new javax.swing.JSeparator();
        pnlInformacion = new javax.swing.JPanel();
        lblInformacionGeneral = new javax.swing.JLabel();
        pnlInformacionGeneral = new javax.swing.JPanel();
        pnlUno = new javax.swing.JPanel();
        pnlLabelsUno = new javax.swing.JPanel();
        lblCliente = new javax.swing.JLabel();
        lblFacturaSap = new javax.swing.JLabel();
        pnlTextsUno = new javax.swing.JPanel();
        txtCliente = new javax.swing.JTextField();
        pnlVtaMercaderia = new javax.swing.JPanel();
        txtFacturaSap = new javax.swing.JTextField();
        pnlBloqEntrega = new javax.swing.JPanel();
        pnlTres = new javax.swing.JPanel();
        pnlTextsTres = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtFechaFactura = new javax.swing.JTextField();
        txtTotalFactura = new javax.swing.JTextField();
        pnlLabels3 = new javax.swing.JPanel();
        lblFechaFactura = new javax.swing.JLabel();
        lblTotalFactura = new javax.swing.JLabel();
        pnlDos = new javax.swing.JPanel();
        pnlLabelsDos = new javax.swing.JPanel();
        lblNPedCliente = new javax.swing.JLabel();
        lblFacturaSRI = new javax.swing.JLabel();
        pnlTextsDos = new javax.swing.JPanel();
        lblnull = new javax.swing.JLabel();
        txtFacturaSRI = new javax.swing.JTextField();
        pnlTabla = new javax.swing.JPanel();
        scroller = new javax.swing.JScrollPane();
        tblDetalleFactura = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Detalle Factura - Promesa");

        pnlBackground.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18));
        pnlBackground.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        pnlContenedor.setLayout(new java.awt.BorderLayout());

        pnlDatos.setLayout(new java.awt.BorderLayout());
        pnlDatos.add(separador, java.awt.BorderLayout.PAGE_START);

        lblInformacionGeneral.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblInformacionGeneral.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacionGeneral.setText("Información General");

        pnlInformacionGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        pnlUno.setPreferredSize(new java.awt.Dimension(175, 75));
        pnlUno.setLayout(new java.awt.BorderLayout(5, 5));

        pnlLabelsUno.setPreferredSize(new java.awt.Dimension(90, 48));
        pnlLabelsUno.setLayout(new java.awt.GridLayout(3, 1));

        lblCliente.setText("Cliente:");
        pnlLabelsUno.add(lblCliente);

        lblFacturaSap.setText("<html>Factura SAP:<font color='red'> *</font></html>");
        pnlLabelsUno.add(lblFacturaSap);

        pnlUno.add(pnlLabelsUno, java.awt.BorderLayout.LINE_START);

        pnlTextsUno.setPreferredSize(new java.awt.Dimension(60, 76));
        pnlTextsUno.setLayout(new java.awt.GridLayout(3, 1, 5, 5));
        pnlTextsUno.add(txtCliente);

        pnlVtaMercaderia.setLayout(new java.awt.BorderLayout());
        pnlVtaMercaderia.add(txtFacturaSap, java.awt.BorderLayout.CENTER);

        pnlTextsUno.add(pnlVtaMercaderia);

        pnlBloqEntrega.setLayout(new java.awt.BorderLayout());
        pnlTextsUno.add(pnlBloqEntrega);

        pnlUno.add(pnlTextsUno, java.awt.BorderLayout.CENTER);

        pnlTres.setPreferredSize(new java.awt.Dimension(175, 75));
        pnlTres.setLayout(new java.awt.BorderLayout(5, 5));

        pnlTextsTres.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel9.add(txtFechaFactura, java.awt.BorderLayout.PAGE_START);

        pnlTextsTres.add(jPanel9);
        pnlTextsTres.add(txtTotalFactura);

        pnlTres.add(pnlTextsTres, java.awt.BorderLayout.CENTER);

        pnlLabels3.setMinimumSize(new java.awt.Dimension(10, 0));
        pnlLabels3.setPreferredSize(new java.awt.Dimension(90, 70));
        pnlLabels3.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        lblFechaFactura.setText("Fecha Factura:");
        pnlLabels3.add(lblFechaFactura);

        lblTotalFactura.setText("Total Factura:");
        pnlLabels3.add(lblTotalFactura);

        pnlTres.add(pnlLabels3, java.awt.BorderLayout.LINE_START);

        pnlDos.setPreferredSize(new java.awt.Dimension(175, 75));
        pnlDos.setLayout(new java.awt.BorderLayout(5, 5));

        pnlLabelsDos.setMaximumSize(new java.awt.Dimension(90, 32767));
        pnlLabelsDos.setMinimumSize(new java.awt.Dimension(90, 42));
        pnlLabelsDos.setPreferredSize(new java.awt.Dimension(90, 100));
        pnlLabelsDos.setLayout(new java.awt.GridLayout(3, 1));

        lblNPedCliente.setPreferredSize(new java.awt.Dimension(60, 14));
        pnlLabelsDos.add(lblNPedCliente);

        lblFacturaSRI.setText("Factura SRI:");
        lblFacturaSRI.setPreferredSize(new java.awt.Dimension(60, 14));
        pnlLabelsDos.add(lblFacturaSRI);

        pnlDos.add(pnlLabelsDos, java.awt.BorderLayout.LINE_START);

        pnlTextsDos.setPreferredSize(new java.awt.Dimension(50, 70));
        pnlTextsDos.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        lblnull.setPreferredSize(new java.awt.Dimension(60, 14));
        pnlTextsDos.add(lblnull);
        pnlTextsDos.add(txtFacturaSRI);

        pnlDos.add(pnlTextsDos, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout pnlInformacionGeneralLayout = new javax.swing.GroupLayout(pnlInformacionGeneral);
        pnlInformacionGeneral.setLayout(pnlInformacionGeneralLayout);
        pnlInformacionGeneralLayout.setHorizontalGroup(pnlInformacionGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        	.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInformacionGeneralLayout.createSequentialGroup()
        	.addContainerGap().addComponent(pnlUno, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE).addGap(18, 18, 18)
        	.addComponent(pnlDos, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(27, 27, 27).addComponent(pnlTres, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
        );
        pnlInformacionGeneralLayout.setVerticalGroup( pnlInformacionGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionGeneralLayout.createSequentialGroup()
            .addContainerGap().addGroup(pnlInformacionGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlDos, 0, 0, Short.MAX_VALUE).addComponent(pnlTres, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
            .addContainerGap()));

        pnlTabla.setLayout(new java.awt.BorderLayout(5, 5));

        tblDetalleFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {{null, null, null, null}, {null, null, null, null},
            				{null, null, null, null}, {null, null, null, null}}, 
            				new String [] {"Title 1", "Title 2", "Title 3", "Title 4"}));
        tblDetalleFactura.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scroller.setViewportView(tblDetalleFactura);

        pnlTabla.add(scroller, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout pnlInformacionLayout = new javax.swing.GroupLayout(pnlInformacion);
        pnlInformacion.setLayout(pnlInformacionLayout);
        pnlInformacionLayout.setHorizontalGroup( pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionLayout.createSequentialGroup().addContainerGap()
            .addGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(lblInformacionGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
            .addComponent(pnlTabla, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
            .addComponent(pnlInformacionGeneral, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
        );
        pnlInformacionLayout.setVerticalGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionLayout.createSequentialGroup().addContainerGap().addComponent(lblInformacionGeneral)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(pnlInformacionGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE).addContainerGap())
        );
        pnlDatos.add(pnlInformacion, java.awt.BorderLayout.CENTER);
        pnlContenedor.add(pnlDatos, java.awt.BorderLayout.CENTER);
        pnlBackground.add(pnlContenedor, java.awt.BorderLayout.CENTER);
        getContentPane().add(pnlBackground, java.awt.BorderLayout.CENTER);
        pack();
    }
    
    public void tablaVacia() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "", "POS.", "MATERIAL", "DESCRIPCION", "CANT. PEDIDO", "CANT. FAC.", "PREC. UNIT.", "DIS. CANAL", "DESC. 3X", "DESC. 4X",
						"DESC. VOLUMEN", "DESC. N°. COD.", "DESC. MANUAL", "VALOR NETO", "PREC. UNIT. FAC."};
				DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				RenderDetalleFactura render = new RenderDetalleFactura();
				tblDetalleFactura.setDefaultRenderer(Object.class, render);
				tblDetalleFactura.setModel(tblTablaModel);
				tblDetalleFactura.updateUI();
				Util.setAnchoColumnas(tblDetalleFactura);
				setAnchoColumnas();
			}

		});
	}
    
    private void inhabilitarCampos() {
    	txtCliente.setEditable(false);
        txtFacturaSap.setEditable(false);
        txtFacturaSRI.setEditable(false);
        txtFechaFactura.setEditable(false);
        txtTotalFactura.setEditable(false);
    }
    
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblFacturaSRI;
    private javax.swing.JLabel lblFacturaSap;
    private javax.swing.JLabel lblFechaFactura;
    private javax.swing.JLabel lblInformacionGeneral;
    private javax.swing.JLabel lblNPedCliente;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalFactura;
    private javax.swing.JLabel lblnull;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlBloqEntrega;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlDos;
    private javax.swing.JPanel pnlInformacion;
    private javax.swing.JPanel pnlInformacionGeneral;
    private javax.swing.JPanel pnlLabels3;
    private javax.swing.JPanel pnlLabelsDos;
    private javax.swing.JPanel pnlLabelsUno;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JPanel pnlTextsDos;
    private javax.swing.JPanel pnlTextsTres;
    private javax.swing.JPanel pnlTextsUno;
    private javax.swing.JPanel pnlTres;
    private javax.swing.JPanel pnlUno;
    private javax.swing.JPanel pnlVtaMercaderia;
    private javax.swing.JScrollPane scroller;
    private javax.swing.JSeparator separador;
    private javax.swing.JTable tblDetalleFactura;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtFacturaSRI;
    private javax.swing.JTextField txtFacturaSap;
    private javax.swing.JTextField txtTotalFactura;
    private javax.swing.JTextField txtFechaFactura;
}