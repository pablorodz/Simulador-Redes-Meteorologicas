package Main;

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
        
        Loggers.clearFileLog();
        Loggers.setLevel(Level.OFF);
        Loggers.fileLogger(Level.ALL);
        
        while(salir == false) {
            // Menu
            Scanner scan = new Scanner(System.in);
            Integer seleccion;

            // Imprimo menu
            System.out.printf("\tMENU\n"
                    + "(1) Crear Red\n"
                    + "(2) Modificar Red\n"
                    + "(3) Cargar Ejemplo\n"
                    + "(4) Correr Simulación\n"
                    + "(5) Ver resumen\n"
                    + "(6) Ver úlima adquisición\n"
                    + "(7) Configurar salida\n"
                    + "(8) Salir\n"
                    + "\nSeleccione menu ingresando el número correspondiente.");
            
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
            case 5: System.out.println("Not implemented yet."); break;
            case 6: System.out.println("Not implemented yet."); break;
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
        EstacionMet met2 = null;
        SensorHum sensor1 = null;
        SensorHum sensor2 = null;
        SensorTemp sensor3 = null;
    
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
                    sensor2 = new SensorHum();
                    sensor3 = new SensorTemp();
                    
                    // Agrego los sensores a la sub estacion
                    met2.agregarSensor(sensor2);
                    met2.agregarSensor(sensor3);

                // Agrego la subestacion a la estacion base
                base.agregarEstacion(met2);
                
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
        
        while(stop != true) {
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

}