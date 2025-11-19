package ejercicioListas1.optimista;

/*
 * Sincronización Optimista
 * Una manera de reducir los costos de sincronización es arriesgarse: buscar sin adquirir bloqueos, 
 * bloquear los nodos encontrados y luego confirmar que los nodos bloqueados son correctos. Si un 
 * conflicto de sincronización provoca que se bloqueen los nodos incorrectos, entonces se deben 
 * liberar los bloqueos y comenzar de nuevo. Cuando este tipo de conflicto no ocurre seguido, esta 
 * técnica funciona bien, por lo que la llamamos sincronización optimista.
 */
public class List {
    Node head;

    public List() {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);

    }

    public Boolean add(Object o) {

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
                        pred.next = curr.next;
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
                    return curr.key == key;
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }

        }

    }

    private boolean validate(Node pred, Node curr) {
        Node node = head;
        while (node.key <= pred.key) {
            if (node == pred)
                return pred.next == curr;
            node = node.next;
        }
        return false;
    }

}
