package com.promesa.dialogo.cobranzas;

import java.net.URL;

import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.sap.SCobranzas;
import com.promesa.util.DLocker;

@SuppressWarnings("serial")
public class DialogoComentario extends javax.swing.JDialog {
	
	private PagoParcial pagoParcial;
	private SCobranzas sCobranzas;
	/** Creates new form DialogoComentario */
	public DialogoComentario(java.awt.Frame parent, PagoParcial pagoParcial, boolean modal) {
		super(parent, modal);
		initComponents();
		this.setResizable(false);
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.pagoParcial = pagoParcial;
		sCobranzas = new SCobranzas();
		txpComentario.setText(sCobranzas.leerComentarioPagoParcial(pagoParcial));
	}
	
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        pnlGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        lblResultado = new javax.swing.JLabel();
        scrTexto = new javax.swing.JScrollPane();
        txpComentario = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Comentarios");

        lblTitulo.setBackground(new java.awt.Color(182, 207, 230));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblTitulo.setText("Comentario de Pago");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 1));
        lblTitulo.setOpaque(true);
        getContentPane().add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        pnlGuardar.setBackground(new java.awt.Color(255, 255, 255));
        pnlGuardar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guadarComentario.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarSeleccionado.png"))); // NOI18N
        btnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarSeleccionado.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        pnlGuardar.add(btnGuardar);
        pnlGuardar.add(lblResultado);

        getContentPane().add(pnlGuardar, java.awt.BorderLayout.PAGE_END);

        scrTexto.setViewportView(txpComentario);

        getContentPane().add(scrTexto, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>

	
	private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				sCobranzas = new SCobranzas();
				pagoParcial.setComentario(txpComentario.getText());
				String[] valores = sCobranzas.guardarComentarioPagoParcial(pagoParcial);
				String mensajeHTML = "<html><table>";
				URL check = this.getClass().getResource("/imagenes/check.png");
				URL error = this.getClass().getResource("/imagenes/error.png");
				if(valores != null){
					if(valores[1].equals("S")){
						mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valores[3].trim() + "</td></tr>";
						mensajeHTML += "</table></html>";
						lblResultado.setText(mensajeHTML);
						bloqueador.dispose();
						dispose();
					}else if(valores[1].equals("E")){
						mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valores[3].trim() + "</td></tr>";
						mensajeHTML += "</table></html>";
						lblResultado.setText(mensajeHTML);
						bloqueador.dispose();
					}else{
						mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>Ocurrió una excepción.</td></tr>";
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
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnGuardar;
	private javax.swing.JLabel lblResultado;
	private javax.swing.JLabel lblTitulo;
	private javax.swing.JPanel pnlGuardar;
	private javax.swing.JScrollPane scrTexto;
	private javax.swing.JTextPane txpComentario;
	// End of variables declaration
}
