package ejercicio6;

class Hilo1 implements Runnable {
    private Ejercicio6 ejercicio6;

    public Hilo1(Ejercicio6 ejercicio6) {
        this.ejercicio6 = ejercicio6;
    }

    @Override
    public void run() {

        while (ejercicio6.obtenerN() > 0) {
            ejercicio6.downSemaforo(1);
            ejercicio6.disminuirN();
            ejercicio6.upSemaforo(2);
        }
        System.out.println(ejercicio6.obtenerN2());
    }

}