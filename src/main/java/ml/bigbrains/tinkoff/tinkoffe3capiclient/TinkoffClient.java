package ml.bigbrains.tinkoff.tinkoffe3capiclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.tinkoff.tinkoffe3capiclient.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.CONTENT_TYPE;
import ru.CryptoPro.JCP.JCP;
import ru.tinkoff.crypto.mapi.CryptoMapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Request;

@Slf4j
public class TinkoffClient {

    private String baseUrl;

    public TinkoffClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void signed(SignedRequest signedRequest, String keyStoreInstanceName, String keyName, String x509SerialNumber)
    {
        CryptoMapi crypto = new CryptoMapi();
        String data = crypto.concatValues(signedRequest.getMapForSign());

        byte[] digestData = null;
        byte[] signature = null;
        try {
            digestData = crypto.calcDigest(JCP.GOST_DIGEST_2012_256_NAME, data.getBytes(Charset.forName("UTF-8")));
            KeyStore store = KeyStore.getInstance(keyStoreInstanceName);
            store.load(null, null);
            Key test = store.getKey(keyName, null);
            signature = crypto.calcSignature(JCP.GOST_EL_SIGN_NAME, (PrivateKey) test, digestData);

        } catch (Exception e) {
            log.error("Error in calc digest or sign for request",e);
        }
        signedRequest.setDigestValue( Base64.getEncoder().encodeToString(digestData));
        signedRequest.setSignatureValue(Base64.getEncoder().encodeToString( signature));
        signedRequest.setX509SerialNumber(x509SerialNumber);
    }


    public GenericResponse post(String url, SignedRequest request) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            log.debug("Делаем POST запрос в TINKOFF по адресу: "+baseUrl+"/e2c/"+url);
            URIBuilder builder = new URIBuilder(baseUrl).setPath("/e2c/"+url);
            URI uri = builder.build();
            HttpPost httpPost = new HttpPost(uri);
            List<NameValuePair> params = new ArrayList<>();
            for(Map.Entry<String,String> param: request.getAllParams().entrySet())
            {
                params.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            httpPost.setHeader("Content-type","application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            log.debug("URI: "+httpPost.getURI());
            log.debug("HttpEntry: "+httpPost.getEntity());
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String entity = EntityUtils.toString(httpResponse.getEntity());
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.error("Ошибка в ответе на запрос: "+httpResponse.toString());
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(entity, GenericResponse.class);
        }
        catch (URISyntaxException e)
        {
            log.error("Ошибка в построении запроса",e);
            return null;
        }

    }


    public List<GetCardListResponse> postGetCardList(SignedRequest request) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            URIBuilder builder = new URIBuilder(baseUrl).setPath("/e2c/GetCardList");
            URI uri = builder.build();
            HttpPost httpPost = new HttpPost(uri);
            List<NameValuePair> params = new ArrayList<>();
            for(Map.Entry<String,String> param: request.getAllParams().entrySet())
            {
                params.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            httpPost.setHeader("Content-type","application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            log.debug("URI: "+httpPost.getURI());
            log.debug("HttpEntry: "+httpPost.getEntity());
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String entity = EntityUtils.toString(httpResponse.getEntity());
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.error("Ошибка в ответе на запрос: "+httpResponse.toString());
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(entity, mapper.getTypeFactory().constructCollectionType(List.class, GetCardListResponse.class));
        }
        catch (URISyntaxException e)
        {
            log.error("Ошибка в построении запроса",e);
            return null;
        }

    }

    public GetAccountInfoResponse postAccountInfo(SignedRequest request) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            URIBuilder builder = new URIBuilder(baseUrl).setPath("/e2c/"+"GetAccountInfo");
            URI uri = builder.build();
            HttpPost httpPost = new HttpPost(uri);
            List<NameValuePair> params = new ArrayList<>();
            for(Map.Entry<String,String> param: request.getAllParams().entrySet())
            {
                params.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            httpPost.setHeader("Content-type","application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            log.debug("URI: "+httpPost.getURI());
            log.debug("HttpEntry: "+httpPost.getEntity());
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String entity = EntityUtils.toString(httpResponse.getEntity());
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.error("Ошибка в ответе на запрос: "+httpResponse.toString());
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(entity, GetAccountInfoResponse.class);
        }
        catch (URISyntaxException e)
        {
            log.error("Ошибка в построении запроса",e);
            return null;
        }

    }

}
