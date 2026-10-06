// DISCLAIMER: i'm not a frontend developer, so i allow the mess here
const WHITE = 'WHITE';
const BLACK = 'BLACK';

const SESSION_COMPLETED = 'COMPLETED';

const PUZZLE_CONFIG_CONTEXT = 'PUZZLE_CONFIG_CONTEXT';
const PUZZLE_CONTEXT = 'PUZZLE_CONTEXT';

const PRESS = 'PRESS';
const RELEASE = 'RELEASE';

const NOTES_AS_TEXT_MODE = 'NOTES_AS_TEXT';
const KEYBOARD_AS_PIANO_MODE = 'KEYBOARD_AS_PIANO';

const MAIN_CONTENT = '#main-content';

var pressedPianoKey = null;
var playingAudios = {};
var sessionState = null;

document.addEventListener('mousedown', function (e) {
    if (!isLMB(e.button)) {
        return;
    }

    var pianoKey = e.target.closest('.piano-key')
    if (pianoKey === null) {
        return;
    }

    // sound playing logic is duplicated on frontend for performance
    playPianoKeySound(pianoKey);

    var pianoKeyboard = pianoKey.closest('.piano-keyboard.puzzle-config');
    if (pianoKeyboard) {
        if (pressedPianoKey) {
            sendPianoKeyAction(RELEASE, PUZZLE_CONFIG_CONTEXT, pianoKeyboard, pressedPianoKey);
        }
        pressedPianoKey = pianoKey;

        sendPianoKeyAction(PRESS, PUZZLE_CONFIG_CONTEXT, pianoKeyboard, pianoKey);
        return;
    }

    pianoKeyboard = pianoKey.closest('.piano-keyboard.perfect-pitch-guessing');
    if (pianoKeyboard) {
        if (pressedPianoKey) {
            sendPianoKeyAction(RELEASE, PUZZLE_CONTEXT, pianoKeyboard, pressedPianoKey);
        }
        pressedPianoKey = pianoKey;

        let sessionId = findSessionId();
        sendPianoKeyAction(PRESS, PUZZLE_CONTEXT, pianoKeyboard, pianoKey, {
            callback: function (response) {
                updatePuzzleAfterGuess(response.guessResult);
            },
            queryString: `?sessionId=${sessionId}`
        });
        return;
    }
});

document.addEventListener('mouseup', function (e) {
    if (!isLMB(e.button)) {
        return;
    }

    if (!pressedPianoKey) {
        return;
    }

    var pianoKey = pressedPianoKey;
    pressedPianoKey = null;

    var pianoKeyboard = pianoKey.closest('.piano-keyboard.puzzle-config');
    if (pianoKeyboard) {
        sendPianoKeyAction(RELEASE, PUZZLE_CONFIG_CONTEXT, pianoKeyboard, pianoKey);
        return;
    }

    pianoKeyboard = pianoKey.closest('.piano-keyboard.perfect-pitch-guessing');
    if (pianoKeyboard) {
        if (sessionState == SESSION_COMPLETED) {
            // if session is completed, no new events can be thrown, including piano key releasing. so, dodging the piano key releasing on ui level
            return;
        }

        sendPianoKeyAction(RELEASE, PUZZLE_CONTEXT, pianoKeyboard, pianoKey);
        return;
    }
});

document.addEventListener('keydown', function (e) {
    if (e.key !== 'Enter') {
        return;
    }

    let target = e.target;
    if (target.getAttribute('id') !== 'perfect-pitch-guessing-textbox') {
        return;
    }

    let sessionId = findSessionId();
    let guess = normalizeNoteFormat(target.value);
    if (!validateNote(guess)) {
        showMessage(guess ? `\`${target.value}\` is invalid or out of range note` : `Invalid note`);
        return;
    }

    ajax('POST', `/api/v1/puzzle/perfect-pitch/audio/guess?sessionId=${sessionId}`, {
        body: {
            note: guess
        },
        handler: function (response) {
            updatePuzzleAfterGuess(response);
            target.value = '';
            if (!sessionState == SESSION_COMPLETED && response.guessIsSuccessful) {
                clearMessage();
            }
            else {
                showMessage(guess ? `${guess} is wrong` : `Wrong guess`);
            }
        }
    })
});


