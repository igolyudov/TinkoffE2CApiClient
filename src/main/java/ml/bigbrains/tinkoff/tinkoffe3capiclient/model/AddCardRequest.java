package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;


@Data
@Slf4j
public class AddCardRequest extends SignedRequest{
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


    @Override
    public Map<String,String> getMapForSign() {
        Map<String,String> data = new HashMap<>();
        if(StringUtils.isNotEmpty(terminalKey))
            data.put("TerminalKey",terminalKey);
        if(StringUtils.isNotEmpty(customerKey))
            data.put("CustomerKay", customerKey);
        if(StringUtils.isNotEmpty(checkType))
            data.put("CheckType",checkType);
        if(StringUtils.isNotEmpty(description))
            data.put("Description",description);
        if(StringUtils.isNotEmpty(payForm))
            data.put("PayForm",payForm);
        if(StringUtils.isNotEmpty(ip))
            data.put("IP",ip);

        return data;

    }
}
