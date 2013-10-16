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

  private static Pattern HOSTNAME_PATTERN = Pattern.compile("^https?://(?<hostname>[^/:?#]+)(?:[/:?#]|$)");
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

  public class QualityCenterIssueFetchFunction implements AbstractIssueFetcher.FetchFunction {

      private String host;
      private String id;
      private UsernamePasswordCredentials credentials;

      public QualityCenterIssueFetchFunction(final String host, final String id, final Credentials credentials) {
          if (host.length() == 0) {
              throw new IllegalArgumentException(String.format("ServerUrl cannot be empty"));
          }
          if (!(credentials instanceof UsernamePasswordCredentials)) {
              throw new IllegalArgumentException(String.format("Credentials must be of UsernamePasswordCredentials type"));
          }

          this.host = host;
          this.id = id;
          this.credentials = (UsernamePasswordCredentials)credentials;
      }

      @NotNull
      public IssueData fetch() {
          ProjectIdMapping idPair = getRealId(id);
          ProjectDomainMapping projectMap = projectMapping.get(idPair.getProject());

          QCRestClient client = new QCRestClient(host, credentials.getUserName(), credentials.getPassword());
          client.login();

          Defect defect = client.getDefect(projectMap.getDomain(), projectMap.getProject() ,idPair.getId());

          String status = defect.getField(DefectField.STATUS);
          Boolean resolved = false;
          if(status.equals("Fixed") ||
             status.equals("Closed") ||
             status.equals("Rejected") ||
             status.equals("Verified"))
          {
              resolved = true;
          }

          IssueData issueData = new IssueData(id, defect.getField(DefectField.NAME), status, getUrl(host, id), resolved);
          return issueData;
      }
  }

  @NotNull
  public IssueData getIssue(@NotNull String host, @NotNull String id, @Nullable Credentials credentials) throws Exception {
    String url = getUrl(host, id);
    return getFromCacheOrFetch(url, new QualityCenterIssueFetchFunction(host, id, credentials));
  }

  @NotNull
  public String getUrl(@NotNull String host, @NotNull String id) {
    ProjectIdMapping idPair = getRealId(id);
    ProjectDomainMapping projectMap = projectMapping.get(idPair.getProject());

    return "td://" + projectMap.getProject() + "." + projectMap.getDomain() + "." + getHostname(host) + "/qcbin/Defects?Action=FindDefect&DefectID=" + idPair.getId();
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

  private String getHostname(@NotNull String host) {
    Matcher matcher = HOSTNAME_PATTERN.matcher(host);

    if (matcher.find()) {
      return matcher.group("hostname");
    }

    return "";
  }
}


