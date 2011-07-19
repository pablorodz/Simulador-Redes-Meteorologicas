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

    /* *** Propiedades *** */

    /// El arreglo que indica todas estaciones que pertenecen a la estacion base.
    private Estacion[] totalEstaciones;

    /* *** Constructores *** */

    // Como se va a crear una unica estacion base, su nombre va a estar definido
    public EstacionBase() throws CreacionException {
        this( "Estacion Base");
        // En caso de tener poder crear mas de una estacion base, el nombre va a
        // ser mas generico.
        // super( Tipo.BASE );
    }

    public EstacionBase(String nombre) throws CreacionException {
        super( nombre, Tipo.BASE );
    }

}
