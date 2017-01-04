package hello;

/**
 * Created by lineplay on 2016-12-26.
 */
public class CloverResult {

    private String success;
    private Object data;

    public CloverResult(String success, Object data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Object getData() { return data;
    }

    public void setData(Object data) { this.data = data; }
}
