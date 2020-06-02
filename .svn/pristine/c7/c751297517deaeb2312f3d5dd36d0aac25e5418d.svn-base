package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.administracion.bean.BeanVista;
import com.promesa.administracion.sql.SqlVista;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlVistaImpl implements SqlVista {

	private String sqlVista = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("unused")
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer,HashMap> mapResultado = new HashMap<Integer,HashMap>();
	private BeanVista vista = null;
	private List<BeanVista> listaVista = null;
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;
	@SuppressWarnings("unused")
	private ResultExecuteQuery resultExecuteQuery = null; 
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaVista(BeanVista vista){
		column = new HashMap();	
		listaVista = new ArrayList<BeanVista>();
		column.put("String:0", "txtMandante");
		column.put("String:1", "txtNombreVista");		  
		column.put("String:2", "txtDescVista");	
		sqlVista = " SELECT " + "txtMandante, " + "txtNombreVista, " + "txtDescVista " + "FROM PROFFLINE_TB_VISTA";       						 
		
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlVista,column,Constante.BD_SYNC);  
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;	 
		for(int i = 0; i < mapResultado.size(); i++){
			this.vista = new BeanVista();
			res = (HashMap)mapResultado.get(i);
			this.vista.setStrMandante(res.get("txtMandante").toString()); 
			this.vista.setStrNomVis(res.get("txtNombreVista").toString());				 
			this.vista.setStrDesVis(String.valueOf(res.get("txtDescVista")));				
			listaVista.add(this.vista); 
		}				
	}
	
	/* MÉTODO QUE VERIFICA SI EXISTE O NO UNA VISITA */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verificaVista(String vista){
		boolean resultado = false;
		column = new HashMap();	
		String nombre = Constante.VACIO;
		column.put("String:0", NOMBREVISTA);
		sqlVista = "SELECT " + NOMBREVISTA + " FROM "+TABLA_VISTA+" WHERE "+NOMBREVISTA+" = '"+vista+"'";		  
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlVista,column,Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if(mapResultado.size() > 0){
			res = (HashMap)mapResultado.get(0);			
			nombre = res.get(NOMBREVISTA).toString();			
		}				
		if(!nombre.equals(Constante.VACIO)){
			resultado = true;
		}
		return resultado;	
	}
	
	public List<BeanVista> getListaVista(){
		return listaVista;
	}
 
	public void setInsertarVista(BeanVista vista){
		sqlVista = "INSERT INTO  PROFFLINE_TB_VISTA (txtMandante,txtNombreVista,txtDescVista) " +
				"VALUES ('"+vista.getStrMandante() + "','" + vista.getStrNomVis()+"','"+ vista.getStrDesVis()+"') ";  	
		ResultExecute resultExecute = new ResultExecute(sqlVista, "Vistas", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();	
	}
	
	public void setEliminarVista(){
		sqlVista = "DELETE FROM PROFFLINE_TB_VISTA";		 
		ResultExecute   resultExecute = new ResultExecute(sqlVista, "Vistas", Constante.BD_SYNC);  
		resultado= resultExecute.isFlag();							
	}
 
	public boolean getEliminarVista(){
		return resultado;
	}	
	
	public boolean getInsertarVista(){
		return resultado;
	}
	
	private static String TABLA_VISTA = "PROFFLINE_TB_VISTA";
	private static String NOMBREVISTA = "txtNombreVista";
}