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
