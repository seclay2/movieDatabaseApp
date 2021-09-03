package io.github.seclay2.api.entity;

public class Movie {
    private Integer id;
    private String title;
    private Integer releaseYear;
    private Integer runtime;
    private String imdbId;

    // Constructor, getters, setters...
    public Movie() {
        // Default constructor
    }

    public Movie(Integer id, String title, Integer releaseYear, Integer runtime, String imdbId) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.imdbId = imdbId;
    }

    public Movie(String title, Integer releaseYear, Integer runtime, String imdbId) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.imdbId = imdbId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", runtime=" + runtime +
                ", imdbId='" + imdbId + '\'' +
                '}';
    }
}
