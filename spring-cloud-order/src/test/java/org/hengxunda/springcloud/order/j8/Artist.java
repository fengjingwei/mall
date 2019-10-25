package org.hengxunda.springcloud.order.j8;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public final class Artist {

    private String name;

    private List<Artist> members;

    private String nationality;

    public Artist(String name, String nationality) {
        this(name, Collections.emptyList(), nationality);
    }

    public Artist(String name, List<Artist> members, String nationality) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(members);
        Objects.requireNonNull(nationality);
        this.name = name;
        this.members = Lists.newArrayList(members);
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public Stream<Artist> getMembers() {
        return members.stream();
    }

    public String getNationality() {
        return nationality;
    }

    public boolean isSolo() {
        return members.isEmpty();
    }

    public boolean isFrom(String nationality) {
        return this.nationality.equals(nationality);
    }

}
