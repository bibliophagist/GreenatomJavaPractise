package concurrency;

public class Main {

    static Concurrency concurrency;

    public static void main(String[] args) {

        concurrency = new Concurrency();
        concurrency.start();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Работает основная программа");
        }
    }
}
