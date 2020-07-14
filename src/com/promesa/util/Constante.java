package com.promesa.util;

import java.awt.Font;

public class Constante {

	/* CONEXION */
	public static final String CONNECTION_URL = "jdbc:sqlite:C:\\dbproffline\\proffline";
	public static final String CONNECTION_URL_TX = "jdbc:sqlite:C:\\dbproffline\\proffline_tx";
	public static final String CONNECTION_URL_FR = "jdbc:sqlite:C:\\dbproffline\\proffline_fr";// Conexion de Ferias
	public static final String DRIVER_CLASSNAME = "org.sqlite.JDBC";
	public static final String MODO_ONLINE = "1";
	public static final String MODO_OFFLINE = "0";
	public static final String MENSAJE_PERDIDA_CONEXION = "Se ha perdido la conexión con SAP.";
	public static final String MENSAJE_CONEXION_NULA = "Se perdió la conexión con SAP, por tanto la aplicación se cerrará.";
	public static final String MENSAJE_CONEXION_BAJA = "La conexión con SAP es baja.";
	public static final String MENSAJE_CONEXION_BUENA = "Se recomienda el modo ONLINE.";

	/* CAMBIO DE CLAVE */
	public static final String MENSAJE_ERROR_CLAVES = "Ingrese clave y confirmación nueva clave.";
	public static final String MENSAJE_ERROR_CONFIRMACION_CLAVE = "Ingrese confirmación nueva clave.";
	public static final String MENSAJE_ERROR_ACTUALIZA_CLAVE = "Hubo un error en la aplicación que impidió que se actualice nueva la clave.";

	/* ACCESO */
	public static final String MENSAJE_ERROR_USUARIO_CLAVE = "Ingrese usuario y clave.";
	public static final String MENSAJE_ERROR_CLAVE = "Ingrese clave.";
	public static final String MENSAJE_ERROR_USUARIO = "Ingrese usuario.";
	public static final String MENSAJE_USUARIO_BLOQUEADO = "Usuario bloqueado.";
	public static final String MENSAJE_CLAVE_INCORRECTA = "Clave de acceso incorrecto.";
	public static final String MENSAJE_USUARIO_INEXISTENTE = "Usuario no existe.";
	public static final String MENSAJE_USUARIO_SIN_ROL = "Este usuario no tiene asignado un role, contáctese con el administrador.";
	public static final String MENSAJE_ERROR_ACCESO = "El usuario y/o clave es(son) incorrecto(s), vuelva a ingresar.";
	public static final String ACCESO_ONLINE = "Usted acaba de ingresar en modo ONLINE.";
	public static final String ACCESO_OFFLINE = "Usted acaba de ingresar en modo OFFLINE.";
	public static final String MENSAJE_ERROR = "Ha ocurrido un error en la aplicación, por favor comuníquese con el administrador.";
	public static final String MENSAJE_ERROR_SERIE_VACIA = "Este dispositivo no esta registrado para acceder a la aplicación.";
	public static final String MENSAJE_ERROR_SERIE_INACTIVA = "El dispositivo esta bloqueado, por tanto no podrá acceder a la aplicación.";
	public static final String VALOR_ESTADO_INACTIVO_DISPOSITIVO = "0";
	public static final String VALOR_ESTADO_ACTIVO_DISPOSITIVO = "1";
	public static final String ID_ROL_ADMINISTRADOR = "ROL-00000001";
	public static final String MENSAJE_ACCESO_ROL_DENEGADO_ADMINISTRADOR = "El rol administrador solo puede ser accedido en modo ONLINE.";
	public static final String MENSAJE_ACCESO_ROL_DENEGADO_ADMINISTRADOR2 = "El rol administrador solo puede ser trabajado en modo ONLINE.";
	public static final String ID_ROL_SUPERVISOR = "ROL-00000002";
	public static final String MENSAJE_ACCESO_ROL_DENEGADO_SUPERVISOR = "El rol supervisor solo puede ser accedido en modo ONLINE.";
	public static final String MENSAJE_ACCESO_ROL_DENEGADO_SUPERVISOR2 = "El rol supervisor solo puede ser trabajado en modo ONLINE.";
	public static final String ID_ROL_VENDEDOR = "ROL-00000003";
	public static final String ID_ROL_VENDEDOR_CAC = "PRO-00000031";
	public static final String MENSAJE_CONEXION_NULA_VENDEDOR = "Se perdió la conexión con SAP, por tanto se pasará a trabajar en modo OFFLINE.";
	public static final String MENSAJE_CONEXION_BAJA_VENDEDOR = "La conexión con SAP es baja, por tanto ¿desea trabajar en modo OFFLINE?.";
	public static final String MENSAJE_CONEXION_OPTIMA_DIRECTA_VENDEDOR = "Existe una buena conexión con SAP, por tanto se pasará a trabajar en modo ONLINE.";
	public static final String MENSAJE_CONEXION_OPTIMA_PREGUNTA_VENDEDOR = "La conexión con SAP es buena, por tanto ¿desea trabajar en modo ONLINE?.";

