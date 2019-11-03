public class Subject {
    private String name;
    private String id;

    public Subject(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Subject(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((Subject) obj).id);
    }

}
