import java.util.Stack;
import java.util.TreeSet;


public class QueryMessage extends Message
{
    
    private static int  lifeLength;
    private static int  expected;
    
    private boolean     firstAttempt;
    private int         attemptTime;
    private Stack<Node> path;
    private State       status;
    private EventID     target;
    private Event       payLoad;
    
    private enum State
    {
        ROUTING, SEARCHING, BACKTRACKING;
    }
    
    public QueryMessage( EventID target, Node position )
    {
        super( position );
        this.timeToLive = QueryMessage.lifeLength;
        this.path = new Stack<Node>();
        this.status = State.SEARCHING;
        this.target = target;
        this.firstAttempt = true;
        this.attemptTime = 0;
    }
    
    @Override
    protected Node choseTarget()
    {
        switch ( this.status ) {
        case SEARCHING:
            return this.findNewPath();
        case ROUTING:
            return this.currentPosition.getRoute( this.target ).getDirection();
        case BACKTRACKING:
            return this.path.peek();
        }
        return null;
    }
    
    @Override
    public void processMessage()
    {
        switch ( this.status ) {
        case SEARCHING:
            if ( this.currentPosition.getRoutingTable().containsKey(
                    this.target ) )
            {
                this.status = State.ROUTING;
            }
            break;
        case ROUTING:
            Route r = this.currentPosition.getRoute( this.target );
            if ( r.getDistance() == 0 )
            {
                this.payLoad = this.currentPosition.getEvent( this.target );
                this.status = State.BACKTRACKING;
            }
            break;
        case BACKTRACKING:
            if ( this.path.empty() )
            {
                System.out.println( this.payLoad );
                this.timeToLive = 0;
                this.firstAttempt = false;
            }
            break;
        
        }
    }
    
    @Override
    public boolean stepUpdate()
    {
        this.attemptTime++;
        if ( this.attemptTime >= QueryMessage.expected )
        {
            if ( this.firstAttempt )
            {
                this.restartQuery();
                this.firstAttempt = false;
                // System.out.print( " Restarting Query to: "+this.target+" 1\n"
                // );
                return true;
            } else
            {
                // System.out.print( " Destroying Query to: "+this.target+" 1\n"
                // );
                return false;
            }
        }
        if ( this.status == State.SEARCHING ) this.timeToLive--;
        if ( this.timeToLive <= 0 )
        {
            if ( this.firstAttempt )
            {
                this.restartQuery();
                this.firstAttempt = false;
                // System.out.print( " Restarting Query to: "+this.target+" 2\n"
                // );
            } else
            {
                // System.out.print( " Destroying Query to: "+this.target+" 2\n"
                // );
                return false;
            }
        }
        return true;
    }
    
    public static void setLifeLength( int lifeLength )
    {
        QueryMessage.lifeLength = lifeLength;
        
    }
    
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
    
    @Override
    public String toString()
    {
        return super.toString() + " / QueryMessage [ status=" + this.status
                + ", target=" + this.target + ", payLoad=" + this.payLoad
                + "] / ";
    }
    
    public static void setExpected( int i )
    {
        QueryMessage.expected = i;
        
    }
    
    private void restartQuery()
    {
        this.attemptTime = 0;
        this.timeToLive = QueryMessage.lifeLength;
        this.currentPosition = this.path.firstElement();
        this.path = new Stack<Node>();
        this.status = State.SEARCHING;
        this.past = new TreeSet<Node>();
        this.payLoad = null;
    }
    
}

