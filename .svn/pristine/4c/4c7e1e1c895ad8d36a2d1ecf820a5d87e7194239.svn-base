package com.promesa.dialogo.devoluciones;

import java.net.URL;

import com.promesa.sap.SDevoluciones;
import com.promesa.util.DLocker;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class DialogoComentarioPedidoDevolucion extends javax.swing.JDialog {
	
	private String vtaMercaderia;
	SDevoluciones sDevoluciones;
	
	public DialogoComentarioPedidoDevolucion(java.awt.Frame parent, boolean modal, String vtaMercaderia) {
		super(parent, modal);
		initComponents();
		this.setTitle("Texto Cabecera");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.vtaMercaderia = vtaMercaderia;
		sDevoluciones = new SDevoluciones();
		txtCabeceraFormulario.setText(sDevoluciones.leerComentarioPedidoDevolucion(vtaMercaderia, "0001"));
		txtNotaCabecera.setText(sDevoluciones.leerComentarioPedidoDevolucion(vtaMercaderia, "0002"));
	}

	private void initComponents() {

		pnlGuardar = new javax.swing.JPanel();
		btnGuardar = new javax.swing.JButton();
		lblResultado = new javax.swing.JLabel();
		pnlContenedor = new javax.swing.JPanel();
		pnlCabeceraFormulario = new javax.swing.JPanel();
		lblCabeceraFormulario = new javax.swing.JLabel();
		scrlCabeceraFormulario = new javax.swing.JScrollPane();
		txtCabeceraFormulario = new javax.swing.JTextArea();
		pnlNotaCabecera = new javax.swing.JPanel();
		lblNotaCabecera = new javax.swing.JLabel();
		scrNotaCabecera = new javax.swing.JScrollPane();
		txtNotaCabecera = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		pnlGuardar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		btnGuardar.setText("Guardar");
		btnGuardar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnGuardarActionPerformed(evt);
			}
		});
		pnlGuardar.add(btnGuardar);
		pnlGuardar.add(lblResultado);

		getContentPane().add(pnlGuardar, java.awt.BorderLayout.PAGE_END);

		pnlContenedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		pnlContenedor.setLayout(new java.awt.GridLayout(2, 1, 4, 4));

		pnlCabeceraFormulario.setLayout(new java.awt.BorderLayout());

		lblCabeceraFormulario.setText("Texto Cabecera de Formulario");
		pnlCabeceraFormulario.add(lblCabeceraFormulario, java.awt.BorderLayout.PAGE_START);

		txtCabeceraFormulario.setColumns(20);
		txtCabeceraFormulario.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
		txtCabeceraFormulario.setRows(5);
		scrlCabeceraFormulario.setViewportView(txtCabeceraFormulario);

		pnlCabeceraFormulario.add(scrlCabeceraFormulario, java.awt.BorderLayout.CENTER);

		pnlContenedor.add(pnlCabeceraFormulario);

		pnlNotaCabecera.setLayout(new java.awt.BorderLayout());

		lblNotaCabecera.setText("Nota Cabecera 1");
		pnlNotaCabecera.add(lblNotaCabecera, java.awt.BorderLayout.PAGE_START);

		txtNotaCabecera.setColumns(20);
		txtNotaCabecera.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
		txtNotaCabecera.setRows(5);
		scrNotaCabecera.setViewportView(txtNotaCabecera);

		pnlNotaCabecera.add(scrNotaCabecera, java.awt.BorderLayout.CENTER);

		pnlContenedor.add(pnlNotaCabecera);

		getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
		if(!vtaMercaderia.trim().equals("")){
			if(!txtCabeceraFormulario.getText().trim().equals("") || !txtNotaCabecera.getText().trim().equals("")){
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						SDevoluciones sDevoluciones = new SDevoluciones();
						String mensajeHTML = "<html><table>";
						URL check = this.getClass().getResource("/imagenes/check.png");
						URL error = this.getClass().getResource("/imagenes/error.png");
						if(!txtCabeceraFormulario.getText().trim().equals("") && !txtNotaCabecera.getText().trim().equals("")){
							String[] valoresCabeceraFormulario = sDevoluciones.guardarComentarioPedidoDevolucion(vtaMercaderia, Util.generarLineas(txtCabeceraFormulario.getText().trim(), 130), "0001");
							String[] valoresNotaCabecera = sDevoluciones.guardarComentarioPedidoDevolucion(vtaMercaderia, Util.generarLineas(txtNotaCabecera.getText().trim(), 130), "0002");
							if(valoresCabeceraFormulario != null && valoresNotaCabecera != null){
								if(valoresCabeceraFormulario[1].equals("S") && valoresNotaCabecera[1].equals("S")){
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
									dispose();
								}else if(valoresCabeceraFormulario[1].equals("E") && valoresNotaCabecera[1].equals("S")){
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}else if(valoresCabeceraFormulario[1].equals("S") && valoresNotaCabecera[1].equals("E")){
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}else{
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}
							} else if (valoresCabeceraFormulario == null && valoresNotaCabecera != null){
								if(valoresNotaCabecera[1].equals("S")){
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}else if(valoresNotaCabecera[1].equals("E")){
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}
							} else if(valoresCabeceraFormulario != null && valoresNotaCabecera == null){
								if(valoresCabeceraFormulario[1].equals("S")){
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}else if(valoresCabeceraFormulario[1].equals("E")){
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}
							} else{
								mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
								mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
								mensajeHTML += "</table></html>";
								lblResultado.setText(mensajeHTML);
								bloqueador.dispose();
							}
						}else if(!txtCabeceraFormulario.getText().trim().equals("")){
							String[] valoresCabeceraFormulario = sDevoluciones.guardarComentarioPedidoDevolucion(vtaMercaderia, Util.generarLineas(txtCabeceraFormulario.getText().trim(), 130), "0001");
							if(valoresCabeceraFormulario != null){
								if(valoresCabeceraFormulario[1].equals("S")){
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
									dispose();
								}else if(valoresCabeceraFormulario[1].equals("E")){
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresCabeceraFormulario[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}
							}else{
								mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
								mensajeHTML += "</table></html>";
								lblResultado.setText(mensajeHTML);
								bloqueador.dispose();
							}
						}else if(!txtNotaCabecera.getText().trim().equals("")){
							String[] valoresNotaCabecera = sDevoluciones.guardarComentarioPedidoDevolucion(vtaMercaderia, Util.generarLineas(txtNotaCabecera.getText().trim(), 130), "0002");
							if(valoresNotaCabecera != null){
								if(valoresNotaCabecera[1].equals("S")){
									mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
									dispose();
								}else if(valoresNotaCabecera[1].equals("E")){
									mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valoresNotaCabecera[3].trim() + "</td></tr>";
									mensajeHTML += "</table></html>";
									lblResultado.setText(mensajeHTML);
									bloqueador.dispose();
								}
							}else{
								mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>No se realizó correctamente la conexión a SAP.</td></tr>";
								mensajeHTML += "</table></html>";
								lblResultado.setText(mensajeHTML);
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}else{
				lblResultado.setText("<html><table>" + "<tr><td><img src=\"" + this.getClass().getResource("/imagenes/error.png") + "\"></td><td>Ud. debe registrar un comentario en el Texto Cabecera de Formulario y/o Nota Cabecera 1.</td></tr>" + "</table></html>");
			}
		}else{
			lblResultado.setText("<html><table>" + "<tr><td><img src=\"" + this.getClass().getResource("/imagenes/error.png") + "\"></td><td>Ud. debe grabar primero el pedido de devolución.</td></tr>" + "</table></html>");
		}
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnGuardar;
	private javax.swing.JLabel lblResultado;
	private javax.swing.JLabel lblCabeceraFormulario;
	private javax.swing.JLabel lblNotaCabecera;
	private javax.swing.JPanel pnlCabeceraFormulario;
	private javax.swing.JPanel pnlContenedor;
	private javax.swing.JPanel pnlGuardar;
	private javax.swing.JPanel pnlNotaCabecera;
	private javax.swing.JScrollPane scrNotaCabecera;
	private javax.swing.JScrollPane scrlCabeceraFormulario;
	private javax.swing.JTextArea txtNotaCabecera;
	private javax.swing.JTextArea txtCabeceraFormulario;
	// End of variables declaration
}
