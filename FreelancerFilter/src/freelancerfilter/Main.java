/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package freelancerfilter;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mattie
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Start Threads:
        //   POP3 POLLER
        //   EVENT ENGINE

        EventQ eq = EventQ.getInstance();

        Thread t1 = new POP3Poller();
        Thread t2 = new EventEngine();

        t1.start();
        t2.start();
    

    }

}


class POP3Poller extends Thread {
    private EventQ eq = EventQ.getInstance();

    public void run () {
        int i = 10;
        while (i > 0){
            eq.add(Events.GETDETAILS);
            eq.add(Events.SENDMAIL);
            eq.add(Events.END);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(POP3Poller.class.getName()).log(Level.SEVERE, null, ex);
            }
            i--;
        }
    }

}

class EventProcessor extends Thread {

    private Events event;

    public EventProcessor (Events e) {
        event = e;
    }

    public void run () {
        switch (event){
            case SENDMAIL:
                System.out.println("this is the sendmail");
                break;
            case GETDETAILS:
                System.out.println("this is the getdetails");
                break;
            case END:
                System.out.println("this is the end");
                break;
            default:
                System.out.println("Event not implemented: " + event);
        }
    }
}

class EventEngine extends Thread {
    private EventQ eq = EventQ.getInstance();

    public void run () {
        while (true){
            while(!eq.isEmpty())
                process(eq.remove());
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(EventProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void process (Events e) {

        EventProcessor ep = new EventProcessor ( e );
        ep.run();

    }

}