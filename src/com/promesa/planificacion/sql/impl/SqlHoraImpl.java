package com.promesa.planificacion.sql.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanHora;
import com.promesa.planificacion.sql.SqlHora;
import com.promesa.util.Constante;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SqlHoraImpl implements SqlHora {

	private String sqlHora = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanHora hora = null;
	private List<BeanHora> listaHora = null;
	@SuppressWarnings("unused")
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;
	@SuppressWarnings("unused")
	private ResultExecuteQuery resultExecuteQuery = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaHora(BeanHora hora) {
		column = new HashMap();
		listaHora = new ArrayList<BeanHora>();
		column.put("String:0", "numIdHora");
		column.put("String:1", "txtHora");
		sqlHora = "SELECT numIdHora,txtHora FROM PROFFLINE_TB_HORA";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlHora, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		Integer Hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		Integer minutes = calendar.get(Calendar.MINUTE); 
		
		
		for (int i = 0; i < mapResultado.size(); i++) {
			this.hora = new BeanHora();
			res = (HashMap) mapResultado.get(i);
			this.hora.setStrIdHora(getData(res.get("numIdHora")));
			this.hora.setStrHora(getData(res.get("txtHora")));
			
			//** Valido la Hora actual **//
			Integer horaBaseDatos = new Integer(this.hora.getStrHora().split(":")[0]);
			Integer minutoBaseDatos = new Integer(this.hora.getStrHora().split(":")[1]);
			
			
			//**Solo para el dia de hoy **//
			if(horaBaseDatos.toString().equals(Hour.toString())){
				if(minutoBaseDatos > minutes)
					listaHora.add(this.hora);		
			}
                
			if(horaBaseDatos > Hour){
					listaHora.add(this.hora);		
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaHoraEvento(BeanHora hora, Date Fecha) {
		
		Date diadehoy = null;
		Date diadelcombo = null;
		column = new HashMap();
		listaHora = new ArrayList<BeanHora>();
		column.put("String:0", "numIdHora");
		column.put("String:1", "txtHora");
		sqlHora = "SELECT numIdHora,txtHora FROM PROFFLINE_TB_HORA";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlHora, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		Integer Hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		Integer minutes = calendar.get(Calendar.MINUTE); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
			diadehoy = sdf.parse(sdf.format(new Date()));
			diadelcombo = sdf.parse(sdf.format(Fecha));
			
		} catch (ParseException pe){
			
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		for (int i = 0; i < mapResultado.size(); i++) {
			this.hora = new BeanHora();
			res = (HashMap) mapResultado.get(i);
			this.hora.setStrIdHora(getData(res.get("numIdHora")));
			this.hora.setStrHora(getData(res.get("txtHora")));
			
			//** Valido la Hora actual **//
			Integer horaBaseDatos = new Integer(this.hora.getStrHora().split(":")[0]);
			Integer minutoBaseDatos = new Integer(this.hora.getStrHora().split(":")[1]);
			
			

			if(diadehoy.equals(diadelcombo)){
				
				//**Solo para el dia de hoy **//
				if(horaBaseDatos.toString().equals(Hour.toString())){
					if(minutoBaseDatos > minutes)
						listaHora.add(this.hora);		
				}
				
				if(horaBaseDatos > Hour){
					listaHora.add(this.hora);		
				}
				
			} else {
				
				listaHora.add(this.hora);
			}
			
			
                
			
		}
	}

	public String getData(Object obj) {
		String result = "";
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	public List<BeanHora> getListaHora() {
		return listaHora;
	}
	
	public List<BeanHora> getListaHoraEvento() {
		return listaHora;
	}
}