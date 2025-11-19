package ejercicioListas6;

import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Queue {
    private final Stack<Object> in = new Stack<Object>();
    private final Stack<Object> out = new Stack<Object>();
    private Lock lockIn = new ReentrantLock();
    private Lock lockOut = new ReentrantLock();

    public void enq(Object o) {
        lockIn.lock();
        try {
            in.push(o);
        } finally {
            lockIn.unlock();
        }

    }

    public Object deq() {
        lockOut.lock();
        try {
            if (out.isEmpty()) {
                lockIn.lock();
                try {
                    while (!in.isEmpty()) {
                        out.push(in.pop());
                    }
                } finally {
                    lockIn.unlock();
                }

            }
            if (out.isEmpty())
                return null;
            return out.pop();

        } finally {
            lockOut.unlock();
        }
    }
}