// TODO: Auto-generated Javadoc
/**
 * The Class Event.
 * @author Alexander Anserud 
 */
public class Event
{


	/** The id. */
	private int id;
    
    /** The orgin. */
    private Node orgin;
    
    /** The created_at. */
    private int created_at;
    
    
    /**
     * Instantiates a new event.
     * @author Alexander Anserud 
     * @param id the id
     * @param orgin the orgin
     * @param created_at the created_at
     */
    public Event (int id, Node orgin, int created_at){
    	this.id = id;
    	this.orgin = orgin;
    	this.created_at = created_at;
    }
    
    /**
     * Gets the event id.
     * @author Alexander Anserud 
     * @return the id
     */
    public int getId() {
		return id;
	}

	/**
	 * Gets the orgin node.
	 * @author Alexander Anserud 
	 * @return the orgin
	 */
	public Node getOrgin() {
		return orgin;
	}

	/**
	 * Gets the time created.
	 * @author Alexander Anserud 
	 * @return the created_at
	 */
	public int getCreated_at() {
		return created_at;
	}
	
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        
        return "(id: "+id+ "orgin: "+orgin+" created_at: "+created_at+")";
    }
}
