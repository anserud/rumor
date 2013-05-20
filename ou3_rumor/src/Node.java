
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Class Node.
 * @author Alexander Anserud
 */
public class Node
{
   
   /** The event_ p. */
   private long event_P;
   
   /** The agent_ p. */
   private long agent_P;
   
   /** The id. */
   private int id;
   
   /** The neighbour list. */
   private LinkedList<Node> neighbourList;
   
   /** The event list. */
   private LinkedList<Event> eventList;
   
   /** The routing table. */
   private LinkedList<Event> routingTable;
   
   /** The message queue. */
   private LinkedBlockingQueue<Message> messageQueue;
   
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
	public Node(int id,long event_P, long agent_P){
	   this.id = id;
	   this.event_P 	= event_P;
	   this.agent_P 	= agent_P;
	   neighbourList 	= new LinkedList<Node>();
	   eventList 		= new LinkedList<Event>();
	   messageQueue 	= new LinkedBlockingQueue<Message>();
	   //routingTable 	= new RouteTable();
	   
   }
   
   /**
    * Adds the neighbour.
    * @author Alexander Anserud
    * @param n the n
    */
   public void addNeighbour(Node n)
   {
	   neighbourList.add(n);
   }
   
   /**
    * Process message.
    * @author Alexander Anserud 
    * @param message the message
    */
   public void ProcessMessage(Message message)
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
   }
   
   /**
    * Send message.
    * @author Alexander Anserud
    * @param message the message
    */
   private void sendMessage(Message message) {
       try {
           messageQueue.put(message);
       } catch (InterruptedException e) {
           return;
       }
   }
   
   /**
    * Try create agent.
    *  @author Alexander Anserud
    */
   private void tryCreateAgent(){
	   float chance  = new Random().nextFloat();
	   if(chance <= agent_P)
	   {
		   // Create agent
	   }
	   
	   
   }
   
   /**
    * Try create event.
    *  @author Alexander Anserud
    */
   private void tryCreateEvent()
   {
	   float chance  = new Random().nextFloat();
	   if(chance <= event_P)
	   {
		   // Create event
	   }  
   }
}
