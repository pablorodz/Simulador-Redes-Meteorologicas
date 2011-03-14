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

    public double getHumedad () {
        return humedad;
    }

    /* 
     *  * Settearlo a mano o segun un archivo externo con los valores de ls humedad en cada instante de tiempo.
     *  * Debe existir un setter?
     *  * Publico? 
     */
    public void setHumedad (double humedad) {
        this.humedad = humedad;
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
