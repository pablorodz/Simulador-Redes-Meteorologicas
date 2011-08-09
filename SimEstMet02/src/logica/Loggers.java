/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logica;

import java.io.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clase para setear el nivel de todos los loggers.
 */
public class Loggers {

    /*
     * Setea el nivel de todos los Loggers y Handlers
     */
    public static void setLevel(Level level) {
        // Seteo el nivel de los Handlers
        Handler[] handlers = LogManager.getLogManager().getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(level);
        }

        // Seteo el nivel de los Loggers
        String logger;
        Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
        while (loggers.hasMoreElements()) {
            logger = loggers.nextElement();
//            System.out.println( logger );
            LogManager.getLogManager().getLogger(logger).setLevel(level);
        }
    }

    /*
     * Crea un handler para manejar el archivo de LOG.
     * Si el archivo no existe lo crea.
     * Se deshabilitan todos los handlers salvo este. En caso de querer 
     * habilitarlos usar setLevel(level)
     */
    public static void fileLogger(Level level) {
        try {
            // Desactivo los handlers
            setLevel(Level.OFF);
            // Creo el handler del archivo
            FileHandler handler;
            handler = new FileHandler("logFile", true);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
            // Seteo el nivel !!
            handler.setLevel(level);
            
            // Agrego el handler y seteo el nivel
            String logger;
            Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
            while (loggers.hasMoreElements()) {
                logger = loggers.nextElement();
    //            System.out.println( logger );
                LogManager.getLogManager().getLogger(logger).addHandler(handler);
                LogManager.getLogManager().getLogger(logger).setLevel(level);
            }

        } catch (IOException ex) {
            Logger.getLogger(Loggers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Loggers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * Limpia el archivo LOG. Esto se hace creando el archivo LOG, exista o no.
     */
    public static void clearFileLog() {
        PrintWriter result = null;
        
        try {
            result = new PrintWriter(new FileWriter("logFile"));
        } catch (IOException ex) {
            Logger.getLogger(Loggers.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            // Finish by closing the files, whatever else may have happened.
            if (result != null)
                result.close();
        }

    }
}
