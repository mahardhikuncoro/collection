package base.network;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import id.collection.mobile.application.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkClient {
    public static okhttp3.OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
//DIBUKA UNTUK DEBUG(VISIBLE TRACE LOGCAT)
            if(BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug"))
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            else
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE));
            builder.connectTimeout(300, TimeUnit.SECONDS);
            builder.readTimeout(300, TimeUnit.SECONDS);
            builder.writeTimeout(300, TimeUnit.SECONDS);
            return builder.build();

            //DIBUKA UNTUK PRODUCTION(INVISIBLE TRACE LOGCAT)
//            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                    .connectTimeout(300, TimeUnit.SECONDS)
//                    .readTimeout(300, TimeUnit.SECONDS)
//                    .writeTimeout(300, TimeUnit.SECONDS)
//                    .build();
//            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}