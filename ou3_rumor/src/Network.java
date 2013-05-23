import java.util.ArrayList;


public class Network
{
    
    private Configuration   config;
    
    private int                    gridSize;
    private int                    queryTimer;
    private Node[][]               network;
    private ArrayList<Message>     messages;
    private ArrayList<Message>     queuedMessages;
    private Node[]                 queryNodes;
    private Random                 randGen;
    
    public Network( String fileName )
    {
        config = new Configuration( fileName );
        
        gridSize = config.getGrid_size();
        queryTimer = config.getQueryStep();
        
        network = new Node[gridSize][gridSize];
        queryNodes = new Node[ config.getQueryNodes()];
        messages = new ArrayList<Messages>();
        queuedMessages = new ArrayList<MEssages>();
        
        randGen = new Random();
        
        generateNodes();
        assgnQueryNodes();
        makeNeighbours();
        
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
    
    private void assignQueryNodes()
    {
        int i = 0;
        while ( i < queryNodeNumber )
        {
            int rX = this.randGen.nextInt( this.gridSize );
            int rY = this.randGen.nextInt( this.gridSize );
            if ( !this.arrayContains( this.queryNodes, this.network[rX][rY] ) )
            {
                this.queryNodes[i]=this.network[rX][rY];
                i++;
            }
        }
    }
    
    private <T> boolean arrayContains( T[] arry, T val )
    {
        boolean retVal = false;
        for( T v: arry )
        {
            if( v.equals( val )) retVal=true;
        }
        return retVal;
    }
    
    private void makeNeighbours()
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
    
    public void timeStep( int time )
    {
        for ( Node[] row : this.network )
        {
            for ( Node n : row )
            {
                AgentMessage am = n.generateEvent( time );
                if ( am != null ) this.messages.add( am );
            }
        }
        
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
        
        ArrayList<Message> toQueue = new ArrayList<Message>();
        ArrayList<Message> toRemoveM = new ArrayList<Message>();
        ArrayList<Message> toRemoveQ = new ArrayList<Message>();
        boolean wasBusy;
        boolean survives;
        for ( Message msg : this.messages )
        {
            
            msg.processMessage();
            survives = msg.stepUpdate();
            if ( !survives )
            {
                toRemoveM.add( msg );
                continue;
            }
            wasBusy = !msg.sendMessage();
            if ( wasBusy ) toQueue.add( msg );
        }
        
        for ( Message msg : this.queuedMessages )
        {
            survives = msg.stepUpdate();
            if ( !survives )
            {
                toRemoveQ.add( msg );
                continue;
            }
            if ( msg.currentPosition.getActive() )
            {
                msg.currentPosition.setActive( false );
                this.messages.add( msg );
                toRemoveQ.add( msg );
            }
        }
        
        // this.printMessage( this.queuedMessages );
        
        this.queuedMessages.addAll( toQueue );
        this.messages.removeAll( toQueue );
        
        this.messages.removeAll( toRemoveM );
        this.queuedMessages.removeAll( toRemoveQ );
        
        for ( Node[] row : this.network )
        {
            for ( Node n : row )
            {
                n.setActive( true );
            }
        }
        
    }
    
    
    @Override
    public String toString()
    {
        // commit
        return "Network:" + "\ngrid_size: " + config.getGrid_size() + "\nnumNodes: "
                + config.getNumNodes() + "\ndistanceNodes: " + config.getDistanceNodes()
                + "\nrangeNodes: " + config.getRangeNodes() + "\nqueryNodes: "
                + config.getQueryNodes() + "\nqueryStep: " + config.getQueryStep()
                + "\nevent_P: " + config.getEvent_P() + "\nagent_P: "
                + config.getAgent_P() + "\nagent_TTL: " + config.getAgent_TTL()
                + "\nquery_TTL: " + config.getQuery_TTL();
    }
}
