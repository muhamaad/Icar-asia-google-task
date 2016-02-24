package test.com.zarea.googletask.model;

/**
 * Created by zarea on 2/23/16.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "kind",
        "id",
        "etag",
        "title",
        "updated",
        "selfLink",
        "parent",
        "position",
        "notes",
        "status",
        "due",
        "completed",
        "deleted",
        "hidden",
        "links"
})
public class Item {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("id")
    private String id;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("title")
    private String title;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("selfLink")
    private String selfLink;
    @JsonProperty("parent")
    private String parent;
    @JsonProperty("position")
    private String position;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("status")
    private String status;
    @JsonProperty("due")
    private String due;
    @JsonProperty("completed")
    private String completed;
    @JsonProperty("deleted")
    private String deleted;
    @JsonProperty("hidden")
    private String hidden;
    @JsonProperty("links")
    private List<Link> links = new ArrayList<Link>();
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
     * The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
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
     * The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The updated
     */
    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    /**
     *
     * @param updated
     * The updated
     */
    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     *
     * @return
     * The selfLink
     */
    @JsonProperty("selfLink")
    public String getSelfLink() {
        return selfLink;
    }

    /**
     *
     * @param selfLink
     * The selfLink
     */
    @JsonProperty("selfLink")
    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    /**
     *
     * @return
     * The parent
     */
    @JsonProperty("parent")
    public String getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     * The parent
     */
    @JsonProperty("parent")
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     * The position
     */
    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    @JsonProperty("position")
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     *
     * @return
     * The notes
     */
    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     * The notes
     */
    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @return
     * The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The due
     */
    @JsonProperty("due")
    public String getDue() {
        return due;
    }

    /**
     *
     * @param due
     * The due
     */
    @JsonProperty("due")
    public void setDue(String due) {
        this.due = due;
    }

    /**
     *
     * @return
     * The completed
     */
    @JsonProperty("completed")
    public String getCompleted() {
        return completed;
    }

    /**
     *
     * @param completed
     * The completed
     */
    @JsonProperty("completed")
    public void setCompleted(String completed) {
        this.completed = completed;
    }

    /**
     *
     * @return
     * The deleted
     */
    @JsonProperty("deleted")
    public String getDeleted() {
        return deleted;
    }

    /**
     *
     * @param deleted
     * The deleted
     */
    @JsonProperty("deleted")
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    /**
     *
     * @return
     * The hidden
     */
    @JsonProperty("hidden")
    public String getHidden() {
        return hidden;
    }

    /**
     *
     * @param hidden
     * The hidden
     */
    @JsonProperty("hidden")
    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    /**
     *
     * @return
     * The links
     */
    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The links
     */
    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
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