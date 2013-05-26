
package ou3;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public abstract class Message 
{
    
    protected int               timeToLive;
    protected HashSet<Node>     past;
    protected Node              currentPosition;
    static private final Random randGen = new Random();
    
    public Message( Node position )
    {
        this.past = new HashSet<Node>(120);
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
    public String toString()
    {
        return "Message [ timeToLive=" + this.timeToLive + ", currentPosition="
                + this.currentPosition + " ]";
    }
    
    public Position getPosition()
    {
        return this.currentPosition.getPosition();
    }
    
}
