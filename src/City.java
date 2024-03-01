import java.util.Objects;

public class City {

  private final int id;
  private final String nom;
  private final Double longitude;
  private final Double latitude;

  public City(int id, String nom, Double longitude, Double latitude) {
    this.id = id;
    this.nom = nom;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    City city = (City) o;
    return id == city.id && Objects.equals(longitude, city.longitude)
        && Objects.equals(latitude, city.latitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, longitude, latitude);
  }

  @Override
  public String toString() {
    return "City{" +
        "id=" + id +
        ", nom='" + nom + '\'' +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        '}';
  }
}
