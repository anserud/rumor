import java.util.TreeMap;


public class AgentMessage extends Message
{
    
    private static int              lifeLength;
    private TreeMap<EventID, Route> routingTable;
    
    public static void setLifeLength( int lifeLength )
    {
        AgentMessage.lifeLength = lifeLength;
    }
    
    public AgentMessage( Node position, Route source )
    {
        super( position );
        this.timeToLive = AgentMessage.lifeLength;
        this.routingTable = new TreeMap<EventID, Route>();
        this.routingTable.put( source.getEvent(), source );
    }
    
    public void syncroniseTable( TreeMap<EventID, Route> nodeTable )
    {
        Route updateAt;
        EventID key;
        for ( Route r : this.routingTable.values() )
        {
            key = r.getEvent();
            if ( nodeTable.containsKey( key ) )
            {
                updateAt = nodeTable.get( key );
                if ( updateAt.getDistance() < r.getDistance() )
                {
                    r.updateRoute( updateAt.getDistance(),
                            updateAt.getDirection() );
                } else
                {
                    updateAt.updateRoute( r.getDistance(), r.getDirection() );
                }
            } else
            {
                nodeTable.put( key, new Route( r ) );
            }
        }
        for ( Route r : nodeTable.values() )
        {
            if ( !this.routingTable.containsKey( r.getEvent() ) )
                this.routingTable.put( r.getEvent(), new Route( r ) );
        }
    }
    
    @Override
    protected Node choseTarget()
    {
        return this.findNewPath();
    }
    
    @Override
    public void processMessage()
    {
        syncroniseTable( this.currentPosition.getRoutingTable() );
    }
    
    @Override
    public boolean stepUpdate()
    {
        this.timeToLive--;
        return this.timeToLive > 0;
    }
    
    @Override
    protected void sendingUpdate()
    {
        for ( Route r : this.routingTable.values() )
        {
            r.updateRoute( r.getDistance() + 1, this.currentPosition );
        }
    }
    
    @Override
    public String toString()
    {
        return super.toString() + " / AgentMessage [routingTableSize="
                + this.routingTable.size() + "] /";
    }
}
