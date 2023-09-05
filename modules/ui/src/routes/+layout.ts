import {loadTranslations} from '$lib/services/I18nService';

/** @type {import('@sveltejs/kit').Load} */
export async function load({ url }) {
    const { pathname } = url;

    const initLocale = 'en'; // get from cookie, user session, ...

    await loadTranslations(initLocale, pathname); // keep this just before the `return`

    return {};
}
