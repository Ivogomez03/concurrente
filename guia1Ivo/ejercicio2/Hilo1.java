package ejercicio2;

class Hilo1 implements Runnable{
    private Ejercicio2 ejercicio2;

    public Hilo1(Ejercicio2 ejercicio2) {
        this.ejercicio2 = ejercicio2;
    }

    

    @Override
    public void run(){
        // C se imprime siempre segunda
        ejercicio2.obtenerSemaforo(2).acquireUninterruptibly();
        System.out.print("C");
        
        ejercicio2.obtenerSemaforo(1).release();
       
        System.out.print("E");
        ejercicio2.obtenerSemaforo(3).release();
    }
        
}