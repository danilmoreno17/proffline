package com.promesa.internalFrame.pedidos.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.promesa.util.Util;

public class RendererConsultaDinamica2 extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel component = new JLabel();
		String texto = ("" + value).toString();
		switch (column) {
		case 6:
		case 7:
		case 8:
		case 12:
		case 13:
		case 11:
			component.setText(Util.formatearNumero(texto));
			break;
		default:
			component.setText(texto);
			break;
		}
		component.setOpaque(true);
		switch (column) {
		case 0:
		case 2:
		case 3:
		case 4:
		case 5:
		case 9:
			component.setHorizontalAlignment(SwingConstants.CENTER);
			break;
		case 1:
			component.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
			component.setHorizontalAlignment(SwingConstants.LEFT);
			break;
		default:
			component.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
			component.setHorizontalAlignment(SwingConstants.RIGHT);
			break;
		}
		if (column == 10 || column == 11) {
			component.setBackground(Color.white);
		} else if(column == 5 || column == 9){
			component.setEnabled(false);
		}else {
			component.setBackground(new Color(217, 219, 242));
		}
		if (isSelected) {
			component.setBackground(new Color(252, 221, 130));
		}
		return component;
	}

	public void setSizeColumn(TableColumnModel tableColumn, int numberColumn, int sizeColumn) {
		TableColumn columnaTabla;
		columnaTabla = tableColumn.getColumn(numberColumn);
		columnaTabla.setPreferredWidth(sizeColumn);
	}

	public DefaultTableCellRenderer getCenterCell() {
		DefaultTableCellRenderer CenterCell = new DefaultTableCellRenderer();
		CenterCell.setHorizontalAlignment(SwingConstants.CENTER);
		return CenterCell;
	}

	public DefaultTableCellRenderer getLeftCell() {
		DefaultTableCellRenderer LeftCell = new DefaultTableCellRenderer();
		LeftCell.setHorizontalAlignment(SwingConstants.LEFT);
		return LeftCell;
	}

	public DefaultTableCellRenderer getRightCell() {
		DefaultTableCellRenderer RightCell = new DefaultTableCellRenderer();
		RightCell.setHorizontalAlignment(SwingConstants.RIGHT);
		return RightCell;
	}
}