package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MediaAttribute {
    @JsonProperty("countries")
    private List<Country> countries = new ArrayList<Country>();

    @JsonProperty("language")
    private Language language;

    @JsonProperty("aspect")
    private String aspectRatio;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
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
