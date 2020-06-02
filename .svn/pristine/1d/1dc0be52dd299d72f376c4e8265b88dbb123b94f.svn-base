package com.promesa.ferias;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderConsultaFeria extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel component = new JLabel();
		component.setFont(new java.awt.Font("Tahoma", 0, 11));
		component.setBackground(Color.white);
		component.setOpaque(true);
		String contenido = "";
		if(value == null){
			contenido = "";
		}else{
			contenido = value.toString();
		}
		switch (column) {
		case 0:
			contenido = contenido.trim();
			if (contenido.compareTo("0") == 0) {
				component.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/sap_logo.png")));
			} else if (contenido.compareTo("1") == 0){
				component.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/db.png")));
			}
			break;
		default:
			component.setText(contenido);
			break;
		}
		switch (column) {
		default:
			component.setHorizontalAlignment(SwingConstants.CENTER);
			break;
		}
		if (isSelected) {
			component.setBackground(new Color(252, 221, 130));
		}
		return component;
	}
}