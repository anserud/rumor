
package ou3;



/**
 * The Class Event. Responsible for keeping the Event s which happen
 * in the nodes. Needs to keep information about when and where it was 
 * created, aswell as its ID.
 * 
 * 
 * @see Node
 */
public class Event
{
    
    /** The id of the Event. */
    private int id;
    
    /** The origin of the Event. */
    private Node    origin;
    
    /** The time the Event happened.  */
    private int     time;
    
    /** The current id. */
    private static int currentID = 0;
    
    
    public static int getCurrentID(){
    	return currentID;
    }
    
    public static int nextID()
    {
    	return currentID++;
    }
     
    /**
     * Instantiates a new event. 
     * At the given node and time.
     *
     * @param origin the origin
     * @param time the time
     */
    public Event( Node origin, int time )
    {
        this.id = nextID();
        this.origin = origin;
        this.time = time;
       
    }
    
    /**
     * Gets the id of this Event.
     *
     * @return the id
     */
    public int getID()
    {
        return this.id;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Event: [ id:" + this.id + ", origin:" + this.origin + ", time:"
                + this.time + " ]\n";
    }
    
}
