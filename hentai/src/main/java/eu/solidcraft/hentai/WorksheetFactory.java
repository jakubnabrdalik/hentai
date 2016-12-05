package eu.solidcraft.hentai;

public class WorksheetFactory {

    private WorksheetBuilder builder;

    public WorksheetFactory worksheet(String title) {
        builder = new WorksheetBuilder(title);
        return this;
    }

    public Worksheet create() {
        return builder.create();
    }


    private static class WorksheetBuilder  {

        private final String title;

        public WorksheetBuilder(String title) {
            this.title = title;
        }

        public Worksheet create() {
            return new Worksheet(title);
        }
    }
}
