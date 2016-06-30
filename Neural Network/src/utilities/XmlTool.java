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
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import sigmoidNeuron.Network;


//http://stackoverflow.com/questions/7373567/java-how-to-read-and-write-xml-files

public class XmlTool {
	
	public static void main(String[] args) {
		Network n;
		String xml = IdxReader.promptForFile(true, "choose xml file");
    	try {
			n=XmlTool.readXML(xml);
			XmlTool.saveNetworkToXML(n,xml);
		} catch (NetworkException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static Network readXML() throws NetworkException{
		
		String xml = IdxReader.promptForFile(true, "choose xml file");
		return readXML(xml);
	}

	public static Network readXML(String xml) throws NetworkException {
		Network network;
		int[] sizes;
		int input_size;
		
		
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try {
            // use the factory to take an instance of the document builder
        	DocumentBuilder db = dbf.newDocumentBuilder();
            
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);
            dom.getDocumentElement().normalize();
            Element networkE = dom.getDocumentElement();
            
            if (networkE.getTagName() != "network" || !networkE.hasChildNodes()) {
            	throw new NetworkException("Couldn't load network: bad xml format - no network or no network children");
            }
            
            	
            Node networkSettingsN = networkE.getElementsByTagName("network_settings").item(0);
            Node networkValuesN = networkE.getElementsByTagName("network_values").item(0);
        	
        	if (networkSettingsN == null || 
        			networkSettingsN.getNodeType() != Node.ELEMENT_NODE){
        		throw new NetworkException("Couldn't load network: bad xml format - no network settings/ not element");
        	}

        	Element networkSettingsE = (Element) networkSettingsN;

			input_size=Integer.valueOf(networkSettingsE.getElementsByTagName("input_size").item(0).getTextContent());
			int sizel=Integer.valueOf(networkSettingsE.getElementsByTagName("sizes_length").item(0).getTextContent());
			
			sizes=new int[sizel];
			
			Node sizesN=(networkSettingsE.getElementsByTagName("sizes").item(0));
			
			if (sizesN.getNodeType() != Node.ELEMENT_NODE) {
        		throw new NetworkException("Couldn't load network: bad xml format - sizes not element");
        	}
			
			Element sizesE=(Element) sizesN;
			
			for(int i=0;i<sizel; i++){
				sizes[i]=Integer.valueOf(sizesE.getElementsByTagName("size"+String.valueOf(i)).item(0).getTextContent());
			}
            
            network=new Network(input_size,sizes);
            
            if (networkValuesN == null || 
        			networkValuesN.getNodeType() != Node.ELEMENT_NODE){
        		return network;
        	}
            
            Element networkValuesE = (Element) networkValuesN;
            
            Node layerN;
            Element layerE;
            
            Node neuronN;
            Element neuronE;
            
            double[] weights;
            
            //first layer
            
            layerN=(networkValuesE.getElementsByTagName("layer0").item(0));
            
            if (layerN == null || 
        			layerN.getNodeType() != Node.ELEMENT_NODE){
        		return network;
        	}
            
            layerE = (Element) layerN;
            
            for(int n = 0; n<sizes[0]; n++){
            	neuronN=layerE.getElementsByTagName("neuron"+String.valueOf(n)).item(0);
            	if (neuronN == null || 
            			neuronN.getNodeType() != Node.ELEMENT_NODE){
            		return network;
            	}
            	neuronE=(Element) neuronN;
            	
            	weights=new double[input_size];
            	for(int w=0; w<input_size;w++){
            		weights[w]=Double.valueOf(neuronE.getElementsByTagName("weight"+String.valueOf(w)).item(0).getTextContent());
            	}
            	
            	network.setNeuronWeights(0, n, weights);
            	network.setNeuronBias(0, n, Double.valueOf(neuronE.getElementsByTagName("bias").item(0).getTextContent()));
            }
            
            //other layers
            for(int l =1; l< sizes.length; l++){
                layerN=(networkValuesE.getElementsByTagName("layer"+String.valueOf(l)).item(0));
                
                if (layerN == null || 
            			layerN.getNodeType() != Node.ELEMENT_NODE){
            		return network;
            	}
                
                layerE = (Element) layerN;
                
                for(int n = 0; n<sizes[l]; n++){
                	neuronN=layerE.getElementsByTagName("neuron"+String.valueOf(n)).item(0);
                	if (neuronN == null || 
                			neuronN.getNodeType() != Node.ELEMENT_NODE){
                		return network;
                	}
                	neuronE=(Element) neuronN;
                	
                	weights=new double[sizes[l-1]];
                	for(int w=0; w<sizes[l-1];w++){
                		weights[w]=Double.valueOf(neuronE.getElementsByTagName("weight"+String.valueOf(w)).item(0).getTextContent());
                	}
                	
                	network.setNeuronWeights(l, n, weights);
                	network.setNeuronBias(l, n, Double.valueOf(neuronE.getElementsByTagName("bias").item(0).getTextContent()));
                }
            }

            return network;
            

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        throw new NetworkException("Couldn't load network: End");
    }
	
	public static void saveNetworkToXML(Network n){
		String xml = IdxReader.promptForFile(true, "choose xml file");
		saveNetworkToXML(n, xml);
	}
	
	public static void saveNetworkToXML(Network network, String xml) {
		Document dom;
	    Element e = null;
	    Element s = null;
	    
	    Element e1 = null;
	    Element s1 = null;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();

	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element networkE = dom.createElement("network");
	        
	        
	        Element netSet = dom.createElement("network_settings");
	        Element netVal = dom.createElement("network_values");

	        // create network settings individual elements and place them
	        e = dom.createElement("input_size");
	        e.appendChild(dom.createTextNode(String.valueOf(network.getInputSize())));
	        netSet.appendChild(e);
	        
	        int[] sizes = network.getSizes();
	        int sizel=sizes.length;
	        int input_size=network.getInputSize();

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
	        
	        
	     // create network values - layers-neurons-weights/bias
	        e = dom.createElement("layer0");
	        
	        double[] weights;
	        
	        //save weights and biases for first layer
	        for(int n =0; n< sizes[0]; n++){ //for each neuron
	        	e1 = dom.createElement("neuron"+String.valueOf(n));
	        	weights=network.getNeuronWeights(0, n);
	        	
	        	//append weights to neuron
	        	for (int w=0; w<input_size; w++){
	        		s1 = dom.createElement("weight"+String.valueOf(w));
		        	s1.appendChild(dom.createTextNode(String.valueOf(weights[w])));
		        	e1.appendChild(s1);
	        	}
	        	//append bias to neuron
	        	s1 = dom.createElement("bias");
	        	s1.appendChild(dom.createTextNode(String.valueOf(network.getNeuronBias(0, n))));
	        	e1.appendChild(s1);
	        	
	        	e.appendChild(e1);
	        }
	        
	        netVal.appendChild(e);
	        
	      //save weights and biases for other layers
	        for(int l=1; l <sizel; l++){
	        
		        e = dom.createElement("layer"+String.valueOf(l));
		        
		        for(int n =0; n< sizes[l]; n++){ //for each neuron
		        	e1 = dom.createElement("neuron"+String.valueOf(n));
		        	weights=network.getNeuronWeights(l, n);
		        	
		        	//append weights to neuron
		        	for (int w=0; w<sizes[l-1]; w++){
		        		s1 = dom.createElement("weight"+String.valueOf(w));
			        	s1.appendChild(dom.createTextNode(String.valueOf(weights[w])));
			        	e1.appendChild(s1);
		        	}
		        	//append bias to neuron
		        	s1 = dom.createElement("bias");
		        	s1.appendChild(dom.createTextNode(String.valueOf(network.getNeuronBias(l, n))));
		        	e1.appendChild(s1);
		        	
		        	e.appendChild(e1);
		        }
		        
		        netVal.appendChild(e);
	        }
	        
	        networkE.appendChild(netSet);
	        networkE.appendChild(netVal);

	        dom.appendChild(networkE);

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
