package com.example.flower.http.bean;

/**
 * @author ShenBen
 * @date 2019/10/7 11:40
 * @email 714081644@qq.com
 */
public class TodayBean {

    /**
     * code : 000000
     * text : 成功
     * data : {"coverImage":"http://static.htxq.net/image/app/checkin/5048495749484855-cover.png","shareImage":"http://static.htxq.net/image/app/checkin/5048495749484855-share.png","shareIcon":"http://htxq.oss-cn-beijing.aliyuncs.com/image/app/icon/official-head-icon.png","shareTitle":"\u2014\u2014吴惠子","shareDescription":"人一辈子，走走瞧瞧，吃吃喝喝，不生病就是福气，如果能遇到自己爱的也爱自己的人，再发点小财，就是天大的福气。","checkInAlert":true,"checkInTitle":"签到成功"}
     */

    private String code;
    private String text;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * coverImage : http://static.htxq.net/image/app/checkin/5048495749484855-cover.png
         * shareImage : http://static.htxq.net/image/app/checkin/5048495749484855-share.png
         * shareIcon : http://htxq.oss-cn-beijing.aliyuncs.com/image/app/icon/official-head-icon.png
         * shareTitle : ——吴惠子
         * shareDescription : 人一辈子，走走瞧瞧，吃吃喝喝，不生病就是福气，如果能遇到自己爱的也爱自己的人，再发点小财，就是天大的福气。
         * checkInAlert : true
         * checkInTitle : 签到成功
         */

        private String coverImage;
        private String shareImage;
        private String shareIcon;
        private String shareTitle;
        private String shareDescription;
        private boolean checkInAlert;
        private String checkInTitle;

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getShareImage() {
            return shareImage;
        }

        public void setShareImage(String shareImage) {
            this.shareImage = shareImage;
        }

        public String getShareIcon() {
            return shareIcon;
        }

        public void setShareIcon(String shareIcon) {
            this.shareIcon = shareIcon;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareDescription() {
            return shareDescription;
        }

        public void setShareDescription(String shareDescription) {
            this.shareDescription = shareDescription;
        }

        public boolean isCheckInAlert() {
            return checkInAlert;
        }

        public void setCheckInAlert(boolean checkInAlert) {
            this.checkInAlert = checkInAlert;
        }

        public String getCheckInTitle() {
            return checkInTitle;
        }

        public void setCheckInTitle(String checkInTitle) {
            this.checkInTitle = checkInTitle;
        }
    }
}
