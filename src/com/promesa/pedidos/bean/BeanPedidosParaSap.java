package com.promesa.pedidos.bean;

import java.util.HashMap;
import java.util.List;

public class BeanPedidosParaSap {
	
	HashMap<String,BeanPedidoHeader> mapPedidoHeader = null;
	HashMap<String, HashMap<String,String>> mapPedidoPartners = null;
	HashMap<String,List<BeanPedidoDetalle>> mapPedidoDetalle = null;	
	
	public HashMap<String, BeanPedidoHeader> getMapPedidoHeader() {
		return mapPedidoHeader;
	}
	public void setMapPedidoHeader(HashMap<String, BeanPedidoHeader> mapPedidoHeader) {
		this.mapPedidoHeader = mapPedidoHeader;
	}
	public HashMap<String,  HashMap<String,String>> getMapPedidoPartners() {
		return mapPedidoPartners;
	}
	public void setMapPedidoPartners(HashMap<String,  HashMap<String,String>> mapPedidoPartners) {
		this.mapPedidoPartners = mapPedidoPartners;
	}
	public HashMap<String, List<BeanPedidoDetalle>> getMapPedidoDetalle() {
		return mapPedidoDetalle;
	}
	public void setMapPedidoDetalle(HashMap<String, List<BeanPedidoDetalle>> mapPedidoDetalle) {
		this.mapPedidoDetalle = mapPedidoDetalle;
	}
	
}
