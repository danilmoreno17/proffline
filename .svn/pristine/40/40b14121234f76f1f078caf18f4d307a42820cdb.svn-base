package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.administracion.bean.DetalleSincronizacion;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.sincronizacion.bean.BeanSincronizacion;

public interface SqlSincronizacion {

	public List<BeanSincronizacion> listaSincronizacion();
	public void sincronizaCondicionExpedicion() throws Exception;
	public boolean sincronizaAgenda(BeanBuscarPedido param) throws Exception;
	public boolean sincronizaJerarquia() throws Exception;
	public boolean sincronizaMaterial() throws Exception;
	public List<BeanSincronizacion> listaHistorialSincronizacion(BeanSincronizacion b);
	public boolean sincronizaMaterialStock() throws Exception;
	public void sincronizaMaterialConsultaDinamica() throws Exception;
	public boolean sincronizaCombos() throws Exception;
	public boolean sincronizaCondicionesComerciales() throws Exception;
	public boolean sincronizaClaseMaterial() throws Exception;
	public boolean sincronizaTipologia() throws Exception;
	public boolean sincronizaBloqueoEntrega() throws Exception;
	public boolean sincronizaCondicionPago() throws Exception;
	public void sincronizaCliente(String idVendedor) throws Exception;
	public void setListaSincronizar();
	public boolean sincronizaSede() throws Exception;
	public void setInsertarSincronizar(BeanSincronizar Sincronizar);
	public void actualizarSincronizar(BeanSincronizar Sincronizar);
	public void setEliminarSincronizacion(BeanSincronizar Sincronizar);
	public void setActualizarSincronizacionDetalle(DetalleSincronizacion ds);
	public boolean getEliminarSincronizacion();
	public boolean getInsertarSincronizar();
	public List<BeanSincronizacion> getListaSincronizar();
	public List<BeanSincronizacion> listaSincronizacionPendientes();
}
