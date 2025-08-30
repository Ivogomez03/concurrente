
/*********************************************************************
@author: Ivogomez03
github: https://www.github.com/Ivogomez03
Ejercicio 3
Dados los siguientes threads:
    P1 :            P2:             P3:
    Print(R);       Print(I);       Print(O);
    Print(OK);      Print(OK);      Print(OK);
                   

Utilizar semáforos para garantizar que el único resultado impreso será R I O OK OK OK (asumimos
que Print es atómico).

*********************************************************************/
package ejercicio3;
import java.util.concurrent.Semaphore;

class Ejercicio3{
    

    private Semaphore semaforo1 = new Semaphore(0);
    private Semaphore semaforo2 = new Semaphore(0);
    private Semaphore semaforo3 = new Semaphore(0);

    public static void main(String[] argumentos){

        Ejercicio3 ej3 = new Ejercicio3();

        new Thread (new Hilo1(ej3), "Hilo1").start();
        new Thread (new Hilo2(ej3), "Hilo2").start();
        new Thread (new Hilo3(ej3), "Hilo3").start();
    }

    public Semaphore obtenerSemaforo(int numeroDeSemaforo){
        if(numeroDeSemaforo == 1) return this.semaforo1;
        else if(numeroDeSemaforo == 2) return this.semaforo2;
        else if(numeroDeSemaforo == 3) return this.semaforo3;
        throw new IllegalArgumentException("Número de semáforo inválido: " + numeroDeSemaforo);
    }
}



/*
    Thread = hilo de ejecución dentro de un programa.

    La JVM permite múltiples hilos concurrentes.

    Cada hilo tiene:

        Prioridad → los de mayor prioridad se ejecutan antes.

        Puede ser daemon o no →

            Daemon: hilo en segundo plano (ej. recolector de basura).

            No daemon: hilo “principal” de la app.

    Cuando se crea un nuevo hilo:

        Su prioridad es la misma que la del hilo creador.

        Será daemon solo si el hilo creador es daemon.

    El main es el primer hilo no-daemon que arranca la JVM.

    Todos los hilos no-daemon terminan.

    Formas de crear un hilo:

    Extender la clase Thread y sobreescribir run().

    También existe implementar Runnable, (como hacemos en los ejercicios)

    https://learn.microsoft.com/en-us/dotnet/api/java.lang.thread?view=net-android-35.0
 */

 /*

 Un semáforo controla el acceso a un recurso compartido usando un contador de permisos (permits).

    Si el contador > 0 → un hilo puede entrar (se le da un permiso y se decrementa).

    Si el contador = 0 → el hilo queda bloqueado hasta que haya permisos disponibles.

Funcionamiento básico

    El hilo llama a acquireUninterruptibly() → intenta conseguir un permiso.

    Si hay permisos → entra, el contador baja.

    Si no hay permisos → queda esperando.

    Cuando termina → llama a release() y devuelve el permiso (contador sube).

    Otro hilo que estaba esperando puede entrar.

Clase en Java
Se usa java.util.concurrent.Semaphore.

Constructor

Semaphore(int num) → número inicial de permisos.


Como bloquear o liberar:

    Hilo llama acquireUninterruptibly() antes de usar el recurso (bloquea).

    Hilo llama release() cuando termina (desbloquea).

    Si num = 1, se comporta como un candado exclusivo (mutex).

    https://www.geeksforgeeks.org/java/semaphore-in-java/
  */