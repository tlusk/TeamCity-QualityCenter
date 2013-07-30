package darkcube.teamcity.issueTracker;

import jetbrains.buildServer.issueTracker.errors.NotFoundException;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import org.apache.commons.httpclient.Credentials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QualityCenterIssueFetcher extends AbstractIssueFetcher {

  public QualityCenterIssueFetcher(@NotNull EhCacheUtil cacheUtil) {
    super(cacheUtil);
  }

  @NotNull
  @Override
  public IssueData getIssue(@NotNull String host, @NotNull String id, @Nullable Credentials credentials) throws Exception {
    throw new NotFoundException("UNIMPLE MENTED");
  }

  @NotNull
  @Override
  public String getUrl(@NotNull String host, @NotNull String id) {
    return "td://PROJECT.DOMAIN." + host + "/gcbin/Defects?Action=FindDefect&DefectID=" + id;
  }
}
