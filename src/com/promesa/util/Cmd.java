package com.promesa.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class Cmd  {
	public static String fechaHora() {
		SimpleDateFormat[] sdfs = { new SimpleDateFormat("dd-MM-yyyy h:mm:ss a") };
		int numItera = 1;
		for (Date d = new Date();; d = new Date(d.getTime() + 1468800000)) {
			for (SimpleDateFormat sdf : sdfs) {
				return sdf.format(d).toString();
			}
			if (--numItera == 0)
				break;
		}
		return "";
	}

	public static int diferencia(String fechaInicio, String fechaFinal) {
		int n1, n2, d;
		d = 0;
		if (fechaInicio.length() == 21) {
			if (fechaInicio.substring(11, 12).equals(
					fechaFinal.substring(11, 12))) {
				if (fechaInicio.substring(13, 15).equals(
						fechaFinal.substring(13, 15))) {
					if ((fechaInicio.charAt(16) + "" + fechaInicio.charAt(17)).equals((fechaFinal.charAt(16) + "" + fechaFinal.charAt(17)))) {
						d = 0;
					} else {
						n1 = Integer.parseInt(fechaInicio.substring(16, 18));
						n2 = Integer.parseInt(fechaFinal.substring(16, 18));
						d = n2 - n1;
					}
				} else {
					n1 = Integer.parseInt(fechaInicio.substring(13, 15));
					n2 = Integer.parseInt(fechaFinal.substring(13, 15));
					d = n2 - n1;
				}
			} else {
				n1 = Integer.parseInt(fechaInicio.substring(11, 12));
				n2 = Integer.parseInt(fechaFinal.substring(11, 12));
				d = n2 - n1;
			}
		} else {
			if (fechaInicio.substring(11, 13).equals(
					fechaFinal.substring(11, 13))) {
				if (fechaInicio.substring(14, 16).equals(
						fechaFinal.substring(14, 16))) {
					if ((fechaInicio.charAt(17) + "" + fechaInicio.charAt(18)).equals((fechaFinal.charAt(17) + "" + fechaFinal.charAt(18)))) {
						d = 0;
					} else {
						n1 = Integer.parseInt(fechaInicio.substring(17, 19));
						n2 = Integer.parseInt(fechaFinal.substring(17, 19));
						d = n2 - n1;
					}
				} else {
					n1 = Integer.parseInt(fechaInicio.substring(14, 16));
					n2 = Integer.parseInt(fechaFinal.substring(14, 16));
					d = n2 - n1;
				}
			} else {
				n1 = Integer.parseInt(fechaInicio.substring(11, 13));
				n2 = Integer.parseInt(fechaFinal.substring(11, 13));
				d = n2 - n1;
			}
		}
		return d;
	}

	/* M�TODO QUE CALCULA EL N�MERO DE SERIE */
	public static String numeroSerie() {
		String resultado, inputLine, command;
		resultado = inputLine = command = "";
		Process child;
		int z;
		try {
			command = "cmd /c cd c:\\windows\\system32\\wbem && wmic os get serialnumber";
			child = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(child.getInputStream()));
			inputLine = in.readLine();
			z = 1;
			while (inputLine != null) {
				if (inputLine.length() > 0) {
					if (z == 2) {
						resultado = inputLine.substring(0).trim();
					}
					z++;
				}
				inputLine = in.readLine();
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = null;
		}
		return resultado;
	}

	public static long tiempoConexion(String ip, int puerto, int maximoRetardo, int intento) {
		long delay = -1l;
		try {
			SocketAddress sockaddr = new InetSocketAddress(ip, puerto);
			Calendar start = Calendar.getInstance();
			Socket sock = new Socket();
			long inicio = start.getTimeInMillis();
			sock.connect(sockaddr, maximoRetardo);
			Calendar end = Calendar.getInstance();
			long fin = end.getTimeInMillis();
			delay = (fin - inicio);
			sock.close();
		} catch (SocketTimeoutException e) {
			delay = -1l;
		} catch (NoRouteToHostException e) {
			delay = -1l;
		} catch (SocketException e) {
			delay = -1l;
		} catch (Exception e) {
			delay = -1l;
		}
		return delay;
	}

	public static String disponibilidadSAP() {
		String ip = "";
		int puerto = 0;
		int maxdelay = 0;
		try {
			Properties props = new Properties();
			InputStreamReader in = null;
			in = new InputStreamReader(new FileInputStream("conexion.properties"), "UTF-8");
			props.load(in);
			ip = props.getProperty("sap.host").trim();
			puerto = Integer.parseInt(props.getProperty("sap.port").trim());
			maxdelay = Integer.parseInt((props.getProperty("sap.maxdelay").trim()));
			in.close();
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			System.exit(1);
		}
		int i = 0;
		while (i < Constante.MAX_NUMERO_INTENTOS) {
			long delay = tiempoConexion(ip, puerto, maxdelay, i);
			if (delay >= 0) {
				return "" + delay;
			}
			i++;
		}
		return "-1";
	}
}