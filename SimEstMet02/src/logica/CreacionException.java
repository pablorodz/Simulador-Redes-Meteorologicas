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
 *  @file CreacionException.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 * @brief Excepcion que se lanza cuando se quiere crear un objeto, pero este no se puede instanciar.
 */
public class CreacionException extends Exception {

    public CreacionException (String msg) { super(msg); }
    public CreacionException () { super(); }
}
