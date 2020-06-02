package com.promesa.administracion.sql;

import java.util.List;
import com.promesa.administracion.bean.BeanVista;

public interface SqlVista {
	public void setListaVista(BeanVista vista);	 
	public List<BeanVista> getListaVista();	 
	public void setInsertarVista(BeanVista vista);	
	public void setEliminarVista();
	public boolean getInsertarVista();
	public boolean getEliminarVista();
}