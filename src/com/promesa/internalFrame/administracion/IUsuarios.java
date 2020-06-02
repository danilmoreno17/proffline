package com.promesa.internalFrame.administracion;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.sql.SqlUsuarioRol;
import com.promesa.administracion.sql.impl.SqlUsuarioImpl;
import com.promesa.administracion.sql.impl.SqlUsuarioRolImpl;
import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.main.Promesa;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Mensaje;
import com.promesa.util.Renderer;
import com.promesa.util.Util;
import com.promesa.sap.*;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.internalFrame.*;

public class IUsuarios extends JInternalFrame implements ActionListener, MouseListener, KeyListener {
	
	private static final long serialVersionUID = 1L;

	JToolBar mnuToolBar;
	JButton btnRol;
	JButton btnVista;
	JButton btnUsuario;
	JButton btnDispositivo;
	JButton btnBuscar;
	JButton btnNuevo;
	JButton btnBorrar;
	JSeparator separador;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JTable tblUsuario;
	DefaultTableModel modeloTabla;
	JScrollPane scrUsuario;
	JPanel pnlUsuario;
	private JLabel lblCC;
	private JLabel lblCA;
	private JLabel lblE;
	SAdministracion objSAP;
	List<BeanUsuario> usuariose;
	List<BeanRolUsuario> rolesusuarioe;
	static List<BeanDato> datose;
	@SuppressWarnings("unused")
	private BeanUsuario usuario = null;
	private List<BeanUsuario> listaUsuario = null;
	private SqlUsuario getUsuario = null;
	@SuppressWarnings("unused")
	private SqlUsuarioRol getUsuarioRol = null;
	private BeanTareaProgramada beanTareaProgramada;

	public IUsuarios(BeanTareaProgramada beanTareaProgramada) {

		super("Usuarios", true, true, true, true);
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/iusuarios.png")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); // 1306, 640
		this.setBounds(0, 0, 606, 640); // 0, 0, 1306, 640
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar, BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 1366, 10); // 0, 39, 1318, 10

		datose = beanTareaProgramada.getDatose();
		usuario = new BeanUsuario();
		getUsuario = new SqlUsuarioImpl();
		getUsuarioRol = new SqlUsuarioRolImpl();

		btnRol = new JButton();
		mnuToolBar.add(btnRol);
		btnRol.setText("Roles");
		btnRol.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/roles.png")));
		btnRol.addActionListener(this);
		btnRol.setToolTipText("Roles");
		btnRol.setPreferredSize(new java.awt.Dimension(93, 29));
		btnRol.setBackground(new java.awt.Color(0, 128, 64));
		btnRol.setOpaque(false);
		btnRol.setBorder(null);
		btnRol.setFocusable(false);

		btnVista = new JButton();
		mnuToolBar.add(btnVista);
		btnVista.setText("Vistas");
		btnVista.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/vistas.gif")));
		btnVista.addActionListener(this);
		btnVista.setToolTipText("Vistas");
		btnVista.setPreferredSize(new java.awt.Dimension(100, 29));
		btnVista.setBackground(new java.awt.Color(0, 128, 64));
		btnVista.setOpaque(false);
		btnVista.setBorder(null);
		btnVista.setFocusable(false);

		btnUsuario = new JButton();
		mnuToolBar.add(btnUsuario);
		btnUsuario.setText("Usuarios");
		btnUsuario.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/usuarios.png")));
		btnUsuario.addActionListener(this);
		btnUsuario.setToolTipText("Usuarios");
		btnUsuario.setPreferredSize(new java.awt.Dimension(100, 29));
		btnUsuario.setBackground(new java.awt.Color(0, 128, 64));
		btnUsuario.setEnabled(false);
		btnUsuario.setOpaque(false);
		btnUsuario.setBorder(null);
		btnUsuario.setFocusable(false);

