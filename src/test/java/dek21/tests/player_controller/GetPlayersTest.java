package dek21.tests.player_controller;

import dek21.tests.CommonTest;
import dtos.response.PlayerGetAllResponseDto;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetPlayersTest extends CommonTest {

    @Test(description = "Get Players")
    public void getPlayersTest() {
        PlayerGetAllResponseDto actualResult = given()
                .get(config.getValue("GET_PLAYERS"))
                .then().statusCode(200)
                .extract().as(PlayerGetAllResponseDto.class);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualResult.getPlayers()).isNotEmpty();
        actualResult.getPlayers().forEach(player -> {
            softAssertions.assertThat(player.getId()).isNotNull();
            softAssertions.assertThat(player.getAge()).isNotNull();
            // TODO: Bug [link to the bug tracking system] see readme bug #10
//             softAssertions.assertThat(player.getRole()).isNotNull();
            softAssertions.assertThat(player.getGender()).isNotNull();
            softAssertions.assertThat(player.getScreenName()).isNotNull();
        });
        softAssertions.assertAll();
    }

    // There cannot be negative test scenarios because this request does not have any parameters to test
}