	/* ADMINISTRACIÓN */
	/* --- ROLES --- */
	public static final String MENSAJE_VALIDA_NUEVO_ROL = "Registrar un rol solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_NUEVO_ROL = "Rol registrado correctamente.";
	public static final String MENSAJE_ERROR_NUEVO_ROL = "Hubo un error en la aplicación que impidió que se registre un nuevo rol.";
	public static final String MENSAJE_VALIDA_MODIFICA_ROL = "Modificar un rol solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_MODIFICA_ROL = "Rol modificado correctamente.";
	public static final String MENSAJE_ERROR_MODIFICA_ROL = "Hubo un error en la aplicación que impidió que se modifique el rol.";
	public static final String MENSAJE_VALIDA_ELIMINA_ROL = "Borrar un rol solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_PREGUNTA_ELIMINA_ROL = "¿Está seguro de que desea borrar el rol seleccionado?";
	public static final String MENSAJE_SELECCION_ELIMINA_ROL = "Si desea borrar un rol debe primero seleccionarlo.";
	public static final String MENSAJE_ERROR_ELIMINA_ROL = "Hubo un error en la aplicación que impidió que se borre el rol.";
	public static final String MENSAJE_NO_ROL_ELIMINA = "No existen registros de roles que eliminar.";
	public static final String MENSAJE_NO_ROLES = "No existe un registro que transferir.";
	public static final String MENSAJE_VALIDA_CAMPO_VACIO_NOMBRE_ROL = "Ingrese nombre de rol.";
	public static final String MENSAJE_VALIDA_MAGNITUD_NOMBRE_ROL = "El nombre de rol solo puede contener hasta un máximo de 20 caracteres";
	public static final String MENSAJE_VALIDA_ESPACIO_NOMBRE_ROL = "El nombre de rol ingresado no debe contener espacios en blanco.";
	public static final String MENSAJE_VALIDA_CARACTER_NOMBRE_ROL = "El nombre de rol ingresado solo debe contener caracteres alfabéticos.";
	public static final String MENSAJE_VALIDA_CARACTER_ESPACIO_NOMBRE_ROL = "El nombre de rol ingresado solo debe contener caracteres alfabéticos y, sin espacios en blanco.";
	public static final String MENSAJE_ERROR_TRANSFERENCIA_ROL = "Hubo un error en la aplicación que impidió que se transfieran los datos.";
	public static final String MENSAJE_ERROR_LISTA_ROL = "Hubo un error en la aplicación que impidió que se listen los roles.";
	public static final String MENSAJE_ERROR_NOMBRE_ROL = "Por favor elija otro nombre de rol porque este ya existe.";
	/* --- VISTAS --- */
	public static final String MENSAJE_VALIDA_ASIGNA_VISTA_ROL = "Asignar una vista a un rol solo puedo ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_ASIGNA_VISTA_ROL = "Vista asignada a rol correctamente.";
	public static final String MENSAJE_ERROR_ASIGNA_VISTA_ROL = "Hubo un error que impidió que se asigne una vista al rol.";
	/* --- USUARIOS --- */
	public static final String MENSAJE_VALIDA_NUEVO_USUARIO = "Registrar un usuario solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_NUEVO_USUARIO = "Usuario registrado correctamente.";
	public static final String MENSAJE_ERROR_NUEVO_USUARIO = "Hubo un error en la aplicación que impidió que se registre un nuevo usuario.";
	public static final String MENSAJE_VALIDA_MODIFICA_USUARIO = "Modificar un usuario solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_MODIFICA_USUARIO = "Usuario modificado correctamente.";
	public static final String MENSAJE_ERROR_MODIFICA_USUARIO = "Hubo un error en la aplicación que impidió que se modifique un usuario.";
	public static final String MENSAJE_VALIDA_ELIMINA_USUARIO = "Borrar un usuario solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_PREGUNTA_ELIMINA_USUARIO = "¿Está seguro de que desea borrar al usuario seleccionado?";
	public static final String MENSAJE_SELECCION_ELIMINA_USUARIO = "Si desea borrar un usuario debe primero seleccionarlo.";
	public static final String MENSAJE_ERROR_ELIMINA_USUARIO = "Hubo un error en la aplicación que impidió que se borre al usuario.";
	public static final String MENSAJE_NO_USUARIO_ELIMINA = "No existen registros de usuarios que eliminar.";
	public static final String MENSAJE_VALIDA_DESBLOQUEA_USUARIO = "Desbloquear un usuario solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_DESBLOQUEA_USUARIO = "Usuario desbloqueado correctamente.";
	public static final String MENSAJE_ERROR_DESBLOQUEA_USUARIO = "Hubo un error en la aplicación que impidió que se desbloquee al usuario.";
	public static final String MENSAJE_ERROR_TRANSFERENCIA_USUARIO = "Hubo un error en la aplicación que impidió que se transfieran los datos.";
	public static final String MENSAJE_NO_USUARIOS = "No existe un registro que transferir.";
	public static final String MENSAJE_BUSQUEDA_USUARIO = "El usuario ingresado no existe.";
	public static final String MENSAJE_ERROR_BUSQUEDA_USUARIO = "Hubo un error en la aplicación que impidió que busque un usuario.";
	public static final String MENSAJE_BUSQUEDA_USUARIOS = "No existe una lista de usuarios.";
	public static final String MENSAJE_ERROR_BUSQUEDA_USUARIOS = "Hubo un error en la aplicación que impidió que se busquen usuarios.";
	public static final String MENSAJE_ERROR_USUARIO_01 = "Ingrese usuario, clave, confirmación clave e identificación.";
	public static final String MENSAJE_ERROR_USUARIO_02 = "Ingrese clave, confirmación clave e identificación.";
	public static final String MENSAJE_ERROR_USUARIO_03 = "Ingrese confirmación clave e identificación.";
	public static final String MENSAJE_ERROR_USUARIO_04 = "Ingrese identificación.";
	public static final String MENSAJE_ERROR_USUARIO_05 = "Ingrese usuario, confirmación clave e identificación.";
	public static final String MENSAJE_ERROR_USUARIO_06 = "Ingrese usuario e identificación.";
	public static final String MENSAJE_ERROR_USUARIO_07 = "Ingrese usuario.";
	public static final String MENSAJE_ERROR_USUARIO_08 = "Ingrese usuario, clave e identificación.";
	public static final String MENSAJE_ERROR_USUARIO_09 = "Ingrese usuario y clave.";
	public static final String MENSAJE_ERROR_USUARIO_10 = "Ingrese usuario, clave y confirmación.";
	public static final String MENSAJE_ERROR_USUARIO_11 = "Ingrese clave e identificación.";
	public static final String MENSAJE_ERROR_USUARIO_12 = "Ingrese clave y confirmación clave.";
	public static final String MENSAJE_ERROR_USUARIO_13 = "Ingrese usuario y confirmación clave.";
	public static final String MENSAJE_ERROR_USUARIO_14 = "El usuario no puede ser mayor a 20 caracteres.\nLa clave y confirmación clave no son iguales.";
	public static final String MENSAJE_ERROR_USUARIO_15 = "El usuario no puede ser mayor a 20 caracteres.";
	public static final String MENSAJE_ERROR_USUARIO_16 = "La clave y confirmación clave no son iguales.";
	public static final String MENSAJE_ERROR_USUARIO_17 = "La nueva clave y antigua clave no pueden ser iguales.";
	public static final String ROL_VENDEDOR = "ROL-00000003";
	public static final String MENSAJE_ASIGNA_ROL_USUARIO = "Debe asignar roles.";
	public static final String MENSAJE_IDENTIFICACION_NO_VALIDA = "La identificación asignada no corresponde a un vendedor.";
	public static final String MENSAJE_USUARIOS_IGUALES = "Debe ingresar un nombre de usuario que no haya sido registrado.";
	public static final String MENSAJE_ERROR_NOMBRE_USUARIO = "Por favor elija otro nombre de usuario porque este ya existe.";
	public static final String MENSAJE_ERROR_CODIGO_USUARIO = "Por favor elija otro código de usuario porque este ya existe.";
	public static final String MENSAJE_ERROR_NOMBRE_CODIGO_USUARIO = "Por favor elija otro nombre y código de usuario porque estos ya existen.";
	/* --- DISPOSITIVOS --- */
	public static final String MENSAJE_VALIDA_ELIMINA_DISPOSITIVO = "Borrar un dispositivo solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_PREGUNTA_ELIMINA_DISPOSTIVO = "¿Está seguro de que desea borrar el dispositivo seleccionado?";
	public static final String MENSAJE_SELECCION_ELIMINA_DISPOSITIVO = "Si desea borrar un dispositivo debe primero seleccionarlo.";
	public static final String MENSAJE_ERROR_ELIMINA_DISPOSITIVO = "Hubo un error en la aplicación que impidió que se borre al dispositivo.";
	public static final String MENSAJE_NO_DISPOSTIVO_ELIMINA = "No existen registros de dispositivos que eliminar.";
	public static final String MENSAJE_ERROR_EXPORTA_DISPOSTIVO = "Hubo un error en la aplicación que impidió la exportación de dispositivos.";
	public static final String MENSAJE_NO_DISPOSTIVO_EXPORTA = "No existen registros de dispositivos que exportar.";
	public static final String MENSAJE_BUSQUEDA_DISPOSITIVO = "El usuario ingresado no tiene ningún dispositivo asignado.";
	public static final String MENSAJE_ERROR_BUSQUEDA_DISPOSITIVO = "Hubo un error en la aplicación que impidió que busque un dispositivo.";
	public static final String MENSAJE_BUSQUEDA_DISPOSITIVOS = "No existe una lista de dispositivos.";
	public static final String MENSAJE_ERROR_BUSQUEDA_DISPOSITIVOS = "Hubo un error en la aplicación que impidió que se busquen dispositivos.";
	public static final String MENSAJE_VALIDA_BLOQUEA_DISPOSITIVO = "Bloquear un dispositivo solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_BLOQUEA_DISPOSITIVO = "Dispositivo bloqueado correctamente.";
	public static final String MENSAJE_ERROR_BLOQUEA_DISPOSITIVO = "Hubo un error en la aplicación que impidió que bloquee un dispositivo.";
	public static final String MENSAJE_VALIDA_NUEVO_DISPOSTIVO = "Registrar un dispositivo solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_NUEVO_DISPOSTIVO = "Dispositivo registrado correctamente.";
	public static final String MENSAJE_ERROR_NUEVO_DISPOSTIVO = "Hubo un error en la aplicación que impidió que se registre un nuevo dispositivo.";
	public static final String MENSAJE_VALIDA_MODIFICA_DISPOSTIVO = "Modificar un dispositivo solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_MODIFICA_DISPOSITIVO = "Dispositivo modificado correctamente.";
	public static final String MENSAJE_ERROR_MODIFICA_DISPOSTIVO = "Hubo un error en la aplicación que impidió que se modifique un dispositivo.";
	public static final String MENSAJE_INGRESA_DISPOSITIVO = "Existen campos vacíos obligatorios, ingrese datos para ingresar un nuevo dispositivo.";
	public static final String DISPOSITIVO_ASIGNADO_VENDEDOR = "El vendedor seleccionado ya tiene ese dispositivo asignado.";
	public static final String NETBOOK = "N";
	public static final String IMPRESORA = "I";
	public static final String OTRO = "O";
	public static final String MENSAJE_ERROR_TRANSFERENCIA_DISPOSITIVO = "Hubo un error en la aplicación que impidió que se transfieran los datos.";
	public static final String MENSAJE_ERROR_NUMERO_SERIE = "Por favor elija otro número de serie porque este ya existe.";
	public static final String MENSAJE_ERROR_NUMERO_SERIE_CODIGO_ACTIVACION = "Por favor elija otra serie y código de activación porque ya existen.";
	public static final String MENSAJE_ERROR_CODIGO_ACTIVACION = "Por favor elija otro código de activación porque este ya existe.";
	/* ADMINISTRACIÓN */

