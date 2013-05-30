package ou3;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * The Class of the Node, existing at a position in the Network.
 * Will keep track off the nearby Node s to which Message s can be sent,
 * The list of Event s that has happened at the Node, and a routingTable of
 * information concerning how to get to other Event s. Must also know if able
 * to recieve or send a Message this round.
 * 
 * @see Network
 */
public class Node 
{
    
    /** The position of the Node. */
    private Point 					point;
    
    /** The nearby Node neighbours. */
    private Node[]                  neighbours;
    
    /** The routing table. */
    private TreeMap<Integer, Route> routingTable;
    
    /** The list of Event that has happened here. */
    private ArrayList<Event>        eventList;
    
    /** The event probability. */
    private static float            eventProbability;
    
    /** The agent probability. */
    private static float              agentProbability;
    
    /** The active state, determining whether it can handle messages. */
    private boolean                 active;
    
    /** The random number generator. */
    private static final Random     randGen = new Random();
    
    /**
     * Instantiates a new node.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Node( int x, int y )
    {
        this.routingTable = new TreeMap<Integer, Route>();
        this.eventList = new ArrayList<Event>();
        this.point = new Point( x, y );
        this.active = true;
    }
    
    /**
     * Update neighbours, setting them to the given array.
     * 
     * @param neighbour
     *            array of the communicatable Node s nearby
     */
    public void updateNeighbours( Node[] nextDoor )
    {
        this.neighbours = nextDoor;
    }
    
    /**
     * Set the event probability.
     *
     * @param eventProbability the new event probability
     */
    public static void setEventProbability( float eventProbability )
    {
        Node.eventProbability = eventProbability;
    }
    
    /**
     * Set the agent probability.
     *
     * @param agentProbability the new agent probability
     */
    public static void setAgentProbability( float agentProbability )
    {
        Node.agentProbability = agentProbability;
    }
    
    /**
     * Get the position.
     *
     * @return the position
     */
    public Point getPoint()
    {
        return this.point;
    }

    
    /**
     * Gets the routing table.
     *
     * @return the routing table
     */
    public TreeMap<Integer, Route> getRoutingTable()
    {
        return routingTable;
    }
    
    /**
     * Gets the route.
     *
     * @param id the id of the requested Route s Event
     * @return the Route
     */
    public Route getRoute( int id )
    {
        return this.routingTable.get( id );
    }
    
    /**
     * Get the active.
     *
     * @return the active state
     */
    public boolean getActive()
    {
        return this.active;
    }
    
    /**
     * Sets the active.
     *
     * @param active the new active state
     */
    public void setActive( boolean active )
    {
        this.active = active;
    }
    
    /**
     * Gets the specified Event.
     *
     * @param id the id of the Event
     * @return the Event, or null if it can't be found
     */
    public Event getEvent( int id )
    {
        for ( Event e : this.eventList )
        {
            if ( e.getID() == id ) return e;
        }
        return null;
    }
    
    /**
     * Gets the neighbours.
     *
     * @return the neighbours
     */
    public Node[] getNeighbours()
    {
        return this.neighbours;
    }
    
    /**
     * Try create event. Testing the set probability, and running createEvent()
     * if successful.
     * 
     * @param time the time
     * @return true, if successful
     * @see #createEvent(int time )
     */
    public boolean tryCreateEvent( int time )
    {
        if ( Node.randGen.nextFloat() <= Node.eventProbability )
        {
            createEvent( time );
            return true;
        }
        return false;
    }
    
    /**
     * Creates the event.
     *
     * @param time the time
     */
    private void createEvent( int time )
    {
        Event e = new Event( this, time );
        this.eventList.add( e );
        Route r = new Route( e.getID(), 0, this );
        this.routingTable.put( new Integer(r.getEventID()), r );
    }
    
    /**
     * Generate query aimed at the id.
     * 
     * @param id the id
     * @return the query message
     */
    public QueryMessage generateQuery( int id )
    {
        
        return new QueryMessage( id, this );
    }
    
    /**
     * Return String of information about the node.
     * 
     * @return String of information
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Node: [ x: " + this.point.toString() + " ]";
    }
    
    /**
     * Test agent probability.
     * 
     * @return true, if successful
     */
    public boolean tryCreateAgent()
    {
        if ( Node.randGen.nextFloat() <= Node.agentProbability ) return true;
        return false;
    }
}
