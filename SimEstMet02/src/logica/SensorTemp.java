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
 *  @file SensorTemp.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Sensor de temperatura.
 *  Indica la temperatura actual en ºC.
 */
public class SensorTemp extends Sensor {
    // Temperatura actual del sensor en ºC.
    private float temp;

    // Valor maximo que puede alcanzar la temperatura medida, en ºC, tanto 
    // positiva como negativa.
    public static int TEMP_MAX = 50;
    // El logger solo para esta clase
    private final static Logger LOGGER = Logger.getLogger(SensorTemp.class .getName());

     public SensorTemp() {
         super();
         
         // Inicializo el sensor
         // La temperatura se setea aleatoria porque despues al actualizar solo 
         // se agrega un offset.
         // Ademas solo cuando se instancia se elige tambien un signo de manera
         // aleatoria. El calculo del signo se hace de manera que el signo es
         // "-1" si el random es 0, y "1" si el random es 1.
         int signoInit = (-1) + random.nextInt(2)*2;
         temp = signoInit * (random.nextInt(TEMP_MAX) + (float)random.nextInt(100) / 100);
         
         LOGGER.log(Level.INFO, String.format("Creado sensor de temperatura, ID = %d", ID));         
     }

    /* *** Setters y Getters *** */

    public float getTemp() {
        actualizar();
        LOGGER.log(Level.INFO, "getTemp");
        return temp;
    }

    /**
     * Setea de forma externa el estado (medicion) del sensor.
     * 
     * Al ser un simulador tiene sentido que los valores medidos sean seteados
     * externamente por valores cargados de archivos con datos previos.
     */
    @Override
    public String getMedicion() {
        return String.valueOf(getTemp());
    }

    /**
     * Setea de forma externa el estado (medicion) del sensor.
     * 
     * Al ser un simulador tiene sentido que los valores medidos sean seteados
     * externamente por valores cargados de archivos con datos previos.
     */
    public void setTemp (float temp) throws InputMismatchException {
        if ( Math.abs(temp) > TEMP_MAX)
            throw new InputMismatchException( String.format("La variable debe "
                    + "ser estar entre [%d, %d]", TEMP_MAX, TEMP_MAX) );
        
        this.temp = temp;
    }

    @Override
    public void setMedicion(String medicion) {
        setTemp( Float.valueOf(medicion) );
    }

    /* *** Otros metodos *** */

    /*
     * Calcula y seatea el valor de la medicion de forma aleatoria.
     * 
     * Tomando el valor de la medicion, se calcula un delta aleatorio, y se
     * actualiza el valor de la "medicion".
     * El valor pseudoaleatorio del delta es entre 0.00 y 1.99.
     */
    @Override
    public void actualizar() {
        // Signo de la variacion, Sumo(1) o resto(-1)
        int signo = (-1) + random.nextInt(2)*2;
        
        // La variacion pseudoaleatoria con la que se modifica la temperatura
        float delta = (float)random.nextInt(2) + (float)random.nextInt(100) / 100;
        
        // Calculo el nuevo valor de la medicion.
        temp = (temp + signo*delta);
        
        // Tiene que ser menor que TEMP_MAX o mayor que (-1)*TEMP_MAX
        // Una solucion: "temp = temp % TEMP_MAX;" pero hace que el valor 
        // varie mucho y es poco real.
        // Si al ponerle la variacion a la temperatura ésta se va de rango, le
        // resto o sumo dos veces el delta, porque fue a causa del delta que se
        // fue de rango.
        if ( temp > TEMP_MAX ) {
            temp -= 2*delta;
        }
        else if ( temp < (-1*TEMP_MAX) ) {
            temp += 2*delta;
        }
             
    }

}
