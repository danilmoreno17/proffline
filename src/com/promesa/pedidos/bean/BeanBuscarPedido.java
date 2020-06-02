package com.promesa.pedidos.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanBuscarPedido {

		private String strVendorId;
		private String strDocType; 
		private String strFechaInicio; 
		private String strFechaFin;	
		private SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		
		////
		
		public String getStrVendorId() {
			return strVendorId;
		}


		public void setStrVendorId(String strVendorId) {
			this.strVendorId = strVendorId;
		}


		public String getStrDocType() {
			return strDocType;
		}


		public void setStrDocType(String strDocType) {
			this.strDocType = strDocType;
		}


		public String getStrFechaInicio() {
			return strFechaInicio;
		}


		public void setStrFechaInicio(Date strFechaInicio) {
			this.strFechaInicio = formato.format(strFechaInicio);
		}


		public String getStrFechaFin() {
			return strFechaFin;
		}


		public void setStrFechaFin(Date strFechaFin) {
			this.strFechaFin = formato.format(strFechaFin);
		}
}
