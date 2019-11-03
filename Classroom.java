public class Classroom {
    private String name;
    private String id;

    public Classroom(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Classroom(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.id.equals(((Classroom) obj).id));
    }
}
