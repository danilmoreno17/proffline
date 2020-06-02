package com.promesa.pedidos.bean;

public class BeanJerarquia {

	private String strIdPadre;
	private String strPRODH;
	private String strS;
	private String strVTEXT;
	private String strZZSEQ;
	private String strICON;
	private String strI;
	private String strCellDesign;

	public String getStrIdPadre() {
		return strIdPadre;
	}

	public void setStrIdPadre(String strIdPadre) {
		this.strIdPadre = strIdPadre;
	}

	public String getStrPRODH() {
		return strPRODH;
	}

	public void setStrPRODH(String strPRODH) {
		this.strPRODH = strPRODH;
	}

	public String getStrS() {
		return strS;
	}

	public void setStrS(String strS) {
		this.strS = strS;
	}

	public String getStrVTEXT() {
		return strVTEXT;
	}

	public void setStrVTEXT(String strVTEXT) {
		this.strVTEXT = strVTEXT;
	}

	public String getStrZZSEQ() {
		return strZZSEQ;
	}

	public void setStrZZSEQ(String strZZSEQ) {
		this.strZZSEQ = strZZSEQ;
	}

	public String getStrICON() {
		return strICON;
	}

	public void setStrICON(String strICON) {
		this.strICON = strICON;
	}

	public String getStrI() {
		return strI;
	}

	public void setStrI(String strI) {
		this.strI = strI;
	}

	public String getStrCellDesign() {
		return strCellDesign;
	}

	public void setStrCellDesign(String strCellDesign) {
		this.strCellDesign = strCellDesign;
	}

	@Override
	public String toString() {
		if (strVTEXT == null || strVTEXT.trim().isEmpty()) {
			return strPRODH;
		}
		if (strPRODH == null || strPRODH.trim().isEmpty()) {
			return strVTEXT;
		}
		return strPRODH + "-" + strVTEXT;
	}

}
