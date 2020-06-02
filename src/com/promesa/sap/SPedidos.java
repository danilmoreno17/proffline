package com.promesa.sap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import util.ConexionSAP;

import com.promesa.administracion.bean.BeanParametro;
import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.main.Index;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.bean.BeanBloqueoEntrega;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.pedidos.bean.BeanClaseMaterial;
import com.promesa.pedidos.bean.BeanCondicionComercial1;
import com.promesa.pedidos.bean.BeanCondicionComercial2;
import com.promesa.pedidos.bean.BeanCondicionPago;
import com.promesa.pedidos.bean.BeanFactura;
import com.promesa.pedidos.bean.BeanJerarquia;
import com.promesa.pedidos.bean.BeanMarcaIndicador;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.bean.BeanPedidoPartners;
import com.promesa.pedidos.bean.BeanSede;
import com.promesa.pedidos.bean.BeanTipologia;
import com.promesa.pedidos.bean.Indicador;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.bean.MarcaVendedor;
import com.promesa.pedidos.bean.NombreMarcaEstrategica;
import com.promesa.pedidos.sql.impl.SqlDivisionImpl;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.util.Conexiones;
import com.promesa.util.Constante;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class SPedidos {
	public static final String PRECIO_NETO_CERO = "0.0";
	public static final String MATERIAL_POP = "950000";

	@SuppressWarnings({ "rawtypes", "unused" })
	public List<String[]> obtenerItemsSimulados(BeanPedido pedido) {
		try {
			BeanPedidoHeader header = pedido.getHeader();
			List<BeanPedidoDetalle> materiales = pedido.getDetalles();
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_SIMULATE");
				con.CreaTabla("ORDER_HEADER_IN");
				con.IngresarDatoTabla(header.getDOC_TYPE(), "DOC_TYPE", 1);
				con.IngresarDatoTabla(header.getSALES_ORG(), "SALES_ORG", 1);
				con.IngresarDatoTabla(header.getDISTR_CHAN(), "DISTR_CHAN", 1);
				con.IngresarDatoTabla(header.getDIVISION(), "DIVISION", 1);
				con.IngresarDatoTabla(header.getSALES_GRP(), "SALES_GRP", 1);
				con.IngresarDatoTabla(header.getSALES_OFF(), "SALES_OFF", 1);
				con.IngresarDatoTabla(header.getREQ_DATE_H(), "REQ_DATE_H", 1);
				con.IngresarDatoTabla(header.getPURCH_DATE(), "PURCH_DATE", 1);
				con.IngresarDatoTabla(header.getPMNTTRMS(), "PMNTTRMS", 1);
				con.IngresarDatoTabla(header.getDLV_BLOCK(), "DLV_BLOCK", 1);
				con.IngresarDatoTabla(header.getPRICE_DATE(), "PRICE_DATE", 1);
				con.IngresarDatoTabla(Constante.NOMBRE_PROGRAMA, "PURCH_NO_C", 1);
				con.IngresarDatoTabla(header.getSD_DOC_CAT(), "SD_DOC_CAT", 1);
				con.IngresarDatoTabla(header.getDOC_DATE(), "DOC_DATE", 1);
				con.IngresarDatoTabla(header.getBILL_DATE(), "BILL_DATE", 1);
				con.IngresarDatoTabla(header.getSERV_DATE(), "SERV_DATE", 1);
				con.IngresarDatoTabla(header.getCURRENCY(), "CURRENCY", 1);
				con.IngresarDatoTabla(header.getCREATED_BY(), "CREATED_BY", 1);
				con.CreaTabla("ORDER_PARTNERS");
				Set set = pedido.getPartners().getHm().entrySet();
				Iterator it = set.iterator();
				int pos = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					con.IngresarDatoTabla(me.getKey().toString(), "PARTN_ROLE", pos);
					con.IngresarDatoTabla(me.getValue().toString(), "PARTN_NUMB", pos);
					con.IngresarDatoTabla("EC", "COUNTRY", pos);
					pos++;
				}
				con.CreaTabla("ORDER_ITEMS_IN");
				if (materiales.size() > 0) {
					pos = 1;
					for (BeanPedidoDetalle material : materiales) {
						con.IngresarDatoTabla(material.getPosicion(), "ITM_NUMBER", pos);
						con.IngresarDatoTabla(material.getMaterial(), "MATERIAL", pos);
						String cantidad = "";
						try {
							cantidad = "" + ((int) Double.parseDouble(material.getCantidad()));
						} catch (Exception e) {
							cantidad = "0";
						}
						con.IngresarDatoTabla(cantidad, "TARGET_QTY", pos);
						con.IngresarDatoTabla(cantidad, "REQ_QTY", pos);
						pos++;
					}
				}
				con.EjecutarRFC();

				HashMap<String, String> descuentos = new HashMap<String, String>();
				con.CreaTabla("ORDER_CONDITION_EX");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] valores = cadena.split("¬");
					descuentos.put(valores[1], valores[5]);
				}
				con.CreaTabla("ET_MSG");
				ur = con.ObtenerDatosTabla();
				String mensaje = "";
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String values[] = cadena.split("¬");
					if (values.length > 3) {
						mensaje = mensaje + values[3] + "\n";
					}
				}
				List<String[]> items = new ArrayList<String[]>();
				if (mensaje.isEmpty()) {
					con.CreaTabla("ORDER_SCHEDULE_EX");
					ur = con.ObtenerDatosTabla();
					for (int i = 0; i < ur.size(); i++) {
						String cadena = String.valueOf(ur.get(i));
					}
					con.CreaTabla("ORDER_ITEMS_OUT");
					ur = con.ObtenerDatosTabla();
					for (int i = 0; i < ur.size(); i++) {
						String cadena = String.valueOf(ur.get(i));
						String[] values = cadena.split("¬");
						String code = "";
						String cantidadConfirmada = "";
						String cantidad = "";
						String valorNeto = "";
						String iva = "";
						String pu = "";
						Double precioUnitario = 0d;
						try {
							iva = "" + Double.parseDouble(values[33]);
						} catch (Exception e) {
							iva = values[33];
						}
						try {
							code = "" + Integer.parseInt(values[3]);
						} catch (Exception e) {
							code = values[3];
						}
						try {
							cantidad = "" + (int) Double.parseDouble(values[31]);
						} catch (Exception e) {
							cantidadConfirmada = values[31];
						}
						try {
							cantidadConfirmada = "" + Double.parseDouble(values[15]) / (int) 1000;
						} catch (Exception e) {
							cantidadConfirmada = values[15];
						}
						try {
							valorNeto = "" + Double.parseDouble(values[28]);
						} catch (Exception e) {
							valorNeto = values[28];
						}
						try {
							double precioTotal = Double.parseDouble(valorNeto);
							int cantidadC = (int) Double.parseDouble(cantidadConfirmada);
							precioUnitario = precioTotal / cantidadC;
							pu = String.format("%.3f", precioUnitario).replaceAll(",", ".");
							
						} catch (Exception e) {
							precioUnitario = 0d;
						}
						String[] item = { values[1], code, cantidad, cantidadConfirmada, values[14], "0.0", values[5], pu, valorNeto, values[43], iva };
						items.add(item);
					}
				} else {
					
					if (mensaje.length() > 2) {
						mensaje = mensaje.substring(0, mensaje.length() - 1);
					}
					Mensaje.mostrarError(mensaje);
					con.DesconectarSAP();
					return null;
				}
				con.DesconectarSAP();
				return items;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List<String[]> invocarSimulacion(String DOC_TYPE, String SALES_ORG,
			String DISTR_CHAN, String DIVISION, String SALES_GRP,
			String SALES_OFF, String REQ_DATE_H, String PURCH_DATE,
			String PMNTTRMS, String DLV_BLOCK, String PRICE_DATE,
			String PURCH_NO_C, String SD_DOC_CAT, String DOC_DATE,
			String BILL_DATE, String SERV_DATE, String CURRENCY,
			String CREATED_BY, String SHIP_TYPE, HashMap<String, String> hm,
			List<BeanPedidoDetalle> materiales) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_SIMULATE");
				con.CreaTabla("ORDER_HEADER_IN");
				con.IngresarDatoTabla(DOC_TYPE, "DOC_TYPE", 1);
				con.IngresarDatoTabla(SALES_ORG, "SALES_ORG", 1);
				con.IngresarDatoTabla(DISTR_CHAN, "DISTR_CHAN", 1);
				con.IngresarDatoTabla(DIVISION, "DIVISION", 1);
				con.IngresarDatoTabla(SALES_GRP, "SALES_GRP", 1);
				con.IngresarDatoTabla(SALES_OFF, "SALES_OFF", 1);
				con.IngresarDatoTabla(REQ_DATE_H, "REQ_DATE_H", 1);
				con.IngresarDatoTabla(PURCH_DATE, "PURCH_DATE", 1);
				con.IngresarDatoTabla(PMNTTRMS, "PMNTTRMS", 1);
				con.IngresarDatoTabla(DLV_BLOCK, "DLV_BLOCK", 1);
				con.IngresarDatoTabla(Constante.NOMBRE_PROGRAMA, "PURCH_NO_C", 1);
				con.IngresarDatoTabla(CREATED_BY, "CREATED_BY", 1);
				con.CreaTabla("ORDER_PARTNERS");
				Set set = hm.entrySet();
				Iterator it = set.iterator();
				int pos = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					con.IngresarDatoTabla(me.getKey().toString(), "PARTN_ROLE", pos);
					con.IngresarDatoTabla(me.getValue().toString(), "PARTN_NUMB", pos);
					con.IngresarDatoTabla("EC", "COUNTRY", pos);
					pos++;
				}
				con.CreaTabla("ORDER_ITEMS_IN");
				if (materiales.size() > 0) {
					pos = 1;
					for (BeanPedidoDetalle material : materiales) {
						con.IngresarDatoTabla(material.getPosicion(), "ITM_NUMBER", pos);
						con.IngresarDatoTabla(material.getMaterial(), "MATERIAL", pos);
						con.IngresarDatoTabla(material.getCantidad(), "TARGET_QTY", pos);
						con.IngresarDatoTabla(material.getCantidad(), "REQ_QTY", pos);
						con.IngresarDatoTabla("1030", "PLANT", pos);
						pos++;
					}
				}
				con.EjecutarRFC();
				HashMap<String, String> descuentos = new HashMap<String, String>();
				con.CreaTabla("ORDER_CONDITION_EX");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] valores = cadena.split("¬");
					descuentos.put(valores[1], valores[5]);
				}
				con.CreaTabla("ET_MSG");
				ur = con.ObtenerDatosTabla();
				String mensaje = "";
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String values[] = cadena.split("¬");
					if (values.length > 3) {
						mensaje = mensaje + values[3] + "\n";
					}
				}
				List<String[]> items = new ArrayList<String[]>();
				if (mensaje.isEmpty()) {
					con.CreaTabla("ORDER_SCHEDULE_EX");
					ur = con.ObtenerDatosTabla();
					for (int i = 0; i < ur.size(); i++) {
						String cadena = String.valueOf(ur.get(i));
					}
					con.CreaTabla("ORDER_ITEMS_OUT");
					ur = con.ObtenerDatosTabla();
					for (int i = 0; i < ur.size(); i++) {
						String cadena = String.valueOf(ur.get(i));
						String[] values = cadena.split("¬");
						String code = "";
						String cantidadConfirmada = "";
						String cantidad = "";
						String valorNeto = "";
						String iva = "";
						String pu = "";
						Double precioUnitario = 0d;
						try {
							iva = "" + Double.parseDouble(values[33]);
						} catch (Exception e) {
							iva = values[33];
						}
						try {
							code = "" + Integer.parseInt(values[3]);
						} catch (Exception e) {
							code = values[3];
						}
						try {
							cantidad = "" + (int) Double.parseDouble(values[31]);
						} catch (Exception e) {
							cantidadConfirmada = values[31];
						}
						try {
							cantidadConfirmada = "" + Double.parseDouble(values[15]) / (int) 1000;
						} catch (Exception e) {
							cantidadConfirmada = values[15];
						}
						try {
							valorNeto = "" + Double.parseDouble(values[28]);
						} catch (Exception e) {
							valorNeto = values[28];
						}
						try {
							double precioTotal = Double.parseDouble(valorNeto);
							int cantidadC = (int) Double.parseDouble(cantidadConfirmada);
							precioUnitario = precioTotal / cantidadC;
							pu = String.format("%.3f", precioUnitario).replaceAll(",", ".");
						} catch (Exception e) {
							precioUnitario = 0d;
						}
						String[] item = { values[1], code, cantidad, cantidadConfirmada, values[14], "0.0", values[5], pu, valorNeto, values[43], iva };
						items.add(item);
					}
				} else {
					if (mensaje.length() > 2) {
						mensaje = mensaje.substring(0, mensaje.length() - 1);
					}
					Mensaje.mostrarError(mensaje);
					con.DesconectarSAP();
					return null;
				}
				con.DesconectarSAP();
				return items;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public String[] guardarPedido(BeanPedido pedido) {
		String mensaje[] = new String[3];
		String error = "";
		String codigo = "";
		try {
			BeanPedidoHeader h = pedido.getHeader();
			List<BeanPedidoDetalle> materiales = pedido.getDetalles();
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_CREATE");
				con.CreaTabla("ORDER_HEADER_IN");
				
				String secuencial = Util.eliminarCerosInicios(h.getStrCodVendedor()) + Util.completarCeros(7, ""+h.getSecuencialPedido()); 
				
				con.IngresarDatoTabla(h.getDOC_TYPE(), "DOC_TYPE", 1);
				con.IngresarDatoTabla(h.getSALES_ORG(), "SALES_ORG", 1);
				con.IngresarDatoTabla(h.getDISTR_CHAN(), "DISTR_CHAN", 1);
				con.IngresarDatoTabla(h.getDIVISION(), "DIVISION", 1);
				con.IngresarDatoTabla(h.getSALES_GRP(), "SALES_GRP", 1);
				con.IngresarDatoTabla(h.getSALES_OFF(), "SALES_OFF", 1);
				con.IngresarDatoTabla(h.getREQ_DATE_H(), "REQ_DATE_H", 1);
				con.IngresarDatoTabla(h.getPURCH_DATE(), "PURCH_DATE", 1);
				con.IngresarDatoTabla(h.getPMNTTRMS(), "PMNTTRMS", 1);
				con.IngresarDatoTabla(h.getDLV_BLOCK(), "DLV_BLOCK", 1);
				con.IngresarDatoTabla(Constante.NOMBRE_PROGRAMA, "PURCH_NO_C", 1);
				con.IngresarDatoTabla(h.getPURCH_NO_C(), "PURCH_NO_S", 1);
				con.IngresarDatoTabla(h.getCREATED_BY(), "CREATED_BY", 1);
				con.IngresarDatoTabla(h.getSHIP_COND(), "SHIP_COND", 1);
				con.IngresarDatoTabla(h.getPO_METHOD(), "PO_METHOD", 1);
				con.IngresarDatoTabla(secuencial, "REF_1", 1);
				con.CreaTabla("ORDER_PARTNERS");
				Set set = pedido.getPartners().getHm().entrySet();
				Iterator it = set.iterator();
				boolean encontrado = false;
				int i = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					String role = me.getKey().toString();
					if (role.compareTo("ZS") == 0) {
						encontrado = true;
						break;
					}
					i++;
				}
				if (!encontrado) {
					String codSuper = Util.obtenerCodigoSupervisor();
					pedido.getPartners().getHm().put("ZS", codSuper);
				}
				set = pedido.getPartners().getHm().entrySet();
				it = set.iterator();
				i = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					con.IngresarDatoTabla(me.getKey().toString(), "PARTN_ROLE", i);
					con.IngresarDatoTabla(me.getValue().toString(), "PARTN_NUMB", i);
					con.IngresarDatoTabla("EC", "COUNTRY", i);
					i++;
				}
				con.CreaTabla("ORDER_ITEMS_IN");
				if (materiales.size() > 0) {
					i = 1;
					String mensajeMaterialPop[] = new String[3];
					for (BeanPedidoDetalle material : materiales) {
						String pop = material.getMaterial();
						String precioNeto = material.getPrecioNeto();
						if(pop.compareTo(MATERIAL_POP) > 0 && precioNeto.equals(PRECIO_NETO_CERO)){
							mensajeMaterialPop[0] = "E";
							mensajeMaterialPop[1] = "Error: posición: " + material.getPosicion() + " Material POP.";
							mensajeMaterialPop[2] = "Error: Material POP";
							return mensajeMaterialPop;
						}
						con.IngresarDatoTabla(material.getPosicion(), "ITM_NUMBER", i);
						con.IngresarDatoTabla(material.getMaterial(), "MATERIAL", i);
						con.IngresarDatoTabla(material.getCantidad(), "TARGET_QTY", i);
						con.IngresarDatoTabla("1030", "PLANT", i);
						Double pd = 0d;
						String porcentaje = "";
						try {
							String porcenDesc = material.getPorcentajeDescuento();
							pd = Math.abs(Double.parseDouble(porcenDesc));
							porcentaje = "" + pd;
						} catch (Exception e) {
							porcentaje = material.getPorcentajeDescuento();
						}
						con.IngresarDatoTabla(porcentaje, "DEPREC_PER", i);
						String cantConfir = material.getCantidadConfirmada();
						con.IngresarDatoTabla(cantConfir, "MAXDEVAMNT", i);
						i++;
					}
				}
				con.EjecutarRFC();
				codigo = con.ObtenerDato("SALESDOCUMENT");
				con.CreaTabla("ET_MSG");
				List ur = con.ObtenerDatosTabla();
