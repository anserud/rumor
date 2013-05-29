
package ou3;


/**
 * The Class EventID, which will keep track of what the current
 * and next IDs will be for the Event s. 
 */
public class EventID implements Comparable<EventID>
{
    
    /** The id. */
    private int        ID;
    
    /** The current id. */
    private static int currentID = 0;
    
    /**
     * Instantiates a new event id with the current number,
     * incrementing it so that each call to this will giva a new
     * number as ID.
     * 
     * @see #EventID( int id )
     */
    public EventID( )
    {
        ID = EventID.nextID();
    }
    
    /**
     * Instantiates a new event id with the given ID-number.
     *
     * @param id the number id
     * @see #EventID( )
     */
    public EventID( int id )
    {
        this.ID = id;
    }
    
    /**
     * Gets the id.
     *
     * @return the number id of this EventID
     */
    public int getID()
    {
        return this.ID;
    }
    
    /**
     * Next id, will return the current ID and increment it,
     * so that the next EventID created will have a higher number.
     *
     * @return the int EventID
     */
    private static int nextID()
    {
        return EventID.currentID++;
    }
    
    /**
     * Current id. Checking what the current ID is at.
     * Used to see how many Event s has been created.
     *
     * @return the int
     */
    public static int currentID()
    {
        return EventID.currentID;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object other )
    {
        if ( this == other ) return true;
        if ( other == null ) return false;
        if ( this.getClass() != other.getClass() ) return false;
        if ( this.ID == ( (EventID) other ).getID() ) return true;
        return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return " ID=[" + ID + "] ";
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( EventID other )
    {
        // Ordered by the ID.
        return this.ID - other.getID();
    }
    
}
