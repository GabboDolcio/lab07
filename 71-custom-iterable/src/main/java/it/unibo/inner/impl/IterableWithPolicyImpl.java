package it.unibo.inner.impl; 

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private final List<T> elements;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] elements){
        this(
            elements,
            new Predicate<>() {
                @Override
                public boolean test(T elements){
                    return true;
                }
            }
        );
    }

    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> filter){
        this.elements = List.of(elements); 
        this.filter = filter;
    }

    @Override 
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new IterableWithPolicyIterator();
    }

    private class IterableWithPolicyIterator implements Iterator<T>{
        private int index = 0;

        @Override
        public boolean hasNext() {
            while (index < elements.size()) {
                final T elem = elements.get(index);
                if(filter.test(elem)){
                    return true;
                }
                index++;
            }
            return false;
        }

        @Override
        public T next() {
            if(hasNext()){
                return elements.get(index);
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}