
package ou3;


import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;


// TODO: Auto-generated Javadoc
/**
 * The Class Node.
 */
public class Node implements Comparable<Node>
{
    
    /** The position. */
    private Position                position;
    
    /** The neighbours. */
    private Node[]                  neighbours;
    
    /** The routing table. */
    private TreeMap<EventID, Route> routingTable;
    
    /** The event list. */
    private ArrayList<Event>        eventList;
    
    /** The event probability. */
    private static float              eventProbability;
    
    /** The agent probability. */
    private static float              agentProbability;
    
    /** The active. */
    private boolean                 active;
    
    /** The Constant randGen. */
    private static final Random     randGen = new Random();
    
    /**
     * Instantiates a new node.
     *
     * @param x the x
     * @param y the y
     */
    public Node( int x, int y )
    {
        this.routingTable = new TreeMap<EventID, Route>();
        this.eventList = new ArrayList<Event>();
        this.position = new Position( x, y );
        this.active = true;
    }
    
    /**
     * Update neighbours.
     *
     * @param nextDoor the next door
     */
    public void updateNeighbours( Node[] nextDoor )
    {
        this.neighbours = nextDoor;
    }
    
    /**
     * Sets the event probability.
     *
     * @param eventProbability the new event probability
     */
    public static void setEventProbability( float eventProbability )
    {
        Node.eventProbability = eventProbability;
    }
    
    /**
     * Sets the agent probability.
     *
     * @param agentProbability the new agent probability
     */
    public static void setAgentProbability( float agentProbability )
    {
        Node.agentProbability = agentProbability;
    }
    
    /**
     * Gets the position.
     *
     * @return the position
     */
    public Position getPosition()
    {
        return this.position;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( Node other )
    {
        return this.position.compare( other.position );
    }
    
    /**
     * Gets the routing table.
     *
     * @return the routing table
     */
    public TreeMap<EventID, Route> getRoutingTable()
    {
        return routingTable;
    }
    
    /**
     * Gets the route.
     *
     * @param id the id
     * @return the route
     */
    public Route getRoute( EventID id )
    {
        return this.routingTable.get( id );
    }
    
    /**
     * Gets the active.
     *
     * @return the active
     */
    public boolean getActive()
    {
        return this.active;
    }
    
    /**
     * Sets the active.
     *
     * @param active the new active
     */
    public void setActive( boolean active )
    {
        this.active = active;
    }
    
    /**
     * Gets the event.
     *
     * @param id the id
     * @return the event
     */
    public Event getEvent( EventID id )
    {
        for ( Event e : this.eventList )
        {
            if ( e.getID().getID() == id.getID() ) return e;
            // System.out.print( " ," );
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
     * Try create event.
     *
     * @param time the time
     * @return true, if successful
     */
    public boolean tryCreateEvent( int time )
    {
        if ( Node.randGen.nextFloat(  ) <= Node.eventProbability )
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
        this.routingTable.put( r.getEvent(), r );
    }
    
    /**
     * Generate query.
     *
     * @param id the id
     * @return the query message
     */
    public QueryMessage generateQuery( EventID id )
    {
        
        return new QueryMessage( id, this );
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Node: [ x: " + this.position.getX() + " , y: "
                + this.position.getY() + " ]";
    }

    /**
     * Try create agent.
     *
     * @return true, if successful
     */
    public boolean tryCreateAgent()
    {
        if (  Node.randGen.nextFloat(  ) <= Node.agentProbability )
            return true;
        return false;
    }
}
