/**
 *  @file Estacion.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.Calendar;
import java.util.Stack;
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
public abstract class Estacion {

    /* *** Propiedades *** */

    protected int ID;
    private static int IDsiguiente = 0;
    private String nombre;

    /// El arreglo que indica con que estaciones esta conectada esta estacion.
    protected Estacion[] redEstaciones;
    /// Variable para chequear si ya existe o no, una EstacionBase.
    protected static boolean estacionBaseExiste = false;  // Tiene que ser static ??
    
    // @NEW
    // Pila con la informacion de las sub-estaciones
    protected Stack<PaqueteDatos> medidasPila;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

    // Definición de los tipos de estaciones
    protected enum Tipo { BASE, MET };
    // Define de que tipo es la estacion
    protected Tipo clase;

    /* *** Constructores *** */

    public Estacion(String nombre, Tipo tipo) throws CreacionException {
        if ( tipo == Tipo.BASE ) {
            // Se puede instanciar solo una estacion base
            /// Chequea la existencia de una Estacion Base
            if ( estacionBaseExiste )
                throw new CreacionException("No se pudo instanciar el "
                        + "objeto. No se puede crear mas de una Estacion Base");
            // else
            estacionBaseExiste = true;
            clase = Tipo.BASE;
        }
        else {
            // No se puede instanciar una estacion meteorologica sin tener una base
            /// Chequea la no existencia de una Estacion Base
            if ( !estacionBaseExiste )
                throw new CreacionException("No se pudo instanciar el "
                        + "objeto. Se debe crear primero una Estacion Base");
            
            clase = Tipo.MET;
        }

        this.ID = IDsiguiente;
        IDsiguiente++;
        if ( nombre == null )  // Nombre por defecto por si no se le dio uno
            nombre = "Estacion" + Integer.toString(ID);
        this.nombre = nombre;

        LOGGER.log(Level.INFO, String.format(
                "Creanda estacion %s %d ( %s ).", clase.toString(), ID, nombre));

        // Creo la red de estaciones del la estacion.
        redEstaciones = new Estacion[4];  // Maximo 4 estaciones conectadas
        for ( Estacion estacion : redEstaciones )
            estacion = null;
        
        // @NEW
        medidasPila = new Stack();
    }

    public Estacion( Tipo tipo ) throws CreacionException {
        this( null, tipo);
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

    public Stack<PaqueteDatos> getMedidas() { return medidasPila; }
    
    /* *** Otros metodos *** */
    
    /**
     *  Agrega una estacion a la red del la estacion actual, si es que hay lugar
     *  para una nueva coneccion.
     *  Se debe tambien agregar la conexion al grafo.
     */
    public void agregarEstacion(Estacion estacionNueva)
            throws ArrayIndexOutOfBoundsException {

        boolean insertado = false;
// No agrega la estacion a redEstaciones
//        for (Estacion estacion : redEstaciones)
//            if ( estacion == null ) {
//                estacion = estacionNueva;
//                insertado = true;
//                break;
//            }
        
        for (int i=0; i<redEstaciones.length; i++) {
            if (redEstaciones[i] == null) {
                redEstaciones[i] = estacionNueva;
                insertado = true;
                break;
            }
        }
        
        if ( !insertado )
            throw new ArrayIndexOutOfBoundsException( "No se puede insertar "
                    + "la estacion. No una coneccion disponible" );

        // Avisar a estacion base
        
        LOGGER.log(Level.INFO, String.format("Agregada estacion %d a la red "
                + "de la estacion %d.", estacionNueva.getID(), ID));
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
                break;
            }

        if ( !eliminado )
            throw new ObjectNotFoundException(" No se pudo eliminar la "
                    + "estacion. La estacion dada no existe." );

        // Avisar a estacion base ¿? ¿como hago esto?
        
        LOGGER.log(Level.INFO, String.format("Eliminada la estacion %d de la"
                + " red de la estacion %d.", estacionElim.getID(), ID));
    }

    @Override
    /// toString() -> Estacion $ID ( $nombre )
    public String toString() {
        return ( String.format("Estación %d ( %s )", ID, nombre) );  
    }
    
    /// Actualiza todas las sub-estaciones
    // @NEW
    public Stack<PaqueteDatos> actualizar() {
        // Toda la informacion recibida de las sub-estaciones
        // La información recibida de _una_ subestacion se almacena acá
        Stack<PaqueteDatos> newData = new Stack();
        medidasPila.clear();    // Limpio la pila
        
        for (Estacion subestacion : redEstaciones) {
            if (subestacion != null) {
                newData = subestacion.actualizar();
                
//                newData.peek().printDatos();    // Para debbugear
            
                // newData >> data
                if(newData.peek() != null)
                    medidasPila.addAll(newData);    // !!! Probar si funciona, agrega al final de la pila ¿?
                
                // otra
//                while(newData.peek() != null) {
//                    medidasCola.addAll(newData);    // !!! Probar si funciona, agrega al final de la pila ¿?
//                    newData.pop();
//                }
            }
        }
        
        LOGGER.log(Level.INFO, String.format("actualizada estacion %s %d", clase.toString(), ID));
                
        return medidasPila;
    }
    
    protected String getHora() {
        int HH = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int MM = Calendar.getInstance().get(Calendar.MINUTE);
        int SS = Calendar.getInstance().get(Calendar.SECOND);
        
        return (HH + ":" + MM + ":" + SS);
    }
}
