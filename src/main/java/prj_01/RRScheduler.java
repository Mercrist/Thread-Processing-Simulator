// Yariel Mercado yariel.mercado1@uprm.edu
package prj_01;

/**
 * Contains the simulator's {@code main} method. Runs the Round Robin {@code Threads} simulator
 * based on what phase of the project is selected and the simulator's other input values from the command line
 * arguments.
 * @author  Abdelrahman ElSaid
 * @author  Yariel Mercado
 */
public class RRScheduler {
    /**
     * <p>
     * The simulator's {@code main} method, which runs based on the indicated phase of the simulator by the user.
     * If phase '1' is selected, the simulator simply initializes the {@code Threads}, runs them once
     * and exits. Based on the user inputs, it sets the amount of threads and the termination limit:
     * the amount of iterations over the circular linked list. Else, the simulator assigns default values
     * to these variables.
     * </p>
     *
     * <p>
     * If phase '2' is selected, a {@code ThreadRunnable} object containing a
     * circular linked list is created with a default value of twelve nodes. Each thread in the
     * {@code Threads} array list then starts and the main process begins to change tasks from
     * processed to unprocessed. If phase '1' was selected, the circular linked list object is never initialized,
     * and thus never runs, exiting the main loop immediately after creating the threads.
     * </p>
     * @param args The command line arguments entered by the user.
     */
    public static void main(String[] args){
        int termination_limit = 100;
        int no_threads = 5;
        int project_step = 1;
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-t") || args[i].equals("--termination")) { //get user inputs from CLI
                termination_limit = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-p") || args[i].equals("--processes")) {
                no_threads = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-s") || args[i].equals("--prjstep")) {
                project_step = Integer.valueOf(args[++i]);
                if (project_step!=1 && project_step!=2) {
                    System.out.println("Project Step value is 1 or 2 (" + project_step + " given).");
                    System.exit(1);
                }
            }
        }

        System.out.println("Starting Program...");


        RoundRobinCLL roundRobine = null;
        if (project_step==2) {
            roundRobine =  new RoundRobinCLL(12, termination_limit); //only initializes a CLL on step 2
        }

        ThreadRunnable rrRunnable = new ThreadRunnable(roundRobine);
        Threads threads = new Threads(no_threads, rrRunnable); //initialize threads

        for (int i=0; i<threads.threads.size(); i++) {
            threads.threads.get(i).start();
        }

        if (roundRobine!=null) roundRobine.findFilledSlot() ; //main process running over CLL

        System.out.println("Main Finished ... Bye Bye");

        if (roundRobine!=null) roundRobine.stopLoop = true;

    }
}
