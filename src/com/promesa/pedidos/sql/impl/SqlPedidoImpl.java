package com.promesa.pedidos.sql.impl;

import java.sql.Connection;
//import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.promesa.administracion.bean.BeanParametro;
import com.promesa.conexiondb.ConexionJDBC;
//import com.promesa.dao.ResultExecute;
//import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.bean.BeanPedidoPartners;
import com.promesa.pedidos.bean.BeanPedidosParaSap;
import com.promesa.pedidos.sql.SqlPedido;
import com.promesa.util.Constante;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class SqlPedidoImpl implements SqlPedido {

	@SuppressWarnings({ "rawtypes" })
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String insertarPedidos(BeanPedido pedido) throws Exception {
		BeanPedidoHeader h = pedido.getHeader();
		BeanPedidoPartners p = pedido.getPartners();
		List<BeanPedidoDetalle> detalles = pedido.getDetalles();
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sql = "INSERT INTO PROFFLINE_TB_PEDIDO_HEADER" + "(txtDisponible, txtLimiteCredito, txtClaseRiesgo, " 
				+ "NOMBRE_CLIENTE, COD_CLIENTE, VALOR_NETO, txtVendedor, " + "txtSincronizado, DOC_TYPE,SALES_ORG,"
				+ "DISTR_CHAN,DIVISION,SALES_GRP,SALES_OFF,REQ_DATE_H,PURCH_DATE,PMNTTRMS,DLV_BLOCK,PRICE_DATE,PURCH_NO_C,"
				+ "SD_DOC_CAT,DOC_DATE,BILL_DATE,SERV_DATE,CURRENCY,CREATED_BY,SHIP_TYPE,SHIP_COND, " 
				+ "SOURCE, CODIGOPEDIDOENSAP, PO_METHOD, SEC_PEDIDO) VALUES ('" + h.getTxtDisponible()
				+ "','" + h.getTxtLimiteCredito() + "','" + h.getTxtClaseRiesgo() + "','" + h.getStrCliente() + "','" 
				+ h.getStrCodCliente() 	  + "','" + h.getStrValorNeto()   + "','" 
				+ Util.completarCeros(10, h.getStrCodVendedor()) + "',0,'" + h.getDOC_TYPE() + "','" + h.getSALES_ORG() + "','" 
				+ h.getDISTR_CHAN() + "','" + h.getDIVISION() + "','" + h.getSALES_GRP() + "','" + h.getSALES_OFF() + "','" 
				+ h.getREQ_DATE_H() + "','" + h.getPURCH_DATE() + "','" + h.getPMNTTRMS() + "','" + h.getDLV_BLOCK() + "','"
				+ h.getPRICE_DATE() + "','" + h.getPURCH_NO_C() + "','" + h.getSD_DOC_CAT() + "','" + h.getDOC_DATE() + "','"
				+ h.getBILL_DATE() + "','" + h.getSERV_DATE() + "','" + h.getCURRENCY() + "','" + h.getCREATED_BY() + "','"
				+ h.getSHIP_TYPE() + "','" + h.getSHIP_COND() + "','" + h.getSource() + "','" + pedido.getCodigoPedido()+ "','" + h.getPO_METHOD() + "','" + h.getSecuencialPedido()+ "');";
		
		stmt = connection.createStatement();
		stmt.execute(sql);
		String id = "";
		HashMap column = new HashMap();
		column.put("String:0", "id");
		String sqlIid = "SELECT MAX(id) as id FROM PROFFLINE_TB_PEDIDO_HEADER LIMIT 1;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlIid, column, Constante.BD_TX);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			id = res.get("id").toString();
		}
		if (!id.isEmpty()) {
			HashMap<String, String> hm = p.getHm();
			Set set = hm.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext()) {
				Map.Entry me = (Map.Entry) i.next();
				sql = "INSERT INTO PROFFLINE_TB_PEDIDO_PARTNERS" + "(id_header, txtSincronizado, txtKey, txtValue) values ("
						+ id + ",0,'" + me.getKey().toString() + "','" + me.getValue().toString() + "');";
				stmt.execute(sql);
			}
			for (BeanPedidoDetalle b : detalles) {
				sql = "INSERT INTO PROFFLINE_TB_PEDIDO_DETALLE(id_header, txtSincronizado, "
						+ "txtPosicion, txtMaterial, txtCantidad, txtCantidadConfirmada, txtUM, "
						+ "txtPorcentajeDescuento, txtDenominacion, txtPrecioNeto, " 
						+ "txtValorNeto, tipo) values (" + id + ",0,'" + b.getPosicion() + "','" + b.getMaterial()
						+ "','" + b.getCantidad() + "','" + b.getCantidadConfirmada() + "','" + b.getUM()
						+ "','" + b.getPorcentajeDescuento() + "','" + b.getDenominacion().replaceAll("'", "''")
						+ "','" + b.getPrecioNeto() + "','" + b.getValorNeto() + "',"  + b.getTipo() + ");";
				stmt.execute(sql);
			}
		}
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
		return id;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public String actualizaPedido(BeanPedido pedido) throws Exception {
		BeanPedidoHeader h = pedido.getHeader();
		BeanPedidoPartners partners = pedido.getPartners();
		List<BeanPedidoDetalle> detalles = pedido.getDetalles();
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = null;
		sql = " UPDATE PROFFLINE_TB_PEDIDO_HEADER SET " + " DLV_BLOCK='" + h.getDLV_BLOCK() + "',SHIP_COND='" + h.getSHIP_COND() 
				+ "', " + " SHIP_TYPE='" + h.getSHIP_TYPE() + "',PMNTTRMS='"
				+ h.getPMNTTRMS() + "', VALOR_NETO = ' " + h.getStrValorNeto() + "' WHERE ID=" + pedido.getHeader().getIdBD() + " ";
		
		stmt = connection.createStatement();
		stmt.execute(sql);
		// SE ELIMINAN LOS PARTNERS //
		sql = " DELETE FROM PROFFLINE_TB_PEDIDO_PARTNERS WHERE ID_HEADER=" + pedido.getHeader().getIdBD();
		stmt.execute(sql);
		// SE ELIMINAN LOS PARTNERS //

		HashMap<String, String> hm = partners.getHm();
		Set set = hm.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			sql = "INSERT INTO PROFFLINE_TB_PEDIDO_PARTNERS" 
				+"(id_header, txtSincronizado, txtKey, txtValue) values ("
				+ pedido.getHeader().getIdBD()
				+ ",0,'" + me.getKey().toString()
				+ "','" + me.getValue().toString() + "');";
			stmt.execute(sql);
		}
		// SE ELIMINAN LOS ITEMS //
		sql = " DELETE FROM PROFFLINE_TB_PEDIDO_DETALLE WHERE ID_HEADER=" + pedido.getHeader().getIdBD();
		stmt.execute(sql);
		// SE ELIMINAN LOS ITEMS //
		for (BeanPedidoDetalle b : detalles) {
			sql = "INSERT INTO PROFFLINE_TB_PEDIDO_DETALLE(id_header, txtSincronizado, "
				+ "txtPosicion, txtMaterial, txtCantidad, txtCantidadConfirmada, txtUM, "
				+ "txtPorcentajeDescuento, txtDenominacion, txtPrecioNeto, " 
				+ "txtValorNeto, tipo) values (" + pedido.getHeader().getIdBD()
				+ ",0,'" + b.getPosicion()
				+ "','" + b.getMaterial()
				+ "','" + b.getCantidad()
				+ "','" + b.getCantidadConfirmada()
				+ "','" + b.getUM()
				+ "','" + b.getPorcentajeDescuento()
				+ "','" + b.getDenominacion().replaceAll("'", "''")
				+ "','" + b.getPrecioNeto()
				+ "','" + b.getValorNeto()
				+ "',"  + b.getTipo() + ");";
			stmt.execute(sql);
		}
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
		return pedido.getCodigoPedido();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BeanPedidoHeader> listarPedidos(String strVendorId, String fechaInicio, String strFechaFinal) {
		List<BeanPedidoHeader> pedidos = new ArrayList<BeanPedidoHeader>();
		column = new HashMap();
		column.put("String:0", "NOMBRE_CLIENTE");
		column.put("String:1", "COD_CLIENTE");
		column.put("String:2", "VALOR_NETO");
		column.put("String:3", "txtVendedor");
		column.put("String:4", "txtSincronizado");
		column.put("String:5", "SHIP_TYPE");
		column.put("String:6", "CREATED_BY");
		column.put("String:7", "CURRENCY");
		column.put("String:8", "SERV_DATE");
		column.put("String:9", "BILL_DATE");
		column.put("String:10", "DOC_DATE");
		column.put("String:11", "SD_DOC_CAT");
		column.put("String:12", "PURCH_NO_C");
		column.put("String:13", "PRICE_DATE");
		column.put("String:14", "DLV_BLOCK");
		column.put("String:15", "PMNTTRMS");
		column.put("String:16", "PURCH_DATE");
		column.put("String:17", "REQ_DATE_H");
		column.put("String:18", "SALES_OFF");
		column.put("String:19", "SALES_GRP");
		column.put("String:20", "DIVISION");
		column.put("String:21", "DISTR_CHAN");
		column.put("String:22", "SALES_ORG");
		column.put("String:23", "DOC_TYPE");
		column.put("String:24", "id");
		column.put("String:25", "txtDisponible");
		column.put("String:26", "txtLimiteCredito");
		column.put("String:27", "txtClaseRiesgo");
		column.put("String:28", "source");
		column.put("String:29", "codigopedidoensap");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PEDIDO_HEADER WHERE txtSincronizado=0 "
				+ "AND DOC_DATE >= '"   + fechaInicio
				+ "' AND DOC_DATE <= '" + strFechaFinal
				+ "' AND txtVendedor='" + Util.completarCeros(10, strVendorId) + "'";
		
		try {
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				BeanPedidoHeader p = new BeanPedidoHeader();
				p.setSource(Integer.parseInt((res.get("source")).toString()));
				p.setCodigoPedido((res.get("codigopedidoensap")).toString());
				String id = res.get("id").toString();
				p.setIdBD(id);
				Integer temp = Integer.parseInt(id);
				p.setStrDocumentoVenta(temp.toString());
				
				SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
				String strFecha = "" + res.get("DOC_DATE").toString();
				Date fecha = null;
				try {
					fecha = formatoDelTexto.parse(strFecha);
				} catch (ParseException ex) {
					Util.mostrarExcepcion(ex);
					Mensaje.mostrarError("La fecha de creación no es correcta.");
					fecha = new Date();
				}
				SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd.MM.yyyy");
				strFecha = formatoDeFecha.format(fecha);
				p.setStrFechaDocumento(strFecha);
				p.setStrFechaCreacion(strFecha);

				p.setStrCreador(res.get("CREATED_BY").toString());
				p.setStrClDocVentas(res.get("DOC_TYPE").toString());
				String codigoVendedor = "";
				try {
					codigoVendedor = "" + Integer.parseInt(res.get("txtVendedor").toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					codigoVendedor = res.get("txtVendedor").toString();
				}
				p.setStrCodVendedor(codigoVendedor);
				p.setStrValorNeto(("" + res.get("VALOR_NETO")).toString());
				p.setStrCliente(res.get("NOMBRE_CLIENTE").toString());
				p.setStrCodCliente(res.get("COD_CLIENTE").toString());
				p.setTxtClaseRiesgo(("" + res.get("txtClaseRiesgo")).toString());
				p.setTxtLimiteCredito(("" + res.get("txtLimiteCredito")).toString());
				p.setTxtDisponible(("" + res.get("txtDisponible")).toString());
				p.setSource(Integer.parseInt("" + res.get("source")));
				p.setStrDocumentoReferencia("");
				p.setStrFechaReferencia("");
				p.setStatus("");
				pedidos.add(p);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return pedidos;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BeanPedidosParaSap listarPedidosParaSaps(String idPedidoHeader) {

		BeanPedidosParaSap bpps = new BeanPedidosParaSap();
		HashMap<String, BeanPedidoHeader> mapH = new HashMap<String, BeanPedidoHeader>();
		HashMap<String, HashMap<String, String>> mapP = new HashMap<String, HashMap<String, String>>();
		HashMap<String, List<BeanPedidoDetalle>> mapD = new HashMap<String, List<BeanPedidoDetalle>>();
		List<BeanPedidoDetalle> listD = null;
		HashMap<String, String> map = null;
		BeanPedidoDetalle pd = null;
		String sql = null;
		
		column = new HashMap();
		column.put("String:0", "NOMBRE_CLIENTE");
		column.put("String:1", "COD_CLIENTE");
		column.put("String:2", "VALOR_NETO");
		column.put("String:3", "txtVendedor");
		column.put("String:4", "txtSincronizado");
		column.put("String:5", "DOC_TYPE");
		column.put("String:6", "SALES_ORG");
		column.put("String:7", "DISTR_CHAN");
		column.put("String:8", "DIVISION");
		column.put("String:9", "SALES_GRP");
		column.put("String:10", "SALES_OFF");
		column.put("String:11", "REQ_DATE_H");
		column.put("String:12", "PURCH_DATE");
		column.put("String:13", "PMNTTRMS");
		column.put("String:14", "DLV_BLOCK");
		column.put("String:15", "PRICE_DATE");
		column.put("String:16", "PURCH_NO_C");
		column.put("String:17", "SD_DOC_CAT");
		column.put("String:18", "DOC_DATE");
		column.put("String:19", "BILL_DATE");
		column.put("String:20", "SERV_DATE");
		column.put("String:21", "CURRENCY");
		column.put("String:22", "CREATED_BY");
		column.put("String:23", "SHIP_TYPE");
		column.put("String:24", "SHIP_COND");
		column.put("String:25", "id");
		column.put("String:26", "txtDisponible");
		column.put("String:27", "txtLimiteCredito");
		column.put("String:28", "txtClaseRiesgo");
		column.put("String:29", "SEC_PEDIDO");
		//column.put("String:29", "PURCH_NO_S");//Nelson 20130221 0324

		ResultExecuteQuery resultExecuteQuery = null;
		sql = "SELECT txtDisponible, txtLimiteCredito, txtClaseRiesgo, NOMBRE_CLIENTE, " 
				+ "COD_CLIENTE, VALOR_NETO, txtVendedor, txtSincronizado, DOC_TYPE,SALES_ORG,"
				+ "DISTR_CHAN,DIVISION,SALES_GRP,SALES_OFF,REQ_DATE_H,PURCH_DATE,"
				+ "PMNTTRMS,DLV_BLOCK,PRICE_DATE,PURCH_NO_C,SD_DOC_CAT,DOC_DATE,"
				+ "BILL_DATE,SERV_DATE,CURRENCY,CREATED_BY,SHIP_TYPE,SHIP_COND,id, SEC_PEDIDO " 
				+ "FROM PROFFLINE_TB_PEDIDO_HEADER "
				+ "WHERE txtSincronizado='0' ";
		if (idPedidoHeader != null) {
			sql = sql + " AND id=" + idPedidoHeader;
		}
		try {
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			for (HashMap res : mapResultado.values()) {
				BeanPedidoHeader h = new BeanPedidoHeader();

				h.setTxtClaseRiesgo(res.get("txtDisponible").toString());
				h.setTxtDisponible(res.get("txtLimiteCredito").toString());
				h.setTxtLimiteCredito(res.get("txtClaseRiesgo").toString());
				h.setStrCliente(res.get("NOMBRE_CLIENTE").toString());
				h.setStrCodCliente(res.get("COD_CLIENTE").toString());
				h.setStrValorNeto(res.get("VALOR_NETO").toString());
				h.setStrCodVendedor(res.get("txtVendedor").toString());
				h.setStrSincronizado(res.get("txtSincronizado").toString());
				h.setDOC_TYPE(res.get("DOC_TYPE").toString());
				h.setSALES_ORG(res.get("SALES_ORG").toString());
				h.setDISTR_CHAN(res.get("DISTR_CHAN").toString());
				h.setDIVISION(res.get("DIVISION").toString());
				h.setSALES_GRP(res.get("SALES_GRP").toString());
				h.setSALES_OFF(res.get("SALES_OFF").toString());
				h.setREQ_DATE_H(res.get("REQ_DATE_H").toString());
				h.setPURCH_DATE(res.get("PURCH_DATE").toString());
				h.setPMNTTRMS(res.get("PMNTTRMS").toString());
				h.setDLV_BLOCK(res.get("DLV_BLOCK").toString());
				h.setPRICE_DATE(res.get("PRICE_DATE").toString());
				h.setPURCH_NO_C(res.get("PURCH_NO_C").toString());
				h.setSD_DOC_CAT(res.get("SD_DOC_CAT").toString());
				h.setDOC_DATE(res.get("DOC_DATE").toString());
				h.setBILL_DATE(res.get("BILL_DATE").toString());
				h.setSERV_DATE(res.get("SERV_DATE").toString());
				h.setCURRENCY(res.get("CURRENCY").toString());
				h.setCREATED_BY(res.get("CREATED_BY").toString());
				h.setSHIP_TYPE(res.get("SHIP_TYPE").toString());
				h.setSHIP_COND(res.get("SHIP_COND").toString());
				h.setIdBD(res.get("id").toString());
				h.setSecuencialPedido(Integer.parseInt(res.get("SEC_PEDIDO").toString().trim()));
				//h.setPURCH_NO_S(res.get("PURCH_NO_S").toString());//nelson 20130221 0325
				mapH.put(h.getIdBD(), h);

				column = new HashMap();
				column.put("String:0", "id_header");
				column.put("String:1", "txtSincronizado");
				column.put("String:2", "txtKey");
				column.put("String:3", "txtValue");
				column.put("String:4", "id");

				sql = "SELECT id_header, txtSincronizado, txtKey, txtValue, id " 
						+ "FROM  PROFFLINE_TB_PEDIDO_PARTNERS "
						+ "WHERE id_header='" + h.getIdBD() + "'";
				try {
					resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
					mapResultado = resultExecuteQuery.getMap();
					map = new HashMap<String, String>();
					for (HashMap res2 : mapResultado.values()) {
						map.put(res2.get("txtKey").toString(), res2.get("txtValue").toString());
					}
					mapP.put(h.getIdBD(), map);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
				column = new HashMap();
				column.put("String:0", "id_header");
				column.put("String:1", "txtSincronizado");
				column.put("String:2", "txtPosicion");
				column.put("String:3", "txtMaterial");
				column.put("String:4", "txtCantidad");
				column.put("String:5", "txtCantidadConfirmada");
				column.put("String:6", "txtUM");
				column.put("String:7", "txtPorcentajeDescuento");
				column.put("String:8", "txtDenominacion");
				column.put("String:9", "txtPrecioNeto");
				column.put("String:10", "txtValorNeto");
				column.put("String:11", "id");
				column.put("String:12", "tipo");
				sql = "SELECT id_header, txtSincronizado, txtPosicion, txtMaterial, " 
						+ "txtCantidad, txtCantidadConfirmada, txtUM, "
						+ "txtPorcentajeDescuento, txtDenominacion, txtPrecioNeto, "
						+ "txtValorNeto,id, tipo FROM PROFFLINE_TB_PEDIDO_DETALLE "
						+ "WHERE id_header='" + h.getIdBD() + "'";
				try {
					resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
					mapResultado = resultExecuteQuery.getMap();
					listD = new ArrayList<BeanPedidoDetalle>();
					for (HashMap res3 : mapResultado.values()) {
						pd = new BeanPedidoDetalle();
						pd.setId_header(res3.get("id_header").toString());
						int tipo = 0;
						try {
							tipo = Integer.parseInt(res3.get("tipo").toString());
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
							tipo = 0;
						}
						pd.setTipo(tipo);
						pd.setTxtSincronizado(res3.get("txtSincronizado").toString());
						pd.setPosicion(res3.get("txtPosicion").toString());
						pd.setMaterial(res3.get("txtMaterial").toString());
						pd.setCantidad(res3.get("txtCantidad").toString());
						pd.setCantidadConfirmada(res3.get("txtCantidadConfirmada").toString());
						pd.setUM(res3.get("txtUM").toString());
						pd.setPorcentajeDescuento(res3.get("txtPorcentajeDescuento").toString());
						pd.setDenominacion(res3.get("txtDenominacion").toString());
						pd.setPrecioNeto(res3.get("txtPrecioNeto").toString());
						pd.setValorNeto(res3.get("txtValorNeto").toString());
						pd.setId(res3.get("id").toString());
						listD.add(pd);
					}
					mapD.put(h.getIdBD(), listD);

				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		bpps.setMapPedidoHeader(mapH);
		bpps.setMapPedidoPartners(mapP);
		bpps.setMapPedidoDetalle(mapD);
		return bpps;
	}

	public void setActualizaPedidoHeaderPartnersDetalle(String id) {
		final List<String> listaSQL = new ArrayList<String>();
		listaSQL.add("UPDATE PROFFLINE_TB_PEDIDO_HEADER "
				+ "SET txtSincronizado='1' " + "WHERE id='" + id.trim() + "' ");

		listaSQL.add("UPDATE PROFFLINE_TB_PEDIDO_PARTNERS "
				+ "SET txtSincronizado='1' " + "WHERE id_header='" + id.trim() + "'");

		listaSQL.add("UPDATE PROFFLINE_TB_PEDIDO_DETALLE "
				+ "SET txtSincronizado='1' " + "WHERE id_header='" + id.trim() + "'");

		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Pedidos", Constante.BD_TX);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BeanPedido obtenerPedido(String codigoPedido) {
		BeanPedido pedido = new BeanPedido();
		BeanPedidoHeader header = new BeanPedidoHeader();
		BeanPedidoPartners partners = new BeanPedidoPartners();
		List<BeanPedidoDetalle> detalles = new ArrayList<BeanPedidoDetalle>();
		column = new HashMap();
		column.put("String:0", "NOMBRE_CLIENTE");
		column.put("String:1", "COD_CLIENTE");
		column.put("String:2", "VALOR_NETO");
		column.put("String:3", "txtVendedor");
		column.put("String:4", "txtSincronizado");
		column.put("String:5", "SHIP_TYPE");
		column.put("String:6", "CREATED_BY");
		column.put("String:7", "CURRENCY");
		column.put("String:8", "SERV_DATE");
		column.put("String:9", "BILL_DATE");
		column.put("String:10", "DOC_DATE");
		column.put("String:11", "SD_DOC_CAT");
		column.put("String:12", "PURCH_NO_C");
		column.put("String:13", "PRICE_DATE");
		column.put("String:14", "DLV_BLOCK");
		column.put("String:15", "PMNTTRMS");
		column.put("String:16", "PURCH_DATE");
		column.put("String:17", "REQ_DATE_H");
		column.put("String:18", "SALES_OFF");
		column.put("String:19", "SALES_GRP");
		column.put("String:20", "DIVISION");
		column.put("String:21", "DISTR_CHAN");
		column.put("String:22", "SALES_ORG");
		column.put("String:23", "DOC_TYPE");
		column.put("String:24", "id");
		column.put("String:25", "SHIP_COND");
		column.put("String:26", "source");// ---- Requerimiento: PRO-2013-0025 ----
		column.put("String:27", "CodigoPedidoEnSap");// ---- Requerimiento: PRO-2013-0025 ----
		column.put("String:28", "SEC_PEDIDO");
		//column.put("String:26", "PURCH_NO_S");//Nelson 201302210320
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PEDIDO_HEADER WHERE id=" + codigoPedido;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
			// ----------- HEADER --------------
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				String id = res.get("id").toString();
				Integer temp = Integer.parseInt(id);
				String strFecha = "" + res.get("DOC_DATE").toString();
				Date fecha = null;
				try {
					fecha = formatoDelTexto.parse(strFecha);
				} catch (ParseException ex) {
					Util.mostrarExcepcion(ex);
					Mensaje.mostrarError("La fecha de creación no es correcta.");
					fecha = new Date();
				}
				SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd.MM.yyyy");
				strFecha = formatoDeFecha.format(fecha);
				String codigoVendedor = "";
				try {
					codigoVendedor = "" + Integer.parseInt(res.get("txtVendedor").toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					codigoVendedor = res.get("txtVendedor").toString();
				}
//				header.setSource(Constante.FROM_DB);
				header.setIdBD(id);
				header.setStrDocumentoVenta(temp.toString());
				header.setStrFechaDocumento(strFecha);
				header.setStrFechaCreacion(strFecha);
				header.setStrCreador(res.get("CREATED_BY").toString());
				header.setStrClDocVentas(("" + res.get("PURCH_NO_C")).toString());
				header.setStrCodVendedor(codigoVendedor);
				header.setStrCliente(res.get("NOMBRE_CLIENTE").toString());
				header.setStrCodCliente(res.get("COD_CLIENTE").toString());
				header.setStrValorNeto(("" + res.get("VALOR_NETO")).toString());
				header.setSHIP_TYPE(("" + res.get("SHIP_TYPE")).toString());
				header.setSHIP_COND(("" + res.get("SHIP_COND")).toString());
				header.setCREATED_BY(("" + res.get("CREATED_BY")).toString());
				header.setCURRENCY(("" + res.get("CURRENCY")).toString());
				header.setSERV_DATE(("" + res.get("SERV_DATE")).toString());
				header.setBILL_DATE(("" + res.get("BILL_DATE")).toString());
				header.setDOC_DATE(("" + res.get("DOC_DATE")).toString());
				header.setSD_DOC_CAT(("" + res.get("SD_DOC_CAT")).toString());
				header.setPURCH_NO_C(("" + res.get("PURCH_NO_C")).toString());
				header.setPRICE_DATE(strFecha);
				header.setDLV_BLOCK(("" + res.get("DLV_BLOCK")).toString());
				header.setPMNTTRMS(("" + res.get("PMNTTRMS")).toString());
				header.setStrCondicionPago(("" + res.get("PMNTTRMS")).toString());
				header.setPURCH_DATE(strFecha);
				header.setREQ_DATE_H(strFecha);
				header.setSALES_OFF(("" + res.get("SALES_OFF")).toString());
				header.setSALES_GRP(("" + res.get("SALES_GRP")).toString());
				header.setDIVISION(("" + res.get("DIVISION")).toString());
				header.setDISTR_CHAN(("" + res.get("DISTR_CHAN")).toString());
				header.setSALES_ORG(("" + res.get("SALES_ORG")).toString());
				header.setDOC_TYPE(("" + res.get("DOC_TYPE")).toString());
				header.setSHIP_COND(("" + res.get("SHIP_COND")).toString());
				//nelson 20130221 0322
				//header.setPURCH_NO_S(("" + res.get("PURCH_NO_S")).toString());
				header.setStrEstadoPicking("0");
				// Requerimiento: PRO-2013-0025
				header.setSource(Integer.parseInt(("" + res.get("source")).toString()));
				header.setCodigoPedido(("" + res.get("CodigoPedidoEnSap")).toString());
				// Requerimiento: PRO-2013-0025
				header.setSecuencialPedido(Integer.parseInt(res.get("SEC_PEDIDO").toString().trim()));
			}
			// --------------- PARTENERS --------------------
			column = new HashMap();
			column.put("String:0", "txtKey");
			column.put("String:1", "txtValue");
			HashMap<String, String> hm = new HashMap<String, String>();
			sql = "select * from proffline_tb_pedido_partners WHERE id_header=" + codigoPedido;
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				hm.put(("" + res.get("txtKey")).toString(), Util.completarCeros(10, "" + res.get("txtValue")).toString());
				partners.setHm(hm);
			}
			// -------------- DETALLES -----------------
			column = new HashMap();
			column.put("String:0", "txtPosicion");
			column.put("String:1", "txtMaterial");
			column.put("String:2", "txtCantidad");
			column.put("String:3", "txtCantidadConfirmada");
			column.put("String:4", "txtUM");
			column.put("String:5", "txtPorcentajeDescuento");
			column.put("String:6", "txtDenominacion");
			column.put("String:7", "txtPrecioNeto");
			column.put("String:8", "txtValorNeto");
			column.put("String:9", "tipo");
			sql = "select * from proffline_tb_pedido_detalle WHERE id_header=" + codigoPedido;
			
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				BeanPedidoDetalle detalle = new BeanPedidoDetalle();
				String material = "";
				try {
					material = "" + Long.parseLong(("" + res.get("txtMaterial")).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					material = ("" + res.get("txtPosicion")).toString();
				}
				int tipo = 0;
				try {
					tipo = Integer.parseInt(("" + res.get("tipo")).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					tipo = 0;
				}
				detalle.setPosicion(("" + res.get("txtPosicion")).toString());
				detalle.setMaterial(material);
				detalle.setCantidad(("" + res.get("txtCantidad")).toString());
				detalle.setCantidadConfirmada(("" + res.get("txtCantidadConfirmada")).toString());
				detalle.setUM(("" + res.get("txtUM")).toString());
				detalle.setPorcentajeDescuento(("" + res.get("txtPorcentajeDescuento")).toString());
				detalle.setDenominacion(("" + res.get("txtDenominacion")).toString());
				detalle.setPrecioNeto(("" + res.get("txtPrecioNeto")).toString());
				detalle.setValorNeto(("" + res.get("txtValorNeto")).toString());
				detalle.setTipo(tipo);
				detalles.add(detalle);
			}
			pedido.setHeader(header);
			pedido.setPartners(partners);
			pedido.setDetalles(detalles);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			pedido = null;
		}
		return pedido;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int ExistePedido(String codigoPedido) {
		int cantidad = 0;
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "CANTIDAD");
		String sqlExiste = "SELECT COUNT(*) AS CANTIDAD FROM PROFFLINE_TB_PEDIDO_HEADER " +
				"WHERE  txtSincronizado = 0 and CodigoPedidoEnSap = '"+ codigoPedido +"' LIMIT 1;";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlExiste, column, Constante.BD_TX);
			if(resultExecuteQuery != null){
				mapResultado = resultExecuteQuery.getMap();
				if(mapResultado.size() > 0){
					HashMap res = (HashMap) mapResultado.get(0);
					cantidad = Integer.parseInt(res.get("CANTIDAD").toString());
				}
			}
		}catch (Exception ex){
			Util.mostrarExcepcion(ex);
			cantidad = 0;
		}
		return cantidad;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerId(String codigoPedido){
		String cantidad = "0";
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "id");
		String sqlExiste = "SELECT id FROM PROFFLINE_TB_PEDIDO_HEADER " +
				"WHERE  txtSincronizado = 0 and CodigoPedidoEnSap= '"+ codigoPedido +"' LIMIT 1;";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlExiste, column, Constante.BD_TX);
			if(resultExecuteQuery != null){
				mapResultado = resultExecuteQuery.getMap();
				if(mapResultado.size() > 0){
					HashMap res = (HashMap) mapResultado.get(0);
					cantidad = res.get("id").toString();
				}
			}
		}catch (Exception ex){
			Util.mostrarExcepcion(ex);
			cantidad = "0";
		}
		return cantidad;
	}
	
	// -------------------- INICIO PROYECTO 14 DE AGOSTO 2013 ------------------------------ //
	// -------------------- MARCELO MOYANO PROYECTO PROFORMA ------------------------------- //
	// ----------------- INGRASAR PARAMETRO DE BLOQUEO DE PEDIDO --------------------------- //
	// ---------------- 1 PEDIDO ACTIVADO --- 0 PEDIDO BLOQUEADO -------------------------- // 
	public void insertarBloquePedido(List<BeanParametro> listaParametro) throws Exception{
		List<String> listaSqlParametro = new ArrayList<String>();
		for (BeanParametro bp : listaParametro) {
			String sqlBloqueoPedido = "INSERT INTO PROFFLINE_TB_ZTCONSTANTE" +
			"(TXTMODULO, TXTNOMBREPROGRAMA, TXTNOMBRECAMPO, SECUENCIA, TXTOPCION, VALOR_1, VALOR_2, TXTNAME, TXTFECHA) " +
			"VALUES ('" + bp.getStrModulo() + "','" + bp.getStrNombrePrograma() + "','" + bp.getStrNombreCampo() + 
			"','" + bp.getSecuencia() 	+ "','" + bp.getStrOpcion() 		+ "','" + bp.getStrValorUno() + 
			"','" + bp.getStrValorDos() + "','" + bp.getStrNombreUsuario() + "','" + bp.getStrFecha() + "');";
			
			listaSqlParametro.add(sqlBloqueoPedido);
		}
		ResultExecuteList resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaSqlParametro, "ZT CONSTANTE", Constante.BD_SYNC);
	}
	// ------------------------------------------------------------------------------------------- //
	
	// ------------------------ ACTUALIZA PARAMETRO BLOQUE DE PEDIDO ----------------------------- //
	public void actualizarBloquePedido(BeanParametro bp) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sqlBloqueoPedido = "UPDATE PROFFLINE_TB_ZTCONSTANTE SET VALOR_1 = '" 
				+ bp.getStrValorUno() + "' WHERE TXTNOMBRECAMPO = '" + bp.getStrNombreCampo() +"';";
		stmt = connection.createStatement();
		stmt.execute(sqlBloqueoPedido);
		if (stmt != null)
			stmt.close();
		if (connection != null)
			connection.close();
		if (conn != null)
			conn.close();
	}
	// -------------------------------------------------------------------------------------------- //
	
	// ------------ OBTIENE EL PARAMETRO DE ACTIVACIÓN O BLOQUEA DE PEDIDO ------------------------ //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String obtenerBloqueoPedido(String strNombreCampo) {
		String strResultado = "";
		column = new HashMap();
		column.put("String:0", "VALOR_1");
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlBloqueoPedido = "SELECT * FROM PROFFLINE_TB_ZTCONSTANTE WHERE TXTNOMBRECAMPO = '"+ strNombreCampo + "';";
		resultExecuteQuery = new ResultExecuteQuery(sqlBloqueoPedido, column, Constante.BD_SYNC);
		if (sqlBloqueoPedido != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
				HashMap res = (HashMap) mapResultado.get(0);
				strResultado = res.get("VALOR_1").toString();
			}
		}
		return strResultado;
	}
	// -------------------------------------------------------------------------------------------- //
	
	// ---------------------- ELIMINA EL REGISTRO STRNOMBRECAMPO DE TABLA ------------------------- //
	public void eliminarTablaBloquePedido(String strNombreCampo) throws Exception{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sqlBloqueoPedido = "DELETE FROM PROFFLINE_TB_ZTCONSTANTE WHERE TXTNOMBRECAMPO = '" + strNombreCampo + "';";
		stmt = connection.createStatement();
		stmt.execute(sqlBloqueoPedido);
		if (stmt != null)
			stmt.close();
		if (connection != null)
			connection.close();
		if (conn != null)
			conn.close();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BeanPedido obtenerPedidoSAPLocal(String codigoPedido) {
		BeanPedido pedido = new BeanPedido();
		BeanPedidoHeader header = new BeanPedidoHeader();
		BeanPedidoPartners partners = new BeanPedidoPartners();
		List<BeanPedidoDetalle> detalles = new ArrayList<BeanPedidoDetalle>();
		column = new HashMap();
		column.put("String:0", "NOMBRE_CLIENTE");
		column.put("String:1", "COD_CLIENTE");
		column.put("String:2", "VALOR_NETO");
		column.put("String:3", "txtVendedor");
		column.put("String:4", "txtSincronizado");
		column.put("String:5", "SHIP_TYPE");
		column.put("String:6", "CREATED_BY");
		column.put("String:7", "CURRENCY");
		column.put("String:8", "SERV_DATE");
		column.put("String:9", "BILL_DATE");
		column.put("String:10", "DOC_DATE");
		column.put("String:11", "SD_DOC_CAT");
		column.put("String:12", "PURCH_NO_C");
		column.put("String:13", "PRICE_DATE");
		column.put("String:14", "DLV_BLOCK");
		column.put("String:15", "PMNTTRMS");
		column.put("String:16", "PURCH_DATE");
		column.put("String:17", "REQ_DATE_H");
		column.put("String:18", "SALES_OFF");
		column.put("String:19", "SALES_GRP");
		column.put("String:20", "DIVISION");
		column.put("String:21", "DISTR_CHAN");
		column.put("String:22", "SALES_ORG");
		column.put("String:23", "DOC_TYPE");
		column.put("String:24", "id");
		column.put("String:25", "SHIP_COND");
		column.put("String:26", "source");// ---- Requerimiento: PRO-2013-0025 ----
		column.put("String:27", "CodigoPedidoEnSap");// ---- Requerimiento: PRO-2013-0025 ----
		//column.put("String:26", "PURCH_NO_S");//Nelson 201302210320
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PEDIDO_HEADER WHERE CodigoPedidoEnSap=" + codigoPedido;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
			// ----------- HEADER --------------
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				String id = res.get("id").toString();
				Integer temp = Integer.parseInt(id);
				String strFecha = "" + res.get("DOC_DATE").toString();
				Date fecha = null;
				try {
					fecha = formatoDelTexto.parse(strFecha);
				} catch (ParseException ex) {
					Util.mostrarExcepcion(ex);
					Mensaje.mostrarError("La fecha de creación no es correcta.");
					fecha = new Date();
				}
				SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd.MM.yyyy");
				strFecha = formatoDeFecha.format(fecha);
				String codigoVendedor = "";
				try {
					codigoVendedor = "" + Integer.parseInt(res.get("txtVendedor").toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					codigoVendedor = res.get("txtVendedor").toString();
				}
//				header.setSource(Constante.FROM_DB);
				header.setIdBD(id);
				header.setStrDocumentoVenta(temp.toString());
				header.setStrFechaDocumento(strFecha);
				header.setStrFechaCreacion(strFecha);
				header.setStrCreador(res.get("CREATED_BY").toString());
				header.setStrClDocVentas(("" + res.get("PURCH_NO_C")).toString());
				header.setStrCodVendedor(codigoVendedor);
				header.setStrCliente(res.get("NOMBRE_CLIENTE").toString());
				header.setStrCodCliente(res.get("COD_CLIENTE").toString());
				header.setStrValorNeto(("" + res.get("VALOR_NETO")).toString());
				header.setSHIP_TYPE(("" + res.get("SHIP_TYPE")).toString());
				header.setSHIP_COND(("" + res.get("SHIP_COND")).toString());
				header.setCREATED_BY(("" + res.get("CREATED_BY")).toString());
				header.setCURRENCY(("" + res.get("CURRENCY")).toString());
				header.setSERV_DATE(("" + res.get("SERV_DATE")).toString());
				header.setBILL_DATE(("" + res.get("BILL_DATE")).toString());
				header.setDOC_DATE(("" + res.get("DOC_DATE")).toString());
				header.setSD_DOC_CAT(("" + res.get("SD_DOC_CAT")).toString());
				header.setPURCH_NO_C(("" + res.get("PURCH_NO_C")).toString());
				header.setPRICE_DATE(strFecha);
				header.setDLV_BLOCK(("" + res.get("DLV_BLOCK")).toString());
				header.setPMNTTRMS(("" + res.get("PMNTTRMS")).toString());
				header.setStrCondicionPago(("" + res.get("PMNTTRMS")).toString());
				header.setPURCH_DATE(strFecha);
				header.setREQ_DATE_H(strFecha);
				header.setSALES_OFF(("" + res.get("SALES_OFF")).toString());
				header.setSALES_GRP(("" + res.get("SALES_GRP")).toString());
				header.setDIVISION(("" + res.get("DIVISION")).toString());
				header.setDISTR_CHAN(("" + res.get("DISTR_CHAN")).toString());
				header.setSALES_ORG(("" + res.get("SALES_ORG")).toString());
				header.setDOC_TYPE(("" + res.get("DOC_TYPE")).toString());
				header.setSHIP_COND(("" + res.get("SHIP_COND")).toString());
				//nelson 20130221 0322
				//header.setPURCH_NO_S(("" + res.get("PURCH_NO_S")).toString());
				header.setStrEstadoPicking("0");
				// Requerimiento: PRO-2013-0025
				header.setSource(Integer.parseInt(("" + res.get("source")).toString()));
				header.setCodigoPedido(("" + res.get("CodigoPedidoEnSap")).toString());
				// Requerimiento: PRO-2013-0025
			}
			// --------------- PARTENERS --------------------
			column = new HashMap();
			column.put("String:0", "txtKey");
			column.put("String:1", "txtValue");
			HashMap<String, String> hm = new HashMap<String, String>();
			sql = "select * from proffline_tb_pedido_partners WHERE id_header=" + header.getIdBD();
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				hm.put(("" + res.get("txtKey")).toString(), Util.completarCeros(10, "" + res.get("txtValue")).toString());
				partners.setHm(hm);
			}
			// -------------- DETALLES -----------------
			column = new HashMap();
			column.put("String:0", "txtPosicion");
			column.put("String:1", "txtMaterial");
			column.put("String:2", "txtCantidad");
			column.put("String:3", "txtCantidadConfirmada");
			column.put("String:4", "txtUM");
			column.put("String:5", "txtPorcentajeDescuento");
			column.put("String:6", "txtDenominacion");
			column.put("String:7", "txtPrecioNeto");
			column.put("String:8", "txtValorNeto");
			column.put("String:9", "tipo");
			sql = "select * from proffline_tb_pedido_detalle WHERE id_header=" + header.getIdBD();
			
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
			mapResultado = resultExecuteQuery.getMap();
			res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				BeanPedidoDetalle detalle = new BeanPedidoDetalle();
				String material = "";
				try {
					material = "" + Long.parseLong(("" + res.get("txtMaterial")).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					material = ("" + res.get("txtPosicion")).toString();
				}
				int tipo = 0;
				try {
					tipo = Integer.parseInt(("" + res.get("tipo")).toString());
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					tipo = 0;
				}
				detalle.setPosicion(("" + res.get("txtPosicion")).toString());
				detalle.setMaterial(material);
				detalle.setCantidad(("" + res.get("txtCantidad")).toString());
				detalle.setCantidadConfirmada(("" + res.get("txtCantidadConfirmada")).toString());
				detalle.setUM(("" + res.get("txtUM")).toString());
				detalle.setPorcentajeDescuento(("" + res.get("txtPorcentajeDescuento")).toString());
				detalle.setDenominacion(("" + res.get("txtDenominacion")).toString());
				detalle.setPrecioNeto(("" + res.get("txtPrecioNeto")).toString());
				detalle.setValorNeto(("" + res.get("txtValorNeto")).toString());
				detalle.setTipo(tipo);
				detalles.add(detalle);
			}
			pedido.setHeader(header);
			pedido.setPartners(partners);
			pedido.setDetalles(detalles);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			pedido = null;
		}
		return pedido;
	}
	// -------------------------------------------------------------------------------------------------- //
	
//	public static void main(String[] args) {
//		SqlPedidoImpl sql = new SqlPedidoImpl();
//		BeanParametro bpPrueba = new BeanParametro();
//		try {
//			sql.eliminarTablaBloquePedido("");
//			sql.insertarBloquePedido(bpPrueba);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String resultado = sql.obtenerBloqueoPedido("");
//	}
	
}