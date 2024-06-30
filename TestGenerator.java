import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

class WeatherRecord {
    private double longitude;
    private double latitude;
    private double temperature;
    private String temperatureUnit;
    private double windSpeed;
    private String windSpeedUnit = "km/h";
    private String direction;
    private int randomInteger;

    public WeatherRecord(double longitude, double latitude, double temperature, String temperatureUnit, double windSpeed, String direction, int randomInteger) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
        this.windSpeed = windSpeed;
        this.direction = direction;
        this.randomInteger = randomInteger;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormatLongitudeLatitude = new DecimalFormat("##0.000000");
        DecimalFormat decimalFormatTemperatureWindSpeed = new DecimalFormat("##0.0");
        
        StringJoiner sj = new StringJoiner("\t");
        sj.add(decimalFormatLongitudeLatitude.format(longitude));
        sj.add(decimalFormatLongitudeLatitude.format(latitude));
        
        String temperatureStr = decimalFormatTemperatureWindSpeed.format(temperature);
        if (temperatureStr.length() == 3) {  // Format x.x (e.g., 5.2)
            sj.add(temperatureStr + "\t");
        } else {
            sj.add(temperatureStr);
        }
        
        sj.add(temperatureUnit);
        
        String windSpeedStr = decimalFormatTemperatureWindSpeed.format(windSpeed);
        if (windSpeedStr.length() == 3) {  // Format x.x (e.g., 5.6)
            sj.add(windSpeedStr + "\t");
        } else {
            sj.add(windSpeedStr);
        }

        sj.add(windSpeedUnit);
        sj.add(direction);
        sj.add(Integer.toString(randomInteger));
        
        return sj.toString();
    }
}

class Batch {
    private String timestamp;
    private List<WeatherRecord> records;

    public Batch(String timestamp) {
        this.timestamp = timestamp;
        this.records = new ArrayList<>();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<WeatherRecord> getRecords() {
        return records;
    }

    public void addRecord(WeatherRecord record) {
        records.add(record);
    }
}

public class TestGenerator {

    private static final Random random = new Random();
    private static final String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm'UTC'");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Ask user for the number of batches
        System.out.print("Enter the number of batches to generate: ");
        int numBatches = scanner.nextInt();
        
        // Filename
        String filename = "output.WIS";
        
        // Calendar instance to manipulate timestamps
        Calendar calendar = Calendar.getInstance();
        
        // List to hold all Batch objects
        List<Batch> batches = new ArrayList<>();
        
        for (int batchIndex = 0; batchIndex < numBatches; batchIndex++) {
            // Get current timestamp for the batch
            String timestamp = dateFormat.format(calendar.getTime());
            
            // Create a new Batch with the current timestamp
            Batch batch = new Batch(timestamp);
            
            // Generate random number of rows between 5 and 10 for this batch
            int numRows = generateRandomNumber(5, 10);
            
            for (int i = 0; i < numRows; i++) {
                WeatherRecord record = generateWeatherRecord();
                batch.addRecord(record);
            }
            
            batches.add(batch);
            
            // Increment the calendar by a random number of minutes (up to 60)
            int randomMinutes = generateRandomNumber(0, 60);
            calendar.add(Calendar.MINUTE, randomMinutes);
        }
        
        // Write data to file
        writeFile(filename, batches);
        
        System.out.println("Random data written to " + filename);
        
        // Close the scanner
        scanner.close();
    }

    private static int generateRandomNumber(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    private static double generateRandomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private static WeatherRecord generateWeatherRecord() {
        double longitude = generateRandomDouble(-180, 180);
        double latitude = generateRandomDouble(-90, 90);
        
        boolean isCelsius = random.nextBoolean();
        double temperature;
        String temperatureUnit;
        if (isCelsius) {
            temperature = generateRandomDouble(-30, 50);
            temperatureUnit = "C";
        } else {
            temperature = generateRandomDouble(-22, 122);
            temperatureUnit = "F";
        }
        
        double windSpeed = generateRandomDouble(0, 100);
        String direction = directions[random.nextInt(directions.length)];
        int randomInteger = random.nextInt(101);
        
        return new WeatherRecord(longitude, latitude, temperature, temperatureUnit, windSpeed, direction, randomInteger);
    }

    private static void writeFile(String filename, List<Batch> batches) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Batch batch : batches) {
                writer.write(batch.getTimestamp());
                writer.newLine();
                
                for (WeatherRecord record : batch.getRecords()) {
                    writer.write(record.toString());
                    writer.newLine();
                }
                
                writer.newLine();  // Add an extra line between batches
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
