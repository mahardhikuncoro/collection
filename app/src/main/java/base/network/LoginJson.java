package base.network;

import java.util.ArrayList;

import base.sqlite.DataMenuModel;
import base.sqlite.DataModel;
import base.sqlite.NewsModel;

public class LoginJson {
    public class UserVerifyCallback {

        private String status;
        private String message;
        private String userid;
        private String photoprofile;

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

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPhotoprofile() {
            return photoprofile;
        }

        public void setPhotoprofile(String photoprofile) {
            this.photoprofile = photoprofile;
        }
    }

    public class LoginRequest extends BaseRequest{
        private String loginType;
        private String password;

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public class loginAutenticationCallback{

        private String status,
                message,
                needupdate,
                urlupdate,
                needchangepassword,
                userid,
                photoprofile,
                fullname,
                groupid,
                groupname,
                branchid,
                branchname,
                accesstoken,
                tokentype,
                sessTimeout;

        public ArrayList<SliderObject> slider;

        public ArrayList<SliderObject> getSlider() {
            return slider;
        }

        public void setSlider(ArrayList<SliderObject> slider) {
            this.slider = slider;
        }

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

        public String getNeedupdate() {
            return needupdate;
        }

        public void setNeedupdate(String needupdate) {
            this.needupdate = needupdate;
        }

        public String getUrlupdate() {
            return urlupdate;
        }

        public void setUrlupdate(String urlupdate) {
            this.urlupdate = urlupdate;
        }

        public String getNeedchangepassword() {
            return needchangepassword;
        }

        public void setNeedchangepassword(String needchangepassword) {
            this.needchangepassword = needchangepassword;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPhotoprofile() {
            return photoprofile;
        }

        public void setPhotoprofile(String photoprofile) {
            this.photoprofile = photoprofile;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getBranchid() {
            return branchid;
        }

        public void setBranchid(String branchid) {
            this.branchid = branchid;
        }

        public String getBranchname() {
            return branchname;
        }

        public void setBranchname(String branchname) {
            this.branchname = branchname;
        }

        public String getAccesstoken() {
            return accesstoken;
        }

        public void setAccesstoken(String accesstoken) {
            this.accesstoken = accesstoken;
        }

        public String getTokentype() {
            return tokentype;
        }

        public void setTokentype(String tokentype) {
            this.tokentype = tokentype;
        }

        public String getExpiredin() {
            return sessTimeout;
        }

        public void setExpiredin(String expiredin) {
            this.sessTimeout = expiredin;
        }

    }

    public class SliderObject{
        private String sliderUrl, sliderDesc, sliderPriority;

        public String getSliderUrl() {
            return sliderUrl;
        }

        public void setSliderUrl(String sliderUrl) {
            this.sliderUrl = sliderUrl;
        }

        public String getSliderDesc() {
            return sliderDesc;
        }

        public void setSliderDesc(String sliderDesc) {
            this.sliderDesc = sliderDesc;
        }

        public String getSliderPriority() {
            return sliderPriority;
        }

        public void setSliderPriority(String sliderPriority) {
            this.sliderPriority = sliderPriority;
        }
    }

    public class getmenuCallback{
        private String status, message;
        private ArrayList<DataMenuModel> data;
        private ArrayList<NewsModel> news;

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

        public ArrayList<DataMenuModel> getData() {
            return data;
        }
        public ArrayList<NewsModel> getNews() {
            return news;
        }

        public void setNews(ArrayList<NewsModel> news) {
            this.news = news;
        }

        public void setData(ArrayList<DataMenuModel> data) {
            this.data = data;
        }

//        public class Data{
//            private String userid,
//                    menuid,
//                    menudesc,
//                    jumlahaplikasi,
//                    menutrack,
//                    assigned,
//                    icon,
//                    is_add;
//
//            public String getUserid() {
//                return userid;
//            }
//
//            public void setUserid(String userid) {
//                this.userid = userid;
//            }
//
//            public String getMenuid() {
//                return menuid;
//            }
//
//            public void setMenuid(String menuid) {
//                this.menuid = menuid;
//            }
//
//            public String getMenudesc() {
//                return menudesc;
//            }
//
//            public void setMenudesc(String menudesc) {
//                this.menudesc = menudesc;
//            }
//
//            public String getJumlahaplikasi() {
//                return jumlahaplikasi;
//            }
//
//            public void setJumlahaplikasi(String jumlahaplikasi) {
//                this.jumlahaplikasi = jumlahaplikasi;
//            }
//
//            public String getMenutrack() {
//                return menutrack;
//            }
//
//            public void setMenutrack(String menutrack) {
//                this.menutrack = menutrack;
//            }
//
//            public String getAssigned() {
//                return assigned;
//            }
//
//            public void setAssigned(String assigned) {
//                this.assigned = assigned;
//            }
//            public String getIcon() {
//                return icon;
//            }
//
//            public void setIcon(String icon) {
//                this.icon = icon;
//            }
//
//            public String getIs_add() {
//                return is_add;
//            }
//
//            public void setIs_add(String is_add) {
//                this.is_add = is_add;
//            }
//        }
    }
}
