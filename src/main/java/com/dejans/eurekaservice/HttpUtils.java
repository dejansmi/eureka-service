package com.dejans.eurekaservice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.ws.rs.QueryParam;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;


public class HttpUtils {
    private String url = new String();
    private String urlForAnalyze;
    private String scheme = new String();
    private String host = new String();
    private String port = new String();
    private String path = new String();

    private String query = new String();
    private String currentParamQueryName;
    private String currentParamQueryValue;
    private int queryPosition = 0;

    // this constructor is used when prepare request
    public HttpUtils() {

    }

    // this constructor is used when should analize request 
    public HttpUtils(String url, String queryString) {
        urlForAnalyze = url;
        if (urlForAnalyze.startsWith("http")) {
            // this is http protocol
            scheme = "http";
            urlForAnalyze = urlForAnalyze.replaceFirst("http://", "");
            int i = urlForAnalyze.indexOf(":");
            if (i > -1) {
                // port exist but first extract host
                host = urlForAnalyze.substring(0, i);
                urlForAnalyze = urlForAnalyze.substring(i + 1);
                // and after that port
                i = urlForAnalyze.indexOf("/");
                if (i > -1) {
                    port = urlForAnalyze.substring(0, i);
                    urlForAnalyze = urlForAnalyze.substring(i);
                } else {
                    // url don't have path so port is rest of url
                    port = urlForAnalyze;
                    urlForAnalyze = "";
                }

            }
            // rest of urlForAnalyze is path (can be empty string ("")) 
            path = urlForAnalyze;

            if (queryString != null && queryString != "") {
                query = queryString;
            }
        }
    }

    public HttpUtils setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public String getScheme() {
        return scheme;
    }

    public HttpUtils setHost(String host) {
        this.host = host;
        return this;
    }

    public String getHost() {
        return host;
    }

    public HttpUtils setPort(String port) {
        this.port = port;
        return this;
    }

    public String getPort() {
        return port;
    }

    public HttpUtils setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPath() {
        return path;
    }

    public HttpUtils addParamToQuery(String param, String value) throws UnsupportedEncodingException {
        if (query == null)
            query = new String();

        if (query != "")
            query += "&";
        query += param;
        if (value == null)
            return this;
        if (value != "") {
            query += "=" + URLEncoder.encode(value, "UTF-8");
        }
        return this;
    }

    public String getParamName() throws UnsupportedEncodingException {
        int i;
        String param;
        if (queryPosition >= query.length()) return null;
        i = query.indexOf('&',queryPosition);
        if (i > -1) {
            param = query.substring(queryPosition,i);
        } else {
            param = query.substring(queryPosition);
        }
        int j = param.indexOf('=');
        if (j> -1) {
            currentParamQueryName = param.substring(0,j);
            currentParamQueryValue = URLDecoder.decode(URLDecoder.decode(param.substring(j+1),"UTF-8"),"UTF-8");
        } else {
            currentParamQueryName = param;
            currentParamQueryValue = "";            
        }
        queryPosition = i + 1;
        return currentParamQueryName;
    }

    public String getParamValue(String nameParam){
        if (currentParamQueryName.equals(nameParam)) {
            return currentParamQueryValue; 
        } else {
            return "";
        }
    }

    // this method will be used if you want to clear existing query and start buld a new
    public HttpUtils newParamsToQuery() {
        query = "";
        return this;
    }

    public String getUrl() {
        if (scheme == null || !scheme.equals("")) {
            scheme = "http";
        }
        if (scheme.toLowerCase().equals("http")) {
            url = scheme + "://";
            url += host;
            if (port != null && !port.equals("")) {
                url += ":" + port;
            }
            if (path != null && !path.equals("")) {
                url += path;
            }
            if (query != null && !query.equals("")) {
                url += "?" + query;
            }
            return url;
        }
        return null;
    }
}