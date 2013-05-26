
package ou3;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


/**
 * The class responisble for keeping the network of Node and control their
 * behaviour. Generates this network, the queryNodes and every Node s neighbour.
 * Also controls the Message s during each timeStep.
 * 
 * @see #network
 * @see #queryNodes
 * @see #messages
 * @see #queuedMessages
 * @see #Network(String fileName)
 * @see #generateNodes()
 * @see #assignQueryNodes()
 * @see #makeNeighbours()
 * @see #timeStep()
 * @see Node
 * @see Message
 * @see java.util.ArrayList
 * @see java.util.Random
 * @see java.util.Collection
 */
public class Network
{
    
    // The configuration data:
    public Configuration       config;
    
    // Mabe remove these?
    private int                gridSize;
    private int                queryTimer;
    
    // The Node-array network:
    private Node[][]           network;
    
    // The Messages, live and queued:
    private ArrayList<Message> messages;
    private ArrayList<Message> queuedMessages;
    
    // The Nodes' responisble for sending queries:
    private Node[]             queryNodes;
    
    // Random number genreator neccesary:
    private Random             randGen;
    
    /**
     * The Network constructor; taking a xml- Configuration - file name as
     * parameter.
     * 
     * @param fileName
     *            String with name of Configuration -xml-file
     * @see generateNodes()
     * @see assignQueryNodes()
     * @see makeNeighbours()
     */
    public Network( String fileName )
    {
        // Get the config Configuration:
        config = new Configuration( fileName );
        
        Node.setAgentProbability( config.getAgent_P() );
        Node.setEventProbability( config.getEvent_P() );
        
        AgentMessage.setLifeLength( config.getAgent_TTL() );
        QueryMessage.setLifeLength( config.getQuery_TTL() );
        QueryMessage.setExpected( config.getQuery_TTL() * 8 );
        
        // Maybe remove?
        gridSize = config.getGrid_size();
        queryTimer = config.getQueryStep();
        
        // Setup data-structures:
        network = new Node[gridSize][gridSize];
        queryNodes = new Node[config.getQueryNodes()];
        messages = new ArrayList<Message>();
        queuedMessages = new ArrayList<Message>();
        
        randGen = new Random();
        
        // Setup Network:
        generateNodes();
        assignQueryNodes();
        makeNeighbours();
        
    }
    
    /**
     * Generates Node s for the network array, putting new ones at every
     * location.
     * <p>
     * The int gridSize must correspond to the size of the square array.
     * 
     * @see Node
     * @see Node#Node( whatever?
     */
    private void generateNodes()
    {
        // Create every Node, with position x, y :
        for ( int x = 0 ; x < this.gridSize ; x++ )
        {
            for ( int y = 0 ; y < this.gridSize ; y++ )
            {
                this.network[x][y] = new Node( x, y );
            }
        }
    }
    
    /**
     * Assign queryNodeNumber of Node s to be respnsible for generating
     * QueryMessage s.
     * 
     * @see #queryNodes
     * @see Node
     * @see QueryMessage
     * @see java.util.Random
     * 
     */
    private void assignQueryNodes()
    {
        // Assign queryNodeNumber of Nodes:
        int i = 0;
        while ( i < queryNodes.length )
        {
            
            // Get random position:
            int rX = this.randGen.nextInt( this.gridSize );
            int rY = this.randGen.nextInt( this.gridSize );
            
            // If the position is old, don't add it:
            if ( !this.arrayContains( this.queryNodes, this.network[rX][rY] ) )
            {
                this.queryNodes[i] = this.network[rX][rY];
                i++;
            }
        }
    }
    
    /**
     * Check whether an array of type <T> contains the value val. Return true if
     * so, and false otherwise.
     * 
     * @param arry
     *            the array of type T
     * @param val
     *            the T value to look for
     * @return retVal boolean over whether arry contains val
     */
    private <T> boolean arrayContains( T[] arry, T val )
    {
        boolean retVal = false;
        for ( T v : arry )
        {
            if ( v != null && v.equals( val ) ) retVal = true;
        }
        return retVal;
    }
    
