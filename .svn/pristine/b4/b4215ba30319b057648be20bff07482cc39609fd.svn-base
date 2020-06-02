package com.promesa.dialogo.cobranzas;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.promesa.cobranzas.bean.DatoCredito;
import com.promesa.internalFrame.cobranzas.custom.ModeloDatosCredito;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorDatosCredito;
import com.promesa.util.Constante;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class DialogoDetalleCartera extends javax.swing.JDialog {

    private ModeloDatosCredito modelo;
    private RenderizadorDatosCredito renderizadorDatosCredito;

    public DialogoDetalleCartera(java.awt.Frame parent, boolean modal, String titulo, List<DatoCredito> listaDatoCredito) {
        super(parent, modal);
        initComponents();
        lblTitulo.setText(titulo);
        modelo = new ModeloDatosCredito();
        tblDatosCredito.setModel(modelo);
        tblDatosCredito.getTableHeader().setReorderingAllowed(false);
        ((DefaultTableCellRenderer) tblDatosCredito.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        this.setSize(700, 280);
        this.setLocationRelativeTo(null);
        for (DatoCredito datoCredito : listaDatoCredito) {
			modelo.agregarDiaDemoraVencimiento(datoCredito);
		}
        renderizadorDatosCredito = new RenderizadorDatosCredito();
        tblDatosCredito.setDefaultRenderer(String.class, renderizadorDatosCredito);
        tblDatosCredito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblDatosCredito.setRowHeight(Constante.ALTO_COLUMNAS);
        Util.setAnchoColumnas(tblDatosCredito);
    }
    
    private void initComponents() {

        scrDatosCredit = new javax.swing.JScrollPane();
        tblDatosCredito = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".:: Datos de crédito ::.");

        tblDatosCredito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}},
            new String [] {"Title 1", "Title 2", "Title 3", "Title 4"}
        ));
        scrDatosCredit.setViewportView(tblDatosCredito);

        getContentPane().add(scrDatosCredit, java.awt.BorderLayout.CENTER);

        lblTitulo.setBackground(new java.awt.Color(41, 101, 148));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 1));
        lblTitulo.setOpaque(true);
        getContentPane().add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scrDatosCredit;
    private javax.swing.JTable tblDatosCredito;
    // End of variables declaration                   
}