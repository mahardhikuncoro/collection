package base.sqlite;

import java.util.List;

public class FieldModel {

    private String fieldId, fieldName, fieldValue, fieldFlagEnabled, fieldFlagHide;
    private List<ContentModel> fieldValueList;

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

    public List<ContentModel> getFieldValueList() {
        return fieldValueList;
    }

    public void setFieldValueList(List<ContentModel> fieldValueList) {
        this.fieldValueList = fieldValueList;
    }
    public String getFieldFlagEnabled() {
        return fieldFlagEnabled;
    }

    public void setFieldFlagEnabled(String fieldFlagEnabled) {
        this.fieldFlagEnabled = fieldFlagEnabled;
    }

    public String getFieldFlagHide() {
        return fieldFlagHide;
    }

    public void setFieldFlagHide(String fieldFlagHide) {
        this.fieldFlagHide = fieldFlagHide;
    }
}
