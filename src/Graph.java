import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {

  private Map<Integer , City> listOfCities;
  private Map<City, Set<Road>> outputRoads;

  public Graph(File cities, File roads) {
    outputRoads = new HashMap<>();
    listOfCities = new HashMap<>();
    Scanner scanCities = null;
    try {
      scanCities = new Scanner(cities);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    while (scanCities.hasNextLine()) {
      String line = scanCities.nextLine();
      String[] values = line.split(",");
      City city = new City(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]), Double.parseDouble(values[3]));
      listOfCities.put(city.getId(), city);
      outputRoads.put(city, new HashSet<>());
    }
    scanCities.close();
    Scanner scanRoads = null;
    try {
      scanRoads = new Scanner(roads);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    while (scanRoads.hasNextLine()) {
      String line = scanRoads.nextLine();
      String[] values = line.split(",");
      City city1 = listOfCities.get(Integer.parseInt(values[0]));
      City city2 = listOfCities.get(Integer.parseInt(values[1]));
      Road road = new Road(city1.getId(), city2.getId());
      outputRoads.get(city1).add(road);
      outputRoads.get(city2).add(road);
    }
    scanRoads.close();
  }
  public void calculerItineraireMinimisantNombreRoutes(String city1, String city2) {
  }


  public void calculerItineraireMinimisantKm(String city1, String city2) {

  }

  private City findCityByName (String cityName) {
    for (City city : listOfCities.values()) {
      if (city.getNom().equals(cityName)) {
        return city;
      }
    }
    return null;
  }
}
