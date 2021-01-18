package base.sqlite;

public class ParameterModel {
    private String parameterName,parameterValue, parameterFieldValue;
    private String fieldName, fieldValue;

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public void setParameterFieldValue(String parameterFieldValue) {
        this.parameterFieldValue = parameterFieldValue;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
