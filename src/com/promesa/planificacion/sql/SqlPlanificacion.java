package com.promesa.planificacion.sql;

import java.util.List;
import com.promesa.planificacion.bean.BeanPlanificacion;

public interface SqlPlanificacion {
	 public void setListaPlanificacion(BeanPlanificacion planificacion);	 
	 public List<BeanPlanificacion> getListaPlanificacion();	 
	 public String getIdPlan(String idCli);
	 public String setInsertarPlanificacion(BeanPlanificacion planificacion);	 
	 public boolean getInsertarPlanificacion();	 
	 public  void setActualizarPlanificacion(BeanPlanificacion planificacion);	 
	 public  boolean getActualizarPlanificacion();		 
	 public void setEliminarPlanificacion(BeanPlanificacion planificacion);	
	 public void setEliminarPlanificacion();
	 public boolean getEliminarPlanificacion();
}