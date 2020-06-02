package com.promesa.administracion.sql;

import java.util.List;

import com.promesa.administracion.bean.DetalleSincronizacion;
import com.promesa.sincronizacion.bean.*;

public interface SqlSincronizacion {
	public List<BeanSincronizacion> listaSincronizacion();
	public boolean sincronizaUsuario();
	public boolean sincronizaRol();
	public boolean sincronizaUsuarioRol();
	public boolean sincronizaVista();
	public boolean sincronizaRolVista();
	public boolean sincronizaDispositivo();
	public void setActualizarSincronizacionDetalle(DetalleSincronizacion detalleSincronizacion);
}