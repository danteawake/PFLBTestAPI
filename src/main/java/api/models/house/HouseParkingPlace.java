package api.models.house;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HouseParkingPlace {

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