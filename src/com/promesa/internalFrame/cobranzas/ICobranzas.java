package com.promesa.internalFrame.cobranzas;

import java.awt.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.CabeceraHojaMaestraCredito;
import com.promesa.cobranzas.bean.DatoCredito;
import com.promesa.cobranzas.bean.DetallePagoPartidaIndividualAbierta;
import com.promesa.cobranzas.bean.DiaDemoraTrasVencimiento;
import com.promesa.cobranzas.bean.HistorialPago;
import com.promesa.cobranzas.bean.PartidaIndividual;
import com.promesa.cobranzas.bean.PartidaIndividualAbierta;
import com.promesa.cobranzas.bean.PedidoPendienteDevolucion;
import com.promesa.cobranzas.bean.ValorPorVencer;
import com.promesa.cobranzas.sql.impl.SqlCabeceraHojaMaestraCreditoImpl;
import com.promesa.cobranzas.sql.impl.SqlDetallePagoPartidaIndividualAbiertaImpl;
import com.promesa.cobranzas.sql.impl.SqlDiaDemoraTrasVencimientoImpl;
import com.promesa.cobranzas.sql.impl.SqlHistorialPagoImpl;
import com.promesa.cobranzas.sql.impl.SqlNotaCreditoImpl;
import com.promesa.cobranzas.sql.impl.SqlPartidaIndividualAbiertaImpl;
import com.promesa.cobranzas.sql.impl.SqlPartidaIndividualImpl;
import com.promesa.cobranzas.sql.impl.SqlPedidoPendienteDevolucionImpl;
import com.promesa.cobranzas.sql.impl.SqlProtestoImpl;
import com.promesa.cobranzas.sql.impl.SqlValorPorVencerImpl;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.dialogo.cobranzas.DialogoDetalleCartera;
import com.promesa.dialogo.cobranzas.DialogoDetalleCarteraPendienteDevolucion;
import com.promesa.dialogo.cobranzas.DialogoPartidasIndividuales;
import com.promesa.internalFrame.cobranzas.custom.ModeloHistorialPago;
import com.promesa.internalFrame.cobranzas.custom.ModeloTablaVencimientoNeto;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorHistorialPagos;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorVencimientoNeto;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.sap.SCobranzas;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Util;

/**
 * 
 * @author Administrador
 */
@SuppressWarnings("serial")
public class ICobranzas extends javax.swing.JInternalFrame {

	// private JFrame contenedor;
	private ModeloTablaVencimientoNeto modeloTablaDiasDemora;
	private ModeloHistorialPago modeloHistorial;
	private RenderizadorVencimientoNeto renderizadorVencimientoNeto;
	private RenderizadorHistorialPagos renderizadorPagos;
	private String codigoCliente;
	private String codigoVendedor;
	private BeanAgenda ba;
	private List<DatoCredito> listaNotaCredito;
	private List<DatoCredito> listaProtesto;
	private DialogoDetalleCarteraPendienteDevolucion dlgDetalleCarteraPendienteDevolucion = null;
	private String nombreCompletoCliente;

	/** Creates new form ICobranzas */
	public ICobranzas(BeanAgenda ba, String codigoVendedor) {
		initComponents();
		this.modeloTablaDiasDemora = new ModeloTablaVencimientoNeto();
		this.modeloHistorial = new ModeloHistorialPago();
		this.codigoCliente = ba.getStrCodigoCliente();
		this.codigoVendedor = codigoVendedor;
		this.ba = ba;
		tblDiasDemora.setModel(modeloTablaDiasDemora);
		tblDiasDemora.getTableHeader().setReorderingAllowed(false);
		tblHistorialPagos.setModel(modeloHistorial);
		tblHistorialPagos.getTableHeader().setReorderingAllowed(false);
		((DefaultTableCellRenderer) tblDiasDemora.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblDiasDemora.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);
		((DefaultTableCellRenderer) tblHistorialPagos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblHistorialPagos.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);
		cargarDatosHojaMaestraCredito(this.codigoCliente,this.codigoVendedor);
		renderizadorVencimientoNeto = new RenderizadorVencimientoNeto();
		renderizadorPagos = new RenderizadorHistorialPagos();
		tblDiasDemora.setDefaultRenderer(String.class, renderizadorVencimientoNeto);
		tblHistorialPagos.setDefaultRenderer(String.class, renderizadorPagos);
		chkBloqueoCredito.setEnabled(false);
	}

