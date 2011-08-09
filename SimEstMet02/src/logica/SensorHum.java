/**
 *  @file SensorHum.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Sensor de humedad.
 *  Indica la humedad actual
 */
public class SensorHum extends Sensor {
    private double humedad;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

     public SensorHum() {
         super();
         // Inicializo el sensor
         humedad = 0;
         
         LOGGER.log(Level.INFO, String.format("Creado sensor de humedad, ID = %d", ID));
     }

    /* *** Setters y Getters *** */

    public double getHumedad () {
        actualizar();
        LOGGER.log(Level.INFO, "getHumedad");
        return humedad;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getHumedad());
    }

    public void setHumedad (double humedad) {
        this.humedad = humedad;
    }

    @Override
    public void setMedicion(String medicion) {
        setHumedad( Double.valueOf(medicion) );
    }

    /* *** Otros metodos *** */

    @Override
    public void actualizar() {
        Random random = new Random();
        
        humedad = random.nextDouble()*100;
    }

}
