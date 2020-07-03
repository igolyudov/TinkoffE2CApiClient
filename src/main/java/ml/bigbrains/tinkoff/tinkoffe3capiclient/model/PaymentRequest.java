package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class PaymentRequest extends SignedRequest {
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("PaymentId")
    private Long paymentId;

    @Override
    public Map<String, String> getMapForSign() {
        Map<String,String> data = new HashMap<>();
        if(StringUtils.isNotEmpty(terminalKey))
            data.put("TerminalKey",terminalKey);
        if(StringUtils.isNotEmpty(String.valueOf(paymentId)))
            data.put("PaymentId", String.valueOf(paymentId));
        return data;
    }
}
