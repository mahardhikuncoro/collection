package ops.screen.fragment;

public class TaskListDetailModel {
    private String customername;
    private String ap_regno, customertype_id, customertype_desc, customerdocument_id, customerdocument_type, product_id, product_desc, plafon, track_id, track_name;

    public String getNamaNasabah() {
        return customername;
    }

    public void setNamaNasabah(String customername) {
        this.customername = customername;
    }

    public String getIdNasabah() {
        return ap_regno;
    }

    public void setIdNasabah(String ap_regno) {
        this.ap_regno = ap_regno;
    }

    public String getCustomertype_id() {
        return customertype_id;
    }

    public void setCustomertype_id(String customertype_id) {
        this.customertype_id = customertype_id;
    }

    public String getCustomertype_desc() {
        return customertype_desc;
    }

    public void setCustomertype_desc(String customertype_desc) {
        this.customertype_desc = customertype_desc;
    }

    public String getCustomerdocument_id() {
        return customerdocument_id;
    }

    public void setCustomerdocument_id(String customerdocument_id) {
        this.customerdocument_id = customerdocument_id;
    }

    public String getCustomerdocument_type() {
        return customerdocument_type;
    }

    public void setCustomerdocument_type(String customerdocument_type) {
        this.customerdocument_type = customerdocument_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getPlafon() {
        return plafon;
    }

    public void setPlafon(String plafon) {
        this.plafon = plafon;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getTrack_name() {
        return track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }
}
