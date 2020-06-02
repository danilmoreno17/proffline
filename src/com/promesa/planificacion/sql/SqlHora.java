package com.promesa.planificacion.sql;

import java.util.Date;
import java.util.List;

import com.promesa.planificacion.bean.BeanHora;

public interface SqlHora {
	 public void setListaHora(BeanHora hora);
	 public void setListaHoraEvento(BeanHora hora, Date fecha);
	 public List<BeanHora> getListaHora();
	 public List<BeanHora> getListaHoraEvento();
}
