package ou3;


import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


/**
 * The Class Configuration. Reading an xml-file and storing the information
 * necessary to create a Network.
 * 
 * @see DefaultHandler
 */
public class Configuration extends DefaultHandler
{
    
    /** The grid_size. */
    private int   grid_size;
    
    /** The num nodes. */
    private int   numNodes;
    
    /** The distance nodes. */
    private int   distanceNodes;
    
    /** The range nodes. */
    private int   rangeNodes;
    
    /** The query nodes. */
    private int   queryNodes;
    
    /** The query step. */
    private int   queryStep;
    
    /** The event_ p. */
    private float event_P;
    
    /** The agent_ p. */
    private float agent_P;
    
    /** The agent_ ttl. */
    private int   agent_TTL;
    
    /** The query_ ttl. */
    private int   query_TTL;
    
    /** The max steps. */
    public int    maxSteps;
    
    /**
     * Gets the max steps.
     *
     * @return the max steps
     */
    public int getMaxSteps()
    {
        return maxSteps;
    }
    
    /**
     * Gets the grid_size.
     *
     * @return the grid_size
     */
    public int getGrid_size()
    {
        return grid_size;
    }
    
    /**
     * Gets the num nodes.
     *
     * @return the num nodes
     */
    public int getNumNodes()
    {
        return numNodes;
    }
    
    /**
     * Gets the distance nodes.
     *
     * @return the distance nodes
     */
    public int getDistanceNodes()
    {
        return distanceNodes;
    }
    
    /**
     * Gets the range nodes.
     *
     * @return the range nodes
     */
    public int getRangeNodes()
    {
        return rangeNodes;
    }
    
    /**
     * Gets the query nodes.
     *
     * @return the query nodes
     */
    public int getQueryNodes()
    {
        return queryNodes;
    }
    
    /**
     * Gets the query step.
     *
     * @return the query step
     */
    public int getQueryStep()
    {
        return queryStep;
    }
    
    /**
     * Gets the event_ p.
     *
     * @return the event_ p
     */
    public float getEvent_P()
    {
        return event_P;
    }
    
    /**
     * Gets the agent_ p.
     *
     * @return the agent_ p
     */
    public float getAgent_P()
    {
        return agent_P;
    }
    
    /**
     * Gets the agent_ ttl.
     *
     * @return the agent_ ttl
     */
    public int getAgent_TTL()
    {
        return agent_TTL;
    }
    
    /**
     * Gets the query_ ttl.
     *
     * @return the query_ ttl
     */
    public int getQuery_TTL()
    {
        return query_TTL;
    }
    
    /**
     * Instantiates a new configuration.
     *
     * @param fileName the file name
     */
    public Configuration( String fileName )
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        try
        {
            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File( fileName ), this );
            
        } catch ( Throwable t )
        {
            System.out.println( "Could not load configuration from: "
                    + fileName );
            t.printStackTrace();
            System.exit( 0 );
        }
    }
    
    // Initialized in constructor by saxParser.parse(new File(fileName), this);
    /**
     * xml-reading.
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String localName,
            String qName, Attributes atts )
    {
        /*System.out.println( "Loading configuration file: "
                + atts.getValue( "name" ) + ".." );*/
        this.maxSteps = Integer.parseInt( atts.getValue( "maxSteps" ) );
        this.grid_size = Integer.parseInt( atts.getValue( "grid_size" ) );
        this.numNodes = Integer.parseInt( atts.getValue( "numNodes" ) );
        this.distanceNodes = Integer
                .parseInt( atts.getValue( "distanceNodes" ) );
        this.queryStep = Integer.parseInt( atts.getValue( "queryStep" ) );
        this.rangeNodes = Integer.parseInt( atts.getValue( "rangeNodes" ) );
        this.queryNodes = Integer.parseInt( atts.getValue( "queryNodes" ) );
        this.agent_TTL = Integer.parseInt( atts.getValue( "agent_TTL" ) );
        this.query_TTL = Integer.parseInt( atts.getValue( "query_TTL" ) );
        this.event_P = Float.parseFloat( atts.getValue( "event_P" ) );
        this.agent_P = Float.parseFloat( atts.getValue( "agent_P" ) );
    }
}
