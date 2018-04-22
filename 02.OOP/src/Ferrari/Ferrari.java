package Ferrari;

public class Ferrari implements Car {
    private static final String model = "488-Spider";
    private String driver;

    public Ferrari(String driver) {
        this.driver = driver;
    }

    public static String getModel() {
        return model;
    }

    public String getDriver() {
        return driver;
    }
}
