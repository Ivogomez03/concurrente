package ejercicioListas1.libreDeLocks;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Node {
    public Object item;
    public int key;
    public AtomicMarkableReference<Node> next;

    public Node(int key) {
        this.key = key;
        this.next = new AtomicMarkableReference<Node>(null, false);
    }

}
