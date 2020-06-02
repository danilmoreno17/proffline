package com.promesa.planificacion.bean;
import java.util.HashMap;

public class BeanClienteVendedorSupervisor {

	HashMap<String,String> cliente=null;
	HashMap<String,String> vendedor=null;
	HashMap<String,String> supervisor=null;
	HashMap<String,String> idCliVenSup=null;	
	
	public	BeanClienteVendedorSupervisor(HashMap<String,String> cliente, HashMap<String,String> vendedor,
			HashMap<String,String> supervisor, HashMap<String,String> idCliVenSup){		 
			
		this.cliente=cliente;
		this.vendedor=vendedor;
		this.supervisor=supervisor;
		this.idCliVenSup=idCliVenSup;				
	}
	
	public HashMap<String, String> getCliente() {
		return cliente;
	}

	public void setCliente(HashMap<String, String> cliente) {
		this.cliente = cliente;
	}

	public HashMap<String, String> getVendedor() {
		return vendedor;
	}

	public void setVendedor(HashMap<String, String> vendedor) {
		this.vendedor = vendedor;
	}

	public HashMap<String, String> getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(HashMap<String, String> supervisor) {
		this.supervisor = supervisor;
	}

	public HashMap<String, String> getIdCliVenSup() {
		return idCliVenSup;
	}

	public void setIdCliVenSup(HashMap<String, String> idCliVenSup) {
		this.idCliVenSup = idCliVenSup;
	}
}