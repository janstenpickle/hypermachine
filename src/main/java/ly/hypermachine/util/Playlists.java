package ly.hypermachine.util;

import ly.hypermachine.pojos.Track;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 14/04/2013
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class Playlists {

    Parser parser;

    public static <K,V> SortedSet<Map.Entry<K,V>>
    entriesSortedByValues(Map<K,V> map, final Comparator<? super V> comp) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(

        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public Playlists(Parser parser) {
        this.parser = parser;
    }

    public Map<String, Track> combine(List<String> playlists) throws IOException {
        TreeMap<String, Track> allTracks = new TreeMap<String, Track>();
        for (Iterator<String> playListIter = playlists.iterator(); playListIter.hasNext(); ) {
            String playlist = playListIter.next();
            Map<String, Track> tracks = parser.parseHtml(Parser.getHtml(Parser.HYPEM_URL + "/" + playlist));

            allTracks.putAll(tracks);
        }
        return allTracks;
    }

    public Map<String, Track> getTracks(String playlist) throws IOException {
        try {
           return parser.parseHtml(Parser.getHtml(Parser.HYPEM_URL + "/" + playlist));
        } catch (NullPointerException e) {

            return null;
        }

    }
}
