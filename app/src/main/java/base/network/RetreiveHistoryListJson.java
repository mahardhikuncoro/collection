package base.network;

import java.util.ArrayList;

import base.location.network.BaseNetworkCallback;
import base.sqlite.TasklistHistoryModel;

public class RetreiveHistoryListJson {

    public class RetreiveRequest extends BaseRequest{
    }

    public class RetreiveCallback extends BaseNetworkCallback {
        private ArrayList<TasklistHistoryModel> data;
        private ArrayList<TasklistHistoryModel> current;
        private ArrayList<TasklistHistoryModel> history;

        public ArrayList<TasklistHistoryModel> getData() {
            return data;
        }
        public void setData(ArrayList<TasklistHistoryModel> data) {
            this.data = data;
        }

        public ArrayList<TasklistHistoryModel> getCurrent() {
            return current;
        }

        public void setCurrent(ArrayList<TasklistHistoryModel> current) {
            this.current = current;
        }

        public ArrayList<TasklistHistoryModel> getHistory() {
            return history;
        }

        public void setHistory(ArrayList<TasklistHistoryModel> history) {
            this.history = history;
        }
    }

}

