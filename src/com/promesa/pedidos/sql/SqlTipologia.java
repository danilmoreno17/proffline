package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.pedidos.bean.BeanTipologia;

public interface SqlTipologia {
	public void setListaTipologia();
	public List<BeanTipologia> getListaTipologia();
	public void setInsertarTipologia(List<BeanTipologia> listaBeanTipologia);
	public void setEliminarTipologia();
	public int getCountRow();
}
