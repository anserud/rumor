
package ou3;


import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;


public abstract class Message implements Comparable<Message>
{
    
    protected int               timeToLive;
    protected TreeSet<Node>     past;
    protected Node              currentPosition;
    static private final Random randGen = new Random();
    
    public Message( Node position )
    {
        this.past = new TreeSet<Node>();
        this.currentPosition = position;
    }
    
    protected abstract Node choseTarget();
    
    public abstract void processMessage();
    
    public abstract boolean stepUpdate();
    
    protected abstract void sendingUpdate();
    
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
    
    public void setTimeToLive( int t )
    {
        this.timeToLive = t;
    }
    
    private Node[] getCandidates()
    {
        return this.currentPosition.getNeighbours();
    }
    
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
    
    protected Node findNewPath()
    {
        Node[] neighbours = this.getCandidates();
        ArrayList<Node> preferable = this.crossReference( neighbours );
        
        if ( preferable.isEmpty() ) { return neighbours[Message.randGen
                .nextInt( neighbours.length )]; }
        return preferable.get( Message.randGen.nextInt( preferable.size() ) );
        
    }
    
    @Override
    public int compareTo( Message other )
    {
        return this.timeToLive - other.timeToLive;
    }
    
    @Override
    public String toString()
    {
        return "Message [ timeToLive=" + this.timeToLive + ", currentPosition="
                + this.currentPosition + " ]";
    }
    
}
