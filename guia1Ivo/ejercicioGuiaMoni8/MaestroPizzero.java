package ejercicioGuiaMoni8;

public class MaestroPizzero implements Runnable {

    private Bar bar;

    public MaestroPizzero(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void run() {
        while (true) {
            bar.servirPizzas();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