	/* SINCRONIZACIÓN */
	public static final String MENSAJE_ERROR_SINCRONIZACION = "Existen campos vacíos, ingrese datos.";

	/* PLANIFICACIÓN */
	public static final String MENSAJE_VALIDA_NUEVA_VISITA = "Registrar una visita solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_NUEVA_VISITA = "Visita registrada correctamente.";
	public static final String MENSAJE_ERROR_NUEVA_VISITA = "Hubo un error en la aplicación que impidió que se registre la visita.";
	public static final String MENSAJE_ERROR_VISITA_EXISTENTE = "La visita ya ha sido registrada, por favor cambie los datos de la visita.";
	public static final String MENSAJE_ERROR_VISITA_FECHA = "No se puede registar una visita con una fecha anterior a la actual.";
	public static final String MENSAJE_ERROR_MAX_VISITA = "El vendedor ya completo el maximo de visitas registrada para esta fecha.";
	public static final String MENSAJE_VALIDA_NUEVA_PLANIFICACION = "Registrar una planificación solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_NUEVA_PLANIFICACION = "Planificación registrada correctamente.";
	public static final String MENSAJE_ERROR_NUEVA_PLANIFICACION = "Hubo un error en la aplicación que impidió que se registre la planificación.";
	public static final String MENSAJE_VALIDA_MODIFICA_PLANIFICACION = "Modificar una planificación solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_EXITO_MODIFICA_PLANIFICACION = "Planificación modificada correctamente.";
	public static final String MENSAJE_ERROR_MODIFICA_PLANIFICACION = "Hubo un error en la aplicación que impidió que se modifique la planificación.";
	public static final String MENSAJE_VALIDA_ELIMINA_PLANIFICACION = "Eliminar una planificación solo puede ser aplicado en modo ONLINE.";
	public static final String MENSAJE_PREGUNTA_ELIMINA_PLANIFICACION = "¿Está seguro de que desea borrar la planificación seleccionada?";
	public static final String MENSAJE_SELECCION_ELIMINA_PLANIFICACION = "Si desea borrar una planificación debe primero seleccionarla.";
	public static final String MENSAJE_EXITO_ELIMINA_PLANIFICACION = "Planificación eliminada satisfactoriamente.";
	public static final String MENSAJE_ERROR_ELIMINA_PLANIFICACION = "Hubo un error en la aplicación que impidió que se elimine la planificación.";
	public static final String MENSAJE_EXITO_IMPORTA_PLANIFICACION = "Planificación importada correctamente.";
	public static final String MENSAJE_ERROR_IMPORTA_PLANIFICACION = "Hubo un error en la aplicación que impidió que se importe la planificación.";
	public static final String MENSAJE_EXITO_IMPORTA_VISITA = "Visita importada correctamente.";
	public static final String MENSAJE_ERROR_IMPORTA_VISITA = "Hubo un error en la aplicación que impidió que se importe la visita.";
	public static final String MENSAJE_ERROR_BUSQUEDA_PLANIFICACION = "Hubo un error en la aplicación que impidió que se busque la planificación.";
	public static final String MENSAJE_ERROR_BUSQUEDA_VISITA = "Hubo un error en la aplicación que impidió que se busque la visita.";
	public static final String MENSAJE_ERROR_DOMINGO = "No se puede registrar una programación para un día domingo.";
	public static final String MENSAJE_ERROR_AÑO_ACTUAL = "No se puede registrar una programación fuera del año actual.";
	public static final String MENSAJE_ERROR_FECHA_PASADA = "No se puede registrar una programación para fechas pasadas.";
	public static final String MENSAJE_ERROR_FERIADO = "No se puede registrar una programación para un día feriado.";
	public static final String MENSAJE_ERROR_CAMPO_VACIO_VENDEDOR = "Campo vacío, seleccione vendedor.";
	public static final String MENSAJE_ERROR_CAMPO_VACIO_CLIENTE = "Campo vacío, seleccione cliente.";
	public static final String MENSAJE_ERROR_CAMPOS_VACIOS_VENDEDOR_CLIENTE = "Campos vacíos, seleccione vendedor y cliente.";
	public static final String MENSAJE_BUSQUEDA_PLANIFICACION = "No existen planificaciones asignadas al cliente en mención.";
	public static final String TIPO_VISITA1 = "01";
	public static final String TIPO_VISITA2 = "02";
	public static final String DIA = "31";
	public static final String MES = "12";
	public static final String SEPARADOR = ".";
	public static final String PRIMERO = "1 er";
	public static final String SEGUNDO = "2 do";
	public static final String TERCERO = "3 er";
	public static final String CUARTO = "4 to";
	public static final String QUINTO = "5 to";
	public static final String MENSAJE_NO_VENDEDOR_NO_CLIENTE = "No existen vendedores ni clientes asociados.";
	public static final String MENSAJE_ERROR_EXPORTA_PLANIFICACION = "Hubo un error en la aplicación que impidió la exportación de planificaciones.";
	public static final String MENSAJE_ERROR_EXPORTA_VISITA = "Hubo un error en la aplicación que impidió la exportación de visitas.";
	public static final String MENSAJE_NO_PLANIFICACION_EXPORTA = "No existen registros de planificaciones que exportar.";
	public static final String MENSAJE_NO_VISITA_EXPORTA = "No existen registros de visitas que exportar.";

