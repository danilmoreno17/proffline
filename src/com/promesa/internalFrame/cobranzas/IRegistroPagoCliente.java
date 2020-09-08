package com.promesa.internalFrame.cobranzas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.CabeceraRegistroPagoCliente;
import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.cobranzas.bean.PartidaIndividualAbierta;
import com.promesa.cobranzas.bean.RegistroPagoCliente;
import com.promesa.cobranzas.sql.impl.SqlBancoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlCabeceraRegistroPagoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlPagoParcialImpl;
import com.promesa.cobranzas.sql.impl.SqlPagoRecibidoImpl;
import com.promesa.cobranzas.sql.impl.SqlPartidaIndividualAbiertaImpl;
import com.promesa.cobranzas.sql.impl.SqlRegistroPagoClienteImpl;
import com.promesa.dialogo.cobranzas.DialogoComentario;
import com.promesa.impresiones.ReporteTicket;
import com.promesa.impresiones.cobranzas.DetalleTicketPago;
import com.promesa.impresiones.cobranzas.TicketPago;
import com.promesa.impresiones.cobranzas.TicketPagoOffline;
import com.promesa.impresiones.dpp350.TicketPagoDPP350;
import com.promesa.impresiones.dpp350.TicketPagoOfflineDpp350;
import com.promesa.internalFrame.cobranzas.custom.ModeloPagosRecibidos;
import com.promesa.internalFrame.cobranzas.custom.PanelColumnasPagosRecibidos;
import com.promesa.internalFrame.cobranzas.custom.PanelElementosPagosRecibidos;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorPagoParcial;
import com.promesa.internalFrame.planificacion.IVentanaVisita;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SCobranzas;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;
import com.promesa.util.ValidadorEntradasDeTexto;

@SuppressWarnings("serial")
public class IRegistroPagoCliente extends javax.swing.JInternalFrame implements FocusListener {

	private ModeloPagosRecibidos modeloPagosRecibidos;
	private String codigoCliente;
	private JTextField txtImportePago;
	private boolean banderaGrabaOnLine;
	private CabeceraRegistroPagoCliente cabecera;
	private int fuente = -1;
	List<TicketPago> listaTicketPago = new ArrayList<TicketPago>();
	List<TicketPagoDPP350> listaTicketPagoDPP350 = new ArrayList<TicketPagoDPP350>();
	SqlCabeceraRegistroPagoClienteImpl sqlCabeceraRegistroPagoClienteImpl = null;
	String nombreCompletoCliente = null;
	int secuenciaFormaPago = 1;
	private BeanAgenda ba;
	private boolean OrdenAscedenteDescendenteFechaVencimiento = false;
	private static final int VENCIMIENTO = 6;
	private static final int REFERENCIA = 3;
	private static final int POSICION = 5;
	
	private String strDispositivoImpresora;

