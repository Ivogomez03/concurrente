package ejercicio1;


class Hilo2 implements Runnable{

    private Ejercicio1 ejercicio1;

    public Hilo2(Ejercicio1 ejercicio1) {
        this.ejercicio1 = ejercicio1;
    }

    @Override
    public void run(){
        System.out.println("E");
        ejercicio1.obtenerSemaforo(2).acquireUninterruptibly();
        System.out.println("F");
        ejercicio1.obtenerSemaforo(1).release();
        System.out.println("G");
    }
}