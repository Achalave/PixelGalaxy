
package devices;

//Designed to allow iteration on multiple thready and maintain safe iteration

import java.util.*;

//dispide adding and removing done on seperate threads


public class ThreadArray<E> implements List<E>{
    
    private List<E> array;
    private List<ThreadIterator<E>> iterators;
    
    public ThreadArray(List<E> l){
        array = Collections.synchronizedList(l);
    }
    public ThreadArray(){
        array = Collections.synchronizedList(new ArrayList<E>());
        iterators = Collections.synchronizedList(new ArrayList<ThreadIterator<E>>());
    }
    
    
    @Override
    public E remove(int i) {
        for(int k=0; k<iterators.size();k++){
            ThreadIterator<E> it = iterators.get(k);
            if(it.index > i){
                it.index--;
            }
        }
        if(i>=0)
            return array.remove(i);
        return null;
    }
    
    @Override
    public boolean removeAll(Collection clctn) {
        boolean success = true;
        for(Object o:clctn){
            if(!remove(o))
                success = false;
        }
        return success;
    }

    @Override
    public boolean retainAll(Collection clctn) {
        return array.retainAll(clctn);
    }
    
    @Override
    public boolean remove(Object o) {
        for(int i=0; i<array.size(); i++){
            if(o == array.get(i)){
                remove(i);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public E get(int i){
        return array.get(i);
    }
    
    @Override
    public int size(){
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return array.contains(o);
    }

    @Override
    public Iterator iterator() {
        ThreadIterator i = new ThreadIterator(this);
        iterators.add(i);
        return i;
    }

    @Override
    public Object[] toArray() {
        return array.toArray();
    }

    @Override
    public Object[] toArray(Object[] ts) {
        return array.toArray(ts);
    }

    @Override
    public boolean containsAll(Collection clctn) {
        return array.containsAll(clctn);
    }

    @Override
    public boolean addAll(Collection clctn) {
        return array.addAll(clctn);
    }

    @Override
    public boolean addAll(int i, Collection clctn) {
        return array.addAll(i, clctn);
    }

    

    @Override
    public void clear() {
        array.clear();
    }

    @Override
    public E set(int i, E e) {
        return array.set(i, e);
    }

    @Override
    public void add(int i, E e) {
        array.add(i, e);
    }

    

    @Override
    public int indexOf(Object o) {
        return array.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return array.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        ThreadIterator i = new ThreadIterator(this);
        iterators.add(i);
        return i;
    }

    @Override
    public ListIterator listIterator(int i) {
        ThreadIterator it = new ThreadIterator(this,i);
        iterators.add(it);
        return it;
    }

    @Override
    public List subList(int i, int i1) {
        return array.subList(i, i1);
    }

    @Override
    public boolean add(E e) {
        return array.add(e);
    }

    public void disposeIterator(ThreadIterator<E> it){
        iterators.remove(it);
    }
    
    @Override
    public String toString(){
        return array.toString();
    }
}
