package base.sqlite;

import android.content.Context;

import java.util.Properties;

import base.AssetsPropertyReader;

public class SQLiteConfig {

    private String sqliteName;
    private Integer sqliteVer;
    private String serverName;
    private Integer serverVersion;
    private String serverLocName;
    private Integer serverLocVersion;
    private Integer serverUserVersion;


    private Integer serverConnectionTimeout;
    private Integer serverReadTimeout;
    private Integer lastLoginConnectionTimeout;
    private Integer lastLoginReadTimeout;

    private Long saveLocationInterval;
    private Long sendLocationInterval;
    private Long updateTimeInterval;
    private Long distanceInterval;
    private Long radius;
    private String gpsIsChanged;
    private String gpsIs24Hours;
    private String alarmEnable;
    private Integer alarmHours;
    private Integer alarmMinute;
    private Integer alarmSecond;

    private String googleMarket;
    private String googlePlaystore;



    public SQLiteConfig(Context context) {
        AssetsPropertyReader assetsPropertyReader = new AssetsPropertyReader(context);
        Properties properties = assetsPropertyReader.getProperties("sqlite-config-pegadaian-mikro.properties");

        setSqliteName(properties.getProperty("SQLITE_NAME"));
        setSqliteVer(Integer.parseInt(properties.getProperty("SQLITE_VER")));
        setServerName(properties.getProperty("SERVER"));
        setServerLocName(properties.getProperty("SERVER_LOC"));
        setServerVersion(Integer.parseInt(properties.getProperty("SERVER_VERSION")));
        setServerUserVersion(Integer.parseInt(properties.getProperty("SERVERUSER_VERSION")));

        setServerConnectionTimeout(Integer.parseInt(properties.getProperty("SERVER_CONNECTION_TIMEOUT")));
        setServerReadTimeout(Integer.parseInt(properties.getProperty("SERVER_READ_TIMEOUT")));
        setLastLoginConnectionTimeout(Integer.parseInt(properties.getProperty("LAST_LOGIN_CONNECTION_TIMEOUT")));
        setLastLoginReadTimeout(Integer.parseInt(properties.getProperty("LAST_LOGIN_READ_TIMEOUT")));

        setGoogleMarket(properties.getProperty("GOOGLE_MARKET"));
        setGooglePlaystore(properties.getProperty("GOOGLE_PLAYSTORE"));

        setAlarmHours(Integer.parseInt(properties.getProperty("GPS_ALARM_CONFIG_HOUR")));
        setAlarmMinute(Integer.parseInt(properties.getProperty("GPS_ALARM_CONFIG_MINUTE")));
        setAlarmSecond(Integer.parseInt(properties.getProperty("GPS_ALARM_CONFIG_SECOND")));
        setRadius(Long.parseLong(properties.getProperty("GPS_RADIUS")));
        setGpsIs24Hours(properties.getProperty("GPS_IS_24HOURS"));
        setGpsIsChanged(properties.getProperty("GPS_IS_CHANGED"));
        setDistanceInterval(Long.parseLong(properties.getProperty("GPS_DISTANCE_INTERVAL")));
        setUpdateTimeInterval(Long.parseLong(properties.getProperty("GPS_UPDATE_TIME_INTERVAL")));
        setSaveLocationInterval(Long.parseLong(properties.getProperty("GPS_GETLOCATION_SERVICE_INTERVAL")));
        setSendLocationInterval(Long.parseLong(properties.getProperty("GPS_SENDLOCATION_SERVICE_INTERVAL")));
        setAlarmEnable(properties.getProperty("GPS_ALARM_SERVICE"));

        setTimeIntervalGetLocation(Integer.parseInt(properties.getProperty("TIME_INTERVAL_GET_LOCATION")));
        setTimeIntervalSendLocation(Integer.parseInt(properties.getProperty("TIME_INTERVAL_SEND_LOCATION")));

    }

    public String getGoogleMarket() {
        return googleMarket;
    }

    public void setGoogleMarket(String googleMarket) {
        this.googleMarket = googleMarket;
    }

    public String getGooglePlaystore() {
        return googlePlaystore;
    }

    public void setGooglePlaystore(String googlePlaystore) {
        this.googlePlaystore = googlePlaystore;
    }

    public void setServerUserVersion(Integer serverUserVersion) {
        this.serverUserVersion = serverUserVersion;
    }

    public Integer getServerUserVersion() {
        return serverUserVersion;
    }

