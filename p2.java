import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.random.*;

class visitors extends Thread {

    // the number of the current guest viewing the vase
    AtomicInteger curguest = new AtomicInteger(0);

    // this is true if the current guest has seen the vase
    AtomicBoolean seen = new AtomicBoolean(false);

    // the number of guests that have already viewed the vase
    AtomicInteger visitedguests = new AtomicInteger();

    // this is true if there is a guest viewing the vase
    AtomicBoolean busy = new AtomicBoolean();

    // the number of guests viewing the vase
    AtomicInteger numguests = new AtomicInteger();

    // a lock for the current visitor viewing the vase
    ReentrantLock visitor = new ReentrantLock();

    // a chance for if the visitor wants to see the vase again
    int chance = 1;

    p2 main = new p2(busy.get(), visitedguests.get(), numguests.get());

    // constructor
    visitors(int curguest, p2 main) {
        this.curguest = new AtomicInteger(curguest);
        this.busy = main.busy;
        this.visitedguests = main.visitedguests;
        this.numguests = main.numguests;
        this.visitor = main.visitor;
        this.main = main;
    }

    // strategy: all guests try to go to the room to see the vase
    // if the room is available, a guest changes the room's availability
    // guests keep trying to go in until all guests have seen the vase
    public void run() {

        while (main.visitedguests.get() < numguests.get()) {

            if (busy.get() == false && main.visitedguests.get() < numguests.get()) {

                this.viewVaseCheck();
            }

        }
    }

    public void viewVaseCheck() {
        if (main.busy.get() == false && main.visitedguests.get() < numguests.get() && this.chance == 1)
            this.viewVase();
    }

    public void viewVase() {

        if (main.visitedguests.get() < numguests.get()) {

            // only one guest can view the vase at a time
            main.visitor.lock();

            // the guest stays in the room and views the vase
            main.busy.set(true);

            // System.out.println("Guest number: " + this.curguest + " views the vase. " +
            // visitedguests);

            try {

                Thread.sleep(50);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            // if this guest hasn't seen the vase before,
            // update the count of unique guests who have
            // seen the vase
            if (this.seen.get() == false) {

                this.seen.set(true);
                visitedguests.getAndIncrement();
                main.visitedguests = visitedguests;

            }

            // the chance they visit again changes
            Random rand = new Random();
            this.chance = rand.nextInt(2);

            // the guest makes the room available again
            main.busy.set(false);

            // now other guests can view the vase
            main.visitor.unlock();

        }
    }

}

public class p2 {

    // a lock for the current visitor viewing the vase
    ReentrantLock visitor = new ReentrantLock();

    // this is true if there is a guest viewing the crystal vase
    AtomicBoolean busy = new AtomicBoolean();

    // the total number of guests that can view the vase
    AtomicInteger numguests = new AtomicInteger();

    // the number of unique guests that viewed the vase
    AtomicInteger visitedguests = new AtomicInteger();

    // an array of threads for each guest
    visitors[] visitorThreads;

    // constructor
    p2(boolean busy, int numguests, int visitedguests) {
        this.busy = new AtomicBoolean(busy);
        this.numguests = new AtomicInteger(numguests);
        this.visitedguests = new AtomicInteger(visitedguests);
    }

    // driver function
    public static void main(String[] args) {

        long start = 0;
        long end = 0;
        long duration = 0;

        p2 vase = new p2(false, 100, 0);

        vase.visitorThreads = new visitors[vase.numguests.get()];

        // create and start each thread
        for (int i = 0; i < vase.numguests.get(); ++i) {

            // each guest keeps trying to see the vase
            vase.visitorThreads[i] = new visitors(i, vase);

        }

        // start the simulation
        start = System.currentTimeMillis();

        for (int i = 0; i < vase.numguests.get(); ++i) {

            // start each thread
            vase.visitorThreads[i].start();

        }

        for (int i = 0; i < vase.numguests.get(); ++i) {
            try {

                // stop the threads
                vase.visitorThreads[i].join();
            }

            catch (Exception e) {

                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();
        duration = end - start;

        // print output
        System.out.println(vase.visitedguests + " guests viewed the crystal vase.");
        System.out.println("This program took " + duration + " ms.");

    }

}
