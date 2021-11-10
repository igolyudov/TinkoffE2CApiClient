package ml.bigbrains.tinkoff.tinkoffe2capiclient;

import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.tinkoff.tinkoffe3capiclient.TinkoffClient;
import ml.bigbrains.tinkoff.tinkoffe3capiclient.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.CryptoPro.JCP.JCP;
import java.security.*;
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
    public void testGetKey()
    {

    }

    @Test
    public void signedRequest()
    {
        TinkoffClient tinkoffClient = new TinkoffClient("rest-api-test.tinkoff.ru");
        AddCustomerRequest addCustomerRequest = new AddCustomerRequest();
        addCustomerRequest.setTerminalKey("123123");
        addCustomerRequest.setCustomerKey("321321");
//        addCustomerRequest.setEmail("info@example.com");
//        addCustomerRequest.setPhone("+79001000110");
//        addCustomerRequest.setIp("127.0.0.1");

        tinkoffClient.signed(addCustomerRequest,"HDImageStore", "testKey", "401437532001722600264206825014014998552699056");
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

    @Test
    public void signedRSARequest()
    {

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

        TinkoffClient tinkoffClient = new TinkoffClient("rest-api-test.tinkoff.ru");
        AddCustomerRequest addCustomerRequest = new AddCustomerRequest();
        addCustomerRequest.setTerminalKey("123123");
        addCustomerRequest.setCustomerKey("321321");

        tinkoffClient.signedRSA(addCustomerRequest,prKey, "401437532001722600264206825014014998552699056");
        log.debug("Signed request: "+addCustomerRequest);
        try {
            byte [] data = Base64.getDecoder().decode(addCustomerRequest.getDigestValue());
            Assert.assertEquals(Base64.getEncoder().encodeToString(data),"HkhXONcmYVSirfRsqXi/fpy0rwWZ8buYZ+vvbB34Qo8=");
            byte [] signature = Base64.getDecoder().decode(addCustomerRequest.getSignatureValue());
            Assert.assertEquals(Base64.getEncoder().encodeToString(signature),"BP6EMViGpqTbu0LyXwT+5a080oCErD+eobbhwl1tsHR2ozqQfCiiIbSVI7olHkigkICIJZ9SCf9nOlulIEonaBceh2YAHMMf3vm3hiRt6t+M6n2+r5lpHBQxJRVILh+8JH1GvDdYLQWdCTBd/zMpCVldC6EUPuWbGCs04UL588r81tYewABVlUR35tzRJfRnsonSNVj57QA4S+0570It0SNDrc6Fv0uC6ZeQ1MESWj10gcvQgM9gqJjJXG74SfvR+NgefhWP4bIjU1VhlJGR3tN26r1xejDGVZwH/qLxWbft79RZWmuFmh/LR0sxlaZQ7KWB9KuEsVY7vpFAfmoomA==");

          }
        catch (Exception e)
        {
            log.error("Ошибка при проверки подписи",e);
            Assert.fail();
        }
    }

}
