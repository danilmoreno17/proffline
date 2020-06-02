package com.promesa.pedidos.bean;

import java.util.List;

public class BeanCondicionComercial1 {
	
	private int intNivel;
	private String strClaseCond;
	private int intAcceso;
	private String strTablaCond;
	private int intPrioridad;
	
	private int intPrioridadGrupo;
	private int intPrioridadInterna;
	private String strClaseMaterial;
	private String strCliente;
	private String strCondicionPago;
	private String strTipo;
	private Double dblDscto;

	private String strUnidad;
	private Double dblImporte;
	private String strEscala;
	private String strNroRegCond;
	private String strNum;
	
	public List<BeanClaseMaterial> getListaStrClaseMaterial() {
		return listaStrClaseMaterial;
	}

	public void setListaStrClaseMaterial(
			List<BeanClaseMaterial> listaStrClaseMaterial) {
		this.listaStrClaseMaterial = listaStrClaseMaterial;
	}
	private List<BeanClaseMaterial> listaStrClaseMaterial;

	public String getStrCondicionPago() {
		return strCondicionPago;
	}
	
	public void setStrCondicionPago(String strCondicionPago) {
		this.strCondicionPago = strCondicionPago;
	}
	
	public int getIntPrioridadGrupo() {
		return intPrioridadGrupo;
	}
	public void setIntPrioridadGrupo(int intPrioridadGrupo) {
		this.intPrioridadGrupo = intPrioridadGrupo;
	}
	public int getIntPrioridadInterna() {
		return intPrioridadInterna;
	}
	public void setIntPrioridadInterna(int intPrioridadInterna) {
		this.intPrioridadInterna = intPrioridadInterna;
	}
	public String getStrClaseMaterial() {
		return strClaseMaterial;
	}
	public void setStrClaseMaterial(String strClaseMaterial) {
		this.strClaseMaterial = strClaseMaterial;
	}
	public String getStrCliente() {
		return strCliente;
	}
	public void setStrCliente(String strCliente) {
		this.strCliente = strCliente;
	}
	public String getStrTipo() {
		return strTipo;
	}
	public void setStrTipo(String strTipo) {
		this.strTipo = strTipo;
	}
	public Double getDblDscto() {
		return dblDscto;
	}
	public void setDblDscto(Double dblDscto) {
		this.dblDscto = dblDscto;
	}
	
	
	public int getIntNivel() {
		return intNivel;
	}

	public void setIntNivel(int intNivel) {
		this.intNivel = intNivel;
	}

	public String getStrClaseCond() {
		return strClaseCond;
	}

	public void setStrClaseCond(String strClaseCond) {
		this.strClaseCond = strClaseCond;
	}

	public int getIntAcceso() {
		return intAcceso;
	}

	public void setIntAcceso(int intAcceso) {
		this.intAcceso = intAcceso;
	}

	public String getStrTablaCond() {
		return strTablaCond;
	}

	public void setStrTablaCond(String strTablaCond) {
		this.strTablaCond = strTablaCond;
	}

	public int getIntPrioridad() {
		return intPrioridad;
	}

	public void setIntPrioridad(int intPrioridad) {
		this.intPrioridad = intPrioridad;
	}	
	
	public String getStrUnidad() {
		return strUnidad;
	}

	public void setStrUnidad(String strUnidad) {
		this.strUnidad = strUnidad;
	}

	public Double getDblImporte() {
		return dblImporte;
	}

	public void setDblImporte(Double dblImporte) {
		this.dblImporte = dblImporte;
	}

	public String getStrEscala() {
		return strEscala;
	}

	public void setStrEscala(String strEscala) {
		this.strEscala = strEscala;
	}

	public String getStrNroRegCond() {
		return strNroRegCond;
	}

	public void setStrNroRegCond(String strNroRegCond) {
		this.strNroRegCond = strNroRegCond;
	}

	public String getStrNum() {
		return strNum;
	}

	public void setStrNum(String strNum) {
		this.strNum = strNum;
	}
}
