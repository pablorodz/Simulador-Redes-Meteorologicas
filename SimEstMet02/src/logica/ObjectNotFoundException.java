/**
 *  @file ObjectNotFoundException.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 * @brief Excepcion que representa una arista que no existe.
 */
public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException (String msg) { super(msg); }
    public ObjectNotFoundException () { super(); }
}
