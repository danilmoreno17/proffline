package com.promesa.internalFrame.administracion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.promesa.administracion.sql.SqlRol;
import com.promesa.administracion.sql.SqlVista;
import com.promesa.administracion.sql.SqlVistaRol;
import com.promesa.administracion.sql.impl.SqlRolImpll;
import com.promesa.administracion.sql.impl.SqlVistaImpl;
import com.promesa.administracion.sql.impl.SqlVistaRolImpl;
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

public class IAsignarVista extends JInternalFrame implements ActionListener, MouseListener, KeyListener {

	JToolBar mnuToolBar;
	JButton btnRol;
	JButton btnVista;
	JButton btnUsuario;
	JButton btnDispositivo;
	private JLabel lblRol;
	@SuppressWarnings("rawtypes")
	JComboBox cmbRol;
	private JLabel lblVista;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbVista;
	JButton btnAsignar;
	private JLabel lblAsignar;
	JSeparator separador;
	private JTable tblVista;
	DefaultTableModel modeloTabla;
	JScrollPane scrVista;
	JPanel pnlVista;
	SAdministracion objSAP;
	List<BeanRol> rolese;
	List<BeanVista> vistase;
	List<BeanVista> vistasrole;
	static List<BeanDato> datose;
	private BeanRol rol = null;
	private SqlRol getRol = null;
	private List<BeanRol> listaRol = null;
	private BeanVista vista = null;
	private SqlVista getVista = null;
	private List<BeanVista> listaVista = null;
	private List<BeanRolVista> listaVistaRol = null;
	private SqlVistaRol getVistaRol = null;
	private BeanTareaProgramada beanTareaProgramada;

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public IAsignarVista(BeanTareaProgramada beanTareaProgramada) {

		super("Vistas", true, true, true, true);
		List<BeanDato> datose = beanTareaProgramada.getDatose();
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/ivistas.gif")));
		this.setPreferredSize(new java.awt.Dimension(606, 640)); 
		this.setBounds(0, 0, 606, 640); 
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(235, 239, 242));

		mnuToolBar = new JToolBar();
		getContentPane().add(mnuToolBar, BorderLayout.NORTH);
		mnuToolBar.setBounds(0, 0, 1366, 37); // 0, 0, 1018, 37 
		mnuToolBar.setBackground(new java.awt.Color(235, 239, 242));
		separador = new JSeparator();
		getContentPane().add(separador);
		separador.setBounds(0, 39, 1366, 10); // 0, 39, 1318, 10 

		this.datose = datose;
		rol = new BeanRol();
		getRol = new SqlRolImpll();
		vista = new BeanVista();
		getVista = new SqlVistaImpl();
		new BeanRolVista();
		getVistaRol = new SqlVistaRolImpl();
		rolese = new ArrayList<BeanRol>();
		vistase = new ArrayList<BeanVista>();

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

		pnlVista = new JPanel();
		getContentPane().add(pnlVista);
		pnlVista.setBounds(0, 48, 790, 273); 
		pnlVista.setLayout(null);
		pnlVista.setBorder(BorderFactory.createTitledBorder("Asignar Vista a Rol"));

