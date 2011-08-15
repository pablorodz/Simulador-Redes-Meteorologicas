package logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import Main.Main;   // Para poder usar limpiarResumenes()

/**
 *
 */
public class SimNoGui {
    
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(SimNoGui.class .getName());
    
    static EstacionBase base = null;    // Es necesario ponerlo a null ??
    
//    public static void main(String args[]) {
//        
//        // Limpio el LOG
//        Loggers.clearFileLog();
//        // Apago todos los handlers y habilito solo el que escribe a un archivo
//        Loggers.setLevel(Level.OFF);
//        Loggers.fileLogger(Level.ALL);
//        // Limpio el directorio de los resumenes
//        limpiarResumenesDir();
//        
//        // Menu principal
//        main();
//    }

    // menu principal
    public void main() {
        Scanner scan = new Scanner(System.in);
        boolean salir = false;
        Integer seleccion;
        
        while(!salir) {
            // Imprimo menu
            System.out.printf("\n\tMENU\n"
                    + "(1) Crear Red\n"
                    + "(2) Modificar Red\n"
                    + "(3) Cargar Ejemplo\n"
                    + "(4) Correr Simulación\n"
                    + "(5) Ver resumen\n"
                    + "(6) Limpiar resumenes\n"
                    + "(7) Salir\n"
                    + "\nSeleccione menu ingresando el número correspondiente: ");
            
            // Busco y ejecuto la seleccion
            try{
                seleccion = scan.nextInt();
                
                if (seleccion < 1 || seleccion >7)
                    throw new InputMismatchException();
                
                salir = ejecutarSeleccion(seleccion);
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }
    }
    /*
     * Metodo que decide la accion a seguir dependiendo de la accion del usuario
     * 
     * @return boolean True=salir
     */
    private boolean ejecutarSeleccion(Integer seleccion) {
        boolean salir = false;
        
        switch(seleccion) {
            case 1: {
                try {
                    base = nuevaRed();
                } catch (CreacionException ex) {
                    Logger.getLogger(SimNoGui.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
                break;
            }
                
            case 2: base = modificarRed(base); break;
                
            case 3: base = ejemplo1(base); break;
                
            case 4: start(base); break;
                
            case 5: {
                Vector<String> direcciones = base.getResumen();
                System.out.println("Los resumenes se encuentran en:"); 
                for (String dir : direcciones) {
                    System.out.printf("\t%s\n", dir);
                }
                break;
            }
                
            case 6: {
                try {
                    if (Main.limpiarResumenesDir() == false)
                        Logger.getLogger("").log(Level.WARNING, "No se pudo "
                                + "limpiar el directorio de resumenes");
                }
                catch (NullPointerException ne) {
                    Logger.getLogger("").log(Level.WARNING, "No se pudo limpiar"
                            + " el directorio de resumenes, no existe");
                }
                break;
            }
                
            case 7: salir = true; break;
        }
        
        return salir;
    }    

    /*
     * Se carga un ejemplo en el simulador
     */
    private EstacionBase ejemplo1(EstacionBase base) {
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
            // Creo una subestacion
            met1 = new EstacionMet();

                // Creo Sensores
                sensor1 = new SensorHum();
                // Agrego los sensores a la sub estacion
                met1.agregarSensor(sensor1, met1.getID());

            // Agrego la subestacion a la estacion base
            base.agregarEstacion(met1, base.getID());

            // Creo una subestacion
            met2 = new EstacionMet();

                // Creo Sensores
                sensor2 = new SensorViento();
                sensor3 = new SensorTemp();

                // Agrego los sensores a la sub estacion
                met2.agregarSensor(sensor2, met2.getID());
                met2.agregarSensor(sensor3, met2.getID());

            // Agrego la subestacion a la estacion base
            base.agregarEstacion(met2, base.getID());

            // Creo una subestacion
            met3 = new EstacionMet();

                // Creo Sensores
                sensor5 = new SensorHum();
                // Agrego los sensores a la sub estacion
                met3.agregarSensor(sensor5, met3.getID());

                // Creo una subestacion
                met4 = new EstacionMet();

                    // Creo Sensores
                    sensor4 = new SensorPluv();
                    // Agrego los sensores a la sub estacion
                    met4.agregarSensor(sensor4, met4.getID());

                // Agrego la subestacion a la estacion base
                met3.agregarEstacion(met4, met3.getID());

            // Agrego la subestacion a la estacion base
            base.agregarEstacion(met3, base.getID());
                
        } catch (CreacionException ex) {
            Logger.getLogger(SimNoGui.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        return base;
    }

    /*
     * Metodo donde se ejcuta la simulacion
     */
    private void start(EstacionBase base) {
        boolean stop = false;
        boolean sigo = false;
        Stack<PaqueteDatos> datos = new Stack();
        Scanner scan = new Scanner(System.in);
        TimerListener timerListener;
        javax.swing.Timer timer;
        int nAct = 0;
        
        // Pido tiempo de ejecucion en numero de actualizaciones (mediciones)
        System.out.print("Numero de actualizaciones: ");
        try{
            nAct = scan.nextInt();

            if (nAct < 0)
                throw new InputMismatchException();

        } catch(InputMismatchException ex) {
            System.out.println("Simbolo ingresado no válido.");
        }

        // Creo el listener e inicio el timer
        timerListener = new TimerListener();
        timer = new javax.swing.Timer(1000, timerListener);

//        while(stop != true) {
        for (int i=0; i<nAct; i++) {
            System.out.println("Esperando actualizacion... ");
            timer.restart();
            
            while (!sigo) {
                // Miro si se activo el timer
                sigo = timerListener.getEstado();
                // Miro si se apreto una tecla
            }
            
            // Paro el timer
            timer.stop();
            
            // Actualizo
            datos = base.actualizar();
            
            // Muestro info
            while( !(datos.empty()) ) {
                datos.pop().printDatos();
            }
            
            // Reinicion variables
            sigo = false;
            timerListener.reset();

        }
    }
    
    /*
     * @return Si se limpio correctamente el directorio
     */
//    private boolean limpiarResumenesDir() throws NullPointerException {
//        File dir = new File("resumenes");
//
//        if (!dir.exists() || !dir.isDirectory())
//            throw new NullPointerException("No existe directorio.");
//        
//        Logger.getLogger("").log(Level.INFO, "Limpiando directorio de resumenes");
//        
//        File[] archivos = dir.listFiles();
//        int numArchivos = dir.list().length;
//        boolean exito = true;
//        
//        for (int i=0; i<numArchivos; i++) {
//            //System.out.println(archivos[i].getName());
//            if (!archivos[i].delete()){
//                exito = false;
//                break;
//            }                
//        }
//        
//        return exito;
//    }

    private EstacionBase nuevaRed() throws CreacionException {
        EstacionBase base = new EstacionBase();
        int numSubEstaciones = 0;
        // Ingreso de datos 
        Scanner scan = new Scanner(System.in);
        boolean valido = false;
        
        System.out.printf("\n### Creando nueva red ###\n");
        
        // Pregunto numero de sub-estaciones, maximo 4
        while (!valido) {
            System.out.printf("\n-Número de sub-estaciones de la Estacion Base (maximo 4): ");
            try{
                numSubEstaciones = scan.nextInt();
                // Chequeo que sea menor que 4
                if (numSubEstaciones < 0 || numSubEstaciones > 4)
                    throw new InputMismatchException();
                
                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }

        // Creo y agrego las sub-estaciones
        for (int i=0; i<numSubEstaciones; i++) {
            base.agregarEstacion( crearEstacion(), base.getID() );
        }

        return base;
    }
    
    private Estacion crearEstacion() throws CreacionException {
        Estacion estacion = new EstacionMet();
        String nombre = null;
        int numSubEstaciones = 0;
        int numSensores = 0;
        // Ingreso de datos 
        Scanner scan = new Scanner(System.in);
        boolean valido = false;
        
        System.out.printf("\n### Creando %s ###\n", estacion.toString());
        
// Dejo nombre por defecto porque no lo uso un ningun lado, ni para mostrar en pantalla
//        // Priguntar si la sub-estacion tiene nombre, sino queda el por defecto
//            // Si tiene, lo seteo
//            estacion.setNombre(nombre);

        // Pregunto si tiene sensores, maximo 4
        while (!valido) {
            System.out.printf("\n-Número de sensores de %s (maximo 4): ", estacion.toString());
            try{
                numSensores = scan.nextInt();
                // Chequeo que sea menor que 4
                if (numSensores < 0 || numSensores > 4)
                    throw new InputMismatchException();
                
                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido");
            }
        }
        // Limpio variable
        valido = false;

        // Si tiene, creo los sensores y los cargo
        for (int i=0; i<numSensores; i++) {
            estacion.agregarSensor( crearSensor(), estacion.getID() );
        }

        // Pregunto si tiene sub-estaciones, maximo 3
        while (!valido) {
            System.out.printf("\n-Número de sub-estaciones de %s (maximo 3): ", estacion.toString());
            try{
                numSubEstaciones = scan.nextInt();
                // Chequeo que sea menor que 3
                if (numSubEstaciones < 0 || numSubEstaciones > 3)
                    throw new InputMismatchException();
                
                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }
        valido = false;

        // Si tiene, creo las sub-estaciones y las agrego
        for (int i=0; i<numSubEstaciones; i++) {
            estacion.agregarEstacion( crearEstacion(), estacion.getID() );
        }
            
        return estacion;
    }

    private Sensor crearSensor() {
        Sensor sensor = null;
        int tipo = 0;
        // Ingreso de datos 
        Scanner scan = new Scanner(System.in);
        boolean valido = false;
        
        System.out.printf("\n### Creando Sensor ###\n");
        
        // Pregunto el tipo e imprimo las opciones
        while (!valido) {
            System.out.printf("\n-Tipo de Sensor: ");
            // Imprimo menu
            System.out.printf("\n\tTipos permitidos:\n"
                    + "\t\t(1) Sensor de Humedad\n"
                    + "\t\t(2) Sensor de Temperatura\n"
                    + "\t\t(3) Sensor de Viento\n"
                    + "\t\t(4) Pluviómetro\n"
                    + "\nSeleccione tipo ingresando el número correspondiente: ");

            // Busco y ejecuto la seleccion
            try{
                tipo = scan.nextInt();
                
                if (tipo < 1 || tipo > 4)
                    throw new InputMismatchException();
                
                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }

        // Segun la opocion(tipo) se crea el sensor
        switch (tipo) {
            case 1: sensor = new SensorHum(); break;
            case 2: sensor = new SensorTemp(); break;
            case 3: sensor = new SensorViento(); break;
            case 4: sensor = new SensorPluv(); break;
        }
        
        return sensor;
    }
    
    /**
     * @brief Modifica una red existente
     * 
     * Modifica la red existente cuya estacion base es pasada como argumento.
     * En este metodo se puede agregar una sub-red, eliminar una sub-red, 
     * agregar un sensor o eliminar un sensor.
     * Todo el proceso es guiado a base de preguntas.
     * 
     * @param base EstacionBase del la red
     * 
     * @return EstacionBase modificada
     */
    private EstacionBase modificarRed(EstacionBase base) {
        Scanner scan = new Scanner(System.in);
        int seleccion = 0;
        boolean valido = false;
        boolean salir = false;
        
        while (!salir) {
            System.out.printf("\n### Modificando red existente ###\n");
            
            // Imprimo menu y pregunto que se decea hacer
            System.out.printf("\n-Que decea hacer? \n"
                    + "\t(1) Agregar estacion (crear sub-red)\n"
                    + "\t(2) Eliminar estacion (eliminar sub-red)\n"
                    + "\t(3) Agregar sensor\n"
                    + "\t(4) Eliminar sensor\n"
                    + "\t(5) Volver al menu principal\n"
                    + "\nSeleccione accion ingresando el número correspondiente: ");

            // Busco y ejecuto la seleccion
            try{
                seleccion = scan.nextInt();
                
                if (seleccion < 1 || seleccion > 5)
                    throw new InputMismatchException();
                
                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
            
            // si la seleccion es valida, ejecuto
            if (valido) {
                switch (seleccion) {
                    case 1: base = crearSubRed(base); break;
                    case 2: base = eliminarSubRed(base); break;
                    case 3: base = agregarSensor(base); break;
                    case 4: base = eliminarSensor(base); break;
                    case 5: salir = true;
                }
            }
        }
        
        return base;
    }

    /**
     * @brief Crea una Sub-Red dentro de la red
     * 
     * Modifica la red existente cuya estacion base es pasada como argumento.
     * Se agrega una Estacion y toda la red que se encuentra aderida a ésta.
     * La estacion y toda su red se crea en el proceso.
     * Todo el proceso es guiado a base de preguntas.
     * 
     * @param base EstacionBase del la red
     * 
     * @return EstacionBase modificada
     */
    private EstacionBase crearSubRed(EstacionBase base) {
        Scanner scan = new Scanner(System.in);
        int padreID = -1;
        boolean valido = false;
        
        System.out.printf("\n### Creando Sub-Red ###\n");

        // Pregunto en la red de que estacion debe ser ubicada la nueva estacion
        while (!valido) {
            System.out.printf("\n-ID de la estacion padre (Base=0): ");
            try{
                padreID = scan.nextInt();
                // Chequear que sea valido, padreID > 0 y < base.getSiguienteID()
                if (padreID < 0|| padreID > base.getSiguienteID())
                    throw new InputMismatchException();

                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }
        
        if (valido) {
            try {
                valido = base.agregarEstacion( crearEstacion(), padreID );
                // Chequeo la correcta insercion
                if (!valido) 
                    throw new CreacionException( String.format("No se puede "
                            + "crear la Sub-Red, debido a que la estacion no "
                            + "puede ser insertada en la red de la "
                            + "Estacion%d", padreID) );
                
            } catch (CreacionException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return base;
    }

    /**
     * @brief Elimina una Sub-Red dentro de la red
     * 
     * Modifica la red existente cuya estacion base es pasada como argumento.
     * Se elimina una Estacion y toda la red que se encuentra aderida a ésta.
     * Todo el proceso es guiado a base de preguntas.
     * 
     * @param base EstacionBase del la red
     * 
     * @return EstacionBase modificada
     */
    private EstacionBase eliminarSubRed(EstacionBase base) {
        Scanner scan = new Scanner(System.in);
        int estacionID = -1;
        boolean valido = false;
        
        System.out.printf("\n### Eliminando una Sub-Red ###\n");

        // Pregunto el ID de la estacion a eliminar
        while (!valido) {
            System.out.printf("\n-ID de la estacion a eliminar (>0): ");
            try{
                estacionID = scan.nextInt();
                // Chequear que sea valido, estacionID > 1
                // Por ahi si la red puede llegar a ser muy grande tambien poner
                // como condicion que sea menor que 1000 (ID base de sensor).
                if (estacionID < 1)
                    throw new InputMismatchException();

                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }
        
        if (valido) {
            try {
                valido = base.eliminarEstacion(estacionID);
                // Chequeo la correcta insercion
                if (!valido) 
                    throw new CreacionException( String.format("No se puede "
                            + "crear la Sub-Red, debido a que no se encuentra"
                            + " la Estacion%d", estacionID) );
                
            } catch (CreacionException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return base;
    }

    /**
     * @brief Agrega un sensor dentro de la red
     * 
     * Modifica la red existente, cuya estacion base es pasada como argumento, 
     * agregando un sensor.
     * Todo el proceso es guiado a base de preguntas.
     * 
     * @param base EstacionBase del la red
     * 
     * @return EstacionBase modificada
     */
    private EstacionBase agregarSensor(EstacionBase base) {
        Scanner scan = new Scanner(System.in);
        int padreID = -1;
        boolean valido = false;
        
        System.out.printf("\n### Agregando Sensor ###\n");

        // Pregunto en la red de que estacion debe ser ubicado el nuevo sensor
        while (!valido) {
            System.out.printf("\n-ID de la estacion padre: ");
            try{
                padreID = scan.nextInt();
                // Chequear que sea valido, padreID > 1 y <base.getSiguienteID;
                if (padreID < 1 || padreID > base.getSiguienteID())
                    throw new InputMismatchException();

                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }
        
        if (valido) {
            valido = base.agregarSensor( crearSensor(), padreID );
            // Chequeo la correcta insercion
            if (!valido) 
                System.out.printf( String.format("No se puede agregar el "
                        + "sensor a  la red de la Estacion%d", padreID) );
        }
        
        return base;
    }

    /**
     * @brief Elimina un Sensor dentro de la red
     * 
     * Modifica la red existente cuya estacion base es pasada como argumento, 
     * eliminando un sensor.
     * Todo el proceso es guiado a base de preguntas.
     * 
     * @param base EstacionBase del la red
     * 
     * @return EstacionBase modificada
     */
    private EstacionBase eliminarSensor(EstacionBase base) {
        Scanner scan = new Scanner(System.in);
        int sensorID = -1;
        boolean valido = false;
        
        System.out.printf("\n### Eliminando Sensor ###\n");

        // Pregunto el ID del sensor a eliminar
        while (!valido) {
            System.out.printf("\n-ID del sensor a eliminar (>=1000): ");
            try{
                sensorID = scan.nextInt();
                // Chequear que sea valido, sensorID >= 1000
                if (sensorID < 1000)
                    throw new InputMismatchException();

                valido = true;
            } catch(InputMismatchException ex) {
                System.out.println("Simbolo ingresado no válido.");
            }
        }
        
        if (valido) {
            valido = base.eliminarSensor(sensorID);
            // Chequeo la correcta insercion
            if (!valido) 
                System.out.printf("No se puede eliminar el sensor debido a que"
                        + " no se encuentra");
        }
        
        return base;
    }
    
    /* *** Clase para trabajar con el timer *** */
    class TimerListener implements ActionListener {
        // Un estado para avisar que se disparo el timer
        private boolean activar;
        
        public void actionPerformed(ActionEvent e) {
            // Se disparo el timer
            activar = true;
        }
        
        public boolean getEstado() { 
            if (activar)
                // Si no pongo esto, no funciona ¿WTF?
                System.out.println();
            
            return activar; 
        }
        
        public void reset() { 
            activar = false;
        }
    }
}