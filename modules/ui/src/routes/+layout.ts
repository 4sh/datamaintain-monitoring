import {loadTranslations} from '$lib/services/I18nService';

/**
 * SSR is deactivated for E2E tests because hydration empties inputs
 */
export const ssr = false;

/** @type {import('@sveltejs/kit').Load} */
export async function load({ url }) {
    const { pathname } = url;

    const initLocale = 'en'; // get from cookie, user session, ...

    await loadTranslations(initLocale, pathname); // keep this just before the `return`

    return {};
}
