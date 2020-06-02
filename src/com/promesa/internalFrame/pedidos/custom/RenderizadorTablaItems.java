package com.promesa.internalFrame.pedidos.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RenderizadorTablaItems implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel celda = new JLabel();
		celda.setOpaque(true);
		celda.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
		celda.setFont(new java.awt.Font("Tahoma", 0, 11));

		switch (column) {
		case 2:
		case 3:
		case 5:
		case 6:
		case 9:
			celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			break;
		case 8:
			celda.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
			celda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			break;
		default:
			celda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			break;
		}

		ModeloTablaItems modelo = (ModeloTablaItems) table.getModel();
		Item item = modelo.obtenerItem(row);
		int tipo = item.getTipo();
		boolean estaSimulado = item.isSimulado();
		if (tipo == 0 || tipo == 1) { // item normal
			switch (column) {
			case 1:
			case 2:
			case 6:
				celda.setBackground(Color.white);
				break;
			default:
				celda.setBackground(new Color(217, 219, 242));
				break;
			}
		} else if (tipo == 2) {
			celda.setBackground(new Color(217, 219, 242));
			if (column == 0) {
				celda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			}
		}
		if (!estaSimulado) {
			celda.setBackground(new Color(251, 209, 202));
		}
		if (isSelected) {
			celda.setBackground(new Color(252, 221, 130));
		}
		if (column == 9) {
			String valor = ("" + value).toString();
			if (valor.compareTo("N") == 0) {
				celda.setToolTipText("Normal");
			} else if (valor.compareTo("R") == 0) {
				celda.setToolTipText("Rojo");
			} else if (valor.compareTo("P") == 0) {
				celda.setToolTipText("Pesado");
			} else if (valor.compareTo("B") == 0) {
				celda.setToolTipText("Bodega");
			}
		}
		if (item.isEliminadoLogicamente()) {
			celda.setBackground(new Color(180, 180, 180));
		}
		celda.setText("" + value);
		return celda;
	}

}