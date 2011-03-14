/**
 *  @file EstacionBase.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/*
 *  Estacion base.
 *      * Solo se puede crear una. Esto se chequea en el constructor de Estacion
 *          debido a que no puedo preguntar primero si ya existe una y luego
 *          llamar al constructor de la superclase.
 *      * ID = 0.
 *      * La agrgacion es por estar el programa visto desde la estacion base (1/12/2010).
 *      * Lista de Estaciones Meteorologicas
 */
public class EstacionBase extends Estacion {
    
    /** super(ID=0) */
    public EstacionBase(Grafo grafo) throws CreacionException {
//        super( 0 );  // ID = 0
        this( "Estacion Base", grafo);
    }

    /** super( ID=0, label) */
    public EstacionBase(String nombre, Grafo grafo) throws CreacionException {
        super( 0, nombre, grafo );  // ID = 0
    }

}
