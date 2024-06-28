import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;

public class TestGenerator {
    public static void main(String[] args) {
        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        // Ask user for the number of batches
        System.out.print("Enter the number of batches to generate: ");
        int numBatches = scanner.nextInt();
        
        // Filename
        String filename = "output.WIS";
        
        // Generate random number data
        Random random = new Random();
        
        // DecimalFormat to format the longitude, latitude, temperature, and wind speed values
        DecimalFormat decimalFormatLongitudeLatitude = new DecimalFormat("##0.000000");
        DecimalFormat decimalFormatTemperatureWindSpeed = new DecimalFormat("##0.0");
        
        // Array of cardinal and ordinal directions
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        
        // Calendar instance to manipulate timestamps
        Calendar calendar = Calendar.getInstance();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int batch = 0; batch < numBatches; batch++) {
                // Get current timestamp for the batch
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm'UTC'");
                String timestamp = dateFormat.format(calendar.getTime());
                
                // Write the timestamp for the batch
                writer.write(timestamp);
                writer.newLine();
                
                // Random number of rows between 5 and 10 for this batch
                int numRows = 5 + random.nextInt(6);  // Random number between 5 and 10
                
                for (int i = 0; i < numRows; i++) {
                    StringBuilder sb = new StringBuilder();
                    
                    // Generate a random longitude value (-180 to 180) and format it
                    double randomLongitude = -180 + (180 - (-180)) * random.nextDouble();
                    sb.append(decimalFormatLongitudeLatitude.format(randomLongitude));
                    sb.append("\t");  // Add tab delimiter
                    
                    // Generate a random latitude value (-90 to 90) and format it
                    double randomLatitude = -90 + (90 - (-90)) * random.nextDouble();
                    sb.append(decimalFormatLongitudeLatitude.format(randomLatitude));
                    sb.append("\t");  // Add tab delimiter
                    
                    // Generate a random temperature value and format it
                    boolean isCelsius = random.nextBoolean();
                    double randomTemperature;
                    String temperatureUnit;
                    if (isCelsius) {
                        // Temperature in Celsius (-30 to 50)
                        randomTemperature = -30 + (50 - (-30)) * random.nextDouble();
                        temperatureUnit = "C";
                    } else {
                        // Temperature in Fahrenheit (-22 to 122)
                        randomTemperature = -22 + (122 - (-22)) * random.nextDouble();
                        temperatureUnit = "F";
                    }
                    String formattedTemperature = decimalFormatTemperatureWindSpeed.format(randomTemperature);
                    sb.append(formattedTemperature);
                    if (formattedTemperature.length() == 3) {
                        sb.append("\t");  // Add an extra tab if temperature is a single digit
                    }
                    sb.append("\t");  // Add tab delimiter
                    
                    // Append temperature unit
                    sb.append(temperatureUnit);
                    sb.append("\t");  // Add tab delimiter
                    
                    // Generate a random wind speed value and format it
                    double randomWindSpeed = random.nextDouble() * 100; // Wind speed (0.0 to 100.0)
                    String formattedWindSpeed = decimalFormatTemperatureWindSpeed.format(randomWindSpeed);
                    sb.append(formattedWindSpeed);
                    if (formattedWindSpeed.length() == 3) {
                        sb.append("\t");  // Add an extra tab if wind speed is a single digit
                    }
                    sb.append("\t");  // Add tab delimiter
                    
                    // Append wind speed unit
                    sb.append("km/h");
                    sb.append("\t");  // Add tab delimiter
                    
                    // Append a random direction
                    String randomDirection = directions[random.nextInt(directions.length)];
                    sb.append(randomDirection);
                    sb.append("\t");  // Add tab delimiter
                    
                    // Generate a random integer (0 to 100) for the eighth tab
                    int randomInteger = random.nextInt(101);  // Random integer between 0 and 100
                    sb.append(randomInteger);
                    
                    writer.write(sb.toString());
                    writer.newLine();  // Move to the next line
                }
                
                writer.newLine();  // Add an extra line between batches
                
                // Increment the calendar by a random number of minutes (up to 60)
                int randomMinutes = random.nextInt(61);
                calendar.add(Calendar.MINUTE, randomMinutes);
            }
            System.out.println("Random data written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        
        // Close the scanner
        scanner.close();
    }
}
