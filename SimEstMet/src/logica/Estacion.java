/**
 *  @file Estacion.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.logging.Level;
import java.util.logging.Logger;

/*  
 *  * Los metodos deben ser protected o private??
 *  * No se setean los ID mediante una variable static debido a que la 
 *  estacion base va a ser unica y tener el ID = 0. Entonces, las estaciones 
 *  meteorologicas son las que van a ir obteniendo un ID unico en serie a 
 *  partir de 1 y cualquier otro tipo de estacion que se quiera crear debe 
 *  extender de esta (EstacionMeteorologica).
 *  * Esta clase es la encargada de chequear que no se creen mas de una Estacion Base.
 */
public abstract class Estacion extends Componente {
    protected int ID;
    private String nombre;
    /// El arreglo que indica con que estaciones esta conectada esta estacion.
    private Estacion[] redEstaciones;
    /// Variable para chequear si ya existe o no, una EstacionBase.
    private static boolean estacionBaseExiste = false;  // Tiene que ser static ??
    // Grafo al cual pertenece la estacion. Posiblemente no tenga mucho sentido tener esta variable (redundancia)
    private Grafo grafo;
    // The Logger only for this class
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

    /**
     *  Constructor.
     *  Setear el ID. 
     */
    public Estacion(int ID, String nombre, Grafo grafo) throws CreacionException {
        if ( ID == 0 ) {
            /// Chequea la existencia de una Estacion Base
            if ( estacionBaseExiste )
                throw new CreacionException("No se pudo instanciar el "
                        + "objeto. No se puede crear mas de una Estacion Base");
            // else
            estacionBaseExiste = true;
        }
        
        this.ID = ID;
        this.nombre = nombre;

        LOGGER.log(Level.INFO, String.format(
                "Creanda estacion %1$ ( %2$ ).", ID, nombre));

        // Creo la red de estaciones del la estacion.
        redEstaciones = new Estacion[4];  // Maximo 4 estaciones conectadas
        for ( Estacion estacion : redEstaciones )
            estacion = null;

        // Agrego la estacion al grafo
        this.grafo = grafo;
        grafo.addNode(ID);
        LOGGER.log(Level.INFO, String.format(
                "Agreganda estacion %1$ al grafo %2$.", ID, grafo.getID()));
    }

    public Estacion(int ID, Grafo grafo) throws CreacionException {
        this( ID, "Estacion" + Integer.toString(ID), grafo );
    }

    /* *** Setters y Getters *** */
    
    public int getID () {
        return ID;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    /* *** Otros metodos *** */
    
    /**
     *  Agrega una estacion a la red del la estacion actual, si es que hay lugar
     *  para una nueva coneccion.
     */
    public void agregarEstacion(Estacion estacionNueva)
            throws ArrayIndexOutOfBoundsException {
        boolean insertado = false;
        for (Estacion estacion : redEstaciones)
            if ( estacion == null ) {
                estacion = estacionNueva;
                insertado = true;
            }
        
        if ( !insertado )
            throw new ArrayIndexOutOfBoundsException( "No se puede insertar "
                    + "la estacion. No una coneccion disponible" );

        LOGGER.log(Level.INFO, String.format("Agreganda estacion %1$ a la red "
                + "de la estacion %2$.", estacionNueva.getID(), ID));
    }

    /**
     *  Elimina una coneccion a una estacion (elimina la estacion del
     *  arreglo redEstaciones) si es que existe.
     */
    public void eliminarEstacion(Estacion estacionElim)
            throws ObjectNotFoundException {
        boolean eliminado = false;
        for (Estacion estacion : redEstaciones)
            if ( estacion == estacionElim ) {
                estacion = null;
                eliminado = true;
            }

        if ( !eliminado )
            throw new ObjectNotFoundException(" No se pudo eliminar la "
                    + "estacion. La estacion dada no existe." );

        LOGGER.log(Level.INFO, String.format("Eliminada la estacion %1$ de la"
                + " red de la estacion %2$.", estacionElim.getID(), ID));
    }

    @Override
    /// Estacion ID ( nombre )
    public String toString() {
        return ( String.format("Estación %1$ ( %2$ )", ID, nombre) );  
    }
}
