package base.sqlite;


public class ContentModel {
    private String dataId,
            dataDesc,
            keyFieldName,
            icon,
            formName,
            tableName,
            sectionName,
            contentId;
    Integer indexData;

//    public ContentModel(String dataId, String dataDesc) {
//        this.dataId = dataId;
//        this.dataDesc = dataDesc;
//    }

    private String fieldId, fieldName, fieldValue, selected;

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

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

    public Integer getIndexData() {
        return indexData;
    }

    public void setIndexData(Integer indexData) {
        this.indexData = indexData;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setKeyFieldName(String keyFieldName) {
        this.keyFieldName = keyFieldName;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKeyFieldName() {
        return keyFieldName;
    }

    public String getIcon() {
        return icon;
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

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    //    @Override
//    public String toString() {
//        return dataDesc;
//    }


}