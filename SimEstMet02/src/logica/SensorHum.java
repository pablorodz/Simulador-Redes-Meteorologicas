/**
 *  @file SensorHum.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Sensor de humedad.
 *  Indica la humedad actual en %
 */
public class SensorHum extends Sensor {
    // Valor de la medicion del sensor en %. Valor siempre positivo
    private float humedad;

    // Valor maximo permitido de la humedad, en %.
    public static int HUM_MAX = 100;
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(SensorHum.class .getName());

     public SensorHum() {
         super();
         
         // Inicializo el sensor
         // La humedad se setea aleatoria porque despues al actualizar solo se 
         // agrega un offset. Es siempre positiva.
         humedad = random.nextInt(HUM_MAX) + (float)random.nextInt(100) / 100;
         
         LOGGER.log(Level.INFO, String.format("Creado sensor de humedad, ID = %d", ID));
     }

    /* *** Setters y Getters *** */

    public float getHumedad () {
        actualizar();
        LOGGER.log(Level.INFO, "getHumedad");
        return humedad;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getHumedad());
    }

    public void setHumedad (float humedad) throws InputMismatchException {
        if (humedad < 0 || humedad > HUM_MAX)
            throw new InputMismatchException(String.format("La variable debe "
                    + "ser no negativa y menor que %d", HUM_MAX));
        
        this.humedad = humedad;
    }

    @Override
    public void setMedicion(String medicion) {
        setHumedad( Float.valueOf(medicion) );
    }

    /* *** Otros metodos *** */

    /*
     * Calcula y seatea el valor de la medicion de forma aleatoria.
     * 
     * Tomando el valor de la medicion, se calcula un delta aleatorio, y se
     * actualiza el valor de la "medicion".
     * El valor pseudoaleatorio del delta es entre 0.00 y 1.99.
     */
    @Override
    public void actualizar() {
        // Signo de la variacion, Sumo(1) o resto(-1)
        int signo = (-1) + random.nextInt(2)*2;
        
        // La variacion pseudoaleatoria con la que se modifica la huemdad
        float delta = (float)random.nextInt(2) + (float)random.nextInt(100) / 100;
        
        // Calculo el nuevo valor de la medicion.
        // Tiene que ser positiva. Como el delta puede ser positivo o negativo
        // se usa Math.abs
        humedad = Math.abs(humedad + signo*delta);
        // Ademas tiene que ser menor que HUM_MAX
        // Una solucion: "humedad = humedad % HUM_MAX;" pero hace que el 
        // valor varie mucho y es poco real.
        // Si al ponerle la variacion a la humedad ésta se va de rango, le
        // resto o sumo dos veces el delta, porque fue a causa del delta que se
        // fue de rango.
        if ( humedad > HUM_MAX )
            humedad -= 2*delta;
    }

}
