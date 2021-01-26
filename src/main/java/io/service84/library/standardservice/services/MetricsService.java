package io.service84.library.standardservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

@Service("CB8BAAA5-D6BD-46A0-91AE-D9C076F96598")
public class MetricsService {
  public static interface MetricsContributor {
    Object metric();
    String metricKey();
  }

  List<MetricsContributor> metricsContributors = new CopyOnWriteArrayList<>();

  public Map<String, Object> getMetrics() {
    Map<String, Object> metrics = new HashMap<>();

    for (MetricsContributor metricsContributor : metricsContributors) {
      if (metricsContributor != null) {
        metrics.put(metricsContributor.metricKey(), metricsContributor.metric());
      }
    }

    return metrics;
  }

  public synchronized void registerMetricsContributor(MetricsContributor metricsContributor) {
    metricsContributors.add(metricsContributor);
  }

  public synchronized void unregisterReadinessContributor(MetricsContributor metricsContributor) {
	  metricsContributors.remove(metricsContributor);
  }
}
