package ejercicio2;

class Hilo2 implements Runnable{

    private Ejercicio2 ejercicio2;

    public Hilo2(Ejercicio2 ejercicio2) {
        this.ejercicio2 = ejercicio2;
    }

    @Override
    public void run(){
        // A se imprime siempre primero
        System.out.print("A");
        ejercicio2.obtenerSemaforo(2).release();
        ejercicio2.obtenerSemaforo(1).acquireUninterruptibly();

      
        System.out.print("R");
        // O se imprime siempre ultima

        ejercicio2.obtenerSemaforo(3).acquireUninterruptibly();
        System.out.print("O");
    }
}