	public IRegistroPagoCliente(BeanAgenda ba, String nombreCompletoCliente) {
		this.codigoCliente = ba.getStrCodigoCliente();
		this.ba =ba;
		initComponents();
		modeloPagosRecibidos = new ModeloPagosRecibidos();
		tblPagoParc.setModel(modeloPagosRecibidos);
		((DefaultTableCellRenderer) tblPagoParc.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblPagoParc.setRowHeight(Constante.ALTO_COLUMNAS);
		tblPagoParc.getTableHeader().setReorderingAllowed(false);
		tblPagoParc.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);
		tblPagoParc.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tblPagoParcOrdenarTablaMouseClicked(evt);
			}
		});
		tblPagoParc.setAutoCreateColumnsFromModel(false);

		setAnchoColumnas();
		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(codigoCliente);
		String idCliente = cliente.getStrIdCliente();
		String nombreCliente = cliente.getStrNombreCliente();
		String titulo = "Registro de Pago de Cliente: "+ idCliente + " "+ nombreCliente;
		lblTitulo.setText(titulo);
		this.setTitle(titulo);
		llenarTablaPartidasAbiertas();
		RenderizadorPagoParcial renderizadorPagoParcial = new RenderizadorPagoParcial(modeloPagosRecibidos);
		IngresarSecuenciaTabla(modeloPagosRecibidos);
		tblPagoParc.setDefaultRenderer(String.class, renderizadorPagoParcial);
		tblPagoParc.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tblPagoParcMouseClicked(evt);
			}
		});
		txtTotal.setEditable(false);
		txtTotal.setText("0.0");
		txtImpEntrado.setEditable(false);
		txtImpEntrado.setText("0.0");
		txtAsig.setEditable(false);
		txtAsig.setText("0.0");
		txtSinAsignar.setEditable(false);
		txtSinAsignar.setText("0.0");
		txtPartidasSel.setEditable(false);
		txtPartidasSel.setText("0");
		btnImprimirComprobante.setEnabled(false);
		txtTotal.setBackground(new Color(175, 200, 222));
		establecerOyenteTabla();
		agregarFormaPago();
		this.nombreCompletoCliente = nombreCompletoCliente;
		strDispositivoImpresora = Util.verificarImpresora();
	}

	private void agregarFormaPago() {
		PanelElementosPagosRecibidos pe = new PanelElementosPagosRecibidos(this, codigoCliente, secuenciaFormaPago);
		pnlGridPagosRecibidos.add(pe);
		pnlGridPagosRecibidos.updateUI();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public IRegistroPagoCliente(RegistroPagoCliente registroPago) {
		initComponents();
		fuente = Constante.FROM_DB;
		// -------- CARGA INICIAL --------------
		cabecera = registroPago.getCabeceraRegistroPagoCliente();
		List<PagoParcial> pagosParciales = registroPago.getListaPagoParcial();
		List<PagoRecibido> pagosRecibidos = registroPago.getListaPagoRecibido();
		this.codigoCliente = cabecera.getCodigoCliente();
		modeloPagosRecibidos = new ModeloPagosRecibidos();
		tblPagoParc.setModel(modeloPagosRecibidos);

		((DefaultTableCellRenderer) tblPagoParc.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblPagoParc.setRowHeight(Constante.ALTO_COLUMNAS);
		tblPagoParc.getTableHeader().setReorderingAllowed(false);
		tblPagoParc.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);

		setAnchoColumnas();
		SqlClienteImpl sql = new SqlClienteImpl();
		BeanCliente cliente = sql.buscarCliente(codigoCliente);
		String idCliente = cliente.getStrIdCliente();
		String nombreCliente = cliente.getStrNombreCliente();
		String titulo = "Registro de Pago de Cliente: " + idCliente + " " + nombreCliente;
		lblTitulo.setText(titulo);
		this.setTitle(titulo);
		llenarTablaPartidasAbiertasDeSQLite(pagosParciales);
		RenderizadorPagoParcial renderizadorPagoParcial = new RenderizadorPagoParcial(modeloPagosRecibidos);
		tblPagoParc.setDefaultRenderer(String.class, renderizadorPagoParcial);
		tblPagoParc.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tblPagoParcMouseClicked(evt);
			}
		});
		txtTotal.setEditable(false);
		txtImpEntrado.setEditable(false);
		txtAsig.setEditable(false);
		txtSinAsignar.setEditable(false);
		txtPartidasSel.setEditable(false);
		btnImprimirComprobante.setEnabled(false);
		txtTotal.setBackground(new Color(175, 200, 222));
		establecerOyenteTabla();
		String total = "" + cabecera.getImporteEntrado();
		txtTotal.setText(total);
		txtImpEntrado.setText(total);
		String asig = "" + cabecera.getImporteAsignado();
		txtAsig.setText(asig);
		String sinAsig = "" + cabecera.getImporteSinAsignar();
		txtSinAsignar.setText(sinAsig);
		String partidaSel = "" + cabecera.getPartidasSeleccionadas();
		txtPartidasSel.setText(partidaSel);
		llenarFormasPago(pagosRecibidos);
		btnGrabar.setEnabled(false);
		btnActivar.setEnabled(false);
		btnDesactivar.setEnabled(false);
		btnAniadir.setEnabled(false);
		btnEliminar.setEnabled(false);
	}

	private void llenarFormasPago(List<PagoRecibido> pagosRecibidos) {
		for (PagoRecibido pagoRecibido : pagosRecibidos) {
			secuenciaFormaPago++;
			PanelElementosPagosRecibidos pe = new PanelElementosPagosRecibidos(this, codigoCliente, secuenciaFormaPago);
			pe.actualizarDatos(pagoRecibido);
			pnlGridPagosRecibidos.add(pe);
		}
		pnlGridPagosRecibidos.updateUI();
	}

	private void establecerOyenteTabla() {
		txtImportePago = new JTextField();
		txtImportePago.setHorizontalAlignment(SwingConstants.RIGHT);
		ValidadorEntradasDeTexto validadorCodigos = new ValidadorEntradasDeTexto(15, Constante.DECIMALES);
		TableColumn columnaImporte = tblPagoParc.getColumnModel().getColumn(9);
		txtImportePago.setDocument(validadorCodigos);
		txtImportePago.setBorder(BorderFactory.createEmptyBorder());
		DefaultCellEditor singleclickCodigo = new DefaultCellEditor(txtImportePago);
		singleclickCodigo.setClickCountToStart(1);
		columnaImporte.setCellEditor(singleclickCodigo);
		txtImportePago.addFocusListener(this);
		KeyListener keyListenerImporte = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tblPagoParc.getCellEditor() != null) {
						tblPagoParc.getCellEditor().stopCellEditing();
					}
					modeloPagosRecibidos.validarImportesIngresados();
					tblPagoParc.updateUI();
					recalcularTodo();
				}
			}
			public void keyReleased(KeyEvent keyEvent) {
			}
			public void keyTyped(KeyEvent keyEvent) {
			}
		};
		txtImportePago.addKeyListener(keyListenerImporte);
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE LANZA EL EVENTO DE CLICK DEL
	 * 			MOUSE EN LA CABECERA DE LA JTABLE PARA 
	 * 			ORDENAR LA TABLA POR COLUMNA.
	 * 
	 * @param	evt EVENTO LANZADO CUANDO SE DA CLICK
	 * 			A LA CABECERA DE LA TABLA.
	 */
	private void tblPagoParcOrdenarTablaMouseClicked(java.awt.event.MouseEvent evt) {
		final int columna = tblPagoParc.columnAtPoint(evt.getPoint());
		for (int i = 0; i < modeloPagosRecibidos.getRowCount(); i++) {
			PagoParcial pp = modeloPagosRecibidos.obtenerPagoParcial(i);
			if (pp.isDesplegado()) {
				modeloPagosRecibidos.eliminarHijos(pp.getIdPagoParcial());
				pp.setDesplegado(false);
			}
		}
		if (columna == REFERENCIA || columna == VENCIMIENTO || columna == POSICION) {
			if (OrdenAscedenteDescendenteFechaVencimiento) {
				quickSort(columna, 0, modeloPagosRecibidos.getRowCount() - 1);
				OrdenAscedenteDescendenteFechaVencimiento = false;
			} else {
				quickSort(columna, 0, modeloPagosRecibidos.getRowCount() - 1);
				OrdenAscedenteDescendenteFechaVencimiento = true;
			}
		}
		tblPagoParc.updateUI();
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO DE ORDENAMIENTO.
	 * 
	 * @param	columna	SE ORDENA CON RESPECTO A LA COLUMMNA.
	 * @param	left POSOCIÓN INICIAL DEL VECTOR A ORDENAR.
	 * @param	rigth POSICIÓN FINAL DEL VECTOR A ORDENAR.
	 * 
	 * Reference http://www.algolist.net/Algorithms/Sorting/Quicksort
	 */
	private void quickSort(int columna, int left, int right) {
		int index = 0;
		if (columna == VENCIMIENTO) {// Ordena Por la Columna de FECHA DE VENCIMIENTO
			index = partitionFechaVencimiento(left, right);
		} else if (columna == REFERENCIA) {// Ordena Por la Columna de REFERENCIA
			index = partition(left, right);
		} else if (columna == POSICION) {// Ordena por la Columna de POSICIÓN
			index = partitionXPosition(left, right);
		}
		if (left < index - 1)
			quickSort(columna, left, index - 1);
		if (index < right)
			quickSort(columna, index, right);
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE DIVIDE EL VERCTOR A ORDENAR
	 * 			PARA QUE EN ORDENAMIENTO SE REALIZE POR
	 * 			REGIONES.
	 * 
	 * @param	left POSOCIÓN INICIAL DEL VECTOR A ORDENAR.
	 * @param	rigth POSICIÓN FINAL DEL VECTOR A ORDENAR.
	 * 
	 * @return	i RETORNA LA POSICIÓN EN QUE DIVIDE EL VECTOR.
	 */
	private int partition(int left, int right) {
		int i = left, j = right;
		int pivot = (left + right) / 2;
		PagoParcial ppPivot = modeloPagosRecibidos.obtenerPagoParcial(pivot);
		String ppReferencia = ppPivot.getReferencia() + ppPivot.getPosicion();
		PagoParcial pptmp = null;
		while (i <= j) {
			while (((modeloPagosRecibidos.obtenerPagoParcial(i)).getReferencia() + (modeloPagosRecibidos.obtenerPagoParcial(i)).getPosicion()).compareTo(ppReferencia) < 0)
				i++;
			while (((modeloPagosRecibidos.obtenerPagoParcial(j)).getReferencia() + (modeloPagosRecibidos.obtenerPagoParcial(j)).getPosicion()).compareTo(ppReferencia) > 0)
				j--;
			if (i <= j) {
				pptmp = modeloPagosRecibidos.obtenerPagoParcial(i);
				modeloPagosRecibidos.ActualizarModeloPagosRecibidos(modeloPagosRecibidos.obtenerPagoParcial(j), i);
				modeloPagosRecibidos.ActualizarModeloPagosRecibidos(pptmp, j);
				i++;
				j--;
			}
		}
		return i;
	}
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE DIVIDE EL VERCTOR A ORDENAR
	 * 			PARA QUE EN ORDENAMIENTO SE REALIZE POR
	 * 			REGIONES.
	 * 
	 * @param	left POSOCIÓN INICIAL DEL VECTOR A ORDENAR.
	 * @param	rigth POSICIÓN FINAL DEL VECTOR A ORDENAR.
	 * 
	 * @return	i RETORNA LA POSICIÓN EN QUE DIVIDE EL VECTOR.
	 */
	private int partitionXPosition(int left, int right) {
		int i = left, j = right;
		int pivot = (left + right) / 2;
		PagoParcial ppPivot = modeloPagosRecibidos.obtenerPagoParcial(pivot);
		String ppPosicion = ppPivot.getPosicion() + ppPivot.getReferencia();
		PagoParcial pptmp = null;
		while (i <= j) {
			while (((modeloPagosRecibidos.obtenerPagoParcial(i)).getPosicion() + (modeloPagosRecibidos.obtenerPagoParcial(i)).getReferencia()).compareTo(ppPosicion) < 0)
				i++;
			while (((modeloPagosRecibidos.obtenerPagoParcial(j)).getPosicion() + (modeloPagosRecibidos.obtenerPagoParcial(j)).getReferencia()) .compareTo(ppPosicion) > 0)
				j--;
			if (i <= j) {
				pptmp = modeloPagosRecibidos.obtenerPagoParcial(i);
				modeloPagosRecibidos.ActualizarModeloPagosRecibidos(modeloPagosRecibidos.obtenerPagoParcial(j), i);
				modeloPagosRecibidos.ActualizarModeloPagosRecibidos(pptmp, j);
				i++;
				j--;
			}
		}
		return i;
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE DIVIDE EL VERCTOR A ORDENAR
	 * 			PARA QUE EN ORDENAMIENTO SE REALIZE POR
	 * 			REGIONES.
	 * 
	 * @param	left POSOCIÓN INICIAL DEL VECTOR A ORDENAR.
	 * @param	rigth POSICIÓN FINAL DEL VECTOR A ORDENAR.
	 * 
	 * @return	i RETORNA LA POSICIÓN EN QUE DIVIDE EL VECTOR.
	 */
	private int partitionFechaVencimiento(int left, int right) {
		int i = left, j = right;
		int pivot = (left + right) / 2;
		PagoParcial ppPivot = modeloPagosRecibidos.obtenerPagoParcial(pivot);
		int ppSecuenciaPivot = ppPivot.getSecuencia();
		PagoParcial pptmp = null;
		if (OrdenAscedenteDescendenteFechaVencimiento) {
			while (i <= j) {
				while (((modeloPagosRecibidos.obtenerPagoParcial(i)).getSecuencia()) < ppSecuenciaPivot)
					i++;
				while (((modeloPagosRecibidos.obtenerPagoParcial(j)).getSecuencia()) > ppSecuenciaPivot)
					j--;
				if (i <= j) {
					pptmp = modeloPagosRecibidos.obtenerPagoParcial(i);
					modeloPagosRecibidos.ActualizarModeloPagosRecibidos(modeloPagosRecibidos.obtenerPagoParcial(j), i);
					modeloPagosRecibidos.ActualizarModeloPagosRecibidos(pptmp, j);
					i++;
					j--;
				}
			}
		} else {
			while (i <= j) {
				while (((modeloPagosRecibidos.obtenerPagoParcial(i)).getSecuencia()) > ppSecuenciaPivot)
					i++;
				while (((modeloPagosRecibidos.obtenerPagoParcial(j)).getSecuencia()) < ppSecuenciaPivot)
					j--;
				if (i <= j) {
					pptmp = modeloPagosRecibidos.obtenerPagoParcial(i);
					modeloPagosRecibidos.ActualizarModeloPagosRecibidos(modeloPagosRecibidos.obtenerPagoParcial(j), i);
					modeloPagosRecibidos.ActualizarModeloPagosRecibidos(pptmp, j);
					i++;
					j--;
				}
			}
		}
		return i;
	}

	private void tblPagoParcMouseClicked(MouseEvent evt) {
		final int fila = tblPagoParc.rowAtPoint(evt.getPoint());
		final int columna = tblPagoParc.columnAtPoint(evt.getPoint());
		if (fila > -1) {
			final PagoParcial pp = modeloPagosRecibidos.obtenerPagoParcial(fila);
			switch (columna) {
			case 1:
				// INSERTA LOS HIJOS
				boolean estaDesplegado = pp.isDesplegado();
				if (pp != null && !pp.isHijo()) {
					if (!estaDesplegado) {
						SqlPagoParcialImpl sqlPagoParcial = new SqlPagoParcialImpl();
						Long idPagoParcial = pp.getIdPagoParcial();
						List<PagoParcial> hijosSap = sqlPagoParcial.obtenerListaDetallePagoParcial(idPagoParcial);// Hijos
						List<PagoParcial> hijosLocal = sqlPagoParcial.obtenerListaPagosParcialLocal(pp);
						if (hijosSap != null && !hijosSap.isEmpty()) {
							modeloPagosRecibidos.agregarHijos(hijosSap, fila);
							tblPagoParc.updateUI();
						}
						if (hijosLocal != null && !hijosLocal.isEmpty()) {
							modeloPagosRecibidos.agregarHijos(hijosLocal, fila + hijosSap.size());
						}
						tblPagoParc.updateUI();
					} else {
						modeloPagosRecibidos.eliminarHijos(pp.getIdPagoParcial());
						tblPagoParc.updateUI();
					}
					pp.setDesplegado(!estaDesplegado);
				}
				break;
			case 10:// COMENTARIO
				BeanDato usuario = Promesa.datose.get(0);
				if (usuario.getStrModo().equals("1")) { // ONLINE
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						public void run() {
							if (pp != null && !pp.isHijo()) {
								DialogoComentario dlg = new DialogoComentario(Promesa.getInstance(), pp, true);
								bloqueador.dispose();
								dlg.setVisible(true);
							}
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
					break;
				} else {
					Mensaje.mostrarAviso("Ver Comentario Requiere Conexión Online");
				}
			}
		}
	}

	private void llenarTablaPartidasAbiertasDeSQLite(final List<PagoParcial> pagosParciales) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				int secuencia = 0;
				for (PagoParcial pp : pagosParciales) {
					pp.setSecuencia(secuencia);// Marcelo Moyano 08/04/2013 - 16:04
					modeloPagosRecibidos.agregarPagoParcial(pp);
					modeloPagosRecibidos.setValueAt(secuencia + 1, secuencia, 0);
					secuencia++;
				}
			}
		});
	}
	
	private void llenarTablaPartidasAbiertas() {
		SqlPartidaIndividualAbiertaImpl sql = new SqlPartidaIndividualAbiertaImpl();
		String codClient = Util.completarCeros(10, codigoCliente);
		List<PartidaIndividualAbierta> listaPia = sql .obtenerListaPartidaIndividualAbierta(codClient);
		int secuencia = 0;
		for (PartidaIndividualAbierta pia : listaPia) {
			PagoParcial pp = new PagoParcial();
			pp.setSecuencia(secuencia);// Marcelo Moyano 08/04/2013 - 15:58
			pp.setIdCabecera(0);
			pp.setIdPadre(0);
			pp.setNumeroDocumento(pia.getDocNo());
			pp.setPstngDate(pia.getPstngDate());
			pp.setDocDate(pia.getDocDate());
			pp.setEntryDate(pia.getEntryDate());
			pp.setVencimiento(pia.getExpirationDate());
			pp.setCurrency(pia.getCurrency());
			Double importePagoTotal = 0d;
			try {
				importePagoTotal = Double.parseDouble("" + pia.getAmtDoccur());
			} catch (Exception e) {
				importePagoTotal = 0d;
			}
			pp.setImportePagoTotal(importePagoTotal);
			pp.setRefOrgUn(pia.getRefOrgUn());
			pp.setReferencia(pia.getRefDocNo());
			pp.setClaseDocumento(pia.getDocType());
			pp.setPosicion(pia.getItemNum());
			pp.setPostKey(pia.getPostKey());
			Double importePagoParcial = 0d;
			try {
				importePagoParcial = Double.parseDouble("" + pia.getPsprt());
			} catch (Exception e) {
				importePagoParcial = 0d;
			}
			pp.setImportePagoParcial(importePagoParcial);
			Double importePago = 0d;
			try {
				importePago = Double.parseDouble("" + pia.getPszah());
			} catch (Exception e) {
				importePagoParcial = 0d;
			}
			pp.setImportePago(importePago);
			pp.setPsskt(pia.getPsskt());
			pp.setInvRef(pia.getInvRef());
			pp.setInvItem(pia.getInvItem());
			pp.setIsLeaf(pia.getIsLeaf());
			pp.setIsExpanded(pia.getIsExpanded());
			pp.setIsReadOnly(pia.getIsReadOnly());
			pp.setIndice(pia.getIndice());
			pp.setDisplayColor(pia.getDisplayColor());
			pp.setFiscYear(pia.getFiscYear());
			pp.setFisPeriod(pia.getFisPeriod());
			pp.setSgtxt(pia.getSgtxt());
			pp.setIsReadOnly2(pia.getIsReadOnly2());
			pp.setDbCrInd(pia.getDbCrInd());
			pp.setVerzn(pia.getVerzn());
			pp.setIdPagoParcial(pia.getIdPartidaIndividualAbierta());
			pp.setComentario("");
			pp.setActivo("0");
			modeloPagosRecibidos.agregarPagoParcial(pp);
			modeloPagosRecibidos.setValueAt(secuencia + 1, secuencia, 0);
			secuencia++;// Marcelo Moyano 03/04/2013 - 16:00
		}
	}

	private void setAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblPagoParc.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblPagoParc.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 0:
				anchoColumna = 30;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 1:
				anchoColumna = 40;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 4:
				anchoColumna = 60;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 5:
				anchoColumna = 80;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 6:
				anchoColumna = 110;// 90
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 7:
				anchoColumna = 60;// Nuevo importe en pos
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 8:
				anchoColumna = 60;// Nuevo Importe Pago en parte
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			case 9:
			case 10:
				anchoColumna = 80;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}

	private void initComponents() {
		pnlSuperior = new JPanel();
		lblTitulo = new JLabel();
		mnuBarSuperior = new JToolBar();
		btnGrabar = new JButton();
		btnAgenda = new JButton();
		jSeparator2 = new JToolBar.Separator();
		btnCancelar = new JButton();
		jSeparator1 = new JToolBar.Separator();
		btnImprimirComprobante = new JButton();
		pnlCentral = new JPanel();
		pnlPagosRecibidos = new JPanel();
		pnlPagosRecibidosToolBar = new JPanel();
		lblPagosRecibidos = new JLabel();
		mnuBarCentral = new JToolBar();
		btnAniadir = new JButton();
		btnEliminar = new JButton();
		pnlTotal = new JPanel();
		lblTotal = new JLabel();
		txtTotal = new JTextField();
		scrPagosRecibidos = new JScrollPane();
		pnlGridPagosRecibidos = new JPanel();
		panelColumnasPagosRecibidos1 = new PanelColumnasPagosRecibidos();
		pnlPagoParc = new JPanel();
		tbpPagoParcial = new JTabbedPane();
		pnlPagoParcialInterno = new JPanel();
		mnuToolBarPagoParcial = new JToolBar();
		btnSeleccionarTodo = new JButton();
		btnDeseleccionar = new JButton();
		separador = new JToolBar.Separator();
		btnActivar = new JButton();
		btnDesactivar = new JButton();
		scrPagoParc = new JScrollPane();
		tblPagoParc = new JTable();
		pnlInferior = new JPanel();
		lblEstatusTratamiento = new JLabel();
		pnlInferiorDatos = new JPanel();
		lblImpEntrado = new JLabel();
		txtImpEntrado = new JTextField();
		lblAsig = new JLabel();
		txtAsig = new JTextField();
		lblSinAsignar = new JLabel();
		txtSinAsignar = new JTextField();
		lblPartidasSel = new JLabel();
		txtPartidasSel = new JTextField();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Registro de pago de cliente:");

		pnlSuperior.setLayout(new BorderLayout());

		lblTitulo.setBackground(new Color(175, 200, 222));
		lblTitulo.setFont(new Font("Tahoma", 0, 18));
		lblTitulo.setText("Registro de pago de cliente:");
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
		lblTitulo.setOpaque(true);
		lblTitulo.setVisible(false);
		pnlSuperior.add(lblTitulo, BorderLayout.PAGE_START);

		mnuBarSuperior.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		mnuBarSuperior.setFloatable(false);
		mnuBarSuperior.setRollover(true);

		String guardarPng = "/imagenes/guardar_32.png";
		URL ulrGuardar = getClass().getResource(guardarPng);
		btnGrabar.setIcon(new ImageIcon(ulrGuardar)); // NOI18N
		btnGrabar.setText("Grabar");
		btnGrabar.setFocusable(false);
		btnGrabar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGrabarActionPerformed(evt);
			}
		});
		mnuBarSuperior.add(btnGrabar);
		mnuBarSuperior.add(jSeparator2);

		String eliminarGif = "/imagenes/eliminar_24.gif";
		URL urlEliminarGif = getClass().getResource(eliminarGif);
		btnCancelar.setIcon(new ImageIcon(urlEliminarGif)); // NOI18N
		btnCancelar.setText("Cancelar");
		btnCancelar.setFocusable(false);
		btnCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCancelarActionPerformed(evt);
			}
		});
		mnuBarSuperior.add(btnCancelar);
		mnuBarSuperior.add(jSeparator1);

		String imprimirPng = "/imagenes/icon-imprimir.png";
		URL urlImprimir = getClass().getResource(imprimirPng);
		btnImprimirComprobante.setIcon(new ImageIcon(urlImprimir)); // NOI18N
		btnImprimirComprobante.setText("Imprimir comprobante");
		btnImprimirComprobante.setFocusable(false);
		btnImprimirComprobante.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnImprimirComprobante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnImprimirComprobanteActionPerformed(evt);
			}
		});
		mnuBarSuperior.add(btnImprimirComprobante);
		JToolBar.Separator separatorAgenda = new JToolBar.Separator();
		mnuBarSuperior.add(separatorAgenda);

		//TODO
		String AgendaPng = "/imagenes/iplanificaciones.gif";
		URL urlAgenda = this.getClass().getResource(AgendaPng);
		ImageIcon agenda = new ImageIcon(urlAgenda);
		btnAgenda.setIcon(agenda);
		btnAgenda.setText("Proximas Visitas");
		btnAgenda.setFocusable(false);
		btnAgenda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnAgenda.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	btmMostrarProximasVisitasActionPerformed(e);
		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Apéndice de método generado automáticamente
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Apéndice de método generado automáticamente
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Apéndice de método generado automáticamente
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Apéndice de método generado automáticamente
				
			}
		});
		mnuBarSuperior.add(btnAgenda);
		

		pnlSuperior.add(mnuBarSuperior, BorderLayout.CENTER);

		getContentPane().add(pnlSuperior, BorderLayout.PAGE_START);

		pnlCentral.setLayout(new BorderLayout());

		pnlPagosRecibidos.setLayout(new BorderLayout());

		pnlPagosRecibidosToolBar.setLayout(new GridLayout(2, 1));

		lblPagosRecibidos.setBackground(new Color(175, 200, 222));
		lblPagosRecibidos.setFont(new Font("Tahoma", 1, 11));
		lblPagosRecibidos.setText("Pagos recibidos");
		lblPagosRecibidos.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
		lblPagosRecibidos.setMaximumSize(new Dimension(94, 14));
		lblPagosRecibidos.setMinimumSize(new Dimension(94, 14));
		lblPagosRecibidos.setOpaque(true);
		lblPagosRecibidos.setPreferredSize(new Dimension(94, 14));
		pnlPagosRecibidosToolBar.add(lblPagosRecibidos);

		mnuBarCentral.setBackground(new Color(175, 200, 222));
		mnuBarCentral.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		mnuBarCentral.setFloatable(false);
		mnuBarCentral.setRollover(true);

		String AniadirPng = "/imagenes/aniadir.png";
		URL urlAniadir = getClass().getResource(AniadirPng);
		btnAniadir.setIcon(new ImageIcon(urlAniadir)); // NOI18N
		btnAniadir.setFocusable(false);
		btnAniadir.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAniadir.setOpaque(false);
		btnAniadir.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAniadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAniadirActionPerformed(evt);
			}
		});
		mnuBarCentral.add(btnAniadir);

		String elimiarPng = "/imagenes/eliminar.png";
		URL urlEliminarPng = getClass().getResource(elimiarPng);
		btnEliminar.setIcon(new ImageIcon(urlEliminarPng)); // NOI18N
		btnEliminar.setFocusable(false);
		btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEliminar.setOpaque(false);
		btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnEliminarActionPerformed(evt);
			}
		});
		mnuBarCentral.add(btnEliminar);

		pnlPagosRecibidosToolBar.add(mnuBarCentral);

		pnlPagosRecibidos.add(pnlPagosRecibidosToolBar, BorderLayout.PAGE_START);

		pnlTotal.setLayout(new FlowLayout(FlowLayout.LEFT));

		lblTotal.setFont(new Font("Tahoma", 1, 11));
		lblTotal.setText("Total:");
		pnlTotal.add(lblTotal);

		txtTotal.setMinimumSize(new Dimension(120, 20));
		txtTotal.setPreferredSize(new Dimension(120, 20));
		pnlTotal.add(txtTotal);

		pnlPagosRecibidos.add(pnlTotal, BorderLayout.PAGE_END);

		scrPagosRecibidos.setMinimumSize(new Dimension(23, 80));
		scrPagosRecibidos.setPreferredSize(new Dimension(2, 100));

		pnlGridPagosRecibidos.setLayout(new BoxLayout(pnlGridPagosRecibidos, BoxLayout.Y_AXIS));
		pnlGridPagosRecibidos.add(panelColumnasPagosRecibidos1);

		scrPagosRecibidos.setViewportView(pnlGridPagosRecibidos);

		pnlPagosRecibidos.add(scrPagosRecibidos, BorderLayout.CENTER);

		pnlCentral.add(pnlPagosRecibidos, BorderLayout.PAGE_START);

		pnlPagoParc.setLayout(new BorderLayout());

		pnlPagoParcialInterno.setLayout(new BorderLayout());

		mnuToolBarPagoParcial.setBackground(new Color(175, 200, 222));
		mnuToolBarPagoParcial.setFloatable(false);
		mnuToolBarPagoParcial.setRollover(true);

		String seleccion = "/imagenes/seleccionar_todos.png";
		URL urlSeleccion = getClass().getResource(seleccion);
		btnSeleccionarTodo.setIcon(new ImageIcon(urlSeleccion)); // NOI18N
		btnSeleccionarTodo.setFocusable(false);
		btnSeleccionarTodo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSeleccionarTodo.setOpaque(false);
		btnSeleccionarTodo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSeleccionarTodo.setToolTipText("Seleccionar todo.");
		btnSeleccionarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSeleccionarTodoActionPerformed(evt);
			}
		});
		mnuToolBarPagoParcial.add(btnSeleccionarTodo);

		String deSelecion = "/imagenes/deseleccionar.png";
		URL urlDeSelecion = getClass().getResource(deSelecion);
		btnDeseleccionar.setIcon(new ImageIcon(urlDeSelecion)); // NOI18N
		btnDeseleccionar.setFocusable(false);
		btnDeseleccionar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDeseleccionar.setOpaque(false);
		btnDeseleccionar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDeseleccionar.setToolTipText("Deseleccionar todo.");
		btnDeseleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDeseleccionarActionPerformed(evt);
			}
		});
		mnuToolBarPagoParcial.add(btnDeseleccionar);
		mnuToolBarPagoParcial.add(separador);

		String activarPng = "/imagenes/actiivar.png";
		URL urlActivar = getClass().getResource(activarPng);
		btnActivar.setIcon(new ImageIcon(urlActivar)); // NOI18N
		btnActivar.setFocusable(false);
		btnActivar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnActivar.setOpaque(false);
		btnActivar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnActivar.setToolTipText("Activar.");
		btnActivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnActivarActionPerformed(evt);
			}
		});
		mnuToolBarPagoParcial.add(btnActivar);

		String desActivar = "/imagenes/desactivar.png";
		URL urlDesactivar = getClass().getResource(desActivar);
		btnDesactivar.setIcon(new ImageIcon(urlDesactivar)); // NOI18N
		btnDesactivar.setFocusable(false);
		btnDesactivar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDesactivar.setOpaque(false);
		btnDesactivar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDesactivar.setToolTipText("Desactivar.");
		btnDesactivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDesactivarActionPerformed(evt);
			}
		});
		mnuToolBarPagoParcial.add(btnDesactivar);

		pnlPagoParcialInterno.add(mnuToolBarPagoParcial,BorderLayout.PAGE_START);

		tblPagoParc.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		scrPagoParc.setViewportView(tblPagoParc);

		pnlPagoParcialInterno.add(scrPagoParc, BorderLayout.CENTER);

		tbpPagoParcial.addTab("Pago Parc.", pnlPagoParcialInterno);

		pnlPagoParc.add(tbpPagoParcial, BorderLayout.CENTER);

		pnlCentral.add(pnlPagoParc, BorderLayout.CENTER);

		getContentPane().add(pnlCentral, BorderLayout.CENTER);

		pnlInferior.setLayout(new BorderLayout());

		lblEstatusTratamiento.setBackground(new Color(175, 200, 222));
		lblEstatusTratamiento.setFont(new Font("Tahoma", 1, 11));
		lblEstatusTratamiento.setText("Estatus de tratamiento");
		lblEstatusTratamiento.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
		lblEstatusTratamiento.setOpaque(true);
		pnlInferior.add(lblEstatusTratamiento, BorderLayout.PAGE_START);

		pnlInferiorDatos.setBackground(new Color(255, 255, 255));
		pnlInferiorDatos.setLayout(new FlowLayout(FlowLayout.LEFT));

		lblImpEntrado.setText("Imp. Entrado:");
		pnlInferiorDatos.add(lblImpEntrado);

		txtImpEntrado.setBackground(new Color(175, 200, 222));
		txtImpEntrado.setHorizontalAlignment(JTextField.LEFT);
		txtImpEntrado.setMinimumSize(new Dimension(100, 20));
		txtImpEntrado.setPreferredSize(new Dimension(100, 20));
		pnlInferiorDatos.add(txtImpEntrado);

		lblAsig.setText("Asig:");
		pnlInferiorDatos.add(lblAsig);

		txtAsig.setBackground(new Color(175, 200, 222));
		txtAsig.setMinimumSize(new Dimension(100, 20));
		txtAsig.setPreferredSize(new Dimension(100, 20));
		pnlInferiorDatos.add(txtAsig);

		lblSinAsignar.setText("Sin asignar:");
		pnlInferiorDatos.add(lblSinAsignar);

		txtSinAsignar.setBackground(new Color(175, 200, 222));
		txtSinAsignar.setMinimumSize(new Dimension(100, 20));
		txtSinAsignar.setPreferredSize(new Dimension(100, 20));
		pnlInferiorDatos.add(txtSinAsignar);

		lblPartidasSel.setText("Partidas Sel:");
		pnlInferiorDatos.add(lblPartidasSel);

		txtPartidasSel.setBackground(new Color(175, 200, 222));
		txtPartidasSel.setMinimumSize(new Dimension(100, 20));
		txtPartidasSel.setPreferredSize(new Dimension(100, 20));
		pnlInferiorDatos.add(txtPartidasSel);

		pnlInferior.add(pnlInferiorDatos, BorderLayout.CENTER);

		getContentPane().add(pnlInferior, BorderLayout.PAGE_END);

		pack();
	}// </editor-fold>

	private void btnGrabarActionPerformed(ActionEvent evt) {
		String message = verificarVisitas("",codigoCliente);
		if(message.equals("")){
			
			IVentanaVisita tp = new IVentanaVisita(codigoCliente);
			tp.setVisible(true);
			while(tp.getisInsert()){
				System.out.println(tp.getisInsert());
			}
		}
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					BeanDato usuario = Promesa.datose.get(0);
					if (usuario.getStrModo().equals("1")) {
						if (fuente == Constante.FROM_DB) {
							actualizarRegistroPagoClienteOffline();
						} else {
							grabarRegistroPagoClienteOnline();
						}
						banderaGrabaOnLine = true;
					} else {
						if (fuente == Constante.FROM_DB) {
							actualizarRegistroPagoClienteOffline();
						} else {
							grabarRegistroPagoClienteOffline();
						}
						banderaGrabaOnLine = false;
					}
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
	public String verificarVisitas(String strMensaje, String codigoCliente){
		BeanDato usuario = Promesa.datose.get(0);
		BeanAgenda ba = new BeanAgenda();
		Util util = new Util();
		ba.setVENDOR_ID(usuario.getStrCodigo());
		ba.setCUST_ID(codigoCliente);
		SqlAgenda getAgenda = new SqlAgendaImpl();
		getAgenda.setListaAgenda(ba);
		List<BeanAgenda> listaAgenda =getAgenda.getListaAgenda();
		if(listaAgenda.size()>1){
			strMensaje="<html>Las proximas visitas son:";
			List<Date> fecha = new ArrayList<Date>();
			for(int i=0;i<listaAgenda.size();i++){
				Date date = util.convierteStringaDate(listaAgenda.get(i).getBEGDA());
				if(date.after(util.convierteStringaDate(this.ba.getBEGDA()))){
				String[] hora = listaAgenda.get(i).getTIME().trim().split(":");
				Calendar cal = Calendar.getInstance(); // creates calendar
			    cal.setTime(date); // sets calendar time/date
			    cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hora[0])); // adds n hour
			    cal.add(Calendar.MINUTE, Integer.parseInt(hora[1])); // adds n minutes
				date = cal.getTime(); // returns new date object, one hour in the future
				fecha.add(date);
				}
			}
			Collections.sort(fecha);  
			for(int i=0;i<fecha.size();i++){
				if(fecha.get(i).after(util.convierteStringaDate(this.ba.getBEGDA()))){
					strMensaje += "<br>"+Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(fecha.get(i));
				}
			}
			strMensaje += "</html>";
			if(fecha.isEmpty()){
				strMensaje="";
			}
		}
		return strMensaje;
	}

	protected void btmMostrarProximasVisitasActionPerformed(MouseEvent evt) {
		try {
			String visitas = verificarVisitas("",codigoCliente);
			if(visitas.equals("")){
				visitas = "El cliente no tiene proximas visitas";
			}
			JOptionPane optionpane = new JOptionPane(visitas,JOptionPane.PLAIN_MESSAGE,
					JOptionPane.DEFAULT_OPTION,null,null,null);//.showMessageDialog(this,visitas);
			
			JDialog dialog = optionpane.createDialog("Proximas Visitas");
			Point point = new Point(evt.getComponent().getLocationOnScreen().x+evt.getX(),evt.getComponent().getLocationOnScreen().y+evt.getY());
			dialog.setLocation(point);
			dialog.setVisible(true);
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			Util.mostrarExcepcion(e);
		}
	}
	private void grabarRegistroPagoClienteOnline() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					// Obtenemos los pagos recibidos
					List<PagoRecibido> pagosRecibidos = new ArrayList<PagoRecibido>();
					Component[] componentes = pnlGridPagosRecibidos.getComponents();
					for (int i = 0; i < componentes.length; i++) {
						Component component = componentes[i];
						if (component instanceof PanelElementosPagosRecibidos) {
							PanelElementosPagosRecibidos registro = (PanelElementosPagosRecibidos) component;
							pagosRecibidos.add(registro.obtenerPagoRecibido());
						}
					}
					// Obtenemos los pagos parciales
					List<PagoParcial> pagosParciales = new ArrayList<PagoParcial>();
					for (PagoParcial pp : modeloPagosRecibidos.obtenerListaPagoParcial()) {
						if (!pp.isHijo()&& pp.getImportePago() > 0d && pp.isActivo()) {
							pagosParciales.add(pp);
						}
					}
					if (validaIngresoDatosPagosRecibidos(pagosRecibidos, pagosParciales)) {
						Promesa promesa = Promesa.getInstance();
						double sinAsignar = Double.parseDouble(txtSinAsignar.getText());
						if (sinAsignar >= 1d) {
							String str = "¿Desea grabar el pago, se realizará un anticipo automático por ";
							String msg = str+ txtSinAsignar.getText()+" ... confirmar?";
							
							int tipo = JOptionPane.showConfirmDialog(promesa, msg, "Confirmación", JOptionPane.YES_NO_OPTION);
							if (tipo == JOptionPane.OK_OPTION) {
								llenarClaseImpresionONLine(pagosRecibidos, pagosParciales);
							}
						} else if (sinAsignar > 0d && sinAsignar < 1d) {
							llenarClaseImpresionONLine(pagosRecibidos, pagosParciales);
						} else if (sinAsignar == 0d) {
							llenarClaseImpresionONLine(pagosRecibidos, pagosParciales);
						} else if (sinAsignar > -1d && sinAsignar < 0d) {
							llenarClaseImpresionONLine(pagosRecibidos, pagosParciales);
						} else if (sinAsignar <= -1d) {
							String msg = "El Importe Sin Asignar debe ser mayor a -1.0";
							JOptionPane.showMessageDialog(promesa, msg, "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
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
	
	
	public void llenarClaseImpresionONLine(List<PagoRecibido> pagosRecibidos, List<PagoParcial> pagosParciales) {
		if (Constante.IMPRESORA_NEW.equals(strDispositivoImpresora)) {
			try {
				grabarRegistroPagoClienteOnDPP350(pagosRecibidos, pagosParciales);
			}catch (Exception ex){
				grabarRegistroPagoClienteOn(pagosRecibidos, pagosParciales);
			}
		} else {
			grabarRegistroPagoClienteOn(pagosRecibidos, pagosParciales);
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void grabarRegistroPagoClienteOnDPP350(List<PagoRecibido> pagosRecibidos, List<PagoParcial> pagosParciales) {
		SCobranzas sCobranzas = new SCobranzas();
		Promesa promesa = Promesa.getInstance();
		try {
			Object arreglo[] = sCobranzas.grabarRegistroPagoCliente(pagosRecibidos, pagosParciales);
			String mensajeHTML = "<html><table>";
			if (arreglo != null) {
				List<String> detalles = (List<String>) arreglo[0];
				if (detalles.size() > 0) {
					// Aquí debo de llenar la lista listaTicketPago
					HashMap<String, TicketPagoDPP350> hmTicketPago = new HashMap<String, TicketPagoDPP350>();
					for (String dp : detalles) {
						String[] valores = String.valueOf(dp).split("[¬]");
						String numeroTicket = valores[7].trim();
						TicketPagoDPP350 ticketPago = hmTicketPago.get(numeroTicket);
						DetalleTicketPago detalleTicketPago = new DetalleTicketPago();
						detalleTicketPago.setNroFactura(valores[5].trim());
						String fVencimiento = Util.convierteFecha(valores[12].trim());
						detalleTicketPago.setFechaVencimiento(fVencimiento);
						double valor = Double.parseDouble(valores[9].trim());
						detalleTicketPago.setValor(valor);
						double abono = Double.parseDouble(valores[10].trim());
						detalleTicketPago.setAbono(abono);
						detalleTicketPago.setIva("0.00");
//						detalleTicketPago.setIva(valores[17].trim());
						if (ticketPago == null) {
							TicketPagoDPP350 ticketPagoTemporal = new TicketPagoDPP350();
							String codigoVendedor = "" + Promesa.datose.get(0).getStrCodigo();
							String stringSecuencia = Util .obtenerSecuenciaPorVendedor(codigoVendedor);
							if (stringSecuencia.equals("0")) {
								stringSecuencia = "1";
								Util.insertarSecuenciaPorVendedor(codigoVendedor, stringSecuencia);
							} else {
								Long secuencia = Long.parseLong(stringSecuencia);
								stringSecuencia = String.valueOf(secuencia);
								Util.actualizarSecuencia(codigoVendedor,stringSecuencia);
							}
							String secuenciaTicket = Util.completarCeros(10,stringSecuencia);
							ticketPagoTemporal.setNumeroTicket(secuenciaTicket+ "-" + valores[7].trim());
							// Marcelo Moyano 27/03/2013
							String cliente = codigoCliente + " - "+ nombreCompletoCliente;
							ticketPagoTemporal.setCliente(cliente);
							String vendedor = codigoVendedor + " "+ Promesa.datose.get(0).getStrUsuario();
							ticketPagoTemporal.setVendedor(vendedor);
							String referencia = !valores[13].trim().equals("|") ? valores[13].trim() : "";
							ticketPagoTemporal.setReferencia(referencia);
							ticketPagoTemporal.setStrIva(detalleTicketPago.getIva());
							String formaPago = valores[2].trim();
							if (!("|").equals(valores[14].trim()) || ("VT").equals(formaPago)) {
								ticketPagoTemporal.setCuentaBanco("");
								SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
								List<BancoCliente> BcoClientes = sqlBancoClienteImpl.obtenerListaBancoCliente(codigoCliente);
								for (BancoCliente bcoCli : BcoClientes) {
									String nroCta = bcoCli.getNroCtaBcoCliente().trim();
									if (nroCta.equals(valores[14].trim())) {
										String ctaBco = nroCta + " - " + bcoCli.getDescripcionBancoCliente().trim();
										ticketPagoTemporal.setCuentaBanco(ctaBco);
										break;
									}
								}
							} else {
								ticketPagoTemporal.setCuentaBanco("");
							}
							String valor16 = valores[16].trim();
							String fecha1 = Util.convierteFechaADDMMMYYYY(valor16);
							String fecha2 = Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(new Date());
							String comparacion = "Mon Jan 01 00:00:00 COT 1900";
							String fDocu = !valor16.equals(comparacion) ? fecha1 : fecha2;
							ticketPagoTemporal.setFechaDocumento(fDocu);
							
							String valor15 = valores[15].trim();
							String fechaVenc = Util.convierteFechaADDMMMYYYY(valor15);
							String fVenc = !valor15.equals(comparacion) ? fechaVenc: "";
							ticketPagoTemporal.setFechaVencimiento(fVenc);
							
							
							if (("VC").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Cheque Postfechado");
							} else if (("VP").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Pagaré");
							} else if (("VT").equals(formaPago) || ("VI").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Retención");
							} else if (("VX").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Papeleta de Depósito");
							} else if (("VY").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Cheque a la vista");
							} else if (("VZ").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Efectivo");
							} else if (("VO").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Recaudo");
							}
							hmTicketPago.put(numeroTicket, ticketPagoTemporal);
							ticketPagoTemporal.agregarDetalleALaLista(detalleTicketPago);
						} else {
							ticketPago.agregarDetalleALaLista(detalleTicketPago);
						}
					}
					for (Entry<String, TicketPagoDPP350> elemento : hmTicketPago.entrySet()) {
						listaTicketPagoDPP350.add(elemento.getValue());
					}
					List<String> lstMensaje = (List<String>) arreglo[1];
					URL check = this.getClass().getResource("/imagenes/check.png");
					URL error = this.getClass().getResource("/imagenes/error.png");
					for (String mensaje : lstMensaje) {
						String[] valores = String.valueOf(mensaje).split("[¬]");
						if (valores[1].trim().equals("S")) {
							mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valores[3].trim()+ "</td></tr>";
						} else {
							mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valores[3].trim()+ "</td></tr>";
						}
					}
					mensajeHTML += "</table></html>";
					enableComponents(pnlCentral, false);
					btnGrabar.setEnabled(false);
					btnImprimirComprobante.setEnabled(true);
					JOptionPane.showMessageDialog(promesa,mensajeHTML, "Resultados",JOptionPane.INFORMATION_MESSAGE);
				} else {
					List<String> lstMensaje = (List<String>) arreglo[1];
					if (lstMensaje.size() > 0) {
						URL check = this.getClass().getResource("/imagenes/check.png");
						URL error = this.getClass().getResource("/imagenes/error.png");
						for (String mensaje : lstMensaje) {
							String[] valores = String.valueOf(mensaje).split("[¬]");
							if (valores[1].trim().equals("S")) {
								mensajeHTML += "<tr><td><img src=\"" + check+ "\"></td><td>" + valores[3].trim()+ "</td></tr>";
							} else {
								mensajeHTML += "<tr><td><img src=\"" + error+ "\"></td><td>" + valores[3].trim()+ "</td></tr>";
							}
						}
						mensajeHTML += "</table></html>";
						JOptionPane.showMessageDialog(promesa, mensajeHTML, "Resultados",JOptionPane.INFORMATION_MESSAGE);
					} else {
						String msg = "No se realizó correctamente la conexión a SAP.";
						JOptionPane.showMessageDialog(promesa, msg,"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(promesa, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	private void grabarRegistroPagoClienteOn( List<PagoRecibido> pagosRecibidos, List<PagoParcial> pagosParciales) {
		SCobranzas sCobranzas = new SCobranzas();
		Promesa promesa = Promesa.getInstance();
		try {
			Object arreglo[] = sCobranzas.grabarRegistroPagoCliente(pagosRecibidos, pagosParciales);
			String mensajeHTML = "<html><table>";
			if (arreglo != null) {
				List<String> detallesPagos = (List<String>) arreglo[0];
				if (detallesPagos.size() > 0) {
					// Aquí debo de llenar la lista listaTicketPago
					HashMap<String, TicketPago> hmTicketPago = new HashMap<String, TicketPago>();
					String usuario = Promesa.datose.get(0).getStrUsuario();
					for (String detalleDePago : detallesPagos) {
						String[] valores = String.valueOf(detalleDePago).split("[¬]");
						String numeroTicket = valores[7].trim();
						TicketPago ticketPago = hmTicketPago.get(numeroTicket);
						DetalleTicketPago detalleTicketPago = new DetalleTicketPago();
						detalleTicketPago.setNroFactura(valores[5].trim());
						detalleTicketPago.setFechaVencimiento(Util.convierteFecha(valores[12].trim()));
						detalleTicketPago.setValor(Double.parseDouble(valores[9].trim()));
						detalleTicketPago.setAbono(Double.parseDouble(valores[10].trim()));
//						detalleTicketPago.setIva(valores[17].trim());
						if (ticketPago == null) {
							TicketPago ticketPagoTemporal = new TicketPago();
							String codigoVendedor = "" + Promesa.datose.get(0).getStrCodigo();
							String stringSecuencia = Util.obtenerSecuenciaPorVendedor(codigoVendedor);
							if (stringSecuencia.equals("0")) {
								stringSecuencia = "1";
								Util.insertarSecuenciaPorVendedor(codigoVendedor, stringSecuencia);
							} else {
								Long secuencia = Long.parseLong(stringSecuencia);
								stringSecuencia = String.valueOf(secuencia);
								Util.actualizarSecuencia(codigoVendedor, stringSecuencia);
							}
							String secuenciaTicket = Util.completarCeros(10,stringSecuencia);
							String nroTicket = secuenciaTicket + "-" + valores[7].trim();
							ticketPagoTemporal.setNumeroTicket(nroTicket);
							// Marcelo Moyano 27/03/2013
							String cliente = codigoCliente + " - " + nombreCompletoCliente;
							ticketPagoTemporal.setCliente(cliente);
							ticketPagoTemporal.setVendedor(codigoVendedor + " " + usuario);
							String referencia = !valores[13] .trim().equals("|") ? valores[13].trim() : "";
							ticketPagoTemporal.setReferencia(referencia);
							String formaPago = valores[2].trim();
							if (!("|").equals(valores[14].trim()) || ("VT").equals(formaPago)) {
								ticketPagoTemporal.setCuentaBanco("");
								SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
								List<BancoCliente> bcoClientes = sqlBancoClienteImpl.obtenerListaBancoCliente(codigoCliente);
								for (BancoCliente bcoCli : bcoClientes) {
									String nroCta = bcoCli.getNroCtaBcoCliente().trim();
									if (nroCta.equals(valores[14].trim())) {
										String descriBcoCli = bcoCli.getDescripcionBancoCliente().trim();
										ticketPagoTemporal.setCuentaBanco(nroCta+ " - " + descriBcoCli);
										break;
									}
								}
							} else {
								ticketPagoTemporal.setCuentaBanco("");
							}
							String str = valores[16].trim();
							String fecha = Util.convierteFechaADDMMMYYYY(str);
							String comparacion = "Mon Jan 01 00:00:00 COT 1900";
							String fDoc = !str.equals(comparacion) ? fecha : "";
							ticketPagoTemporal.setFechaDocumento(fDoc);
							
							String str15 = valores[15].trim();
							String fVenc15 = Util.convierteFechaADDMMMYYYY(str15) ;
							String fVenc = !str15.equals(comparacion) ? fVenc15 : "";
							ticketPagoTemporal.setFechaVencimiento(fVenc);
							if (("VC").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Cheque Postfechado");
							} else if (("VP").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Pagaré");
							} else if (("VT").equals(formaPago) || ("VI").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Retención");
							} else if (("VX").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Papeleta de Depósito");
							} else if (("VY").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Cheque a la vista");
							} else if (("VZ").equals(formaPago)) {
								ticketPagoTemporal.setFormaPago("Efectivo");
							} else if (("VO").equals(formaPago)) {// Marcelo  Moyano Recaudo
								ticketPagoTemporal.setFormaPago("Recaudo");// Marcelo Moyano Recaudo
							}
							hmTicketPago.put(numeroTicket, ticketPagoTemporal);
							ticketPagoTemporal.agregarDetalleALaLista(detalleTicketPago);
						} else {
							ticketPago.agregarDetalleALaLista(detalleTicketPago);
						}
					}
					for (Entry<String, TicketPago> elemento : hmTicketPago.entrySet()) {
						listaTicketPago.add(elemento.getValue());
					}
					List<String> lstMensaje = (List<String>) arreglo[1];
					URL check = this.getClass().getResource("/imagenes/check.png");
					URL error = this.getClass().getResource("/imagenes/error.png");
					for (String mensaje : lstMensaje) {
						String[] valores = String.valueOf(mensaje).split("[¬]");
						if (valores[1].trim().equals("S")) {
							mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valores[3].trim()+ "</td></tr>";
						} else {
							mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valores[3].trim() + "</td></tr>";
						}
					}
					mensajeHTML += "</table></html>";
					enableComponents(pnlCentral, false);
					btnGrabar.setEnabled(false);
					btnImprimirComprobante.setEnabled(true);
					JOptionPane.showMessageDialog(promesa, mensajeHTML, "Resultados", JOptionPane.INFORMATION_MESSAGE);
				} else {
					List<String> lstMensaje = (List<String>) arreglo[1];
					if (lstMensaje.size() > 0) {
						URL check = this.getClass().getResource( "/imagenes/check.png");
						URL error = this.getClass().getResource( "/imagenes/error.png");
						for (String mensaje : lstMensaje) {
							String[] valores = String.valueOf(mensaje).split( "[¬]");
							if (valores[1].trim().equals("S")) {
								mensajeHTML += "<tr><td><img src=\"" + check + "\"></td><td>" + valores[3].trim() + "</td></tr>";
								btnGrabar.setEnabled(false);
								btnImprimirComprobante.setEnabled(true);
							} else {
								mensajeHTML += "<tr><td><img src=\"" + error + "\"></td><td>" + valores[3].trim() + "</td></tr>";
							}
						}
						mensajeHTML += "</table></html>";
						JOptionPane.showMessageDialog(promesa, mensajeHTML, "Resultados", JOptionPane.INFORMATION_MESSAGE);
					} else {
						String msg = "No se realizó correctamente la conexión a SAP.";
						JOptionPane.showMessageDialog(promesa,msg,"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(promesa, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarRegistroPagoClienteOffline() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Obtenemos los pagos recibidos
				List<PagoRecibido> pagosRecibidos = new ArrayList<PagoRecibido>();
				Component[] componentes = pnlGridPagosRecibidos.getComponents();
				for (int i = 0; i < componentes.length; i++) {
					Component component = componentes[i];
					if (component instanceof PanelElementosPagosRecibidos) {
						PanelElementosPagosRecibidos registro = (PanelElementosPagosRecibidos) component;
						pagosRecibidos.add(registro.obtenerPagoRecibido());
					}
				}
				for(PagoRecibido pagoRecibido : pagosRecibidos){
					if(pagoRecibido.getFormaPago().equals("VZ")){
						String fechaDoc = pagoRecibido.getFechaDocumento();
						String fDco = Util.convierteCadenaAFormatoYYYYMMDD(fechaDoc);
						pagoRecibido.setFechaDocumento(fDco);
					}
				}
				// Obtenemos los pagos parciales
				List<PagoParcial> pagosParciales = new ArrayList<PagoParcial>();
				List<PagoParcial> pagosParcialesActivos = new ArrayList<PagoParcial>();
				for (PagoParcial pp : modeloPagosRecibidos.obtenerListaPagoParcial()) {
					if (!pp.isHijo() && pp.getImportePago() > 0d && pp.isActivo()) {
						pagosParcialesActivos.add(pp);
					}
					pagosParciales.add(pp);
				}
				if (validaIngresoDatosPagosRecibidos(pagosRecibidos, pagosParcialesActivos)) {
					Promesa promesa = Promesa.getInstance();
					String strSinAsignar = txtSinAsignar.getText();
					double sinAsignar = Double.parseDouble(strSinAsignar);
					String msg = "Se actualizó el pago "+ cabecera.getIdCabecera() + " correctamente";
					int infoMessage = JOptionPane.INFORMATION_MESSAGE;
					if (sinAsignar >= 1d) {
						// Se genera un anticipo
						actualizarRegistroPagoCliente(pagosRecibidos, pagosParciales);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
						JOptionPane.showMessageDialog(promesa, msg, "Exito", infoMessage);
					} else if (sinAsignar > 0d && sinAsignar < 1d) {
						// Se genera un ajuste a favor de Promesa
						actualizarRegistroPagoCliente(pagosRecibidos, pagosParciales);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
						JOptionPane.showMessageDialog(promesa, msg, "Exito",infoMessage);
					} else if (sinAsignar == 0d) {
						// Exacto
						actualizarRegistroPagoCliente(pagosRecibidos, pagosParciales);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
						JOptionPane.showMessageDialog(promesa, msg, "Exito", infoMessage);
					} else if (sinAsignar > -1d && sinAsignar < 0d) {
						// Se genera un ajuste a favor del cliente
						actualizarRegistroPagoCliente(pagosRecibidos, pagosParciales);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
						JOptionPane.showMessageDialog(promesa, msg, "Exito", infoMessage);
					} else if (sinAsignar <= -1d) {
						// No se graban los pagos.
						msg = "El Importe Sin Asignar debe ser mayor a -1.0";
						JOptionPane.showMessageDialog(promesa,msg, "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	private void imprimirReporteOnline() {
		if (listaTicketPago != null) {
			ReporteTicket rt = new ReporteTicket(listaTicketPago);
			rt.generarReporteRegistroPagoOnline();
		}
	}

	private void imprimirReporteOnlineDPP350(String strTitulo) {
		if (listaTicketPagoDPP350 != null) {
			for (TicketPagoDPP350 reporte : listaTicketPagoDPP350) {
				reporte.setStrTitulo(strTitulo);
				reporte.imprimir();
			}
		}
	}

	private void imprimirReporteOffline() {
		// Obtenemos los pagos recibidos
		List<PagoRecibido> pagosRecibidos = new ArrayList<PagoRecibido>();
		Component[] componentes = pnlGridPagosRecibidos.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			Component component = componentes[i];
			if (component instanceof PanelElementosPagosRecibidos) {
				PanelElementosPagosRecibidos registro = (PanelElementosPagosRecibidos) component;
				pagosRecibidos.add(registro.obtenerPagoRecibido());
			}
		}
		Long idCabecera = modeloPagosRecibidos.obtenerPagoParcial(0).getIdCabecera();
		List<PagoParcial> pagosParcialesActivos = new ArrayList<PagoParcial>();
		for (PagoParcial pp : modeloPagosRecibidos.obtenerListaPagoParcial()) {
			if (!pp.isHijo() && pp.getImportePago() > 0d && pp.isActivo()) {
				pagosParcialesActivos.add(pp);
			}
		}
		for(PagoRecibido pg : pagosRecibidos){
			String strDoc = pg.getFechaDocumento();
			String fDco = Util.convierteFechaYYYYMMDDaFormatoReporte(strDoc);
			pg.setFechaDocumento(fDco);
			
			String strVenc = pg.getFechaVencimiento();
			String fVend = Util.convierteFechaYYYYMMDDaFormatoReporte(strVenc);
			pg.setFechaVencimiento(fVend);
		}
		// Marcelo Moyano 28/03/2013
		String secuencia = Util.obtenerSecuenciaPorCabecera(String.valueOf(idCabecera));
		SqlClienteImpl sqlCliente = new SqlClienteImpl();
		BeanCliente cliente = sqlCliente.buscarCliente(codigoCliente);
		TicketPagoOffline ticket = new TicketPagoOffline();
		ticket.setDireccion(Util.obtenerDireccionPromesa());
		ticket.setTelefono(Util.obtenerTelefonoPromesa());
		String codigoVendedor = "" + Promesa.datose.get(0).getStrCodigo();
		String numeroTicket = "" + Util.completarCeros(10, secuencia);
		ticket.setNumeroTicket(numeroTicket);// Marcelo Moyano Secuencia y codigo Vendedor
		String fDoc = Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(new Date());
		ticket.setFechadoc(fDoc);
		String strCliente = codigoCliente + " " + cliente.getStrNombreCliente();
		ticket.setCliente(strCliente);
		String codVend = String.valueOf(Integer.parseInt(codigoVendedor));
		String vendedor = codVend + " " + Promesa.datose.get(0).getStrUsuario();
		ticket.setVendedor(vendedor);
		ticket.setListaPagosRecibidos(pagosRecibidos);
		ticket.setListaPagosParciales(pagosParcialesActivos);
		ReporteTicket rt = new ReporteTicket(ticket);
		rt.generarReporteRegistroPagoOffline();
	}

	private void imprimirReporteOfflineDPP350(String strTitulo) {
		List<PagoRecibido> listaPagosRecibidos = new ArrayList<PagoRecibido>();
		Component[] componentes = pnlGridPagosRecibidos.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			Component component = componentes[i];
			if (component instanceof PanelElementosPagosRecibidos) {
				PanelElementosPagosRecibidos registro = (PanelElementosPagosRecibidos) component;
				listaPagosRecibidos.add(registro.obtenerPagoRecibido());
			}
		}
		Long idCabecera = modeloPagosRecibidos.obtenerPagoParcial(0).getIdCabecera();
		List<PagoParcial> listaPagosParcialesActivos = new ArrayList<PagoParcial>();
		for (PagoParcial pp : modeloPagosRecibidos.obtenerListaPagoParcial()) {
			if (!pp.isHijo() && pp.getImportePago() > 0d && pp.isActivo()) {
				listaPagosParcialesActivos.add(pp);
			}
		}
		
		for(PagoRecibido pg : listaPagosRecibidos){
			String strDco = pg.getFechaDocumento();
			String fDco = Util.convierteFechaYYYYMMDDaFormatoReporte(strDco);
			pg.setFechaDocumento(fDco);
			
			String strVenc = pg.getFechaVencimiento();
			String fVenc = Util.convierteFechaYYYYMMDDaFormatoReporte(strVenc);
			pg.setFechaVencimiento(fVenc);
		}
		
		// Marcelo Moyano 28/03/2013
		String secuencia = Util.obtenerSecuenciaPorCabecera(String.valueOf(idCabecera));
		SqlClienteImpl sqlCliente = new SqlClienteImpl();
		BeanCliente cliente = sqlCliente.buscarCliente(codigoCliente);
		TicketPagoOfflineDpp350 ticket = new TicketPagoOfflineDpp350();
		ticket.setTitulo(strTitulo);
		ticket.setDireccion(Util.obtenerDireccionPromesa());
		ticket.setTelefono(Util.obtenerTelefonoPromesa());
		// Marcelo Moyano secuencia y codigoVendedor en reporte de RPC
		String codigoVendedor = "" + Promesa.datose.get(0).getStrCodigo();
		String numeroTicket = "" + Util.completarCeros(10, secuencia);
		ticket.setNumeroTicket(numeroTicket);// Marcelo Moyano Secuencia y codigo Vendedor
		String strCliente = codigoCliente + " " + cliente.getStrNombreCliente();
		ticket.setCliente(strCliente);
		String codVend = String.valueOf(Integer.parseInt(codigoVendedor));
		String strVendedor = codVend+ " " + Promesa.datose.get(0).getStrUsuario();
		ticket.setVendedor(strVendedor);
		ticket.setListaPagosRecibidos(listaPagosRecibidos);
		ticket.setListaPagosParciales(listaPagosParcialesActivos);
		ticket.imprimir();
	}

	private void grabarRegistroPagoClienteOffline() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				List<PagoRecibido> pagosRecibidos = new ArrayList<PagoRecibido>();
				Component[] componentes = pnlGridPagosRecibidos.getComponents();
				for (int i = 0; i < componentes.length; i++) {
					Component component = componentes[i];
					if (component instanceof PanelElementosPagosRecibidos) {
						PanelElementosPagosRecibidos registro = (PanelElementosPagosRecibidos) component;
						pagosRecibidos.add(registro.obtenerPagoRecibido());
					}
				}
				// Obtenemos los pagos parciales desde modeloPagosRecibidos
				List<PagoParcial> pagosParciales = new ArrayList<PagoParcial>();
				List<PagoParcial> pagosParcialesActivos = new ArrayList<PagoParcial>();
				SqlPartidaIndividualAbiertaImpl sql = new SqlPartidaIndividualAbiertaImpl();
				for (PagoParcial pp : modeloPagosRecibidos.obtenerListaPagoParcial()) {
					if (!pp.isHijo()&& pp.getImportePago() > 0d && pp.isActivo()) {
						try {
							Double importePago = sql.ObtenerImportePagoPartidasIndividualAbierta(pp);
							Double importePagoParcial = pp.getImportePago();
							importePagoParcial = importePagoParcial+ pp.getImportePagoParcial();
							SqlPagoParcialImpl sqlPagoParcial = new SqlPagoParcialImpl();
							sqlPagoParcial.ActualizarImportePagoParcial(pp, importePagoParcial);
							importePago = importePago - pp.getImportePago();
							sql.ActualizarImportePagoPartidasIndividuales( pp, importePago, importePagoParcial);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						pagosParcialesActivos.add(pp);
					}
					pagosParciales.add(pp);
				}
				if (validaIngresoDatosPagosRecibidos(pagosRecibidos, pagosParcialesActivos)) {
					Promesa instancia = Promesa.getInstance();
					String strSinAsignar = txtSinAsignar.getText();
					double sinAsignar = Double.parseDouble(strSinAsignar);
					if (sinAsignar >= 1d) {
						Long idSecuencia = grabarSecuencialTicketRPC();
						grabarRegistroPagoClienteOff(pagosRecibidos, pagosParciales, idSecuencia);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
					} else if (sinAsignar > 0d && sinAsignar < 1d) {
						Long idSecuencia = grabarSecuencialTicketRPC();
						grabarRegistroPagoClienteOff(pagosRecibidos, pagosParciales, idSecuencia);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
					} else if (sinAsignar == 0d) {
						Long idSecuencia = grabarSecuencialTicketRPC();
						grabarRegistroPagoClienteOff(pagosRecibidos, pagosParciales, idSecuencia);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
					} else if (sinAsignar > -1d && sinAsignar < 0d) {
						Long idSecuencia = grabarSecuencialTicketRPC();// 28/03/2013
						grabarRegistroPagoClienteOff(pagosRecibidos, pagosParciales, idSecuencia);
						enableComponents(pnlCentral, false);
						btnGrabar.setEnabled(false);
						btnImprimirComprobante.setEnabled(true);
					} else if (sinAsignar <= -1d) {
						// No se graban los pagos.
						String msg = "El Importe Sin Asignar debe ser mayor a -1.0";
						JOptionPane.showMessageDialog(instancia, msg, "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
 
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO DE GENERACION DE TICKET SECUENCIAL
	 * 
	 * @return	RETORNA UN VALOR LONG QUE REPRESENTA
	 * 			UNA SECUENCIA DEL VENDEDOR QUE SE 
	 * 			GENERA POR CADA TRANSSACCIÓN
	 */
	public Long grabarSecuencialTicketRPC() {
		Long idCabecera;
		String codVendByCli = Util.obtenerCodigoVendedorPorCliente(codigoCliente);
		String codigoVendedor = String.valueOf(codVendByCli);
		if (!codigoVendedor.equals("")) {
			idCabecera = Long.parseLong(Util.obtenerSecuenciaPorVendedor(codigoVendedor));
		} else {
			idCabecera = Long.parseLong("0");
		}
		idCabecera++;
		try {
			if (Util.verificarVendedorEnTablaSecuencia(codigoVendedor))
				Util.actualizarSecuencia(codigoVendedor, String.valueOf(idCabecera));
			else {
				idCabecera = Long.parseLong("1");
				Util.insertarSecuenciaPorVendedor(codigoVendedor, "1");
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		String msg = "Se grabó correctamente el pago con N° Registro " + idCabecera;
		JOptionPane.showMessageDialog(Promesa.getInstance(), msg,"Exito", JOptionPane.INFORMATION_MESSAGE);
		return idCabecera;
	}

	private void actualizarRegistroPagoCliente(List<PagoRecibido> pagosRecibidos, List<PagoParcial> pagosParciales) {
		CabeceraRegistroPagoCliente crpc = cabecera;
		crpc.setCodigoCliente(codigoCliente);
		crpc.setCodigoVendedor(Promesa.datose.get(0) .getStrCodigo());
		crpc.setImporteEntrado(Double.parseDouble(txtImpEntrado.getText()));
		crpc.setImporteAsignado(Double.parseDouble(txtAsig.getText()));
		crpc.setImporteSinAsignar(Double.parseDouble(txtSinAsignar.getText()));
		crpc.setPartidasSeleccionadas(Long.parseLong(txtPartidasSel.getText()));
		crpc.setFechaCreacion(Util.convierteFechaACadenaDDMMYYYY(new Date()));
		crpc.setCodigoVendedor(Promesa.datose.get(0).getStrCodigo());
		crpc.setImporteEntrado(Double.parseDouble(txtImpEntrado.getText()));
		crpc.setImporteAsignado(Double.parseDouble(txtAsig.getText()));
		crpc.setImporteSinAsignar(Double.parseDouble(txtSinAsignar.getText()));
		crpc.setPartidasSeleccionadas(Long.parseLong(txtPartidasSel.getText()));
		crpc.setFechaCreacion(Util.convierteFechaACadenaDDMMYYYY(new Date()));
		Long idCabecera = crpc.getIdCabecera();
		for (PagoRecibido pagoRecibido : pagosRecibidos) {
			pagoRecibido.setIdCabecera(idCabecera);
		}
		for (PagoParcial pagoParcial : pagosParciales) {
			pagoParcial.setIdCabecera(idCabecera);
		}
		RegistroPagoCliente registroPago = new RegistroPagoCliente();
		registroPago.setCabeceraRegistroPagoCliente(crpc);
		registroPago.setListaPagoParcial(pagosParciales);
		registroPago.setListaPagoRecibido(pagosRecibidos);
		try {
			SqlRegistroPagoClienteImpl.actualizarRegistroPagoCliente(registroPago);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Promesa.getInstance(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void grabarRegistroPagoClienteOff(List<PagoRecibido> pagosRecibidos,List<PagoParcial> pagosParciales, Long secuencia) {
		// Marcelo Moyano secuencia de Registro Pago Cliente
		String codVendedor = Promesa.datose.get(0).getStrCodigo();
		sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
		CabeceraRegistroPagoCliente crpc = new CabeceraRegistroPagoCliente();
		crpc.setCodigoCliente(codigoCliente);
		crpc.setCodigoVendedor(codVendedor);// Marcelo Moyano
		crpc.setImporteEntrado(Double.parseDouble(txtImpEntrado.getText()));
		crpc.setImporteAsignado(Double.parseDouble(txtAsig.getText()));
		crpc.setImporteSinAsignar(Double.parseDouble(txtSinAsignar.getText()));
		crpc.setPartidasSeleccionadas(Long.parseLong(txtPartidasSel.getText()));
		crpc.setFechaCreacion(Util.convierteFechaACadenaDDMMYYYY(new Date()));
		crpc.setSincronizado("0");
		crpc.setCabeceraSecuencia(String.valueOf(secuencia));
		try {
			sqlCabeceraRegistroPagoClienteImpl.insertarCabeceraRegistroPagoCliente(crpc);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Promesa.getInstance(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		Long idCabecera = Long.parseLong(sqlCabeceraRegistroPagoClienteImpl.obtenerUltimoIdCabeceraRegistroPagoCliente());
		SqlPagoRecibidoImpl sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
		for (PagoRecibido pagoRecibido : pagosRecibidos) {
			pagoRecibido.setIdCabecera(idCabecera);
		}
		sqlPagoRecibidoImpl.insertarListaPagoRecibido(pagosRecibidos);
		SqlPagoParcialImpl sqlPagoParcialImpl = new SqlPagoParcialImpl();
		for (PagoParcial pagoParcial : pagosParciales) {
			pagoParcial.setIdCabecera(idCabecera);
		}
		sqlPagoParcialImpl.insertarListaPagoParcial(pagosParciales);
	}

	private boolean validaIngresoDatosPagosRecibidos(List<PagoRecibido> pagosRecibidos, List<PagoParcial> pagosParcialesActivos) {
		StringBuilder mensaje = new StringBuilder();
		String ordenSecuencia = "";
		boolean resultado = true;
		String msg = "";
		if (pagosRecibidos.size() > 0) {
			int posicion = 0;
			for (PagoRecibido pr : pagosRecibidos) {
				posicion++;
				if (pr.getFormaPago() != null) {
					ordenSecuencia = "Linea " + pr.getOrdenSecuencia();
					String idFormaPago = pr.getFormaPago().getIdFormaPago();
					if (("").equals(idFormaPago)) { // Vacío
						msg = ".- Debe definir el tipo de forma de pago para el registro " + posicion + ".\n";
						mensaje.append(ordenSecuencia+msg );
						resultado = false;
					} else if (("VC").equals(idFormaPago)) {
						if (pr.getImporte() == 0d) {// Cheque Post Fechado
							mensaje.append(ordenSecuencia + ".- El importe asociado a un cheque posfechado no " + "puede ser menor o igual a 0.\n");
							resultado = false;
						}
						if (("").equals(pr.getReferencia().trim())) {
							mensaje.append(ordenSecuencia + ".- Un cheque posfechado debe estar asociada a " + "una factura activa.\n");
							resultado = false;
						}
						String nroCta =  pr.getBancoCliente().getNroCtaBcoCliente();
						if (!validarNumeroDeCuentaCliente(codigoCliente, nroCta) || ("").equals(nroCta)) {
							mensaje.append(ordenSecuencia + ".- Un cheque posfechado requiere un número de "
									+ "cuenta de banco válido del cliente.\n");
							resultado = false;
						}
						if (("").equals(pr.getFechaVencimiento())) {
							mensaje.append(ordenSecuencia + ".- La fecha de vencimiento de un cheque posfechado " + "no puede ser nula.\n");
							resultado = false;
						} else {
							String fActual = Util.convierteFechaACadenaDDMMYYYY(new Date());
							Date fecha1 = Util.convierteCadenaDDMMYYYYAFecha(fActual);
							String strFecha2 = pr.getFechaVencimiento();
							Date fecha2 = Util.convierteCadenaYYYYMMDDAFecha(strFecha2);
							if (!Util.comparaFecha1MenorQueFecha2(fecha1,fecha2)) {
								mensaje.append(ordenSecuencia
										+ ".- La fecha de vencimiento de un cheque posfechado " + "debe ser mayor que la fecha de hoy.\n");
								resultado = false;
							}
						}
					} else if (("VP").equals(idFormaPago)) { // Pagaré
						if (pr.getImporte() == 0d) {
							mensaje.append(ordenSecuencia + ".- El importe asociado a un pagaré no puede ser " + "menor o igual a 0.\n");
							resultado = false;
						}
						if (("").equals(pr.getReferencia().trim())) {
							mensaje.append(ordenSecuencia + ".- Un pagaré debe estar asociada a una factura activa.\n");
							resultado = false;
						}
						if (("").equals(pr.getFechaVencimiento())) {
							mensaje.append(ordenSecuencia + ".- La fecha de vencimiento de un pagaré no " + "puede ser nula.\n");
							resultado = false;
						}
					} else if (("VT").equals(idFormaPago) || ("VI").equals(idFormaPago)) {
						if (pr.getImporte() == 0d) {// Retención
							mensaje.append(ordenSecuencia + ".- El importe asociado a una retención no puede " + "ser menor o igual a 0.\n");
							resultado = false;
						}
						if (pr.getReferencia().trim().length() != 17) {
							mensaje.append(ordenSecuencia + ".- La referencia de la retención debe tener " + "17 caracteres.\n");
							resultado = false;
						}
						if (!Pattern.matches(Constante.EXP_REG_REFERENCIA, pr.getReferencia().trim())) {
							mensaje.append(ordenSecuencia + ".- " +Constante.MENSAJE_COBRANZA_RETENCION+ "\n");
							resultado = false;
						}
						if (("").equals(pr.getNumeroFactura().trim())) {
							mensaje.append(ordenSecuencia + ".- Una retención debe estar asociada a " + "una factura activa.\n");
							resultado = false;
						} else {
							boolean bandera = false;
							for (PagoParcial pp : pagosParcialesActivos) {
								if (pp.getReferencia().trim().equals(pr.getNumeroFactura() .trim())) {
									bandera = true;
									break;
								}
							}
							String strFecha1 = pr.getFechaDocumento();
							Date fecha1 = Util.convierteCadenaYYYYMMDDAFecha(strFecha1);
							if(!Util.fechaMaximoTresMeses(fecha1)){
								mensaje.append(ordenSecuencia + ".- La fecha de documento debe ser superior a "+Util.fechaTresMesesAntes(fecha1)+".\n");
								resultado = false;
							}
							if (!bandera) {
								mensaje.append(ordenSecuencia + ".- Una retención debe estar asociada a " + "una factura válida.\n");
								resultado = false;
							}
						}
						if (("").equals(pr.getReferencia().trim())) {
							mensaje.append(ordenSecuencia + ".- Una retención debe tener una referencia asociada.\n");
							resultado = false;
						}
						if(pr.getFechaDocumento().equalsIgnoreCase("")){
							String msg1 = ".- Retencion IVA y Retencion Renta deben tener una fecha de Documento.\n";
							mensaje.append(ordenSecuencia + msg1);
							resultado = false;
						}else {
							String strFecha1 = pr.getFechaDocumento();
							Date fecha1 = Util.convierteCadenaYYYYMMDDAFecha(strFecha1);
							String strFecha2 = Util.convierteFechaACadenaDDMMYYYY(new Date());
							Date fecha2 = Util.convierteCadenaDDMMYYYYAFecha(strFecha2);
							if(!Util.esformatoFechaCorrecto(Util.convierteFechaACadenaDDMMYYYY(fecha1))){
								mensaje.append(ordenSecuencia + ".- La fecha de documento debe tener el siguiente formato (dd.mm.yyyy).\n");
								resultado = false;
							}
							if(!Util.fechaMaximoTresMeses(fecha1)){
								mensaje.append(ordenSecuencia + ".- La fecha de documento debe ser superior a "+Util.fechaTresMesesAntes(fecha1)+".\n");
								resultado = false;
							}
							if (!Util.comparaFecha1MenorOIgualQueFecha2(fecha1,fecha2)) {
								mensaje.append(ordenSecuencia + ".- La fecha de documento debe ser menor o " + "igual a la fecha de hoy.\n");
								resultado = false;
							}
						}
						if(("VI").equals(idFormaPago) && !this.validacionRetencionRentaIva(pr, pagosRecibidos)){
							String msg1 = ".- Retencion iva deba estar asocioda con Retencion Renta, con una factura valida y contar con la misma fecha documento.\n";
							mensaje.append(ordenSecuencia + msg1);
							resultado = false;
						}
					} else if (("VX").equals(idFormaPago) || ("VO").equals(idFormaPago)) {
						if (pr.getImporte() == 0d) {// Papeleta de
							mensaje.append(ordenSecuencia + ".- El importe asociado a una papeleta de depósito " + "no puede ser menor o igual a 0.\n");
							resultado = false;
						}
						/*
						 * En cobranza tipo recaudo realizada por Promovil, no debe validar referencia.
						 * solo la validacion debe de estar para recaudo "VO" y no para deposito "VX".
						 */
						if (("").equals(pr.getReferencia().trim())) {
							mensaje.append(ordenSecuencia + ".- Una papeleta de depósito debe estar asociada " + "a una factura activa.\n");
							resultado = false;
						}
						if (("").equals(pr.getFechaDocumento())) {
							mensaje.append(ordenSecuencia + ".- La fecha de documento de una papeleta de " + "depósito no puede ser nula.\n");
							resultado = false;
						} else {
							String strFecha1 = pr.getFechaDocumento();
							Date fecha1 = Util.convierteCadenaYYYYMMDDAFecha(strFecha1);
							if(!Util.esformatoFechaCorrecto(Util.convierteFechaACadenaDDMMYYYY(fecha1))){
								mensaje.append(ordenSecuencia + ".- La fecha de documento debe tener el siguiente formato (dd.mm.yyyy).\n");
								resultado = false;
							}
							if(!Util.fechaMaximoTresMeses(fecha1)){
								mensaje.append(ordenSecuencia + ".- La fecha de documento debe ser superior a "+Util.fechaTresMesesAntes(fecha1)+".\n");
								resultado = false;
							}
							String strFecha2 = Util.convierteFechaACadenaDDMMYYYY(new Date());
							Date fecha2 = Util.convierteCadenaDDMMYYYYAFecha(strFecha2);
							if (!Util.comparaFecha1MenorOIgualQueFecha2(fecha1,fecha2)) {
								mensaje.append(ordenSecuencia + ".- La fecha de documento debe ser menor o " + "igual a la fecha de hoy.\n");
								resultado = false;
							}
						}
						if (("").equals(pr.getBancoPromesa().getIdBancoPromesa().trim())) {
							mensaje.append(ordenSecuencia + ".- Un número de cuenta de banco " + "de promesa asociado a una papeleta de depósito " + "no puede ser nulo.\n");
							resultado = false;
						}
						if (("").equals(pr.getBancoPromesa().getDescripcionBancoPromesa().trim())) {
							mensaje.append(ordenSecuencia
									+ ".- Un número de cuenta de banco de promesa asociado " + "a una papeleta de depósito es incorrecto.\n");
							resultado = false;
						}
					} else if (("VY").equals(idFormaPago)) {
						if (pr.getImporte() == 0d) { // Cheque a la Vista
							mensaje.append(ordenSecuencia + ".- El importe asociado a un cheque a la vista no " + "puede ser menor o igual a 0.\n");
							resultado = false;
						}
						if (("").equals(pr.getReferencia().trim())) {
							mensaje.append(ordenSecuencia + ".- Un cheque a la vista debe estar asociado a una " + "factura activa.\n");
							resultado = false;
						}
						if (!validarNumeroDeCuentaCliente(codigoCliente, pr.getBancoCliente().getNroCtaBcoCliente())
								|| ("").equals(pr.getBancoCliente().getIdBancoCliente())) {
							mensaje.append(ordenSecuencia + ".- Un cheque a la vista requiere un número de " + "cuenta de banco válido del cliente.\n");
							resultado = false;
						}
						if (("").equals(pr.getBancoPromesa().getIdBancoPromesa().trim())) {
							mensaje.append(ordenSecuencia + ".- Un número de cuenta de banco " + "de promesa asociado a un cheque a la vista " + "no puede ser nulo.\n");
							resultado = false;
						}
					} else if (("VZ").equals(idFormaPago)) {
						if (pr.getImporte() == 0d) { // Efectivo
							mensaje.append(ordenSecuencia + ".- El importe asociado a un pago en efectivo no puede " + "ser menor o igual a 0.\n");
							resultado = false;
						}
//						if(("").equals(pr.getReferencia().trim())) {
//							mensaje.append(ordenSecuencia + ".- Un Pago en efectivo debe tener una Referencia.\n");
//							resultado = false;
//						}
					}
				} else {
					mensaje.append(ordenSecuencia + ".- Debe ingresar al menos una forma de pago.\n");
					resultado = false;
				}
			}
			if (verificaRepeticionNumeroFactura(pagosRecibidos)) {
				mensaje.append("Las retenciones sólo deben estar asociadas a " + "un número de factura.\n");
				resultado = false;
			}
			if (verificaRepeticionReferencia(pagosRecibidos)) {
				mensaje.append("Las retenciones sólo deben estar asociadas a " + "un número de Referencia.\n");
				resultado = false;
			}
			if (pagosParcialesActivos.size() == 0) {
				mensaje.append("Debe activar al menos una factura.\n");
				resultado = false;
			}
		} else {
			mensaje.append("Debe ingresar al menos una forma de pago.\n");
			resultado = false;
		}
		if (!resultado) {
			Mensaje.mostrarError(mensaje.toString());
		}
		return resultado;
	}

	private boolean validacionRetencionRentaIva(PagoRecibido pr,
			List<PagoRecibido> pagosRecibidos) {
		boolean retencion = false;
		boolean nroFactura = false;
		boolean fechaDoc = false;
		for (PagoRecibido pagoRecibido : pagosRecibidos) {
			FormaPago fp = pagoRecibido.getFormaPago();
			if(fp.getIdFormaPago().equals("VT")){
				retencion = false;
				nroFactura = false;
				if(validarValores(pr.getReferencia(), pagoRecibido.getReferencia())){
					retencion = true;
				} if(validarValores(pr.getNumeroFactura(), pagoRecibido.getNumeroFactura())){
					nroFactura = true;
				} if(validarValores(pr.getFechaDocumento(), pagoRecibido.getFechaDocumento())){
					fechaDoc = true;
				}
				if(retencion && nroFactura && fechaDoc){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean validarValores(String str1, String str2){
		if(str1.equalsIgnoreCase(str2)){
			return true;
		}
		return false;
	}

	private boolean validarNumeroDeCuentaCliente(String codigoCliente, String nroCtaBcoCliente) {
		boolean bandera = false;
		SqlBancoClienteImpl sqlBcoCliImpl = new SqlBancoClienteImpl();
		List<BancoCliente> bcosClientes = sqlBcoCliImpl.obtenerListaBancoCliente(codigoCliente);
		for (BancoCliente bc : bcosClientes) {
			if (bc.getNroCtaBcoCliente().equals(nroCtaBcoCliente)) {
				bandera = true;
				break;
			}
		}
		return bandera;
	}

	private boolean verificaRepeticionNumeroFactura(List<PagoRecibido> pagosRecibidos) {
		List<String> nrosFacturas = new ArrayList<String>();
		for (PagoRecibido pr : pagosRecibidos) {
			String nroFactura = pr.getNumeroFactura().trim();
			FormaPago fp = pr.getFormaPago();
			String idFormaPago = fp.getIdFormaPago();
			if (!("").equals(nroFactura) && !idFormaPago.equalsIgnoreCase("VI")) {
				nrosFacturas.add(pr.getNumeroFactura().trim());
			}
		}
		if (nrosFacturas.size() > 0) {
			for (int i = 0; i < nrosFacturas.size(); i++) {
				String numeroFactura1 = nrosFacturas.get(i);
				for (int j = i + 1; j < nrosFacturas.size(); j++) {
					String numeroFactura2 = nrosFacturas.get(j);
					if (numeroFactura1.equals(numeroFactura2)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//Marcelo Moyano
	private boolean verificaRepeticionReferencia(List<PagoRecibido> pagosRecibidos) {
		List<String> nrosFacturas = new ArrayList<String>();
		for (PagoRecibido pr : pagosRecibidos) {
			String referencia = pr.getReferencia().trim();
			FormaPago fp = pr.getFormaPago();
			String idFormaPago = fp.getIdFormaPago();
			if (!("").equals(referencia) && idFormaPago.equalsIgnoreCase("VT")) {
				nrosFacturas.add(pr.getReferencia().trim());
			}
		}
		if (nrosFacturas.size() > 0) {
			for (int i = 0; i < nrosFacturas.size(); i++) {
				String numeroFactura1 = nrosFacturas.get(i);
				for (int j = i + 1; j < nrosFacturas.size(); j++) {
					String numeroFactura2 = nrosFacturas.get(j);
					if (numeroFactura1.equals(numeroFactura2)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void enableComponents(Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				enableComponents((Container) component, enable);
			}
		}
	}

	private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		int tipo = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la ventana?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (tipo == JOptionPane.OK_OPTION) {
			this.dispose();
			if(verificarVisitas("",codigoCliente).equals("")){
				
				IVentanaVisita tp = new IVentanaVisita(codigoCliente);
				tp.setVisible(true);
			}
		}
	}

	private void btnImprimirComprobanteActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
//					imprimirNew(" - ORIGINAL - ");
//					imprimirOld(); // Probando Marcelo Moyano 07/07/2014
					if(Constante.IMPRESORA_NEW.equals(strDispositivoImpresora)){
						try {
								imprimirNew(" - ORIGINAL - ");
								imprimirNew(" - COPIA - ");
						}catch (Exception e) {
							imprimirOld();
						}
					}else {
						imprimirOld();
					}
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
	
	protected void imprimirNew(String strTitulo) {
		if (fuente == Constante.FROM_DB) {
			imprimirReporteOfflineDPP350(strTitulo);
		} else {
			if (banderaGrabaOnLine) {
				 imprimirReporteOnlineDPP350(strTitulo);
			} else {
				imprimirReporteOfflineDPP350(strTitulo);
			}
		}
	}

	protected void imprimirOld() {
		if (fuente == Constante.FROM_DB) {
			imprimirReporteOffline();
		} else {
			if (banderaGrabaOnLine) {
				imprimirReporteOnline();
			} else {
				imprimirReporteOffline();
			}
		}
	}

	private void btnAniadirActionPerformed(java.awt.event.ActionEvent evt) {
		secuenciaFormaPago++;
		agregarFormaPago();
	}

	private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
		Component[] componentes = pnlGridPagosRecibidos.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			Component component = componentes[i];
			if (component instanceof PanelElementosPagosRecibidos) {
				if (((PanelElementosPagosRecibidos) component).isSeleccionado()) {
					pnlGridPagosRecibidos.remove(component);
					pnlGridPagosRecibidos.updateUI();
					secuenciaFormaPago--;
					break;
				}
			}
		}
		recalcularSecuenciaFormaPago();
		pnlGridPagosRecibidos.updateUI();
		recalcularTodo();
	}

	private void recalcularSecuenciaFormaPago() {
		Component[] componentes = pnlGridPagosRecibidos.getComponents();
		PanelElementosPagosRecibidos pepr = null;
		for (int i = 0; i < componentes.length; i++) {
			if (componentes[i] instanceof PanelElementosPagosRecibidos) {
				pepr = (PanelElementosPagosRecibidos) componentes[i];
				pepr.setSecuenciaFormaPago(i);
			}
		}
	}

	private void btnSeleccionarTodoActionPerformed(java.awt.event.ActionEvent evt) {
		tblPagoParc.setRowSelectionInterval(0, tblPagoParc.getRowCount() - 1);
	}

	private void btnDeseleccionarActionPerformed(java.awt.event.ActionEvent evt) {
		tblPagoParc.clearSelection();
	}

	private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {
		int filas[] = tblPagoParc.getSelectedRows();
		for (int i : filas) {
			boolean esHijo = modeloPagosRecibidos.esHijo(i);
			PagoParcial pp = modeloPagosRecibidos.obtenerPagoParcial(i);
			if (!esHijo && pp.getImportePago() > 0d && !pp.isActivo()) {
				pp.setActivo("1");
			}
		}
		tblPagoParc.updateUI();
		recalcularTodo();
		int cantidad = modeloPagosRecibidos.obtenerCantidadPartidasActivas();
		txtPartidasSel.setText(String.valueOf(cantidad));
	}

	private void btnDesactivarActionPerformed(java.awt.event.ActionEvent evt) {
		int filas[] = tblPagoParc.getSelectedRows();
		for (int i : filas) {
			boolean esHijo = modeloPagosRecibidos.esHijo(i);
			PagoParcial pp = modeloPagosRecibidos.obtenerPagoParcial(i);
			double pagoTotal = pp.getImportePagoTotal();
			double pagoParcial =  pp.getImportePagoParcial();
			double importePago = pagoTotal - pagoParcial;
			pp.setImportePago(importePago);
			if (!esHijo && pp.getImportePago() > 0d && pp.isActivo()) {
				pp.setActivo("0");
			}
		}
		tblPagoParc.updateUI();
		recalcularTodo();
		int cantidad= modeloPagosRecibidos.obtenerCantidadPartidasActivas();
		txtPartidasSel.setText(String.valueOf(cantidad));
	}

	public void recalcularTodo() {
		BigDecimal importeTotal = new BigDecimal(0);
		Component[] componentes = pnlGridPagosRecibidos.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			Component component = componentes[i];
			if (component instanceof PanelElementosPagosRecibidos) {
				importeTotal = importeTotal.add(((PanelElementosPagosRecibidos) component).obtenerImporte());
			}
		}
		String total = Util.formatearNumero("" + importeTotal);
		txtTotal.setText(total);
		String impEntrada = Util.formatearNumero("" + importeTotal);
		txtImpEntrado.setText(impEntrada);
		BigDecimal asig = modeloPagosRecibidos.obtenerImporteTotal();
		String strAsig = Util.formatearNumero("" + asig);
		txtAsig.setText(strAsig);
		BigDecimal sinAsignar = new BigDecimal(0);
		BigDecimal entrado = new BigDecimal(txtImpEntrado.getText());
		BigDecimal asignado = new BigDecimal(txtAsig.getText());
		sinAsignar = entrado.subtract(asignado);
		String strSinAsig = Util.formatearNumero(String.valueOf(sinAsignar));
		txtSinAsignar.setText(strSinAsig);
	}

	private void IngresarSecuenciaTabla(ModeloPagosRecibidos mpr) {
		for (int i = 0; i < mpr.getRowCount(); i++) {
			PagoParcial pp = mpr.obtenerPagoParcial(i);
			if (pp.getSecuencia() != 0) {
				tblPagoParc.setValueAt(pp.getSecuencia(), i, 0);
				tblPagoParc.updateUI();
			}
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if (arg0.getSource() == txtImportePago) {
			txtImportePago.selectAll();
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (arg0.getSource() == txtImportePago) {
			modeloPagosRecibidos.validarImportesIngresados();
			recalcularTodo();
		}
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnActivar;
	private javax.swing.JButton btnAniadir;
	private javax.swing.JButton btnCancelar;
	private javax.swing.JButton btnDesactivar;
	private javax.swing.JButton btnDeseleccionar;
	private javax.swing.JButton btnEliminar;
	private javax.swing.JButton btnGrabar;
	private javax.swing.JButton btnImprimirComprobante;
	//TODO
	private JButton btnAgenda;
	private javax.swing.JButton btnSeleccionarTodo;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JToolBar.Separator jSeparator2;
	private javax.swing.JLabel lblAsig;
	private javax.swing.JLabel lblEstatusTratamiento;
	private javax.swing.JLabel lblImpEntrado;
	private javax.swing.JLabel lblPagosRecibidos;
	private javax.swing.JLabel lblPartidasSel;
	private javax.swing.JLabel lblSinAsignar;
	private javax.swing.JLabel lblTitulo;
	private javax.swing.JLabel lblTotal;
	private javax.swing.JToolBar mnuBarCentral;
	private javax.swing.JToolBar mnuBarSuperior;
	private javax.swing.JToolBar mnuToolBarPagoParcial;
	private PanelColumnasPagosRecibidos panelColumnasPagosRecibidos1;
	private javax.swing.JPanel pnlCentral;
	private javax.swing.JPanel pnlGridPagosRecibidos;
	private javax.swing.JPanel pnlInferior;
	private javax.swing.JPanel pnlInferiorDatos;
	private javax.swing.JPanel pnlPagoParc;
	private javax.swing.JPanel pnlPagoParcialInterno;
	private javax.swing.JPanel pnlPagosRecibidos;
	private javax.swing.JPanel pnlPagosRecibidosToolBar;
	private javax.swing.JPanel pnlSuperior;
	private javax.swing.JPanel pnlTotal;
	private javax.swing.JScrollPane scrPagoParc;
	private javax.swing.JScrollPane scrPagosRecibidos;
	private javax.swing.JToolBar.Separator separador;
	private javax.swing.JTable tblPagoParc;
	private javax.swing.JTabbedPane tbpPagoParcial;
	private javax.swing.JTextField txtAsig;
	private javax.swing.JTextField txtImpEntrado;
	private javax.swing.JTextField txtPartidasSel;
	private javax.swing.JTextField txtSinAsignar;
	private javax.swing.JTextField txtTotal;
}