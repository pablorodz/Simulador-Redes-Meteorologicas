/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Indicación en km/h.
 * Indicación S, SE, E, NE, N, NO, O, SO.
 */
public class SensorViento extends Sensor {
    // Velocidad del viento medida en km/h. Variable siempre positiva y menor 
    // que VEL_MAX.
    private float vientoVel;
    // Direccion del viento.
    private Direccion vientoDir;

    // Maxima velocidad del viento en km/h
    public static int VEL_MAX = 120;
    // Valores que puede tomar la direccion del viento
    public enum Direccion { S, SE, E, NE, N, NO, O, SO };
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(SensorViento.class .getName());

     public SensorViento() {
         super();
         
         // Inicializo el sensor
         // La velocidad se setea aleatoria porque despues al actualizar solo 
         // se agrega un offset. Es siempre positiva.
         vientoVel = random.nextInt(VEL_MAX) + (float)random.nextInt(100) / 100;
         vientoDir = Direccion.N;

         LOGGER.log(Level.INFO, String.format("Creado sensor de viento, ID = %d", ID));         
     }

    /* *** Setters y Getters *** */

    public float getVientoVel() {
        actualizarVel();
        LOGGER.log(Level.INFO, "getVientoVel()");
        return vientoVel;
    }
    
    public Direccion getVientoDir() {
        actualizarDir();
        LOGGER.log(Level.INFO, "getVientoDir()");
        return vientoDir;
    }
    
    @Override
    public String getMedicion() {
        //return String.format("%f, %s", getVientoVel(), getVientoDir().toString());
        return (String.valueOf( getVientoVel()) + " " + getVientoDir().toString());
    }

    public void setVientoVel(float vientoVel) {
        if (vientoVel < 0 || vientoVel > VEL_MAX)
            throw new InputMismatchException(String.format("La variable debe ser"
                    + "mayor que 0 y menor que %d", VEL_MAX));
        
        this.vientoVel = vientoVel;
    }
    
    public void setVientoDir(Direccion dir) {
        vientoDir = dir;
    }

    /*
     * No es recomendable usar este metodo, se recomienda hacer uso de los
     * metodos individuales (setVientoVel() y setVientoDir()
     * 
     * @arg medicion Es el valor que se le quiere dar a la medicion. "float Direccion"
     */
    @Override
    public void setMedicion(String medicion) {
        String[] token = new String[2];
        if (medicion.lastIndexOf(" ") != -1) {
            try {
                token = medicion.split(" ");
                setVientoVel( Float.valueOf(token[0]) );
                setVientoDir( strToDir(token[1]) );
            }
            catch (InputMismatchException ie) {
                LOGGER.log(Level.WARNING, "No se pudo setear la medicion. La "
                        + "direccion no es valida.");
                // En la interfaz grafica que salte un cartel
            }
        }
        else {
            LOGGER.log(Level.WARNING, "No se pudo setear la medición, mal"
                    + " formato del argumento");
        }
    }

    /* *** Otros metodos *** */
    
    /*
     * @brief Calcula y seatea el valor de la velocidad del viento de forma aleatoria.
     * 
     * Tomando el valor de la medicion, se calcula un delta aleatorio, y se
     * actualiza el valor de la "medicion".
     * El valor pseudoaleatorio del delta es entre 0.00 y 1.99.
     */
    private void actualizarVel() {
        // Signo de la variacion, Sumo(1) o resto(-1)
        int signo = (-1) + random.nextInt(2)*2;
        
        // La variacion pseudoaleatoria con la que se modifica la velocidad
        float delta = (float)random.nextInt(2) + (float)random.nextInt(100) / 100;

        // Calculo el nuevo valor de la medicion.
        // Tiene que ser positiva. Como el delta puede ser positivo o negativo
        // se usa Math.abs
        vientoVel = Math.abs(vientoVel + signo*delta);
        // Ademas tiene que ser menor que VEL_MAX
        // Una solucion: "vientoVel = vientoVel % VEL_MAX;" pero hace que el 
        // valor varie mucho y es poco real.
        // Si al ponerle la variacion a la velocidad ésta se va de rango, le
        // resto o sumo dos veces el delta, porque fue a causa del delta que se
        // fue de rango.
        if ( vientoVel > VEL_MAX )
            vientoVel -= 2*delta;
    }

    /*
     * @brief Calcula y seatea el valor de la direccion del viento de forma aleatoria.
     * 
     * Se elige una de las direcciones, de forma pseudoaleatoria.
     * Para hacer un poco mas real el sensor, se trata de ponderar la 
     * aleatoriedad, dandole mas probabilidades al estado actual.
     * Esto se hace mirando el valor pseudoaleatorio calculado, y si el valor
     * es menor a la prioridad de todos los valores no actuales 
     * ( es decir, 1 - prioridadDelActual) se cambia de estado, es decir, cambia
     * la direccion del viento. La nueva direccion se busca nuevamente de manera
     * aleatoria.
     * Luego se actualiza el valor de la "medicion".
     */
    private void actualizarDir() {
        // Variables que se usan para decidir si seguir o no con los valores actuales
        float aleatorio = random.nextFloat();
        float prioridad = (float) 0.75; // La prioridad que se le da al valor actual
        
        // Calculo la variacion en la direccion ponderando la direccion actual
        if (aleatorio <= (1 - prioridad)) {    // Si cambia la direccion del viento
            // # Ver si se puede hacer de una manera mas prolija
            switch (random.nextInt(8)) {
                case 0: vientoDir = Direccion.E; break;
                case 1: vientoDir = Direccion.N; break;
                case 2: vientoDir = Direccion.NE; break;
                case 3: vientoDir = Direccion.NO; break;
                case 4: vientoDir = Direccion.O; break;
                case 5: vientoDir = Direccion.S; break;
                case 6: vientoDir = Direccion.SE; break;
                case 7: vientoDir = Direccion.SO; break;
            }
        }
    }

    /*
     * @brief Calcula y seatea el valor de la medicion de forma aleatoria
     */
    @Override
    public void actualizar() {
        actualizarVel();
        actualizarDir();
    }
    
    /*
     * @brief Convierte de string a Direccion. En caso de no poder, deja notficado.
     */
    private Direccion strToDir(String string) throws InputMismatchException {
        Direccion direccion = null;
        
        if (Direccion.E.toString().equalsIgnoreCase(string))
                direccion = Direccion.E;
        else if (Direccion.N.toString().equalsIgnoreCase(string))
                direccion = Direccion.N;
        else if (Direccion.NE.toString().equalsIgnoreCase(string))
                direccion = Direccion.NE;
        else if (Direccion.NO.toString().equalsIgnoreCase(string))
                direccion = Direccion.NO;
        else if (Direccion.O.toString().equalsIgnoreCase(string))
                direccion = Direccion.O;
        else if (Direccion.S.toString().equalsIgnoreCase(string))
                direccion = Direccion.S;
        else if (Direccion.SE.toString().equalsIgnoreCase(string))
                direccion = Direccion.SE;
        else if (Direccion.SO.toString().equalsIgnoreCase(string))
                direccion = Direccion.SO;
        else
            throw new InputMismatchException("No se pudo setear la medición, "
                    + "mal formato del argumento");

        return direccion;
    }
    
}
