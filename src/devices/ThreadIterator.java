package devices;

import java.util.ListIterator;

public class ThreadIterator<E> implements ListIterator<E> {

    ThreadArray<E> array;
    int index;

    ThreadIterator(ThreadArray<E> a) {
        array = a;
        index = 0;
    }

    ThreadIterator(ThreadArray<E> a, int i) {
        this(a);
        index = i;
    }

    @Override
    public E next() {
        return array.get(index++);
    }

    public E getCurrent(){
        if(index-1<0)
            return null;
        return array.get(index-1);
    }
    
    @Override
    public void remove() {
        array.remove(index-1);
    }

    @Override
    public boolean hasNext() {
        if(array.isEmpty())
            return false;
        if(index<0)
            index = 0;
        return index < array.size();
    }

    @Override
    public boolean hasPrevious() {
        return index-1 > 0;
    }

    @Override
    public E previous() {
        return array.get(--index-1);
    }

    @Override
    public int nextIndex() {
        return index;
    }
    public int getIndex(){
        return index-1;
    }
    @Override
    public int previousIndex() {
        return index - 2;
    }

    @Override
    public void set(E e) {
        array.set(index, e);
    }

    @Override
    public void add(E e) {
        array.add(e);
    }

    public void dispose() {
        array.disposeIterator(this);
    }
}

