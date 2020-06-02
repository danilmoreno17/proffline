package com.promesa.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class RendererTomaPedidos extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel component = new JLabel();
		component.setText(value.toString());
		component.setOpaque(true);
		if (column == 1 || column == 2 || column == 5) {
			component.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
			component.setHorizontalAlignment(SwingConstants.RIGHT);
			component.setBackground(Color.white);
		} else {
			component.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
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