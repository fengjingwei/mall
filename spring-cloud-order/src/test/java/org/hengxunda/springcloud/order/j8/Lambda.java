package org.hengxunda.springcloud.order.j8;

import com.google.common.collect.Lists;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda {

    private static final ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E")));

    public static void main(String[] args) {

        Runnable noArguments = () -> System.out.println("Hello");
        noArguments.run();

        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
        System.out.println(addExplicit.apply(6L, 8L));

        Predicate<Integer> atLeast5 = (x -> x > 5);
        System.out.println(atLeast5.test(3));

        System.out.println(formatter.get().getFormat().format(new Date()));

        List<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Dw", "London"));
        artists.add(new Artist("Jk", "London"));
        artists.add(new Artist("Lucy", "China"));
        long count = artists.stream().filter(artist -> artist.isFrom("London")).count();
        System.out.println(count);

        artists.stream().filter(artist -> {
            System.out.println(artist.getName());
            return artist.isFrom("London");
        });

        artists.stream().filter(artist -> {
            System.out.println(artist.getName());
            return artist.isFrom("London");
        }).count();

        List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());
        System.out.println(collected.size());
        collected.forEach(System.out::println);

        List<String> list = Stream.of("hello", "to", "java").map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(list);

        Optional<String> name = Stream.of(artists).map(artist -> artist.get(0).getName()).findFirst();
        name.ifPresent(System.out::println);

        List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(together.size());

        List<Track> tracks = Arrays.asList(new Track("Bakai", 524), new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));
        Track shortestTrack = tracks.stream().max(Comparator.comparing(Track::getCount)).get();
        System.out.println(shortestTrack.getName());

        FunctionInterface addFunction = (x, y) -> x + y;
        System.err.println(addFunction.operate(12, 56));

        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();

        List<Integer> numbers = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            numbers.add(i);
        }

        List<Integer> sequentialList = numbers.stream().filter(number -> number > 2).collect(Collectors.toList());
        System.out.println(sequentialList);

        List<String> languages = Arrays.asList("java", "C", "C++", "C#", "JavaScript", null);

        languages.parallelStream().filter(e -> e != null && e.length() > 3).forEach(System.out::println);

        languages = languages.stream().filter(Objects::nonNull).map(e -> e.toUpperCase() + "-language")
                .collect(Collectors.toList());
        System.out.println(languages);

        languages = languages.parallelStream().sorted().collect(Collectors.toList());
        System.out.println(languages);

        languages = languages.parallelStream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(languages);

        Stream<Integer> number = Stream.of(1, 2, 3, 4, 5, 0, 9);
        System.out.println(number.count());

        System.out.println(Stream.of(1, 9, 3).reduce(9, (acc, element) -> acc + element));

        System.out.println(addUp(Stream.of(56, 11, 3)));

        "aBCde".chars().filter(Character::isLowerCase).forEach(System.out::println);

        String[] strArr = {"java8", "is", "easy", "to", "use", "to", "is"};

        List<String> distinctStr = Arrays.stream(strArr)
                .map(str -> str.split(","))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        distinctStr.forEach(System.out::println);
    }

    private static int addUp(Stream<Integer> numbers) {
        return numbers.reduce((x, y) -> x + y).get();
    }

}
