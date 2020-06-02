package com.promesa.administracion.sql;

import java.util.List;
import com.promesa.administracion.bean.BeanUsuario;
import com.promesa.administracion.bean.BeanUsuarioRol;

public interface SqlUsuario {
	public List<BeanUsuarioRol> accesoUsuario(String nomUsu, String claUsu);
	@SuppressWarnings("rawtypes")
	public List getVistasPorUsuarioRol(String nomUsu, String claUsu);
	public void setListaUsuario();
	public void setListaUsuario(BeanUsuario usr); 
	public void setInsertarUsuario(BeanUsuario usuario); 
	public void setActualizarUsuario(BeanUsuario usuario); 
	public void setEliminarUsuario(BeanUsuario usuario); 
	public void setEliminarUsuario();
	public List<BeanUsuario> getListaUsuario(); 
	public boolean getInsertarUsuario(); 
	public boolean getActualizarUsuario(); 
	public boolean getEliminarUsuario();
	public boolean getActualizarClaveUsuario();
	public BeanUsuario getIdUsuario();
	public String getIdUsuarioPorNombreUsuario(String nomUsu);
	public String getIdUsuariosPorNombreUsuario(String nomUsu);
	public void desbloquearUsuario(String IdUsu);
	public void setActualizarClaveUsuario(String claUsu, String fecUlAcUsu, String horUlAcUsu, String nomUsu);
	public void setActualizarNumIntentoUsuario(int numIntento, String nomUsu);
	public int getNumIntentoPorNombreUsuario(String nomUsu);
	public void bloquearUsuario(String IdUsu);
	public String getCodigoVendedor(String strUsuario, String strClave);
}