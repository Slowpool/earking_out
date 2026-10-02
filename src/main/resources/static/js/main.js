// DISCLAIMER: i'm not a frontend developer, so i allow the mess here
const WHITE = 'WHITE';
const BLACK = 'BLACK';

const SESSION_COMPLETED = 'COMPLETED';

const PUZZLE_CONFIG_CONTEXT = 'PUZZLE_CONFIG_CONTEXT';
const PUZZLE_CONTEXT = 'PUZZLE_CONTEXT';

const PRESS = 'PRESS';
const RELEASE = 'RELEASE';

var pressedPianoKey = null;
var playingAudios = {};
var sessionState = null;

document.addEventListener('mousedown', function (e) {
    var pianoKey = e.target.closest('.piano-key')
    if (pianoKey == null) {
        return;
    }

    // TODO remove it. added in experimenting purposes
    playPianoKeySound(pianoKey.getAttribute('data-keyNumber'));

    var pianoKeyboard = pianoKey.closest('.piano-keyboard.puzzle-config');
    if (pianoKeyboard) {
        if (pressedPianoKey) {
            sendPianoKeyAction(RELEASE, PUZZLE_CONFIG_CONTEXT, pianoKeyboard, pressedPianoKey, null);
        }
        pressedPianoKey = pianoKey;

        sendPianoKeyAction(PRESS, PUZZLE_CONFIG_CONTEXT, pianoKeyboard, pianoKey, null);
        return;
    }

    pianoKeyboard = pianoKey.closest('.piano-keyboard.perfect-pitch-guessing');
    if (pianoKeyboard) {
        if (pressedPianoKey) {
            sendPianoKeyAction(RELEASE, PUZZLE_CONTEXT, pianoKeyboard, pressedPianoKey, null);
        }
        pressedPianoKey = pianoKey;

        sendPianoKeyAction(PRESS, PUZZLE_CONTEXT, pianoKeyboard, pianoKey, function (response) {
            sessionState = response.sessionState;
            if (sessionState == SESSION_COMPLETED) {
                // TODO
                alert("display session stats");
                return;
            }
            if (response.guessIsSuccessful) {
                updateCompletedPuzzlesNumber(response.numberOfCompletedPuzzles);
                updateHint(response.newHint);
            }
        });
        return;
    }
});

document.addEventListener('mouseup', function (e) {
    if (!pressedPianoKey) {
        return;
    }

    var pianoKey = pressedPianoKey;
    pressedPianoKey = null;

    var pianoKeyboard = pianoKey.closest('.piano-keyboard.puzzle-config');
    if (pianoKeyboard) {
        sendPianoKeyAction(RELEASE, PUZZLE_CONFIG_CONTEXT, pianoKeyboard, pianoKey, null);
        return;
    }

    pianoKeyboard = pianoKey.closest('.piano-keyboard.perfect-pitch-guessing');
    if (pianoKeyboard) {
        if (sessionState == SESSION_COMPLETED) {
            // if session is completed, no new events can be thrown, including piano key releasing. so, dodging the piano key releasing on ui level
            alert('dodging the piano key release');
            return;
        }

        sendPianoKeyAction(RELEASE, PUZZLE_CONTEXT, pianoKeyboard, pianoKey, null);
        return;
    }
});

function ajax(method, url, options = {}, async = true) {
    const xhr = new XMLHttpRequest();
    xhr.open(method.toUpperCase(), url, async);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Accept', 'application/json');

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseObject = JSON.parse(xhr.responseText);
            options.handler(responseObject);
        } else {
            console.error(`ajax failed, status: ${xhr.status}`);
        }
    };

    xhr.onerror = function () {
        console.error('network error');
    };

    const bodyData = options.body
        ? JSON.stringify(options.body)
        : null;
    xhr.send(bodyData);
}

function updatePianoKeyboardState(pianoKeyboard, newState) {
    var whiteKeysLayer = pianoKeyboard.querySelector('.white-keys-layer');
    var blackKeysLayer = pianoKeyboard.querySelector('.black-keys-layer');

    var whiteI = 0;
    var blackI = 0;
    for (var i = 0; i < newState.pianoKeys.length; i++) {
        let newPianoKey = newState.pianoKeys[i];
        if (newPianoKey.color == WHITE) {
            key = whiteKeysLayer.children[whiteI];
            whiteI++;
        }
        else {
            key = blackKeysLayer.children[blackI];
            blackI++;
        }
        key.setAttribute('data-isPressed', newPianoKey.pressed);
        key.setAttribute('data-isSelected', newPianoKey.selected);
    }
}

function playPianoKeySound(pianoKeyNumber) {
    if (playingAudios.length > 1) {
        stopAllSounds();
    }

    const audioPath = `/sounds/piano_keys/key${pianoKeyNumber}.wav`;
    const audio = new Audio(audioPath);

    playingAudios[pianoKeyNumber] = audio;
    audio.play();
}

function stopAllSounds() {
    // it may seem awkward that it stops only one sound, but it's because in future several sounds played the same moment feature will be added, probably with setting allowing to on/off this behavior
    playingAudios[0].pause();
    playingAudios[0].currentTime = 0;
}

function updateCompletedPuzzlesNumber(newCompletedPuzzlesNumber) {
    var numberOfPuzzlesElement = document.querySelector("#number-of-completed-puzzles");
    numberOfPuzzlesElement.textContent = newCompletedPuzzlesNumber;
}

function updateHint(newHint) {
    var hintElement = document.querySelector("#audio-perfect-pitch-hint-player");
    hintElement.setAttribute("src", newHint);
}

function sendPianoKeyAction(action, pianoKeyboardContext, pianoKeyboard, pianoKey, callback) {
    const pianoKeyboardId = pianoKeyboard.getAttribute("data-pianoKeyboardId")
    const keyNumber = pianoKey.getAttribute('data-keyNumber');

    var uri = pianoKeyboardContext == PUZZLE_CONFIG_CONTEXT
        ? `/api/v1/puzzle/config/perfect-pitch/audio/piano-keyboard/${action.toLowerCase()}-key`
        : `/api/v1/puzzle/perfect-pitch/audio/piano-keyboard/${action.toLowerCase()}-key`;

    ajax('POST', uri,
        {
            body: {
                pianoKeyboardId: pianoKeyboardId,
                pianoKeyNumber: keyNumber
            },
            handler: function (response) {
                console.log(`response: ${JSON.stringify(response)}`);
                updatePianoKeyboardState(pianoKeyboard, response.pianoKeyboard);

                if (callback) {
                    callback(response);
                }
            }
        }, action != PRESS
    );
}