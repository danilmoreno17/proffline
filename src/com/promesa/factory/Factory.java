package com.promesa.factory;

import com.promesa.pedidos.sql.SqlIndicador;
import com.promesa.pedidos.sql.SqlMarcaEstrategica;
import com.promesa.pedidos.sql.SqlMarcaVendedor;
import com.promesa.pedidos.sql.SqlMaterial;
import com.promesa.pedidos.sql.SqlSecuencialPedido;
import com.promesa.pedidos.sql.impl.SqlIndicadorImpl;
import com.promesa.pedidos.sql.impl.SqlMarcaEstrategicaImpl;
import com.promesa.pedidos.sql.impl.SqlMarcaVendedorImpl;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.pedidos.sql.impl.SqlSecuencialPedidoImpl;

public class Factory {
	
	public static SqlSecuencialPedido createSqlSecuenciaPedido(){
		return SqlSecuencialPedidoImpl.create();
	}
	
	public static SqlMarcaEstrategica createSqlMarcaEstrategica(){
		return SqlMarcaEstrategicaImpl.create();
	}
	
	public static SqlIndicador createSqlIndicador(){
		return SqlIndicadorImpl.create();
	}
	
	public static SqlMaterial createSqlMaterial(){
		return SqlMaterialImpl.create();
	}
	
	public static SqlMarcaVendedor createSqlMarcaVendedor() {
		return SqlMarcaVendedorImpl.create();
	}
}
