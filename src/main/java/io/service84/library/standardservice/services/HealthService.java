package io.service84.library.standardservice.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

@Service("C36ABA46-34D7-42A7-8B4E-98125BC54B2F")
public class HealthService {
  public static interface HealthContributor {
    Boolean isReady();
  }

  List<HealthContributor> healthContributors = new CopyOnWriteArrayList<>();

  public Boolean isHealthy() {
    for (HealthContributor healthContributor : healthContributors) {
      if (healthContributor != null) {
        if (!healthContributor.isReady()) {
          return Boolean.FALSE;
        }
      }
    }

    return Boolean.TRUE;
  }

  public synchronized void registerReadinessContributor(HealthContributor healthContributor) {
    healthContributors.add(healthContributor);
  }

  public synchronized void unregisterReadinessContributor(HealthContributor healthContributor) {
    healthContributors.remove(healthContributor);
  }
}
