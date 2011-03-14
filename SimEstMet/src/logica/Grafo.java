/**
 *  @file Grafo.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  @brief Clase que representa un grafo.
 * 
 *  Grafo de estaciones conectadas.
 *      * Cada nodo del grafo es una Estacion.
 *      * El ID de cada estacion equivale a su indice en la lista de nodos.
 * 
 *  La implementacion realizada se basa en:
 *      http://cs.fit.edu/~ryan/java/programs/graph/Dijkstra-java.html
 */
public class Grafo {

/* **Variables** */
    private LinkedList<NodePrinc> nodes;      /// Lista de nodos que conforman el grafo.

    // Grafo ID
    private int IDsiguiente = 0;
    private int ID;
    
    // The Logger only for this class
    private final static Logger LOGGER = Logger.getLogger(Grafo.class .getName());

/* **Constructores** */
    
    /**
     *  @brief Constructor de la clase Grafo.
     */
    public Grafo() {
        nodes = new LinkedList();

        // Seteo el ID
        ID = IDsiguiente;
        IDsiguiente++;

        LOGGER.log(Level.INFO, String.format("Creado grafo %1$.", ID));
    }

/* **Metodos** */

    /**
     *  @brief Retorna el tamaño del grafo (cantidad de nodos que lo conforman).
     *
     *  @return Tamaño del grafo (cantidad de nodos que lo conforman).
     */
    public int size() { return nodes.size(); }

    /**
     *  @brief Retorna el ID del grafo
     *
     *  @return ID del grafo
     */
    public int getID () {
        return ID;
    }

    /**
     *  @brief Agrega un nodo (una estacion)
     *
     *  @param ID - ID de la estacion, index en la lista
     */
    public void addNode(int ID) {
        nodes.add(ID, new NodePrinc(ID, 0));

        LOGGER.log(Level.INFO, String.format(
                "Agregada estacion %1$ al grafo %2$.", ID, this.ID));
    }

    /**
     *  @brief Elimina un nodo (una estacion)
     *
     *  @param ID - ID de la estacion.
     */
    public void removeNode(int ID) {
        nodes.remove(ID);

        LOGGER.log(Level.INFO, String.format(
                "Eliminada estacion %1$ del grafo %2$.", ID, this.ID));
    }

    /**
     *  @brief Agrega una arista con coste formada por dos nodos.
     *
     *  La insersion se hace al inicio de la lista de adyacentes del nodo source.
     *  De esta manera el tiempo de insercion permanece constante sin importar
     *  la cantidad de elementos que alla.
     *
     *  Agrega una conexion desde la estacion $source a la estacion $target con
     *  peso $weight.
     *
     *  @param source - Nodo origen (index).
     *  @param target - Nodo destino (index).
     *  @param weight - Costo de la arista.
     */
    public void addEdge(int source, int target, int weight) { 
        // Inserto al inicio de la lista de adycentes del nodo source.
        NodeAdy newVertix = new NodeAdy( nodes.get(target), weight );
        newVertix.setNextNodeAdy( nodes.get(source).getFirstAdy() );
        nodes.get(source).setFirstAdy(newVertix);

        LOGGER.log(Level.INFO, String.format("Agregada la conexion de la "
                + "estacion %1$ a la estacion %2$, con peso %3$.",
                source, target, weight));
    }

    /**
     *  @brief Elimina una arista formada por dos nodos.
     *
     *  Elimina la conexion de la estacion $source con la estacion $target.
     * 
     *  @param source - Nodo origen.
     *  @param target - Nodo destino.
     *  @throws EdgeNotFoundException - Si la arista no existe.
     */    
    public void removeEdge(int source, int target) throws EdgeNotFoundException {
        if (nodes.get(source).getFirstAdy().getID() == target)
             // Pone al siguiente del primero como primero
            nodes.get(source).setFirstAdy(nodes.get(source).getFirstAdy().getNextNodeAdy());
        else {
            NodeAdy anterior = nodes.get(source).getFirstAdy();
            NodeAdy actual = anterior.getNextNodeAdy();
            for (NodeAdy node=actual; !(node.getID() == target); node = node.getNextNodeAdy()) {
                anterior = node;
                actual = node.getNextNodeAdy();
            }
            // Si la encontró
            if (actual.getID() == target)
                // Pone al siguiente del actual como siguiente del anterior
                anterior.setNextNodeAdy(actual.getNextNodeAdy());
            else
                throw new EdgeNotFoundException("Arista no encontrada");
        }

        LOGGER.log(Level.INFO, String.format("Eliminada la conexion de la "
                + "estacion %1$ a la estacion %2$.", source, target));
    }

