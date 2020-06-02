package com.promesa.internalFrame.pedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar.Separator;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.CabeceraHojaMaestraCredito;
import com.promesa.cobranzas.sql.impl.SqlCabeceraHojaMaestraCreditoImpl;
import com.promesa.internalFrame.cobranzas.ICobranzas;
import com.promesa.internalFrame.devoluciones.ISolicitudDevoluciones;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.pedidos.sql.impl.SqlPedidoImpl;
import com.promesa.sap.SPedidos;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Renderer;
import com.promesa.util.Util;
 
public class IPanelControl extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private javax.swing.JButton btnCobranza;
	private javax.swing.JButton btnCotizacion;
	private javax.swing.JButton btnProforma;
	private javax.swing.JButton btnRegistroSello;
	private javax.swing.JButton btnTomarPedidos;
	private javax.swing.JButton btnActualizar;
	private com.toedter.calendar.JCalendar dchCalendario;
	private javax.swing.JLabel lblAgenda;
	private javax.swing.JLabel lblClaseRiesgo;
	private javax.swing.JLabel lblDisponible;
	private javax.swing.JLabel lblLimiteCredito;
	private javax.swing.JToolBar mnuToolBar;
	private javax.swing.JPanel pnlCalendario;
	private javax.swing.JPanel pnlContenedor;
	private javax.swing.JPanel pnlDetalle;
	private javax.swing.JPanel pnlPedidos;
	private javax.swing.JPanel pnlTabla;
	private javax.swing.JScrollPane scrListado;
	private javax.swing.JTable tblAgenda;
	private javax.swing.JTextField txtClaseRiesgo;
	private javax.swing.JTextField txtDisponible;
	private javax.swing.JTextField txtLimiteCredito;
	private List<BeanAgenda> listaAgenda;
	private List<BeanAgenda> agendae;
	private BeanAgenda age = null;
	private SPedidos objSAP;
	private SqlAgendaImpl getAgenda = null;
	private String strIdVendedor;
	private String strFecha;
	private BeanTareaProgramada beanTareaProgramada;
	public String modo = "0";
	private JDesktopPane destokp = null;

	public IPanelControl(BeanTareaProgramada beanTareaProgramada, JDesktopPane destokp) {
		initComponents();
		try {
			this.beanTareaProgramada = beanTareaProgramada;
			this.destokp = destokp;
			this.getAgenda = new SqlAgendaImpl();
			BeanDato usuario = Promesa.datose.get(0);
			setModo(usuario.getStrModo().trim());
			strIdVendedor = usuario.getStrCodigo();
			SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
			strFecha = formato.format(new Date());
			((DefaultTableCellRenderer) tblAgenda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			tblAgenda.setRowHeight(Constante.ALTO_COLUMNAS);
			buscaAgenda();
			mnuToolBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
			tblAgenda.getTableHeader().setReorderingAllowed(false);
			((DefaultTableCellRenderer) tblAgenda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			
			/*
			 * 	BLOQUEO DE PEDIDOS
			 */
			bloqueoAutomaticoPedido();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * @autor	MARCELO MOYANO
	 * 
	 * 			METODO QUE BLOQUE EL BONTON DE
	 * 			PEDIDO POR MEDIO DE UN PARAMETRO
	 * 			"BLOCK_PED" DESDE LA BASE LOCAL.
	 */
	private void bloqueoAutomaticoPedido() {
		// TODO Auto-generated method stub
		SqlPedidoImpl sqlPedidoImpl = new SqlPedidoImpl();
		String strBloque = sqlPedidoImpl.obtenerBloqueoPedido("BLOCK_PED");
		if(Constante.BANDERA_ACTIVAR_PEDIDO.equals(strBloque)){
			btnTomarPedidos.setEnabled(false);
		}else {
			btnTomarPedidos.setEnabled(true);
		}
	}

	// M�todo que inicializa la interfaz gr�fica y asigna los eventos
	private void initComponents() {
		mnuToolBar = new javax.swing.JToolBar();
		btnCobranza = new javax.swing.JButton();
		btnTomarPedidos = new javax.swing.JButton();
		btnProforma = new javax.swing.JButton();
		btnCotizacion = new javax.swing.JButton();
		btnRegistroSello = new javax.swing.JButton();
		btnActualizar = new javax.swing.JButton();
		pnlContenedor = new javax.swing.JPanel();
		lblAgenda = new javax.swing.JLabel();
		pnlCalendario = new javax.swing.JPanel();
		dchCalendario = new com.toedter.calendar.JCalendar();
		pnlPedidos = new javax.swing.JPanel();
		pnlDetalle = new javax.swing.JPanel();
		lblClaseRiesgo = new javax.swing.JLabel();
		lblLimiteCredito = new javax.swing.JLabel();
		lblDisponible = new javax.swing.JLabel();
		txtClaseRiesgo = new javax.swing.JTextField();
		txtLimiteCredito = new javax.swing.JTextField();
		txtDisponible = new javax.swing.JTextField();
		pnlTabla = new javax.swing.JPanel();
		scrListado = new javax.swing.JScrollPane();
		tblAgenda = new javax.swing.JTable();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Panel Control - ProMovil");

		mnuToolBar.setFloatable(false);
		mnuToolBar.setRollover(true);

		btnCobranza.setText("Cobranza");
		btnCobranza.setFocusable(false);
		btnCobranza.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCobranza.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBar.add(btnCobranza);

		Separator separador1 = new javax.swing.JToolBar.Separator();
		mnuToolBar.add(separador1);

		btnTomarPedidos.setText("Tomar Pedidos");
		btnTomarPedidos.setFocusable(false);
		btnTomarPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnTomarPedidos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		
		mnuToolBar.add(btnTomarPedidos);

		Separator separador2 = new javax.swing.JToolBar.Separator();
		mnuToolBar.add(separador2);

		btnProforma.setText("Proforma");
		btnProforma.setFocusable(false);
		btnProforma.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnProforma.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

		mnuToolBar.add(btnProforma);

		Separator separador6 = new javax.swing.JToolBar.Separator();
		mnuToolBar.add(separador6);

		btnCotizacion.setText("Cotizaci�n");
		btnCotizacion.setFocusable(false);
		btnCotizacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCotizacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBar.add(btnCotizacion);

		Separator separador4 = new javax.swing.JToolBar.Separator();
		mnuToolBar.add(separador4);

		btnRegistroSello.setText("Registro Sello");
		btnRegistroSello.setFocusable(false);
		btnRegistroSello.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnRegistroSello.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBar.add(btnRegistroSello);

		// --- MD ---
		Separator separador7 = new javax.swing.JToolBar.Separator();
		mnuToolBar.add(separador7);

		btnActualizar.setText("Actualizar");
		btnActualizar.setFocusable(false);
		btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		mnuToolBar.add(btnActualizar);

		getContentPane().add(mnuToolBar, java.awt.BorderLayout.PAGE_START);

		pnlContenedor.setLayout(new java.awt.BorderLayout());

		lblAgenda.setBackground(new java.awt.Color(175, 200, 222));
		lblAgenda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		lblAgenda.setText("AGENDA");
		lblAgenda.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
		lblAgenda.setOpaque(true);
		pnlContenedor.add(lblAgenda, java.awt.BorderLayout.PAGE_START);

		pnlCalendario.add(dchCalendario);

		pnlContenedor.add(pnlCalendario, java.awt.BorderLayout.LINE_END);

		pnlDetalle.setBackground(new java.awt.Color(204, 204, 204));
		pnlDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		pnlDetalle.setLayout(new java.awt.GridLayout(2, 3, 5, 5));

		lblClaseRiesgo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblClaseRiesgo.setText("Clase Riesgo");
		pnlDetalle.add(lblClaseRiesgo);

		lblLimiteCredito.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblLimiteCredito.setText("L�mite de Cr�dito");
		pnlDetalle.add(lblLimiteCredito);

		lblDisponible.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblDisponible.setText("Disponible");
		pnlDetalle.add(lblDisponible);

		txtClaseRiesgo.setEditable(false);
		txtClaseRiesgo.setOpaque(false);
		txtClaseRiesgo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		pnlDetalle.add(txtClaseRiesgo);

		txtLimiteCredito.setEditable(false);
		txtLimiteCredito.setOpaque(false);
		txtLimiteCredito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		pnlDetalle.add(txtLimiteCredito);

		txtDisponible.setEditable(false);
		txtDisponible.setOpaque(false);
		txtDisponible.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		pnlDetalle.add(txtDisponible);

		pnlTabla.setLayout(new java.awt.BorderLayout());

		tblAgenda.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		tblAgenda.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrListado.setViewportView(tblAgenda);

		pnlTabla.add(scrListado, java.awt.BorderLayout.CENTER);

		javax.swing.GroupLayout pnlPedidosLayout = new javax.swing.GroupLayout(pnlPedidos);
		pnlPedidos.setLayout(pnlPedidosLayout);
		pnlPedidosLayout.setHorizontalGroup(pnlPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPedidosLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(pnlTabla,javax.swing.GroupLayout.Alignment.LEADING,javax.swing.GroupLayout.DEFAULT_SIZE,257,Short.MAX_VALUE)
				.addComponent(pnlDetalle,javax.swing.GroupLayout.Alignment.LEADING,javax.swing.GroupLayout.DEFAULT_SIZE,
										257,Short.MAX_VALUE)).addContainerGap()));
		pnlPedidosLayout.setVerticalGroup(pnlPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPedidosLayout.createSequentialGroup().addContainerGap()
				.addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										  javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		pnlContenedor.add(pnlPedidos, java.awt.BorderLayout.CENTER);

		getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

		// Customizado
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/ipanelcontrol.png")));
		txtClaseRiesgo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtLimiteCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtDisponible.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		btnTomarPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnTomarPedidos.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnTomarPedidos.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/toma_pedidos.png")));
		btnCobranza.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnCobranza.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCobranza.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/cobranza.png")));
		btnProforma.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnProforma.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnProforma.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/proforma.png")));
		btnCotizacion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnCotizacion.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCotizacion.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/cotizacion.png")));
		btnRegistroSello.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnRegistroSello.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnRegistroSello.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/registro_sello.png")));

		btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnActualizar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/update.png")));

		tblAgenda.addMouseListener(this);
		btnTomarPedidos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tomarPedido();
			}
		});

		btnProforma.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hacerProforma();
			}
		});

		btnCotizacion.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hacerCotizacion();
			}
		});

		btnCobranza.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				realizarCobranza();
			}
		});

		// --- MD ---
		btnRegistroSello.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registrarSello();
			}
		});

		btnActualizar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actualizar();
			}
		});

		this.dchCalendario.getDayChooser().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				if (evt.getPropertyName().compareTo("day") == 0) {
					actualizar();
				}
			}
		});
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));
		btnCobranza.setVisible(true);
		// --- MD ---
		pack();
	}// </editor-fold>

	protected void realizarCobranza() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					int fila = tblAgenda.getSelectedRow();
					if (fila > -1 && !agendae.isEmpty()) {
						BeanAgenda ba = agendae.get(fila);
						ICobranzas rc = new ICobranzas(ba, strIdVendedor);
						destokp.add(rc);
						rc.setVisible(true);
						rc.setMaximum(true);
					} else {
						JOptionPane.showMessageDialog(null, "Por favor seleccione un item de la Agenda.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					bloqueador.dispose();
				}

			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	// --- MD ---
	private void registrarSello() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					int fila = tblAgenda.getSelectedRow();
					if (fila > -1 && !agendae.isEmpty()) {
						BeanAgenda ba = agendae.get(fila);
						String limiteCredito = "";
						String claseRiesgo = "";
						String disponible = "";
						SqlAgendaImpl agenda = new SqlAgendaImpl();
						String[] valoresCrediticios = agenda.valoresAgenda(ba.getStrCodigoCliente());
						if (valoresCrediticios != null) {
							limiteCredito = valoresCrediticios[0];
							claseRiesgo = valoresCrediticios[1];
							disponible = valoresCrediticios[2];
						}
						ISolicitudDevoluciones sd = new ISolicitudDevoluciones(ba.getStrCodigoCliente(), ba.getStrNombreCliente(), claseRiesgo, limiteCredito, disponible, ba.getStrCondicionPago(), beanTareaProgramada, destokp);
						destokp.add(sd);
						sd.setVisible(true);
						sd.setMaximum(true);
					} else {
						JOptionPane.showMessageDialog(null, "Por favor seleccione un item de la Agenda.", "Mensaje", JOptionPane.WARNING_MESSAGE);
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

	private void actualizar() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					cambiarFechaAgenda();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void buscaAgenda() {
		agendae = new ArrayList<BeanAgenda>();
		BeanDato usuario = Promesa.datose.get(0);

		Util util = new Util();
		/*if (usuario.getStrModo().equals("1")) { // DESDE SAP
			try {
				objSAP = new SPedidos();
				listaAgenda = objSAP.clienteAgenda(strIdVendedor, strFecha);
			} catch (Exception e) {
				Mensaje.mostrarError(e.getMessage());
			}
		} else */{ // DESDE SQLITE
			BeanAgenda agenda = new BeanAgenda();
			agenda.setVENDOR_ID(strIdVendedor);
			agenda.setBEGDA(util.convierteFecha2(strFecha));
			getAgenda.setListaAgenda(agenda);
			listaAgenda = getAgenda.getListaAgenda();
		}
		if (listaAgenda != null) {
			for (BeanAgenda a : listaAgenda) {
				age = new BeanAgenda();
				age.setBEGDA(a.getBEGDA());
				age.setStrClaseRiesgo(a.getStrClaseRiesgo());
				age.setStrCodigoCliente(a.getStrCodigoCliente());
				age.setStrDireccionCliente(a.getStrDireccionCliente());
				age.setStrDisponible(a.getStrDisponible());
				age.setStrHora(a.getStrHora());
				age.setStrIdAgenda(a.getStrIdAgenda());
				age.setStrLimiteCredito(a.getStrLimiteCredito());
				age.setStrNombreCliente(a.getStrNombreCliente());
				age.setStrTelefonoCliente(a.getStrTelefonoCliente());
				age.setStrCondicionPago(a.getStrCondicionPago());
				age.setStrTipo(a.getStrTipo());
				try {
					agendae.add(age);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_PLANIFICACION);
				}
			}
		}
		if (agendae == null || agendae.size() == 0) {
			tablaVacia();
		} else {
			tablaLLena();
		}
		// Habilitar botones solo para tomar pedidos a partir de la fecha actual
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date fecha = sdf.parse(strFecha, new ParsePosition(0));
		boolean enable = true;
		if (fecha.before(new Date()) && strFecha.compareTo(sdf.format(new Date())) != 0) {
			enable = false;
		}
		mnuToolBar.setVisible(enable);
	}

	private void tomarPedido() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					int fila = tblAgenda.getSelectedRow();
					if (fila > -1 && !agendae.isEmpty()) {
						BeanAgenda ba = agendae.get(fila);
						ITomarPedidos tp = new ITomarPedidos( ba.getStrCodigoCliente(), ba.getStrNombreCliente(), ba.getStrClaseRiesgo(), ba.getStrLimiteCredito(), ba.getStrDisponible(),
								ba.getStrCondicionPago(), "Pedido " + ba.getStrCodigoCliente() + "-" + ba.getStrNombreCliente(), "");
						destokp.add(tp);
						tp.setVisible(true);
						tp.setMaximum(true);
					} else {
						JOptionPane.showMessageDialog(null, "Por favor seleccione un item de la Agenda.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void hacerProforma() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					int fila = tblAgenda.getSelectedRow();
					if (fila > -1 && !agendae.isEmpty()) {
						// IPedidos tomarPedidos = new IPedidos();
						BeanAgenda ba = agendae.get(fila);
//						
//						String limitCredi = ba.getStrLimiteCredito().replace(".", "~");
//						limitCredi = limitCredi.replace(",", ".");
//						limitCredi = limitCredi.replace("~", ",");
						
						
						SqlCabeceraHojaMaestraCreditoImpl sqlchmc = new SqlCabeceraHojaMaestraCreditoImpl();
						String codigoVendedor = Promesa.datose.get(0).getStrCodigo();
						CabeceraHojaMaestraCredito chmc = sqlchmc.obtenerCabeceraHojaMaestraCredito(ba.getStrCodigoCliente(), codigoVendedor);
						
						String limitCredi = "";
						String disponibilidad = "";
						String claseRiesgo = "";
						
						if(chmc != null){
							try {
								limitCredi = chmc.getLimiteCredito().replace(".", "~");
								limitCredi = limitCredi.replace(",", ".");
								limitCredi = limitCredi.replace("~", ",");
								claseRiesgo = chmc.getClaseRiesgo();
								disponibilidad = chmc.getCupoDisponible();
								disponibilidad = Util.formatoDigitoDecimal(disponibilidad);
							} catch (Exception e) {
								// TODO: handle exception
								limitCredi = ba.getStrLimiteCredito().replace(".", "~");
								limitCredi = limitCredi.replace(",", ".");
								limitCredi = limitCredi.replace("~", ",");
								claseRiesgo = ba.getStrClaseRiesgo();
								disponibilidad = ba.getStrDisponible();
							}
						}
						
						//IProforma tp = new IProforma(ba.getStrCodigoCliente(), ba.getStrNombreCliente(),claseRiesgo, limitCredi, disponibilidad,
							//	ba.getStrCondicionPago(), "Proforma " + ba.getStrCodigoCliente() + "-" + ba.getStrNombreCliente(), "");
						Promesa.datose.get(0).setAuxCliente(ba.getStrCodigoCliente());
						IProforma tp = new IProforma(ba,claseRiesgo, limitCredi, disponibilidad, "Proforma " + ba.getStrCodigoCliente() + "-" + ba.getStrNombreCliente(), "");
						
						Promesa.destokp.add(tp);
						tp.setVisible(true);
						tp.setMaximum(true);
					} else {
						JOptionPane.showMessageDialog(null, "Por favor seleccione un item de la Agenda.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					bloqueador.dispose();
				}

			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	private void hacerCotizacion() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					int fila = tblAgenda.getSelectedRow();
					if (fila > -1 && !agendae.isEmpty()) {
						BeanAgenda ba = agendae.get(fila);
						ICotizacion tp = new ICotizacion(ba.getStrCodigoCliente(), ba.getStrNombreCliente(), ba.getStrClaseRiesgo(), ba.getStrLimiteCredito(), ba.getStrDisponible(),
								ba.getStrCondicionPago(), "Cotizaci�n " + ba.getStrCodigoCliente() + "-" + ba.getStrNombreCliente(), "");
						Promesa.destokp.add(tp);
						tp.setVisible(true);
						tp.setMaximum(true);
					}
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	public void tablaVacia() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String Columnas[] = { "HORA", "TIPO", "C�D. CLIENTE", "CLIENTE", "DIRECCI�N", "TEL�FONO" };
				DefaultTableModel tblTablaModel = new DefaultTableModel(
						new Object[][] { { "08:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "09:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "10:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "11:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "12:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "13:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "14:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "15:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "16:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "17:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" },
								{ "18:00", "", "", "", "", "" },
								{ "", "", "", "", "", "" } }, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblAgenda.setModel(tblTablaModel);
				txtClaseRiesgo.setText("");
				txtLimiteCredito.setText("");
				txtDisponible.setText("");
				tblAgenda.updateUI();
			}

		});
	}

	public void tablaLLena() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				String Columnas[] = { "HORA", "TIPO", "C�D. CLIENTE", "CLIENTE", "DIRECCI�N", "TEL�FONO" };
				DefaultTableModel tblTablaModel = new DefaultTableModel( new Object[][] {}, Columnas) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				tblTablaModel.setNumRows(agendae.size());
				for (int i = 0; i < agendae.size(); i++) {
					tblTablaModel.setValueAt(agendae.get(i).getStrHora(), i, 0);
					String tipo = agendae.get(i).getStrTipo();
					JLabel lblTipo = new JLabel();
					lblTipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
					lblTipo.setOpaque(true);
					if (tipo.compareTo("01") == 0) {
						lblTipo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/01.png")));
					} else {
						lblTipo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/02.png")));
					}
					tblTablaModel.setValueAt(lblTipo, i, 1);
					tblTablaModel.setValueAt(agendae.get(i).getStrCodigoCliente(), i, 2);
					tblTablaModel.setValueAt(agendae.get(i).getStrNombreCliente(), i, 3);
					tblTablaModel.setValueAt(agendae.get(i).getStrDireccionCliente(), i, 4);
					tblTablaModel.setValueAt(agendae.get(i).getStrTelefonoCliente(), i, 5);
				}
				tblAgenda.setDefaultRenderer(Object.class, new Renderer());
				tblAgenda.setModel(tblTablaModel);
				tblAgenda.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
				tblAgenda.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getCenterCell());
				tblAgenda.getColumnModel().getColumn(3).setCellRenderer(new Renderer().getCenterCell());
				tblAgenda.getColumnModel().getColumn(4).setCellRenderer(new Renderer().getCenterCell());
				tblAgenda.getColumnModel().getColumn(5).setCellRenderer(new Renderer().getCenterCell());
				new Renderer().setSizeColumn(tblAgenda.getColumnModel(), 0, 50);
				new Renderer().setSizeColumn(tblAgenda.getColumnModel(), 1, 50);
				new Renderer().setSizeColumn(tblAgenda.getColumnModel(), 2, 100);
				new Renderer().setSizeColumn(tblAgenda.getColumnModel(), 3, 230);
				new Renderer().setSizeColumn(tblAgenda.getColumnModel(), 4, 250);
				new Renderer().setSizeColumn(tblAgenda.getColumnModel(), 5, 120);
				tblAgenda.setModel(tblTablaModel);
				txtClaseRiesgo.setText("");
				txtLimiteCredito.setText("");
				txtDisponible.setText("");
				tblAgenda.updateUI();
			}
		});
	}

	private void cambiarFechaAgenda() {
		String a�o = Integer.toString(dchCalendario.getCalendar().get(java.util.Calendar.YEAR));
		int mm = dchCalendario.getCalendar().get(java.util.Calendar.MONTH) + 1;
		String mes = mm < 10 ? "0" + mm : "" + mm;
		int dia = dchCalendario.getCalendar().get(java.util.Calendar.DATE);
		if (dia < 10) {
			strFecha = a�o + "" + mes + "0" + dia;
		} else {
			strFecha = a�o + "" + mes + "" + dia;
		}
		buscaAgenda();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int fila = tblAgenda.getSelectedRow();
		if (fila > -1) {
			if (!agendae.isEmpty()) {
				String claseRiesgo = agendae.get(fila).getStrClaseRiesgo();
				String limiteCredito = agendae.get(fila).getStrLimiteCredito();
				String disponible = agendae.get(fila).getStrDisponible();
				txtClaseRiesgo.setText(claseRiesgo);
				txtLimiteCredito.setText(limiteCredito);
				txtDisponible.setText(disponible);
			}
		}
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
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