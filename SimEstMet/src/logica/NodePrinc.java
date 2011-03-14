/**
 *  @file NodePrinc.java
 * 
 *  @author Pablo Rodríguez <pablorodriguez.bb at gmail dot com>
 */

package logica;

/**
 * @brief Clase que representa un nodo de un grafo.
 * 
 * Cada nodo representa una Estacion.
 * Es por ello que es necesario el ID para crear un objeto.
 */
public class NodePrinc {
    private int ID;              /// Identificador del nodo
//     private String label;        /// Nombre del nodo
    private int cost;            /// Costo del minimo camino desde el nodo base
    private int modifBy;         /// ID del nodo que modifico el costo (cost).
    private NodeAdy firstAdy;    /// Primer nodo adyacente
    private boolean inSupernode; /// Indica si esta en el supernodo de Dijkstra

    /**
     * @brief Constructor base de la clase NodePrinc.
     * @param ID - Numero de identificacion de la Estacion.
     * @param cost - Costo del minimo camino desde el nodo base.
     * @param firstAdy - Primer nodo adyacente.
     */
    public NodePrinc(int ID, int cost, NodeAdy firstAdy) {
        this.ID = ID;
        this.cost = cost;
        this.firstAdy = firstAdy;
        inSupernode = false;
        // Valor por defecto. -1 porque los arreglos arancan en 0
        modifBy = -1;      
    }
    
    public NodePrinc(int ID, int cost) { this(ID, cost, null); }
    
    public NodePrinc(int ID) { this(ID, 1, null); }

    // Setters y Getters
/*    public void setLabel(String label) { this.label = label; }
    public String getLabel() { return label; }*/
    
    public int getID() { return ID; }
    
    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }
    
    public NodeAdy getFirstAdy() { return firstAdy; }
    public void setFirstAdy(NodeAdy firstAdy) { this.firstAdy = firstAdy; }
    
    public int getModifBy() { return modifBy; }
    public void setModifBy(int modifBy) { this.modifBy = modifBy; }
    
    public void setInSupernode() { inSupernode = true; }
    public boolean isInSupernode() { return inSupernode; }
}
