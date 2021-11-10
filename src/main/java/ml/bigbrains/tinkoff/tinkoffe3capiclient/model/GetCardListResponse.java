package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetCardListResponse {
    @JsonProperty("Pan")
    private String pan;
    @JsonProperty("CardId")
    private String cardId;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("RebillId")
    private Long rebilld;
    @JsonProperty("CardType")
    private Integer cardType;
    @JsonProperty("ExpDate")
    private String expDate;
}
