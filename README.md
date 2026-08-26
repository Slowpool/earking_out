Inspired by https://tonedear.com/

Earking out = Ear + working out;

# Brief description

An app to train **perfect pitch** and related musical skills. There are billions of similar apps, but this one is the best in my honest opinion, at least because I can modify it on my own and do **whatever** i want.
I also develop it in learning purposes - Java, JavaFX, DDD, TDD, Event sourcing, PosgreSQL, SQLite, Hibernate

# Ubiquitous langauge

### The vast majority of points are made-up-by-me terms, cuz dunno how to correctly name these actions/phenomena. There're no any definitions for them neither in english nor in russian. So, they may be not clear without these definitions.

1. `Exercise` - some type of musical **training activity**, that is conducted in order to improve the one narrowly focused skill.

   <img width="854" height="480" alt="Screencast from 2026-08-26 11-04-15 (online-video-cutter com)" src="https://github.com/user-attachments/assets/0e7fcb6b-4b95-4794-a390-7be114132e97" />

   `Exercise` can be:
   1. `Audio` - user **listens** sounds and guesses basing on them. E.g.: **.mp3** sound of `C#1` note
   2. `Visual` - user **watches** at visual views and guesses basing on them. E.g.: `C#1` note on the stave

2. `Puzzle` - one independent **set/repetition/step** of `Exercise`.

   Scenario:
   1. User starts the `Audio perfect pitch` exercise
   2. App generates one `Puzzle` with some note (this note is `Solution`)
   3. User makes one or several `Guesses` and eventually makes successful `Guess`
   4. App generates the next `Puzzle`

3. `Guess` - assumpted **solution** to the current `Puzzle`.
   ### Technically, there's no special `Guess` entity - app manipulates with `Solution` everywhere.

   It's provided by user. `Guess` can be either `Successful` or `Wrong`.

   Scenario:
   1. In `Audio perfect pitch` exercise the app generated some note
   2. User thinks: "Is it `C#1`?"
   3. User presses `C#1` on piano keyboard. Here the `C#1` is `Guess`

4. `Solution` - the correct **answer** to `Puzzle`.

   Scenario:
   In `Audio perfect pitch` exercise app generated `C#1` note as `Solution`. If user will press any note except `C#1`, the guess will be wrong. When and only when user pressed `C#1` note, the guess is correct.

5. `Hint` - **multimedia item** (sound, picture, video, etc.), which is used by user to make guesses.

   Scenario:
   1. User starts the `Audio perfect pitch` exercise
   2. App gives the user a `Puzzle` with `C#1` note as `Solution`. The `Hint` in this case is playing the **.mp3** sound file of `C#1` note. With this `Hint` user is supposed to approach the `Solution` - that is why it's a `Hint`

6. `Puzzle config` - user-configured **settings**, specific for and relating to the concrete `Exercise`.

   In any `Perfect pitch` exercise user can pick on which **notes** to train. It can be done via `Puzzle config`. App will generate `Solutions` with only those notes.

   Whereas in `Chords guessing` exercise a user can pick on which **chords** to train. It shows that each `Puzzle config` has it's own properties structure.

   <img width="1920" height="699" alt="image" src="https://github.com/user-attachments/assets/93ff75c5-6505-4465-a45a-42ef091a5920" />

7. `Session` - after picking an `Exercise` and setting up the `Puzzle config`, user wants to start the **guessing process** and app does it via `Session` starting.

   `Session` has unique id.

   When app creates the next `Puzzle` and generates the `Solution` for it, it watches at the current `Puzzle config`.

   `Session` can be in one of these states:
   - `In progress` - means the `Puzzle` guessing is in process
   - `Completed` - `Puzzle` guessing is finished **naturally** due to finishing the last `Puzzle`
   - `Aborted` - `Puzzle` guessing is finished **manually** via "abort" button, when user have yet not completed the specified target number of `Puzzles`

   If the `Session` is `Completed` or `Aborted`, it cannot be changed any more.

// TODO the draft is further
x. Achievement



domains/subdomain:
1. Training ear for music
   a) ear training via exercises
   b) statistics dashboard

P.S. yep, the sole domain cuz i ain't gonna difficulty (the ending is pronounced like in simplify) things with separating it in two different domains (the training itself and statistics)


technical details:
* in general, the whole app is supposed to be tightly coupled to spring.boot. because it's decoupling framework by itself. although it's still possible to decouple it and it'd be fine for really high complexity software.
* domain events publishing and subscribing interface is decoupled via port, so that implementation can be replaced, current one is via spring.boot.
* di container is decoupled. two implementaions of it exist:
1. Spring framework (for real app utilizing). so, yes, spring annotations aren't used
2. handmade DI (for tests)
// TODO two different builds: 1. web 2. desktop. - they implement the same app port (general ui code), though the core is the same.
