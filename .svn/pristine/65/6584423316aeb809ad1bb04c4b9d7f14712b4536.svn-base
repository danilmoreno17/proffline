package com.promesa.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class Renderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof JLabel) {
			JLabel label = (JLabel) value;
			if (isSelected) {
				label.setBackground(table.getSelectionBackground());
			} else {
				label.setBackground(table.getBackground());
			}
			return (JLabel) label;
		}
		else
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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