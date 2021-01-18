package base.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

public class ContentParcelAbleModel implements Parcelable {
    private String dataId, dataDesc, keyFieldName, icon;
    Integer indexData;

    private String fieldId, fieldName, fieldValue, selected;

    public ContentParcelAbleModel(Parcel in) {
        dataId = in.readString();
        dataDesc = in.readString();
        keyFieldName = in.readString();
        icon = in.readString();
        if (in.readByte() == 0) {
            indexData = null;
        } else {
            indexData = in.readInt();
        }
        fieldId = in.readString();
        fieldName = in.readString();
        fieldValue = in.readString();
        selected = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dataId);
        dest.writeString(dataDesc);
        dest.writeString(keyFieldName);
        dest.writeString(icon);
        if (indexData == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(indexData);
        }
        dest.writeString(fieldId);
        dest.writeString(fieldName);
        dest.writeString(fieldValue);
        dest.writeString(selected);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContentParcelAbleModel> CREATOR = new Creator<ContentParcelAbleModel>() {
        @Override
        public ContentParcelAbleModel createFromParcel(Parcel in) {
            return new ContentParcelAbleModel(in);
        }

        @Override
        public ContentParcelAbleModel[] newArray(int size) {
            return new ContentParcelAbleModel[size];
        }
    };

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

}