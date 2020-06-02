package com.promesa.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.promesa.planificacion.sql.impl.SqlFeriadoImpl;

public class VisitaIteracion {

	public static List<String> siguientesVisitas(String strFechaInicio,String strFrecuencia){
		List<String> lstFechasFuturas = new ArrayList<String>();
		String strFechaOriginal = strFechaInicio;
		boolean fin = false;
		String strAnio = "";
		String strAnioSiguiente = "";
		  while(!fin){
			  strAnio=strFechaOriginal.substring(6,10);		   
			  List<String> result=siguienteVisita(strFechaOriginal, strFrecuencia);		   
			  strAnioSiguiente=result.get(1).substring(6,10);
			  if(strAnio.equals(strAnioSiguiente)){
				  lstFechasFuturas.add(result.get(1));		    
				  strFechaOriginal=result.get(0);
			  }else{
				  fin=true;
			  }
		  }
		  return lstFechasFuturas;
	 }

	 public static List<String> siguienteVisita(String strFechaInicio,String strFrecuencia){
		 int intDia=Integer.parseInt(strFechaInicio.substring(0,2));
		 int intMes=Integer.parseInt(strFechaInicio.substring(3,5))-1;
		 int intAnio=Integer.parseInt(strFechaInicio.substring(6,10));
		 Calendar c = Calendar.getInstance();  
		 c.set(intAnio,intMes,intDia);
		 if(strFrecuencia.equals("01")){c.add(Calendar.WEEK_OF_YEAR, 1);}
		 else if(strFrecuencia.equals("02")){c.add(Calendar.WEEK_OF_YEAR, 2);}
		 else{c.add(Calendar.WEEK_OF_YEAR, 4);}	  
		 String DATE_FORMAT = "dd.MM.yyyy";
	     SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	     String strResultado=sdf.format(c.getTime());
	     String strFechaBaseSiguiente=strResultado;
	     if(esFeriado(strResultado)){
	    	 strResultado=siguienteDiaUtil(strResultado);
	     }
	     List<String> retorno = new ArrayList<String>();
	     retorno.add(strFechaBaseSiguiente);
	     retorno.add(strResultado);	  
	     return retorno;
	 }	 

	public static boolean esFeriado(String strFecha){		
		SqlFeriadoImpl f = new SqlFeriadoImpl();
		return f.esFeriado(strFecha);
	}

	public static String siguienteDiaUtil(String strFecha){
		int intDia = Integer.parseInt(strFecha.substring(0,2));
		int intMes = Integer.parseInt(strFecha.substring(3,5))-1;
		int intAnio = Integer.parseInt(strFecha.substring(6,10));
		Calendar c = Calendar.getInstance();
		c.set(intAnio,intMes,intDia);
		String DATE_FORMAT = "dd.MM.yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		boolean blnEncontrado = false;
		String strFechaTemp = "";
		while(!blnEncontrado){
			c.add(Calendar.DATE, 1);
			strFechaTemp = sdf.format(c.getTime());
			if(!esFeriado(strFechaTemp)){
				blnEncontrado = true;
			}
		}
		return strFechaTemp;
	}
}