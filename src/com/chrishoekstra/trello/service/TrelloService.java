package com.chrishoekstra.trello.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.chrishoekstra.trello.vo.AddCardVO;
import com.chrishoekstra.trello.vo.AllBoardsResultVO;
import com.chrishoekstra.trello.vo.BoardResultVO;
import com.chrishoekstra.trello.vo.CardVO;
import com.chrishoekstra.trello.vo.LoginResultsVO;
import com.chrishoekstra.trello.vo.NotificationVO;
import com.chrishoekstra.trello.vo.NotificationsResultVO;

public class TrelloService {
    private static final Object USER_AGENT_STRING = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:5.0.1) Gecko/20100101 Firefox/5.0.1";
    
    private static final String TRELLO_URL = "https://trello.com/";
    private static final String ME_BOARDS = "data/me/boards";
    private static final String DATA_BOARD = "data/board";
    private static final String DATA = "data";
    private static final String API_APP = "api/app";
    private static final String API_CARD = "api/card";
    
    private static final Object METHOD_LOGIN = "login";



    
    public JsonFactory mJsonFactory;
    public ObjectMapper mObjectMapper;
    public HttpContext mContext;
    public CookieStore mCookieStore;
    
    public TrelloService() {
        mJsonFactory = new JsonFactory();
        mObjectMapper = new ObjectMapper()
            .configure(Feature.AUTO_DETECT_FIELDS, true)
            .configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
            .configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, false)
            .configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        
        mCookieStore = new BasicCookieStore();
        mContext = new BasicHttpContext();
        mContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);
    }

    public String getFilteredToken() {
        ArrayList<Cookie> cookies = new ArrayList<Cookie>(mCookieStore.getCookies());
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                return token.replaceFirst("%2F", "/");
            }
        }
        
        return "";
    }
    
    public boolean login(String username, String password) {
        boolean results = false;
        HttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(TRELLO_URL + API_APP);
        
        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        
        try {
            data.put("user", username);
            data.put("password", password);
            json.put("data", data);
            json.put("token", "");
            json.put("method", METHOD_LOGIN);
            
            StringEntity parametersString = new StringEntity(json.toString());
            parametersString.setContentEncoding("UTF-8");
            parametersString.setContentType("application/json");
            
            httpPost.setEntity(parametersString);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);
            
            HttpResponse response = httpClient.execute(httpPost, mContext);
            
            if (response != null) {
                LoginResultsVO loginResults = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), LoginResultsVO.class);
                results = true;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }      
        
        return results;
    }
    
    public AllBoardsResultVO getBoardResults() {
        AllBoardsResultVO results = null;
        HttpGet httpGet = new HttpGet(TRELLO_URL + ME_BOARDS);
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                results = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), AllBoardsResultVO.class);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }      
        
        return results;
    }

    public BoardResultVO getBoard(String boardId) {
        BoardResultVO results = null;
        HttpGet httpGet = new HttpGet(TRELLO_URL + DATA_BOARD + "/" + boardId + "/current");
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                results = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), BoardResultVO.class);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return results;
    }

    public CardVO addCard(AddCardVO addCardVO) {
        CardVO result = null;
        HttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(TRELLO_URL + API_CARD);
        
        try {
            StringWriter writer = new StringWriter();
            mObjectMapper.writeValue(writer, addCardVO);
            
            StringEntity parametersString = new StringEntity(writer.toString());
            parametersString.setContentEncoding("UTF-8");
            parametersString.setContentType("application/json");
            
            httpPost.setEntity(parametersString);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);
            
            HttpResponse response = httpClient.execute(httpPost, mContext);
            
            if (response != null) {
                result = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), CardVO.class);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public NotificationsResultVO getNotifications(String username, Integer counts) {
        NotificationsResultVO results = null;
        String url = TRELLO_URL + DATA + "/" + username + "/notifications";
        
        if (counts != null) {
            url += "?skip=" + counts;
        }
        
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                results = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), NotificationsResultVO.class);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    public class CustomSSLSocketFactory extends SSLSocketFactory {

        private SSLContext sslContext = SSLContext.getInstance("TLS");

        public CustomSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            };

            sslContext.init(null, new TrustManager[] {tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

    }
    
    public HttpClient getHttpClient() {

        DefaultHttpClient client = null;

        try {

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            
            // Setting up parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "utf-8");
            params.setBooleanParameter("http.protocol.expect-continue", true);

            // Setting timeout
            HttpConnectionParams.setConnectionTimeout(params, 100000);
            HttpConnectionParams.setSoTimeout(params, 100000);

            // Registering schemes for both HTTP and HTTPS
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            // Creating thread safe client connection manager
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            // Creating HTTP client
            client = new DefaultHttpClient(ccm, params);

        } catch (Exception e) {
            client = new DefaultHttpClient();
        }

        return client;
    }
}