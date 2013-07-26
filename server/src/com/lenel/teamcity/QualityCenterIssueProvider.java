package com.lenel.teamcity;

import jetbrains.buildServer.issueTracker.AbstractIssueProvider;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class QualityCenterIssueProvider extends AbstractIssueProvider {
  public QualityCenterIssueProvider(@NotNull IssueFetcher fetcher) {
    super("QualityCenter", fetcher);
  }

  protected QualityCenterIssueProvider(@NotNull Pattern pattern, @NotNull IssueFetcher fetcher) {
    super(pattern, fetcher);
  }
}
