package base.network;

import java.util.ArrayList;

import base.location.network.BaseNetworkCallback;

public class RetreiveNewListJson {

    public class RetreiveRequest extends BaseRequest{
    }

    public class RetreiveCallback extends BaseNetworkCallback {
        private String allowNewItem;
        private ArrayList<Data> data;

        public String getAllowNewItem() {
            return allowNewItem;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public class Data{
            private String doc_code,
                    doc_desc,
                    doc_group,
                    multiple_file;
            private ArrayList<Content> content;

            public String getDoc_code() {
                return doc_code;
            }

            public String getDoc_desc() {
                return doc_desc;
            }

            public String getDoc_group() {
                return doc_group;
            }

            public String getMultiple_file() {
                return multiple_file;
            }

            public ArrayList<Content> getContent() {
                return content;
            }

            public void setContent(ArrayList<Content> content) {
                this.content = content;
            }
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

}

