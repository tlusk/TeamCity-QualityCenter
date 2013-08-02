package darkcube.teamcity.issueTracker;

import jetbrains.buildServer.issueTracker.AbstractIssueProvider;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QualityCenterIssueProvider extends AbstractIssueProvider {

  public QualityCenterIssueProvider(@NotNull String type, @NotNull IssueFetcher fetcher) {
    super(type, fetcher);
  }

  @Override
  public void setProperties(@NotNull final Map<String, String> map) {
    super.setProperties(map);
  }

  @NotNull
  @Override
  protected Pattern compilePattern(@NotNull final Map<String, String> properties) {
    String prefixes = properties.get("idPrefix");
    Pattern pattern = EMPTY_PATTERN;

    if(prefixes != null)
    {
      List<String> list = StringUtil.split(prefixes);
      StringBuilder builder = new StringBuilder();
      builder.append("(?<![a-zA-Z_-])")
              .append("(");
      for (String s : list) {
        builder.append(s).append("|");
      }
      if (builder.length() > 0) {
        builder.deleteCharAt(builder.length() - 1);
      }
      builder.append(")")
              .append("-")
              .append("(\\d+)")
              .append("(?![0-9_-])");

      pattern = safeCompile(builder.toString());
    }

    if (myFetcher instanceof QualityCenterIssueFetcher) {
      ((QualityCenterIssueFetcher)myFetcher).setPattern(pattern);
    }
    return pattern;
  }

  @Override
  protected boolean useIdPrefix() {
    return true;
  }
}
