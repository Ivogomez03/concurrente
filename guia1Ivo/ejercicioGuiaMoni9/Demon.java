package ejercicioGuiaMoni9;

public class Demon extends Thread {
    private Viaje viaje;

    public Demon(Viaje viaje) {
        this.viaje = viaje;
    }

    @Override
    public void run() {
        while (true) {
            try {
                viaje.cruzar();
            } catch (InterruptedException e) {
                System.out.println("Se vencio el temporizador, moviendo bote");
            }
        }
    }
}
