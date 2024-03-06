import java.util.Objects;

public class Road {
  private final int extremite1;
  private final int extremite2;

  public Road (int extremite1, int extremite2) {
    this.extremite1 = extremite1;
    this.extremite2 = extremite2;
  }

  public int getExtremite1() {
    return extremite1;
  }

  public int getExtremite2() {
    return extremite2;
  }

  public int getOtherEnd(int extremity) {
    if (extremity == extremite1) {
      return extremite2;
    } else if (extremity == extremite2) {
      return extremite1;
    } else {
      throw new IllegalArgumentException("Given extremity is not part of this road.");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Road road = (Road) o;
    return extremite1 == road.extremite1 && extremite2 == road.extremite2;
  }

  @Override
  public int hashCode() {
    return Objects.hash(extremite1, extremite2);
  }

  @Override
  public String toString() {
    return "Road{" +
        "extremite1=" + extremite1 +
        ", extremite2=" + extremite2 +
        '}';
  }
}
