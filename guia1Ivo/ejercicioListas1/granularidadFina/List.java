package ejercicioListas1.granularidadFina;

/*
 * Sincronización de granularidad fina
 * - Un lock para cada Nodo
 * - 
 */
public class List {
    Node head;

    public List() {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);

    }

    public Boolean add(Object o) {
        Node pred, curr;
        int key = o.hashCode();
        head.lock();
        pred = head;

        try {

            curr = pred.next;
            curr.lock();
            try {

                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }

                if (key == curr.key)
                    return false;

                Node node = new Node(key);
                node.next = curr;
                pred.next = node;
                return true;

            } finally {
                curr.unlock();

            }

        } finally {
            pred.unlock();
        }

    }

    public Boolean remove(Object o) {
        Node pred, curr;
        int key = o.hashCode();
        head.lock();
        pred = head;

        try {

            curr = pred.next;
            curr.lock();
            try {

                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    pred.next = curr.next;
                    return true;
                }
                return false;
            } finally {
                curr.unlock();
            }

        } finally {
            pred.unlock();
        }

    }
}
