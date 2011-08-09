/**
 *  @file Sensor.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.logging.Logger;

/*
 * Cada sensor especifico posee su valor de la medicion.
 * Es una clase abstracta porque tengo varios metodos definidos.
 */
public abstract class Sensor {
    protected int ID;
    private static int IDsiguiente = 1000;
    private boolean estado;
    // Define de que tipo es el sensor. Para utilizar en paquete datos.
    // Voy a probar primero con .getClass.getSimpleName()
//    public enum Tipo { HUM, PLUV, TEMP, VDIR, VVEL };  // Es public debido a que paquete de datos lo debe utilizar

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());
    
    /** 
     *  Constructor.
     *  estado = false. 
     */
    public Sensor () {
        // Seteo el ID
        ID = IDsiguiente;
        IDsiguiente++;

        // Seteo el estado inicial
        setEstado(false);  // deberia poner solo estado = false ??        
    }

    /* *** Setters y Getters *** */
    
    public int getID() { return ID; }
    public boolean isEstado () { return estado; }
    // El valor que mide cada sensor
    public abstract String getMedicion();
     /*
     *  * Settearlo a mano o segun un archivo externo con los valores.
     *  * Debe existir un setter?
     *  * Publico?
     */
    public abstract void setMedicion( String medicion);

    /** Podria eliminarse y dejar solo prender() apagar() */
    private void setEstado ( boolean estado ) { this.estado = estado; }
    
    /* *** Otros metodos *** */
    
    /** 
     *  Prende el sensor.
     *      * setEstado(true) 
     */
    public void prender () {
        setEstado(true);  // Si elimino setEstado() --> estado = true
    }

    /** 
     *  Apaga el sensor.
     *      * setEstado(false) */
    public void apagar () {
        setEstado(false);  // Si elimino setEstado() --> estado = false
    }

    // Implements ClockListener ??
    public abstract void actualizar();

}