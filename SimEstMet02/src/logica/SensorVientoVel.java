/**
 *  @file SensorVientoVel.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Sensor de velocidad del viento.
 *  Indicación en km/h.
 */
public class SensorVientoVel extends Sensor {
    private double vientoVel;
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

     public SensorVientoVel() {
         super();
         // Inicializo el sensor
         vientoVel = 0;

         LOGGER.log(Level.INFO, String.format("Creado sensor de velocidad del viento, ID = %d", ID));         
     }

    /* *** Setters y Getters *** */

    public double getVientoVel() {
        return vientoVel;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getVientoVel());
    }

    public void setVientoVel(double vientoVel) {
        this.vientoVel = vientoVel;
    }

    @Override
    public void setMedicion(String medicion) {
        setVientoVel( Double.valueOf(medicion) );
    }

    /* *** Otros metodos *** */
    
    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
