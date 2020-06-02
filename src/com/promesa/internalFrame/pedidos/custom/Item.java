package com.promesa.internalFrame.pedidos.custom;

import java.util.ArrayList;
import java.util.List;

public class Item {

	private Item padre;
	private String posicion = "";
	private String codigo = "";
	private String cantidad = "";
	private String cantidadConfirmada = "";
	private String stock = "";
	private String unidad = "";
	private String porcentajeDescuento = "";
	private String denominacion = "";
	private String denominacionCorta = "";
	private String fecha = "";
	private String marca = "";
	private String tipoMaterial;
	private Double precioNeto = 0d;
	private Double precioIVA = 0d;
	private Double precioCompra = 0d;
	private Double precioLista = 0d;
	private Double valorNeto = 0d;
	private Double precioBase = 0d;
	private Double pesoUnitario ;
	private int tipo; // 0: Para items normales, 1: Para items que pertenecen a un combo
	private String tipoSAP = "";
	private boolean editable;
	private List<Item> combos = new ArrayList<Item>();
	private boolean remover;
	private int cantidadAMostrar = 0;
	private boolean simulado = false;
	private boolean eliminiable = true;
	private boolean eliminadoLogicamente = false;
	private int intUbicacionGrilla;
	private String strDescCalc;
	private String strCondCalc1;
	private String strCondCalc2;
	private String strCondCalc3;
	private String strCondCalc4;
	private String strCondCalc5;
	private String strCondCalc6;
	private String strcondPreCliXMat;// ------ PRUEBA PRECIO CLIENTE MATERIAL -----
	private String strNroCond1;
	private String strNroCond2;
	private String strNroCond3;
	private String strNroCond4;
	private String strNroCond5;
	private String strNroCond6;
	private Double dblDesc1;
	private Double dblDesc2;
	private Double dblDesc3;
	private Double dblDesc4;
	private Double dblDesc5;
	private Double dblDesc6;
	private int intConfirmado;
	private String strVacio = "";
	private String descuentosManuales;
	private boolean esmaterialnuevo=false;
	private String strFech_Ing;
	
	public String getStrFech_Ing() {
		return strFech_Ing;
	}

	public void setStrFech_Ing(String strFech_Ing) {
		this.strFech_Ing = strFech_Ing;
	}

	public String getDescuentosManuales() {
		return descuentosManuales;
	}

	public void setDescuentosManuales(String descuentosManuales) {
		this.descuentosManuales = descuentosManuales;
	}

	public String getStrVacio() {
		return strVacio;
	}

	public void setStrVacio(String strVacio) {
		this.strVacio = strVacio;
	}

	public int getIntConfirmado() {
		return intConfirmado;
	}

