package ejercicioListas7;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue {
    Node head;
    Node tail;
    AtomicInteger contadorIncrementos;
    AtomicInteger contadorDecrementos;
    Integer capacity;
    ReentrantLock lockEnq;
    ReentrantLock lockDeq;
    Condition notEmpty, notFull;

    public BoundedQueue(int c) {
        head = new Node(null);
        tail = head;
        contadorIncrementos = new AtomicInteger(0);
        contadorDecrementos = new AtomicInteger(0);
        capacity = c;
        lockEnq = new ReentrantLock();
        lockDeq = new ReentrantLock();
        notEmpty = lockDeq.newCondition();
        notFull = lockEnq.newCondition();
    }

    public void enq(Object o) throws InterruptedException {
        boolean mustWakeDequeuers = false;
        lockEnq.lock();

        try {
            while ((contadorIncrementos.get() - contadorDecrementos.get()) == capacity)
                notFull.await();
            Node n = new Node(o);
            tail.next = n;
            tail = n;
            if (contadorIncrementos.getAndIncrement() == 0)
                mustWakeDequeuers = true;

        } finally {
            lockEnq.unlock();
        }

        if (mustWakeDequeuers) {
            lockDeq.lock();
            try {
                notEmpty.signalAll();
            } finally {
                lockDeq.unlock();
            }
        }

    }

    public Object deq() throws InterruptedException {
        boolean mustWakeEnqueuers = false;
        Object n;
        lockDeq.lock();

        try {
            while (contadorIncrementos.get() == contadorDecrementos.get())
                notEmpty.await();
            n = head.next.item;
            head = head.next;
            if (contadorIncrementos.get() - contadorDecrementos.getAndIncrement() == capacity)
                mustWakeEnqueuers = true;

        } finally {
            lockDeq.unlock();
        }

        if (mustWakeEnqueuers) {
            lockEnq.lock();
            try {
                notFull.signalAll();
            } finally {
                lockEnq.unlock();
            }
        }
        return n;

    }
}
