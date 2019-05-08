package com.thesisapp.thesisapp;

import com.opencsv.CSVReader;
import com.thesisapp.thesisapp.DTO.CityCountryPair;
import com.thesisapp.thesisapp.DTO.AirportCityPair;
import com.thesisapp.thesisapp.Entity.DestinationCodeEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

public class DataReader {
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

    public JSONObject getResponseFromApi(String flyFrom, String flyTo) {
        setTrustCertificate();
        HttpURLConnection connection = null;
        JSONObject json = null;
        try {
            URL url = new URL("https://kiwicom-prod.apigee.net/v2/search?fly_from=" +flyFrom + "&fly_to=" + flyTo + "&date_from=01/05/2019&date_to=30/06/2019&flight_type=oneway&one_per_date=1&adults=1&curr=EUR&locale=en&price_from=0&price_to=150&max_stopovers=0&limit=200&sort=date");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("apikey","nAqZDGFOO40G77YOVNZt0mRazqLJf4Mx");
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

        // Install the all-trusting trust manager
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

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
