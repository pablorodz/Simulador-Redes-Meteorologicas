/**
 *  @file SensorVientoVel.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 *  Sensor de velocidad del viento.
 *  Indicación en km/h.
 */
public class SensorVientoVel extends Sensor {
    private double vientoVel;

//     public SensorVientoVel() {
//         
//     }

    public double getVientoVel () {
        return vientoVel;
    }

    /* 
     *  * Settearlo a mano o segun un archivo externo con los valores del viento en cada instante de tiempo.
     *  * Debe existir un setter?
     *  * Publico? 
     */
    public void setVientoVel (double vientoVel) {
        this.vientoVel = vientoVel;
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
