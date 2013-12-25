package org.llaith.toolkit.core.repository;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.junit.Test;
import org.llaith.toolkit.core.repository.impl.DefaultRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.llaith.toolkit.common.guava.MatcherPredicate.match;

/**
 *
 */
public class RepositoryTest {

    @Test
    public void indexingWorks() {

        final Set<String> set = new HashSet<String>() {{
            this.add("1");
            this.add("2");
            this.add("3");
        }};

        final Repository repo = new DefaultRepository(set);
        final FluentIterable<String> view = repo.select(String.class);
        final Map<Integer,String> index = view.uniqueIndex(new Function<String,Integer>() {
            public Integer apply(final String input) {
                return Integer.valueOf(input);
            }
        });

        final Map<Integer,String> desired = new HashMap<Integer,String>(){{
            this.put(1,"1");
            this.put(2,"2");
            this.put(3,"3");
        }};

        assertThat(index,is(equalTo(desired)));

    }

    @Test
    public void hancrestMatchingWorks() {

        final Set<String> set = new HashSet<String>() {{
            this.add("1");
            this.add("2");
            this.add("3");
        }};

        final Repository repo = new DefaultRepository(set);

        final FluentIterable<String> view = repo
                .select(String.class,match(anyOf(
                        equalTo("1"),
                        equalTo("2"))));

        assertThat(view.contains("1"),is(true));
        assertThat(view.contains("2"),is(true));
        assertThat(view.contains("3"),is(false));

    }

}
