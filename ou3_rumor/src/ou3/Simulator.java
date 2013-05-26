
package ou3;


public class Simulator
{
    
    private int     numberOfSteps;
    private Network net;
    
    public Simulator( int gridSize, int numberOfSteps, int eventProbability,
            int agentProbability, int queryNodes, int queryTime, int agentTTL,
            int queryTTL)
    {
        Node.setAgentProbability( agentProbability );
        Node.setEventProbability( eventProbability );
        
        AgentMessage.setLifeLength( agentTTL );
        QueryMessage.setLifeLength( queryTTL );
        QueryMessage.setExpected( queryTTL * 8 );
        
        this.numberOfSteps = numberOfSteps;
        this.net = new Network( gridSize, queryNodes, queryTime );
    }
    
    public void runSimulator()
    {
        for ( int i = 0 ; i < this.numberOfSteps ; i++ )
        {
 //           System.out.print(i%100==0?i+"\n":"");
            this.net.timeStep( i );
//            if(i%1000==0)
//            {
//                System.out.println( EventID.currentID() );
//                System.out.println( );
//            }
        }
        
        System.out.println( QueryMessage.getSucces()+" / "+QueryMessage.getTotal());
    }
    
}
