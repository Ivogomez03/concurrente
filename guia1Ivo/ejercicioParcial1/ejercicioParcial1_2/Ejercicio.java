package ejercicioParcial1.ejercicioParcial1_2;

import java.util.Random;

/*
 * Se desea modelar el control de tránsito sobre un puente que conecta a dos ciudades. Dado que el puente es
 * muy estrecho se debe evitar que dos autos circulen al mismo tiempo en sentido opuesto, dado que quedarian
 * atascados.
 * Cada coche debe ser modelado como un thread independiente que desea comenzar a atravesar el puente desde
 * un particular extremo. Atravesar no es una acción atómica, y por lo tanto, requiere de cierto tiempo. El
 * tiempo que transcurre desde comenzar a cruzar hasta finalizar puede ser modelado con un sleep().
 * 
 * Puede agregar en su modelo los threads y los monitores que considere necesarios.
 * Se solicita resolver los siguientes problemas usando monitores y asumiendo la política signal y continua
 * 
 * 1- Dar una solución libre de deadlocks que asegure que en todo momento los coches que se desplazan sobre
 * el puente lo hacen en el mismo sentido. Además debe garantizar que todos los coches que desean circular 
 * en el mismo sentido lo pueden hacer simultáneamente.
 * 
 * 2- Dar una solución al problema pero restringiendo la circulación de manera tal que al máximo 3 coches 
 * puedan circular por el puente al mismo tiempo. Además la solución debe ser libre de inanición. Explique
 * si su solución hace alguna suposición acerca del lock del monitor y las variables de condición.
 */
public class Ejercicio {
    public static void main(String[] args) {
        Puente puente = new Puente();

        for (Integer i = 0; i < 20; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Coche(i + 1, sentidoRandom(), puente), "Coche").start();

        }
    }

    private static Integer sentidoRandom() {
        Random random = new Random();

        if (random.nextDouble() < 0.5) {
            return 0;
        } else
            return 1;
    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
