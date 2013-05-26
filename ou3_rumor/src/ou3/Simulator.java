
package ou3;


public class Simulator
{
    
    private int     numberOfSteps;
    private Network net;
    
    public Simulator( )
    {   
        this.net = new Network( "H:\\workspace\\ou3_rumorrouting\\configuration.xml" );
    }
    
    public void runSimulator()
    {
        for ( int i = 0 ; i < this.numberOfSteps ; i++ )
        {
            this.net.timeStep( i );
        }
        
        System.out.println( QueryMessage.getSucces()+" / "+QueryMessage.getTotal());
    }
    
}
