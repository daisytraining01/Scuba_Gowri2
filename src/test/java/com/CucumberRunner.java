package com;

import com.maveric.core.cucumber.CucumberBaseTest;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"./src/test/resources/features/"},
        glue ={ "com.internetapp.ParabankstepDefs"},
        tags= "@parabank"
)
public class CucumberRunner extends CucumberBaseTest {


}
