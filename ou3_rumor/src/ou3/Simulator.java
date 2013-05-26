
package ou3;


public class Simulator
{
    
    private Network net;
    
    public Simulator( )
    {   
        this.net = new Network( "H:\\git\\ou3_oop\\ou3_rumor\\src\\configuration.xml" );
    }
    
    public void runSimulator()
    {
        for ( int i = 0 ; i < net.config.getMaxSteps() ; i++ )
        {
            this.net.timeStep( i );
            if( i > 9900)
            {
                System.out.println( this.net);
            }
        }
        
        System.out.println( net.printQueryNodes());
        System.out.println( QueryMessage.getSucces()+" / "+QueryMessage.getTotal());
        System.out.println( EventID.currentID());
    }
    
}
