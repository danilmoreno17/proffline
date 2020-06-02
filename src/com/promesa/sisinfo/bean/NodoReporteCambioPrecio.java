package com.promesa.sisinfo.bean;

import java.util.List;

public class NodoReporteCambioPrecio {
	private String codigo;
	private String descripcion;
	private String contenido;
	private List<NodoReporteCambioPrecio> nodos;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public List<NodoReporteCambioPrecio> getNodos() {
		return nodos;
	}

	public void setNodos(List<NodoReporteCambioPrecio> nodos) {
		this.nodos = nodos;
	}
}