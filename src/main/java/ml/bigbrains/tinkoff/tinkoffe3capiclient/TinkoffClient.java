package ml.bigbrains.tinkoff.tinkoffe3capiclient;

import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.tinkoff.tinkoffe3capiclient.model.SignedRequest;
import ru.CryptoPro.JCP.JCP;
import ru.tinkoff.crypto.mapi.CryptoMapi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Map;

@Slf4j
public class TinkoffClient {

    public void signed(SignedRequest signedRequest, String keyStoreInstanceName, String keyName, String x509SerialNumber)
    {
        CryptoMapi crypto = new CryptoMapi();
        String data = crypto.concatValues(signedRequest.getMapForSign());

        byte[] digestData = null;
        byte[] signature = null;
        try {
            digestData = crypto.calcDigest(JCP.GOST_DIGEST_2012_512_NAME, data.getBytes(Charset.forName("UTF-8")));
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


}
