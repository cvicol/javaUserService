package com.helkaerea.user.service.user;

import org.easymock.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.anyInt;

public class UserServiceTutorialTest extends EasyMockSupport {

    // -- 0. Setup of Tutorial --

    /*
     * In the tutorial we use the Person object.
     * Also the PersonRepository and PersonService are used.
     *
     * We will have a look at a complete different way of using matchers.
     * In EasyMock you can define expectations on mocked interfaces.
     * We call this from our UserService, the layer on top of the repository.
     * If we want to test the UserService, that it actually calls the repository,
     * we have to mock the repository.
     */

    // -- 1. Matchers and EasyMock --

    /*
     * We use EasyMock.
     */

    UserRepository repository = mock(UserRepository.class);

    /*
     * Imagine we try to add a new user.
     * We create the new user.
     * We define the expectation that the repository is called to add the new user.
     *
     * We call the userService and check that our expectation on the mock was valid
     * (by calling verifyall);
     */

    @Test
    public void testCallToRepositoryByTestingService() throws Exception {
        UserService userService = new UserService(repository);
        User newUser = new User("Claudia", 42);

        repository.addNewUser(newUser); // the expectation on the mock

        replayAll();
        userService.add(newUser);
        verifyAll();
    }

    /*
     * Note: the addNewUser() method is void, so NO EasyMock.expect() around
     * the method call, and no .andReturn() after.
     *
     * If the Person object would contain more fields, it can be a lot of work to
     * create a Person object which exactly matches the object which is passed to
     * the repository.
     * There are two solutions to handle this:
     * 1. Write an EasyMock matcher to express the expectation the the repository
     *    is called with an User object with a given name (for instance).
     * 2. Capture the User object and check manually one or more fields to make
     *    sure that the right object was passed to the repository.
     */

    /*
     * 1. Write an EasyMock matcher.
     *
     * With an matcher in EasyMock we make sure that the name of the person, passed to
     * the repository, is "Claudia".
     *
     * The return type of the method should be User, because that is what the addNewUser()
     * method expects to get passed in.
     * The method signature will look like this:
     *
     * private User aUserWithName(String name) {
     *     // do the actual matching stuff
     *     return null; // because EasyMock did already a match, it will not use the
     *                  // return of this method.
     * }
     *
     * The matcher should be reported to EasyMock.
     */

    private User aUserWithName(final String expectedName) {
        EasyMock.reportMatcher(new IArgumentMatcher() {
            @Override
            public boolean matches(Object argument) {
                User person = (User) argument;
                return expectedName.equals(person.getName());
            }

            @Override
            public void appendTo(StringBuffer buffer) {
                buffer.append("a user with name: " + expectedName);
            }
        });
        return null;
    }

    /*
     * We report a new matcher to EasyMock.
     * The actual matcher is an anonymous inner class of IArgumentMatcher.
     *
     * For the matchers() method, we can do whatever we want to make
     * sure the object was the right one. In this case we check the name.
     *
     * Note the cast (which is valid here, if it can't cast to Person
     * something was wrong, so it's okay we get an exception!) and note
     * that the expectedName became final (because we have to use it in the
     * inner class - and it should not change...).
     *
     * We also get a stringBuffer where we can append an indicating message
     * which will be printed if the matching failed.
     *
     * Let's try!
     */

    @Test
    public void easyMockMatcher() throws Exception {
        UserService userService = new UserService(repository);
        User newUser = new User("Claudia", 42);

        repository.addNewUser(aUserWithName("Claudia")); // the new expectation on the mock

        replayAll();
        userService.add(newUser);
        verifyAll();
    }

    /*
     * It works!
     *
     * --> Try to make an EasyMock matcher for the age of a user.
     */

    /*
     * 2. Capture the person object.
     *
     * EasyMock provides a Capture object with which you can make a snapshot
     * of an object at the time it was passed into the expected method call.
     * It will not do any matching on that moment, you have to do them
     * yourself afterwards.
     *
     * The implementation is quite straightforward.
     */

    @Test
    public void capturePerson() throws Exception {
        UserService userService = new UserService(repository);
        User newUser = new User("Claudia", 42);

        Capture<User> capturedUser = new Capture<User>();
        repository.addNewUser(EasyMock.capture(capturedUser)); // the new expectation on the mock

        replayAll();
        userService.add(newUser);
        verifyAll();

        User actualUser = capturedUser.getValue();
        Assert.assertThat(actualUser.getName(), Matchers.is("Claudia"));
    }


    @Test
    public void capturePersonWithNewCapture() throws Exception {
        UserService userService = new UserService(repository);
        User newUser = new User("Claudia", 42);

        Capture<User> capturedUser = EasyMock.newCapture();

        repository.addNewUser(EasyMock.capture(capturedUser)); // the new expectation on the mock

        replayAll();
        userService.add(newUser);
        verifyAll();

        User actualUser = capturedUser.getValue();
        Assert.assertThat(actualUser.getName(), Matchers.is("Claudia"));
    }
    /*
     * Note that you have to call EasyMock.capture() on the captured object at the place you want
     * to have the 'snapshot'.
     * Afterwards (i.e. after verifying the mocks - control.verify()), you get the snapshot
     * object with capturedObject.getValue();
     * Now you can check everything you want on that object which was passed to the method
     * of the mocked repository.
     *
     * --> Try to make a capture construction for the age of a person.
     */


    /*
      * It gets easier if we can contruct the objects or they are easy
     */

    @Test
    public void verifyAllParameters() throws Exception {
        UserService userService = new UserService(repository);
        User newUser = new User("Claudia", 42);

        repository.addNewUserWith(EasyMock.anyString(), anyInt()); // the new expectation on the mock

        replayAll();
        userService.addWithComponent(newUser);
        verifyAll();
    }

    @Test
    public void verifyAllParametersExactly() throws Exception {
        UserService userService = new UserService(repository);
        User newUser = new User("Claudia", 42);

        repository.addNewUserWith("Claudia", 42); // the new expectation on the mock

        replayAll();
        userService.addWithComponent(newUser);
        verifyAll();
    }

    @Test
    public void verifyAllParametersExactlyOneWeDoNOtCare() throws Exception {
        UserService userService = new UserService(repository);
        User newUser = new User("Claudia", 42);

        repository.addNewUserWith(EasyMock.eq("Claudia"), anyInt()); // the new expectation on the mock

        replayAll();
        userService.addWithComponent(newUser);
        verifyAll();
    }



}