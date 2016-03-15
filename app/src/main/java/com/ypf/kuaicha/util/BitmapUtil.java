package com.ypf.kuaicha.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;

import com.ypf.kuaicha.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {
    public static final String TAG = "BitmapUtil";

    public static int getRotationForImage(String path) {
        int rotation = 0;

        try {
            ExifInterface exif = new ExifInterface(path);
            rotation = (int) exifOrientationToDegrees(exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rotation;
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static float exifOrientationToDegrees(int exifOrientation) {

        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {

            return 90;

        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {

            return 180;

        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {

            return 270;

        }

        return 0;

    }

    /**
     * 这个是用来优化xml文件加载图片的 代替xml中ImageView加载图片的方式 少占用内存
     */
    public static BitmapDrawable getBitmapDrawable(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, opt);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (null == bitmap) {
            return null;
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * 通过一种比较省内存的方式获得bitmap
     */
    public static Bitmap getBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, opt);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static BitmapDrawable getBitmapDrawableResized(Context context, int resId, int width, int height) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, opt);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new BitmapDrawable(context.getResources(), getResizedBitmap(bitmap, width, height));
    }

    /**
     * 根据resourceId获取缩略好的bitmap
     */
    public static Bitmap getBitmapResized(Context context, int resId, int width, int height) {
        return getBitmapDrawableResized(context, resId, width, height).getBitmap();
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {
        Matrix matrix = new Matrix();
        if (matrix != null) {
            matrix.postScale((float) width / (float) bitmap.getWidth(), (float) height / (float) bitmap.getHeight()); //长和宽放大缩小的比例
        }

        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 获取缩放后的bitmap
     *
     * @param scale 缩放比例
     */
    public static Bitmap getScaledBitmap(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例

        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 从本地获取bitmap
     */
    public static Bitmap getBitmapFromDisk(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        return getBitmapFromDisk(new File(fileName));
    }

    /**
     * 从本地获取bitmap
     */
    public static Bitmap getBitmapFromDisk(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError error) {//这里是有可能内存溢出的
            error.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 将bitmap缓存到本地
     */
    public static void saveBitmapToDisk(String fileName, Bitmap bitmap) {
        if (!isBitmapAvailable(bitmap)) {
            return;
        }
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        saveBitmapToDisk(new File(fileName), bitmap);
    }

    /**
     * 将bitmap缓存到本地
     */
    public static void saveBitmapToDisk(File file, Bitmap bitmap) {
        if (!isBitmapAvailable(bitmap)) {
            return;
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            file.delete();//出错了就删除
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();//出错了就删除
        } catch (Throwable e) {
            e.printStackTrace();
            file.delete();//出错了就删除
        }
    }

    /**
     * 判断bitmap是否可用 不能为空 不能是已经被回收的 isRecycled返回false
     *
     * @param bitmap
     * @return
     */
    public static boolean isBitmapAvailable(Bitmap bitmap) {
        if (null == bitmap || "".equals(bitmap) || bitmap.isRecycled()) {// 如果为null或者是已经回收了的就证明是不可用的
            return false;
        }
        return true;
    }

    /**
     * 压缩图片 --先按90%的质量压缩（经过测试，90%压缩效果最好），然后缩放到一个特定比例
     *
     * @param image
     * @return
     */
    public static Bitmap compress(Bitmap image) {
        image = cutImage(image, 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 90, baos);
        image.recycle();
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        int standardWidth = 128;
        int standardHeight = 192;

        int be = (newOpts.outWidth / standardWidth + newOpts.outHeight / standardHeight) / 2 + 2;
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        newOpts.inJustDecodeBounds = false;
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        //DLOG.d("choices", "compress Image!!");
        try {
            isBm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos  
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10  
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;
    }

    /**
     * 将bitmap压缩到制定的大小
     */

    public static Bitmap getZoomBitmap(Bitmap bitmap) {
        //图片允许最大空间   单位：KB
        double maxSize = 500.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitmap = zoomImage(bitmap, bitmap.getWidth(),
                    bitmap.getHeight());
        }
        return bitmap;
    }

    private static Bitmap zoomImage(Bitmap bitmap, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap mbitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) width,
                (int) height, matrix, true);
        return mbitmap;
    }

    /**
     * 保存高斯模糊图片
     */
    public static void saveBlurToDisk(String url, Bitmap bitmap) {
        //DLOG.d("choices", "save Image enter");
        if (TextUtils.isEmpty(url)) {
            return;
        }

        url = url.concat("blur");
        int key = url.hashCode();
        File imgFile = new File(StorageConfig.defaultImagePath + File.separator + String.valueOf(key));

        if (imgFile.exists()) {
            return;
        }

        try {
            FileOutputStream out = new FileOutputStream(imgFile);
            bitmap.compress(CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //DLOG.d("choices", "save Image true");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从磁盘去高斯图片， 不建议复用
     *
     * @param url
     * @return
     */
    public static Bitmap getBlurFromDist(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        url = url.concat("blur");
        int key = url.hashCode();
        File imgFile = new File(StorageConfig.defaultImagePath + File.separator + String.valueOf(key));
        //DLOG.d("choices", "getDist Image true");
        return getBitmapFromDisk(imgFile);
    }

    /**
     * bitmap 按屏幕比例裁边
     *
     * @param src
     * @param cutY -- Y轴需要裁掉的距离
     * @return
     */
    public static Bitmap cutImage(Bitmap src, int cutY) {
        //DLOG.d("choices", "cutImage!!");

        int bit_x, bit_y, bit_width, bit_height;
        bit_y = cutY;
        bit_height = src.getHeight() - 2 * bit_y;
        bit_width = bit_height * 10 / 16;
        bit_x = (src.getWidth() - bit_width) / 2 + 1;
        return Bitmap.createBitmap(src, bit_x, bit_y, bit_width, bit_height);
    }

    /**
     * 生成圆角图片
     *
     * @param bitmap
     * @param roundPx -- 圆角的半径，单位是PX
     * @return
     */
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap, final float roundPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    /**
     * 通过需要的宽高计算图片收缩的比例
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding bitmaps using the
     * decode* methods from {@link BitmapFactory}. This implementation calculates the closest inSampleSize that will
     * result in the final decoded bitmap having a width and height equal to or larger than the requested width and
     * height. This implementation does not ensure a power of 2 is returned for inSampleSize which can be faster when
     * decoding but results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options   An options object with out* params already populated (run through a decode* method with
     *                  inJustDecodeBounds==true
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (reqHeight <= 0 || reqWidth <= 0) {
            return inSampleSize;
        }

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            //             This offers some additional logic in case the image has a strange
            //             aspect ratio. For example, a panorama may have a much larger
            //             width than height. In these cases the total pixels might still
            //             end up being too large to fit comfortably in memory, so we should
            //             be more aggressive with sample down the image (=largerinSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further.
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
        // final int height = options.outHeight;
        // final int width = options.outWidth;
        // int inSampleSize = 1;
        //
        // if (height > reqHeight || width > reqWidth) {
        //
        // // Calculate ratios of height and width to requested height and width
        // final int heightRatio = Math.round((float) height / (float) reqHeight);
        // final int widthRatio = Math.round((float) width / (float) reqWidth);
        //
        // // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
        // // with both dimensions larger than or equal to the requested height and width.
        // inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        //
        // // This offers some additional logic in case the image has a strange
        // // aspect ratio. For example, a panorama may have a much larger
        // // width than height. In these cases the total pixels might still
        // // end up being too large to fit comfortably in memory, so we should
        // // be more aggressive with sample down the image (=larger inSampleSize).
        //
        // final float totalPixels = width * height;
        //
        // // Anything more than 2x the requested pixels we'll sample down further
        // final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        //
        // while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
        // inSampleSize++;
        // }
        // }
        // return inSampleSize;

    }

    /**
     * Decode and sample down a bitmap from a file to the requested width and height.
     *
     * @param filename  The full path of the file to decode
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions that are equal to or
     * greater than the requested width and height
     * @throws Throwable
     * @deprecated 不要再使用decodefile和decodeResources而是使用decodeStream
     */
    public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) throws Throwable {
        // synchronized (lock1)
        {
            int sample = 0;
            try {
                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filename, options);

                sample = calculateInSampleSize(options, reqWidth, reqHeight);
                // Calculate inSampleSize
                options.inSampleSize = sample;
                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                // DLOG.e("Image", "warn,path="+filename);
                return BitmapFactory.decodeFile(filename, options);
            } catch (Throwable e) {
                e.printStackTrace();
                if (e instanceof OutOfMemoryError) {
                    throw e;
                }
            }
        }
        return null;
    }

    /**
     * Decode and sample down a bitmap from resources to the requested width and height.
     *
     * @param res       The resources object containing the image data
     * @param resId     The resource id of the image data
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions that are equal to or
     * greater than the requested width and height
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        Bitmap bitmap = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream is = null;
        int sample = 0;
        try {

            options.inJustDecodeBounds = true;
            is = res.openRawResource(resId);
            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
            sample = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inSampleSize = sample;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            is = res.openRawResource(resId);//有部分设备之前is可能已经关闭了，所以重新open
            bitmap = BitmapFactory.decodeStream(is, null, options);
            return bitmap;
        } catch (Throwable e) {
            e.printStackTrace();
            // System.gc();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * 旋转Bitmap
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) rotateDegree);
        Bitmap rotaBitmap = null;
        try {
            rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return rotaBitmap;
    }

    /**
     * 根据图片id从res下获取drawable
     *
     * @param picId
     * @return
     */
    public static Drawable getDrawableFromRes(Context context, int picId) {
        Resources res = context.getResources();
        return res.getDrawable(picId);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 根据给出的图片获取圆角图片
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap == null)
            return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        try {
            Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff000000;
            final Paint paint = new Paint();
            final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
            final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
            final RectF rectF = new RectF(dst);

            paint.setAntiAlias(true);

            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, src, dst, paint);

            return output;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取缩放后的bitmap 缩放的规则是 根据给出的宽度即width 按照bitmap的宽高比计算出高度然后再进行缩放
     *
     * @param bitmap 要进行缩放的bitmap
     * @param width  给出的宽度 根据此宽度 按照bitmap的宽高比计算出高度
     * @return 缩放后的bitmap
     */
    public static Bitmap getResizedBitmapByWidth(Bitmap bitmap, int width) {
        int height = (int) (width * ((float) bitmap.getHeight() / (float) bitmap.getWidth()));
        return getResizedBitmap(bitmap, width, height);
    }

    /**
     * 获取缩放后的bitmap 缩放的规则是 根据给出的高度即height 按照bitmap的宽高比计算出高度然后再进行缩放
     *
     * @param bitmap 要进行缩放的bitmap
     * @param height 给出的高度 根据此宽度 按照bitmap的宽高比计算出宽度
     * @return 缩放后的bitmap
     */
    public static Bitmap getResizedBitmapByHeight(Bitmap bitmap, int height) {
        int width = (int) (height * ((float) bitmap.getWidth() / (float) bitmap.getHeight()));
        return getResizedBitmap(bitmap, width, height);
    }

    /**
     * 将图片裁剪成微信所需要的尺寸-正方形
     *
     * @param bitmap
     */
    public static Bitmap getWechatBitmap(Context cotnext, Bitmap bitmap) {
        if (!BitmapUtil.isBitmapAvailable(bitmap)) {
            return BitmapUtil.getBitmap(cotnext, R.mipmap.ic_launcher);
        }
        try {
            return Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0, bitmap.getHeight(),
                    bitmap.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    /**
    //     * 将bitmap加工成分享直播时所需要的样式
    //     * 
    //     * @param bitmap 原本的bitmap
    //     */
    //    public static Bitmap getLiveShareBitmap(Activity cotnext, Bitmap bitmap) {
    //        if (!BitmapUtil.isBitmapAvailable(bitmap)) {//如果不可用
    //            bitmap = BitmapUtil.getBitmap(cotnext, R.drawable.ic_launcher);
    //        }
    //        Bitmap bitmapBg = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    //        Canvas canvas = new Canvas(bitmapBg);
    //        canvas.drawBitmap(bitmap, new Matrix(), null);
    //
    //        Bitmap topBitmap = BitmapUtil.getBitmap(cotnext, R.drawable.photoframe_top);
    //        Bitmap bottomBitmap = BitmapUtil.getBitmap(cotnext, R.drawable.photoframe_bottom);
    //
    //        Rect topSrcRect = new Rect(0, 0, topBitmap.getWidth(), topBitmap.getHeight());
    //        Rect topDstRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 10);
    //
    //        Rect bottomSrcRect = new Rect(0, 0, bottomBitmap.getWidth(), bottomBitmap.getHeight());
    //        Rect bottomDstRect = new Rect(0, bitmap.getHeight() - (bitmap.getHeight() / 10), bitmap.getWidth(),
    //                bitmap.getHeight());
    //
    //        canvas.drawBitmap(topBitmap, topSrcRect, topDstRect, null);
    //        canvas.drawBitmap(bottomBitmap, bottomSrcRect, bottomDstRect, null);
    //
    //        return bitmapBg;
    //    }


    /**
     * @author WangChunliang
     * @Date 2015-7-14
     * @Description 图片加载回调
     */
    public static interface BitmapLoadListener {
        public void onBitmapLoaded(Bitmap bitmap);
    }

    public static interface BitmapLoadListenerWithId {
        public void onBitmapLoaded(int id, Bitmap bitmap);
    }

    /**
     * 获取重复性质的bitmap
     */
    public static BitmapDrawable getRepeatBitmap(Context context, int resId) {
        BitmapDrawable bd = getBitmapDrawable(context, resId);
        bd.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
        return bd;
    }

    /**
     * 水平镜像转换
     */
    public static Bitmap horizontalMirrorConvert(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas canvas = new Canvas(newBitmap);
        Matrix matrix = new Matrix();
        //        m.postScale(1, -1); //镜像垂直翻转
        matrix.postScale(-1, 1); //镜像水平翻转
        //        m.postRotate(-90); //旋转-90度
        Bitmap tmpBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        canvas.drawBitmap(tmpBitmap, new Rect(0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight()), new Rect(0, 0, w, h),
                null);
        return newBitmap;
    }

    /**
     * 将某一张图片保存到了本地 刷新相册让它出现在相册中
     *
     * @param fileName 图片的本地位置
     */
    public static void insertImageToPhotoAlbum(Context context, String fileName) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(fileName));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}
