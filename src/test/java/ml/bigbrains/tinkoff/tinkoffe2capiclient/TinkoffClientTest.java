package ml.bigbrains.tinkoff.tinkoffe2capiclient;

import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.tinkoff.tinkoffe3capiclient.TinkoffClient;
import ml.bigbrains.tinkoff.tinkoffe3capiclient.model.AddCustomerRequest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.CryptoPro.JCP.JCP;
import ru.tinkoff.crypto.mapi.CryptoMapi;

import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;


@RunWith(JUnit4.class)
@Slf4j
public class TinkoffClientTest {

    @Test
    public void test()
    {
        log.info("TEST");
    }

    @Test
    public void signedRequest()
    {
        TinkoffClient tinkoffClient = new TinkoffClient();
        AddCustomerRequest addCustomerRequest = new AddCustomerRequest();
        addCustomerRequest.setTerminalKey("123123");
        addCustomerRequest.setCustomerKey("321321");
        addCustomerRequest.setEmail("info@example.com");
        addCustomerRequest.setPhone("+79001000110");
        addCustomerRequest.setIp("127.0.0.1");

        tinkoffClient.signed(addCustomerRequest,"HDImageStore", "testKey","401437532001722600264206825014014998552699056");
        log.debug("Signed request: "+addCustomerRequest);
        try {
            byte [] signature = Base64.getDecoder().decode(addCustomerRequest.getSignatureValue());
            byte [] data = Base64.getDecoder().decode(addCustomerRequest.getDigestValue());
            KeyStore store = KeyStore.getInstance("HDImageStore");
            store.load(null, null);
            Signature instance = Signature.getInstance(JCP.GOST_EL_SIGN_NAME, JCP.PROVIDER_NAME);
            instance.initVerify(store.getCertificate("testKey"));
            instance.update(data);
            Assert.assertTrue(instance.verify(signature));
        }
        catch (Exception e)
        {
            log.error("Ошибка при проверки подписи",e);
            Assert.fail();
        }
    }


}
