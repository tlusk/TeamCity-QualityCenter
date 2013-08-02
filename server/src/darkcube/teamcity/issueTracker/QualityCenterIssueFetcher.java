package darkcube.teamcity.issueTracker;

import com.intellij.openapi.util.Pair;
import darkcube.qc.client.QCRestClient;
import darkcube.qc.model.Defect;
import darkcube.qc.model.DefectField;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QualityCenterIssueFetcher extends AbstractIssueFetcher {

  private Pattern pattern;
  private Map<String,Pair<String,String>> projectMapping;

  public QualityCenterIssueFetcher(@NotNull EhCacheUtil cacheUtil) {
    super(cacheUtil);
  }

  public void setPattern(final Pattern pattern) {
    this.pattern = pattern;
  }

  public void setProjectMapping(final Map<String,Pair<String,String>> projectMapping) {
    this.projectMapping = projectMapping;
  }

  @NotNull
  @Override
  public IssueData getIssue(@NotNull String host, @NotNull String id, @Nullable Credentials credentials) throws Exception {
    Pair<String,Integer> idPair = getRealId(id);
    Pair<String,String> projectMap = projectMapping.get(idPair.first);

    String username = "";
    String password = "";

    if(credentials instanceof UsernamePasswordCredentials) {
      username = ((UsernamePasswordCredentials)credentials).getUserName();
      password = ((UsernamePasswordCredentials)credentials).getPassword();
    }

    QCRestClient client = new QCRestClient(host, username, password);
    client.login();

    Defect defect = client.getDefect(projectMap.first, projectMap.second ,idPair.second);

    IssueData issueData = new IssueData(id, defect.getField(DefectField.NAME), defect.getField(DefectField.STATUS), getUrl(host, id), false);
    return issueData;
  }

  @NotNull
  @Override
  public String getUrl(@NotNull String host, @NotNull String id) {
    Pair<String,Integer> idPair = getRealId(id);
    Pair<String,String> projectMap = projectMapping.get(idPair.first);

    return "td://" + projectMap.second + "." + projectMap.first + "." + host + "/gcbin/Defects?Action=FindDefect&DefectID=" + id;
  }

  private Pair<String,Integer> getRealId(@NotNull String id) {
    Matcher matcher = pattern.matcher(id);

    if (matcher.find()) {
      String project = matcher.group("project");
      Integer realId = Integer.parseInt(matcher.group("id"));

      return new Pair<>(project, realId);
    }
    return null;
  }
}
