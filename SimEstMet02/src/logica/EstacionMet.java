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
        for ( Sensor sensor : redSensores )
            sensor = null;
    }
    
    /* *** Otros metodos *** */

    /**
     *  Agrega un sensor a la estacion, si es que hay lugar.
     */
    public void agregarSensor(Sensor sensorNuevo)
            throws ArrayIndexOutOfBoundsException {
        boolean insertado = false;
// No agrega el sensor a redSensores
//        for (Sensor sensor : redSensores)
//            if ( sensor == null ) {
//                sensor = sensorNuevo;
//                insertado = true;
//                break;
//            }

        for (int i=0; i<redSensores.length; i++) {
            if (redSensores[i] == null) {
                redSensores[i] = sensorNuevo;
                insertado = true;
                break;
            }
        }
        
        if ( !insertado )
            throw new ArrayIndexOutOfBoundsException( "No se puede insertar el"
                    + " sensor. No una coneccion disponible" );

        LOGGER.log(Level.INFO, String.format("Agregando sensor %d a la red "
                + "de la estacion %d", sensorNuevo.getID(), ID));
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
                break;
            }

        if ( !eliminado )
            throw new ObjectNotFoundException(" No se pudo eliminar el sensor."
                    + " El sensor dado no existe." );

        LOGGER.log(Level.INFO, String.format("Eliminado el sensor %d de la "
                + "red de la estacion %d", sensorElim.getID(), ID));
    }

    /**
     * @Override por actualizar()
     * Este metodo es al que se llama desde la estacion base para pedir los datos.
     * El return debe ser un PaqueteDatos.
     */
//    public PaqueteDatos pedirData() {
//        String[] tipo = new String[redSensores.length];
//        String[] medicion = new String[redSensores.length];
//        // Creo el arreglo conlos tipos de sensores
//        for ( int i=0; i<=redSensores.length; i++ ) {
//            tipo[i] = redSensores[i].getClass().getSimpleName();
//            medicion[i] = redSensores[i].getMedicion();
//        }
//        int HH = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        int MM = Calendar.getInstance().get(Calendar.MINUTE);
//        int SS = Calendar.getInstance().get(Calendar.SECOND);
//        String hora = HH + ":" + MM + ":" + SS;
//
//        return new PaqueteDatos(ID, hora, tipo, medicion);
//    }
//    
    
    // @NEW
    // Agrego los sensores de esta estacion a los sensores de las sub-estaciones
    @Override
    public Stack<PaqueteDatos> actualizar() {
        // Informacion para PaqueteDatos()
        Integer[] sensoresID = new Integer[redSensores.length];
        String[] tipo = new String[redSensores.length];
        String[] medicion = new String[redSensores.length];
    
        // Cargo el resumen de la estacion si es que existe.
        XMLConfiguration registro = new XMLConfiguration();
        registro.setFileName(String.format("resumenes/%d", ID));
        try {
            registro.load();
        } catch (ConfigurationException ex) {
            // Nothing
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

    private void resumenSave(XMLConfiguration registro, Integer sensorID, String tipo, String medicion) {
        try {
            // Busco si ya hay algun registro del sensor.
            List<String> idsRegistro = registro.getList("Sensor.id");
            boolean existe = false;
            
            for (String id : idsRegistro) {
                if (Integer.valueOf(id).equals(sensorID)) {
                    existe = true;
                    break;
                }
            }
            // Si ya hay, actualizo
            if (existe) {
                
            }
            // Si no, creo y cargo los valores como iniciales
            else {
                registro.addProperty("id", sensorID.toString());
                registro.addProperty("tipo", tipo);
                registro.addProperty("maximo", medicion);
                registro.addProperty("minimo", medicion);
                registro.addProperty("medio", medicion);
                registro.addProperty("mediciones", new Integer(1));
            }
            registro.save();
        } catch (ConfigurationException ex) {
            Logger.getLogger(EstacionMet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