	private void cargarDatosHojaMaestraCredito(String codigoCliente, String codigoVendedor) {
		try {
			limpiarCajasDeComponentes();
			SqlCabeceraHojaMaestraCreditoImpl sqlCabeceraHojaMaestraCreditoImpl = new SqlCabeceraHojaMaestraCreditoImpl();
			CabeceraHojaMaestraCredito cabeceraHojaMaestraCredito = sqlCabeceraHojaMaestraCreditoImpl.obtenerCabeceraHojaMaestraCredito(codigoCliente, codigoVendedor);
			if (cabeceraHojaMaestraCredito != null) {
				this.setTitle("Hoja Maestra de Créditos: " + this.codigoCliente + " - "+ cabeceraHojaMaestraCredito.getNombreCompletoCliente());
				lblTitulo.setText("Hoja Maestra de Créditos: " + this.codigoCliente + " - " + cabeceraHojaMaestraCredito.getNombreCompletoCliente());
				txtLimiteCredito.setText(cabeceraHojaMaestraCredito.getLimiteCredito());
				txtClaseRiesgo.setText(cabeceraHojaMaestraCredito.getClaseRiesgo());
				txtCupoDisponible.setText(cabeceraHojaMaestraCredito.getCupoDisponible());
				txtValorVencido.setText(cabeceraHojaMaestraCredito.getValorVencido());
				txtFUD.setText(cabeceraHojaMaestraCredito.getFuds());
				txtNC.setText(cabeceraHojaMaestraCredito.getNotaCredito());
				txtProt.setText(cabeceraHojaMaestraCredito.getProtestante());
				if (("X").equalsIgnoreCase(cabeceraHojaMaestraCredito.getBloqueoCredito())) {
					chkBloqueoCredito.setSelected(true);
				} else {
					chkBloqueoCredito.setSelected(false);
				}
				this.nombreCompletoCliente = cabeceraHojaMaestraCredito.getNombreCompletoCliente();
			}
			SqlDiaDemoraTrasVencimientoImpl sqlDiaDemoraTrasVencimientoImpl = new SqlDiaDemoraTrasVencimientoImpl();
			List<DiaDemoraTrasVencimiento> listaDiaDemoraTrasVencimiento = sqlDiaDemoraTrasVencimientoImpl.obtenerListaDiaDemoraTrasVencimiento(Util.eliminarCerosInicios(codigoCliente));
			for (DiaDemoraTrasVencimiento diaDemoraTrasVencimiento : listaDiaDemoraTrasVencimiento) {
				this.modeloTablaDiasDemora.agregarDiaDemoraVencimiento(diaDemoraTrasVencimiento);
			}
			SqlHistorialPagoImpl sqlHistorialPagoImpl = new SqlHistorialPagoImpl();
			List<HistorialPago> listaHistorialPago = sqlHistorialPagoImpl.obtenerListaHistorialPago(codigoCliente);
			for (HistorialPago historialPago : listaHistorialPago) {
				this.modeloHistorial.agregarHistorialPago(historialPago);
			}
			SqlValorPorVencerImpl sqlValorPorVencerImpl = new SqlValorPorVencerImpl();
			List<ValorPorVencer> listaValorPorVencer = sqlValorPorVencerImpl.obtenerListaValorPorVencer(codigoCliente);
			if (listaValorPorVencer.size() > 0) {
				lblMes1.setText(listaValorPorVencer.get(0).getMesAnio());
				txtMes1.setText(listaValorPorVencer.get(0).getCantidad());
				lblMes2.setText(listaValorPorVencer.get(1).getMesAnio());
				txtMes2.setText(listaValorPorVencer.get(1).getCantidad());
				lblMes3.setText(listaValorPorVencer.get(2).getMesAnio());
				txtMes3.setText(listaValorPorVencer.get(2).getCantidad());
				lblMes4.setText(listaValorPorVencer.get(3).getMesAnio());
				txtMes4.setText(listaValorPorVencer.get(3).getCantidad());
				lblMes5.setText(listaValorPorVencer.get(4).getMesAnio());
				txtMes5.setText(listaValorPorVencer.get(4).getCantidad());
				lblMes6.setText(listaValorPorVencer.get(5).getMesAnio());
				txtMes6.setText(listaValorPorVencer.get(5).getCantidad());
			}
			SqlNotaCreditoImpl sqlNotaCreditoImpl = new SqlNotaCreditoImpl();
			listaNotaCredito = sqlNotaCreditoImpl.obtenerListaNotaCredito(codigoCliente);
			SqlProtestoImpl sqlProtestoImpl = new SqlProtestoImpl();
			listaProtesto = sqlProtestoImpl.obtenerListaProtesto(codigoCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void limpiarCajasDeComponentes() {
		txtLimiteCredito.setText("");
		txtClaseRiesgo.setText("");
		txtCupoDisponible.setText("");
		txtValorVencido.setText("");
		txtFUD.setText("");
		txtNC.setText("");
		txtProt.setText("");
		chkBloqueoCredito.setSelected(false);
		this.modeloTablaDiasDemora.limpiar();
		this.modeloHistorial.limpiar();
		lblMes1.setText("");
		txtMes1.setText("");
		lblMes2.setText("");
		txtMes2.setText("");
		lblMes3.setText("");
		txtMes3.setText("");
		lblMes4.setText("");
		txtMes4.setText("");
		lblMes5.setText("");
		txtMes5.setText("");
		lblMes6.setText("");
		txtMes6.setText("");
	}

	private void initComponents() {
		lblTitulo = new javax.swing.JLabel();
		pnlCentral = new javax.swing.JPanel();
		barraHerramientas = new javax.swing.JToolBar();
		btnPISel = new javax.swing.JButton();
		jSeparator4 = new javax.swing.JToolBar.Separator();
		btnCobranza = new javax.swing.JButton();
		jSeparator3 = new javax.swing.JToolBar.Separator();
		btnAnticipo = new javax.swing.JButton();
		jSeparator2 = new javax.swing.JToolBar.Separator();
		btnCancelar = new javax.swing.JButton();
		pnlContenedor = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		lblTituloDatosCredito = new javax.swing.JLabel();
		pnlDatosCredito = new javax.swing.JPanel();
		lblLimiteCredito = new javax.swing.JLabel();
		lblCupoDisponible = new javax.swing.JLabel();
		lblFUD = new javax.swing.JLabel();
		lblNC = new javax.swing.JLabel();
		lblProt = new javax.swing.JLabel();
		txtLimiteCredito = new javax.swing.JTextField();
		txtCupoDisponible = new javax.swing.JTextField();
		txtFUD = new javax.swing.JTextField();
		txtNC = new javax.swing.JTextField();
		txtProt = new javax.swing.JTextField();
		lblClaseRiesgo = new javax.swing.JLabel();
		lblValorVencido = new javax.swing.JLabel();
		txtClaseRiesgo = new javax.swing.JTextField();
		txtValorVencido = new javax.swing.JTextField();
		btnDt1 = new javax.swing.JButton();
		btnDt2 = new javax.swing.JButton();
		btnDt3 = new javax.swing.JButton();
		chkBloqueoCredito = new javax.swing.JCheckBox();
		pnlInformativo = new javax.swing.JPanel();
		pnlContenedorValoresPorVencer = new javax.swing.JPanel();
		lblTituloValoresPorVencer = new javax.swing.JLabel();
		pnlValoresPorVencer = new javax.swing.JPanel();
		pnlEtiquetasMeses = new javax.swing.JPanel();
		lblMes1 = new javax.swing.JLabel();
		lblMes2 = new javax.swing.JLabel();
		lblMes3 = new javax.swing.JLabel();
		lblMes4 = new javax.swing.JLabel();
		lblMes5 = new javax.swing.JLabel();
		lblMes6 = new javax.swing.JLabel();
		pnlCajasTextoMeses = new javax.swing.JPanel();
		txtMes1 = new javax.swing.JTextField();
		txtMes2 = new javax.swing.JTextField();
		txtMes3 = new javax.swing.JTextField();
		txtMes4 = new javax.swing.JTextField();
		txtMes5 = new javax.swing.JTextField();
		txtMes6 = new javax.swing.JTextField();
		pnlContenedorTablas = new javax.swing.JPanel();
		pnlDiasDemora = new javax.swing.JPanel();
		lblTituloDiasDemora = new javax.swing.JLabel();
		pnlTablaDiasDemora = new javax.swing.JPanel();
		scrDiasDemora = new javax.swing.JScrollPane();
		tblDiasDemora = new javax.swing.JTable();
		pnlHistorialPagos = new javax.swing.JPanel();
		lblHistorialPagos = new javax.swing.JLabel();
		pnlTablaHistorialPagos = new javax.swing.JPanel();
		scrHistorialPagos = new javax.swing.JScrollPane();
		tblHistorialPagos = new javax.swing.JTable();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Hoja Maestra de Créditos:");

		lblTitulo.setBackground(new java.awt.Color(175, 200, 222));
		lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18));
		lblTitulo.setText("Hoja Maestra de Créditos: ");
		lblTitulo.setOpaque(true);
		lblTitulo.setVisible(false);
		getContentPane().add(lblTitulo, java.awt.BorderLayout.PAGE_START);

		pnlCentral.setLayout(new java.awt.BorderLayout());

		barraHerramientas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		barraHerramientas.setFloatable(false);
		barraHerramientas.setRollover(true);

		btnPISel.setText("PI Sel.");
		btnPISel.setFocusable(false);
		btnPISel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnPISel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnPISel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPISelActionPerformed(evt);
			}
		});
		barraHerramientas.add(btnPISel);
		barraHerramientas.add(jSeparator4);

		btnCobranza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon_money.png"))); // NOI18N
		btnCobranza.setText("Cobranza");
		btnCobranza.setFocusable(false);
		btnCobranza.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnCobranza.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCobranzaActionPerformed(evt);
			}
		});
		barraHerramientas.add(btnCobranza);
		barraHerramientas.add(jSeparator3);

		btnAnticipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/gnome_finance.png"))); // NOI18N
		btnAnticipo.setText("Anticipo");
		btnAnticipo.setFocusable(false);
		btnAnticipo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		btnAnticipo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnAnticipo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAnticipoActionPerformed(evt);
			}
		});
		barraHerramientas.add(btnAnticipo);
		barraHerramientas.add(jSeparator2);

		btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar_24.gif"))); // NOI18N
		btnCancelar.setText("Cancelar");
		btnCancelar.setFocusable(false);
		btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelarActionPerformed(evt);
			}
		});
		barraHerramientas.add(btnCancelar);

		pnlCentral.add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

		jPanel2.setLayout(new java.awt.BorderLayout());

		lblTituloDatosCredito.setBackground(new java.awt.Color(175, 200, 222));
		lblTituloDatosCredito.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTituloDatosCredito.setText("Datos de Crédito");
		lblTituloDatosCredito.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 1));
		lblTituloDatosCredito.setOpaque(true);
		jPanel2.add(lblTituloDatosCredito, java.awt.BorderLayout.PAGE_START);

		pnlDatosCredito.setBackground(new java.awt.Color(255, 255, 255));

		lblLimiteCredito.setText("Límite de crédito:");

		lblCupoDisponible.setText("Cupo disponible:");

		lblFUD.setText("FUDs:");

		lblNC.setText("N/C:");

		lblProt.setText("Prot.:");

		txtLimiteCredito.setBackground(new java.awt.Color(175, 200, 222));
		txtLimiteCredito.setEditable(false);
		txtLimiteCredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		txtCupoDisponible.setBackground(new java.awt.Color(175, 200, 222));
		txtCupoDisponible.setEditable(false);
		txtCupoDisponible.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		txtFUD.setBackground(new java.awt.Color(175, 200, 222));
		txtFUD.setEditable(false);
		txtFUD.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		txtNC.setBackground(new java.awt.Color(175, 200, 222));
		txtNC.setEditable(false);
		txtNC.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		txtProt.setBackground(new java.awt.Color(175, 200, 222));
		txtProt.setEditable(false);
		txtProt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		lblClaseRiesgo.setText("Clase de riesgo:");

		lblValorVencido.setText("Valor vencido:");

		txtClaseRiesgo.setBackground(new java.awt.Color(175, 200, 222));
		txtClaseRiesgo.setEditable(false);

		txtValorVencido.setBackground(new java.awt.Color(175, 200, 222));
		txtValorVencido.setEditable(false);

		btnDt1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cobranzas.png"))); // NOI18N
		btnDt1.setBorder(null);
		btnDt1.setBorderPainted(false);
		btnDt1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDt1ActionPerformed(evt);
			}
		});

		btnDt2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cobranzas.png"))); // NOI18N
		btnDt2.setBorder(null);
		btnDt2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDt2ActionPerformed(evt);
			}
		});

		btnDt3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cobranzas.png"))); // NOI18N
		btnDt3.setBorder(null);
		btnDt3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDt3ActionPerformed(evt);
			}
		});

		chkBloqueoCredito.setText("Bloqueo Crédito");
		chkBloqueoCredito.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		chkBloqueoCredito.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		chkBloqueoCredito.setOpaque(false);

		javax.swing.GroupLayout pnlDatosCreditoLayout = new javax.swing.GroupLayout(pnlDatosCredito);
		pnlDatosCredito.setLayout(pnlDatosCreditoLayout);
		pnlDatosCreditoLayout.setHorizontalGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addContainerGap()
			.addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addComponent(lblLimiteCredito,javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(txtLimiteCredito, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addComponent(lblCupoDisponible, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(txtCupoDisponible, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addComponent(lblFUD, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(txtFUD, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addComponent(lblNC, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(txtNC, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addComponent(lblProt, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(txtProt, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
			.addGap(18, 18, 18).addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addComponent(lblClaseRiesgo, javax.swing.GroupLayout.DEFAULT_SIZE, 77,	Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(txtClaseRiesgo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addComponent(lblValorVencido, javax.swing.GroupLayout.DEFAULT_SIZE, 77,	Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(txtValorVencido, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
			.addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
			.addComponent(btnDt3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(btnDt2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(btnDt1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(chkBloqueoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addContainerGap()));
		pnlDatosCreditoLayout.setVerticalGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlDatosCreditoLayout.createSequentialGroup().addContainerGap()
			.addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
			.addComponent(txtLimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addComponent(lblClaseRiesgo).addComponent(txtClaseRiesgo, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addComponent(lblLimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
			.addComponent(lblCupoDisponible).addComponent(txtCupoDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addComponent(lblValorVencido).addComponent(txtValorVencido, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addComponent(chkBloqueoCredito)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
			.addComponent(lblFUD).addComponent(txtFUD, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addComponent(btnDt1)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
			.addComponent(lblNC).addComponent(txtNC, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addComponent(btnDt2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(pnlDatosCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
			.addComponent(lblProt).addComponent(txtProt, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addComponent(btnDt3)).addContainerGap()));

		pnlDatosCreditoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] 
		       { txtClaseRiesgo, txtCupoDisponible, txtFUD, txtLimiteCredito, txtNC, txtProt, txtValorVencido });

		jPanel2.add(pnlDatosCredito, java.awt.BorderLayout.CENTER);

		pnlInformativo.setLayout(new java.awt.BorderLayout(5, 5));

		pnlContenedorValoresPorVencer.setPreferredSize(new java.awt.Dimension(240, 284));
		pnlContenedorValoresPorVencer.setLayout(new java.awt.BorderLayout());

		lblTituloValoresPorVencer.setBackground(new java.awt.Color(175, 200, 222));
		lblTituloValoresPorVencer.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTituloValoresPorVencer.setText("Valores por Vencer");
		lblTituloValoresPorVencer.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 1));
		lblTituloValoresPorVencer.setOpaque(true);
		pnlContenedorValoresPorVencer.add(lblTituloValoresPorVencer, java.awt.BorderLayout.PAGE_START);

		pnlValoresPorVencer.setBackground(new java.awt.Color(255, 255, 255));

		pnlEtiquetasMeses.setOpaque(false);
		pnlEtiquetasMeses.setLayout(new java.awt.GridLayout(6, 1, 4, 4));

		lblMes1.setText("OCT-12");
		pnlEtiquetasMeses.add(lblMes1);

		lblMes2.setText("NOV-12");
		pnlEtiquetasMeses.add(lblMes2);

		lblMes3.setText("DIC-12");
		pnlEtiquetasMeses.add(lblMes3);

		lblMes4.setText("ENE-13");
		pnlEtiquetasMeses.add(lblMes4);

		lblMes5.setText("FEB-13");
		pnlEtiquetasMeses.add(lblMes5);

		lblMes6.setText(">");
		pnlEtiquetasMeses.add(lblMes6);

		pnlCajasTextoMeses.setOpaque(false);
		pnlCajasTextoMeses.setLayout(new java.awt.GridLayout(6, 1, 4, 4));

		txtMes1.setBackground(new java.awt.Color(175, 200, 222));
		txtMes1.setEditable(false);
		txtMes1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlCajasTextoMeses.add(txtMes1);

		txtMes2.setBackground(new java.awt.Color(175, 200, 222));
		txtMes2.setEditable(false);
		txtMes2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlCajasTextoMeses.add(txtMes2);

		txtMes3.setBackground(new java.awt.Color(175, 200, 222));
		txtMes3.setEditable(false);
		txtMes3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlCajasTextoMeses.add(txtMes3);

		txtMes4.setBackground(new java.awt.Color(175, 200, 222));
		txtMes4.setEditable(false);
		txtMes4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlCajasTextoMeses.add(txtMes4);

		txtMes5.setBackground(new java.awt.Color(175, 200, 222));
		txtMes5.setEditable(false);
		txtMes5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlCajasTextoMeses.add(txtMes5);

		txtMes6.setBackground(new java.awt.Color(175, 200, 222));
		txtMes6.setEditable(false);
		txtMes6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		pnlCajasTextoMeses.add(txtMes6);

		javax.swing.GroupLayout pnlValoresPorVencerLayout = new javax.swing.GroupLayout(pnlValoresPorVencer);
		pnlValoresPorVencer.setLayout(pnlValoresPorVencerLayout);
		pnlValoresPorVencerLayout.setHorizontalGroup(pnlValoresPorVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlValoresPorVencerLayout.createSequentialGroup().addContainerGap()
				.addComponent(pnlEtiquetasMeses,javax.swing.GroupLayout.PREFERRED_SIZE, 70,	javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlCajasTextoMeses,javax.swing.GroupLayout.PREFERRED_SIZE, 117,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(37, Short.MAX_VALUE)));
		pnlValoresPorVencerLayout.setVerticalGroup(pnlValoresPorVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlValoresPorVencerLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlValoresPorVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
				.addComponent(pnlCajasTextoMeses, javax.swing.GroupLayout.Alignment.LEADING, 0, 0,Short.MAX_VALUE)
				.addComponent(pnlEtiquetasMeses, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
				.addContainerGap()));

		pnlContenedorValoresPorVencer.add(pnlValoresPorVencer,java.awt.BorderLayout.CENTER);

		pnlInformativo.add(pnlContenedorValoresPorVencer,java.awt.BorderLayout.LINE_END);

		pnlContenedorTablas.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

		pnlDiasDemora.setLayout(new java.awt.BorderLayout());

		lblTituloDiasDemora.setBackground(new java.awt.Color(175, 200, 222));
		lblTituloDiasDemora.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblTituloDiasDemora.setText("Días de demora tras vencimiento neto");
		lblTituloDiasDemora.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 1));
		lblTituloDiasDemora.setOpaque(true);
		pnlDiasDemora.add(lblTituloDiasDemora, java.awt.BorderLayout.PAGE_START);

		pnlTablaDiasDemora.setBackground(new java.awt.Color(255, 255, 255));
		pnlTablaDiasDemora.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlTablaDiasDemora.setLayout(new java.awt.BorderLayout());

		tblDiasDemora.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { 
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null } }, 
				new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		scrDiasDemora.setViewportView(tblDiasDemora);

		pnlTablaDiasDemora.add(scrDiasDemora, java.awt.BorderLayout.CENTER);

		pnlDiasDemora.add(pnlTablaDiasDemora, java.awt.BorderLayout.CENTER);

		pnlContenedorTablas.add(pnlDiasDemora);

		pnlHistorialPagos.setLayout(new java.awt.BorderLayout());

		lblHistorialPagos.setBackground(new java.awt.Color(175, 200, 222));
		lblHistorialPagos.setFont(new java.awt.Font("Tahoma", 1, 11));
		lblHistorialPagos.setText("Historial de Pagos");
		lblHistorialPagos.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 1));
		lblHistorialPagos.setOpaque(true);
		pnlHistorialPagos.add(lblHistorialPagos, java.awt.BorderLayout.PAGE_START);

		pnlTablaHistorialPagos.setBackground(new java.awt.Color(255, 255, 255));
		pnlTablaHistorialPagos.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlTablaHistorialPagos.setLayout(new java.awt.BorderLayout());

		tblHistorialPagos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { 
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null } },
				new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		scrHistorialPagos.setViewportView(tblHistorialPagos);

		pnlTablaHistorialPagos.add(scrHistorialPagos, java.awt.BorderLayout.CENTER);

		pnlHistorialPagos.add(pnlTablaHistorialPagos, java.awt.BorderLayout.CENTER);

		pnlContenedorTablas.add(pnlHistorialPagos);

		pnlInformativo.add(pnlContenedorTablas, java.awt.BorderLayout.CENTER);

		javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
		pnlContenedor.setLayout(pnlContenedorLayout);
		pnlContenedorLayout.setHorizontalGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
			.addComponent(pnlInformativo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE));
		pnlContenedorLayout.setVerticalGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlContenedorLayout.createSequentialGroup()
			.addComponent(jPanel2,javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(pnlInformativo, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)));

		pnlCentral.add(pnlContenedor, java.awt.BorderLayout.CENTER);

		getContentPane().add(pnlCentral, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void btnPISelActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					SqlPartidaIndividualImpl sqlPIImpl = new SqlPartidaIndividualImpl();
					List<PartidaIndividual> partidas = sqlPIImpl.obtenerListaPartidaIndividual(codigoCliente);
					DialogoPartidasIndividuales dlgPIs = new DialogoPartidasIndividuales(Promesa.getInstance(), true, partidas,codigoCliente,codigoVendedor);
					dlgPIs.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnCobranzaActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					BeanDato usuario = Promesa.datose.get(0);
					if(usuario.getStrModo().equals("1")){
//					//Nelson Villegas-llamada a metodo de sincronizacion
						ActualizarPartidasAbiertasClientes();
					}
					IRegistroPagoCliente iR = new IRegistroPagoCliente(ba,nombreCompletoCliente);
					Promesa.destokp.add(iR);
					iR.setVisible(true);
					iR.setMaximum(true);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnAnticipoActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					IRegistroAnticipo iR = new IRegistroAnticipo(ba,codigoVendedor,nombreCompletoCliente);
					Promesa.destokp.add(iR);
					iR.setVisible(true);
					iR.setMaximum(true);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		int tipo = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la ventana?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (tipo == JOptionPane.OK_OPTION) {
			this.dispose();
		}
	}
	public String verificarVisitas(String strMensaje, String codigoCliente){
		BeanDato usuario = Promesa.datose.get(0);
		BeanAgenda ba = new BeanAgenda();
		Util util = new Util();
		boolean istrue = false; 
		ba.setVENDOR_ID(usuario.getStrCodigo());
		SqlAgenda getAgenda = new SqlAgendaImpl();
		getAgenda.setListaAgenda(ba);
		List<BeanAgenda> listaAgenda =getAgenda.getListaAgenda();
		for(int i=0;i<listaAgenda.size();i++){
			if(listaAgenda.get(i).getCUST_ID().equals(codigoCliente)){
				Date date = util.convierteStringaDate(listaAgenda.get(i).getBEGDA());
				String[] hora = listaAgenda.get(i).getTIME().trim().split(":");
				Calendar cal = Calendar.getInstance(); // creates calendar
			    cal.setTime(date); // sets calendar time/date
			    cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hora[0])); // adds n hour
			    cal.add(Calendar.MINUTE, Integer.parseInt(hora[1])); // adds n minutes
				date = cal.getTime(); // returns new date object, one hour in the future
				if(date.after(new Date())){
					istrue=true;
				}
			}
		}
		if(!istrue){
			strMensaje="<html>"+strMensaje+"<br>"+"El cliente no tiene proximas visitas. "+"<br>"+"Por favor, agende su proxima visita!!"+"</html>";
		}
		return strMensaje;
	}
	private void btnDt1ActionPerformed(java.awt.event.ActionEvent evt) {
		// FUD's
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					if (dlgDetalleCarteraPendienteDevolucion == null) {
						SqlPedidoPendienteDevolucionImpl sqlPedidoImpl = new SqlPedidoPendienteDevolucionImpl();
						List<PedidoPendienteDevolucion> devoluciones =  sqlPedidoImpl.obtenerListaPedidoPendienteDevolucion(codigoCliente);
						dlgDetalleCarteraPendienteDevolucion = new DialogoDetalleCarteraPendienteDevolucion (Promesa.getInstance(), true, devoluciones);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					bloqueador.dispose();
					if (dlgDetalleCarteraPendienteDevolucion != null) {
						dlgDetalleCarteraPendienteDevolucion.setVisible(true);
					}
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void btnDt2ActionPerformed(java.awt.event.ActionEvent evt) {
		// N/C
		DialogoDetalleCartera dlg = new DialogoDetalleCartera(Promesa.getInstance(), true, "Notas de crédito", listaNotaCredito);
		dlg.setVisible(true);
	}

	private void btnDt3ActionPerformed(java.awt.event.ActionEvent evt) {
		
		DialogoDetalleCartera dlg = new DialogoDetalleCartera(Promesa.getInstance(), true, "Protestos", listaProtesto);
		dlg.setVisible(true);
	}
	
	private void ActualizarPartidasAbiertasClientes()
	{
		SCobranzas sCobranzas = null;
		Object[] listas;
		String codigoCliente1 = Util.completarCeros(10, codigoCliente);
		long contador = 1;
		List<PartidaIndividualAbierta> partidas = new ArrayList<PartidaIndividualAbierta>();
		List<DetallePagoPartidaIndividualAbierta> detalles = new ArrayList<DetallePagoPartidaIndividualAbierta>();
		sCobranzas = new SCobranzas();
		String fHasta = Util.convierteFechaAFormatoYYYYMMdd(new Date());
		listas = sCobranzas.obtenerArregloDeListaPartidaIndividualAbierta(codigoCliente1, fHasta, contador);
		@SuppressWarnings("unchecked")
		List<PartidaIndividualAbierta> pias = (List<PartidaIndividualAbierta>) listas[0];
		@SuppressWarnings("unchecked")
		List<DetallePagoPartidaIndividualAbierta> dppias = (List<DetallePagoPartidaIndividualAbierta>) listas[1];
		contador = (Long)listas[2];
		partidas.addAll(pias);
		detalles.addAll(dppias);
		
		if (partidas.size() > 0) {
			SqlPartidaIndividualAbiertaImpl sqlPIAImpl = new SqlPartidaIndividualAbiertaImpl();
			sqlPIAImpl.eliminarListaPartidaIndividualAbiertaPorCliente(codigoCliente1);
			sqlPIAImpl.insertarListaPartidaIndividualAbierta(partidas);
			
			SqlPartidaIndividualAbiertaImpl sql = new SqlPartidaIndividualAbiertaImpl();
			String cod = Util.completarCeros(10,codigoCliente1);
			@SuppressWarnings("unused")
			List<PartidaIndividualAbierta> abiertas = sql.obtenerListaPartidaIndividualAbierta(cod);
//			for (PartidaIndividualAbierta pia : listaPartidasIndividualesAbiertas) 
//			{
//				//Marcelo Moyano 06/05/2013 - 13:07
//				SqlPagoParcialImpl sqlpp = new SqlPagoParcialImpl();
//				List<PagoParcial> listaPagoParcial = null;
//					try {
//						listaPagoParcial = sqlpp.ObtenerImportePago(pia);
//						for(PagoParcial pp : listaPagoParcial){
//							SqlPagoRecibidoImpl sqlPagoRecibido = new SqlPagoRecibidoImpl();
//							Double importe = sqlPagoRecibido.ObtenerImportePagoRecibido(pp.getIdCabecera());
//							pia.setPszah(String.valueOf( Double.parseDouble(pia.getPszah())- importe));
//							pia.setPsprt(String.valueOf(Double.parseDouble(pia.getPsprt()) + importe));
//							try {
//								sqlPartidaIndividualAbiertaImpl.ActualizarImportePagoPartidasIndividuales(pp, Double.parseDouble(pia.getPszah()), Double.parseDouble(pia.getPsprt()));
//							} catch (NumberFormatException e) {
//								e.printStackTrace();
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}
//						}
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				// Fin Marcelo Moyano
//				for(DetallePagoPartidaIndividualAbierta detallePagoPartidaIndividualAbierta : lstDetallePagoPartidaIndividualAbierta){
//					if (pia.getDocNo().equals(detallePagoPartidaIndividualAbierta.getInvRef()) && pia.getItemNum().equals(detallePagoPartidaIndividualAbierta.getInvItem())){
//						detallePagoPartidaIndividualAbierta .setIdPartidaIndividualAbierta(pia.getIdPartidaIndividualAbierta());
//					}
//				}
//			}
		}
		if (detalles.size() > 0) {
			SqlDetallePagoPartidaIndividualAbiertaImpl sqlDPPIAImpl =  new SqlDetallePagoPartidaIndividualAbiertaImpl();
			sqlDPPIAImpl.insertarListaDetallePagoPartidaIndividualAbierta(detalles);
		}
		Promesa.getInstance().mostrarAvisoSincronizacion("");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int maxTabla(String tabla, String columna) {
		HashMap column = new HashMap();
		int filas = 0;
		HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
		column.put("String:0", "filas");
		String sqlSinc = "SELECT MAX(" + columna + ") as filas FROM " + tabla;
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlSinc, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = (HashMap) mapResultado.get(0);
		filas = Integer.parseInt(res.get("filas").toString());
		return filas;
	}

	// Variables declaration - do not modify
	private javax.swing.JToolBar barraHerramientas;
	private javax.swing.JButton btnAnticipo;
	private javax.swing.JButton btnCancelar;
	private javax.swing.JButton btnCobranza;
	private javax.swing.JButton btnDt1;
	private javax.swing.JButton btnDt2;
	private javax.swing.JButton btnDt3;
	private javax.swing.JButton btnPISel;
	private javax.swing.JCheckBox chkBloqueoCredito;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JToolBar.Separator jSeparator2;
	private javax.swing.JToolBar.Separator jSeparator3;
	private javax.swing.JToolBar.Separator jSeparator4;
	private javax.swing.JLabel lblClaseRiesgo;
	private javax.swing.JLabel lblCupoDisponible;
	private javax.swing.JLabel lblFUD;
	private javax.swing.JLabel lblHistorialPagos;
	private javax.swing.JLabel lblLimiteCredito;
	private javax.swing.JLabel lblMes1;
	private javax.swing.JLabel lblMes2;
	private javax.swing.JLabel lblMes3;
	private javax.swing.JLabel lblMes4;
	private javax.swing.JLabel lblMes5;
	private javax.swing.JLabel lblMes6;
	private javax.swing.JLabel lblNC;
	private javax.swing.JLabel lblProt;
	private javax.swing.JLabel lblTitulo;
	private javax.swing.JLabel lblTituloDatosCredito;
	private javax.swing.JLabel lblTituloDiasDemora;
	private javax.swing.JLabel lblTituloValoresPorVencer;
	private javax.swing.JLabel lblValorVencido;
	private javax.swing.JPanel pnlCajasTextoMeses;
	private javax.swing.JPanel pnlCentral;
	private javax.swing.JPanel pnlContenedor;
	private javax.swing.JPanel pnlContenedorTablas;
	private javax.swing.JPanel pnlContenedorValoresPorVencer;
	private javax.swing.JPanel pnlDatosCredito;
	private javax.swing.JPanel pnlDiasDemora;
	private javax.swing.JPanel pnlEtiquetasMeses;
	private javax.swing.JPanel pnlHistorialPagos;
	private javax.swing.JPanel pnlInformativo;
	private javax.swing.JPanel pnlTablaDiasDemora;
	private javax.swing.JPanel pnlTablaHistorialPagos;
	private javax.swing.JPanel pnlValoresPorVencer;
	private javax.swing.JScrollPane scrDiasDemora;
	private javax.swing.JScrollPane scrHistorialPagos;
	private javax.swing.JTable tblDiasDemora;
	private javax.swing.JTable tblHistorialPagos;
	private javax.swing.JTextField txtClaseRiesgo;
	private javax.swing.JTextField txtCupoDisponible;
	private javax.swing.JTextField txtFUD;
	private javax.swing.JTextField txtLimiteCredito;
	private javax.swing.JTextField txtMes1;
	private javax.swing.JTextField txtMes2;
	private javax.swing.JTextField txtMes3;
	private javax.swing.JTextField txtMes4;
	private javax.swing.JTextField txtMes5;
	private javax.swing.JTextField txtMes6;
	private javax.swing.JTextField txtNC;
	private javax.swing.JTextField txtProt;
	private javax.swing.JTextField txtValorVencido;
	// End of variables declaration
}