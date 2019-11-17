package com.example.flower.http.bean;

/**
 * @author ShenBen
 * @date 2019/10/20 11:04
 * @email 714081644@qq.com
 */
public class DailyDiscoveryDetailBean {

    /**
     * code : 000000
     * text : 成功
     * data : {"id":"e3f17d32-2a66-46fd-8540-2bfe248f3f5a","seenVal":28213,"likeVal":135,"videoUrl":"","liked":false,"title":"鲜花垃圾的正确处理方式","intro":"职业花艺师必修操作规范，正确的处理方式是爱花人的专业操守。","pageUrl":"http://api.htxq.net/h5/for_app/html/article_detail.html?id=e3f17d32-2a66-46fd-8540-2bfe248f3f5a","category":"#花艺学堂#","coverImage":"https://static.htxq.net/UploadFiles/2019/10/17/test/20191017142804012.jpg","isVideo":false,"shareModel":{"caption":"鲜花垃圾的正确处理方式","details":"职业花艺师必修操作规范，正确的处理方式是爱花人的专业操守。","image":"https://static.htxq.net/UploadFiles/2019/10/17/test/20191017142804012.jpg","icon":null,"link":"http://api.htxq.net/h5/for_app/html/article_detail.html?isShare=true&id=e3f17d32-2a66-46fd-8540-2bfe248f3f5a","type":1}}
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
         * id : e3f17d32-2a66-46fd-8540-2bfe248f3f5a
         * seenVal : 28213
         * likeVal : 135
         * videoUrl :
         * liked : false
         * title : 鲜花垃圾的正确处理方式
         * intro : 职业花艺师必修操作规范，正确的处理方式是爱花人的专业操守。
         * pageUrl : http://api.htxq.net/h5/for_app/html/article_detail.html?id=e3f17d32-2a66-46fd-8540-2bfe248f3f5a
         * category : #花艺学堂#
         * coverImage : https://static.htxq.net/UploadFiles/2019/10/17/test/20191017142804012.jpg
         * isVideo : false
         * shareModel : {"caption":"鲜花垃圾的正确处理方式","details":"职业花艺师必修操作规范，正确的处理方式是爱花人的专业操守。","image":"https://static.htxq.net/UploadFiles/2019/10/17/test/20191017142804012.jpg","icon":null,"link":"http://api.htxq.net/h5/for_app/html/article_detail.html?isShare=true&id=e3f17d32-2a66-46fd-8540-2bfe248f3f5a","type":1}
         */

        private String id;
        private int seenVal;
        private int likeVal;
        private String videoUrl;
        private boolean liked;
        private String title;
        private String intro;
        private String pageUrl;
        private String category;
        private String coverImage;
        private boolean isVideo;
        private ShareModelBean shareModel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSeenVal() {
            return seenVal;
        }

        public void setSeenVal(int seenVal) {
            this.seenVal = seenVal;
        }

        public int getLikeVal() {
            return likeVal;
        }

        public void setLikeVal(int likeVal) {
            this.likeVal = likeVal;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public boolean isIsVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public ShareModelBean getShareModel() {
            return shareModel;
        }

        public void setShareModel(ShareModelBean shareModel) {
            this.shareModel = shareModel;
        }

        public static class ShareModelBean {
            /**
             * caption : 鲜花垃圾的正确处理方式
             * details : 职业花艺师必修操作规范，正确的处理方式是爱花人的专业操守。
             * image : https://static.htxq.net/UploadFiles/2019/10/17/test/20191017142804012.jpg
             * icon : null
             * link : http://api.htxq.net/h5/for_app/html/article_detail.html?isShare=true&id=e3f17d32-2a66-46fd-8540-2bfe248f3f5a
             * type : 1
             */

            private String caption;
            private String details;
            private String image;
            private Object icon;
            private String link;
            private int type;

            public String getCaption() {
                return caption;
            }

            public void setCaption(String caption) {
                this.caption = caption;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Object getIcon() {
                return icon;
            }

            public void setIcon(Object icon) {
                this.icon = icon;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
