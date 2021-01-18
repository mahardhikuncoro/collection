package base.location.network;

public class BaseNetworkCallback {
    private String status,message, regno, docid/*, field*/;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    /* public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }*/
}