	/* DEVOLUCIONES */
	public static final String MENSAJE_PREGUNTA_SOLICITA_DEVOLUCION = "¿Está seguro de que desea solicitar la solicitud (de devolución) seleccionada?";
	public static final String MENSAJE_SELECCION_SOLICITA_DEVOLUCION = "Si desea programar una devolución debe primero seleccionar una solicitud.";
	public static final String MENSAJE_EXITO_SOLICITA_DEVOLUCION = "La información ha sido guardada con éxito.";
	public static final String MENSAJE_ERROR_SOLICITA_DEVOLUCION = "Hubo un error en la aplicación que impidió que se solicite la devolución.";
	public static final String MENSAJE_ERROR_FECHA_FORMATO = "El formato de fecha a ingresar es DD.MM.AAAA";
	public static final String MENSAJE_ERROR_FECHA_MINIMA = "La fecha debe ser mayor o igual a la fecha de hoy.";
	public static final String MENSAJE_ERROR_HORA = "El formato de hora a ingresar es HH:MM.";
	public static final String MENSAJE_ERROR_GUARDAR_DEVOLUCION_MODO_ONLINE = "Ud. sólo puede guardar la devolución en modo ONLINE.";
	public static final String MENSAJE_ERROR_IMPRIMIR_DEVOLUCION_MODO_ONLINE = "Ud. sólo puede imprimir la devolución en modo ONLINE.";
	public static final String MENSAJE_ERROR_GUARDAR_COMENTARIO_MODO_ONLINE = "Ud. sólo puede guardar comentario en modo ONLINE.";
	public static final String MENSAJE_SELECCION_INGRESA_SELLO_PEDIDO_DEVOLUCION = "Si desea ingresar un sello debe seleccionar primero el pedido.";
	public static final String MENSAJE_CAMPO_VACIO_MOTIVO_DEVOLUCION = "Debe seleccionar un motivo de devolución.";
	public static final String MENSAJE_CAMPO_VACIO_SELLO = "Debe ingresar un sello.";
	public static final String MENSAJE_CAMPO_VACIO_GUIA_TRANSPORTE = "Debe ingresar guia de Transporte.";
	public static final String MENSAJE_CAMPO_VACIO_NUMERO_BULTOS = "Debe ingresar Número de Bultoscy debe tener maximo cuatro digitos.";
	public static final String MENSAJE_CAMPO_VACIO_NOMBRE_CONTACTO = "Debe ingresar Nombre de Contacto.";
	public static final String MENSAJE_CAMPO_VACIO_DIR_VEN = "Debe seleccionar tipo DIR o VEN.";
	public static final String MENSAJE_CAMPO_VACIO_NUMERO_FUD = "Debe ingresar número FUD y debe tener maximo tres digitos.";
	public static final String MENSAJE_NO_EXISTE_MATERIAL = "No existe ningún material (en la lista) a devolver.";
	
