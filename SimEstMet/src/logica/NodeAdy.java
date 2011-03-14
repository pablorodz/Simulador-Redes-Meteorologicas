/**
 *  @file NodeAdy.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 * @brief Clase que representa un nodo adyacente.
 */
public class NodeAdy {
    private int ID;              /// ID del nodo principal
//     private String label;        /// Nombre del nodo principal
    private int weight;          /// Costo de la arista entre este nodo y el nodo principal
    private NodeAdy nextNodeAdy; /// Siguiente nodo adyacente del nodo principal

    /**
     * @brief Constructor de la clase NodoAdy.
     * @param node - Nodo al cual se refiere el adyacente.
     * @param weight - Costo de la arista formada.
     */
    public NodeAdy(NodePrinc node, int weight) {
//         label = node.getLabel();
        ID = node.getID();
        this.weight = weight;
    }

    // Setters and Getters
//     public void setLabel(String label) { this.label = label; }
//     public String getLabel() { return label; }

    public void setID(int ID) { this.ID = ID; }
    public int getID() { return ID; }
    
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    
    public NodeAdy getNextNodeAdy() { return nextNodeAdy; }   
    public void setNextNodeAdy(NodeAdy nextNodeAdy) { this.nextNodeAdy = nextNodeAdy; }
}
