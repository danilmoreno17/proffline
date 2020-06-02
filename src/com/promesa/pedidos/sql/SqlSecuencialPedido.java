package com.promesa.pedidos.sql;

import com.promesa.pedidos.bean.BeanSecuencia;

public interface SqlSecuencialPedido {
	
	
	public int getSecuencialPedido(String codigoVendedor);
	public void actualizarSecuencialPedido(String codigoVendedor, int secuencialPedido);
	public void insertarSecuenciaPorVendedor(BeanSecuencia bs) throws Exception;
}
