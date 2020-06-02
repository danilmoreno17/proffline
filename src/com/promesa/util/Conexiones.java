package com.promesa.util;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import util.ConexionSAP;

import com.promesa.bean.BeanConexion;

public class Conexiones {

	public static File archivoConfiguracion;
	public static File archivoWebService;
	public static File archivoJDBC;

	public static ConexionSAP getConexionSAP() throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ConexionSAP> future = executor.submit(new Task());
        ConexionSAP con = null;
        try {
            con = future.get(60, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            con = null;
        }
        executor.shutdownNow();
        return con;
	}
}

class Task implements Callable<ConexionSAP> {
	@Override
	public ConexionSAP call() throws Exception {
		ConexionSAP con;
		BeanConexion bean = new BeanConexion();
		String strMandanteSAP = bean.getStrMandanteSAP();
		String strUsuarioSAP = bean.getStrUsuarioSAP();
		String strPasswordSAP = bean.getStrPasswordSAP();
		String strIdiomaSAP = bean.getStrIdiomaSAP();
		String strHostSAP = bean.getStrHostSAP();
		String strSistemaSAP = bean.getStrSistemaSAP();
		try {
			con = new ConexionSAP(strMandanteSAP, strUsuarioSAP, strPasswordSAP, strIdiomaSAP, strHostSAP, strSistemaSAP);
		} catch (Exception e) {
			con = null;
		}
		return con;
	}
}