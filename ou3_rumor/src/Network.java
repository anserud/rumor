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
        return "Network:" + "\ngrid_size: " + config.grid_size + "\nnumNodes: "
                + config.numNodes + "\ndistanceNodes: " + config.distanceNodes
                + "\nrangeNodes: " + config.rangeNodes + "\nqueryNodes: "
                + config.queryNodes + "\nqueryStep: " + config.queryStep
                + "\nevent_P: " + config.event_P + "\nagent_P: "
                + config.agent_P + "\nagent_TTL: " + config.agent_TTL
                + "\nquery_TTL: " + config.query_TTL;
    }
}
