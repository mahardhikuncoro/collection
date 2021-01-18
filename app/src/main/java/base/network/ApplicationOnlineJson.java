package base.network;

/**
 * Created by christian on 4/6/18.
 */

public class ApplicationOnlineJson {

    public class ApplicationOnlineRequest{
        private Integer version;
        private String paket;
        private String produk;
        private Double harga;
        private Integer tahunkendaraan;
        private Long cabang_smmf;
        private String nama_apk;
        private String telp;
        private String email;
        private String tipe_kendaraan;
        private Integer tenor;
        private Double dp;
        private String noktp;
        private String gender;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getPaket() {
            return paket;
        }

        public void setPaket(String paket) {
            this.paket = paket;
        }

        public String getProduk() {
            return produk;
        }

        public void setProduk(String produk) {
            this.produk = produk;
        }

        public Double getHarga() {
            return harga;
        }

        public void setHarga(Double harga) {
            this.harga = harga;
        }

        public Integer getTahunkendaraan() {
            return tahunkendaraan;
        }

        public void setTahunkendaraan(Integer tahunkendaraan) {
            this.tahunkendaraan = tahunkendaraan;
        }

        public Long getCabang_smmf() {
            return cabang_smmf;
        }

        public void setCabang_smmf(Long cabang_smmf) {
            this.cabang_smmf = cabang_smmf;
        }

        public String getNama_apk() {
            return nama_apk;
        }

        public void setNama_apk(String nama_apk) {
            this.nama_apk = nama_apk;
        }

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getTipe_kendaraan() {
            return tipe_kendaraan;
        }

        public void setTipe_kendaraan(String tipe_kendaraan) {
            this.tipe_kendaraan = tipe_kendaraan;
        }

        public Integer getTenor() {
            return tenor;
        }

        public void setTenor(Integer tenor) {
            this.tenor = tenor;
        }

        public Double getDp() {
            return dp;
        }

        public void setDp(Double dp) {
            this.dp = dp;
        }

        public String getNoktp() {
            return noktp;
        }

        public void setNoktp(String noktp) {
            this.noktp = noktp;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public class ApplicationOnlineCallback{
        private String responseStatus;
        private Long id;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }



}
