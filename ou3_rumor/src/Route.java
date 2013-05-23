// TODO: Auto-generated Javadoc
/**
 * The Class Route.
 * @author Alexander Anserud 
 */
public class Route
{
	
	/** The event id. */
	private int eventID;
    
    /** The direction. */
    private Node direction;
    
    /** The distance. */
    private int distance;
    
    /**
     * Instantiates a new route.
     * @author Alexander Anserud 
     * @param eventID the event id
     * @param direction the direction
     * @param distance the distance
     */
    public Route(int eventID, Node direction, int distance)
    {
    	this.eventID = eventID;
    	this.direction = direction;
    	this.distance = distance;
    }
    
    
    /**
     * Gets the event id.
     * @author Alexander Anserud 
     * @return the event id
     */
    public int getEventID() {
		return eventID;
	}

	/**
	 * Gets the direction.
	 * @author Alexander Anserud 
	 * @return the direction
	 */
	public Node getDirection() {
		return direction;
	}

	/**
	 * Gets the distance.
	 * @author Alexander Anserud 
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Update the node using this dist and dir
	 * @param dist the new distance
	 * @param dir the new direction
	 */ 
	public void updateRoute( int dist, Node dir )
    {
        this.distance = dist;
        this.direction = dir;
    }
	
	/**
	 * Copy the Route toCopy
	 * @param toCopy Route to copy
	 * @return new Route with the same attributes
	 */
    public Route copy( Route toCopy )
    {
    	return new Route(toCopy.getEvent(),getDistance(),getDirection());
    }
}
