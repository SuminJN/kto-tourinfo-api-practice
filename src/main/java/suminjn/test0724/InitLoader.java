package suminjn.test0724;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import suminjn.test0724.service.TourApiService;

@Component
@RequiredArgsConstructor
public class InitLoader implements CommandLineRunner {

    private final TourApiService tourApiService;

    @Override
    public void run(String... args) throws Exception {
        tourApiService.syncTourContents();
    }
}
