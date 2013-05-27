
package ou3;


// TODO: Auto-generated Javadoc
/**
 * The Class EventID.
 */
public class EventID implements Comparable<EventID>
{
    
    /** The id. */
    private int        ID;
    
    /** The current id. */
    private static int currentID = 0;
    
    /**
     * Instantiates a new event id.
     */
    public EventID( )
    {
        ID = nextID();
    }
    
    /**
     * Instantiates a new event id.
     *
     * @param id the id
     */
    public EventID( int id )
    {
        this.ID = id;
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getID()
    {
        return this.ID;
    }
    
    /**
     * Next id.
     *
     * @return the int
     */
    private static int nextID()
    {
        return EventID.currentID++;
    }
    
    /**
     * Current id.
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
        return this.ID - other.getID();
    }
    
}
