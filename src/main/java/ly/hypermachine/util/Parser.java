package ly.hypermachine.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ly.hypermachine.pojos.Cookie;
import ly.hypermachine.pojos.Track;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 14/04/2013
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    private static Cookie cookie = new Cookie();

    private static JsonParser jsonParser = new JsonParser();


    public static final String HYPEM_URL = "http://hypem.com";

    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36";

    public Parser(Cookie cookie){
        this.cookie = cookie;
    }

    public static String getTrackUrl(String url, Cookie cookie) throws IOException {
        Header[] headers = new Header[1];
        System.out.println(cookie.getCookie());
        Header header = new BasicHeader("Cookie", cookie.getCookie());
        headers[0] = header;

        String html = getHtml(url, headers);

        System.out.println(html);

      try {
        JsonObject trackInfo = jsonParser.parse(html).getAsJsonObject();

        //  System.out.println(trackInfo.get("url").getAsString()) ;
        return trackInfo.get("url").getAsString();
      } catch (Exception e){
        e.printStackTrace();
      }
        return null;
    }

    public static String getHtml(String url) throws IOException {
        return getHtml(url, null);
    }

    public static String getHtml(String url, Header[] headers) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeaders(headers);
        httpGet.setHeader("User-Agent", USER_AGENT);

        HttpResponse response = httpClient.execute(httpGet);
        Header[] resHeaders = response.getAllHeaders();

        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null)
        {
            str.append(line);
        }
        in.close();

        for (int i = 0; i < resHeaders.length; i++) {
            if (resHeaders[i].getName().equals("Set-Cookie")){
               cookie.setCookie(resHeaders[i].getValue());
            }
        }



        return str.toString();


    }

    public Map<String,Track> parseHtml(String html) throws IOException {

        Map<String,Track> tracks = new TreeMap<String,Track>();

        Document document = Jsoup.parse(html);
        //System.out.println(document);
        Element element = document.getElementById("displayList-data");

        Element yourSite = document.getElementById("your-site");

        Document snippet = Jsoup.parse(yourSite.html());

        String dataJson = "";
        Elements links = snippet.select("a[href]");
        for (Iterator<Element> linksIter = links.iterator(); linksIter.hasNext();){
            Element link = linksIter.next();
            if (link.toString().contains("JSON"))  {
                String linkHref = link.attr("href");
                dataJson = "http://hypem.com" +linkHref;
            }
        }

        System.out.println(getHtml(dataJson));

        JsonElement jsonElement = jsonParser.parse(element.html());

        System.out.println(jsonElement);

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonArray jsonTracks = jsonObject.getAsJsonArray("tracks");

        for (Iterator<JsonElement> tracksIter = jsonTracks.iterator(); tracksIter.hasNext();){
            Track track = Track.parseTrack(cookie, tracksIter.next().getAsJsonObject());
            tracks.put(track.getId(),track);
      //      System.out.println(track);

        }

        return tracks;
    }

}
