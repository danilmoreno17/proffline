package com.promesa.internalFrame.cobranzas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.BeanFacturaHeader;
import com.promesa.internalFrame.cobranzas.custom.RenderConsultaFactura;
import com.promesa.main.Promesa;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SFacturas;
import com.promesa.sap.SPlanificacion;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class IListadoFacturas extends javax.swing.JInternalFrame {

	List<BeanFacturaHeader> facturae;
	
	/** Creates new form IListadoFacturas */
	public IListadoFacturas() {
		initComponents();
		tablaVacia();
		cargaComboClienteFacturas();
		((DefaultTableCellRenderer) tblFacturas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblFacturas.setRowHeight(Constante.ALTO_COLUMNAS);
		tblFacturas.getTableHeader().setReorderingAllowed(false);
		dateDesde.setDate(new Date());
		dateHasta.setDate(new Date());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		pnlOpciones = new javax.swing.JPanel();
		lblCliente = new javax.swing.JLabel();
		cmbCliente = new javax.swing.JComboBox();
		lblDesde = new javax.swing.JLabel();
		dateDesde = new com.toedter.calendar.JDateChooser();
		lblHasta = new javax.swing.JLabel();
		dateHasta = new com.toedter.calendar.JDateChooser();
		btnBuscar = new javax.swing.JButton();
		btnVerDetalle = new javax.swing.JButton();
		scrFacturas = new javax.swing.JScrollPane();
		tblFacturas = new javax.swing.JTable();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Listado de facturas");
		getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

		pnlOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de búsqueda"));
		pnlOpciones.setPreferredSize(new java.awt.Dimension(572, 86));

		lblCliente.setText("Cliente:");

		cmbCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));

		lblDesde.setText("Filtrar desde:");

		dateDesde.setDateFormatString("dd/MM/yyyy");

		lblHasta.setText("hasta:");

		dateHasta.setDateFormatString("dd/MM/yyyy");

		btnBuscar.setText("Buscar");
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});

		btnVerDetalle.setText("Ver detalle");
		btnVerDetalle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnVerDetalleActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pnlOpcionesLayout = new javax.swing.GroupLayout(pnlOpciones);
		pnlOpciones.setLayout(pnlOpcionesLayout);
		pnlOpcionesLayout.setHorizontalGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlOpcionesLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				.addGroup(pnlOpcionesLayout.createSequentialGroup().addComponent(lblCliente)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(cmbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(pnlOpcionesLayout.createSequentialGroup().addComponent(lblDesde)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(dateDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(lblHasta)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(dateHasta,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnBuscar)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnVerDetalle).addContainerGap(109, Short.MAX_VALUE)));

		pnlOpcionesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { lblCliente, lblDesde });

		pnlOpcionesLayout.setVerticalGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlOpcionesLayout.createSequentialGroup()
				.addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblCliente)
				.addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				.addComponent(btnBuscar).addComponent(btnVerDetalle))
				.addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
				.addComponent(dateHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(dateDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(lblDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(lblHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		getContentPane().add(pnlOpciones, java.awt.BorderLayout.PAGE_START);

		tblFacturas.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		tblFacturas.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblFacturasMouseClicked(evt);
			}
		});
		scrFacturas.setViewportView(tblFacturas);

		getContentPane().add(scrFacturas, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void tblFacturasMouseClicked(java.awt.event.MouseEvent evt) {
		final int fila = tblFacturas.rowAtPoint(evt.getPoint());
		if (evt.getClickCount() == 2) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						if(fila > -1){
							verDetalleFactura(fila);
						}else
							Mensaje.mostrarAviso("Debe seleccionar un registro de la tabla factura.");
					}catch (Exception ex) {
						if (!(ex instanceof NullPointerException)) {
							Mensaje.mostrarAviso(ex.getMessage());
						}
					}finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
	}

	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		Date fechaDesde = dateDesde.getDate(); 
		Date fechaHasta = dateHasta.getDate();
		if(Util.comparaFecha1MenorOIgualQueFecha2(fechaDesde, fechaHasta)){
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						try {
							buscarFactura();
						} finally {
							bloqueador.dispose();
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
		}else {
			Mensaje.mostrarError("fecha inicial no debe ser mayor a fecha final.");
		}
	}

	private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {
		final int fila = tblFacturas.getSelectedRow();
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try{
					if(fila > -1){
						verDetalleFactura(fila);
					}else {
						Mensaje.mostrarAviso("Debe seleccionar un registro de la tabla factura.");
					}
				}catch(Exception ex){
					Util.mostrarExcepcion(ex);
				}finally{
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}
	
	public void verDetalleFactura(int fila){
			BeanFacturaHeader fh = new BeanFacturaHeader();
			fh.setNumeroFacturaSap(tblFacturas.getValueAt(fila, 1).toString());
			fh.setNumeroFacturaSRI(tblFacturas.getValueAt(fila, 2).toString());
			fh.setFechaFactura(tblFacturas.getValueAt(fila, 3).toString());
			fh.setTipoDocumento(tblFacturas.getValueAt(fila, 4).toString());
			fh.setCodigocliente(tblFacturas.getValueAt(fila, 5).toString());
			fh.setNombreCliente(tblFacturas.getValueAt(fila, 6).toString());
			fh.setNumeroPedido(tblFacturas.getValueAt(fila, 7).toString());
			fh.setValorNeto(tblFacturas.getValueAt(fila, 8).toString());
			fh.setTotalImpuesto(tblFacturas.getValueAt(fila, 9).toString());
			fh.setValorTotal(tblFacturas.getValueAt(fila, 10).toString());
			fh.setCondicionPago(tblFacturas.getValueAt(fila, 11).toString());
			
			IDetalleFactura iDetalle = new IDetalleFactura(fh);
			Promesa.destokp.add(iDetalle);
			iDetalle.setVisible(true);
			iDetalle.setMaximizable(true);
	}

	@SuppressWarnings("unchecked")
	public void cargaComboClienteFacturas() {
		BeanDato usuario = Promesa.datose.get(0);
		SPlanificacion objSAP;
		List<BeanCliente> listaCliente = null;
		SqlClienteImpl getCliente = new SqlClienteImpl();
		if (usuario.getStrModo().equals(Constante.MODO_ONLINE)) {
			try {
				objSAP = new SPlanificacion();
				listaCliente = objSAP.listaClienteVisita(usuario.getStrCodigo());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		} else {
			listaCliente = new ArrayList<BeanCliente>();
			getCliente.clientesXvendedor(usuario.getStrCodigo());
			listaCliente = getCliente.getListaCliente();
		}
		if (listaCliente != null && listaCliente.size() > 0) {
			for (int i = 0; i < listaCliente.size(); i++) {
				if (usuario.getStrModo().equals(Constante.MODO_ONLINE)) {
					String codigoClienteSap = ((BeanCliente) listaCliente.get(i)).getStrIdCliente();
					if (!codigoClienteSap.equals("")) {
//						codigoClienteSap = codigoClienteSap.substring(0, codigoClienteSap.length());
						codigoClienteSap = Util.eliminarCerosInicios(codigoClienteSap);
					}
					cmbCliente.addItem(((BeanCliente) listaCliente.get(i)).getStrCompaniaCliente() + " - " + codigoClienteSap);
				} else {
					cmbCliente.addItem(((BeanCliente) listaCliente.get(i)).getStrNombreCliente() + " - " + ((BeanCliente) listaCliente.get(i)).getStrIdCliente());
				}
			}
		} else {
			cmbCliente.addItem(Constante.VACIO);
		}
	}

	public void tablaVacia() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "", "FACTURA SAP", "FACTURA SRI", "FECHA FAC.", "TIPO DOC.", "COD. DE CLIENTE",
						"CLIENTE", "DOC. DE VENTA", "VALOR NETO", "TOTAL IMP.", "VALOR TOTAL", "COND. PAGO" };

				DefaultTableModel modelo = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				RenderConsultaFactura render = new RenderConsultaFactura();
				tblFacturas.setDefaultRenderer(Object.class, render);
				tblFacturas.setModel(modelo);
				tblFacturas.updateUI();
				Util.setAnchoColumnas(tblFacturas);
				setAnchoColumnas();
			}
		});
	}
	
	private void setAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblFacturas.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblFacturas.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}

	protected void buscarFactura() {
			facturae = new ArrayList<BeanFacturaHeader>();
			String codigoVendedor = "" + Promesa.datose.get(0).getStrCodigo();
			String cadena = cmbCliente.getSelectedItem().toString();
			String[] cliente = cadena.split(" - ");
			String codigoCliente = "";
			if(cliente.length > 0)
				codigoCliente = cliente[1];
			SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
			String strFechaInicio = formato.format(dateDesde.getDate());
			String strFechaFinal = formato.format(dateHasta.getDate());
			BeanDato usuario = Promesa.datose.get(0);
			SFacturas sf = new SFacturas();
			if (usuario.getStrModo().equals("1")) {
				if(!codigoCliente.equals("")){
					facturae = sf.obtenerFacturasSap(codigoVendedor, Util.completarCeros(10, codigoCliente), strFechaInicio, strFechaFinal);
				}else {
					facturae = sf.obtenerFacturasSap(codigoVendedor, codigoCliente, strFechaInicio, strFechaFinal);
				}
				if (facturae != null) {
					llenarTabla();
				} else
					Mensaje.mostrarAviso("El cliente: " + codigoCliente + " no tiene Facturas.");
			} else {
				Mensaje.mostrarError("Consulta de Factura Tienes que tener conección.");
			}
	}

	private void llenarTabla() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "", "FACTURA SAP", "FACTURA SRI", "FECHA FAC.", "TIPO DOC.", "COD. DE CLIENTE",
						"CLIENTE", "DOC. DE VENTA", "VALOR NETO", "TOTAL IMP.", "VALOR TOTAL", "COND. PAGO" };

				DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				tblTablaModel.setNumRows(0);
				for (int i = 0; i < facturae.size(); i++) {
					BeanFacturaHeader f = facturae.get(i);
					String factura = Util.eliminarCerosInicios(f.getNumeroFacturaSap());
					String codigocCliente = Util.eliminarCerosInicios(f.getCodigocliente());
					String numeroPedido = Util.eliminarCerosInicios(f.getNumeroPedido());
					
					String[] values = { "", "" + factura, //f.getNumeroFacturaSap(),// 00
							"" + f.getNumeroFacturaSRI(),// 01
							"" + f.getFechaFactura(),// 02
							"" + f.getTipoDocumento(),// 03
							"" + codigocCliente, //f.getCodigocliente(),// 04
							"" + f.getNombreCliente(),// 05
							"" + numeroPedido, //f.getNumeroPedido(),// 06
							"" + Util.formatearNumero(f.getValorNeto()),// 07
							"" + Util.formatearNumero(f.getTotalImpuesto()),// 08
							"" + Util.formatearNumero(f.getValorTotal()),// 09
							"" + f.getCondicionPago() };// 10
					tblTablaModel.addRow(values);
				}
				RenderConsultaFactura render = new RenderConsultaFactura();
				tblFacturas.setDefaultRenderer(Object.class, render);
				tblFacturas.setModel(tblTablaModel);
				Util.setAnchoColumnas(tblFacturas);
				setAnchoColumnas();
				tblFacturas.updateUI();
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnVerDetalle;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox cmbCliente;
	private com.toedter.calendar.JDateChooser dateDesde;
	private com.toedter.calendar.JDateChooser dateHasta;
	private javax.swing.JLabel lblCliente;
	private javax.swing.JLabel lblDesde;
	private javax.swing.JLabel lblHasta;
	private javax.swing.JPanel pnlOpciones;
	private javax.swing.JScrollPane scrFacturas;
	private javax.swing.JTable tblFacturas;
	// End of variables declaration
}