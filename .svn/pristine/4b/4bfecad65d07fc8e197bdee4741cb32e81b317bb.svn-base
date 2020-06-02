package com.promesa.dialogo.pedidos;

import java.util.List;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DialogoConfirmacionSincronizacion extends javax.swing.JDialog {

	/** Creates new form DialogoConfirmacionSincronizacion */
	public DialogoConfirmacionSincronizacion(java.awt.Frame parent, boolean modal, List<String[]> mensajes, String titulo) {
		super(parent, modal);
		initComponents();
		pnlMensajes.setLayout(new java.awt.GridLayout(mensajes.size(), 2, 5, 5));
		for (String[] strings : mensajes) {
			String estatus = strings[0];
			JLabel lbl = new JLabel(strings[1]);
			if (estatus.compareTo("S") == 0) {
				lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon_check_yes.png")));
			} else {
				lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon_check_no.png")));
			}
			pnlMensajes.add(lbl);
		}
		this.setResizable(false);
		this.setTitle(titulo);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	private void initComponents() {

		pnlBotones = new javax.swing.JPanel();
		btnAceptar = new javax.swing.JButton();
		pnlMensajes = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		btnAceptar.setText("Aceptar");
		btnAceptar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAceptarActionPerformed(evt);
			}
		});
		pnlBotones.add(btnAceptar);

		getContentPane().add(pnlBotones, java.awt.BorderLayout.PAGE_END);

		pnlMensajes.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMensajes.setLayout(new java.awt.GridLayout());
		getContentPane().add(pnlMensajes, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
		dispose();
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnAceptar;
	private javax.swing.JPanel pnlBotones;
	private javax.swing.JPanel pnlMensajes;
	// End of variables declaration
}
