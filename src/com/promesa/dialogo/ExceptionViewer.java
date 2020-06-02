package com.promesa.dialogo;

import com.promesa.main.Promesa;

@SuppressWarnings("serial")
public class ExceptionViewer extends javax.swing.JDialog {

	private static ExceptionViewer instancia;
	
	/** Creates new form ExceptionViewer */
	public ExceptionViewer(java.awt.Frame parent, boolean modal) {
		super(parent, false);
		initComponents();
		instancia = this;
		setSize(650, 500);
		setLocationRelativeTo(null);
	}

	public static ExceptionViewer getInstance() { // Singleton
		if (instancia == null) {
			instancia = new ExceptionViewer(Promesa.getInstance(), true);
		}
		return instancia;
	}

	public void hacerVisible() {
		if (!getInstance().isVisible()) {
			getInstance().setVisible(true);
		}
	}

	public void setStackTrace(String texto) {
		areaTexto.append("Seguimiento de la pila de errores\n---------------------------------\n" + texto + "\n");
	}
	
	public void limpiar() {
		areaTexto.setText("");
	}	

	private void initComponents() {
		jScrollPane1 = new javax.swing.JScrollPane();
		areaTexto = new javax.swing.JTextArea();
		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();

		setTitle("Visor de errores");

		areaTexto.setColumns(20);
		areaTexto.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
		areaTexto.setRows(5);
		jScrollPane1.setViewportView(areaTexto);

		getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jButton1.setText("Limpiar consola");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jPanel1.add(jButton1);

		jButton2.setText("Cerrar");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		jPanel1.add(jButton2);

		getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

		pack();
	}// </editor-fold>

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		setVisible(false);
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		areaTexto.setText("");
	}
	private javax.swing.JTextArea areaTexto;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
}
