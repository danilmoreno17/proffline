package com.promesa.pedidos.bean;

import java.util.List;



public class BeanMarcaIndicador {
	
	private List<MarcaEstrategica> marcas;
	private List<Indicador> indicadores;
	private List<NombreMarcaEstrategica> nombres;
	private List<MarcaVendedor> marcavendedor;
	public List<MarcaVendedor> getMarcavendedor() {
		return marcavendedor;
	}

	public void setMarcavendedor(List<MarcaVendedor> marcavendedor) {
		this.marcavendedor = marcavendedor;
	}
	public List<MarcaEstrategica> getMarcas() {
		return marcas;
	}
	public void setMarcas(List<MarcaEstrategica> marcas) {
		this.marcas = marcas;
	}
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	public List<NombreMarcaEstrategica> getNombres() {
		return nombres;
	}
	public void setNombres(List<NombreMarcaEstrategica> nombres) {
		this.nombres = nombres;
	}

}
