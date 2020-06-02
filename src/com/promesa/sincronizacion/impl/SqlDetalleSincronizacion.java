package com.promesa.sincronizacion.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.sincronizacion.bean.BeanDetalleSincronizacion;
import com.promesa.util.Constante;

public class SqlDetalleSincronizacion {
	    @SuppressWarnings("rawtypes")
		private HashMap column = null;
		private ResultExecuteQuery resultExecuteQuery = null;
		@SuppressWarnings("rawtypes")
		private HashMap<Integer,HashMap> mapResultado = new HashMap<Integer,HashMap>();
		private List <BeanDetalleSincronizacion> lista = new ArrayList<BeanDetalleSincronizacion>();
		@SuppressWarnings("unused")
		private boolean resultado = false;	
		@SuppressWarnings("unused")
		private ResultExecute resultExecute = null;
		BeanDetalleSincronizacion beanDetalleSincronizacion=null;
		String sqlSinc;
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List<BeanDetalleSincronizacion> listaSincronizacion(String opciones){		
			column=new HashMap();	
		    column.put("String:0", HORA_INICIO);
		    column.put("String:1", FRECUENCIA);
		    column.put("String:2", ID_SINCRONIZADO);  
		    column.put("String:3", TXT_INF_SINCRONIZADO); 
		    column.put("String:4", FECH_SINCRONIZADO); 
		    sqlSinc = "SELECT "+ HORA_INICIO+", "+ FRECUENCIA+", "+
		              "DS."+ID_SINCRONIZADO+", "+  TXT_INF_SINCRONIZADO+", "+  FECH_SINCRONIZADO+" "+
		    		  "FROM PROFFLINE_TB_DETALLE_SINCRONIZACION  DS "+
		              "INNER JOIN PROFFLINE_TB_SINCRONIZACION PS   ON DS.numIdeSinc=PS.numIdeSinc ";
		    if(opciones!=null){
		    	sqlSinc=sqlSinc+" WHERE PS.numIdeSinc IN("+opciones+") " + " ORDER BY "+HORA_INICIO+" ASC ";
		    }	    
		    resultExecuteQuery = new ResultExecuteQuery(sqlSinc,column, Constante.BD_SYNC);  
		    mapResultado = resultExecuteQuery.getMap();
		    HashMap res = null;	 
		    for(int i=0;i<mapResultado.size();i++){
		    	beanDetalleSincronizacion = new BeanDetalleSincronizacion();
		    	res = (HashMap)mapResultado.get(i);			 
		    	beanDetalleSincronizacion.setSrtHorIni(res.get(HORA_INICIO).toString());
		    	beanDetalleSincronizacion.setStrNumIdeSinc(res.get(ID_SINCRONIZADO).toString());
		    	beanDetalleSincronizacion.setStrnumFec(res.get(FRECUENCIA).toString());
		    	beanDetalleSincronizacion.setStrInfSinc(res.get(TXT_INF_SINCRONIZADO).toString());
		    	beanDetalleSincronizacion.setTxtFecHor(res.get(FECH_SINCRONIZADO).toString());
		    	lista.add(beanDetalleSincronizacion);		 
		    }		
			return lista;		
		}
	
		 private static String HORA_INICIO="txtHorIni";
		 private static String FRECUENCIA="numFec";
		 private static String ID_SINCRONIZADO="numIdeSinc";
		 private static String TXT_INF_SINCRONIZADO="txtInfSinc";
		 private static String FECH_SINCRONIZADO="txtFecHor";	
}