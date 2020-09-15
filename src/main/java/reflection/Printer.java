package reflection;

public class Printer {

    @PrintHelloWorld
    public void printer() {
        System.out.println("I am a printer class");
    }

    @PrintHelloWorld
    public void printer1() {
        System.out.println("I am a not printer class");
    }

    public void printer2() {
        System.out.println("I am a definitely not a printer class");
    }

}
