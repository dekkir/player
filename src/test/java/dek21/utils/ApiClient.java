package dek21.utils;

import dtos.request.PlayerRequestDto;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public static Response deletePlayerWithoutCheck(ReadProperties config, long playerId) {
        return given()
                .contentType("application/json\r\n")
                .body(new PlayerRequestDto(playerId))
                .delete(config.getValue("DELETE_PLAYER"), Constants.Role.SUPERVISOR.getRole());
    }
}
