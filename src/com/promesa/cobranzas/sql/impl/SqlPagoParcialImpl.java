package com.promesa.cobranzas.sql.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PartidaIndividualAbierta;
import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlPagoParcialImpl {

	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;

	public void insertarListaPagoParcial(List<PagoParcial> lstPagoParcial) {
		List<String> listaPagoParcial = new ArrayList<String>();
		for (PagoParcial pagoParcial : lstPagoParcial) {
			String sql = "INSERT INTO PROFFLINE_TB_PAGO_PARCIAL (idCabecera, idPadre, numeroDocumento, pstngDate, docDate,"
					+ "entryDate, vencimiento, currency, importePagoTotal, refOrgUn, referencia, claseDocumento, posicion,"
					+ "postKey, importePagoParcial, importePago, psskt, invRef, invItem, isLeaf, isExpanded, isReadOnly,"
					+ "indice, displayColor, fiscYear, fisPeriod, sgtxt, isReadOnly2, dbCrInd, verzn, comentario, activo, hijo) VALUES ("
					+ pagoParcial.getIdCabecera() 				+ "," + pagoParcial.getIdPadre() + ",'" + pagoParcial.getNumeroDocumento()
					+ "','" + pagoParcial.getPstngDate() 		+ "','" + pagoParcial.getDocDate()
					+ "','" + pagoParcial.getEntryDate() 		+ "','" + pagoParcial.getVencimiento()
					+ "','" + pagoParcial.getCurrency() 		+ "','" + pagoParcial.getImportePagoTotal()
					+ "','" + pagoParcial.getRefOrgUn() 		+ "','" + pagoParcial.getReferencia()
					+ "','" + pagoParcial.getClaseDocumento() 	+ "','" + pagoParcial.getPosicion()
					+ "','" + pagoParcial.getPostKey() 			+ "','" + pagoParcial.getImportePagoParcial()
					+ "','" + pagoParcial.getImportePago() 		+ "','" + pagoParcial.getPsskt()
					+ "','" + pagoParcial.getInvRef() 			+ "','" + pagoParcial.getInvItem()
					+ "','" + pagoParcial.getIsLeaf() 			+ "','" + pagoParcial.getIsExpanded()
					+ "','" + pagoParcial.getIsReadOnly() 		+ "','" + pagoParcial.getIndice()
					+ "','" + pagoParcial.getDisplayColor() 	+ "','" + pagoParcial.getFiscYear()
					+ "','" + pagoParcial.getFisPeriod() 		+ "','" + pagoParcial.getSgtxt()
					+ "','" + pagoParcial.getIsReadOnly2() 		+ "','" + pagoParcial.getDbCrInd()
					+ "','" + pagoParcial.getVerzn() 			+ "','" + pagoParcial.getComentario()
					+ "','" + pagoParcial.getActivo() 			+ "','" + pagoParcial.getHijo() + "');";
			listaPagoParcial.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaPagoParcial, Constante.BD_TX); 
	}

	public boolean eliminarListaPagoParcial(long idCabecera) {
		String sql = "DELETE FROM PROFFLINE_TB_PAGO_PARCIAL " + "WHERE idCabecera = " + idCabecera + ";";
		resultExecute = new ResultExecute(sql, Constante.BD_TX);
		return resultExecute.isFlag();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PagoParcial> obtenerListaPagoParcial(long idCabecera) {
		List<PagoParcial> listaPagoParcial = new ArrayList<PagoParcial>();
		column = new HashMap();
		column.put("String:0", "idPagoParcial");
		column.put("String:1", "idCabecera");
		column.put("String:2", "idPadre");
		column.put("String:3", "numeroDocumento");
		column.put("String:4", "pstngDate");
		column.put("String:5", "docDate");
		column.put("String:6", "entryDate");
		column.put("String:7", "vencimiento");
		column.put("String:8", "currency");
		column.put("String:9", "importePagoTotal");
		column.put("String:10", "refOrgUn");
		column.put("String:11", "referencia");
		column.put("String:12", "claseDocumento");
		column.put("String:13", "posicion");
		column.put("String:14", "postKey");
		column.put("String:15", "importePagoParcial");
		column.put("String:16", "importePago");
		column.put("String:17", "psskt");
		column.put("String:18", "invRef");
		column.put("String:19", "invItem");
		column.put("String:20", "isLeaf");
		column.put("String:21", "isExpanded");
		column.put("String:22", "isReadOnly");
		column.put("String:23", "indice");
		column.put("String:24", "displayColor");
		column.put("String:25", "fiscYear");
		column.put("String:26", "fisPeriod");
		column.put("String:27", "sgtxt");
		column.put("String:28", "isReadOnly2");
		column.put("String:29", "dbCrInd");
		column.put("String:30", "verzn");
		column.put("String:31", "comentario");
		column.put("String:32", "activo");
		column.put("String:33", "hijo");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PAGO_PARCIAL " + "WHERE idCabecera = " + idCabecera + ";";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
				for (int i = 0; i < mapResultado.size(); i++) {
					HashMap res = (HashMap) mapResultado.get(i);
					PagoParcial pagoParcial = new PagoParcial();
					pagoParcial.setIdPagoParcial(Integer.parseInt(res.get("idPagoParcial").toString()));
					pagoParcial.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					pagoParcial.setIdPadre(Integer.parseInt(res.get("idPadre").toString()));
					pagoParcial.setNumeroDocumento(res.get("numeroDocumento").toString());
					pagoParcial.setPstngDate(res.get("pstngDate").toString());
					pagoParcial.setDocDate(res.get("docDate").toString());
					pagoParcial.setEntryDate(res.get("entryDate").toString());
					pagoParcial.setVencimiento(res.get("vencimiento").toString());
					pagoParcial.setCurrency(res.get("currency").toString());
					pagoParcial.setImportePagoTotal(Double.parseDouble(res.get("importePagoTotal").toString()));
					pagoParcial.setRefOrgUn(res.get("refOrgUn").toString());
					pagoParcial.setReferencia(res.get("referencia").toString());
					pagoParcial.setClaseDocumento(res.get("claseDocumento").toString());
					pagoParcial.setPosicion(res.get("posicion").toString());
					pagoParcial.setPostKey(res.get("postKey").toString());
					pagoParcial.setImportePagoParcial(Double.parseDouble(res.get("importePagoParcial").toString()));
					pagoParcial.setImportePago(Double.parseDouble(res.get("importePago").toString()));
					pagoParcial.setPsskt(res.get("psskt").toString());
					pagoParcial.setInvRef(res.get("invRef").toString());
					pagoParcial.setInvItem(res.get("invItem").toString());
					pagoParcial.setIsLeaf(res.get("isLeaf").toString());
					pagoParcial.setIsExpanded(res.get("isExpanded").toString());
					pagoParcial.setIsReadOnly(res.get("isReadOnly").toString());
					pagoParcial.setIndice(res.get("indice").toString());
					pagoParcial.setDisplayColor(res.get("displayColor").toString());
					pagoParcial.setFiscYear(res.get("fiscYear").toString());
					pagoParcial.setFisPeriod(res.get("fisPeriod").toString());
					pagoParcial.setSgtxt(res.get("sgtxt").toString());
					pagoParcial.setIsReadOnly2(res.get("isReadOnly2").toString());
					pagoParcial.setDbCrInd(res.get("dbCrInd").toString());
					pagoParcial.setVerzn(res.get("verzn").toString());
					pagoParcial.setComentario(res.get("comentario").toString());
					pagoParcial.setActivo(res.get("activo").toString());
					pagoParcial.setHijo(res.get("hijo").toString());
					listaPagoParcial.add(pagoParcial);
				}
			}
		}
		return listaPagoParcial;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PagoParcial> obtenerListaDetallePagoParcial(long idPagoParcial) {
		List<PagoParcial> listaPagoParcial = new ArrayList<PagoParcial>();
		column = new HashMap();
		column.put("String:0", "idDetallePagoPartidaIndividualAbierta");
		column.put("String:1", "idPartidaIndividualAbierta");
		column.put("String:2", "docNo");
		column.put("String:3", "pstngDate");
		column.put("String:4", "docDate");
		column.put("String:5", "entryDate");
		column.put("String:6", "expirationDate");
		column.put("String:7", "currency");
		column.put("String:8", "amtDoccur");
		column.put("String:9", "refOrgUn");
		column.put("String:10", "refDocNo");
		column.put("String:11", "docType");
		column.put("String:12", "itemNum");
		column.put("String:13", "postKey");
		column.put("String:14", "psprt");
		column.put("String:15", "pszah");
		column.put("String:16", "psskt");
		column.put("String:17", "invRef");
		column.put("String:18", "invItem");
		column.put("String:19", "isLeaf");
		column.put("String:20", "isExpanded");
		column.put("String:21", "isReadOnly");
		column.put("String:22", "indice");
		column.put("String:23", "displayColor");
		column.put("String:24", "fiscYear");
		column.put("String:25", "fisPeriod");
		column.put("String:26", "sgtxt");
		column.put("String:27", "isReadOnly2");
		column.put("String:28", "dbCrInd");
		column.put("String:29", "verzn");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA " + "WHERE idPartidaIndividualAbierta = " + idPagoParcial + ";";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
				for (int i = 0; i < mapResultado.size(); i++) {
					HashMap res = (HashMap) mapResultado.get(i);
					PagoParcial pagoParcial = new PagoParcial();
					pagoParcial.setIdPagoParcial(i + 1);
					pagoParcial.setIdPadre(idPagoParcial);
					pagoParcial.setNumeroDocumento(res.get("docNo").toString());
					pagoParcial.setPstngDate(res.get("pstngDate").toString());
					pagoParcial.setDocDate(res.get("docDate").toString());
					pagoParcial.setEntryDate(res.get("entryDate").toString());
					pagoParcial.setVencimiento(res.get("expirationDate").toString());
					pagoParcial.setCurrency(res.get("currency").toString());
					Double importePagoTotal = 0d;
					try {
						importePagoTotal = Double.parseDouble(res.get("amtDoccur").toString());
					} catch (Exception e) {
						importePagoTotal = 0d;
					}
					pagoParcial.setImportePagoTotal(importePagoTotal);
					pagoParcial.setRefOrgUn(res.get("refOrgUn").toString());
					pagoParcial.setReferencia(res.get("refDocNo").toString());
					pagoParcial.setClaseDocumento(res.get("docType").toString());
					pagoParcial.setPosicion(res.get("itemNum").toString());
					pagoParcial.setPostKey(res.get("postKey").toString());
					Double importePagoParcial = 0d;
					try {
						importePagoParcial = Double.parseDouble(res.get("psprt").toString());
					} catch (Exception e) {
						importePagoParcial = 0d;
					}
					pagoParcial.setImportePagoParcial(importePagoParcial);
					Double importePago = 0d;
					try {
						importePago = Double.parseDouble(res.get("pszah").toString());
					} catch (Exception e) {
						importePagoParcial = 0d;
					}
					pagoParcial.setImportePago(importePago);
					pagoParcial.setPsskt(res.get("psskt").toString());
					pagoParcial.setInvRef(res.get("invRef").toString());
					pagoParcial.setInvItem(res.get("invItem").toString());
					pagoParcial.setIsLeaf(res.get("isLeaf").toString());
					pagoParcial.setIsExpanded(res.get("isExpanded").toString());
					pagoParcial.setIsReadOnly(res.get("isReadOnly").toString());
					pagoParcial.setIndice(res.get("indice").toString());
					pagoParcial.setDisplayColor(res.get("displayColor").toString());
					pagoParcial.setFiscYear(res.get("fiscYear").toString());
					pagoParcial.setFisPeriod(res.get("fisPeriod").toString());
					pagoParcial.setSgtxt(res.get("sgtxt").toString());
					pagoParcial.setIsReadOnly2(res.get("isReadOnly2").toString());
					pagoParcial.setDbCrInd(res.get("dbCrInd").toString());
					pagoParcial.setVerzn(res.get("verzn").toString());
					pagoParcial.setHijo("1");
					pagoParcial.setComentario("");
					listaPagoParcial.add(pagoParcial);
				}
			}
		}
		return listaPagoParcial;
	}
	
	// Marcelo Moyano 18/04/2013
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PagoParcial> obtenerListaPagosParcialLocal(PagoParcial pp) {
		List<PagoParcial> listaPagoParcial = new ArrayList<PagoParcial>();
		column = new HashMap();
		column.put("String:0", "idPagoParcial");
		column.put("String:1", "idCabecera");
		column.put("String:2", "idPadre");
		column.put("String:3", "numeroDocumento");
		column.put("String:4", "pstngDate");
		column.put("String:5", "docDate");
		column.put("String:6", "entryDate");
		column.put("String:7", "vencimiento");
		column.put("String:8", "currency");
		column.put("String:9", "importePagoTotal");
		column.put("String:10", "refOrgUn");
		column.put("String:11", "referencia");
		column.put("String:12", "claseDocumento");
		column.put("String:13", "posicion");
		column.put("String:14", "postKey");
		column.put("String:15", "importePagoParcial");
		column.put("String:16", "importePago");
		column.put("String:17", "psskt");
		column.put("String:18", "invRef");
		column.put("String:19", "invItem");
		column.put("String:20", "isLeaf");
		column.put("String:21", "isExpanded");
		column.put("String:22", "isReadOnly");
		column.put("String:23", "indice");
		column.put("String:24", "displayColor");
		column.put("String:25", "fiscYear");
		column.put("String:26", "fisPeriod");
		column.put("String:27", "sgtxt");
		column.put("String:28", "isReadOnly2");
		column.put("String:29", "dbCrInd");
		column.put("String:30", "verzn");
		column.put("String:31", "comentario");
		column.put("String:32", "activo");
		column.put("String:33", "hijo");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PAGO_PARCIAL " + "WHERE ACTIVO = '1'"
				+ "  AND NUMERODOCUMENTO = '" + pp.getNumeroDocumento()
				+ "' AND REFERENCIA = '"      + pp.getReferencia()
				+ "' AND POSICION = '"        + pp.getPosicion()
				+ "' AND VENCIMIENTO = '"     + pp.getVencimiento() + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
				for (int i = 0; i < mapResultado.size(); i++) {
					HashMap res = (HashMap) mapResultado.get(i);
					PagoParcial pagoParcial = new PagoParcial();
					pagoParcial.setIdPagoParcial(Integer.parseInt(res.get("idPagoParcial").toString()));
					pagoParcial.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					pagoParcial.setIdPadre(pp.getIdPagoParcial());
					pagoParcial.setNumeroDocumento(res.get("numeroDocumento").toString());
					pagoParcial.setPstngDate(res.get("pstngDate").toString());
					pagoParcial.setDocDate(res.get("docDate").toString());
					pagoParcial.setEntryDate(res.get("entryDate").toString());
					pagoParcial.setVencimiento(res.get("vencimiento").toString());
					pagoParcial.setCurrency(res.get("currency").toString());
					pagoParcial.setImportePagoTotal(Double.parseDouble(res.get("importePagoTotal").toString()));
					pagoParcial.setRefOrgUn(res.get("refOrgUn").toString());
					pagoParcial.setReferencia(res.get("referencia").toString());
					pagoParcial.setClaseDocumento(res.get("claseDocumento").toString());
					pagoParcial.setPosicion(res.get("posicion").toString());
					pagoParcial.setPostKey(res.get("postKey").toString());
					pagoParcial.setImportePagoParcial(Double.parseDouble(res.get("importePagoParcial").toString()));
					pagoParcial.setImportePago(Double.parseDouble(res.get("importePago").toString()));
					pagoParcial.setPsskt(res.get("psskt").toString());
					pagoParcial.setInvRef(res.get("invRef").toString());
					pagoParcial.setInvItem(res.get("invItem").toString());
					pagoParcial.setIsLeaf(res.get("isLeaf").toString());
					pagoParcial.setIsExpanded(res.get("isExpanded").toString());
					pagoParcial.setIsReadOnly(res.get("isReadOnly").toString());
					pagoParcial.setIndice(res.get("indice").toString());
					pagoParcial.setDisplayColor(res.get("displayColor").toString());
					pagoParcial.setFiscYear(res.get("fiscYear").toString());
					pagoParcial.setFisPeriod(res.get("fisPeriod").toString());
					pagoParcial.setSgtxt(res.get("sgtxt").toString());
					pagoParcial.setIsReadOnly2(res.get("isReadOnly2").toString());
					pagoParcial.setDbCrInd(res.get("dbCrInd").toString());
					pagoParcial.setVerzn(res.get("verzn").toString());
					pagoParcial.setComentario(res.get("comentario").toString());
					pagoParcial.setActivo(res.get("activo").toString());
					pagoParcial.setHijo("1");
					pagoParcial.setPago_hecho_offline(true);
					listaPagoParcial.add(pagoParcial);
				}
			}
		}
		return listaPagoParcial;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PagoParcial obtenerListaPagoParcialActivo(long idCabecera){
		PagoParcial pagoParcial = new PagoParcial();
		column = new HashMap();
		column.put("String:0", "idPagoParcial");
		column.put("String:1", "idCabecera");
		column.put("String:2", "idPadre");
		column.put("String:3", "numeroDocumento");
		column.put("String:4", "pstngDate");
		column.put("String:5", "docDate");
		column.put("String:6", "entryDate");
		column.put("String:7", "vencimiento");
		column.put("String:8", "currency");
		column.put("String:9", "importePagoTotal");
		column.put("String:10", "refOrgUn");
		column.put("String:11", "referencia");
		column.put("String:12", "claseDocumento");
		column.put("String:13", "posicion");
		column.put("String:14", "postKey");
		column.put("String:15", "importePagoParcial");
		column.put("String:16", "importePago");
		column.put("String:17", "psskt");
		column.put("String:18", "invRef");
		column.put("String:19", "invItem");
		column.put("String:20", "isLeaf");
		column.put("String:21", "isExpanded");
		column.put("String:22", "isReadOnly");
		column.put("String:23", "indice");
		column.put("String:24", "displayColor");
		column.put("String:25", "fiscYear");
		column.put("String:26", "fisPeriod");
		column.put("String:27", "sgtxt");
		column.put("String:28", "isReadOnly2");
		column.put("String:29", "dbCrInd");
		column.put("String:30", "verzn");
		column.put("String:31", "comentario");
		column.put("String:32", "activo");
		column.put("String:33", "hijo");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PAGO_PARCIAL " + "WHERE activo = '1' and idCabecera = " + idCabecera + ";";
		
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
					HashMap res = (HashMap) mapResultado.get(0);
					pagoParcial.setIdPagoParcial(Integer.parseInt(res.get("idPagoParcial").toString()));
					pagoParcial.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					pagoParcial.setIdPadre(Integer.parseInt(res.get("idPadre").toString()));
					pagoParcial.setNumeroDocumento(res.get("numeroDocumento").toString());
					pagoParcial.setPstngDate(res.get("pstngDate").toString());
					pagoParcial.setDocDate(res.get("docDate").toString());
					pagoParcial.setEntryDate(res.get("entryDate").toString());
					pagoParcial.setVencimiento(res.get("vencimiento").toString());
					pagoParcial.setCurrency(res.get("currency").toString());
					pagoParcial.setImportePagoTotal(Double.parseDouble(res.get("importePagoTotal").toString()));
					pagoParcial.setRefOrgUn(res.get("refOrgUn").toString());
					pagoParcial.setReferencia(res.get("referencia").toString());
					pagoParcial.setClaseDocumento(res.get("claseDocumento").toString());
					pagoParcial.setPosicion(res.get("posicion").toString());
					pagoParcial.setPostKey(res.get("postKey").toString());
					pagoParcial.setImportePagoParcial(Double.parseDouble(res.get("importePagoParcial").toString()));
					pagoParcial.setImportePago(Double.parseDouble(res.get("importePago").toString()));
					pagoParcial.setPsskt(res.get("psskt").toString());
					pagoParcial.setInvRef(res.get("invRef").toString());
					pagoParcial.setInvItem(res.get("invItem").toString());
					pagoParcial.setIsLeaf(res.get("isLeaf").toString());
					pagoParcial.setIsExpanded(res.get("isExpanded").toString());
					pagoParcial.setIsReadOnly(res.get("isReadOnly").toString());
					pagoParcial.setIndice(res.get("indice").toString());
					pagoParcial.setDisplayColor(res.get("displayColor").toString());
					pagoParcial.setFiscYear(res.get("fiscYear").toString());
					pagoParcial.setFisPeriod(res.get("fisPeriod").toString());
					pagoParcial.setSgtxt(res.get("sgtxt").toString());
					pagoParcial.setIsReadOnly2(res.get("isReadOnly2").toString());
					pagoParcial.setDbCrInd(res.get("dbCrInd").toString());
					pagoParcial.setVerzn(res.get("verzn").toString());
					pagoParcial.setComentario(res.get("comentario").toString());
					pagoParcial.setActivo(res.get("activo").toString());
					pagoParcial.setHijo(res.get("hijo").toString());
			}
		}
		return pagoParcial;
	}
	
	public void ActualizarImportePagoParcial(PagoParcial pp, Double importePagoParcial)
		throws SQLException{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "UPDATE PROFFLINE_TB_PAGO_PARCIAL SET " + "importePagoParcial = '" + importePagoParcial
				+ "' WHERE NUMERODOCUMENTO = '"  + pp.getNumeroDocumento()
				+ "' and POSICION = '"  + pp.getPosicion() + "' and REFERENCIA = '" + pp.getReferencia() 
				+ "' and VENCIMIENTO = '"  +pp.getVencimiento()+ "';";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PagoParcial> ObtenerImportePago(PartidaIndividualAbierta pia)
		throws SQLException{
		List<PagoParcial> listaPagoParcial = new ArrayList<PagoParcial>();
		column = new HashMap();
		column.put("String:1","ImportePago");
		column.put("String:2","IdCabecera");
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlpia = "select importePago, IdCabecera from PROFFLINE_TB_PAGO_PARCIAL "
						+ "where numerodocumento = '" + pia.getDocNo() + "' and posicion = '" + pia.getItemNum()
						+ "' and referencia = '" + pia.getRefDocNo() + "' and vencimiento = '" + pia.getExpirationDate()
						+ "' and activo = '1';";
		resultExecuteQuery = new ResultExecuteQuery(sqlpia, column, Constante.BD_TX);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for (int i = 0; i < mapResultado.size(); i++){
					PagoParcial pp = new PagoParcial();
					HashMap res = (HashMap) mapResultado.get(i);
						pp.setIdCabecera(Integer.parseInt(res.get("IdCabecera").toString()));
						pp.setNumeroDocumento(pia.getDocNo());
						pp.setPosicion(pia.getItemNum());
						pp.setReferencia(pia.getRefDocNo());
						pp.setDocDate(pia.getDocDate());
					listaPagoParcial.add(pp);
				}
			}
		}
		return listaPagoParcial;
	}
}
