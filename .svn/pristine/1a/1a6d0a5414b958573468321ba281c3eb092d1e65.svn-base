package com.promesa.internalFrame.pedidos.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class RenderizadorTablaFactura implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		JLabel celda = new JLabel();
		celda.setOpaque(true);
		celda.setBackground(Color.white);
		celda.setText("" + arg1);
		switch (arg5) {
		case 0:
		case 1:
		case 5:
			celda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			break;
		case 2:
			celda.setBorder(new EmptyBorder(1, 4, 1, 1));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			break;
		case 3:
			celda.setBorder(new EmptyBorder(1, 4, 1, 1));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
