package ejercicioGuiaMoni7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
7. Se desea implementar utilizando monitores la siguiente sala de apuestas. La sala de apuestas propone
un acertijo (basicamente una palabra que los apostadores deben descubrir). Los apostadores proponen
una palabra y un monto. Si aciertan ganan 10 veces su apuesta y el acertijo concluye. Si un participante
falla, no puede volver a apostar hasta tanto haya participado otro jugador. Al final, cada apostador
debe mostrar por pantalla si gano o no y el monto. El monitor Juego que modela a la sala de apuestas
debe proveer las siguientes operaciones.
concluido: responde si el acertijo finalizo o no.
apostar(palabra,apuesta) con la cual un jugador realiza su apuesta. Retorna verdadero si el jugador
gano su apuesta y falso en caso contrario.

Se solicita:

a) Dar la implementacion de un jugador (asumiendo que se pasa la sala de juegos como parametro al
momento de la construccion). El jugador no puede asumir que el monitor implementa operaciones
distintas de las mencionadas anteriormente.

b) Dar la implementacion del monitor Juego que garantice que ningun Jugador que se comporte
segun el codigo definido anteriormente pueda apostar dos veces consecutivas y que finaliza cuando
el acertijo concluye (aun si el que adivina es otro jugador).

*/
public class Juego {
    private Lock lock;

    private String acertijo;

    private Condition noPuedoVolverApostar;

    private boolean acertijoFinalizo = false;

    public Juego(String acertijo) {
        this.lock = new ReentrantLock();

        this.acertijo = acertijo;

        this.noPuedoVolverApostar = lock.newCondition();
    }

    public boolean concluido() {
        return acertijoFinalizo;
    }

    public boolean apostar(String palabra, int monto, int id) {
        lock.lock();
        try {

            if (acertijoFinalizo)
                return false;

            System.out.println("Participante " + id + " apostando, palabra elegida: " + palabra + ".");

            if (acertijo.equals(palabra)) {
                System.out.println("Participante " + id + " ha ganado, monto ganado: " + monto * 10);
                acertijoFinalizo = true;
                noPuedoVolverApostar.signalAll();
                return true;

            }
            noPuedoVolverApostar.signalAll();
            noPuedoVolverApostar.await();
            return false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;

    }

}
