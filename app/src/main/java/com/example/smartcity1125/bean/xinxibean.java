package com.example.smartcity1125.bean;

public class xinxibean {

    public xinxibean(UserBean user) {
        this.user = user;
    }

    /**
     * msg : 操作成功
     * code : 200
     * user : {"userId":1112742,"userName":"dzy123","nickName":"ll","email":"","phonenumber":"15688888888","sex":"0","avatar":"","idCard":null,"balance":1000,"score":1000}
     */

    private String msg;
    private int code;
    /**
     * userId : 1112742
     * userName : dzy123
     * nickName : ll
     * email :
     * phonenumber : 15688888888
     * sex : 0
     * avatar :
     * idCard : null
     * balance : 1000
     * score : 1000
     */

    private UserBean user;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        private int userId;
        private String userName;
        private String nickName;
        private String email;
        private String phonenumber;
        private String sex;
        private String avatar;
        private Object idCard;
        private int balance;
        private int score;

        public UserBean(String nickName, String phonenumber, String sex, String avatar, Object idCard) {
            this.nickName = nickName;
            this.phonenumber = phonenumber;
            this.sex = sex;
            this.avatar = avatar;
            this.idCard = idCard;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getIdCard() {
            return idCard;
        }

        public void setIdCard(Object idCard) {
            this.idCard = idCard;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
