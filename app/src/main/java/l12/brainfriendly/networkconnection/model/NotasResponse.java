package l12.brainfriendly.networkconnection.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author pjohnson on 26/03/18.
 */

public class NotasResponse {

    private String msg;
    private int code;
    @SerializedName("data")
    private List<Nota> notas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
}
