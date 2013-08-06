package ly.hypermachine.pojos;

import com.google.gson.JsonObject;
import ly.hypermachine.util.Parser;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 14/04/2013
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class Track implements Comparable<Track> {

    public static final String SERVE_URL = "http://hypem.com/serve/source";

    private Cookie cookie;
    private String artist;
    private String song;
    private String id;
    private String key;
    private int timestamp;



    public static Track parseTrack(Cookie cookie, JsonObject jsonObject){
        Track track = new Track(cookie, jsonObject.get("artist").getAsString(), jsonObject.get("song").getAsString(), jsonObject.get("id").getAsString(), jsonObject.get("key").getAsString(), jsonObject.get("ts").getAsInt());
        return track;
    }

    public Track(Cookie cookie, String artist, String song, String id, String key, int timestamp){
        this.cookie = cookie;
        this.artist = artist;
        this.song = song;
        this.id = id;
        this.key = key;
        this.timestamp = timestamp;
    }

    public String getArtist() {
        return artist;
    }

    public String getSong() {
        return song;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public String getSource() throws IOException {
        String url = SERVE_URL+"/"+getId()+"/"+getKey();
        System.out.println(url);
        return Parser.getTrackUrl(url, cookie);
    }

    public String toString() {
            return getArtist() + " - " + getSong() + " (id="+getId() + ", key="+getKey()+", timestamp="+getTimestamp()+")";

    }

    public int getTimestamp() {
        return timestamp;
    }


    @Override
    public int compareTo(Track track) {
        return getTimestamp() - track.getTimestamp();
    }
}
