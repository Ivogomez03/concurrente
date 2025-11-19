package ejercicioListas1.libreDeLocks;

import java.util.concurrent.atomic.AtomicMarkableReference;

/*
 * Sincronización Libre de locks
 * Una manera de reducir los costos de sincronización es arriesgarse: buscar sin adquirir bloqueos, 
 * bloquear los nodos encontrados y luego confirmar que los nodos bloqueados son correctos. Si un 
 * conflicto de sincronización provoca que se bloqueen los nodos incorrectos, entonces se deben 
 * liberar los bloqueos y comenzar de nuevo. Cuando este tipo de conflicto no ocurre seguido, esta 
 * técnica funciona bien, por lo que la llamamos sincronización optimista.
 */
public class List {
    private Node head;

    public List() {
        this.head = new Node(Integer.MIN_VALUE);
        Node node = new Node(Integer.MAX_VALUE);
        head.next = new AtomicMarkableReference<Node>(node, false);

    }

    public Boolean add(Object o) {
        Integer key = o.hashCode();
        while (true) {
            Window nodos = find(o);
            Node pred = nodos.pred, curr = nodos.curr;

            if (curr.key == key)
                return false; // si encontramos un nodo con el objeto o, significa que ya existe en la lista
            Node node = new Node(key);
            node.next = new AtomicMarkableReference<Node>(curr, false);
            if (pred.next.compareAndSet(curr, node, false, false))
                return true;
        }

    }

    public Boolean remove(Object o) {
        Integer key = o.hashCode();
        while (true) { // volver a empezar
            Window nodos = find(o);
            Node pred = nodos.pred, curr = nodos.curr, succ = nodos.succ;

            if (curr.key != key)
                return false;
            if (!curr.next.attemptMark(succ, true)) // Intenta cambiar la marca del puntero curr.next a true, pero solo
                                                    // si curr.next todavía está apuntando al nodo succ (y su marca
                                                    // actual es false)
                continue;
            pred.next.compareAndSet(curr, succ, false, false);
            return true;
        }

    }

    public Boolean contains(Object o) {
        Integer key = o.hashCode();
        Window window = find(o);
        Node curr = window.curr;
        return curr.key == key;

    }

    private Window find(Object o) {

        Node pred = null, curr = null, succ = null;

        boolean snip;
        boolean[] cmark = new boolean[1];

        int key = o.hashCode();
        retry: while (true) {
            pred = head;
            curr = pred.next.getReference();

            while (true) {
                succ = curr.next.get(cmark); // actualiza cmark con el valor de la marca de curr.next, osea esa marca
                                             // es true si curr esta eliminado lógicamente

                while (cmark[0]) { // solo entramos aca si curr esta eliminado logicamente
                    snip = pred.next.compareAndSet(curr, succ, false, false); // intento de eliminación fisica, devuelve
                                                                              // true si fue exitoso
                    if (!snip) // si fallo es porque otro hilo modifico pred o pred.next
                        continue retry;
                    curr = succ; // si fue exitoso seguimos limpiando nodos marcados
                    succ = curr.next.get(cmark);

                }

                if (curr.key >= key) { // curr es un nodo válido y no marcado
                    return new Window(pred, curr, succ); // devolvemos pred,curr,succ
                }

                pred = curr; // si curr.key es < key avanzamos un paso en la lista
                curr = succ;
            }

        }
    }

}
