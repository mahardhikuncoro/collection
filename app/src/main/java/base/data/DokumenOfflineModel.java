package base.data;

public class DokumenOfflineModel {
    private Integer id;
    private String idtypedokumen;
    private String typedokumen;
    private String namadokumen;
    private String docid;
    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdtypedokumen() {
        return idtypedokumen;
    }

    public void setIdtypedokumen(String idtypedokumen) {
        this.idtypedokumen = idtypedokumen;
    }

    public String getTypedokumen() {
        return typedokumen;
    }

    public void setTypedokumen(String typedokumen) {
        this.typedokumen = typedokumen;
    }

    public String getNamadokumen() {
        return namadokumen;
    }

    public void setNamadokumen(String namadokumen) {
        this.namadokumen = namadokumen;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
