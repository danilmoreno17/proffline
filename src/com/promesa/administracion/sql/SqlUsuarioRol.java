package com.promesa.administracion.sql;

import java.util.List;
import com.promesa.administracion.bean.BeanUsuarioRol;

public interface SqlUsuarioRol {
	public void setListaUsuarioRol(BeanUsuarioRol usuarioRol);	
	public void setListaUsuarioRol();	 
	public void setInsertarUsuarioRol(BeanUsuarioRol usuarioRol);	 
	public void setActualizarUsuarioRol(BeanUsuarioRol usuarioRol);	 
	public void setEliminarUsuarioRol(BeanUsuarioRol usuarioRol);
	public void setEliminarUsuarioRol();
	public List<BeanUsuarioRol> getListaUsuarioRol();	 
	public boolean getInsertarUsuarioRol();	 
	public boolean getActualizarUsuarioRol();	 
	public boolean getEliminarUsuarioRol();
}