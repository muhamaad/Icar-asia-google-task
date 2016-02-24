package test.com.zarea.googletask.model;

/**
 * Created by zarea on 2/23/16.
 */
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "kind",
        "etag",
        "nextPageToken",
        "items"
})
public class TaskList {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("nextPageToken")
    private String nextPageToken;
    @JsonProperty("items")
    private List<Item> items = new ArrayList<Item>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The kind
     */
    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     * The kind
     */
    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     * The etag
     */
    @JsonProperty("etag")
    public String getEtag() {
        return etag;
    }

    /**
     *
     * @param etag
     * The etag
     */
    @JsonProperty("etag")
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     *
     * @return
     * The nextPageToken
     */
    @JsonProperty("nextPageToken")
    public String getNextPageToken() {
        return nextPageToken;
    }

    /**
     *
     * @param nextPageToken
     * The nextPageToken
     */
    @JsonProperty("nextPageToken")
    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    /**
     *
     * @return
     * The items
     */
    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}