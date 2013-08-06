package ly.hypermachine;

import ly.hypermachine.pojos.Cookie;
import ly.hypermachine.pojos.Track;
import ly.hypermachine.util.Downloader;
import ly.hypermachine.util.Output;
import ly.hypermachine.util.Parser;
import ly.hypermachine.util.Playlists;
import org.blinkenlights.jid3.ID3Exception;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 14/04/2013
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException, URISyntaxException, ID3Exception {

        Playlists playlists = new Playlists(new Parser(new Cookie()));

        List<String> lists = new ArrayList<String>();
        lists.add("janstenpickle/1");

        Map<String, Track> tracks = playlists.combine(lists);
        System.out.println("-------------------------------------");

        Downloader downloader = new Downloader("/Users/chris/Downloads/music");

        for (Iterator<Track> i = tracks.values().iterator(); i.hasNext();){
            Track track = i.next();
            String url = track.getSource();
            System.out.println(track);
            System.out.println(url);
            downloader.download(track);
        }

    }
}
