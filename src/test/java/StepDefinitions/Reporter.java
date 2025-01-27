package StepDefinitions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.IOException;

public class Reporter {

    private static ExtentReports extent;
    private static ExtentTest test;

    // Initialize ExtentReports
    public static void initialize() {
        // Define the location of the report file with the custom path
        String reportFilePath = "/Users/vijay/eclipse-workspace/CapStoneProject/extentReportOfSelenium.html";
        
        // Create the necessary directories and file if they don't exist
        File reportFile = new File(reportFilePath);
        if (!reportFile.exists()) {
            try {
                reportFile.getParentFile().mkdirs();  // Create parent directories if not present
                reportFile.createNewFile();           // Create the file if it doesn't exist
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Setup Extent Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    // Start a new test log
    public static ExtentTest createTest(String testName) {
        // Create and return a new test log
        test = extent.createTest(testName);
        return test;
    }

    // Add a pass message to the report
    public static void pass(String message) {
        if (test != null) {
            test.pass(message);
        } else {
            System.out.println("Test is not initialized. Please create a test first.");
        }
    }

    // Add a fail message to the report
    public static void fail(String message) {
        if (test != null) {
            test.fail(message);
        } else {
            System.out.println("Test is not initialized. Please create a test first.");
        }
    }

    // Flush the report after tests are completed
    public static void flush() {
        if (extent != null) {
            extent.flush(); // Write the results to the HTML report file
        } else {
            System.out.println("ExtentReports is not initialized.");
        }
    }
}
