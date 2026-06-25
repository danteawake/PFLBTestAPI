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
}