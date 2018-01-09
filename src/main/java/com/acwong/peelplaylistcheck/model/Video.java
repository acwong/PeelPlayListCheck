package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {
    private final String tag;
    private final MediaAttribute attributes;

    @JsonCreator
    public Video(@JsonProperty("name") String tag, @JsonProperty("attributes") MediaAttribute attributes) {
        this.tag = tag;
        this.attributes = attributes;
    }

    public String getTag() {
        return tag;
    }

    public MediaAttribute getAttributes() {
        return attributes;
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
