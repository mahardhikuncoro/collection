package base.network;

import java.util.ArrayList;

public class TaskListJson {

    public class TasklistRequest{
        private String userid, type, tc;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTc() {
            return tc;
        }

        public void setTc(String tc) {
            this.tc = tc;
        }
    }

    public class TasklistCallback{
        private String status, message;
        private ArrayList<Data> data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data{
            private String ap_regno,
                    customertype_id,
                    customertype_desc,
                    customername,
                    customerdocument_id,
                    customerdocument_type,
                    product_id,
                    product_desc,
                    plafon,
                    track_id,
                    track_name,
                    formCode,
                    icon;
            private String last_track_date;


            public String getAp_regno() {
                return ap_regno;
            }

            public void setAp_regno(String ap_regno) {
                this.ap_regno = ap_regno;
            }

            public String getCustomertype_id() {
                return customertype_id;
            }

            public void setCustomertype_id(String customertype_id) {
                this.customertype_id = customertype_id;
            }

            public String getCustomertype_desc() {
                return customertype_desc;
            }

            public void setCustomertype_desc(String customertype_desc) {
                this.customertype_desc = customertype_desc;
            }

            public String getCustomername() {
                return customername;
            }

            public void setCustomername(String customername) {
                this.customername = customername;
            }

            public String getCustomerdocument_id() {
                return customerdocument_id;
            }

            public void setCustomerdocument_id(String customerdocument_id) {
                this.customerdocument_id = customerdocument_id;
            }

            public String getCustomerdocument_type() {
                return customerdocument_type;
            }

            public void setCustomerdocument_type(String customerdocument_type) {
                this.customerdocument_type = customerdocument_type;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getProduct_desc() {
                return product_desc;
            }

            public void setProduct_desc(String product_desc) {
                this.product_desc = product_desc;
            }

            public String getPlafon() {
                return plafon;
            }

            public void setPlafon(String plafon) {
                this.plafon = plafon;
            }

            public String getTrack_id() {
                return track_id;
            }

            public void setTrack_id(String track_id) {
                this.track_id = track_id;
            }

            public String getTrack_name() {
                return track_name;
            }

            public void setTrack_name(String track_name) {
                this.track_name = track_name;
            }

            public String getFormCode() {
                return formCode;
            }

            public void setFormCode(String formCode) {
                this.formCode = formCode;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLast_track_date() {
                return last_track_date;
            }

            public void setLast_track_date(String last_track_date) {
                this.last_track_date = last_track_date;
            }
        }
        }


}
