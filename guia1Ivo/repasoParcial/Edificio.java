package repasoParcial;
/*7. En un edificio de dos pisos, hay un ascensor kosher que puede transportar hasta N personas.
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
a uno con prioridad que llega luego.*/

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Edificio {
    private Lock lock;
    private int capacidad;
    private int pisoAscensor;
    private int personasEnAscensor;
    private boolean[] ascensorEnPiso; // 0 para PB, 1 para PP
    private Condition estaLleno;
    private Condition puedoSubir;
    private Condition esperandoAqueBajen;
    private Condition esperarTraslado;
    private boolean timeout;

    public Edificio(int N) {
        this.capacidad = N;
        this.personasEnAscensor = 0;
        this.pisoAscensor = 0;
        this.lock = new ReentrantLock();
        this.esperarTraslado = lock.newCondition();
        this.estaLleno = lock.newCondition();
        this.puedoSubir = lock.newCondition();
        this.esperandoAqueBajen = lock.newCondition();
        this.timeout = true;
        this.ascensorEnPiso = new boolean[2];
        this.ascensorEnPiso[pisoAscensor] = true;
        this.ascensorEnPiso[1 - pisoAscensor] = false;

    }

    public void subir(int pisoPersona, int id) {
        lock.lock();
        try {
            while (!ascensorEnPiso[pisoPersona] || personasEnAscensor == capacidad) {
                puedoSubir.await();
            }
            personasEnAscensor++;
            System.out.println("Persona " + id + " subio al ascensor en piso " + pisoPersona);

            if (personasEnAscensor == capacidad) {
                estaLleno.signal();
            }

            esperarTraslado.await();

            System.out.println("Persona " + id + " bajando del ascensor en piso " + (1 - pisoPersona));
            personasEnAscensor--;

            if (personasEnAscensor == 0) {
                esperandoAqueBajen.signal();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void subirPersonas() {
        lock.lock();
        try {
            System.out.println("Esperando a que suban personas en piso: " + pisoAscensor);

            while (personasEnAscensor < capacidad || !timeout) {
                estaLleno.await();
            }

            System.out.println("Ascensor en piso " + pisoAscensor + " cambiando a " + (1 - pisoAscensor));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void moverse() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void bajarPersonas() {
        lock.lock();
        try {
            ascensorEnPiso[pisoAscensor] = false;
            pisoAscensor = 1 - pisoAscensor;

            System.out.println("Ascensor esperando a que bajen personas en piso " + pisoAscensor);

            esperarTraslado.signalAll();

            esperandoAqueBajen.await();

            System.out.println("Todas las personas estan abajo, ascensor habilita a que suban");

            ascensorEnPiso[pisoAscensor] = true;

            puedoSubir.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}