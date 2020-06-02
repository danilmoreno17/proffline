package com.promesa.impresiones.dpp350;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.promesa.impresiones.devoluciones.DetalleTicketDevolucion;
import com.promesa.main.Promesa;

public class TicketDevolucionesDPP350 extends IReportManagerDpp350 {

	private String direccion;
	@SuppressWarnings("unused")
	private String telefono ;
	private String codigoCliente;
	private String nombreCliente;
	private String numeroPedido;
	@SuppressWarnings("unused")
	private String direccionDestinatario;
	@SuppressWarnings("unused")
	private String descripcionBloqueEntrega;
	@SuppressWarnings("unused")
	private String numeroPedidoCliente;
	private String sello;
	@SuppressWarnings("unused")
	private String claseRiesgo;
	@SuppressWarnings("unused")
	private String descripcionCondicionPago;
	@SuppressWarnings("unused")
	private String descripcionMotivoDevolucion;
	@SuppressWarnings("unused")
	private String valorNeto;
	@SuppressWarnings("unused")
	private String cabeceraFormulario;
	@SuppressWarnings("unused")
	private String notaCabecera;
	private String fechaFactura;
	private String strFud;
	private String guiaTransp;
	private String numeroBulto;
	private String tipoDirVen;
	private String contacto;
	private String motivo;
	@SuppressWarnings("unused")
	private String codigoPedidoDevoluciones;
	private String strFechaDevolucionSistema;
	private String facturaSAP;
	private String facturaSRI;
	private List<DetalleTicketDevolucion> listaDetalleTicketDevolucion;
	
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public void setDireccionDestinatario(String direccionDestinatario) {
		this.direccionDestinatario = direccionDestinatario;
	}
	public void setDescripcionBloqueEntrega(String descripcionBloqueEntrega) {
		this.descripcionBloqueEntrega = descripcionBloqueEntrega;
	}
	public void setNumeroPedidoCliente(String numeroPedidoCliente) {
		this.numeroPedidoCliente = numeroPedidoCliente;
	}
	public void setSello(String sello) {
		this.sello = sello;
	}
	public void setClaseRiesgo(String claseRiesgo) {
		this.claseRiesgo = claseRiesgo;
	}
	public void setDescripcionCondicionPago(String descripcionCondicionPago) {
		this.descripcionCondicionPago = descripcionCondicionPago;
	}
	public void setDescripcionMotivoDevolucion(String descripcionMotivoDevolucion) {
		this.descripcionMotivoDevolucion = descripcionMotivoDevolucion;
	}
	public void setValorNeto(String valorNeto) {
		this.valorNeto = valorNeto;
	}
	public void setCabeceraFormulario(String cabeceraFormulario) {
		this.cabeceraFormulario = cabeceraFormulario;
	}
	public void setNotaCabecera(String notaCabecera) {
		this.notaCabecera = notaCabecera;
	}
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	public void setStrFud(String strFud) {
		this.strFud = strFud;
	}
	public void setGuiaTransp(String guiaTransp) {
		this.guiaTransp = guiaTransp;
	}
	public void setNumeroBulto(String numeroBulto) {
		this.numeroBulto = numeroBulto;
	}
	public void setTipoDirVen(String tipoDirVen) {
		this.tipoDirVen = tipoDirVen;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public void setCodigoPedidoDevoluciones(String codigoPedidoDevoluciones) {
		this.codigoPedidoDevoluciones = codigoPedidoDevoluciones;
	}
	public void setFacturaSAP(String facturaSAP) {
		this.facturaSAP = facturaSAP;
	}
	public void setFacturaSRI(String facturaSRI) {
		this.facturaSRI = facturaSRI;
	}
	public void setListaDetalleTicketDevolucion(
			List<DetalleTicketDevolucion> listaDetalleTicketDevolucion) {
		this.listaDetalleTicketDevolucion = listaDetalleTicketDevolucion;
	}
	public void setStrFechaDevolucionSistema(String strFechaDevolucioSistema) {
		this.strFechaDevolucionSistema = strFechaDevolucioSistema;
	}
	@Override
	public String getPathResource() {
		// TODO Auto-generated method stub
		return "com/promesa/impresiones/dpp350/ireport/ticket_devolucion.jasper";
	}
	
	@SuppressWarnings("rawtypes")
	public List getListRegistros() {
		// TODO Auto-generated method stub
		return listaDetalleTicketDevolucion;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getParametros() {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		
		param.put("titulo", "-  ORIGINAL  -");
        param.put("nroSeguimiento", this.numeroPedido);
        param.put("fecha", this.strFechaDevolucionSistema);
        param.put("cliente", this.codigoCliente + this.nombreCliente);
        param.put("ciudad", this.direccion);
        param.put("vendedor", Promesa.datose.get(0).getStrCodigo() + " - " + Promesa.datose.get(0).getStrUsuario());
        param.put("fecFactura", this.fechaFactura);
        param.put("facSap", this.facturaSAP);
        param.put("facSri", this.facturaSRI);
        param.put("nroSello", this.sello);
        param.put("fud", this.strFud);
        param.put("guiaTransporte", this.guiaTransp);
        param.put("nroBulto", this.numeroBulto);
        param.put("tipo", this.tipoDirVen);
        param.put("contacto", this.contacto);
        param.put("motivo", this.motivo);
		
		return param;
	}
}
