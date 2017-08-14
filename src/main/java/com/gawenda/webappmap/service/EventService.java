package com.gawenda.webappmap.service;

import com.gawenda.webappmap.domain.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    List<Event> eventList = new ArrayList<>();

    private List<Event> createEventList (){

        eventList.add(new Event("Roadworks", 52.400, 16.923,
                LocalDateTime.now().minusMinutes(119).toInstant(ZoneOffset.UTC).getEpochSecond(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond(), "Test Street1"));
        eventList.add(new Event("Traffic jam", 52.406, 16.925,
                LocalDateTime.now().minusHours(2).toInstant(ZoneOffset.UTC).getEpochSecond(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond(), "Test Street2"));
        eventList.add(new Event("Public transport breakdown", 52.402, 16.927,
                LocalDateTime.now().minusHours(2).toInstant(ZoneOffset.UTC).getEpochSecond(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond(), "Test Street3"));
        eventList.add(new Event("Roadworks", 52.409, 16.924,
                LocalDateTime.now().minusHours(2).toInstant(ZoneOffset.UTC).getEpochSecond(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond(), "Test Street4"));
        eventList.add(new Event("Traffic jam", 52.410, 16.923,
                LocalDateTime.now().minusHours(2).toInstant(ZoneOffset.UTC).getEpochSecond(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond(), "Test Street5"));

        return eventList;
    }

    private Double getDistance(Double latUser, Double lonUser, Double latEvent, Double lonEvent) {
        return Math.sqrt(Math.pow((latEvent - latUser), 2)
                + Math.pow(((Math.cos(((latUser * Math.PI) / 180))) * (lonEvent - lonUser)), 2)) * (40075.704 / 360);
    }

    public List<Event> filterEventList (Double latitude, Double longitude, Boolean roadworks, Boolean traffic,
                                        Boolean breakdowns, Double radius) {

        List<Event> filteredEventList = new ArrayList<>();
        long currentDate = LocalDateTime.now().minusHours(2).toInstant(ZoneOffset.UTC).getEpochSecond();
        List<Event> baseList;
        if (eventList.size() == 0) {
            baseList = createEventList();
         } else {
            baseList = eventList;
        }

        for (Event event: baseList) {
            Double distance = getDistance(latitude, longitude, event.getLatitude(), event.getLongitude());
            if (distance <= radius && currentDate >= event.getStartDate() && currentDate <= event.getEndDate() ) {
                if (roadworks && event.getType().equals("Roadworks")) {
                    filteredEventList.add(event);
                }
                else if (traffic && event.getType().equals("Traffic jam")) {
                    filteredEventList.add(event);
                }
                else if (breakdowns && event.getType().equals("Public transport breakdown")) {
                    filteredEventList.add(event);
                }
            }
        }
        return filteredEventList;
    }
}
