/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.service84.library.standardservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

@Service("CB8BAAA5-D6BD-46A0-91AE-D9C076F96598")
public class MetricsService {
  public static interface MetricsContributor {
    String key();

    Object value();
  }

  List<MetricsContributor> metricsContributors = new CopyOnWriteArrayList<>();

  public Map<String, Object> getMetrics() {
    Map<String, Object> metrics = new HashMap<>();

    for (MetricsContributor metricsContributor : metricsContributors) {
      if (metricsContributor != null) {
        try {
          metrics.put(metricsContributor.key(), safeValue(metricsContributor));
        } catch (Throwable t) {
          // Bad MetricsContributor
        }
      }
    }

    return metrics;
  }

  public synchronized void registerMetricsContributor(MetricsContributor metricsContributor) {
    metricsContributors.add(metricsContributor);
  }

  private Object safeValue(MetricsContributor metricsContributor) {
    try {
      return metricsContributor.value();
    } catch (Throwable t) {
      return null;
    }
  }

  public synchronized void unregisterReadinessContributor(MetricsContributor metricsContributor) {
    metricsContributors.remove(metricsContributor);
  }
}
