import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestGenerator {
    public static void main(String[] args) {
        // Number of batches to generate
        int numBatches = 10;  // You can change this to any number of batches you want
        
        // Filename
        String filename = "output.WIS";
        
        // Generate random number data
        Random random = new Random();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int batch = 0; batch < numBatches; batch++) {
                // Get current timestamp for the batch
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm'UTC'");
                String timestamp = dateFormat.format(new Date());
                
                // Write the timestamp for the batch
                writer.write("Timestamp: " + timestamp);
                writer.newLine();
                
                // Random number of rows between 5 and 10 for this batch
                int numRows = 5 + random.nextInt(6);  // Random number between 5 and 10
                
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
                
                writer.newLine();  // Add an extra line between batches
                // Simulate a delay for next batch to have a different timestamp
                Thread.sleep(1000);  // Sleep for 1 second
            }
            System.out.println("Random number data written to " + filename);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
