package base.network;

import java.util.ArrayList;
import java.util.List;

import base.location.network.BaseNetworkCallback;
import base.sqlite.ParameterModel;

public class FormJson {

    public class RequestForm{
        private String type, field ,userid, tc, regno;

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

        public void setTc(String tc) {
            this.tc = tc;
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
    }

    public class CallbackForm{
        private String status, message;
        private List<Data> data = new ArrayList<>();

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

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    public class Data extends BaseNetworkCallback {
        private String tc, formCode, formVersion, sectionId, sectionName, sectionSequence, tableName;
        private List<Field> field = new ArrayList<>();
        private List<Content> content = new ArrayList<>();

        public String getTc() {
            return tc;
        }

        public void setTc(String tc) {
            this.tc = tc;
        }

        public String getFormCode() {
            return formCode;
        }

        public void setFormCode(String formCode) {
            this.formCode = formCode;
        }

        public String getFormVersion() {
            return formVersion;
        }

        public void setFormVersion(String formVersion) {
            this.formVersion = formVersion;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getSectionSequence() {
            return sectionSequence;
        }

        public void setSectionSequence(String sectionSequence) {
            this.sectionSequence = sectionSequence;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public List<Field> getField() {
            return field;
        }

        public void setField(List<Field> field) {
            this.field = field;
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public class  Field{
        private String fieldId,
                isKey,
                fieldName,
                fieldLabel,
                fieldType,
                fieldDataType,
                fieldDataLength,
                fieldMandatory,
                fieldRule,
                hasReference,
                readOnly,
                fieldSave,
                referenceType,
                referenceName,
                hasReferenceParameter;
        private List<ReferenceParameter>referenceParameter;

        public String getFieldId() {
            return fieldId;
        }

        public void setFieldId(String fieldId) {
            this.fieldId = fieldId;
        }

        public String getIsKey() {
            return isKey;
        }

        public void setIsKey(String isKey) {
            this.isKey = isKey;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldLabel() {
            return fieldLabel;
        }

        public void setFieldLabel(String fieldLabel) {
            this.fieldLabel = fieldLabel;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getFieldDataType() {
            return fieldDataType;
        }

        public void setFieldDataType(String fieldDataType) {
            this.fieldDataType = fieldDataType;
        }

        public String getFieldDataLength() {
            return fieldDataLength;
        }

        public void setFieldDataLength(String fieldDataLength) {
            this.fieldDataLength = fieldDataLength;
        }

        public String getFieldMandatory() {
            return fieldMandatory;
        }

        public void setFieldMandatory(String fieldMandatory) {
            this.fieldMandatory = fieldMandatory;
        }

            public String getFieldRule() {
                return fieldRule;
            }

            public void setFieldRule(String fieldRule) {
                this.fieldRule = fieldRule;
            }

            public String getHasReference() {
            return hasReference;
        }

        public void setHasReference(String hasReference) {
            this.hasReference = hasReference;
        }

        public String getReferenceType() {
            return referenceType;
        }

        public void setReferenceType(String referenceType) {
            this.referenceType = referenceType;
        }

        public String getReferenceName() {
            return referenceName;
        }

        public void setReferenceName(String referenceName) {
            this.referenceName = referenceName;
        }

        public String getHasReferenceParameter() {
            return hasReferenceParameter;
        }

        public void setHasReferenceParameter(String hasReferenceParameter) {
            this.hasReferenceParameter = hasReferenceParameter;
        }

        public String getReadOnly() {
            return readOnly;
        }

        public void setReadOnly(String readOnly) {
            this.readOnly = readOnly;
        }

            public String getFieldSave() {
                return fieldSave;
            }

            public void setFieldSave(String fieldSave) {
                this.fieldSave = fieldSave;
            }

            public List<ReferenceParameter> getReferenceParameter() {
            return referenceParameter;
        }

        public void setReferenceParameter(List<ReferenceParameter> referenceParameter) {
            this.referenceParameter = referenceParameter;
        }

            public class ReferenceParameter{
            private String parameterName, parameterFieldValue, parameterId;

                public String getParameterId() {
                    return parameterId;
                }

                public void setParameterId(String parameterId) {
                    this.parameterId = parameterId;
                }

                public String getParameterName() {
                return parameterName;
            }

            public void setParameterName(String parameterName) {
                this.parameterName = parameterName;
            }

            public String getParameterFieldValue() {
                return parameterFieldValue;
            }

            public void setParameterFieldValue(String parameterFieldValue) {
                this.parameterFieldValue = parameterFieldValue;
            }
        }
    }

    public class Content{
            private String dataId, dataDesc;

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
    }
    }
    }

}
