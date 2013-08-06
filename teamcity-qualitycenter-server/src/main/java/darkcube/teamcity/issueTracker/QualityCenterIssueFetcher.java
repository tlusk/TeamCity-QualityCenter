package darkcube.teamcity.issueTracker;

import darkcube.qc.client.QCRestClient;
import darkcube.qc.model.Defect;
import darkcube.qc.model.DefectField;
import darkcube.teamcity.model.ProjectDomainMapping;
import darkcube.teamcity.model.ProjectIdMapping;
import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QualityCenterIssueFetcher extends AbstractIssueFetcher {

  private Pattern pattern;
  private Map<String,ProjectDomainMapping> projectMapping;

  public QualityCenterIssueFetcher(@NotNull EhCacheUtil cacheUtil) {
    super(cacheUtil);
  }

  public void setPattern(final Pattern pattern) {
    this.pattern = pattern;
  }

  public void setProjectMapping(final Map<String,ProjectDomainMapping> projectMapping) {
    this.projectMapping = projectMapping;
  }

  @NotNull
  public IssueData getIssue(@NotNull String host, @NotNull String id, @Nullable Credentials credentials) throws Exception {
    ProjectIdMapping idPair = getRealId(id);
    ProjectDomainMapping projectMap = projectMapping.get(idPair.getProject());

    String username = "";
    String password = "";

    if(credentials instanceof UsernamePasswordCredentials) {
      username = ((UsernamePasswordCredentials)credentials).getUserName();
      password = ((UsernamePasswordCredentials)credentials).getPassword();
    }

    QCRestClient client = new QCRestClient(host, username, password);
    client.login();

    Defect defect = client.getDefect(projectMap.getDomain(), projectMap.getProject() ,idPair.getId());

    IssueData issueData = new IssueData(id, defect.getField(DefectField.NAME), defect.getField(DefectField.STATUS), getUrl(host, id), false);
    return issueData;
  }

  @NotNull
  public String getUrl(@NotNull String host, @NotNull String id) {
    ProjectIdMapping idPair = getRealId(id);
    ProjectDomainMapping projectMap = projectMapping.get(idPair.getProject());

    return "td://" + projectMap.getProject() + "." + projectMap.getDomain() + "." + host + "/gcbin/Defects?Action=FindDefect&DefectID=" + id;
  }

  private ProjectIdMapping getRealId(@NotNull String id) {
    Matcher matcher = pattern.matcher(id);

    if (matcher.find()) {
      String project = matcher.group("project");
      Integer realId = Integer.parseInt(matcher.group("id"));

      return new ProjectIdMapping(realId, project);
    }
    return null;
  }
}