	/* OTROS */
	public static final String VACIO = "";
	public static final String PREGUNTA_SALIR = "¿Desea salir de la aplicación?";
	public static final String MENSAJE_CIERRE = "La aplicación PROFFLINE se cerrará.";
	public static final String EXTENSION_ARCHIVO = ".xls";
	public static final String MENSAJE_EXITO_EXPORTA = "El siguiente reporte fue almacenado en ";

	/* OPCIONES DE MENU */
	public static final String OPCION_ADMINISTRACION = "Administración";
	public static final String OPCION_PANEL_CONTROL = "Panel de Control";
	public static final String OPCION_CLIENTES = "Clientes";
	public static final String OPCION_CONSULTA_PEDIDOS = "Consulta de Pedidos";
	public static final String OPCION_LISTA_PRECIOS = "Lista de Precios";
	public static final String OPCION_CONSULTAS = "Consultas";
	public static final String OPCION_REPORTE_CONTROL = "Reportes de Control";
	public static final String OPCION_DEVOLUCIONES = "Devoluciones";
	public static final String OPCION_SINC_ADM = "Sinc. Adm.";
	public static final String OPCION_SINC_PLA = "Sinc. Pla.";
	public static final String OPCION_SINC_PED = "Sincronización";
	public static final String OPCION_VISITA_FUERA_RUTA = "Registro de Agenda";
	public static final String OPCION_PARAMETROS_PLANIFICACION = "Parámetros Planificación";
	public static final String OPCION_FACTURAS = "Consulta de Facturas";
	public static final String OPCION_FERIAS = "Ferias";
	public static final String OPCION_SALIR = "Salir";

