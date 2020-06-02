package com.promesa.planificacion.sql;

import java.util.List;

import com.promesa.cobranzas.bean.Presupuesto;
import com.promesa.planificacion.bean.BeanCliente;

public interface SqlCliente {
	public void setListaCliente(BeanCliente cliente);
	public void clientesXvendedor(String vendedor); 
	public void clienteXvendedor(String vendedor, String Cliente); 
	public List<BeanCliente> getListaCliente();
	public void setInsertarCliente(BeanCliente cliente);
	public void setInsertarCliente(List<BeanCliente> listaCliente);
	public void setInsertarCliente2(String strCodVendedor, List<BeanCliente> listaCliente);
	public void setInsertarClienteM2(List<String> listaCliente);
	public boolean getInsertarCliente();
	public void setEliminarCliente();
	public void setEliminarCliente(String idVendedor);
	public boolean getEliminarCliente();
	public int getCountRow();
	public void setListaCliente();
	public String obtenerMarcaBloqueoAlmacen(String idCliente);
	public BeanCliente obtenerCliente(String idCliente);
	public String obtenerCondicionPagoPorDefecto(String codigo);
	public void insertPresupuesto(List<Presupuesto> presupuestos);
	public Presupuesto getPresupestoByCliente(String codigoCliente);
	public void updatePresupuesto(Presupuesto pre);
}