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

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("26A42823-D5B5-4385-85CB-6E9C4669B85C")
public class ReadinessService {
  private static final Logger logger = LoggerFactory.getLogger(ReadinessService.class);

  public static interface ReadinessContributor {
    Boolean isReady();
  }

  public static class UnreadyUntil implements ReadinessContributor {
    private static final Logger logger = LoggerFactory.getLogger(UnreadyUntil.class);

    private ReadinessService readinessService;
    private LocalDateTime until;

    UnreadyUntil(LocalDateTime until, ReadinessService readinessService) {
      this.until = until;
      this.readinessService = readinessService;
    }

    @Override
    public Boolean isReady() {
      logger.debug("isReady");
      if (until.isAfter(LocalDateTime.now())) {
        return Boolean.FALSE;
      }

      readinessService.unregisterReadinessContributor(this);
      return Boolean.TRUE;
    }
  }

  List<ReadinessContributor> readinessContributors = new CopyOnWriteArrayList<>();

  public Boolean isReady() {
    logger.debug("isReady");
    for (ReadinessContributor readinessContributor : readinessContributors) {
      if (readinessContributor != null) {
        try {
          if (!readinessContributor.isReady()) {
            return Boolean.FALSE;
          }
        } catch (Throwable t) {
          return Boolean.FALSE;
        }
      }
    }

    return Boolean.TRUE;
  }

  public synchronized void registerReadinessContributor(ReadinessContributor readinessContributor) {
    logger.debug("registerReadinessContributor");
    readinessContributors.add(readinessContributor);
  }

  public synchronized void unregisterReadinessContributor(
      ReadinessContributor readinessContributor) {
    logger.debug("unregisterReadinessContributor");
    readinessContributors.remove(readinessContributor);
  }
}
