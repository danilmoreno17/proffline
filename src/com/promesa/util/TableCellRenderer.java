package com.promesa.util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
		Component comp=super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (row%2==1) {
			comp.setBackground(Color.lightGray);
		}
		else {
			comp.setBackground(Color.white);
		}
		return(comp);
	}
}