package com.promesa.planificacion.sql;

import java.util.List;

import com.promesa.planificacion.bean.BeanVisita;

public interface SqlVisita {
	 public	 void setListaVisita(BeanVisita visita);	 
	 public	 List<BeanVisita> getListaVisita();	 
	 public void setInsertarVisita(BeanVisita vista);	 
	 public boolean getInsertarVisita();	 
	 public void comparaFecha(BeanVisita visita);
	 public void actualizaStatus(BeanVisita visita,String fecha);
	 public void eliminarXStatus(BeanVisita visita);
	 public boolean getEliminaXStatus();
	 public void setEliminarVisita(BeanVisita visita);	 
	 public boolean getEliminarVisita(); 	 		
}
