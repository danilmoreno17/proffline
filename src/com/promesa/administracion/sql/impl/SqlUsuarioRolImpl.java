package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.administracion.bean.*;
import com.promesa.administracion.sql.SqlUsuarioRol;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlUsuarioRolImpl implements SqlUsuarioRol{
	private String sqlUsuarioRol = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("unused")
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer,HashMap> mapResultado = new HashMap<Integer,HashMap>();
	private BeanRol rol = null;
	private BeanUsuario usuario = null;
	@SuppressWarnings("unused")
	private BeanUsuarioRol usuarioRol = null;
	private List<BeanUsuarioRol> listaUsuarioRol = null;
	private boolean resultado = false;
	private ResultExecuteQuery resultExecuteQuery = null;
			
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaUsuarioRol(BeanUsuarioRol usuarioRol){
		column = new HashMap();	
		listaUsuarioRol = new ArrayList<BeanUsuarioRol>();
		usuario = usuarioRol.getUsuario();
		BeanRol rol = usuarioRol.getRol();
		BeanUsuario usuario = usuarioRol.getUsuario();
		BeanUsuarioRol usuarioRoll = null;	  
		String paramIdUsuario=null;		
		if(usuario.getStrIdUsuario()!=null){
			paramIdUsuario=" WHERE PUR.txtIdUsuario='"+usuario.getStrIdUsuario().trim()+"')";
		}		
		column.put("String:0", "txtIdRol");
		column.put("String:1", "txtNombreRol");
		column.put("String:2", "txtIdUsuario");		
		column.put("String:4", "txtIdentificacion");
		column.put("String:5", "txtClaveUsuario");		
		sqlUsuarioRol = "SELECT PR1.txtIdRol, PR1.txtNombreRol,TM.txtIdUsuario,TM.txtIdentificacion,TM.txtClaveUsuario FROM PROFFLINE_TB_ROL PR1 "+
					"LEFT JOIN  (SELECT  PR.txtIdRol, txtNombreRol,PUR.txtIdUsuario,PU.txtIdentificacion,PU.txtClaveUsuario "+
					"FROM  PROFFLINE_TB_ROL PR "+ "LEFT JOIN PROFFLINE_TB_USUARIO_ROL PUR ON  PR.txtIdRoL=PUR.txtIdRoL "+
					"LEFT JOIN PROFFLINE_TB_USUARIO PU ON PUR.txtIdUsuario=PU.txtIdUsuario "+
					paramIdUsuario + " TM  ON  TM.txtIdRoL=PR1.txtIdRoL ";
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuarioRol,column,Constante.BD_SYNC);  
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;		 
		for(int i = 0; i < mapResultado.size(); i++){
			rol = new BeanRol();
			usuario = new BeanUsuario();
			usuarioRoll = new BeanUsuarioRol();		   
			this.usuarioRol = new BeanUsuarioRol();		   
			res = (HashMap)mapResultado.get(i);		   
			rol.setStrIdRol(res.get("txtIdRol").toString());
			rol.setStrNomRol(res.get("txtNombreRol").toString());
			if(res.get("txtIdUsuario") != null){
				rol.setStrCheck("1");			   
				usuario.setStrIdUsuario(res.get("txtIdUsuario").toString());
			}else{
				rol.setStrCheck(null); 
				usuario.setStrIdUsuario(null);
			}				
			usuario.setStrClaUsuario(getData(res.get("txtIdentificacion")));
			usuario.setStrIdentificacion(getData(res.get("txtClaveUsuario")));			
			usuarioRoll.setRol(rol);
			usuarioRoll.setUsuario(usuario);		
		    listaUsuarioRol.add(usuarioRoll);
		}					
	}
	
	 public List<BeanUsuarioRol> getListaUsuarioRol(){
		 return listaUsuarioRol;
	 }
	 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void setListaUsuarioRol(){
		 column = new HashMap();
		 listaUsuarioRol = new ArrayList<BeanUsuarioRol>();
		 BeanUsuarioRol usuarioRoll = null;		 
		 column.put("String:0", "txtMandante");
		 column.put("String:1", "txtIdUsuario");
		 column.put("String:2", "txtIdRol");		 
		 sqlUsuarioRol = "SELECT txtMandante,txtIdUsuario,txtIdRol FROM PROFFLINE_TB_USUARIO_ROL ";	
		 resultExecuteQuery = new ResultExecuteQuery(sqlUsuarioRol,column,Constante.BD_SYNC);  
		 mapResultado = resultExecuteQuery.getMap();
		 HashMap res = null;		 
		 for(int i = 0; i < mapResultado.size(); i++){
			 rol = new BeanRol();
			 usuario = new BeanUsuario();
			 usuarioRoll = new BeanUsuarioRol();		   
			 this.usuarioRol = new BeanUsuarioRol();		   
			 res = (HashMap)mapResultado.get(i);		   
			 rol.setStrIdRol(res.get("txtIdRol").toString());
			 	if(res.get("txtIdUsuario") != null){
			 		rol.setStrCheck("1");			   
			 		this.usuario.setStrIdUsuario(res.get("txtIdUsuario").toString());
			 	}else{
			 		rol.setStrCheck(null); 
			 		usuario.setStrIdUsuario(null);
			 	}
			 usuarioRoll.setRol(rol);
			 usuarioRoll.setUsuario(usuario);
			 usuarioRoll.setStrMandante(res.get("txtMandante").toString());
			 listaUsuarioRol.add(usuarioRoll);
		 }
	 }
	 
	 public void setInsertarUsuarioRol(BeanUsuarioRol usuarioRol){			 
		 this.rol = usuarioRol.getRol();
		 this.usuario= usuarioRol.getUsuario();		 
		 sqlUsuarioRol ="INSERT INTO PROFFLINE_TB_USUARIO_ROL (txtMandante,txtIdUsuario,txtIdRoL) "+ 
		 			"VALUES ('"+this.usuario.getStrMandante()+"','"+this.usuario.getStrIdUsuario()+"','"+this.rol.getStrIdRol()+"') ";	  
		 ResultExecute    resultExecute = new ResultExecute(sqlUsuarioRol, "Roles de usuario", Constante.BD_SYNC);  
		 resultado= resultExecute.isFlag();							
	 }
	 
	 public boolean getInsertarUsuarioRol(){
		    return resultado;
	 }
	 
	 public void setActualizarUsuarioRol(BeanUsuarioRol usuarioRol){
		 this.rol = usuarioRol.getRol();
		 this.usuario= usuarioRol.getUsuario();		 
		 sqlUsuarioRol = " UPDATE  "+ " PROFFLINE_TB_USUARIO_ROL  "+ " SET txtIdRoL='"+rol.getStrIdRol()+"', "+ " txtSincronizado = 2 "+ " WHERE txtIdUsuario='"+usuario.getStrIdUsuario()+"' ";		      
		 ResultExecute resultExecute = new ResultExecute(sqlUsuarioRol, "Roles de usuario", Constante.BD_SYNC);  
		 resultado= resultExecute.isFlag();							
	 }
	 
	 public void setSincronizadoUsuarioRol(String idUsr){
		sqlUsuarioRol = " UPDATE  " + " PROFFLINE_TB_USUARIO_ROL  " + " SET txtSincronizado = 2 " + " WHERE txtIdUsuario = '" + idUsr + "'";		
		ResultExecute resultExecute = new ResultExecute(sqlUsuarioRol, "Roles de usuario", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();		   
	}
	 
	 public boolean getActualizarUsuarioRol(){
		 return resultado;
	 }
	 
	 public void setEliminarUsuarioRol(BeanUsuarioRol usuarioRol){
		 this.rol = usuarioRol.getRol();
		 this.usuario= usuarioRol.getUsuario();		  
		 sqlUsuarioRol = "DELETE FROM " + "PROFFLINE_TB_USUARIO_ROL " + "WHERE txtIdUsuario = '" + usuario.getStrIdUsuario() + "'";		 
		 ResultExecute   resultExecute = new ResultExecute(sqlUsuarioRol, "Roles de usuario", Constante.BD_SYNC);  
		 resultado= resultExecute.isFlag();							
	 }
	 
	 public void setEliminarUsuarioRol(){
		 sqlUsuarioRol = "DELETE FROM PROFFLINE_TB_USUARIO_ROL;";		 
		 ResultExecute   resultExecute = new ResultExecute(sqlUsuarioRol, "Roles de usuario", Constante.BD_SYNC);  
		 resultado= resultExecute.isFlag();	 
	 }
	 
	 public boolean getEliminarUsuarioRol(){
		 return resultado;
	 }	
	 
	 public String getData(Object obj){
		String result="";			 
		if(obj!=null){
			result=obj.toString();
		} 
		return  result;
	 }
}