//				String mensaje[] = new String[3];
//				String error = "";
				boolean huboError = false;
				for (i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String values[] = cadena.split("¬");
					String cod = values[1];
					if (cod.compareTo("S") != 0) {
						huboError = true;
					}
					error = error + values[3] + "\n";
				}
				if (error.length() > 2) {
					error = (error.substring(0, error.length() - 1)).trim() + "\n";
				}
				if (!error.isEmpty()) {
					if (huboError) {
						mensaje[0] = "E";
					} else {
						mensaje[0] = "N";
					}
				} else {
					mensaje[0] = "E";
					error = "Error a generar pedido online\n";
					codigo = "-1";
				}
				mensaje[1] = error;
				mensaje[2] = codigo;
				return mensaje;
			} else {
				mensaje[0] = "E";
				error = "Error a generar pedido online\n";
				codigo = "-1";
				mensaje[1] = error;
				mensaje[2] = codigo;
				return mensaje;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public String[] crearOrden(String DOC_TYPE, String SALES_ORG, String DISTR_CHAN, String DIVISION, String SALES_GRP,
			String SALES_OFF, String REQ_DATE_H, String PURCH_DATE, String PMNTTRMS, String DLV_BLOCK, String PRICE_DATE,
			String PURCH_NO_C, String SD_DOC_CAT, String DOC_DATE, String BILL_DATE, String SERV_DATE, String CURRENCY,
			String CREATED_BY, String SHIP_TYPE, String SHIP_COND, HashMap<String, String> hm, List<BeanPedidoDetalle> materiales, String secuencialPedido) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_CREATE");
				con.CreaTabla("ORDER_HEADER_IN");
				con.IngresarDatoTabla(DOC_TYPE, "DOC_TYPE", 1);
				con.IngresarDatoTabla(SALES_ORG, "SALES_ORG", 1);
				con.IngresarDatoTabla(DISTR_CHAN, "DISTR_CHAN", 1);
				con.IngresarDatoTabla(DIVISION, "DIVISION", 1);
				con.IngresarDatoTabla(SALES_GRP, "SALES_GRP", 1);
				con.IngresarDatoTabla(SALES_OFF, "SALES_OFF", 1);
				con.IngresarDatoTabla(REQ_DATE_H, "REQ_DATE_H", 1);
				con.IngresarDatoTabla(PURCH_DATE, "PURCH_DATE", 1);
				con.IngresarDatoTabla(PMNTTRMS, "PMNTTRMS", 1);
				con.IngresarDatoTabla(DLV_BLOCK, "DLV_BLOCK", 1);
				con.IngresarDatoTabla(Constante.NOMBRE_PROGRAMA, "PURCH_NO_C", 1);
				con.IngresarDatoTabla(PURCH_NO_C, "PURCH_NO_S", 1);
				con.IngresarDatoTabla(CREATED_BY, "CREATED_BY", 1);
				con.IngresarDatoTabla(SHIP_COND, "SHIP_COND", 1);
				con.IngresarDatoTabla(secuencialPedido, "REF_1", 1);
				con.CreaTabla("ORDER_PARTNERS");
				Set set = hm.entrySet();
				boolean encontrado = false;
				Iterator it = set.iterator();
				int i = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					String role = me.getKey().toString();
					String codigo = me.getValue().toString();
					int codigoValor = 0;
					try {
						codigoValor = Integer.parseInt(codigo);
					} catch (Exception e) {
						codigoValor = 0;
					}
					if (role.compareTo("ZS") == 0 && codigoValor != 0) {
						encontrado = true;
						break;
					}
					i++;
				}
				if (!encontrado) {
					hm.put("ZS", Util.obtenerCodigoSupervisor());
				}
				set = hm.entrySet();
				it = set.iterator();
				i = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					con.IngresarDatoTabla(me.getKey().toString(), "PARTN_ROLE", i);
					con.IngresarDatoTabla(me.getValue().toString(), "PARTN_NUMB", i);
					con.IngresarDatoTabla("EC", "COUNTRY", i);
					i++;
				}
				con.CreaTabla("ORDER_ITEMS_IN");
				if (materiales.size() > 0) {
					i = 1;
					for (BeanPedidoDetalle m : materiales) {
						con.IngresarDatoTabla(m.getPosicion(), "ITM_NUMBER", i);
						con.IngresarDatoTabla(m.getMaterial(), "MATERIAL", i);
						con.IngresarDatoTabla(m.getCantidad(), "TARGET_QTY", i);
						Double pd = 0d;
						String porcentaje = "";
						try {
							pd = Math.abs(Double.parseDouble(m.getPorcentajeDescuento()));
							porcentaje = "" + pd;
						} catch (Exception e) {
							porcentaje = "0.0";
						}
						con.IngresarDatoTabla(porcentaje, "DEPREC_PER", i);
						con.IngresarDatoTabla(m.getCantidadConfirmada(), "MAXDEVAMNT", i);
						con.IngresarDatoTabla("1030", "PLANT", i);
						i++;
					}
				}
				con.EjecutarRFC();
				String codigo = con.ObtenerDato("SALESDOCUMENT");
				con.CreaTabla("ET_MSG");
				List ur = con.ObtenerDatosTabla();
				String mensaje[] = new String[3];
				String error = "";
				boolean huboError = false;
				for (i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String values[] = cadena.split("¬");
					String cod = values[1];
					if (cod.compareTo("S") != 0) {
						huboError = true;
					}
					error = error + values[3] + "\n";
				}
				if (error.length() > 2) {
					error = (error.substring(0, error.length() - 1)).trim();
				}
				if (!error.isEmpty()) {
					if (huboError) {
						mensaje[0] = "E";
					} else {
						mensaje[0] = "N";
					}
				}
				mensaje[1] = error;
				mensaje[2] = codigo;
				return mensaje;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public String[] editarOrden(BeanPedido pedido) {
		try {
			BeanPedidoHeader header = pedido.getHeader();
			List<BeanPedidoDetalle> materiales = pedido.getDetalles();
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_UPDATE_OFF");
				con.IngresarDatosInput(Util.completarCeros(10, pedido.getCodigoPedido()), "IV_DOC_ID");
				con.CreaTabla("ORDER_HEADER_IN");
				con.IngresarDatoTabla(header.getDOC_TYPE(), "DOC_TYPE", 1);
				con.IngresarDatoTabla(header.getSALES_ORG(), "SALES_ORG", 1);
				con.IngresarDatoTabla(header.getDISTR_CHAN(), "DISTR_CHAN", 1);
				con.IngresarDatoTabla(header.getDIVISION(), "DIVISION", 1);
				con.IngresarDatoTabla(header.getSALES_GRP(), "SALES_GRP", 1);
				con.IngresarDatoTabla(header.getSALES_OFF(), "SALES_OFF", 1);
				con.IngresarDatoTabla(header.getREQ_DATE_H(), "REQ_DATE_H", 1);
				con.IngresarDatoTabla(header.getPURCH_DATE(), "PURCH_DATE", 1);
				con.IngresarDatoTabla(header.getPMNTTRMS(), "PMNTTRMS", 1);
				con.IngresarDatoTabla(header.getDLV_BLOCK(), "DLV_BLOCK", 1);
				con.IngresarDatoTabla(Constante.NOMBRE_PROGRAMA, "PURCH_NO_C", 1);
				con.IngresarDatoTabla(header.getPURCH_NO_C(), "PURCH_NO_S", 1);
				con.IngresarDatoTabla(header.getCREATED_BY(), "CREATED_BY", 1);
				con.IngresarDatoTabla(header.getSHIP_COND(), "SHIP_COND", 1);
				con.CreaTabla("ORDER_PARTNERS");
				Set set = pedido.getPartners().getHm().entrySet();
				Iterator it = set.iterator();
				int i = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					con.IngresarDatoTabla(me.getKey().toString(), "PARTN_ROLE", i);
					con.IngresarDatoTabla(me.getValue().toString(), "PARTN_NUMB", i);
					con.IngresarDatoTabla("EC", "COUNTRY", i);
					i++;
				}
				con.CreaTabla("ORDER_ITEMS_IN");
				if (materiales.size() > 0) {
					i = 1;
					for (BeanPedidoDetalle material : materiales) {
						con.IngresarDatoTabla(material.getPosicion(), "ITM_NUMBER", i);
						con.IngresarDatoTabla(material.getMaterial(), "MATERIAL", i);
						con.IngresarDatoTabla(Util.formatearNumero(material.getCantidad()), "TARGET_QTY", i);
						String descuentoManual = "";
						try {
							descuentoManual = "" + Math.abs(Double.parseDouble(material.getPorcentajeDescuento()));
						} catch (Exception e) {
							descuentoManual = material.getPorcentajeDescuento();
						}
						con.IngresarDatoTabla(descuentoManual, "DEPREC_PER", i);
						con.IngresarDatoTabla(material.getCantidadConfirmada(), "MAXDEVAMNT", i);
						con.IngresarDatoTabla("1030", "PLANT", i);
						i++;
					}
				}
				con.EjecutarRFC();
				con.CreaTabla("ET_MSG");
				List ur = con.ObtenerDatosTabla();
				String mensaje[] = new String[3];
				String error = "";
				boolean huboError = false;
				for (i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String values[] = cadena.split("¬");
					String cod = values[1];
					if (cod.compareTo("S") != 0) {
						huboError = true;
					}
					error = error + values[3] + "\n";
				}
				if (error.length() > 2) {
					error = (error.substring(0, error.length() - 1)).trim();
				}
				if (!error.isEmpty()) {
					if (huboError) {
						mensaje[0] = "E";
					} else {
						mensaje[0] = "N";
					}
				}
				mensaje[1] = error;
				mensaje[2] = pedido.getCodigoPedido();
				con.DesconectarSAP();
				return mensaje;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public String[] crearCotizacion(BeanPedido pedido) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_CREATE_QUOTATION");
				con.CreaTabla("ORDER_HEADER_IN");
				BeanPedidoHeader header = pedido.getHeader();
				List<BeanPedidoDetalle> materiales = pedido.getDetalles();
				con.IngresarDatoTabla(header.getDOC_TYPE(), "DOC_TYPE", 1);
				con.IngresarDatoTabla(header.getSALES_ORG(), "SALES_ORG", 1);
				con.IngresarDatoTabla(header.getDISTR_CHAN(), "DISTR_CHAN", 1);
				con.IngresarDatoTabla(header.getDIVISION(), "DIVISION", 1);
				con.IngresarDatoTabla(header.getSALES_GRP(), "SALES_GRP", 1);
				con.IngresarDatoTabla(header.getSALES_OFF(), "SALES_OFF", 1);
				con.IngresarDatoTabla(header.getREQ_DATE_H(), "REQ_DATE_H", 1);
				con.IngresarDatoTabla(header.getPURCH_DATE(), "PURCH_DATE", 1);
				con.IngresarDatoTabla(header.getPMNTTRMS(), "PMNTTRMS", 1);
				con.IngresarDatoTabla(header.getDLV_BLOCK(), "DLV_BLOCK", 1);
				con.IngresarDatoTabla(Constante.NOMBRE_PROGRAMA, "PURCH_NO_C", 1);
				con.IngresarDatoTabla(header.getPURCH_NO_C(), "PURCH_NO_S", 1);
				con.IngresarDatoTabla(header.getCREATED_BY(), "CREATED_BY", 1);
				con.IngresarDatoTabla(header.getSHIP_COND(), "SHIP_COND", 1);
				con.CreaTabla("ORDER_PARTNERS");
				Set set = pedido.getPartners().getHm().entrySet();
				Iterator it = set.iterator();
				int i = 1;
				while (it.hasNext()) {
					Map.Entry me = (Map.Entry) it.next();
					con.IngresarDatoTabla(me.getKey().toString(), "PARTN_ROLE", i);
					con.IngresarDatoTabla(me.getValue().toString(), "PARTN_NUMB", i);
					con.IngresarDatoTabla("EC", "COUNTRY", i);
					i++;
				}
				con.CreaTabla("ORDER_ITEMS_IN");

				if (materiales.size() > 0) {
					i = 1;
					for (BeanPedidoDetalle material : materiales) {
						con.IngresarDatoTabla(material.getPosicion(), "ITM_NUMBER", i);
						con.IngresarDatoTabla(material.getMaterial(), "MATERIAL", i);
						con.IngresarDatoTabla(material.getCantidad(), "TARGET_QTY", i);
						con.IngresarDatoTabla(material.getPrecioNeto(), "RNDDLV_QTY", i);
						con.IngresarDatoTabla(material.getPorcentajeDescuento(), "DEPREC_PER", i);
						con.IngresarDatoTabla(material.getCantidadConfirmada(), "MAXDEVAMNT", i);
						con.IngresarDatoTabla("1030", "PLANT", i);i++;
					}
				}
				con.EjecutarRFC();
				String codigo = con.ObtenerDato("SALESDOCUMENT");
				con.CreaTabla("ET_MSG");
				List ur = con.ObtenerDatosTabla();
				String mensaje[] = new String[3];
				String error = "";
				boolean huboError = false;
				for (i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String values[] = cadena.split("¬");
					String cod = values[1];
					if (cod.compareTo("S") != 0) {
						huboError = true;
					}
					error = error + values[3] + "\n";
				}
				if (error.length() > 2) {
					error = (error.substring(0, error.length() - 1)).trim();
				}
				if (!error.isEmpty()) {
					if (huboError) {
						mensaje[0] = "E";
					} else {
						mensaje[0] = "N";
					}
				}
				mensaje[1] = error;
				mensaje[2] = codigo;
				return mensaje;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<BeanMaterial> buscarPromoOferta() {
		try {
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Promoferta");
			List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_MATERIAL_BUSQCON1");
				con.EjecutarRFC();
				con.CreaTabla("ET_MAT_BUSQCON1");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Promoferta");
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					String codigo = "";
					try {
						codigo = "" + Long.parseLong(values[1]);
					} catch (Exception e) {
						codigo = values[1];
					}
					SqlMaterialImpl sql = new SqlMaterialImpl();
					BeanMaterial material = sql.getMaterial(codigo);
					if (material != null) {
						material.setPrice_1("0.0");
						materiales.add(material);
					}
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return materiales;
			} else {
				return null;
			}
		} catch (Exception ex) {
			Util.mostrarExcepcion(ex);
			return null;
		}
	}

	public List<BeanAgenda> clienteAgenda(String strIdVendedor, String strFecha) {
		try {
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Agenda");
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_AGENDA3");
				con.IngresarDatosInput(strIdVendedor, "IV_VENDOR_ID");
				con.IngresarDatosInput(strFecha, "IV_DATE");
				con.EjecutarRFC();
				con.CreaTabla("ET_AGENDA");
				@SuppressWarnings("rawtypes")
				List ur = con.ObtenerDatosTabla();
				BeanAgenda ba = null;
				List<BeanAgenda> ure = new ArrayList<BeanAgenda>();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Agenda");
					String cadena = String.valueOf(ur.get(i));
					String[] temporal = cadena.split("¬");
					ba = new BeanAgenda();
					ba.setStrTipo(temporal[21]);
					ba.setStrHora(temporal[6]);
					ba.setStrCodigoCliente(temporal[7]);
					ba.setStrNombreCliente(temporal[8]);
					ba.setStrDireccionCliente(temporal[9]);
					ba.setStrTelefonoCliente(temporal[10]);
					ba.setStrClaseRiesgo(temporal[16]);
					ba.setStrLimiteCredito(temporal[15]);
					ba.setStrDisponible(temporal[17]);
					ba.setStrCondicionPago(temporal[23]);
					ure.add(ba);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return ure;
			} else {
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private String convierteFecha(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List<BeanPedidoHeader> vendedorPedidos(String strVendorId, String strDocType, String strFechaInicio, String strFechaFin) {
		try {
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Pedidos");
			List<BeanPedidoHeader> ure = new ArrayList<BeanPedidoHeader>();
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_FIND3");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla("", "ORDER_ID", 1);
				con.IngresarDatoTabla(strVendorId, "VENDOR_ID", 1); 
				con.IngresarDatoTabla("", "CUST_ID", 1);
				con.IngresarDatoTabla(strDocType, "DOC_TYPE", 1);
				con.IngresarDatoTabla(strFechaInicio, "LOW_DATE", 1);
				con.IngresarDatoTabla(strFechaFin, "HIGH_DATE", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_ORDER");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Pedidos");
					String cadena = String.valueOf(ur.get(i));
					String valores[] = cadena.split("¬");
					BeanPedidoHeader bean = new BeanPedidoHeader();
					Integer temp = Integer.parseInt(valores[1]);
					bean.setStrDocumentoVenta(temp.toString());
					bean.setStrFechaDocumento(convierteFecha(valores[2]));
					bean.setStrFechaCreacion(convierteFecha(valores[3]));
					bean.setStrCreador(valores[4]);
					bean.setStrValorNeto(valores[5]);
					bean.setStrClDocVentas(valores[6]);
					temp = Integer.parseInt(valores[7]);
					bean.setStrCodVendedor(temp.toString());
					temp = Integer.parseInt(valores[12]);
					bean.setStrCodCliente(temp.toString());
					bean.setStrCliente(valores[13]);
					bean.setStatus(valores[14]);
					/*
					 *	Marcelo Moyano
					 */
					bean.setStrDocumentoReferencia(valores[15]);
					String valor16 = valores[16];
					String fReferen = this.convierteFecha(valor16);
					String strFechareferencia = (!valor16.equals("null")) ? fReferen : ""; 
					bean.setStrFechaReferencia(strFechareferencia);
					bean.setSource(Constante.FROM_SAP);
					ure.add(bean);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.CreaTabla("ET_MSG");
				List mensaje = con.ObtenerDatosTabla();
				for (int i = 0; i < mensaje.size(); i++) {
					String msg = String.valueOf(ur.get(i));
				}
				con.DesconectarSAP();
				return ure;
			} else {
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return null;
			}
		} catch (Exception e) {
			Promesa.getInstance().mostrarAvisoSincronizacion("");
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List<BeanCliente> vendedorClientes(String strVendorId, String strCodCliente, String strNombreCiente) {
		List<BeanCliente> ure = new ArrayList<BeanCliente>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Vendedor-Clientes");
		try {
			if (!strCodCliente.isEmpty()) {
				strCodCliente = "" + Long.parseLong(strCodCliente);
			}
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				//con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND4");
				//con.CreaTabla("IS_VENDOR");
				//con.IngresarDatoTabla(strVendorId, "VENDOR_ID", 1);
				//con.CreaTabla("IS_SEARCH_OPTIONS");
				//con.IngresarDatoTabla(strCodCliente, "CUST_ID", 1);
				//con.EjecutarRFC();
				//con.CreaTabla("ET_CUSTOMER");
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND2_2");
	            con.CreaTabla("IS_VENDOR");
	            con.IngresarDatoTabla(strVendorId, "VENDOR_ID", 1);
	            con.CreaTabla("IS_SEARCH_OPTIONS");
	            con.IngresarDatoTabla(strCodCliente, "CUST_ID", 1);
	            con.EjecutarRFC();
	            con.CreaTabla("ET_CUSTOMER");
				List ur = con.ObtenerDatosTabla();
				for (int i = 1; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Vendedor-Clientes");
					String cadena = String.valueOf(ur.get(i));
					String valores[] = cadena.split("¬");
					BeanCliente bean = new BeanCliente();
					Integer temp = Integer.parseInt(valores[1]);
					bean.setStrIdCliente(temp.toString());
					bean.setStrNombreCliente(valores[2]);
					bean.setStrDireccionCliente(valores[3]);
					bean.setStrTelefonoTrabajoCliente(valores[4]);
					bean.setStrNumeroFaxCliente(valores[5]);
					bean.setStrCodigoTipologia(valores[18]);
					bean.setStrDescripcionTipologia(valores[19]);
					long id_cliente = Long.parseLong(valores[1]);
					strCodCliente = strCodCliente.trim();
					strNombreCiente = strNombreCiente.trim();
					String aux_gui = strNombreCiente.toLowerCase();
					String aux_sap = valores[2].toLowerCase();
					if (strCodCliente.isEmpty()) {
						if (strNombreCiente.isEmpty()) {
							ure.add(bean);
						} else {
							if (aux_sap.contains(aux_gui)) {
								ure.add(bean);
							}
						}
					} else {
						if (strCodCliente.compareTo("" + id_cliente) == 0) {
							if (strNombreCiente.isEmpty()) {
								ure.add(bean);
							} else {
								if (aux_sap.contains(aux_gui)) {
									ure.add(bean);
								}
							}
						}
					}
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.CreaTabla("ET_MSG");
				List mensaje = con.ObtenerDatosTabla();
				for (int i = 0; i < mensaje.size(); i++) {
					String msg = String.valueOf(ur.get(i));
				}
				con.DesconectarSAP();
				return ure;
			} else {
				return null;
			}
		} catch (Exception exec) {
			Util.mostrarExcepcion(exec);
			return ure;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List<BeanCliente> vendedorClientes2(String strVendorId, String strCodCliente, String strNombreCiente) {
		List<BeanCliente> ure = new ArrayList<BeanCliente>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Vendedor-Clientes");
		try {
			if (!strCodCliente.isEmpty()) {
				strCodCliente = "" + Long.parseLong(strCodCliente);
			}
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND2_2");
				con.CreaTabla("IS_VENDOR");
				con.IngresarDatoTabla(strVendorId, "VENDOR_ID", 1);
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla(strCodCliente, "CUST_ID", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_CUSTOMER");
				List ur = con.ObtenerDatosTabla();
				for (int i = 1; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Vendedor-Clientes");
					String cadena = String.valueOf(ur.get(i));
					String valores[] = cadena.split("¬");
					BeanCliente bean = new BeanCliente();
					Integer temp = Integer.parseInt(valores[1]);
					bean.setStrIdCliente(temp.toString());
					bean.setStrNombreCliente(valores[2]);
					bean.setStrDireccionCliente(valores[3]);
					bean.setStrTelefonoTrabajoCliente(valores[4]);
					bean.setStrNumeroFaxCliente(valores[5]);
					bean.setStrCodOrgVentas(valores[6]);
					bean.setStrCodCanalDist(valores[7]);
					bean.setStrCodSector(valores[8]);
					bean.setStrGrupoVentas(valores[9]);
					bean.setStrOficinaVentas(valores[10]);
					bean.setStrCiudadCliente(valores[11]);
					bean.setStrCondicionPagoDefecto(valores[16]);
					bean.setIndicadorIva(valores[17]);
					bean.setStrMarcaBloqueoAlmacen(valores[19]);
					bean.setStrCodigoTipologia(valores[20]);
					bean.setStrDescripcionTipologia(valores[21]);
					
					try{
						bean.setStrClase(valores[22]);
					} catch (IndexOutOfBoundsException indexEx){
						bean.setStrClase("");
					}
					
					long id_cliente = Long.parseLong(valores[1]);
					strCodCliente = strCodCliente.trim();
					strNombreCiente = strNombreCiente.trim();
					String aux_gui = strNombreCiente.toLowerCase();
					String aux_sap = valores[2].toLowerCase();
					if (strCodCliente.isEmpty()) {
						if (strNombreCiente.isEmpty()) {
							ure.add(bean);
						} else {
							if (aux_sap.contains(aux_gui)) {
								ure.add(bean);
							}
						}
					} else {
						if (strCodCliente.compareTo("" + id_cliente) == 0) {
							if (strNombreCiente.isEmpty()) {
								ure.add(bean);
							} else {
								if (aux_sap.contains(aux_gui)) {
									ure.add(bean);
								}
							}
						}
					}
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.CreaTabla("ET_MSG");
				List mensaje = con.ObtenerDatosTabla();
				for (int i = 0; i < mensaje.size(); i++) {
					String msg = String.valueOf(ur.get(i));
				}
				con.DesconectarSAP();
				return ure;
			} else {
				return null;
			}
		} catch (Exception exec) {
			Util.mostrarExcepcion(exec);
			return ure;
		}
	}

	private List<BeanJerarquia> obtenerJerarquiaPorNivel(String level) {
		try {
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Jerarquía por nivel");
			List<BeanJerarquia> ure = new ArrayList<BeanJerarquia>();
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_HIERARCHY_GET");
				con.IngresarDatosInput("", "IV_HERARCHY_PARENT_ID");
				con.IngresarDatosInput(level, "IV_LEVEL");
				con.EjecutarRFC();
				con.CreaTabla("T_HERARCHY");
				@SuppressWarnings("rawtypes")
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Jerarquía por nivel");
					String cadena = String.valueOf(ur.get(i));
					String[] valores = cadena.split("¬");
					String PRODH = valores[1] == null ? "" : valores[1];
					String S = valores[2] == null ? "" : valores[2];
					String VTEXT = valores[3] == null ? "" : valores[3];
					String ZZSEQ = valores[4] == null ? "" : valores[4];
					String ICON = valores[5] == null ? "" : valores[5];
					String I = valores[6] == null ? "" : valores[6];
					String CELL_DESING = valores[7] == null ? "" : valores[7];
					BeanJerarquia bean = new BeanJerarquia();
					bean.setStrPRODH(PRODH);
					bean.setStrS(S);
					bean.setStrVTEXT(VTEXT);
					bean.setStrZZSEQ(ZZSEQ);
					bean.setStrICON(ICON);
					bean.setStrI(I);
					bean.setStrCellDesign(CELL_DESING);
					ure.add(bean);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return ure;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("finally")
	public List<BeanJerarquia>[] jerarquiaMateriales() {
		@SuppressWarnings("unchecked")
		List<BeanJerarquia>[] jerarquia = new List[5];
		try {
			jerarquia[0] = obtenerJerarquiaPorNivel("0");
			jerarquia[1] = obtenerJerarquiaPorNivel("1");
			jerarquia[2] = obtenerJerarquiaPorNivel("2");
			jerarquia[3] = obtenerJerarquiaPorNivel("3");
			jerarquia[4] = obtenerJerarquiaPorNivel("4");
		} catch (Exception e) {
			jerarquia = null;
		} finally {
			return jerarquia;
		}
	}

	@SuppressWarnings("unchecked")
	public List<BeanJerarquia> listaJerarquia() {
		List<BeanJerarquia>[] ArrayJerarquia = new List[5];
		List<BeanJerarquia> listJerarquia = new ArrayList<BeanJerarquia>();
		List<BeanJerarquia> listPadreTemp = null;
		try {
			for (int i = 0; i < ArrayJerarquia.length; i++) {
				ArrayJerarquia[i] = obtenerJerarquiaPorNivel(i + "");
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		boolean bandera = false;
		boolean hereda = false;
		for (List<BeanJerarquia> listaJer : ArrayJerarquia) {
			if (bandera) {
				for (BeanJerarquia beanHijo : listaJer) {
					for (BeanJerarquia beanPadre : listPadreTemp) {
						if (beanHijo.getStrPRODH().startsWith(beanPadre.getStrPRODH())) {
							hereda = true;
							beanHijo.setStrIdPadre(beanPadre.getStrPRODH());
							break;
						}
					}
					if (hereda) {
						listJerarquia.add(beanHijo);
						hereda = false;
					}
				}
				listPadreTemp = listaJer;
			} else {
				for (BeanJerarquia bean : listaJer) {
					String prodh = bean.getStrPRODH();
					bean.setStrPRODH(prodh);
					bean.setStrIdPadre("");
					listJerarquia.add(bean);
				}
				listPadreTemp = listaJer;
				bandera = true;
			}
		}
		return listJerarquia;
	}
	
	public static List<BeanMaterial> obtenerListaMaterialNuevo() throws Exception {
        List<BeanMaterial> listaMaterialNuevo = new ArrayList<BeanMaterial>();
        Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Lista de materiales nuevos");
        ConexionSAP con = Conexiones.getConexionSAP();
        if (con != null) {
            con.RegistrarRFC("ZSD_RFC_MATERIAL_NEW");            
            con.EjecutarRFC();
            con.CreaTabla("ET_MATERIAL");//TODO cambiar 
            List<String> ur = con.ObtenerDatosTabla();
            for (int i = 0; i < ur.size(); i++) {
            	Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Lista de materiales nuevos");
                String cadena = String.valueOf(ur.get(i));
                String[] valores = cadena.split("¬");
                BeanMaterial material = new BeanMaterial();
                material.setIdMaterial("" + Integer.parseInt(valores[2]));
                material.setZzordco(Util.convierteFechayyyyMMdd(valores[3]));
                material.setNormt(valores[4]);
                listaMaterialNuevo.add(material);
            }
            con.DesconectarSAP();
            } else {
        }
        Promesa.getInstance().mostrarAvisoSincronizacion("");
        return listaMaterialNuevo;
    }
	
	@SuppressWarnings("rawtypes")
	public List<BeanMaterial> listaMateriales(String hierarchy) {
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Lista de materiales");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_MATERIAL_GET_PRI_LIST5");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla("", "ID", 1);
				con.IngresarDatoTabla(hierarchy + "*", "HIERARCHY", 1);
				con.IngresarDatoTabla("", "SHORT_TEXT", 1);
				con.IngresarDatoTabla("", "NORMT", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_MATERIAL");
				List ur = con.ObtenerDatosTabla();
				SqlMaterialImpl sql = new SqlMaterialImpl();
				HashMap<String, String> hm = sql.existeMaterial();
				HashMap<String, String> hmp = sql.existeMaterialPrice1();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Lista de materiales");
					String cadena = String.valueOf(ur.get(i));
					String[] valores = cadena.split("¬");
					String MATNR = "" + Integer.parseInt(valores[1]);
					String STOCK = "" + Double.parseDouble(valores[2]);
					String S_U = valores[3];
					String SHORT_TEXT = valores[4].replaceAll("'", "''");
					String TEXT_LINE = valores[5].replaceAll("'", "''");
					String TARGET_QTY = "" + Double.parseDouble(valores[6]);
					String PRICE_1 = "" + Double.parseDouble(valores[7]);
					String PRICE_2 = "" + Double.parseDouble(valores[8]);
					String PRICE_3 = "" + Double.parseDouble(valores[9]);
					String PRICE_4 = "" + Double.parseDouble(valores[10]);
					String PRDHA = valores[11];
					String HER = valores[12];
					String NORMT = valores[13];
					String CELL_DESIGN = valores[15];
					String MTART = valores[16];
					String TYPEMAT = valores[17];
					String GRUPO_COMPRA = valores[18];
					String ST_1 = valores[19];
					@SuppressWarnings("unused")
					String LAEDA = valores[20];
					String ZZORDCO = valores[14];
					String temporal = hm.get(MATNR);
					String temporalPrice = hmp.get(MATNR);
					String fec_ing = valores[21];
					String costo = valores[22];
					String margen_obj = valores[23];
					if (temporal == null) {
						materiales.add(new BeanMaterial(MATNR, STOCK, S_U, SHORT_TEXT, TEXT_LINE, TARGET_QTY, PRICE_1, PRICE_2, PRICE_3, PRICE_4, PRDHA, HER, NORMT, ZZORDCO, CELL_DESIGN, MTART, TYPEMAT, GRUPO_COMPRA, ST_1, Constante.AGREGAR_MATERIAL,fec_ing,costo,margen_obj));
					} else {
						// ACTUALIZA MATERIAL
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						Date fechaSQLite = sdf.parse(temporal, new ParsePosition(0));
//						Date fechaSAP = sdf.parse(LAEDA, new ParsePosition(0));
//						if (fechaSQLite == null || fechaSQLite.before(fechaSAP) || !(temporalPrice.equals(PRICE_1))) {
						if (fechaSQLite == null ||  !(temporalPrice.equals(PRICE_1))) {
							materiales.add(new BeanMaterial(MATNR, STOCK, S_U, SHORT_TEXT, TEXT_LINE, TARGET_QTY, PRICE_1, PRICE_2, PRICE_3, PRICE_4, PRDHA, HER, NORMT, ZZORDCO, CELL_DESIGN, MTART, TYPEMAT, GRUPO_COMPRA, ST_1, Constante.ACTUALIZAR_MATERIAL,fec_ing,costo,margen_obj));
						}
					}
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return materiales;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<BeanMaterial> listaMaterialesStock(String _MATNR) {
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Stock de materiales");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_MATERIAL_GET_MAT_STOCK");
				con.IngresarDatosInput(_MATNR, "P_MATNR");
				con.EjecutarRFC();
				con.CreaTabla("ET_MATERIAL_STOCK");
				List ur = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				BeanMaterial m = null;
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Stock de materiales");
					m = new BeanMaterial();
					String cadena = String.valueOf(ur.get(i));
					String[] valores = cadena.split("¬");
					String MATNR = "" + Integer.parseInt(valores[1]);
					String STOCK = "" + Double.parseDouble(valores[2]);
					m.setIdMaterial(MATNR);
					m.setStock(STOCK);
					materiales.add(m);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return materiales;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<List<BeanMaterial>> listaMaterialesConsultaDinamica() {
		List<List<BeanMaterial>> lista = new ArrayList<List<BeanMaterial>>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Consulta dinámica");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_MATERIAL_BUSQCON1");
				con.EjecutarRFC();
				con.CreaTabla("ET_MAT_BUSQCON1");
				List ur1 = con.ObtenerDatosTabla();
				con.CreaTabla("ET_STRUCT_DSCTO");
				List ur2 = con.ObtenerDatosTabla();
				con.CreaTabla("ET_CLASS_MAT");
				List ur3 = con.ObtenerDatosTabla();
				con.CreaTabla("ET_MARCA_MAT");
				List ur4 = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				List<BeanMaterial> listaMateriales1 = new ArrayList<BeanMaterial>();
				List<BeanMaterial> listaMateriales2 = new ArrayList<BeanMaterial>();
				List<BeanMaterial> listaMateriales3 = new ArrayList<BeanMaterial>();
				String codigo = "";
				String tipo = "";
				String[] values = null;
				BeanMaterial bm = null;
				List<BeanMaterial> l = null;
				for (int i = 0; i < ur1.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Consulta dinámica");
					String cadena = String.valueOf(ur1.get(i));
					values = cadena.split("¬");
					bm = new BeanMaterial();
					bm.setIdMaterial("" + Integer.parseInt(values[1]));
					if (values[3].trim().equals("005"))
						listaMateriales1.add(bm);
					if (values[3].trim().equals("004"))
						listaMateriales2.add(bm);
					if (values[3].trim().equals("006"))
						listaMateriales3.add(bm);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				String c1 = "";
				String c2 = "";
				String c3 = "";
				String c4 = "";
				String c5 = "";
				String codigoJerarquia = "";
				SqlDivisionImpl sqlDivision = new SqlDivisionImpl();
				l = new ArrayList<BeanMaterial>();
				for (int i = 0; i < ur2.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Consulta dinámica");
					String cadena = String.valueOf(ur2.get(i));
					values = cadena.split("¬");
					c1 = "" + values[2];
					c2 = "" + values[3];
					c3 = "" + values[4];
					c4 = "" + values[5];
					c5 = "" + values[6];
//					tipo = values[10];
					if (values.length == 11) {
						tipo = values[10];
					} else {
						tipo = "005";
					}
					codigoJerarquia = c1 + c2 + c3 + c4 + c5;
					l = sqlDivision.getListaPrecios(codigoJerarquia);
					if (tipo.trim().equals("005"))
						listaMateriales1.addAll(l);
					if (tipo.trim().equals("004"))
						listaMateriales2.addAll(l);
					if (tipo.trim().equals("006"))
						listaMateriales3.addAll(l);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				SqlMaterialImpl sqlMaterial = new SqlMaterialImpl();
				l = new ArrayList<BeanMaterial>();
				for (int i = 0; i < ur3.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Consulta dinámica");
					String cadena = String.valueOf(ur3.get(i));
					values = cadena.split("¬");
					codigo = values[1];
					tipo = values[3];
					l = sqlMaterial.obtenerMaterialesPorClaseMaterial(codigo);
					if (tipo.trim().equals("005"))
						listaMateriales1.addAll(l);
					if (tipo.trim().equals("004"))
						listaMateriales2.addAll(l);
					if (tipo.trim().equals("006"))
						listaMateriales3.addAll(l);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				l = new ArrayList<BeanMaterial>();
				for (int i = 0; i < ur4.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Consulta dinámica");
					String cadena = String.valueOf(ur4.get(i));
					values = cadena.split("¬");
					codigo = values[1];
					tipo = values[3];
					l = sqlMaterial.buscarMaterialPorMarca(codigo);
					if (tipo.trim().equals("005"))
						listaMateriales1.addAll(l);
					if (tipo.trim().equals("004"))
						listaMateriales2.addAll(l);
					if (tipo.trim().equals("006"))
						listaMateriales3.addAll(l);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				lista.add(listaMateriales1);
				lista.add(listaMateriales2);
				lista.add(listaMateriales3);
				return lista;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<List<BeanMaterial>> listaMaterialesTop(String strCodVendedor) {
		try {
			List<List<BeanMaterial>> listaFinal = new ArrayList<List<BeanMaterial>>();
			List<BeanMaterial> listaTemp = null;
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Top de materiales");
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Top de materiales");
				listaTemp = new ArrayList<BeanMaterial>();
				con.RegistrarRFC("ZSD_RFC_ORDER_GET_CANTID3");
				con.IngresarDatosInput(strCodVendedor, "V_KUNRG");
				con.EjecutarRFC();
				con.CreaTabla("ET_MATERIAL");
				List ur1 = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				String[] values = null;
				BeanMaterial bm = null;
				for (int i = 0; i < ur1.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Top de materiales");
					String cadena = String.valueOf(ur1.get(i));
					values = cadena.split("¬");
					bm = new BeanMaterial();
					bm.setIdMaterial("" + Integer.parseInt(values[2]));
					bm.setDblAcumulado(Double.parseDouble(values[3]));
					bm.setDblPromedio(Double.parseDouble(values[4]));
					bm.setStrCodCliente("" + Integer.parseInt(values[1]));
					listaTemp.add(bm);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				listaFinal.add(listaTemp);
				return listaFinal;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<List<BeanMaterial>> listaMaterialesTopTipol(String strCodVendedor) {
		List<List<BeanMaterial>> listaFinal = new ArrayList<List<BeanMaterial>>();
		List<BeanMaterial> listaTemp = new ArrayList<BeanMaterial>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Top por tipología");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Top por tipología");
				listaTemp = new ArrayList<BeanMaterial>();
				con.RegistrarRFC("ZSD_RFC_GROUP_CLIENT_TOP3");
				con.IngresarDatosInput(strCodVendedor, "V_KUNNR");
				con.EjecutarRFC();
				con.CreaTabla("T_GRUOP");
				List ur1 = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				String[] values = null;
				BeanMaterial bm = null;
				for (int i = 0; i < ur1.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Top por tipología");
					String cadena = String.valueOf(ur1.get(i));
					values = cadena.split("¬");
					bm = new BeanMaterial();
					bm.setIdMaterial("" + Integer.parseInt(values[2]));
					bm.setDblAcumulado(Double.parseDouble(values[3]));
					bm.setDblPromedio(Double.parseDouble(values[4]));
					bm.setStrCodCliente(values[1]);
					listaTemp.add(bm);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				listaFinal.add(listaTemp);
				return listaFinal;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static List<List<Item>> listaCombos() {
		List<List<Item>> result = new ArrayList<List<Item>>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Combos");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_COMB_SELECT");
				con.EjecutarRFC();
				con.CreaTabla("T_COM");
				List listCombos = con.ObtenerDatosTabla();
				con.CreaTabla("T_MATCOM");
				List listCombosHijos = con.ObtenerDatosTabla();
				List<Item> listaCombos = new ArrayList<Item>();
				List<Item> listaCombosHijos = new ArrayList<Item>();
				con.DesconectarSAP();
				Item beanItem = null;
				Item beanItemHijo = null;
				String cadena = "";
				Item beanItemPadre = null;
				for (int i = 0; i < listCombosHijos.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Combos");
					beanItemHijo = new Item();
					cadena = String.valueOf(listCombosHijos.get(i));
					String[] valores = cadena.split("¬");
					String strCodCombo = "";
					try {
						Long l = Long.parseLong(valores[1]);
						strCodCombo = "" + l;
					} catch (Exception e) {
						strCodCombo = valores[1];
					}
					String MATNR_COMBO = "" + strCodCombo;
					String strCod = "";
					try {
						Long l = Long.parseLong(valores[3]);
						strCod = "" + l;
					} catch (Exception e) {
						strCod = valores[3];
					}
					String MATNR = "" + strCod;
					String MENGE = "" + (int) Double.parseDouble(valores[4]);
					String MEINS = "" + valores[5];
					beanItemPadre = new Item();
					beanItemPadre.setCodigo(MATNR_COMBO);
					beanItemHijo.setPadre(beanItemPadre);
					beanItemHijo.setCodigo(MATNR);
					beanItemHijo.setCantidad(MENGE);
					beanItemHijo.setUnidad(MEINS);
					listaCombosHijos.add(beanItemHijo);
				}
				for (int i = 0; i < listCombos.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Combos");
					beanItem = new Item();
					cadena = String.valueOf(listCombos.get(i));
					String[] valores = cadena.split("¬");
					String strCodCombo = "";
					try {
						Long l = Long.parseLong(valores[1]);
						strCodCombo = "" + l;
					} catch (Exception e) {
						strCodCombo = valores[1];
					}
					String MATNR = "" + strCodCombo;
					String SHORT_TEXT = "" + valores[2];
					String MEINS = "" + valores[3];
					beanItem.setCodigo(MATNR);
					beanItem.setDenominacion(SHORT_TEXT);
					beanItem.setUnidad(MEINS);
					listaCombos.add(beanItem);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				result.add(listaCombos);
				result.add(listaCombosHijos);
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<BeanCondicionComercial1> listaCondiciones1() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones comerciales");
		List<BeanCondicionComercial1> lista = new ArrayList<BeanCondicionComercial1>();
		ConexionSAP con = null;
		try {
			con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_DESCUENTO_PAGO");
				con.EjecutarRFC();
				con.CreaTabla("ET_DESCUENTO_PAGO");
				List<String> listCondiciones1 = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				BeanCondicionComercial1 bean = null;
				String cadena = "";
				for (int i = 0; i < listCondiciones1.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones comerciales");
					bean = new BeanCondicionComercial1();
					cadena = String.valueOf(listCondiciones1.get(i));
					String[] valores = cadena.split("¬");
					int intPrioridad1 = 0;
					int intPrioridad2 = 0;
					try {
						intPrioridad1 = Integer.parseInt(valores[2]);
					} catch (Exception e) {
						intPrioridad1 = 0;
					}
					try {
						intPrioridad2 = Integer.parseInt(valores[3]);
					} catch (Exception e) {
						intPrioridad2 = 0;
					}
					bean.setIntPrioridadGrupo(intPrioridad1);
					bean.setIntPrioridadInterna(intPrioridad2);
					bean.setStrClaseMaterial(valores[4]);
					if (bean.getStrClaseMaterial().trim().equals(""))
						bean.setStrClaseMaterial("*");
					bean.setStrCliente(valores[5]);
					if (bean.getStrCliente().trim().equals(""))
						bean.setStrCliente("*");
					bean.setStrCondicionPago(valores[6]);
					if (bean.getStrCondicionPago().trim().equals(""))
						bean.setStrCondicionPago("*");
					if (valores[8].indexOf("-") != -1) {
						bean.setStrTipo("D");
					} else {
						bean.setStrTipo("C");
					}
					String strCifra = "" + valores[8].substring(0, valores[8].length());
					Double d = 0d;
					if (!strCifra.isEmpty()) {
						try {
							d = Double.parseDouble(strCifra);
						} catch (Exception e) {
							d = 0d;
						}
					}
					bean.setDblDscto(d);
					lista.add(bean);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return lista;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<BeanCondicionComercial2> listaCondiciones2() {
		List<BeanCondicionComercial2> lista = new ArrayList<BeanCondicionComercial2>();
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones comerciales");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_DESCUENTO_CANAL");
				con.EjecutarRFC();
				con.CreaTabla("ET_DESCUENTO_CANAL");
				List<String> listCondiciones2 = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				BeanCondicionComercial2 bean = null;
				String cadena = "";
				for (int i = 0; i < listCondiciones2.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones comerciales");
					bean = new BeanCondicionComercial2();
					cadena = String.valueOf(listCondiciones2.get(i));
					String[] valores = cadena.split("¬");
					int intPrioridad1 = 0;
					int intPrioridad2 = 0;
					try {
						intPrioridad1 = Integer.parseInt(valores[2]);
					} catch (Exception e) {
						intPrioridad1 = 0;
					}
					try {
						intPrioridad2 = Integer.parseInt(valores[3]);
					} catch (Exception e) {
						intPrioridad2 = 0;
					}
					bean.setIntPrioridadGrupo(intPrioridad1);
					bean.setIntPrioridadInterna(intPrioridad2);
					bean.setStrCliente(valores[4]);
					if (bean.getStrCliente().trim().equals(""))
						bean.setStrCliente("*");
					bean.setStrGrupoCliente(valores[5]);
					if (bean.getStrGrupoCliente().trim().equals(""))
						bean.setStrGrupoCliente("*");
					bean.setStrCanal(valores[6]);
					if (bean.getStrCanal().trim().equals(""))
						bean.setStrCanal("*");
					if (valores[8].indexOf("-") != -1) {
						bean.setStrTipo("D");
					} else {
						bean.setStrTipo("C");
					}
					String strCifra = "" + valores[8].substring(0, valores[8].length());
					Double d = 0d;
					if (!strCifra.isEmpty()) {
						try {
							d = Double.parseDouble(strCifra);
						} catch (Exception e) {
							d = 0d;
						}
					}
					bean.setDblDscto(d);
					lista.add(bean);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return lista;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<BeanClaseMaterial> listaClaseMaterial() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Clase de materiales");
		List<BeanClaseMaterial> lista = new ArrayList<BeanClaseMaterial>();
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_MATERIAL_CLASES_MAT");
				con.EjecutarRFC();
				con.CreaTabla("ET_MATERIALES_CLASES");
				List<String> listClaseMaterial = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				BeanClaseMaterial bean = null;
				String cadena = "";
				SqlMaterialImpl sql = new SqlMaterialImpl();
				HashMap<String, String> hm = sql.getClaseMaterial();
				for (int i = 0; i < listClaseMaterial.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion( "SAP: Clase de materiales");
					bean = new BeanClaseMaterial();
					cadena = String.valueOf(listClaseMaterial.get(i));
					String[] valores = cadena.split("¬");
					String codigoClase = "";
					String descripcion = valores[2];
					String codigoMaterial = "";
					try {
						codigoClase = "" + Long.parseLong(valores[1]);
					} catch (Exception e) {
						codigoClase = valores[1];
					}
					try {
						codigoMaterial = "" + Long.parseLong(valores[3]);
					} catch (Exception e) {
						codigoMaterial = valores[3];
					}
					String claseMaterial = codigoMaterial;
					String key = hm.get(codigoClase + "," + claseMaterial);
					if (key == null) {
						bean.setStrCodigoClaseMaterial(codigoClase);
						bean.setStrDescripcionClaseMaterial(descripcion.replaceAll("''", ""));
						bean.setStrCodigoMaterial(codigoMaterial);
						lista.add(bean);
					}
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return lista;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public BeanPedido obtenerDetallePedido(String idPedido) {
		try {
			idPedido = Util.completarCeros(10, idPedido);
			BeanPedido pedido = new BeanPedido();
			List<BeanPedidoDetalle> lista = new ArrayList<BeanPedidoDetalle>();
			BeanPedidoHeader header = new BeanPedidoHeader();
			BeanPedidoPartners partners = new BeanPedidoPartners();
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_GET2");
				con.IngresarDatosInput(idPedido, "IV_SALES_DOC_ID");
				con.EjecutarRFC();
				con.CreaTabla("ES_ORDER_HEADERS");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					if (values != null && values.length > 8) {
						header.setSALES_ORG(values[6]);
						header.setDISTR_CHAN(values[7]);
						header.setDIVISION(values[8]);
						header.setDLV_BLOCK(values[26]);
						header.setSHIP_COND(values[48]);
						header.setNAME(values[17]);
					}
				}
				con.CreaTabla("ES_ORDER_BUSINESS");
				ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					header.setStrCondicionPago(values[17]);
					header.setStrClDocVentas(values[45]);
				}
				HashMap<String, String> descuentos = new HashMap<String, String>();
				con.CreaTabla("ET_CONDITIONS");
				ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					descuentos.put(values[4], values[14]);
				}
				con.CreaTabla("ET_PARTNERS");
				HashMap<String, String> hmPartners = new HashMap<String, String>();
				ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					hmPartners.put(values[1], values[2]);
				}
				partners.setHm(hmPartners);
				con.CreaTabla("ET_ORDER_ITEMS");
				ur = con.ObtenerDatosTabla();
				Double dPesoNetoTotal = 0d;
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					
					BeanPedidoDetalle detalle = new BeanPedidoDetalle();
					String posicion = values[3];
					String strPesoNeto = values[61];
					String porcentajeDescuento = descuentos.get(posicion);
					try {
						double porcentaje = Double.parseDouble(porcentajeDescuento);
						porcentajeDescuento = "" + porcentaje;
					} catch (Exception e) {
						porcentajeDescuento = "0.0";
					}
					String material = "";
					try {
						material = "" + Long.parseLong(values[4]);
					} catch (NumberFormatException e) {
						material = values[4];
					}
					Double a = 0d;
					String valorNeto = "0";
					try {
						a = Double.parseDouble(values[46]);
						valorNeto = "" + a;
					} catch (NumberFormatException e) {
						valorNeto = values[46];
					}
					Double b = 0d;
					String cantidadConfirmada = "0";
					try {
						b = Double.parseDouble(values[54]);
						cantidadConfirmada = "" + b;
					} catch (NumberFormatException e) {
						cantidadConfirmada = values[54];
					}
					Double dPesoNeto = 0d;
					try{
						dPesoNeto = Double.parseDouble(strPesoNeto);
						dPesoNetoTotal += dPesoNeto;
						
					}catch(NumberFormatException e) {
					}
					String precioNeto = "0";
					if (b != 0) {
						Double r = (a / b);
						r = Math.round(r * 100) / 100.0d;
						precioNeto = String.format("%.3f", r).replaceAll(",", ".");
					}
					String categoria = values[10];
					String posicionDetalle = values[14];
					detalle.setTipoSAP(categoria);
					if(!posicionDetalle.equals("000000")){
						detalle.setTipo(2);
					} else if (categoria.compareTo("ZP01") == 0 || categoria.compareTo("ZO01") == 0) {
						detalle.setTipo(0);
					} else if (categoria.compareTo("ZPP1") == 0 || categoria.compareTo("ZOP1") == 0 || categoria.compareTo("ZPP3") == 0) {
						detalle.setTipo(1);
					} 
//					else if (categoria.compareTo("ZPH1") == 0 || categoria.compareTo("ZOH1") == 0 || categoria.compareTo("ZPB3") == 0) {
//						detalle.setTipo(2);
//					} 
					detalle.setPosicion(posicion);
					detalle.setPorcentajeDescuento(porcentajeDescuento);
					detalle.setMaterial(material);
					double dCantidad = Double.parseDouble(values[52]);
					int icantidad = (int) dCantidad;
					detalle.setCantidad(String.valueOf(icantidad));
					detalle.setCantidadConfirmada(cantidadConfirmada);
					if(values[20].equals("")) {
						SqlMaterialImpl mat = new SqlMaterialImpl();
						BeanMaterial m = mat.getMaterial(detalle.getMaterial());
						detalle.setUM(m.getUn());
					}else {  
						detalle.setUM(values[20]);// ARREGLAR
					}
					detalle.setDenominacion(values[9]);
					detalle.setPrecioNeto(precioNeto);
					detalle.setValorNeto(valorNeto);
					detalle.setMotivoRechazo(values[16]);
					detalle.setdPesoNeto(dPesoNeto);
					lista.add(detalle);
				}
				String strEstadoPicking = con.ObtenerDato("V_KOSTK");
				header.setStrEstadoPicking(strEstadoPicking);
				int cifras=(int) Math.pow(10,3);
				header.setNET_WEIGHT(String.valueOf(Math.rint(dPesoNetoTotal*cifras)/cifras));
				con.DesconectarSAP();
				pedido.setHeader(header);
				pedido.setPartners(partners);
				pedido.setDetalles(lista);
				con.DesconectarSAP();
				return pedido;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public List<String[]> obtenerCondicionesPago() throws Exception {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones de pago");
		List<String[]> sucursales = new ArrayList<String[]>();
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_CONDICION_PAGO");
				con.EjecutarRFC();
				con.CreaTabla("ET_COND_PAGO");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones de pago");
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					String[] string = new String[2];
					string[0] = values[2];
					string[1] = values[3];
					sucursales.add(string);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return sucursales;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<BeanSede> listarSucursales(String idCustomer) {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Destinatarios");
		List<BeanSede> sucursales = new ArrayList<BeanSede>();
		int longitud = ("" + idCustomer).length();
		try {
			if (longitud > 0) {
				idCustomer = Util.completarCeros(10, idCustomer);
				ConexionSAP con = Conexiones.getConexionSAP();
				if (con != null) {
					con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_SEDE2");
					con.IngresarDatosInput(idCustomer, "LV_USER_ID");
					con.EjecutarRFC();
					con.CreaTabla("ET_TEAM");
					List ur = con.ObtenerDatosTabla();
					for (int i = 0; i < ur.size(); i++) {
						BeanSede beanSede = new BeanSede();
						String cadena = String.valueOf(ur.get(i));
						String[] values = cadena.split("¬");
						String codigo = "";
						String idCliente = "";
						try {
							codigo = "" + Integer.parseInt(values[2]);
						} catch (Exception e) {
							codigo = values[2];
						}
						try {
							idCliente = "" + Integer.parseInt(values[9]);
						} catch (Exception e) {
							idCliente = values[9];
						}
						beanSede.setCodigo(codigo.replaceAll("'", ""));
						beanSede.setDireccion(values[10].replaceAll("'", ""));
						beanSede.setIdCliente(idCliente.replaceAll("'", ""));
						sucursales.add(beanSede);
					}
					con.DesconectarSAP();
				}
			}
			return sucursales;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public List<BeanBloqueoEntrega> obtenerBloqueos() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Bloqueos de entrega");
		List<BeanBloqueoEntrega> bloqueos = new ArrayList<BeanBloqueoEntrega>();
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_GET_BLOQUEO");
				con.EjecutarRFC();
				con.CreaTabla("ET_TVLST");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Bloqueos de entrega");
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					BeanBloqueoEntrega bloqueo = new BeanBloqueoEntrega(null, values[3], values[4]);
					bloqueos.add(bloqueo);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return bloqueos;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<BeanAgenda> obtenerAgenda(BeanBuscarPedido param) {
		try{
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Agenda");
		}catch(Exception e1){
		}
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				List<BeanAgenda> listaAgenda = new ArrayList<BeanAgenda>();
				con.RegistrarRFC("ZSD_RFC_AGENDA3");
				con.IngresarDatosInput(param.getStrVendorId(), "IV_VENDOR_ID");
				con.IngresarDatosInput(param.getStrFechaFin(), "IV_DATE");
				con.EjecutarRFC();
				con.CreaTabla("ET_AGENDA");
				BeanAgenda beanAgenda = null;
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					try{
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Obtener agenda");
					}catch(Exception e2){}
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					beanAgenda = new BeanAgenda();
					beanAgenda.setVENDOR_ID(values[2]);
					beanAgenda.setVENDOR_NAME(values[3]);
					beanAgenda.setBEGDA(values[4]);
					beanAgenda.setENDDA(values[5]);
					beanAgenda.setTIME(values[6]);
					beanAgenda.setCUST_ID(values[7]);
					beanAgenda.setCUST_NAME(values[8]);
					beanAgenda.setCUST_ADDRES(values[9]);
					beanAgenda.setCUST_TELF1(values[10]);
					beanAgenda.setCUST_TELFX(values[11]);
					beanAgenda.setCUST_1(values[12]);
					beanAgenda.setCU_1(values[13]);
					beanAgenda.setCU_2(values[14]);
					beanAgenda.setCUST_KLIMK(values[15]);
					beanAgenda.setCUS(values[16]);
					beanAgenda.setCUST_AVAILABLE(values[17]);
					beanAgenda.setDESCRIPTION(values[18]);
					beanAgenda.setST(values[19]);
					beanAgenda.setSTAT(values[20]);
					beanAgenda.setTY(values[21]);
					beanAgenda.setTYPE(values[22]);
					beanAgenda.setCUST_2(values[23]);
					beanAgenda.setTYPE_DESCRIPTION(values[24]);
					beanAgenda.setESTADO("000"); // el valor 000 es algo referencial pero indica que todos los otros datos encapsulados al BEAN vienen de SAP
					listaAgenda.add(beanAgenda);
				}
				try{
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				}catch(Exception e3){}
				con.DesconectarSAP();
				if (listaAgenda.size() > 0) {
					return listaAgenda;
				} else {
					return null;
				}
			}
			return null;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	// Métodos usados para sincronizar
	@SuppressWarnings({ "unused" })
	private void guardarJerarquia() {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
			Connection connection = conn.getConnection();
			ResultSet rs = null;
			Statement stmt = null;
			List<BeanJerarquia>[] jerarquiaMaeriales = jerarquiaMateriales();
			if (con != null) {
				// INSERTAMOS LOS DOS PADRES DE MAS ALTO NIVEL
				for (BeanJerarquia bean : jerarquiaMaeriales[0]) {
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando jerarquía de materiales");
					String sql = "INSERT INTO PROFFLINE_TB_JERARQUIA VALUES(0,'','" + bean.getStrPRODH() + "','"
							+ bean.getStrS() + "','" + bean.getStrVTEXT() + "','"
							+ bean.getStrZZSEQ() + "','" + bean.getStrICON() + "','"
							+ bean.getStrI() + "','" + bean.getStrCellDesign() + "');";
					try {
						stmt = connection.createStatement();
						stmt.execute(sql);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
					}
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				for (int i = 1; i < jerarquiaMaeriales.length; i++) {
					boolean hereda = false;
					List<BeanJerarquia> list = jerarquiaMaeriales[i];
					for (BeanJerarquia hijo : list) {
						/*** Obtener el id del padre */
						for (BeanJerarquia padre : jerarquiaMaeriales[i - 1]) {
							if (hijo.getStrPRODH().startsWith(padre.getStrPRODH())) {
								hereda = true;
								hijo.setStrIdPadre(padre.getStrPRODH());
								break;
							}
						}
						if (hereda) {
							Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando jerarquía de materiales");
							String sql = "INSERT INTO PROFFLINE_TB_JERARQUIA VALUES(0,'"
									+ hijo.getStrIdPadre() + "','" + hijo.getStrPRODH()   + "','"
									+ hijo.getStrS()       + "','" + hijo.getStrVTEXT()   + "','"
									+ hijo.getStrZZSEQ()   + "','" + hijo.getStrICON()    + "','"
									+ hijo.getStrI() + "','" + hijo.getStrCellDesign() + "');";
							try {
								stmt = connection.createStatement();
								stmt.execute(sql);
							} catch (Exception e) {
								Util.mostrarExcepcion(e);
							}
							Promesa.getInstance().mostrarAvisoSincronizacion("");
						}
					}
				}
				con.DesconectarSAP();
			}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			Util.mostrarExcepcion(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public List<BeanCondicionPago> listaCondicionesPago() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Obtener condiciones de pago");
		List<BeanCondicionPago> listaCondicionesPago = null;
		listaCondicionesPago = new ArrayList<BeanCondicionPago>();
		BeanCondicionPago beanCondicionPago;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_CONDICION_PAGO");
				con.EjecutarRFC();
				con.CreaTabla("ET_COND_PAGO");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Obtener condiciones de pago");
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					if (values.length > 3) {
						beanCondicionPago = new BeanCondicionPago(values[2], values[3]);
						listaCondicionesPago.add(beanCondicionPago);
					}
				}
				con.DesconectarSAP();
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return listaCondicionesPago;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<BeanCondicionPago> listaCondicionesPagoDetalle() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones de pago");
		List<BeanCondicionPago> listaCondicionesPago = null;
		listaCondicionesPago = new ArrayList<BeanCondicionPago>();
		BeanCondicionPago beanCondicionPago;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_REL_COND_PAGO");
				con.EjecutarRFC();
				con.CreaTabla("ET_ZTCONSTANTES");
				List ur = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Condiciones de pago");
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					beanCondicionPago = new BeanCondicionPago();
					beanCondicionPago.setTxtZTERMH(values[4]);
					beanCondicionPago.setTxtZTERMD(values[7]);
					listaCondicionesPago.add(beanCondicionPago);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return listaCondicionesPago;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public List<BeanTipologia> listarTipologias() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Obtener lista de tipologias");
		List<BeanTipologia> listaBeanTipologia = new ArrayList<BeanTipologia>();
		BeanTipologia beanTipologia = null;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_GET_TIPO_MAT");
				con.EjecutarRFC();
				con.CreaTabla("ET_T134T");
				List ur = con.ObtenerDatosTabla();
				ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
				Connection connection = conn.getConnection();
				ResultSet rs = null;
				Statement stmt = null;
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Obtener lista de tipologias");
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					listaBeanTipologia.add(new BeanTipologia(values[3], values[4]));
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return listaBeanTipologia;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public String[] obtenerDatosCliente(String codigo) {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Obtener datos cliente");
		String[] datos = new String[3];
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_DETAIL");
				con.IngresarDatosInput(codigo, "IV_CUST_ID");
				con.EjecutarRFC();
				con.CreaTabla("ES_CUST_INFO");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Obtener datos cliente");
					String cadena = String.valueOf(ur.get(i));
					String[] valores = cadena.split("¬");
					datos[0] = valores[10].trim();
					datos[1] = valores[9].trim();
					datos[2] = valores[11].trim();
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return datos;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public List<BeanBloqueoEntrega> guardarBloqueosEntrega() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Bloqueos de entrega");
		BeanBloqueoEntrega beanBloqueoEntrega = null;
		List<BeanBloqueoEntrega> listaBloqueoEntrega;
		listaBloqueoEntrega = new ArrayList<BeanBloqueoEntrega>();
		ConexionSAP con = null;
		try {
			con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_GET_BLOQUEO");
				con.EjecutarRFC();
				con.CreaTabla("ET_TVLST");
				List ur = con.ObtenerDatosTabla();
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] values = cadena.split("¬");
					beanBloqueoEntrega = new BeanBloqueoEntrega();
					beanBloqueoEntrega.setCodigo(values[3]);
					beanBloqueoEntrega.setDescripcion(values[4]);
					listaBloqueoEntrega.add(beanBloqueoEntrega);
				}
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				con.DesconectarSAP();
				return listaBloqueoEntrega;
			} else {
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE OBTIENE LISTA DE FACTURAS Y MATERIALES */
	public List<BeanFactura> facturas(String idCliente) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			List<BeanFactura> lbf = new ArrayList<BeanFactura>();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_FACTURA_MATERIAL");
				con.IngresarDatosInput(idCliente, "V_KUNRG");
				con.EjecutarRFC();
				con.CreaTabla("IT_FACTURA_MAT_F");
				@SuppressWarnings("rawtypes")
				List facturas = con.ObtenerDatosTabla();
				BeanFactura bf = null;
				for (int i = 0; i < facturas.size(); i++) {
					String cadena = String.valueOf(facturas.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bf = new BeanFactura();
					String numeroFactura = "";
					try {
						numeroFactura = "" + Long.parseLong(temporal[1]);
					} catch (Exception e) {
						numeroFactura = temporal[2];
					}
					bf.setStrNFactura(numeroFactura);
					BeanMaterial bm = new BeanMaterial();
					String idMaterial = "";
					try {
						idMaterial = "" + Long.parseLong(temporal[2]);
					} catch (Exception e) {
						idMaterial = temporal[2];
					}
					bm.setIdMaterial(idMaterial);
					bm.setDescripcion(temporal[3]);
					bf.setMaterial(bm);
					Double precioNeto = 0d;
					Double precioNetoFacturado = 0d;
					Double cantidadFacturada = 0d;
					try {
						precioNetoFacturado = Double.parseDouble(temporal[5]);
					} catch (Exception e) {
						precioNetoFacturado = 1d;
					}
					try {
						cantidadFacturada = Double.parseDouble(temporal[4]);
					} catch (Exception e) {
						cantidadFacturada = 1d;
					}
					if (cantidadFacturada != 0d) {
						precioNeto = precioNetoFacturado / cantidadFacturada;
					}
					bf.setStrCFactura(String.valueOf(cantidadFacturada));
					bf.setStrPNeto(Util.formatearNumero(precioNeto));
					bf.setStrVNeto(Util.formatearNumero(temporal[5]));
					bf.setStrFFactura(Util.convierteFecha(temporal[6]));
					bf.setStrPUnitario(Util.formatearNumero(temporal[7]));
					bf.setStrDCanal(Util.formatearNumero(temporal[8]));
					bf.setStrD3x(Util.formatearNumero(temporal[9]));
					bf.setStrD4x(Util.formatearNumero(temporal[10]));
					bf.setStrDVolumen(Util.formatearNumero(temporal[11]));
					bf.setStrD5x(Util.formatearNumero(temporal[12]));
					bf.setStrDManual(Util.formatearNumero(temporal[13]));
					bf.setStrUVentas(temporal[14]);
					bf.setStrMarca(temporal[15]);
					bf.setStrCPedida(Util.formatearNumero(temporal[16]));
					try {
						bf.setStrFacturaSRI(temporal[17]);// Marcelo Moyano 12/05/2013 - 12:42	
					}catch (Exception ex){
						bf.setStrFacturaSRI("");// Marcelo Moyano 12/05/2013 - 12:42
					}
					lbf.add(bf);
					con.DesconectarSAP();
				}
			}
			con.DesconectarSAP();
			return lbf;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	// SUPERVISOR ASOCIADO A VENDEDOR
	public String obtenerCodigoSupervisor(String codigoVendedor) {
		String codigoSupervisor = "";
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_GET_OVERSEER");
				con.IngresarDatosInput(Util.completarCeros(10, codigoVendedor), "IS_KUNNR");
				con.EjecutarRFC();
				codigoSupervisor = con.ObtenerDato("ES_OVERSEER");
				con.DesconectarSAP();
			}
			return codigoSupervisor;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE PASA UNA COTIZACIÓN
	 * 			A PEDIDO
	 * 
	 * @param	RECIBE COMO PARAMETRO EL
	 * 			ID DE LA COTIZACIÓN
	 * 
	 * @return	RETORNA COMO PARAMETRO EL
	 * 			CODIGO DEL PEDIDO QUE SE HA
	 * 			CREADO LA PASAR LA COTIZACIÓN
	 * 			A PEDIDO
	 *  
	 * @time	13/05/2013 - 10:13
	 */
	public String PasarCotizacionAPedido(String id){
		String codigoPedido = null;
		try{
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				con.RegistrarRFC("ZSD_RFC_ORDER_CREATE_BY_REF");
				con.IngresarDatosInput(id, "IV_VBELN");
				con.IngresarDatosInput("ZP01", "IV_DOC_TYPE");
				con.EjecutarRFC();
				codigoPedido = con.ObtenerDato("EV_VBELN");
				con.CreaTabla("ET_MSG");
				con.DesconectarSAP();
				return codigoPedido;
			}else
				return null;
		}catch (Exception e){
			Util.mostrarExcepcion(e);
			return null;
		}
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			SINCRONIZA UNA CONSTANTE PARAMETRO
	 * 
	 * @return	RETORNA UNA LISTA DE PARAMETROS
	 */
	@SuppressWarnings("rawtypes")
	public List<BeanParametro> obtenerParametrosConstantes() {
		List<BeanParametro> listaParametro = null;
		listaParametro = new ArrayList<BeanParametro>();
		BeanParametro bp;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_GET_CONST_PROFFLINE");
				con.EjecutarRFC();
				con.CreaTabla("ET_CONST");
				List ur = con.ObtenerDatosTabla();
				if(ur.size() > 0){
					for (int i = 0; i < ur.size(); i++) {
						String cadena = String.valueOf(ur.get(i));
						String[] values = cadena.split("¬");
						bp = new BeanParametro();
						bp.setStrModulo(values[2].trim());
						bp.setStrNombrePrograma(values[3].trim());
						bp.setStrNombreCampo(values[4].trim());
						bp.setSecuencia(Integer.parseInt(values[5].trim()));
						bp.setStrOpcion(values[6].trim());
						bp.setStrValorUno(values[7].trim());
						bp.setStrValorDos(values[8].trim());
						bp.setStrNombreUsuario(values[9].trim());
						bp.setStrFecha("");
						listaParametro.add(bp);
					}
					con.DesconectarSAP();
					return listaParametro;
				}else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public BeanMarcaIndicador getMarcaEstrategica(String codigoVendedor){
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Marca Estrategica e indicadores");
				con.RegistrarRFC("ZSD_RFC_GET_MARCA_ESTRATEGICA");
				con.IngresarDatosInput(codigoVendedor, "PI_VENDEDOR");
//				con.IngresarDatosInput("20160320", "PI_FECHA");//YYYYMMDD
				con.EjecutarRFC();
				con.CreaTabla("PT_MARCAS");
				List<String> listMarcas = con.ObtenerDatosTabla();
				con.CreaTabla("PT_INDICADORES");
				List<String> listIndicadores = con.ObtenerDatosTabla();
				con.CreaTabla("PT_DENOMINACION");
				List<String> listNombres = con.ObtenerDatosTabla();
				con.CreaTabla("PT_MARCAVENDEDOR");
	            List<String> listMarcaVendedor = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				return obtenerData(listMarcas, listIndicadores, listNombres, listMarcaVendedor);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Util.mostrarExcepcion(e);
		}
		return null;
	}
	
	private BeanMarcaIndicador obtenerData(List<String> listMarcas, List<String> listIndicadores, List<String> listNombres, List<String> listMarcaVendedor){
		List<MarcaEstrategica> marcas = null;
		List<Indicador> indicadores = null;
		List<NombreMarcaEstrategica> nombres = null;
		if(listMarcas != null && listMarcas.size() > 0 ){
			marcas =  new ArrayList<MarcaEstrategica>();
			for(String s: listMarcas){
				MarcaEstrategica ms = new MarcaEstrategica();
				String[] values = s.split("¬");
				ms.setCodigoCliente(values[1]);
				ms.setMarca(values[2]);
				ms.setPresupuesto(values[3]);
				ms.setAcumulado(values[4]);
				ms.setFecha(Util.convierteFecha(values[5]));
				marcas.add(ms);
			}
		}
		if(listIndicadores != null && listIndicadores.size() > 0){
			indicadores = new ArrayList<Indicador>();
			for (String s: listIndicadores){
				Indicador i = new Indicador();
				String[] values = s.split("¬");
				i.setCodigoCliente(values[1]);
				i.setMonto(values[2]);
				i.setAcumulado(values[3]);
				i.setEstatus(values[4]);
				indicadores.add(i);
			}
		}
		if(listNombres != null && listNombres.size() > 0){
			nombres = new ArrayList<NombreMarcaEstrategica>();
			for (String s : listNombres) {
				NombreMarcaEstrategica n = new NombreMarcaEstrategica();
				String[] value = s.split("¬");
				n.setMarca(value[1]);
				n.setNombre(value[2]);
				nombres.add(n);
			}
		}
		List<MarcaVendedor> marcavend = new ArrayList<MarcaVendedor>();
        for (String s : listMarcaVendedor) {
            MarcaVendedor ms = new MarcaVendedor();
            String[] values = s.split("¬");
            ms.setCodigoCliente(values[1]);
            ms.setMarca(values[2]);
            ms.setPresupuestoMes(values[3]);
            ms.setVentaMes(values[4]);
            ms.setPresupuestoAcumulado(values[5]);
            ms.setVentaAcumulado(values[6]);
            ms.setFecha(Util.convierteFecha(values[7]));
            marcavend.add(ms);
        }
			
			BeanMarcaIndicador bean = new BeanMarcaIndicador();
			bean.setMarcas(marcas);
			bean.setIndicadores(indicadores);
			bean.setNombres(nombres);
			bean.setMarcavendedor(marcavend);
			return bean;
	}
	
}