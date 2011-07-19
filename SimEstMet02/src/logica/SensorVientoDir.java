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
    public enum Direccion { S, SE, E, NE, N, NO, O, SO };
    
//     public SensorVientoDir() {
//         
//     }

    /* *** Setters y Getters *** */

    /*
     *  No existe un metodo getVientoDir() porque seria igual a getMedicion()
     */
    @Override
    public String getMedicion() {
        return vientoDir;
    }

    public void setViento (Direccion dir) {
        vientoDir = dir.toString();
    }

    @Override
    public void setMedicion(String medicion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* *** Otros metodos *** */

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
