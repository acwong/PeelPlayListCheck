package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Content extends VideoGroup {
    private final List<PreRollId> preRollIds;

    @JsonCreator
    public Content(@JsonProperty("name") String videoGroupId, @JsonProperty("videos") List<Video> videos,
                   @JsonProperty("preroll") List<PreRollId> preRollIds) {
        super(videoGroupId, videos);
        this.preRollIds = Collections.unmodifiableList((preRollIds == null) ? new ArrayList<PreRollId>() : preRollIds);
    }

    /**
     * Elements in {@code preRollIds} of type {@link PreRollId} are not deep-copied.
     */
    public Content(Content content, List<Video> videos) {
        super(content, videos);
        preRollIds = Collections.unmodifiableList(content.getPreRollIds());
    }

    public List<PreRollId> getPreRollIds() {
        return preRollIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Content content = (Content) o;
        return Objects.equals(preRollIds, content.preRollIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), preRollIds);
    }

    @Override
    public String toString() {
        return "Content{" +
                "preRollIds=" + preRollIds +
                '}';
    }
}
