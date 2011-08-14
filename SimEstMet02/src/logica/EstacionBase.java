/**
 *  @file EstacionBase.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.Stack;
import java.util.logging.Logger;

/*
 *  Estacion base.
 *      * Solo se puede crear una. Esto se chequea en el constructor de Estacion
 *          debido a que no puedo preguntar primero si ya existe una y luego
 *          llamar al constructor de la superclase.
 *      * ID = 0.
 *      * La agrgacion es por estar el programa visto desde la estacion base (1/12/2010).
 *      * Lista de Estaciones Meteorologicas
 */
public class EstacionBase extends Estacion {

    /* *** Propiedades *** */

    /// El arreglo que indica todas estaciones que pertenecen a la estacion base.
    // private Estacion[] totalEstaciones;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(EstacionBase.class .getName());

    /* *** Constructores *** */

    // Como se va a crear una unica estacion base, su nombre va a estar definido
    public EstacionBase() throws CreacionException {
        this("Estacion Base");
        // En caso de tener poder crear mas de una estacion base, el nombre va a
        // ser mas generico.
        // super( Tipo.BASE );
    }

    public EstacionBase(String nombre) throws CreacionException {
        super( nombre, Tipo.BASE );
    }

    @Override
    public Stack<PaqueteDatos> actualizar() {
        // Ejecuta el metodo original, pero no usa su salida
        Stack<PaqueteDatos> datos = super.actualizar();
        
        return datos;
    }

    @Override
    public boolean agregarSensor(Sensor sensorNuevo, int padreID) {
        boolean insertado = false;
        
        int i = 0;
        int redSize = redEstaciones.length;
        while(!insertado && i<redSize) {
            if (redEstaciones[i] != null)
                insertado = redEstaciones[i].agregarSensor(sensorNuevo, padreID);
            i++;
        }
        
        return insertado;
    }

    @Override
    public boolean eliminarSensor(Sensor sensorElim) {
        boolean eliminado = false;
        
        int i = 0;
        int redSize = redEstaciones.length;
        while(!eliminado && i<redSize) {
            if (redEstaciones[i] != null)
                eliminado = redEstaciones[i].eliminarSensor(sensorElim);
            i++;
        }
        
        return eliminado;
    }

    @Override
    public boolean eliminarSensor(int sensorElimID) {
        boolean eliminado = false;
        
        int i = 0;
        int redSize = redEstaciones.length;
        while(!eliminado && i<redSize) {
            if (redEstaciones[i] != null)
                eliminado = redEstaciones[i].eliminarSensor(sensorElimID);
            i++;
        }
        
        return eliminado;
    }
    
// EstacionBase se deberia crear un resumen con todos los resumnes de su red. 
// Por el momento esto no lo hace debido a complicaciones a la hora de copiar un
// resumen(XML) en otro, se pierde la estructura.
//    @Override
//    public Vector<String> getResumen() {
//        // Direcciones de todos los resumes de la red
//        Vector<String> direcciones = super.getResumen();
//
//        // Crear resumen con los resumenes
//        
//        return direcciones;
//    }

}
