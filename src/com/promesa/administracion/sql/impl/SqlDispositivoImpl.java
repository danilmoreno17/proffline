package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.administracion.bean.BeanDispositivo;
import com.promesa.administracion.sql.SqlDispositivo;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;
import com.promesa.util.GenerarId;
import com.promesa.util.Util;

public class SqlDispositivoImpl implements SqlDispositivo {
	private String sqlDispositivo = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("unused")
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer,HashMap> mapResultado = new HashMap<Integer,HashMap>();
	private List<BeanDispositivo> listaDispositivo = null;
	private boolean resultado = false;
			
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaDispositivo(BeanDispositivo disp){
		column = new HashMap();	
		listaDispositivo = new ArrayList<BeanDispositivo>();
		column.put("String:0", "txtIdDis");
		column.put("String:1", "txtTipDis");
		column.put("String:2", "txtSerie");
		column.put("String:3", "txtCodAct");
		column.put("String:4", "txtSimm");
		column.put("String:5", "txtImei");
		column.put("String:6", "txtNroTelf");
		column.put("String:7", "txtNumSeg");
		column.put("String:8", "txtIdUsu");
		column.put("String:9", "txtUsu");
		column.put("String:10", "txtDisRel");
		column.put("String:11", "numEstado");
		column.put("String:12", "txtObs");
		column.put("String:13","txtUsuReg");
	    column.put("String:14", "txtFecReg");
	    column.put("String:15","txtHorReg");
	    column.put("String:16", "txtUsuUpd");
	    column.put("String:17", "txtFecUpd");
	    column.put("String:18", "txtHorUpd");	    
		sqlDispositivo = "SELECT txtIdDis, " +  " txtTipDis, " + " txtSerie, " + " txtCodAct, " + " txtSimm, " + " txtImei, " +
						 " txtNroTelf, " + " txtNumSeg, " + " txtIdUsu, " + " txtUsu, " + " txtDisRel, " +
						 " numEstado, " + " txtObs, " + " txtUsuReg, " + " txtFecReg, " + " txtHorReg, " +
						 " txtUsuUpd, " + " txtFecUpd, " + " txtHorUpd " + " FROM PROFFLINE_TB_DISPOSITIVO ";					 	 		
		if(disp.getStrNomUsuario()!=null &&  disp.getStrNomUsuario().trim().length()>0){
			sqlDispositivo += " WHERE txtUsu = '" + disp.getStrNomUsuario() + "'";
		}				
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlDispositivo,column,Constante.BD_SYNC);  
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;		 
		for(int i = 0; i < mapResultado.size(); i++){
			BeanDispositivo dispositivo = new BeanDispositivo();
			res = (HashMap)mapResultado.get(i);		 
			dispositivo.setStrIdDispositivo(res.get("txtIdDis").toString());
			dispositivo.setStrTipoDispositivo(res.get("txtTipDis").toString());
			dispositivo.setStrNumeroSerieDispositivo(res.get("txtSerie").toString());
			dispositivo.setStrCodigoActivo(res.get("txtCodAct").toString());
			dispositivo.setStrSimm(res.get("txtSimm").toString());
			dispositivo.setStrImei(res.get("txtImei").toString());
			dispositivo.setStrNumeroTelefono(res.get("txtNroTelf").toString());
			dispositivo.setStrNumeroSeguro(res.get("txtNumSeg").toString());
			dispositivo.setStrIdUsuario(res.get("txtIdUsu").toString());
			dispositivo.setStrNomUsuario(res.get("txtUsu").toString());
			dispositivo.setStrDispositivoRelacionado(res.get("txtDisRel").toString());
			dispositivo.setStrEstado(res.get("numEstado").toString());				
			dispositivo.setStrObservacion(res.get("txtObs").toString());
			dispositivo.setStrUsuReg(res.get("txtUsuReg").toString());
			dispositivo.setStrFecReg(res.get("txtFecReg").toString());
			dispositivo.setStrHorReg(res.get("txtHorReg").toString());				
			if(res.get("txtUsuUpd")!=null){dispositivo.setStrUsuUpd(res.get("txtUsuUpd").toString()); }
			else {dispositivo.setStrUsuUpd("");}			 
			if(res.get("txtFecUpd")!=null){dispositivo.setStrFecUpd(res.get("txtFecUpd").toString()); }
			else {dispositivo.setStrFecUpd("");}			 
			if(res.get("txtHorUpd")!=null){dispositivo.setStrHorUpd(res.get("txtHorUpd").toString()); }
			else {dispositivo.setStrHorUpd("");}			 
			listaDispositivo.add(dispositivo); 
		}				
	}
	
	public List<BeanDispositivo> getListaDispositivo(){
		return listaDispositivo;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public boolean yaTieneDispositivo(String idUsuario,String disRel){
		column = new HashMap();	
		boolean resultado=false;
		listaDispositivo = new ArrayList<BeanDispositivo>();
		column.put("String:0", "val");		
		sqlDispositivo = " SELECT  COUNT(*) as val  FROM  PROFFLINE_TB_DISPOSITIVO " + " WHERE txtIdUsu ='"+idUsuario.trim()+"' "+ " AND txtTipDis='"+disRel+"'";			      
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlDispositivo,column,Constante.BD_SYNC);  
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;		 
		BeanDispositivo dispositivo = new BeanDispositivo();
		res = (HashMap)mapResultado.get(0);		 
		if(Integer.parseInt(res.get("val").toString().trim())>0){
			resultado=true;		 
		}
		return resultado;			
	}
	
	public void setInsertarDispositivo(BeanDispositivo dispositivo){
		sqlDispositivo = "INSERT INTO PROFFLINE_TB_DISPOSITIVO "+                                        
                                "VALUES ('" + dispositivo.getStrIdDispositivo()+ "','" +
                                			  dispositivo.getStrTipoDispositivo()+ "','" +
                                			  dispositivo.getStrNumeroSerieDispositivo()+ "','" +
                                			  dispositivo.getStrCodigoActivo()+ "','" +
                                			  dispositivo.getStrSimm()+ "','" +
                                			  dispositivo.getStrImei()+ "','" +
                                			  dispositivo.getStrNumeroTelefono()+ "','" +
                                			  dispositivo.getStrNumeroSeguro()+ "','" +
                                			  dispositivo.getStrIdUsuario()+ "','" +
                                			  dispositivo.getStrNomUsuario()+ "','" +
                                			  dispositivo.getStrDispositivoRelacionado()+ "'," +
                                			  dispositivo.getStrEstado()+ ",'"+                                			  											  
                                			  dispositivo.getStrObservacion()+ "','" +
                                			  dispositivo.getStrUsuReg()+ "','" +
                                			  dispositivo.getStrFecReg()+ "','" +
                                			  dispositivo.getStrHorReg()+ "','" +
                                			  dispositivo.getStrUsuUpd()+ "','" +
                                			  dispositivo.getStrFecUpd()+ "','" +
                                			  dispositivo.getStrHorUpd()+ "')";		
		try{
			ResultExecute resultExecute = new ResultExecute(sqlDispositivo, "Dispositivos", Constante.BD_SYNC);  
			resultado = resultExecute.isFlag();	
		}catch(Exception e){
			Util.mostrarExcepcion(e);
			resultado = false;
		}
	}
	
	public boolean getInsertarDispositivo(){
	    return resultado;
	}
	 
	public void setActualizarDispositivo(BeanDispositivo dispositivo){
		sqlDispositivo = "UPDATE  " + " PROFFLINE_TB_DISPOSITIVO  " +
		            	 " SET txtSerie = '" + dispositivo.getStrNumeroSerieDispositivo() + 
		            	 "', txtIdUsu = '" + dispositivo.getStrIdUsuario() +
		            	 "', txtUsu = '" + dispositivo.getStrNomUsuario() + 
		            	 "', txtObs = '" + dispositivo.getStrObservacion() +
		         		 "', txtUsuUpd = '" + dispositivo.getStrUsuUpd() + 
		         		 "', txtFecUpd = '" + dispositivo.getStrFecUpd() + 
		         		 "', txtHorUpd = '" + dispositivo.getStrHorUpd() + "',  " +
		         		 " txtSincronizado=2"+ " WHERE txtIdDis = '" + dispositivo.getStrIdDispositivo() + "'";		
		ResultExecute resultExecute = new ResultExecute(sqlDispositivo, "Dispositivos", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();		   
	}
	
	public boolean getActualizarDispositivo(){
		return resultado;
	}
	 
	public void setEliminarDispositivo(BeanDispositivo dispositivo){
		sqlDispositivo = "UPDATE "+ "PROFFLINE_TB_DISPOSITIVO "+ "SET Eliminado = 1, "+ "txtSincronizado= 3 "+
		            	 "WHERE txtIdDis = '" + dispositivo.getStrIdDispositivo() + "'";		      
		ResultExecute   resultExecute = new ResultExecute(sqlDispositivo, "Dispositivos", Constante.BD_SYNC);  
		resultado= resultExecute.isFlag();					
	}
	
	public void setEliminarDispositivo(){
		sqlDispositivo = "DELETE FROM PROFFLINE_TB_DISPOSITIVO;";		      
		ResultExecute   resultExecute = new ResultExecute(sqlDispositivo, "Dispositivos", Constante.BD_SYNC);  
		resultado= resultExecute.isFlag();					
	}
	
	public void setSincronizadoDispositivo(String idDis){
		sqlDispositivo = " UPDATE  " + " PROFFLINE_TB_DISPOSITIVO  " + " SET txtSincronizado = 1 "+  " WHERE txtIdDis = '" + idDis + "'";		
		ResultExecute resultExecute = new ResultExecute(sqlDispositivo, "Dispositivos", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();		   
	}
	
	public boolean getEliminarDispositivo(){
		return resultado;
	}
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BeanDispositivo getIdDispositivo(){
		BeanDispositivo dispositivo = new BeanDispositivo();
		GenerarId generarId = new GenerarId();
		column = new HashMap();	
		column.put("String:0", "ID");		
		sqlDispositivo = " SELECT COUNT(*) AS ID  FROM  PROFFLINE_TB_DISPOSITIVO";		
		ResultExecuteQuery   resultExecuteQuery = new ResultExecuteQuery(sqlDispositivo,column,Constante.BD_SYNC);  
		mapResultado= resultExecuteQuery.getMap();
		HashMap res = null;
		res = (HashMap)mapResultado.get(0);		
		if(!(res.get("ID").toString().trim()).equals("0")){
			sqlDispositivo = "SELECT SUBSTR(txtIdDis,5,LENGTH(MAX(txtIdDis))) AS ID " + "FROM PROFFLINE_TB_DISPOSITIVO"; 			      
 			resultExecuteQuery = new ResultExecuteQuery(sqlDispositivo,column,Constante.BD_SYNC);  
 			mapResultado = resultExecuteQuery.getMap();
 			res = (HashMap)mapResultado.get(0);  
 		}		
		generarId.setIdDispositivo(res.get("ID").toString());
		dispositivo.setStrIdDispositivo(generarId.getIdDispositivo().getStrIdDispositivo());		
		return dispositivo;						
	}
	
	public void setBloquearDispositivo(String txtIdDispositivo){
		sqlDispositivo = "UPDATE " + "PROFFLINE_TB_DISPOSITIVO " + "SET numEstado = 0 " + "WHERE txtIdDis = '" + txtIdDispositivo + "'";
		ResultExecute resultExecute = new ResultExecute(sqlDispositivo, "Dispositivos", Constante.BD_SYNC);  
		resultado = resultExecute.isFlag();
	}	
	
	public boolean getBloquearDispositivo(){
		return resultado;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String verificarImpresora(String strUsuarioVendedor, String strDispositivoReal){
		String strResultado = "";
		column = new HashMap();	
		
		column.put("String:0", "txtObs");	    
		sqlDispositivo = "SELECT txtObs FROM PROFFLINE_TB_DISPOSITIVO where txtUsu = '" +strUsuarioVendedor +
					"' and txtDisRel = '" + strDispositivoReal + "' AND NUMESTADO = '1' limit 1;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlDispositivo,column,Constante.BD_SYNC);  
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if(mapResultado.size() > 0){
			res = mapResultado.get(0);
			strResultado = res.get("txtObs").toString().trim();
		}
		
		return strResultado;				
	}
}