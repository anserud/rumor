
package ou3;


public class Route
{
    
    private EventID event;
    private int     distance;
    private Node    direction;
    
    public Route( EventID ID, int dist, Node dir )
    {
        this.event = ID;
        this.distance = dist;
        this.direction = dir;
    }
    
    public Route( Route toCopy )
    {
        this.event = toCopy.getEvent();
        this.distance = toCopy.getDistance();
        this.direction = toCopy.getDirection();
    }
    
    public EventID getEvent()
    {
        return this.event;
    }
    
    public int getDistance()
    {
        return this.distance;
    }
    
    public Node getDirection()
    {
        return this.direction;
    }
    
    public void updateRoute( int dist, Node dir )
    {
        this.distance = dist;
        this.direction = dir;
    }
    
    @Override
    public boolean equals( Object other )
    {
        if ( this == other ) return true;
        if ( other == null ) return false;
        if ( other.getClass() == this.getClass() )
            return ( this.event.getID() == ( (Route) other ).getEvent().getID() );
        if ( other.getClass() == this.event.getClass() )
            return ( this.event.getID() == ( (EventID) other ).getID() );
        return false;
    }
}
