import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestGenerator {
    public static void main(String[] args) {
        // Number of rows to generate
        int numRows = 100;  // You can change this to any number of rows you want

        // Filename
        String filename = "output.WIS";

        // Generate random number data
        Random random = new Random();

        // Get current timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm'UTC'");
        String timestamp = dateFormat.format(new Date());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write the timestamp to the beginning of the file
            writer.write("Timestamp: " + timestamp);
            writer.newLine();
            
            for (int i = 0; i < numRows; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 8; j++) {
                    int randomNumber = random.nextInt(1000);  // Generate a random number (0-999)
                    sb.append(randomNumber);
                    if (j < 7) {
                        sb.append("\t");  // Add tab delimiter
                    }
                }
                writer.write(sb.toString());
                writer.newLine();  // Move to the next line
            }
            System.out.println("Random number data written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
