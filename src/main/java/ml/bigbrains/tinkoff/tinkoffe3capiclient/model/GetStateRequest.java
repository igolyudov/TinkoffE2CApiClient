package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class GetStateRequest extends SignedRequest {
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("PaymentId")
    private Long paymentId;
    @JsonProperty("IP")
    private String ip;

    @Override
    public Map<String, String> getMapForSign() {
        Map<String,String> data = new HashMap<>();
        if(StringUtils.isNotEmpty(terminalKey))
            data.put("TerminalKey",terminalKey);
        if(StringUtils.isNotEmpty(String.valueOf(paymentId)))
            data.put("PaymentId", String.valueOf(paymentId));
        if(StringUtils.isNotEmpty(ip))
            data.put("IP",ip);
        return data;
    }
}
