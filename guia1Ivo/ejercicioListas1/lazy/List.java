package ejercicioListas1.lazy;

/*
 * Sincronización Lazy
 * Una desventaja del algoritmo OptimisticList (y de los algoritmos CoarseList y FineList) es que contains() 
 * adquiere bloqueos, lo cual resulta poco atractivo ya que las llamadas a contains() 
 * suelen ser mucho más frecuentes que las llamadas a otros métodos. Mostramos cómo refinar este algoritmo 
 * para que contains() sea sin espera, y los métodos add() y remove(), aunque todavía bloqueantes, 
 * recorran la lista solo una vez (en ausencia de contención).
 * 
 * Agregamos a cada nodo un campo marked booleano que indica si ese nodo está en el conjunto. Ahora, los 
 * recorridos no necesitan bloquear el nodo objetivo, y no es necesario validar que el nodo sea accesible 
 * volviendo a recorrer toda la lista. En su lugar, el algoritmo mantiene la invariante de que cada nodo 
 * no marcado es accesible. Si un hilo que recorre no encuentra un nodo, o lo encuentra marcado, entonces ese 
 * elemento no está en el conjunto.
 * 
 * Para añadir un elemento a la lista, add() recorre la lista, bloquea el predecesor del objetivo e inserta 
 * el nodo. 
 * 
 * El método remove() es lazy (perezoso), y consta de dos pasos: 
 *  primero marca el nodo objetivo, eliminándolo lógicamente, 
 *  y luego redirige el campo next de su predecesor, eliminándolo físicamente.

 */
public class List {
    Node head;

    public List() {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);

    }

    public Boolean add(Object o) {

        int key = o.hashCode();
        while (true) { // volver a intentar
            Node pred, curr;
            pred = head;
            curr = pred.next;

            while (curr.key < key) {
                pred = curr;
                curr = pred.next;
            }
            try {
                pred.lock();
                curr.lock();

                if (validate(pred, curr)) {
                    if (key == curr.key)
                        return false;

                    Node node = new Node(key);
                    node.next = curr;
                    pred.next = node;
                    return true;
                }

            } finally {
                pred.unlock();
                curr.unlock();
            }
        }

    }

    public Boolean remove(Object o) {
        int key = o.hashCode();
        while (true) {
            Node pred, curr;
            pred = head;
            curr = pred.next;

            while (curr.key < key) {
                pred = curr;
                curr = pred.next;
            }

            try {
                pred.lock();
                curr.lock();

                if (validate(pred, curr)) {
                    if (key == curr.key) {
                        curr.marked = true; // borrar lógicamente
                        pred.next = curr.next; // borrar físicamente
                        return true;
                    }
                    return false;
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }

        }

    }

    public Boolean contains(Object o) {
        int key = o.hashCode();
        Node curr = head;
        while (curr.key < key)
            curr = curr.next;
        return curr.key == key && !curr.marked;

    }

    private boolean validate(Node pred, Node curr) {
        return !pred.marked && !curr.marked && pred.next == curr;

    }

}
