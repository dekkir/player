package dek21.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class Constants {

    @AllArgsConstructor
    @Getter
    public enum Role {
        ADMIN("admin"),
        SUPERVISOR("supervisor"),
        USER("user");

        private final String role;

    }

    @AllArgsConstructor
    @Getter
    public enum Gender {
        MALE("male"),
        FEMALE("female");

        private final String gender;

    }
}
