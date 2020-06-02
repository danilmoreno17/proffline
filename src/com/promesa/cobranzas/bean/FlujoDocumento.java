package com.promesa.cobranzas.bean;

public class FlujoDocumento {
	private String numeroDocumento;
	private String ei;
	private String estado;
	private boolean isRoot;
	private String codigo;
	private String nivel;
	private String codigoPadre;

	public FlujoDocumento(String numeroDocumento, String ei, String estado, boolean isRoot, String codigo, String nivel, String codigoPadre) {
		super();
		this.numeroDocumento = numeroDocumento;
		this.ei = ei;
		this.estado = estado;
		this.isRoot = isRoot;
		this.codigo = codigo;
		this.nivel = nivel;
		this.codigoPadre = codigoPadre;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getEi() {
		return ei;
	}
	public void setEi(String ei) {
		this.ei = ei;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	public String getCodigoPadre() {
		return codigoPadre;
	}
	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}
	@Override
	public String toString() {
		return "FlujoDocumento [numeroDocumento=" + numeroDocumento + ", ei="
				+ ei + ", estado=" + estado + ", isRoot=" + isRoot
				+ ", codigo=" + codigo + ", nivel=" + nivel + ", codigoPadre=" + codigoPadre + "]";
	}
}
