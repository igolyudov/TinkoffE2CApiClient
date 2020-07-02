package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InitRequest extends SignedRequest {
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("OrderId")
    private String orderId;
    @JsonProperty("IP")
    private String ip;
    @JsonProperty("CardId")
    private String cardId;
    @JsonProperty("Amount")
    private Long amount;
    //ISO 4217 Code
    @JsonProperty("Currency")
    private Integer currency;
    @JsonProperty("CustomerKey")
    private String customerKey;
    @JsonProperty("DATA")
    private String data;
}
