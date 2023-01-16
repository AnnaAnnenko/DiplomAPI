package qa.annenko.specs;

import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import qa.annenko.config.ApiConfig;

import static io.restassured.RestAssured.with;
import static qa.annenko.helpers.CustomAPIListener.withCustomTemplates;

public class CommonRequestSpec {

    static ApiConfig config = ConfigFactory.create(ApiConfig.class);
    static String baseUrl = config.baseUrl();
    public static RequestSpecification commonRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri(baseUrl)
            .basePath("/api/users")
            .log().all();
}
