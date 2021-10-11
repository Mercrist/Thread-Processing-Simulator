// Yariel Mercado yariel.mercado1@uprm.edu
package prj_01;

/* Gives each Thread a random instance. Safeguards because when Threads run in parallel
 * a random seed isn't shared among the Threads.*/
import java.util.concurrent.ThreadLocalRandom;

/**
 * Initializes a single {@code Node} to build a circular, singly linked list. Has a single link {@code next} which
 * stores a reference to the next {@code Node} in the circular linked list.
 * @author  Abdelrahman ElSaid
 * @author  Yariel Mercado
 */
class Node {
    public int id;
    public Node next;
    public Boolean proccessed_flag;

    /** Class constructor. Initializes a {@code Node} with the Node's ID. All Nodes are initially
     * marked as processed.
     * @param id Assigns a Node to its identifier.
     */
    public Node (int id) {
        this.id = id;
        proccessed_flag = true;
    }
}

/**
 * The Round Robin circular linked list interface. Contains the abstract methods to find an empty and filled
 * slot which are run by the threads and the main method respectively.
 * @author  Abdelrahman ElSaid
 * @author  Yariel Mercado
 */
interface RoundRobinCLLInterface {
    abstract void findEmptySlot();
    abstract void findFilledSlot();
}

/**
 * The Round Robin circular linked list which implements {@code RoundRobinCLLInterface}. Builds the Round Robin
 * circular linked list and defines the abstract methods which iterate over the list. Stores a reference to both
 * {@code head} and {@code tail}.
 * @author  Abdelrahman ElSaid
 * @author  Yariel Mercado
 */
public class RoundRobinCLL implements RoundRobinCLLInterface {
    private int num_nodes = 5;
    public Node head = null;
    public Node tail = null;
    public Boolean stopLoop = false;
    private int termination_limit;

    /**
     * Sleeps the current thread for a random amount of time to prevent race conditions. Allows for each thread
     * to run in parallel.
     */
    private void holdon() {
        try {
            Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(500, 3000));
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    /**
     * Overrides the {@code toString()} method. Gets the current thread and iterates over the circular linked list
     * printing whether each {@code Node} has been processed or not.
     * @return A string containing information about the current thread iterating over the slots and each
     * Node's processed status.
     */
    @Override
    public String toString() {
        String s = new String("" + Thread.currentThread().getName() + " ");
        Node node = head;
        s += "(Node-1: " + node.proccessed_flag + ")";
        s += " ==> ";

        for (int i = 1; i < num_nodes; i++) {
            node = node.next;
            s += "(Node-" + (i + 1) + ": " + node.proccessed_flag + ")";
            if (i < num_nodes - 1)
                s += " ==> ";
        }
        return s;
    }

    /**
     * This synchronized method manages the {@code Threads} to avoid race conditions, i.e. two or more threads
     * competing over the same slots. Allows only one thread to execute at a time at the given {@code Node}.
     * Additionally, sets that Node's processed status.
     * @param node The {@code Node} the current thread is accessing.
     * @param set_slot Sets the current {@code Node} being accessed to either processed or not processed.
     */
    private synchronized void holdRR(Node node, Boolean set_slot) {
        System.out.println("Thread " + Thread.currentThread().getName() + " Holding Resources");
        node.proccessed_flag = set_slot;
        System.out.println("Thread " + Thread.currentThread().getName() + " Releasing Resources");
        if (set_slot) holdon();
    }

    /**
     * Sleeps the current thread to avoid race conditions. Iterates over the circular linked list with {@code Threads}
     * until an unprocessed {@code Node} is found. Marks the {@code Node} from unprocessed to processed
     * and halts the loop.
     */
    public void findEmptySlot() {
        holdon();
        Node node = head;
        while (true) {
            if (!node.proccessed_flag) {
                holdRR(node, true); //changes to not processed
                return;
            }
            node = node.next;
        }
    }

    /**
     * Main process utilizes this method to loop over the circular linked list until the simulator hits the termination
     * limit. Looks for processed slots and marks them to unprocessed. Upon hitting the termination limit, the main
     * process exits and the program ends. A loop is completed once the tail of the circular linked list is accessed.
     */
    public void findFilledSlot() {
        int count = 0;
        Node node = head;
        while (!stopLoop) {
            if (node.proccessed_flag)
                holdRR(node, false); //change to not processed

            node = node.next;
            if (node == head) //one loop of the CLL
                count++;

            if (count > termination_limit) break;
            System.out.println("Main Move No. if (node == tail) //one loop of the CLL
                count++; if (node == tail) //one loop of the CLL
                count++;: " + count % num_nodes + "\t" + toString());
        }
    }


    /**
     * Creates the circular, singly linked list. Assigns each {@code Node} an incremental ID and utilizes a
     * single pointer to store a reference to the next {@code Node}. Completes the circular linked list by connecting
     * the {@code tail} of the list to the {@code head}.
     */
    private void fillRoundRubin () {
        int id = 1;
        head = new Node(0);
        Node prev = head;
        while(id < num_nodes){
            Node created = new Node(id);
            prev.next = created;
            prev = created;
            id++;
        }
        tail = prev;
        tail.next = head;
    }

    /** Class constructor. Initializes the circular linked list based on the number of nodes and the simulator's
     * termination limit.
     * @param num_nodes The number of nodes to add to the Round Robin circular linked list.
     * @param termination_limit The amount of times each process will iterate over the circular linked list.
     */
    public RoundRobinCLL(int num_nodes, int termination_limit) {
        this.num_nodes = num_nodes;
        this.termination_limit = termination_limit;
        fillRoundRubin();
    }

    /** Class constructor. Initializes the circular linked list based on the number of nodes the circular
     * linked list will contain.
     * @param num_nodes The number of nodes to add to the Round Robin circular linked list.
     */
    public RoundRobinCLL(int num_nodes) {
        this.num_nodes = num_nodes;
        fillRoundRubin();
    }

    /** Default class constructor. Initializes the circular linked list. Since the amount of nodes aren't specified,
     * the circular linked list will initialize with a default value of five nodes.
     */
    public RoundRobinCLL() {
        fillRoundRubin();
    }

}
