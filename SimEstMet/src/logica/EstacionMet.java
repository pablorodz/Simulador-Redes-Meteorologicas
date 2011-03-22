/**
 *  @file EstacionMet.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.logging.Level;
import java.util.logging.Logger;

/* 
 *  Estacion Meteorologica
 *      * Lista de sensores (maximo 4)
 *      * Lista de Estaciones Meteorologicas (maximo 3) ??
 *      * Lanza una excepcion, pero esta excepcion nunca deberia pasar. 
          Asi que en el try-catch no se deberia atrapar nunca nada.
 *  * La estacion base le pide a cada subestacion los datos --> EstacionMet.pedirData().
 */
public class EstacionMet extends Estacion {
    private static int IDsiguiente = 1;
    /// El arreglo que indica los sensores conectados a la estacion.
    private Sensor[] redSensores;
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(EstacionMet.class .getName());

    public EstacionMet( String nombre, Grafo grafo ) throws CreacionException {
        super( IDsiguiente, nombre, grafo );
        IDsiguiente++;

        // Creo la red de sensores de la estacion.
        redSensores = new Sensor[4];  // Maximo 4 sensores
        for ( Sensor sensor : redSensores )
            sensor = null;

    }

    public EstacionMet( Grafo grafo ) throws CreacionException {
        /*
         * Se puede usar tambien el metodo propio de la superclase, la cual
         * hace lo mismo, 
         *
         *   super( IDsiguiente );
         *   IDsiguiente++;
         *
         * pero de esta manera me parece mas correcto.
         */
        this( "Estacion" + Integer.toString(IDsiguiente), grafo );
    }

    /**
     *  Agrega un sensor a la estacion, si es que hay lugar.
     */
    public void agregarSensor(Sensor sensorNuevo)
            throws ArrayIndexOutOfBoundsException {
        boolean insertado = false;
        for (Sensor sensor : redSensores)
            if ( sensor == null ) {
                sensor = sensorNuevo;
                insertado = true;
            }

        if ( !insertado )
            throw new ArrayIndexOutOfBoundsException( "No se puede insertar el"
                    + " sensor. No una coneccion disponible" );

        LOGGER.log(Level.INFO, String.format("Agregando sensor %1$ a la red "
                + "de la estacion %2$", sensorNuevo.getID(), ID));
    }

    /**
     *  Elimina una coneccion a un sensor (elimina el sensor del arreglo
     *  redSensores) si es que existe.
     */
    public void eliminarSensor(Sensor sensorElim) throws ObjectNotFoundException {
        boolean eliminado = false;
        for (Sensor sensor : redSensores)
            if ( sensor == sensorElim ) {
                sensor = null;
                eliminado = true;
            }

        if ( !eliminado )
            throw new ObjectNotFoundException(" No se pudo eliminar el sensor."
                    + " El sensor dado no existe." );

        LOGGER.log(Level.INFO, String.format("Eliminado el sensor %1$ de la "
                + "red de la estacion %2$", sensorElim.getID(), ID));
    }

    /**
     * Este metodo es al que se llama desde la estacion base para pedir los datos.
     * El return debe ser un PaqueteDatos.
     */
    public PaqueteDatos pedirData() {
        // Crear un objeto de tipo PaqueteDatos y cargarle los sensores
        
    }
}
