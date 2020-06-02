package com.promesa.impresiones.devoluciones;

import java.util.ArrayList;
import java.util.List;

import com.promesa.util.Constante;

public class TicketDevolucion {

	private String direccion = "";
	private String telefono = "";
	private String codigoCliente = "";
	private String nombreCliente = "";
	private String numeroPedido = "";
	private String direccionDestinatario = "";
	private String descripcionBloqueEntrega = "";
	private String numeroPedidoCliente = "";
	private String sello = "";
	private String claseRiesgo = "";
	private String descripcionCondicionPago = "";
	private String descripcionMotivoDevolucion = "";
	private String valorNeto = "";
	private String cabeceraFormulario = "";
	private String notaCabecera = "";
	private String fechaFactura = Constante.VACIO;
	private String strFud = Constante.VACIO;
	private String guiaTransp = Constante.VACIO;
	private String numeroBulto = Constante.VACIO;
	private String tipoDirVen = Constante.VACIO;
	private String contacto = Constante.VACIO;
	private String motivo = Constante.VACIO;
	private String codigoPedidoDevoluciones = Constante.VACIO;
	private String facturaSAP = Constante.VACIO;
	private String facturaSRI = Constante.VACIO;
	private List<DetalleTicketDevolucion> listaDetalleTicketDevolucion;

	public TicketDevolucion() {
		super();
		direccion = "";
		telefono = "";
		codigoCliente = "";
		nombreCliente = "";
		numeroPedido = "";
		direccionDestinatario = "";
		descripcionBloqueEntrega = "";
		numeroPedidoCliente = "";
		sello = "";
		claseRiesgo = "";
		descripcionCondicionPago = "";
		descripcionMotivoDevolucion = "";
		valorNeto = "";
		cabeceraFormulario = "";
		notaCabecera = "";
		fechaFactura = "";
		strFud = "";
		guiaTransp = "";
		numeroBulto = "";
		tipoDirVen = "";
		contacto = "";
		motivo = "";
		codigoPedidoDevoluciones = "";
		facturaSRI = "";
		setFacturaSAP(Constante.VACIO);
		listaDetalleTicketDevolucion = new ArrayList<DetalleTicketDevolucion>();
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getDireccionDestinatario() {
		return direccionDestinatario;
	}

	public void setDireccionDestinatario(String direccionDestinatario) {
		this.direccionDestinatario = direccionDestinatario;
	}

	public String getDescripcionBloqueEntrega() {
		return descripcionBloqueEntrega;
	}

	public void setDescripcionBloqueEntrega(String descripcionBloqueEntrega) {
		this.descripcionBloqueEntrega = descripcionBloqueEntrega;
	}

	public String getNumeroPedidoCliente() {
		return numeroPedidoCliente;
	}

	public void setNumeroPedidoCliente(String numeroPedidoCliente) {
		this.numeroPedidoCliente = numeroPedidoCliente;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getClaseRiesgo() {
		return claseRiesgo;
	}

	public void setClaseRiesgo(String claseRiesgo) {
		this.claseRiesgo = claseRiesgo;
	}

	public String getDescripcionCondicionPago() {
		return descripcionCondicionPago;
	}

	public void setDescripcionCondicionPago(String descripcionCondicionPago) {
		this.descripcionCondicionPago = descripcionCondicionPago;
	}

	public String getDescripcionMotivoDevolucion() {
		return descripcionMotivoDevolucion;
	}

	public void setDescripcionMotivoDevolucion(String descripcionMotivoDevolucion) {
		this.descripcionMotivoDevolucion = descripcionMotivoDevolucion;
	}

	public String getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(String valorNeto) {
		this.valorNeto = valorNeto;
	}

	public String getCabeceraFormulario() {
		return cabeceraFormulario;
	}

	public void setCabeceraFormulario(String cabeceraFormulario) {
		this.cabeceraFormulario = cabeceraFormulario;
	}

	public String getNotaCabecera() {
		return notaCabecera;
	}

	public void setNotaCabecera(String notaCabecera) {
		this.notaCabecera = notaCabecera;
	}

	public List<DetalleTicketDevolucion> getListaDetalleTicketDevolucion() {
		return listaDetalleTicketDevolucion;
	}

	public void setListaDetalleTicketDevolucion(List<DetalleTicketDevolucion> listaDetalleTicketDevolucion) {
		this.listaDetalleTicketDevolucion = listaDetalleTicketDevolucion;
	}

	public String getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getStrFud() {
		return strFud;
	}

	public void setStrFud(String strFud) {
		this.strFud = strFud;
	}

	public String getGuiatransp() {
		return guiaTransp;
	}

	public void setGuiatransp(String guiatransp) {
		this.guiaTransp = guiatransp;
	}

	public String getNumeroBulto() {
		return numeroBulto;
	}

	public void setNumeroBulto(String numeroBulto) {
		this.numeroBulto = numeroBulto;
	}

	public String getTipoDirVen() {
		return tipoDirVen;
	}

	public void setTipoDirVen(String tipoDirVen) {
		this.tipoDirVen = tipoDirVen;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public void setCodigoPedidoDevoluciones(String codigoPedidoDevoluciones) {
		this.codigoPedidoDevoluciones = codigoPedidoDevoluciones;
	}

	public String getCodigoPedidoDevoluciones() {
		return codigoPedidoDevoluciones;
	}

	public void setFacturaSRI(String facturaSRI) {
		this.facturaSRI = facturaSRI;
	}

	public String getFacturaSRI() {
		return facturaSRI;
	}

	public void setFacturaSAP(String facturaSAP) {
		this.facturaSAP = facturaSAP;
	}

	public String getFacturaSAP() {
		return facturaSAP;
	}	
}
