package com.promesa.dialogo.cobranzas;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.promesa.cobranzas.bean.PedidoPendienteDevolucion;
import com.promesa.internalFrame.cobranzas.custom.ModeloCarteraPendienteDevolucion;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorPedidoPendienteDevolucion;
import com.promesa.util.Constante;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class DialogoDetalleCarteraPendienteDevolucion extends javax.swing.JDialog {

	private ModeloCarteraPendienteDevolucion modelo;
	private RenderizadorPedidoPendienteDevolucion renderizadorPedidoPendienteDevolucion;

	/** Creates new form DialogoDetalleCarteraPendienteDevolucion */
	public DialogoDetalleCarteraPendienteDevolucion(java.awt.Frame parent, boolean modal, List<PedidoPendienteDevolucion> listaPedidoPendienteDevolucion) {
		super(parent, modal);
		initComponents();
		modelo = new ModeloCarteraPendienteDevolucion();
		tblpedidosPendientesDevolucion.setModel(modelo);
		tblpedidosPendientesDevolucion.getTableHeader().setReorderingAllowed(false);
		((DefaultTableCellRenderer) tblpedidosPendientesDevolucion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		this.setSize(700, 280);
		this.setLocationRelativeTo(null);
		for (PedidoPendienteDevolucion pedidoPendienteDevolucion : listaPedidoPendienteDevolucion) {
			modelo.agregarDetalleCarteraPendienteDevolucion(pedidoPendienteDevolucion);
		}
		renderizadorPedidoPendienteDevolucion = new RenderizadorPedidoPendienteDevolucion();
		tblpedidosPendientesDevolucion.setDefaultRenderer(String.class, renderizadorPedidoPendienteDevolucion);
		tblpedidosPendientesDevolucion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblpedidosPendientesDevolucion.setRowHeight(Constante.ALTO_COLUMNAS);
		Util.setAnchoColumnas(tblpedidosPendientesDevolucion);
	}

	private void initComponents() {

		scrPedidosPendientesDevolución = new javax.swing.JScrollPane();
		tblpedidosPendientesDevolucion = new javax.swing.JTable();
		lblTitulo = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(".:: Pedidos pendientes de devolución ::.");

		tblpedidosPendientesDevolucion.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } }, 
								new String[] {"Title 1", "Title 2", "Title 3", "Title 4" }));
		scrPedidosPendientesDevolución.setViewportView(tblpedidosPendientesDevolucion);

		getContentPane().add(scrPedidosPendientesDevolución, java.awt.BorderLayout.CENTER);

		lblTitulo.setBackground(new java.awt.Color(41, 101, 148));
		lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
		lblTitulo.setText("Pedidos pendientes de devolución");
		lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 1));
		lblTitulo.setOpaque(true);
		getContentPane().add(lblTitulo, java.awt.BorderLayout.PAGE_START);

		pack();
	}// </editor-fold>

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(DialogoDetalleCarteraPendienteDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DialogoDetalleCarteraPendienteDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DialogoDetalleCarteraPendienteDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DialogoDetalleCarteraPendienteDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	// Variables declaration - do not modify
	private javax.swing.JLabel lblTitulo;
	private javax.swing.JScrollPane scrPedidosPendientesDevolución;
	private javax.swing.JTable tblpedidosPendientesDevolucion;
	// End of variables declaration
}
