package com.promesa.internalFrame.cobranzas.custom;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import com.promesa.cobranzas.bean.FlujoDocumento;

public class MyTreeModel extends AbstractTreeTableModel {
	private String[] titles = { "DOCUMENTO", "EI", "ESTATUS" };

	public MyTreeModel(DefaultMutableTreeNode root) {
		super(root);
	}

	public String getColumnName(int column) {
		if (column < titles.length)
			return (String) titles[column];
		else
			return "";
	}

	public int getColumnCount() {
		return titles.length;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		return String.class;
	}

	public Object getValueAt(Object arg0, int arg1) {
		if (arg0 instanceof FlujoDocumento) {
			FlujoDocumento data = (FlujoDocumento) arg0;
			if (data != null) {
				switch (arg1) {
				case 0:
					return data.getNumeroDocumento();
				case 1:
					return data.getEi();
				case 2:
					return data.getEstado();
				}
			}

		}

		if (arg0 instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode dataNode = (DefaultMutableTreeNode) arg0;
			FlujoDocumento data = (FlujoDocumento) dataNode.getUserObject();
			if (data != null) {
				switch (arg1) {
				case 0:
					return data.getNumeroDocumento();
				case 1:
					return data.getEi();
				case 2:
					return data.getEstado();
				}
			}

		}
		return null;
	}

	public Object getChild(Object arg0, int arg1) {

		if (arg0 instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode nodes = (DefaultMutableTreeNode) arg0;
			return nodes.getChildAt(arg1);
		}
		return null;
	}

	public int getChildCount(Object arg0) {

		if (arg0 instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode nodes = (DefaultMutableTreeNode) arg0;
			return nodes.getChildCount();
		}
		return 0;
	}

	public int getIndexOfChild(Object arg0, Object arg1) {
		return 0;
	}

	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}
}