package com.promesa.sincronizacion.impl;

import java.util.List;
import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.planificacion.bean.BeanPlanificacion;
import com.promesa.planificacion.sql.SqlPlanificacion;
import com.promesa.planificacion.sql.SqlSincronizacion;
import com.promesa.planificacion.sql.impl.SqlPlanificacionImpl;
import com.promesa.planificacion.sql.impl.SqlSincronizacionImpl;
import com.promesa.sap.SPlanificacion;
import com.promesa.util.Cmd;
import com.promesa.util.Util;

public class SincronizacionPlanificacion {	
	SPlanificacion pla = new SPlanificacion();	
	SqlSincronizacionImpl objSI;
	String idVendedor;
		
	public SincronizacionPlanificacion(String idVendedor){
		this.idVendedor = idVendedor; 
	}
 
	@SuppressWarnings("static-access")
	public void sincronizaFeriado(){
		Cmd objC = new Cmd();	  
		String fIF = objC.fechaHora();	  
		objSI = new SqlSincronizacionImpl();	  
		if(objSI.sincronizaFeriado() == true){
			objSI.sF(fIF);        			        						
		}	
	}
 
	@SuppressWarnings("static-access")
	public void sincronizaEmpleadoCliente(String idSup){
		Cmd objC = new Cmd();
		String fIEC = objC.fechaHora();	 
		objSI = new SqlSincronizacionImpl();    			        					
		if(objSI.sincronizaEmpleadoCliente(idSup) == true){
			objSI.sEC(fIEC);  			        						
		} 
	}
	 
	@SuppressWarnings("static-access")
	public void sincronizaPlanificacion(String idVendedor) throws Exception{
		SqlPlanificacion getPlanificacion = new SqlPlanificacionImpl();
		getPlanificacion.setEliminarPlanificacion();
		Cmd objC = new Cmd();
		String fechaInicio = objC.fechaHora();
		BeanSincronizar sincronizar = new BeanSincronizar();
		String fechaFinal;
		int i;		
		fechaFinal = "";	
		SqlSincronizacion getSincronizacion = new  SqlSincronizacionImpl();	 
		SPlanificacion sPlanificacion = new SPlanificacion();	 
		List<BeanPlanificacion> listaPlan= sPlanificacion.planificaciones(idVendedor);	 
		for(BeanPlanificacion plan : listaPlan){
			getPlanificacion.setInsertarPlanificacion(plan);
		}			
		try {			
			objC = new Cmd();								
			i = 0;            
            i+=getSincronizacion.filasTabla("PROFFLINE_TB_PLANIFICACION");           
            sincronizar.setStrIdeSinc("7");            
            getSincronizacion.setEliminarSincronizacion(sincronizar);                
            fechaFinal = objC.fechaHora();           
            sincronizar.setStrInfSinc("Planificacion");
            sincronizar.setStrCantReg(i+"");
            sincronizar.setStrFecHor(fechaFinal);
            sincronizar.setStrTie(objC.diferencia(fechaInicio, fechaFinal)+"");
            getSincronizacion.setInsertarSincronizar(sincronizar);                       
        } catch (Exception e) {
        	Util.mostrarExcepcion(e);
        }	 
	}
}