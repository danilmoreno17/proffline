package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.administracion.bean.BeanRol;
import com.promesa.administracion.sql.SqlRol;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;
import com.promesa.util.GenerarId;

public class SqlRolImpll implements SqlRol {
	private String sqlRol=null;
	@SuppressWarnings("rawtypes")
	private HashMap column=new HashMap();
	@SuppressWarnings({ "unused", "rawtypes" })
	private HashMap getMap=new HashMap();
	@SuppressWarnings("unused")
	private String tipo[]=null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer,HashMap> mapResultado=new HashMap<Integer,HashMap>();
	@SuppressWarnings("unused")
	private BeanRol rol = null;
	private List<BeanRol> listaRol = null;
	private boolean resultado=false;
			
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaRol(BeanRol rol){
		column=new HashMap();	
		listaRol = new ArrayList<BeanRol>();
		column.put("String:0", "txtMandante");
		column.put("String:1", "txtIdRol");
		column.put("String:2", "txtNombreRol");
		sqlRol = "SELECT  " + " txtMandante, " + " txtIdRol, " + " txtNombreRol  " + " FROM  PROFFLINE_TB_ROL ";		 
		ResultExecuteQuery   resultExecuteQuery = new ResultExecuteQuery(sqlRol,column, Constante.BD_SYNC);  
		mapResultado= resultExecuteQuery.getMap();
		HashMap res=null;		 
		for(int i=0;i<mapResultado.size();i++){
			rol = new BeanRol();
			res = (HashMap)mapResultado.get(i);
			rol.setStrIdRol(res.get("txtIdRol").toString()); 
			rol.setStrMandante(res.get("txtMandante").toString()); 
			rol.setStrNomRol(res.get("txtNombreRol").toString());
			listaRol.add(rol); 
		}							
	}
	
	/* MÉTODO QUE VERIFICA SI EXISTE O NO EL ROL */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verficaRol(String rol){
		boolean resultado = false;
		column = new HashMap();	
		String nombre = Constante.VACIO;		
		column.put("String:0", NOMBREROL);		
		sqlRol = "SELECT "+	NOMBREROL + " FROM "+TABLA_ROL+" WHERE "+NOMBREROL+" = '"+rol+"'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlRol,column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if(mapResultado.size() > 0){			
			res = (HashMap)mapResultado.get(0);			
			nombre = res.get(NOMBREROL).toString();			
		}				
		if(!nombre.equals(Constante.VACIO)){
			resultado = true;
		}
		return resultado;
	}
		
	public List<BeanRol> getListaRol(){
		return listaRol;
	}
	
	public void setInsertarRol(BeanRol rol){		 
		sqlRol = "INSERT INTO  PROFFLINE_TB_ROL (txtMandante, txtIdRol, txtNombreRol) "+ "VALUES ('"+rol.getStrMandante()+"','"+ rol.getStrIdRol()+"','"+ rol.getStrNomRol()+"') ";		
		ResultExecute   resultExecute = new ResultExecute(sqlRol, "Roles", Constante.BD_SYNC);  
		resultado= resultExecute.isFlag();							
	}
	 
	 public boolean getInsertarRol(){
		 return resultado;
	 }
	 
	 public void setActualizarRol(BeanRol rol){
		 sqlRol = " UPDATE  "+ " PROFFLINE_TB_ROL  "+ " SET txtNombreRol='"+rol.getStrNomRol()+"' "+  " WHERE txtIdRol='"+rol.getStrIdRol()+"' ";    			 
		 ResultExecute   resultExecute = new ResultExecute(sqlRol, "Roles", Constante.BD_SYNC);  
		 resultado= resultExecute.isFlag();							
	 }
	 
	 public void setSincronizadoRol(String idRol){
		 sqlRol = " UPDATE  " + " PROFFLINE_TB_ROL  " + " WHERE txtIdRol = '" + idRol + "'";		
		ResultExecute resultExecute = new ResultExecute(sqlRol, "Roles", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();		   
	}
	 
	 public boolean getActualizarRol(){
		 return resultado;
	 }
	 
	 public void setEliminarRol(BeanRol rol){
		 sqlRol = " UPDATE  " + " PROFFLINE_TB_ROL " + " WHERE txtIdRol='"+rol.getStrIdRol()+"' ";		      
		 ResultExecute   resultExecute = new ResultExecute(sqlRol, "Roles", Constante.BD_SYNC);  
		 resultado= resultExecute.isFlag();							
	 }
	 
	 public void setEliminarRol(){
		 sqlRol = " DELETE FROM PROFFLINE_TB_ROL;";		 
		 ResultExecute   resultExecute = new ResultExecute(sqlRol, "Roles", Constante.BD_SYNC);  
		 resultado= resultExecute.isFlag();							
	 }
	 
	 public boolean getEliminarRol(){
		 return resultado;
	 }
	 
	 @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	 public BeanRol getIdRol(){
		 int id;
		 BeanRol rol = new BeanRol();
		 GenerarId generaId = new GenerarId();  
		 column = new HashMap();	
 		 column.put("String:0", "ID"); 		
 		 sqlRol = " SELECT COUNT(*) AS ID  FROM  PROFFLINE_TB_ROL"; 		
 		 ResultExecuteQuery   resultExecuteQuery = new ResultExecuteQuery(sqlRol,column,Constante.BD_SYNC);  
 		 mapResultado= resultExecuteQuery.getMap();
 		 HashMap res = null;
 		 res = (HashMap)mapResultado.get(0); 		
 		 if(!(res.get("ID").toString().trim()).equals("0")){
 			 sqlRol = " SELECT SUBSTR(txtIdRol,5,LENGTH(txtIdRol)) AS ID " + " FROM  PROFFLINE_TB_ROL ORDER BY txtIdRol DESC LIMIT 1 "; 			      
 			 resultExecuteQuery = new ResultExecuteQuery(sqlRol,column,Constante.BD_SYNC);  
 			 mapResultado= resultExecuteQuery.getMap();
 			 res = (HashMap)mapResultado.get(0);  
 		 }  
 		 generaId.setIdRol(res.get("ID").toString());
 		 rol.setStrIdRol(generaId.getIdRol().getStrIdRol());	
 		 return rol;					
	 }
	 
	 private static String TABLA_ROL = "PROFFLINE_TB_ROL";
	 private static String NOMBREROL = "txtNombreRol";
}