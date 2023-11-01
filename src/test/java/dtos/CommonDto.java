package dtos;

import com.google.gson.Gson;

public class CommonDto {

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
