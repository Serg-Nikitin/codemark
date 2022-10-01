package ru.nikitin.userservice.codemark.to;

import org.springframework.data.util.Streamable;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Roles implements Streamable<Role> {

    private final Streamable<Role> streamable;

    public Roles(Streamable<Role> streamable) {
        this.streamable = streamable;
    }

    static Roles of(Streamable<Role> streamable) {
        return new Roles(streamable);
    }

    @Override
    public Iterator<Role> iterator() {
        return streamable.iterator();
    }

    public UserTo createUserTo() {
        return streamable
                .stream()
                .collect(RolesCollector.getUserTo());
    }

    static class RolesCollector implements Collector<Role, HashMap<User, Set<String>>, UserTo> {

        public static RolesCollector getUserTo() {
            return new RolesCollector();
        }

        @Override
        public Supplier<HashMap<User, Set<String>>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<HashMap<User, Set<String>>, Role> accumulator() {
            return (map, role) -> {
                map.putIfAbsent(role.getUser(), new HashSet<>());
                map.get(role.getUser()).add(role.getRole_name());
            };
        }

        @Override
        public BinaryOperator<HashMap<User, Set<String>>> combiner() {
            return (map, map2) -> map;
        }

        @Override
        public Function<HashMap<User, Set<String>>, UserTo> finisher() {
            return map -> {
                User user = map.keySet().stream().findFirst().orElseThrow(() -> new NotFoundException("Streamable Roles not found user"));
                List<String> list = map.get(user).stream().sorted().toList();
                return new UserTo(user, list);
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.UNORDERED);
        }
    }


}
