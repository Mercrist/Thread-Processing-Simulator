// Yariel Mercado 
package prj_01;
import java.util.*;

/**
 * Initializes the {@code Threads} in an array list
 * based on the function parameters.
 * @author  Yariel Mercado
 */
public class Threads {
    public ArrayList<Thread> threads = new ArrayList<Thread>(); //Initializes an Array List which contains each thread created.

    /** Class constructor. Initializes the number of threads based on the parameter given.
     * Adds each thread in the array list along with the thread number. Initializes each thread
     * with a {@code ThreadRunnable} object, without a circular linked list, which allows each thread to run.
     * @param noThreads Specifies the number of threads to create.
     */
    public Threads(int noThreads){
       for (int i=0; i<noThreads; i++){
           ThreadRunnable runnable = new ThreadRunnable();
           System.out.println("Creating Thread " + (i+1));
           threads.add(new Thread(runnable, ""+i));
       }
    }

    /** Class constructor. Initializes the number of threads based on the parameters given.
     * Adds each thread in the array list along with the thread number and the specified
     * {@code ThreadRunnable} object.
     * @param noThreads Specifies the number of threads to create.
     * @param runnable Represents a {@code ThreadRunnable} object containing the circular linked list and
     *                 the threads' {@code run()} method.
     */
    public Threads(int noThreads, ThreadRunnable runnable){
        for (int i=0; i<noThreads; i++){
            System.out.println("Creating Thread " + (i+1));
            threads.add(new Thread(runnable, ""+i));
        }
    }
}
