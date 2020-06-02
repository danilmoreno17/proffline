package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.pedidos.bean.Indicador;

public interface SqlIndicador {
	
	public void insertIndicador(List<Indicador> indicadores);
	
	public Indicador getIndicacorByCliente(String codigoCliente);

}
