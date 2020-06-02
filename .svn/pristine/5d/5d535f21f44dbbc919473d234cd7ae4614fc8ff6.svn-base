package com.promesa.planificacion.sql;

import java.util.List;

import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.administracion.bean.DetalleSincronizacion;
import com.promesa.sincronizacion.bean.BeanSincronizacion;

public interface SqlSincronizacion {
	public List<BeanSincronizacion> listaSincronizacion();
	public void sF(String fechaInicio);
	public boolean sincronizaFeriado();
	public void sEC(String fechaInicio);
	public boolean sincronizaEmpleadoCliente(String idSup);
	public void setActualizarSincronizacionDetalle(DetalleSincronizacion ds);
	public void setEliminarSincronizacion(BeanSincronizar Sincronizar);
	public int filasTabla(String tabla);
	public void setInsertarSincronizar(BeanSincronizar Sincronizar);
}