	public static final String OPCIONES_ADMINISTRACION = "1,2,3,4,5,6";
	public static final String OPCIONES_PLANIFICACION = "7,8,9";
	// programaciones que crean hilos al ejecutar la aplicacion
	public static final String OPCIONES_PEDIDOS = "8,10,11,12,13,14,15,16,17,19,20,21,22,23,24";
	/*
	 * la sincronizacion de 13 (materiales) incluye la sincronizacion de clase
	 * mat, material, jerarquia, stock y consulta dinamica (12,13,22,23,24)
	 */
	public static final String OPCIONES_PEDIDOS_AGRUPADO = "8,10,11,13,14,15,16,17,19,20,21";
	// public static final String OPCIONES_PEDIDOS = "15";

	public static final String TABLA_VISTA = "Vistas";
	public static final String TABLA_ROLES_VISTA = "Roles Vistas";
	public static final String TABLA_USUARIO_ROLES = "Usuarios Roles";
	public static final String TABLA_DISPOSITIVOS = "Dispositivos";
	public static final String TABLA_ROLES = "Roles";
	public static final String TABLA_USUARIOS = "Usuarios";

	public static final String TABLA_PLANIFICACION = "Planificacion";
	public static final String TABLA_PLANIFICACION2 = "Planificación";

	public static final String TABLA_FERIADOS = "Feriados";
	public static final String TABLA_EMPLEADO_CLIENTE = "Emp. Cli.";

