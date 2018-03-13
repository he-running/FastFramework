/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.he.data.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Helper class to do operations on regular files/directories.
 */
@Singleton
public class FileManager {

    private static final String LOG_TAG = "FileManager";
    public static final String APK_PATH = Environment.getExternalStorageDirectory().toString() + "/data/app-private/Android/data/.data-app";

    @Inject
    public FileManager() {
    }


    /**
     * 将对象序列化为本地文件
     *
     * @param context  上下文，要求为 Activity 实例
     * @param obj      对象，不能为空
     * @param filename 文件名，不能为空
     * @return 保存是否成功
     */
    public void serialize2File(Context context, Object obj, String filename) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 从本地文件中加载序列化过的对象
     *
     * @param context  上下文，要求为 Activity 实例
     * @param filename 文件名，不能为空
     * @return 对象
     */
    public Object loadSerializedObject(Context context, String filename) {

        FileInputStream fis = null;

        try {
            if (!existFile(context, filename)) {
                return null;
            }

            fis = context.openFileInput(filename);
            ObjectInputStream oin = new ObjectInputStream(fis);
            Object obj = oin.readObject();
            oin.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 移除序列化文件
     *
     * @param context  上下文，要求为 Activity 实例
     * @param filename 文件名，不能为空
     * @return
     */
    public boolean removeFile(Context context, String filename) {
        try {
            return context.deleteFile(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查是否存在序列化过的对象文件
     *
     * @param context  上下文，要求为 Activity 实例
     * @param filename 文件名，不能为空
     * @return
     */
    public boolean existFile(Context context, String filename) {
        try {
            if (context != null) {
                String[] files = context.fileList();
                for (String file : files) {
                    if (filename.equals(file)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Writes a file to Disk.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform this operation using another thread.
     *
     * @param file The file to write to Disk.
     */
    public void writeToFile(File file, String fileContent) {
        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(fileContent);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    /**
     * Reads a content from a file.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param file The file to read from.
     * @return A string with the content of the file.
     */
    public String readFileContent(File file) {
        StringBuilder fileContentBuilder = new StringBuilder();
        if (file.exists()) {
            String stringLine;
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((stringLine = bufferedReader.readLine()) != null) {
                    fileContentBuilder.append(stringLine + "\n");
                }
                bufferedReader.close();
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileContentBuilder.toString();
    }

    /**
     * Returns a boolean indicating whether this file can be found on the underlying file system.
     *
     * @param file The file to check existence.
     * @return true if this file exists, false otherwise.
     */
    public boolean exists(File file) {
        return file.exists();
    }

    /**
     * Warning: Deletes the content of a directory.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param directory The directory which its content will be deleted.
     */
    public void clearDirectory(File directory) {
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                file.delete();
            }
        }
    }

    /**
     * Write a value to a user preferences file.
     *
     * @param context            {@link Context} to retrieve android user preferences.
     * @param preferenceFileName A file name reprensenting where data will be written to.
     * @param key                A string for the key that will be used to retrieve the value in the future.
     * @param value              A long representing the value to be inserted.
     */
    public <T> void writeToPreferences(Context context, String preferenceFileName, String key,
                                       T value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
            String methodName = "put" + value.getClass().getSimpleName();

            Log.d(LOG_TAG, "准备调用方法[" + methodName + "]");

            SharedPreferences.class.getMethod(methodName).invoke(editor, value);
        } catch (Exception e) {
//            Log.e(LOG_TAG, ExceptionUtils.getStackTrace(e));
        }

        editor.apply();
    }

    /**
     * Get a value from a user preferences file.
     *
     * @param context            {@link Context} to retrieve android user preferences.
     * @param preferenceFileName A file name representing where data will be get from.
     * @param key                A key that will be used to retrieve the value from the preference file.
     * @return A long representing the value retrieved from the preferences file.
     */
    public <T> T getFromPreferences(Context context, String preferenceFileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        return (T) sharedPreferences.getAll().get(key);
    }

    /**
     * 从默认SP中获取参数
     *
     * @param context
     * @param key
     * @return
     */
    public <T> T getFromDefaultPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return (T) sharedPreferences.getAll().get(key);
    }

    /**
     * 写入到默认SP中
     *
     * @param context
     * @param key
     * @param value
     */
    public <T> void writeToDefaultPreferences(Context context, String key, T value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            String methodName = "put" + value.getClass().getSimpleName();

            Log.d(LOG_TAG, "准备调用方法[" + methodName + "]");

            SharedPreferences.class.getMethod(methodName).invoke(editor, value);
        } catch (Exception e) {
//            Log.e(LOG_TAG, ExceptionUtils.getStackTrace(e));
        }

        editor.apply();
    }

    /**
     * 方法: saveData <br>
     * 描述:  保存<br>
     *
     * @param context  上下文对象
     * @param key      键
     * @param data     值
     * @param filename 保存的文件名
     */
    public void saveData(Context context, String key, Object data,
                         String filename) {
        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = null;
        if (TextUtils.isEmpty(filename)) {
            PreferenceManager.getDefaultSharedPreferences(context);
        }
        sharedPreferences = context.getSharedPreferences(
                filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        } else {
            Log.e(LOG_TAG, "不存在[\" + " + type + " + \"]方法");
        }
        Log.e(LOG_TAG, "保存[\" + " + key + "+ \"]成功");
        editor.commit();
    }

    /**
     * 保存到默认的SharedPreferences文件中
     *
     * @param context
     * @param key
     * @param data
     */
    public void save2DefaultData(Context context, String key, Object data) {
        saveData(context, key, data, null);
    }

    /**
     * 从默认的SharedPreferences文件中取数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public Object getDefaultData(Context context, String key, Object defValue) {
        return getData(context, key, defValue, null);
    }

    /**
     * 方法: getData <br>
     * 描述: 取数据<br>
     *
     * @param context
     * @param key          键
     * @param defValue     默认值
     * @param savefilename 文件名
     * @return
     */
    public Object getData(Context context, String key, Object defValue,
                          String savefilename) {
        if (null == defValue) {
            throw new IllegalArgumentException("defValue 默认参数不能为空");
        }
        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = null;
        if (TextUtils.isEmpty(savefilename)) {
            PreferenceManager.getDefaultSharedPreferences(context);
        }
        sharedPreferences = context.getSharedPreferences(
                savefilename, Context.MODE_PRIVATE);
        // defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        } else {
            Log.e(LOG_TAG, "不存在[\" + " + type + " + \"]方法");
        }
        return defValue;
    }

}
