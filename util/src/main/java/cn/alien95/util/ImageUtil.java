package cn.alien95.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by linlongxin on 2016/5/12.
 */
public class ImageUtil {

    public static final int REQUEST_CODE_PICK_IMAGE = 357;
    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 951;
    private static Handler handler;

    /**
     * 相册获取照片
     *
     * @param activity
     */
    public static void getImageFromAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 从相机获取图片
     */
    public static void getImageFromCamera(Activity activity) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            activity.startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Utils.ToastLong("请确认已经插入SD卡");
        }
    }

    //Bitmap --> file 更加图片大小压缩，压缩到200KB左右
    public static boolean compress(Bitmap photo, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file, false));
            int size = photo.getByteCount();
            if (size > 2 * 1024 * 1024) {
                photo.compress(Bitmap.CompressFormat.JPEG, 10, bos);
            } else if (size > 1024 * 1024) {
                photo.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            } else if (size > 512 * 1024) {
                photo.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            } else if (size > 200 * 1024) {
                photo.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            } else {
                photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //uri --> bitmap 把uri对应的图片压缩后转换成Bitmap
    public static void compress(final Uri uri, final int width, final int height, final Callback callback) {
        String filePath = new StringBuilder(String.valueOf(uri)).delete(0, 7).toString(); //删除file://
        compress(filePath, width, height, callback);
//        if (handler == null) {
//            handler = new Handler(Looper.getMainLooper());
//        }
//        AsyncThreadPool.getInstance().executeRunnable(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Utils.getContext().getContentResolver(), uri);
//                    File file = new File(Utils.getCacheDir(), System.currentTimeMillis() + ".jpg");
//                    compress(bitmap, file);
//                    final Bitmap newBitmap = compress(file, width, height);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callback.callback(newBitmap);
//                        }
//                    });
//                } catch (Exception e) {
//                    Utils.Log("[Android]  " + e.getMessage());
//                    Utils.Log("[Android] -- 目录为：" + uri);
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public static void compress(final String filePath, final int width, final int height, final Callback callback) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        AsyncThreadPool.getInstance().executeRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap newBitmap = compress(new File(filePath), width, height);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.callback(newBitmap);
                        }
                    });
                } catch (Exception e) {
                    Utils.Log("[Android]  " + e.getMessage());
                    Utils.Log("[Android] -- 目录为：" + filePath);
                    e.printStackTrace();
                }
            }
        });
    }

    //uri -- > bitmap 默认200px
    public static void compress(final Uri uri, final Callback callback) {
        compress(uri, 200, 200, callback);
    }

    //file_path --> file_path
    public static void compress(final String resourcePath, final File target, final int width, final int height) {
        final Uri uri = Uri.parse("file://" + resourcePath);
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        AsyncThreadPool.getInstance().executeRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap newBitmap = compress(new File(resourcePath), width, height);
                    compress(newBitmap, target);
                } catch (Exception e) {
                    Utils.Log("[Android]  " + e.getMessage());
                    Utils.Log("[Android] -- 目录为：" + uri);
                    e.printStackTrace();
                }
            }
        });
    }

    //file_path --> file
    public static void compress(String resourcePath, final String targetPath, final int width, final int height) {
        compress(resourcePath, new File(targetPath), width, height);
    }

    //默认200px
    public static void compress(String resourcePath, final File target) {
        compress(resourcePath, target, 200, 200);
    }

    //file --> file
    public static void compress(File resource, final File target, final int width, final int height) {
        compress(resource.getAbsolutePath(), target, width, height);
    }

    //file --> file 默认200px
    public static void compress(File resource, File target) {
        compress(resource, target, 200, 200);
    }

    //file --> bitmap 长或宽压缩到200像素以内
    public static Bitmap compress(File f) {
        return compress(f, 200, 200);
    }

    //file --> bitmap 长或宽压缩到指定像素以内
    public static Bitmap compress(File f, int width, int height) {
        Bitmap b = null;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int scale = 1;
            if (o.outHeight > width && o.outWidth > height) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(200 / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //回调
    public interface Callback {

        void callback(Bitmap bitmap);
    }
}
