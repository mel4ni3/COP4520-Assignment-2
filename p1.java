import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class p1 extends Thread {

    // a lock for the current visitor in the labyrinth
    static ReentrantLock visitor = new ReentrantLock();

    // the number of guests visting the party
    static AtomicInteger numguests = new AtomicInteger(100);

    // the number of the current guest visiting the labyrinth
    AtomicInteger curguest = new AtomicInteger(0);

    // this is true if there is a cupcake at the labyrinth's exit
    static AtomicBoolean cupcake = new AtomicBoolean(true);

    // this is true if the current guest has eaten the cupcake
    AtomicBoolean eaten = new AtomicBoolean(false);

    // the number of guests that have already visited the labyrinth
    static AtomicInteger visitedguests = new AtomicInteger(1);

    // constructor
    p1(int curguest) {
        this.curguest = new AtomicInteger(curguest);
    }

    // the current guest navigates the labyrinth
    // whenever the guest's thread is run
    // all threads try to go at the same time but only eat
    // if they haven't already
    public void run() {

        // this only goes if the total number of guests
        // is greater than how many unique guests
        // visited the labyrinth
        while (visitedguests.get() < numguests.get()) {
            // we can only eat at the labyrinth
            // if we haven't eaten before, to represent
            // unique guests visiting

            // the thread that enters first locks and then sees
            // if they ate already
            visitor.lock();

            // thread 0 is used as the counter of visited guests and replaces the cupcake
            // if there's no cupcake, that means a new guest ate a cupcake
            // and the minotaur's servant brings out another cupcake
            if (this.curguest.get() == 0 && cupcake.get() == false) {
                visitedguests.getAndIncrement();
                cupcake.set(true);

            } else if (this.eaten.get() == false && cupcake.get() == true && this.curguest.get() != 0) {

                // if we're not on thread 0, then this thread represents a guest
                // this guest will eat a cupcake if they haven't already eaten and there
                // is a cupcake available
                this.eaten.set(true);
                cupcake.set(false);
                // System.out.println("Guest " + this.curguest + " ate a cupcake.");

            }

            else {

                // the guest at this thread ate already and is not a unique guest
                // System.out.println("Guest " + this.curguest + " did not eat.");
            }

            // now other guests can visit the labyrinth
            visitor.unlock();

        }

    }

    public static void main(String[] args) {

        // used to find out how long the simulation took
        long start = 0;
        long end = 0;
        long duration = 0;

        // an array of threads for each guest
        p1[] visitorThreads = new p1[numguests.get()];

        // start the simulation
        start = System.currentTimeMillis();

        // create and start each thread
        for (int i = 0; i < numguests.get(); ++i) {

            visitorThreads[i] = new p1(i);
            visitorThreads[i].start();

        }

        // keep calling guests to the labyrinth
        // until all the guests have gone inside
        while (visitedguests.get() < numguests.get()) {

            // if the amount of unique guests that visited
            // is the total amount of guests
            if (visitedguests.get() == numguests.get()) {

                for (int i = 0; i < numguests.get(); ++i) {
                    try {

                        // stop the threads
                        visitorThreads[i].join();
                    }

                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        }

        end = System.currentTimeMillis();
        duration = end - start;

        // print output
        System.out.println(visitedguests + " guests visited the labyrinth.");
        System.out.println("This program took " + duration + " ms.");

    }

}