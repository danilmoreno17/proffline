package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.BancoPromesa;
import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlPagoRecibidoImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes") 
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaPagoRecibido(List<PagoRecibido> lstPagoRecibido) {
		List<String> listaPagoRecibido = new ArrayList<String>();
		for(PagoRecibido pagoRecibido : lstPagoRecibido){
			String sql = "INSERT INTO PROFFLINE_TB_PAGO_RECIBIDO (idCabecera, codigoCliente, idFormaPago, importe, referencia,"
					+ "numeroFactura, nroCtaBcoCliente, idBancoCliente, fechaVencimiento, fechaDocumento, idBancoPromesa) VALUES ("
					+ pagoRecibido.getIdCabecera()
					+ ",'" + pagoRecibido.getBancoCliente().getCodigoCliente()
					+ "','" + pagoRecibido.getFormaPago().getIdFormaPago()
					+ "','" + pagoRecibido.getImporte()
					+ "','" + pagoRecibido.getReferencia()
					+ "','" + pagoRecibido.getNumeroFactura()
					+ "','" + pagoRecibido.getBancoCliente().getNroCtaBcoCliente()
					+ "','" + pagoRecibido.getBancoCliente().getIdBancoCliente()
					+ "','" + pagoRecibido.getFechaVencimiento()
					+ "','" + pagoRecibido.getFechaDocumento()
					+ "','" + pagoRecibido.getBancoPromesa().getIdBancoPromesa() + "');";
			listaPagoRecibido.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaPagoRecibido, Constante.BD_TX);
	}
	
	public boolean eliminarListaPagoRecibido(long idCabecera){
		String sql = "DELETE FROM PROFFLINE_TB_PAGO_RECIBIDO " + "WHERE idCabecera = " + idCabecera + ";";
		resultExecute = new ResultExecute(sql, Constante.BD_TX);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PagoRecibido> obtenerListaPagoRecibido(long idCabecera){
		List<PagoRecibido> listaPagoRecibido = new ArrayList<PagoRecibido>();
		column = new HashMap();
		column.put("String:0", "idPagoRecibido");
		column.put("String:1", "idCabecera");
		column.put("String:2", "codigoCliente");
		column.put("String:3", "idFormaPago");
		column.put("String:4", "importe");
		column.put("String:5", "referencia");
		column.put("String:6", "numeroFactura");
		column.put("String:7", "nroCtaBcoCliente");
		column.put("String:8", "idBancoCliente");
		column.put("String:9", "fechaVencimiento");
		column.put("String:10", "fechaDocumento");
		column.put("String:11", "idBancoPromesa");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PAGO_RECIBIDO " + "WHERE idCabecera = " + idCabecera + ";";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
				SqlFormaPagoCobranzaImpl sqlFormaPagoCobranzaImpl = new SqlFormaPagoCobranzaImpl();
				List<FormaPago> listaFormaPagoCobranza = sqlFormaPagoCobranzaImpl.obtenerListaFormaPagoCobranza();
				SqlBancoPromesaImpl sqlBancoPromesaImpl = new SqlBancoPromesaImpl();
				List<BancoPromesa> listaBancoPromesa = sqlBancoPromesaImpl.obtenerListaBancoPromesa();
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					PagoRecibido pagoRecibido = new PagoRecibido();
					pagoRecibido.setIdPagoRecibido(Integer.parseInt(res.get("idPagoRecibido").toString()));
					pagoRecibido.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					BancoCliente bancoCliente = new BancoCliente();
					bancoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					bancoCliente.setNroCtaBcoCliente(res.get("nroCtaBcoCliente").toString());
					bancoCliente.setIdBancoCliente(res.get("idBancoCliente").toString());
					bancoCliente.setDescripcionBancoCliente("");
					List<BancoCliente> listaBancoCliente = sqlBancoClienteImpl.obtenerListaBancoCliente(bancoCliente.getCodigoCliente());
					if(listaBancoCliente.size() > 0){
						for(BancoCliente bc : listaBancoCliente){
							if(bc.getIdBancoCliente().equals(bancoCliente.getIdBancoCliente()) && bc.getNroCtaBcoCliente().equals(bancoCliente.getNroCtaBcoCliente())){
								bancoCliente.setDescripcionBancoCliente(bc.getDescripcionBancoCliente());
								break;
							}
						}
					}
					pagoRecibido.setBancoCliente(bancoCliente);
					FormaPago formaPago = new FormaPago();
					formaPago.setIdFormaPago(res.get("idFormaPago").toString());
					formaPago.setDescripcionFormaPago("");
					if(listaFormaPagoCobranza.size() > 0){
						for(FormaPago fp : listaFormaPagoCobranza){
							if(fp.getIdFormaPago().equals(formaPago.getIdFormaPago())){
								formaPago.setDescripcionFormaPago(fp.getDescripcionFormaPago());
								break;
							}
						}
					}
					pagoRecibido.setFormaPago(formaPago);
					pagoRecibido.setImporte(Double.parseDouble(res.get("importe").toString()));
					pagoRecibido.setReferencia(res.get("referencia").toString());
					pagoRecibido.setNumeroFactura(res.get("numeroFactura").toString());
					pagoRecibido.setFechaVencimiento(res.get("fechaVencimiento").toString());
					pagoRecibido.setFechaDocumento(res.get("fechaDocumento").toString());
					BancoPromesa bancoPromesa = new BancoPromesa();
					bancoPromesa.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					bancoPromesa.setDescripcionBancoPromesa("");
					if(listaBancoPromesa.size() > 0){
						for(BancoPromesa bp : listaBancoPromesa){
							if(bp.getIdBancoPromesa().equals(bancoPromesa.getIdBancoPromesa())){
								bancoPromesa.setDescripcionBancoPromesa(bp.getDescripcionBancoPromesa());
								break;
							}
						}
					}
					pagoRecibido.setBancoPromesa(bancoPromesa);
					listaPagoRecibido.add(pagoRecibido);
				}
			}
		}
		return listaPagoRecibido;
	}
	
	//MArcelo Moyano
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Double ObtenerImportePagoRecibido(long idcabecera){
		Double importe = 0d;
		column = new HashMap();
		column.put("String:0", "importe");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT importe FROM PROFFLINE_TB_PAGO_RECIBIDO " + "WHERE idCabecera = " + idcabecera + ";";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				importe = Double.parseDouble(res.get("importe").toString());
			}
		}
		return importe;
	}
}