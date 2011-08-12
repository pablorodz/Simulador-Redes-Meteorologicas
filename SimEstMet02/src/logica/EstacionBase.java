/**
 *  @file EstacionBase.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.*;

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
    private final static Logger LOGGER = Logger.getLogger(EstacionMet.class .getName());

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
    
    /*
     * @brief Ubicacion de los resumenes, en un Vector<String>
     * 
     * Retorna la ubicacion de los resumenes de esta estacion y de sus 
     * sub-estaciones, en un Vector<String>.
     * Se guardan en esa forma, primero la direccion propia y luego la de las 
     * sub-estaciones.
     * Como la estacion base no tiene sensores, se elimina a esta de las 
     * direcciones.
     * Este metodo no tiene sentido y menos que se encuentre getResumen() en 
     * Estacion. Esto es asi porque en getResumen() de EstacionBase se deberia
     * crear un resumen con todos los resumnes de su red. Por el momento esto 
     * no lo hace debido a complicaciones a la hora de copiar un resumen(XML) 
     * en otro (se pierde la estructura).
     * A pesar de que no haya un resumen de la EstacionBase se podria pensar de
     * la siguiente manera:
     *   * Las sub-estaciones tiene sus resumenes en sus servidores.
     *   * getResumen() de estacionBase toma estas direcciones y hace una copia local.
     *   * getResumen() retorna la direccion en disco donde estan estas copias.
     * 
     * @return Direccion donde se encuentran los resumenes.
     */
    @Override
    public Vector<String> getResumen() {
        // Direcciones de todos los resumes de la red
        Vector<String> direcciones = super.getResumen();

        // Antes de retornar las direcciones, borra la propia del arreglo
        direcciones.removeElementAt(0);
        
        return direcciones;
    }
    
}
