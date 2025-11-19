package repasoParcial;

public class Demonio extends Thread {
    private Ascensor monitor;

    public Demonio(Ascensor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                monitor.moverse();
                Thread.sleep(monitor.getTimeOut());
            } catch (InterruptedException e) {
                System.out.println("Se vencio el temporizador, moviendo ascensor");
            }
        }
    }

}
