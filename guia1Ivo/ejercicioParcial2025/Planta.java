import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Planta {
    private Lock lock;

    public int cantAprobadas;
    public int cantVerif;

    private Condition esperarAsignacionEstacion;
    private Condition esperarParticular;
    private Condition esperarEstatal;

    private Condition[] esperarEstatalesEnEstacion = new Condition[3];
    private Condition[] esperarParticularesEnEstacion = new Condition[3];
    private Condition[] esperarVerificacionEst = new Condition[3];

    private Condition noHayVehiculosEnAdmin;
    private Condition[] noHayVehiculosEstacion = new Condition[3];

    private boolean hayVehiculoQueEsperaAsignacion = false;

    private int cantidadDeVehiculosAdminEstatales;
    private int cantidadDeVehiculosAdminPart;

    private int[] cantidadDeVehiculosEstatalesEnEstacion = new int[3];
    private int[] cantidadDeVehiculosParticularesEnEstacion = new int[3];

    private Vehiculo vehiculo;

    private boolean[] hayVehiculoQueEsperaVerificacionEstacion = { false, false, false };

    public Planta() {
        this.lock = new ReentrantLock();

        this.cantAprobadas = 0;
        this.cantVerif = 0;

        this.esperarAsignacionEstacion = lock.newCondition();
        this.esperarParticular = lock.newCondition();
        this.esperarEstatal = lock.newCondition();

        for (int i = 0; i < 3; i++)
            this.esperarEstatalesEnEstacion[i] = lock.newCondition();

        for (int i = 0; i < 3; i++)
            this.esperarParticularesEnEstacion[i] = lock.newCondition();

        for (int i = 0; i < 3; i++)
            this.esperarVerificacionEst[i] = lock.newCondition();

        this.noHayVehiculosEnAdmin = lock.newCondition();

        for (int i = 0; i < 3; i++)
            this.noHayVehiculosEstacion[i] = lock.newCondition();

        this.cantidadDeVehiculosAdminEstatales = 0;
        this.cantidadDeVehiculosAdminPart = 0;

        for (int i = 0; i < 3; i++)
            this.cantidadDeVehiculosEstatalesEnEstacion[i] = 0;

        for (int i = 0; i < 3; i++)
            this.cantidadDeVehiculosParticularesEnEstacion[i] = 0;

    }

    public void irAdministracion(Vehiculo v) {
        lock.lock();
        try {
            if (v.getTipoDeVehiculo() == 0) {
                cantidadDeVehiculosAdminEstatales++;

                if (cantidadDeVehiculosAdminEstatales > 1)
                    esperarEstatal.await();

                vehiculo = v;

                hayVehiculoQueEsperaAsignacion = true;

                noHayVehiculosEnAdmin.signal();

                esperarAsignacionEstacion.await();

                cantidadDeVehiculosAdminEstatales--;

                if (cantidadDeVehiculosAdminEstatales > 0)
                    esperarEstatal.signal();
                else {
                    esperarParticular.signal();
                }

            } else {
                cantidadDeVehiculosAdminPart++;

                if (cantidadDeVehiculosAdminPart > 1 || cantidadDeVehiculosAdminEstatales > 0)
                    esperarParticular.await();

                vehiculo = v;

                hayVehiculoQueEsperaAsignacion = true;

                noHayVehiculosEnAdmin.signal();

                esperarAsignacionEstacion.await(); // libera lock

                cantidadDeVehiculosAdminPart--;

                if (cantidadDeVehiculosAdminEstatales > 0)
                    esperarEstatal.signal();
                else {
                    esperarParticular.signal();
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int devolverEstacionConMenorCant() {

        if (cantidadDeVehiculosEstatalesEnEstacion[0]
                + cantidadDeVehiculosParticularesEnEstacion[0] > cantidadDeVehiculosEstatalesEnEstacion[1]
                        + cantidadDeVehiculosParticularesEnEstacion[1]) {
            if (cantidadDeVehiculosEstatalesEnEstacion[2]
                    + cantidadDeVehiculosParticularesEnEstacion[2] > cantidadDeVehiculosEstatalesEnEstacion[1]
                            + cantidadDeVehiculosParticularesEnEstacion[1])
                return 1;
        } else {
            if (cantidadDeVehiculosEstatalesEnEstacion[0]
                    + cantidadDeVehiculosParticularesEnEstacion[0] > cantidadDeVehiculosEstatalesEnEstacion[2]
                            + cantidadDeVehiculosParticularesEnEstacion[2])
                return 2;
        }

        return 0;
    }

    public void asignarEstacion() {
        lock.lock();
        try {
            while (!hayVehiculoQueEsperaAsignacion)
                noHayVehiculosEnAdmin.await();
            // completar metodo de asignacion

            int e = devolverEstacionConMenorCant();

            vehiculo.asignarEstacion(e);
            System.out.println("Estacion asignada");

            hayVehiculoQueEsperaAsignacion = false;

            esperarAsignacionEstacion.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void realizarVerificacion(int numeroDeEstacion) {
        lock.lock();
        try {
            while (!hayVehiculoQueEsperaVerificacionEstacion[numeroDeEstacion])
                noHayVehiculosEstacion[numeroDeEstacion].await();

            System.out.println("Tomando datos del vehiculo");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void terminarVerificacion(boolean aprobada, int numeroDeEstacion) {

        lock.lock();
        try {
            cantVerif++;
            if (aprobada)
                cantAprobadas++;

            hayVehiculoQueEsperaVerificacionEstacion[numeroDeEstacion] = false;

            esperarVerificacionEst[numeroDeEstacion].signal();

        } finally {
            lock.unlock();
        }

    }

    public void irEstacion(Vehiculo v, int numeroDeEstacion) {
        lock.lock();
        try {
            if (v.getTipoDeVehiculo() == 0) {

                cantidadDeVehiculosEstatalesEnEstacion[numeroDeEstacion]++;

                if (cantidadDeVehiculosEstatalesEnEstacion[numeroDeEstacion] > 1)
                    esperarEstatalesEnEstacion[numeroDeEstacion].await();

                System.out.println("Me van a empezar a verificar");

                noHayVehiculosEstacion[numeroDeEstacion].signal();

                hayVehiculoQueEsperaVerificacionEstacion[numeroDeEstacion] = true;

                esperarVerificacionEst[numeroDeEstacion].await();

                cantidadDeVehiculosEstatalesEnEstacion[numeroDeEstacion]--;

                if (cantidadDeVehiculosEstatalesEnEstacion[numeroDeEstacion] > 0)
                    esperarEstatalesEnEstacion[numeroDeEstacion].signal();
                else {
                    esperarParticularesEnEstacion[numeroDeEstacion].signal();
                }

            } else {

                cantidadDeVehiculosParticularesEnEstacion[numeroDeEstacion]++;

                if (cantidadDeVehiculosEstatalesEnEstacion[numeroDeEstacion] > 0
                        || cantidadDeVehiculosParticularesEnEstacion[numeroDeEstacion] > 1)
                    esperarParticularesEnEstacion[numeroDeEstacion].await();

                System.out.println("Me van a empezar a verificar");

                noHayVehiculosEstacion[numeroDeEstacion].signal();

                hayVehiculoQueEsperaVerificacionEstacion[numeroDeEstacion] = true;

                esperarVerificacionEst[numeroDeEstacion].await();

                cantidadDeVehiculosParticularesEnEstacion[numeroDeEstacion]--;

                if (cantidadDeVehiculosEstatalesEnEstacion[numeroDeEstacion] > 0)
                    esperarEstatalesEnEstacion[numeroDeEstacion].signal();
                else {
                    esperarParticularesEnEstacion[numeroDeEstacion].signal();
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
