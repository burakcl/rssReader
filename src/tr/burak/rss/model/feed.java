package tr.burak.rss.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by burakcl on 4/1/17.
 */

public class feed {
    final String title;
    final String link;
    final String description;
    final String date;

    final List<feedMesaj> entries = new ArrayList<feedMesaj>();

    public feed(String title, String link, String description, String date) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
    }
    public List<feedMesaj> getEntries(){
        return entries;
    }
    public String getTitle(){
        return title;
    }
    public String getLink(){
        return link;
    }
    public String getDescription(){
        return description;
    }
    public String getDate(){
        return date;
    }

    @Override
    public String toString(){
            File dizin = new File("./"+title);
            if (!dizin.exists()){
                dizin.mkdir();
            }
        return "{\n\"title\": \""+title+"\",\n\"link\": \""+link
                +"\",\n\"description\": \""+description
                +"\",\n\"date\": \""+date+"\"\n}\n";
    }
}
