package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.administracion.sql.SqlHistorialSincronizacion;
import com.promesa.administracion.bean.*;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlHistorialSincronizacionImpl implements SqlHistorialSincronizacion{

	@SuppressWarnings("rawtypes")
	private HashMap column = null;
	private ResultExecuteQuery resultExecuteQuery = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer,HashMap> mapResultado = new HashMap<Integer,HashMap>();
	BeanSincronizar objS = new BeanSincronizar();
	private boolean resultado = false;	
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;	
	private String sqlSincronizar;	
	private List<BeanSincronizar> listaSincronizar = null;
	private String sqlSinc;
	
	// ALL
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void listaHistorialSincronizacion(int id){		
		column=new HashMap();	
		listaSincronizar = new ArrayList<BeanSincronizar>();
	    column.put("String:0", NUM_ID_HIST_SINC);
	    column.put("String:1", CANT_REG);
	    column.put("String:2", NUM_ID_SINC);
	    column.put("String:3", FECH_HOR);
	    column.put("String:4", TIEMPO);	
	    sqlSinc = " SELECT "+NUM_ID_HIST_SINC+", " + CANT_REG+", "+
							 NUM_ID_SINC +  "," + FECH_HOR+", "+ TIEMPO+" "+
							 " FROM "+TABLA_HISTORIAL_SINCRONIZACION+
							 " WHERE "+NUM_ID_SINC+"="+id+
							 " ORDER BY "+FECH_HOR+" DESC ";
	    		    	    
	    resultExecuteQuery = new ResultExecuteQuery(sqlSinc,column,Constante.BD_SYNC);  
	    mapResultado = resultExecuteQuery.getMap();
	    HashMap res = null;	 
	    for(int i=0;i<mapResultado.size();i++){
	    	objS = new BeanSincronizar();	    	    			    	
	    	res = (HashMap)mapResultado.get(i);		    	
	 		objS.setStrIdeSinc(res.get(NUM_ID_SINC).toString());
	 		objS.setStrIdeHistSinc(res.get(NUM_ID_HIST_SINC).toString());
	 		objS.setStrCantReg(res.get(CANT_REG).toString());
	 		objS.setStrFecHor(res.get(FECH_HOR).toString());	 		
	 		objS.setStrTie(res.get(TIEMPO).toString());	 		
	 		listaSincronizar.add(objS);		 
	    }			
	}


	public void setInsertarHistorialSincronizacion(BeanSincronizar Sincronizar){
		String temp=" VALUES ((SELECT MAX(numIdeHistSinc)+1  FROM "+TABLA_HISTORIAL_SINCRONIZACION+"),";	
		if(!getExisteRegistro(Sincronizar)){
			temp=" VALUES (1,";
		}		
		sqlSincronizar = " INSERT INTO "+TABLA_HISTORIAL_SINCRONIZACION+" (" + NUM_ID_HIST_SINC+"," +
						 NUM_ID_SINC+"," + CANT_REG+"," + FECH_HOR+"," + TIEMPO+") "+
						 temp+ Sincronizar.getStrIdeSinc()+","+ Sincronizar.getStrCantReg()+",'"+
	                  	 Sincronizar.getStrFecHor()+"',"+ Sincronizar.getStrTie() +") ";							
		ResultExecute   resultExecute = new ResultExecute(sqlSincronizar, "Historial de sincronización", Constante.BD_SYNC);  
		resultado= resultExecute.isFlag();							
	}
	 
	public void setEliminarHistorialSincronizacion(BeanSincronizar Sincronizar){		
		sqlSincronizar = "DELETE FROM "+TABLA_HISTORIAL_SINCRONIZACION;	 
		ResultExecute   resultExecute = new ResultExecute(sqlSincronizar, "Historial de sincronización", Constante.BD_SYNC);  
		resultado= resultExecute.isFlag();		 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean getExisteRegistro(BeanSincronizar Sincronizar){	
		column=new HashMap();	
	    column.put("String:0","NUM");	    
		sqlSincronizar = "SELECT COUNT("+NUM_ID_HIST_SINC+") AS NUM FROM "+TABLA_HISTORIAL_SINCRONIZACION+
		                 " WHERE numIdeSinc="+Sincronizar.getStrIdeSinc();	
	    resultExecuteQuery = new ResultExecuteQuery(sqlSincronizar,column,Constante.BD_SYNC);  
	    mapResultado = resultExecuteQuery.getMap();		
		HashMap res = (HashMap)mapResultado.get(0);		
		int cantReg= Integer.parseInt(res.get("NUM").toString().trim());		
		if(cantReg>0){
			return true;
		}else{
			return false;
		}				
	}

	public List<BeanSincronizar> getlistaHistorialSincronizacion(){
		return listaSincronizar;	
	}	
	
	public boolean getEliminarHistorialSincronizacion(){
		return resultado;	
	}	  
	 	
	public boolean getInsertarHistorialSincronizacion(){
		return resultado;	
	}
	
	private static String NUM_ID_HIST_SINC="numIdeHistSinc";
	private static String CANT_REG="numCantReg";
	private static String NUM_ID_SINC="numIdeSinc"; 
	private static String FECH_HOR="txtFecHor"; 	
	private static String TIEMPO="numTie";
	private static String TABLA_HISTORIAL_SINCRONIZACION="PROFFLINE_TB_HISTORIAL_SINCRONIZACION";
}