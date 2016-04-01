package com.ypf.kuaicha.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;

import java.io.File;

/**
 * Created by Administrator on 2016/3/15.
 */
public class ShareUtil {
    public static boolean shareToWXFriend(String imagepath) {
        File file = new File(imagepath);
        if (!file.exists()) {
            ToastUtil.showToast(R.string.sharefail);
            return false;
        }
        WXImageObject imageObject = new WXImageObject();
        imageObject.setImagePath(imagepath);

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = imageObject;
        mediaMessage.description = "img" + String.valueOf(System.currentTimeMillis());
        mediaMessage.title = "我的分享";


        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
        bitmap.recycle();
        mediaMessage.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = mediaMessage;
        req.transaction = "img" + String.valueOf(System.currentTimeMillis());
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        return TApplication.mApi.sendReq(req);
    }

    public static boolean shareToWXFriend(Bitmap image, int flag) {
        WXImageObject imageObject = new WXImageObject(image);

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = imageObject;
        mediaMessage.description = "img" + String.valueOf(System.currentTimeMillis());
        mediaMessage.title = "我的分享";

        Bitmap thumbBitmap = Bitmap.createScaledBitmap(image, 150, 120, true);
        image.recycle();
        Log.d("TAG", "thumbBitmp.size = " + thumbBitmap.getByteCount() / 1024);
        mediaMessage.thumbData = BitmapUtil.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = mediaMessage;
        req.transaction = "img" + String.valueOf(System.currentTimeMillis());

        req.scene = flag == 1 ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        return TApplication.mApi.sendReq(req);
    }

}
