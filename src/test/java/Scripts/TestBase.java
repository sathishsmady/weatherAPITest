package Scripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class TestBase {


    private static final Logger logger = LogManager.getLogger(TestBase.class);
    private static String testCaseName;

    static ExtentReports extentReports;
    static ExtentSparkReporter extentSparkReporter;
    static ExtentTest extentTest;

    static {

        extentSparkReporter  = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/extentReport.html");
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setReportName("Weather API Regression");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);

    }


    @BeforeMethod
    public static void getName(Method m) {


        logger.info( "STARTING TEST: "+ m.getName());
        testCaseName = m.getName();

        extentTest = extentReports.createTest(m.getName());

    }


    @AfterMethod
    public static void getStatus(ITestResult result){

        int SUCCESS = 1;
        int FAILURE = 2;
        int SKIP = 3;

        if(result.getStatus()==SUCCESS){
            extentTest.log(Status.PASS, result.getMethod().getDescription());
            logger.info(testCaseName + " is Passed");
            logger.info("==========================================================================================");
        }
        else if (result.getStatus()==FAILURE) {
            extentTest.log(Status.FAIL, result.getMethod().getDescription());
            extentTest.log(Status.FAIL,result.getThrowable());
            logger.info(testCaseName + " is Failed");
            logger.error(result.getThrowable());
            logger.info("==========================================================================================");

        }
        else if (result.getStatus()==SKIP) {
            extentTest.log(Status.SKIP, result.getMethod().getDescription());
            extentTest.log(Status.SKIP, result.getThrowable());
            logger.info(testCaseName + " is Skipped");
            logger.error(result.getThrowable());
            logger.info("==========================================================================================");

        }

        extentReports.flush();

    }


}
