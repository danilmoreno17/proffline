package com.promesa.internalFrame.pedidos.custom;

import java.awt.Color;
import java.awt.Component;
//import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
//import com.promesa.util.Util;

public class RenderizadorTablaSincronizacion implements TableCellRenderer {

	Border noFocusBorder;
	Border focusBorder;

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		Component celda = null;
		if (arg5 == 0) {
			JCheckBox check = new JCheckBox();
			check.setSelected(Boolean.TRUE.equals(arg1));
			check.setOpaque(true);
			check.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			check.setBackground(Color.white);
			if (arg3) {
				if (focusBorder == null) {
					focusBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
				}
				check.setBorder(focusBorder);
			} else {
				if (noFocusBorder == null) {
					noFocusBorder = new EmptyBorder(1, 1, 1, 1);
				}
				check.setBorder(noFocusBorder);
			}
			celda = check;
		}
		else {
			JLabel label = new JLabel();
			label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			if (arg5 == 1 || arg5 == 4) {
				label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/timer.png")));
				label.setText("" + arg1);
				label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			} else {
				label.setBackground(new Color(217, 219, 242));
				label.setText(("" + arg1).toString().toUpperCase());
			}
			label.setOpaque(true);
			celda = label;
		}
		if (arg2) {
			celda.setBackground(new Color(252, 221, 130));
		}
		return celda;
	}
}