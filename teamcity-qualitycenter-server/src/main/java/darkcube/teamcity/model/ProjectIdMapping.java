package darkcube.teamcity.model;

public class ProjectIdMapping {
    private final int id;
    private final String project;

    public ProjectIdMapping( int id, String project ) {
        this.id = id;
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public int getId() {
        return id;
    }
}