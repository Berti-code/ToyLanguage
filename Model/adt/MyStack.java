package Model.adt;

import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack;

    public MyStack() { this.stack = new Stack<>(); }

    @Override
    public T pop() { return stack.pop(); }

    @Override
    public void push(T newElem) { stack.push(newElem); }

    @Override
    public boolean isEmpty() { return stack.isEmpty(); }

    public Stack<T> getStack() { return this.stack; }

    @Override
    public String toString()
    {
        String allElems = "";
        for(T element : stack)
        {
            allElems += element.toString();
        }
        return allElems;
    }
}
