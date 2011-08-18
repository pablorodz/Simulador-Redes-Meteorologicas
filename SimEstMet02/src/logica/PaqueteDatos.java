/*
 * Simulador de Redes Meteorológicas
 * Copyright 2011 (C) Rodríguez Pablo Andrés
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; under version 2 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses>.
 */ 

/**
 *  @file PaqueteDatos.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 * Clase que recopila la informacion que es enviada de una estacion a otra.
 * 
 * Primera parte: ID de la estacion y hora
 * Segunda parte: sensores, tipos y mediciones
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
    /**
     * Constructor
     * 
     * @param IDestacion El id de la estacion de cual proviene al informacion
     * @param hora La hora en la cual se hicieron las mediciones
     * @param sensoresID Los id de los sensores que pertenecen a la estacion
     * @param tipos El tipo de los sensores
     * @param mediciones  Las mediciones tomadas de los sensores
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
    
    /**
     * metodo utilizado para imprimir la informacion
     */
    public void printDatos() {
        System.out.printf("Estacion: %d \n", IDestacion);
        System.out.printf("Hora: %s \n", hora);
        
        int largo = tipos.length;
        for (int i=0; i<largo; i++) {
            if(tipos[i] != null)
                System.out.printf("\tSensor: %d\n\tTipo: %s \tMedición: %s \n\n", sensoresID[i], tipos[i], mediciones[i]);
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
