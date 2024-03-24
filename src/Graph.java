import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {

  private Map<Integer , City> listOfCities;
  private Map<City, Set<Road>> outputRoads;
  private Map<String, City> cityByName;

  public Graph(File cities, File roads) {
    outputRoads = new HashMap<>();
    listOfCities = new HashMap<>();
    cityByName = new HashMap<>();

    Scanner scanCities = null;
    try {
      scanCities = new Scanner(cities);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    while (scanCities.hasNextLine()) {
      String line = scanCities.nextLine();
      String[] values = line.split(",");
      City city = new City(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[3]), Double.parseDouble(values[2]));
      listOfCities.put(city.getId(), city);
      outputRoads.put(city, new HashSet<>());
      cityByName.put(city.getNom(), city);
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
        break;
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


    List<City> itinerary = new ArrayList<>();
    City currentCity = endCity;
    while (currentCity != null) {
      itinerary.add(currentCity);
      currentCity = parentMap.get(currentCity);
    }
    Collections.reverse(itinerary);

    double totalDistance = 0.0;
    String routesFinal = "";
    for (int i = 0; i < itinerary.size() - 1; i++) {
      City city = itinerary.get(i);
      City nextCity = itinerary.get(i + 1);
      double distance = Util.distance(city.getLatitude(), city.getLongitude(), nextCity.getLatitude(), nextCity.getLongitude());
      totalDistance += distance;
      routesFinal += city.getNom() + " -> " + nextCity.getNom() + " (" + String.format("%.2f", distance) + " km) \n" ;
    }
    System.out.print("Trajet de " + city1Name + " à " + city2Name + ": ");
    System.out.println( (itinerary.size()-1) + " routes et " + totalDistance + " kms");
    System.out.println(routesFinal);
  }

  public void calculerItineraireMinimisantKm(String city1Name, String city2Name) {
    City city1 = findCityByName(city1Name);
    City city2 = findCityByName(city2Name);

    if (city1 == null || city2 == null) {
      System.out.println("Villes non trouvées.");
      return;
    }

    Map<City, Double> distances = new HashMap<>();
    Map<City, City> predecessors = new HashMap<>();
    PriorityQueue<City> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

    for (City city : listOfCities.values()) {
      distances.put(city, Double.MAX_VALUE);
      predecessors.put(city, null);
    }

    distances.put(city1, 0.0);
    queue.add(city1);

    while (!queue.isEmpty()) {
      City current = queue.poll();
      if (current.equals(city2)) {
        break;
      }
      for (Road road : outputRoads.get(current)) {
        City neighbor = listOfCities.get(road.getOtherEnd(current.getId()));
        double distanceThroughU = distances.get(current) + Util.distance(current.getLatitude(), current.getLongitude(), neighbor.getLatitude(), neighbor.getLongitude());
        if (distanceThroughU < distances.get(neighbor)) {
          distances.put(neighbor, distanceThroughU);
          predecessors.put(neighbor, current);
          queue.add(neighbor);
        }
      }
    }

    LinkedList<City> path = new LinkedList<>();
    City current = city2;
    while (current != null) {
      path.addFirst(current);
      current = predecessors.get(current);
    }


    double distanceTotal = 0;
    String sortie = "";
    for (int i = 0; i < path.size() - 1; i++) {
      City from = path.get(i);
      City to = path.get(i + 1);

      double dist = Util.distance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
      distanceTotal += dist;

      sortie += from.getNom() + " -> " + to.getNom() + " (" + String.format("%.2f", dist) + " km) \n";
    }

    System.out.println("Trajet de " + city1Name + " à " + city2Name + ": " + (path.size() - 1) + " routes et " + distanceTotal + " kms");
    System.out.println();
    System.out.println(sortie);
  }

  private City findCityByName(String cityName) {
    return cityByName.get(cityName);
  }
}
