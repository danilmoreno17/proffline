package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.bean.BeanPedidosParaSap;

public interface SqlPedido {
	String insertarPedidos(BeanPedido pedido) throws Exception;
	String actualizaPedido(BeanPedido pedido) throws Exception;
	List<BeanPedidoHeader> listarPedidos(String strVendorId,String fechaInicio, String strFechaFinal);
	BeanPedidosParaSap listarPedidosParaSaps(String idPedidoHeader);
	void setActualizaPedidoHeaderPartnersDetalle(String id);
}
