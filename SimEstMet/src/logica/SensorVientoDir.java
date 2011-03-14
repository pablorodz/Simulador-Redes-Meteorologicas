/**
 *  @file SensorVientoDir.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 *  Sensor de dirección de viento. 
 *  Indicación S, SE, E, NE, N, NO, O, SO.
 */
public class SensorVientoDir extends Sensor {
    private String vientoDir;

    public enum Direccion { S, SE, E, NE, N, NO, O, SO }
    
//     public SensorVientoDir() {
//         
//     }

    public String getVientoDir () {
        return vientoDir;
    }

    /* 
     *  * Settearlo a mano o segun un archivo externo con los valores del viento en cada instante de tiempo.
     *  * Debe existir un setter?
     *  * Publico? 
     */
    public void setViento (Direccion dir) {
        vientoDir = dir.toString();
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
