package ejercicio3;

class Hilo1 implements Runnable{
    private Ejercicio3 ejercicio3;

    public Hilo1(Ejercicio3 ejercicio3) {
        this.ejercicio3 = ejercicio3;
    }

    

    @Override
    public void run(){
        System.out.print("R ");
        ejercicio3.obtenerSemaforo(2).release();
        ejercicio3.obtenerSemaforo(1).acquireUninterruptibly();
        System.out.print("OK ");
       
    }
        
}