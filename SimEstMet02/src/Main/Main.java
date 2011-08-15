/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import gui.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.*;

/**
 *
 * @author pablo
 */
public class Main {

    public static final boolean GUI = true;
    
    public static void main(String args[]) {
        
        // Limpio el LOG
        Loggers.clearFileLog();
        // Redirecciono la salida estandar de err a un archivo de log
        Loggers.redirectErr(true);
        // Limpio el directorio de los resumenes
        limpiarResumenesDir();
        
        // Si se usa GUI --> gui.MainWindow.main(null)
        // Si no se usa GUI --> logica.SimNoGui.main()
        if (GUI) {
            MainWindow.main(null);
        }
        else {
            SimNoGui app = new SimNoGui();
            app.main();
        }

        Loggers.redirectErr(false);

    }
    
    /*
     * @return Si se limpio correctamente el directorio
     */
    public static boolean limpiarResumenesDir() throws NullPointerException {
        File dir = new File("resumenes");

        if (!dir.exists() || !dir.isDirectory())
            throw new NullPointerException("No existe directorio.");
        
        Logger.getLogger("").log(Level.INFO, "Limpiando directorio de resumenes");
        
        File[] archivos = dir.listFiles();
        int numArchivos = dir.list().length;
        boolean exito = true;
        
        for (int i=0; i<numArchivos; i++) {
            //System.out.println(archivos[i].getName());
            if (!archivos[i].delete()){
                exito = false;
                break;
            }                
        }
        
        return exito;
    }

}
