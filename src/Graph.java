import java.io.File;
import java.util.*;

public class Graph {

  private Map<City, List<Road>> outputRoads;

  public Graph(File cities, File roads) {
    outputRoads = new HashMap<>();
    Scanner scanCities = new Scanner(cities);
    List<City> citiesList = new ArrayList<>();
    while (scanCities.hasNextLine()) {
      String line = scanCities.nextLine();
      String[] values = line.split(",");
      City city = new City(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]), Double.parseDouble(values[3]));
      citiesList.add(city);
      outputRoads.put(city, new ArrayList<>());
    }
    scanCities.close();
    Scanner scanRoads = new Scanner(roads);
    while (scanRoads.hasNextLine()) {
      String line = scanRoads.nextLine();
      String[] values = line.split(",");
      City city1 = citiesList.get(Integer.parseInt(values[0]));
      City city2 = citiesList.get(Integer.parseInt(values[1]));
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
}
