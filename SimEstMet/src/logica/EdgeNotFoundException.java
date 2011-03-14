/**
 *  @file EdgeNotFoundException.java
 * 
 *  @author Manuel Argüelles <manu.argue at gmail dot com>
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 * @brief Excepcion que representa una arista que no existe.
 */
public class EdgeNotFoundException extends Exception {

    public EdgeNotFoundException (String msg) { super(msg); }
    public EdgeNotFoundException () { super(); }
}
