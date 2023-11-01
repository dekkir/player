package dtos.response;

import dtos.CommonDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerUpdateResponseDto extends CommonDto {

    private Integer age;

    private String gender;

    private Long id;

    private String login;

    private String role;

    private String screenName;
}
