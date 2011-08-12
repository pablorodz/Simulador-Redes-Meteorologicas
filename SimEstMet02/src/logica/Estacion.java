/**
 *  @file Estacion.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.Calendar;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.*;

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
    protected EstacionMet[] redEstaciones;
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

    // Para trabajar con los resumenes en XML
    // Lo instancio en el momento de usarlo
//    XMLConfiguration registro;

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
            // Creo la red de estaciones del la estacion. 
            //Maximo 4 estaciones conectadas
            redEstaciones = new EstacionMet[4];
        }
        else {
            // No se puede instanciar una estacion meteorologica sin tener una base
            /// Chequea la no existencia de una Estacion Base
            if ( !estacionBaseExiste )
                throw new CreacionException("No se pudo instanciar el "
                        + "objeto. Se debe crear primero una Estacion Base");
            
            clase = Tipo.MET;
            // Creo la red de estaciones del la estacion. 
            //Maximo 4 estaciones conectadas
            redEstaciones = new EstacionMet[3];
        }

        this.ID = IDsiguiente;
        IDsiguiente++;
        if ( nombre == null )  // Nombre por defecto por si no se le dio uno
            nombre = "Estacion" + Integer.toString(ID);
        this.nombre = nombre;

        LOGGER.log(Level.INFO, String.format(
                "Creanda estacion %s %d ( %s ).", clase.toString(), ID, nombre));

        // Creo la red de estaciones del la estacion.
        for ( EstacionMet estacion : redEstaciones )
            estacion = null;
                
        // Creo la pila donde se guardan las mediciones (PaqueteDeDatos)
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
     * @brief Agrega una estacion a la red del la estacion actual.
     * 
     * Agrega la estacion pasada como argumento a la red de la estacion actual.
     * Esto se hace siempre y cuando haya una conexion libre en la estacion. Es 
     * decir, si hay lugar en el arreglo redEstaciones.
     * 
     * @param estacionNueva Estacion a ser agragada a la red.
     * 
     * @return true Si la estacion fue agregada correctamente a la red
     */
    private boolean agregarEstacion(EstacionMet estacionNueva) {
        boolean insertado = false;
        
        for (int i=0; i<redEstaciones.length; i++) {
            if (redEstaciones[i] == null) {
                redEstaciones[i] = estacionNueva;
                insertado = true;
                break;
            }
        }
        
// Reemplazado por el return boolean
//        if ( !insertado )
//            throw new ArrayIndexOutOfBoundsException( "No se puede insertar "
//                    + "la estacion. No una coneccion disponible" );
        
        LOGGER.log(Level.INFO, String.format("Agregada estacion %d a la red "
                + "de la estacion %d.", estacionNueva.getID(), ID));
        
        return insertado;
    }

    /**
     * @brief Agrega una estacion a la red de la estacion padreID.
     * 
     * Agrega la estacion pasada como argumento a la red de la estacion padreID.
     * Si padreID es el ID de la estacion actual, se agrega la nueva estacion 
     * a la red.
     * Esto se hace siempre y cuando haya una conexion libre en la estacion. Es 
     * decir, si hay lugar en el arreglo redEstaciones.
     * Si padreID no corresponde a la estacion actual, la orden se pasa a todas
     * las sub-estaciones.
     * 
     * @param estacionNueva Estacion a ser agragada a la red.
     * @param padreID ID de la estacion a la cual debe agregarse estacionNueva.
     * 
     * @return true Si la estacion fue agregada correctamente a la red
     */
    public boolean agregarEstacion(EstacionMet estacionNueva, int padreID) {
        boolean insertado = false;

        if ( padreID == ID) // Si esta estacion es el padre, agrego
            insertado = agregarEstacion(estacionNueva);
        else {  // Si no, le paso la orden a las sub-estaciones
            int i = 0;
            int redSize = redEstaciones.length;
            while(!insertado && i<redSize) {
                insertado = redEstaciones[i].agregarEstacion(estacionNueva, padreID);
                i++;
            }  
        }
        
        return insertado;
    }

    /**
     * @brief Elimina una estacion de la red.
     * 
     * Elimina una coneccion a una estacion (elimina la estacion del
     * arreglo redEstaciones) si es que existe.
     * De no existir, le pasa la orden a las sub-estaciones.
     * Se pasa como argumento un Objeto Estacion, lo cual es un problema si no 
     * se tiene acceso directo al objeto.
     * 
     * @param estacionElim Una instancia del objeto a eliminar.
     * 
     * @return true Si la estacion fue eliminada correctamente de la red.
     */
    public boolean eliminarEstacion(EstacionMet estacionElim) {
        boolean eliminado = false;
        
        for (int i=0; i<redEstaciones.length; i++) {
            if (redEstaciones[i] == estacionElim) {
                redEstaciones[i] = null;
                eliminado = true;
                break;
            }
        }

        if (eliminado) {    // Si se logro eliminar, guardo aviso.
            LOGGER.log(Level.INFO, String.format("Eliminada la estacion %d de la"
                + " red de la estacion %d.", estacionElim.getID(), ID));
        }
        else { // Si no, le paso la orden a las sub-estaciones
            int i = 0;
            int redSize = redEstaciones.length;
            while(!eliminado && i<redSize) {
                eliminado = redEstaciones[i].eliminarEstacion(estacionElim);
                i++;
            }
        }
        
// Reemplazado por el return boolean
//        if ( !eliminado )
//            throw new ObjectNotFoundException(" No se pudo eliminar la "
//                    + "estacion. La estacion dada no existe." );

        return eliminado;
    }

    /**
     * @brief Elimina una estacion de la red.
     * 
     * Elimina una coneccion a una estacion (elimina la estacion del
     * arreglo redEstaciones) si es que existe.
     * De no existir, le pasa la orden a las sub-estaciones.
     * Se pasa como argumento solo el ID de la estacion a eliminar. Util si no 
     * se tiene acceso al objeto de la estacion.
     * 
     * @param estacionElim Una instancia del objeto a eliminar.
     * 
     * @return true Si la estacion fue eliminada correctamente de la red.
     */
    public boolean eliminarEstacion(int estacionElimID) {
        boolean eliminado = false;
        
        for (int i=0; i<redEstaciones.length; i++) {
            if (redEstaciones[i].getID() == estacionElimID) {
                redEstaciones[i] = null;
                eliminado = true;
                break;
            }
        }
        
        if (eliminado) {    // Si se logro eliminar, guardo aviso.
            LOGGER.log(Level.INFO, String.format("Eliminada la estacion %d de la"
                + " red de la estacion %d.", estacionElimID, ID));
        }
        else { // Si no, le paso la orden a las sub-estaciones
            int i = 0;
            int redSize = redEstaciones.length;
            while(!eliminado && i<redSize) {
                eliminado = redEstaciones[i].eliminarEstacion(estacionElimID);
                i++;
            }
        }
        
// Reemplazado por el return boolean
//        if ( !eliminado )
//            throw new ObjectNotFoundException(" No se pudo eliminar la "
//                    + "estacion. La estacion dada no existe." );
        
        return eliminado;
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
        LOGGER.log(Level.INFO, String.format("Actualizando estacion %s %d", clase.toString(), ID));
        
        medidasPila.clear();    // Limpio la pila
        
        for (EstacionMet subestacion : redEstaciones) {
            if (subestacion != null) {
                newData = subestacion.actualizar();
                
//                newData.peek().printDatos();    // Para debbugear
            
                // newData >> data
                if(newData.peek() != null)
                    // Copia newData (pila) en la base de medidasPila (otra pila).
                    medidasPila.addAll(newData);
            }
        }
        
        return medidasPila;
    }
    
    protected String getHora() {
        int HH = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int MM = Calendar.getInstance().get(Calendar.MINUTE);
        int SS = Calendar.getInstance().get(Calendar.SECOND);
        
        return (HH + ":" + MM + ":" + SS);
    }
    
    /*
     * @brief Ubicacion de los resumenes, en un Vector<String>
     * 
     * Retorna la ubicacion de los resumenes de esta estacion y de sus 
     * sub-estaciones, en un Vector<String>.
     * Se guardan en esa forma, primero la direccion propia y luego la de las 
     * sub-estaciones.
     * @return Direccion donde se encuentra el resumen.
     */
    public Vector<String> getResumen() {
        Vector<String> direcciones = new Vector();
        // Instancio el manejador de XML
        XMLConfiguration registro = new XMLConfiguration();
        registro.setFileName(String.format("resumenes/%d.xml", ID));

        if ( !registro.getFile().exists() ) {
            // Si no existe, simplemente seteo el nombre del elemento base
            registro.setRootElementName("resumen");
            // Y creo el archivo
            try {
                registro.save();
            } catch (ConfigurationException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        }
        
        // getFileName() me devuelve la direccion dentro del proyecto.
        // getURL() me devuelve la direccion desde el root de la maquina.
        direcciones.add( registro.getFileName() );
        
        for (EstacionMet subestacion : redEstaciones) {
            if ( subestacion != null )
                direcciones.addAll(subestacion.getResumen());
        }
        
        return direcciones;
    }
}
