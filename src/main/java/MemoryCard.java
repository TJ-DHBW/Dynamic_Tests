import java.util.Stack;

public class MemoryCard {
    Stack<Picture> store = new Stack<>();

    public void store(Picture picture){
        store.push(picture);
    }

    public Stack<Picture> getStore() {
        return store;
    }
}
