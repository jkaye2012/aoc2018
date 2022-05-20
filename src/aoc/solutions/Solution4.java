package aoc.solutions;

import lombok.Getter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

class Day {
    @Getter
    private final LocalDate date;

    @Getter
    private final Integer guardId;

    @Getter
    private final Set<Integer> sleepingMinutes;

    public Day(LocalDate date, Integer guardId) {
        this.date = date;
        this.guardId = guardId;
        this.sleepingMinutes = new HashSet<>();
    }

    public void addSleepingRange(Integer start, Integer end) {
        IntStream.range(start, end).forEach(this.sleepingMinutes::add);
    }
}

public class Solution4 implements PuzzleSolution {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    static List<Day> parseDays(List<String> input) throws ParseException {
        var days = new ArrayList<Day>();
        Day current = null;
        LocalDateTime sleepTime = null;
        for (var i : input.stream().sorted().toList()) {
            var pieces = i.substring(1).split("] ");
            var date = LocalDateTime.parse(pieces[0], formatter);
            if (current == null) {
                var guardId = Integer.parseInt(pieces[1].split("#")[1].split(" ")[0]);
                current = new Day(date.toLocalDate(), guardId);
                days.add(current);
            } else if (pieces[1].startsWith("Guard")) {
                var guardId = Integer.parseInt(pieces[1].split("#")[1].split(" ")[0]);
                current = new Day(current.getDate().plusDays(1), guardId);
                days.add(current);
                sleepTime = null;
            } else if (sleepTime != null) {
                current.addSleepingRange(sleepTime.getMinute(), date.getMinute());
                sleepTime = null;
            } else {
                sleepTime = date;
            }
        }

        return days;
    }

    @Override
    public String solvePart1(List<String> input) {
        try {
            var daysByGuard = new HashMap<Integer, List<Day>>();
            var days = parseDays(input);
            for (var d : days) {
                daysByGuard.putIfAbsent(d.getGuardId(), new ArrayList<>());
                daysByGuard.get(d.getGuardId()).add(d);
            }

            Integer sleepiestGuardId = 0;
            Integer sleepiestMinutes = 0;
            for (var e : daysByGuard.entrySet()) {
                var mins = e.getValue().stream().reduce(0, (acc, d) -> acc + d.getSleepingMinutes().size(), Integer::sum);
                if (mins > sleepiestMinutes) {
                    sleepiestGuardId = e.getKey();
                    sleepiestMinutes = mins;
                }
            }

            var minutesSlept = new HashMap<Integer, Integer>();
            for (var e : daysByGuard.get(sleepiestGuardId)) {
                e.getSleepingMinutes().forEach(m -> minutesSlept.put(m, minutesSlept.getOrDefault(m, 0) + 1));
            }
            Integer maxMin = 0, maxMins = 0;
            for (var e : minutesSlept.entrySet()) {
                if (e.getValue() > maxMins) {
                    maxMin = e.getKey();
                    maxMins = e.getValue();
                }
            }

            return String.valueOf(maxMin * sleepiestGuardId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String solvePart2(List<String> input) {
        try {
            var daysByGuard = new HashMap<Integer, List<Day>>();
            var days = parseDays(input);
            for (var d : days) {
                daysByGuard.putIfAbsent(d.getGuardId(), new ArrayList<>());
                daysByGuard.get(d.getGuardId()).add(d);
            }

            Integer sleepiestGuardId = 0;
            Integer sleepiestFrequency = 0;
            Integer sleepiestMinute = 0;
            for (var e : daysByGuard.entrySet()) {
                var minutesSlept = new HashMap<Integer, Integer>();
                e.getValue().forEach(d -> d.getSleepingMinutes().forEach(m ->
                        minutesSlept.put(m, minutesSlept.getOrDefault(m, 0) + 1)));
                if (minutesSlept.isEmpty()) {
                    continue;
                }
                var sleepiest = Collections.max(minutesSlept.entrySet(),
                        Comparator.comparingInt(Map.Entry::getValue));
                if (sleepiest.getValue() > sleepiestFrequency) {
                    sleepiestGuardId = e.getKey();
                    sleepiestFrequency = sleepiest.getValue();
                    sleepiestMinute = sleepiest.getKey();
                }
            }

            return String.valueOf(sleepiestGuardId * sleepiestMinute);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