    /**
     * Generate the arrays of neighbours for every Node in the network.
     * <p>
     * Only nodes one rank away ( within 15 units if there's 10 units between
     * every row and column ) will be considered neighbours of the Node.
     * 
     * @see #network
     * @see Node
     * @see Node#updateNeighbours(Node[] neighbours )
     */
    private void makeNeighbours()
    {
        int nodes;
        Node[] neighbours;
        int i;
        
        // For every Node in the network:
        for ( int x = 0 ; x < this.gridSize ; x++ )
        {
            for ( int y = 0 ; y < this.gridSize ; y++ )
            {
                
                // Count the number of neighbours:
                nodes = 8;
                if ( x == 0 || x == ( this.gridSize - 1 ) ) nodes -= 3;
                if ( y == 0 || y == ( this.gridSize - 1 ) ) nodes -= 3;
                if ( nodes == 2 ) nodes++;
                
                // Prepare array of correct size:
                neighbours = new Node[nodes];
                i = 0;
                
                // Keep adding the correct nodes:
                
                // To the left:
                if ( x != 0 && y != 0 )
                    neighbours[i++] = this.network[x - 1][y - 1];
                if ( x != 0 ) neighbours[i++] = this.network[x - 1][y];
                if ( x != 0 && y != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x - 1][y + 1];
                
                // To the right:
                if ( x != ( this.gridSize - 1 ) && y != 0 )
                    neighbours[i++] = this.network[x + 1][y - 1];
                if ( x != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x + 1][y];
                if ( x != ( this.gridSize - 1 ) && y != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x + 1][y + 1];
                
                // Above and below:
                if ( y != 0 ) neighbours[i++] = this.network[x][y - 1];
                if ( y != ( this.gridSize - 1 ) )
                    neighbours[i++] = this.network[x][y + 1];
                
                // Update the Node with these neighbours:
                this.network[x][y].updateNeighbours( neighbours );
            }
        }
    }
    
    /**
     * Take timeStep time, updating the network.
     * <p>
     * Start by generating Events and AgentMessages. Then generate QueryMessages
     * at the QueryNodes if it's time for that, determined in the Configuration.
     * Then update Messages. And finaly activate all Node s for the next step.
     * <p>
     * The Message update will follow the formula: For each active Message in
     * messages: process, stepUpdate, and Send. if it didn't survive the
     * stepUpdate, mark for removal. if it couldn't send, mark for queueing. For
     * each Message in queue queuedMessages: stepUpdate and then attempt send.
     * if it didn't survive the stepUpdate, mark for removal. if it did send,
     * mark as active. For each Message marked for removal; drop reference. For
     * each Message to change state, add to correct list and remove from other.
     * 
     * @param time
     *            the current timeStep
     * @see #network
     * @see #queryNodes
     * @see #messages
     * @see #queuedMessages
     * @see Node
     * @see Node#generateEvent(int time )
     * @see Node#generateQuery(EventID target )
     * @see Message
     * @see Message#processMessage()
     * @see Message#stepUpdate()
     * @see Message#sendMessage()
     */
    public void timeStep( int time )
    {
        
        generateEvents( time );
        
        generateQueries( time );
        
        handleMessages();
        
        activateNodes();
        
        // printMessage(this.messages);
        // System.out.println(time+" : "+
        // this.messages.size()+" "+this.queuedMessages.size());
    }
    
    /**
     * 
     */
    private void activateNodes()
    {
        // Activate all Node s for next timeStep:
        for ( Node[] row : this.network )
        {
            for ( Node n : row )
            {
                n.setActive( true );
            }
        }
    }
    
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
    
    private void generateQueries( int time )
    {
        // If it is time for it:
        if ( ( time % this.queryTimer == 0 ) && ( EventID.currentID() != 0 ) )
        {
            int target;
            
            // Generate a new QueryMessage at every Node in queryNodes:
            for ( Node qNode : this.queryNodes )
            {
                
                // Determine target:
                target = this.randGen.nextInt( EventID.currentID() );
                
                // Add Message:
                this.messages
                        .add( qNode.generateQuery( new EventID( target ) ) );
            }
        }
    }
    
    private void generateEvents( int time )
    {
        // For every Node generate Event:
        
        for ( Node[] row : this.network )
        {
            for ( Node n : row )
            {
                
                if ( n.tryCreateEvent( time ) )
                {
                    if ( n.tryCreateAgent() )
                    {
                        this.messages.add( new AgentMessage( n ) );
                    }
                }
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
    
    @Override
    public String toString()
    {
//        return "Network:" + "\ngrid_size: " + config.getGrid_size()
//                + "\nnumNodes: " + config.getNumNodes() + "\ndistanceNodes: "
//                + config.getDistanceNodes() + "\nrangeNodes: "
//                + config.getRangeNodes() + "\nqueryNodes: "
//                + config.getQueryNodes() + "\nqueryStep: "
//                + config.getQueryStep() + "\nevent_P: " + config.getEvent_P()
//                + "\nagent_P: " + config.getAgent_P() + "\nagent_TTL: "
//                + config.getAgent_TTL() + "\nquery_TTL: "
//                + config.getQuery_TTL();
       
       String s = ""; 
       for( int y =20; y<30;y++)
       {
           for( int x=0; x<50; x++)
           {
               s = s+" "+messagesAt(x,y);
           }
           s = s+"\n";
       }
       return s;
    }
    
    private int messagesAt( int x, int y)
    {
        int nr =0;
        Position pos= new Position(x,y);
        for( Message m: this.messages)
        {
            if( m.getPosition().equals(pos))
            {
                nr++;
            }
        }
        for( Message m: this.queuedMessages)
        {
            if( m.getPosition().equals(pos))
            {
                nr++;
            }
        }
        return nr;
    }
    
    public String printQueryNodes()
    {
        String s = "";
        for ( Node n : this.queryNodes )
        {
            s = s + ", " + n.toString();
        }
        return s;
    }
}
