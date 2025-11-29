package ejercicioGuiaMoni6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
6. El comite organizador de una conferencia posee una sala para exposicion de charlas de distintos temas.
Las personas que desean asistir a una charla entran en la sala y esperan hasta que la charla comienza.
Las charlas empiezan cuando el orador llega a la sala. Por respeto, los asistentes no se retiran hasta
que la charla termina ni entran cuando la charla ya ha comenzado. La sala posee una capacidad de 50
personas, sin contar al orador. De una solucion usando monitores que modele los siguientes escenarios:

a) Se dispone de una sala para dar repetidas veces la misma charla. El orador descansa 5 minutos
entre charla y charla. Si al momento de iniciar la charla el auditorio esta vacıo el orador descansa
otros 5 minutos esperando que lleguen oyentes. Cuando el auditorio se llena los asistentes deben
esperar a que comience la siguiente charla luego del descanso del orador.

b) Al igual que en el punto a) se dispone de una sala para dar tres charlas distintas, pero en este
caso los oradores esperan a que haya al menos 40 personas en el auditorio.
 */
public class Auditorio {
    private Lock lock;
    private Condition esperarSiguienteCharla;
    private Condition esperarQueTermineCharla;

    private final int capacidad = 50;
    private int cantidadDePersonasEnAuditorio;
    private boolean laCharlaYaHaComenzado = false;

    public Auditorio() {
        this.lock = new ReentrantLock();

        this.esperarQueTermineCharla = lock.newCondition();
        this.esperarSiguienteCharla = lock.newCondition();

        this.cantidadDePersonasEnAuditorio = 0;

    }

    public void entrarAlaCharla() {
        lock.lock();
        try {
            while (laCharlaYaHaComenzado || cantidadDePersonasEnAuditorio == capacidad) {
                esperarSiguienteCharla.await();
            }

            cantidadDePersonasEnAuditorio++;

            System.out.println("Oyente entro al auditorio, esperando a que empiece la charla");

            esperarQueTermineCharla.await();

            System.out.println("Oyente saliendo del auditorio");

            cantidadDePersonasEnAuditorio--;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void entrarAlAuditorioParaDarCharla() {
        lock.lock();
        try {
            while (cantidadDePersonasEnAuditorio == 0) {
                System.out.println("No hay oyentes, esperando a que lleguen");
                lock.unlock();
                Thread.sleep(3000);
                lock.lock();
            }

            laCharlaYaHaComenzado = true;

            System.out.println("La charla ya ha comenzado");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void terminarCharla() {
        lock.lock();
        try {
            System.out.println("La charla ya ha terminado, pueden irse");

            if (cantidadDePersonasEnAuditorio > 0) {
                esperarQueTermineCharla.signalAll();
                laCharlaYaHaComenzado = false;
                esperarSiguienteCharla.signalAll();
            }

        } finally {
            lock.unlock();
        }

    }

}
