package com.helkaerea.user.service.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * User represents someone with a name and an age.
 */
public class User implements Comparable<User>{

    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static int compareByNameThenAge(User user1, User user2) {
        if (user1.getName().equals(user2.getName())) {
            return user1.getAge() - (user2.getAge());
        } else {
            return user1.getName().compareTo(user2.getName());
        }
    }

    @Override
    public int compareTo(User o) {
       return this.getName().compareTo(o.getName());
    }
}
