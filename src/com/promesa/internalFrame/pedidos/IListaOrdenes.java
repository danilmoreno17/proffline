package com.promesa.internalFrame.pedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

//import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.CabeceraHojaMaestraCredito;
import com.promesa.cobranzas.sql.impl.SqlCabeceraHojaMaestraCreditoImpl;
import com.promesa.internalFrame.cobranzas.IFlujoDocumento;
//import com.promesa.internalFrame.cobranzas.IRegistroAnticipo;
import com.promesa.internalFrame.pedidos.custom.ModeloListaPedidos;
import com.promesa.internalFrame.pedidos.custom.RenderConsultaPedidos;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoHeader;
//import com.promesa.pedidos.bean.BeanPedidosParaSap;
//import com.promesa.pedidos.sql.SqlSincronizacion;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.pedidos.sql.impl.SqlPedidoImpl;
import com.promesa.sap.SPedidos;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.sincronizacion.impl.SincronizacionPedidos;
//import com.promesa.util.Cmd;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.TablaAExcel;
import com.promesa.util.Util;
/*
 * 		CLASES QUE MUESTRA UNA INTERFAZ GRACICA DONDE SE
 * 		PUEDE REALIZAR LAS CONSULTAS DE LOS PEDIDOS, ORDENES,
 * 		Y COTIZACIONES DESDE UNA FECHA DE INICIO HASTA UNA FECHA
 * 		FINAL.
 */
