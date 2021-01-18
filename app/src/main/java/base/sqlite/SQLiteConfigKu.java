package base.sqlite;

import android.content.Context;

import java.util.Properties;

import base.AssetsPropertyReader;

public class SQLiteConfigKu {

    private String server;
    private String serverOtp;
    private Integer serverVersion;
    private Integer serverConnectionTimeout;
    private Integer serverReadTimeout;
    private String databaseName;
    private Integer databaseVersion;

    public SQLiteConfigKu(Context context) {
        AssetsPropertyReader assetsPropertyReader = new AssetsPropertyReader(context);
        Properties properties = null;
        properties = assetsPropertyReader.getProperties("sqlite-config-bpdbalicollection.properties");
        setServerVersion(Integer.parseInt(properties.getProperty("SERVER_VERSION")));
        setServer(properties.getProperty("SERVER"));
        setServerConnectionTimeout(Integer.parseInt(properties.getProperty("SERVER_CONNECTION_TIMEOUT")));
        setServerReadTimeout(Integer.parseInt(properties.getProperty("SERVER_READ_TIMEOUT")));

        setDatabaseName(properties.getProperty("DATABASE_NAME"));
        setDatabaseVersion(Integer.parseInt(properties.getProperty("DATABASE_VER")));

        setServerOtp(properties.getProperty("SERVER_OTP"));

    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(Integer serverVersion) {
        this.serverVersion = serverVersion;
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

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Integer getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(Integer databaseVersion) {
        this.databaseVersion = databaseVersion;
    }

    public String getServerOtp() {
        return serverOtp;
    }

    public void setServerOtp(String serverOtp) {
        this.serverOtp = serverOtp;
    }
}