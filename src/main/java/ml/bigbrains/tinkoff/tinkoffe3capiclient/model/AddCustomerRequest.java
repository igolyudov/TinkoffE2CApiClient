package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString(callSuper = true)
public class AddCustomerRequest extends SignedRequest{
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("CustomerKey")
    private String customerKey;
    @JsonProperty("IP")
    private String ip;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Phone")
    private String phone;


    @Override
    public Map<String, String> getMapForSign() {
        Map<String,String> data = new HashMap<>();
        if(StringUtils.isNotEmpty(terminalKey))
            data.put("TerminalKey",terminalKey);
        if(StringUtils.isNotEmpty(customerKey))
            data.put("CustomerKey", customerKey);
        if(StringUtils.isNotEmpty(ip))
            data.put("IP",ip);
        if(StringUtils.isNotEmpty(email))
            data.put("Email",email);
        if(StringUtils.isNotEmpty(phone))
            data.put("Phone",phone);
        return data;
    }
}
