package com.thesisapp.thesisapp;

import com.opencsv.CSVReader;
import com.thesisapp.thesisapp.DTO.AirportCityPair;
import com.thesisapp.thesisapp.DTO.CityCountryPair;
import com.thesisapp.thesisapp.Repository.*;
import com.thesisapp.thesisapp.Entity.ApiDataEntity;
import com.thesisapp.thesisapp.Entity.DestinationCodeEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.*;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

@SpringBootApplication
@RestController
public class ThesisappApplication {

	@Autowired
	CountryRepository countryRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	AirportRepository airportRepository;
	@Autowired
	FlightRepository flightRepository;
	@Autowired
	TicketRepository ticketRepository;

	//@GetMapping(value="/country/{countryCode}")
	@RequestMapping(value="/country/{countryCode}", method= RequestMethod.GET)
	public String getTaskName(@PathVariable("countryCode") String countryCode) {
		return countryRepository.getCountryIdByCountryCode(countryCode);
	}

	//@RequestMapping(value="/insertCityData", method= RequestMethod.GET)
    public String insertCityData(){
		String csvFileLocation = "C:\\cityNameCountryCode.csv";
		CityCountryPair[] cityCountryPairs = readCityCountryPairCsvData(csvFileLocation);
		for (int i = 0; i < cityCountryPairs.length; i++) {
			Integer countryID = Integer.valueOf(countryRepository.getCountryIdByCountryCode(cityCountryPairs[i].getCountryCode()));
			cityRepository.insertCity(cityCountryPairs[i].getCityName(), countryID);
		}
        return "Successful insertCityData!";
    }

	//@RequestMapping(value="/insertAirportData", method= RequestMethod.GET)
	public String insertAirportData(){
		String csvFileLocation = "C:\\airportCodeCityName.csv";
		AirportCityPair[] airportCityPairs = readAirportCityPairCsvData(csvFileLocation);
		for (int i = 0; i < airportCityPairs.length; i++) {
			Integer cityID = Integer.valueOf(cityRepository.getCityIdByCityName(airportCityPairs[i].getCityName()));
			airportRepository.insertAirport(airportCityPairs[i].getAirportCode(), cityID);
		}
		return "Successful insertAirportData!";
	}

	//@RequestMapping(value="/insertApiData", method= RequestMethod.GET)
	public void insertApiData(){
		String csvFileLocation = "C:\\destinationCodes.csv";
		DestinationCodeEntity[] destinationCodes = readDestinationCodeCsvData(csvFileLocation);
		ApiDataEntity[] apiData = null;
		for (int i = 0; i < destinationCodes.length; i++) {
			JSONObject jsonObject = getResponseFromApi(destinationCodes[i].getFlyFrom(), destinationCodes[i].getFlyTo());
			JSONArray dataArray = (JSONArray) jsonObject.get("data");
			apiData = new ApiDataEntity[dataArray.size()];
			for (int j = 0; j < dataArray.size(); j++) {
				JSONObject eachData = (JSONObject) dataArray.get(j);
				apiData[j] = (new ApiDataEntity(eachData));
				Integer airportFromId = Integer.valueOf(airportRepository.getAirportIdByAirportCode(apiData[j].getFlyFrom()));
				Integer airportToId = Integer.valueOf(airportRepository.getAirportIdByAirportCode(apiData[j].getFlyTo()));
				flightRepository.insertFlight(apiData[j].getFlightNo(), apiData[j].getAirline(), airportFromId, airportToId );
				Integer flightId = Integer.valueOf(flightRepository.getFlightIdByFlightNumber(apiData[j].getFlightNo()));
				ticketRepository.insertTicket(apiData[j].getCombination_id(), apiData[j].getPrice(), apiData[j].getLocal_departure(),
						apiData[j].getLocal_arrival(), apiData[j].getDeep_link(), flightId);
				}
		}
	}

	public AirportCityPair[] readAirportCityPairCsvData(String file) {
		AirportCityPair[] airportCity = new AirportCityPair[64];
		try {
			FileReader filereader = new FileReader(file);
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;
			int count = 0;
			while ((nextRecord = csvReader.readNext()) != null) {
				for (String cell : nextRecord) {
					String[] split = cell.split(";");
					AirportCityPair eachPair = new AirportCityPair(split[0],split[1]);
					airportCity[count] = eachPair;
					count++;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return airportCity;
	}

	public CityCountryPair[] readCityCountryPairCsvData(String file) {
		CityCountryPair[] cityCountry = new CityCountryPair[54];
		try {
			FileReader filereader = new FileReader(file);
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;
			int count = 0;
			while ((nextRecord = csvReader.readNext()) != null) {
				for (String cell : nextRecord) {
					String[] split = cell.split(";");
					CityCountryPair eachPair = new CityCountryPair(split[0],split[1]);
					cityCountry[count] = eachPair;
					count++;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cityCountry;
	}

	public DestinationCodeEntity[] readDestinationCodeCsvData(String file) {
		DestinationCodeEntity[] destinationCodes = new DestinationCodeEntity[1850];
		try {
			FileReader filereader = new FileReader(file);
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;
			int count = 0;
			while ((nextRecord = csvReader.readNext()) != null) {
				for (String cell : nextRecord) {
					String[] split = cell.split(";");
					DestinationCodeEntity eachPair = new DestinationCodeEntity(split[0],split[1]);
					destinationCodes[count] = eachPair;
					count++;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return destinationCodes;
	}

	private void setTrustCertificate() {
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		}
		};
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public JSONObject getResponseFromApi(String flyFrom, String flyTo) {
		setTrustCertificate();
		HttpURLConnection connection = null;
		JSONObject json = null;
		try {
			URL url = new URL("https://kiwicom-prod.apigee.net/v2/search?fly_from=airport:" +flyFrom + "&fly_to=airport:" + flyTo + "&date_from=01/05/2019&date_to=30/06/2019&flight_type=oneway&one_per_date=1&adults=1&curr=EUR&locale=en&price_from=0&price_to=150&max_stopovers=0&limit=200&sort=date");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("accept", "application/json");
			connection.setRequestProperty("apikey","YOUR-APIKEY");
			connection.setRequestProperty("Accept-Encoding", "gzip");
			connection.setUseCaches(false);
			connection.setDoOutput(true);

			//Get Response
			Reader reader = null;
			String line="";
			if ("gzip".equals(connection.getContentEncoding())) {
				reader = new InputStreamReader(new GZIPInputStream(connection.getInputStream()));
			}
			else {
				reader = new InputStreamReader(connection.getInputStream());
			}
			while (true) {
				int ch = reader.read();
				if (ch==-1) {
					break;
				}
				line = line + (char)ch;
			}

			reader.close();
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(line);

			return json;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return json;
	}

	public static void main(String[] args) {
		SpringApplication.run(ThesisappApplication.class, args);
	}
}