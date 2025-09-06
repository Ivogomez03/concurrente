/*********************************************************************
@author: Ivogomez03
github: https://www.github.com/Ivogomez03

7. Modelar con semáforos un transbordador entre dos costas. El transbordador tiene capacidad para N
personas y funciona de la siguiente manera. Espera en una costa hasta llenarse y automáticamente
cambia de costa. Cuando llega a una costa se bajan todas las personas y suben las que están esperando.
Cada persona debe ser implementada como un thread independiente del transbordador.

Las personas tienen que:
1- Esperar a que llegue el barco a la costa
2- Esperar a que bajen todas las personas
3- Subir al barco
4- Cuando llegan a la otra costa bajan



*********************************************************************/
package ejercicio7;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

class Ejercicio7 {

    private final Map<String, Semaphore> semaforos = new HashMap<>();
    private int costas = 0; // arrancamos en costa 0, ir a 1, volver, repetir proceso
    private final int n = 50;

    public Ejercicio7() {

        semaforos.put("Barco", new Semaphore(0));
        semaforos.put("Subir", new Semaphore(0));
        semaforos.put("Bajar", new Semaphore(0));

    }

    public static void main(String[] argumentos) {

        Ejercicio7 ej7 = new Ejercicio7();

        new Thread(new Barco(ej7), "Barco").start();

        for (Integer i = 0; i <= ej7.obtenerN(); i++) {
            new Thread(new Persona(ej7, i), "Persona").start();
        }

    }

    public int obtenerN() {
        return this.n;
    }

    private Semaphore obtenerSemaforo(String sem) {
        Semaphore s = this.semaforos.get(sem);
        if (s == null)
            throw new IllegalArgumentException("Semáforo inválido: " + sem);
        return s;
    }

    public int obtenerCosta() {
        return this.costas;
    }

    public void cambiarCosta() {
        this.costas = 1 - this.costas;
    }

    public void upSemaforo(String sem) {
        obtenerSemaforo(sem).release();
    }

    public void downSemaforo(String sem) {
        obtenerSemaforo(sem).acquireUninterruptibly();
    }

}