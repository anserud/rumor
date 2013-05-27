
package ou3;


import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;


// TODO: Auto-generated Javadoc
/**
 * The Class Message.
 */
public abstract class Message implements Comparable<Message>
{
    
    /** The time to live. */
    protected int               timeToLive;
    
    /** The past. */
    protected TreeSet<Node>     past;
    
    /** The current position. */
    protected Node              currentPosition;
    
    /** The Constant randGen. */
    static private final Random randGen = new Random();
    
    /**
     * Instantiates a new message.
     *
     * @param position the position
     */
    public Message( Node position )
    {
        this.past = new TreeSet<Node>();
        this.currentPosition = position;
    }
    
    /**
     * Chose target.
     *
     * @return the node
     */
    protected abstract Node choseTarget();
    
    /**
     * Process message.
     */
    public abstract void processMessage();
    
    /**
     * Step update.
     *
     * @return true, if successful
     */
    public abstract boolean stepUpdate();
    
    /**
     * Sending update.
     */
    protected abstract void sendingUpdate();
    
    /**
     * Send message.
     *
     * @return true, if successful
     */
    public boolean sendMessage()
    {
        Node target = this.choseTarget();
        this.sendingUpdate();
        this.past.add( currentPosition );
        this.currentPosition = target;
        
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
     * Cross reference.
     *
     * @param neighbours the neighbours
     * @return the array list
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
     * Find new path.
     *
     * @return the node
     */
    protected Node findNewPath()
    {
        Node[] neighbours = this.getCandidates();
        ArrayList<Node> preferable = this.crossReference( neighbours );
        
        if ( preferable.isEmpty() ) { return neighbours[Message.randGen
                .nextInt( neighbours.length )]; }
        return preferable.get( Message.randGen.nextInt( preferable.size() ) );
        
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( Message other )
    {
        return this.timeToLive - other.timeToLive;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Message [ timeToLive=" + this.timeToLive + ", currentPosition="
                + this.currentPosition + " ]";
    }
    
}