	public void setIntConfirmado(int intConfirmado) {
		this.intConfirmado = intConfirmado;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getStrCondCalc1() {
		return strCondCalc1;
	}

	public void setStrCondCalc1(String strCondCalc1) {
		this.strCondCalc1 = strCondCalc1;
	}

	public String getStrCondCalc2() {
		return strCondCalc2;
	}

	public void setStrCondCalc2(String strCondCalc2) {
		this.strCondCalc2 = strCondCalc2;
	}

	public String getStrCondCalc3() {
		return strCondCalc3;
	}

	public void setStrCondCalc3(String strCondCalc3) {
		this.strCondCalc3 = strCondCalc3;
	}

	public String getStrCondCalc4() {
		return strCondCalc4;
	}

	public void setStrCondCalc4(String strCondCalc4) {
		this.strCondCalc4 = strCondCalc4;
	}

	public String getStrCondCalc5() {
		return strCondCalc5;
	}

	public void setStrCondCalc5(String strCondCalc5) {
		this.strCondCalc5 = strCondCalc5;
	}

	public String getStrCondCalc6() {
		return strCondCalc6;
	}

	public void setStrCondCalc6(String strCondCalc6) {
		this.strCondCalc6 = strCondCalc6;
	}
	
	public String getStrDescCalc() {
		return strDescCalc;
	}

	public void setStrDescCalc(String strDescCalc) {
		this.strDescCalc = strDescCalc;
	}

	public String getStrValorAcumulado() {
		return strValorAcumulado;
	}

	public void setStrValorAcumulado(String strValorAcumulado) {
		this.strValorAcumulado = strValorAcumulado;
	}

	public String getStrValorPromedio() {
		return strValorPromedio;
	}

	public void setStrValorPromedio(String strValorPromedio) {
		this.strValorPromedio = strValorPromedio;
	}

	private String strValorAcumulado;
	private String strValorPromedio;

	public int getIntUbicacionGrilla() {
		return intUbicacionGrilla;
	}

	public void setIntUbicacionGrilla(int intUbicacionGrilla) {
		this.intUbicacionGrilla = intUbicacionGrilla;
	}

	public Item getPadre() {
		return padre;
	}

	public void setPadre(Item padre) {
		this.padre = padre;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getCantidadConfirmada() {
		return cantidadConfirmada;
	}

	public void setCantidadConfirmada(String cantidadConfirmada) {
		this.cantidadConfirmada = cantidadConfirmada;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(String porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Double getPrecioNeto() {
		return precioNeto;
	}

	public void setPrecioNeto(Double precioNeto) {
		this.precioNeto = precioNeto;
	}

	public Double getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(Double valorNeto) {
		this.valorNeto = valorNeto;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<Item> getCombos() {
		return combos;
	}

	public void setCombos(List<Item> combos) {
		this.combos = combos;
	}

	public boolean isRemover() {
		return remover;
	}

	public void setRemover(boolean remover) {
		this.remover = remover;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public int getCantidadAMostrar() {
		return cantidadAMostrar;
	}

	public void setCantidadAMostrar(int cantidadAMostrar) {
		this.cantidadAMostrar = cantidadAMostrar;
	}

	public Double getPrecioIVA() {
		return precioIVA;
	}

	public void setPrecioIVA(Double precioIVA) {
		this.precioIVA = precioIVA;
	}

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Double getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(Double precioLista) {
		this.precioLista = precioLista;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDenominacionCorta() {
		return denominacionCorta;
	}

	public void setDenominacionCorta(String denominacionCorta) {
		this.denominacionCorta = denominacionCorta;
	}

	public boolean isSimulado() {
		return simulado;
	}

	public void setSimulado(boolean simulado) {
		this.simulado = simulado;
	}

	public boolean isEliminadoLogicamente() {
		return eliminadoLogicamente;
	}

	public void setEliminadoLogicamente(boolean eliminadoLogicamente) {
		this.eliminadoLogicamente = eliminadoLogicamente;
	}

	public boolean isEliminiable() {
		return eliminiable;
	}

	public void setEliminiable(boolean eliminiable) {
		this.eliminiable = eliminiable;
	}

	public String getTipoSAP() {
		return tipoSAP;
	}

	public void setTipoSAP(String tipoSAP) {
		this.tipoSAP = tipoSAP;
	}

	public Double getDblDesc1() {
		return dblDesc1;
	}

	public void setDblDesc1(Double dblDesc1) {
		this.dblDesc1 = dblDesc1;
	}

	public Double getDblDesc2() {
		return dblDesc2;
	}

	public void setDblDesc2(Double dblDesc2) {
		this.dblDesc2 = dblDesc2;
	}

	public Double getDblDesc3() {
		return dblDesc3;
	}

	public void setDblDesc3(Double dblDesc3) {
		this.dblDesc3 = dblDesc3;
	}

	public Double getDblDesc4() {
		return dblDesc4;
	}

	public void setDblDesc4(Double dblDesc4) {
		this.dblDesc4 = dblDesc4;
	}

	public Double getDblDesc5() {
		return dblDesc5;
	}

	public void setDblDesc5(Double dblDesc5) {
		this.dblDesc5 = dblDesc5;
	}

	public Double getDblDesc6() {
		return dblDesc6;
	}

	public void setDblDesc6(Double dblDesc6) {
		this.dblDesc6 = dblDesc6;
	}

	public Double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(Double precioBase) {
		this.precioBase = precioBase;
	}	

	public String getStrNroCond1() {
		return strNroCond1;
	}

	public void setStrNroCond1(String strNroCond1) {
		this.strNroCond1 = strNroCond1;
	}

	public String getStrNroCond2() {
		return strNroCond2;
	}

	public void setStrNroCond2(String strNroCond2) {
		this.strNroCond2 = strNroCond2;
	}

	public String getStrNroCond3() {
		return strNroCond3;
	}

	public void setStrNroCond3(String strNroCond3) {
		this.strNroCond3 = strNroCond3;
	}

	public String getStrNroCond4() {
		return strNroCond4;
	}

	public void setStrNroCond4(String strNroCond4) {
		this.strNroCond4 = strNroCond4;
	}

	public String getStrNroCond5() {
		return strNroCond5;
	}

	public void setStrNroCond5(String strNroCond5) {
		this.strNroCond5 = strNroCond5;
	}

	public String getStrNroCond6() {
		return strNroCond6;
	}

	public void setStrNroCond6(String strNroCond6) {
		this.strNroCond6 = strNroCond6;
	}	
	
	@Override
	public String toString() {
		return "Item [posicion=" + posicion + ", codigo=" + codigo
				+ ", cantidad=" + cantidad + ", cantidadConfirmada="
				+ cantidadConfirmada + ", unidad=" + unidad
				+ ", porcentajeDescuento=" + porcentajeDescuento
				+ ", denominacion=" + denominacion + ", marca=" + marca
				+ ", tipoMaterial=" + tipoMaterial + ", precioNeto="
				+ precioNeto + ", valorNeto=" + valorNeto + ", tipo=" + tipo
				+ ", simulado=" + simulado + "]";
	}

	// ------ PRUEBA PRECIO CLIENTE MATERIAL -----
	public void setStrcondPreCliXMat(String strcondPreCliXMat) {
		this.strcondPreCliXMat = strcondPreCliXMat;
	}

	public String getStrcondPreCliXMat() {
		return strcondPreCliXMat;
	}
	// ------ PRUEBA PRECIO CLIENTE MATERIAL -----
	
	public Double getPesoUnitario() {
		return pesoUnitario;
	}

	public void setPesoUnitario(Double pesoUnitario) {
		this.pesoUnitario = pesoUnitario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public boolean isEsmaterialnuevo() {
		return esmaterialnuevo;
	}

	public void setEsmaterialnuevo(boolean esmaterialnuevo) {
		this.esmaterialnuevo = esmaterialnuevo;
	}
}