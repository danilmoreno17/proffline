package com.promesa.administracion.sql;
import java.util.List;
import com.promesa.administracion.bean.BeanSincronizar;

public interface SqlHistorialSincronizacion {
	public void listaHistorialSincronizacion(int id);
	public void setInsertarHistorialSincronizacion(BeanSincronizar Sincronizar);
	public void setEliminarHistorialSincronizacion(BeanSincronizar Sincronizar);
	public List<BeanSincronizar> getlistaHistorialSincronizacion();
	public boolean getEliminarHistorialSincronizacion();	  
	public boolean getInsertarHistorialSincronizacion();
}
