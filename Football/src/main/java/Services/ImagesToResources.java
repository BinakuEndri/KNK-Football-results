package Services;

import Repository.LeagueRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImagesToResources {


    public static void imageToResources(String folderName, String imageName, Path sourcePath) throws IOException {
            String path = "C:\\Users\\PC-SYSTEMS\\IdeaProjects\\KNK-Football-results\\Football\\src\\main\\resources\\images\\" + folderName + "\\" + imageName;

            File filedest = new File(path);
            if (!filedest.exists()) {
                path = "C:\\Users\\PC-SYSTEMS\\IdeaProjects\\KNK-Football-results\\Football\\src\\main\\resources\\images\\" + folderName;
                File folder = new File(path);
                folder.mkdir();
                File newFile = new File(path + "\\" + imageName);
                Files.copy(sourcePath, newFile.toPath());
            } else {
                Files.copy(sourcePath, filedest.toPath());
            }
    }
}
