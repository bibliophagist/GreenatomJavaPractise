package fisrt.tasks.serialization;

public enum Occupation {
    AT_HOME(0), KINDERGARTEN(3), SCHOOL(7), INSTITUTE(18), WORK(23), RETIREE(65);

    private final int age;

    private Occupation(int age) {
        this.age = age;
    }

    public static Occupation getOccupation(int age) {
        Occupation occupation = AT_HOME;
        for (Occupation o : values()) {
            if (o.age <= age) {
                occupation = o;
            }
        }
        return occupation;
    }
}
