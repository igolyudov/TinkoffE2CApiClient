package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class RemoveCustomerRequest extends SignedRequest{
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("CustomerKey")
    private String customerKey;
    @JsonProperty("IP")
    private String ip;

    @Override
    public Map<String, String> getMapForSign() {
        Map<String,String> data = new HashMap<>();
        if(StringUtils.isNotEmpty(terminalKey))
            data.put("TerminalKey",terminalKey);
        if(StringUtils.isNotEmpty(customerKey))
            data.put("CustomerKey", customerKey);
        if(StringUtils.isNotEmpty(ip))
            data.put("IP",ip);
        return data;
    }
}
