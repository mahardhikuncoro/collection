package base.network;

import java.util.ArrayList;

import base.location.network.BaseNetworkCallback;

public class RetreiveJson {

    public class RetreiveRequest {
        private String regno, userid, tc, type, dataLevel, formname, listItemId, sectionId, field, tableName, multiple_doccode ;
        private ArrayList<Content> content;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getListItemId() {
            return listItemId;
        }

        public void setListItemId(String listItemId) {
            this.listItemId = listItemId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public void setContent(ArrayList<Content> content) {
            this.content = content;
        }

        public String getMultiple_doccode() {
            return multiple_doccode;
        }

        public void setMultiple_doccode(String multiple_doccode) {
            this.multiple_doccode = multiple_doccode;
        }
    }

    public class RetreiveCallback extends BaseNetworkCallback {
        private String allowDeleteItem, allowNewItem, allowBackPool, allowReject, allowSave, allowSubmit;
        private ArrayList<Data> data;

        public String getAllowDeleteItem() {
            return allowDeleteItem;
        }

        public void setAllowDeleteItem(String allowDeleteItem) {
            this.allowDeleteItem = allowDeleteItem;
        }

        public String getAllowSubmit() {
            return allowSubmit;
        }

        public void setAllowSubmit(String allowSubmit) {
            this.allowSubmit = allowSubmit;
        }

        public String getAllowSave() {
            return allowSave;
        }

        public String getAllowBackPool() {
            return allowBackPool;
        }

        public void setAllowBackPool(String allowBackPool) {
            this.allowBackPool = allowBackPool;
        }

        public String getAllowNewItem() {
            return allowNewItem;
        }

        public void setAllowNewItem(String allowNewItem) {
            this.allowNewItem = allowNewItem;
        }

        public String getAllowReject() {
            return allowReject;
        }

        public void setAllowReject(String allowReject) {
            this.allowReject = allowReject;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data{
            private String formName,
                    tableName,
                    sectionName,
                    sectionId,
                    sectionType,
                    dataDesc,
                    dataId,
                    keyFieldName,
                    icon;

            private ArrayList<Field> field;

            public String getSectionType() {
                return sectionType;
            }

            public void setSectionType(String sectionType) {
                this.sectionType = sectionType;
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

            public String getSectionId() {
                return sectionId;
            }

            public void setSectionId(String sectionId) {
                this.sectionId = sectionId;
            }

            public ArrayList<Field> getField() {
                return field;
            }

            public void setField(ArrayList<Field> field) {
                this.field = field;
            }

            public String getSectionName() {
                return sectionName;
            }

            public void setSectionName(String sectionName) {
                this.sectionName = sectionName;
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

            public class Field{
                private String fieldName, fieldValue, fieldId, keyFieldName, dataId, dataDesc, icon;
                private ArrayList<ReferenceParameter> referenceParameter;

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

                public String getFieldId() {
                    return fieldId;
                }

                public ArrayList<ReferenceParameter> getReferenceParameter() {
                    return referenceParameter;
                }

                public void setReferenceParameter(ArrayList<ReferenceParameter> referenceParameter) {
                    this.referenceParameter = referenceParameter;
                }

                public void setFieldId(String fieldId) {
                    this.fieldId = fieldId;
                }

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

                public class ReferenceParameter{
                    private String parameterName, parameterFieldValue;

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

        }
    }

    public class Content{
        private String fieldName, fieldValue;

        public String getFieldName() {
            return fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }
    }

}

