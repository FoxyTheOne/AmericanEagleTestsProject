package api.models;

import io.restassured.response.ValidatableResponse;

public class ApiHttpResponse {
    private final ValidatableResponse response;

    public ApiHttpResponse(ValidatableResponse response) {
        this.response = response;
    }
}
