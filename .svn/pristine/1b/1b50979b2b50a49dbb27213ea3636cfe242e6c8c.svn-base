package com.promesa.dialogo.cobranzas;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.PartidaIndividual;
import com.promesa.cobranzas.sql.impl.SqlPartidaIndividualImpl;
import com.promesa.internalFrame.cobranzas.custom.ModeloPartidasIndividuales;
import com.promesa.internalFrame.cobranzas.custom.RenderizadorPartidaIndividual;
import com.promesa.main.Promesa;
import com.promesa.sap.SCobranzas;
import com.promesa.util.Constante;
import com.promesa.util.DLocker;
import com.promesa.util.Util;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class DialogoPartidasIndividuales extends javax.swing.JDialog {

	private ModeloPartidasIndividuales modeloPartidasIndividuales = new ModeloPartidasIndividuales();
	private boolean estaOculto = true;
	private String codigoCliente;
	private String codigoVendedor;
	private RenderizadorPartidaIndividual renderizadorPartidaIndividual;

	/** Creates new form DialogoPartidasIndividuales */
	public DialogoPartidasIndividuales(java.awt.Frame parent, boolean modal, List<PartidaIndividual> listaPartidaIndividual, String codigoCliente, String codigoVendedor) {
		super(parent, modal);
		initComponents();
		this.codigoCliente = codigoCliente;
		this.codigoVendedor = codigoVendedor;
		this.modeloPartidasIndividuales = new ModeloPartidasIndividuales();
		tblPartidasIndividuales.setModel(modeloPartidasIndividuales);
		tblPartidasIndividuales.getTableHeader().setReorderingAllowed(false);
		((DefaultTableCellRenderer) tblPartidasIndividuales.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tblPartidasIndividuales.getTableHeader().setFont(Constante.FUENTE_CABECERA_TABLA);
		this.setSize(800, 350);
		this.setLocationRelativeTo(null);
		pnlParametrosBusqueda.setVisible(false);
		btnGrpOpcionesBusqueda.add(rdbPartidasAbiertas);
		btnGrpOpcionesBusqueda.add(rdbPartidasCompensadas);
		btnGrpOpcionesBusqueda.add(rdbTodasPartidas);
		for (PartidaIndividual partidaIndividual : listaPartidaIndividual) {
			modeloPartidasIndividuales.agregarPartidaIndividual(partidaIndividual);
		}
		rdbPartidasAbiertas.setSelected(true);
		datePartidasAbiertas.setDate(new Date());
		renderizadorPartidaIndividual = new RenderizadorPartidaIndividual();
		tblPartidasIndividuales.setDefaultRenderer(String.class, renderizadorPartidaIndividual);
		tblPartidasIndividuales.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblPartidasIndividuales.setRowHeight(Constante.ALTO_COLUMNAS);
		Util.setAnchoColumnas(tblPartidasIndividuales);
	}

	private void initComponents() {

		btnGrpOpcionesBusqueda = new javax.swing.ButtonGroup();
		pnlBusqueda = new javax.swing.JPanel();
		pnlParametrosBusqueda = new javax.swing.JPanel();
		rdbPartidasAbiertas = new javax.swing.JRadioButton();
		lblPartidasAbiertas = new javax.swing.JLabel();
		datePartidasAbiertas = new com.toedter.calendar.JDateChooser();
		rdbPartidasCompensadas = new javax.swing.JRadioButton();
		lblFechaClave = new javax.swing.JLabel();
		datePartidasCompensadas = new com.toedter.calendar.JDateChooser();
		rdbTodasPartidas = new javax.swing.JRadioButton();
		lblDesde = new javax.swing.JLabel();
		dateDesde = new com.toedter.calendar.JDateChooser();
		lblHasta = new javax.swing.JLabel();
		dateHasta = new com.toedter.calendar.JDateChooser();
		btnBuscar = new javax.swing.JButton();
		pbnlTitulo = new javax.swing.JPanel();
		lblOpcionesBusqueda = new javax.swing.JLabel();
		btnOcultar = new javax.swing.JButton();
		jSeparator2 = new javax.swing.JSeparator();
		pnlTabla = new javax.swing.JPanel();
		lblTitulo = new javax.swing.JLabel();
		scrPartidasIndividuales = new javax.swing.JScrollPane();
		tblPartidasIndividuales = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(".:: Partidas Individuales ::.");

		pnlBusqueda.setLayout(new java.awt.BorderLayout());

		pnlParametrosBusqueda.setPreferredSize(new java.awt.Dimension(400, 126));

		rdbPartidasAbiertas.setText("Partidas abiertas");
		rdbPartidasAbiertas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

		lblPartidasAbiertas.setText("Abiertas en la fecha clave:");

		rdbPartidasCompensadas.setText("Partidas compensadas");

		lblFechaClave.setText("Abiertas en la fecha clave:");

		rdbTodasPartidas.setText("Todas las partidas");

		lblDesde.setText("Desde:");

		lblHasta.setText("Hasta:");

		btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonBuscar.jpg"))); // NOI18N
		btnBuscar.setBorder(null);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setContentAreaFilled(false);

		javax.swing.GroupLayout pnlParametrosBusquedaLayout = new javax.swing.GroupLayout(pnlParametrosBusqueda);
		pnlParametrosBusqueda.setLayout(pnlParametrosBusquedaLayout);
		pnlParametrosBusquedaLayout.setHorizontalGroup(pnlParametrosBusquedaLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlParametrosBusquedaLayout.createSequentialGroup().addContainerGap()
				.addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(btnBuscar).addComponent(rdbPartidasCompensadas)
				.addComponent(rdbTodasPartidas,javax.swing.GroupLayout.DEFAULT_SIZE,631,Short.MAX_VALUE)
				.addGroup(pnlParametrosBusquedaLayout.createSequentialGroup()
				.addComponent(lblDesde,javax.swing.GroupLayout.PREFERRED_SIZE,52,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(dateDesde,javax.swing.GroupLayout.PREFERRED_SIZE,111,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(lblHasta,javax.swing.GroupLayout.PREFERRED_SIZE,48,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(dateHasta,javax.swing.GroupLayout.PREFERRED_SIZE,103,javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(pnlParametrosBusquedaLayout.createSequentialGroup()
				.addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(rdbPartidasAbiertas,javax.swing.GroupLayout.DEFAULT_SIZE,556,Short.MAX_VALUE)
				.addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,false)
				.addGroup(javax.swing.GroupLayout.Alignment.LEADING,pnlParametrosBusquedaLayout.createSequentialGroup()
				.addComponent(lblFechaClave).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(datePartidasCompensadas,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,
							Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.LEADING,pnlParametrosBusquedaLayout.createSequentialGroup()
				.addComponent(lblPartidasAbiertas).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(datePartidasAbiertas,javax.swing.GroupLayout.PREFERRED_SIZE,109,javax.swing.GroupLayout.PREFERRED_SIZE))))
				.addGap(75, 75, 75))).addContainerGap()));
		pnlParametrosBusquedaLayout.setVerticalGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlParametrosBusquedaLayout.createSequentialGroup().addContainerGap()
				.addComponent(rdbPartidasAbiertas,javax.swing.GroupLayout.PREFERRED_SIZE,14,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(datePartidasAbiertas,0, 0,Short.MAX_VALUE).addComponent(lblPartidasAbiertas))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(rdbPartidasCompensadas,javax.swing.GroupLayout.PREFERRED_SIZE,14,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(datePartidasCompensadas,0, 0,Short.MAX_VALUE).addComponent(lblFechaClave))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(rdbTodasPartidas,javax.swing.GroupLayout.PREFERRED_SIZE,13,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(dateHasta,javax.swing.GroupLayout.PREFERRED_SIZE,14,Short.MAX_VALUE)
				.addComponent(dateDesde, 0,0,Short.MAX_VALUE)
				.addComponent(lblDesde,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
				.addComponent(lblHasta,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(btnBuscar,javax.swing.GroupLayout.PREFERRED_SIZE,23,javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));

		pnlBusqueda.add(pnlParametrosBusqueda, java.awt.BorderLayout.CENTER);

		pbnlTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 0, 4));
		pbnlTitulo.setLayout(new java.awt.BorderLayout());

		lblOpcionesBusqueda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		lblOpcionesBusqueda.setText("Opciones de búsqueda");
		pbnlTitulo.add(lblOpcionesBusqueda, java.awt.BorderLayout.CENTER);

		btnOcultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnMaximizar.png"))); // NOI18N
		btnOcultar.setBorder(null);
		btnOcultar.setContentAreaFilled(false);
		btnOcultar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOcultarActionPerformed(evt);
			}
		});
		pbnlTitulo.add(btnOcultar, java.awt.BorderLayout.LINE_END);

		jSeparator2.setBackground(new java.awt.Color(41, 101, 148));
		jSeparator2.setForeground(new java.awt.Color(41, 101, 148));
		jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 101, 148)));
		pbnlTitulo.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

		pnlBusqueda.add(pbnlTitulo, java.awt.BorderLayout.PAGE_START);

		getContentPane().add(pnlBusqueda, java.awt.BorderLayout.PAGE_START);

		pnlTabla.setLayout(new java.awt.BorderLayout());

		lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		lblTitulo.setText("Header");
		lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 1));
		pnlTabla.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

		tblPartidasIndividuales.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null },
								{ null, null, null, null },
								{ null, null, null, null },
								{ null, null, null, null } }, new String[] {
								"Title 1", "Title 2", "Title 3", "Title 4" }));
		scrPartidasIndividuales.setViewportView(tblPartidasIndividuales);

		pnlTabla.add(scrPartidasIndividuales, java.awt.BorderLayout.CENTER);

		getContentPane().add(pnlTabla, java.awt.BorderLayout.CENTER);
		
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnBuscarActionPerformed(evt);
			}
		});
		
		pack();
	}// </editor-fold>

	private void btnOcultarActionPerformed(java.awt.event.ActionEvent evt) {
		if (estaOculto) {
			btnOcultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnOcultar.png"))); // NOI18N
		} else {
			btnOcultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnMaximizar.png"))); // NOI18N
		}
		pnlParametrosBusqueda.setVisible(estaOculto);
		estaOculto = !estaOculto;
	}
	
	private void establecerColorDeFondoCalendario(JDateChooser dateChooser, Color background) {
       Component[] lc = dateChooser.getComponents();
       for (int i = 0; i < lc.length; i++) {
           Object object = lc[i];
           if(object instanceof JTextField) {
               ((JTextField) object).setBackground(background);
           }
       }
   }
	
	private void btnBuscarActionPerformed(ActionEvent evt){
		final DLocker bloqueador = new DLocker();
		Thread hilo = new Thread() {
			public void run() {
				try {
					modeloPartidasIndividuales.limpiar();
					BeanDato usuario = Promesa.datose.get(0);
					if (usuario.getStrModo().equals("1")) { //ONLINE} else {//OFFLINE}
						buscarPIOnLine();
					}else{
						buscarPIOffLine();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(Promesa.getInstance(),"No se realizó correctamente la conexión a SAP.","Error", JOptionPane.ERROR_MESSAGE);
				} finally {
					bloqueador.dispose();
				}
			}
		};
		hilo.start();
		bloqueador.setVisible(true);
	}
	
	private void buscarPIOnLine(){
		SCobranzas sCobranzas = new SCobranzas();
		String cc = Util.completarCeros(10,codigoCliente);
		if(rdbPartidasAbiertas.isSelected()){
			if(datePartidasAbiertas.getDate() != null){
				establecerColorDeFondoCalendario(datePartidasAbiertas, Color.white);
				String fechaHasta = Util.convierteFechaAFormatoYYYYMMdd(datePartidasAbiertas.getDate());
				List<PartidaIndividual> listaPartidaIndividual = sCobranzas.obtenerListaPartidaIndividual(cc, codigoVendedor, "X", "", "", "", fechaHasta);
				for (PartidaIndividual partidaIndividual : listaPartidaIndividual) {
					modeloPartidasIndividuales.agregarPartidaIndividual(partidaIndividual);
				}
				Util.setAnchoColumnas(tblPartidasIndividuales);
			}else{
				establecerColorDeFondoCalendario(datePartidasAbiertas, new Color(254, 205, 215));
				JOptionPane.showMessageDialog(Promesa.getInstance(),"Por favor, ingrese fecha de las partidas abiertas.","Error", JOptionPane.ERROR_MESSAGE);
			}
		}else if(rdbPartidasCompensadas.isSelected()){
			if(datePartidasCompensadas.getDate() != null){
				establecerColorDeFondoCalendario(datePartidasCompensadas, Color.white);
				String fechaHasta = Util.convierteFechaAFormatoYYYYMMdd(datePartidasCompensadas.getDate());
				List<PartidaIndividual> listaPartidaIndividual = sCobranzas.obtenerListaPartidaIndividual(cc, codigoVendedor, "", "X", "", "", fechaHasta);
				for (PartidaIndividual partidaIndividual : listaPartidaIndividual) {
					modeloPartidasIndividuales.agregarPartidaIndividual(partidaIndividual);
				}
				Util.setAnchoColumnas(tblPartidasIndividuales);
			}else{
				establecerColorDeFondoCalendario(datePartidasCompensadas, new Color(254, 205, 215));
				JOptionPane.showMessageDialog(Promesa.getInstance(),"Por favor, ingrese fecha de las partidas compensadas.","Error", JOptionPane.ERROR_MESSAGE);
			}
		}else if(rdbTodasPartidas.isSelected()){
			if(dateDesde.getDate() != null && dateHasta.getDate() != null){
				establecerColorDeFondoCalendario(dateDesde, Color.white);
				establecerColorDeFondoCalendario(dateHasta, Color.white);
				String fechaDesde = Util.convierteFechaAFormatoYYYYMMdd(dateDesde.getDate());
				String fechaHasta = Util.convierteFechaAFormatoYYYYMMdd(dateHasta.getDate());
				List<PartidaIndividual> listaPartidaIndividual = sCobranzas.obtenerListaPartidaIndividual(cc, codigoVendedor, "", "", "X", fechaDesde, fechaHasta);
				for (PartidaIndividual partidaIndividual : listaPartidaIndividual) {
					modeloPartidasIndividuales.agregarPartidaIndividual(partidaIndividual);
				}
				Util.setAnchoColumnas(tblPartidasIndividuales);
			}else{
				if(dateDesde.getDate() == null && dateHasta.getDate() == null){
					establecerColorDeFondoCalendario(dateDesde, new Color(254, 205, 215));
					establecerColorDeFondoCalendario(dateHasta, new Color(254, 205, 215));
				}else if(dateDesde.getDate() == null){
					establecerColorDeFondoCalendario(dateDesde, new Color(254, 205, 215));
					establecerColorDeFondoCalendario(dateHasta, Color.white);
				}else if(dateHasta.getDate() == null){
					establecerColorDeFondoCalendario(dateDesde, Color.white);
					establecerColorDeFondoCalendario(dateHasta, new Color(254, 205, 215));
				}
				JOptionPane.showMessageDialog(Promesa.getInstance(),"Por favor, ingrese las fechas de todas las partidas.","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void buscarPIOffLine(){
		if(rdbPartidasAbiertas.isSelected()){
			if(datePartidasAbiertas.getDate() != null){
				establecerColorDeFondoCalendario(datePartidasAbiertas, Color.white);
				String fechaHasta = Util.convierteFechaAFormatoYYYYMMdd(datePartidasAbiertas.getDate());
				SqlPartidaIndividualImpl sqlPartidaIndividualImpl = new SqlPartidaIndividualImpl();
				List<PartidaIndividual> listaPartidaIndividual = sqlPartidaIndividualImpl.obtenerListaPartidaIndividual(codigoCliente);
				for (PartidaIndividual partidaIndividual : listaPartidaIndividual) {
					if(Util.comparaFecha1MenorOIgualQueFecha2(Util.convierteCadenaDDMMYYYYAFecha(partidaIndividual.getFechaDocumento()), Util.convierteCadenaYYYYMMDDAFecha(fechaHasta))){
						modeloPartidasIndividuales.agregarPartidaIndividual(partidaIndividual);
					}
				}
				Util.setAnchoColumnas(tblPartidasIndividuales);
			}else{
				establecerColorDeFondoCalendario(datePartidasAbiertas, new Color(254, 205, 215));
				JOptionPane.showMessageDialog(Promesa.getInstance(),"Por favor, ingrese fecha de las partidas abiertas.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(Promesa.getInstance(),"Ud. se encuentra en modo OFFLINE, por favor, seleccione Partidas Abiertas e ingrese una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Variables declaration - do not modify
	private javax.swing.JButton btnBuscar;
	private javax.swing.ButtonGroup btnGrpOpcionesBusqueda;
	private javax.swing.JButton btnOcultar;
	private com.toedter.calendar.JDateChooser dateDesde;
	private com.toedter.calendar.JDateChooser dateHasta;
	private com.toedter.calendar.JDateChooser datePartidasAbiertas;
	private com.toedter.calendar.JDateChooser datePartidasCompensadas;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JLabel lblDesde;
	private javax.swing.JLabel lblFechaClave;
	private javax.swing.JLabel lblHasta;
	private javax.swing.JLabel lblOpcionesBusqueda;
	private javax.swing.JLabel lblPartidasAbiertas;
	private javax.swing.JLabel lblTitulo;
	private javax.swing.JPanel pbnlTitulo;
	private javax.swing.JPanel pnlBusqueda;
	private javax.swing.JPanel pnlParametrosBusqueda;
	private javax.swing.JPanel pnlTabla;
	private javax.swing.JRadioButton rdbPartidasAbiertas;
	private javax.swing.JRadioButton rdbPartidasCompensadas;
	private javax.swing.JRadioButton rdbTodasPartidas;
	private javax.swing.JScrollPane scrPartidasIndividuales;
	private javax.swing.JTable tblPartidasIndividuales;
	// End of variables declaration
}