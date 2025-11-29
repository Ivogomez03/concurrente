package ejercicioGuiaMoni5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
5. Se desea resolver utilizando monitores el problema de la peluquerıa en la cual los clientes y los peluqueros
son modelados como threads independientes que se sincronizan utilizando un monitor, denominado pelu,
que tiene la siguientes operaciones:
    cortarseElPelo
    empezarCorte
    terminarCorte
Los threads peluqueros se comportan de la siguiente manera
    while(true){
    pelu.empezarCorte()
    //cortar
    pelu.terminarCorte()
    }
mientras que los clientes invocan a la operacion cortarseElPelo. El funcionamiento es el esperado
para una peluquerıa. Los clientes llegan y esperan hasta ser atendidos. Solo se van de la peluquerıa
cuando finalizan de cortarles el pelo. Se solicita:
a) Dar una implementacion a este problema utilizando monitores (con la polıtica signal y continua
y utilizando diferentes variables de condicion).
b) Como se modifica su solucion si puede utilizar una unica variable de condicion
*/

public class Peluqueria {
    private Lock lock;

    private Condition[] esperarSerAtendido = new Condition[4]; // para cada peluquero
    private Condition[] esperarCorte = new Condition[4];

    private Condition[] noHayClientes = new Condition[4];
    private int[] cantidadDeClientes = new int[4];

    private boolean[] clienteEnAtencion = { false, false, false, false };

    public Peluqueria() {
        this.lock = new ReentrantLock();
        for (int i = 0; i < 4; i++) {
            this.esperarSerAtendido[i] = lock.newCondition();
        }
        for (int i = 0; i < 4; i++) {
            this.esperarCorte[i] = lock.newCondition();
        }
        for (int i = 0; i < 4; i++) {
            this.cantidadDeClientes[i] = 0;
        }
        for (int i = 0; i < 4; i++) {
            this.noHayClientes[i] = lock.newCondition();
        }

    }

    public void empezarCorte(int peluqueroAsignado) {
        lock.lock();
        try {

            while (!clienteEnAtencion[peluqueroAsignado]) {
                noHayClientes[peluqueroAsignado].await();
            }

            System.out.println("Empezando corte del cliente");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void cortarseElPelo(int peluqueroAsignado) {
        lock.lock();
        try {
            cantidadDeClientes[peluqueroAsignado]++;
            if (cantidadDeClientes[peluqueroAsignado] > 1 || clienteEnAtencion[peluqueroAsignado]) { // miro si hay
                                                                                                     // alguien
                                                                                                     // esperando antes
                // de mi, o si hay alguien atendido
                esperarSerAtendido[peluqueroAsignado].await();
            }
            clienteEnAtencion[peluqueroAsignado] = true;

            noHayClientes[peluqueroAsignado].signal();

            cantidadDeClientes[peluqueroAsignado]--;

            System.out.println("El peluquero " + peluqueroAsignado + " me empieza a cortar el pelo, a esperar");

            esperarCorte[peluqueroAsignado].await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void terminarCorte(int peluqueroAsignado) {
        lock.lock();
        try {
            System.out.println("Peluquero " + peluqueroAsignado + " terminando corte del cliente atendido");

            esperarCorte[peluqueroAsignado].signal();

            clienteEnAtencion[peluqueroAsignado] = false;

            if (cantidadDeClientes[peluqueroAsignado] > 0) {
                System.out.println("Peluquero " + peluqueroAsignado + " dice: que pase el siguiente.");
                esperarSerAtendido[peluqueroAsignado].signal();
            } else {
                System.out.println("No hay clientes, peluquero " + peluqueroAsignado + " libre.");
                noHayClientes[peluqueroAsignado].await();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
