package com.promesa.pedidos.sql;

import java.util.List;
import com.promesa.pedidos.bean.BeanAgenda;

public interface SqlAgenda { 
	public void setListaAgenda(BeanAgenda agenda);
	public void getAgendaByQuery(String query);
	public void listaAgendaSAP(BeanAgenda agenda); // Método que obtiene registros en estado "111" 
	public void setInsertaAgenda(List<BeanAgenda> listaA);
	public boolean isInsertarAgenda();
	public void setEliminarAgenda(BeanAgenda ag);
	public boolean isEliminarAgenda();
	public List<BeanAgenda> getListaAgenda();	
	public int getCountRow();
	public int obtenerMaxVisitas();
}