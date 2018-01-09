package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaAttribute {
    private final List<Country> countries;
    private final Language language;
    private final String aspectRatio;

    @JsonCreator
    public MediaAttribute(@JsonProperty("countries") List<Country> countries,
                          @JsonProperty("language") Language language, @JsonProperty("aspect") String aspectRatio) {
        this.countries = Collections.unmodifiableList((countries == null) ? new ArrayList<Country>() : countries);
        this.language = language;
        this.aspectRatio = aspectRatio;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public Language getLanguage() {
        return language;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        MediaAttribute that = (MediaAttribute) o;
        return Objects.deepEquals(countries, that.countries) &&
                language == that.language &&
                Objects.deepEquals(aspectRatio, that.aspectRatio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countries, language, aspectRatio);
    }

    @Override
    public String toString() {
        return "MediaAttribute{" +
                "countries=" + countries +
                ", language=" + language +
                ", aspectRatio='" + aspectRatio + '\'' +
                '}';
    }
}
