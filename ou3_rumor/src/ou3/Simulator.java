
package ou3;


// TODO: Auto-generated Javadoc
/**
 * The Class Simulator.
 */
public class Simulator
{
    
    /** The net. */
    private Network net;
    
    /**
     * Instantiates a new simulator.
     */
    public Simulator( )
    {   
        this.net = new Network( "C:\\Users\\Alexander\\git\\ou3123\\ou3_rumor\\src\\configuration.xml" );
    }
    
    /**
     * Run simulator.
     */
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
