package com.promesa.util;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class NodoArbol extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;
	private ImageIcon icon_red = new ImageIcon(getClass().getResource("/imagenes/nodo.png"));
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setIcon(icon_red);
		return this;
	}
}