package idv.rap.demo.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author raphael.wong
 * @since 01 July 2022
 */
@Slf4j
@Component
public class GracefulShutdownEvent implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // TODO close thread pool when threads are idle

    }
}
