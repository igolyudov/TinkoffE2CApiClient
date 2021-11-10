package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

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


    @Override
    public Map<String, String> getMapForSign() {
        Map<String,String> data = new HashMap<>();
        if(StringUtils.isNotEmpty(terminalKey))
            data.put("TerminalKey",terminalKey);
        if(StringUtils.isNotEmpty(orderId))
            data.put("OrderId",orderId);
        if(StringUtils.isNotEmpty(ip))
            data.put("IP",ip);
        if(StringUtils.isNotEmpty(cardId))
            data.put("CardId",cardId);
        if(StringUtils.isNotEmpty(String.valueOf(amount)))
            data.put("Amount",String.valueOf(amount));
        if(StringUtils.isNotEmpty(String.valueOf(currency)))
            data.put("Currency", String.valueOf(currency));
        if(StringUtils.isNotEmpty(customerKey))
            data.put("CustomerKey", customerKey);
        if(StringUtils.isNotEmpty(this.data))
            data.put("DATA",this.data);
        return data;
    }
}
