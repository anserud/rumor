package ou3;


import java.util.HashSet;
import java.util.Stack;

/**
 * The Class QueryMessage. A query for a certain event can be initiated from a
 * querynode. From there the query will be forwarded to the node which detected
 * the particular event.
 * 
 * @see Message
 */
public class QueryMessage extends Message
{
    
    /** The life length of the query. */
    private static int  lifeLength;
    
    /** The expected time for the query to return. */
    private static int  expected;
    
    /** The first attempt to send query to an event. */
    private boolean     firstAttempt;
    
    /** The attempt time. */
    private int         attemptTime;
    
    /** The path walked. */
    private Stack<Node> path;
    
    /** The status of the query
      * ex. SEARCHING for Route. 
      */
    private State       status;
    
    /** The target event id. */
    private int     target;
    
    /** The information the event contains. */
    private Event       eventInformation;
    
    /** The number of successful queries. */
    private static int success = 0;
    
    /** The total number of queries sent. */
    private static int total = 0;
    
    /**
     * Gets the number of successfully sent queries.
     *
     * @return the succes
     */
    public static int getSucces()
    {
        return QueryMessage.success;
        
        
    }
    
    /**
     * Gets the total number of sent queries sent
     * including the unsuccsessful ones.
     *
     * @return the total amount sent
     */
    public static int getTotal()
    {
        return QueryMessage.total;
    }
    
    /**
     * The state of the query.
     */
    private enum State
    {
        
        /** The routing. */
        ROUTING, 
        /** The searching. */
        SEARCHING, 
        /** The backtracking. */
        BACKTRACKING;
    }
    
    /**
     * Initiates a new query message.
     *
     * @param target id of the Event
     * @param position the Node at which created
     */
    public QueryMessage( int target, Node position )
    {
        super( position );
        this.timeToLive = QueryMessage.lifeLength;
        this.path = new Stack<Node>();
        this.status = State.SEARCHING;
        this.target = target;
        this.firstAttempt = true;
        this.attemptTime = 0;
        
        QueryMessage.total++;
    }
    
    /**
     * Overriden abstract method. Will chose the target differently depending on
     * the current status. Status:
     * <p>
     * SEARCHING: use the standard findNewPath() to continue randomly.
     * <p>
     * ROUTING: follow the Route in the Node s routingTable.
     * <p>
     * BACKTRACKING: follow the path backwards.
     * 
     * @return the chosen Node for target
     * @see Message#choseTarget()
     */
    @Override
    protected Node choseTarget()
    {
        switch ( this.status ) {
        case SEARCHING:
            // Keep looking:
            return this.findNewPath();
        case ROUTING:
            // Follow the Route:
            return this.currentPosition.getRoute( this.target ).getDirection();
        case BACKTRACKING:
            // Follow the path backwards:
            return this.path.peek();
        }
        return null;
    }
    
    /**
     * Overriden abstract method. Will process differently depending on current
     * status. Status:
     * <p>
     * SEARCHING: check if the routingtable in the node contains the target
     * event. If so start ROUTING.
     * <p>
     * ROUTING: check whether the Route has been followed to the end. If so
     * start BACKTRACKING.
     * <P>
     * BACKTRACKING: check whether back to start If so print the Event, mark
     * succes and destroy.
     * 
     * @see Message#processMessage()
     */
    @Override
    public void processMessage()
    {
        switch ( this.status ) {
        case SEARCHING:
            // Check whether the current Node has a Route to follow:
            if ( this.currentPosition.getRoutingTable().containsKey(
                    this.target ) )
            {
                // If so start ROUTING there:
                this.status = State.ROUTING;
            }
            break;
        case ROUTING:
            // Check whether the Route has been followed to the end:
            Route r = this.currentPosition.getRoute( this.target );
            if ( r.getDistance() == 0 )
            {
                // If so get the event and start BACKTRACKING:
                this.eventInformation = this.currentPosition.getEvent( this.target );
                this.status = State.BACKTRACKING;
            }
            break;
        case BACKTRACKING:
            // Check whether back to start:
            if ( this.path.empty() )
            {
                // If so print the event, mark succes and destroy:
                System.out.println( this.eventInformation );
                QueryMessage.success++;
                this.timeToLive = 0;
                this.firstAttempt = false;
            }
            break;
        
        }
    }
    
    /**
     * Overriden abstract method. Update the QueryMessage after the timeStep,
     * incrementing the attemptTime, and (decrement) the timeToLive if
     * SEARCHING. If they reach their limits, restart if firstAttempt or return
     * false otherwise.
     * 
     * @return true if it survives
     * @see Message#stepUpdate()
     */
    @Override
    public boolean stepUpdate()
    {
        // Check attemptTime survival:
        this.attemptTime++;
        if ( this.attemptTime >= QueryMessage.expected )
        {
            // Check restart:
            if ( this.firstAttempt )
            {
                // Restart:
                this.restartQuery();
                this.firstAttempt = false;
                return true;
            } else
            {
                // Didn't survive:
                return false;
            }
        }
        
        // Check timeToLive survival:
        if ( this.status == State.SEARCHING ) this.timeToLive--;
        if ( this.timeToLive <= 0 )
        {
            // Check restart:
            if ( this.firstAttempt )
            {
                // Restart:
                this.restartQuery();
                this.firstAttempt = false;
            
            } else
            {
                // Didn't survive:
                return false;
            }
        }
        return true;
    }
    
    /**
     * Sets the life length of the query.
     *
     * @param lifeLength, new life length
     */
    public static void setLifeLength( int lifeLength )
    {
        QueryMessage.lifeLength = lifeLength;
        
    }
    
    /**
     * Overriden abstract method. Will update differently depending on current
     * status. If SEARCHING or ROUTING add the currentPosition to the path. If
     * BACKTRACKING pop the top of the path.
     * 
     * @see Message#sendingUpdate()
     */
    @Override
    protected void sendingUpdate()
    {
        switch ( this.status ) {
        case SEARCHING:
        case ROUTING:
            this.path.add( currentPosition );
            break;
        case BACKTRACKING:
            this.path.pop();
            break;
        }
        
    }
    
    /**
     * Return String of some information about the Message.
     * 
     * @return String of information
     * @see Message#toString()
     */
    @Override
    public String toString()
    {
        return super.toString() + " / QueryMessage [ status=" + this.status
                + ", target=" + this.target + ", eventInformation=" 
                + this.eventInformation + "] / ";
    }
    
    /**
     * Sets the expected time for the query to return.
     *
     * @param i, the new expected time.
     */
    public static void setExpected( int i )
    {
        QueryMessage.expected = i;
        
    }
    
    /**
     * Restart query. Used if firstAttempt failed.
     */
    private void restartQuery()
    {
        this.attemptTime = 0;
        this.timeToLive = QueryMessage.lifeLength;
        this.currentPosition = this.path.firstElement();
        this.path = new Stack<Node>();
        this.status = State.SEARCHING;
        this.past = new HashSet<Node>(120);
        this.eventInformation = null;
    }
    
}
