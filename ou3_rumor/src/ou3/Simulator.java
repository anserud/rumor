package ou3;



/**
 * The Class Simulator. Creating the Network and running
 * timeSteps.
 */
public class Simulator
{
    
    /** The Network. */
    private Network net;
    
    /**
     * Instantiates a new simulator.
     */
    public Simulator( )
    {   
        this.net = new Network( "H:\\git\\ou3_0101\\ou3_rumor\\src\\configuration.xml" );
    }
    
    /**
     * Run the timeSteps.
     */
    public void runSimulator()
    {
        for ( int i = 0 ; i < net.config.getMaxSteps() ; i++ )
        {
            this.net.timeStep( i );
            
//            if( i > 9900)
//            {
//                System.out.println( this.net);
            
//            }
        }
        
// System.out.println( net.printQueryNodes());
        System.out.println( QueryMessage.getSucces()+" / "+QueryMessage.getTotal());
      //        System.out.println( EventID.currentID());  
    }
    
}
