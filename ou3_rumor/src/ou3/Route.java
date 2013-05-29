
package ou3;


// TODO: Auto-generated Javadoc
/**
 * The Class Route.
 */
public class Route
{
    
    /** The event. */
    private EventID event;
    
    /** The distance. */
    private int     distance;
    
    /** The direction. */
    private Node    direction;
    
    /**
     * Instantiates a new route.
     *
     * @param ID the id
     * @param dist the dist
     * @param dir the dir
     */
    public Route( EventID ID, int dist, Node dir )
    {
        this.event = ID;
        this.distance = dist;
        this.direction = dir;
    }
    
    /**
     * Instantiates a new route.
     *
     * @param toCopy the to copy
     */
    public Route( Route toCopy )
    {
        this.event = toCopy.getEvent();
        this.distance = toCopy.getDistance();
        this.direction = toCopy.getDirection();
    }
    
    /**
     * Gets the event.
     *
     * @return the event
     */
    public EventID getEvent()
    {
        return this.event;
    }
    
    /**
     * Gets the distance.
     *
     * @return the distance
     */
    public int getDistance()
    {
        return this.distance;
    }
    
    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public Node getDirection()
    {
        return this.direction;
    }
    
    /**
     * Update route.
     *
     * @param dist the dist
     * @param dir the dir
     */
    public void updateRoute( int dist, Node dir )
    {
        this.distance = dist;
        this.direction = dir;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
