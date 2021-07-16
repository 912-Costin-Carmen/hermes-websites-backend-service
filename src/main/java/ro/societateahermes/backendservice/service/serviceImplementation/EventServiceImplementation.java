package ro.societateahermes.backendservice.service.serviceImplementation;

import net.sf.saxon.functions.ConstantFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.societateahermes.backendservice.entities.Event;
import ro.societateahermes.backendservice.repository.EventRepositoryInterface;
import ro.societateahermes.backendservice.service.EventService;
import ro.societateahermes.backendservice.service.dto.NotificationSwitchDTO;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class EventServiceImplementation implements EventService {

    private final EventRepositoryInterface eventRepository;

    @Autowired
    public EventServiceImplementation(EventRepositoryInterface eventRepository) {
        this.eventRepository = eventRepository;
    }

    public NotificationSwitchDTO getEventStatusByEventName(String eventName) {
        Optional<Event> eventOptional = eventRepository.findByEventName(eventName);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (event.getEventStartDate().isAfter(LocalDateTime.now())) {
                return new NotificationSwitchDTO("Event has not started yet", false);
            } else if (event.getEventEndDate().isAfter(LocalDateTime.now())) {
                return new NotificationSwitchDTO("Event has started", true);
            } else {
                return new NotificationSwitchDTO("Event has finished", false);
            }

        }
        return new NotificationSwitchDTO("Event not found", false);
    }

    @Override
    public void doSmth() {

    }

}
