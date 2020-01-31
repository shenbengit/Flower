package com.example.flower.http.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author ShenBen
 * @date 2020/1/25 16:03
 * @email 714081644@qq.com
 */
public class KnowFlowerResultBean {

    /**
     * result : 1
     * error  : 10000
     * message  : something goes wrong
     * response : {"identify_results":[{"reference_url":"http://static.xingseapp.com/users/40468/flowers/1474186321.jpg?auth_key=1531276920-0-0-cd3ad230b30cc192c13014ed4ac37254","name":"鹅掌楸","desc":"娇嫩鹅黄，珍稀脆弱","probability":0.58588814735413,"detail_url":"http://10.35.10.77/item/detail?id=WGtCRG5pRUQ4b1BQOWlVRDNlcnhMeDkxdFdQUTNVOFdvRjF3d052VVgya012RUd2UmJKeTJJR2dNTXcycGdBcg=="},{"reference_url":"http://static.xingseapp.com/users/385456/flowers/1476600546.jpg?auth_key=1531276920-0-0-1a398106e9acbf5f0395440ee5dc1517","name":"北美鹅掌楸","desc":"本就多高树，高攀难登顶","probability":0.17450802028179,"detail_url":"http://10.35.10.77/item/detail?id=WGtCRG5pRUQ4b1BQOWlVRDNlcnhMN3NubSttVnpmbXc1bER5K0JQTVVHMndGTWs5Y29lTGpENm1wc1l1bDVjUA=="},{"reference_url":"http://static.xingseapp.com/users/94737/flowers/1469203007.jpg?auth_key=1531276920-0-0-31fb9961ea356af63fd0222acb22bf07","name":"杂交鹅掌楸","desc":"绿叶茵茵铺，秋季绿转红","probability":0.019778996706009,"detail_url":"http://10.35.10.77/item/detail?id=WGtCRG5pRUQ4b1BQOWlVRDNlcnhMMXJzZUg3QkRzNVFpbGQ0dHZGcHo0RXhSdGl2RkczbGtMNjBCb0JyUlJIbQ=="}]}
     */

    private int result;
    private int error;
    private String message;
    private ResponseBean response;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        @SerializedName("identify_results")
        private List<IdentifyResultsBean> identifyResults;

        public List<IdentifyResultsBean> getIdentifyResults() {
            return identifyResults;
        }

        public void setIdentifyResults(List<IdentifyResultsBean> identifyResults) {
            this.identifyResults = identifyResults;
        }

        public static class IdentifyResultsBean {
            /**
             * reference_url : http://static.xingseapp.com/users/40468/flowers/1474186321.jpg?auth_key=1531276920-0-0-cd3ad230b30cc192c13014ed4ac37254
             * name : 鹅掌楸
             * desc : 娇嫩鹅黄，珍稀脆弱
             * probability : 0.58588814735413
             * detail_url : http://10.35.10.77/item/detail?id=WGtCRG5pRUQ4b1BQOWlVRDNlcnhMeDkxdFdQUTNVOFdvRjF3d052VVgya012RUd2UmJKeTJJR2dNTXcycGdBcg==
             */

            @SerializedName("reference_url")
            private String referenceUrl;
            private String name;
            private String desc;
            private double probability;
            @SerializedName("detail_url")
            private String detailUrl;

            public String getReferenceUrl() {
                return referenceUrl;
            }

            public void setReferenceUrl(String referenceUrl) {
                this.referenceUrl = referenceUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public double getProbability() {
                return probability;
            }

            public void setProbability(double probability) {
                this.probability = probability;
            }

            public String getDetailUrl() {
                return detailUrl;
            }

            public void setDetail_url(String detailUrl) {
                this.detailUrl = detailUrl;
            }
        }
    }
}
