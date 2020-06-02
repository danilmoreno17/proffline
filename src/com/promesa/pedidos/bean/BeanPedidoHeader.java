package com.promesa.pedidos.bean;

public class BeanPedidoHeader implements Cloneable {

	private String strDocumentoVenta;
	private String strFechaDocumento;
	private String strFechaCreacion;
	private String strCreador;
	private String strValorNeto;
	private String strClDocVentas;
	private String strCodVendedor;
	private String strCodCliente;
	private String strCliente;
	private String strClaseRiesgo;
	private String strDestinatario;
	private String strCondicionPago;
	private String strSincronizado;
	private String strSello;
	
	private String strDocumentoReferencia;
	private String strFechaReferencia;
	

	private String DOC_TYPE;
	private String SALES_ORG;
	private String DISTR_CHAN;
	private String DIVISION;

	private String SALES_GRP;
	private String SALES_OFF;
	private String REQ_DATE_H;
	private String PURCH_DATE;
	private String PMNTTRMS;
	private String DLV_BLOCK;
	private String PRICE_DATE;
	private String PURCH_NO_C;
	private String PURCH_NO_S;//Nombre contacto para retiro
	private String SD_DOC_CAT;
	private String DOC_DATE;
	private String BILL_DATE;
	private String SERV_DATE;
	private String CURRENCY;
	private String CREATED_BY;
	private String SHIP_TYPE;
	private String SHIP_COND;
	private String PO_METHOD;
	private String ORD_REASON;
	private String codigoPedido;
	
	// Marcelo Moyano 10/05/2013 - 11:15
	// Codigo: PRO-2013-0024
	// Titulo: Incluir campos Adicionales en Pantalla de Devoluciones
	private String REF_1;//No Guía Servientrega
	private String DUN_COUNT;//Nro. Bultos
	private String PO_SUPPLEM;//Tpo Ons (Dir,Ven)
	private String COLLECT_NO;//Nro. FUD
	private String NAME;//Persona Reporta devolución Responsable
	private String NET_WEIGHT;//PESOTOTAL
	// Asunto: PRO-2013-0023 - Mostrar Ultimo Status de Pedidos
	// 13/05/2013 - 15:48
	private String status;
	
	private int secuencialPedido;

	public String getNET_WEIGHT() {
		return NET_WEIGHT;
	}

	public void setNET_WEIGHT(String nET_WEIGHT) {
		NET_WEIGHT = nET_WEIGHT;
	}

	public String getREF_1() {
		return REF_1;
	}

	public void setREF_1(String rEF_1) {
		REF_1 = rEF_1;
	}

	public String getDUN_COUNT() {
		return DUN_COUNT;
	}

	public void setDUN_COUNT(String dUN_COUNT) {
		DUN_COUNT = dUN_COUNT;
	}

	public String getPO_SUPPLEM() {
		return PO_SUPPLEM;
	}

	public void setPO_SUPPLEM(String pO_SUPPLEM) {
		PO_SUPPLEM = pO_SUPPLEM;
	}

	public String getCOLLECT_NO() {
		return COLLECT_NO;
	}

	public void setCOLLECT_NO(String cOLLECT_NO) {
		COLLECT_NO = cOLLECT_NO;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}
	// Marcelo Moyano

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public String getSHIP_COND() {
		return SHIP_COND;
	}

	public void setSHIP_COND(String sHIP_COND) {
		SHIP_COND = sHIP_COND;
	}

	public String getPO_METHOD() {
		return PO_METHOD;
	}

	public void setPO_METHOD(String pO_METHOD) {
		PO_METHOD = pO_METHOD;
	}

	private String txtClaseRiesgo;
	private String txtLimiteCredito;
	private String txtDisponible;

	private String idBD;

	private int source;

	private String strEstadoPicking = "";

	public String getStrEstadoPicking() {
		return strEstadoPicking;
	}

	public void setStrEstadoPicking(String strEstadoPicking) {
		this.strEstadoPicking = strEstadoPicking;
	}

	public String getStrDocumentoVenta() {
		return strDocumentoVenta;
	}

	public void setStrDocumentoVenta(String strDocumentoVenta) {
		this.strDocumentoVenta = strDocumentoVenta;
	}

	public String getStrFechaDocumento() {
		return strFechaDocumento;
	}

	public void setStrFechaDocumento(String strFechaDocumento) {
		this.strFechaDocumento = strFechaDocumento;
	}

	public String getStrFechaCreacion() {
		return strFechaCreacion;
	}

	public void setStrFechaCreacion(String strFechaCreacion) {
		this.strFechaCreacion = strFechaCreacion;
	}

