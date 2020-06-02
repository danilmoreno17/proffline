package com.promesa.sap;

import java.util.List;

import util.ConexionSAP;

import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.dao.ResultExecute;
import com.promesa.util.Conexiones;
import com.promesa.util.Constante;

public class STEST {

	@SuppressWarnings({ "unused", "unchecked" })
	private void guardarTexto() throws Exception {
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_ORDER_SAVE_TEXT");
			con.IngresarDatosInput("1000", "IV_BUKRS");// Sociedad
			con.IngresarDatosInput("2012", "IV_FISC_YEAR");// Ejercicio
			con.IngresarDatosInput("3500001352", "IV_DOC_NO");// Número de un documento contable
			con.IngresarDatosInput("000003", "IV_POS");// Número de posición del documento comercial
			con.CreaTabla("IT_LINES");
			con.IngresarDatoTabla("Esto es un dato de prueba", "TDLINE", 1);
			con.EjecutarRFC();
			con.CreaTabla("ET_MSG");
			List<String> mensajes = con.ObtenerDatosTabla();
			if (!mensajes.isEmpty()) {
				for (String string : mensajes) {
					System.out.println(string);
				}
			}
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private String leerTexto() throws Exception {
		String descripcion = "";
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_ORDER_READ_TEXT");
			con.IngresarDatosInput("1000", "IV_BUKRS");// Sociedad
			con.IngresarDatosInput("2012", "IV_FISC_YEAR");// Ejercicio
			con.IngresarDatosInput("3500001352", "IV_DOC_NO");// Número de un documento contable
			con.IngresarDatosInput("000003", "IV_POS");// Número de posición del documento comercial
			con.CreaTabla("ET_MSG");
			con.EjecutarRFC();
			con.CreaTabla("IT_LINES");
			List<String> mensajes = con.ObtenerDatosTabla();
			// OBSERVACION
			if (!mensajes.isEmpty()) {
				for (String string : mensajes) {
					String observaciones[] = string.split("[¬]");
					if (observaciones.length == 3) {
						descripcion = observaciones[2];
					}
				}
			}
			con.CreaTabla("ET_MSG");
			mensajes = con.ObtenerDatosTabla();
			if (!mensajes.isEmpty()) {
				for (String string : mensajes) {
					System.out.println(string);
				}
			}
		}
		return descripcion;
	}

