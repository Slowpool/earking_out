// DISCLAIMER: i'm not a frontend developer, so i allow the mess here

document.addEventListener('mousedown', function (e) {
    var pianoKey = e.target.closest('.piano-key')
    if (pianoKey == null) {
        return;
    }

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
                handler: function(response) {
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

    const bodyData = options.body ? JSON.stringify(options.body) : null;
    xhr.send(bodyData);
}

function updatePianoKeyboardState(pianoKeyboard, newState) {
    var whiteKeysLayer = pianoKeyboard
}