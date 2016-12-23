package jacksonmarkowski.launcher;

public class App {

    private int id;
    private String name;

    public App() {

    }

    public App(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
