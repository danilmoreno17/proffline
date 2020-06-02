package com.promesa.pedidos.sql;
import java.util.List;


import com.promesa.pedidos.bean.MarcaVendedor;
public interface SqlMarcaVendedor {
	public void insertMarcaVendedor(List<MarcaVendedor> marcas);
	
	public List<MarcaVendedor> getMarcaVendedor();
	
	public void actualizarMarcaVendedor(List<MarcaVendedor> marcas);
	
	public List<MarcaVendedor> getMarcaVendedorByCliente(String cliente);
	
	public boolean perteneceAMarcaVendedor(String cliente, String marca);
}
