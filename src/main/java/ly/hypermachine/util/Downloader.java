package ly.hypermachine.util;

import ly.hypermachine.pojos.Track;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.client.utils.URIUtils;
import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v1.ID3V1_0Tag;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 04/08/2013
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public class Downloader {

    private String path;

    public Downloader (String path) {
        this.path = path;
    }

    public String download(Track track) throws IOException, URISyntaxException, ID3Exception {
        File file = new File(path+"/"+track.getArtist()+" - "+track.getSong()+".mp3");

        URL url = new URL( track.getSource() );
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        url = uri.toURL();


        System.out.println(url);
        URLConnection connection = url.openConnection();

        InputStream input = connection.getInputStream();
        byte[] buffer = new byte[4096];
        int n = - 1;

        OutputStream output = new FileOutputStream( file );
        while ( (n = input.read(buffer)) != -1)
        {
            if (n > 0)
            {
                output.write(buffer, 0, n);
            }
        }
        output.close();

        MediaFile oMediaFile = new MP3File(file);

        // create a v1.0 tag object, and set some values
        ID3V1_0Tag oID3V1_0Tag = new ID3V1_0Tag();
        oID3V1_0Tag.setArtist(track.getArtist());
        oID3V1_0Tag.setComment("Downloaded from "+track.getSource());
        oID3V1_0Tag.setTitle(track.getSong());
        oID3V1_0Tag.setYear("1999");

        // set this v1.0 tag in the media file object
        oMediaFile.setID3Tag(oID3V1_0Tag);

        oMediaFile.sync();

        return null;
    }
}
