import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.OpenableColumns;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class Class_Uri_To_Blob {
    public static byte[] uriToBlob(Context context, Uri uri) {
        if (context == null || uri == null) return null;
        int initialCapacity = getFileSize(context, uri);
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            if (inputStream == null) return null;
            ByteArrayOutputStream byteBuffer = (initialCapacity > 0) ? new ByteArrayOutputStream(initialCapacity) : new ByteArrayOutputStream();
            byte[] buffer = new byte[16384];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap blobToBitmap(byte[] blob) {
        if (blob == null || blob.length == 0) return null;
        return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }

    public static String blobToBase64(byte[] blob) {
        if (blob == null || blob.length == 0) return null;
        return android.util.Base64.encodeToString(blob, android.util.Base64.DEFAULT);
    }

    public static byte[] base64ToBlob(String base64) {
        if (base64 == null || base64.isEmpty()) return null;
        return android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
    }

    private static int getFileSize(Context context, Uri uri) {
        try (Cursor cursor = context.getContentResolver().query(uri, new String[]{OpenableColumns.SIZE}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (sizeIndex != -1) {
                    long size = cursor.getLong(sizeIndex);
                    if (size > 0 && size <= Integer.MAX_VALUE) {
                        return (int) size;
                    }
                }
            }
        } catch (Exception ignored) {}
        return -1;
    }
}