	public static final String TABLA_CONDICION_EXPEDICION = "Condi. Expedi.";
	public static final String TABLA_AGENDA = "Agenda";
	public static final String TABLA_JERARQUIA = "Jerarquia";
	public static final String TABLA_MATERIAL = "Material";
	public static final String TABLA_MATERIAL_STOCK = "Stock";
	public static final String TABLA_MATERIAL_CONSULTA_DINAMICA = "Consulta Dinamica";
	public static final String TABLA_COMBOS = "Combos";
	public static final String TABLA_TIPOLOGIA = "Tipologia";
	public static final String TABLA_BLOQUEO_ENTREGA = "Bloqueo Entrega.";
	public static final String TABLA_CONDICION_PAGO = "Condi. Pago";
	public static final String TABLA_CLIENTE = "Cliente";
	public static final String TABLAS_PEDIDOS = "Pedidos";
	public static final String TABLA_SEDE = "Destinatarios";
	public static final String TABLA_CONDICIONES_COMERCIALES = "Cond.Comerciales";
	public static final String TABLA_CLASE_MATERIAL = "Clase Material";
	public static final String TABLA_SUPERVISOR = "Supervisores";
	public static final String MENSAJE_PEDIDO = "ESTE PEDIDO TIENE SINCRONIZACIÓN PENDIENTE";

