package base.sqlite;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class TaskListDetailModel implements Comparable<TaskListDetailModel>{
    private String customername;
    private String dataDesc;
    private String dataId;
    private String keyFieldName;
    private String ap_regno,
            customertype_id,
            customertype_desc,
            customerdocument_id,
            customerdocument_type,
            product_id,
            product_desc,
            plafon,
            track_id,
            track_name,
            formCode,
            formName,
            tableName,
            sectionName,
            sectionId,
            sectionType,
            icon,
            doc_code,
            doc_desc,
            doc_group,
            multiple_file;
    private String last_track_date;
    private Date sort_date;

    private ArrayList<Content> content;
    public String getNamaNasabah() {
        return customername;
    }

    public void setNamaNasabah(String customername) {
        this.customername = customername;
    }

    public String getIdNasabah() {
        return ap_regno;
    }

    public void setIdNasabah(String ap_regno) {
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

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getKeyFieldName() {
        return keyFieldName;
    }

    public void setKeyFieldName(String keyFieldName) {
        this.keyFieldName = keyFieldName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDoc_code() {
        return doc_code;
    }

    public void setDoc_code(String doc_code) {
        this.doc_code = doc_code;
    }

    public String getDoc_desc() {
        return doc_desc;
    }

    public void setDoc_desc(String doc_desc) {
        this.doc_desc = doc_desc;
    }

    public String getDoc_group() {
        return doc_group;
    }

    public void setDoc_group(String doc_group) {
        this.doc_group = doc_group;
    }

    public String getMultiple_file() {
        return multiple_file;
    }

    public void setMultiple_file(String multiple_file) {
        this.multiple_file = multiple_file;
    }

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public String getLast_track_date() {
        return last_track_date;
    }

    public void setLast_track_date(String last_track_date) {
        this.last_track_date = last_track_date;
    }

    public Date getSort_date() {
        return sort_date;
    }

    public void setSort_date(Date sort_date) {
        this.sort_date = sort_date;
    }

    @Override
    public int compareTo(@NonNull TaskListDetailModel taskListDetailModel) {
        return this.sort_date.compareTo(taskListDetailModel.sort_date) ;
    }

    public class Content{
        private String keyFieldName, dataId, dataDesc, icon;

        public String getKeyFieldName() {
            return keyFieldName;
        }

        public void setKeyFieldName(String keyFieldName) {
            this.keyFieldName = keyFieldName;
        }

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public String getDataDesc() {
            return dataDesc;
        }

        public void setDataDesc(String dataDesc) {
            this.dataDesc = dataDesc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
