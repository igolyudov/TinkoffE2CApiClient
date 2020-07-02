package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddCardRequest extends SignedRequest {
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("CustomerKey")
    private String customerKey;
    @JsonProperty("CheckType")
    private String checkType;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("PayForm")
    private String payForm;
    @JsonProperty("IP")
    private String ip;
}
