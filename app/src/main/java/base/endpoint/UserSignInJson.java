package base.endpoint;


import base.network.BaseResponseCode;

public class UserSignInJson {

    public class Request {

        private Integer v;    // version
        private String un;     // username
        private String p;     // password
        private String im;    // deviceImei
        private String k;      // hashKey

        public void setK(String k) {
            this.k = k;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public void setUn(String un) {
            this.un = un;
        }

        public void setP(String p) {
            this.p = p;
        }

        public void setIm(String im) {
            this.im = im;
        }

    }

    public class Callback extends BaseResponseCode {

        private Long bi;      // branchId;
        private String bt;    // branchType;
        private String bn;    // branchName;
        private Long ui;      // mufinsUserId;
        private String n;     // name;
        private String rn;    // roleName;
        private Long rg;      // roleGroupId;

        public Long getBi() {
            return bi;
        }

        public String getBt() {
            return bt;
        }

        public String getBn() {
            return bn;
        }

        public Long getUi() {
            return ui;
        }

        public String getN() {
            return n;
        }

        public String getRn() {
            return rn;
        }

        public Long getRg() {
            return rg;
        }
    }
}
