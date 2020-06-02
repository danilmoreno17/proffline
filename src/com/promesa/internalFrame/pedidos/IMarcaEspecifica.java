package com.promesa.internalFrame.pedidos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.promesa.internalFrame.pedidos.custom.ItemMarcaEstrategica;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.Indicador;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.util.Util;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.UIManager;
import java.awt.Font;

public class IMarcaEspecifica extends JPanel {
	
	private static IMarcaEspecifica instance = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOjbetivo;
	private JTextField txtReal;
	private JTextField txtCumplimiento;
	private JPanel jpnPrincipal;
	private JPanel jpnBoton;
	private JPanel jpnMarcaEstrategica;
	private JPanel panel;
	
	private JScrollPane spMarcaEstrategica;
	
	private JLabel lblImagen;
	
	private Indicador indicador;
	
	private List<ItemMarcaEstrategica> items;
	
	private String cliente = "";
	private JTextField txtDiferencia;
	
	
	public static IMarcaEspecifica getIinstance(){
		synchronized (IMarcaEspecifica.class) {
			if(instance == null){
				instance =  new IMarcaEspecifica();
			}
		}
		return instance;
	}
	
	private IMarcaEspecifica() {
		this.initComponent();
	}
	
	private void initComponent(){
		setPreferredSize(new Dimension(180, 278));
		setMinimumSize(new Dimension(180, 69));
		setMaximumSize(new Dimension(180, 32767));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		jpnPrincipal = new JPanel();
		add(jpnPrincipal);
		jpnPrincipal.setLayout(new BorderLayout(0, 0));
		
		this.addPanelNorte();
		this.addPanelCenter();
		this.addPanelSur();
		
	}
	
