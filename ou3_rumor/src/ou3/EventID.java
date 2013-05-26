
package ou3;


public class EventID implements Comparable<EventID>
{
    
    private int        ID;
    private static int currentID = 0;
    
    public EventID( )
    {
        ID = nextID();
    }
    
    public EventID( int id )
    {
        this.ID = id;
    }
    
    public int getID()
    {
        return this.ID;
    }
    
    private static int nextID()
    {
        return EventID.currentID++;
    }
    
    public static int currentID()
    {
        return EventID.currentID;
    }
    
    @Override
    public boolean equals( Object other )
    {
        if ( this == other ) return true;
        if ( other == null ) return false;
        if ( this.getClass() != other.getClass() ) return false;
        if ( this.ID == ( (EventID) other ).getID() ) return true;
        return false;
    }
    
    @Override
    public String toString()
    {
        return " ID=[" + ID + "] ";
    }
    
    @Override
    public int compareTo( EventID other )
    {
        return this.ID - other.getID();
    }
    
}
