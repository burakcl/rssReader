package tr.burak.rss.model;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import com.sun.xml.internal.txw2.Document;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by burakcl on 4/1/17.
 */
public class feedMesaj {
    String category, title, time, description, link, image, guid;

    public String getCategory(){ return category;}
    public String getTitle(){
        return title;
    }
    public String getTime(){
        return time;
    }
    public String getDescription(){
        return description;
    }
    public String getLink(){
        return link;
    }
    public String getImage(){
        return image;
    }
    public String getGuid(){
        return guid;
    }

    public void setCategory(String category){
        this.category = category;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setLink(String link){
        this.link = link;
    }
    public void setImage(String image){
        this.image = image;
    }
    public void setGuid(String guid){
        this.guid = guid;
    }

    @Override
    public String toString(){
        String bilgi = "{\n\"id\": "+guid+",\n\"category\": \""+category+"\",\n\"title\": \""+title
                +"\",\n\"time\": \""+time+"\",\n\"aciklama\": \""+description
                +"\",\n\"link\": \""+link+"\",\n\"resim\": \""+image
                +"\"\n}";
        try {
            File kategori = new File("./Hürriyet/"+category+"/"+title);
            if (!kategori.exists()){
                kategori.mkdirs();
            }
            File dosya = new File("./Hürriyet/"+category+"/"+title+"/"+title+".txt");
            File resim = new File("./Hürriyet/"+category+"/"+title+"/"+image.substring(43));
            if (!dosya.exists()){
                    dosya.createNewFile();
            }
            URL url = new URL(image);
            FileWriter yazici = new FileWriter(dosya, false);
            BufferedWriter byazici = new BufferedWriter(yazici);
            byazici.write(bilgi);
            byazici.close();

            BufferedImage bresim = ImageIO.read(url);
            ImageIO.write(bresim,"jpg", resim);
        }catch (IOException e){
            e.printStackTrace();
        }
        return bilgi;
    }
}