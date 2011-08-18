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
 *  @file EstacionBase.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */
package logica;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * Clase estacion base.
 * 
 * Es la estacion raiz de toda la red. Solo puede crearse una.
 */
public class EstacionBase extends Estacion {

    /* *** Propiedades *** */

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(EstacionBase.class .getName());

    /* *** Constructores *** */

    // Como se va a crear una unica estacion base, su nombre va a estar definido
    public EstacionBase() throws CreacionException {
        this("Estacion Base");
    }

    public EstacionBase(String nombre) throws CreacionException {
        super( nombre, Tipo.BASE );
    }

    /* *** Otros metodos *** */
    
    /**
     * Agrega un sensor a la red.
     * 
     * @param sensorNuevo Sensor a agregar
     * @param padreID Id de la estacion donde debe agregarse el sensor
     * @return Si se agrego correctamente el sensor
     */
    @Override
    public boolean agregarSensor(Sensor sensorNuevo, int padreID) {
        boolean insertado = false;
        
        int i = 0;
        int redSize = redEstaciones.length;
        while(!insertado && i<redSize) {
            if (redEstaciones[i] != null)
                insertado = redEstaciones[i].agregarSensor(sensorNuevo, padreID);
            i++;
        }
        
        return insertado;
    }

    /**
     * Elimina un sensor de la red.
     * 
     * @param sensorElim Sensor a ser eliminado
     * @return Si se elimino correctamente el sensor
     */
    @Override
    public boolean eliminarSensor(Sensor sensorElim) {
        boolean eliminado = false;
        
        int i = 0;
        int redSize = redEstaciones.length;
        while(!eliminado && i<redSize) {
            if (redEstaciones[i] != null)
                eliminado = redEstaciones[i].eliminarSensor(sensorElim);
            i++;
        }
        
        return eliminado;
    }

    /**
     * Elimina un sensor de la red.
     * 
     * @param sensorElimID El id del sensor a ser eliminado
     * @return Si se elimino correctamente el sensor
     */
    @Override
    public boolean eliminarSensor(int sensorElimID) {
        boolean eliminado = false;
        
        int i = 0;
        int redSize = redEstaciones.length;
        while(!eliminado && i<redSize) {
            if (redEstaciones[i] != null)
                eliminado = redEstaciones[i].eliminarSensor(sensorElimID);
            i++;
        }
        
        return eliminado;
    }
    
    /**
     * Limpia la estacion base. Es como si se creara una nueva.
     */
    public void nueva() {
        int redSize = redEstaciones.length;
        for (int i=0; i<redSize; i++)
            redEstaciones[i] = null;
        
        estacionTreeNode.removeAllChildren();
    }
}
