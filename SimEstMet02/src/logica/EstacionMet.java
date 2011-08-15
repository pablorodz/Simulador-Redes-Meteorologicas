/**
 *  @file EstacionMet.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.commons.configuration.*;

/* 
 *  Estacion Meteorologica
 *      * Lista de sensores (maximo 4)
 *      * Lista de Estaciones Meteorologicas (maximo 3) ??
 *      * Lanza una CreacionException, pero esta excepcion nunca deberia pasar.
          Asi que en el try-catch no se deberia atrapar nunca nada.
 *  * La estacion base le pide a cada subestacion los datos --> EstacionMet.pedirData().
 */
public class EstacionMet extends Estacion {

    /* *** Propiedades *** */

    /// El arreglo que indica los sensores conectados a la estacion.
    private Sensor[] redSensores;
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(EstacionMet.class .getName());

    /* *** Constructores *** */

    public EstacionMet( String nombre ) throws CreacionException {
        super( nombre, Tipo.MET );
        inicializar();
    }

    public EstacionMet() throws CreacionException {
        super( Tipo.MET );
        inicializar();
    }

    /**
     * Inicializo la estcion meteorologica
     */
    private void inicializar() {
        // Creo la red de sensores de la estacion.
        redSensores = new Sensor[4];  // Maximo 4 sensores
        int redSize = redSensores.length;
        for (int i=0; i<redSize; i++)
            redSensores[i] = null;
    }
    
    /* *** Otros metodos *** */

        /**
     * Retorna el objeto TreeNode del sensor coorespondiente al ID
     * 
     * Pasa la orden a las sub-estaciones en busca de El TreeNode del sensor 
     * correspondiente.
     * 
     * @return El objeto TreeNode de la estacion o null si no existe en la red de esta estacion.
     */
    @Override
    public DefaultMutableTreeNode getSensorTreeNode( int sensorID ) { 
        DefaultMutableTreeNode treeNode = null;

        int i = 0;
        int redSize = redSensores.length;
        while(treeNode==null && i<redSize) {
            if (redSensores[i] != null)
                treeNode = redSensores[i].getTreeNode();
            i++;
        }

        // Si no se encontro en los sensores propios, se pasa la orden
        if (treeNode == null) {
            i = 0;
            redSize = redEstaciones.length;
            while(treeNode==null && i<redSize) {
                if (redEstaciones[i] != null)
                    treeNode = redEstaciones[i].getSensorTreeNode(sensorID);
                i++;
            }
        }        

        return treeNode; 
    }

    /**
     * @brief Agrega un sensor a la red del la estacion actual.
     * 
     * Agrega el sensor pasado como argumento a la red de la estacion actual.
     * Esto se hace siempre y cuando haya una conexion libre en la estacion. Es 
     * decir, si hay lugar en el arreglo redSensores.
     * 
     * @param sensorNuevo Sensor a ser agregado a la red.
     * 
     * @return true Si el sensor fue agregado correctamente a la red.
     */
    private boolean agregarSensor(Sensor sensorNuevo) {
        boolean insertado = false;

        for (int i=0; i<redSensores.length; i++) {
            if (redSensores[i] == null) {
                redSensores[i] = sensorNuevo;
                insertado = true;
                break;
            }
        }
// Reemplazado por el return boolean
//        if ( !insertado )
//            throw new ArrayIndexOutOfBoundsException( "No se puede insertar el"
//                    + " sensor. No una coneccion disponible" );

        LOGGER.log(Level.INFO, String.format("Agregando sensor %d a la red "
                + "de la estacion %d", sensorNuevo.getID(), ID));
        
        return insertado;
    }

    /**
     * @brief Agrega un sensor a la red del la estacion padreID.
     * 
     * Agrega la sensor pasado como argumento a la red de la estacion padreID.
     * Si padreID es el ID de la estacion actual, se agrega el nuevo sensor
     * a la red.
     * Esto se hace siempre y cuando haya una conexion libre en la estacion. Es 
     * decir, si hay lugar en el arreglo redSensores.
     * Si padreID no corresponde a la estacion actual, la orden se pasa a todas
     * las sub-estaciones.
     * 
     * @param sensorNuevo Sensor a ser agragado a la red.
     * @param padreID ID de la estacion a la cual debe agregarse sensorNuevo.
     * 
     * @return true Si el sensor fue agregado correctamente a la red
     */
    public boolean agregarSensor(Sensor sensorNuevo, int padreID) {
        boolean insertado = false;

        if ( padreID == ID) // Si esta estacion es el padre, agrego
            insertado = agregarSensor(sensorNuevo);
        else {  // Si no, le paso la orden a las sub-estaciones
            int i = 0;
            int redSize = redEstaciones.length;
            while(!insertado && i<redSize) {
                if (redEstaciones[i] != null)
                    insertado = redEstaciones[i].agregarSensor(sensorNuevo, padreID);
                i++;
            }  
        }
        
        return insertado;
    }

