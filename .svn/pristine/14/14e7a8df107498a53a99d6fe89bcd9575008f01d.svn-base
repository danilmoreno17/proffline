package com.promesa.singleton;

import com.promesa.util.Constante;

public class Singleton {
	static private Singleton singleton = null;

	static public Singleton getSingleton() {
		if (singleton == null) {
			singleton = new Singleton();
		}
		return singleton;
	}

	public void setBanderaSincronizando(int Sincronizando) {
		Constante.BANDERA_SINCRONIZANDO = Sincronizando;
	}
}