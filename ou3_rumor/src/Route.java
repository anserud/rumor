// TODO: Auto-generated Javadoc
/**
 * The Class Route.
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
     *
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
     *
     * @return the event id
     */
    public int getEventID() {
		return eventID;
	}

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public Node getDirection() {
		return direction;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
}
