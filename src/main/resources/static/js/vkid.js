import pkceChallenge from 'pkce-challenge';

(async () => {
    if ('VKIDSDK' in window) {
        const VKID = window.VKIDSDK;
        const { code_verifier, code_challenge } = await pkceChallenge();

        VKID.Config.init({
            app: 54834142,
            redirectUrl: 'https://earking-out.ru/oauth/vk-id/callback',
            responseMode: VKID.ConfigResponseMode.Callback,
            source: VKID.ConfigSource.LOWCODE,
            scope: 'vkid.profile_info',
            codeChallenge: code_challenge,
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
                width: 208,
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
                codeVerifier: code_verifier
            };
            finishVkidOauth(body, function (response) {
                // TODO check response, do something if it's not 200
                location.reload();
            });
        }

        function vkidOnError(error) {
            alert('something went wrong, try again');
        }
    }
})();