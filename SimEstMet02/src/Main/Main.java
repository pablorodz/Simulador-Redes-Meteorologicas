/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import gui.*;
import java.io.File;
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
        
// No se pueden ejecutar antes de la gui ¿WTF?
//        // Limpio el LOG
//        Loggers.clearFileLog();
//        // Apago todos los handlers y habilito solo el que escribe a un archivo
//        Loggers.setLevel(Level.OFF);
//        Loggers.fileLogger(Level.ALL);
//        // Limpio el directorio de los resumenes
//        limpiarResumenesDir();
        
        // Si se usa GUI --> gui.MainWindow.main()
        // Si no se usa GUI --> logica.SimNoGui.menu()
        if (GUI) {
            MainWindow.main(null);
        }
        else {
            // Limpio el LOG
            Loggers.clearFileLog();
            // Apago todos los handlers y habilito solo el que escribe a un archivo
            Loggers.setLevel(Level.OFF);
            Loggers.fileLogger(Level.ALL);
            // Limpio el directorio de los resumenes
            limpiarResumenesDir();

            SimNoGui app = new SimNoGui();
            app.main();
        }

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
