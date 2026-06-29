package models.positive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HouseRequest {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("floorCount")
    @Expose
    public Integer floorCount;

    @SerializedName("price")
    @Expose
    public Double price;

    @SerializedName("parkingPlaces")
    @Expose
    public List<HouseParkingPlace> parkingPlaces;

    @SerializedName("lodgers")
    @Expose
    public List<UserResponse> lodgers;

    @Data
    @Builder
    public static class HouseParkingPlace {

        @SerializedName("id")
        @Expose
        public Integer id;

        @SerializedName("isWarm")
        @Expose
        public Boolean isWarm;

        @SerializedName("isCovered")
        @Expose
        public Boolean isCovered;

        @SerializedName("placesCount")
        @Expose
        public Integer placesCount;
    }

    @Data
    public static class UserResponse {
        @SerializedName("id")
        @Expose
        public Integer id;

        @SerializedName("firstName")
        @Expose
        public String firstName;

        @SerializedName("secondName")
        @Expose
        public String secondName;

        @SerializedName("age")
        @Expose
        public Integer age;

        @SerializedName("sex")
        @Expose
        public String sex;

        @SerializedName("money")
        @Expose
        public Double money;
    }
}