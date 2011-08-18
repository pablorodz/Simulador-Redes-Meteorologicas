/*
 * Simulador de Redes Meteorológicas
 * Copyright 2011 (C) Rodríguez Pablo Andrés
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; under version 2 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses>.
 */ 

/**
 *  @file Main.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package Main;

import gui.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.*;

/**
 * Clase princpial de la aplicaion
 */
public class Main {

    // Variable que denota la utilizacion de la GUI
    public static boolean GUI = true;
    
    /**
     * Metodo de ejecucion de la aplicacion.
     * 
     * @param args Selecciona el uso de GUI (true/false)
     */
    public static void main(String args[]) {
        
        if (args.length != 0) {
            if (args.length == 1 && args[0].equals(true))
                GUI = true;
            else
                System.err.println("Solo se acepta un argumento: true/false");
        }
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
    
    /**
     * Elimina todos los archivos de la carpeta resumenes
     * 
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
            if (!archivos[i].delete()){
                exito = false;
                break;
            }                
        }
        
        return exito;
    }

}
