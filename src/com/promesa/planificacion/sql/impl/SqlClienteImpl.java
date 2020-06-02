package com.promesa.planificacion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.Presupuesto;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlClienteImpl implements SqlCliente {

	private String sqlCliente = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("unused")
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanCliente cliente = null;
	private List<BeanCliente> listaCliente = null;
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaCliente(BeanCliente cliente) {
		column = new HashMap();
		listaCliente = new ArrayList<BeanCliente>();
		column.put("String:0", ID_CLIENTE);
		column.put("String:1", NOMBRE_CLIENTE);
		column.put("String:2", TELEFONO_TRABAJO);
		column.put("String:3", DIRECCION_CLIENTE);
		column.put("String:4", FAX_CLIENTE);
		column.put("String:5", TXTCODORGVENTAS);
		column.put("String:6", TXTCODCANALDIST);
		column.put("String:7", TXTCODSECTOR);
		sqlCliente = " SELECT txtIdCliente, txtNombreCliente, txtDireccionCliente, txtTelefonoTrabajoCliente, txtNumeroFaxCliente, "
				+ " txtCodOrgVentas,txtCodCanalDist,txtCodSector " + " FROM PROFFLINE_TB_CLIENTE";
		sqlCliente = sqlCliente + " ORDER BY " + COMPANIA_CLIENTE;
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.cliente = new BeanCliente();
			res = (HashMap) mapResultado.get(i);
			this.cliente.setStrIdCliente(getData(res.get(ID_CLIENTE)));
			this.cliente.setStrNombreCliente(getData(res.get(NOMBRE_CLIENTE)));
			this.cliente.setStrDireccionCliente(getData(res.get(DIRECCION_CLIENTE)));
			this.cliente.setStrTelefonoTrabajoCliente(getData(res.get(TELEFONO_TRABAJO)));
			this.cliente.setStrNumeroFaxCliente(getData(res.get(FAX_CLIENTE)));
			this.cliente.setStrCodOrgVentas(getData(res.get(TXTCODORGVENTAS)));
			this.cliente.setStrCodCanalDist(getData(res.get(TXTCODCANALDIST)));
			this.cliente.setStrCodSector(getData(res.get(TXTCODSECTOR)));
			listaCliente.add(this.cliente);
		}
	}

	/* MÉTODO QUE LISTA CLIENTES POR VENDEDOR */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void clientesXvendedor(String vendedor) {
		column = new HashMap();
		listaCliente = new ArrayList<BeanCliente>();
		column.put("String:0", ID_CLIENTE);
		column.put("String:1", NOMBRE_CLIENTE);
		column.put("String:2", TELEFONO_TRABAJO);
		column.put("String:3", DIRECCION_CLIENTE);
		column.put("String:4", FAX_CLIENTE);
		sqlCliente = "SELECT " + "TC." + ID_CLIENTE + "," + "TC." + NOMBRE_CLIENTE + "," + "TC." + TELEFONO_TRABAJO + "," + "TC."
				+ DIRECCION_CLIENTE + "," + "TC." + FAX_CLIENTE + " FROM " + TABLA_CLIENTE + " TC," + TABLA_EMPLEADO_CLIENTE + " TEC"
				+ " WHERE " + "TEC." + ID_CLIENTE + "=" + "TC." + ID_CLIENTE + " AND " + "TEC." + ID_EMPLEADO + "='" + vendedor + "' order by 2 asc";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.cliente = new BeanCliente();
			res = (HashMap) mapResultado.get(i);
			this.cliente.setStrIdCliente(getData(res.get(ID_CLIENTE)));
			this.cliente.setStrNombreCliente(getData(res.get(NOMBRE_CLIENTE)));
			this.cliente.setStrDireccionCliente(getData(res.get(DIRECCION_CLIENTE)));
			this.cliente.setStrTelefonoTrabajoCliente(getData(res.get(TELEFONO_TRABAJO)));
			this.cliente.setStrNumeroFaxCliente(getData(res.get(FAX_CLIENTE)));
			listaCliente.add(this.cliente);
		}
	}
	
	/* MÉTODO QUE LISTA CLIENTES POR VENDEDOR */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void clienteXvendedor(String vendedor, String cliente) {
		column = new HashMap();
		listaCliente = new ArrayList<BeanCliente>();
		column.put("String:0", ID_CLIENTE);
		column.put("String:1", NOMBRE_CLIENTE);
		column.put("String:2", TELEFONO_TRABAJO);
		column.put("String:3", DIRECCION_CLIENTE);
		column.put("String:4", FAX_CLIENTE);
		sqlCliente = "SELECT " + "CL." + ID_CLIENTE + "," + "CL." + NOMBRE_CLIENTE + "," + "CL." + TELEFONO_TRABAJO + "," + "CL."
				+ DIRECCION_CLIENTE + "," + "CL." + FAX_CLIENTE + " FROM " + TABLA_CLIENTE + " CL INNER JOIN " + TABLA_EMPLEADO_CLIENTE + " TC  on (TC.txtIdCliente = CL.txtIdCliente)"
				+ " WHERE " + "TC." + ID_EMPLEADO + "='" + vendedor +  "'  AND CL.txtIdCliente" + "='" + cliente + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.cliente = new BeanCliente();
			res = (HashMap) mapResultado.get(i);
			this.cliente.setStrIdCliente(getData(res.get(ID_CLIENTE)));
			this.cliente.setStrNombreCliente(getData(res.get(NOMBRE_CLIENTE)));
			this.cliente.setStrDireccionCliente(getData(res.get(DIRECCION_CLIENTE)));
			this.cliente.setStrTelefonoTrabajoCliente(getData(res.get(TELEFONO_TRABAJO)));
			this.cliente.setStrNumeroFaxCliente(getData(res.get(FAX_CLIENTE)));
			listaCliente.add(this.cliente);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaCliente() {
		BeanCliente cliente;
		column = new HashMap();
		listaCliente = new ArrayList<BeanCliente>();
		column.put("String:0", ID_CLIENTE);
		column.put("String:1", NOMBRE_CLIENTE);
		sqlCliente = "SELECT " + ID_CLIENTE + "," + NOMBRE_CLIENTE + " FROM " + TABLA_CLIENTE;
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		for (HashMap res : mapResultado.values()) {
			cliente = new BeanCliente();
			cliente.setStrIdCliente(getData(res.get(ID_CLIENTE)));
			cliente.setStrNombreCliente(getData(res.get(NOMBRE_CLIENTE)));
			listaCliente.add(cliente);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanCliente> listarClientes() {
		List<BeanCliente> listaClientes = new ArrayList<BeanCliente>();
		BeanCliente cliente;
		column = new HashMap();
		listaCliente = new ArrayList<BeanCliente>();
		column.put("String:0", ID_CLIENTE);
		column.put("String:1", NOMBRE_CLIENTE);
		sqlCliente = "SELECT " + ID_CLIENTE + "," + NOMBRE_CLIENTE + " FROM " + TABLA_CLIENTE;
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		for (HashMap res : mapResultado.values()) {
			cliente = new BeanCliente();
			cliente.setStrIdCliente(getData(res.get(ID_CLIENTE)));
			cliente.setStrNombreCliente(getData(res.get(NOMBRE_CLIENTE)));
			listaClientes.add(cliente);
		}
		return listaClientes;
	}

	public List<BeanCliente> getListaCliente() {
		return listaCliente;
	}

	public void setInsertarCliente(BeanCliente cliente) {
		sqlCliente = "INSERT INTO " + TABLA_CLIENTE + " (" + ID_CLIENTE + ","
				+ COMPANIA_CLIENTE + "," + NOMBRE_CLIENTE + ","
				+ APELLIDO_CLIENTE + "," + EMAIL_CLIENTE + ","
				+ TELEFONO_CLIENTE + "," + TELEFONO_TRABAJO + ","
				+ TELEFONO_MOVIL + "," + FAX_CLIENTE + "," + DIRECCION_CLIENTE
				+ "," + CIUDAD_CLIENTE + "," + ESTADO_PROVINCIA_CLIENTE + ","
				+ CODIGO_POSTAL_CLIENTE + ") " + " VALUES ('"
				+ cliente.getStrIdCliente().replaceAll("'", "''") + "','"
				+ cliente.getStrCompaniaCliente().replaceAll("'", "''") + "','"
				+ cliente.getStrNombreCliente().replaceAll("'", "''") + "','"
				+ cliente.getStrApellidosCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrEmailCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrTelefonoPrivadoCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrTelefonoTrabajoCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrTelefonoMovilCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrNumeroFaxCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrDireccionCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrCiudadCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrEstadoProvinciaCliente().replaceAll("'", "''")
				+ "','" + cliente.getStrCodigoPostalCliente().replaceAll("'", "''") + "')";
		ResultExecute resultExecute = new ResultExecute(sqlCliente, "Clientes", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	/* MÉTODO QUE VERIFICA SI EXISTE O NO UN REGISTRO DE CLIENTE */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verificaCliente(String codCli) {
		boolean resultado = false;
		column = new HashMap();
		String codigo = Constante.VACIO;
		column.put("String:0", ID_CLIENTE);
		sqlCliente = "SELECT " + ID_CLIENTE + " FROM " + TABLA_CLIENTE + " WHERE " + ID_CLIENTE + " = '" + codCli + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			codigo = res.get(ID_CLIENTE).toString();
		}
		if (!codigo.equals(Constante.VACIO)) {
			resultado = true;
		}
		return resultado;
	}

	public void setInsertarCliente(List<BeanCliente> listaC) {
		List<String> listaCliente = new ArrayList<String>();
		for (BeanCliente cliente : listaC) {
			sqlCliente = "INSERT INTO " + TABLA_CLIENTE + " (" + ID_CLIENTE + "," + COMPANIA_CLIENTE + "," + NOMBRE_CLIENTE
					+ "," + APELLIDO_CLIENTE + "," + EMAIL_CLIENTE + "," + TELEFONO_CLIENTE + "," + TELEFONO_TRABAJO
					+ "," + TELEFONO_MOVIL + "," + FAX_CLIENTE + "," + DIRECCION_CLIENTE + "," + CIUDAD_CLIENTE 
					+ "," + ESTADO_PROVINCIA_CLIENTE + "," + CODIGO_POSTAL_CLIENTE + "," + MARCA_BLOQUEO_ALMACEN
					+ ", txtCodOrgVentas, txtCodCanalDist, txtCodSector, txtOficinaVentas, txtGrupoVentas, txtIndicadorImpuesto, txtCodigoTipologia, txtDescripcionTipologia, txtClase)"
					+ " VALUES ('" + cliente.getStrIdCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrCompaniaCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrNombreCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrApellidosCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrCondicionPagoDefecto().replaceAll("'", "''")
					+ "','" + cliente.getStrTelefonoPrivadoCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrTelefonoTrabajoCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrTelefonoMovilCliente() .replaceAll("'", "''")
					+ "','" + cliente.getStrNumeroFaxCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrDireccionCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrCiudadCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrEstadoProvinciaCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrCodigoPostalCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrMarcaBloqueoAlmacen().replaceAll("'", "''")
					+ "','" + cliente.getStrCodOrgVentas().replaceAll("'", "''")
					+ "','" + cliente.getStrCodCanalDist().replaceAll("'", "''")
					+ "','" + cliente.getStrCodSector().replaceAll("'", "''")
					+ "','" + cliente.getStrOficinaVentas().replaceAll("'", "''")
					+ "','" + cliente.getStrGrupoVentas().replaceAll("'", "''")
					+ "','" + cliente.getIndicadorIva().replaceAll("'", "''")
					+ "','" + cliente.getStrCodigoTipologia().replaceAll("'", "''")
					+ "','" + cliente.getStrDescripcionTipologia().replaceAll("'", "''")
					+ "','" + cliente.getStrClase().replaceAll("'", "''")+ "')";
			listaCliente.add(sqlCliente);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaCliente, "Clientes", Constante.BD_SYNC);
	}

	public void setInsertarCliente2(String strCodVendedor, List<BeanCliente> listaC) {
		List<String> listaCliente = new ArrayList<String>();
		String sqlEmpCli = "";
		for (BeanCliente cliente : listaC) {
			sqlCliente = "INSERT INTO " + TABLA_CLIENTE + " (" + ID_CLIENTE + "," + COMPANIA_CLIENTE + "," + NOMBRE_CLIENTE
					+ "," + APELLIDO_CLIENTE + "," + EMAIL_CLIENTE + "," + TELEFONO_CLIENTE
					+ "," + TELEFONO_TRABAJO + "," + TELEFONO_MOVIL + "," + FAX_CLIENTE + "," + DIRECCION_CLIENTE
					+ "," + CIUDAD_CLIENTE + "," + ESTADO_PROVINCIA_CLIENTE + "," + CODIGO_POSTAL_CLIENTE + "," + MARCA_BLOQUEO_ALMACEN
					+ "," + CODIGO_TIPOLOGIA + "," + DESCRIPCION_TIPOLOGIA + "," + TXTCODORGVENTAS + "," + TXTCODCANALDIST
					+ "," + TXTCODSECTOR + "," + INDICADOR_IMPUESTO + "," + OFICINA_VENTAS + "," + GRUPO_VENTAS
					+ ") " + " VALUES ('" + cliente.getStrIdCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrCompaniaCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrNombreCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrApellidosCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrEmailCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrTelefonoPrivadoCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrTelefonoTrabajoCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrTelefonoMovilCliente() .replaceAll("'", "''")
					+ "','" + cliente.getStrNumeroFaxCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrDireccionCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrCiudadCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrEstadoProvinciaCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrCodigoPostalCliente().replaceAll("'", "''")
					+ "','" + cliente.getStrMarcaBloqueoAlmacen().replaceAll("'", "''")
					+ "','" + cliente.getStrCodigoTipologia().replaceAll("'", "''")
					+ "','" + cliente.getStrDescripcionTipologia() .replaceAll("'", "''") 
					+ "','" + cliente.getStrCodOrgVentas().replaceAll("'", "''")
					+ "','" + cliente.getStrCodCanalDist().replaceAll("'", "''")
					+ "','" + cliente.getStrCodSector().replaceAll("'", "''")
					+ "','" + cliente.getIndicadorIva().replaceAll("'", "''")
					+ "','" + cliente.getStrOficinaVentas().replaceAll("'", "''")
					+ "','" + cliente.getStrGrupoVentas().replaceAll("'", "''") + "')";
			listaCliente.add(sqlCliente);
			sqlEmpCli = " insert into " + TABLA_EMPLEADO_CLIENTE + " values ('" + strCodVendedor + "','" + cliente.getStrIdCliente() + "')";
			listaCliente.add(sqlEmpCli);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaCliente, "Clientes", Constante.BD_SYNC);
	}

	public void setInsertarClienteM2(List<String> listaCliente) {
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaCliente, "Clientes", Constante.BD_SYNC);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlCliente = " SELECT COUNT(*) AS filas FROM " + TABLA_CLIENTE;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	public boolean getInsertarCliente() {
		return resultado;
	}

	public void setEliminarCliente() {
		sqlCliente = "DELETE FROM " + TABLA_CLIENTE;
		ResultExecute resultExecute = new ResultExecute(sqlCliente, "Clientes", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setEliminarCliente(String idVendedor) {
		List<String> listaCliente = new ArrayList<String>();
		column = new HashMap();
		column.put("String:0", "txtIdCliente");
		sqlCliente = "SELECT C.txtIdCliente FROM PROFFLINE_TB_CLIENTE C " + "INNER JOIN PROFFLINE_TB_EMPLEADO_CLIENTE EC "
				+ "ON CAST(EC.txtIdCliente AS NUMERIC)=C.txtIdCliente " + "WHERE txtIdEmpleado='" + idVendedor.trim() + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		String strCli = "";
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			strCli = String.valueOf(res.get("txtIdCliente"));
			sqlCliente = " DELETE FROM PROFFLINE_TB_CLIENTE WHERE txtIdCliente='" + strCli + "'";
			listaCliente.add(sqlCliente);
			sqlCliente = " DELETE FROM PROFFLINE_TB_EMPLEADO_CLIENTE WHERE " + " txtIdEmpleado='" + idVendedor + "' " + " and txtIdCliente='" + strCli + "' ";
			listaCliente.add(sqlCliente);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaCliente, "Clientes", Constante.BD_SYNC);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setEliminarClientePorVendedor(String idVendedor) {
		List<String> listaCliente = new ArrayList<String>();
		column = new HashMap();
		column.put("String:0", "txtIdCliente");
		sqlCliente = "SELECT C.txtIdCliente FROM PROFFLINE_TB_CLIENTE C " + "INNER JOIN PROFFLINE_TB_EMPLEADO_CLIENTE EC "
				+ "ON CAST(EC.txtIdCliente AS NUMERIC)=C.txtIdCliente " + "WHERE txtIdEmpleado='" + idVendedor.trim() + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			sqlCliente = "DELETE FROM PROFFLINE_TB_CLIENTE WHERE txtIdCliente='" + res.get("txtIdCliente") + "'";
			listaCliente.add(sqlCliente);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaCliente, "Clientes", Constante.BD_SYNC);
	}

	public boolean getEliminarCliente() {
		return resultado;
	}

	public String getData(Object obj) {
		String result = "";
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerMarcaBloqueoAlmacen(String idCliente) {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "txtMarcaBloqueoAlmacen");
		int f = 0;
		sqlCliente = " SELECT txtMarcaBloqueoAlmacen FROM " + TABLA_CLIENTE + " WHERE txtIdCliente='" + idCliente + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (mapResultado.size() > 0) {
				res = (HashMap) mapResultado.get(0);
				String marca = ("" + res.get("txtMarcaBloqueoAlmacen"));
				try{					
					f = Integer.parseInt(marca.toString().trim());
				} catch (NumberFormatException numberFormatEx) {
					
				}
				
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return "" + f;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BeanCliente obtenerCliente(String idCliente) {
		ResultExecuteQuery resultExecuteQuery = null;
		BeanCliente cliente = new BeanCliente();
		column = new HashMap();
		column.put("String:0", "txtNombreCliente");
		column.put("String:1", "txtDireccionCliente");
		column.put("String:2", "txtTelefonoTrabajoCliente");
		column.put("String:3", "txtCiudadCliente");
		sqlCliente = "select txtNombreCliente, txtDireccionCliente, txtTelefonoTrabajoCliente,txtCiudadCliente from proffline_tb_cliente where txtIdCliente='"
				+ idCliente + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (mapResultado.size() > 0) {
				res = (HashMap) mapResultado.get(0);
				String nombre = ("" + res.get("txtNombreCliente"));
				String direccion = ("" + res.get("txtDireccionCliente"));
				String telefono = ("" + res.get("txtTelefonoTrabajoCliente"));
				String ciudad = ("" + res.get("txtCiudadCliente"));
				cliente.setStrNombreCliente(nombre);
				cliente.setStrDireccionCliente(direccion);
				cliente.setStrTelefonoTrabajoCliente(telefono);
				cliente.setStrCiudadCliente(ciudad);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return cliente;
	}

	public void setInsertarEmpleadoClientes(String idSup, List<BeanCliente> clientes) {
		List<String> listaConsultas = new ArrayList<String>();
		sqlCliente = "DELETE FROM PROFFLINE_TB_EMPLEADO_CLIENTE;";
		listaConsultas.add(sqlCliente);
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaConsultas, "Clientes", Constante.BD_SYNC);
		listaConsultas = new ArrayList<String>();
		for (BeanCliente cliente : clientes) {
			sqlCliente = "INSERT INTO PROFFLINE_TB_EMPLEADO_CLIENTE(txtIdEmpleado,txtidCliente) values ('"
					+ idSup.replaceAll("'", "''") + "','" + cliente.getStrIdCliente().replaceAll("'", "''") + "');";
			listaConsultas.add(sqlCliente);
		}
		resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaConsultas, "Empleado-Cliente", Constante.BD_SYNC);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerCondicionPagoPorDefecto(String codigo) {
		String condicionPagoPorDefecto = "";
		column = new HashMap();
		column.put("String:0", "txtEmailCliente");
		sqlCliente = "select txtEmailCliente from proffline_tb_cliente where txtIdCliente='" + codigo + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		if (mapResultado.size() > 0) {
			mapResultado = (HashMap) mapResultado.get(0);
			condicionPagoPorDefecto = ("" + mapResultado.get("txtEmailCliente"));
		}
		return condicionPagoPorDefecto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BeanCliente buscarCliente(String codigo) {
		BeanCliente c = new BeanCliente();
		column = new HashMap();
		listaCliente = new ArrayList<BeanCliente>();
		column.put("String:0", ID_CLIENTE);
		column.put("String:1", NOMBRE_CLIENTE);
		column.put("String:2", TELEFONO_TRABAJO);
		column.put("String:3", DIRECCION_CLIENTE);
		column.put("String:4", TELEFONO_TRABAJO);
		column.put("String:5", FAX_CLIENTE);
		column.put("String:6", "txtCodigoTipologia");
		column.put("String:7", "txtDescripcionTipologia");
		column.put("String:8", TXTCODORGVENTAS);
		column.put("String:9", TXTCODCANALDIST);
		column.put("String:10", TXTCODSECTOR);
		column.put("String:11", OFICINA_VENTAS);
		column.put("String:12", GRUPO_VENTAS);
		column.put("String:13","txtClase");
		sqlCliente = "SELECT txtIdCliente, txtNombreCliente, txtDireccionCliente, txtTelefonoTrabajoCliente, "
				+ "txtNumeroFaxCliente, txtCodigoTipologia, txtDescripcionTipologia, "
				+ "txtCodOrgVentas,txtCodCanalDist,txtCodSector, txtOficinaVentas, txtGrupoVentas, txtClase FROM PROFFLINE_TB_CLIENTE where txtIdCliente='"
				+ codigo + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.cliente = new BeanCliente();
			res = (HashMap) mapResultado.get(i);
			c.setStrIdCliente(getData(res.get(ID_CLIENTE)));
			c.setStrNombreCliente(getData(res.get(NOMBRE_CLIENTE)));
			c.setStrDireccionCliente(getData(res.get(DIRECCION_CLIENTE)));
			c.setStrTelefonoTrabajoCliente(getData(res.get(TELEFONO_TRABAJO)));
			c.setStrNumeroFaxCliente(getData(res.get(FAX_CLIENTE)));
			c.setStrCodigoTipologia(getData(res.get("txtCodigoTipologia")));
			c.setStrDescripcionTipologia(getData(res.get("txtDescripcionTipologia")));
			c.setStrCodOrgVentas(getData(res.get(TXTCODORGVENTAS)));
			c.setStrCodCanalDist(getData(res.get(TXTCODCANALDIST)));
			c.setStrCodSector(getData(res.get(TXTCODSECTOR)));
			c.setStrOficinaVentas(getData(res.get(OFICINA_VENTAS)));
			c.setStrGrupoVentas(getData(res.get(GRUPO_VENTAS)));
			c.setStrCodOrgVentas(getData(res.get("txtCodOrgVentas")));
			c.setStrCodCanalDist(getData(res.get("txtCodCanalDist")));
			c.setStrCodSector(getData(res.get("txtCodSector")));
			c.setStrClase(getData(res.get("txtClase")));
		}
		return c;
	}
	
	public void insertPresupuesto(List<Presupuesto> presupuestos){
        try {
            List<String> queries = new ArrayList<String>();
            String sqlCliente = "DELETE FROM PROFFLINE_TB_PRESUPUESTO;";
            queries.add(sqlCliente);
            for (Presupuesto pre : presupuestos) {
            	
            	
            	String sql = "INSERT INTO PROFFLINE_TB_PRESUPUESTO VALUES ('" + pre.getCodCliente()
				                +"','" + pre.getPresupuestoReal()
				                +"','" + pre.getPresupuestoAnual()
				                +"','" + pre.getPresupuestoMensual()
				                +"','" + pre.getPresupuestoFecha()
				                +"','" + pre.getVentaAnual()
				                +"','" + pre.getVentaMensual() 
				                +"','" + pre.getVentaAcumAnioAnt()
				                +"','" + pre.getFechaRegistro() 
				                +"','" + pre.getPromoPlus() 
				                +"','" + pre.getVta_gracia()+"')";
                queries.add(sql);
            }
            
            ResultExecuteList resultExecuteList = new ResultExecuteList();
    		resultExecuteList.insertarListaConsultas(queries, "PRESUPUESTO_CLIENTE", Constante.BD_SYNC);
        } catch (Exception e) {
        	Util.mostrarExcepcion(e);
        }
    }
	
	
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Presupuesto getPresupestoByCliente(String codigoCliente) {
		column.put("String:0", "codigoCliente");
		column.put("String:1", "presupuesto_real");
		column.put("String:2", "presupuesto_anual");
		column.put("String:3", "presupuesto_mensual");
		column.put("String:4", "presupuesto_fecha");
		column.put("String:5", "venta_anual");
		column.put("String:6", "venta_mensual");
		column.put("String:7", "acum_anio_ant");
		column.put("String:8", "fecha_registro");
		column.put("String:9", "promo_plus");
		column.put("String:10", "vta_gracia");
		String sql = "SELECT * FROM PROFFLINE_TB_PRESUPUESTO WHERE codigoCliente = '"+codigoCliente+"';";  
		
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		
		Presupuesto pre = null;
		
		for (int i = 0; i < mapResultado.size(); i++) {
			this.cliente = new BeanCliente();
			res = (HashMap) mapResultado.get(i);
			pre = new Presupuesto();
			pre.setCodCliente(res.get("codigoCliente").toString());
			pre.setPresupuestoReal(res.get("presupuesto_real").toString());
			pre.setPresupuestoAnual(res.get("presupuesto_anual").toString());
			pre.setPresupuestoMensual(res.get("presupuesto_mensual").toString());
			pre.setPresupuestoFecha(res.get("presupuesto_fecha").toString());
			pre.setVentaAnual(res.get("venta_anual").toString());
			pre.setVentaMensual(res.get("venta_mensual").toString());
			pre.setVentaAcumAnioAnt(res.get("acum_anio_ant").toString());
			pre.setFechaRegistro(res.get("fecha_registro").toString());
			pre.setPromoPlus(res.get("promo_plus").toString());
			pre.setVta_gracia(res.get("vta_gracia").toString());
		}
		
		return pre;
	}
	
	@Override
	public void updatePresupuesto(Presupuesto pre) {
		String sql = "UPDATE PROFFLINE_TB_PRESUPUESTO SET venta_mensual= '"
					 +pre.getVentaMensual()+"' where codigoCliente='"
					 + pre.getCodCliente()+"';";
		
		ResultExecute resultExecute = new ResultExecute(sql, "Presupuesto", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}



	private static String TABLA_EMPLEADO_CLIENTE = "PROFFLINE_TB_EMPLEADO_CLIENTE";
	private static String ID_EMPLEADO = "txtIdEmpleado";
	private static String TABLA_CLIENTE = "PROFFLINE_TB_CLIENTE";
	private static String ID_CLIENTE = "txtIdCliente";
	private static String COMPANIA_CLIENTE = "txtCompaniaCliente";
	private static String NOMBRE_CLIENTE = "txtNombreCliente";
	private static String APELLIDO_CLIENTE = "txtApellidosCliente";
	private static String EMAIL_CLIENTE = "txtEmailCliente";
	private static String TELEFONO_CLIENTE = "txtTelefonoPrivadoCliente";
	private static String TELEFONO_TRABAJO = "txtTelefonoTrabajoCliente";
	private static String TELEFONO_MOVIL = "txtTelefonoMovilCliente";
	private static String FAX_CLIENTE = "txtNumeroFaxCliente";
	private static String DIRECCION_CLIENTE = "txtDireccionCliente";
	private static String CIUDAD_CLIENTE = "txtCiudadCliente";
	private static String ESTADO_PROVINCIA_CLIENTE = "txtEstadoProvinciaCliente";
	private static String CODIGO_POSTAL_CLIENTE = "txtCodigoPostalCliente";
	private static String MARCA_BLOQUEO_ALMACEN = "txtMarcaBloqueoAlmacen";
	private static String CODIGO_TIPOLOGIA = "txtCodigoTipologia";
	private static String DESCRIPCION_TIPOLOGIA = "txtDescripcionTipologia";
	private static String TXTCODORGVENTAS = "txtCodOrgVentas";
	private static String TXTCODCANALDIST = "txtCodCanalDist";
	private static String TXTCODSECTOR = "txtCodSector";
	private static String INDICADOR_IMPUESTO = "txtIndicadorImpuesto";
	private static String OFICINA_VENTAS = "txtOficinaVentas";
	private static String GRUPO_VENTAS = "txtGrupoVentas";
}