	public String getStrCreador() {
		return strCreador;
	}

	public void setStrCreador(String strCreador) {
		this.strCreador = strCreador;
	}

	public String getStrValorNeto() {
		return strValorNeto;
	}

	public void setStrValorNeto(String strValorNeto) {
		this.strValorNeto = strValorNeto;
	}

	public String getStrClDocVentas() {
		return strClDocVentas;
	}

	public void setStrClDocVentas(String strClDocVentas) {
		this.strClDocVentas = strClDocVentas;
	}

	public String getStrCodVendedor() {
		return strCodVendedor;
	}

	public void setStrCodVendedor(String strCodVendedor) {
		this.strCodVendedor = strCodVendedor;
	}

	public String getStrCodCliente() {
		return strCodCliente;
	}

	public void setStrCodCliente(String strCodCliente) {
		this.strCodCliente = strCodCliente;
	}

	public String getStrCliente() {
		return strCliente;
	}

	public void setStrCliente(String strCliente) {
		this.strCliente = strCliente;
	}

	public String getStrClaseRiesgo() {
		return strClaseRiesgo;
	}

	public void setStrClaseRiesgo(String strClaseRiesgo) {
		this.strClaseRiesgo = strClaseRiesgo;
	}

	public String getStrDestinatario() {
		return strDestinatario;
	}

	public void setStrDestinatario(String strDestinatario) {
		this.strDestinatario = strDestinatario;
	}

	public String getStrCondicionPago() {
		return strCondicionPago;
	}

	public void setStrCondicionPago(String strCondicionPago) {
		this.strCondicionPago = strCondicionPago;
	}

	public String getDOC_TYPE() {
		return DOC_TYPE;
	}

	public void setDOC_TYPE(String dOC_TYPE) {
		DOC_TYPE = dOC_TYPE;
	}

	public String getSALES_ORG() {
		return SALES_ORG;
	}

	public void setSALES_ORG(String sALES_ORG) {
		SALES_ORG = sALES_ORG;
	}

	public String getDISTR_CHAN() {
		return DISTR_CHAN;
	}

	public void setDISTR_CHAN(String dISTR_CHAN) {
		DISTR_CHAN = dISTR_CHAN;
	}

	public String getDIVISION() {
		return DIVISION;
	}

	public void setDIVISION(String dIVISION) {
		DIVISION = dIVISION;
	}

	public String getSALES_GRP() {
		return SALES_GRP;
	}

	public void setSALES_GRP(String sALES_GRP) {
		SALES_GRP = sALES_GRP;
	}

	public String getSALES_OFF() {
		return SALES_OFF;
	}

	public void setSALES_OFF(String sALES_OFF) {
		SALES_OFF = sALES_OFF;
	}

	public String getREQ_DATE_H() {
		return REQ_DATE_H;
	}

	public void setREQ_DATE_H(String rEQ_DATE_H) {
		REQ_DATE_H = rEQ_DATE_H;
	}

	public String getPURCH_DATE() {
		return PURCH_DATE;
	}

	public void setPURCH_DATE(String pURCH_DATE) {
		PURCH_DATE = pURCH_DATE;
	}

	public String getPMNTTRMS() {
		return PMNTTRMS;
	}

	public void setPMNTTRMS(String pMNTTRMS) {
		PMNTTRMS = pMNTTRMS;
	}

	public String getDLV_BLOCK() {
		return DLV_BLOCK;
	}

	public void setDLV_BLOCK(String dLV_BLOCK) {
		DLV_BLOCK = dLV_BLOCK;
	}

	public String getPRICE_DATE() {
		return PRICE_DATE;
	}

	public void setPRICE_DATE(String pRICE_DATE) {
		PRICE_DATE = pRICE_DATE;
	}

	public String getPURCH_NO_C() {
		return PURCH_NO_C;
	}
	
	public void setPURCH_NO_C(String pURCH_NO_C) {
		PURCH_NO_C = pURCH_NO_C;
	}
	
	public String getPURCH_NO_S() {
		return PURCH_NO_S;
	}
	
	public void setPURCH_NO_S(String pURCH_NO_S) {
		PURCH_NO_S = pURCH_NO_S;
	}

	public String getSD_DOC_CAT() {
		return SD_DOC_CAT;
	}

	public void setSD_DOC_CAT(String sD_DOC_CAT) {
		SD_DOC_CAT = sD_DOC_CAT;
	}

	public String getDOC_DATE() {
		return DOC_DATE;
	}

	public void setDOC_DATE(String dOC_DATE) {
		DOC_DATE = dOC_DATE;
	}

	public String getBILL_DATE() {
		return BILL_DATE;
	}

