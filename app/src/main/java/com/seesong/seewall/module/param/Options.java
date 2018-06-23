package com.seesong.seewall.module.param;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public enum Options {

    IMAGETYPE(Parameter.ImageType.class),
    ORIENTATION(Parameter.Orientation.class),
    ORDER(Parameter.Order.class),
    CAT(Parameter.Cat.class);

    private Parameter[] mOptions;

    private Options(Class<? extends Parameter> options) {
        mOptions = options.getEnumConstants();
    }


    public interface Parameter {

        enum ImageType implements Parameter {

            PHOTO("photo"),
            VECTOR("vector"),
            ILLUSTRATION("illustration");


            private String mImageType;

            private ImageType(String image_type) {
                mImageType = image_type;
            }

        }

        enum Orientation implements Parameter {

            HORIZONTAL("horizontal"),
            VERTICAL("vertical");

            private String mOrientation;

            Orientation(String orientation) {
                mOrientation = orientation;
            }
        }

        enum Order implements Parameter {
            POPULAR("popular"), LATEST("latest"), UPCOMING("upcoming"), EC("ec");

            private String mOrder;

            Order(String mOrder) {
                this.mOrder = mOrder;
            }
        }

        enum Cat implements Parameter {
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
        }

    }


}
