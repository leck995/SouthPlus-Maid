package net.southplus.southplusmaid.ui.control;

import java.net.HttpCookie;

public class LocalHttpCookie {
    private String name;  // NAME= ... "$Name" style is reserved
    private String value;       // value of NAME

    private String comment;     // Comment=VALUE ... describes cookie's use
    private String commentURL;  // CommentURL="http URL" ... describes cookie's use
    private boolean discard;  // Discard ... discard cookie unconditionally
    private String domain;      // Domain=VALUE ... domain that sees cookie
    private long maxAge;  // Max-Age=VALUE ... cookies auto-expire
    private String path;        // Path=VALUE ... URLs that see the cookie
    private String portlist;    // Port[="portlist"] ... the port cookie may be returned to
    private boolean secure;     // Secure ... e.g. use SSL
    private boolean httpOnly;   // HttpOnly ... i.e. not accessible to scripts
    private int version;    // Version=1 ... RFC 2965 style

    public LocalHttpCookie() {
    }


    public HttpCookie getHttpCookie(){
        HttpCookie httpCookie=new HttpCookie(name,value);
        httpCookie.setComment(comment);
        httpCookie.setCommentURL(commentURL);
        httpCookie.setDiscard(discard);
        httpCookie.setDomain(domain);
        httpCookie.setMaxAge(maxAge);
        httpCookie.setPath(path);
        httpCookie.setPortlist(portlist);
        httpCookie.setSecure(secure);
        httpCookie.setHttpOnly(httpOnly);
        httpCookie.setVersion(version);
        return httpCookie;
    }







    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentURL() {
        return commentURL;
    }

    public void setCommentURL(String commentURL) {
        this.commentURL = commentURL;
    }

    public boolean isDiscard() {
        return discard;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPortlist() {
        return portlist;
    }

    public void setPortlist(String portlist) {
        this.portlist = portlist;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
