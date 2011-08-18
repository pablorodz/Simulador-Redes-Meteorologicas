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
 *  @file SensorPluv.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Pluviómetro.
 *  Indica la lluvia caída en los últimos 5 minutos en mm.
 */
public class SensorPluv extends Sensor {
    // Lluvia caida en los últimos 5 minutos. Variable siempre positiva, menor 
    // que LLUVIA_MAX y en mm.
    private float lluviaInstantanea;
    // Maxima cantidad de lluvia que puede caer en 5 minutos, en mm.
    public static int LLUVIA_MAX = 10;

    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(SensorPluv.class .getName());

    /** 
     *  Constructor.
     */
     public SensorPluv () {
         super();
         
         // Inicializo el sensor. random = 1 --> llueve
         if (random.nextBoolean() == true) // LLueve?
             lluviaInstantanea = randomLluvia();
         else
             lluviaInstantanea = 0;
         
         LOGGER.log(Level.INFO, String.format("Creado sensor de humedad, ID = %d", ID));
     }

    /* *** Setters y Getters *** */

    /** 
     *  Lluvia caída en los últimos 5 minutos. 
     */
    public float getLluvia() {
        actualizar();
        LOGGER.log(Level.INFO, "getLluvia");
        return lluviaInstantanea;
    }

    @Override
    public String getMedicion() {
        return String.valueOf(getLluvia());
    }

    /**
     * Setea de forma externa el estado (medicion) del sensor.
     * 
     * Al ser un simulador tiene sentido que los valores medidos sean seteados
     * externamente por valores cargados de archivos con datos previos.
     */
    public void setLluvia (float lluvia) throws InputMismatchException {
        if (lluvia < 0 || lluvia > LLUVIA_MAX)
            throw new InputMismatchException(String.format("La variable debe "
                    + "ser no negativa y menor que %d", LLUVIA_MAX));
            
        this.lluviaInstantanea = lluvia;
    }

    /**
     * Setea de forma externa el estado (medicion) del sensor.
     * 
     * Al ser un simulador tiene sentido que los valores medidos sean seteados
     * externamente por valores cargados de archivos con datos previos.
     */
    @Override
    public void setMedicion(String medicion) {
        setLluvia( Float.valueOf(medicion) );
    }

    /* *** Otros metodos *** */

    /*
     * @brief Calcula un valor valido que puede tomar la variable del sensor.
     *
     * El valor pseudoaleatorio de la medicion varia entre 0.00 y LLUVIA_MAX.
     */
    private float randomLluvia() {
        float lluvia;
        
        // Calculo valor aleatorio, el cual es siempre positivo
        lluvia = (float)random.nextInt(LLUVIA_MAX) + (float)random.nextInt(100) / 100;
        // Y tiene que ser menor que LLUVIA_MAX
        if (lluvia > LLUVIA_MAX)
            // Tomo solo el resto de la division, el cual va a ser siempre 
            // menor que LLUVIA_MAX.
            // Esta solucion hace que el valor de la medicion varie mucho si 
            // LLUVIA_MAX es grande y es poco real. 
            // Como los valores de la lluvia caida en 5 min son chicos, se puede
            // usar esta solucion.
            lluvia = lluvia % LLUVIA_MAX;

        // Retorno el valor obtenido
        return lluvia;
    }

    /*
     * @brief Calcula y seatea el valor de la medicion de forma aleatoria.
     * 
     * Para hacer un poco mas real el sensor, se trata de ponderar la 
     * aleatoriedad, dandole mas probabilidades al estado actual.
     * Esto se hace mirando el estado actual (llueve o no), y si el valor 
     * pseudoaleatorio es menor a la prioridad de todos los valores no actuales
     * ( es decir, 1 - prioridadDelActual) se cambia de estado, es decir, si
     * llovia para, sino llovia, comienza a llover. 
     * Luego se actualiza el valor de la "medicion".
     * El valor pseudoaleatorio de la medicion varia entre 0.00 y LLUVIA_MAX.
     */
    @Override
    public void actualizar() {
        // Variables que se usan para decidir si seguir o no con los valores actuales
        float aleatorio = random.nextFloat();
        float prioridad = (float) 0.75;     // La prioridad que se le da al valor actual
        // Variable intermedia
        boolean llueve = false;
        
        if (lluviaInstantanea == 0) {       // Si no llueve
            if (aleatorio <= (1-prioridad)) // Y si pseudoaleatoriamente dice que va a llover, llueve
                llueve = true;
            // else                         // Si no, sigue sin llover
        }
        else {                              // Si lleve
            if (aleatorio <= (1-prioridad)) // Y si pseudoaleatoriamente dice que va a parar, para de llover
                lluviaInstantanea = 0;
            else                            // Si no, sigue lloviendo
                llueve = true;
        }
        
        // Si esta lloviendo calculo el valor pseudoaleatorio que deberia tomar
        if (llueve)
            lluviaInstantanea = randomLluvia();
        
    }
}
