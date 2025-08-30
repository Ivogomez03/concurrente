package ejercicio3;

class Hilo3 implements Runnable{

    private Ejercicio3 ejercicio3;

    public Hilo3(Ejercicio3 ejercicio3) {
        this.ejercicio3 = ejercicio3;
    }

    @Override
    public void run(){
        ejercicio3.obtenerSemaforo(3).acquireUninterruptibly();
        System.out.print("O ");
        ejercicio3.obtenerSemaforo(1).release();
        ejercicio3.obtenerSemaforo(2).release();
        System.out.print("OK ");
    
    }
}