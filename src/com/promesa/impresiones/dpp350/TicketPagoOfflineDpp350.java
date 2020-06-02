package com.promesa.impresiones.dpp350;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.util.Util;


public class TicketPagoOfflineDpp350 extends IReportManagerDpp350{

	private List<PagoRecibido> listaPagosRecibidos;
	private List<PagoParcial> listaPagosParciales;
	private String direccion;
	private String telefono;
	private String numeroTicket;
	private String cliente;
	private String vendedor;
	private String titulo;
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setListaPagosRecibidos(List<PagoRecibido> listaPagosRecibidos) {
		this.listaPagosRecibidos = listaPagosRecibidos;
	}
	public void setListaPagosParciales(List<PagoParcial> listaPagosParciales) {
		this.listaPagosParciales = listaPagosParciales;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setNumeroTicket(String numeroTicket) {
		this.numeroTicket = numeroTicket;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	@Override
	public String getPathResource() {
		return "com/promesa/impresiones/dpp350/ireport/registroPagoOffline.jasper";
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getListRegistros() {
		// TODO Auto-generated method stub
		
		return listaPagosParciales;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getParametros() {
		// TODO Auto-generated method stub
		
		Double abonoTotal = 0d;
		for (PagoParcial pagoParcial : listaPagosParciales) {
			abonoTotal += pagoParcial.getImportePago();
		}
		
		
		Map param = new HashMap();
		
		param.put("titulo", this.titulo);
        param.put("nroRegistro", this.numeroTicket);
        param.put("direccion", this.direccion);
        param.put("telefono", this.telefono);
        param.put("cliente", this.cliente);
        param.put("vendedor", this.vendedor);
        param.put("total", abonoTotal);
        param.put("lista", listaPagosRecibidos);
//        param.put("fecha", Util.convierteFechaAFormatoDDMMMYYYY(new Date()));
        param.put("fecha", Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(new Date()));
        
		return param;
	}
	
	
}