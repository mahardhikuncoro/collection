package base.network;

import java.util.List;

public class PushNotificationJson {

    public class registerTokenIdRequest{
        private PushDatamodel model;

        public PushDatamodel getModel() {
            return model;
        }

        public void setModel(PushDatamodel model) {
            this.model = model;
        }

        public class PushDatamodel {
            private String criteria1;
            private String criteria2;
            private String criteria3;
            private String criteria4;
            private String criteria5;
            private String instanceIdToken;
            private String createdBy;

            public String getCriteria1() {
                return criteria1;
            }

            public void setCriteria1(String criteria1) {
                this.criteria1 = criteria1;
            }

            public String getCriteria2() {
                return criteria2;
            }

            public void setCriteria2(String criteria2) {
                this.criteria2 = criteria2;
            }

            public String getCriteria3() {
                return criteria3;
            }

            public void setCriteria3(String criteria3) {
                this.criteria3 = criteria3;
            }

            public String getCriteria4() {
                return criteria4;
            }

            public void setCriteria4(String criteria4) {
                this.criteria4 = criteria4;
            }

            public String getCriteria5() {
                return criteria5;
            }

            public void setCriteria5(String criteria5) {
                this.criteria5 = criteria5;
            }

            public String getInstanceIdToken() {
                return instanceIdToken;
            }

            public void setInstanceIdToken(String instanceIdToken) {
                this.instanceIdToken = instanceIdToken;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }
        }
    }

    public class registerTokenIdCallback {

        private String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }
}
