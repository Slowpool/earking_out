inspired by https://tonedear.com/

Earking out = ear | working out;

Brief description:

Just pet app to train perfect pitch. there're billions of similar apps, but this one is the best in my honest opinion, because i can modify it on my own and do whatever i want.
I also develop it in learning purposes - Java(and FX), DDD, TDD, PSQL

Ubiquitous langauge:

1. Exercise - some type of MUSICAL training activity, that is conducted in order to improve one narrowly focused skill. E.g.: perfect pitch, intervals guessing (in sound way and in visual note notation)
2. Puzzle - one set/repetition/step of exercise, e.g. in perfect pitch app generates some note for user to guess. here the generated note = puzzle.
3. Guess - assumption of user about the correct solution to the previously proposed Puzzle (see p.2). E.g.: in perfect pitch exercise (see p.1) app generated `Java` note (oops, typo. actually i meant `app generated C# note`. but who cares, they are almost the same visually) note and user thought it's `C` note and pressed `C`, here the `C` is guess (of user)
4. Hint - some multimedia item (sound, picture, video), that is used by user to make guesses. e.g., consider this situation: perfect pitch exercise. audio format. app gives to user a puzzle to guess the note and the solution is `C#1` note, the hint here would be is .mp3 sound of `C#1` - by this hint user is supposed to approach the right solution - that is why it's a hint.
// TODO
5. Puzzle config - user-configured settings, specific for concrete exercise. e.g. in perfect pitch exercise user can pick on which notes to train.
6. Session - after picking the exercise and setting up the puzzle config, the exercise is started and session is started also. it has info about current puzzle config. // TODO dunno yet: ~~technically session is UUID, assigned to all events, that happen until the end of the exercise~~

x. Achievement

P.S. most points are are made-up-by-me terms actually, cuz dunno how to correctly name those actions/phenomena - there're no any definitions for them in natural english language (neither in russian though). so they are kind of not obvious without definition.

domains/subdomain:
1. Training ear for music
   a) ear training via exercises
   b) statistics dashboard

P.S. yep, the sole domain cuz i ain't gonna difficulty (the ending is pronounced like in simplify) things with separating it in two different domains (the training itself and statistics)
