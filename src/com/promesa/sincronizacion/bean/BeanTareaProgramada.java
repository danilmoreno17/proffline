package com.promesa.sincronizacion.bean;

import java.util.List;
import java.util.Timer;

import javax.swing.JPopupMenu;

import com.promesa.bean.BeanDato;

public class BeanTareaProgramada {
	 private Timer []temporizador;	
	 private List<BeanDato> datose;
	 private String codigoUsuario;
	 private JPopupMenu popupOpciones;
	
	 public String getCodigoUsuario() {
		return codigoUsuario;
	}
	
	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	
	public Timer[] getTemporizador() {
		return temporizador;
	 }
	
	 public void setTemporizador(Timer[] temporizador) {
		this.temporizador = temporizador;
	 }
	
	 public List<BeanDato> getDatose() {
		return datose;
	 }
	
	 public void setDatose(List<BeanDato> datose) {
		this.datose = datose;
	 }
	 
	 public JPopupMenu getPopupOpciones() {
		return popupOpciones;
	 }
	
	 public void setPopupOpciones(JPopupMenu popupOpciones) {
		this.popupOpciones = popupOpciones;
	 }
}