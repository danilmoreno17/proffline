package com.promesa.internalFrame.sisinfo.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.promesa.util.Util;

public class RenderizadorTablaIndicadoresDetalleSupervisor implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
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
		case 5:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 6:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 7:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			texto += "%";
			break;
		case 8:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 9:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 10:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			texto += "%";
			break;
		case 11:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 12:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 13:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			texto += "%";
			break;
		case 14:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 15:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 16:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			texto += "%";
			break;
		case 17:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 18:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 19:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			texto += "%";
			break;
		case 20:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 21:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 22:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			texto += "%";
			break;
		case 23:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 24:
			texto = Util.formatearNumero(texto);
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 25:
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
