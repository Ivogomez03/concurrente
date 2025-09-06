package ejercicio7;

/*
* El barco arranca vacio, tiene que:
1- Esperar a que se suban las personas
2- Cambiar de costa
3- Esperar a que se bajen las personas 
4- Esperar a que suban
5- repetir
*/
class Barco implements Runnable {
    private final Ejercicio7 ejercicio7;

    public Barco(Ejercicio7 ejercicio7) {
        this.ejercicio7 = ejercicio7;
    }

    @Override
    public void run() {

        while (true) {
            System.out.println("Personas esperando en la costa");
            System.out.println("Subiendo personas");
            for (int i = 0; i < ejercicio7.obtenerN(); i++) {

                ejercicio7.upSemaforo("Subir");
                ejercicio7.downSemaforo("Barco");
            }

            System.out.println("Cambiando de costa");

            System.out.println("Bajando personas");
            for (int i = 0; i < ejercicio7.obtenerN(); i++) {
                ejercicio7.upSemaforo("Bajar");
                ejercicio7.downSemaforo("Barco");

            }
            System.err.println("Personas abajo");

        }

    }
}