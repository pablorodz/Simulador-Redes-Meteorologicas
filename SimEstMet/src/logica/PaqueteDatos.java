/**
 *  @file PaqueteDatos.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/*
 *  Primera parte: ID de la estacion y hora
 *  Segunda parte: sensores
 */
public class PaqueteDatos {
    // Primera parte - Cabecera
    private int IDestacion;
    private int hora;  // int ??
    // Segunda parte - Cuerpo
    
    public PaqueteDatos(int IDestacion, int hora) {
        // Cabecera
        this.IDestacion = IDestacion;
        this.hora = hora;
        // Cuerpo
    }

}
