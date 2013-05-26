
package ou3;


public class Event
{
    
    private EventID id;
    private Node    origin;
    private int     time;
    
    public Event( Node origin, int time )
    {
        this.id = new EventID();
        this.origin = origin;
        this.time = time;
    }
    
    public EventID getID()
    {
        return this.id;
    }
    
    @Override
    public String toString()
    {
        return "Event: [ id:" + this.id + ", origin:" + this.origin + ", time:"
                + this.time + " ]\n";
    }
    
}