public class IListaOrdenes extends javax.swing.JInternalFrame implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	List<BeanPedidoHeader> listaPedido;
	List<BeanPedidoHeader> pedidoe;
	BeanPedidoHeader pedido = null;
	SPedidos objSAP;
	private String strIdVendedor;
	public String modo = "0";
	private ModeloListaPedidos modeloPedido;
	
	private static final int COLUMNA_DOC_VENTA = 1;
	private static final int COLUMNA_CL_PEDIDO = 7;
	private IListaOrdenes singleton = null;
	
	/*
	 * 	CONSTRUCTOR
	 */
	public IListaOrdenes(BeanTareaProgramada beanTareaProgramada) {
		initComponents();
		
		modeloPedido = new ModeloListaPedidos();
		tblOrdenes.setModel(modeloPedido);
		((DefaultTableCellRenderer) tblOrdenes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		btnBuscar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/buscar.gif")));
		btnExportar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/excel.png")));
		btnSincronizar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/synch.png")));
		btnFlujoDocumentos.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/flujo.png")));
		btnCotizacionAPedido.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/toma_pedido.png")));
		dateFechaInicio.setDateFormatString("dd-MM-yyyy");
		datefechaFinal.setDateFormatString("dd-MM-yyyy");

		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/ipanelcontrol.png")));
		tblOrdenes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		BeanDato usuario = Promesa.datose.get(0);
		strIdVendedor = usuario.getStrCodigo();
		dateFechaInicio.setDate(new Date());
		datefechaFinal.setDate(new Date());
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));
		setIconifiable(true);
		tablaVacia();
		((DefaultTableCellRenderer) tblOrdenes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblOrdenes.setRowHeight(Constante.ALTO_COLUMNAS);
		tblOrdenes.getTableHeader().setReorderingAllowed(false);
		btnSincronizar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSincronizarActionPerformed(evt);
			}
		});
		
		tblOrdenes.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblOrdenesTablaMouseClicked(evt);
			}
		});
		singleton = this;
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE LANZA UN EVENTO DE DOBLE CLICK A UN JTABLE
	 * 			Y LO ORDENA POR LAS COLUMNA DOC DE VENTA Y CLASE DE PEDIDO.
	 * 
	 * @param	evt EVENTO CAPTURADO.
	 * 
	 */
	protected void tblOrdenesTablaMouseClicked(MouseEvent evt) {
		final int columna = tblOrdenes.columnAtPoint(evt.getPoint());
		if(columna == COLUMNA_DOC_VENTA || columna == COLUMNA_CL_PEDIDO){
			quickSort(columna, 0, modeloPedido.getRowCount() - 1);
		}
		tblOrdenes.updateUI();
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO DE ORDEMAMIENTO RAPIDO.
	 * 
	 *  @param	columna PARAMETRO QUE INDICA POR CUAL COLUMNA
	 *  		SE DEBE ORDENAR.
	 *  @param 	left PARAMETRO DE INICIO DE REGION DEL VECTOR QUE
	 *  		SE VA A ORDENAR.
	 *  @param	right PARAMETRO DE FIN DE REGION DEL VECTOR QUE
	 *  		SE VA A ORDENAR.
	 *  
	 */
	private void quickSort(int columna, int left, int right) {
		int index = 0;
		if (columna == COLUMNA_DOC_VENTA) {
			index = partitionDocVenta(left, right);
		} else if (columna == COLUMNA_CL_PEDIDO) {
			index = partitionClaseDoc(left, right);
		} 
		if (left < index - 1)
			quickSort(columna, left, index - 1);
		if (index < right)
			quickSort(columna, index, right);
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE PARTICIONA Y ORDENA EL JTABLE
	 * 			CON POR MEDIO DE LA COLUMNA DOCUMENTO DE VENTA.
	 * 
	 * @param 	left PARAMETRO DE INICIO DE REGION DEL VECTOR QUE
	 *  		SE VA A ORDENAR.
	 * @param	right PARAMETRO DE FIN DE REGION DEL VECTOR QUE
	 *  		SE VA A ORDENAR.
	 */
	private int partitionDocVenta(int left, int right) {
		int i = left, j = right;
		int pivot = (left + right) / 2;
		BeanPedidoHeader pivotHeader = modeloPedido.obtenerPedidoHeader(pivot);
		int strReferencia = Integer.parseInt(pivotHeader.getStrDocumentoVenta());
		BeanPedidoHeader beanPedidoHeaderTmp = null;
		while (i <= j) {
			while (((Integer.parseInt(modeloPedido.obtenerPedidoHeader(i).getStrDocumentoVenta()) < strReferencia)))
				i++;
			while (Integer.parseInt(modeloPedido.obtenerPedidoHeader(j).getStrDocumentoVenta()) > strReferencia)
				j--;
			if (i <= j) {
				beanPedidoHeaderTmp = modeloPedido.obtenerPedidoHeader(i);
				BeanPedidoHeader beanPedidoHeaderTmpLista = pedidoe.get(i);
				BeanPedidoHeader listaTmp = listaPedido.get(i);
				
				modeloPedido.actualizarModeloPedido(modeloPedido.obtenerPedidoHeader(j), i);
				actualizarPedidos(pedidoe.get(j), i);
				actualizarListaPedidos(listaPedido.get(j), i);
				
				modeloPedido.actualizarModeloPedido(beanPedidoHeaderTmp, j);
				actualizarPedidos(beanPedidoHeaderTmpLista, j);
				actualizarListaPedidos(listaTmp, i);
				
				i++;
				j--;
			}
		}
		return i;
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE PARTICIONA Y ORDENA EL JTABLE
	 * 			CON POR MEDIO DE LA COLUMNA CLASE DE DOCUMENTO.
	 * 
	 * @param 	left PARAMETRO DE INICIO DE REGION DEL VECTOR QUE
	 *  		SE VA A ORDENAR.
	 * @param	right PARAMETRO DE FIN DE REGION DEL VECTOR QUE
	 *  		SE VA A ORDENAR.
	 */
	private int partitionClaseDoc(int left, int right) {
		int i = left, j = right;
		int pivot = (left + right) / 2;
		BeanPedidoHeader pivotHeader = pedidoe.get(pivot);
		String strReferencia = pivotHeader.getStrClDocVentas();
		BeanPedidoHeader beanPedidoHeaderTmp = null;
		while (i <= j) {
			while (((modeloPedido.obtenerPedidoHeader(i)).getStrClDocVentas()).compareTo(strReferencia) < 0)
				i++;
			while (((modeloPedido.obtenerPedidoHeader(j)).getStrClDocVentas()).compareTo(strReferencia) > 0)
				j--;
			if (i <= j) {
				beanPedidoHeaderTmp = modeloPedido.obtenerPedidoHeader(i);
				BeanPedidoHeader beanPedidoHeaderTmpLista = pedidoe.get(i);
				BeanPedidoHeader listaTmp = listaPedido.get(i);
				
				modeloPedido.actualizarModeloPedido(modeloPedido.obtenerPedidoHeader(j), i);
				actualizarPedidos(pedidoe.get(j), i);
				actualizarListaPedidos(listaPedido.get(j), i);
				
				modeloPedido.actualizarModeloPedido(beanPedidoHeaderTmp, j);
				actualizarPedidos(beanPedidoHeaderTmpLista, j);
				actualizarListaPedidos(listaTmp, i);
				i++;
				j--;
			}
		}
		return i;
	}
	
	public void actualizarPedidos(BeanPedidoHeader pedidoCabecera, int i){
		if(i >= 0 && i < pedidoe.size()){
			pedidoe.set(i, pedidoCabecera);
		}
	}
	public void actualizarListaPedidos(BeanPedidoHeader pedidoCabecera, int i){
		if(i >= 0 && i < listaPedido.size()){
			listaPedido.set(i, pedidoCabecera);
		}
	}
	
	/*
	 * 	SEGUNDO CONSTRUCTOR
	 */
	public IListaOrdenes(){
	}

	/*
	 * 		METODO QUE INICIALIZA LOS COMPONENTES DE LA
	 * 		INTERFAZ GRAFICA DE LISTA DE ORDENES. 
	 */
	private void initComponents() {
		pnlContenedor = new javax.swing.JPanel();
		pnlTabla = new javax.swing.JPanel();
		scrollOrdenes = new javax.swing.JScrollPane();
		tblOrdenes = new javax.swing.JTable();
		pnlBuscar = new javax.swing.JPanel();
		lblFechaInicio = new javax.swing.JLabel();
		dateFechaInicio = new com.toedter.calendar.JDateChooser();
		lblFechaFinal = new javax.swing.JLabel();
		datefechaFinal = new com.toedter.calendar.JDateChooser();
		pnlOpciones = new javax.swing.JPanel();
		btnBuscar = new javax.swing.JButton();
		btnExportar = new javax.swing.JButton();
		btnSincronizar = new javax.swing.JButton();
		btnFlujoDocumentos = new javax.swing.JButton();
		btnCotizacionAPedido = new javax.swing.JButton();

		setClosable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Lista de ordenes");

		pnlTabla.setLayout(new java.awt.BorderLayout());

		scrollOrdenes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		tblOrdenes.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblOrdenesMouseClicked(evt);
			}
		});
		scrollOrdenes.setViewportView(tblOrdenes);

		pnlTabla.add(scrollOrdenes, java.awt.BorderLayout.CENTER);

		pnlBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

		lblFechaInicio.setText("Desde:");

		dateFechaInicio.setDateFormatString("dd-MM-yyyy");

		lblFechaFinal.setText("Hasta:");

		datefechaFinal.setDateFormatString("dd-MM-yyyy");

		pnlOpciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));


		btnBuscar.setText("Buscar");
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});
		pnlOpciones.add(btnBuscar);

		btnExportar.setText("Exportar");
		btnExportar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExportarActionPerformed(evt);
			}
		});
		pnlOpciones.add(btnExportar);

		btnSincronizar.setText("Sincronizar");
		pnlOpciones.add(btnSincronizar);

		btnFlujoDocumentos.setText("Flujo de documentos");
		btnFlujoDocumentos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFlujoDocumentosActionPerformed(evt);
			}
		});
		pnlOpciones.add(btnFlujoDocumentos);
		
		//	Marcelo Moyano 12/05/2013
		//	Asunto: PRO-2013-0034 - Copia Cotización Pedido
		btnCotizacionAPedido.setText("Pasar a Pedido");
		btnCotizacionAPedido.setVisible(false);
		btnCotizacionAPedido.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				btnCotizacionAPedidoActionPerformed(evt);
			}
		});
		pnlOpciones.add(btnCotizacionAPedido);

		javax.swing.GroupLayout pnlBuscarLayout = new javax.swing.GroupLayout(pnlBuscar);
		pnlBuscar.setLayout(pnlBuscarLayout);
		pnlBuscarLayout.setHorizontalGroup(pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlBuscarLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlBuscarLayout.createSequentialGroup()
				.addComponent(lblFechaInicio).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(dateFechaInicio,javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(lblFechaFinal).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(datefechaFinal,javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addComponent(pnlOpciones,javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, 652,Short.MAX_VALUE)).addContainerGap()));

		pnlBuscarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { lblFechaFinal, lblFechaInicio });

		pnlBuscarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { dateFechaInicio, datefechaFinal });

		pnlBuscarLayout.setVerticalGroup(pnlBuscarLayout
			.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlBuscarLayout.createSequentialGroup()
			.addGroup(pnlBuscarLayout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlBuscarLayout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING, false)
			.addComponent(lblFechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
			.addComponent(lblFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(dateFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE,
					javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addComponent(datefechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE,
					javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(pnlOpciones, javax.swing.GroupLayout.DEFAULT_SIZE,
					33, Short.MAX_VALUE).addContainerGap()));

		javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
		pnlContenedor.setLayout(pnlContenedorLayout);
		pnlContenedorLayout.setHorizontalGroup(pnlContenedorLayout
			.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlContenedorLayout.createSequentialGroup()
			.addContainerGap().addGroup(pnlContenedorLayout
			.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addComponent(pnlTabla, javax.swing.GroupLayout.Alignment.TRAILING,
					javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
			.addComponent(pnlBuscar, javax.swing.GroupLayout.Alignment.TRAILING,
					javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE))
			.addContainerGap()));
		pnlContenedorLayout.setVerticalGroup(pnlContenedorLayout
			.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(pnlContenedorLayout.createSequentialGroup()
			.addContainerGap().addComponent(pnlBuscar, javax.swing.GroupLayout.PREFERRED_SIZE,
					85, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE,
					320, Short.MAX_VALUE).addContainerGap()));

		getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void btnFlujoDocumentosActionPerformed(java.awt.event.ActionEvent evt) {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						int filaSeleccionada = tblOrdenes.getSelectedRow();
						if (filaSeleccionada > -1) {
							String estado = tblOrdenes.getValueAt(filaSeleccionada, 0).toString();
							if (estado.compareTo("0") == 0) {
								String numeroPedido = tblOrdenes.getValueAt(filaSeleccionada, 1).toString();
								String codigoCliente = tblOrdenes.getValueAt(filaSeleccionada, 9).toString();
								String nombreCliente = tblOrdenes.getValueAt(filaSeleccionada, 10).toString();
								IFlujoDocumento iF = new IFlujoDocumento(numeroPedido, codigoCliente, nombreCliente);
								Promesa.destokp.add(iF);
								iF.setVisible(true);
								iF.setMaximum(true);
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
	}

	private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					buscaPedidos();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE ACTUALIZA LA TABLA DE CONSULTA DE PEDIDOS
	 * 			REALIZADA DESPUES DE REALIZAR UNA MODIFICACION DE 
	 * 			UN PEDIDO, PROFORMA O COTIZACION.
	 */
	public void actualizarPedidos() {
		//final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					buscaPedidos();
				} catch(Exception e){
					Util.mostrarExcepcion(e);
				}finally {
					//bloqueador.dispose();
				}
			}
		};
		hilo.start();
		//bloqueador.setVisible(true);
	}
	

	private void btnSincronizarActionPerformed(java.awt.event.ActionEvent evt) {
		BeanDato usuario = Promesa.datose.get(0);
		if (usuario.getStrModo().equals("1")) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						SincronizacionPedidos spe = new SincronizacionPedidos();
						int filas[] = tblOrdenes.getSelectedRows();
						if (filas == null || filas.length == 0) {
							bloqueador.dispose();
							return;
						}
						String id = null;
						// validamos las filas
						for (int m : filas) {
							String estado = tblOrdenes.getValueAt(m, 0).toString();
							if (estado.compareTo("0") == 0) {
								bloqueador.dispose();
								Mensaje.mostrarWarning("Solo pueden sincronizarse " + "pedidos almacenados en repositorio local.");
								return;
							}
						}
						for (int m : filas) {
							id = tblOrdenes.getModel().getValueAt(m, 1).toString();
							String source = tblOrdenes.getValueAt(m, 0).toString();
							SqlPedidoImpl sqlPi = new SqlPedidoImpl();
							BeanPedido bp = new BeanPedido();
							BeanPedidoHeader bph = new BeanPedidoHeader();
							String codigoPedido = "";
							String idBd = sqlPi.obtenerId(id);
							bp = sqlPi.obtenerPedido(idBd);
							bph = bp.getHeader();
							
							codigoPedido = bph.getCodigoPedido();
							if(source.compareTo("2") == 0){
								bph.setREQ_DATE_H(Util.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(bph.getREQ_DATE_H()));
								bph.setPURCH_DATE(Util.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(bph.getPURCH_DATE()));
								SPedidos sp = new SPedidos();
								bp.setCodigoPedido(codigoPedido);
								String mensaje[] = sp.editarOrden(bp);
								if(!mensaje[0].equals("E")){
									SqlPedidoImpl sqlPedido = new SqlPedidoImpl();
									sqlPedido.setActualizaPedidoHeaderPartnersDetalle(bph.getIdBD());	
								}else{
									Mensaje.mostrarError(mensaje[1] + "\n");
								}
									
							}else
								spe.sincronizaPedidos(id);	
						}
						buscaPedidos();
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarWarning(Constante.ERROR_SINCRONIZACION);
		}
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE REALIZA LA CONSULTA DE LOS 
	 * 			PEDIDOS, PROFORMAS Y COTIZACIONES DESDE
	 * 			UN RANGO DE FECHAS TANTO DESDE SAP Y EN 
	 * 			BASE LOCAL.
	 *  
	 */
	protected void buscaPedidos() {
//	public void buscaPedidos() {
		tablaVacia();
		String strFechaInicio;
		String strFechaFinal;
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		strFechaInicio = formato.format(dateFechaInicio.getDate());
		strFechaFinal = formato.format(datefechaFinal.getDate());
		pedidoe = new ArrayList<BeanPedidoHeader>();
		BeanDato usuario = Promesa.datose.get(0);
		SqlPedidoImpl sqlPedido = new SqlPedidoImpl();
		if (usuario.getStrModo().equals("1")) {
			// DESDE SAP
			try {
				objSAP = new SPedidos();
				listaPedido = objSAP.vendedorPedidos(strIdVendedor, "ZP01", strFechaInicio, strFechaFinal);
				SqlPedidoImpl pedido = new SqlPedidoImpl();
				List<BeanPedidoHeader> fromLocal = pedido.listarPedidos(strIdVendedor, strFechaInicio, strFechaFinal);
				if(listaPedido != null && listaPedido.size() > 0){
					listaPedido.addAll(fromLocal);
				} else {
					listaPedido = new ArrayList<BeanPedidoHeader>();
					listaPedido.addAll(fromLocal);
				}
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		} else {
			// DESDE SQLITE
			listaPedido = sqlPedido.listarPedidos(strIdVendedor, strFechaInicio, strFechaFinal);
		}
		if(listaPedido !=null && listaPedido.size() > 0){
			for (BeanPedidoHeader p : listaPedido) {
				pedido = new BeanPedidoHeader();
				int source = p.getSource();
				pedido.setSource(source);
				pedido.setStrCliente(p.getStrCliente());
				pedido.setStrClDocVentas(p.getStrClDocVentas());
				pedido.setStrCodCliente(p.getStrCodCliente());
				pedido.setStrCodVendedor(p.getStrCodVendedor());
				pedido.setStrCreador(p.getStrCreador());
				pedido.setIdBD(p.getIdBD());
				/*
				 * 	Marcelo Moyano 06/05/2013 - 13:03
				 * 	Requerimientos: PRO-2013-0025
				 * 	Titulo: Modificar pedidos offline Grabados en SAP
				 */
				if(source == 0 )
					pedido.setStrDocumentoVenta(p.getStrDocumentoVenta());
				else if(source == 2)
					pedido.setStrDocumentoVenta(p.getCodigoPedido());
				else
					pedido.setStrDocumentoVenta(p.getStrDocumentoVenta());
				pedido.setStrFechaCreacion(p.getStrFechaCreacion());
				pedido.setStrFechaDocumento(p.getStrFechaDocumento());
				pedido.setStrValorNeto(Util.formatearNumeroTresDigitos(p.getStrValorNeto()));
				/*
				 *	Marcelo Moyano 02/09/2013
				 * 	Requerimineto: PRO-2013-0077 - Ajuste a consulta de Pedidos
				 */
				pedido.setStrDocumentoReferencia(p.getStrDocumentoReferencia());
				pedido.setStrFechaReferencia(p.getStrFechaReferencia());
				pedido.setStatus(p.getStatus());
				try {
					pedidoe.add(pedido);
				} catch (Exception e) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
					Util.mostrarExcepcion(e);
				}
			}
			tablaLLena2();
		}
		
	}

	public void tablaVacia() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "", "<html><b>DOC. DE VENTA ▼</b></html>", "FECHA DOC.", "STATUS", "DOC. REFER.", "FECH. REFER.", "VALOR NETO",
						"<html><b>CL. DOC. VENTAS ▼</b></html>", "COD. DE CLIENTE", "CLIENTE", "CREADO POR", "COD. VENDEDOR", "CREADO EL", "" };
				
				DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblOrdenes.setModel(tblTablaModel);
				tblOrdenes.updateUI();
				Util.setAnchoColumnas(tblOrdenes);
				setAnchoColumnas();
			}

		});
	}

	private void setAnchoColumnas() {
		int anchoColumna = 0;
		TableColumnModel modeloColumna = tblOrdenes.getColumnModel();
		TableColumn columnaTabla;
		for (int i = 0; i < tblOrdenes.getColumnCount(); i++) {
			columnaTabla = modeloColumna.getColumn(i);
			switch (i) {
			case 13:
				anchoColumna = 0;
				columnaTabla.setMinWidth(anchoColumna);
				columnaTabla.setMaxWidth(anchoColumna);
				columnaTabla.setPreferredWidth(anchoColumna);
				break;
			}
		}
	}
	
	/*
	 * @author	MARCCELO MOYANO
	 * 
	 * 			METODO QUE SE ENCARGA DE LLENAR LA TABLA DE PEDIDOS
	 */
	public void tablaLLena2() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				modeloPedido.limpiar();
				for (BeanPedidoHeader asistencia : pedidoe) {
					modeloPedido.agregarPedido(asistencia);
				}
				RenderConsultaPedidos render = new RenderConsultaPedidos();
				tblOrdenes.setDefaultRenderer(Object.class, render);
				tblOrdenes.setModel(modeloPedido);
				Util.setAnchoColumnas(tblOrdenes);
				setAnchoColumnas();
				tblOrdenes.updateUI();
			}
		});
	}
	
	/*
	 * @deprecated
	 */
	public void tablaLLena() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				String Columnas[] = { "", "<html><b>DOC. DE VENTA ▼</b></html>", "FECHA DOC.", "STATUS", "DOC. REFER.", "FECH. REFER.", "VALOR NETO",
						"<html><b>CL. DOC. VENTAS ▼</b></html>", "COD. DE CLIENTE", "CLIENTE", "CREADO POR", "COD. VENDEDOR", "CREADO EL", "" };
				
				DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblTablaModel.setNumRows(0);
				for (int i = 0; i < pedidoe.size(); i++) {
					BeanPedidoHeader p = pedidoe.get(i);
					String[] values = { "" + p.getSource(),//00
							"" + p.getStrDocumentoVenta(),//01
							"" + p.getStrFechaDocumento(),//02
							"" + p.getStatus(),//03
							"" + p.getStrDocumentoReferencia(),//04
							"" + p.getStrFechaReferencia(),//05
							"" + Util.formatearNumero(p.getStrValorNeto()),// 06
							"" + p.getStrDocumentoVenta(),// 07
							"" + p.getStrCodCliente(), // 08
							"" + p.getStrCliente(), // 09
							"" + p.getStrCreador(), // 10
							"" + p.getStrCodVendedor(), // 11
							"" + p.getStrFechaCreacion(), // 12
							"" + p.getSource() };//13
					tblTablaModel.addRow(values);
				}
				RenderConsultaPedidos render = new RenderConsultaPedidos();
				tblOrdenes.setDefaultRenderer(Object.class, render);
				tblOrdenes.setModel(tblTablaModel);
				Util.setAnchoColumnas(tblOrdenes);
				setAnchoColumnas();
				tblOrdenes.updateUI();
			}
		});

	}

	private void tblOrdenesMouseClicked(java.awt.event.MouseEvent evt) {
		final int fila = tblOrdenes.rowAtPoint(evt.getPoint());
		if (evt.getClickCount() == 2) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						if (fila > -1) {
							SqlPedidoImpl sql = new SqlPedidoImpl();
							String source = tblOrdenes.getValueAt(fila, 0).toString();
//							String codigoCliente = tblOrdenes.getValueAt(fila, 9).toString();
							String codigoCliente = tblOrdenes.getValueAt(fila, 8).toString();
							String codigoPedido = tblOrdenes.getValueAt(fila, 1).toString();
							IPedidos tp = null;
							/*
							 * OLD
							 */
//							BeanPedidoHeader header = listaPedido.get(fila);
							/*
							 * new MARCELO MOYANO
							 */
							BeanPedidoHeader header = pedidoe.get(fila);
							String tipoDocumentoVenta = header.getStrClDocVentas();
							// DATOS CREDITICIOS
							SqlAgendaImpl agenda = new SqlAgendaImpl();
							
							
							SqlCabeceraHojaMaestraCreditoImpl sqlchmc = new SqlCabeceraHojaMaestraCreditoImpl();
							String codigoVendedor = Promesa.datose.get(0).getStrCodigo();
							CabeceraHojaMaestraCredito chmc = sqlchmc.obtenerCabeceraHojaMaestraCredito(codigoCliente, codigoVendedor);
							
							if(chmc != null){
								
								String limitCredi = chmc.getLimiteCredito().replace(".", "~");
								limitCredi = limitCredi.replace(",", ".");
								limitCredi = limitCredi.replace("~", ",");
								header.setTxtLimiteCredito(limitCredi);
								header.setTxtClaseRiesgo(chmc.getClaseRiesgo());
								header.setTxtDisponible(chmc.getCupoDisponible());
							} else{
								String[] valoresCrediticios = agenda.valoresAgenda(codigoCliente);
								if (valoresCrediticios != null && valoresCrediticios.length == 3) {
									header.setTxtLimiteCredito(valoresCrediticios[0]);
									header.setTxtClaseRiesgo(valoresCrediticios[1]);
									header.setTxtDisponible(valoresCrediticios[2]);
								}else {
									header.setTxtLimiteCredito("");
									header.setTxtClaseRiesgo("");
									header.setTxtDisponible("");
								}
							}
							
							BeanDato usuario = Promesa.datose.get(0);
							if (tipoDocumentoVenta.compareTo("ZP01") == 0) {
								// PEDIDO
								if (source.compareTo("0") == 0 && usuario.getStrModo().equals("1")) {
									SPedidos objSap = new SPedidos();
										if(sql.ExistePedido(codigoPedido) == 0){
											BeanPedido order = objSap.obtenerDetallePedido(header.getStrDocumentoVenta());
											if (order != null) {
												order.setCodigoPedido(header.getStrDocumentoVenta());
												order.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
												tp = new IEditarPedido(order, header.getStrCodCliente(),
												header.getStrCliente(), header.getTxtClaseRiesgo(),
												header.getTxtLimiteCredito(), header.getTxtDisponible(),
												order.getHeader().getStrCondicionPago(),
												"Edición de pedido " + header.getStrCodCliente() + "-" + header.getStrCliente(), "Pedido", "ZP01", source, singleton);
											}
									}else {
										Mensaje.mostrarWarning(Constante.MENSAJE_PEDIDO);
									}
								} else if (source.compareTo("1") == 0) {
									BeanPedido order = sql.obtenerPedido(header.getStrDocumentoVenta());
									order.setCodigoPedido(header.getStrDocumentoVenta());
									order.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
									order.getHeader().setStrEstadoPicking("0");
									tp = new IEditarPedido(order, header.getStrCodCliente(), header.getStrCliente(), header.getTxtClaseRiesgo(),
											header.getTxtLimiteCredito(), header.getTxtDisponible(), order.getHeader().getStrCondicionPago(),
											"Edición de pedido " + header.getStrCodCliente() + "-" + header.getStrCliente(), "Pedido", "ZP01", source, singleton);
								}else if(source.compareTo("2") == 0){
									/*
									 * 	Marcelo Moyano Editar un depido de SAP Guardado en SQLite
									 * 	Requerimiento: PRO-2013-0025
									 * Titulo: Modificar pedidos offline Grabados en SAP
									 */
										BeanPedido order = sql.obtenerPedido(header.getIdBD());
										order.setCodigoPedido(header.getStrDocumentoVenta());
										order.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
										order.getHeader().setStrEstadoPicking("0");
										tp = new IEditarPedido(order, header.getStrCodCliente(), header.getStrCliente(), header.getTxtClaseRiesgo(),
												header.getTxtLimiteCredito(), header.getTxtDisponible(), order.getHeader().getStrCondicionPago(),
												"Edición de pedido "+ header.getStrCodCliente() + "-" + header.getStrCliente(), "Pedido", "ZP01", source, singleton);
								}
							} else if (tipoDocumentoVenta.compareTo("ZP05") == 0) {
								// PROFORMA
								if (source.compareTo("0") == 0 && usuario.getStrModo().equals("1")) {
									SPedidos objSap = new SPedidos();
									BeanPedido order = objSap.obtenerDetallePedido(header.getStrDocumentoVenta());
									order.getHeader().setStrValorNeto(header.getStrValorNeto());
									if (order != null) {
										order.setCodigoPedido(header.getStrDocumentoVenta());
										order.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
										tp = new IEditarPedido(order, header.getStrCodCliente(), header.getStrCliente(),
												header.getTxtClaseRiesgo(), header.getTxtLimiteCredito(), header.getTxtDisponible(),
												order.getHeader().getStrCondicionPago(), "Edición de proforma " + header.getStrCodCliente() + "-" + header.getStrCliente(), "Proforma", "ZP05", source, singleton);
									}
								} else if (source.compareTo("1") == 0) {
									BeanPedido order = sql.obtenerPedido(header.getStrDocumentoVenta());
									order.setCodigoPedido(header.getStrDocumentoVenta());
									order.getHeader().setStrEstadoPicking("0");
									order.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
									tp = new IEditarPedido(order, header.getStrCodCliente(), header.getStrCliente(), header.getTxtClaseRiesgo(), header.getTxtLimiteCredito(),
											header.getTxtDisponible(), order.getHeader().getStrCondicionPago(), "Edición de proforma " + header.getStrCodCliente() + "-" + header.getStrCliente(), "Pedido", "ZP05", source, singleton);
								}else if (source.compareTo("2") == 0) {// Edicion de Proforma desde SAP y Local
									BeanPedido order = sql.obtenerPedidoSAPLocal(header.getStrDocumentoVenta());
									order.setCodigoPedido(header.getStrDocumentoVenta());
									order.getHeader().setStrEstadoPicking("0");
									order.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
									tp = new IEditarPedido(order, header.getStrCodCliente(), header.getStrCliente(), header.getTxtClaseRiesgo(), header.getTxtLimiteCredito(),
											header.getTxtDisponible(), order.getHeader().getStrCondicionPago(), "Edición de proforma " + header.getStrCodCliente() + "-" + header.getStrCliente(), "Pedido", "ZP05", source, singleton);
								}
							} else if (tipoDocumentoVenta.compareTo("ZO01") == 0) {
								// COTIZACIÓN
								// if (usuario.getStrModo().equals("1")) {
								// SPedidos objSap = new SPedidos();
								// } else {
								// SqlPedidoImpl sqlPedido = new
								// SqlPedidoImpl();
								// }
							} else /*if(Util.esPedidoZPXX(tipoDocumentoVenta) )*/{//PEDIDOS CAC ZPXX excepto de ZP01 Y ZP05
								if (source.compareTo("0") == 0 && usuario.getStrModo().equals("1")) {
									SPedidos objSap = new SPedidos();
									BeanPedido order = objSap.obtenerDetallePedido(header.getStrDocumentoVenta());
									order.getHeader().setStrValorNeto(header.getStrValorNeto());
									if (order != null) {
										order.setCodigoPedido(header.getStrDocumentoVenta());
										order.getHeader().setStrClDocVentas(header.getStrClDocVentas());//20160120_1030
										order.getHeader().setStrDocumentoVenta(header.getStrDocumentoVenta());
										tp = new IEditarPedido(order, header.getStrCodCliente(), header.getStrCliente(),
												header.getTxtClaseRiesgo(), header.getTxtLimiteCredito(), header.getTxtDisponible(),
												order.getHeader().getStrCondicionPago(), "Edición de proforma " + header.getStrCodCliente() + "-" + header.getStrCliente(), "Proforma", "ZP03", source, singleton);
									}
								}
							} if (tp != null) {
								try {
									Promesa.destokp.add(tp);
									tp.setVisible(true);
									tp.setMaximum(true);
									tp.moveToFront();
									tp.setSelected(true);
								} catch (Exception e) {
									if (!(e instanceof NullPointerException)) {
										Mensaje.mostrarAviso(e.getMessage());
									}
								}
							}
						}
					} catch (Exception e) {
						if (!(e instanceof NullPointerException)) {
							Mensaje.mostrarAviso(e.getMessage());
						}
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
	}

	private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {
		TablaAExcel ex = new TablaAExcel();
		try {
			ex.generarReporteOrdenes("pedidos.xls", tblOrdenes.getModel());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			EVENTO LANZADO CUANDO SE PULSA EL BOTON
	 * 			COTIZACIÓN A PEDIDO, REALIZANDO UN TRASPASO
	 * 			DE UNA COTIZACIÓN A PEDIDO.
	 * 
	 * @subject	PRO-2013-0034 - COPIA COTIZACION A PEDIDO.
	 * 
	 * @time	13/05/2013 - 09:11
	 */
	public void btnCotizacionAPedidoActionPerformed(java.awt.event.ActionEvent evt){
		BeanDato usuario = Promesa.datose.get(0);
		if(usuario.getStrModo().equals("1")){
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try{
						int filas[] = tblOrdenes.getSelectedRows();
						if (filas == null || filas.length == 0) {
							bloqueador.dispose();
							return;
						}
						String id = null;
						// validamos las filas
						for (int m : filas) {
							String strClseDocVentas = tblOrdenes.getValueAt(m, 7).toString();
							if (strClseDocVentas.compareTo("ZO01") != 0 && strClseDocVentas.compareTo("ZP05") != 0) {
								bloqueador.dispose();
								Mensaje.mostrarWarning("FILA "+ (m + 1) +": DOC. DE VENTA NO CORRESPONDE A COTIZACION O A PROFORMA.");
								return;
							}
						}
						for (int m : filas){
							id = tblOrdenes.getModel().getValueAt(m, 1).toString();
							SPedidos ps = new SPedidos();
							String codigoPedido = ps.PasarCotizacionAPedido(Util.completarCeros(10, id));
							if(codigoPedido.trim().length() > 0 ){
								Mensaje.mostrarAviso("SE CREO EL PEDIDO "+ codigoPedido +" A PARTIR DEL DOC. VENTA "+id+".\n");	
							}else{
								Mensaje.mostrarError("EL DOC. DE VENTA: "+ id + ", NO CREO EL PEDIDO.");
							}
						}
						buscaPedidos();
					}catch (Exception e){
						Util.mostrarExcepcion(e);
					}finally{
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}else {
			Mensaje.mostrarWarning("TIENE QUE TENER CONEXIÓN PARA PASAR UNA COTIZACIÓN A PEDIDO.");
		}
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnBuscar;
	private javax.swing.JButton btnExportar;
	private javax.swing.JButton btnSincronizar;
	private javax.swing.JButton btnCotizacionAPedido;//Marcelo Moyano 12/05/2013
	private com.toedter.calendar.JDateChooser dateFechaInicio;
	private com.toedter.calendar.JDateChooser datefechaFinal;
	private javax.swing.JLabel lblFechaFinal;
	private javax.swing.JLabel lblFechaInicio;
	private javax.swing.JPanel pnlBuscar;
	private javax.swing.JPanel pnlContenedor;
	private javax.swing.JPanel pnlOpciones;
	private javax.swing.JPanel pnlTabla;
	private javax.swing.JScrollPane scrollOrdenes;
	private javax.swing.JTable tblOrdenes;
	private javax.swing.JButton btnFlujoDocumentos;
	// End of variables declaration

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}