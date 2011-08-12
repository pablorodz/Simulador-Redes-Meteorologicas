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
    private Integer[] sensoresID;   // Es el arreglo con los ID de los sensores. MAX 4
    private String[] tipos;  // Es el arreglo de sensores que posee la estacion. MAX 4
    private String[] mediciones;  // Corresponde a la medicion de cada sensor en la estacion. MAX 4

    /* *** Constructores *** */

    /*
     *  El arreglo con los tipos de sensores y la medicion lo pongo en el
     *  constructor para que sea mas simple la utilizacions de la clase.
     */
    public PaqueteDatos(int IDestacion, String hora, Integer[] sensoresID, String[] tipos, String[] mediciones) {
        // Cabecera
        this.IDestacion = IDestacion;
        this.hora = hora;
        // Cuerpo ( Se carga a parte )
        this.sensoresID = sensoresID;
        this.tipos = tipos;
        this.mediciones = mediciones;
    }
    
    public void printDatos() {
        System.out.printf("Estacion: %d \n", IDestacion);
        System.out.printf("Hora: %s \n", hora);
        
        int largo = tipos.length;
        for (int i=0; i<largo; i++) {
            if(tipos[i] != null)
                System.out.printf("\tSensor: %s \tMedición: %s \n\n", tipos[i], mediciones[i]);
        }
    }
    
    /* *** Getters *** */
    /* No se usan, pero existen :) */
    
    public int getIDestacion() {
        return IDestacion;
    }

    public String[] getMediciones() {
        return mediciones;
    }

    public Integer[] getSensoresID() {
        return sensoresID;
    }

    public String[] getTipos() {
        return tipos;
    }
}
