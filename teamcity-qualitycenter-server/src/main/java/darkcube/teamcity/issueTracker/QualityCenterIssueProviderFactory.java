package darkcube.teamcity.issueTracker;

import jetbrains.buildServer.issueTracker.AbstractIssueProviderFactory;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.issueTracker.IssueProvider;
import org.jetbrains.annotations.NotNull;

public class QualityCenterIssueProviderFactory extends AbstractIssueProviderFactory {

  public QualityCenterIssueProviderFactory(@NotNull IssueFetcher fetcher) {
    super(fetcher, "QualityCenter");
  }

  @NotNull
  public IssueProvider createProvider() {
    return new QualityCenterIssueProvider(myFetcher);
  }
}
