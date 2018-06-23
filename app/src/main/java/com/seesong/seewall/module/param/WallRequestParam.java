package com.seesong.seewall.module.param;


import java.util.EnumMap;
import java.util.Map;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public class WallRequestParam {

    //    https://pixabay.com/zh/photos/
// ?min_height= 720
// &orientation=horizontal / vertical
// &image_type= all(全部)/photo（照片） /vector（矢量图） /illustration（插画）
// &cat= transportation （交通运输）/industry（产业/技术）/people（人物）/
// animals（动物）/health（医疗/健康）/business（商业/金融）/places（地标/地点）/
//    religion（宗教）/buildings（建筑）/education（教育）/travel（旅游度假）/
//    science（科学/技术）/fashion（美妆/时尚）/backgrounds（背景/花纹）/
//    nature（自然风景）/feelings（表情）/computer（计算机/沟通）/sports（运动）
//    music（音乐）/food（食物/饮料）

// &q=美女（关键字）
// &min_width= 1080
// &order=popular（热门）/latest（最新）/upcoming（即将到来的）/ec（小编精选）
// &pagi=2
// # colors  transparent#
//

    /**
     * all(全部)/photo（照片） /vector（矢量图） /illustration（插画）
     */
    private String image_type;
    private String orientation;//orientation=horizontal / vertical
    private String min_height;
    private String min_width;
    private String q;//关键字
    /**
     * popular（热门）/latest（最新）/upcoming（即将到来的）/ec（小编精选）
     */
    private String order;
    /**
     * transportation （交通运输）/industry（产业/技术）/people（人物）/
     * animals（动物）/health（医疗/健康）/business（商业/金融）/places（地标/地点）/
     * religion（宗教）/buildings（建筑）/education（教育）/travel（旅游度假）/
     * science（科学/技术）/fashion（美妆/时尚）/backgrounds（背景/花纹）/
     * nature（自然风景）/feelings（表情）/computer（计算机/沟通）/sports（运动）
     * music（音乐）/food（食物/饮料）
     */
    private String cat;
    private String pagi;//页码

    private Map<Builder.Param, String> paramEnumMap;


    public WallRequestParam(String image_type,
                            String orientation,
                            String min_height,
                            String min_width,
                            String q,
                            String order,
                            String cat,
                            String pagi,
                            Map<Builder.Param, String> paramMap) {
        this.image_type = image_type;
        this.orientation = orientation;
        this.min_height = min_height;
        this.min_width = min_width;
        this.q = q;
        this.order = order;
        this.cat = cat;
        this.pagi = pagi;
        this.paramEnumMap = paramMap;

    }

    public static class Builder {

        private String image_type;
        private String orientation;
        private String min_height;
        private String min_width;
        private String q;
        private String order;
        private String cat;
        private String pagi;

        public Builder(String min_height, String min_width, String q, String pagi) {
            this.min_height = min_height;
            this.min_width = min_width;
            this.q = q;
            this.pagi = pagi;
        }

        public Builder() {
            //默认 照片；横向；小编精选；无分类
            image_type = ImageType.PHOTO.getImageType();
            orientation = Orientation.HORIZONTAL.getOrientation();
            order = Order.EC.getOrder();
            cat = Cat.NONE.getCat();
            pagi = "1";
        }

        public Builder setImageType(ImageType type) {
            this.image_type = type.getImageType();
            return this;
        }

        public Builder setOrientation(Orientation orientation) {
            this.orientation = orientation.name();
            return this;
        }

        public Builder setMinHeight(String min_height) {
            this.min_height = min_height;
            return this;
        }

        public Builder setMinWidth(String min_height) {
            this.min_height = min_width;
            return this;
        }

        public Builder setQ(String q) {
            this.q = q;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order.getOrder();
            return this;
        }

        public Builder setCat(Cat cat) {
            this.cat = cat.getCat();
            return this;
        }

        public Builder setPagi(String pagi) {
            this.pagi = pagi;
            return this;
        }

        public WallRequestParam build() {

            Map<Param, String> paramMap = new EnumMap<Param, String>(Param.class);
            paramMap.put(Param.IMAGE_TYPE, image_type);
            paramMap.put(Param.ORIENTATION, orientation);
            paramMap.put(Param.MIN_HEIGHT, min_height);
            paramMap.put(Param.MIN_WIDTH, min_width);
            paramMap.put(Param.Q, q);
            paramMap.put(Param.ORDER, order);
            paramMap.put(Param.CAT, cat);
            paramMap.put(Param.PAGI, pagi);

            return new WallRequestParam(image_type,
                    orientation,
                    min_height,
                    min_width,
                    q,
                    order,
                    cat,
                    pagi,
                    paramMap);
        }


        public enum ImageType {

            PHOTO("photo"),
            VECTOR("vector"),
            ILLUSTRATION("illustration");


            private String mImageType;

            private ImageType(String image_type) {
                mImageType = image_type;
            }

            public String getImageType() {
                return mImageType;
            }
        }

        public enum Orientation {

            HORIZONTAL("horizontal"),
            VERTICAL("vertical");

            private String mOrientation;

            Orientation(String orientation) {
                mOrientation = orientation;
            }

            public String getOrientation() {
                return mOrientation;
            }
        }

        public enum Order {
            POPULAR("popular"), LATEST("latest"), UPCOMING("upcoming"), EC("ec");

            private String mOrder;

            Order(String mOrder) {
                this.mOrder = mOrder;
            }

            public String getOrder() {
                return mOrder;
            }
        }

        public enum Cat {
            NONE(""),
            TRANSPORTATION("transportation"),
            INDUSTRY("industry"),
            PEOPLE("people"),
            ANIMALS("animals"),
            HEALTH("health"),
            BUSINESS("business"),
            PLACES("places"),
            RELIGION("religion"),
            BUILDINGS("buildings"),
            EDUCATION("education"),
            TRAVEL("travel"),
            SCIENCE("science"),
            FASHION("fashion"),
            BACKGROUNDS("backgrounds"),
            NATURE("nature"),
            FEELINGS("feelings"),
            COMPUTER("computer"),
            SPORTS("sports"),
            MUSIC("music"),
            FOOD("food");


            private String mCat;

            Cat(String mCat) {
                this.mCat = mCat;
            }

            public String getCat() {
                return mCat;
            }
        }


        public enum Param {

            IMAGE_TYPE("image_type"),
            ORIENTATION("orientation"),
            MIN_HEIGHT("min_height"),
            MIN_WIDTH("min_width"),
            Q("q"),
            ORDER("order"),
            CAT("cat"),
            PAGI("pagi");

            private String param;

            Param(String param) {
                this.param = param;
            }

            public String getParam() {
                return param;
            }
        }

    }


    public String getImage_type() {
        return image_type;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getMin_height() {
        return min_height;
    }

    public String getMin_width() {
        return min_width;
    }

    public String getQ() {
        return q;
    }

    public String getOrder() {
        return order;
    }

    public String getCat() {
        return cat;
    }

    public String getPagi() {
        return pagi;
    }


    public String increasePage() {
        pagi = String.valueOf(Integer.parseInt(pagi) + 1);
        paramEnumMap.put(Builder.Param.PAGI, pagi);
        return pagi;
    }

    public void setQ(String q){
        this.q = q;
        pagi = "1";
        paramEnumMap.put(Builder.Param.Q,q);
        paramEnumMap.put(Builder.Param.PAGI, pagi);
    }


    public Map<Builder.Param, String> getParamEnumMap() {
        return paramEnumMap;
    }
}
