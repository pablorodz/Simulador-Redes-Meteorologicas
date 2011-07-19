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

    /* *** Setters y Getters *** */

    public double getTemp() {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