    public Integer getServerConnectionTimeout() {
        return serverConnectionTimeout;
    }

    public void setServerConnectionTimeout(Integer serverConnectionTimeout) {
        this.serverConnectionTimeout = serverConnectionTimeout;
    }

    public Integer getServerReadTimeout() {
        return serverReadTimeout;
    }

    public void setServerReadTimeout(Integer serverReadTimeout) {
        this.serverReadTimeout = serverReadTimeout;
    }

    public Integer getLastLoginConnectionTimeout() {
        return lastLoginConnectionTimeout;
    }

    public void setLastLoginConnectionTimeout(Integer lastLoginConnectionTimeout) {
        this.lastLoginConnectionTimeout = lastLoginConnectionTimeout;
    }

    public Integer getLastLoginReadTimeout() {
        return lastLoginReadTimeout;
    }

    public void setLastLoginReadTimeout(Integer lastLoginReadTimeout) {
        this.lastLoginReadTimeout = lastLoginReadTimeout;
    }



    public Long getSaveLocationInterval() {
        return saveLocationInterval;
    }

    public void setSaveLocationInterval(Long saveLocationInterval) {
        this.saveLocationInterval = saveLocationInterval;
    }

    public Long getSendLocationInterval() {
        return sendLocationInterval;
    }

    public void setSendLocationInterval(Long sendLocationInterval) {
        this.sendLocationInterval = sendLocationInterval;
    }

    public Long getUpdateTimeInterval() {
        return updateTimeInterval;
    }

    public void setUpdateTimeInterval(Long updateTimeInterval) {
        this.updateTimeInterval = updateTimeInterval;
    }

    public Long getDistanceInterval() {
        return distanceInterval;
    }

    public void setDistanceInterval(Long distanceInterval) {
        this.distanceInterval = distanceInterval;
    }

    public Long getRadius() {
        return radius;
    }

    public void setRadius(Long radius) {
        this.radius = radius;
    }

    public String getGpsIsChanged() {
        return gpsIsChanged;
    }

    public void setGpsIsChanged(String gpsIsChanged) {
        this.gpsIsChanged = gpsIsChanged;
    }

    public String getGpsIs24Hours() {
        return gpsIs24Hours;
    }

    public void setGpsIs24Hours(String gpsIs24Hours) {
        this.gpsIs24Hours = gpsIs24Hours;
    }

    public String getAlarmEnable() {
        return alarmEnable;
    }

    public void setAlarmEnable(String alarmEnable) {
        this.alarmEnable = alarmEnable;
    }

    public Integer getAlarmHours() {
        return alarmHours;
    }

    public void setAlarmHours(Integer alarmHours) {
        this.alarmHours = alarmHours;
    }

    public Integer getAlarmMinute() {
        return alarmMinute;
    }

    public void setAlarmMinute(Integer alarmMinute) {
        this.alarmMinute = alarmMinute;
    }

    public Integer getAlarmSecond() {
        return alarmSecond;
    }

    public void setAlarmSecond(Integer alarmSecond) {
        this.alarmSecond = alarmSecond;
    }

    public Integer getTimeIntervalSendLocation() {
        return timeIntervalSendLocation;
    }

    public void setTimeIntervalSendLocation(Integer timeIntervalSendLocation) {
        this.timeIntervalSendLocation = timeIntervalSendLocation;
    }

    public Integer getTimeIntervalGetLocation() {
        return timeIntervalGetLocation;
    }

    public void setTimeIntervalGetLocation(Integer timeIntervalGetLocation) {
        this.timeIntervalGetLocation = timeIntervalGetLocation;
    }

    private Integer timeIntervalSendLocation;
    private Integer timeIntervalGetLocation;




    public void setServerLocName(String serverLocName) {
        this.serverLocName = serverLocName;
    }

    public Integer getServerLocVersion() {
        return serverLocVersion;
    }

    public void setServerLocVersion(Integer serverLocVersion) {
        this.serverLocVersion = serverLocVersion;
    }

    public String getServerLocName() {
        return serverLocName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(Integer serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getServerName() {
        return serverName;
    }

    public String getSqliteName() {
        return sqliteName;
    }

    public void setSqliteName(String sqliteName) {
        this.sqliteName = sqliteName;
    }

    public Integer getSqliteVer() {
        return sqliteVer;
    }

    public void setSqliteVer(Integer sqliteVer) {
        this.sqliteVer = sqliteVer;
    }
}
