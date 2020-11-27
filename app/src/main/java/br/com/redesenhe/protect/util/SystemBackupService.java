package br.com.redesenhe.protect.util;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import br.com.redesenhe.protect.service.constants.ProtectConstants;
import br.com.redesenhe.protect.service.listener.ValidationListener;

import static br.com.redesenhe.protect.service.constants.ProtectConstants.SYSTEM.LOG;

public class SystemBackupService {

    private static final String CURRENT_DB_FOLDER = "//data//br.com.redesenhe.protect//databases//";
    private static final String BACKUP_FOLDER = "//protect//backup//";
    private static final String CURRENT_DB_PATH = CURRENT_DB_FOLDER + ProtectConstants.DATA_BASE.NAME;

    private SystemBackupService() {
        super();
    }

    public static ValidationListener exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Log.d(LOG, "Fazendo backup da base de dados");

            if (sd.canWrite()) {

                File  backupFolder = new File(sd,BACKUP_FOLDER);
                String backupDBPath = BACKUP_FOLDER + ProtectConstants.DATA_BASE.NAME;
                File currentDB = new File(data, CURRENT_DB_PATH);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    Log.d(LOG, "Pode escrever no cartao de memoria");
                    if (!backupFolder.exists()){
                        backupFolder.mkdirs();
                    }

                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();

                    Log.d(LOG, "O banco de dados da aplicação existe e foi copiado. Backup realizado");

                    return new ValidationListener();
                }
            }
            return new ValidationListener("Não foi possivel realizar backup");
        }
        catch (Exception e) {
            return new ValidationListener(e.toString());
        }
    }

    public static ValidationListener importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Log.d(LOG, "Restaurando a base de dados da aplicação");

            if (sd.canWrite()) {
                Log.d(LOG, "Pode escrever no sd");
                File  backupFolder = new File(sd,BACKUP_FOLDER);
                String backupDBPath = BACKUP_FOLDER + ProtectConstants.DATA_BASE.NAME;
                File backupDB = new File(sd, backupDBPath);
                File currentDB = new File(data, CURRENT_DB_PATH);

                if (!backupFolder.exists()){
                    backupFolder.mkdirs();
                }

                if (currentDB.canWrite()){
                    Log.d(LOG, "Pode escrever na area de banco da aplicação");

                    if(backupDB.exists()) {
                        FileChannel src = new FileInputStream(backupDB).getChannel();
                        FileChannel dst = new FileOutputStream(currentDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                        Log.d(LOG, "O backup da aplicação foi restaurado");
                        return new ValidationListener();
                    }
                    return new ValidationListener("Não foi localizado arquivo de backup");
                }
            }
        }
        catch (Exception e) {
            return new ValidationListener(e.toString());
        }
        return new ValidationListener("Não foi possivel restaurar base");
    }

    public static boolean deleteDataBase() {
        if(existeDatabase()) {
            File data = Environment.getDataDirectory();
            File currentDB = new File(data, CURRENT_DB_PATH);
            return currentDB.delete();
        }
        return false;
    }

    public static boolean existeDatabase() {
        return new File(Environment.getDataDirectory(), CURRENT_DB_PATH).exists();
    }
}
