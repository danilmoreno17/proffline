package com.promesa.internalFrame.pedidos.custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.promesa.factory.Factory;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.util.Util;

@SuppressWarnings("serial")
public class ItemMarcaEstrategica extends JPanel {
	
	private JLabel lblMarca;
	private JLabel lblCumplimiento;
	private JLabel lblPresupuesto;
	private JLabel lblAcumulado;
	private JLabel lblReal;
	
	private JTextField txtPresupuesto;
	private JTextField txtAcumulado;
	private JTextField txtReal;
	private JTextField txtCumplimiento;
	
	private JPanel pnlLabel;
	private JPanel pnlTexto;
	private JPanel pnlMarca;
	
	public Double dblCumpli;
	private MarcaEstrategica ms;
	
	public ItemMarcaEstrategica(){
		initComponent();
	}
	
	private void initComponent(){
		
		this.setLayout(new GridLayout(3, 0, 0, 0));
		setMaximumSize(new Dimension(150, 50));
		setMinimumSize(new Dimension(150, 50));
		setPreferredSize(new Dimension(150, 50));
		setBorder(new LineBorder(Color.blue));
		
//		lblMarca = new JLabel();
//		this.add(lblMarca);
		addPanelMarca();
		addPanelLabel();
		addPanelTexto();
		
	}
	
	private void addPanelMarca(){
		pnlMarca = new JPanel();
		this.add(pnlMarca);
		pnlMarca.setLayout(new GridLayout(0, 3, 0, 0));
		
		lblMarca = new JLabel();
		lblMarca.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarca.setFont(new Font("Arial Black", Font.PLAIN, 11));
		pnlMarca.add(lblMarca);
		
		lblCumplimiento = new JLabel("Cump.:");
		lblCumplimiento.setHorizontalAlignment(SwingConstants.CENTER);
		pnlMarca.add(lblCumplimiento);
		
		txtCumplimiento = createTextField();
		pnlMarca.add(txtCumplimiento);
		
	}
	
	private void addPanelLabel(){
		pnlLabel = new JPanel();
		this.add(pnlLabel);
		pnlLabel.setLayout(new GridLayout(0, 3, 0, 0));
		
		lblPresupuesto = new JLabel("Presup.");
		lblPresupuesto.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLabel.add(lblPresupuesto);
		
		lblAcumulado = new JLabel("Acm. Mes");
		lblAcumulado.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLabel.add(lblAcumulado);
		
		lblReal = new JLabel("Vta. Hoy");
		lblReal.setHorizontalAlignment(SwingConstants.CENTER);
		pnlLabel.add(lblReal);
	}
	
	private void addPanelTexto(){
		pnlTexto = new JPanel();
		this.add(pnlTexto);
		pnlTexto.setLayout(new GridLayout(0, 3, 0, 0));
		
		txtPresupuesto = createTextField();
		pnlTexto.add(txtPresupuesto);
		
		txtAcumulado = createTextField();
		pnlTexto.add(txtAcumulado);
		
		txtReal = createTextField();
		pnlTexto.add(txtReal);
	}
	
	private JTextField createTextField(){
		JTextField txt = new JTextField();
		txt.setHorizontalAlignment(SwingConstants.RIGHT);
		txt.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txt.setEditable(false);
		txt.setColumns(10);
		return txt;
	}
	
	public void setValueTexto(MarcaEstrategica ms){
		this.ms = ms;
		lblMarca.setText(getNombreMarca(ms.getMarca()));
		txtPresupuesto.setText(ms.getPresupuesto());
		txtAcumulado.setText(ms.getAcumulado());
		setCumplimiento();
	}
	
	private void setCumplimiento(){
		double presu = Double.parseDouble(txtPresupuesto.getText());
		double acum = Double.parseDouble(txtAcumulado.getText())+getValorReal();
		double cumpli = 0;
		if(presu > 0){
			cumpli = (acum / presu) * 100;
		}else {
			cumpli = 100;
		}
		dblCumpli = cumpli;
		txtCumplimiento.setForeground(Color.WHITE);
		if(cumpli < 100){
			txtCumplimiento.setBackground(Color.RED);
		} else if(cumpli > 100) {
			txtCumplimiento.setBackground(Color.BLUE);
		} else {
			txtCumplimiento.setBackground(Color.GREEN);
		}
		txtCumplimiento.setText(Util.formatearNumero(cumpli)+"%");
	}
	
	public Double getDblCumpli() {
		return dblCumpli;
	}

	public void setDblCumpli(Double dblCumpli) {
		this.dblCumpli = dblCumpli;
	}

	public double getValorReal(){
		try {
			return Double.parseDouble(txtReal.getText());
		} catch (Exception e) {
			// TODO: handle exception
			return 0.0;
		}
	}
	
	public void setValorReal(String valor){
		txtReal.setText(valor);
		setCumplimiento();
	}
	
	public String getMarca(){
		return lblMarca.getText();
	}
	
	private String getNombreMarca(String marca){
		return Factory.createSqlMarcaEstrategica().getNombreMarcaEstrategicaByMarca(marca);
	}
	
	public double getAcumulado(){
		return Double.parseDouble(txtAcumulado.getText());
	}
	
	public String getCliente(){
		return ms.getCodigoCliente();
	}
	
	public String getCodigoMarca(){
		return ms.getMarca();
	}
	
	public String getPresupuesto(){
		return txtPresupuesto.getText();
	}

}
