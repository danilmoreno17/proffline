package com.promesa.internalFrame.cobranzas;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import com.promesa.internalFrame.cobranzas.custom.MyTreeModel;
import com.promesa.internalFrame.cobranzas.custom.TreeTableCellRenderer;
import com.promesa.sap.SCobranzas;


@SuppressWarnings("serial")
public class IFlujoDocumento extends javax.swing.JInternalFrame {

	@SuppressWarnings("unused")
	private String numeroPedido;
	@SuppressWarnings("unused")
	private String codigoCliente;
	@SuppressWarnings("unused")
	private String nombreCliente;

	public IFlujoDocumento(String numeroPedido, String codigoCliente, String nombreCliente) {
		initComponents();
		this.numeroPedido = numeroPedido;
		this.codigoCliente = codigoCliente;
		this.nombreCliente = nombreCliente;
		lblTitulo.setText("<html><p><b>Interloc.comercial</b> " + codigoCliente + " " + nombreCliente + "</p></html>");
		construirTablaDeArbol(numeroPedido);
	}

	private void construirTablaDeArbol(String codigoPedido) {
		DefaultMutableTreeNode rootNode = null;
		try {
			rootNode = SCobranzas.obtenerFlujoDocumento(codigoPedido);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rootNode != null) {
			JXTreeTable binTree = new JXTreeTable(new MyTreeModel(rootNode));
			Highlighter highligher = HighlighterFactory.createSimpleStriping(HighlighterFactory.BEIGE);
			binTree.setHighlighters(highligher);
			binTree.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			binTree.setShowGrid(false);
			binTree.setShowsRootHandles(true);
			binTree.setFillsViewportHeight(false);
			binTree.expandAll();
			configureCommonTableProperties(binTree);
			binTree.setTreeCellRenderer(new TreeTableCellRenderer());
			binTree.updateUI();
			scrTabla.setViewportView(binTree);
		}
	}

	private void configureCommonTableProperties(JXTable table) {
		table.setColumnControlVisible(true);
		org.jdesktop.swingx.renderer.StringValue toString = new org.jdesktop.swingx.renderer.StringValue() {
			public String getString(Object value) {
				if (value instanceof Point) {
					Point p = (Point) value;
					return createString(p.x, p.y);
				} else if (value instanceof Dimension) {
					Dimension dim = (Dimension) value;
					return createString(dim.width, dim.height);
				}
				return "";
			}

			private String createString(int width, int height) {
				return "(" + width + ", " + height + ")";
			}
		};
		TableCellRenderer renderer = new DefaultTableRenderer(toString);
		table.setDefaultRenderer(Point.class, renderer);
		table.setDefaultRenderer(Dimension.class, renderer);
	}

	private void initComponents() {

		lblTitulo = new javax.swing.JLabel();
		scrTabla = new javax.swing.JScrollPane();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Flujo de pedidos");

		lblTitulo.setBackground(new java.awt.Color(247, 247, 231));
		lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
		lblTitulo.setText("Interloc.comercial");
		lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 1));
		lblTitulo.setOpaque(true);
		getContentPane().add(lblTitulo, java.awt.BorderLayout.PAGE_START);
		getContentPane().add(scrTabla, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>
	
	private javax.swing.JLabel lblTitulo;
	private javax.swing.JScrollPane scrTabla;
}