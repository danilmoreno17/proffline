package com.promesa.pedidos.bean;

import java.util.List;

public class BeanCondicionComercial5x {

	private int intNivel;
	private String strClaseCond;
	private int intAcceso;
	private String strTablaCond;
	private int intPrioridad;
	

	private String strDivisionCom;
	private String strCatProd;
	private String strFamilia;
	private String strCliente;
	
	
	private String strUnidad;
	private Double dblImporte;
	private String strEscala;
	private String strNroRegCond;
	private String strNum;	
	private List<BeanCondicionComercialEscala> listEscalas;	
	
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
	public List<BeanCondicionComercialEscala> getListEscalas() {
		return listEscalas;
	}
	public void setListEscalas(List<BeanCondicionComercialEscala> listEscalas) {
		this.listEscalas = listEscalas;
	}	
	public String getStrDivisionCom() {
		return strDivisionCom;
	}
	public void setStrDivisionCom(String strDivisionCom) {
		this.strDivisionCom = strDivisionCom;
	}
	public String getStrCatProd() {
		return strCatProd;
	}
	public void setStrCatProd(String strCatProd) {
		this.strCatProd = strCatProd;
	}
	public String getStrFamilia() {
		return strFamilia;
	}
	public void setStrFamilia(String strFamilia) {
		this.strFamilia = strFamilia;
	}
	public String getStrCliente() {
		return strCliente;
	}
	public void setStrCliente(String strCliente) {
		this.strCliente = strCliente;
	}	
	@SuppressWarnings("unused")
	public Double getDblImporteEscala(Double dblCompara) {
		Double dblRetorno = new Double(0);
		if(listEscalas!=null && listEscalas.size()>0){
			Double dblLimiteInferior = new Double(0);
			Double dblLimiteSuperior = new Double(0);
			for (BeanCondicionComercialEscala beanEscala : listEscalas) {
				if(beanEscala.getStrTipoEscala().equals("V")){
					dblLimiteSuperior = new Double(beanEscala.getStrValorEscala());
				}
				else{
					dblLimiteSuperior = new Double(beanEscala.getStrCantEscala());
				}
				
				if(dblCompara.compareTo(dblLimiteInferior)>0 && dblCompara.compareTo(new Double(beanEscala.getStrValorEscala()))<=0){
					dblRetorno = new Double(beanEscala.getStrImporte());
					//dblLimiteInferior = new Double(beanEscala.getStrValorEscala());
				}
				else{
					//dblLimiteInferior = new Double(beanEscala.getStrValorEscala());
				}
				
				if(beanEscala.getStrTipoEscala().equals("V")){
					dblLimiteInferior = new Double(beanEscala.getStrValorEscala());
				}
				else{
					dblLimiteInferior = new Double(beanEscala.getStrCantEscala());
				}				
				
			}
			
		}
		
		return dblRetorno;
	}	
}
