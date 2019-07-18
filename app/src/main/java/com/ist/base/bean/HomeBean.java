package com.ist.base.bean;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

public class HomeBean {
    @Pick(value = "div.picshow_img > ul > li")
    private List<BannerBean> banners;
    @Pick(value = "div.select_btn > ul > li")
    private List<BannerText> bannerTexts;

    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

    public List<BannerText> getBannerTexts() {
        return bannerTexts;
    }

    public void setBannerTexts(List<BannerText> bannerTexts) {
        this.bannerTexts = bannerTexts;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "banners=" + banners +
                ", bannerTexts=" + bannerTexts +
                '}';
    }

    public static class BannerBean {
        @Pick(value = "a", attr = Attrs.HREF)
        String href;
        @Pick(value = "a > img", attr = Attrs.SRC)
        String imgUrl;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @Override
        public String toString() {
            return "BannerBean{" +
                    "href='" + href + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }
    }

    public static class BannerText {
        @Pick("span.select_text")
        String title;
        @Pick("span.select_date")
        String auther;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuther() {
            return auther;
        }

        public void setAuther(String auther) {
            this.auther = auther;
        }

        @Override
        public String toString() {
            return "BannerText{" +
                    "title='" + title + '\'' +
                    ", auther='" + auther + '\'' +
                    '}';
        }
    }

}
