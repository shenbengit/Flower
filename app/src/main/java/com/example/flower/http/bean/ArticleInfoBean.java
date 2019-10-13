package com.example.flower.http.bean;

/**
 * @author ShenBen
 * @date 2019/10/13 13:53
 * @email 714081644@qq.com
 */
public class ArticleInfoBean {

    /**
     * code : 000000
     * text : 成功
     * data : {"fnCreateDate":1511846101000,"appointCount":55,"readCount":33452,"favoCount":35,"commentCount":0,"shareCount":7,"authorId":"91e7329a-3d0a-45ee-af75-c8d857be67d3","authorName":"简花艺","title":"灵感视频 | 镜中之舞","isAppoint":null,"isSubscribe":null,"isFavorite":null,"articleTitleImgUrl":"http://static.htxq.net/UploadFiles/2017/11/21/test/20171121194225073.jpg","videoUrl":"http://static.htxq.net/UploadFiles/2017/11/21/test/20171121195426890.mp4","isVideo":true,"userIdentity":null,"cartName":"花艺学堂","subscribeCount":115,"userHeadImgUrl":"https://static.htxq.net/UploadFiles/headimg/20171204121648461.jpg","sharePageUrl":"/servlet/SysArticleServlet?action=sharePreview&artId=464b27fb-7c47-4445-94ab-521bb5fb7f34","articleDesc":"","isHaveRedBag":false,"userNewAuth":null}
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
         * fnCreateDate : 1511846101000
         * appointCount : 55
         * readCount : 33452
         * favoCount : 35
         * commentCount : 0
         * shareCount : 7
         * authorId : 91e7329a-3d0a-45ee-af75-c8d857be67d3
         * authorName : 简花艺
         * title : 灵感视频 | 镜中之舞
         * isAppoint : null
         * isSubscribe : null
         * isFavorite : null
         * articleTitleImgUrl : http://static.htxq.net/UploadFiles/2017/11/21/test/20171121194225073.jpg
         * videoUrl : http://static.htxq.net/UploadFiles/2017/11/21/test/20171121195426890.mp4
         * isVideo : true
         * userIdentity : null
         * cartName : 花艺学堂
         * subscribeCount : 115
         * userHeadImgUrl : https://static.htxq.net/UploadFiles/headimg/20171204121648461.jpg
         * sharePageUrl : /servlet/SysArticleServlet?action=sharePreview&artId=464b27fb-7c47-4445-94ab-521bb5fb7f34
         * articleDesc :
         * isHaveRedBag : false
         * userNewAuth : null
         */

        private long fnCreateDate;
        private int appointCount;
        private int readCount;
        private int favoCount;
        private int commentCount;
        private int shareCount;
        private String authorId;
        private String authorName;
        private String title;
        private Object isAppoint;
        private Object isSubscribe;
        private Object isFavorite;
        private String articleTitleImgUrl;
        private String videoUrl;
        private boolean isVideo;
        private Object userIdentity;
        private String cartName;
        private int subscribeCount;
        private String userHeadImgUrl;
        private String sharePageUrl;
        private String articleDesc;
        private boolean isHaveRedBag;
        private Object userNewAuth;

        public long getFnCreateDate() {
            return fnCreateDate;
        }

        public void setFnCreateDate(long fnCreateDate) {
            this.fnCreateDate = fnCreateDate;
        }

        public int getAppointCount() {
            return appointCount;
        }

        public void setAppointCount(int appointCount) {
            this.appointCount = appointCount;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public int getFavoCount() {
            return favoCount;
        }

        public void setFavoCount(int favoCount) {
            this.favoCount = favoCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getIsAppoint() {
            return isAppoint;
        }

        public void setIsAppoint(Object isAppoint) {
            this.isAppoint = isAppoint;
        }

        public Object getIsSubscribe() {
            return isSubscribe;
        }

        public void setIsSubscribe(Object isSubscribe) {
            this.isSubscribe = isSubscribe;
        }

        public Object getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(Object isFavorite) {
            this.isFavorite = isFavorite;
        }

        public String getArticleTitleImgUrl() {
            return articleTitleImgUrl;
        }

        public void setArticleTitleImgUrl(String articleTitleImgUrl) {
            this.articleTitleImgUrl = articleTitleImgUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public boolean isVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public Object getUserIdentity() {
            return userIdentity;
        }

        public void setUserIdentity(Object userIdentity) {
            this.userIdentity = userIdentity;
        }

        public String getCartName() {
            return cartName;
        }

        public void setCartName(String cartName) {
            this.cartName = cartName;
        }

        public int getSubscribeCount() {
            return subscribeCount;
        }

        public void setSubscribeCount(int subscribeCount) {
            this.subscribeCount = subscribeCount;
        }

        public String getUserHeadImgUrl() {
            return userHeadImgUrl;
        }

        public void setUserHeadImgUrl(String userHeadImgUrl) {
            this.userHeadImgUrl = userHeadImgUrl;
        }

        public String getSharePageUrl() {
            return sharePageUrl;
        }

        public void setSharePageUrl(String sharePageUrl) {
            this.sharePageUrl = sharePageUrl;
        }

        public String getArticleDesc() {
            return articleDesc;
        }

        public void setArticleDesc(String articleDesc) {
            this.articleDesc = articleDesc;
        }

        public boolean isIsHaveRedBag() {
            return isHaveRedBag;
        }

        public void setIsHaveRedBag(boolean isHaveRedBag) {
            this.isHaveRedBag = isHaveRedBag;
        }

        public Object getUserNewAuth() {
            return userNewAuth;
        }

        public void setUserNewAuth(Object userNewAuth) {
            this.userNewAuth = userNewAuth;
        }
    }
}
