package darkcube.teamcity.issueTracker;

import jetbrains.buildServer.issueTracker.AbstractIssueProviderFactory;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.issueTracker.IssueProvider;
import org.jetbrains.annotations.NotNull;

public class QualityCenterIssueProviderFactory extends AbstractIssueProviderFactory {

  private static String FACTORY_TYPE = "QualityCenter";

  public QualityCenterIssueProviderFactory(@NotNull IssueFetcher fetcher) {
    super(fetcher, FACTORY_TYPE);
  }

  @NotNull
  @Override
  public IssueProvider createProvider() {
    return new QualityCenterIssueProvider(FACTORY_TYPE, myFetcher);
  }
}
