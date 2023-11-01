package dtos.response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.CommonDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerResponseDto extends CommonDto {

    private Integer age;

    private String gender;

    private Long id;

    private String login;

    private String password;

    private String role;

    private String screenName;

    public PlayerUpdateResponseDto mapToPlayerUpdateResponseDto() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(this, PlayerUpdateResponseDto.class);
    }
}
