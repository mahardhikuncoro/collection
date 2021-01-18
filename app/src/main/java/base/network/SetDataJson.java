package base.network;

import java.util.ArrayList;

import base.location.network.BaseNetworkCallback;
import base.sqlite.FieldModel;

public class SetDataJson {

    public class SetDataRequest extends BaseRequest {
        private String  status, rejectreason, rejectdesc;
        private ArrayList<FieldModel> field;

        public ArrayList<FieldModel> getField() {
            return field;
        }

        public String getRejectreason() {
            return rejectreason;
        }

        public void setRejectreason(String rejectreason) {
            this.rejectreason = rejectreason;
        }

        public String getRejectdesc() {
            return rejectdesc;
        }

        public void setRejectdesc(String rejectdesc) {
            this.rejectdesc = rejectdesc;
        }

        public void setField(ArrayList<FieldModel> field) {
            this.field = field;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public class Field{
            private String fieldName, fieldValue;

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getFieldValue() {
                return fieldValue;
            }

            public void setFieldValue(String fieldValue) {
                this.fieldValue = fieldValue;
            }
        }

    }

    public class SetDataCallback extends BaseNetworkCallback {
//        private String status, message;
//        private ArrayList<Data> data;
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//
//        public ArrayList<Data> getData() {
//            return data;
//        }
//
//        public void setData(ArrayList<Data> data) {
//            this.data = data;
//        }
//
//        public class Data{
//            private String formName, tableName, sectionName;
//            private ArrayList<Field> field;
//
//            public String getFormName() {
//                return formName;
//            }
//
//            public void setFormName(String formName) {
//                this.formName = formName;
//            }
//
//            public String getTableName() {
//                return tableName;
//            }
//
//            public void setTableName(String tableName) {
//                this.tableName = tableName;
//            }
//
//            public ArrayList<Field> getField() {
//                return field;
//            }
//
//            public void setField(ArrayList<Field> field) {
//                this.field = field;
//            }
//
//            public String getSectionName() {
//                return sectionName;
//            }
//
//            public void setSectionName(String sectionName) {
//                this.sectionName = sectionName;
//            }
//
//            public class Field{
//                private String fieldName, fieldValue, fieldId;
//
//                public String getFieldName() {
//                    return fieldName;
//                }
//
//                public void setFieldName(String fieldName) {
//                    this.fieldName = fieldName;
//                }
//
//                public String getFieldValue() {
//                    return fieldValue;
//                }
//
//                public void setFieldValue(String fieldValue) {
//                    this.fieldValue = fieldValue;
//                }
//
//                public String getFieldId() {
//                    return fieldId;
//                }
//            }
//
//        }
    }

}

