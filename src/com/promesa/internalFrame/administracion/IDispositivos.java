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
import java.io.File;
import jxl.*;
import jxl.write.*;
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
import com.promesa.administracion.sql.SqlDispositivo;
import com.promesa.administracion.sql.impl.SqlDispositivoImpl;
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


@SuppressWarnings("serial")
public class IDispositivos extends JInternalFrame implements ActionListener, MouseListener, KeyListener {

	JToolBar mnuToolBar;
	JButton btnRol;
	JButton btnVista;
	JButton btnUsuario;
	JButton btnDispositivo;
	JButton btnBuscar;
	JButton btnNuevo;
	JButton btnBorrar;
	JButton btnExportar;
	JSeparator separador;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JTable tblDispositivo;
	DefaultTableModel modeloTabla;
	JScrollPane scrDispositivo;
	JPanel pnlDispositivo;
	SAdministracion objSAP;
	List<BeanDispositivo> dispositivose;
	static List<BeanDato> datose;
	private List<BeanDispositivo> listaDispositivo = null;
	private SqlDispositivo getDispositivo = null;
	BeanTareaProgramada beanTareaProgramada = null;

	@SuppressWarnings("static-access")
	public IDispositivos(BeanTareaProgramada beanTareaProgramada) {

		super("Dispositivos", true, true, true, true);
		List<BeanDato> datose = beanTareaProgramada.getDatose();
		this.beanTareaProgramada = beanTareaProgramada;
		this.setFrameIcon(new ImageIcon(this.getClass().getResource("/imagenes/idispositivos.png")));
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

		this.datose = datose;
		getDispositivo = new SqlDispositivoImpl();

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
		btnDispositivo.setEnabled(false);
		btnDispositivo.setOpaque(false);
		btnDispositivo.setBorder(null);
		btnDispositivo.setFocusable(false);

		pnlDispositivo = new JPanel();
		getContentPane().add(pnlDispositivo);
		pnlDispositivo.setBounds(0, 48, 790, 273); // 0, 48, 1119, 273
		pnlDispositivo.setLayout(null);
		pnlDispositivo.setBorder(BorderFactory.createTitledBorder("Buscar Dispositivo"));

		{
			lblUsuario = new JLabel();
			pnlDispositivo.add(lblUsuario);
			lblUsuario.setText("Usuario:");
			lblUsuario.setBounds(12, 32, 119, 17);
			lblUsuario.setFont(new java.awt.Font("Arial", 1, 11));
		}

		{
			txtUsuario = new JTextField();
			pnlDispositivo.add(txtUsuario);
			txtUsuario.setBounds(70, 30, 147, 24);
			txtUsuario.setBackground(new java.awt.Color(255, 255, 255));
			txtUsuario.setToolTipText("Usuario");
			txtUsuario.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						if (!txtUsuario.getText().trim().equals(""))
							buscaDispositivo();
					}
				}
			});
		}

		{
			btnBuscar = new JButton();
			pnlDispositivo.add(btnBuscar);
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
			pnlDispositivo.add(btnNuevo);
			btnNuevo.setText("Nuevo");
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/nuevo.png")));
			btnNuevo.addActionListener(this);
			btnNuevo.setToolTipText("Nuevo");
			btnNuevo.setBackground(new java.awt.Color(0, 128, 64));
			btnNuevo.setBounds(330, 30, 100, 20); // horizontal, vertical,width, high
			btnNuevo.setOpaque(false);
			btnNuevo.setFocusable(false);
		}

		{
			btnBorrar = new JButton();
			pnlDispositivo.add(btnBorrar);
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
			btnExportar = new JButton();
			pnlDispositivo.add(btnExportar);
			btnExportar.setText("Exportar");
			btnExportar.setIcon(new ImageIcon(this.getClass().getResource("/imagenes/exportar.gif")));
			btnExportar.addActionListener(this);
			btnExportar.setToolTipText("Exportar");
			btnExportar.setBackground(new java.awt.Color(0, 128, 64));
			btnExportar.setBounds(530, 30, 100, 20); // horizontal, vertical,width, high
			btnExportar.setOpaque(false);
			btnExportar.setFocusable(false);
		}

		scrDispositivo = new JScrollPane();
		pnlDispositivo.add(scrDispositivo);
		scrDispositivo.setBounds(50, 65, 680, 180); // 150, 65, 800, 180
		scrDispositivo.setToolTipText("Relación de Dispositivos");

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

	/* MÉTODO BORRA DISPOSITIVO */
	public void borraDispositivo() {
		if (modeloTabla.getRowCount() > 0) {
			try {
				if (tblDispositivo.getSelectedRow() > -1) {
					if (Mensaje.preguntar(Constante.MENSAJE_PREGUNTA_ELIMINA_DISPOSTIVO)) {
						if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
							final DLocker bloqueador = new DLocker();
							Thread hilo = new Thread() {
								boolean fallo = false;
								public void run() {
									try {
										objSAP = new SAdministracion();
										DefaultTableModel dtm = (DefaultTableModel) tblDispositivo.getModel();
										int fila = tblDispositivo.getSelectedRow();
										for (int i = 0; i < dispositivose.size(); i++) {
											if (((BeanDispositivo) dispositivose.get(i)).getStrNumeroSerieDispositivo()	.equals(tblDispositivo.getValueAt(fila, 3).toString())) {
												objSAP.eliminaDispositivo(((BeanDispositivo) dispositivose.get(i)).getStrIdDispositivo());
												break;
											}
										}
										dtm.removeRow(fila);
										if (!(txtUsuario.getText().trim().equals(""))) {	txtUsuario.setText(""); }
										if (dispositivose.size() <= 9) { dtm.insertRow(8, new Object[] { "", "", "", "", "", "", "", "", "", "" }); } else { dtm.insertRow(dispositivose.size() - 1,	new Object[] { "", "", "", "", "", "", "", "", "", "" }); }
									} catch (Exception e) {
										fallo = true;
										Util.mostrarExcepcion(e);
									} finally {
										bloqueador.dispose();
										if (fallo) {
											Mensaje.mostrarError(Constante.MENSAJE_ERROR_ELIMINA_DISPOSITIVO);
										}
									}
								}
							};
							hilo.start();
							bloqueador.setVisible(true);
						} else {
							Mensaje.mostrarWarning(Constante.MENSAJE_VALIDA_ELIMINA_DISPOSITIVO);
						}
					}
				} else {
					Mensaje.mostrarAviso(Constante.MENSAJE_SELECCION_ELIMINA_DISPOSITIVO);
				}
			} catch (Exception e) {
				Mensaje.mostrarError(Constante.MENSAJE_ERROR_ELIMINA_DISPOSITIVO);
				Util.mostrarExcepcion(e);
			}
		}else{
			Mensaje.mostrarAviso(Constante.MENSAJE_NO_DISPOSTIVO_ELIMINA);
		}	
	}

	/* MÉTODO QUE EXPORTA A EXCEL */
	@SuppressWarnings("static-access")
	public void exportar() {
		if (modeloTabla.getRowCount() > 0) {
			javax.swing.JFileChooser jF1 = new javax.swing.JFileChooser();
			String ruta = "";
			try{
				if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){
					ruta = jF1.getSelectedFile().getAbsolutePath();
					if (fillData(tblDispositivo, new File(ruta+Constante.EXTENSION_ARCHIVO)))
						Mensaje.mostrarAviso(Constante.MENSAJE_EXITO_EXPORTA+ruta+Constante.EXTENSION_ARCHIVO);
					else
						Mensaje.mostrarAviso(Constante.MENSAJE_ERROR_EXPORTA_DISPOSTIVO);
				}
			}catch (Exception e){
				Util.mostrarExcepcion(e);
			}			
		} else {
			Mensaje.mostrarAviso(Constante.MENSAJE_NO_DISPOSTIVO_EXPORTA);
		}
	}

	boolean fillData(JTable table, File file) {
		boolean resultado;
		try {
			WritableWorkbook workbook1 = Workbook.createWorkbook(file);
			WritableSheet sheet1 = workbook1.createSheet("Dispositivos", 0);
			TableModel model = table.getModel();
			for (int i = 0; i < model.getColumnCount(); i++) {
				Label column = new Label(i, 0, model.getColumnName(i));
				sheet1.addCell(column);
			}
			int j = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				for (j = 0; j < model.getColumnCount(); j++) {
					if (model.getValueAt(i, j) != null) {
						Label row = new Label(j, i + 1, model.getValueAt(i, j).toString());
						sheet1.addCell(row);
					}
				}
			}
			workbook1.write();
			workbook1.close();
			resultado = true;
		} catch (Exception ex) {
			Mensaje.mostrarError(ex.getMessage());
			resultado = false;
			Util.mostrarExcepcion(ex);
		}
		return resultado;
	}

	/* MÉTODO QUE BUSCA DISPOSITIVO */
	public void buscaDispositivo() {
		if (txtUsuario.getText().trim().equals(Constante.VACIO)) {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				boolean fallo = false;
				public void run() {
					try {
						if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
							objSAP = new SAdministracion();
							dispositivose = objSAP.buscaDispositivo("");
						} else {
							BeanDispositivo dispositivo = new BeanDispositivo();
							dispositivose = new ArrayList<BeanDispositivo>();
							dispositivo.setStrNomUsuario(Constante.VACIO);
							getDispositivo.setListaDispositivo(dispositivo);
							listaDispositivo = getDispositivo.getListaDispositivo();
							BeanDispositivo beanDispositivo = null;
							for (BeanDispositivo d : listaDispositivo) {
								beanDispositivo = new BeanDispositivo();
								beanDispositivo.setStrIdDispositivo(d.getStrIdDispositivo());
								beanDispositivo.setStrTipoDispositivo(d.getStrTipoDispositivo());
								beanDispositivo.setStrNumeroSerieDispositivo(d.getStrNumeroSerieDispositivo());
								beanDispositivo.setStrIdUsuario(d.getStrIdUsuario());
								beanDispositivo.setStrNomUsuario(d.getStrNomUsuario());
								beanDispositivo.setStrEstado(d.getStrEstado()+ "");								
								beanDispositivo.setStrDispositivoRelacionado(d.getStrDispositivoRelacionado());
								beanDispositivo.setStrCodigoActivo(d.getStrCodigoActivo());
								beanDispositivo.setStrNumeroSeguro(d.getStrNumeroSeguro());
								beanDispositivo.setStrSimm(d.getStrSimm());
								beanDispositivo.setStrImei(d.getStrImei());
								beanDispositivo.setStrNumeroTelefono(d.getStrNumeroTelefono());
								beanDispositivo.setStrObservacion(d.getStrObservacion());
								dispositivose.add(beanDispositivo);
							}
						}
					} catch (Exception e) {
						fallo = true;
						Util.mostrarExcepcion(e);
					} finally {
						bloqueador.dispose();
						if (fallo) {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_DISPOSITIVOS);
						}
					}
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		} else {
			final DLocker bloqueador = new DLocker();
			Thread hilo = new Thread() {
				boolean fallo = false;				
				public void run() {
					try {
						if (((BeanDato) datose.get(0)).getStrModo().equals(Constante.MODO_ONLINE)) {
							objSAP = new SAdministracion();
							dispositivose = objSAP.buscaDispositivo(txtUsuario.getText().toUpperCase().trim());
						} else {
							BeanDispositivo dispositivo = new BeanDispositivo();
							dispositivose = new ArrayList<BeanDispositivo>();
							dispositivo.setStrNomUsuario(txtUsuario.getText().toUpperCase().trim());
							getDispositivo.setListaDispositivo(dispositivo);
							listaDispositivo = getDispositivo.getListaDispositivo();
							BeanDispositivo beanDispositivo = null;
							for (BeanDispositivo d : listaDispositivo) {
								beanDispositivo = new BeanDispositivo();
								beanDispositivo.setStrIdDispositivo(d.getStrIdDispositivo());
								beanDispositivo.setStrTipoDispositivo(d.getStrTipoDispositivo());
								beanDispositivo.setStrNumeroSerieDispositivo(d.getStrNumeroSerieDispositivo());
								beanDispositivo.setStrIdUsuario(d.getStrIdUsuario());
								beanDispositivo.setStrNomUsuario(d.getStrNomUsuario());
								beanDispositivo.setStrEstado(d.getStrEstado() + "");								
								beanDispositivo.setStrDispositivoRelacionado(d.getStrDispositivoRelacionado());
								beanDispositivo.setStrCodigoActivo(d.getStrCodigoActivo());
								beanDispositivo.setStrNumeroSeguro(d.getStrNumeroSeguro());
								beanDispositivo.setStrSimm(d.getStrSimm());
								beanDispositivo.setStrImei(d.getStrImei());
								beanDispositivo.setStrNumeroTelefono(d.getStrNumeroTelefono());
								beanDispositivo.setStrObservacion(d.getStrObservacion());
								dispositivose.add(beanDispositivo);
							}
						}
					} catch (Exception e) {
						fallo = true;
						Util.mostrarExcepcion(e);
					} finally {
						bloqueador.dispose();
						if(fallo) {
							Mensaje.mostrarError(Constante.MENSAJE_ERROR_BUSQUEDA_DISPOSITIVO);
						}
					}					
				}
			};
			hilo.start();
			bloqueador.setVisible(true);
		}
		if (dispositivose == null || dispositivose.size() == 0) {
			if (txtUsuario.getText().trim().equals(Constante.VACIO)) {
				Mensaje.mostrarAviso(Constante.MENSAJE_BUSQUEDA_DISPOSITIVOS);
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_BUSQUEDA_DISPOSITIVO);
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

	/* MÉTODO QUE MUESTRA UNA TABLA VACÍA */
	public void tablaVacia() {
		String Columnas[] = { "Estado", "Usuario", "Dispositivo", "Serie", "Cod. Act.", "Contrato", "Simm", "Imei", "Teléfono", "Observación" };
		modeloTabla = new DefaultTableModel(null, Columnas);
		TableModel tblTablaModel = new DefaultTableModel(new Object[][] {
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "", "" } }, Columnas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblDispositivo = new JTable();
		tblDispositivo.setModel(tblTablaModel);
		tblDispositivo.addKeyListener(this);
		tblDispositivo.addMouseListener(this);
		scrDispositivo.setViewportView(tblDispositivo);
		pnlDispositivo.add(scrDispositivo);		
	}

	/* MÉTODO QUE MUESTRA UNA TABLA LLENA */
	public void tablaLlena() {
		String Columnas[] = { "Estado", "Usuario", "Dispositivo", "Serie", "Cod. Act.", "Contrato", "Simm", "Imei", "Teléfono", "Observación" };
		modeloTabla = new DefaultTableModel(null, Columnas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		if (dispositivose.size() <= 9) {
			modeloTabla.setNumRows(9);
		} else {
			modeloTabla.setNumRows(dispositivose.size());
		}
		for (int i = 0; i < dispositivose.size(); i++) {
			for (int j = 0; j < 1; j++) {
				if (((BeanDispositivo) dispositivose.get(i)).getStrEstado().equals("1"))
					modeloTabla.setValueAt("Activo", i, j);
				else
					modeloTabla.setValueAt("Inactivo", i, j);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrNomUsuario(), i, j + 1);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrDispositivoRelacionado(), i, j + 2);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrNumeroSerieDispositivo(), i, j + 3);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrCodigoActivo(), i, j + 4);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrNumeroSeguro(), i, j + 5);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrSimm(), i, j + 6);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrImei(), i, j + 7);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrNumeroTelefono(), i, j + 8);
				modeloTabla.setValueAt(((BeanDispositivo) dispositivose.get(i)).getStrObservacion(), i, j + 9);
			}
		}
		tblDispositivo = new JTable();
		tblDispositivo.setModel(modeloTabla);
		tblDispositivo.addKeyListener(this);
		tblDispositivo.addMouseListener(this);
		tblDispositivo.getColumnModel().getColumn(0).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(1).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(2).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(3).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(4).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(5).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(6).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(7).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(8).setCellRenderer(new Renderer().getCenterCell());
		tblDispositivo.getColumnModel().getColumn(9).setCellRenderer(new Renderer().getCenterCell());
		scrDispositivo.setViewportView(tblDispositivo);
		pnlDispositivo.add(scrDispositivo);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 0, 56);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 1, 68);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 2, 68);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 3, 72);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 4, 68);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 5, 68);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 6, 68);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 7, 68);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 8, 68);
		new Renderer().setSizeColumn(tblDispositivo.getColumnModel(), 9, 76);
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
		Administrador a = new Administrador();
		if (arg0.getClickCount() == 2) {
			int fila = tblDispositivo.rowAtPoint(arg0.getPoint()); // get row
			if ((fila > -1)) {
				try {
					for (int i = 0; i < dispositivose.size(); i++) {
						if (((BeanDispositivo) dispositivose.get(i)).getStrNumeroSerieDispositivo().equals(tblDispositivo.getValueAt(fila, 3).toString())) {
							a.ventanaAdministradorPasaDatosDispositivos(0, Promesa.destokp, ((BeanDispositivo) dispositivose.get(i)).getStrIdDispositivo(),
									((BeanDispositivo) dispositivose.get(i)).getStrDispositivoRelacionado(),
									((BeanDispositivo) dispositivose.get(i)).getStrNumeroSerieDispositivo(),
									((BeanDispositivo) dispositivose.get(i)).getStrCodigoActivo(),
									((BeanDispositivo) dispositivose.get(i)).getStrNumeroSeguro(),
									((BeanDispositivo) dispositivose.get(i)).getStrSimm(),
									((BeanDispositivo) dispositivose.get(i)).getStrImei(),
									((BeanDispositivo) dispositivose.get(i)).getStrNumeroTelefono(),
									((BeanDispositivo) dispositivose.get(i)).getStrIdUsuario(),
									((BeanDispositivo) dispositivose.get(i)).getStrNomUsuario(),
									((BeanDispositivo) dispositivose.get(i)).getStrEstado(),
									((BeanDispositivo) dispositivose.get(i)).getStrObservacion(), beanTareaProgramada);
							break;
						}
					}
				} catch (PropertyVetoException e1) {
					Mensaje.mostrarError(Constante.MENSAJE_ERROR_TRANSFERENCIA_DISPOSITIVO);
					Util.mostrarExcepcion(e1);
				}
			} else {
				Mensaje.mostrarAviso(Constante.MENSAJE_BUSQUEDA_DISPOSITIVOS);
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
		if (arg0.getSource() == btnUsuario) {
			/* LLAMA A PANTALLA USUARIO */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(2, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnBuscar) {
			/* IMPLEMENTA FUNCIONALIDAD BUSCAR */
			buscaDispositivo();
		}
		if (arg0.getSource() == btnNuevo) {
			/* LLAMA A PANTALLA NUEVO DISPOSITIVO */
			Administrador a = new Administrador();
			try {
				a.muestraVentanaAdministrador(7, Promesa.destokp, beanTareaProgramada);
			} catch (PropertyVetoException e1) {
				Util.mostrarExcepcion(e1);
			}
		}
		if (arg0.getSource() == btnBorrar) {
			/* FUNCIONALIDAD BORRAR */
			borraDispositivo();
		}
		if (arg0.getSource() == btnExportar) {
			/* FUNCIONALIDAD EXPORTAR */
			exportar();
		}
	}
}