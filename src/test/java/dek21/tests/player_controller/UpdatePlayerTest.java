package dek21.tests.player_controller;

import dek21.tests.CommonTest;
import dek21.utils.Constants;
import dtos.response.PlayerResponseDto;
import dtos.response.PlayerUpdateResponseDto;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static dek21.utils.ApiClient.deletePlayerWithoutCheck;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePlayerTest extends CommonTest {
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
                        "role", Constants.Role.USER.getRole(),
                        "screenName", "dekkir"
                ))
                .get(config.getValue("CREATE_PLAYER"), Constants.Role.SUPERVISOR.getRole())
                .then().statusCode(200)
                .extract().as(PlayerResponseDto.class);

        // TODO: Bug [link to the bug tracking system] see README bug #2
        existedPlayer.setAge(28);
        existedPlayer.setGender(Constants.Gender.FEMALE.getGender());
        existedPlayer.setPassword("qwerty");
        existedPlayer.setRole(Constants.Role.USER.getRole());
        existedPlayer.setScreenName("dekkir");
    }

    @AfterClass
    public void clearData() {
        deletePlayerWithoutCheck(config, existedPlayer.getId());
    }

    @Test(description = "Update Player")
    public void updatePlayerTest() {
        PlayerUpdateResponseDto expectedPlayer = existedPlayer.mapToPlayerUpdateResponseDto();

        PlayerResponseDto playerToPatch = new PlayerResponseDto();
        playerToPatch.setAge(55);
        expectedPlayer.setAge(playerToPatch.getAge());

        PlayerUpdateResponseDto patchedPlayer = given()
                .contentType("application/json\r\n")
                .body(playerToPatch)
                .patch(config.getValue("UPDATE_PLAYER"), existedPlayer.getLogin(), existedPlayer.getId())
                .then().statusCode(200)
                .extract().as(PlayerUpdateResponseDto.class);

        assertThat(patchedPlayer).isEqualTo(expectedPlayer);
    }

    @Test(description = "Update not existed Player")
    public void updateNotExistedPlayerTest() {
        given()
                .contentType("application/json\r\n")
                .body(new PlayerResponseDto())
                .patch(config.getValue("UPDATE_PLAYER"), existedPlayer.getLogin(), Long.MAX_VALUE)
                // TODO: Bug [link to the bug tracking system] see readme bug #12
                .then().statusCode(200);
    }

    @Test(description = "Update Player by negative ID")
    public void updateByNegativeIdTest() {
        given()
                .contentType("application/json\r\n")
                .body(new PlayerResponseDto())
                .patch(config.getValue("UPDATE_PLAYER"), existedPlayer.getLogin(), -1)
                // TODO: Bug [link to the bug tracking system] see readme bug #12
                .then().statusCode(200);
    }
}
