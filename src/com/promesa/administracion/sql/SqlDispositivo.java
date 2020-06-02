package com.promesa.administracion.sql;

import java.util.List;
import com.promesa.administracion.bean.BeanDispositivo;

public interface SqlDispositivo {
	public void setListaDispositivo(BeanDispositivo disp);	 
	public List<BeanDispositivo> getListaDispositivo();	 
	public void setInsertarDispositivo(BeanDispositivo dispositivo);	 
	public boolean getInsertarDispositivo();	 
	public void setActualizarDispositivo(BeanDispositivo dispositivo);	 
	public boolean getActualizarDispositivo();		 
	public void setEliminarDispositivo(BeanDispositivo dispositivo);
	public void setEliminarDispositivo();
	public boolean getEliminarDispositivo();	 
	public BeanDispositivo getIdDispositivo();	 
	public void setBloquearDispositivo(String txtIdDispositivo);	 
	public boolean getBloquearDispositivo();	 
	public boolean yaTieneDispositivo(String idUsuario,String disRel);
	public String verificarImpresora(String strUsuarioVendedor, String strDispositivoReal);
}