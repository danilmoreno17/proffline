package com.promesa.pedidos.sql;

import java.util.List;
import com.promesa.pedidos.bean.BeanJerarquia;

public interface SqlJerarquia {
	public String getMarcaVendedor(String marca);
	public void setListaJerarquia();
	public List<BeanJerarquia> getListaJerarquia();
	public void setInsertarJerarquia(List<BeanJerarquia> listaBeanJerarquia);
	public void setEliminarJerarquia();	
	public int getCountRow();
}
