package com.promesa.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


@SuppressWarnings("serial")
public class ValidadorEntradasDeTexto extends PlainDocument {

	private int maxChars;
	private int tipo;

	public ValidadorEntradasDeTexto(int maxChars, int tipo) {
		super();
		this.maxChars = maxChars;
		this.tipo = tipo;
	}

	public int getMaxChars() {
		return maxChars;
	}

	public void setMaxChars(int maxChars) {
		this.maxChars = maxChars;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if (str != null && (getLength() + str.length() < maxChars)) {
			if (tipo == Constante.ENTEROS && !Character.isDigit(str.charAt(str.length() - 1))) {
				// Permite solo números
				return;
			} else if (tipo == Constante.CADENAS && !Character.isDigit(str.charAt(str.length() - 1)) && !Character.isLetter(str.charAt(str.length() - 1))) {
				// Permite letras y números
				return;
			} else if (tipo == Constante.DECIMALES && !Character.isDigit(str.charAt(str.length() - 1)) && str.charAt(str.length() - 1) != '.') {
				// Permite letras y números
				return;
			}
			 else if (tipo == Constante.VALIDACION_RETENCIONES && !Character.isDigit(str.charAt(str.length() - 1)) && str.charAt(str.length() - 1) != '-') {
					// Para retenciones
					return;
				}
			super.insertString(offs, str, a);
		}
	}
}