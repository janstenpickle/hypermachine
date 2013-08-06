package ly.hypermachine.pojos;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 14/04/2013
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class Cookie {
    private String cookie;

    public Cookie() {}

    public Cookie (String cookie){
        this.cookie = cookie;
    }

    public synchronized String getCookie() {
        return cookie;
    }

    public synchronized void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
