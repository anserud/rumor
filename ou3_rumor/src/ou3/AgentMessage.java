package ou3;


import java.util.TreeMap;

/**
 * The Class AgentMessage, creates an agent message. 
 * Holds a long lifelenght and forwards information as 
 * it gets sent around the network.
 * Contains a table with events and paths to them.
 * 
 */
public class AgentMessage extends Message
{
    
    /** The life length of the agent message. */
    private static int              lifeLength;
    
    /** The routing table. */
    private TreeMap<Integer, Route> routingTable;
    
    /**
     * Sets the life length for an agentmessage.
     *
     * @param lifeLength the new life length
     */
    public static void setLifeLength( int lifeLength )
    {
        AgentMessage.lifeLength = lifeLength;
    }
    
    /**
     * Instantiates a new agent message.
     *
     * @param position the position
     */
    public AgentMessage( Node position )
    {
        super( position );
        this.timeToLive = AgentMessage.lifeLength;
        this.routingTable = new TreeMap<Integer, Route>();
    }
    
    /**
     * Syncronise table.
     * Updated each time the message is forwarded 
     *
     * @param nodeTable the node table 
     */
    public void syncroniseTable( TreeMap<Integer, Route> nodeTable )
    {
        Route updateAt;
        int key;
        
        // For every route in the message routingtable:
        for ( Route r : this.routingTable.values() )
        {
            key = r.getEventID();
            
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
                nodeTable.put(key, new Route(r) );
            }
        }
        
        // Add all routes from the node:
        for ( Route r : nodeTable.values() )
        {
            if ( !this.routingTable.containsKey( r.getEventID() ) )
                this.routingTable.put( r.getEventID(), new Route( r ) );
        }
    }
    
    /* (non-Javadoc)
     * @see ou3.Message#choseTarget()
     */
    @Override
    protected Node choseTarget()
    {
        return this.findNewPath();
    }
    
    /* (non-Javadoc)
     * @see ou3.Message#processMessage()
     */
    @Override
    public void processMessage()
    {
        syncroniseTable( this.currentPosition.getRoutingTable() );
    }
    
    /* (non-Javadoc)
     * @see ou3.Message#stepUpdate()
     */
    @Override
    public boolean stepUpdate()
    {
        this.timeToLive--;
        return this.timeToLive > 0;
    }
    
    /* (non-Javadoc)
     * @see ou3.Message#sendingUpdate()
     */
    @Override
    protected void sendingUpdate()
    {
        for ( Route r : this.routingTable.values() )
        {
            r.updateRoute( r.getDistance() + 1, this.currentPosition );
        }
    }
    
    /* (non-Javadoc)
     * @see ou3.Message#toString()
     */
    @Override
    public String toString()
    {
        return super.toString() + " / AgentMessage [routingTableSize="
                + this.routingTable.size() + "] /";
    }
}
