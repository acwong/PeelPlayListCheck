package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PreRollId {
    @JsonProperty("name")
    private String videoGroupId;

    public String getVideoGroupId() {
        return videoGroupId;
    }

    public void setVideoGroupId(String videoGroupId) {
        this.videoGroupId = videoGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        PreRollId preRollId = (PreRollId) o;
        return Objects.equals(videoGroupId, preRollId.videoGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoGroupId);
    }

    @Override
    public String toString() {
        return "PreRollId{" +
                "videoGroupId='" + videoGroupId + '\'' +
                '}';
    }
}
