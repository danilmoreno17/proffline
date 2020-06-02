package com.promesa.util;

import java.util.*;
import java.text.*;

public class FechaHora {
	
	public static String getFecha(){
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
		return formato.format(fechaActual);
	}
	
	public static String getHora(){
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		return formato.format(fechaActual);
	}
	
	public static Date FechaMasDias(Date dFecha,int Dias){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(dFecha);
		calendar.add(Calendar.DATE,Dias);
		return calendar.getTime();
	}	
	
	public static String Formato(Date Fecha){
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return formato.format(Fecha);
	}	
	
	public static String FormatoEuropeo(String FormatoNormal){		
		String dias="",mes="",anno="";
		for(int i=0;i<FormatoNormal.length();i++){				
				if(i<2)
				dias	= dias + FormatoNormal.charAt(i);	
				if(i>2 && i<5)
				mes 	= mes +FormatoNormal.charAt(i);				
				if(i>5)
				anno	= anno+FormatoNormal.charAt(i);
		}			
		return anno+"/"+mes+"/"+dias;
	}
	
	public static String getDia(String fecha){
		String[] diasDeLaSemana = {"Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
		try{
			DateFormat formatter ; 
			Date date ; 
			formatter = new SimpleDateFormat("dd.MM.yyyy");
			date = (Date)formatter.parse(fecha); 
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return diasDeLaSemana[cal.get(java.util.Calendar.DAY_OF_WEEK) - 1];
		}catch(ParseException e){
			Util.mostrarExcepcion(e);
			return "";
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String Sumar_Restar_Fechas(String Fecha_Actual, int Dias){
		Date FechaActual	=new Date(FechaHora.FormatoEuropeo(Fecha_Actual));					
		return FechaHora.Formato(FechaHora.FechaMasDias(FechaActual, Dias));
	}
	
	/* CONVERSIÓN A DD.MM.YYYY */
	public String convierteFecha(String fsf){
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy",Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        try{
        	String StringRecogido = fsf;
            Date datehora = sdf1.parse(StringRecogido);
            return sdf2.format(datehora);
        }catch(Exception e){
        	Util.mostrarExcepcion(e);
        	return e.getMessage();
        }		
	}
}