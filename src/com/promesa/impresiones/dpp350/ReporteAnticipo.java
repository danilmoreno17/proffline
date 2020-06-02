package com.promesa.impresiones.dpp350;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.promesa.util.Util;

 

public class ReporteAnticipo extends IReportManagerDpp350{

	private String direccion;
	private String telefono;
	private String numeroTicket;
	private String numeroTicketSAP;
	private String cliente;
	private String vendedor;
	private String fecha;
	private String referencia;
	private String numeroCuentaBanco;
	private String formaPago;
	private String observacion;
	private String totalCobranza;
	private String titulo;
	
	private String importe;
	private String iva;
	private String idFormaPago;
	

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public void setNumeroCuentaBanco(String numeroCuentaBanco) {
		this.numeroCuentaBanco = numeroCuentaBanco;
	}
	public void setNumeroTicket(String numeroTicket) {
		this.numeroTicket = numeroTicket;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setTotalCobranza(String totalCobranza) {
		this.totalCobranza = totalCobranza;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setNumeroTicketSAP(String numeroTicketSAP) {
		this.numeroTicketSAP = numeroTicketSAP;
	}

	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getIva() {
		return iva;
	}
	
	public void setIva(String iva) {
		this.iva = iva;
	}
	
	public String getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(String idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	@Override
	public String getPathResource() {
		// TODO Auto-generated method stub
		String path ;
		if(idFormaPago.equals("VT")){
			path = "com/promesa/impresiones/dpp350/ireport/ticket_anticipo_retencion.jasper";
		} else {
			path = "com/promesa/impresiones/dpp350/ireport/ticket_anticipo.jasper";
		}
		return path;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getListRegistros() {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getParametros() {
		// TODO Auto-generated method stub
		
		Map param = new HashMap();
		
		param.put("titulo", this.titulo);
        param.put("nroTicket", this.numeroTicket);
        param.put("nroSap", this.numeroTicketSAP);
        param.put("fechaDocumento", this.fecha);
        param.put("direccion", this.direccion);
        param.put("telefono", this.telefono);
        param.put("cliente", this.cliente);
        param.put("vendedor", this.vendedor);
        param.put("fechaCreacion", Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(new Date()));
        param.put("cuentaBanco", this.numeroCuentaBanco);
        param.put("formaPago", this.formaPago);
        param.put("observacion", this.observacion);
        param.put("referencia", this.referencia);
        param.put("importe", this.importe);
//        param.put("iva", this.iva);
        param.put("total", this.totalCobranza);
		
		return param;
	}
}
