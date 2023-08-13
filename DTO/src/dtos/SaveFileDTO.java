package dtos;

import java.io.Serializable;

public class SaveFileDTO implements Serializable {
    private final String fullFilePath;
    private final String fileName;
    private final WorldCodeForm savedCurrentForm;
    private final WorldCodeForm savedOriginalForm;
    public SaveFileDTO(String fullFilePath, String fileName, WorldCodeForm savedCurrentForm, WorldCodeForm savedOriginalForm){
        this.fullFilePath = fullFilePath;
        this.fileName = fileName;
        this.savedCurrentForm = savedCurrentForm;
        this.savedOriginalForm = savedOriginalForm;
    }

    public String getFullFilePath() {
        return fullFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public WorldCodeForm getSavedOriginalForm() {
        return savedOriginalForm;
    }

    public WorldCodeForm getSavedCurrentForm() {
        return savedCurrentForm;
    }
}
// how to divide DTOs to this project:
// the files that will be need
