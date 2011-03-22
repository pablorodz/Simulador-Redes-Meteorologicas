/**
 *  @file Sensor.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/*
 * Cada sensor especifico posee su valor de la medicion.
 * Es una clase abstracta porque tengo varios metodos definidos.
 */
public abstract class Sensor extends Componente implements ClockListener {
    protected int ID;
    private static int IDsiguiente = 1000;
    private boolean estado;

    // Variables no necesarias para el funcionamiento básico
//    private double[] rangoMedida;
//    private double precision;
//    private double offset;
//    private double sensibilidad;
//    private double resolucion;
//    private double rapidezDeRespuesta;
//    private double repetitividad;

    /** 
     *  Constructor.
     *  estado = false. 
     */
    public Sensor () {
        // Seteo el ID
        ID = IDsiguiente;
        IDsiguiente++;
        
//        rangoMedida = new double[2];
        setEstado(false);  // deberia poner solo estado = false ??
        
    }

    /* *** Setters y Getters *** */
    
    public int getID() { return ID; }
    public boolean isEstado () { return estado; }
//    public double[] getRangoMedida() { return rangoMedida; }
//    public double getPecision() { return precision; }
//    public double getOffset() { return offset; }
//    public double getSensibilidad() { return sensibilidad; }
//    public double getResolucion() { return resolucion; }
//    public double getRapidezDeRespuesta() { return rapidezDeRespuesta; }
//    public double getRepetitividad() { return repetitividad; }
    
    /** Podria eliminarse y dejar solo prender() apagar() */
    private void setEstado ( boolean estado ) { estado = false; }
//    public void setRangoMedida( double[] rangoMedida ) { this.rangoMedida = rangoMedida; }
//    public void setPecision( double precision ) { this.precision = precision; }
//    public void setOffset( double offset ) { this.offset = offset; }
//    public void setSensibilidad( double sensibilidad ) { this.sensibilidad = sensibilidad; }
//    public void setResolucion( double resolucion ) { this.resolucion = resolucion; }
//    public void setRapidezDeRespuesta( double rapidezDeRespuesta ) { this.rapidezDeRespuesta = rapidezDeRespuesta; }
//    public void setRepetitividad( double repetitividad ) { this.repetitividad = repetitividad; }
    
    /* *** Otros metodos *** */
    
    /** 
     *  Prende el sensor.
     *      * setEstado(true) 
     */
    public void prender () {
        setEstado(true);  // Si elimino setEstado() --> estado = true
    }

    /** 
     *  Apaga el sensor.
     *      * setEstado(false) */
    public void apagar () {
        setEstado(false);  // Si elimino setEstado() --> estado = false
    }
    
    public abstract void actualizar();

}