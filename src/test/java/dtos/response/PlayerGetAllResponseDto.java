package dtos.response;

import dtos.CommonDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerGetAllResponseDto extends CommonDto {

    List<PlayerItem> players;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class PlayerItem extends CommonDto {

        private Integer age;

        private String gender;

        private Long id;

        private String role;

        private String screenName;
    }
}
