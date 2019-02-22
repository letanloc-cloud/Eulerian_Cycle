/*
 * Created by Lê Tấn Lộc on 14:34 22/02/2019
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 14:34 22/02/2019
 */

package app.b1605339.letanloc.eulerian_cycle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileOperations {
    public Boolean write(String fileName, String fileContent){
        try{
            String filePath = "/sdcard/" + fileName + ".txt";
            File file = new File(filePath);

            //if file doesn't exist, then create it
            if(!file.exists()){
                file.createNewFile();
            }

            Writer bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file.getAbsolutePath()),
                    "UTF-8"
            ));

            bw.write(fileContent);
            bw.close();

            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}