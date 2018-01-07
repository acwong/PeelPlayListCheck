package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Content extends VideoGroup {
    @JsonProperty("preroll")
    private List<PreRollId> preRollIds = new ArrayList<PreRollId>();

    @SuppressWarnings("unused")
    public Content() {
    }

    /**
     * Elements in {@code preRollIds} of type {@link PreRollId} are not deep-copied.
     */
    public Content(Content content) {
        super(content);
        preRollIds.addAll(content.getPreRollIds());
    }

    public List<PreRollId> getPreRollIds() {
        return preRollIds;
    }

    public void setPreRollIds(List<PreRollId> preRollIds) {
        this.preRollIds = preRollIds;
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
