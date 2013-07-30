package darkcube.teamcity.issueTracker;

import jetbrains.buildServer.issueTracker.AbstractIssueProvider;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class QualityCenterIssueProvider extends AbstractIssueProvider {

  public QualityCenterIssueProvider(@NotNull String type, @NotNull IssueFetcher fetcher) {
    super(type, fetcher);
  }
}
