package dek21.tests.player_controller;

import dek21.tests.CommonTest;
import dek21.utils.Constants;
import dtos.response.PlayerGetAllResponseDto;
import dtos.response.PlayerResponseDto;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dek21.utils.ApiClient.deletePlayerWithoutCheck;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DeletePlayerTest extends CommonTest {

    private PlayerResponseDto existedPlayer;
    private PlayerResponseDto existedPlayer2;

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

        existedPlayer2 = given()
                .contentType("application/json\r\n")
                .params(Map.of(
                        "age", 35,
                        "gender", Constants.Gender.MALE.getGender(),
                        "login", "delllogin",
                        "password", "qwerty",
                        "role", Constants.Role.USER.getRole(),
                        "screenName", "DELL"
                ))
                .get(config.getValue("CREATE_PLAYER"), Constants.Role.SUPERVISOR.getRole())
                .then().statusCode(200)
                .extract().as(PlayerResponseDto.class);
    }

    @AfterClass
    public void clearData() {
        deletePlayerWithoutCheck(config, existedPlayer.getId());
        deletePlayerWithoutCheck(config, existedPlayer2.getId());
    }

    @Test(description = "Delete existed Player")
    public void deleteExistedPlayerTest() {
        deletePlayerWithoutCheck(config, existedPlayer.getId()).then().statusCode(204);

        PlayerGetAllResponseDto players = given()
                .get(config.getValue("GET_PLAYERS"))
                .then().statusCode(200)
                .extract().as(PlayerGetAllResponseDto.class);
        List<Long> ids = players.getPlayers().stream()
                .map(PlayerGetAllResponseDto.PlayerItem::getId).collect(Collectors.toList());
        assertThat(ids).doesNotContain(existedPlayer.getId());
    }

    @Test(description = "Delete Player by negative ID")
    public void deletePlayerByNegativeIdTest() {
        deletePlayerWithoutCheck(config, -1L).then().statusCode(403);
    }
}
