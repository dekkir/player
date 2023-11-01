package dek21.tests.player_controller;

import dek21.tests.CommonTest;
import dek21.utils.Constants;
import dtos.response.PlayerResponseDto;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Map;

import static dek21.utils.ApiClient.deletePlayerWithoutCheck;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreatePlayerTest extends CommonTest {

    private final PlayerResponseDto expectedPlayer = new PlayerResponseDto(
            17,
            Constants.Gender.FEMALE.getGender(),
            null,
            "dekkir",
            "qwerty",
            Constants.Role.ADMIN.getRole(),
            "dekkir"
    );

    @AfterClass
    public void clearData() {
        if (expectedPlayer.getId() != null)
            deletePlayerWithoutCheck(config, expectedPlayer.getId());
    }

    @Test(description = "Create Player")
    public void createPlayerTest() {
        PlayerResponseDto actualPlayer = given()
                .params(Map.of(
                        "age", expectedPlayer.getAge(),
                        "gender", expectedPlayer.getGender(),
                        "login", expectedPlayer.getLogin(),
                        "password", expectedPlayer.getPassword(),
                        "role", expectedPlayer.getRole(),
                        "screenName", expectedPlayer.getScreenName()
                ))
                .get(config.getValue("CREATE_PLAYER"), Constants.Role.SUPERVISOR.getRole())
                .then().statusCode(200)
                .extract().as(PlayerResponseDto.class);
        expectedPlayer.setId(actualPlayer.getId());

        // TODO: Bug [link to the bug tracking system] see README bug #2
//        assertThat(actualPlayer)
//                .usingRecursiveComparison()
//                .ignoringFields("id")
//                .isEqualTo(expectedPlayer);

        assertThat(actualPlayer.getLogin()).isEqualTo(expectedPlayer.getLogin());
    }

    @Test(description = "Create Player with the age of 61")
    public void createPlayerWithAge61Test() {
        given()
                .params(Map.of(
                        "age", 61,
                        "gender", expectedPlayer.getGender(),
                        "login", expectedPlayer.getLogin(),
                        "password", expectedPlayer.getPassword(),
                        "role", expectedPlayer.getRole(),
                        "screenName", expectedPlayer.getScreenName()
                ))
                .get(config.getValue("CREATE_PLAYER"), Constants.Role.SUPERVISOR.getRole())
                .then().statusCode(400);
    }
}
