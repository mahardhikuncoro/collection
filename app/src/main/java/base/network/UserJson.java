package base.network;


import java.util.ArrayList;
import java.util.List;


public class UserJson {

    public class UserCheckRequest{
        private String telp;

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }
    }



    public class UserCheckRequestCallback{

        private String responseStatus;
        private user_identity user_identity;


        public class user_identity{
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

        public String getRs() {
            return responseStatus;
        }

        public UserCheckRequestCallback.user_identity getUser_identity() {
            return user_identity;
        }
    }
}
