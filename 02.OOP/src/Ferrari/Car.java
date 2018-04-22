package Ferrari;

public interface Car {
    default void PushBrakes() {
        System.out.print("Brakes!");
    }
    default void PushGas() {
        System.out.print("Zadu6avam sA!");
    }
}
