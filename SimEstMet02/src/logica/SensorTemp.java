/**
 *  @file SensorTemp.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Sensor de temperatura.
 *  Indica la temperatura actual.
 */
public class SensorTemp extends Sensor {
    private double temp;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

     public SensorTemp() {
         super();
         // Inicializo el sensor
         temp = 0;
         
         LOGGER.log(Level.INFO, String.format("Creado sensor de temperatura, ID = %d", ID));         
     }

    /* *** Setters y Getters *** */

    public double getTemp() {
        actualizar();
        LOGGER.log(Level.INFO, "getTemp");
        return temp;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getTemp());
    }

    public void setTemp (double temp) {
        this.temp = temp;
    }

    @Override
    public void setMedicion(String medicion) {
        setTemp( Double.valueOf(medicion) );
    }

    /* *** Otros metodos *** */

    @Override
    public void actualizar() {
        Random random = new Random();

        temp = random.nextDouble()*100;
    }

}
