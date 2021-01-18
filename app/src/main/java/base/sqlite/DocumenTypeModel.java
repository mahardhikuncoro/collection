package base.sqlite;

public class DocumenTypeModel {
    private String document_name;
    private String document_id;

    public DocumenTypeModel(String dokumenId,String namaDokumen){
        this.document_name= namaDokumen;
        this.document_id=dokumenId;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    @Override
    public String toString() {
        return document_name;
    }
}
