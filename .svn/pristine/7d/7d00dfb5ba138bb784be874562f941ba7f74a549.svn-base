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

public class SqlPartidaIndividualAbiertaImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaPartidaIndividualAbierta(List<PartidaIndividualAbierta> lstPartidaIndividualAbierta){
		List<String> listaPartidaIndividualAbierta = new ArrayList<String>();
		for(PartidaIndividualAbierta partidaIndividualAbierta : lstPartidaIndividualAbierta){
			String sql = "INSERT INTO PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA (codigoCliente, docNo, pstngDate, docDate, entryDate, expirationDate,"
				+ "currency, amtDoccur, refOrgUn, refDocNo, docType, itemNum, postKey, psprt, pszah, psskt, invRef, invItem, isLeaf,"
				+ "isExpanded, isReadOnly, indice, displayColor, fiscYear, fisPeriod, sgtxt, isReadOnly2, dbCrInd, verzn) VALUES ('"
				+ partidaIndividualAbierta.getCodigoCliente()
				+ "','" + partidaIndividualAbierta.getDocNo()
				+ "','" + partidaIndividualAbierta.getPstngDate()
				+ "','" + partidaIndividualAbierta.getDocDate()
				+ "','" + partidaIndividualAbierta.getEntryDate()
				+ "','" + partidaIndividualAbierta.getExpirationDate()
				+ "','" + partidaIndividualAbierta.getCurrency()
				+ "','" + partidaIndividualAbierta.getAmtDoccur()
				+ "','" + partidaIndividualAbierta.getRefOrgUn()
				+ "','" + partidaIndividualAbierta.getRefDocNo()
				+ "','" + partidaIndividualAbierta.getDocType()
				+ "','" + partidaIndividualAbierta.getItemNum()
				+ "','" + partidaIndividualAbierta.getPostKey()
				+ "','" + partidaIndividualAbierta.getPsprt()
				+ "','" + partidaIndividualAbierta.getPszah()
				+ "','" + partidaIndividualAbierta.getPsskt()
				+ "','" + partidaIndividualAbierta.getInvRef()
				+ "','" + partidaIndividualAbierta.getInvItem()
				+ "','" + partidaIndividualAbierta.getIsLeaf()
				+ "','" + partidaIndividualAbierta.getIsExpanded()
				+ "','" + partidaIndividualAbierta.getIsReadOnly()
				+ "','" + partidaIndividualAbierta.getIndice()
				+ "','" + partidaIndividualAbierta.getDisplayColor()
				+ "','" + partidaIndividualAbierta.getFiscYear()
				+ "','" + partidaIndividualAbierta.getFisPeriod()
				+ "','" + partidaIndividualAbierta.getSgtxt()
				+ "','" + partidaIndividualAbierta.getIsReadOnly2()
				+ "','" + partidaIndividualAbierta.getDbCrInd()
				+ "','" + partidaIndividualAbierta.getVerzn() + "');";
			listaPartidaIndividualAbierta.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaPartidaIndividualAbierta, "PARTIDA INDIVIDUAL ABIERTA", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaPartidaIndividualAbierta(){
		String sql = "DELETE FROM PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA;";
		resultExecute = new ResultExecute(sql, "PARTIDA INDIVIDUAL ABIERTA", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	public boolean eliminarListaPartidaIndividualAbiertaPorCliente(String codigoCliente){
		String sql = "DELETE FROM PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA "+
						" WHERE idPartidaIndividualAbierta IN ("+
						" select idPartidaIndividualAbierta from PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA "+
						" WHERE codigoCliente = '" + codigoCliente + "'); " ;
			String sql1 = "delete FROM  PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA"+ " WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecute = new ResultExecute(sql, "DETALLE PAGO PARTIDA INDIVIDUAL ABIERTA", Constante.BD_SYNC);
		resultExecute = new ResultExecute(sql1, "PARTIDA INDIVIDUAL ABIERTA", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PartidaIndividualAbierta> obtenerListaPartidaIndividualAbierta(String codigoCliente){
		List<PartidaIndividualAbierta> listaPartidaIndividualAbierta = new ArrayList<PartidaIndividualAbierta>();
		column = new HashMap();
		column.put("String:0", "idPartidaIndividualAbierta");
		column.put("String:1", "codigoCliente");
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
		String sql = "SELECT * FROM PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					PartidaIndividualAbierta partidaIndividualAbierta = new PartidaIndividualAbierta();
					partidaIndividualAbierta.setIdPartidaIndividualAbierta(Long.parseLong(res.get("idPartidaIndividualAbierta").toString()));
					partidaIndividualAbierta.setCodigoCliente(res.get("codigoCliente").toString());
					partidaIndividualAbierta.setDocNo(res.get("docNo").toString());
					partidaIndividualAbierta.setPstngDate(res.get("pstngDate").toString());
					partidaIndividualAbierta.setDocDate(res.get("docDate").toString());
					partidaIndividualAbierta.setEntryDate(res.get("entryDate").toString());
					partidaIndividualAbierta.setExpirationDate(res.get("expirationDate").toString());
					partidaIndividualAbierta.setCurrency(res.get("currency").toString());
					partidaIndividualAbierta.setAmtDoccur(res.get("amtDoccur").toString());
					partidaIndividualAbierta.setRefOrgUn(res.get("refOrgUn").toString());
					partidaIndividualAbierta.setRefDocNo(res.get("refDocNo").toString());
					partidaIndividualAbierta.setDocType(res.get("docType").toString());
					partidaIndividualAbierta.setItemNum(res.get("itemNum").toString());
					partidaIndividualAbierta.setPostKey(res.get("postKey").toString());
					partidaIndividualAbierta.setPsprt(res.get("psprt").toString());
					partidaIndividualAbierta.setPszah(res.get("pszah").toString());
					partidaIndividualAbierta.setPsskt(res.get("psskt").toString());
					partidaIndividualAbierta.setInvRef(res.get("invRef").toString());
					partidaIndividualAbierta.setInvItem(res.get("invItem").toString());
					partidaIndividualAbierta.setIsLeaf(res.get("isLeaf").toString());
					partidaIndividualAbierta.setIsExpanded(res.get("isExpanded").toString());
					partidaIndividualAbierta.setIsReadOnly(res.get("isReadOnly").toString());
					partidaIndividualAbierta.setIndice(res.get("indice").toString());
					partidaIndividualAbierta.setDisplayColor(res.get("displayColor").toString());
					partidaIndividualAbierta.setFiscYear(res.get("fiscYear").toString());
					partidaIndividualAbierta.setFisPeriod(res.get("fisPeriod").toString());
					partidaIndividualAbierta.setSgtxt(res.get("sgtxt").toString());
					partidaIndividualAbierta.setIsReadOnly2(res.get("isReadOnly2").toString());
					partidaIndividualAbierta.setDbCrInd(res.get("dbCrInd").toString());
					partidaIndividualAbierta.setVerzn(res.get("verzn").toString());
					listaPartidaIndividualAbierta.add(partidaIndividualAbierta);
				}
			}
		}
		return listaPartidaIndividualAbierta;
	}
	
	// Marcelo Moyano
	public void ActualizarImportePagoPartidasIndividuales(PagoParcial pp, Double importePago, Double importePagoParcial)
		throws SQLException{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "UPDATE PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA SET " + "pszah = '" + importePago + "' ,psprt = '" + importePagoParcial + "' WHERE docNo = '"  + pp.getNumeroDocumento()
				+ "' and itemnum = '"  + pp.getPosicion() + "' and refDocNo = '" + pp.getReferencia() + "' and docDate = '"  +pp.getDocDate()+ "';";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Double ObtenerImportePagoPartidasIndividualAbierta(PagoParcial pp){
		Double importePago = 0d;
		column = new HashMap();
		column.put("String:0", "pszah");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT pszah FROM PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA " + "  WHERE docNo = '"  + pp.getNumeroDocumento()
			+ "' and itemnum = '"  + pp.getPosicion() + "' and refDocNo = '" + pp.getReferencia() + "' and docDate = '"  + pp.getDocDate()    + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				importePago = Double.parseDouble(res.get("pszah").toString());
			}
		}
		return importePago;
	}
}