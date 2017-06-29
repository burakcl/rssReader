package tr.burak.rss.read;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import tr.burak.rss.model.feed;
import tr.burak.rss.model.feedMesaj;

/**
 * Created by burakcl on 4/1/17.
 */
public class rssFeedParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String IMAGE =  "{http://search.yahoo.com/mrss/}content";
    static final String CATEGORY = "category";
    static final String LINK = "link";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";

    final URL url;

    public rssFeedParser(String feedUrl){
        try {
            this.url = new URL(feedUrl);
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }
    public feed readFeed(){
        feed feed = null;
        try {
            boolean isFeedHeader = true;
            String description = "";
            String title = "";
            String link = "";
            String date = "";
            String guid = "";
            String image = "";
            String category = "";
            //ilk xml inputfactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            //yeni event okuyucu
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            //xml dosyasını oku
            while (eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()){
                    String localPart = event.asStartElement().getName().toString();
                    switch (localPart){
                        case ITEM:
                            if (isFeedHeader){
                                isFeedHeader = false;
                                feed = new feed(title,link,description,date);
                            }
                            event = eventReader.nextEvent();
                            break;
                        case CATEGORY:
                            category = getCharacterData(event, eventReader);
                            break;
                        case IMAGE:
                            image = getCharacterData(event, eventReader);
                            break;
                        case TITLE:
                            title = getCharacterData(event, eventReader);
                            break;
                        case DESCRIPTION:
                            description =  getCharacterData(event, eventReader);
                            break;
                        case GUID:case LINK:
                            link = getCharacterData(event, eventReader);
                            break;
                        case PUB_DATE:
                            date = getCharacterData(event, eventReader);
                            String id = date.substring(17);
                            id = id.replace("+","");
                            id = id.replace(":","");
                            id = id.replace(" ","");
                            guid = id;
                            break;
                    }
                }else if (event.isEndElement()){
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)){
                        feedMesaj mesaj = new feedMesaj();
                        mesaj.setDescription(description);
                        mesaj.setGuid(guid);
                        mesaj.setLink(link);
                        mesaj.setTitle(title);
                        mesaj.setTime(date);
                        mesaj.setImage(image);
                        mesaj.setCategory(category);
                        feed.getEntries().add(mesaj);
                        event = eventReader.nextEvent();
                        continue;
                    }
                }
            }
        }catch (XMLStreamException e){
            throw new RuntimeException(e);
        }
        return feed;
    }

        private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException{
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters){
            result = event.asCharacters().getData();
        }else if (event.isStartElement()){
            result = event.toString().substring(74);
            result = result.replace("'","");
            result = result.replace("file","http");
            result = result.replace(">","");
        }
        return result;
        }

        private InputStream read(){
        try {
            return url.openStream();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        }
}
