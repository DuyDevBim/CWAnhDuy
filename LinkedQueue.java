package CWAnhDuy;



public class LinkedQueue implements AbstractLinkedQueues<Order> {
    public class Node {
        // Attributes
        Order order;
        Node next;

        // Constructor
        public Node (Order order) {
            this.order = order;
            this.next = null;
        }
    }

    // Attributes
    private Node head;
    private Node tail;
    private int size;

    // Constructor
    public LinkedQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void offer (Order order) {
        Node newNode = new Node(order);

        // if the queue is empty
        if (head == null && tail == null) {
            this.head = newNode;
            this.tail = newNode;
        } else { // if the queue is not empty
            this.tail.next = newNode;
            this.tail = newNode;
        }

        this.size++;
    }

    @Override
    public Order poll() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }

        Order removeOrder = head.order;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        this.size--;
        return removeOrder;
    }

    @Override
    public Order peek() {
        if (isEmpty()) {
            return  null;
        }

        return head.order;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public Node getHead() {
        return head;
    }

    public void displayAllOrders() {


        if (isEmpty()) {
            System.out.println("No order yet");
            return;
        }

        System.out.println("All Orders in Queue:");
        Node current = head;
        while (current != null) {
            current.order.displayOrderInformation();
            current = current.next;
        }
    }
}
