package darkcube.teamcity.issueTracker;

import darkcube.qc.client.QCRestClient;
import darkcube.qc.model.Defect;
import darkcube.qc.model.DefectField;
import darkcube.qc.model.Domain;
import darkcube.qc.model.Project;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QualityCenterIssueFetcher extends AbstractIssueFetcher {

  public QualityCenterIssueFetcher(@NotNull EhCacheUtil cacheUtil) {
    super(cacheUtil);
  }

  @NotNull
  @Override
  public IssueData getIssue(@NotNull String host, @NotNull String id, @Nullable Credentials credentials) throws Exception {
    String username = "";
    String password = "";

    if(credentials instanceof UsernamePasswordCredentials) {
      username = ((UsernamePasswordCredentials)credentials).getUserName();
      password = ((UsernamePasswordCredentials)credentials).getPassword();
    }

    QCRestClient client = new QCRestClient(host, username, password);
    client.login();

    List<Domain> domains = client.getDomains();
    List<Project> projects = client.getProjects(domains.get(0).getName());
    Defect defect = client.getDefect(domains.get(0).getName(),projects.get(0).getName(),Integer.parseInt(id));

    IssueData issueData = new IssueData(id, defect.getField(DefectField.NAME), defect.getField(DefectField.STATUS), getUrl(host, id), false);
    return issueData;
  }

  @NotNull
  @Override
  public String getUrl(@NotNull String host, @NotNull String id) {
    return "td://PROJECT.DOMAIN." + host + "/gcbin/Defects?Action=FindDefect&DefectID=" + id;
  }
}
