package CWAnhDuy;

public interface AbstractArrayList<E> {
    boolean add(E item);
    E get(int index);
    boolean remove(String isbn);
    int size();
    boolean contains(String isbn);
    boolean isEmpty();
}