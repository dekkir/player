package dek21.tests.player_controller;

import dek21.tests.CommonTest;
import dek21.utils.Constants;
import dtos.request.PlayerRequestDto;
import dtos.response.PlayerResponseDto;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static dek21.utils.ApiClient.deletePlayerWithoutCheck;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPlayerByIdTest extends CommonTest {

    private PlayerResponseDto existedPlayer;

    @BeforeClass
    public void preparePlayers() {
        existedPlayer = given()
                .contentType("application/json\r\n")
                .params(Map.of(
                        "age", 28,
                        "gender", Constants.Gender.FEMALE.getGender(),
                        "login", "dekkir",
                        "password", "qwerty",
                        "role", Constants.Role.ADMIN.getRole(),
                        "screenName", "dekkir"
                ))
                .get(config.getValue("CREATE_PLAYER"), Constants.Role.SUPERVISOR.getRole())
                .then().statusCode(200)
                .extract().as(PlayerResponseDto.class);
    }

    @AfterClass
    public void clearData() {
        deletePlayerWithoutCheck(config, existedPlayer.getId());
    }

    @Test(description = "Get default Player")
    public void getDefaultPlayerByIdTest() {
        PlayerResponseDto expectedPlayer = new PlayerResponseDto(
                28,
                Constants.Gender.MALE.getGender(),
                1L,
                "supervisor",
                "testSupervisor",
                "supervisor",
                "testSupervisor"
        );

        PlayerResponseDto actualPlayer = given()
                .contentType("application/json\r\n")
                .body(new PlayerRequestDto(1L))
                .post(config.getValue("GET_PLAYER_BY_ID"))
                .then().statusCode(200)
                .extract().as(PlayerResponseDto.class);

        assertThat(actualPlayer).isEqualTo(expectedPlayer);
    }

    @Test(description = "Get Player by ID")
    public void getPlayerByIdTest() {
        PlayerResponseDto actualPlayer = given()
                .contentType("application/json\r\n")
                .body(new PlayerRequestDto(existedPlayer.getId()))
                .post(config.getValue("GET_PLAYER_BY_ID"))
                .then().statusCode(200)
                .extract().as(PlayerResponseDto.class);

        // TODO: Bug [link to the bug tracking system] see README bug #2
        // The object existedPlayer wasn't filled properly in beforeClass method because of the bug
//        assertThat(actualPlayer).isEqualTo(existedPlayer);
        assertThat(actualPlayer.getLogin()).isEqualTo(existedPlayer.getLogin());
    }

    @Test(description = "Get Player by negative ID")
    public void getPlayerByNegativeIdTest() {
        given()
                .contentType("application/json\r\n")
                // TODO: Bug [link to the bug tracking system] see readme bug #11
                .body(new PlayerRequestDto(-1L))
                .post(config.getValue("GET_PLAYER_BY_ID"))
                .then().statusCode(200);
    }
}
