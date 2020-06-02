package com.promesa.planificacion.sql;

import java.util.List;
import com.promesa.planificacion.bean.BeanEmpleado;

public interface SqlEmpleado {

	public void setListaVendedor(String idCliente);
	public	List<BeanEmpleado> getListaVendedor();
	public	void setListaEmpleado(BeanEmpleado empleado);	 
	public	List<BeanEmpleado> getListaEmpleado();	 
	public void setInsertarEmpleado(String idEmp, String nomEmp, String ID); /* SINCRONIZACIÓN */
	public void setInsertarEmpleadoM2(List<String> listaCliente);
	public boolean getInsertarEmpleado();			 
	public void setEliminarEmpleado();	 
	public boolean getEliminarEmpleado();			
	
}