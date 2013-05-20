import java.util.ArrayList;


public class Network
{
    
    private Configuration   config;
    private ArrayList<Node> nodes;
    
    public Network( String fileName )
    {
        config = new Configuration( fileName );
    }
    
    private void generateNodes()
    {
    }
    
    private void assignQueryNodes()
    {
    }
    
    private void makeNeighbours()
    {
    }
    
    private void sendAgents()
    {
    }
    
    private void sendQueries()
    {
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
