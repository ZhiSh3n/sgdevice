public class Holder <T> {
    private T val;
    public Holder() {
    }
    public Holder(T v) {
        this.val = v;
    }
    public T getVal() {
        return val;
    }
    public void setVal(T val) {
        this.val = val;
    }

}
