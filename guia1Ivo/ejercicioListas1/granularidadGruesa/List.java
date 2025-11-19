package ejercicioListas1.granularidadGruesa;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Sincronización de granularidad gruesa
 * - Un lock para TODA LA ESTRUCTURA
 * - 
 */
public class List {
    Node head;
    private Lock lock = new ReentrantLock();

    public List() {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);

    }

    public Boolean add(Object o) {
        Node pred, curr;
        int key = o.hashCode();
        lock.lock();

        try {
            pred = head;
            curr = pred.next;

            while (curr.key < key) {
                pred = curr;
                curr = pred.next;
            }

            if (key == curr.key)
                return false;
            else {
                Node node = new Node(key);
                node.next = curr;
                pred.next = node;
                return true;
            }

        } finally {
            lock.unlock();
        }

    }

    public Boolean remove(Object o) {
        Node pred, curr;
        int key = o.hashCode();
        lock.lock();

        try {
            pred = head;
            curr = pred.next;

            while (curr.key < key) {

                pred = curr;
                curr = pred.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            }
            return false;

        } finally {
            lock.unlock();
        }

    }
}
