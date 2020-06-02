package com.promesa.administracion.sql;

import java.util.List;

import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.sincronizacion.bean.BeanSincronizacion;

public interface SqlSincronizar {
	public void setListaSincronizar();
	public void setInsertarSincronizar(BeanSincronizar sincronizar); 
	public void setEliminarSincronizacion(BeanSincronizar Sincronizar);
	public List<BeanSincronizacion> getListaSincronizar();
	public boolean getInsertarSincronizar();
	public boolean getEliminarSincronizacion();
}
