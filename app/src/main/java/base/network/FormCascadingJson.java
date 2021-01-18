package base.network;

import java.util.ArrayList;
import java.util.List;

import base.location.network.BaseNetworkCallback;
import base.network.FormCascadingJson.CallbackForm.DataCascading.Content;
import base.sqlite.ParameterModel;

public class FormCascadingJson  {

    public class RequestForm {
        private String type, field ;
        private String regno, userid, tc, dataLevel, formname, sectionId, tableName;
        private ArrayList<Content> content;
        private ArrayList<ParameterModel> parameter;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public ArrayList<ParameterModel> getParameter() {
            return parameter;
        }

        public void setParameter(ArrayList<ParameterModel> parameter) {
            this.parameter = parameter;
        }

        public String getRegno() {
            return regno;
        }

        public void setRegno(String regno) {
            this.regno = regno;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTc() {
            return tc;
        }

        public void setTc(String tc) {
            this.tc = tc;
        }

        public String getDataLevel() {
            return dataLevel;
        }

        public void setDataLevel(String dataLevel) {
            this.dataLevel = dataLevel;
        }

        public String getFormname() {
            return formname;
        }

        public void setFormname(String formname) {
            this.formname = formname;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public void setContent(ArrayList<Content> content) {
            this.content = content;
        }
    }

    public class CallbackForm extends BaseNetworkCallback{
        private List<DataCascading> data = new ArrayList<>();

        public List<DataCascading> getData() {
            return data;
        }
        public void setData(List<DataCascading> data) {
            this.data = data;
        }

    public class DataCascading{
        private String field;
        private List<Content> content = new ArrayList<>();

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }



    public class Content{
            private String dataId, dataDesc;
        private String fieldName, fieldValue;

        public String getDataId() {
            return dataId;
        }

        public String getDataDesc() {
            return dataDesc;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }
    }


    }
    }

}
