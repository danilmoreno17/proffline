package com.promesa.internalFrame;

import java.beans.PropertyVetoException;
import java.util.List;
import javax.swing.JDesktopPane;
//import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import com.promesa.internalFrame.administracion.*;
import com.promesa.internalFrame.sincronizacion.*;
import com.promesa.sincronizacion.bean.BeanTareaProgramada;
import com.promesa.util.DLocker;
import com.promesa.util.Util;
import com.promesa.administracion.bean.*;

public class Administrador {
	

	IRoles Roles;
	INuevoRol NuevoRol;
	IVistas Vistas;
	IAsignarVista AsignarVista;
	IUsuarios Usuarios;
	INuevoUsuario NuevoUsuario;
	IDispositivos Dispositivo;
	INuevoDispositivo NuevoDispositivo;

	ISincronizacionAdministracion Sincronizacion;
	BeanTareaProgramada beanTareaProgramada;

	/* M�TODO QUE TRANSFIERE DATOS DE ROLES A LA PANTALLA NUEVO ROL */
	public void ventanaAdministradorPasaDatosRoles(int proceso, final JDesktopPane destokp, final String dato1, final String dato2, final BeanTareaProgramada beanTareaProgramada) throws PropertyVetoException {
		this.beanTareaProgramada = beanTareaProgramada;
		switch (proceso) {
		case 0: {
			if (NuevoRol == null || NuevoRol.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							NuevoRol = new INuevoRol(dato1, dato2, beanTareaProgramada);
							destokp.add(NuevoRol);
							NuevoRol.setVisible(true);
							NuevoRol.setMaximum(true);
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
							cerrar = true;
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(NuevoRol);
				NuevoRol.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}
		}
	}

	/* M�TODO QUE TRANSFIERE DATOS DE USUARIO A LA PANTALLA NUEVO USUARIO */
	public void ventanaAdministradorPasaDatosUsuarios(int proceso, final JDesktopPane destokp, final String idUsu,
			final String nomUsu, final String claUsu, final String fecCre, final String fecUltAccSis, final String horUltAccSis,
			final String bloqueado, final String identificacion, final List<BeanRolUsuario> rolesusuariose,
			final BeanTareaProgramada beanTareaProgramada) throws PropertyVetoException {
		switch (proceso) {
		case 0: {
			if (NuevoUsuario == null || NuevoUsuario.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						boolean cerrar = false;
						try {
							NuevoUsuario = new INuevoUsuario(idUsu, nomUsu, claUsu, fecCre, fecUltAccSis, horUltAccSis, bloqueado, identificacion, rolesusuariose, beanTareaProgramada);
							destokp.add(NuevoUsuario);
							NuevoUsuario.setVisible(true);
							NuevoUsuario.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(NuevoUsuario);
				NuevoUsuario.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}
		}
	}

	/*
	 * M�TODO QUE TRANSFIERE DATOS DE DISPOSITIVOS A LA PANTALLA NUEVO
	 * DISPOSITIVO
	 */
	public void ventanaAdministradorPasaDatosDispositivos(int proceso, final JDesktopPane destokp, final String idDis,
			final String nomRel, final String serie, final String codAct, final String numSeg, final String simm, final String imei,
			final String numTel, final String idUsu, final String nomUsu, final String estado, final String obs,
			final BeanTareaProgramada beanTareaProgramada) throws PropertyVetoException {
		switch (proceso) {
		case 0: {
			if (NuevoDispositivo == null || NuevoDispositivo.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					public void run() {
						boolean cerrar = false;
						try {
							NuevoDispositivo = new INuevoDispositivo(idDis, nomRel, serie, codAct, numSeg, simm, imei, numTel, idUsu, nomUsu, estado, obs, beanTareaProgramada);
							destokp.add(NuevoDispositivo);
							NuevoDispositivo.setVisible(true);
							NuevoDispositivo.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(NuevoDispositivo);
				NuevoDispositivo.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}
		}
	}

	/* M�TODO QUE MUESTRA PANTALLA SEG�N OPCIONES DEL ADMINISTRADOR */
	public void muestraVentanaAdministrador(int proceso, final JDesktopPane destokp, final BeanTareaProgramada beanTareaProgramada) throws PropertyVetoException {
		this.beanTareaProgramada = beanTareaProgramada;
		switch (proceso) {
		case 0: {
			if (Roles == null || Roles.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							Roles = new IRoles(beanTareaProgramada);
							destokp.add(Roles);
							Roles.setVisible(true);
							Roles.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(Roles);
				Roles.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
			break;
		}
		case 1: {
			if (Vistas == null || Vistas.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							Vistas = new IVistas(beanTareaProgramada);
							destokp.add(Vistas);
							Vistas.setVisible(true);
							Vistas.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(Vistas);
				Vistas.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
			break;
		}
		case 2: {
			if (Usuarios == null || Usuarios.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							Usuarios = new IUsuarios(beanTareaProgramada);
							destokp.add(Usuarios);
							Usuarios.setVisible(true);
							Usuarios.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(Usuarios);
				Usuarios.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
			break;
		}
		case 3: {
			if (AsignarVista == null || AsignarVista.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							AsignarVista = new IAsignarVista(beanTareaProgramada);
							destokp.add(AsignarVista);
							AsignarVista.setVisible(true);
							AsignarVista.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(AsignarVista);
				AsignarVista.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
			break;
		}
		case 4: {
			if (NuevoRol == null || NuevoRol.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							NuevoRol = new INuevoRol(beanTareaProgramada);
							destokp.add(NuevoRol);
							NuevoRol.setVisible(true);
							NuevoRol.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(NuevoRol);
				NuevoRol.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
			break;
		}
		case 5: {
			if (NuevoUsuario == null || NuevoUsuario.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							NuevoUsuario = new INuevoUsuario(beanTareaProgramada);
							destokp.add(NuevoUsuario);
							NuevoUsuario.setVisible(true);
							NuevoUsuario.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(NuevoUsuario);
				NuevoUsuario.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
			break;
		}
		case 6: {
			if (Dispositivo == null || Dispositivo.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							Dispositivo = new IDispositivos(beanTareaProgramada);
							destokp.add(Dispositivo);
							Dispositivo.setVisible(true);
							Dispositivo.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(Dispositivo);
				Dispositivo.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
			break;
		}
		case 7: {
			if (NuevoDispositivo == null || NuevoDispositivo.isClosed()) {
				final DLocker bloqueador = new DLocker();
				Thread hilo = new Thread() {
					boolean cerrar = false;
					public void run() {
						try {
							NuevoDispositivo = new INuevoDispositivo(beanTareaProgramada);
							destokp.add(NuevoDispositivo);
							NuevoDispositivo.setVisible(true);
							NuevoDispositivo.setMaximum(true);
						} catch (Exception e) {
							cerrar = true;
							Util.mostrarExcepcion(e);
						} finally {
							if (!cerrar) {
								bloqueador.dispose();
							}
						}
					}
				};
				hilo.start();
				bloqueador.setVisible(true);
			}
			try {
				restaurarVentana(NuevoDispositivo);
				NuevoDispositivo.setSelected(true);
			} catch (PropertyVetoException e) {
				Util.mostrarExcepcion(e);
			}
		}
			break;
		case 8: {
			if (Sincronizacion == null || Sincronizacion.isClosed()) {
				Sincronizacion = new ISincronizacionAdministracion(beanTareaProgramada);
				Sincronizacion.setVisible(true);
				destokp.add(Sincronizacion);
				Sincronizacion.setMaximum(true);
				try {
					Sincronizacion.setSelected(true);
				} catch (PropertyVetoException e) {
					Util.mostrarExcepcion(e);
				}
			}
		}
		}
	}

	private void restaurarVentana(JInternalFrame internalFrame) {
		try {
			if (internalFrame != null) {
				internalFrame.setSelected(true);
				internalFrame.getDesktopPane().getDesktopManager().deiconifyFrame(internalFrame);
				internalFrame.getDesktopPane().getDesktopManager().maximizeFrame(internalFrame);
				internalFrame.getDesktopPane().getDesktopManager().minimizeFrame(internalFrame);
				internalFrame.moveToFront();
			}
		} catch (PropertyVetoException e) {
			Util.mostrarExcepcion(e);
		}
	}
}