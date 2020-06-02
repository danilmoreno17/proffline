package com.promesa.util;

import com.promesa.administracion.bean.*;
import com.promesa.planificacion.bean.BeanVisita;

public class GenerarId {

	private BeanRol rol = new BeanRol(); 
	private BeanUsuario usuario = new BeanUsuario();	
	private BeanDispositivo dispositivo = new BeanDispositivo();
	private BeanVisita visita = new BeanVisita();

	public void setIdRol(String id){
		int idRol = 0;
 		String base = "ROL-00000000";		  
 		idRol = Integer.parseInt(id.trim()); 
 		idRol++;		  
 		base = base.substring(0,base.length()-(idRol+"").length()) + idRol;  
 		rol.setStrIdRol(base);
	}
	
	public BeanRol getIdRol(){
		 return rol;	 
	}
	
	public void setIdUsuario(String id){
		int idUsuario = 0;
		String base = "USE-00000000";	  
		idUsuario = Integer.parseInt(id.trim()); 
		idUsuario++;	  
		base = base.substring(0,base.length()-(idUsuario+"").length()) + idUsuario;  
		usuario.setStrIdUsuario(base);
	}
	
	public BeanUsuario getIdUsuario(){
		 return usuario;	 
	}
	
	public void setIdDispositivo(String id){
		int idDispositivo = 0;
		String base = "DIS-00000000";	  
		idDispositivo = Integer.parseInt(id.trim()); 
		idDispositivo++; 	  
		base = base.substring(0,base.length()-(idDispositivo+"").length()) + idDispositivo;  
		dispositivo.setStrIdDispositivo(base);
	}
	
	public BeanDispositivo getIdDispositivo(){
		 return dispositivo;
	}
	
	public void setVisita(String id) {		
		int idVisita = 0;
		String base = "VIS-00000000";	  
		idVisita = Integer.parseInt(id.trim()); 
		idVisita++;	  
		base = base.substring(0,base.length()-(idVisita+"").length()) + idVisita;
	}
	
	public BeanVisita getVisita() {
		return visita;
	}
}