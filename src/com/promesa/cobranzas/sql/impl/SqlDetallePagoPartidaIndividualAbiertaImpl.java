package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.DetallePagoPartidaIndividualAbierta;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlDetallePagoPartidaIndividualAbiertaImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaDetallePagoPartidaIndividualAbierta(List<DetallePagoPartidaIndividualAbierta> lstDetallePagoPartidaIndividualAbierta){
		List<String> listaDetallePagoPartidaIndividualAbierta = new ArrayList<String>();
		for(DetallePagoPartidaIndividualAbierta detallePagoPartidaIndividualAbierta : lstDetallePagoPartidaIndividualAbierta){
			String sql = "INSERT INTO PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA (idPartidaIndividualAbierta, docNo, pstngDate, docDate,"
				+ "entryDate, expirationDate, currency, amtDoccur, refOrgUn, refDocNo, docType, itemNum, postKey, psprt, pszah, psskt, invRef, invItem,"
				+ "isLeaf, isExpanded, isReadOnly, indice, displayColor, fiscYear, fisPeriod, sgtxt, isReadOnly2, dbCrInd, verzn) VALUES ("
				+ detallePagoPartidaIndividualAbierta.getIdPartidaIndividualAbierta() + ",'" + detallePagoPartidaIndividualAbierta.getDocNo()
				+ "','" + detallePagoPartidaIndividualAbierta.getPstngDate() 	+ "','" + detallePagoPartidaIndividualAbierta.getDocDate()
				+ "','" + detallePagoPartidaIndividualAbierta.getEntryDate() 	+ "','" + detallePagoPartidaIndividualAbierta.getExpirationDate()
				+ "','" + detallePagoPartidaIndividualAbierta.getCurrency() 	+ "','" + detallePagoPartidaIndividualAbierta.getAmtDoccur()
				+ "','" + detallePagoPartidaIndividualAbierta.getRefOrgUn() 	+ "','" + detallePagoPartidaIndividualAbierta.getRefDocNo()
				+ "','" + detallePagoPartidaIndividualAbierta.getDocType() 		+ "','" + detallePagoPartidaIndividualAbierta.getItemNum()
				+ "','" + detallePagoPartidaIndividualAbierta.getPostKey() 		+ "','" + detallePagoPartidaIndividualAbierta.getPsprt()
				+ "','" + detallePagoPartidaIndividualAbierta.getPszah() 		+ "','" + detallePagoPartidaIndividualAbierta.getPsskt()
				+ "','" + detallePagoPartidaIndividualAbierta.getInvRef() 		+ "','" + detallePagoPartidaIndividualAbierta.getInvItem()
				+ "','" + detallePagoPartidaIndividualAbierta.getIsLeaf() 		+ "','" + detallePagoPartidaIndividualAbierta.getIsExpanded()
				+ "','" + detallePagoPartidaIndividualAbierta.getIsReadOnly() 	+ "','" + detallePagoPartidaIndividualAbierta.getIndice()
				+ "','" + detallePagoPartidaIndividualAbierta.getDisplayColor() + "','" + detallePagoPartidaIndividualAbierta.getFiscYear()
				+ "','" + detallePagoPartidaIndividualAbierta.getFisPeriod() 	+ "','" + detallePagoPartidaIndividualAbierta.getSgtxt()
				+ "','" + detallePagoPartidaIndividualAbierta.getIsReadOnly2() 	+ "','" + detallePagoPartidaIndividualAbierta.getDbCrInd()
				+ "','" + detallePagoPartidaIndividualAbierta.getVerzn() 		+ "');";
			listaDetallePagoPartidaIndividualAbierta.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaDetallePagoPartidaIndividualAbierta, "DETALLE PAGO PARTIDA INDIVIDUAL ABIERTA", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaDetallePagoPartidaIndividualAbierta(){
		String sql = "DELETE FROM PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA;";
		resultExecute = new ResultExecute(sql, "DETALLE PAGO PARTIDA INDIVIDUAL ABIERTA", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DetallePagoPartidaIndividualAbierta> obtenerListaDetallePagoPartidaIndividualAbierta(long idPartidaIndividualAbierta){
		List<DetallePagoPartidaIndividualAbierta> listaDetallePagoPartidaIndividualAbierta = new ArrayList<DetallePagoPartidaIndividualAbierta>();
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
		column.put("String:22", "index");
		column.put("String:23", "displayColor");
		column.put("String:24", "fiscYear");
		column.put("String:25", "fisPeriod");
		column.put("String:26", "sgtxt");
		column.put("String:27", "isReadOnly2");
		column.put("String:28", "dbCrInd");
		column.put("String:29", "verzn");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA " + "WHERE idPartidaIndividualAbierta = " + idPartidaIndividualAbierta + ";";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					DetallePagoPartidaIndividualAbierta detallePagoPartidaIndividualAbierta = new DetallePagoPartidaIndividualAbierta();
					detallePagoPartidaIndividualAbierta.setIdDetallePagoPartidaIndividualAbierta(Long.parseLong(res.get("idDetallePagoPartidaIndividualAbierta").toString()));
					detallePagoPartidaIndividualAbierta.setIdPartidaIndividualAbierta(Long.parseLong(res.get("idPartidaIndividualAbierta").toString()));
					detallePagoPartidaIndividualAbierta.setDocNo(res.get("docNo").toString());
					detallePagoPartidaIndividualAbierta.setPstngDate(res.get("pstngDate").toString());
					detallePagoPartidaIndividualAbierta.setDocDate(res.get("docDate").toString());
					detallePagoPartidaIndividualAbierta.setEntryDate(res.get("entryDate").toString());
					detallePagoPartidaIndividualAbierta.setExpirationDate(res.get("expirationDate").toString());
					detallePagoPartidaIndividualAbierta.setCurrency(res.get("currency").toString());
					detallePagoPartidaIndividualAbierta.setAmtDoccur(res.get("amtDoccur").toString());
					detallePagoPartidaIndividualAbierta.setRefOrgUn(res.get("refOrgUn").toString());
					detallePagoPartidaIndividualAbierta.setRefDocNo(res.get("refDocNo").toString());
					detallePagoPartidaIndividualAbierta.setDocType(res.get("docType").toString());
					detallePagoPartidaIndividualAbierta.setItemNum(res.get("itemNum").toString());
					detallePagoPartidaIndividualAbierta.setPostKey(res.get("postKey").toString());
					detallePagoPartidaIndividualAbierta.setPsprt(res.get("psprt").toString());
					detallePagoPartidaIndividualAbierta.setPszah(res.get("pszah").toString());
					detallePagoPartidaIndividualAbierta.setPsskt(res.get("psskt").toString());
					detallePagoPartidaIndividualAbierta.setInvRef(res.get("invRef").toString());
					detallePagoPartidaIndividualAbierta.setInvItem(res.get("invItem").toString());
					detallePagoPartidaIndividualAbierta.setIsLeaf(res.get("isLeaf").toString());
					detallePagoPartidaIndividualAbierta.setIsExpanded(res.get("isExpanded").toString());
					detallePagoPartidaIndividualAbierta.setIsReadOnly(res.get("isReadOnly").toString());
					detallePagoPartidaIndividualAbierta.setIndice(res.get("indice").toString());
					detallePagoPartidaIndividualAbierta.setDisplayColor(res.get("displayColor").toString());
					detallePagoPartidaIndividualAbierta.setFiscYear(res.get("fiscYear").toString());
					detallePagoPartidaIndividualAbierta.setFisPeriod(res.get("fisPeriod").toString());
					detallePagoPartidaIndividualAbierta.setSgtxt(res.get("sgtxt").toString());
					detallePagoPartidaIndividualAbierta.setIsReadOnly2(res.get("isReadOnly2").toString());
					detallePagoPartidaIndividualAbierta.setDbCrInd(res.get("dbCrInd").toString());
					detallePagoPartidaIndividualAbierta.setVerzn(res.get("verzn").toString());
					listaDetallePagoPartidaIndividualAbierta.add(detallePagoPartidaIndividualAbierta);
				}
			}
		}
		return listaDetallePagoPartidaIndividualAbierta;
	}
}