	@SuppressWarnings("unchecked")
	public String[] obtenerBancoAsociadoANumeroCuenta(String numeroDeCuenta, String codigoCliente) throws Exception {
		String[] banco = null;
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_BNK_ACCT");
			con.IngresarDatosInput(numeroDeCuenta, "IV_BANKN");
			con.IngresarDatosInput(codigoCliente, "IV_CUST_ID");
			con.EjecutarRFC();
			con.CreaTabla("T_BNK_ACCOUNT");
			con.IngresarDatoTabla("Esto es un dato de prueba", "TDLINE", 1);
			List<String> mensaje = con.ObtenerDatosTabla();
			if (!mensaje.isEmpty()) {
				banco = String.valueOf(mensaje.get(0)).split("[¬]");
			}
			con.DesconectarSAP();
		}
		return banco;
	}

	public String[] registroAnticipo(AnticipoCliente anticipoCliente) throws Exception {
		String[] arregloRegistroAnticipo = new String[3];
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_CUSTOMER_ENTER_DOWNPAY");
			con.CreaTabla("IS_PAYMENT_INFO");
			con.IngresarDatoTabla(anticipoCliente.getIdFormaPago(), "BLART", 1);
			con.IngresarDatoTabla(anticipoCliente.getReferencia(), "XBLNR", 1);
			con.IngresarDatoTabla(anticipoCliente.getImporte(), "WRBTR", 1);
			con.IngresarDatoTabla(anticipoCliente.getCodigoCliente(), "CUST_ID", 1);
			con.IngresarDatoTabla(anticipoCliente.getIdBancoPromesa(), "XREF2_HD", 1);
			con.IngresarDatoTabla(anticipoCliente.getNroCtaBcoCliente(), "BANKN", 1);
			con.IngresarDatoTabla(anticipoCliente.getIdBancoCliente(), "BANK", 1);
			con.IngresarDatoTabla(anticipoCliente.getCodigoVendedor(), "RESPONSABLE_ID", 1);
			con.IngresarDatoTabla(anticipoCliente.getObservaciones(), "COMMENTS", 1);
			con.EjecutarRFC();
			String cadenaNumTicket = con.ObtenerDato("EV_TICKET_NRO");
			con.CreaTabla("ET_MSG");
			@SuppressWarnings("unchecked")
			List<String> mensaje = con.ObtenerDatosTabla();
			String[] valores = String.valueOf(mensaje.get(0)).split("[¬]");
			arregloRegistroAnticipo[0] = cadenaNumTicket; // Num. Ticket.
			arregloRegistroAnticipo[1] = valores[1].trim(); // Indicador.
			arregloRegistroAnticipo[2] = valores[3].trim(); // Mensaje.
			con.DesconectarSAP();
			return arregloRegistroAnticipo;
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public void testFUDs(String idCliente) throws Exception {
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_DETAIL2");
			con.IngresarDatosInput(idCliente, "IV_CUST_ID");
			con.EjecutarRFC();
			con.CreaTabla("ET_ORDER");
			List piSel = con.ObtenerDatosTabla();
			for (int i = 0; i < piSel.size(); i++) {
				String[] valores = String.valueOf(piSel.get(i)).split("[¬]");
				System.out.println(String.valueOf(piSel.get(i)));
				for (int j = 1; j < valores.length; j++) {
					System.out.println("Item " + j + " : " + valores[j].trim());
				}
			}
			con.DesconectarSAP();
		}
	}

	public static void main(String[] args) {
		try {
			STEST.testCondicionesComerciales("ZTSD_ZD01");
			 System.out.println("********************************************");
			 STEST.testCondicionesComerciales("ZTSD_ZD02");
			 System.out.println("********************************************");
			 STEST.testCondicionesComerciales("ZTSD_ZD3X");
			 System.out.println("********************************************");
			 STEST.testCondicionesComerciales("ZTSD_ZD4X");
			 System.out.println("********************************************");
			 STEST.testCondicionesComerciales("ZTSD_SCALES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ZTSD_ZD01,ZTSD_ZD02,ZTSD_ZD3X,ZTSD_ZD4X,ZTSD_SCALES
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void testCondicionesComerciales(String tabla) throws Exception {
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_MIGRA_CONDICIONES");
			con.IngresarDatosInput(tabla, "TABLE_NAME");
			con.EjecutarRFC();
			if (tabla.compareTo("ZTSD_ZD01") == 0) {
				con.CreaTabla("T_ZD01");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_1X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String nivel = v[2].isEmpty() ? "*" : v[2];
					String claseCond = v[3].isEmpty() ? "*" : v[3];
					String acceso = v[4].isEmpty() ? "*" : v[4];
					String tablaCond = v[5].isEmpty() ? "*" : v[5];
					String prioridad = v[6].isEmpty() ? "*" : v[6];
					String claseMaterial = v[7].isEmpty() ? "*" : v[7];
					String condicionPago = v[9].isEmpty() ? "*" : v[9];
					String cliente = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
					String unidad = v[10].isEmpty() ? "*" : v[10];
					String importe = v[11].isEmpty() ? "*" : v[11];
					String escala = v[12];
					String nroRegCond = v[13].isEmpty() ? "*" :  "" + Long.parseLong(v[13]);
					String num = v[14].isEmpty() ? "*" : v[14];
					sql = "insert into PROFFLINE_TB_CONDICION_1X values('"
							+ nivel + "','" + claseCond + "','" + acceso
							+ "','" + tablaCond + "','" + prioridad + "','"
							+ claseMaterial + "','" + condicionPago + "','"
							+ cliente + "','" + unidad + "','" + importe
							+ "','" + escala + "','" + nroRegCond + "','" + num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);

				}
			} else if (tabla.compareTo("ZTSD_ZD02") == 0) {
				con.CreaTabla("T_ZD02");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_2X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String nivel = v[2].isEmpty() ? "*" : v[2];
					String claseCond = v[3].isEmpty() ? "*" : v[3];
					String acceso = v[4].isEmpty() ? "*" : v[4];
					String tablaCond = v[5].isEmpty() ? "*" : v[5];
					String prioridad = v[6].isEmpty() ? "*" : v[6];
					String cliente = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
					String grupoCliente = v[8].isEmpty() ? "*" : v[8];
					String canal = v[9].isEmpty() ? "*" : v[9];
					String unidad = v[10].isEmpty() ? "*" : v[10];
					String importe = v[11].isEmpty() ? "*" : v[11];
					String escala = v[12];
					String nroRegCond = v[13].isEmpty() ? "*" : "" + Long.parseLong(v[13]);
					String num = v[14].isEmpty() ? "*" : v[14];
					sql = "insert into PROFFLINE_TB_CONDICION_2X values('"
							+ nivel + "','" + claseCond + "','" + acceso + "','" + tablaCond
							+ "','" + prioridad + "','" + cliente + "','" + grupoCliente
							+ "','" + canal + "','" + unidad + "','" + importe
							+ "','" + escala + "','" + nroRegCond + "','" + num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);

				}
			} else if (tabla.compareTo("ZTSD_ZD3X") == 0) {
				con.CreaTabla("T_ZD3X");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_3X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				sql = "DELETE FROM PROFFLINE_TB_CONDICION_ZPR1;";
				rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String nivel = v[2].isEmpty() ? "*" : v[2];
					String ClaseCond = v[3].isEmpty() ? "*" : v[3];
					String Acceso = v[4].isEmpty() ? "*" : v[4];
					String TablaCond = v[5].isEmpty() ? "*" : v[5];
					String Prioridad = v[6].isEmpty() ? "*" : v[6];
					String Material = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
					String DivisionC = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
					String DivisionCom = v[9].isEmpty() ? "*" : "" + Long.parseLong(v[9]);
					String CatProd = v[10].isEmpty() ? "*" : "" + Long.parseLong(v[10]);
					String Familia = v[11].isEmpty() ? "*" :"" + Long.parseLong(v[11]);
					String Linea = v[12].isEmpty() ? "*" : "" + Long.parseLong(v[12]);
					String Grupo = v[13].isEmpty() ? "*" : "" + Long.parseLong(v[13]);
					String Cliente = v[14].isEmpty() ? "*" : "" + Long.parseLong(v[14]);
					String ZonaPromesa = v[15].isEmpty() ? "*" : v[15];
					String Clase = v[16].isEmpty() ? "*" : v[16];
					String GrupoCliente = v[17].isEmpty() ? "*" : v[17];
					String Unidad = v[18].isEmpty() ? "*" : v[18];
					String Importe = v[19].isEmpty() ? "*" : v[19];
					String Escala = v[20];
					String NroRegCond = v[21].isEmpty() ? "*" : "" + Long.parseLong(v[21]);
					String Num = v[22].isEmpty() ? "*" : v[22];
					String tabla3X = " PROFFLINE_TB_CONDICION_3X ";
					if(ClaseCond.equalsIgnoreCase("ZPR1")){
						tabla3X = " PROFFLINE_TB_CONDICION_ZPR1 ";
					}
					sql = "insert into" + tabla3X +"values('"
							+ nivel + "','" + ClaseCond + "','" + Acceso + "','" + TablaCond
							+ "','" + Prioridad + "','" + Material + "','" + DivisionC
							+ "','" + DivisionCom + "','" + CatProd + "','" + Familia
							+ "','" + Linea + "','" + Grupo + "','" + Cliente
							+ "','" + ZonaPromesa + "','" + Clase + "','" + GrupoCliente
							+ "','" + Unidad + "','" + Importe + "','" + Escala
							+ "','" + NroRegCond + "','" + Num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);

				}
			} else if (tabla.compareTo("ZTSD_ZD4X") == 0) {
				con.CreaTabla("T_ZD4X");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_4X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String Nivel = v[2].isEmpty() ? "*" : v[2];
					String ClaseCond = v[3].isEmpty() ? "*" : v[3];
					String Acceso = v[4].isEmpty() ? "*" : v[4];
					String TablaCond = v[5].isEmpty() ? "*" : v[5];
					String Prioridad = v[6].isEmpty() ? "*" : v[6];
					String Material = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
					String DivisionC = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
					String DivisionCom = v[9].isEmpty() ? "*" : "" + Long.parseLong(v[9]);
					String CatProd = v[10].isEmpty() ? "*" : "" + Long.parseLong(v[10]);
					String Familia = v[11].isEmpty() ? "*" : "" + Long.parseLong(v[11]);
					String Linea = v[12].isEmpty() ? "*" : "" + Long.parseLong(v[12]);
					String Grupo = v[13].isEmpty() ? "*" : "" + Long.parseLong(v[13]);
					String Cliente = v[14].isEmpty() ? "*" : "" + Long.parseLong(v[14]);
					String ZonaPromesa = v[15].isEmpty() ? "*" : v[15];
					String Clase = v[16].isEmpty() ? "*" : v[16];
					String GrupoCliente = v[17].isEmpty() ? "*" : v[17];
					String Unidad = v[18].isEmpty() ? "*" : v[18];
					String Importe = v[19].isEmpty() ? "*" : v[19];
					String Escala = v[20];
					String NroRegCond = v[21].isEmpty() ? "*" : "" + Long.parseLong(v[21]);
					String Num = v[22].isEmpty() ? "*" : v[22];
					sql = "insert into PROFFLINE_TB_CONDICION_4X values('"
							+ Nivel + "','" + ClaseCond + "','" + Acceso + "','" + TablaCond
							+ "','" + Prioridad + "','" + Material + "','" + DivisionC
							+ "','" + DivisionCom + "','" + CatProd + "','" + Familia
							+ "','" + Linea + "','" + Grupo + "','" + Cliente
							+ "','" + ZonaPromesa + "','" + Clase + "','" + GrupoCliente
							+ "','" + Unidad + "','" + Importe + "','" + Escala
							+ "','" + NroRegCond + "','" + Num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);
				}
			}
			 else if (tabla.compareTo("ZTSD_ZD05") == 0) {
					con.CreaTabla("T_ZD05");
					List resultado = con.ObtenerDatosTabla();
					String sql = "DELETE FROM PROFFLINE_TB_CONDICION_5X;";
					ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
					for (int i = 0; i < resultado.size(); i++) {
						String[] v = String.valueOf(resultado.get(i)).split("[¬]");
						String Nivel = v[2].isEmpty() ? "*" : v[2];
						String ClaseCond = v[3].isEmpty() ? "*" : v[3];
						String Acceso = v[4].isEmpty() ? "*" : v[4];
						String TablaCond = v[5].isEmpty() ? "*" : v[5];
						String Prioridad = v[6].isEmpty() ? "*" : v[6];

						String DivisionCom = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
						String CatProd = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
						String Familia = v[9].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
						String Cliente = v[10].isEmpty() ? "*" : "" + Long.parseLong(v[10]);

						String Unidad = v[11].isEmpty() ? "*" : v[11];
						String Importe = v[12].isEmpty() ? "*" : v[12];
						String Escala = v[13];
						String NroRegCond = v[14].isEmpty() ? "*" : "" + Long.parseLong(v[14]);
						String Num = v[15].isEmpty() ? "*" : v[15];
						sql = "insert into PROFFLINE_TB_CONDICION_5X values('"
								+ Nivel + "','" + ClaseCond + "','" + Acceso + "','" + TablaCond
								+ "','" + Prioridad + "','" + DivisionCom + "','" + CatProd + "','" + Familia
								+ "','" + Cliente
								+ "','" + Unidad + "','" + Importe + "','" + Escala
								+ "','" + NroRegCond + "','" + Num + "');";
						rs = new ResultExecute(sql, Constante.BD_SYNC);
					}
				}
			else if (tabla.compareTo("ZTSD_SCALES") == 0) {
				con.CreaTabla("T_SCALES");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_ESCALAS;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String ClaseCond = v[2].isEmpty() ? "*" : v[2];
					String TipoEscala = v[3].isEmpty() ? "*" : v[3];
					String NroRegCond = v[4].isEmpty() ? "*" : "" + Long.parseLong(v[4]);
					String NumActCond = v[5].isEmpty() ? "*" : v[5];
					String NumLinea = v[6].isEmpty() ? "*" : v[6];
					String CantEscala = v[7];
					String ValorEscala = v[8];
					String Importe = v[9].isEmpty() ? "*" : v[9];
					sql = "insert into PROFFLINE_TB_CONDICION_ESCALAS values('"
							+ ClaseCond + "','" +TipoEscala + "','" + NroRegCond + "','" + NumActCond
							+ "','" + NumLinea + "','" + CantEscala + "','" + ValorEscala
							+ "','" + Importe + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);
				}
			}
			con.DesconectarSAP();
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public static String tetsOpenItems(String codigoCliente) throws Exception {
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND_T_ITEMS");
			con.CreaTabla("IS_SEARCH_OPTIONS");
			con.IngresarDatoTabla(codigoCliente, "CUST_ID", 1);
			con.IngresarDatoTabla("", "DOC_TYPE", 1);
			con.IngresarDatoTabla("X", "OPEN_ITEMS", 1);
			con.IngresarDatoTabla("", "CLEARED_ITEMS", 1);
			con.IngresarDatoTabla("", "ALL_ITEMS", 1);
			con.IngresarDatoTabla("", "DATE_FROM", 1);
			con.IngresarDatoTabla("20121212", "DATE_TO", 1);
			con.IngresarDatoTabla("", "WITH_EXPIRATION_DATE", 1);
			con.EjecutarRFC();
			con.CreaTabla("ET_OPEN_ITEM");
			List piSel = con.ObtenerDatosTabla();
			for (int i = 0; i < piSel.size(); i++) {
				String[] valores = String.valueOf(piSel.get(i)).split("[¬]");
			}
			con.CreaTabla("ET_OPEN_ITEM_LEAD");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			piSel = con.ObtenerDatosTabla();
			for (int i = 0; i < piSel.size(); i++) {
				String[] valores = String.valueOf(piSel.get(i)).split("[¬]");
			}
			con.DesconectarSAP();
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	public String testPISEL(String codigoVendedor) throws Exception {
		String codigoSupervisor = "";
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND_ITEMS");
			con.CreaTabla("IS_SEARCH_OPTIONS");
			con.IngresarDatoTabla(codigoVendedor, "CUST_ID", 1);
			con.IngresarDatoTabla("", "DOC_TYPE", 1);
			con.IngresarDatoTabla("X", "OPEN_ITEMS", 1);
			con.IngresarDatoTabla("", "CLEARED_ITEMS", 1);
			con.IngresarDatoTabla("", "ALL_ITEMS", 1);
			con.IngresarDatoTabla("", "DATE_FROM", 1);
			con.IngresarDatoTabla("20121127", "DATE_TO", 1);
			con.IngresarDatoTabla("", "WITH_EXPIRATION_DATE", 1);
			con.EjecutarRFC();
			con.CreaTabla("ET_OPEN_ITEM");
			List piSel = con.ObtenerDatosTabla();
			for (int i = 0; i < piSel.size(); i++) {
				String[] valores = String.valueOf(piSel.get(i)).split("[¬]");
				System.out.println(String.valueOf(piSel.get(i)));
				for (int j = 1; j < valores.length; j++) {
					System.out.println("Item " + j + " : " + valores[j].trim());
				}
			}
			con.DesconectarSAP();
		}
		return codigoSupervisor;
	}
}
