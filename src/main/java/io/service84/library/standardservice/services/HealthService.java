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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("C36ABA46-34D7-42A7-8B4E-98125BC54B2F")
public class HealthService {
  private static final Logger logger = LoggerFactory.getLogger(HealthService.class);

  public static interface HealthContributor {
    Boolean isHealthy();
  }

  List<HealthContributor> healthContributors = new CopyOnWriteArrayList<>();

  public Boolean isHealthy() {
    logger.debug("isHealthy");
    for (HealthContributor healthContributor : healthContributors) {
      if (healthContributor != null) {
        try {
          if (!healthContributor.isHealthy()) {
            return Boolean.FALSE;
          }
        } catch (Throwable t) {
          return Boolean.FALSE;
        }
      }
    }

    return Boolean.TRUE;
  }

  public synchronized void registerReadinessContributor(HealthContributor healthContributor) {
    logger.debug("registerReadinessContributor");
    healthContributors.add(healthContributor);
  }

  public synchronized void unregisterReadinessContributor(HealthContributor healthContributor) {
    logger.debug("unregisterReadinessContributor");
    healthContributors.remove(healthContributor);
  }
}
