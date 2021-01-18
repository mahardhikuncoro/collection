package base.endpoint;

import base.location.network.BaseNetworkCallback;

public class UploadImageJson  {
    public class Callback extends BaseNetworkCallback {
        private String PHOTO_PROFILE;

        public String getPHOTO_PROFILE() {
            return PHOTO_PROFILE;
        }

        public void setPHOTO_PROFILE(String PHOTO_PROFILE) {
            this.PHOTO_PROFILE = PHOTO_PROFILE;
        }
    }
}
