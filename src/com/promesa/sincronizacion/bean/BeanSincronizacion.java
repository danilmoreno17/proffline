package com.promesa.sincronizacion.bean;

public class BeanSincronizacion {
	private String strIdeSinc;
	private String strInfSinc;
	private String strNumCantReg;
	private String strFecHor;
	private String strNumTie;
	private String strHoraIni;
	private String strFrecuencia;
	private boolean seleccionado;

	public String getStrHoraIni() {
		return strHoraIni;
	}

	public void setStrHoraIni(String strHoraIni) {
		this.strHoraIni = strHoraIni;
	}

	public String getStrFrecuencia() {
		return strFrecuencia;
	}

	public void setStrFrecuencia(String strFrecuencia) {
		this.strFrecuencia = strFrecuencia;
	}

	public String getStrIdeSinc() {
		return strIdeSinc;
	}

	public void setStrIdeSinc(String strIdeSinc) {
		this.strIdeSinc = strIdeSinc;
	}

	public String getStrInfSinc() {
		return strInfSinc;
	}

	public void setStrInfSinc(String strInfSinc) {
		this.strInfSinc = strInfSinc;
	}

	public String getStrNumCantReg() {
		return strNumCantReg;
	}

	public void setStrNumCantReg(String strNumCantReg) {
		this.strNumCantReg = strNumCantReg;
	}

	public String getStrFecHor() {
		return strFecHor;
	}

	public void setStrFecHor(String strFecHor) {
		this.strFecHor = strFecHor;
	}

	public String getStrNumTie() {
		return strNumTie;
	}

	public void setStrNumTie(String strNumTie) {
		this.strNumTie = strNumTie;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	@Override
	public String toString() {
		return strInfSinc;
	}
}