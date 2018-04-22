package BorderControl;

public class Robot implements Identifiable {
    private String model;
    private String id;

    public Robot(String model, String id) {
        this.model = model;
        this.id = id;
    }

    @Override
    public boolean CheckIfFake(String fake) {
        if(id.substring(id.length() - fake.length()).equals(fake))
            return true;
        return false;
    }

    @Override
    public String getId() {
        return id;
    }
}
