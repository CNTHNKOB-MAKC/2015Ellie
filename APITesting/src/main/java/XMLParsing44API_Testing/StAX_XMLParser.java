package XMLParsing44API_Testing;

//Streaming API for XML

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StAX_XMLParser {
public static void main(String[] args) throws XMLStreamException, IOException {

	URL url = new URL("http://learn2test.net/sdc.xml");

	final String element_01 = "orig-kw";
	final String element_02 = "engine";
	final String element_03 = "kadu-version";
	final String element_04 = "response-time";
	
	final String element_05 = "deals";

	final String element_name_01 = "Key Word: ";
	final String element_name_02 = "Engine: ";
	final String element_name_03 = "Version: ";
	final String element_name_04 = "Response time: ";
	
	final String element_name_05 = "Number of deals: ";

	InputStream in = url.openStream();

	XMLInputFactory factory = XMLInputFactory.newInstance();
	XMLStreamReader reader = factory.createXMLStreamReader(in);
	int eventType = reader.getEventType();

	while (reader.hasNext()) {

	eventType = reader.next();

	if (eventType == XMLStreamReader.START_ELEMENT) {
		if (reader.getLocalName() == element_01) {
		System.out.println(element_name_01 + reader.getElementText());
		}
		if (reader.getLocalName() == element_02) {
		System.out.println(element_name_02 + reader.getElementText());
		}
		if (reader.getLocalName() == element_03) {
		System.out.println(element_name_03 + reader.getElementText());
		}
		if (reader.getLocalName() == element_04) {
		System.out.println(element_name_04 + reader.getElementText());
		}
		if (reader.getLocalName() == element_05) {
		System.out.println(element_name_05 + reader.getAttributeValue(0));
		}
	}
	}
}
}

