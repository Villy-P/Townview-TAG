import java.util.ArrayList;
import java.util.Scanner;
import core.data.DataSource;

public class PetriniV29 {
	public static void main(String[] args) {
		ArrayList<WeatherStation> stations = WeatherMultiThread.getStations();
		ArrayList<WeatherStation> texasStations = loneStarStations(stations);
        double temperatureRange = temperatureRange(texasStations);
		System.out.println("\n\nThe temperature range in Texas is " + temperatureRange + " degrees.\n\n");
		WeatherStation st = pickStation(texasStations);
		for (WeatherStation s : findByTemp(stations, st))
			System.out.println(s.getStationID() + "\t" + s.getTemperature() + "\t" + s.getLocation());
	}

	public static ArrayList<WeatherStation> loneStarStations(ArrayList<WeatherStation> northAmerica) {
        ArrayList<WeatherStation> loneStar = new ArrayList<>();
        for (WeatherStation w : northAmerica)
            if (w.getLocation().indexOf("TX") > -1)
                loneStar.add(w);
		return loneStar;
	}

	public static double temperatureRange(ArrayList<WeatherStation> list) {
		double least = list.get(0).getTemperature();
        double most = list.get(0).getTemperature();
        for (WeatherStation w : list) {
            if (w.getTemperature() < least) 
                least = w.getTemperature();
            if (w.getTemperature() > most) 
                most = w.getTemperature();
        }
        return most - least;
	}

	public static void displayStationsNeatly(ArrayList<WeatherStation> toDisplay) {
		System.out.println("Available Stations :\n");
		for (int i = 0; i < toDisplay.size(); i++) {
			System.out.print(toDisplay.get(i).getStationID() + " ");
			if (i % 10 == 0 && i != 0)
				System.out.println();
		}
		System.out.println("\n");
	}

	public static WeatherStation pickStation(ArrayList<WeatherStation> choices) {
		displayStationsNeatly(choices);
		Scanner s = new Scanner(System.in);
		WeatherStation station = null;
		WHILE:
		while (true) {
			System.out.print("Select a Station\t");
			String e = s.nextLine();
			for (WeatherStation w : choices) {
				if (w.getStationID().equals(e)) {
					station = w;
					break WHILE;
				}
			}
			System.out.println("Not a valid station");
		}
		s.close();
		return station;
	}

	public static ArrayList<WeatherStation> findByTemp(ArrayList<WeatherStation> all, WeatherStation choice) {
		ArrayList<WeatherStation> eq = new ArrayList<>();
		for (WeatherStation s : all)
			if (s.getTemperature() == choice.getTemperature())
				eq.add(s);
		return eq;
	}
}


class WeatherMultiThread {
	public static int stationNumber = 0;
	public static ArrayList<WeatherStation> stations = new ArrayList<WeatherStation>();
	public static ArrayList<String> station_urls = getStationList();

	public static ArrayList<WeatherStation> getStations() {
		long startTime = System.currentTimeMillis();
		System.out.println("There are " + station_urls.size() + " possibly available North American weather stations.");
		System.out.print("Loading stations -----\tThis could take several minutes   ");
		ArrayList<NetworkThread> threads = new ArrayList<NetworkThread>();
		for(int i = 0; i < 10; i++) {
			NetworkThread temp = new NetworkThread();
			temp.start();
			threads.add(temp);
			try{
                Thread.sleep(50);
            } catch (Exception e) {}
		}
		boolean running = true;
		while(running) {
			running = false;
			for (NetworkThread nt : threads)
				if (nt.running())
					running = true;
		}
		startTime = System.currentTimeMillis()-startTime;
		System.out.println("\nLoad time :" + (startTime / 1000) + " seconds");

		return stations;
	}

	/**Creates and returns an ArrayList of Strings representing
	 * the websites for all available weather station xml files
	 * @return 	a reference to an ArrayList containing String representations
	 *			of the websites for all available weather station xml files
	 */
	public static ArrayList<String> getStationList() {
		DataSource ds1 = DataSource.connect("https://w1.weather.gov/xml/current_obs/index.xml");
		ds1.setCacheTimeout(15 * 60);
      	ds1.load();
      	return ds1.fetchList("String", "station/xml_url");
	}
}

class WeatherStation {
    private String stationID;
    private String location;
    private double temperature;

    public WeatherStation(String stationID, String location, double temperature) {
        this.stationID = stationID;
        this.location = location;
        this.temperature = temperature;
    }

    public String getStationID() { return stationID; }
    public String getLocation() { return location; }
    public double getTemperature() { return temperature; }
}

class NetworkThread implements Runnable {
	private Thread t;
	private boolean running;

	public void run() {
		while (running) {
            try {
                WeatherMultiThread.stations.add(createStation(WeatherMultiThread.station_urls.get(WeatherMultiThread.stationNumber++)));
            } catch(core.access.DataAccessException cae) {

            } catch(Exception e) {
                running = false;
            }
        }
	}

	public boolean running(){
        return running;
    }

	public void start() {
		if (t == null) {
			running = true;
			t = new Thread(this, "network");
			t.start();
		}
	}

	/**Creates and returns a WeatherStation object.
	 * The method returns null to avoid compile errors, you will have
	 * to change this.
	 * @param 	stationAddress  the website for the xml file
	 * @return 	a reference to the WeatherStation object found at the
	 *			parameter stationAddress
	 */
	public static WeatherStation createStation(String stationAddress) {
		DataSource ds1 = DataSource.connect(stationAddress);
		ds1.setCacheTimeout(15 * 60);
      	ds1.load();        
		return new WeatherStation(
            ds1.fetch("String", "station_id"),
            ds1.fetch("String", "location"),
            Double.parseDouble(ds1.fetch("String", "temp_f"))
        );
	}
}