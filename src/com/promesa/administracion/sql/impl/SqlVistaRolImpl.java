package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.administracion.bean.*;
import com.promesa.administracion.sql.SqlVistaRol;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlVistaRolImpl implements SqlVistaRol{

	private String sqlVistaRol=null;
	@SuppressWarnings("rawtypes")
	private HashMap column=new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap=new HashMap();
	@SuppressWarnings("unused")
	private String tipo[]=null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer,HashMap> mapResultado=new HashMap<Integer,HashMap>();
	private BeanVista vista = null;
	private BeanRol rol = null;
	private BeanRolVista vistaRol = null;
	private List<BeanRolVista> listaVistaRol = null;
	private boolean resultado=false;
	private ResultExecuteQuery   resultExecuteQuery=null;
			
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaVistaRol(BeanRolVista vistaRol){
		column = new HashMap();	
		listaVistaRol = new ArrayList<BeanRolVista>();	
		BeanRol rol = vistaRol.getRol();		
		column.put("String:1", "txtIdRol"); 
		column.put("String:2", "txtNombreVista");   
		column.put("String:3", "txtDescVista");		
		sqlVistaRol = "SELECT PVR.txtIdRol, " + "       PV.txtNombreVista, " + "       PV.txtDescVista "+					  
					  "FROM  PROFFLINE_TB_VISTA_ROL PVR "+ "INNER JOIN PROFFLINE_TB_VISTA PV "+ "ON PVR.txtNombreVista = PV .txtNombreVista "+
					  "INNER JOIN PROFFLINE_TB_ROL  PR "+ "ON PVR.txtIdRol = PR.txtIdRol ";		
		if(rol.getStrNomRol()!=null){
			sqlVistaRol +=  "WHERE PR.txtNombreRol='"+rol.getStrNomRol()+"'";
		}		
		resultExecuteQuery = new ResultExecuteQuery(sqlVistaRol,column,Constante.BD_SYNC);  
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;		 
		for(int i=0;i<mapResultado.size();i++){				
			vista = new BeanVista();		   
			rol = new BeanRol();	
			this.vistaRol = new BeanRolVista();		   
			res = (HashMap)mapResultado.get(i);  	  
			rol.setStrIdRol(res.get("txtIdRol").toString());	
			vista.setStrNomVis(res.get("txtNombreVista").toString());	
			vista.setStrDesVis(res.get("txtDescVista").toString());  	         	 
			this.vistaRol.setVista(this.vista);	
			this.vistaRol.setRol(rol);	
			listaVistaRol.add(this.vistaRol); 
		}							
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaVistaRol(){
		column = new HashMap();	
		listaVistaRol = new ArrayList<BeanRolVista>();		
		column.put("String:0", "txtIdRol"); 
		column.put("String:2", "txtNombreVista");			
		sqlVistaRol = "SELECT txtIdRol,txtNombreVista FROM PROFFLINE_TB_VISTA_ROL ";
		resultExecuteQuery = new ResultExecuteQuery(sqlVistaRol,column,Constante.BD_SYNC);  
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;		 
		for(int i=0;i<mapResultado.size();i++){				
			vista = new BeanVista();		   
			this.vistaRol = new BeanRolVista();		
			rol = new BeanRol();  	       	
			res = (HashMap)mapResultado.get(i);  	       	
			rol.setStrIdRol(res.get("txtIdRol").toString());	
			vista.setStrNomVis(res.get("txtNombreVista").toString());			
			this.vistaRol.setVista(this.vista);	
			this.vistaRol.setRol(this.rol);	
			listaVistaRol.add(this.vistaRol); 
		}							
	}
	
	/* MÉTODO QUE VERIFICA SI EXISTE O NO UNA VISITA */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verificaVistaRol(String rol, String vista){
		boolean resultado = false;
		column = new HashMap();	
		String ID = Constante.VACIO;
		String nombre = Constante.VACIO;
		column.put("String:0", CADENA);						
		sqlVistaRol = "SELECT "+ IDROL +"||','||" + NOMBREVISTA +" AS "+CADENA+	               
    				" FROM " + TABLA_VISTA_ROL + " WHERE " + IDROL + " = '" + rol + "' AND " + NOMBREVISTA + " = '"+vista+"'";		  
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlVistaRol,column,Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if(mapResultado.size() > 0){
			res = (HashMap)mapResultado.get(0);			
			String temp = res.get(CADENA).toString();
			String [] temporal = temp.split(",");
			ID = temporal[0];
			nombre = temporal[1];
		}				
		if(!ID.equals(Constante.VACIO) && !nombre.equals(Constante.VACIO)){
			resultado = true;
		}
		return resultado;	
	}	
	
	public List<BeanRolVista> getListaVistaRol(){
		return listaVistaRol;
	}
	 
	public void setInsertarVistaRol(BeanRolVista vistaRol){			 
		vistaRol.getRol();
		this.vista= vistaRol.getVista();		
		sqlVistaRol = "INSERT INTO PROFFLINE_TB_VISTA_ROL (txtMandante,txtIdRol,txtNombreVista) VALUES ('"+vistaRol.getStrMandante()+"'," +
				"'"+vistaRol.getStrIdRol()+"'," + "'"+vistaRol.getStrNomVista()+"'); ";		
		ResultExecute resultExecute = new ResultExecute(sqlVistaRol, "Roles de vistas", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();							
	}
	
	public void setSincronizadoVistaRol(String idVisRol){
		sqlVistaRol = " UPDATE  " + " PROFFLINE_TB_VISTA_ROL  " + " SET txtSincronizado = 1 " + " WHERE txtIdRol = '" + idVisRol + "'";		
		ResultExecute resultExecute = new ResultExecute(sqlVistaRol, "Roles de vistas", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();		   
	}
	 
	public void setEliminarVistaRol(String idVisRol){
		sqlVistaRol = "DELETE FROM PROFFLINE_TB_VISTA_ROL "+ "WHERE txtIdRol='"+idVisRol+"'";	
		ResultExecute resultExecute = new ResultExecute(sqlVistaRol, "Roles de vistas", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();		   
	}
	
	public void setEliminarVistaRol(){
		sqlVistaRol = "DELETE FROM PROFFLINE_TB_VISTA_ROL ";
		ResultExecute resultExecute = new ResultExecute(sqlVistaRol, "Roles de vistas", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();		   
	}
	
	public boolean getInsertarVistaRol(){
		return resultado;
	}
	
	public boolean getEliminarVistaRol(){
		return resultado;	   
	}
	
	private static String TABLA_VISTA_ROL = "PROFFLINE_TB_VISTA_ROL";
	private static String IDROL = "txtIdRol";
	private static String NOMBREVISTA = "txtNombreVista";
	private static String CADENA = "CADENA";
}