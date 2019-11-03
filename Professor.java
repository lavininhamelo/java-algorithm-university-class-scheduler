public class Professor {
    private String name;
    private String id;

    public Professor(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Professor(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.id.equals(((Professor) obj).id));
    }
}