	/* PEDIDOS */
	public static final String ERROR_TRANSACCION = "Ha ocurrido un error que ha impedido completar la transacción.";

	public static final int FROM_SAP = 0;
	public static final int FROM_DB = 1;
	public static final int FROM_SAP_OFFLINE = 2;// Marcelo Moyano PRO-2013-0025 17/04/2013
	
	// ------------------------- MARCELO MOYANO ----------------------- //
	public static final String BANDERA_ACTIVAR_PEDIDO = "X";// --- X PEDIDO BLOQUEADO ------- //
	public static final String EXP_REG_REFERENCIA = "\\d{3}-\\d{3}-\\d{9}";
	public static final String EXP_REG_CLASE_PEDIDO_ZPXX = "[Z][P][01][^15]";
	public static final String MENSAJE_ANTICIPO_RETENCION = "Anticipos Con retención, la referencia tiene que tener el formato xxx-xxx-xxxxxxxxx";
	public static final String MENSAJE_COBRANZA_RETENCION = "Cobranzas Pagos Con retención, la referencia tiene que tener el formato xxx-xxx-xxxxxxxxx";
	public static final String MENSAJE_ERROR_COBRANZA = "<tr><td><font color='red'>Por favor Eliminar Cobranza y volver a digitar</font></td></tr>";
	public static final String MENSAJE_ERROR_ANTICIPO = "<tr><td><font color='red'>Por favor Eliminar Anticipo y volver a digitar</font></td></tr>";
	// --------------------- PROFORMA MARCELO MOYANO ------------------ //
	
	public static final String TECLA_DELETE = "Retroceso";// ---- consulta dinamica ---- //
	public static final String IMPRESORA_NEW = "NEW";
	public static final String NOMNRE_IMPRESORA = "Datecs DPP-350";
	
	
	public static int BANDERA_SINCRONIZANDO = 0;
	public static String ERROR_SINCRONIZACION = "Solo se puede sincronizar en modo Online";
	public static String ERROR_CONSULTA = "Esta consulta requiere conexión.";
	public static int ALTO_COLUMNAS = 22;
	public static final String ERROR_GUI = "No se ha podido desplegar la interfaz gráfica de usuario.";
	public static final int ENTEROS = 0;
	public static final int CADENAS = 1;
	public static final int DECIMALES = 2;
	public static final int VALIDACION_RETENCIONES = 3;
	public static final int VENTANA_CREAR_PEDIDO = 1;
	public static final int VENTANA_EDITAR_PEDIDO = 2;
	public static final int VENTANA_CREAR_PROFORMA = 3;
	public static final int VENTANA_CREAR_COTIZACION = 4;
	public static final String NOMBRE_PROGRAMA = "PROFFLINE";
	public static final int AGREGAR_MATERIAL = -1;
	public static final int SIN_CAMBIOS = 0;
	public static final int ACTUALIZAR_MATERIAL = 1;
	public static final int MAX_NUMERO_INTENTOS = 2;
	public static final int CANTIDAD_REGISTROS_POR_PAGINA = 400;
	public static final int BD_SYNC = 0;
	public static final int BD_TX = 1;
	public static final int BD_FR = 2; // coneccion a Ferias
	public static final Font FUENTE_CABECERA_TABLA = new java.awt.Font("Tahoma", Font.PLAIN, 9);
	
	public static final String PATH = "C:/dbproffline/ConsultaDinamica/";
	public static final String MATERIAL = "Material";
	public static final String MATERIAL_NUEVO = "Material Nuevo";
	public static final String PROMOOFERTA = "PromoOferta";
	public static final String DESCUENTOMANUAL = "DescuentoManual";
	public static final String DESCUENTOPOLITICA = "DescuentoPolitica";
	public static final String TOPCLIENTE = "TopCliente";
	public static final String TOPTIPOLOGIA = "TopTipologia";
	public static final String VENTACRUZADA = "Venta Cruzada";
	public static final String MENSAJE_ERROR_VISITA_CLIENTE_EXISTENTE = "El cliente ya tiene una visita para esa fecha!!";

}