
package ou3;


import java.util.TreeMap;


public class AgentMessage extends Message
{
    
    private static int              lifeLength;
    private TreeMap<EventID, Route> routingTable;
    
    public static void setLifeLength( int lifeLength )
    {
        AgentMessage.lifeLength = lifeLength;
    }
    
    public AgentMessage( Node position )
    {
        super( position );
        this.timeToLive = AgentMessage.lifeLength;
        this.routingTable = new TreeMap<EventID, Route>();
    }
    
    public void syncroniseTable( TreeMap<EventID, Route> nodeTable )
    {
        Route updateAt;
        EventID key;
        
        // For every route in the message routingtable:
        for ( Route r : this.routingTable.values() )
        {
            key = r.getEvent();
            
            // Check if the node contains this route:
            if ( nodeTable.containsKey( key ) )
            {
                
                // If so update:
                updateAt = nodeTable.get( key );
                if ( updateAt.getDistance() < r.getDistance() )
                {
                    // The nodes route is shorter, update r
                    r.updateRoute( updateAt.getDistance(),
                            updateAt.getDirection() );
                } else
                {
                    // if this route is shorter, update the node.
                    updateAt.updateRoute( r.getDistance(), r.getDirection() );
                }
            } else
            {
                // the node doesn't have the route, add it:
                nodeTable.put( key, new Route(r) );
            }
        }
        
        // Add all routes from the node:
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
