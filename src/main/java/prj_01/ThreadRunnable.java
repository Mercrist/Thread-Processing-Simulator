// Yariel Mercado yariel.mercado1@uprm.edu
package prj_01;

/**
 * Implements the {@code Runnable} interface which allows overriding of the threads' {@code run()}
 * method. Takes the circular linked list as a parameter and allows each thread to run.
 * @author  Yariel Mercado
 */
public class ThreadRunnable implements Runnable {

    private RoundRobinCLL rr = null; //circular linked list

    /* Constructors */
    /** Default class constructor. Initializes a {@code ThreadRunnable} object
     * without a circular linked list.
     */
    public ThreadRunnable() {
    }

    /** Class constructor. Receives as a parameter the circular linked list which both the {@code Threads} and
     * main processes will iterate over.
     * @param rr Specifies the Round Robin circular linked list to iterate over.
     */
    public ThreadRunnable(RoundRobinCLL rr) {
        this.rr = rr;
    }

    /** Overrides the {@code Runnable} interface {@code run()} method. Allows each thread to run. Prints
     * the current thread being run and then exits if no circular linked list has been provided. If a
     * circular linked list has been provided in the class constructor, uses the {@code Threads}
     * to find a processed slot and change it to unprocessed, as long as the circular linked list is running.
     */
    @Override
    public void run() {
        System.out.println("Running Thread... This is Thread " + Thread.currentThread().getName());
        if (rr==null) {
            return;
        }
        while  (!rr.stopLoop) {
            // keep doing what this thread should do.
            rr.findEmptySlot();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " Finished ... Bye Bye");
    }
}
