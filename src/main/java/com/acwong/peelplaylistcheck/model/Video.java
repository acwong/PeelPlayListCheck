package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Video {
    @JsonProperty("name")
    private String tag;

    @JsonProperty("attributes")
    private MediaAttribute attributes;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public MediaAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(MediaAttribute attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Video video = (Video) o;
        return Objects.deepEquals(tag, video.tag) &&
                Objects.deepEquals(attributes, video.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, attributes);
    }

    @Override
    public String toString() {
        return "Video{" +
                "tag='" + tag + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
