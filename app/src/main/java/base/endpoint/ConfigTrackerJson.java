package base.endpoint;

import java.util.ArrayList;
import java.util.List;

import base.network.BaseResponseCode;


public class ConfigTrackerJson {

    public class Callback extends BaseResponseCode {

        private List<ConfigList> cl = new ArrayList<>();

        public List<ConfigList> getCl() {
            return cl;
        }

        public class ConfigList{
            private Long ci;
            private String k;
            private String v;

            public Long getCi() {
                return ci;
            }

            public String getK() {
                return k;
            }

            public String getV() {
                return v;
            }
        }
    }
}
