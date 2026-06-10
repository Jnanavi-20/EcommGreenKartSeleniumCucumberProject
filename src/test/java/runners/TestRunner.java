package runners;


import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",   // path to feature files
        glue = {"stepdefinitions", "hooks"}, // step defs + hooks
        tags = "@AddMultipleToCart or @CheckoutFlow",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json",
                "rerun:target/failed_scenarios.txt" 
              
        },
        monochrome = true,
        publish = true
)

public class TestRunner {

}
