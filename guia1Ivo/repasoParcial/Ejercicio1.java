/*********************************************************************
@author: Ivogomez03
github: https://www.github.com/Ivogomez03

7. En un edificio de dos pisos, hay un ascensor kosher que puede transportar hasta N personas.
El ascensor alterna continuamente entre la planta baja y el primer piso. Espera a que se llene
o hasta que ocurra un timeout, lo que suceda primero y luego cambia de piso. En ese momento,
todas las personas dentro del ascensor descienden y las que están esperando entran. El tiempo
que tarda el ascensor en cambiar de piso se puede simular utilizando la funcion moverse(), que
representa el paso del tiempo (esta función no libera locks, si el thread que la ejecuta hubiera 
adquirido alguno).

Usar monitores en Java, donde cada persona es un hilo (que puede dirigirse a cualquiera de los
dos pisos). Notar que la politica debe ser signal y continua. La solución debe ser libre de deadlocks.

¿Es la solución libre de inanición?

Modificar la solución de manera tal que haya usuarios con prioridad, es decir, cuando deben subir
al ascensor, comienzan los que tienen prioridad. Los sin prioridad, suben luego de los que tienen
prioridad. De todas maneras, si un usuario sin prioridad ya está dentro, no sale para cederle el puesto 
a uno con prioridad que llega luego.



*********************************************************************/
package repasoParcial;

import java.util.Random;

class Ejercicio1 {
    public static void main(String[] args) {
        int N = 10;

        Ascensor ascensor = new Ascensor(N, "Primer Piso");

        for (Integer i = 0; i < 50; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Persona("Persona" + ((i).toString()), ascensor, pisoRandom()), "Persona").start();

        }
    }

    private static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    private static String pisoRandom() {
        Random random = new Random();

        if (random.nextDouble() < 0.5) {
            return "Primer Piso";
        } else
            return "Planta Baja";
    }

}