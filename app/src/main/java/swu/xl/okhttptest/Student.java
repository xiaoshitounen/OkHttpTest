package swu.xl.okhttptest;

public class Student {

    /**
     * account : {"name":"xl","password":"0000"}
     * id : 222017602053039
     * status : 3
     */

    private AccountBean account;
    private String id;
    private int status;

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "account=" + account +
                ", id='" + id + '\'' +
                ", status=" + status +
                '}';
    }

    public static class AccountBean {
        /**
         * name : xl
         * password : 0000
         */

        private String name;
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "AccountBean{" +
                    "name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
