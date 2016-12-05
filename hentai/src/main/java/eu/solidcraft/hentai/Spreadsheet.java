package eu.solidcraft.hentai;

import lombok.Data;

@Data
public class Spreadsheet {

    private String title;

    public Spreadsheet(String title) {
        this.title = title;
    }

    public Worksheet worksheet(String title) {
        return null;
    }
}
