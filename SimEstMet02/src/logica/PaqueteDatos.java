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

    /* *** Propiedades *** */

    // Primera parte - Cabecera
    private int IDestacion;
    private String hora;  // String -> HH:MM:SS
    // Segunda parte - Cuerpo
    private String[] tipo;  // Es el arreglo de sensores que posee la estacion. MAX 4
    private String[] medicion;  // Corresponde a la medicion de cada sensor en la estacion. MAX 4

    /* *** Constructores *** */

    /*
     *  El arreglo con los tipos de sensores y la medicion lo pongo en el
     *  constructor para que sea mas simple la utilizacions de la clase.
     */
    public PaqueteDatos(int IDestacion, String hora, String[] tipo, String[] medicion) {
        // Cabecera
        this.IDestacion = IDestacion;
        this.hora = hora;
        // Cuerpo ( Se carga a parte )
        this.tipo = tipo;
        this.medicion = medicion;
    }

}
