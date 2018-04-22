package Animals;

public class Kittens extends Cat {
    public Kittens(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void produceSound() {
        System.out.println("Miau");
    }
}
