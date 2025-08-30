package ejercicio3;

class Hilo2 implements Runnable{

    private Ejercicio3 ejercicio3;

    public Hilo2(Ejercicio3 ejercicio3) {
        this.ejercicio3 = ejercicio3;
    }

    @Override
    public void run(){
        ejercicio3.obtenerSemaforo(2).acquireUninterruptibly();
        System.out.print("I ");
        ejercicio3.obtenerSemaforo(3).release();
        ejercicio3.obtenerSemaforo(2).acquireUninterruptibly();
        System.out.print("OK ");
    }
}