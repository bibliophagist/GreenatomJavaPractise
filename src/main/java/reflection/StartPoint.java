package reflection;

public class StartPoint {
    public static void main(String[] args) {
        Printer printer = (Printer) Test.printHelloWorld(new Printer());
        printer.printer();
    }
}
