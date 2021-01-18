package base.network;

public class BaseRequest {

    private String regno,
            userid,
            tc,
            type,
            dataLevel,
            formname,
            listItemId,
            sectionId,
            tableName,
            lon,
            lat,
            addr,
            multiple_doccode,
            flag,
            deviceId;

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public String getListItemId() {
        return listItemId;
    }

    public void setListItemId(String listItemId) {
        this.listItemId = listItemId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMultiple_doccode() {
        return multiple_doccode;
    }

    public void setMultiple_doccode(String multiple_doccode) {
        this.multiple_doccode = multiple_doccode;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
