package darkcube.teamcity.issueTracker;

import com.intellij.openapi.util.Pair;
import jetbrains.buildServer.issueTracker.AbstractIssueProvider;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    Pattern mappingPattern = safeCompile("(?<mapping>(?<domain>[a-zA-Z_-]*):(?<project>[a-zA-Z_-]*):(?<key>[a-zA-Z_-]*))");
    Matcher matcher = mappingPattern.matcher(prefixes);
    Map<String, Pair<String, String>> projectMapping = new HashMap<>();
    while (matcher.find()) {
      String domain = matcher.group("domain");
      String project = matcher.group("project");
      String key = matcher.group("key");

      projectMapping.put(key, new Pair<>(domain, project));
    }

    if(projectMapping.size() > 0)
    {
      Set<String> keys = projectMapping.keySet();
      StringBuilder builder = new StringBuilder();
      builder.append("(?<![a-zA-Z_-])")
              .append("(?<project>");
      for (String s : keys) {
        builder.append(s).append("|");
      }
      if (builder.length() > 0) {
        builder.deleteCharAt(builder.length() - 1);
      }
      builder.append(")")
              .append("-")
              .append("(?<id>\\d+)")
              .append("(?![0-9_-])");

      pattern = safeCompile(builder.toString());
    }

    if (myFetcher instanceof QualityCenterIssueFetcher) {
      ((QualityCenterIssueFetcher)myFetcher).setPattern(pattern);
      ((QualityCenterIssueFetcher)myFetcher).setProjectMapping(projectMapping);
    }
    return pattern;
  }

  @Override
  protected boolean useIdPrefix() {
    return true;
  }
}
