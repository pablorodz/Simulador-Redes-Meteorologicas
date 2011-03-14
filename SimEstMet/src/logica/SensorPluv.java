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
     *  lluvia total = 0. 
     */
//     public SensorPluv () {
//         
//     }

    /** 
     *  Lluvia caída en los últimos 5 minutos. 
     */
    public double getLluvia () {
        return lluviaInstantanea;
    }

    /* 
     *  * Settearlo a mano o segun un archivo externo con los valores del viento en cada instante de tiempo.
     *  * Debe existir un setter?
     *  * Publico? 
     */
    public void setLluvia (double lluvia) {
        this.lluviaInstantanea = lluvia;
    }

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
