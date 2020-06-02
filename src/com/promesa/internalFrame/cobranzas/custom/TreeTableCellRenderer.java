package com.promesa.internalFrame.cobranzas.custom;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.promesa.cobranzas.bean.FlujoDocumento;

public class TreeTableCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 5593629042737938947L;

	public TreeTableCellRenderer() {

		setOpenIcon(new ImageIcon("icons/minus.gif"));
		setClosedIcon(new ImageIcon("icons/plus.gif"));
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension dim = super.getPreferredSize();
		FontMetrics fm = getFontMetrics(getFont());
		char[] chars = getText().toCharArray();

		int w = getIconTextGap() + 16;
		for (char ch : chars) {
			w += fm.charWidth(ch);
		}
		w += getText().length();
		dim.width = w;
		return dim;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/arrow.png")));
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		if (node != null && node.getUserObject() != null && (node.getUserObject() instanceof FlujoDocumento)) {
			FlujoDocumento item = (FlujoDocumento) (node.getUserObject());
			setText(item.getNumeroDocumento());
			if (item.isRoot()) {
				setOpenIcon(new ImageIcon("icons/minus.gif"));
				setClosedIcon(new ImageIcon("icons/plus.gif"));
			} else {
				setIcon(null);
				setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/hoja_flujo.png")));
			}
		} else {
			setIcon(null);
		}
		getPreferredSize();
		return this;

	}

}
