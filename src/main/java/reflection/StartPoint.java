package reflection;

public class StartPoint {
    public static void main(String[] args) {
        Printer printer = (Printer) PrintWithAnnotation.printHelloWorld(new Printer());
        printer.printer();
    }
}
