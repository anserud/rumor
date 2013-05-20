import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class Configuration extends DefaultHandler
{
    
    private int   grid_size;
    private int   numNodes;
    private int   distanceNodes;
    private int   rangeNodes;
    private int   queryNodes;
    private int   queryStep;
    private float event_P;
    private float agent_P;
    private int   agent_TTL;
    private int   query_TTL;
    
    public int getGrid_size()
    {
        return grid_size;
    }
    
    public int getNumNodes()
    {
        return numNodes;
    }
    
    public int getDistanceNodes()
    {
        return distanceNodes;
    }
    
    public int getRangeNodes()
    {
        return rangeNodes;
    }
    
    public int getQueryNodes()
    {
        return queryNodes;
    }
    
    public int getQueryStep()
    {
        return queryStep;
    }
    
    public float getEvent_P()
    {
        return event_P;
    }
    
    public float getAgent_P()
    {
        return agent_P;
    }
    
    public int getAgent_TTL()
    {
        return agent_TTL;
    }
    
    public int getQuery_TTL()
    {
        return query_TTL;
    }
    
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
    public void startElement( String namespaceURI, String localName,
            String qName, Attributes atts )
    {
        System.out.println( "Loading configuration file: "
                + atts.getValue( "name" ) + ".." );
        this.grid_size = Integer.parseInt( atts.getValue( "grid_size" ) );
        this.numNodes = Integer.parseInt( atts.getValue( "numNodes" ) );
        this.distanceNodes = Integer
                .parseInt( atts.getValue( "distanceNodes" ) );
        this.rangeNodes = Integer.parseInt( atts.getValue( "rangeNodes" ) );
        this.queryNodes = Integer.parseInt( atts.getValue( "queryNodes" ) );
        this.agent_TTL = Integer.parseInt( atts.getValue( "agent_TTL" ) );
        this.query_TTL = Integer.parseInt( atts.getValue( "query_TTL" ) );
        this.event_P = Float.parseFloat( atts.getValue( "event_P" ) );
        this.agent_P = Float.parseFloat( atts.getValue( "event_P" ) );
    }
}
