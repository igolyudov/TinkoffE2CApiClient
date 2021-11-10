package ml.bigbrains.tinkoff.tinkoffe3capiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetAccountInfoResponse extends GenericResponse {
    @JsonProperty("Total")
    private String total;
    @JsonProperty("Hold")
    private String hold;
    @JsonProperty("Available")
    private String available;
}
