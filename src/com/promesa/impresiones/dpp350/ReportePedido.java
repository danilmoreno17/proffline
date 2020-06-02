package com.promesa.impresiones.dpp350;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.promesa.impresiones.pedidos.DetalleTicketOrden;
import com.promesa.util.Util;


public class ReportePedido  extends IReportManagerDpp350{
	
	private String direccion = "";
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setCliente(String cliente) {
		Cliente = cliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
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
			this.subtotal = redondear(subtotal);
			this.iva = redondear(Util.obtenerIva() * subtotal);
			this.total = redondear((1 + Util.obtenerIva()) * subtotal);
		}
	}
	
	private double redondear(double valor){
		return Math.rint(valor * 100)/100;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	private String telefono;
	private String Cliente;
	@SuppressWarnings("unused")
	private String direccionCliente;
	private String vendedor;
	private String fecha;
	private String condicionesPago;
	private String numeroPedido;
	@SuppressWarnings("unused")
	private String codigoCliente ;
	private List<DetalleTicketOrden> detalles;
	private double subtotal;
	private double iva;
	private double total;

	private String titulo;

	@Override
	public String getPathResource() {
		// TODO Auto-generated method stub
		return "com/promesa/impresiones/dpp350/ireport/ticket_orden.jasper";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListRegistros() {
		// TODO Auto-generated method stub
		return detalles;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getParametros() {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		
		param.put("titulo", this.titulo);
        param.put("nroPedido", this.numeroPedido);
        param.put("cliente", this.Cliente);
        param.put("direccion", this.direccion);
        param.put("telefono", this.telefono);
        param.put("vendedor", this.vendedor);
        param.put("fecha", this.fecha);
        param.put("condicionPago", this.condicionesPago);
        param.put("subTotal", this.subtotal);
        param.put("iva", this.iva);
        param.put("total", this.total);
		
		return param;
	}
}
