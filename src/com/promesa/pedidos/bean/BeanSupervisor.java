package com.promesa.pedidos.bean;

public class BeanSupervisor {

	private String txtIdVendedor;
	private String txtIdSupervisor;

	public String getTxtIdVendedor() {
		return txtIdVendedor;
	}

	public void setTxtIdVendedor(String txtIdVendedor) {
		this.txtIdVendedor = txtIdVendedor;
	}

	public String getTxtIdSupervisor() {
		return txtIdSupervisor;
	}

	public void setTxtIdSupervisor(String txtIdSupervisor) {
		this.txtIdSupervisor = txtIdSupervisor;
	}

	@Override
	public String toString() {
		return "BeanSupervisor [txtIdVendedor=" + txtIdVendedor
				+ ", txtIdSupervisor=" + txtIdSupervisor + "]";
	}

}
