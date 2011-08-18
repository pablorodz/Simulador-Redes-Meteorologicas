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
 *  @file Sensor.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.Random;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Clase padre de todos los sensores
 * 
 * Es una clase abstracta porque tengo varios metodos definidos.
 * Cada sensor especifico posee su valor de la medicion.
 */
public abstract class Sensor {
    
    /* *** Propiedades *** */
    
    protected int ID;
    private static int IDsiguiente = 1000;
    private boolean estado;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(Sensor.class .getName());
    
    // TreeNode del sensor
    protected DefaultMutableTreeNode sensorTreeNode;
    
    // Creo un Random, porque se usa mucho en todas las subclases.
    protected Random random = new Random();
    
    public Sensor () {
        // Seteo el ID
        ID = IDsiguiente;
        IDsiguiente++;

        // Creo el treeNode
        sensorTreeNode = new DefaultMutableTreeNode(this);

        // Seteo el estado inicial
//        setEstado(false);
    }

    /* *** Setters y Getters *** */
    
    public int getID() { return ID; }
    public boolean isEstado () { return estado; }
    
    /**
     * Valor de la medicion del sensor
     * 
     * @return Valor de la medicion del sensor
     */
    public abstract String getMedicion();
    
    /**
     * Retorna el id que va a tener el siguiente sensor. Utilizado para saber
     * el maximo numero de sensores.
     * 
     * @return ID del siguiente sensor a crear
     */
    public static int getSiguienteID() {
        return IDsiguiente;
    }

    /**
     * Retorna el objeto TreeNode del sensor.
     * 
     * @return El objeto TreeNode del sensor
     */
    public DefaultMutableTreeNode getTreeNode() { return sensorTreeNode; }

    /**
     * Setea de forma externa el estado (medicion) del sensor.
     * 
     * Al ser un simulador tiene sentido que los valores medidos sean seteados
     * externamente por valores cargados de archivos con datos previos.
     */
    public abstract void setMedicion( String medicion);

    private void setEstado ( boolean estado ) { this.estado = estado; }
    
    /* *** Otros metodos *** */

    /**
     * Metodo que se llama cuando se actualiza el sistema
     */
    public abstract void actualizar();

    @Override
    public String toString() {
        return ( String.format("Sensor%d", ID) );
    }

}