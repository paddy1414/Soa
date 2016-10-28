package pdesigns.com.soa;

/**
 * Created by Patrick on 25/04/2016.
 */
public class Movie {

    private int id;
    private String title;
    private String genre;
    private String length;

    public Movie(int id, String title, String length, String genre) {
        this.id = id;
        this.title = title;
        this.length = length;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (getId() != movie.getId()) return false;
        if (getTitle() != null ? !getTitle().equals(movie.getTitle()) : movie.getTitle() != null)
            return false;
        if (getGenre() != null ? !getGenre().equals(movie.getGenre()) : movie.getGenre() != null)
            return false;
        return !(getLength() != null ? !getLength().equals(movie.getLength()) : movie.getLength() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", length='" + length + '\'' +
                '}';
    }
}
