package com.promesa.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.promesa.planificacion.sql.impl.SqlFeriadoImpl;

public class Visita {

	@SuppressWarnings({ "unused", "deprecation" })
	public static List<String> siguientesVisitas(String strFechaInicio,String strFrecuencia){
		String strFechaOriginalTotal=strFechaInicio;
		String strFechaOriginalMes=strFechaInicio;
		List<String> lstFechasFuturas=new ArrayList<String>();
		int intDia=Integer.parseInt(strFechaInicio.substring(0,2));
		int intMes=Integer.parseInt(strFechaInicio.substring(3,5))-1;
		int intAnio=Integer.parseInt(strFechaInicio.substring(6,10));
		Calendar c = new GregorianCalendar(Locale.ROOT);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(intAnio,intMes,intDia);
		GregorianCalendar gc=new GregorianCalendar();
		gc.set(intAnio,intMes,intDia);
		int intDiaSemana=c.getTime().getDay();
		int intPosicionMes=c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		int intMesAnio=c.get(Calendar.MONTH);		
		boolean finAnio=false;
		boolean finMes=false;
		while(!finAnio){			
			lstFechasFuturas.add(strFechaOriginalMes);
			while(!finMes){				
				String strMes=strFechaOriginalMes.substring(3,5);
				List<String> result=siguienteVisita(strFechaOriginalMes, strFrecuencia);
				String strMesSiguiente=result.get(1).substring(3,5);
				if(strMes.equals(strMesSiguiente)){
					lstFechasFuturas.add(result.get(1));
					strFechaOriginalMes=result.get(0);
				}
				else{
					finMes=true;
				}
			}			
			if(finDosMeses(Integer.parseInt(strFechaOriginalMes.substring(3,5)),intMes)){
				finAnio=true;
			}
			else{
				finMes=false;				
				int intMesT=Integer.parseInt(strFechaOriginalMes.substring(3,5));
				int intAnioT=Integer.parseInt(strFechaOriginalMes.substring(6,10));				
				GregorianCalendar gct=new GregorianCalendar();
				gct.set(intAnioT,intMesT,1);
				boolean bucle=true;
				while(bucle){
					int intDiaSemanaTemp=gct.getTime().getDay();
					int intPosicionMesTemp=gct.get(Calendar.DAY_OF_WEEK_IN_MONTH);
					int intMesAnioTemp=gct.get(Calendar.MONTH);		
					if(intDiaSemana==intDiaSemanaTemp && intPosicionMes==intPosicionMesTemp){
						String DATE_FORMAT = "dd.MM.yyyy";
					    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
						strFechaOriginalMes=sdf.format(gct.getTime());
						bucle=false;	
					}
					else{
						gct.add(Calendar.DATE,1);
					}					
				}				
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
		int intDia=Integer.parseInt(strFecha.substring(0,2));
		int intMes=Integer.parseInt(strFecha.substring(3,5))-1;
		int intAnio=Integer.parseInt(strFecha.substring(6,10));
		Calendar c = Calendar.getInstance();   
		c.set(intAnio,intMes,intDia);
		String DATE_FORMAT = "dd.MM.yyyy";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);		
		boolean blnEncontrado=false;
		String strFechaTemp="";
		while(!blnEncontrado){
			c.add(Calendar.DATE, 1);
			strFechaTemp=sdf.format(c.getTime());
			if(!esFeriado(strFechaTemp)){
				blnEncontrado=true;
			}
		}		
		return strFechaTemp;
	}
	
	public static boolean finDosMeses(int a,int b){
		boolean retorno=false;	  
		if(a>=2){
			if(a-b == 2){
				retorno=true;
			}
		}else{
			if(a==1 && b==11) retorno = true;
		}	  
		return retorno;	  
	}	
}