package com.ktw.ktwlib.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * =====================================
 * @author： luxiaolin
 * @date： 2017-12-7
 * @description：
 * =====================================
 */
public class FileUtils {
	public static final String ASSETS_PATH = "file:///android_asset/";

	private static final String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"}, {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"}, {".bin", "application/octet-stream"},
			{".bmp", "image/bmp"}, {".c", "text/plain"}, {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"}, {".doc", "application/msword"},
			{".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, {".xls", "application/vnd.ms-excel"},
			{".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, {".exe", "application/octet-stream"}, {".gif", "image/gif"}, {".gtar", "application/x-gtar"},
			{".gz", "application/x-gzip"}, {".h", "text/plain"}, {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"}, {".java", "text/plain"},
			{".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"}, {".js", "application/x-javascript"}, {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"}, {".m4a", "audio/mp4a-latm"},
			{".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"}, {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"}, {".mov", "video/quicktime"}, {".mp2", "audio/x-mpeg"},
			{".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"}, {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"}, {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"},
			{".mpg4", "video/mp4"}, {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"}, {".pdf", "application/pdf"}, {".png", "image/png"},
			{".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"}, {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
			{".prop", "text/plain"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"},
			{".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"},
			{".wps", "application/vnd.ms-works"}, {".xml", "text/plain"}, {".z", "application/x-compress"}, {".zip", "application/x-zip-compressed"}, {"", "*/*"}};

	/**
	 * @return
	 * @author： luxiaolin
	 * @date： 2017-12-20
	 * @description： 获取SD卡路径
	 */
	public static boolean hasSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static void openFile(Context context, File file) {
		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 设置intent的Action属性
			intent.setAction(Intent.ACTION_VIEW);
			// 获取文件file的MIME类型
			String type = getMIMEType(file);
			// 设置intent的data和Type属性。
			intent.setDataAndType(Uri.fromFile(file), type);
			// 跳转
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param file
	 * @return
	 * @author： luxiaolin
	 * @date： 2017-12-20
	 * @description：根据文件后缀名获得对应的MIME类型。
	 */
	private static String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	/**
	 * @param path
	 * @author： luxiaolin
	 * @date： 2017-12-15
	 * @description：删除文件
	 */
	public static void DelFiles(String path) {
		File delFile = new File(path);
		if (delFile.exists()) {
			delFile.delete();
		}
	}

	/**
	 * @param context
	 * @param filePath 原始文件路径
	 * @param toFile   复制路径
	 * @return
	 * @author： luxiaolin
	 * @date： 2017-12-20
	 * @description：从assets中复制文件
	 */
	public static boolean copyFileFromAssets(Context context, String filePath, File toFile) {
		boolean isCopy = false;
		try {
			InputStream in = context.getAssets().open(filePath);
			if (!toFile.getParentFile().exists())
				toFile.getParentFile().mkdirs();
			if (toFile.exists())
				toFile.delete();
			FileOutputStream out = new FileOutputStream(toFile);
			byte buff[] = new byte[4096];
			do {
				int i = in.read(buff);
				if (i == -1) {
					out.flush();
					out.close();
					in.close();
					break;
				}
				out.write(buff, 0, i);
			} while (true);
			isCopy = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isCopy;
	}

	/**
	 * @param in 源文件
	 * @return
	 * @throws Exception
	 * @author： luxiaolin
	 * @date： 2017-12-20
	 * @description：文件读取工具类
	 */
	public static byte[] readUploadFile(InputStream in) throws Exception {
		byte[] tmp = new byte[8192];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		while ((len = in.read(tmp)) > 0) {
			out.write(tmp, 0, len);
		}
		byte[] jdata = out.toByteArray();
		in.close();
		out.close();
		return jdata;
	}

	public static String getPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) { // ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
//                Log.i(TAG,"isExternalStorageDocument***"+uri.toString());
//                Log.i(TAG,"docId***"+docId);
//                以下是打印示例：
//                isExternalStorageDocument***content://com.android.externalstorage.documents/document/primary%3ATset%2FROC2018421103253.wav
//                docId***primary:Test/ROC2018421103253.wav
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			} // DownloadsProvider
			else if (isDownloadsDocument(uri)) { //                Log.i(TAG,"isDownloadsDocument***"+uri.toString());
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			} // MediaProvider
			else if (isMediaDocument(uri)) { //                Log.i(TAG,"isMediaDocument***"+uri.toString());
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{split[1]};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		} // MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) { //            Log.i(TAG,"content***"+uri.toString());
			return getDataColumn(context, uri, null, null);
		} // File
		else if ("file".equalsIgnoreCase(uri.getScheme())) { //            Log.i(TAG,"file***"+uri.toString());
			return uri.getPath();
		}
		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	private static String getDataColumn(Context context, Uri uri, String selection,
										String[] selectionArgs) {
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null) cursor.close();
		}
		return null;
	}

	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	public static String readToString(String fileName) {
		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 复制单个文件
	 *
	 * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
	 * @param newPath$Name String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
	 * @return <code>true</code> if and only if the file was copied;
	 * <code>false</code> otherwise
	 */
	public static boolean copyFile(String oldPath$Name, String newPath$Name) {
		try {
			File oldFile = new File(oldPath$Name);
			if (!oldFile.exists()) {
				Log.e("--Method--", "copyFile:  oldFile not exist.");
				return false;
			} else if (!oldFile.isFile()) {
				Log.e("--Method--", "copyFile:  oldFile not file.");
				return false;
			} else if (!oldFile.canRead()) {
				Log.e("--Method--", "copyFile:  oldFile cannot read.");
				return false;
			}

        /* 如果不需要打log，可以使用下面的语句
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return false;
        }
        */

			FileInputStream fileInputStream = new FileInputStream(oldPath$Name);    //读入原文件
			FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
			byte[] buffer = new byte[1024];
			int byteRead;
			while ((byteRead = fileInputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, byteRead);
			}
			fileInputStream.close();
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 复制文件夹及其中的文件
	 *
	 * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
	 * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
	 * @return <code>true</code> if and only if the directory and files were copied;
	 * <code>false</code> otherwise
	 */
	public static boolean copyFolder(String oldPath, String newPath) {
		try {
			File newFile = new File(newPath);
			if (!newFile.exists()) {
				if (!newFile.mkdirs()) {
					Log.e("--Method--", "copyFolder: cannot create directory.");
					return false;
				}
			}
			File oldFile = new File(oldPath);
			String[] files = oldFile.list();
			File temp;
			for (String file : files) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file);
				} else {
					temp = new File(oldPath + File.separator + file);
				}

				if (temp.isDirectory()) {   //如果是子文件夹
					copyFolder(oldPath + "/" + file, newPath + "/" + file);
				} else if (!temp.exists()) {
					Log.e("--Method--", "copyFolder:  oldFile not exist.");
					return false;
				} else if (!temp.isFile()) {
					Log.e("--Method--", "copyFolder:  oldFile not file.");
					return false;
				} else if (!temp.canRead()) {
					Log.e("--Method--", "copyFolder:  oldFile cannot read.");
					return false;
				} else {
					FileInputStream fileInputStream = new FileInputStream(temp);
					FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
					byte[] buffer = new byte[1024];
					int byteRead;
					while ((byteRead = fileInputStream.read(buffer)) != -1) {
						fileOutputStream.write(buffer, 0, byteRead);
					}
					fileInputStream.close();
					fileOutputStream.flush();
					fileOutputStream.close();
				}

            /* 如果不需要打log，可以使用下面的语句
            if (temp.isDirectory()) {   //如果是子文件夹
                copyFolder(oldPath + "/" + file, newPath + "/" + file);
            } else if (temp.exists() && temp.isFile() && temp.canRead()) {
                FileInputStream fileInputStream = new FileInputStream(temp);
                FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                byte[] buffer = new byte[1024];
                int byteRead;
                while ((byteRead = fileInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, byteRead);
                }
                fileInputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            */
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}