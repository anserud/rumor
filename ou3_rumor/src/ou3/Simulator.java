
package ou3;


public class Simulator
{
    
    private int     numberOfSteps;
    private Network net;
    
    public Simulator( )
    {   
        this.net = new Network( "H:\\git\\ou3\\ou3_rumor\\src\\configuration.xml" );
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
