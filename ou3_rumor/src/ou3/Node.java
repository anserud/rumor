
package ou3;


import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;


public class Node implements Comparable<Node>
{
    
    private Position                position;
    private Node[]                  neighbours;
    private TreeMap<EventID, Route> routingTable;
    private ArrayList<Event>        eventList;
    private static float            eventProbability;
    private static float            agentProbability;
    private boolean                 active;
    private static final Random     randGen = new Random();
    
    public Node( int x, int y )
    {
        this.routingTable = new TreeMap<EventID, Route>();
        this.eventList = new ArrayList<Event>();
        this.position = new Position( x, y );
        this.active = true;
    }
    
    public void updateNeighbours( Node[] nextDoor )
    {
        this.neighbours = nextDoor;
    }
    
    public static void setEventProbability( float eventProbability )
    {
        Node.eventProbability = eventProbability;
    }
    
    public static void setAgentProbability( float agentProbability )
    {
        Node.agentProbability = agentProbability;
    }
    
    public Position getPosition()
    {
        return this.position;
    }
    
    @Override
    public int compareTo( Node other )
    {
        return this.position.compare( other.position );
    }
    
    public TreeMap<EventID, Route> getRoutingTable()
    {
        return routingTable;
    }
    
    public Route getRoute( EventID id )
    {
        return this.routingTable.get( id );
    }
    
    public boolean getActive()
    {
        return this.active;
    }
    
    public void setActive( boolean active )
    {
        this.active = active;
    }
    
    public Event getEvent( EventID id )
    {
        for ( Event e : this.eventList )
        {
            if ( e.getID().getID() == id.getID() ) return e;
            // System.out.print( " ," );
        }
        return null;
    }
    
    public Node[] getNeighbours()
    {
        return this.neighbours;
    }
    
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
     * @param time
     * @return
     */
    private void createEvent( int time )
    {
        Event e = new Event( this, time );
        this.eventList.add( e );
        
        Route r = new Route( e.getID(), 0, this );
        this.routingTable.put( r.getEvent(), r );
    }
    
    public QueryMessage generateQuery( EventID id )
    {
        
        return new QueryMessage( id, this );
    }
    
    @Override
    public String toString()
    {
        return "Node: [ x: " + this.position.getX() + " , y: "
                + this.position.getY() + " ]";
    }
    
    public boolean tryCreateAgent()
    {
        if ( Node.randGen.nextFloat() <= Node.agentProbability ) return true;
        return false;
    }
}
