package eu.solidcraft.hentai;

public class SpreadsheetFactory {

    private SpreadsheetBuilder builder;

    public SpreadsheetFactory spreadsheet(String title) {
        builder = new SpreadsheetBuilder(title);
        return this;
    }

    public Spreadsheet create() {
        return builder.create();
    }

    private static class SpreadsheetBuilder  {

        private final String title;

        public SpreadsheetBuilder(String title) {
           this.title = title;
        }

        public Spreadsheet create() {
            return new Spreadsheet(title);
        }
    }
}
