/**
 *  @file SensorPluv.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 *  Pluviómetro.
 *  Lluvia caída en los últimos 5 minutos.
 */
public class SensorPluv extends Sensor {
    private double lluviaInstantanea;  // En los últimos 5 minutos.

    /** 
     *  Constructor.
     */
//     public SensorPluv () {
//
//     }

    /* *** Setters y Getters *** */

    /** 
     *  Lluvia caída en los últimos 5 minutos. 
     */
    public double getLluvia() {
        return lluviaInstantanea;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getLluvia());
    }

    public void setLluvia (double lluvia) {
        this.lluviaInstantanea = lluvia;
    }

    @Override
    public void setMedicion(String medicion) {
        setLluvia( Double.valueOf(medicion) );
    }

    /* *** Otros metodos *** */
    
    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /** 
     *  LLuvia caída desde que se encendio el sensor. 
     */
//     public double getLluviaTotal () {
//         
//     }

}
