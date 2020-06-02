package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.pedidos.bean.BeanTipoGestion;

public interface SqlComboTipoGestion {

	public void setListaTipoGestion();
	public List<BeanTipoGestion> getListaTipoGestion();
	//public int getCountRow();
	
}//End tipo Gestion
