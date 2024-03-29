package ml.bigbrains.tinkoff.tinkoffe3capiclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.tinkoff.tinkoffe3capiclient.model.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.CryptoPro.JCP.JCP;
import ru.tinkoff.crypto.mapi.CryptoMapi;
import ru.tinkoff.crypto.mapi.RsaCryptoMapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;


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

    public void signedRSA(SignedRequest signedRequest, String publicKeyBase64, String x509SerialNumber)
    {
        RsaCryptoMapi crypto = new RsaCryptoMapi();
        String data = crypto.concatValues(signedRequest.getMapForSign());

        byte[] digestData = null;
        byte[] signature = null;
        try {
            final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(publicKeyBase64));
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
            digestData = crypto.calcDigest(data.getBytes(StandardCharsets.UTF_8));
            signature = crypto.calcSignature(privateKey, digestData);

        } catch (Exception e) {
            log.error("Error in calc RSA digest or sign for request",e);
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
