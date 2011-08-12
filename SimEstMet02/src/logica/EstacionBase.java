/**
 *  @file EstacionBase.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.*;

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
    // private Estacion[] totalEstaciones;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(EstacionMet.class .getName());

    /* *** Constructores *** */

    // Como se va a crear una unica estacion base, su nombre va a estar definido
    public EstacionBase() throws CreacionException {
        this("Estacion Base");
        // En caso de tener poder crear mas de una estacion base, el nombre va a
        // ser mas generico.
        // super( Tipo.BASE );
    }

    public EstacionBase(String nombre) throws CreacionException {
        super( nombre, Tipo.BASE );
    }

    @Override
    public Stack<PaqueteDatos> actualizar() {
        // Ejecuta el metodo original, pero no usa su salida
        Stack<PaqueteDatos> datos = super.actualizar();
        
        return datos;
    }
    
    /*
     * @brief Ubicacion de los resumenes, en un Vector<String>
     * 
     * Retorna la ubicacion de los resumenes de esta estacion y de sus 
     * sub-estaciones, en un Vector<String>.
     * Se guardan en esa forma, primero la direccion propia y luego la de las 
     * sub-estaciones.
     * Se agrega al metodo de la superclase que antes de retornar la direccion
     * del resumen, actuliza el resumen.
     * 
     * @return Direccion donde se encuentra el resumen.
     */
    @Override
    public Vector<String> getResumen() {
        // Direcciones de todos los resumes de la red
        Vector<String> direcciones = super.getResumen();

        // Antes de retornar las direcciones, se actuliza el resumen
        actualizarResumen(direcciones);
        
        return direcciones;
    }
    
    /*
     * @brief Actualiza el resumen de la Estacion Base
     * 
     * Se crea un nuevo resumen a partir de los resumenes de todas las 
     * estaciones de la red.
     */
    private void actualizarResumen(Vector<String> direcciones) {
        // Instancio el manejador de XML
        XMLConfiguration registro = new XMLConfiguration();
        registro.setFileName(String.format("resumenes/%d.xml", ID));
        // Configurador local que se usa para cargar configuraciones externas
        XMLConfiguration config2add = new XMLConfiguration();
        // Objeto para operaciones intermedias
        File file;
        
        // Elimino el archivo actual
        // Se que existe porque lo creo en getResumen()
        file = new File(registro.getFileName());
        file.delete();
        
        // Seteo el nombre del elemento base
        registro.setRootElementName("resumen");
        // Y creo el archivo nuevamente
        try {
            registro.save();
        } catch (ConfigurationException ex1) {
            Logger.getLogger(EstacionBase.class.getName()).log(Level.SEVERE, null, ex1);
        }

        // Creo el resumen con resumenes
        // Trate de hacerlo de una manera mas prolija pero no se porque no podia
        // cargar el archivo de configuracion. Decia que no existia.
        int numDir = direcciones.size();
        // Inicio en 1 porque el indice 0 es la direccion de esta estacion.
        for (int i=1; i<numDir; i++) {
            file = new File(direcciones.elementAt(i));
            config2add.setFile(file);
//            System.out.println( String.format("%s\t%s", config2add.getFileName(), config2add.getFile().exists()) );
            
            try {
                config2add.load();
                registro.append(config2add);
                registro.save();
            } catch (ConfigurationException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
