package comp3350.projectmanagementapplication;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import comp3350.projectmanagementapplication.persistence.DataAccess;
import comp3350.projectmanagementapplication.persistence.HSQLDatabase;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        App.writeDatabaseScript(this.getApplicationContext(), false);
    }

    public static DataAccess getDatabase() {
        return HSQLDatabase.getInstance();
    }

    public static void writeDatabaseScript(Context context, boolean overwrite) {
        App.writeDatabaseScript(
            context,
            HSQLDatabase.FILE,
            HSQLDatabase.FILE_SYSTEM_DIRECTORY,
            overwrite
        );
    }

    public static void writeDatabaseScript(Context context, String assetFilePath, String fileSystemDirectoryPath, boolean overwrite) {
        File fileSystemDirectory = context.getDir(fileSystemDirectoryPath, Context.MODE_PRIVATE);

        try {
            Class.forName("org.hsqldb.jdbcDriver");

            // Write asset database to local database if it doesn't already exist
            if (overwrite || !Arrays.asList(fileSystemDirectory.list()).contains(assetFilePath)) {
                App.writeAssetToFileSystem(context, assetFilePath, fileSystemDirectoryPath);
            }
        } catch (IOException exception) {
            System.out.println("Failed to write database script");
        } catch (ClassNotFoundException exception) {
            System.out.println("Failed to initialize HSQLDB");
        }
    }

    public static void writeAssetToFileSystem(Context context, String assetPath, String fileSystemDirectoryPath) throws IOException {
        // Get reader for asset file
        InputStreamReader databaseAssetReader = new InputStreamReader(
            context.getAssets().open(assetPath)
        );

        // Get writer for local file
        File fsDirectory = context.getDir(fileSystemDirectoryPath, Context.MODE_PRIVATE);
        FileWriter fsDatabaseAssetWriter = new FileWriter(
            fsDirectory.toString() + File.separator + assetPath
        );

        // Write contents of asset to local file
        char[] buffer = new char[1024];
        int lengthRead;
        while ((lengthRead = databaseAssetReader.read(buffer)) > 0) {
            fsDatabaseAssetWriter.write(buffer, 0, lengthRead);
        }

        fsDatabaseAssetWriter.close();
        databaseAssetReader.close();
    }
}
