package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RemoveCustomerRequest extends SignedRequest{
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("CustomerKey")
    private String customerKey;
    @JsonProperty("IP")
    private String ip;
}
