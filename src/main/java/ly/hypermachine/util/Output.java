package ly.hypermachine.util;

import ly.hypermachine.pojos.Track;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import sun.misc.IOUtils;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 09/05/2013
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class Output {

    public static void outputFile (Map<String, Track> tracks) throws IOException, InterruptedException {

        HttpClient httpClient = new DefaultHttpClient();


        for (Iterator<Track> i = tracks.values().iterator(); i.hasNext();){
            Track track = i.next();

     //       System.out.println(track);
             track.getSource();

            Thread.sleep(1000);
          /*
            HttpGet httpGet = new HttpGet(track.getSource());
            HttpResponse response = httpClient.execute(httpGet);

            InputStream in = response.getEntity().getContent();

            OutputStream os = new FileOutputStream("/tmp/hypemachine/"+track.getArtist()+" - "+track.getSong()+".mp3");

            byte[] buffer = new byte[256];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) != -1) {
                System.out.println(bytesRead);
                os.write(buffer, 0, bytesRead);
            }

            in.close();
            os.close();

            break;
            */
        }
    }
}
