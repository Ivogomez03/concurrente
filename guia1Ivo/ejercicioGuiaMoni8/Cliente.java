package ejercicioGuiaMoni8;

public class Cliente implements Runnable {
    private Bar bar;

    public Cliente(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void run() {
        bar.agarrarPizzas();

    }

}
