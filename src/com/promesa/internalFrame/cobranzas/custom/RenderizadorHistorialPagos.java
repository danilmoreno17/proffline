package com.promesa.internalFrame.cobranzas.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class RenderizadorHistorialPagos implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		JLabel celda = new JLabel();
		celda.setOpaque(true);
		celda.setBackground(Color.white);
		celda.setText("" + arg1);
		switch (arg5) {
		case 0:
		case 1:
		case 2:
			celda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			break;
		default:
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		}
		if (arg2) {
			celda.setBackground(new Color(252, 221, 130));
		}
		return celda;
	}
}