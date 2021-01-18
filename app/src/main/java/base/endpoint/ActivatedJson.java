package base.endpoint;

import base.network.BaseResponseCode;

public class ActivatedJson {

    public class Request {
        private Integer v;  // version
        private String un;  // username
        private String im;  // imei
        private String mf;  // manufacture
        private String b;   // brand
        private String p;   // product
        private String m;   // model
        private String os;  // os
        private String pmi; // pushMessagingId
        private String appv;
        private Long uid;

        public void setUid(Long uid) {
            this.uid = uid;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public void setUn(String un) {
            this.un = un;
        }

        public void setIm(String im) {
            this.im = im;
        }

        public void setMf(String mf) {
            this.mf = mf;
        }

        public void setB(String b) {
            this.b = b;
        }

        public void setP(String p) {
            this.p = p;
        }

        public void setM(String m) {
            this.m = m;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public void setPmi(String pmi) {
            this.pmi = pmi;
        }

        public void setAppv(String appv) {
            this.appv = appv;
        }

    }

    public class Callback extends BaseResponseCode {

        private Long mdi; //mobile device id

        public Long getMdi() {
            return mdi;
        }
    }

}
