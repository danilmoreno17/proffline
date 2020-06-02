package com.promesa.pedidos.bean;

public class BeanTipoGestion {
	
	private String bsar;
	private String descripcion;
	
	public String getBsar() {
		return bsar;
	}
	public void setBsar(String bsar) {
		this.bsar = bsar;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	@Override
	public String toString() {
		return descripcion;
	}

}
