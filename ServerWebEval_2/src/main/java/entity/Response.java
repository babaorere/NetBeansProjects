package entity;

import java.io.Serializable;

/**
 *
 */
public class Response implements Serializable {

    private int status;
    private Object data;

    public Response() {
        this(-1, null);
    }

    public Response(int status) {
        this(status, null);
    }

    public Response(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

}
