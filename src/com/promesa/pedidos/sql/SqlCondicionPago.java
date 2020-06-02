package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.pedidos.bean.BeanCondicionPago;

public interface SqlCondicionPago {

	public void setListaCondicionesPago();
	public List<BeanCondicionPago> getListaCondiciones();
	public void setInsertarCondicionesPago(List<BeanCondicionPago> listaBeanCondicionPago);
	public void setEliminarCondicionesPago();
	public void insertarCondicionesPagoDetalle(List<BeanCondicionPago> listaBeanCondicionPago);
	public void eliminarCondicionesPagoDetalle();
	public int getCountRow();
	public List<BeanCondicionPago> listarCondicionesPagoPorCliente(String strCodCond);
}
