import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import ru.tinkoff.crypto.mapi.RsaCryptoMapi;

public class RsaCryptoMapiTest {

    @Test
    public void testConcatValues() {
        RsaCryptoMapi crypto = new RsaCryptoMapi();
        Map<String, String> data = new HashMap<>();
        data.put("C", "1");
        data.put("A", "2");
        data.put("B", "3");

        String str = crypto.concatValues(data);
        Assert.assertEquals(str, "231");
    }

    @Test
    public void testCalcDigest() throws NoSuchAlgorithmException {
        RsaCryptoMapi crypto = new RsaCryptoMapi();
        byte[] digestData = crypto.calcDigest("NOTestCustomer3192.168.40.741573803282696E2C".getBytes(StandardCharsets.UTF_8));
        Assert.assertEquals(Base64.getEncoder().encodeToString(digestData), "03F2HA40d7eFJFHZh9QzTnbZ4g4sTATodlaGTyVI894=");
    }

    @Test
    public void testCalcSignature() throws Exception {
        String prKey = "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCIywyUKeK+1vMg\n" +
                "OeJlZXZIyjOEDb1DQpVDuT2qCPmfGeftj8GfP3nSPcxfanDxKpavMTH+LW0y5/5b\n" +
                "3BdbuvfgBe+hYNd1C7Jtg0quXCulRUZhcH3z7zbPksVjhr0gz0s1QgVcK6vOmSA2\n" +
                "jvJLNMrgnDa/Game31jVg+XBdcgfl6aKmXsTB/bOP7MZVMStUEpQQ3j0cRB/qJkJ\n" +
                "/qtLqgfE4tvn8bs/OFzHIBcZbkkNTcYZnM+leVyxfBRoh8WQr41vlK/NFS6UO5Qg\n" +
                "hlHjb4GVcVx5BZMF8ofz3O5bwBBVQol+u1/erslZn7AzMhCIO3M6K+RXmYpcF7Uo\n" +
                "QZDJvVVhAgMBAAECgf86Pjoc27iviNX74qmCgrDaTT2h/DeZa8AiFb0rqKagF2eo\n" +
                "voyyQVSdL7LU7X0pMfj2nvuqPa1A/4ZNlmhbpj/kIqLlE1apf1F5T218z8HFnLla\n" +
                "w4rdUf0EZHmm5regQKLFClc951o/nPMaR8LiQpgfCmRE+ag+/NnWD1LEDGIpEMk6\n" +
                "cAph/eG7e92URdcvcGKhW7IVRg9KLzC6/1o055PUbtfztNgbLNrc45FoYwwZmoJ6\n" +
                "TUTai7owiqTY/cPwYy1JNLkNWGrriayyHQ60r0TMa1iHPk8Y12E4DMfe1iFzuJC1\n" +
                "fkE2vldRhCoktReGp/9I2OA3AQTIf+dqeHJG43ECgYEAxiOQqDTsgLLJURMq7CxW\n" +
                "jROupxT793ZpWdmu1J8at7bz72yp6tYAOgnOYeb32t3DPoD210Ds0WZvgwyO2erB\n" +
                "Q5m3P1mrCbWIDyjE7prqPF7k/81a5ZyIhFxoJ67bFDWXH+taxVlDyPp5PykW12HA\n" +
                "+fKfUvuyarsMQoFgr/5DGX0CgYEAsL1ofGPnK5lt7ssfdyJfvRLomtFwEKq6mKL0\n" +
                "DDaGbNyWcXLxZNCDYhAfX/Mn221s6gwsb86sD2/+3fMkGsjNIq0KCtqYA12hrrHL\n" +
                "XKlrfss+hLhf51Psasod4LJ9pIwOA5M9daGbBOxnEYuUzJDCDWG/3F6IoU2gJVh+\n" +
                "jy+ukLUCgYBlmsFIcHNC/+uaZrDhEU+E4q3yQiF/Ybped/FeWQRzZx8qDNgJc5oE\n" +
                "KRkrPSymkIfZmnp6IXIPY28VjbAHcYmPp9i/ZLT/spZF58ss7EXnlWXrAxx0mFOt\n" +
                "RFGo39K6R0zq8l7GyqKnAkKAsAplglxaEB/Bhw6UCr0bsYqqVUGFZQKBgHmD0Int\n" +
                "miwU0kujJmAfpxl1Ha4MH3OmHqMBLrAS8Pt58onP0H5TCnHqydeLc7TfGEoW0pqQ\n" +
                "yyXWB7KMEB/GPZwAwu3Re0CdKKPWpA5ygXDsxnAz3+RJhDur+TzbG519mckFe/Ub\n" +
                "hlytOPQMNuMrB/Bxa5tU97WHjHsAzAsganIdAoGAe24+Cx/jAzL9PVv17eZIw1uf\n" +
                "E5nkjMDrs6f7kzAvcKVuwc/0MtgQj6nj5e1jwnDzpQMQclQzIXHUyJZ5eu5yqeGp\n" +
                "CggYIRuupVtoNDcnYaF8KKODTUDmD8072RU/11sBBbFxQqypyFq27r5XK1Q8+FZT\n" +
                "lfBGWTEKpGGHSQ9ERY4=";
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(prKey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        RsaCryptoMapi crypto = new RsaCryptoMapi();
        String digest = "03F2HA40d7eFJFHZh9QzTnbZ4g4sTATodlaGTyVI894=";
        byte[] signatureData = digest.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = crypto.calcSignature(privateKey, signatureData);
        // Check calculated signature value
        Assert.assertEquals(Base64.getEncoder().encodeToString(bytes), "Yaovdno+L3KXlNuTLyq11rC+vPIUvjHYRAb5xy+FAMKLxMOdbKVGQmTRDzUX/JAuf9aYeBAyxKOjBZ1Z3WPDudnCu9R7E/OqVlGssKlURgm0aSuul+Rj9VXdRwQDPcU9KL9zmzJDb9gP3TU6pgrMXrRGkcyPNFcT1Xjo24ZlFgCo+duYQ7f1U+qKp67KuWLHwRsfP14kqX84XB/0dPPMeKcPEBE+jYC3Mu+6jLUQvohEGz4a514NWLXmrWjL4BJbJj9VpFDYrH2N9hcm1OWxVvpDR32rANHS34AfWdAx2fYfKR0JHoyUqpYHCup3DBZT4VgvXnXEzdKzxcskzYbNHQ==");

        String certKey = ("MIIDVTCCAj2gAwIBAgIEBXCRkTANBgkqhkiG9w0BAQsFADBbMQswCQYDVQQGEwI2\n" +
                "OTEOMAwGA1UECBMFU3RhdGUxDTALBgNVBAcTBENpdHkxEDAOBgNVBAoTB01haW5P\n" +
                "cmcxDDAKBgNVBAsTA09yZzENMAsGA1UEAxMETmFtZTAeFw0yMTA0MjExMzQxMjFa\n" +
                "Fw0yMjA0MjExMzQxMjFaMFsxCzAJBgNVBAYTAjY5MQ4wDAYDVQQIEwVTdGF0ZTEN\n" +
                "MAsGA1UEBxMEQ2l0eTEQMA4GA1UEChMHTWFpbk9yZzEMMAoGA1UECxMDT3JnMQ0w\n" +
                "CwYDVQQDEwROYW1lMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiMsM\n" +
                "lCnivtbzIDniZWV2SMozhA29Q0KVQ7k9qgj5nxnn7Y/Bnz950j3MX2pw8SqWrzEx\n" +
                "/i1tMuf+W9wXW7r34AXvoWDXdQuybYNKrlwrpUVGYXB98+82z5LFY4a9IM9LNUIF\n" +
                "XCurzpkgNo7ySzTK4Jw2vxmpnt9Y1YPlwXXIH5emipl7Ewf2zj+zGVTErVBKUEN4\n" +
                "9HEQf6iZCf6rS6oHxOLb5/G7PzhcxyAXGW5JDU3GGZzPpXlcsXwUaIfFkK+Nb5Sv\n" +
                "zRUulDuUIIZR42+BlXFceQWTBfKH89zuW8AQVUKJfrtf3q7JWZ+wMzIQiDtzOivk\n" +
                "V5mKXBe1KEGQyb1VYQIDAQABoyEwHzAdBgNVHQ4EFgQUDi9YZ72rA17NZnwk/HZ/\n" +
                "BdaR4vYwDQYJKoZIhvcNAQELBQADggEBAHY7N6X8Unt0CgzQvWHR0hOR50bd0sds\n" +
                "gjxX+FLxt8TRg0iFR3ufIFpK8bNcew3gueUJtl7gxqT0t5rMP4w+Y7PBVJr6gClJ\n" +
                "hVj3+VZl/djjZ4rDsoZvTIIPspVwf5BHqMd9ezFHzRS8VrLknsuiy+AKiPkFYqWt\n" +
                "iag12g7/n7C01eJpPXqyO0g0d+YxANqYc9QJeIbUN8nCTKIKJ/A7lAb44/k/PwDY\n" +
                "jbI+kfCBEZHuFyKEl6Lt9Ivtg/owURi6skGWfIEsCdcjAwh5M/HtRWpt2Y1lfRPu\n" +
                "+7Umn5WbSvrU8l7D+UVtsdx5u4yjdE2ccCEerQxksTT2ivw7Vil+AqE=");

        X509Certificate cert;
        final CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        final byte[] certBytes = org.apache.commons.codec.binary.Base64.decodeBase64(certKey);
        InputStream in = new ByteArrayInputStream(certBytes);
        cert = (X509Certificate) certFactory.generateCertificate(in);
        Signature instance = Signature.getInstance("SHA256withRSA");
        instance.initVerify(cert);
        instance.update(signatureData);
        // Check signature verification
        Assert.assertTrue(instance.verify(bytes));
    }
}
