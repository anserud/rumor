package ou3;


/**
 * The Class Route, containing information used in the
 * routingTables pertaining to how to get to an event.
 * Will be bound to a specific Event by the ID, and know
 * the distance there. Will also store information about 
 * which way to go to get there.
 */
public class Route
{
    
    /** The event. */
    private int eventID;
    
    /** The distance. */
    private int     distance;
    
    /** The direction. */
    private Node    direction;
    
    /**
     * Instantiates a new route with the given parameters.
     *
     * @param ID the int id
     * @param dist the dist
     * @param dir the dir
     */
    public Route( int ID, int dist, Node dir )
    {
        this.eventID = ID;
        this.distance = dist;
        this.direction = dir;
    }
    
    /**
     * Instantiates a new route based on a previous one.
     *
     * @param toCopy the to copy
     */
    public Route( Route toCopy )
    {
        this.eventID = toCopy.getEventID();
        this.distance = toCopy.getDistance();
        this.direction = toCopy.getDirection();
    }
    
    /**
     * Get the event.
     *
     * @return the event
     */
    public int getEventID()
    {
        return this.eventID;
    }
    
    /**
     * Get the distance.
     *
     * @return the distance
     */
    public int getDistance()
    {
        return this.distance;
    }
    
    /**
     * Get the direction.
     *
     * @return the direction
     */
    public Node getDirection()
    {
        return this.direction;
    }
    
    /**
     * Update route to the given distance and direction.
     *
     * @param dist the dist
     * @param dir the dir
     */
    public void updateRoute( int dist, Node dir )
    {
        this.distance = dist;
        this.direction = dir;
    }
    
    /**
     * Return String information about the Route.
     * 
     * @return String of information
     */
    @Override
    public String toString(){
    	return "["+this.eventID+" "+this.direction.toString()+" ]";
    }
    
    /** 
     * Compare if this route is about the same as the other Object.
     * 
     * @return true if they are about the same Event
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object other )
    {
        if ( this == other ) return true;
        if ( other == null ) return false;
        if ( other.getClass() == this.getClass() )
            return ( this.getEventID() == ( (Route) other ).getEventID() );
        if ( ( this.getEventID() == (((Route) other).getEventID())) )
            return ( this.getEventID() == (((Route) other).getEventID()));
        return false;
    }
}
