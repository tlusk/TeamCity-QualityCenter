package com.lenel.teamcity;

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
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @NotNull
  @Override
  public String getUrl(@NotNull String host, @NotNull String id) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
