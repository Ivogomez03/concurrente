package ejercicioListas1.granularidadFina;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
    public Object item;
    public int key;
    public Node next;
    public Lock lock = new ReentrantLock();

    public Node(int key) {
        this.key = key;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

}
