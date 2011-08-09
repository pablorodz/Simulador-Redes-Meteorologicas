/**
 *  @file SensorVientoDir.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Sensor de dirección de viento. 
 *  Indicación S, SE, E, NE, N, NO, O, SO.
 */
public class SensorVientoDir extends Sensor {
    private Direccion vientoDir;
    public enum Direccion { S, SE, E, NE, N, NO, O, SO };
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

     public SensorVientoDir() {
         super();
         // Inicializo el sensor
         vientoDir = Direccion.N;
         
         LOGGER.log(Level.INFO, String.format("Creado sensor de direccion del viento, ID = %d", ID));         
     }

    /* *** Setters y Getters *** */

    /*
     *  GetVientoDir() igual a getMedicion()
     */
    @Override
    public String getMedicion() {
        return getViento().toString();
    }

    public Direccion getViento() {
        return vientoDir;
    }
    
    public void setViento (Direccion dir) {
        vientoDir = dir;
    }

    @Override
    public void setMedicion(String medicion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* *** Otros metodos *** */

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
