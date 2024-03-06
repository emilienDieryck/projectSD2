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
      System.out.println(city.getNom() + " " + city.getLatitude() + " " + city.getLongitude());
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
  public void calculerItineraireMinimisantNombreRoutes(String city1Name, String city2Name) {
    City startCity = findCityByName(city1Name);
    City endCity = findCityByName(city2Name);

    if (startCity == null || endCity == null) {
      throw new IllegalArgumentException("Ville non trouvée");
    }

    Queue<City> queue = new LinkedList<>();
    Map<City, City> parentMap = new HashMap<>();
    Set<City> visited = new HashSet<>();

    queue.offer(startCity);
    visited.add(startCity);

    while (!queue.isEmpty()) {
      City currentCity = queue.poll();

      if (currentCity.equals(endCity)) {
        break; // Nous avons trouvé l'itinéraire, donc nous sortons de la boucle
      }

      for (Road road : outputRoads.get(currentCity)) {
        City neighborCity = road.getExtremite1() == currentCity.getId() ?
            listOfCities.get(road.getExtremite2()) :
            listOfCities.get(road.getExtremite1());

        if (!visited.contains(neighborCity)) {
          queue.offer(neighborCity);
          visited.add(neighborCity);
          parentMap.put(neighborCity, currentCity);
        }
      }
    }

    if (!visited.contains(endCity)) {
      throw new RuntimeException("Itinéraire non trouvé entre " + city1Name + " et " + city2Name);
    }

    // Reconstruire l'itinéraire à partir du parentMap
    List<City> itinerary = new ArrayList<>();
    City currentCity = endCity;
    while (currentCity != null) {
      itinerary.add(currentCity);
      currentCity = parentMap.get(currentCity);
    }
    Collections.reverse(itinerary);

    // Afficher l'itinéraire dans le format spécifié
    double totalDistance = 0.0;
    System.out.println("Trajet de " + city1Name + " à " + city2Name + ":");
    for (int i = 0; i < itinerary.size() - 1; i++) {
      City city = itinerary.get(i);
      City nextCity = itinerary.get(i + 1);
      double distance = Util.distance(city.getLatitude(), city.getLongitude(), nextCity.getLatitude(), nextCity.getLongitude());
      totalDistance += distance;
      System.out.println(city.getNom() + " -> " + nextCity.getNom() + " (" + distance + " km)");
    }
    System.out.println("Total: " + (itinerary.size()-1) + " routes et " + totalDistance + " kms");
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
