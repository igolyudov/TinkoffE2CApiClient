package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class RemoveCardRequest extends SignedRequest {
    @JsonProperty("TerminalKey")
    private String terminalKey;
    @JsonProperty("CardId")
    private Long cardId;
    @JsonProperty("CustomerKey")
    private String customerKey;

    @Override
    public Map<String, String> getMapForSign() {
        Map<String,String> data = new HashMap<>();
        if(StringUtils.isNotEmpty(terminalKey))
            data.put("TerminalKey",terminalKey);
        if(StringUtils.isNotEmpty(String.valueOf(cardId)))
            data.put("CardId",String.valueOf(cardId));
        if(StringUtils.isNotEmpty(customerKey))
            data.put("CustomerKay", customerKey);
        return data;
    }
}
