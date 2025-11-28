import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Estacion implements Runnable {

    private int cantidadDeVehiculos;
    private Condition esperarEstatal;
    private Condition esperarParticular;
    private int cantidadEstatales;
    private int cantidadParticulares;
    private Administracion administracion;
    private Queue<Vehiculo> vehiculos;

    private Lock lock;

    public Estacion(int cantidadDeVehiculos, Administracion administracion) {
        this.cantidadDeVehiculos = cantidadDeVehiculos;

        this.lock = new ReentrantLock();
        this.esperarEstatal = lock.newCondition();
        this.esperarParticular = lock.newCondition();
        this.administracion = administracion;
    }

    public void incrementarCantidadDeVehiculos() {
        this.cantidadDeVehiculos++;
    }

    public int getCantidadDeVehiculos() {
        return this.cantidadDeVehiculos;
    }

    public void pushearVehiculo(Vehiculo v) {
        vehiculos.add(v);
        incrementarCantidadDeVehiculos();

    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                Vehiculo v = vehiculos.poll();
                if (v.getTipoDeVehiculo() == 0) {

                    while (cantidadEstatales > 0)
                        esperarEstatal.await();

                    cantidadEstatales++;

                    administracion.incrementarVerificacion();

                    System.out.println("Verificación realizada del vehiculo " + v.getId());

                    if (administracion.revisionyAprobacion()) {
                        administracion.incrementarAprobadas();
                        System.out.println("Vehiculo aprobado ");
                    } else {
                        System.out.println("Vehiculo no aprobado");
                    }

                    cantidadEstatales--;

                    if (cantidadEstatales > 0) {
                        esperarEstatal.signalAll();

                    } else {
                        esperarParticular.signalAll();

                    }

                } else { // si es particular
                    while (cantidadEstatales > 0 || cantidadParticulares > 0)
                        esperarParticular.await();
                    cantidadParticulares++;

                    administracion.incrementarVerificacion();

                    System.out.println("Verificación realizada del vehiculo " + v.getId());

                    if (administracion.revisionyAprobacion()) {
                        administracion.incrementarAprobadas();
                        System.out.println("Vehiculo aprobado ");
                    } else {
                        System.out.println("Vehiculo no aprobado");
                    }
                    cantidadParticulares--;

                    if (cantidadEstatales > 0) {
                        esperarEstatal.signalAll();

                    } else {
                        esperarParticular.signalAll();

                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
