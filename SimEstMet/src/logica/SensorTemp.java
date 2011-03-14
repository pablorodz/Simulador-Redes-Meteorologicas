/**
 *  @file SensorTemp.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 *  Sensor de temperatura.
 *  Indica la temperatura actual.
 */
public class SensorTemp extends Sensor {
    private double temp;

//     public SensorTemp() {
//         
//     }

    public double getTemp () {
        return temp;
    }

    /* 
     *  * Settearlo a mano o segun un archivo externo con los valores del viento en cada instante de tiempo.
     *  * Debe existir un setter?
     *  * Publico? 
     */
    public void setTemp (double temp) {
        this.temp = temp;
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
