package Queues;

public interface Queues<E> {
	int size();
	boolean isEmpty();
	void enqueue(E e);
	E first();
	E dequeue();
}
