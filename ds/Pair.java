package ds;

public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair() {
        this.first = null;
        this.second = null;
    }

    public Pair(T1 element1, T2 element2) {
        this.first = element1;
        this.second = element2;
    }

    public String toString() {
        return "<" + this.first + ", " + this.second + ">";
    }

    // Getters
    public T1 getFirst() {
        return this.first;
    }

    public T2 getSecond() {
        return this.second;
    }

    // Setters.
    public void setFirst(T1 element) {
        this.first = element;
    }

    public void setSecond(T2 element) {
        this.second = element;
    }
}