package base.data;

public class UserMetaData {

    private Integer id;
    private Long mobileId;
    private Long userId;
    private String username;
    private String name;
    private String roleName;
    private Long roleGroupId;
    private Long branchId;
    private Long recapId;
    private String branchType;
    private String branchName;
    private String pushMessagingId;
    private String appName;
    private Long loginTime;
    private String imei;
    private Integer v;
    private String pin;
    private Integer notifTotal;
    private String notifMessage;

    public void setNotifMessage(String notifMessage) {
        this.notifMessage = notifMessage;
    }

    public Integer getNotifTotal() {
        return notifTotal;
    }

    public String getNotifMessage() {
        return notifMessage;
    }

    public String getPin() {
        return pin;
    }

    public void setNotifTotal(Integer notifTotal) {
        this.notifTotal = notifTotal;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMobileId() {
        return mobileId;
    }

    public void setMobileId(Long mobileId) {
        this.mobileId = mobileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(Long roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPushMessagingId() {
        return pushMessagingId;
    }

    public void setPushMessagingId(String pushMessagingId) {
        this.pushMessagingId = pushMessagingId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getRecapId() {
        return recapId;
    }

    public void setRecapId(Long recapId) {
        this.recapId = recapId;
    }
}
