package darkcube.teamcity.model;

public class ProjectDomainMapping {
    private final String domain;
    private final String project;

    public ProjectDomainMapping( String domain, String project ) {
        this.domain = domain;
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public String getDomain() {
        return domain;
    }
}