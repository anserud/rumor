import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Class Node.
 * @author Alexander Anserud
 */
public class Node implements Comparable<Node> // Om vi vill ha vissa datatyper måste vi kunna jämföra noder?
{
   
   /** The event_ p. */
   private long event_P; // Dessa vet jag inte ?
   
   /** The agent_ p. */
   private long agent_P; // Dessa vet jag inte ?
   
   /** Whether the Node is active */
   private boolean active;
   
   /** The id. */
   private Position position; // Bytte ut ID mot position, är nog vättigare.
   
   /** The neighbour array. */
   private Node[] neighbours; // Behöver inte vara en lista.
   
   /** The event list. */
   private LinkedList<Event> eventList;
   
   /** The routing table. */
   private LinkedList<Event> routingTable; // Prolly inte LinkedList! För komplex contains()!
   
   /** The message queue. */
   private LinkedBlockingQueue<Message> messageQueue; // Blocking? vi kan ha meddelanden nån annanstanns!
   
   /** A random generator */ //Bättre att ha en för alla, än o göra nya överallt, risk för samma seed !
   private static final Random     randGen = new Random();
   
   /**
    * The class describing the node position 
    * 
    * 
    */
   private class Position implements Comparable<Position>
    {
        
        private int x;
        private int y;
        
        public Position( int x, int y )
        {
            this.x = x;
            this.y = y;
        }
        
        public int getX()
        {
            return x;
        }
        
        public void setX( int x )
        {
            this.x = x;
        }
        
        public int getY()
        {
            return y;
        }
        
        public void setY( int y )
        {
            this.y = y;
        }
        
        @Override
        public int compareTo( Position other )	// Not so sure about this
        {
            int deltaX;
            deltaX = this.x - other.getX();
            if( deltaX != 0 ) return deltaX;
            return (   this.y - other.getY() ) ;
        }
        
        @Override
        public boolean equals( Object other )
        {
            if( other == this ) return true;
            if( other == null ) return false;
            if( this.getClass() != other.getClass()) return false;
            return ( (this.x==other.getX())&&(this.y==other.getY()));
        }
    }
   
   /**
    * Gets the routing table.
    * @author Alexander Anserud
    * @return the routing table
    */
   public LinkedList<Event> getRoutingTable() {
	   return routingTable;
   }
   
   /**
    * Gets the event list.
    * @author Alexander Anserud
    * @return the event list
    */
   public LinkedList<Event> getEventList() {
	   return eventList;
   }

   
	/**
	 * Instantiates a new node.
	 * @author Alexander Anserud
	 * @param id the id
	 * @param event_P the event_ p
	 * @param agent_P the agent_ p
	 */
   public Node(int x, int y /*long event_P, long agent_P*/){ // Var ska vi ha P-variablerna?
	   this.position = new Position(x,y);
	 //  this.event_P 	= event_P;
	//   this.agent_P 	= agent_P;
	   eventList 		= new LinkedList<Event>();
	   messageQueue 	= new LinkedBlockingQueue<Message>();
	   
	   //routingTable 	= new RouteTable(); //Vad ska vi göra för typ?
   }
   
   /**
    * Adds the neighbours.
    * @author Alexander Anserud
    * @param nextDoor the neighbours
    */
   public void updateNeighbours( Node[] nextDoor)
   {
	   neighbours= nextDoor;
   }
   
  /* public static void setEventProbability( int eventProbability )
    {
        Node.eventProbability = eventProbability;
    }
    
    public static void setAgentProbability( int agentProbability )
    {
        Node.agentProbability = agentProbability;
    }*/
    
    public Position getPosition()
    {
        return this.position;
    }
   
   /**
    * Process message.
    * @author Alexander Anserud 
    * @param message the message
    */
/*   public void ProcessMessage(Message message)
   {
	   // Dercrement time to live
	   message.decrement_TTL();
	   // Is the message dead?
	   if(message.get_TTL() != 0)
	   {
		   return;
	   }
	   // Is it a query message
	   if(message instanceof QueryMessage) { 
		   // Declare tmp variables for event
		   int tmpEventID;
		   int tmpEventDistance;
		   boolean isFound = false;
		   // Cast it to query message so we can get its attribues
		   QueryMessage queryMessage = ((QueryMessage)message);
		   // First look in route table for a trail of the event
		   for (Route r : getRoutingTable())
		   {
			   tmpEventID = r.getEventID;
			   tmpEventDistance = r.getDistance;
			   // Did we found a trail for the event ?
			   if(r.getEventID == queryMessage.getEventId())
			   {
				   isFound = true;
				   // Are we at the event node?
				   if(tmpDistance == 0)
				   {
					   // We found the node orgin of the event
					   // Send it back to query node
				   }
				   else
				   {
					   // Forward it to the node and follow trail
					   // To the event
				   }
				   // No need to search further, break loop
				   break;
			   }
		   }
		   // If the route table did not have a trail
		   if(isFound == false)
		   {
			   // Forward message to random neigbor
		   }
	   }
	   // Is it a agent message
	   else if(message instanceof AgentMessage)
	   {
		   // update nodes routing table and agents (Sync)
		   // Forward message
	   }
   }*/ // Vart ska vi he denna egentligen? En typs beteende ska bestämmas av typen osv... Varje nod kommer inte ha meddelanden och så, vi måste kolla över detta tillsammans inom gruppen asap!
   
   /**
    * Send message.
    * @author Alexander Anserud
    * @param message the message
    */
  /* private void sendMessage(Message message) {
       try {
           messageQueue.put(message);
       } catch (InterruptedException e) {
           return;
       }
   }*/ // Samma här, vad ska vi göra?
   
   @Override
    public int compareTo( Node other )
    {
        return this.position.compare( other.position );
    }
   
   public Route getRoute( EventID id )
    {
        return this.routingTable.get( id );
    }
    
    public boolean getActive()
    {
        return this.active;
    }
   
   public void setActive( boolean active )
    {
        this.active = active;
    }
    
    public Event getEvent( EventID id );
    
    public Node[] getNeighbours()
    {
        return this.neighbours;
    }
   
 
 // Andra versioner av generate event osv:
 /**
    * Try create agent.
    *  @author Alexander Anserud
    */
  /* private void tryCreateAgent(){
	   // A float between 1 and 0
	   float chance  = new Random().nextFloat();
	   if(chance <= agent_P)
	   {
		   // Create agent
	   }
	   
	   
   }*/
   
   /**
    * Try create event.
    *  @author Alexander Anserud
    */
 /*  private void tryCreateEvent()
   {
	   // A float between 1 and 0
	   float chance  = new Random().nextFloat();
	   if(chance <= event_P)
	   {
		   // Create event
	   }  
   }*/
   
   // Hur ska vi göra saker?
   public AgentMessage generateEvent( int time )
    {
        if ( Node.randGen.nextInt( eventProbability ) == 0 )
        {
            Event e = new Event( this, time );
            this.eventList.add( e );
            
            Route r = new Route( e.getID(), 0, this );
            this.routingTable.put( r.getEvent(), r );
            
            if ( Node.randGen.nextInt( agentProbability ) == 0 )
                return new AgentMessage( this, r );
        }
        return null;
    }
    
    // Egentligen?
    public QueryMessage generateQuery( EventID id )
    {
        
        return new QueryMessage( id, this );
    }
   
}
