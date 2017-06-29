import tr.burak.rss.read.rssFeedParser;
import tr.burak.rss.model.feed;
import tr.burak.rss.model.feedMesaj;


public class Main {

    public static void main(String[] args) {
        rssFeedParser parser = new rssFeedParser("http://www.hurriyet.com.tr/rss/gundem");
        feed feed = parser.readFeed();
        System.out.println(feed);
        for (feedMesaj mesaj:feed.getEntries()){
            System.out.println(mesaj);
        }
    }
}
