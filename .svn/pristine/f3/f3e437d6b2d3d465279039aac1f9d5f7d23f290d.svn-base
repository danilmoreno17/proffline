package com.promesa.internalFrame.sisinfo.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.promesa.util.Util;

public class RenderizadorTablaIndicadoresSupervisor implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		JLabel celda = new JLabel();
		celda.setOpaque(true);
		celda.setBackground(Color.white);
		String texto = ("" + arg1).toString();
		switch (arg5) {
		case 0:
			celda.setBorder(new EmptyBorder(1, 4, 1, 1));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			break;
		case 1:
			celda.setBorder(new EmptyBorder(1, 4, 1, 1));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			break;
		case 2:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 3:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 4:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			texto += "%"; 
			break;
		}
		celda.setText("" + texto);
		return celda;
	}
}
