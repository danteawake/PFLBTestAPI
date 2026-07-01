package api.models.car;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarRequestUpdate {
    @SerializedName("engineType")
    @Expose
    public String engineType;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("mark")
    @Expose
    public String mark;
    @SerializedName("model")
    @Expose
    public String model;
    @SerializedName("price")
    @Expose
    public Integer price;
}
