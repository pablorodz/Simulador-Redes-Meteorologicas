/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logica;

import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Clase para setear el nivel de todos los loggers.
 */
public class Loggers {

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
            System.out.println( logger );
            LogManager.getLogManager().getLogger(logger).setLevel(level);
        }
    }

}
