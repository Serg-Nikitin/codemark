package ru.nikitin.userservice.codemark;

import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public interface MatcherFactory {
    private static <T> Matcher<T> usingAssertions(Class<T> clazz, BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> asertions) {
        return new Matcher<>(clazz, assertion, asertions);
    }

    static <T> Matcher<T> usingEqualsComparator(Class<T> clazz) {
        return usingAssertions(clazz,
                (a, e) -> assertThat(a).isEqualTo(e),
                (a, e) -> assertThat(a).isEqualTo(e)
        );
    }

    static <T> Matcher<T> usingIgnoringFieldsComparator(Class<T> clazz, String... fieldsToIgnoring) {
        return usingAssertions(clazz,
                (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields(fieldsToIgnoring).isEqualTo(e),
                (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnoring).isEqualTo(e));
    }


    class Matcher<T> {
        private final Class<T> clazz;
        private final BiConsumer<T, T> assertion;
        private final BiConsumer<Iterable<T>, Iterable<T>> iterableAssertions;

        public Matcher(Class<T> clazz, BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> assertions) {
            this.clazz = clazz;
            this.assertion = assertion;
            this.iterableAssertions = assertions;
        }

        public void assertMatch(T a, T e) {
            assertion.accept(a, e);
        }

        public void assertMatch(Iterable<T> a, T... e) {
            assertMatch(a, List.of(e));
        }

        public void assertMatch(Iterable<T> a, Iterable<T> e) {
            iterableAssertions.accept(a, e);
        }


    }


}
