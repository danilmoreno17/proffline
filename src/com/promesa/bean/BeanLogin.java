package com.promesa.bean;

import java.util.List;

public class BeanLogin {
	private String strAcceso;
	private String strCambioClave;	
	private String strUsuario;
	private String strCodigo;
	private String strEstado;
	private String strRol;
	private List <String> menu;
	private List <String> msg;	
	
	public String getStrAcceso() {
		return strAcceso;
	}
	public void setStrAcceso(String strAcceso) {
		this.strAcceso = strAcceso;
	}
	public String getStrCambioClave() {
		return strCambioClave;
	}
	public void setStrCambioClave(String strCambioClave) {
		this.strCambioClave = strCambioClave;
	}	
	public String getStrUsuario() {
		return strUsuario;
	}
	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}
	public String getStrCodigo() {
		return strCodigo;
	}
	public void setStrCodigo(String strCodigo) {
		this.strCodigo = strCodigo;
	}
	public String getStrEstado() {
		return strEstado;
	}
	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
	public String getStrRol() {
		return strRol;
	}
	public void setStrRol(String strRol) {
		this.strRol = strRol;
	}
	public List<String> getMenu() {
		return menu;
	}
	public void setMenu(List<String> menu) {
		this.menu = menu;
	}
	public List<String> getMsg() {
		return msg;
	}
	public void setMsg(List<String> msg) {
		this.msg = msg;
	}
}