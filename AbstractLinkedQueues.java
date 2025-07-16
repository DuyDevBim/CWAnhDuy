package CWAnhDuy;

public interface AbstractLinkedQueues<E> {
    void offer(E order);
    E poll();
    E peek();
    int size();
    boolean isEmpty();
}
