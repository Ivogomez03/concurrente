package ejercicioParcialMonos;

/*
 * Algunos monos estan intentando cruzar un barranco. Hay una sola cuerda que atraviesa el barranco, y los monos
 * pueden cruzar de forma horizontal. Hasta cinco monos pueden estar colgados de la cuerda al mismo tiempo; si
 * hay mas de cinco, la cuerda se rompera y todos moriran. Además, si los monos que se mueven hacie el este se
 * encuentran con monos que cruzar hacia el oeste, todos caeran y moriran. Cada mono opera en un hilo separado.
 * 
 * a) un máximo de cinco monos pueden ejecutar crossRavine() al mismo tiempo
 * b) Todos los monos que están ejecutando crossRavine() se dirijen en la misma dirección
 * c) Ningún mono debe esperar innecesariamente. Es decir, si hay monos cruzando en una direccion y llega otro
 * que quiere cruzar en esa dirección lo puede hacer, a pesar de que hay monos esperando al otro lado.
 */
import java.util.Random;

public class Ejercicio {
    public static void main(String[] args) {
        Ravine ravine = new Ravine();

        for (Integer i = 0; i < 20; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Monkey(i + 1, sentidoRandom(), ravine), "Monkey").start();

        }
    }

    private static String sentidoRandom() {
        Random random = new Random();

        if (random.nextDouble() < 0.5) {
            return "Oeste";
        } else
            return "Este";
    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
