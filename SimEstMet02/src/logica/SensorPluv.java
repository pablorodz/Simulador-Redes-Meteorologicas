/**
 *  @file SensorPluv.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Pluviómetro.
 *  Lluvia caída en los últimos 5 minutos.
 */
public class SensorPluv extends Sensor {
    private double lluviaInstantanea;  // En los últimos 5 minutos.

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

    /** 
     *  Constructor.
     */
     public SensorPluv () {
         super();
         // Inicializo el sensor
         lluviaInstantanea = 0;
         
         LOGGER.log(Level.INFO, String.format("Creado sensor de humedad, ID = %d", ID));
     }

    /* *** Setters y Getters *** */

    /** 
     *  Lluvia caída en los últimos 5 minutos. 
     */
    public double getLluvia() {
        return lluviaInstantanea;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getLluvia());
    }

    public void setLluvia (double lluvia) {
        this.lluviaInstantanea = lluvia;
    }

    @Override
    public void setMedicion(String medicion) {
        setLluvia( Double.valueOf(medicion) );
    }

    /* *** Otros metodos *** */
    
    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /** 
     *  LLuvia caída desde que se encendio el sensor. 
     */
//     public double getLluviaTotal () {
//         
//     }

}
