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
import javax.swing.tree.*;

/**
 * Clase padre de todas las estaciones
 *
 * Esta clase es la encargada de chequear que no se creen mas de una Estacion Base.
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
    
    // Pila con la informacion de las sub-estaciones
    protected Stack<PaqueteDatos> medidasPila;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

    // Definición de los tipos de estaciones
    protected enum Tipo { BASE, MET };
    // Define de que tipo es la estacion
    protected Tipo clase;

    // TreeNode de la estacion
    protected DefaultMutableTreeNode estacionTreeNode;

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
            redEstaciones = new Estacion[4];
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
            redEstaciones = new Estacion[3];
        }

        this.ID = IDsiguiente;
        IDsiguiente++;
        if ( nombre == null )  // Nombre por defecto por si no se le dio uno
            nombre = "Estacion" + Integer.toString(ID);
        this.nombre = nombre;

        LOGGER.log(Level.INFO, String.format(
                "Creanda estacion %s %d ( %s ).", clase.toString(), ID, nombre));

        // Creo la red de estaciones del la estacion.
        int redSize = redEstaciones.length;
        for (int i=0; i<redSize; i++)
            redEstaciones[i] = null;
        
        // Creo la pila donde se guardan las mediciones (PaqueteDeDatos)
        medidasPila = new Stack();
        
        // Creo el treeNode
        estacionTreeNode = new DefaultMutableTreeNode(this);
    }

    public Estacion( Tipo tipo ) throws CreacionException {
        this( null, tipo);
    }

    /* *** Setters y Getters *** */
    
    /**
     * Retorna el id de la estacion actual
     * 
     * @return ID de la estacion actual
     */
    public int getID () {
        return ID;
    }

    /**
     * Retorna el id que va a tener la siguiente estacion. Utilizado para saber
     * el maximo numero de estaciones.
     * 
     * @return ID de la siguiente estacion a crear
     */
    public static int getSiguienteID() {
        return IDsiguiente;
    }
    
    /**
     * Retorna el nombre de la estacion actual
     * 
     * @return Nombre de la estacion actual
     */
    public String getNombre () {
        return nombre;
    }

    /**
     * Retorna la pila con las mediciones de la estacion actual
     * 
     * @return Pila con las mediciones de la estacion actual
     */
    public Stack<PaqueteDatos> getMedidas() { return medidasPila; }
    
    /**
     * Renombra la estacion
     * 
     * @param nombre Nombre para la estacion
     */
    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el objeto TreeNode de la estacion.
     * 
     * @return El objeto TreeNode de la estacion
     */
    public DefaultMutableTreeNode getTreeNode() { return estacionTreeNode; }
    
    /**
     * Retorna el objeto TreeNode de la estacion coorespondiente al ID
     * 
     * Si el id es de esta estacion, retorna el TreeNode de esta estacion. Si 
     * no es asi, pasa la orden a las sub-estaciones.
     * 
     * @return El objeto TreeNode de la estacion o null si no existe en la red de esta estacion.
     */
    public DefaultMutableTreeNode getEstacionTreeNode( int estacionID ) { 
        DefaultMutableTreeNode treeNode = null;
        
        if (ID == estacionID)
            treeNode = getTreeNode();
        else {
            int i = 0;
            int redSize = redEstaciones.length;
            while(treeNode==null && i<redSize) {
                if (redEstaciones[i] != null)
                    treeNode = redEstaciones[i].getEstacionTreeNode(estacionID);
                i++;
            }
        }
        
        return treeNode; 
    }
    
    /**
     * Retorna el objeto TreeNode del sensor coorespondiente al ID
     * 
     * Pasa la orden a las sub-estaciones en busca de El TreeNode del sensor 
     * correspondiente.
     * 
     * @return El objeto TreeNode de la estacion o null si no existe en la red de esta estacion.
     */
    public DefaultMutableTreeNode getSensorTreeNode( int sensorID ) { 
        DefaultMutableTreeNode treeNode = null;
        
        int i = 0;
        int redSize = redEstaciones.length;
        while(treeNode==null && i<redSize) {
            if (redEstaciones[i] != null)
                treeNode = redEstaciones[i].getSensorTreeNode(sensorID);
            i++;
        }
        
        return treeNode; 
    }
    
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
    private boolean agregarEstacion(Estacion estacionNueva) {
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
    public boolean agregarEstacion(Estacion estacionNueva, int padreID) {
        boolean insertado = false;

        if ( padreID == ID) // Si esta estacion es el padre, agrego
            insertado = agregarEstacion(estacionNueva);
        else {  // Si no, le paso la orden a las sub-estaciones
            int i = 0;
            int redSize = redEstaciones.length;
            while(!insertado && i<redSize) {
                if (redEstaciones[i] != null)
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
    public boolean eliminarEstacion(Estacion estacionElim) {
        boolean eliminado = false;
        
        int redSize = redEstaciones.length;
        for (int i=0; i<redSize; i++) {
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
            while(!eliminado && i<redSize) {
                if (redEstaciones[i] != null)
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
        
        int redSize = redEstaciones.length;
        for (int i=0; i<redSize; i++) {
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
            while(!eliminado && i<redSize) {
                if (redEstaciones[i] != null)
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
    /// toString() -> Estacion $ID
    public String toString() {
        return ( String.format("Estación%d", ID) );  
    }
    
    /**
     *  Actualiza todas las sub-estaciones y retorna una pila con todas ellas
     * 
     * @return Pila de PaqueteDatos con las mediciones de toda esta sub red
     */
    public Stack<PaqueteDatos> actualizar() {
        // Toda la informacion recibida de las sub-estaciones
        // La información recibida de _una_ subestacion se almacena acá
        Stack<PaqueteDatos> newData = new Stack();
        LOGGER.log(Level.INFO, String.format("Actualizando estacion %s %d", clase.toString(), ID));
        
        medidasPila.clear();    // Limpio la pila
        
        for (Estacion subestacion : redEstaciones) {
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
    
    /**
     * @brief Ubicacion de los resumenes, en un Vector<String>
     * 
     * Retorna la ubicacion de los resumenes de esta estacion y de sus 
     * sub-estaciones, en un Vector<String>.
     * Se guardan en esa forma, primero la direccion propia y luego la de las 
     * sub-estaciones.
     * 
     * @return Direccion donde se encuentra el resumen.
     */
    public Vector<String> getResumen() {
        Vector<String> direcciones = new Vector();
        // Instancio el manejador de XML
        XMLConfiguration registro = new XMLConfiguration();
        registro.setFileName(String.format("resumenes/%d.xml", ID));

// No es necesario crear el resumen si no existe
//        if ( !registro.getFile().exists() ) {
//            // Si no existe, simplemente seteo el nombre del elemento base
//            registro.setRootElementName("resumen");
//            // Y creo el archivo
//            try {
//                registro.save();
//            } catch (ConfigurationException ex1) {
//                LOGGER.log(Level.SEVERE, null, ex1);
//            }
//        }
        
        // getFileName() me devuelve la direccion dentro del proyecto.
        // getURL() me devuelve la direccion desde el root de la maquina.
        if ( registro.getFile().exists() )
            direcciones.add( registro.getFileName() );
        
        for (Estacion subestacion : redEstaciones) {
            if ( subestacion != null )
                direcciones.addAll(subestacion.getResumen());
        }
        
        return direcciones;
    }

    /* *** Metodos para trabajar con sensores *** */
    public abstract boolean agregarSensor(Sensor sensorNuevo, int padreID);
    
    public abstract boolean eliminarSensor(Sensor sensorElim);
    
    public abstract boolean eliminarSensor(int sensorElimID);


}
