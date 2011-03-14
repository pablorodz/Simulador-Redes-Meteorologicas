/**
 *  @file ClockListener.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/* 
 *  Listener del reloj que se encarga de que todos los componentes que deban ser
 *  actualizados tengan el metodo actualizar(), para directamente suscribirlos 
 *  al clock
 */
public interface ClockListener {

    public void actualizar();

}
 