		{
			lblRol = new JLabel();
			pnlVista.add(lblRol);
			lblRol.setText("Rol:");
			lblRol.setBounds(18, 32, 119, 17);
			lblRol.setFont(new java.awt.Font("Arial", 1, 11));
		}
		cargaRol();
		{
			cmbRol = new JComboBox();
			if (rolese != null && rolese.size() > 0) {
				for (int i = 0; i < rolese.size(); i++) {
					cmbRol.addItem(((BeanRol) rolese.get(i)).getStrNomRol());
				}
			} else {
				cmbRol.addItem("");
			}
			pnlVista.add(cmbRol);
			cmbRol.setBounds(48, 30, 137, 21);
			cmbRol.setToolTipText("Roles");
			cmbRol.setFocusable(false);
			cmbRol.setFont(new java.awt.Font("Candara", 0, 11));
			cmbRol.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final DLocker bloqueador = new DLocker();
					Thread hilo = new Thread() {
						boolean fallo = false;
						public void run() {
							try {
								elementoCambiado();
							} catch (Exception e) {								
								Util.mostrarExcepcion(e);
								fallo = true;
							} finally {
								bloqueador.dispose();
								if (fallo) {
									Mensaje.mostrarError(Constante.ERROR_TRANSACCION);
								}
							}
						}
					};
					hilo.start();
					bloqueador.setVisible(true);
				}
			});
			cmbRol.setBackground(new java.awt.Color(255, 255, 255));
		}

		{
			lblVista = new JLabel();
			pnlVista.add(lblVista);
			lblVista.setText("Vista:");
			lblVista.setBounds(205, 32, 119, 17);
			lblVista.setFont(new java.awt.Font("Arial", 1, 11));
			lblVista.setVisible(false);
		}
		cargaVista();
		{
			cmbVista = new JComboBox();
			if (vistase != null && vistase.size() > 0) {
				for (int i = 0; i < vistase.size(); i++) {
					cmbVista.addItem(((BeanVista) vistase.get(i)).getStrDesVis());
				}
			} else {
				cmbVista.addItem("");
			}
			pnlVista.add(cmbVista);
			cmbVista.setBounds(246, 30, 185, 21);
			cmbVista.setToolTipText("Vistas");
			cmbVista.setFocusable(false);
			cmbVista.setFont(new java.awt.Font("Candara", 0, 11));
			cmbVista.setBackground(new java.awt.Color(255, 255, 255));
			cmbVista.setVisible(false);
		}

		{
			btnAsignar = new JButton();
			pnlVista.add(btnAsignar);
			btnAsignar.setText("");
			btnAsignar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/asignar.gif")));
			btnAsignar.addActionListener(this);
			btnAsignar.setToolTipText("Asignar");
			btnAsignar.setBackground(new java.awt.Color(0, 128, 64));
			btnAsignar.setBounds(460, 32, 30, 20);
			btnAsignar.setOpaque(false);
			btnAsignar.setFocusable(false);
		}

		{
			lblAsignar = new JLabel();
			pnlVista.add(lblAsignar);
			lblAsignar.setText("Guardar");
			lblAsignar.setBounds(497, 32, 125, 17);
			lblAsignar.setFont(new java.awt.Font("Arial", 1, 10));
		}

		scrVista = new JScrollPane();
		scrVista.setBounds(50, 65, 680, 180);
		scrVista.setToolTipText("Asignación de Vistas");
		tablaRolVista();
	}

	/* MÉTODO QUE CARGA ROLES */
	public void cargaRol() {
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			try {
				objSAP = new SAdministracion();
				rolese = objSAP.listaRoles();
			} catch (Exception e) {				
				Util.mostrarExcepcion(e);
			}
		} else {
			getRol.setListaRol(rol);
			listaRol = getRol.getListaRol();
			BeanRol beanRol = null;
			for (BeanRol rol : listaRol) {
				beanRol = new BeanRol();
				beanRol.setStrIdRol(rol.getStrIdRol());
				beanRol.setStrMandante(rol.getStrMandante());
				beanRol.setStrNomRol(rol.getStrNomRol());
				rolese.add(beanRol);
			}
		}
	}

	/* MÉTODO QUE CARGA VISTAS */
	@SuppressWarnings("unused")
	public void cargaVista() {
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			try {
				objSAP = new SAdministracion();
				Date time=new Date();
				vistase = objSAP.listaVistas("");
				Date time2 = new Date();
			} catch (Exception e) {				
				Util.mostrarExcepcion(e);
			}
		} else {
			getVista.setListaVista(vista);
			listaVista = getVista.getListaVista();
			BeanVista beanVista = null;
			for (BeanVista vista : listaVista) {
				beanVista = new BeanVista();
				beanVista.setStrNomVis(vista.getStrNomVis());
				beanVista.setStrDesVis(vista.getStrDesVis());
				vistase.add(beanVista);
			}
		}
	}

	/* MÉTODO QUE LLENA TABLA REMARCANDO LAS VISTAS SEGUN LOS ROLES */
	public void tablaRolVista() {
		try {
			for (int i = 0; i < rolese.size(); i++) {
				if (((BeanRol) rolese.get(i)).getStrNomRol().equals(cmbRol.getSelectedItem().toString().trim())) {
					vistasrole = new ArrayList<BeanVista>();
					if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
						vistasrole = objSAP.listaVistas(((BeanRol) rolese.get(i)).getStrIdRol());
					} else {
						BeanRolVista vistRol = new BeanRolVista();
						BeanRol rol = new BeanRol();
						rol.setStrNomRol(cmbRol.getSelectedItem().toString().trim());
						vistRol.setRol(rol);
						getVistaRol.setListaVistaRol(vistRol);
						listaVistaRol = getVistaRol.getListaVistaRol();
						BeanVista beanVista = null;
						for (BeanRolVista vistaRol : listaVistaRol) {
							beanVista = new BeanVista();
							beanVista.setStrNomVis(vistaRol.getVista().getStrNomVis());
							beanVista.setStrDesVis(vistaRol.getVista().getStrDesVis());
							vistasrole.add(beanVista);
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		String Columnas[] = { "Nombre", "Descripción", "Asignado" };
		modeloTabla = new DefaultTableModel(null, Columnas);
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			modeloTabla.setNumRows(vistasrole.size());
			for (int i = 0; i < vistasrole.size(); i++) {
				for (int j = 0; j < 1; j++) {
					modeloTabla.setValueAt(	((BeanVista) vistasrole.get(i)).getStrNomVis(), i, j);
					modeloTabla.setValueAt(	((BeanVista) vistasrole.get(i)).getStrDesVis(), i, j + 1);
					if (((BeanVista) vistasrole.get(i)).getStrC() != null)
						modeloTabla.setValueAt(new Boolean(true), i, j + 2);
					else
						modeloTabla.setValueAt(new Boolean(false), i, j + 2);
				}
			}
		} else {
			modeloTabla.setNumRows(vistase.size());
			for (int i = 0; i < vistase.size(); i++) {
				for (int j = 0; j < 1; j++) {
					modeloTabla.setValueAt(	((BeanVista) vistase.get(i)).getStrNomVis(), i, j);
					modeloTabla.setValueAt(	((BeanVista) vistase.get(i)).getStrDesVis(), i, j + 1);
					for (int k = 0; k < vistasrole.size(); k++) {
						if (((BeanVista) vistase.get(i)).getStrNomVis().equals(((BeanVista) vistasrole.get(k)).getStrNomVis()))
							modeloTabla.setValueAt(new Boolean(true), i, j + 2);
					}
				}
			}
		}
		tblVista = new JTable();
		tblVista.setModel(modeloTabla);
		tblVista.addKeyListener(this);
		tblVista.addMouseListener(this);
		tblVista.getColumn("Asignado").setCellRenderer(new CELL_RENDERER());
		tblVista.getColumn("Asignado").setMaxWidth(80);
		tblVista.getColumn("Asignado").setCellEditor(new CELL_EDITOR(new JCheckBox()));
		scrVista.setViewportView(tblVista);
		pnlVista.add(scrVista);
		new Renderer().setSizeColumn(tblVista.getColumnModel(), 0, 300);
		new Renderer().setSizeColumn(tblVista.getColumnModel(), 2, 80);		
	}		

	/* METODO QUE ASIGNA VISTA A ROL */
	public void asignaVistaRol() {
		if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				boolean fallo = false;
				public void run() {
					try {
						Administrador a = new Administrador();
						@SuppressWarnings("unused")
						String idRol, nomVis;
						nomVis = "";
						idRol = "";
						int numeroFilas = modeloTabla.getRowCount();
						List<String> vistas = new ArrayList<String>();
						for (int i = 0; i < numeroFilas; i++) {
							String codigoVista = modeloTabla.getValueAt(i, 0).toString();
							String estaSeleccionado = modeloTabla.getValueAt(i, 2).toString();
							if(estaSeleccionado.compareTo("true")==0) {
								vistas.add(codigoVista);
							}
						}
						
						for (int i = 0; i < rolese.size(); i++) {
							if (((BeanRol) rolese.get(i)).getStrNomRol().trim().equals(cmbRol.getSelectedItem().toString().trim())) {
								idRol = ((BeanRol) rolese.get(i)).getStrIdRol().trim();
								break;
							}
						}
						objSAP = new SAdministracion();
						objSAP.asignaVistaRol(idRol, vistas);						
						tablaRolVista();
						Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_ASIGNA_VISTA_ROL);
						a.muestraVentanaAdministrador(1, Promesa.destokp,beanTareaProgramada);						
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						fallo = true;
					} finally {
						bloqueador.dispose();
						if (fallo) {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_ASIGNA_VISTA_ROL);
						}
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_ASIGNA_VISTA_ROL);
		}
	}

	/* CAMBIO DE ROL */
	public void elementoCambiado() {
		tablaRolVista();
	}

	public class CELL_RENDERER extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CELL_RENDERER() {
			setHorizontalAlignment(JLabel.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable arg0, Object value, boolean arg2, boolean arg3, int arg4, int arg5) {
			setSelected((value != null && ((Boolean) value).booleanValue()));
			setBackground(arg0.getBackground());
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}

	}

	public class CELL_EDITOR extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;

		public CELL_EDITOR(JCheckBox checkBox) {
			super(checkBox);
			checkBox.setHorizontalAlignment(JLabel.CENTER);
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
		if (arg0.getSource() == btnUsuario) {
			/* LLAMA A PANTALLA USUARIO */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(2, Promesa.destokp, beanTareaProgramada);
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
		if (arg0.getSource() == btnAsignar) {
			/* FUNCIONALIDAD ASIGNAR */
			asignaVistaRol();
		}
	}
}