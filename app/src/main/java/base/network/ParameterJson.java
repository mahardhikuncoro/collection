package base.network;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 4/16/18.
 */

public class ParameterJson {
    public class PinjamanCallback {
        private List<ParamPinjaman> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<ParamPinjaman> getL() {
            return l;
        }

        public class ParamPinjaman {
            private Long id;
            private String parameter;
            private String description;
            private Integer value_mobil;
            private Integer value_motor;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getParameter() {
                return parameter;
            }

            public void setParameter(String parameter) {
                this.parameter = parameter;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Integer getValue_mobil() {
                return value_mobil;
            }

            public void setValue_mobil(Integer value_mobil) {
                this.value_mobil = value_mobil;
            }

            public Integer getValue_motor() {
                return value_motor;
            }

            public void setValue_motor(Integer value_motor) {
                this.value_motor = value_motor;
            }
        }
    }

    public class PinjamanExtendedCallback {
        private List<ParamPinjamanExtended> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<ParamPinjamanExtended> getL() {
            return l;
        }

        public class ParamPinjamanExtended {
            private Long id;
            private String produk;
            private String type;
            private Integer nilai;
            private String satuan_nilai;
            private Integer usia_batas_bawah;
            private Integer usia_batas_atas;
            private String keterangan;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getProduk() {
                return produk;
            }

            public void setProduk(String produk) {
                this.produk = produk;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Integer getNilai() {
                return nilai;
            }

            public void setNilai(Integer nilai) {
                this.nilai = nilai;
            }

            public String getSatuan_nilai() {
                return satuan_nilai;
            }

            public void setSatuan_nilai(String satuan_nilai) {
                this.satuan_nilai = satuan_nilai;
            }

            public Integer getUsia_batas_bawah() {
                return usia_batas_bawah;
            }

            public void setUsia_batas_bawah(Integer usia_batas_bawah) {
                this.usia_batas_bawah = usia_batas_bawah;
            }

            public Integer getUsia_batas_atas() {
                return usia_batas_atas;
            }

            public void setUsia_batas_atas(Integer usia_batas_atas) {
                this.usia_batas_atas = usia_batas_atas;
            }

            public String getKeterangan() {
                return keterangan;
            }

            public void setKeterangan(String keterangan) {
                this.keterangan = keterangan;
            }
        }
    }

    public class PenyusutanCallback {
        private List<ParamPenyusutan> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<ParamPenyusutan> getL() {
            return l;
        }

        public class ParamPenyusutan {
            private Long id;
            private Integer parameter;
            private Double value;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Integer getParameter() {
                return parameter;
            }

            public void setParameter(Integer parameter) {
                this.parameter = parameter;
            }

            public Double getValue() {
                return value;
            }

            public void setValue(Double value) {
                this.value = value;
            }
        }
    }

    public class TenorCallback {
        private List<ParamTenor> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<ParamTenor> getL() {
            return l;
        }

        public class ParamTenor {
            private Long id;
            private String parameter;
            private Double batas_atas;
            private Double batas_bawah;
            private Integer value;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getParameter() {
                return parameter;
            }

            public void setParameter(String parameter) {
                this.parameter = parameter;
            }

            public Double getBatas_atas() {
                return batas_atas;
            }

            public void setBatas_atas(Double batas_atas) {
                this.batas_atas = batas_atas;
            }

            public Double getBatas_bawah() {
                return batas_bawah;
            }

            public void setBatas_bawah(Double batas_bawah) {
                this.batas_bawah = batas_bawah;
            }

            public Integer getValue() {
                return value;
            }

            public void setValue(Integer value) {
                this.value = value;
            }
        }
    }

    public class KatAsuransiCallback {
        private List<KatAsuransi> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<KatAsuransi> getL() {
            return l;
        }

        public class KatAsuransi {
            private Long id;
            private Integer batas_bawah;
            private Integer batas_atas;
            private String value;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Integer getBatas_bawah() {
                return batas_bawah;
            }

            public void setBatas_bawah(Integer batas_bawah) {
                this.batas_bawah = batas_bawah;
            }

