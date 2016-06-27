package utilities;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sigmoidNeuron.Network;


//http://stackoverflow.com/questions/7373567/java-how-to-read-and-write-xml-files

public class XmlTool {
	
	public static void main(String[] args) {
		Network n;
  
    	try {
			n=XmlTool.readXML();
			XmlTool.saveNetworkToXML(n);
		} catch (NetworkException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public static Network readXML() throws NetworkException {
		Network n;
		int[] sizes;
		int input_size;
		
		String xml = IdxReader.promptForFile(true, "choose xml file");
		
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try {
            // use the factory to take an instance of the document builder
        	DocumentBuilder db = dbf.newDocumentBuilder();
            
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            Element network = dom.getDocumentElement();
            
            if (network.getTagName() == "network" && network.hasChildNodes()) {
            	
            	NodeList networknl = network.getChildNodes();
            	Element networksettings=(Element) networknl.item(1);
            	//Element networkvalues=(Element) networknl.item(2);
            	
            	System.out.println(networksettings.getTagName());
            	//System.out.println(networkvalues.getTagName());
            	
            	if (networksettings.getTagName() != "network_settings"){
            		throw new NetworkException("Couldn't load network: bad xml format - no network by name");
            	}
            }else{
            	throw new NetworkException("Couldn't load network: bad xml format - no network or no network children");
            }
            
            sizes=new int[2];
            sizes[0]=3;
            sizes[1]=3;
            input_size=3;
            
            n=new Network(input_size,sizes);

            return n;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        throw new NetworkException("Couldn't load network: End");
    }
	
	public static void saveNetworkToXML(Network n) {
		
		String xml = IdxReader.promptForFile(true, "choose xml file");
		
		Document dom;
	    Element e = null;
	    Element s = null;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();

	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element network = dom.createElement("network");
	        
	        
	        Element netSet = dom.createElement("network_settings");
	        Element netVal = dom.createElement("network_values");

	        // create data elements and place them under root
	        e = dom.createElement("input_size");
	        e.appendChild(dom.createTextNode(String.valueOf(n.getInputSize())));
	        netSet.appendChild(e);
	        
	        int[] sizes = n.getSizes();
	        int sizel=sizes.length;

	        e = dom.createElement("sizes_length");
	        e.appendChild(dom.createTextNode(String.valueOf(sizel)));
	        netSet.appendChild(e);
	        
	        e = dom.createElement("sizes");
	        for (int i=0; i< sizel; i++){
	        	s = dom.createElement("size"+String.valueOf(i));
	        	s.appendChild(dom.createTextNode(String.valueOf(sizes[i])));
	        	e.appendChild(s);
	        }
	        netSet.appendChild(e);
	        
	        network.appendChild(netSet);
	        network.appendChild(netVal);

	        dom.appendChild(network);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.STANDALONE, "yes");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}
	
}
