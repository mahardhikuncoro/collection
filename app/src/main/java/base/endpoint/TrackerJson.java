package base.endpoint;

import java.util.List;

import base.network.BaseResponseCode;


public class TrackerJson {

    public class Request {
        private Integer v;
        private Long ri;
        private Long ui; //userId
        private Long bi; //branchId
        private Long rgi; //roleGroupId
        private String im; //imei
        private Integer app; //app
        private List<LocationBatchInsertList> lobr;

        public void setV(Integer v) {
            this.v = v;
        }

        public void setUi(Long ui) {
            this.ui = ui;
        }

        public void setBi(Long bi) {
            this.bi = bi;
        }

        public void setRgi(Long rgi) {
            this.rgi = rgi;
        }

        public void setIm(String im) {
            this.im = im;
        }

        public void setApp(Integer app) {
            this.app = app;
        }

        public void setRi(Long ri) {
            this.ri = ri;
        }

        public void setLobr(List<LocationBatchInsertList> lobr) {
            this.lobr = lobr;
        }

        public class LocationBatchInsertList {
            private Long d; //date
            private Double lat; //LATITUDE
            private Double lng; //LONGITUDE
            private Double dg; //degre
            private Integer a; //activity

            public void setD(Long d) {
                this.d = d;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }

            public void setDg(Double dg) {
                this.dg = dg;
            }

            public void setA(Integer a) {
                this.a = a;
            }
        }
    }

    public class Callback extends BaseResponseCode {
        private Long ri;

        public Long getRi() {
            return ri;
        }
    }
}