		btnDispositivo = new JButton();
		mnuToolBar.add(btnDispositivo);
		btnDispositivo.setText("Dispositivos");
		btnDispositivo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/dispositivos.png")));
		btnDispositivo.addActionListener(this);
		btnDispositivo.setToolTipText("Dispositivos");
		btnDispositivo.setPreferredSize(new java.awt.Dimension(100, 29));
		btnDispositivo.setBackground(new java.awt.Color(0, 128, 64));
		btnDispositivo.setOpaque(false);
		btnDispositivo.setBorder(null);
		btnDispositivo.setFocusable(false);

		pnlUsuario = new JPanel();
		getContentPane().add(pnlUsuario);
		pnlUsuario.setBounds(0, 48, 790, 295); // 0, 48, 1119, 273
		pnlUsuario.setLayout(null);
		pnlUsuario.setBorder(BorderFactory.createTitledBorder("Buscar Usuario"));
		{
			lblUsuario = new JLabel();
			pnlUsuario.add(lblUsuario);
			lblUsuario.setText("Usuario:");
			lblUsuario.setBounds(12, 32, 119, 17);
			lblUsuario.setFont(new java.awt.Font("Arial", 1, 11));
		}
		{
			txtUsuario = new JTextField();
			pnlUsuario.add(txtUsuario);
			txtUsuario.setBounds(70, 30, 147, 24);
			txtUsuario.setBackground(new java.awt.Color(255, 255, 255));
			txtUsuario.setToolTipText("Usuario");
			txtUsuario.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						if (!txtUsuario.getText().trim().equals(""))
							buscaUsuario();
					}
				}
			});
		}
		{
			btnBuscar = new JButton();
			pnlUsuario.add(btnBuscar);
			btnBuscar.setText("Buscar");
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/buscar.gif")));
			btnBuscar.addActionListener(this);
			btnBuscar.setToolTipText("Buscar");
			btnBuscar.setBackground(new java.awt.Color(0, 128, 64));
			btnBuscar.setBounds(230, 30, 100, 20); // horizontal, vertical, width, high
			btnBuscar.setOpaque(false);
			btnBuscar.setFocusable(false);
		}
		{
			btnNuevo = new JButton();
			pnlUsuario.add(btnNuevo);
			btnNuevo.setText("Nuevo");
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/nuevo.png")));
			btnNuevo.addActionListener(this);
			btnNuevo.setToolTipText("Nuevo");
			btnNuevo.setBackground(new java.awt.Color(0, 128, 64));
			btnNuevo.setBounds(330, 30, 100, 20); // horizontal, vertical,  width, high
			btnNuevo.setOpaque(false);
			btnNuevo.setFocusable(false);
		}
		{
			btnBorrar = new JButton();
			pnlUsuario.add(btnBorrar);
			btnBorrar.setText("Borrar");
			btnBorrar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/borrar.png")));
			btnBorrar.addActionListener(this);
			btnBorrar.setToolTipText("Borrar");
			btnBorrar.setBackground(new java.awt.Color(0, 128, 64));
			btnBorrar.setBounds(430, 30, 100, 20); // horizontal, vertical, width, high
			btnBorrar.setOpaque(false);
			btnBorrar.setFocusable(false);
		}
		{
			lblE = new JLabel();
			pnlUsuario.add(lblE);
			lblE.setText("E: Estado del Usuario");
			lblE.setBounds(57, 260, 119, 17);
			lblE.setFont(new java.awt.Font("Arial", 1, 11));
		}
		scrUsuario = new JScrollPane();
		pnlUsuario.add(scrUsuario);
		scrUsuario.setBounds(50, 65, 680, 180); // 150, 65, 800, 180
		scrUsuario.setToolTipText("Relación de Usuarios");

		tablaVacia();
	}

	public class MakeEnterDoAction extends KeyAdapter {
		public void keyPressed(KeyEvent ke) {
			if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
				Object src = ke.getSource();
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new ActionEvent(src, ActionEvent.ACTION_PERFORMED, "Enter"));
			}
		}
	}

	/* MÉTODO QUE MUESTRA UNA TABLA VACÍA */
	public void tablaVacia() {
		String Columnas[] = { "E", "Usuario", "Nombre Completo" };
		modeloTabla = new DefaultTableModel(null, Columnas);
		TableModel tblTablaModel = new DefaultTableModel(new Object[][] {
				{ "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" },
				{ "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" },
				{ "", "", "" } }, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblUsuario = new JTable();
		tblUsuario.setModel(tblTablaModel);
		tblUsuario.addKeyListener(this);
		tblUsuario.addMouseListener(this);
		scrUsuario.setViewportView(tblUsuario);
		pnlUsuario.add(scrUsuario);
		new Renderer().setSizeColumn(tblUsuario.getColumnModel(), 0, 20);
		new Renderer().setSizeColumn(tblUsuario.getColumnModel(), 1, 280);
		new Renderer().setSizeColumn(tblUsuario.getColumnModel(), 2, 380);
	}

	/* MÉTODO QUE MUESTRA UNA TABLA LLENA */
	public void tablaLlena() {
		String Columnas[] = { "E", "Usuario", "Nombre Completo" };
		modeloTabla = new DefaultTableModel(null, Columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		if (usuariose.size() <= 9) {
			modeloTabla.setNumRows(9);
		} else {
			modeloTabla.setNumRows(usuariose.size());
		}
		for (int i = 0; i < usuariose.size(); i++) {
			for (int j = 0; j < 1; j++) {
				if (((BeanUsuario) usuariose.get(i)).getStrBloqueado().equals("") || ((BeanUsuario) usuariose.get(i)).getStrBloqueado().equals("0")) {
					lblCA = new JLabel();
					lblCA.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/candado_abierto.png")));
					modeloTabla.setValueAt(lblCA, i, j);
				} else {
					lblCC = new JLabel();
					lblCC.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/candado_cerrado.png")));
					modeloTabla.setValueAt(lblCC, i, j);
				}
				modeloTabla.setValueAt(((BeanUsuario) usuariose.get(i)).getStrNomUsuario(), i, j + 1);
				modeloTabla.setValueAt(((BeanUsuario) usuariose.get(i)).getStrNomApe(), i, j + 2);
			}
		}
		tblUsuario = new JTable();
		tblUsuario.setDefaultRenderer(Object.class, new Renderer());
		tblUsuario.setModel(modeloTabla);
		tblUsuario.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getLeftCell());
		tblUsuario.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getLeftCell());
		scrUsuario.setViewportView(tblUsuario);
		tblUsuario.addMouseListener(this);
		new Renderer().setSizeColumn(tblUsuario.getColumnModel(), 0, 20);
		new Renderer().setSizeColumn(tblUsuario.getColumnModel(), 1, 280);
		new Renderer().setSizeColumn(tblUsuario.getColumnModel(), 2, 500);
	}

	/* MÉTODO QUE BUSCA USUARIO */
	public void buscaUsuario() {
		if (txtUsuario.getText().trim().equals(Constante.VACIO)) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						if (((BeanDato) datose.get(0)).getStrModo().equals( Constante.MODO_ONLINE)) {
							objSAP = new SAdministracion();
							usuariose = objSAP.buscaUsuario("");
						} else {
							BeanUsuario usr = new BeanUsuario();
							usuariose = new ArrayList<BeanUsuario>();
							usr.setStrNomUsuario("");
							getUsuario.setListaUsuario(usr);
							listaUsuario = getUsuario.getListaUsuario();
							BeanUsuario beanUsuario = null;
							for (BeanUsuario u : listaUsuario) {
								beanUsuario = new BeanUsuario();
								beanUsuario.setStrBloqueado(u.getStrUsuarioBloqueado());
								beanUsuario.setStrClaUsuario(u.getStrClaUsuario());
								beanUsuario.setStrFecCre(u.getStrFecCre());
								beanUsuario.setStrHorUltAccSis(u.getStrHorUltAccSis());
								beanUsuario.setStrIdUsuario(u.getStrIdUsuario());
								beanUsuario.setStrMandante(u.getStrMandante());
								beanUsuario.setStrNomUsuario(u.getStrNomUsuario());
								beanUsuario.setStrFecUltAccSis(u.getStrFecUltAccSis());
								beanUsuario.setStrIdentificacion(u.getStrIdentificacion());
								beanUsuario.setStrNomApe(u.getStrNomApe());
								usuariose.add(beanUsuario);
							}
						}
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_USUARIO);
					}
					bloqueador.dispose();
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				public void run() {
					try {
						if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
							objSAP = new SAdministracion();
							usuariose = objSAP.buscaUsuario(txtUsuario.getText().toUpperCase().trim());
						} else {
							BeanUsuario usr = new BeanUsuario();
							usuariose = new ArrayList<BeanUsuario>();
							usr.setStrNomUsuario(txtUsuario.getText().toUpperCase().trim());
							getUsuario.setListaUsuario(usr);
							listaUsuario = getUsuario.getListaUsuario();
							BeanUsuario beanUsuario = null;
							for (BeanUsuario u : listaUsuario) {
								beanUsuario = new BeanUsuario();
								beanUsuario.setStrBloqueado(u.getStrUsuarioBloqueado());
								beanUsuario.setStrClaUsuario(u.getStrClaUsuario());
								beanUsuario.setStrFecCre(u.getStrFecCre());
								beanUsuario.setStrHorUltAccSis(u.getStrHorUltAccSis());
								beanUsuario.setStrIdUsuario(u.getStrIdUsuario());
								beanUsuario.setStrMandante(u.getStrMandante());
								beanUsuario.setStrNomUsuario(u.getStrNomUsuario());
								beanUsuario.setStrFecUltAccSis(u.getStrFecUltAccSis());
								beanUsuario.setStrIdentificacion(u.getStrIdentificacion());
								beanUsuario.setStrNomApe(u.getStrNomApe());
								usuariose.add(beanUsuario);
							}
						}
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_USUARIOS);
					}
					bloqueador.dispose();
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
		if (usuariose == null || usuariose.size() == 0) {
			if (txtUsuario.getText().trim().equals(Constante.VACIO)) {
				Mensaje.mostrarAviso(Constante.MENSAJE_BUSQUEDA_USUARIOS);
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_BUSQUEDA_USUARIO);
				txtUsuario.setText("");
			}
			tablaVacia();
		} else {
			if (!txtUsuario.getText().trim().equals(Constante.VACIO)) {
				txtUsuario.setText(txtUsuario.getText().toUpperCase().trim());
				txtUsuario.setSelectionStart(0);
				txtUsuario.setSelectionEnd(txtUsuario.getText().length());
			}
			tablaLlena();
		}
	}

	/* MÉTODO QUE BORRA USUARIO */
	public void borraUsuario() {
		if (modeloTabla.getRowCount() > 0) {
			try {
				if (tblUsuario.getSelectedRow() > -1) {
					if (Mensaje.preguntar(Constante.MENSAJE_PREGUNTA_ELIMINA_USUARIO)) {
						if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
							final DLocker bloqueador = new DLocker();
							Thread hilo = new Thread() {
								public void run() {
									try {
										objSAP = new SAdministracion();
										DefaultTableModel dtm = (DefaultTableModel) tblUsuario.getModel();
										int fila = tblUsuario.getSelectedRow();
										for (int i = 0; i < usuariose.size(); i++) {
											if (((BeanUsuario) usuariose.get(i)).getStrNomUsuario().equals(tblUsuario.getValueAt(fila, 1).toString())) {
												objSAP.eliminaUsuario(((BeanUsuario) usuariose.get(i)).getStrIdUsuario());
												break;
											}
										}
										dtm.removeRow(fila);
										if (!(txtUsuario.getText().trim().equals(""))) {
											txtUsuario.setText("");
										}
										if (usuariose.size() <= 9) {
											dtm.insertRow(8, new Object[] { "", "", "" });
										} else {
											dtm.insertRow(usuariose.size() - 1, new Object[] { "", "", "" });
										}
									} catch (Exception e) {
										Util.mostrarExcepcion(e);
										Mensaje.mostrarError(Constante.MENSAJE_ERROR_ELIMINA_USUARIO);
									}
									bloqueador.dispose();
								}
							};
							hilo.start();
							bloqueador.setVisible(true);
						} else {
							Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_ELIMINA_USUARIO);
						}
					}
				} else {
					Mensaje.mostrarAviso(Constante.MENSAJE_SELECCION_ELIMINA_USUARIO);
				}
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_ELIMINA_USUARIO);
			}
		} else {
			Mensaje.mostrarAviso(Constante.MENSAJE_NO_USUARIO_ELIMINA);
		}
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
		if (arg0.getClickCount() == 2) {
			final int fila = tblUsuario.rowAtPoint(arg0.getPoint());
			if ((fila > -1)) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						try {
							Administrador a = new Administrador();
							String idUsuario, nombreUsuario, clave, fechaCrea, fechaAcceso, horaAcceso, bloqueado, identificacion;
							idUsuario = nombreUsuario = clave = fechaCrea = fechaAcceso = horaAcceso = bloqueado = identificacion = Constante.VACIO;
							if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
								try {
									for (int i = 0; i < usuariose.size(); i++) {
										if (((BeanUsuario) usuariose.get(i)).getStrNomUsuario().trim().equals(tblUsuario.getValueAt(fila, 1).toString().trim())) {
											rolesusuarioe = objSAP.listaRolesUsuario(((BeanUsuario) usuariose.get(i)).getStrIdUsuario().trim());
											idUsuario = ((BeanUsuario) usuariose.get(i)).getStrIdUsuario().trim();
											nombreUsuario = ((BeanUsuario) usuariose.get(i)).getStrNomUsuario().trim();
											clave = ((BeanUsuario) usuariose.get(i)).getStrClaUsuario().trim();
											fechaCrea = ((BeanUsuario) usuariose.get(i)).getStrFecCre().trim();
											fechaAcceso = ((BeanUsuario) usuariose.get(i)).getStrFecUltAccSis().trim();
											horaAcceso = ((BeanUsuario) usuariose.get(i)).getStrHorUltAccSis().trim();
											bloqueado = ((BeanUsuario) usuariose.get(i)).getStrBloqueado().trim();
											identificacion = ((BeanUsuario) usuariose.get(i)).getStrIdentificacion().trim();
											break;
										}
									}
									a.ventanaAdministradorPasaDatosUsuarios(0, Promesa.destokp, idUsuario, nombreUsuario, clave, fechaCrea, fechaAcceso, horaAcceso, bloqueado, identificacion, rolesusuarioe, beanTareaProgramada);
								} catch (Exception e) {
									Util.mostrarExcepcion(e);
									Mensaje.mostrarError(Constante.MENSAJE_ERROR_TRANSFERENCIA_USUARIO);
								}
							}
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
						}
						bloqueador.dispose();
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_NO_USUARIOS);
			}
		}
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
		if (arg0.getSource() == btnRol) {
			/* LLAMA A PANTALLA ROL */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(0, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnVista) {
			/* LLAMA A PANTALLA VISTA */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(1, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnDispositivo) {
			/* LLAMA A PANTALLA DISPOSITIVO */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(6, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnBuscar) {
			/* IMPLEMENTA FUNCIONALIDAD BUSCAR */
			buscaUsuario();
		}
		if (arg0.getSource() == btnNuevo) {
			/* LLAMA A PANTALLA NUEVO USUARIO */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(5, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnBorrar) {
			/* IMPLEMENTA FUNCIONALIDAD BORRAR */
			borraUsuario();
		}
	}
}