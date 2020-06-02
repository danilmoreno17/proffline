package com.promesa.impresiones.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.promesa.util.Util;

/**
 * 
 * @author Rolando
 */
public class TicketOrden {

	private String direccion = "";
	private String telefono = "";
	private String Cliente = "";
	private String direccionCliente = "";
	private String vendedor = "";
	private String fecha = "";
	private String condicionesPago = "";
	private String numeroPedido = "";
	private String codigoCliente = "";
	private List<DetalleTicketOrden> detalles;
	private double subtotal;
	private double iva;
	private double total;

	private String titulo;

	public TicketOrden(String titulo) {
		super();
		direccion = "";
		telefono = "";
		Cliente = "";
		direccionCliente = "";
		vendedor = "";
		fecha = "";
		condicionesPago = "";
		numeroPedido = "";
		detalles = new ArrayList<DetalleTicketOrden>();
		subtotal = 0d;
		iva = 0d;
		total = 0d;
		this.titulo = titulo;
	}

	public String getCliente() {
		return Cliente;
	}

	public void setCliente(String Cliente) {
		this.Cliente = Cliente;
	}

	public String getCondicionesPago() {
		return condicionesPago;
	}

	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}

	public List<DetalleTicketOrden> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleTicketOrden> detalles) {
		this.detalles = detalles;
		if (detalles != null) {
			Double subtotal = 0d;
			for (DetalleTicketOrden detalleTicketOrden : detalles) {
				int tipo = detalleTicketOrden.getTipo();
				if (tipo == 0 || tipo == 1) {
					Double price = detalleTicketOrden.getValor();
					subtotal += price;
				}
			}
			this.subtotal = subtotal;
			this.iva = Util.obtenerIva() * subtotal;
			this.total = (1 + Util.obtenerIva()) * subtotal;
		}
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
}
