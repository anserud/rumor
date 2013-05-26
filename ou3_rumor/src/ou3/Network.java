
package ou3;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


public class Network
{
    
    private int                    gridSize;
    private int                    queryTimer;
    private Node[][]               network;
    private ArrayList<Message>     messages;
    private ArrayList<Message> queuedMessages;
    private ArrayList<Node>        queryNodes;
    private Random                 randGen;
    
    public Network( int size, int queryNodeNumber, int queryTime )
    {
        this.gridSize = size;
        this.network = new Node[size][size];
        this.messages = new ArrayList<Message>();
        this.queuedMessages = new ArrayList<Message>();
        this.queryTimer = queryTime;
        this.queryNodes = new ArrayList<Node>();
        
        this.randGen = new Random();
        
        this.generateNodes();
        this.generateNeighbours();
        this.assignQueryNodes( queryNodeNumber );
    }
    
    private void generateNodes()
    {
        for ( int x = 0 ; x < this.gridSize ; x++ )
        {
            for ( int y = 0 ; y < this.gridSize ; y++ )
            {
                this.network[x][y] = new Node( x, y );
            }
        }
    }
    
    private void generateNeighbours() 
    {
        int nodes;
        Node[] neighbours;
        int i;
        for ( int x = 0 ; x < this.gridSize ; x++ )
        {
            for ( int y = 0 ; y < this.gridSize ; y++ )
            {
                nodes = 8;
                if ( x == 0 || x == ( this.gridSize - 1 ) ) nodes -= 3;
                if ( y == 0 || y == ( this.gridSize - 1 ) ) nodes -= 3;
                if ( nodes == 2 ) nodes++;
                neighbours = new Node[nodes];
                i = 0;
                
                if ( x != 0 && y != 0 )
                    neighbours[i++] = this.network[x - 1][y - 1];
                if ( x != 0 ) neighbours[i++] = this.network[x - 1][y];
                if ( x != 0 && y != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x - 1][y + 1];
                
                if ( x != ( this.gridSize - 1 ) && y != 0 )
                    neighbours[i++] = this.network[x + 1][y - 1];
                if ( x != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x + 1][y];
                if ( x != ( this.gridSize - 1 ) && y != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x + 1][y + 1];
                
                if ( y != 0 ) neighbours[i++] = this.network[x][y - 1];
                if ( y != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x][y + 1];
                
                this.network[x][y].updateNeighbours( neighbours );
            }
        }
    }
    
    private void assignQueryNodes( int queryNodeNumber )
    {
        int i = 0;
        while ( i < queryNodeNumber )
        {
            int rX = this.randGen.nextInt( this.gridSize );
            int rY = this.randGen.nextInt( this.gridSize );
            if ( !this.queryNodes.contains( this.network[rX][rY] ) )
            {
                this.queryNodes.add( this.network[rX][rY] );
                i++;
            }
        }
    }
    
    public void timeStep( int time )
    {
        generateEvents( time );
        
        generateQuery( time );
        
        handleMessages();
        
        activateNodes();
        
    }

    /**
     * 
     */
    private void activateNodes()
    {
        for ( Node[] row : this.network )
        {
            for ( Node n : row )
            {
                n.setActive( true );
            }
        }
    }

    /**
     * 
     */
    private void handleMessages()
    {
        // Setup Message handling:
        ArrayList<Message> toQueue = new ArrayList<Message>();
        ArrayList<Message> toRemoveM = new ArrayList<Message>();
        ArrayList<Message> toRemoveQ = new ArrayList<Message>();
        boolean wasBusy;
        boolean survives;
        
        // For every active Message in messages:
        for ( Message msg : this.messages )
        {
            
            // Process:
            msg.processMessage();
            
            // Update:
            survives = msg.stepUpdate();
            if ( !survives )
            {
                // Remove if it didn't survive:
                toRemoveM.add( msg );
                continue;
            }
            
            // Send if possible, else add to the queue:
            wasBusy = !msg.sendMessage();
            if ( wasBusy ) toQueue.add( msg );
        }
        
        // For every Message in the queuedMessages
        for ( Message msg : this.queuedMessages )
        {
            
            // Update
            survives = msg.stepUpdate();
            if ( !survives )
            {
                // Remove if it didn't survive:
                toRemoveQ.add( msg );
                continue;
            }
            
            // If possible, let the Node accept the Message:
            if ( msg.currentPosition.getActive() )
            {
                msg.currentPosition.setActive( false );
                this.messages.add( msg );
                toRemoveQ.add( msg );
            }
        }
        
        // Move from messages to queuedMessages:
        this.queuedMessages.addAll( toQueue );
        this.messages.removeAll( toQueue );
        
        // Remove Messages that perished:
        this.messages.removeAll( toRemoveM );
        this.queuedMessages.removeAll( toRemoveQ );
    }

    /**
     * @param time
     */
    private void generateQuery( int time )
    {
        if ( ( time % this.queryTimer == 0 ) && ( EventID.currentID() != 0 ) )
        {
            int target;
            for ( Node qNode : this.queryNodes )
            {
                target = this.randGen.nextInt( EventID.currentID() );
                this.messages
                        .add( qNode.generateQuery( new EventID( target ) ) );
            }
        }
    }

    /**
     * @param time
     */
    private void generateEvents( int time )
    {
        for ( Node[] row : this.network )
        {
            for ( Node n : row )
            {
                AgentMessage am = n.generateEvent( time );
                if ( am != null ) this.messages.add( am );
            }
        }
    }
    
    public void printMessage( Collection<Message> com )
    {
        for ( Message msg : com )
        {
            System.out.println( msg );
        }
    }
}
