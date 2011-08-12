package Main;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.*;

/**
 *
 */
public class TestSimulador {

    static EstacionBase base = null;
    
    public static void main(String args[]) {
        boolean salir = false;
        
        // Limpio el LOG
        Loggers.clearFileLog();
        // Apago todos los handlers y habilito solo el que escribe a un archivo
        Loggers.setLevel(Level.OFF);
        Loggers.fileLogger(Level.ALL);
        // Limpio el directorio de los resumenes
        limpiarResumenesDir();
        
        while(salir == false) {
            // Menu
            Scanner scan = new Scanner(System.in);
            Integer seleccion;

            // Imprimo menu
            System.out.printf("\n\tMENU\n"
                    + "(1) Crear Red\n"
                    + "(2) Modificar Red\n"
                    + "(3) Cargar Ejemplo\n"
                    + "(4) Correr Simulación\n"
                    + "(5) Ver resumen\n"
                    + "(6) Limpiar resumenes\n"
                    + "(7) Configurar salida\n"
                    + "(8) Salir\n"
                    + "\nSeleccione menu ingresando el número correspondiente.");
            // # Agregar limpiar registros
            
            // Busco y ejecuto la seleccion
            try{
                seleccion = scan.nextInt();
                salir = ejecutarSeleccion(seleccion);
            } catch(InputMismatchException ex) {
                Logger.getLogger(TestSimulador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * Metodo que decide la accion a seguir dependiendo de la accion del usuario
     * 
     * @return boolean True=salir
     */
    private static boolean ejecutarSeleccion(Integer seleccion) {
        boolean salir = false;
        
        switch(seleccion) {
            case 1: System.out.println("Not implemented yet."); break;
            case 2: System.out.println("Not implemented yet."); break;
            case 3: base = ejemplo1(); break;
            case 4: start(base); break;
            case 5: {
                System.out.println("El resumen se encuentra en: " + base.getResumen().firstElement());
                break;
            }
            case 6: {
                try {
                    if (limpiarResumenesDir() == false)
                        Logger.getLogger("").log(Level.WARNING, "No se pudo "
                                + "limpiar el directorio de resumenes");
                }
                catch (NullPointerException ne) {
                    Logger.getLogger("").log(Level.WARNING, "No se pudo limpiar"
                            + " el directorio de resumenes, no existe");
                }
                break;
            }
            case 7: System.out.println("Not implemented yet."); break;
            case 8: salir = true; break;
        }
        
        return salir;
    }    

    /*
     * Se carga un ejemplo en el simulador
     */
    public static EstacionBase ejemplo1() {
        EstacionBase base = null;
            EstacionMet met1 = null;
                SensorHum sensor1 = null;
            EstacionMet met2 = null;
                SensorViento sensor2 = null;
                SensorTemp sensor3 = null;
            EstacionMet met3 = null;
                SensorHum sensor5 = null;
                EstacionMet met4 = null;
                    SensorPluv sensor4 = null;
                
        try {
            // Creo estacion Base
            base = new EstacionBase();
            
                // Creo una subestacion
                met1 = new EstacionMet();
                
                    // Creo Sensores
                    sensor1 = new SensorHum();
                    // Agrego los sensores a la sub estacion
                    met1.agregarSensor(sensor1);

                // Agrego la subestacion a la estacion base
                base.agregarEstacion(met1);
                
                // Creo una subestacion
                met2 = new EstacionMet();
                
                    // Creo Sensores
                    sensor2 = new SensorViento();
                    sensor3 = new SensorTemp();
                    
                    // Agrego los sensores a la sub estacion
                    met2.agregarSensor(sensor2);
                    met2.agregarSensor(sensor3);

                // Agrego la subestacion a la estacion base
                base.agregarEstacion(met2);
                
                // Creo una subestacion
                met3 = new EstacionMet();
                
                    // Creo Sensores
                    sensor5 = new SensorHum();
                    // Agrego los sensores a la sub estacion
                    met3.agregarSensor(sensor5);

                    // Creo una subestacion
                    met4 = new EstacionMet();

                        // Creo Sensores
                        sensor4 = new SensorPluv();
                        // Agrego los sensores a la sub estacion
                        met4.agregarSensor(sensor4);

                    // Agrego la subestacion a la estacion base
                    met3.agregarEstacion(met4);

                // Agrego la subestacion a la estacion base
                base.agregarEstacion(met3);
                
        } catch (CreacionException ex) {
            Logger.getLogger(TestSimulador.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        return base;
    }

    /*
     * Metodo donde se ejcuta la simulacion
     */
    private static void start(EstacionBase base) {
        boolean stop = false;
        Stack<PaqueteDatos> datos = new Stack();
        
//        while(stop != true) {
        for (int i=0; i<5; i++) {
            // Inicio un timer
            // Para cuando haya un relor, si es que lo hay

            // Temporal mientras no haya un reloj o algo parecido.
            // Espero a que se aprete una tecla
            try {
                System.out.printf("\nPresione Enter para actualizar\n");
                System.in.read();
            } catch (IOException ex) {
                Logger.getLogger(TestSimulador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Actualizo
            datos = base.actualizar();
            
            // Muestro info
            while( !(datos.empty()) ) {
                datos.pop().printDatos();
            }
        }
        
        // Guardo resumen
        //base.guardarResumen();
        
    }
    
    /*
     * @return Si se limpio correctamente el directorio
     */
    private static boolean limpiarResumenesDir() throws NullPointerException {
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