package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

}
