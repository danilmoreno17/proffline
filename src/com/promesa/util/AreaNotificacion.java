package com.promesa.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AreaNotificacion {
	static TrayIcon trayIcon;
	
	public static void Notificar(){
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("imagenes/icono.jpg");
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Terminar Proceso");
            defaultItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); 
                }
            });
            popup.add(defaultItem);
            trayIcon = new TrayIcon(image, "Proffline se esta ejecutando", popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("Proffline", "PROMESA", TrayIcon.MessageType.INFO);                          
                }
            });
            try {            	
                tray.add(trayIcon);
            } catch (AWTException e) {
            	Util.mostrarExcepcion(e);
            }
        }
	}
}