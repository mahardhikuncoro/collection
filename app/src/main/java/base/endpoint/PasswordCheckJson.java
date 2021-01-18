package base.endpoint;

import base.network.BaseResponseCode;

/**
 * Created by ADMINSMMF on 3/19/2018.
 */

public class PasswordCheckJson {
    public class PasswordCheckRequest {

        private Integer v;    // version
        private String u;     // username
        private String p;     // password

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }



    }

    public class Callback extends BaseResponseCode {

    }

}
