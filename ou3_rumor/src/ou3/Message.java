package ou3;


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 * The abstract Class Message. Containing functionality to send messages
 * throughout the Network by processing, updating and sending them.
 * 
 * @see QueryMessage
 * @see AgentMessage
 * @see Network
 */
public abstract class Message 
{
    
    /** The time to live. */
    protected int               timeToLive;
    
    /** The past, visited Node s. */
    protected HashSet<Node>     past;
    
    /** The current position. */
    protected Node              currentPosition;
    
    /** The random number generator. */
    static private final Random randGen = new Random();
    
    /**
     * Instantiates a new message.
     *
     * @param position the Node position created at
     */
    public Message( Node position )
    {
        this.past = new HashSet<Node>(120);
        this.currentPosition = position;
    }
    
    /**
     * Chose target. Abstract method to be implemented. Must return the node to
     * which a message will move next; used in the sendMessage().
     * 
     * @return the node to move to
     */
    protected abstract Node choseTarget();
    
    /**
     * Process message. Abstract method to be implemented. The Messages will
     * have different processes to perform at each timeStep, they will be done
     * by this.
     */
    public abstract void processMessage();
    
    /**
     * Step update. Abstract method to be implemented. The Messages will update
     * differently at each timeStep, this method must return whether they
     * survive (true) to the next step.
     * 
     * @return true, if successful
     */
    public abstract boolean stepUpdate();
    
    /**
     * Sending update. Abstract method to be implemented. The Messages will
     * behave differently as they are sent. This method will be called during
     * the sendMessage().
     * 
     * @see #sendMessage()
     */
    protected abstract void sendingUpdate();
    
    /**
     * Send the Message, performing all actions neccesary during this moment.
     * First chose target, then do the sendingUpdate() and add the
     * currentPosition to the past, also add the Neighbours. Then set the
     * currentPosition to the target. Also set the Node s inactive. Return
     * whether the target was active, used to see whether queueing is neccesary.
     * 
     * @return true, if the target was active
     */
    public boolean sendMessage()
    {
        this.currentPosition.setActive( false );
        
        // Chose target:
        Node target = this.choseTarget();
        
        // Send Update:
        this.sendingUpdate();
        
        // Add known to past:
        this.past.add( currentPosition );
        for ( Node n : currentPosition.getNeighbours() )
            this.past.add( n );
            
        // Change the current position:
        this.currentPosition = target;
        
        // Check and set activity:
        if ( target.getActive() )
        {
            target.setActive( false );
            return true;
        }
        return false;
    }
    
    /**
     * Sets the time to live.
     *
     * @param t the new time to live
     */
    public void setTimeToLive( int t )
    {
        this.timeToLive = t;
    }
    
    /**
     * Gets the candidates.
     *
     * @return the candidates
     */
    private Node[] getCandidates()
    {
        return this.currentPosition.getNeighbours();
    }
    
    /**
     * Cross reference the neighbours with the past positions visited.
     * 
     * @param neighbours
     *            the neighbours
     * @return the array list of nodes not seen earlier
     */
    private ArrayList<Node> crossReference( Node[] neighbours )
    {
        ArrayList<Node> unvisited = new ArrayList<Node>();
        for ( Node n : neighbours )
        {
            if ( !this.past.contains( n ) )
            {
                unvisited.add( n );
            }
        }
        return unvisited;
    }
    
    /**
     * Find new path. Standard method of chosing a target for sending. Will 
     * try to avoid Node s already seen.
     * 
     * @return the Node target
     */
    protected Node findNewPath()
    {
        Node[] neighbours = this.getCandidates();
        ArrayList<Node> preferable = this.crossReference( neighbours );
        
        if ( preferable.isEmpty() ) { return neighbours[Message.randGen
                .nextInt( neighbours.length )]; }
        return preferable.get( Message.randGen.nextInt( preferable.size() ) );
        
    }
    
    
    /**
     * String information about the Message.
     * 
     * @return String information
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Message [ timeToLive=" + this.timeToLive + ", currentPosition="
                + this.currentPosition + " ]";
    }
    
    /**
     * Get the position of the Node at which the Message is
     * 
     * @return Position of current Node
     */
    public Point getPoint()
    {
        return this.currentPosition.getPoint();
    }
    
}
