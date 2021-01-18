package base.endpoint;


import base.network.BaseResponseCode;

public class InActivatedJson {

    public class Request {
        private Integer v; //version
        private String un; //email
        private String im; // imei
        private Long mdi; //mobileDeviceId

        public void setV(Integer v) {
            this.v = v;
        }

        public void setUn(String un) {
            this.un = un;
        }

        public void setIm(String im) {
            this.im = im;
        }

        public void setMdi(Long mdi) {
            this.mdi = mdi;
        }
    }

    public class Callback extends BaseResponseCode {

    }
}
