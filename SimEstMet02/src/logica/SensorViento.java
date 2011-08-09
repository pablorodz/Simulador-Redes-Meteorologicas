/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Indicación en km/h.
 * Indicación S, SE, E, NE, N, NO, O, SO.
 */
public class SensorViento extends Sensor {
    private double vientoVel;
    private Direccion vientoDir;

    public enum Direccion { S, SE, E, NE, N, NO, O, SO };
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Estacion.class .getName());

     public SensorViento() {
         super();
         // Inicializo el sensor
         vientoVel = 0;
         vientoDir = Direccion.N;

         LOGGER.log(Level.INFO, String.format("Creado sensor de viento, ID = %d", ID));         
     }

    /* *** Setters y Getters *** */

    public double getVientoVel() {
        return vientoVel;
    }
    
    public Direccion getVientoDir() {
        return vientoDir;
    }
    
    @Override
    public String getMedicion() {
        return String.format("%d, %s", getVientoVel(), getVientoDir().toString());
    }

    public void setVientoVel(double vientoVel) {
        this.vientoVel = vientoVel;
    }
    
    public void setVientoDir(Direccion dir) {
        vientoDir = dir;
    }

    /*
     * No es recomendable usar este metodo, se recomienda hacer uso de los
     * metodos individuales (setVientoVel() y setVientoDir()
     * 
     * @arg medicion Es el valor que se le quiere dar a la medicion. "double, Direccion"
     */
    @Override
    public void setMedicion(String medicion) {
        String[] token = new String[2];
        if (medicion.lastIndexOf(", ") != -1) {
            token = medicion.split(", ");
            setVientoVel( Double.valueOf(token[0]) );
            setVientoDir( strToDir(token[1]) );
        }
        else {
            LOGGER.log(Level.WARNING, "No se pudo setear la medición, mal"
                    + " formato del argumento");
        }
    }

    /* *** Otros metodos *** */
    
    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*
     * Convierte de string a Direccion. En caso de no poder, deja notficado.
     */
    private Direccion strToDir(String string) {
        Direccion direccion = null;
        
        if (Direccion.E.toString().equalsIgnoreCase(string))
                direccion = Direccion.E;
        else if (Direccion.N.toString().equalsIgnoreCase(string))
                direccion = Direccion.N;
        else if (Direccion.NE.toString().equalsIgnoreCase(string))
                direccion = Direccion.NE;
        else if (Direccion.NO.toString().equalsIgnoreCase(string))
                direccion = Direccion.NO;
        else if (Direccion.O.toString().equalsIgnoreCase(string))
                direccion = Direccion.O;
        else if (Direccion.S.toString().equalsIgnoreCase(string))
                direccion = Direccion.S;
        else if (Direccion.SE.toString().equalsIgnoreCase(string))
                direccion = Direccion.SE;
        else if (Direccion.SO.toString().equalsIgnoreCase(string))
                direccion = Direccion.SO;
        else
            LOGGER.log(Level.WARNING, "No se pudo setear la medición, mal"
                    + " formato del argumento");
        
        return direccion;
    }
    
}
