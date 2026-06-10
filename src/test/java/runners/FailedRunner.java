package runners;


import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@target/failed_scenarios.txt",  // ← reads the failed list
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/failed-rerun-report.html",
                "json:target/failed-rerun.json"
                 // ← overwrites with any still-failing
        },
        monochrome = true
)

public class FailedRunner {

}