    /**
     *  @brief Retorna el costo de una arista formada por dos nodos.
     *
     *  @param source - Nodo origen.
     *  @param target - Nodo destino.
     *  @throws EdgeNotFoundException - Si la arista no existe.
     */    
    public int getWeight(int source, int target) throws EdgeNotFoundException {
        NodeAdy actual = nodes.get(source).getFirstAdy();
        for (NodeAdy node=actual; !(node.getID() == target); node = node.getNextNodeAdy())
            actual = node;
        // Si la encontró
        if (actual.getID() == target)
            return actual.getWeight();
        else
            throw new EdgeNotFoundException("Arista no encontrada");
    }

    /**
     *  @brief Setea el coste de una arista formada por dos nodos.
     *
     *  Setea el peso, de la conexion de la estacion $source a la
     *  estacion $target, en $weght
     *
     *  @param source - Nodo origen.
     *  @param target - Nodo destino.
     *  @param weight - Costo de la arista.
     *  @throws EdgeNotFoundException - Si la arista no existe.
     */ 
    public void setWeight(int source, int target, int weight)
            throws EdgeNotFoundException {
        NodeAdy actual = nodes.get(source).getFirstAdy();
        for (NodeAdy node=actual; !(node.getID() == target); node = node.getNextNodeAdy())
            actual = node;
        if (actual.getID() == target)  // Si se encontro
            actual.setWeight(weight);
        else
            throw new EdgeNotFoundException("Arista no encontrada");

        LOGGER.log(Level.INFO, String.format("Seteado el peso, de la conexion "
                + "de la estacion %1$ a la estacion %2$, en %3$",
                source, target, weight));
    }

    /**
     *  @brief Devuelve el costo del camino minimo obtenido de ejecutar Dijkstra.
     *
     *  @param source - Nodo.
     *  @return Costo del camino minimo.
     */    
    public int getCost(int source) { return nodes.get(source).getCost(); }

    /**
     *  @brief Ejecuta el algoritmo de Dijkstra sobre el grafo.
     * 
     *  El algoritmo se ejecuta para el nodo correspondiente a la estacion 
     *  base (ID = 0).
     *  Ejecuta el algoritmo de Dijkstra sobre el grafo, asignando los costos
     *  minimos obtenidos a cada nodo del grafo. Además, en cada nodo se guarda
     *  quién fue el nodo que modificó su costo durante la ejecución del 
     *  algoritmo para poder reconstruir el camino minimo.
     */
    public void dijkstra() {
        int baseNodeID = 0;  // Es el ID de la Estacion Base
        // Seteo costos iniciales, costo nodo base = 0
        for (int i=0; i<nodes.size(); i++)
            nodes.get(i).setCost(Integer.MAX_VALUE);
        nodes.get(baseNodeID).setCost(0);
        // minimo = [lugar en el arreglo, costo]
        int[] minimo = {-1, Integer.MAX_VALUE};

        while (true) {
            // Busco el minimo
            for (int i=0; i<nodes.size(); i++)
                if (!(nodes.get(i).isInSupernode()) && nodes.get(i).getCost() < minimo[1]) {
                    minimo[0] = i; 
                    minimo[1] = nodes.get(i).getCost();
                }
            // Si minimo == Integer.MAX_VALUE, salgo
            if (minimo[0] == -1) 
                return;
            // Actualizo costos y por quien fue modificado
            for (NodeAdy nodoActual = nodes.get(minimo[0]).getFirstAdy(); nodoActual != null; nodoActual = nodoActual.getNextNodeAdy())
                if (nodes.get(nodoActual.getID()).getCost() > nodes.get(minimo[0]).getCost() + nodoActual.getWeight()) {
                    nodes.get(nodoActual.getID()).setCost(nodes.get(minimo[0]).getCost() + nodoActual.getWeight());
                    nodes.get(nodoActual.getID()).setModifBy(minimo[0]);
                }

            // Seteo el nodo que se uso como perteneciente al supernodo y reinicio el minimo.
            nodes.get(minimo[0]).setInSupernode();
            minimo[0] = -1; 
            minimo[1] = Integer.MAX_VALUE;
        }
    }
}
