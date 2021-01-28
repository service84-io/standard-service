package io.service84.library.standardservice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

@Service("26A42823-D5B5-4385-85CB-6E9C4669B85C")
public class ReadinessService {
  public static interface ReadinessContributor {
    Boolean isReady();
  }

  public static class UnreadyUntil implements ReadinessContributor {
    private ReadinessService readinessService;
    private LocalDateTime until;

    UnreadyUntil(LocalDateTime until, ReadinessService readinessService) {
      this.until = until;
      this.readinessService = readinessService;
    }

    @Override
    public Boolean isReady() {
      if (until.isAfter(LocalDateTime.now())) {
        return Boolean.FALSE;
      } else {
        readinessService.unregisterReadinessContributor(this);
        return Boolean.TRUE;
      }
    }
  }

  List<ReadinessContributor> readinessContributors = new CopyOnWriteArrayList<>();

  public Boolean isReady() {
    for (ReadinessContributor readinessContributor : readinessContributors) {
      if (readinessContributor != null) {
        if (!readinessContributor.isReady()) {
          return Boolean.FALSE;
        }
      }
    }

    return Boolean.TRUE;
  }

  public synchronized void registerReadinessContributor(ReadinessContributor readinessContributor) {
    readinessContributors.add(readinessContributor);
  }

  public synchronized void unregisterReadinessContributor(
      ReadinessContributor readinessContributor) {
    readinessContributors.remove(readinessContributor);
  }
}
