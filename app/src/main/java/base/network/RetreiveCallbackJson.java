package base.network;

import java.util.ArrayList;
import java.util.List;

import base.location.network.BaseNetworkCallback;
import base.sqlite.DataModel;

public class RetreiveCallbackJson extends BaseNetworkCallback {

    private String allowDeleteItem;
    private String needChangeForm;
    private String sectionIdAfter;
    private List<DataModel> data = new ArrayList<>();

    public String getAllowDeleteItem() {
        return allowDeleteItem;
    }

    public void setAllowDeleteItem(String allowDeleteItem) {
        this.allowDeleteItem = allowDeleteItem;
    }

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    public String getNeedChangeForm() {
        return needChangeForm;
    }

    public String getSectionIdAfter() {
        return sectionIdAfter;
    }
}
