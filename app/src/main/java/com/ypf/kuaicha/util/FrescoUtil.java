package com.ypf.kuaicha.util;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class FrescoUtil {
    public static final String FrescoLocalDrawableURI = "res://com.ypf.kuaicha/";

    public static void showGif(Uri uri, SimpleDraweeView mSimpleDraweeView) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mSimpleDraweeView.setController(controller);
    }

    public static void showPicFromNet(SimpleDraweeView view, String url){
        String viewDispalyUrl = "";
        Object tagObject = view.getTag();
        if (tagObject != null) {
            viewDispalyUrl = (String)tagObject;
        }
        if (url.compareTo(viewDispalyUrl) != 0) {
            view.setTag(url);

            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    .setImageType(ImageRequest.ImageType.SMALL)
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(imageRequest)
                    .setOldController(view.getController())
                    .build();
            view.setController(draweeController);
        }
    }
}
