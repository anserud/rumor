
package ou3;


// TODO: Auto-generated Javadoc
/**
 * The Class Event.
 */
public class Event
{
    
    /** The id. */
    private EventID id;
    
    /** The origin. */
    private Node    origin;
    
    /** The time. */
    private int     time;
    
    /**
     * Instantiates a new event.
     *
     * @param origin the origin
     * @param time the time
     */
    public Event( Node origin, int time )
    {
        this.id = new EventID();
        this.origin = origin;
        this.time = time;
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public EventID getID()
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
