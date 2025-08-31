
/*********************************************************************
@author: Ivogomez03
github: https://www.github.com/Ivogomez03

Ejercicio 5

Considere los siguientes dos procesos

T_1 = while true do print(A)
T_2 = while true do print(B)

a) Utilizar semáforos para garantizar que en todo momento la cantidad de A y B difiera al máximo
en 1.
b) Modificar la solución para que la única salida posible sea ABABABABAB...

*********************************************************************/
package ejercicio6;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

class Ejercicio6 {

    private Map<Integer, Semaphore> semaforos = new HashMap<>();
    private int N2 = 0;
    private int N = 50;

    public Ejercicio6() {
        for (int i = 1; i < 3; i++) {
            semaforos.put(i, new Semaphore(0));
        }
    }

    public static void main(String[] argumentos) {

        Ejercicio6 ej6 = new Ejercicio6();

        new Thread(new Hilo1(ej6), "Hilo1").start();
        new Thread(new Hilo2(ej6), "Hilo2").start();
    }

    private Semaphore obtenerSemaforo(int numeroDeSemaforo) {
        Semaphore s = this.semaforos.get(numeroDeSemaforo);
        if (s == null)
            throw new IllegalArgumentException("Número de semáforo inválido: " + numeroDeSemaforo);
        return s;
    }

    public void upSemaforo(int numeroDeSemaforo) {
        obtenerSemaforo(numeroDeSemaforo).release();
    }

    public void downSemaforo(int numeroDeSemaforo) {
        obtenerSemaforo(numeroDeSemaforo).acquireUninterruptibly();
    }

    public void disminuirN() {
        this.N--;
    }

    public int obtenerN() {
        return this.N;
    }

    public int obtenerN2() {
        return this.N2;
    }

    public void sumarEnesimoImpar_N2() {
        this.N2 += this.N * 2 - 1;
    }

}

/*
 * Thread = hilo de ejecución dentro de un programa.
 * 
 * La JVM permite múltiples hilos concurrentes.
 * 
 * Cada hilo tiene:
 * 
 * Prioridad → los de mayor prioridad se ejecutan antes.
 * 
 * Puede ser daemon o no →
 * 
 * Daemon: hilo en segundo plano (ej. recolector de basura).
 * 
 * No daemon: hilo “principal” de la app.
 * 
 * Cuando se crea un nuevo hilo:
 * 
 * Su prioridad es la misma que la del hilo creador.
 * 
 * Será daemon solo si el hilo creador es daemon.
 * 
 * El main es el primer hilo no-daemon que arranca la JVM.
 * 
 * Todos los hilos no-daemon terminan.
 * 
 * Formas de crear un hilo:
 * 
 * Extender la clase Thread y sobreescribir run().
 * 
 * También existe implementar Runnable, (como hacemos en los ejercicios)
 * 
 * https://learn.microsoft.com/en-us/dotnet/api/java.lang.thread?view=net-
 * android-35.0
 */

/*
 * 
 * Un semáforo controla el acceso a un recurso compartido usando un contador de
 * permisos (permits).
 * 
 * Si el contador > 0 → un hilo puede entrar (se le da un permiso y se
 * decrementa).
 * 
 * Si el contador = 0 → el hilo queda bloqueado hasta que haya permisos
 * disponibles.
 * 
 * Funcionamiento básico
 * 
 * El hilo llama a acquireUninterruptibly() → intenta conseguir un permiso.
 * 
 * Si hay permisos → entra, el contador baja.
 * 
 * Si no hay permisos → queda esperando.
 * 
 * Cuando termina → llama a release() y devuelve el permiso (contador sube).
 * 
 * Otro hilo que estaba esperando puede entrar.
 * 
 * Clase en Java
 * Se usa java.util.concurrent.Semaphore.
 * 
 * Constructor
 * 
 * Semaphore(int num) → número inicial de permisos.
 * 
 * 
 * Como bloquear o liberar:
 * 
 * Hilo llama acquireUninterruptibly() antes de usar el recurso (bloquea).
 * 
 * Hilo llama release() cuando termina (desbloquea).
 * 
 * Si num = 1, se comporta como un candado exclusivo (mutex).
 * 
 * https://www.geeksforgeeks.org/java/semaphore-in-java/
 */