    /**
     * @brief Elimina un sensor de la red.
     * 
     * Elimina una coneccion a un sensor (elimina el sensor del arreglo
     * redSensores) si es que existe.
     * De no existir, le pasa la orden a las sub-estaciones.
     * Se pasa como argumento un Objeto Sensor, lo cual es un problema si no 
     * se tiene acceso directo al objeto.
     * 
     * @param sensorElim Una instancia del objeto a eliminar.
     * 
     * @return true Si el sensor fue eliminado correctamente de la red.
     */
    public boolean eliminarSensor(Sensor sensorElim) {
        boolean eliminado = false;
        
        int redSize = redSensores.length;
        for (int i=0; i<redSize; i++) {
            if (redSensores[i] == sensorElim) {
                redSensores[i] = null;
                eliminado = true;
                break;
            }
        }
        
        if (eliminado) {    // Si se logro eliminar, guardo aviso.
            LOGGER.log(Level.INFO, String.format("Eliminado el sensor %d de la "
                    + "red de la estacion %d", sensorElim.getID(), ID));        
        }
        else { // Si no, le paso la orden a las sub-estaciones
            int i = 0;
            redSize = redEstaciones.length;
            while(!eliminado && i<redSize) {
                if (redEstaciones[i] != null)
                    eliminado = redEstaciones[i].eliminarSensor(sensorElim);
                i++;
            }
        }

// Reemplazado por el return boolean
//        if ( !eliminado )
//            throw new ObjectNotFoundException(" No se pudo eliminar el sensor."
//                    + " El sensor dado no existe." );

        return eliminado;
    }

    /**
     * @brief Elimina un sensor de la red.
     * 
     * Elimina una coneccion a un sensor (elimina el sensor del arreglo
     * redSensores) si es que existe.
     * De no existir, le pasa la orden a las sub-estaciones.
     * Se pasa como argumento solo el ID del sensor a eliminar. Util si no se 
     * tiene acceso al objeto del sensor.
     * 
     * @param sensorElimID El id del sensor a eliminar de la red.
     * 
     * @return true Si el sensor fue eliminado correctamente de la red.
     */
    public boolean eliminarSensor(int sensorElimID) {
        boolean eliminado = false;
        
        int redSize = redSensores.length;
        for (int i=0; i<redSize; i++) {
            if (redSensores[i].getID() == sensorElimID) {
                redSensores[i] = null;
                eliminado = true;
                break;
            }
        }

        if (eliminado) {    // Si se logro eliminar, guardo aviso.
            LOGGER.log(Level.INFO, String.format("Eliminado el sensor %d de la "
                    + "red de la estacion %d", sensorElimID, ID));        
        }
        else { // Si no, le paso la orden a las sub-estaciones
            int i = 0;
            redSize = redEstaciones.length;
            while(!eliminado && i<redSize) {
                if (redEstaciones[i] != null)
                    eliminado = redEstaciones[i].eliminarSensor(sensorElimID);
                i++;
            }
        }

// Reemplazado por el return boolean
//        if ( !eliminado )
//            throw new ObjectNotFoundException(" No se pudo eliminar el sensor."
//                    + " El sensor dado no existe." );

        return eliminado;
    }

