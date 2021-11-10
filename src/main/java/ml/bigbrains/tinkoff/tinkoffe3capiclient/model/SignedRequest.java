package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public abstract class SignedRequest {
    @JsonProperty("DigestValue")
    private String digestValue;
    @JsonProperty("SignatureValue")
    private String signatureValue;
    @JsonProperty("X509SerialNumber")
    private String x509SerialNumber;

    public abstract Map<String,String> getMapForSign();
    public Map<String,String> getAllParams()
    {
        Map<String,String> map = new HashMap<>();
        map.putAll(getMapForSign());
        map.put("DigestValue",digestValue);
        map.put("SignatureValue",signatureValue);
        map.put("X509SerialNumber",x509SerialNumber);
        return map;
    }

}
