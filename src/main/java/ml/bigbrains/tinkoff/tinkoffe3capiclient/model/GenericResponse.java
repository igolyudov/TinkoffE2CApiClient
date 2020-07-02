package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GenericResponse {
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("Amount")
    private Long amount;
    @JsonProperty("OrderId")
    private String orderId;
    @JsonProperty("Success")
    private Boolean success;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("PaymentId")
    private Long paymentId;
    @JsonProperty("PaymentURL")
    private String paymentUrl;
    @JsonProperty("ErrorCode")
    private String errorCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Details")
    private String details;

}
