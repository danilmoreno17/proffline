package com.promesa.util;

import javax.swing.*;

import com.promesa.main.Promesa;

public class Mensaje {

	public static void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(Promesa.getInstance(), mensaje, "Error", 0);
	}

	public static void mostrarAviso(String mensaje) {
		JOptionPane.showMessageDialog(Promesa.getInstance(), mensaje, "Aviso", 1);
	}

	public static void mostrarWarning(String mensaje) {
		JOptionPane.showMessageDialog(Promesa.getInstance(), mensaje, "Advertencia", 2);
	}

	public static boolean preguntar(String mensaje) {
		if (JOptionPane.showConfirmDialog(Promesa.getInstance(), mensaje,"Pregunta", JOptionPane.YES_NO_OPTION) == 0)
			return true;
		return false;
	}

	public static int preguntaSerie(String mensaje) {
		return JOptionPane.showConfirmDialog(Promesa.getInstance(), mensaje, "Pregunta", 1);
	}

	public static String getCadena(String mensaje) {
		return JOptionPane.showInputDialog(Promesa.getInstance(), mensaje, "Ingreso", 3);
	}

	public static void CerrarSistema(String pregunta, String Mensaje) {
		String ObjButtons[] = { "Yes", "No" };
		int Rpta = JOptionPane.showOptionDialog(Promesa.getInstance(), pregunta, Mensaje, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
		if (Rpta == 0) {
			System.exit(0);
		}
	}
}