htmx.on('htmx:afterRequest', function (evt) {
    let sourceElement = evt.detail.elt;
    if (sourceElement && (sourceElement.getAttribute('id') === 'perfect-pitch-input-mode')) {
        let selectedElement = sourceElement.options[sourceElement.selectedIndex];
        if (selectedElement && (selectedElement.getAttribute('value') === KEYBOARD_AS_PIANO_MODE)) {
            var shouldHideRootNotePiano = false;
        }
        else {
            var shouldHideRootNotePiano = true;
        }
        let rootNotePiano = document.querySelector("#audio-perfect-pitch-root-note");
        if (shouldHideRootNotePiano) {
            rootNotePiano.setAttribute('hidden', true);
        }
        else {
            rootNotePiano.removeAttribute('hidden');
        }
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

function playPianoKeySound(pianoKey) {
    var pianoKeyboard = pianoKey.closest(".piano-keyboard");

    var shouldPlaySound = pianoKeyboard.getAttribute("data-areKeySoundsEnabled");
    if (shouldPlaySound === 'false') {
        return;
    }

    var pianoKeyNumber = pianoKey.getAttribute('data-keyNumber');
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

    var puzzleProgressBar = document.querySelector("#puzzle-progress");
    puzzleProgressBar.setAttribute("value", newCompletedPuzzlesNumber);
}

function updateHint(newHint) {
    var hintElement = document.querySelector("#audio-perfect-pitch-hint-player");
    hintElement.setAttribute("src", newHint);
}

function sendPianoKeyAction(action, pianoKeyboardContext, pianoKeyboard, pianoKey, { callback = null, queryString = '' } = {}) {
    const pianoKeyboardId = pianoKeyboard.getAttribute("data-pianoKeyboardId")
    const keyNumber = pianoKey.getAttribute('data-keyNumber');

    var uri = pianoKeyboardContext == PUZZLE_CONFIG_CONTEXT
        ? `/api/v1/puzzle/config/perfect-pitch/audio/piano-keyboard/${action.toLowerCase()}-key${queryString}`
        : `/api/v1/puzzle/perfect-pitch/audio/piano-keyboard/${action.toLowerCase()}-key${queryString}`;

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

function setAsMainContent(html) {
    let mainContent = document.querySelector(MAIN_CONTENT);
    mainContent.innerHTML = html;

    htmx.process(mainContent);
}

// LMB - Left Mouse Button
function isLMB(mouseButton) {
    return mouseButton === 0;
}

function updatePuzzleAfterGuess(guessResult) {
    sessionState = guessResult.sessionState;
    if (sessionState == SESSION_COMPLETED) {
        setAsMainContent(guessResult.sessionStatsHtml);
    }
    else if (guessResult.guessIsSuccessful) {
        updateCompletedPuzzlesNumber(guessResult.numberOfCompletedPuzzles);
        updateHint(guessResult.newHint);
    }
}

function findSessionId() {
    return document.querySelector("#sessionId")
        .getAttribute("data-sessionId");
}

function clearMessage() {
    showMessage('');
}

function showMessage(message) {
    var messageBox = document.querySelector('.notification-box');
    if (messageBox) {
        messageBox.textContent = message;
    }
}

// eB4 => Eb4
// Eb4 => Eb4
function normalizeNoteFormat(note) {
    if (!note) {
        return '';
    }

    return note.charAt(0)
        .toUpperCase()
        + note.slice(1)
            .toLowerCase();
}

function validateNote(note) {
    let regexp = /^[A-G][b#]?[1-7]$/;
    return regexp.test(note);
}