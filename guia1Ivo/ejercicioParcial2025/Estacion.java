import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Estacion {
    private int cantidadDeVehiculos;
    private Condition esperarEstatal;
    private Condition esperarParticular;
    private int cantidadEstatales;
    private int cantidadParticulares;
    private Administracion administracion;

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

    public void realizarVerificacion(Vehiculo vehiculo) throws InterruptedException {
        lock.lock();
        try {
            if (vehiculo.getTipoDeVehiculo() == 0) {
                while (cantidadEstatales > 0)
                    esperarEstatal.await();
                cantidadEstatales++;

                administracion.incrementarVerificacion();

                System.out.println("Verificación realizada del vehiculo " + vehiculo.getId());

                if (administracion.revisionyAprobacion()) {
                    administracion.incrementarAprobadas();
                    System.out.println("Vehiculo aprobado ");
                } else {
                    System.out.println("Vehiculo no aprobado");
                }

                if (cantidadEstatales > 0) {
                    esperarEstatal.signalAll();

                } else {
                    esperarParticular.signalAll();

                }
                cantidadEstatales--;

            } else { // si es particular
                while (cantidadEstatales > 0 || cantidadParticulares > 0)
                    esperarParticular.await();
                cantidadParticulares++;

                administracion.incrementarVerificacion();

                System.out.println("Verificación realizada del vehiculo " + vehiculo.getId());

                if (administracion.revisionyAprobacion()) {
                    administracion.incrementarAprobadas();
                    System.out.println("Vehiculo aprobado ");
                } else {
                    System.out.println("Vehiculo no aprobado");
                }

                if (cantidadEstatales > 0) {
                    esperarEstatal.signalAll();

                } else {
                    esperarParticular.signalAll();

                }
                cantidadParticulares--;

            }

        } finally {
            lock.unlock();
        }

    }

    public int getCantidadDeVehiculos() {
        return this.cantidadDeVehiculos;
    }
}
