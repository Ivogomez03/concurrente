package ejercicioListas8;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Stack {
    Node head;
    int size;
    Integer capacity;
    ReentrantLock lock;

    Condition notEmpty, notFull;

    public Stack(int c) {
        head = new Node(null);
        size = 0;
        capacity = c;
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public void push(Object o) throws InterruptedException {
        lock.lock();
        try {
            while (size == capacity)
                notFull.await();
            Node n = new Node(o);
            Node siguiente = head;
            head = n;
            head.next = siguiente;

            if (size++ == 0)
                notEmpty.signalAll();

        } finally {
            lock.unlock();
        }

    }

    public Object pop() throws InterruptedException {

        Object n;
        lock.lock();

        try {
            while (size == 0)
                notEmpty.await();
            n = head.item;
            head = head.next;
            if (size-- == capacity)
                notFull.signalAll();

        } finally {
            lock.unlock();
        }
        return n;

    }

}