    // Agrego los sensores de esta estacion a los sensores de las sub-estaciones
    @Override
    public Stack<PaqueteDatos> actualizar() {
        // Informacion para PaqueteDatos()
        Integer[] sensoresID = new Integer[redSensores.length];
        String[] tipo = new String[redSensores.length];
        String[] medicion = new String[redSensores.length];
    
        // Cargo el resumen de la estacion si es que existe.
        // Instancio el manejador de XML
        XMLConfiguration registro = new XMLConfiguration();
        registro.setFileName(String.format("resumenes/%d.xml", ID));

        try {
            // Cargo si existe el registro
            registro.load();
        } catch (ConfigurationException ex) {
            // Si no existe, simplemente seteo el nombre del elemento base
            registro.setRootElementName("resumen");
            // Y creo el archivo
            try {
                registro.save();
            } catch (ConfigurationException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        }
        
        // Datos de las sub-estaciones
        medidasPila = super.actualizar();
        
        // Busco y guardo las mediciones de los sensores.
        int largo = redSensores.length;
        for ( int i=0; i<largo; i++ ) {
            if (redSensores[i] != null) {
                sensoresID[i] = redSensores[i].getID();
                tipo[i] = redSensores[i].getClass().getSimpleName();
                medicion[i] = redSensores[i].getMedicion();
                resumenSave(registro, sensoresID[i], tipo[i], medicion[i]);
            }
        }

        // Agrego el los datos de esta estacion al final de los datos de las sub-estaciones
        medidasPila.push(new PaqueteDatos(ID, getHora(), sensoresID, tipo, medicion));
        
        return medidasPila;
    }

    private void resumenSave(XMLConfiguration registro, Integer sensorID, String tipo, String medicionS) {
        try {
            // Busco si ya hay algun registro del sensor.
            List<String> idsRegistro = registro.getList( String.format("estacion%d.sensor.id", ID));
            int index = -1;
            
            for (String id : idsRegistro) {
                if (Integer.valueOf(id).equals(sensorID)) {
                    index = idsRegistro.indexOf( String.valueOf(sensorID) );
                    break;
                }
            }
            // Si ya hay, actualizo
            if (index != -1) {
                float medicion;         // Medicion actual en float
                String nMedicionesS;    // Numero de mediciones
                int nMediciones;
                String maximoS;         // Valor maximo de las mediciones
                float maximo;
                String minimoS;         // Valor minimo de las mediciones
                float minimo;
                String medioS;          // Valor medio de las mediciones
                float medio;
                
                // Cargo los valores
                nMedicionesS = registro.getString( String.format("estacion%d.sensor(%d).mediciones", ID, index) );
                maximoS = registro.getString( String.format("estacion%d.sensor(%d).maximo", ID, index) );
                minimoS = registro.getString( String.format("estacion%d.sensor(%d).minimo", ID, index) );
                medioS = registro.getString( String.format("estacion%d.sensor(%d).medio", ID, index) );

                // Aumento en uno la cantidad de mediciones
                nMediciones = Integer.valueOf(nMedicionesS) + 1;
                registro.setProperty( String.format("estacion%d.sensor(%d).mediciones", ID, index), String.valueOf(nMediciones) );
                
                // Recalculo maximo y minimo
                medicion = Float.valueOf(medicionS.split(" ")[0]);
                maximo = Float.valueOf( maximoS.split(" ")[0] );
                minimo = Float.valueOf( minimoS.split(" ")[0] );
                
                if (medicion > maximo)
                    registro.setProperty(String.format("estacion%d.sensor(%d).maximo", ID, index), medicionS);
                if (medicion < minimo)
                    registro.setProperty(String.format("estacion%d.sensor(%d).minimo", ID, index), medicionS);
                
                // Recalculo el valor medio
                medio = Float.valueOf( medioS.split(" ")[0] );

                medio = (medio + medicion) / 2;
                registro.setProperty(String.format("estacion%d.sensor(%d).medio", ID, index), String.valueOf(medio));
            }
            else {  // Si no, creo y cargo los valores como iniciales
                index = idsRegistro.size();
                registro.addProperty(String.format("estacion%d.sensor(%d).id", ID, index), sensorID.toString());
                registro.addProperty(String.format("estacion%d.sensor(%d).tipo", ID, index), tipo);
                registro.addProperty(String.format("estacion%d.sensor(%d).maximo", ID, index), medicionS);
                registro.addProperty(String.format("estacion%d.sensor(%d).minimo", ID, index), medicionS);
                registro.addProperty(String.format("estacion%d.sensor(%d).medio", ID, index), medicionS);
                registro.addProperty(String.format("estacion%d.sensor(%d).mediciones", ID, index), "1");
            }
            
            registro.save();
        } catch (ConfigurationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
