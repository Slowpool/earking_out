// DISCLAIMER: i'm not a frontend developer, so i allow the mess here
const WHITE = 'WHITE';
const BLACK = 'BLACK';

var pressedPianoKey = null;
var playingAudios = {};

document.addEventListener('mousedown', function (e) {
    var pianoKey = e.target.closest('.piano-key')
    if (pianoKey == null) {
        return;
    }

    pressedPianoKey = pianoKey;
    // TODO remove it. added in experimenting purposes
    playPianoKeySound(pianoKey.getAttribute('data-keyNumber'));

    var pianoKeyboard = pianoKey.closest('.piano-keyboard.puzzle-config');
    if (pianoKeyboard) {
        const pianoKeyboardId = pianoKeyboard.getAttribute("data-pianoKeyboardId")
        const keyNumber = pianoKey.getAttribute('data-keyNumber');

        ajax('POST', '/api/v1/puzzle/config/perfect-pitch/audio/piano-keyboard/press-key',
            {
                body: {
                    pianoKeyboardId: pianoKeyboardId,
                    pianoKeyNumber: keyNumber
                },
                handler: function (response) {
                    // TODO display errors
                    console.log(`response: ${JSON.stringify(response)}`);
                    updatePianoKeyboardState(pianoKeyboard, response.pianoKeyboard);
                }
            }
        );
        return;
    }

    // TODO looking for other piano keyboard types
});

document.addEventListener('mouseup', function (e) {
    if (pressedPianoKey) {
        var pianoKey = pressedPianoKey;
    }
    else {
        return;
        
    }

    var pianoKeyboard = pianoKey.closest('.piano-keyboard.puzzle-config');
    if (pianoKeyboard) {
        const pianoKeyboardId = pianoKeyboard.getAttribute("data-pianoKeyboardId")
        const keyNumber = pianoKey.getAttribute('data-keyNumber');

        ajax('POST', '/api/v1/puzzle/config/perfect-pitch/audio/piano-keyboard/release-key',
            {
                body: {
                    pianoKeyboardId: pianoKeyboardId,
                    pianoKeyNumber: keyNumber
                },
                handler: function (response) {
                    // TODO display errors
                    console.log(`response: ${JSON.stringify(response)}`);
                    updatePianoKeyboardState(pianoKeyboard, response.pianoKeyboard);
                }
            }
        );
        return;
    }

    // TODO looking for other piano keyboard types
});

function ajax(method, url, options = {}) {
    const xhr = new XMLHttpRequest();
    xhr.open(method.toUpperCase(), url, true);
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