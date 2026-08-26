Inspired by https://tonedear.com/

Earking out = Ear + working out;

# Brief description

An app to train **perfect pitch** and related musical skills. There are billions of similar apps, but this one is the best in my honest opinion, at least because I can modify it on my own and do **whatever** i want.
I also develop it in learning purposes - Java, JavaFX, DDD, TDD, Event sourcing, PosgreSQL, SQLite, Hibernate

# Ubiquitous langauge

1. `Exercise` - some type of *musical* training activity, that is conducted in order to improve the one narrowly focused skill. E.g.: `Perfect pitch`, `Intervals guessing`.
   
   Exercise can be:
   1. `Audio` - user **listens** sounds and guesses basing on them. E.g.: **.mp3** sound of `C#1` note
   2. `Visual` - user **watches** at visual views and guesses basing on them. E.g.: `C#1` note on the stave
3. `Puzzle` - one independent set/repetition/step of exercise.

   Scenario:
   1. User starts the `Audio perfect pitch` exercise
   2. App generates one `Puzzle` with some note (this note is `Solution`)
   3. User makes one or several `Guesses` and eventually makes successful `Guess`
   4. App generates the next `Puzzle`
5. `Guess` - assumption of user about the correct solution to the previously proposed `Puzzle`. Technically, the entity of `Solution` is used everywhere as `Guess`. // TODO what to do if there's no such an entity in code?
6. `Guess` - 
7. `Solution` - assumption of user about the correct solution to the previously proposed `Puzzle`. E.g.: in perfect pitch exercise (see p.1) app generated `Java` note (oops, typo. actually i meant `app generated C# note`. but who cares, they are almost the same visually) note and user thought it's `C` note and pressed `C`, here the `C` is guess (of user)
8. `Hint` - some multimedia item (sound, picture, video), that is used by user to make guesses. e.g., consider this situation: perfect pitch exercise. audio format. app gives to user a puzzle to guess the note and the solution is `C#1` note, the hint here is playing the .mp3 sound of `C#1` - by this hint user is supposed to approach the right solution - that is why it's a hint.
9. `Puzzle config` - user-configured settings, specific for concrete exercise. e.g. in perfect pitch exercise user can pick on which notes to train, whereas in major guessing exercise user can pick on which accords to train.
10. `Session` - after picking the exercise and setting up the puzzle config, the exercise is started and session is started also. session has unique id. during the puzzle creating, it uses current puzzle config. session can be in states: `in progress` - means the puzzle guessing is in process; `completed` - puzzle guessing is finished naturally due to finishing the last puzzle; `aborted` - puzzle guesing is finished manually via "abort" button. after the session is completed or aborted, it cannot be changed any more.
// TODO
x. Achievement

P.S. most points are are made-up-by-me terms, cuz dunno how to correctly name those actions/phenomena - there're no any definitions for them in natural english language (neither in russian though). so they are kind of not obvious without definition.

domains/subdomain:
1. Training ear for music
   a) ear training via exercises
   b) statistics dashboard

P.S. yep, the sole domain cuz i ain't gonna difficulty (the ending is pronounced like in simplify) things with separating it in two different domains (the training itself and statistics)


technical details:
* in general, the whole app is supposed to be tightly coupled to spring.boot. because it's decoupling framework by itself (although it's still possible to decouple it and it'd be fine for high complexity software).
* domain events publishing and subscribing interface is decoupled via port, so that implementation can be replaced, current one is via spring.boot.
* di container is decoupled. two implementaions of it exist:
1. Spring framework (for real app utilizing). so, yes, spring annotations aren't used
2. handmade DI (for tests)
// TODO two different builds: 1. web 2. desktop. - they implement the same app port (general ui code), though the core is the same.
