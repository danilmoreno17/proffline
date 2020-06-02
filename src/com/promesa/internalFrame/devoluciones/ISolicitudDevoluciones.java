package com.promesa.internalFrame.devoluciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.promesa.bean.BeanDato;
import com.promesa.devoluciones.bean.BeanDevolucion;
import com.promesa.main.Promesa;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.sap.SDevoluciones;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.Cmd;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Renderer;
import com.promesa.util.Util;

public class ISolicitudDevoluciones extends javax.swing.JInternalFrame implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	List<BeanDevolucion> listaDevolucion;
	List<BeanDevolucion> devolucione;
	BeanDevolucion devolucion = null;
	SDevoluciones objSAP;

	// EN VEREMOS ...
	private String codigoVendedor;
	private String codigoCliente = Constante.VACIO;
	private String nombreCliente = Constante.VACIO;
	private String claseRiesgo = Constante.VACIO;
	private String limiteCredito = Constante.VACIO;
	private String disponible = Constante.VACIO;
	private String condicionPago = Constante.VACIO;
	private String codigoPedidoDevolucion = Constante.VACIO;
	private String nombreResponsable = Constante.VACIO;
	private String fechaFactura = Constante.VACIO;
	static List<BeanDato> datose;
	public String modo = "0";
	private Cmd objC;
	@SuppressWarnings("unused")
	private BeanTareaProgramada beanTareaProgramada;
	private JDesktopPane destokp = null;

	@SuppressWarnings("static-access")
	public ISolicitudDevoluciones(BeanTareaProgramada beanTareaProgramada, JDesktopPane destokp) {
		this.beanTareaProgramada = beanTareaProgramada;
		this.destokp = destokp;
		initComponents();
		setTitle("Casos Abiertos Devolución");
		this.datose = beanTareaProgramada.getDatose();
		BeanDato usuario = ((BeanDato) datose.get(0));
		codigoVendedor = usuario.getStrCodigo();
		lblResultado.setVisible(false);
		buscar();
	}

	@SuppressWarnings("static-access")
	public ISolicitudDevoluciones(String codigoCliente, String nombreCliente, String claseRiesgo, String limiteCredito, String disponible, String condicionPago, BeanTareaProgramada beanTareaProgramada, JDesktopPane destokp) {
		this.beanTareaProgramada = beanTareaProgramada;
		this.destokp = destokp;
		this.codigoCliente = codigoCliente;
		this.nombreCliente = nombreCliente;
		this.claseRiesgo = claseRiesgo;
		this.limiteCredito = limiteCredito;
		this.disponible = disponible;
		this.condicionPago = condicionPago;
		initComponents();
		setTitle("Registro de Sello en Pedidos de Devolución: [" + codigoCliente + "] - [" + nombreCliente + "]");
		this.setFrameIcon(new ImageIcon(this.getClass().getResource( "/imagenes/idevolucion.gif")));
		this.datose = beanTareaProgramada.getDatose();
		BeanDato usuario = ((BeanDato) datose.get(0));
		codigoVendedor = usuario.getStrCodigo();
		lblResultado.setVisible(false);
		buscar();
	}

	private void initComponents() {
		mnuToolBar = new javax.swing.JToolBar();

		pnlContenedor = new javax.swing.JPanel();
		GroupLayout pnlContenedorLayout = new GroupLayout((JComponent) pnlContenedor);
		pnlContenedor.setLayout(pnlContenedorLayout);
		lblMsg = new javax.swing.JLabel();
		pnlResultado = new javax.swing.JPanel();
		lblResultado = new javax.swing.JLabel();
		scrResultado = new javax.swing.JScrollPane();
		tblResultado = new javax.swing.JTable();

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);

		mnuToolBar.setFloatable(false);
		mnuToolBar.setRollover(true);

		if (codigoCliente.equals(Constante.VACIO)) {
			btnIngRegSelloDesdeMenuDevoluciones = new javax.swing.JButton();
			btnIngRegSelloDesdeMenuDevoluciones.setText("Ingresar Registro de Sello");
			btnIngRegSelloDesdeMenuDevoluciones.setFocusable(false);
			btnIngRegSelloDesdeMenuDevoluciones.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			btnIngRegSelloDesdeMenuDevoluciones.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
			btnIngRegSelloDesdeMenuDevoluciones.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/guardar_32.png")));
			btnIngRegSelloDesdeMenuDevoluciones.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					btnIngRegSelloDesdeMenuDevolucionesActionPerformed(evt);
				}
			});
			mnuToolBar.add(btnIngRegSelloDesdeMenuDevoluciones);
		} else {
			btnIngRegSelloDesdePanelDeControl = new javax.swing.JButton();
			btnIngRegSelloDesdePanelDeControl.setText("Ingresar Registro de Sello");
			btnIngRegSelloDesdePanelDeControl.setFocusable(false);
			btnIngRegSelloDesdePanelDeControl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			btnIngRegSelloDesdePanelDeControl.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
			btnIngRegSelloDesdePanelDeControl.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/registro_sello.png")));
			btnIngRegSelloDesdePanelDeControl.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					btnIngRegSelloDesdePanelDeControlActionPerformed(evt);
				}
			});
			mnuToolBar.add(btnIngRegSelloDesdePanelDeControl);
		}

		mnuToolBar.add(new javax.swing.JToolBar.Separator());

		btnActualizar = new javax.swing.JButton();
		btnActualizar.setText("Actualizar");
		btnActualizar.setFocusable(false);
		btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		btnActualizar.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagenes/update.png")));
		btnActualizar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnActualizarActionPerformed(evt);
			}
		});
		mnuToolBar.add(btnActualizar);

		getContentPane().add(mnuToolBar, java.awt.BorderLayout.PAGE_START);

		lblMsg.setText("<html><b>Listado Casos Abiertos Devolución</b></html>");

		pnlResultado.setLayout(new java.awt.BorderLayout());
		pnlContenedorLayout.setHorizontalGroup(pnlContenedorLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlContenedorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(pnlContenedorLayout.createSequentialGroup().addComponent(lblMsg)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 6,GroupLayout.PREFERRED_SIZE))
				.addComponent(pnlResultado, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
				.addContainerGap());
		pnlContenedorLayout.setVerticalGroup(pnlContenedorLayout.createSequentialGroup().addContainerGap()
				.addComponent(lblMsg).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(pnlResultado, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE).addContainerGap());

		lblResultado.setBackground(new java.awt.Color(175, 200, 222));
		lblResultado.setFont(new java.awt.Font("Tahoma", 1, 18));
		lblResultado.setText("RESULTADO DE BÚSQUEDA");
		lblResultado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
		lblResultado.setOpaque(true);
		lblResultado.setPreferredSize(new java.awt.Dimension(115, 24));
		pnlResultado.add(lblResultado, java.awt.BorderLayout.PAGE_START);

		String Columnas[] = { "# CASO", "FECHA CASO", "", "CLIENTE", "DIRECCIÓN", "TELÉFONO" };
		DefaultTableModel tblTablaModel = new DefaultTableModel(new Object[][] {}, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblResultado.setModel(tblTablaModel);
		tblResultado.getTableHeader().setReorderingAllowed(false);
		((DefaultTableCellRenderer) tblResultado.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);// JLabel.CENTER
		tblResultado.setRowHeight(Constante.ALTO_COLUMNAS);
		scrResultado.setViewportView(tblResultado);
		pnlResultado.add(scrResultado, java.awt.BorderLayout.CENTER);
		getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);
		mnuToolBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pack();
	}

	/* MÉTODO QUE ELIMINA CEROS */
	private String eCeros(String codigo) {
		String nCodigo = "";
		int pos = 0;
		for (int i = 0; i < codigo.length(); i++) {
			if (codigo.charAt(i) != '0') {
				pos = i;
				break;
			}
		}
		for (int j = pos; j < codigo.length(); j++) {
			nCodigo += "" + codigo.charAt(j);
		}
		return nCodigo;
	}

	/* MÉTODO QUE COMPLETA CEROS */
	private String cCeros(String codigo) {
		String nCodigo = codigo;
		for (int i = codigo.length(); i < 10; i++) {
			nCodigo = "0" + nCodigo;
		}
		return nCodigo;
	}

	public void tablaLLena() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DefaultTableModel tblTablaModel = (DefaultTableModel) tblResultado.getModel();
				tblResultado.getTableHeader().setReorderingAllowed(false);
				((DefaultTableCellRenderer) tblResultado.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
				tblTablaModel.setNumRows(devolucione.size());
				for (int i = 0; i < devolucione.size(); i++) {
					tblTablaModel.setValueAt(eCeros(devolucione.get(i).getStrDocVta()), i, 0);
					tblTablaModel.setValueAt(devolucione.get(i).getStrFecReg(), i, 1);
					JLabel lblTipo = new JLabel();
					lblTipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
					lblTipo.setOpaque(true);
					if (devolucione.get(i).getStrTipVis().equalsIgnoreCase("@5C@")) {
						lblTipo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/rojo.gif")));
					} else {
						lblTipo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/reloj.gif")));
					}
					tblTablaModel.setValueAt(lblTipo, i, 2);
					tblTablaModel.setValueAt(devolucione.get(i).getStrNomCli(), i, 3);
					tblTablaModel.setValueAt(devolucione.get(i).getStrDirCli(), i, 4);
					tblTablaModel.setValueAt(devolucione.get(i).getStrTelCli(), i, 5);
				}
				tblResultado.setDefaultRenderer(Object.class, new Renderer());
				tblResultado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tblResultado.setRowHeight(Constante.ALTO_COLUMNAS);

				for (int i = 0; i < 6; i++) {
					if (i != 2) {
						tblResultado.getColumnModel().getColumn(i).setCellRenderer(new Renderer().getCenterCell());
					}
				}
				tblResultado.updateUI();
				Util.setAnchoColumnasTablaResultado(tblResultado);
			}
		});
	}

	private void ingresarDesdeMenuDevoluciones() {
		if (tblResultado.getSelectedRow() > -1) {
			ingresaRegistroSelloDesdeMenuDevoluciones();
//			final DLocker bloqueador = new DLocker();
//			Thread hilo = new Thread() {
//				public void run() {
//					try {
//						ingresaRegistroSelloDesdeMenuDevoluciones();
//					} finally {
//						bloqueador.dispose();
//					}
//				}
//			};
//			hilo.start();
//			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarAviso(Constante.MENSAJE_SELECCION_INGRESA_SELLO_PEDIDO_DEVOLUCION);
		}
	}

	private void ingresarDesdePanelDeControl() {
		if (tblResultado.getSelectedRow() > -1) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						ingresaRegistroSelloDesdePanelDeControl();
					} finally {
						bloqueador.dispose();
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarAviso(Constante.MENSAJE_SELECCION_INGRESA_SELLO_PEDIDO_DEVOLUCION);
		}
	}

	private void ingresaRegistroSelloDesdeMenuDevoluciones() {
//		objC = new Cmd();
//		int tiempo = Integer.parseInt(objC.disponibilidadSAP().trim());
//		if (tiempo > 0 && tiempo <= Promesa.MAX_DELAY) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					int fila;
					try {
						fila = tblResultado.getSelectedRow();
						objSAP = new SDevoluciones();
						List<String[]> p = new ArrayList<String[]>();
						try {
							p = objSAP.pedidosPorCaso(cCeros(tblResultado.getValueAt(fila, 0).toString().trim()));
						} catch (Exception e) {
							// TODO: handle exception
							bloqueador.dispose();
							return;
						}
						if (p != null) {
							codigoCliente = Integer.parseInt(devolucione.get(fila).getStrCodCli())+ "";
							nombreCliente = devolucione.get(fila).getStrNomCli();
							codigoPedidoDevolucion = devolucione.get(fila).getStrDocVta();
							fechaFactura = devolucione.get(fila).getStrFecReg();
							SqlAgendaImpl agenda = new SqlAgendaImpl();
							String[] valoresCrediticios = agenda.valoresAgenda(codigoCliente);
							if (valoresCrediticios != null) {
								limiteCredito = valoresCrediticios[0];
								claseRiesgo = valoresCrediticios[1];
								disponible = valoresCrediticios[2];
							}
							try {
								condicionPago = p.get(5)[16];
								nombreResponsable = p.get(1)[16];
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
							IRegistroDevoluciones rg = new
							IRegistroDevoluciones(codigoCliente, nombreCliente, claseRiesgo, limiteCredito,
							disponible, condicionPago, codigoPedidoDevolucion, nombreResponsable, fechaFactura,p);
							rg.setVisible(true);
							destokp.add(rg);
							rg.setMaximum(true);
						} else {
							bloqueador.dispose();
						}
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
					} finally {
						bloqueador.dispose();
					}
					bloqueador.dispose();
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
//		}
	}

	@SuppressWarnings("static-access")
	private void ingresaRegistroSelloDesdePanelDeControl() {
		objC = new Cmd();
		int tiempo = Integer.parseInt(objC.disponibilidadSAP().trim());
		if (tiempo > 0 && tiempo <= Promesa.MAX_DELAY) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					int fila;
					try {
						fila = tblResultado.getSelectedRow();
						objSAP = new SDevoluciones();
						List<String[]> p = new ArrayList<String[]>();
						p = objSAP.pedidosPorCaso(cCeros(tblResultado.getValueAt(fila, 0).toString().trim()));
						if (p != null) {
							codigoPedidoDevolucion = devolucione.get(fila).getStrDocVta();
							IRegistroDevoluciones rg = new IRegistroDevoluciones(codigoCliente, nombreCliente, claseRiesgo, limiteCredito, disponible, condicionPago, codigoPedidoDevolucion, nombreResponsable, fechaFactura,p);
							rg.setVisible(true);
							destokp.add(rg);
							rg.setMaximum(true);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					bloqueador.dispose();
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
	}

	private void buscar() {
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					//if (codigoCliente.equals(Constante.VACIO))
						buscaSolicitudesDevoluciones();
					//else
					//	buscaSolicitudesDevolucionesXCliente();
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}

	@SuppressWarnings("static-access")
	private void buscaSolicitudesDevoluciones() {
		devolucione = new ArrayList<BeanDevolucion>();
		BeanDevolucion devolucion;
		objC = new Cmd();
		int tiempo = Integer.parseInt(objC.disponibilidadSAP().trim());
		if (tiempo > 0 && tiempo <= Promesa.MAX_DELAY) {
			try {
				objSAP = new SDevoluciones();
				listaDevolucion = objSAP.obtenerSolicitudes(codigoVendedor, Constante.VACIO);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		}
		if (listaDevolucion != null) {
			for (BeanDevolucion d : listaDevolucion) {
				devolucion = new BeanDevolucion();
				devolucion.setStrDocVta(d.getStrDocVta());
				devolucion.setStrFecReg(d.getStrFecReg());
				devolucion.setStrCodCli(d.getStrCodCli());
				devolucion.setStrNomCli(d.getStrNomCli());
				devolucion.setStrDirCli(d.getStrDirCli());
				devolucion.setStrTelCli(d.getStrTelCli());
				devolucion.setStrFecVis(d.getStrFecVis());
				devolucion.setStrHorVis(d.getStrHorVis());
				devolucion.setStrTipVis(d.getStrTipVis());
				devolucion.setStrCodVis(d.getStrCodVis());
				try {
					devolucione.add(devolucion);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		tablaLLena();
	}

	@SuppressWarnings({ "static-access", "unused" })
	private void buscaSolicitudesDevolucionesXCliente() {
		devolucione = new ArrayList<BeanDevolucion>();
		BeanDevolucion devolucion;
		objC = new Cmd();
		int tiempo = Integer.parseInt(objC.disponibilidadSAP().trim());
		if (tiempo > 0 && tiempo <= Promesa.MAX_DELAY) {
			try {
				objSAP = new SDevoluciones();
				listaDevolucion = objSAP.obtenerSolicitudes(codigoVendedor, codigoCliente);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		}
		if (listaDevolucion != null) {
			for (BeanDevolucion d : listaDevolucion) {
				devolucion = new BeanDevolucion();
				devolucion.setStrDocVta(d.getStrDocVta());
				devolucion.setStrFecReg(d.getStrFecReg());
				devolucion.setStrCodCli(d.getStrCodCli());
				devolucion.setStrNomCli(d.getStrNomCli());
				devolucion.setStrDirCli(d.getStrDirCli());
				devolucion.setStrTelCli(d.getStrTelCli());
				devolucion.setStrFecVis(d.getStrFecVis());
				devolucion.setStrHorVis(d.getStrHorVis());
				devolucion.setStrTipVis(d.getStrTipVis());
				devolucion.setStrCodVis(d.getStrCodVis());
				try {
					devolucione.add(devolucion);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		tablaLLena();
	}

	private javax.swing.JButton btnIngRegSelloDesdeMenuDevoluciones;
	private javax.swing.JLabel lblMsg;
	private javax.swing.JButton btnIngRegSelloDesdePanelDeControl;
	private javax.swing.JButton btnActualizar;
	private javax.swing.JLabel lblResultado;
	private javax.swing.JToolBar mnuToolBar;
	private javax.swing.JPanel pnlContenedor;
	private javax.swing.JPanel pnlResultado;
	private javax.swing.JScrollPane scrResultado;
	private javax.swing.JTable tblResultado;

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	private void btnIngRegSelloDesdeMenuDevolucionesActionPerformed(ActionEvent evt) {
		ingresarDesdeMenuDevoluciones();
	}

	private void btnIngRegSelloDesdePanelDeControlActionPerformed(ActionEvent evt) {
		ingresarDesdePanelDeControl();
	}

	private void btnActualizarActionPerformed(ActionEvent evt) {
		buscar();
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