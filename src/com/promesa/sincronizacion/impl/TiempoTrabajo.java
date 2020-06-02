package com.promesa.sincronizacion.impl;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPopupMenu;
import com.promesa.sincronizacion.bean.BeanTiempoTrabajo;

public class TiempoTrabajo extends TimerTask {
	Timer temporizador = null;
	Date date = null;
	int f = 0;
	String tarea = null;
	String opciones;
	String codigoUsuario;
	@SuppressWarnings("unused")
	private JPopupMenu popupOpciones = null;
	@SuppressWarnings("unused")
	private boolean activaInicio;
	int horaRe;
	int segundoRe;
	int minutoRe;

	public TiempoTrabajo(BeanTiempoTrabajo beanTiempoTrabajo) {
		this.temporizador = beanTiempoTrabajo.getTemporizador();
		this.date = beanTiempoTrabajo.getDate();
		this.f = beanTiempoTrabajo.getFrecuenacia();
		this.tarea = beanTiempoTrabajo.getTarea();
		this.opciones = beanTiempoTrabajo.getOpciones();
		this.codigoUsuario = beanTiempoTrabajo.getCodigoUsuario();
		this.popupOpciones = beanTiempoTrabajo.getPopupOpciones();
		this.activaInicio = beanTiempoTrabajo.isActivaInicio();
	}

	public void run() {
	}

	@SuppressWarnings("deprecation")
	public Date getHorasMasFrecuencia(Date date, int f) {
		date.setHours(date.getHours() + f);
		return date;
	}

	@SuppressWarnings("deprecation")
	public int getSegundos(Date date) {
		return ((date.getHours() * 3600) + (date.getMinutes() * 60) + date.getSeconds());
	}

	@SuppressWarnings("deprecation")
	public void getCapturarHorasMinutos(Date dateBD, Date dateSi) {
		int horaBD = 0, horaSi = 0;
		int minutoBD = 0, minutoSi = 0;
		horaBD = dateBD.getHours();
		horaSi = dateSi.getHours();
		horaRe = horaSi - horaBD;
		minutoBD = dateBD.getMinutes();
		minutoSi = dateSi.getMinutes();
		segundoRe = dateSi.getSeconds();
		minutoRe = minutoSi - minutoBD;
	}

	@SuppressWarnings("deprecation")
	public Date getActualizaHorasMinutos(Date dateIn) {
		dateIn.setHours(dateIn.getHours() + horaRe);
		dateIn.setMinutes(dateIn.getMinutes() + minutoRe);
		dateIn.setSeconds(dateIn.getSeconds() + segundoRe);
		return dateIn;
	}

	@SuppressWarnings("deprecation")
	public int getDia(Date date) {
		return date.getDay();
	}

	@SuppressWarnings("deprecation")
	public Date getHorasMenosFrecuencia(Date date, int f) {
		date.setHours(date.getHours() - f);
		return date;
	}
}