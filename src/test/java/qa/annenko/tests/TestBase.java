package qa.annenko.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.lang.module.Configuration;

import static qa.annenko.helpers.CustomAPIListener.withCustomTemplates;

public class TestBase {

    @BeforeAll
    public static void setup(){
        RestAssured.filters(withCustomTemplates());
    }
}
