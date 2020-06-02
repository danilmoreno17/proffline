package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.bean.NombreMarcaEstrategica;

public interface SqlMarcaEstrategica {
	
	public void insertMarcaEstrategica(List<MarcaEstrategica> marcas);
	
	public List<MarcaEstrategica> getMarcaEstrategicaByCliente(String cliente);
	
	public boolean perteneceAMarcaEstrategica(String cliente, String marca);
	
	public void inserNombreMarcaEstrategica(List<NombreMarcaEstrategica> nombresMs);
	
	public String getNombreMarcaEstrategicaByMarca(String marca);
	
	public void actualizarMarcaEstrategica(List<MarcaEstrategica> marcas);

}
