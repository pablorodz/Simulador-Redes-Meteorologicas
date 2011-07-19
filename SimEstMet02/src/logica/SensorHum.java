/**
 *  @file SensorHum.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 *  Sensor de humedad.
 *  Indica la humedad actual
 */
public class SensorHum extends Sensor {
    private double humedad;

//     public SensorHum() {
//         
//     }

    /* *** Setters y Getters *** */

    public double getHumedad () {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
