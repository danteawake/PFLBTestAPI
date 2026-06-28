package models.positive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserRequest {
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("money")
    @Expose
    public Double money;
    @SerializedName("secondName")
    @Expose
    public String secondName;
    @SerializedName("sex")
    @Expose
    public String sex;
}
