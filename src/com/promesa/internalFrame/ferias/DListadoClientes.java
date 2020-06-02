package com.promesa.internalFrame.ferias;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.promesa.ferias.Cliente;
import com.promesa.ferias.sql.impl.SqlFeriasImpl;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

import net.coderazzi.filters.gui.TableFilterHeader;

/**
 *
 * @author Rolando
 */
@SuppressWarnings("serial")
public class DListadoClientes extends javax.swing.JDialog {

    private TableFilterHeader filterHeader;
    private TableSorter tableSorter;
    private IFerias contenedor;
    
    public DListadoClientes(java.awt.Frame parent, boolean modal, IFerias contenedor) {
        super(parent, modal);
        initComponents();
        setSize(650, 400);
        setLocationRelativeTo(null);
        this.contenedor = contenedor;
        llenarTablaCliente();
    }
                          
    private void llenarTablaCliente() {
    		SwingUtilities.invokeLater(new Runnable() {
    			@Override
    			public void run() {
    				String Columnas[] = { "COD. CLIENTE", "NOMBRE", "RAZON SOCIAL", "RUC.", "TELEFONO", "CELULAR", "CIUDAD", "CORREO" };

    				DefaultTableModel modelo = new DefaultTableModel(new Object[][] {}, Columnas) {
    					private static final long serialVersionUID = 1L;

    					@Override
    					public boolean isCellEditable(int row, int column) {
    						return false;
    					}
    				};
    				
    				SqlFeriasImpl sqlFerias = new SqlFeriasImpl();
    				List<Cliente> listaClientes = sqlFerias.obtenerClientes();
    				
    				modelo.setNumRows(0);
    				for (int i = 0; i < listaClientes.size(); i++) {
    					Cliente c = listaClientes.get(i);
    					
    					String[] values = { "" + c.getCodigoCliente(), 
    							"" + c.getNombreCliente(),
    							"" + c.getRazonSocial(),
    							"" + c.getRUC(),// 03
    							"" + c.getTelefono(),
    							"" + c.getCelular(),
    							"" + c.getCiudad(), 
    							"" + c.getCorreo() };
    					modelo.addRow(values);
    				}
    				
    				tblClientes.setModel(modelo);
    				tableSorter = new TableSorter(modelo, tblClientes.getTableHeader());
    		        tblClientes.setModel(tableSorter);
    		        filterHeader = new TableFilterHeader();
    		        filterHeader.setTable(tblClientes);
    				tblClientes.setRowHeight(22);
    				Util.setAnchoColumnas(tblClientes);
    				tblClientes.updateUI();
    			}
    		});
	}

	private void initComponents() {
        pnlOpciones = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        scrTabla = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".:: Clientes ::.");
        setResizable(false);

        pnlOpciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ok.png"))); // NOI18N
        btnAceptar.setBorder(null);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnAceptarActionPerformed(evt);
            }
        });
        pnlOpciones.add(btnAceptar);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        btnCancelar.setBorder(null);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pnlOpciones.add(btnCancelar);

        getContentPane().add(pnlOpciones, java.awt.BorderLayout.PAGE_END);
        
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblClientesMouseClicked(evt);
			}
		});
        
        scrTabla.setViewportView(tblClientes);

        getContentPane().add(scrTabla, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

	protected void tblClientesMouseClicked(MouseEvent evt) {
		final int fila = tblClientes.rowAtPoint(evt.getPoint());
		if (evt.getClickCount() == 2) {
			if(fila > -1){
				String[] datos = new String[8];
				datos[0] = tblClientes.getValueAt(fila, 0).toString();
				datos[1] = tblClientes.getValueAt(fila, 1).toString();
				datos[2] = tblClientes.getValueAt(fila, 2).toString();
				datos[3] = tblClientes.getValueAt(fila, 3).toString();
				datos[4] = tblClientes.getValueAt(fila, 4).toString();
				datos[5] = tblClientes.getValueAt(fila, 5).toString();
				datos[6] = tblClientes.getValueAt(fila, 6).toString();
				datos[7] = tblClientes.getValueAt(fila, 7).toString();
				this.contenedor.establecerClienteSelecciondo(datos);
				setVisible(false);
				dispose();
			}else {
				Mensaje.mostrarAviso("Debe seleccionas un registro.");
			}
		}
	}

	protected void btnAceptarActionPerformed(ActionEvent evt) {
		int fila = tblClientes.getSelectedRow();
		if(fila > -1){
			String[] datos = new String[8];
			datos[0] = tblClientes.getValueAt(fila, 0).toString();
			datos[1] = tblClientes.getValueAt(fila, 1).toString();
			datos[2] = tblClientes.getValueAt(fila, 2).toString();
			datos[3] = tblClientes.getValueAt(fila, 3).toString();
			datos[4] = tblClientes.getValueAt(fila, 4).toString();
			datos[5] = tblClientes.getValueAt(fila, 5).toString();
			datos[6] = tblClientes.getValueAt(fila, 6).toString();
			datos[7] = tblClientes.getValueAt(fila, 7).toString();
			this.contenedor.establecerClienteSelecciondo(datos);
			setVisible(false);
		    dispose();
		}else {
			Mensaje.mostrarAviso("Debe seleccionas un registro.");
		}
	}

	private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
	    setVisible(false);
	    dispose();
	}                                           

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPanel pnlOpciones;
    private javax.swing.JScrollPane scrTabla;
    private javax.swing.JTable tblClientes;
    // End of variables declaration
}