	private void addPanelNorte(){
		jpnBoton = new JPanel();
		jpnPrincipal.add(jpnBoton, BorderLayout.NORTH);
		jpnBoton.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnRegresarMenu = new JButton("Regresar a Men\u00FA");
		btnRegresarMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IMarcaEspecifica.getIinstance().clearCamposMarcaEstrategica();
				//IMarcaEspecifica.getIinstance().clearCamposIndicadores();
				Promesa.getInstance().regresarAMenu();
			}
		});
		jpnBoton.add(btnRegresarMenu);
	}
	
	private void addPanelCenter(){
		JPanel jpnSecundario = new JPanel();
		jpnPrincipal.add(jpnSecundario, BorderLayout.CENTER);
		jpnSecundario.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator_1 = new JSeparator();
		jpnSecundario.add(separator_1, BorderLayout.NORTH);
		
		jpnMarcaEstrategica = 	new JPanel();
		jpnMarcaEstrategica.setBorder(new TitledBorder(null, "Marcas Estrategicas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jpnSecundario.add(jpnMarcaEstrategica, BorderLayout.CENTER);
		jpnMarcaEstrategica.setLayout(new BoxLayout(jpnMarcaEstrategica, BoxLayout.X_AXIS));
		
		spMarcaEstrategica = new JScrollPane();
		spMarcaEstrategica.setAutoscrolls(true);
		jpnMarcaEstrategica.add(spMarcaEstrategica);
		
		panel = new JPanel();
		panel.setAlignmentY(1.5f);
		spMarcaEstrategica.setViewportView(panel);
		panel.removeAll();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel jpnSeparador = new JPanel();
		jpnSecundario.add(jpnSeparador, BorderLayout.SOUTH);
		jpnSeparador.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSeparator separator_2 = new JSeparator();
		jpnSeparador.add(separator_2);
		separator_2.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 3, 0), null));
		
		JSeparator separator_3 = new JSeparator();
		jpnSeparador.add(separator_3);
	}
	
	private void addPanelSur(){
		JPanel jpnTercero = new JPanel();
		jpnPrincipal.add(jpnTercero, BorderLayout.SOUTH);
		
		
		//jpnTercero.setLayout(new GridLayout(1, 0, 0, 0));
		
		/*JPanel jpnIndicadores = new JPanel();
		jpnIndicadores.setFont(new Font("Verdana", Font.BOLD, 9));
		jpnIndicadores.setForeground(Color.BLACK);
		jpnIndicadores.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Negocio Seguro " + Util.getTrimestre(), TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		jpnTercero.add(jpnIndicadores);
		jpnIndicadores.setLayout(new BorderLayout(0, 0));
		
		JPanel jpnValores = new JPanel();
		jpnIndicadores.add(jpnValores, BorderLayout.CENTER);
		jpnValores.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel jpnEtiquetas = new JPanel();
		jpnValores.add(jpnEtiquetas);
		jpnEtiquetas.setLayout(new GridLayout(4, 0, 0, 0));
		
		JLabel lblObjetivo = new JLabel("Objetivo: ");
		lblObjetivo.setHorizontalAlignment(SwingConstants.RIGHT);
		jpnEtiquetas.add(lblObjetivo);
		
		JLabel lblReal = new JLabel("Real: ");
		lblReal.setHorizontalAlignment(SwingConstants.RIGHT);
		jpnEtiquetas.add(lblReal);
		
		JLabel lblCumplimiento = new JLabel("Cump:");
		lblCumplimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		jpnEtiquetas.add(lblCumplimiento);
		
		JLabel lblDiferencia = new JLabel("Diferencia:");
		lblDiferencia.setHorizontalAlignment(SwingConstants.RIGHT);
		jpnEtiquetas.add(lblDiferencia);
		
		JPanel jpnCantidad = new JPanel();
		jpnValores.add(jpnCantidad);
		jpnCantidad.setLayout(new GridLayout(4, 0, 0, 0));
		
		txtOjbetivo = new JTextField();
		txtOjbetivo.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOjbetivo.setEditable(false);
		jpnCantidad.add(txtOjbetivo);
		txtOjbetivo.setColumns(10);
		
		txtReal = new JTextField();
		txtReal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtReal.setEditable(false);
		jpnCantidad.add(txtReal);
		txtReal.setColumns(10);
		
		txtCumplimiento = new JTextField();
		txtCumplimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCumplimiento.setEditable(false);
		txtCumplimiento.setForeground(Color.WHITE);
		jpnCantidad.add(txtCumplimiento);
		txtCumplimiento.setColumns(10);
		
		txtDiferencia = new JTextField();
		txtDiferencia.setForeground(Color.WHITE);
		txtDiferencia.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDiferencia.setEditable(false);
		jpnCantidad.add(txtDiferencia);
		txtDiferencia.setColumns(10);
		*/
		JPanel jpnImagen = new JPanel();
		jpnTercero.add(jpnImagen);
		lblImagen = new JLabel("");
		jpnImagen.add(lblImagen);
	}
	static int e = 0;
	static Timer timer;
	TimerTask task;
	Toolkit toolkit;
	int beep=5;
	public void cargarImagen(String estado) {
		try {
			task.cancel();
			timer.cancel();
			timer.purge();
			}catch(Exception e) {
				
			}
		timer = new Timer();
		toolkit = Toolkit.getDefaultToolkit();
		if(estado.equals("R")) {
			task = new TimerTask() {
				@Override
				public void run() {
					if(beep>0) {
						try {
							lblImagen.setIcon(new  ImageIcon(this.getClass().getResource("/imagenes/ovalorojodark.png")));
							Thread.sleep(500);
							toolkit.beep();
							lblImagen.setIcon(new  ImageIcon(this.getClass().getResource("/imagenes/ovalorojo.png")));
							beep--;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}else {
						toolkit.beep();
						lblImagen.setIcon(new  ImageIcon(this.getClass().getResource("/imagenes/ovalorojo.png")));
						this.cancel();
					}
				}
			};
			
			timer.schedule(task, 0, 1000);
		}
		if(estado.equals("A")) {			
			lblImagen.setIcon(new  ImageIcon(this.getClass().getResource("/imagenes/ovaloverde.png")));
		}
	}
	public void addMarcaEstrategica(List<MarcaEstrategica> marcas){
		String estado = "";
		items = new ArrayList<ItemMarcaEstrategica>();
		for (MarcaEstrategica ms : marcas) {
			ItemMarcaEstrategica item = new ItemMarcaEstrategica();
			item.setValueTexto(ms);
			items.add(item);
			panel.add(item);
			if(item.getDblCumpli()<100) {
				estado = "R";
			}else if(!estado.equals("R")) {
				estado = "A";
			}
		}
		cargarImagen(estado);
		panel.updateUI();
	}
	
	public Indicador getIndicador() {
		return indicador;
	}


	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	
	public void setDataIndicadores(){
		txtOjbetivo.setText(indicador.getMonto());
		txtReal.setText(indicador.getAcumulado());
//		txtCumplimiento.setText(indicador.getAcumulado());
		if(indicador.getEstatus().equalsIgnoreCase("c")){
			lblImagen.setIcon(new ImageIcon(IMarcaEspecifica.class.getResource("/imagenes/blanco.png")));
		} else if(indicador.getEstatus().equalsIgnoreCase("b")){
			lblImagen.setIcon(new ImageIcon(IMarcaEspecifica.class.getResource("/imagenes/oro.png")));
		} else {
			lblImagen.setIcon(new ImageIcon(IMarcaEspecifica.class.getResource("/imagenes/plata.png")));
		}
	}
	
	public void actualizarValores(String marca, double valor){
		double totalAcum = 0.0;
		String estado = "";
		for (ItemMarcaEstrategica item : items) {
			if(item.getCodigoMarca().equalsIgnoreCase(marca)){
				totalAcum = item.getValorReal() + valor;
				item.setValorReal(Util.formatearNumero(totalAcum));
			}
			if(item.getDblCumpli()<35) {
				estado = "R";
			}else if((item.getDblCumpli()>=35&&item.getDblCumpli()<100)&&(!estado.equals("R"))) {
				estado = "A";
			}else if(!estado.equals("R")&&!estado.equals("A")){
				estado = "V";
			}
		}
		cargarImagen(estado);
		panel.updateUI();
	}
	
	private void actualizarIndicadores(){
		try {
			double objetivo = Double.parseDouble(txtOjbetivo.getText());
			double real = Double.parseDouble(txtReal.getText());
			double cump = 0;
			if(objetivo > 0){
				cump = (real / objetivo)*100;
			}
			double diferencia = objetivo - real;
			txtCumplimiento.setText(Util.formatearNumero(cump)+"%");
			txtDiferencia.setText(Util.formatearNumero(Math.abs(diferencia)));
			if(cump < 100){
				txtCumplimiento.setBackground(Color.RED);
			} else if(cump > 100) {
				txtCumplimiento.setBackground(Color.BLUE);
			} else {
				txtCumplimiento.setBackground(Color.GREEN);
			}
			if(real < objetivo){
				txtDiferencia.setBackground(Color.RED);
			} else if(real > objetivo){
				txtDiferencia.setBackground(Color.BLUE);
			} else {
				txtDiferencia.setBackground(Color.GREEN);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public List<MarcaEstrategica> getDatosMarcaEstrategica(){
		
		List<MarcaEstrategica> marcas = new ArrayList<MarcaEstrategica>();
		if(items != null && items.size() > 0){
			for (ItemMarcaEstrategica item : items) {
				MarcaEstrategica ms = new MarcaEstrategica();
				ms.setCodigoCliente(item.getCliente());
				ms.setMarca(item.getCodigoMarca());
				ms.setAcumulado("" + (item.getAcumulado()));
				ms.setPresupuesto(item.getPresupuesto());
				ms.setValor(""+item.getValorReal());
				marcas.add(ms);
			}
			return marcas;
		}
		return null;
	}
	
	public void clearCamposMarcaEstrategica(){
		beep=5;
		panel.removeAll();
		panel.updateUI();
	}
	
	public void clearCamposIndicadores(){
		txtOjbetivo.setText("");
		txtReal.setText("");
		txtCumplimiento.setText("");
	}
	
	public void actualizarCampoRealIndicador(){
		if(items != null && items.size() >0){
			double real = 0.0;
			try {
//				real = Double.parseDouble(txtReal.getText());
				real = Double.parseDouble(indicador.getAcumulado());
			} catch (Exception e) {
				// TODO: handle exception
				real = 0.0;
			}
			for (ItemMarcaEstrategica item : items) {
				real = real + item.getValorReal();
			}
			txtReal.setText(Util.formatearNumero(real));
			actualizarIndicadores();
		}
	}
	
	public void inicializarValoresVentaHoy(){
		if(items != null && items.size() > 0){
			for (ItemMarcaEstrategica item : items) {
				item.setValorReal("");
			}
		}
		
	}
}
