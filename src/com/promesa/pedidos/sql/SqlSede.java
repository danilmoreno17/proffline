package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.pedidos.bean.BeanSede;

public interface SqlSede {
	public void setListarSede();
	public void setInsertarSede(List<BeanSede> lists);
	public void setEliminarSede();
	public boolean isEliminarSede();
	public List<BeanSede> getListaSede();
	public int getCountRow();
}
