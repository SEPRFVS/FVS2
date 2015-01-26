package Util;

public class Tuple<T,U> {
    private T val1;
    private U val2;

    public Tuple(T val1, U val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public T getFirst() {
        return val1;
    }

    public U getSecond() {
        return val2;
    }
}
