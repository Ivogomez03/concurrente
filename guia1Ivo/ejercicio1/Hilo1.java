package ejercicio1;


class Hilo1 implements Runnable{
    private Ejercicio1 ejercicio1;

    public Hilo1(Ejercicio1 ejercicio1) {
        this.ejercicio1 = ejercicio1;
    }

    @Override
    public void run(){
        // A se imprime antes de F
        System.out.println("A");
        ejercicio1.obtenerSemaforo(2).release();
        System.out.println("B");
        ejercicio1.obtenerSemaforo(1).acquireUninterruptibly();
        
        
        System.out.println("C");
    }
}