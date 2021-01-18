package base.network;

public class ChangePassJson {
    public class ChangePassRequest extends BaseRequest{
        private String password, newpwd, newpwd2;
        
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNewpwd() {
            return newpwd;
        }

        public void setNewpwd(String newpwd) {
            this.newpwd = newpwd;
        }

        public String getNewpwd2() {
            return newpwd2;
        }

        public void setNewpwd2(String newpwd2) {
            this.newpwd2 = newpwd2;
        }
    }

    public class ChangePassCallback{
        private String status, message;

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
    }
}
