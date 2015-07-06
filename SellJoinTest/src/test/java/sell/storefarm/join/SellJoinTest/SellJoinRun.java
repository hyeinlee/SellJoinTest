package sell.storefarm.join.SellJoinTest;

import net.serenitybdd.cucumber.CucumberWithSerenity;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin = {"pretty"}, features ={"src/test/resource"},format = {"json:target/checkoutJenkins/cucumber.json", "html:target/html/cucumber.html"})
//@CucumberOptions(features = "sec/test/resource")

public class SellJoinRun {

}
