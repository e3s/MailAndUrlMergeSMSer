package freelancerfilter;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

public class EventQ extends PriorityQueue<Events> {

    private static EventQ events = null;

    private EventQ(){
    }

    synchronized public static EventQ getInstance() {
        if ( null == events )
            events = new EventQ();
        return events;
    }

}
