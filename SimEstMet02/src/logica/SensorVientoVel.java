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

    /* *** Setters y Getters *** */

    public double getVientoVel() {
        return vientoVel;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getVientoVel());
    }

    public void setVientoVel(double vientoVel) {
        this.vientoVel = vientoVel;
    }

    @Override
    public void setMedicion(String medicion) {
        setVientoVel( Double.valueOf(medicion) );
    }

    /* *** Otros metodos *** */
    
    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
