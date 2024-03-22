package tech.drufontael.marketlist.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import tech.drufontael.marketlist.data.model.GoodsList;

public class GoodsListFile {
    public static void saveListToFile(Context context, String fileName, List<GoodsList> goodsList) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(goodsList);


            File file = new File(context.getFilesDir(), fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(json.getBytes());
            outputStream.close();

    }

    public static List<GoodsList> loadListFromFile(Context context, String fileName) throws IOException {
        Gson gson = new Gson();
        StringBuilder stringBuilder = new StringBuilder();


            File file = new File(context.getFilesDir(), fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();


        String json = stringBuilder.toString();
        return gson.fromJson(json, new TypeToken<List<GoodsList>>() {}.getType());
    }
}
