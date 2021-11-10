package ru.tinkoff.crypto.mapi;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.CryptoPro.JCP.JCP;

/**
 * @author a.bloshchetsov
 */
public class CryptoMapiTest {

    @Test
    public void testConcatValues() {
        CryptoMapi crypto = new CryptoMapi();
        Map<String, String> data = new HashMap<>();
        data.put("C", "1");
        data.put("A", "2");
        data.put("B", "3");

        String str = crypto.concatValues(data);
        Assert.assertEquals(str, "231");
    }

    @Test
    public void testCalcDigest() throws NoSuchAlgorithmException {
        CryptoMapi crypto = new CryptoMapi();
        byte[] digestData = crypto.calcDigest(JCP.GOST_DIGEST_NAME, "TestString".getBytes(Charset.forName("UTF-8")));
        Assert.assertEquals(Base64.getEncoder().encodeToString(digestData), "Qcic98Kqbxx4jG7rAZ000B9d1FJ1H/nDGHCJVqqQ/7k=");
    }

    @Test
    public void testCalcDigest2012_256() throws NoSuchAlgorithmException {
        CryptoMapi crypto = new CryptoMapi();
        byte[] digestData = crypto.calcDigest(JCP.GOST_DIGEST_2012_256_NAME, "TestString".getBytes(Charset.forName("UTF-8")));
        Assert.assertEquals(Base64.getEncoder().encodeToString(digestData), "sZiuwRhSq0XUFFx5y4fV5V3CWyqQo35ZtaAWrtGTJMk=");
    }

    @Test
    public void testCalcDigest2012_512() throws NoSuchAlgorithmException {
        CryptoMapi crypto = new CryptoMapi();
        byte[] digestData = crypto.calcDigest(JCP.GOST_DIGEST_2012_512_NAME, "TestString".getBytes(Charset.forName("UTF-8")));
        Assert.assertEquals(Base64.getEncoder().encodeToString(digestData), "V1XPdyK/vv7dqeLW/UExtqVeHdRqrddre8A82g27qyf7M/O5iLfZtiu7SCFGFI68A3O6ZrcJO+SWggU60DExHw==");
    }

    @Ignore
    @Test
    public void testCalcSignature() throws Exception {
        KeyStore store = KeyStore.getInstance("HDImageStore");
        store.load(null, null);
        Key test = store.getKey("test", null);
        CryptoMapi crypto = new CryptoMapi();
        byte[] signatureData = "someData".getBytes();
        byte[] bytes = crypto.calcSignature(JCP.GOST_EL_SIGN_NAME, (PrivateKey) test, signatureData);
        Signature instance = Signature.getInstance(JCP.GOST_EL_SIGN_NAME, JCP.PROVIDER_NAME);
        instance.initVerify(store.getCertificate("test"));
        instance.update(signatureData);
        Assert.assertTrue(instance.verify(bytes));
    }

    @Ignore
    @Test
    public void testCalcSignature2012_256() throws Exception {
        KeyStore store = KeyStore.getInstance("HDImageStore");
        store.load(null, null);
        Key test = store.getKey("test2012-256", null);
        CryptoMapi crypto = new CryptoMapi();
        byte[] signatureData = "someData".getBytes();
        byte[] bytes = crypto.calcSignature(JCP.GOST_EL_2012_256_NAME, (PrivateKey) test, signatureData);
        Signature instance = Signature.getInstance(JCP.GOST_EL_2012_256_NAME, JCP.PROVIDER_NAME);
        instance.initVerify(store.getCertificate("test2012-256"));
        instance.update(signatureData);
        Assert.assertTrue(instance.verify(bytes));
    }

    @Ignore
    @Test
    public void testCalcSignature2012_512() throws Exception {
        KeyStore store = KeyStore.getInstance("HDImageStore");
        store.load(null, null);
        Key test = store.getKey("test2012-512", null);
        CryptoMapi crypto = new CryptoMapi();
        byte[] signatureData = "someData".getBytes();
        byte[] bytes = crypto.calcSignature(JCP.GOST_EL_2012_512_NAME, (PrivateKey) test, signatureData);
        Signature instance = Signature.getInstance(JCP.GOST_EL_2012_512_NAME, JCP.PROVIDER_NAME);
        instance.initVerify(store.getCertificate("test2012-512"));
        instance.update(signatureData);
        Assert.assertTrue(instance.verify(bytes));
    }

}
