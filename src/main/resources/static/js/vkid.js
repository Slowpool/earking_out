import pkceChallenge from 'pkce-challenge';

(async() => {
    if('VKIDSDK' in window) {
        const VKID = window.VKIDSDK;
        const { codeVerifier, codeChallenge } = await pkceChallenge();

        VKID.Config.init({
            app: 54834142,
            redirectUrl: 'https://earking-out.ru/oauth/vk-id/callback',
            responseMode: VKID.ConfigResponseMode.Callback,
            source: VKID.ConfigSource.LOWCODE,
            scope: 'vkid.profile_info',
            codeChallenge: codeChallenge,
            // TODO is it necessary?
            codeChallengeMethod: 'S256'
        });

        const oneTap = new VKID.OneTap();

        oneTap.render({
            container: document.currentScript.parentElement,
            fastAuthEnabled: false,
            showAlternativeLogin: true,
            styles: {
                borderRadius: 4,
                width: 175,
                height: 32
            }
        })
            .on(VKID.WidgetEvents.ERROR, vkidOnError)
            .on(VKID.OneTapInternalEvents.LOGIN_SUCCESS, onSuccessfulVkidLogin);

        function onSuccessfulVkidLogin(payload) {
            const body = {
                code: payload.code,
                state: payload.state,
                deviceId: payload.device_id,
                codeVerifier: codeVerifier
            };
            finishVkidOauth(body, function (response) {
                alert('after sending the user info to db: ' + response);
                location.reload();
            });
        }

        function vkidOnError(error) {
            alert('something went wrong, try again');
        }
    }
})();