	public void setBILL_DATE(String bILL_DATE) {
		BILL_DATE = bILL_DATE;
	}

	public String getSERV_DATE() {
		return SERV_DATE;
	}

	public void setSERV_DATE(String sERV_DATE) {
		SERV_DATE = sERV_DATE;
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public String getSHIP_TYPE() {
		return SHIP_TYPE;
	}

	public void setSHIP_TYPE(String sHIP_TYPE) {
		SHIP_TYPE = sHIP_TYPE;
	}
	
	public void setORD_REASON(String oRD_REASON) {
		ORD_REASON = oRD_REASON;
	}

	public String getORD_REASON() {
		return ORD_REASON;
	}

	public String getStrSincronizado() {
		return strSincronizado;
	}

	public void setStrSincronizado(String strSincronizado) {
		this.strSincronizado = strSincronizado;
	}

	public String getStrSello() {
		return strSello;
	}

	public void setStrSello(String strSello) {
		this.strSello = strSello;
	}

	public String getIdBD() {
		return idBD;
	}

	public void setIdBD(String idBD) {
		this.idBD = idBD;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getTxtClaseRiesgo() {
		return txtClaseRiesgo;
	}

	public void setTxtClaseRiesgo(String txtClaseRiesgo) {
		this.txtClaseRiesgo = txtClaseRiesgo;
	}

	public String getTxtLimiteCredito() {
		return txtLimiteCredito;
	}

	public void setTxtLimiteCredito(String txtLimiteCredito) {
		this.txtLimiteCredito = txtLimiteCredito;
	}

	public String getTxtDisponible() {
		return txtDisponible;
	}

	public void setTxtDisponible(String txtDisponible) {
		this.txtDisponible = txtDisponible;
	}

	@Override
	public String toString() {
		return "BeanPedidoHeader [strDocumentoVenta=" + strDocumentoVenta
				+ ", strFechaDocumento=" + strFechaDocumento
				+ ", strFechaCreacion=" + strFechaCreacion + ", strCreador="
				+ strCreador + ", strValorNeto=" + strValorNeto
				+ ", strClDocVentas=" + strClDocVentas + ", strCodVendedor="
				+ strCodVendedor + ", strCodCliente=" + strCodCliente
				+ ", strCliente=" + strCliente + ", strClaseRiesgo="
				+ strClaseRiesgo + ", strDestinatario=" + strDestinatario
				+ ", strCondicionPago=" + strCondicionPago
				+ ", strSincronizado=" + strSincronizado + ", DOC_TYPE="
				+ DOC_TYPE + ", SALES_ORG=" + SALES_ORG + ", DISTR_CHAN="
				+ DISTR_CHAN + ", DIVISION=" + DIVISION + ", SALES_GRP="
				+ SALES_GRP + ", SALES_OFF=" + SALES_OFF + ", REQ_DATE_H="
				+ REQ_DATE_H + ", PURCH_DATE=" + PURCH_DATE + ", PMNTTRMS="
				+ PMNTTRMS + ", DLV_BLOCK=" + DLV_BLOCK + ", PRICE_DATE="
				+ PRICE_DATE + ", PURCH_NO_C=" + PURCH_NO_C + ", SD_DOC_CAT="
				+ SD_DOC_CAT + ", DOC_DATE=" + DOC_DATE + ", BILL_DATE="
				+ BILL_DATE + ", SERV_DATE=" + SERV_DATE + ", CURRENCY="
				+ CURRENCY + ", CREATED_BY=" + CREATED_BY + ", SHIP_TYPE="
				+ SHIP_TYPE + ", txtClaseRiesgo=" + txtClaseRiesgo
				+ ", txtLimiteCredito=" + txtLimiteCredito + ", txtDisponible="
				+ txtDisponible + ", idBD=" + idBD + ", source=" + source + "]";
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}
	
	//MARCELO MOYANO 13/05/2013 - 15:48
	//Asunto: PRO-2013-0023 - Mostrar Ultimo Status de Pedidos
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	//MARCELO MOYANO

	public void setStrDocumentoReferencia(String strDocumentoReferencia) {
		this.strDocumentoReferencia = strDocumentoReferencia;
	}

	public String getStrDocumentoReferencia() {
		return strDocumentoReferencia;
	}

	public void setStrFechaReferencia(String strFechaReferencia) {
		this.strFechaReferencia = strFechaReferencia;
	}

	public String getStrFechaReferencia() {
		return strFechaReferencia;
	}

	public int getSecuencialPedido() {
		return secuencialPedido;
	}

	public void setSecuencialPedido(int secuencialPedido) {
		this.secuencialPedido = secuencialPedido;
	}
}