import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Configuration extends DefaultHandler{
	
	public int grid_size;
	public int numNodes;
	public int distanceNodes;
	public int rangeNodes;
	public int queryNodes;
	public int queryStep;
	public float event_P;
	public float agent_P;
	public int agent_TTL;
	public int query_TTL;
	
	public Configuration(String fileName){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(fileName), this);

		} catch (Throwable t) {
			System.out.println("Could not load configuration");
			t.printStackTrace();
			System.exit(0);
		}
	}
	// Initialized in constructor by saxParser.parse(new File(fileName), this);
	public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) 
	{
			System.out.println("Loading configuration file: " + atts.getValue("name") + "..");
			this.grid_size = Integer.parseInt(atts.getValue("grid_size"));
			this.numNodes =  Integer.parseInt(atts.getValue("numNodes"));
			this.distanceNodes =  Integer.parseInt(atts.getValue("distanceNodes"));
			this.rangeNodes =  Integer.parseInt(atts.getValue("rangeNodes"));
			this.queryNodes =  Integer.parseInt(atts.getValue("queryNodes"));
			this.agent_TTL =  Integer.parseInt(atts.getValue("agent_TTL"));
			this.query_TTL =  Integer.parseInt(atts.getValue("query_TTL"));
			this.event_P =  Float.parseFloat(atts.getValue("event_P"));
			this.agent_P =  Float.parseFloat(atts.getValue("event_P"));
	}
}
