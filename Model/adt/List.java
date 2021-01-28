package Model.adt;

import java.util.LinkedList;
import java.util.Queue;

public class List<T> implements IList<T> {
    private final Queue<T> list;

    public List() { this.list = new LinkedList<>();}

    @Override
    public void add(T v) { list.add(v); }

    @Override
    public T pop() { return list.poll(); }

}
