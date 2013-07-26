package com.lenel.teamcity;

import jetbrains.buildServer.issueTracker.AbstractIssueProviderFactory;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.issueTracker.IssueProvider;
import org.jetbrains.annotations.NotNull;

public class QualityCenterIssueProviderFactory extends AbstractIssueProviderFactory {

  protected QualityCenterIssueProviderFactory(@NotNull IssueFetcher fetcher) {
    super(fetcher, "QualityCenter");
  }

  @NotNull
  @Override
  public IssueProvider createProvider() {
    return new QualityCenterIssueProvider(myFetcher);
  }
}
