package com.promesa.internalFrame.cobranzas.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.promesa.cobranzas.bean.PagoParcial;

public class RenderizadorPagoParcial implements TableCellRenderer {

	private ModeloPagosRecibidos modelo;

	public RenderizadorPagoParcial(ModeloPagosRecibidos modelo) {
		super();
		this.modelo = modelo;
	}

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		JLabel celda = new JLabel();
		celda.setOpaque(true);
		celda.setBackground(new Color(217, 229, 242));
		celda.setText("" + arg1);
		switch (arg5) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			celda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			break;
		case 7:
		case 8:
		case 9:
			celda.setBorder(new EmptyBorder(1, 1, 1, 4));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		default:
			celda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			break;
		}
		PagoParcial pagoParcial = modelo.obtenerPagoParcial(arg4);
		if(pagoParcial.isActivo()) {
			celda.setForeground(new java.awt.Color(255, 0, 0));
		} else {
			celda.setForeground(Color.black);
		}
		if (arg5 == 1) {
			celda.setBorder(new EmptyBorder(1, 5, 1, 5));
			if (modelo.esHijo(arg4)) {
				celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
				celda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/punto.png")));
			} else {
				celda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
				if (modelo.estaDesplegado(arg4)) {
					celda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flechaDesplegada.png")));
				} else {
					celda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flechaCobranzas.png")));
				}
			}
		} else if (arg5 == 10) {
			if (modelo.esHijo(arg4)) {
				celda.setEnabled(false);
				celda.setText("<html><u><font color='gray'>Ver</font></u></html>");
			} else {
				celda.setBackground(Color.white);
				celda.setText("<html><u><font color='blue'>Ver</font></u></html>");
			}
		}
		if (arg2) {
			celda.setBackground(new Color(252, 221, 130));
		}
		if(pagoParcial.isPago_hecho_offline()){
			celda.setForeground(Color.gray);
			celda.setEnabled(false);
		}
		return celda;
	}
}