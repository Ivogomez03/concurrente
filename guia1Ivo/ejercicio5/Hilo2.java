package ejercicio5;

class Hilo2 implements Runnable {

    private Ejercicio5 ejercicio5;

    public Hilo2(Ejercicio5 ejercicio5) {
        this.ejercicio5 = ejercicio5;
    }

    @Override
    public void run() {
        int i = 0;
        do {
            if (ejercicio5.obtenerContador() == -1)
                ejercicio5.downSemaforo(2);

            System.out.print("B");

            ejercicio5.disminuirContador();

            ejercicio5.upSemaforo(1);
            i++;
        } while (i < 5);
    }
}