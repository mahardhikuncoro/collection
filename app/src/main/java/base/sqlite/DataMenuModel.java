package base.sqlite;


public class DataMenuModel {

    private String userid,
            su_fullname,
            groupid,
            sg_grpname,
            branchid,
            branchname,
            typeid,
            menuid,
            menudesc,
            menutrack,
            assigned,
            track,
            jumlahaplikasi,
            icon,
            is_add;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenudesc() {
        return menudesc;
    }

    public void setMenudesc(String menudesc) {
        this.menudesc = menudesc;
    }

    public String getJumlahaplikasi() {
        return jumlahaplikasi;
    }

    public void setJumlahaplikasi(String jumlahaplikasi) {
        this.jumlahaplikasi = jumlahaplikasi;
    }

    public String getMenutrack() {
        return menutrack;
    }

    public void setMenutrack(String menutrack) {
        this.menutrack = menutrack;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIs_add() {
        return is_add;
    }

    public void setIs_add(String is_add) {
        this.is_add = is_add;
    }

    public String getSu_fullname() {
        return su_fullname;
    }

    public void setSu_fullname(String su_fullname) {
        this.su_fullname = su_fullname;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getSg_grpname() {
        return sg_grpname;
    }

    public void setSg_grpname(String sg_grpname) {
        this.sg_grpname = sg_grpname;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