            public Integer getBatas_atas() {
                return batas_atas;
            }

            public void setBatas_atas(Integer batas_atas) {
                this.batas_atas = batas_atas;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public class IndicatorHargaCallback {
        private List<IndicatorHarga> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<IndicatorHarga> getL() {
            return l;
        }

        public class IndicatorHarga {
            private Long id;
            private Double batas_bawah;
            private Double batas_atas;
            private String indicator_harga;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Double getBatas_bawah() {
                return batas_bawah;
            }

            public void setBatas_bawah(Double batas_bawah) {
                this.batas_bawah = batas_bawah;
            }

            public Double getBatas_atas() {
                return batas_atas;
            }

            public void setBatas_atas(Double batas_atas) {
                this.batas_atas = batas_atas;
            }

            public String getIndicator_harga() {
                return indicator_harga;
            }

            public void setIndicator_harga(String indicator_harga) {
                this.indicator_harga = indicator_harga;
            }
        }
    }

    public class IndicatorTahunCallback {
        private List<IndicatorTahun> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<IndicatorTahun> getL() {
            return l;
        }

        public class IndicatorTahun {
            private Long id;
            private Integer batas_bawah;
            private Integer batas_atas;
            private String indicator_tahun;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Integer getBatas_bawah() {
                return batas_bawah;
            }

            public void setBatas_bawah(Integer batas_bawah) {
                this.batas_bawah = batas_bawah;
            }

            public Integer getBatas_atas() {
                return batas_atas;
            }

            public void setBatas_atas(Integer batas_atas) {
                this.batas_atas = batas_atas;
            }

            public String getIndicator_tahun() {
                return indicator_tahun;
            }

            public void setIndicator_tahun(String indicator_tahun) {
                this.indicator_tahun = indicator_tahun;
            }
        }
    }

    public class RateBungaCallback {
        private List<RateBunga> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<RateBunga> getL() {
            return l;
        }

        public class RateBunga {
            private Long id;
            private String tipe;
            private String produk;
            private String indicator_tahun;
            private Double rate_tahun_1;
            private Double rate_tahun_2;
            private Double rate_tahun_3;
            private Double rate_tahun_4;
            private Double rate_tahun_5;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getTipe() {
                return tipe;
            }

            public void setTipe(String tipe) {
                this.tipe = tipe;
            }

            public String getProduk() {
                return produk;
            }

            public void setProduk(String produk) {
                this.produk = produk;
            }

            public String getIndicator_tahun() {
                return indicator_tahun;
            }

            public void setIndicator_tahun(String indicator_tahun) {
                this.indicator_tahun = indicator_tahun;
            }

            public Double getRate_tahun_1() {
                return rate_tahun_1;
            }

            public void setRate_tahun_1(Double rate_tahun_1) {
                this.rate_tahun_1 = rate_tahun_1;
            }

            public Double getRate_tahun_2() {
                return rate_tahun_2;
            }

            public void setRate_tahun_2(Double rate_tahun_2) {
                this.rate_tahun_2 = rate_tahun_2;
            }

            public Double getRate_tahun_3() {
                return rate_tahun_3;
            }

            public void setRate_tahun_3(Double rate_tahun_3) {
                this.rate_tahun_3 = rate_tahun_3;
            }

            public Double getRate_tahun_4() { return rate_tahun_4; }

            public void setRate_tahun_4(Double rate_tahun_4) {
                this.rate_tahun_4 = rate_tahun_4;
            }

            public Double getRate_tahun_5() { return rate_tahun_5; }

            public void setRate_tahun_5(Double rate_tahun_5) { this.rate_tahun_5 = rate_tahun_5; }
        }
    }

    public class RateAsuransiCallback {
        private List<RateAsuransi> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<RateAsuransi> getL() {
            return l;
        }

        public class RateAsuransi {
            private Long id;
            private String tipe;
            private String indicator_harga;
            private String kategori_asuransi;
            private Double rate_wilayah_1;
            private Double rate_wilayah_2;
            private Double rate_wilayah_3;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getTipe() {
                return tipe;
            }

            public void setTipe(String tipe) {
                this.tipe = tipe;
            }

            public String getIndicator_harga() {
                return indicator_harga;
            }

            public void setIndicator_harga(String indicator_harga) {
                this.indicator_harga = indicator_harga;
            }

            public String getKategori_asuransi() {
                return kategori_asuransi;
            }

            public void setKategori_asuransi(String kategori_asuransi) {
                this.kategori_asuransi = kategori_asuransi;
            }

            public Double getRate_wilayah_1() {
                return rate_wilayah_1;
            }

            public void setRate_wilayah_1(Double rate_wilayah_1) {
                this.rate_wilayah_1 = rate_wilayah_1;
            }

            public Double getRate_wilayah_2() {
                return rate_wilayah_2;
            }

            public void setRate_wilayah_2(Double rate_wilayah_2) {
                this.rate_wilayah_2 = rate_wilayah_2;
            }

            public Double getRate_wilayah_3() {
                return rate_wilayah_3;
            }

            public void setRate_wilayah_3(Double rate_wilayah_3) {
                this.rate_wilayah_3 = rate_wilayah_3;
            }
        }

    }

    public class RateAsuransiMotorCallback {
        private List<RateAsuransiMotor> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<RateAsuransiMotor> getL() {
            return l;
        }

        public class RateAsuransiMotor {
            private Integer id;
            private String produk;
            private String tipe;
            private Integer tenor;
            private Double value_asuransi;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getProduk() {
                return produk;
            }

            public void setProduk(String produk) {
                this.produk = produk;
            }

            public String getTipe() {
                return tipe;
            }

            public void setTipe(String tipe) {
                this.tipe = tipe;
            }

            public Integer getTenor() {
                return tenor;
            }

            public void setTenor(Integer tenor) {
                this.tenor = tenor;
            }

            public Double getValue_asuransi() {
                return value_asuransi;
            }

            public void setValue_asuransi(Double value_asuransi) {
                this.value_asuransi = value_asuransi;
            }
        }

    }

    public class RateAsuransiJiwaCallback {
        private List<RateAsuransiJiwa> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<RateAsuransiJiwa> getL() {
            return l;
        }

        public class RateAsuransiJiwa {
            private Integer id;
            private String keterangan;
            private Double batas_atas;
            private Double batas_bawah;
            private Double biaya_tahun_1;
            private Double biaya_tahun_2;
            private Double biaya_tahun_3;
            private Double biaya_tahun_4;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getKeterangan() {
                return keterangan;
            }

            public void setKeterangan(String keterangan) {
                this.keterangan = keterangan;
            }

            public Double getBatas_atas() {
                return batas_atas;
            }

            public void setBatas_atas(Double batas_atas) {
                this.batas_atas = batas_atas;
            }

            public Double getBatas_bawah() {
                return batas_bawah;
            }

            public void setBatas_bawah(Double batas_bawah) {
                this.batas_bawah = batas_bawah;
            }

            public Double getBiaya_tahun_1() {
                return biaya_tahun_1;
            }

            public void setBiaya_tahun_1(Double biaya_tahun_1) {
                this.biaya_tahun_1 = biaya_tahun_1;
            }

            public Double getBiaya_tahun_2() {
                return biaya_tahun_2;
            }

            public void setBiaya_tahun_2(Double biaya_tahun_2) {
                this.biaya_tahun_2 = biaya_tahun_2;
            }

            public Double getBiaya_tahun_3() {
                return biaya_tahun_3;
            }

            public void setBiaya_tahun_3(Double biaya_tahun_3) {
                this.biaya_tahun_3 = biaya_tahun_3;
            }

            public Double getBiaya_tahun_4() {
                return biaya_tahun_4;
            }

            public void setBiaya_tahun_4(Double biaya_tahun_4) {
                this.biaya_tahun_4 = biaya_tahun_4;
            }
        }
    }


    public class RateBiayaCallback {
        private List<RateBiaya> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<RateBiaya> getL() {
            return l;
        }

        public class RateBiaya {
            private Long id;
            private String tipe;
            private String kategori;
            private String produk;
            private String nama_biaya;
            private Integer tenor;
            private Double value;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getTipe() {
                return tipe;
            }

            public void setTipe(String tipe) {
                this.tipe = tipe;
            }

            public String getKategori() {
                return kategori;
            }

            public void setKategori(String kategori) {
                this.kategori = kategori;
            }

            public String getProduk() {
                return produk;
            }

            public void setProduk(String produk) {
                this.produk = produk;
            }

            public String getNama_biaya() {
                return nama_biaya;
            }

            public void setNama_biaya(String nama_biaya) {
                this.nama_biaya = nama_biaya;
            }

            public Integer getTenor() {
                return tenor;
            }

            public void setTenor(Integer tenor) {
                this.tenor = tenor;
            }

            public Double getValue() {
                return value;
            }

            public void setValue(Double value) {
                this.value = value;
            }
        }
    }

    public class DisclaimerRequest{
        public String produk;
        public String type;
        public String tanggal_saatini;

        public String getProduk() {
            return produk;
        }

        public void setProduk(String produk) {
            this.produk = produk;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTanggal_saatini() {
            return tanggal_saatini;
        }

        public void setTanggal_saatini(String tanggal_saatini) {
            this.tanggal_saatini = tanggal_saatini;
        }
    }

    public class DisclaimerCallback{
        public String rs;
        public String result1;
        public String result2;

        public String getRs() {
            return rs;
        }

        public void setRs(String rs) {
            this.rs = rs;
        }

        public String getResult1() {
            return result1;
        }

        public void setResult1(String result1) {
            this.result1 = result1;
        }

        public String getResult2() {
            return result2;
        }

        public void setResult2(String result2) {
            this.result2 = result2;
        }
    }

    public class RegisterNewRequest{
        public Integer version;
        public String nama_apk;
        public String telp;
        public String noktp;
        public String gender;
        public String email;
        public String password;
        public String imei;

        public String getImei() {
            return imei;
        }
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }

        public String getNama_apk() {
            return nama_apk;
        }

        public void setNama_apk(String nama_apk) {
            this.nama_apk = nama_apk;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public void setImei(String imei) {
            this.imei = imei;
        }
    }

    public class RegisterNewCallback{
        public String responseStatus;
        public String rs;

        public String getRs() {
            return rs;
        }

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }

    public class VerifyEmailRequest{
        public String email;
        public String code;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public class  VerifyEmailCallback{
        public String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }

    public class otpRequest{
        public String msisdn;
        public String channel;
        public String otp;
        public String reffNo;
        public String lang;
        public String uploadBy;
        public String usedFor;
        public String imei;




        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getReffno() {
            return reffNo;
        }

        public void setReffno(String reffno) {
            this.reffNo = reffno;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getUploadBy() {
            return uploadBy;
        }

        public void setUploadBy(String uploadBy) {
            this.uploadBy = uploadBy;
        }

        public String getUsedFor() {
            return usedFor;
        }

        public void setUsedFor(String usedFor) {
            this.usedFor = usedFor;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }
    }

    public class otpCallback {
        public responseResult responseResult;
        public String rs;

        public String getRs() {
            return rs;
        }

        public class responseResult{
            public responseCode responseCode;

            public class responseCode{
                public String code;
                public String description;

                public String getDescription() {
                    return description;
                }

                public String getCode() {
                    return code;
                }
            }
            public otpCallback.responseResult.responseCode getResponseCode() {
                return responseCode;
            }
        }
        public otpCallback.responseResult getResponseResult() {
            return responseResult;
        }
    }

    public class otpValidRequest{
        public String otp;
        public String reffNo;
        public String msisdn;
        public String uploadBy;
        public String lang;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getUploadBy() {
            return uploadBy;
        }

        public void setUploadBy(String uploadBy) {
            this.uploadBy = uploadBy;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getReffNo() {
            return reffNo;
        }

        public void setReffNo(String reffNo) {
            this.reffNo = reffNo;
        }
    }

    public class otpValidCallback {
        private String UserStatus;
        public ValidationResponse ValidationResponse;
        private User_Identity User_Identity;

        public String getUserStatus() {
            return UserStatus;
        }

        public otpValidCallback.User_Identity getUser_identity() {
            return User_Identity;
        }

        public otpValidCallback.ValidationResponse getValidationResponse() {
            return ValidationResponse;
        }

        public class ValidationResponse {
            public responseResult responseResult;

            public otpValidCallback.ValidationResponse.responseResult getResponseResult() {
                return responseResult;
            }

            public class responseResult{
                public responseCode responseCode;

                public otpValidCallback.ValidationResponse.responseResult.responseCode getResponseCode() {
                    return responseCode;
                }

                public class responseCode{
                    public String code;
                    public String description;

                    public String getDescription() {
                        return description;
                    }

                    public String getCode() {
                        return code;
                    }
                }

            }

        }
        public class User_Identity{
            private String nama;
            private String noktp;
            private String gender;
            private String email;
            private String telp;


            public String getNama() {
                return nama;
            }

            public String getNoKtp() {
                return noktp;
            }

            public String getGender() {
                return gender;
            }

            public String getEmail() {
                return email;
            }

            public String getTelp() {
                return telp;
            }


        }
    }

    public class aplikasiOnlineRequest{
        public Integer version;
        public String paket;
        public String produk;
        public Double harga;
        public Integer tahunkendaraan;
        public Long cabang_smmf;
        public String telp;
        public String tipe_kendaraan;
        public Integer tenor;
        public Double dp;
        public String referal_mkt;
        public String keterangan_referal_mkt;
        public String customer_id;

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

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

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
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

        public String getReferal_mkt() {
            return referal_mkt;
        }

        public void setReferal_mkt(String referal_mkt) {
            this.referal_mkt = referal_mkt;
        }

        public String getKeterangan_referal_mkt() {
            return keterangan_referal_mkt;
        }

        public void setKeterangan_referal_mkt(String keterangan_referal_mkt) {
            this.keterangan_referal_mkt = keterangan_referal_mkt;
        }
    }

    public class aplikasiOnlineCallback{
        private String responseStatus;
        private Long id;

        public String getResponseStatus() {
            return responseStatus;
        }

        public Long getId() {
            return id;
        }
    }


    public class OtpParamCallback {
        private List<OtpParam> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<OtpParam> getL() {
            return l;
        }

        public class OtpParam {
           private String name;
           private Integer value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getValue() {
                return value;
            }

            public void setValue(Integer value) {
                this.value = value;
            }
        }
    }

    public class OtpParamRequest{
        public String getUploadBy() {
            return uploadBy;
        }

        public void setUploadBy(String uploadBy) {
            this.uploadBy = uploadBy;
        }

        private String uploadBy;

    }


    public class VersionRequest {
        private Integer version;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }
    }

    public class VersionCallback {
        private String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }

    public class RentalRequest {
        private Integer version;
        private String telp;
        private String action;
        private String parameter;
        private String customer_id;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
    }

    public class RentalCallback {
       private String responseStatus;


        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }
    public class rentalListRequest {
        private String telp;

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }
    }

    public class rentalListCallback {
        private List<rentalList> list = new ArrayList<>();
        private String responseStatus;


        public List<rentalList> getList() {
            return list;
        }

        public String getResponseStatus() {
            return responseStatus;
        }

        public class rentalList {
            private Integer id;
            private String telp;
            private String action;
            private String parameter;
            private String date_request;
            private String status;
            private String customer_name;
            private String error_message;

            public String getError_message() {
                return error_message;
            }

            public void setError_message(String error_message) {
                this.error_message = error_message;
            }

            public String getCustomer_name() {
                return customer_name;
            }

            public void setCustomer_name(String customer_name) {
                this.customer_name = customer_name;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getTelp() {
                return telp;
            }

            public void setTelp(String telp) {
                this.telp = telp;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getParameter() {
                return parameter;
            }

            public void setParameter(String parameter) {
                this.parameter = parameter;
            }

            public String getDate_request() {
                return date_request;
            }

            public void setDate_request(String date_request) {
                this.date_request = date_request;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }

    public class RentalDetailRequest{
        public String ticket_id;

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }
    }

    public class RentalDetailCallback{
        public String responseStatus;
        public ticket_response ticket_response;

        public String getResponseStatus() {
            return responseStatus;
        }

        public RentalDetailCallback.ticket_response getTicket_response() {
            return ticket_response;
        }

        public class ticket_response{
            public detail_response detail_response;

            public RentalDetailCallback.ticket_response.detail_response getDetail_response() {
                return detail_response;
            }

            public class detail_response{
                public String responseCode;
                public String errorMessage;
                public response response;

                public String getErrorMessage() {
                    return errorMessage;
                }

                public String getResponseCode() {
                    return responseCode;
                }

                public RentalDetailCallback.ticket_response.detail_response.response getResponse() {
                    return response;
                }

                public class response{
                    private String responseMessage;
                    private String customerName;
                    private String vaNumber;
                    private String ppk;
                    private String terminateDate;
                    private String tenor;
                    private List<paymenthistory> paymentHistory = new ArrayList<>();

                    public List<paymenthistory> getPaymentHistory() {
                        return paymentHistory;
                    }

                    public String getResponseMessage() {
                        return responseMessage;
                    }

                    public void setResponseMessage(String responseMessage) {
                        this.responseMessage = responseMessage;
                    }

                    public String getCustomerName() {
                        return customerName;
                    }

                    public void setCustomerName(String customerName) {
                        this.customerName = customerName;
                    }

                    public String getVaNumber() {
                        return vaNumber;
                    }

                    public void setVaNumber(String vaNumber) {
                        this.vaNumber = vaNumber;
                    }

                    public String getPpk() {
                        return ppk;
                    }

                    public void setPpk(String ppk) {
                        this.ppk = ppk;
                    }

                    public String getTerminateDate() {
                        return terminateDate;
                    }

                    public void setTerminateDate(String terminateDate) {
                        this.terminateDate = terminateDate;
                    }

                    public String getTenor() {
                        return tenor;
                    }

                    public void setTenor(String tenor) {
                        this.tenor = tenor;
                    }

                    public class paymenthistory{
                        public String dueDate;
                        public String paymentDate;
                        public String osBalance;
                        public String rental;


                        public String getDueDate() {
                            return dueDate;
                        }

                        public void setDueDate(String dueDate) {
                            this.dueDate = dueDate;
                        }

                        public String getPaymentDate() {
                            return paymentDate;
                        }

                        public void setPaymentDate(String paymentDate) {
                            this.paymentDate = paymentDate;
                        }

                        public String getOsBalance() {
                            return osBalance;
                        }

                        public void setOsBalance(String osBalance) {
                            this.osBalance = osBalance;
                        }

                        public String getRental() {
                            return rental;
                        }

                        public void setRental(String rental) {
                            this.rental = rental;
                        }
                    }
                }

            }
        }
    }

    public class RefreshTicketRequest{
        public String ticket_id;

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }
    }

    public class RefreshTicketResponse{
        public String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }

    public class HideRequest{
        private Integer version;
        private String ticket_id;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }
    }

    public class HideResponse{
        public String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }

    public class MemberCheckRequest{
        private String telp;
        private String customer_id;

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }

        public String getTelp() {
            return telp;
        }
    }

    public class MemberCheckResponse{
        public String responseStatus;
        private List<MemberId> response = new ArrayList<>();

        public List<MemberId> getList() {
            return response;
        }

        public void setList(List<MemberId> list) {
            this.response = list;
        }

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public class MemberId{
            String parameter1;
            String parameter2;
            String partner_code;

            public String getParameter1() {
                return parameter1;
            }

            public String getParameter2() {
                return parameter2;
            }

            public String getPartner_code() {
                return partner_code;
            }
        }
    }

    public class MemberRequest{
        private Integer version;
        private String telp;
        private String customer_id;
        private String parameter1;
        private String parameter2;
        private String noktp;
        private String request_type;


        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }

        public String getParameter1() {
            return parameter1;
        }

        public void setParameter1(String parameter1) {
            this.parameter1 = parameter1;
        }

        public String getParameter2() {
            return parameter2;
        }

        public void setParameter2(String parameter2) {
            this.parameter2 = parameter2;
        }

        public String getNoktp() {
            return noktp;
        }

        public void setNoktp(String noktp) {
            this.noktp = noktp;
        }

        public String getRequest_type() {
            return request_type;
        }

        public void setRequest_type(String request_type) {
            this.request_type = request_type;
        }
    }

    public class MemberResponse{
        private String responseStatus;
        private String memberGetMember_requestId;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public String getMemberGetMember_requestId() {
            return memberGetMember_requestId;
        }

        public void setMemberGetMember_requestId(String memberGetMember_requestId) {
            this.memberGetMember_requestId = memberGetMember_requestId;
        }
    }

    public class GetIdRequest{
        private String telp;
        private String imei;


        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

    }

    public class GetIdResponse{
        private String rs;
        private String customer_id;
        public String getRs() {
            return rs;
        }

        public void setRs(String rs) {
            this.rs = rs;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
    }



    //UPDATE WITH LOGIN KUN
    public class GetPassRequest {
        private Integer version;
        private String telp;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }
    }

    public class getPassResponse{
        private String responseStatus;
        private String nama;
        private String noktp;
        private String email;
        private String gender;
        private String customer_id;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getNoktp() {
            return noktp;
        }

        public void setNoktp(String noktp) {
            this.noktp = noktp;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
    }

    public class setPasswordUserRequest{
            private Integer version;
            private String customer_id;
            private String email;
            private String password;
            private String imei;

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getImei() {
                return imei;
            }

            public void setImei(String imei) {
                this.imei = imei;
            }


            public Integer getVersion() {
                return version;
            }

            public void setVersion(Integer version) {
                this.version = version;
            }

    }
    public class setPasswordUserResponse{
        private String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }


    public class loginJson{
        private Integer version;
        private String password;
        private String telp;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }
    }

    public class loginJsonResponse{
        private String responseStatus;
        private String nama;
        private String noktp;
        private String email;
        private String gender;
        private String customer_id;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getNoktp() {
            return noktp;
        }

        public void setNoktp(String noktp) {
            this.noktp = noktp;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
    }

    public class changePassRequest{

        private Integer version;
        private String telp;
        private String current_password;
        private String new_password;
        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }

        public String getCurrent_password() {
            return current_password;
        }

        public void setCurrent_password(String current_password) {
            this.current_password = current_password;
        }

        public String getNew_password() {
            return new_password;
        }

        public void setNew_password(String new_password) {
            this.new_password = new_password;
        }

    }
    public class changePassResponse{
        private String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }


    public class forgetPasswordJson{
        private String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

    }


    public class listPPKCallback{
        private List<listPPK> listPPK = new ArrayList<>();
        private String responseStatus;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public List<listPPK> getList() {
            return listPPK;
        }

        public void setList(List<listPPK> list) {
            this.listPPK = list;
        }


        public class listPPK {
            private Integer id;
            private String parameter;
            private String create_date;
            private String ppk;
            private String customer_id;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getParameter() {
                return parameter;
            }

            public void setParameter(String parameter) {
                this.parameter = parameter;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getPpk() {
                return ppk;
            }

            public void setPpk(String ppk) {
                this.ppk = ppk;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }
        }
    }

    public class listPPKrequest{
        private String customer_id;

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
    }


    public class reminderStatusCallback{

        private String responseStatus;
        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }
    }

    public class changeReminderStatusRequest{
        private Integer version;
        private String customer_id;
        private String reminder_condition;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
        public String getReminder_condition() {
            return reminder_condition;
        }

        public void setReminder_condition(String reminder_condition) {
            this.reminder_condition = reminder_condition;
        }
    }

    public class getReminderStatusRequest{
        private Integer version;
        private String customer_id;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
    }

}
